/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ApprovalsDBbean.java
 * Creation/Modification History  :
 *
 * SH   5/1/07      Created
 *
 * Description
 * This class will handle db updates to approvals table.  It will insert or delete
 * records from table, get all approvals, determin if initial or final was approved,
 * and handle sending emails.
 *****************************************************************************/
package mypackage;
import java.sql.*;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;

import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ApprovalsDBbean 
{
  public ApprovalsDBbean()
  {
  }
  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  
   /**
   * This creates connection to oracle ldgrants db
   * @throws java.lang.Exception
   * @return 
   */
  private static Connection initializeConn() throws Exception
  {
      Context namingContext = new InitialContext();
      DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      return ds.getConnection();   
  }
  
  public void Close(Connection conn)
  {
    try
    {
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try
    {
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

   public void Close(ResultSet rs)
  {
    try
    {
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }
  
  
  /**
   * Method will determine whether to save or delete an initial or final approval
   * record for the grant based on the options the admin chose from the adminCheckStatus.jsp
   * page. Only admin with approve permissions are allowed to save/delete approvals.
   */
   public void saveAdminApproval(AppStatusBean asb, long grantid, UserBean userb)
  {
    boolean hasInitial=false, hasInterim=false, hasFinal=false, hasDeny = false, 
    hasDecline=false, hasFinalYr2=false, hasFinalYr3=false, hasReadyYr3=false, hasAmendYr1=false, hasAmendYr2=false, hasAmendYr3=false;
    
    try{      
      //check to see if initial and final were already approved
      hasInitial = isInitialAppApproved(grantid);
      hasFinal = isFinalAppApproved(grantid, false, 0);
      hasFinalYr2 = isFinalAppApproved(grantid, true, 2);
      hasFinalYr3 = isFinalAppApproved(grantid, true, 3);
      hasDeny = isAppDenied(grantid);
      hasInterim = isInterimAppApproved(grantid);
      hasDecline = isAppDeclined(grantid);
      hasReadyYr3 = isYr2ApprovedForYear3Start(grantid);
      // new 04/12/17 PER KBALSEN
      hasAmendYr1 = isAmendApprYr1(grantid);
      hasAmendYr2 = isAmendApprYr2(grantid);
      hasAmendYr3 = isAmendApprYr3(grantid);
      
      if(hasInitial)
      {
        if(asb.isInitialappr()==false)//grant has initial approval record but it needs to be deleted
            deleteAdminApproval(grantid, "Initial");      
      }
      else
      {
        if(asb.isInitialappr()){//grant needs an initial approval record
           insertApprovalRecord(grantid, userb, "Initial");
           
            if(hasDeny)
               deleteAdminDenial(grantid);
            if(hasDecline)
                deleteDecline(grantid);
        }
      }
      
      
      if(hasFinal){
        if(asb.isFinalappr()==false)//grant has final approval record but it needs to be deleted
           deleteAdminApproval(grantid, "Final");            
      }
      else{
        if(asb.isFinalappr())//grant needs final approval record
           insertApprovalRecord(grantid, userb, "Final");      
      }    
      
      
      if(hasFinalYr2){
          if(asb.isFinalappryear2()==false)//grant has final yr2 approval record but it needs to be deleted
             deleteAdminApproval(grantid, "Final Year2");            
      }
      else{
        if(asb.isFinalappryear2())//grant needs final yr2 approval record
           insertApprovalRecord(grantid, userb, "Final Year2");      
      } 
      
      if(hasFinalYr3){//yr 3 for literacy
          if(asb.isFinalappryear3()==false)//grant has final yr3 approval record but it needs to be deleted
             deleteAdminApproval(grantid, "Final Year3");            
      }
      else{
        if(asb.isFinalappryear3())//grant needs final yr3 approval record
           insertApprovalRecord(grantid, userb, "Final Year3");      
      } 
      
      
      if(hasDeny){
        if(asb.isAppDenied()==false)//grant has deny record already but it needs to be deleted
           deleteAdminDenial(grantid);            
      }
      else
      {
        if(asb.isAppDenied())
        {
           //grant needs a denial record
           insertDenialRecord(grantid, userb);
          
          //delete any prior approval records that had been saved
          if(hasInitial)
             deleteAdminApproval(grantid, "Initial");
          if(hasFinal)
             deleteAdminApproval(grantid, "Final");     
          if(hasDecline)
             deleteDecline(grantid);
             
          //reset any 'amt approved' back to zero for denied grants
          BudgetDBHandler bdh = new BudgetDBHandler();
          bdh.deleteAmtApprovedAll(grantid, userb.getUserid());
        }
      }
      
      if(hasInterim){
        if(asb.isInterimappr()==false)//grant has interim approval record but it needs to be deleted
            deleteAdminApproval(grantid, "Interim");      
      }
      else{
        if(asb.isInterimappr())//grant needs an interim approval record
           insertApprovalRecord(grantid, userb, "Interim");
      }
        
        
      //new 8/14/15 literacy per KBALSEN
      if(hasReadyYr3){
        if(asb.isReadyYear3()==false)//grant has ready year3 approval record but it needs to be deleted
            deleteAdminApproval(grantid, "Ready For Year3");  
      }
      else{
        if(asb.isReadyYear3())//grant needs ready year3 approval record
           insertApprovalRecord(grantid, userb, "Ready For Year3");
      }
        
      
			// new 04/12/17 PER KBALSEN
			ApprovalDao approvalDao = new ApprovalDao(); // From NEW CODE
			
			Long apprId = null;
			if (hasAmendYr1) {
				if (asb.isAmendApprYr1() == false)// grant has amend approval
													// record but it needs to be
													// deleted
					for (Approval approval : approvalDao.searchApprovalsByGrant(grantid)) {
						if (approval.getFormTypeId().longValue() == 5L && approval.getFyCode().longValue() == 17L) {
							apprId = approval.getId();
						}
					}

				approvalDao.delete(apprId);
			} else {
				if (asb.isAmendApprYr1()){// grant needs an amend approval record
					Approval approval = new Approval();
					User user = new User();
					//Populate approval domain object
					approval.setGrantId(grantid);
					approval.setApprovalType("Approve");
					approval.setFormTypeId(5L);
					approval.setFyCode(17L);
					approval.setVersion("fs10amendment");
					
					user.setCreatedBy(userb.getUserid());
					
					approvalDao.insert(approval, user);
				}
			}
		
			if (hasAmendYr2) {
				if (asb.isAmendApprYr2() == false)// grant has amend approval
													// record but it needs to be
													// deleted
					for (Approval approval : approvalDao.searchApprovalsByGrant(grantid)) {
						if (approval.getFormTypeId().longValue() == 5L && approval.getFyCode().longValue() == 18L) {
							apprId = approval.getId();
						}
					}

				approvalDao.delete(apprId);
			} else {
				if (asb.isAmendApprYr2()){// grant needs an amend approval record
					Approval approval = new Approval();
					User user = new User();
					//Populate approval domain object
					approval.setGrantId(grantid);
					approval.setApprovalType("Approve");
					approval.setFormTypeId(5L);
					approval.setFyCode(18L);
					approval.setVersion("fs10amendment");
					
					user.setCreatedBy(userb.getUserid());
					
					approvalDao.insert(approval, user);
				}
			}
		
			if (hasAmendYr3) {
				if (asb.isAmendApprYr3() == false)// grant has amend approval
													// record but it needs to be
													// deleted
					for (Approval approval : approvalDao.searchApprovalsByGrant(grantid)) {
						if (approval.getFormTypeId().longValue() == 5L && approval.getFyCode().longValue() == 19L) {
							apprId = approval.getId();
						}
					}

				approvalDao.delete(apprId);
			} else {
				if (asb.isAmendApprYr3()){// grant needs an amend approval record
					Approval approval = new Approval();
					User user = new User();
					//Populate approval domain object
					approval.setGrantId(grantid);
					approval.setApprovalType("Approve");
					approval.setFormTypeId(5L);
					approval.setFyCode(19L);
					approval.setVersion("fs10amendment");
					
					user.setCreatedBy(userb.getUserid());
					
					approvalDao.insert(approval, user);
				}
			}
        // END of 04/12/17 changes
      
    if(hasDecline){
      if(asb.isAppDeclined()==false)//grant has decline record but it needs to be deleted
          deleteDecline(grantid);      
      else if(asb.isAppDeclined())
          updateDecline(userb, asb);
    }
    else
    {
      if(asb.isAppDeclined()){//grant needs decline record
          insertDecline(grantid, userb, asb);
              
          //delete any prior approval records that had been saved
          if(hasInitial)
             deleteAdminApproval(grantid, "Initial");
          if(hasFinal)
             deleteAdminApproval(grantid, "Final");    
          if(hasDeny)
             deleteAdminDenial(grantid);
      }
    }
      
      ///handle reveal/hide of reviewers scores/amount approved to appcnt
      handleShowHideScores(grantid, userb, asb.isShowscorecomm());
    
    }catch(Exception e){System.err.println("error saveAdminApproval " + e.getMessage().toString());}
    
  }

    /**
     * Method for lgrmif reviewers to insert/delete the approve/deny record in approvals table.
     * @param initialappr
     * @param grantid
     * @param userb
     */
    public void saveReviewerApproval(boolean initialappr, long grantid, UserBean userb) 
    {
        boolean hasInitial =false, hasDeny=false;
        try{            
            //check to see if initial was already approved/denied
            hasInitial = isInitialAppApproved(grantid);           
            hasDeny = isAppDenied(grantid);
            
            if(hasInitial){
              if(initialappr==false)//grant has initial approval record but it needs to be deleted
                  deleteAdminApproval(grantid, "Initial");      
            }
            else{
              if(initialappr)//grant needs an initial approval record
                 insertApprovalRecord(grantid, userb, "Initial");
            }
            
                    
            if(hasDeny){
              if(initialappr)//grant has deny record already but it needs to be deleted
                 deleteAdminDenial(grantid);            
            }
            else
            {
              if(initialappr==false)
              {
                 //grant needs a denial record
                 insertDenialRecord(grantid, userb);
                
                //delete any prior approval records that may have been saved
                if(hasInitial)
                   deleteAdminApproval(grantid, "Initial");
              }
            }
            
        }catch(Exception e){System.err.println("error saveReviewerApproval " + e.getMessage().toString());}
            
    }

  
    public int handleShowHideScores(long grantid, UserBean userb, boolean showscores)
    {
      int outcome=0;
      try{            
        conn = initializeConn();
        
        //update the show/hide flag for reviewers scores to apcnt
        ps = conn.prepareStatement("update grants set amt_approved_yn=?, date_modified=sysdate, "+
        " modified_by=? where id=?");
        ps.setBoolean(1, showscores);
        ps.setString(2, userb.getUserid());//admin
        ps.setLong(3, grantid);
        outcome = ps.executeUpdate();        
               
      }catch(Exception e){
        System.err.println("error handleShowHideScores() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
  
  /**
   * Method inserts an initial or final approval into Approvals table in db.
   * @return 
   * @param version is either initial or final
   * @param grantid
   * @param userb
   */
  public int insertApprovalRecord(long grantid, UserBean userb, String version)
  {
    int outcome=0;
    try{            
      conn = initializeConn();
      
      //insert record into approvals
      ps = conn.prepareStatement("insert into APPROVALS (ID, GRA_ID, DATE_ACCEPTED, ADMIN, "+
            " VERSION, APPROVAL_TYPE, DATE_CREATED, CREATED_BY) values ( APPROVAL_SEQ.NEXTVAL, "+
            " ?, SYSDATE, ?, ?, ?, SYSDATE, ?) ");
      ps.setLong(1, grantid);
      ps.setString(2, userb.getUserid());//admin
      ps.setString(3, version);//version ie. Initial, Final, Interim
      ps.setString(4, "Approve");//approval_type
      ps.setString(5, userb.getUserid());      
      outcome = ps.executeUpdate();
      
      Close(ps);
      
      //update the grant field to signal that approval was made      
      if(version.equals("Initial"))
      {
        ps = conn.prepareStatement("update GRANTS set AWAITING_APPR=0, LOCK_APP=0, COVERSHEET_YN=1, PROJ_DESC_YN=1, "+
          " AMT_REQUESTED_YN=1, INST_AUTH_YN=1, FS20_YN=1, EXP_APPROVED_YN=1 where ID=?");
        //if initial approved - unlock app
      }
      else
      {
        ps = conn.prepareStatement("update GRANTS set AWAITING_APPR=0, LOCK_APP=1, FINAL_NARRATIVE_YN=1, "+
          " EXP_SUBMITTED_YN=1, FINAL_SIGNOFF_YN=1 where ID=?");
      }
      ps.setLong(1, grantid);
      outcome = ps.executeUpdate();      
      
    }catch(Exception e){
      System.err.println("error insertApprovalRecord() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }
  
  
  /**
   * This method will delete an admin approval record in table Approvals in case the approval
   * record was created by the user by accident. 
   * @return 
   * @param version is either initial or final
   * @param grantid
   */
  public int deleteAdminApproval(long grantid, String version)
  {
    int outcome=0;
    try{
      conn = initializeConn();            
      ps =  conn.prepareStatement("delete from APPROVALS where GRA_ID=? and VERSION=?");
      ps.setLong(1, grantid);
      ps.setString(2, version);      
      outcome = ps.executeUpdate();
      
      Close(ps);    
      
      ps = conn.prepareStatement("update GRANTS set AWAITING_APPR=1, LOCK_APP=1 where ID=?");
      ps.setLong(1, grantid);
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error deleteAdminApproval() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
    return outcome;    
  }
  
  
    public int deleteDasnyApproval(long grantid)
    {
      int outcome=0;
      try{
        conn = initializeConn();            
        ps =  conn.prepareStatement("delete from APPROVALS where GRA_ID=? and VERSION=?");
        ps.setLong(1, grantid);
        ps.setString(2, "DASNY");      
        outcome = ps.executeUpdate();
                
      }catch(Exception e){
        System.err.println("error deleteDasnyApproval() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
      return outcome;    
    }
    
    
    public int deleteBondCouncilApproval(long grantid)
    {
      int outcome=0;
      try{
        conn = initializeConn();            
        ps =  conn.prepareStatement("delete from APPROVALS where GRA_ID=? and VERSION=?");
        ps.setLong(1, grantid);
        ps.setString(2, "BondCouncil");      
        outcome = ps.executeUpdate();
                
      }catch(Exception e){
        System.err.println("error deleteBondCouncilApproval() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
      return outcome;    
    }
  
  public int insertDenialRecord(long grantid, UserBean userb)
  {  
    int outcome=0;
    try{            
      conn = initializeConn();
      
      //insert denial record into approvals
      ps = conn.prepareStatement("insert into APPROVALS (ID, GRA_ID, DATE_ACCEPTED, ADMIN, "+
            " VERSION, APPROVAL_TYPE, DATE_CREATED, CREATED_BY) values ( APPROVAL_SEQ.NEXTVAL, "+
            " ?, SYSDATE, ?, ?, ?, SYSDATE, ?) ");
      ps.setLong(1, grantid);
      ps.setString(2, userb.getUserid());//admin
      ps.setString(3, "All" );//version ie. Initial, Final
      ps.setString(4, "Denied" );//approval_type
      ps.setString(5, userb.getUserid());      
      outcome = ps.executeUpdate();
      
      Close(ps);
      
      //update the grant field to signal that denial was made       
      ps = conn.prepareStatement("update GRANTS set AWAITING_APPR=0, LOCK_APP=1 where ID=?");
      ps.setLong(1, grantid);
      outcome = ps.executeUpdate();      
            
    }catch(Exception e){
      System.err.println("error insertDenialRecord() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }
  
  
    public int insertDasnyApproval(long grantid, UserBean userb)
    {  
      int outcome=0;
      try{            
        conn = initializeConn();
        
        //insert into approvals
        ps = conn.prepareStatement("insert into APPROVALS (ID, GRA_ID, DATE_ACCEPTED, ADMIN, "+
              " VERSION, APPROVAL_TYPE, DATE_CREATED, CREATED_BY) values ( APPROVAL_SEQ.NEXTVAL, "+
              " ?, SYSDATE, ?, ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());//admin
        ps.setString(3, "DASNY");//version ie. Initial, Final
        ps.setString(4, "Approve");//approval_type
        ps.setString(5, userb.getUserid());      
        outcome = ps.executeUpdate();
              
      }catch(Exception e){
        System.err.println("error insertDasnyApproval() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
  
  
    public int insertBondCouncilApproval(long grantid, UserBean userb)
    {  
      int outcome=0;
      try{            
        conn = initializeConn();
        
        //insert into approvals
        ps = conn.prepareStatement("insert into APPROVALS (ID, GRA_ID, DATE_ACCEPTED, ADMIN, "+
              " VERSION, APPROVAL_TYPE, DATE_CREATED, CREATED_BY) values ( APPROVAL_SEQ.NEXTVAL, "+
              " ?, SYSDATE, ?, ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());//admin
        ps.setString(3, "BondCouncil");//version ie. Initial, Final
        ps.setString(4, "Approve");//approval_type
        ps.setString(5, userb.getUserid());      
        outcome = ps.executeUpdate();
              
      }catch(Exception e){
        System.err.println("error insertBondCouncilApproval() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
  
  public int deleteAdminDenial(long grantid)
  {  
    int outcome=0;
    try{
      conn = initializeConn();            
      ps =  conn.prepareStatement("delete from APPROVALS where GRA_ID=? and VERSION='All' "+
                             " and APPROVAL_TYPE='Denied' ");
      ps.setLong(1, grantid);           
      outcome = ps.executeUpdate();
      
      Close(ps);       
            
      ps = conn.prepareStatement("update GRANTS set AWAITING_APPR=1, LOCK_APP=1 where ID=?");
      ps.setLong(1, grantid);
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error deleteAdminDenial() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
    return outcome;
  }  
  
  
   public boolean isAppDenied(long grantid)
  {
    boolean appDenied = false;    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " VERSION = ? and APPROVAL_TYPE = ?");                
      ps.setLong(1, grantid);
      ps.setString(2, "All");
      ps.setString(3, "Denied");
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the app was denied
        appDenied = true;
      }
    }catch(Exception e){
      System.err.println("error isAppDenied() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return appDenied;
  }
  
  
    public AppStatusBean getDeclinedAwardInfo(AppStatusBean asb)
    {      
      Format formatter = new SimpleDateFormat("MM/dd/yyyy");
      try{
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                  " VERSION = ? and APPROVAL_TYPE = ?");                
        ps.setLong(1, asb.getGrantid());
        ps.setString(2, "All");
        ps.setString(3, "Declined");        
        rs = ps.executeQuery();
        while(rs.next()){
          asb.setAmountDeclinedStr(rs.getString("amount_decline"));
          asb.setDateDeclined(formatter.format(rs.getDate("date_accepted")));
          asb.setDeclineId(rs.getLong("id"));
        }
      }catch(Exception e){
        System.err.println("error getDeclinedAwardInfo() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return asb;
    }
  
  
    public boolean isAppDeclined(long grantid)
    {
      boolean decline = false;
      
      try{
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                  " VERSION = ? and APPROVAL_TYPE = ?");                
        ps.setLong(1, grantid);
        ps.setString(2, "All");
        ps.setString(3, "Declined");
        
        rs = ps.executeQuery();
        while(rs.next()){
          //if a record is returned by query - then the app was declined
          decline = true;
        }
      }catch(Exception e){
        System.err.println("error isAppDeclined() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return decline;
    }
    
    
    public boolean isDasnyApproval(long grantid)
    {
      boolean approval = false;
      
      try{
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                  " VERSION = ? and APPROVAL_TYPE = ?");                
        ps.setLong(1, grantid);
        ps.setString(2, "DASNY");
        ps.setString(3, "Approve");
        
        rs = ps.executeQuery();
        while(rs.next()){
          //if a record is returned by query - then the app was appr by dasny
          approval = true;
        }
      }catch(Exception e){
        System.err.println("error isDasnyApproval() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return approval;
    }
    
    
    public boolean isDasnyBondComplete(long grantid)
    {
      boolean approval = false;
      
      try{
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                  " VERSION = ? and APPROVAL_TYPE = ?");                
        ps.setLong(1, grantid);
        ps.setString(2, "BondCouncil");
        ps.setString(3, "Approve");
        
        rs = ps.executeQuery();
        while(rs.next()){
          //if a record is returned by query - then bond council appr by dasny
          approval = true;
        }
      }catch(Exception e){
        System.err.println("error isDasnyBondComplete() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }      
      return approval;
    }
    
    public int deleteDecline(long grantid)
    {  
      int outcome=0;
      try{
        conn = initializeConn();            
        ps =  conn.prepareStatement("delete from APPROVALS where GRA_ID=? and VERSION='All' "+
                               " and APPROVAL_TYPE='Declined' ");
        ps.setLong(1, grantid);           
        outcome = ps.executeUpdate();        
      }catch(Exception e){
        System.err.println("error deleteDecline() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }
      return outcome;
    }  
  
    public int insertDecline(long grantid, UserBean userb, AppStatusBean asb)
    {  
      int outcome=0;
      try{            
        conn = initializeConn();
        
        //parse out any characters in amtdeclined
        DBHandler dbh = new DBHandler();
        if(asb.getAmountDeclinedStr()!=null && !asb.getAmountDeclinedStr().equals(""))
          asb.setAmountDeclined(dbh.parseCurrencyAmtNoDecimal(asb.getAmountDeclinedStr()));
        
        //insert decline record into approvals table
        ps = conn.prepareStatement("insert into APPROVALS (ID, GRA_ID, DATE_ACCEPTED, ADMIN, "+
        " VERSION, APPROVAL_TYPE, DATE_CREATED, CREATED_BY, AMOUNT_DECLINE) values (APPROVAL_SEQ.NEXTVAL, "+
        " ?, to_date(?, 'mm/dd/yy'), ?, ?, ?, SYSDATE, ?, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, asb.getDateDeclined());
        ps.setString(3, userb.getUserid());//admin
        ps.setString(4, "All" );//version ie. Initial, Final
        ps.setString(5, "Declined" );//approval_type
        ps.setString(6, userb.getUserid());           
        ps.setInt(7, asb.getAmountDeclined());
        outcome = ps.executeUpdate();
                      
      }catch(Exception e){
        System.err.println("error insertDecline() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
    
    public int updateDecline(UserBean userb, AppStatusBean asb)
    {  
      int outcome=0;
      try{            
        conn = initializeConn();
        
        //parse out any characters in amtdeclined
        DBHandler dbh = new DBHandler();
        if(asb.getAmountDeclinedStr()!=null && !asb.getAmountDeclinedStr().equals(""))
          asb.setAmountDeclined(dbh.parseCurrencyAmtNoDecimal(asb.getAmountDeclinedStr()));
        
        //insert decline record into approvals table
        ps = conn.prepareStatement("update APPROVALS set DATE_ACCEPTED=to_date(?, 'mm/dd/yy'), "+
        " DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, AMOUNT_DECLINE=? WHERE ID=? ");
        ps.setString(1, asb.getDateDeclined());
        ps.setString(2, userb.getUserid());//admin
        ps.setInt(3, asb.getAmountDeclined());
        ps.setLong(4, asb.getDeclineId());
        outcome = ps.executeUpdate();
                      
      }catch(Exception e){
        System.err.println("error updateDecline() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
    
    
   public boolean isInitialAppApproved(long grantid)
  {
    boolean appAppr = false;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " VERSION = ? and APPROVAL_TYPE = ?");                
      ps.setLong(1, grantid);
      ps.setString(2, "Initial");
      ps.setString(3, "Approve");
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the initial grant app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isInitialAppApproved() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return appAppr;
  }

   public boolean isAmendApprYr1(long grantid)
  {
    boolean appAppr = false;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " FMT_ID = ? and APPROVAL_TYPE = ? and FY_FY_CODE = ?");                
      ps.setLong(1, grantid);
      ps.setInt(2, 5);
      ps.setString(3, "Approve");
      ps.setInt(4, 17);
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the initial grant app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isAmendApprYr1() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return appAppr;
  }
   public boolean isAmendApprYr2(long grantid)
  {
    boolean appAppr = false;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " FMT_ID = ? and APPROVAL_TYPE = ? and FY_FY_CODE = ?");                
      ps.setLong(1, grantid);
      ps.setInt(2, 5);
      ps.setString(3, "Approve");
      ps.setInt(4, 18);
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the initial grant app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isAmendApprYr1() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return appAppr;
  }

   public boolean isAmendApprYr3(long grantid)
  {
    boolean appAppr = false;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " FMT_ID = ? and APPROVAL_TYPE = ? and FY_FY_CODE = ?");                
      ps.setLong(1, grantid);
      ps.setInt(2, 5);
      ps.setString(3, "Approve");
      ps.setInt(4, 19);
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the initial grant app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isAmendApprYr1() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return appAppr;
  }


  public boolean isFinalAppApproved(long grantid, boolean multiYrApproval, int approvalYear)
  {
    boolean appAppr = false;
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " VERSION = ? and APPROVAL_TYPE = ?");                
      ps.setLong(1, grantid);
      if(multiYrApproval){
          if(approvalYear==2)
              ps.setString(2, "Final Year2");
          else
              ps.setString(2, "Final Year3");
      }        
      else
        ps.setString(2, "Final");
      ps.setString(3, "Approve");
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then the initial grant app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isFinalAppApproved() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return appAppr;
  }
  
  
  public boolean isInterimAppApproved(long grantid)
  {
    boolean appAppr = false;    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID = ? and "+
                " VERSION = ? and APPROVAL_TYPE = ?");                
      ps.setLong(1, grantid);
      ps.setString(2, "Interim");
      ps.setString(3, "Approve");
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then interim app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isInterimAppApproved() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return appAppr;
  }
  
  
  //8/14/15 new literacy category per KBALSEN
  public boolean isYr2ApprovedForYear3Start(long grantid)
  {
    boolean appAppr = false;    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select id from APPROVALS where GRA_ID = ? and "+
                " VERSION = ? and APPROVAL_TYPE = ?");                
      ps.setLong(1, grantid);
      ps.setString(2, "Ready For Year3");
      ps.setString(3, "Approve");
      
      rs = ps.executeQuery();
      while(rs.next()){
        //if a record is returned by query - then interim app was already approved
        appAppr = true;
      }
    }catch(Exception e){
      System.err.println("error isYr2ApprovedForYear3Start() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }      
    return appAppr;
  }
  
  
  
  
  
  /**
   * This method will get all approval records for a given grant id
   * @param grantid
   */
  public Vector getApprovals(long grantid)
  {
    Vector results = new Vector();    
    try{
      conn = initializeConn();        
      ps = conn.prepareStatement("select * from APPROVALS where GRA_ID=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ApproveBean ab = new ApproveBean();
        ab.setGrantid(rs.getLong("gra_id"));
        ab.setDateapproved(rs.getDate("DATE_ACCEPTED"));
        ab.setAdminuser(rs.getString("ADMIN"));
        ab.setVersion(rs.getString("VERSION"));
        ab.setApprovalType(rs.getString("APPROVAL_TYPE"));          
        results.add(ab);        
      }      
      
    }catch(Exception e){
      System.err.println("error getApprovals() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return results;
  }

  public Vector getAmountForApprovals(Vector allrecords)
  {    
    try{
        BudgetDBHandler bdh = new BudgetDBHandler();
        
        for(int i=0; i<allrecords.size(); i++)
        {
          ApproveBean a = (ApproveBean) allrecords.get(i);
          
          if(a.getVersion().equals("Initial"))
          {
            int amtappr = bdh.calcTotalAmtApproved( a.getGrantid(),0);
            a.setAmountappr(amtappr);
          }
          else if(a.getVersion().equals("Final"))
          {
            int amtappr = bdh.calcTotalExpApproved(a.getGrantid(),0);
            a.setAmountappr(amtappr);
          }
          
        }
    }catch(Exception e){
      System.out.println("error getAmountForApprovals() "+e.getMessage().toString());
    }    
    return allrecords;
  }
  
}