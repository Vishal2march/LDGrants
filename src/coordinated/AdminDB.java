/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminDB.java
 * Creation/Modification History  :
 *
 * SH   7/13/07      Created
 *
 * Description
 * This class will get all sa/co/di/lit/lg grants that were submitted, display on admin home page.
 *****************************************************************************/
package coordinated;

import mypackage.*;
import clobpackage.ClobBean;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import java.text.NumberFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import oracle.jdbc.OracleDriver;
import oracle.sql.CLOB;

public class AdminDB 
{
  public AdminDB()
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
  
  public Vector getSubmittedApplications(int fccode)
  {   
      
    Vector results = new Vector(); //hold all initial grants submitted
           
    try {
       conn = initializeConn();      
        
      if(fccode==80){
           //changed 2/10/10 to get projects even if the submitted record does not have a projcategory.
          ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, VERSION, " + 
          " DATE_SUBMITTED, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
          " initcap(POPULAR_NAME) as popular_name, descr from grant_submissions, grants " + 
          " left join project_categories on grants.pc_id=project_categories.id, " + 
          " co_institutions left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
          " where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
          " VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' " + 
          " and grant_submissions.gra_id not in (select GRA_ID from APPROVALS where VERSION='Initial' " + 
          " or APPROVAL_TYPE='Denied' or APPROVAL_TYPE='Declined') and date_submitted in (select max(DATE_SUBMITTED) from " + 
          " GRANT_SUBMISSIONS group by GRA_ID) order by popular_name");
      }
      else{
      //get all initial applications submitted - that were not yet approved
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, VERSION, DATE_SUBMITTED, "+
      " FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name "+
        " from grant_submissions, grants, co_institutions "+
        " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
        " where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID  "+
        " and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' "+
        " and grant_submissions.gra_id not in (select GRA_ID from APPROVALS where VERSION='Initial' or APPROVAL_TYPE='Denied' " +
        "or APPROVAL_TYPE='Declined') "+
        " and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS group by GRA_ID) "+
      " order by popular_name ");
      }
      ps.setInt(1, fccode);
      rs = ps.executeQuery();      
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));    
        gb.setInstID(rs.getLong("INST_ID"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
        
    } catch (Exception ex){
        System.err.println("error getSubmittedApplications()  " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
  
  public Vector getSubmittedFinalApps(int fccode)
  {
    Vector results = new Vector();//hold final/intereim submissions
    try{
      conn = initializeConn();     
      
      if(fccode==80){
        ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, "+
        " VERSION, descr, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, "+
        " initcap(POPULAR_NAME) as popular_name from GRANTS, GRANT_SUBMISSIONS, project_categories, "+
        " CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
        " where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID and GRANTS.ID = CO_INSTITUTIONS.GRA_ID and "+
        " grants.pc_id=project_categories.id and VERSION='Final' and FC_CODE=? and IS_LEAD='Y' " + 
        " and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from APPROVALS where VERSION='Final' or " + 
        " APPROVAL_TYPE='Denied' or APPROVAL_TYPE='Declined') and date_submitted in (select max(DATE_SUBMITTED) from "+
        " GRANT_SUBMISSIONS where VERSION='Final' group by GRA_ID) order by popular_name ");          
      }
      else if(fccode==40 || fccode==42){
          //get all finalyr1 and finalyr2 submitted for literacy
       ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, VERSION, " + 
       "         FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
       "         initcap(POPULAR_NAME) as popular_name " + 
       "         from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
       "         sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID=GRANT_SUBMISSIONS.GRA_ID " + 
       "         and GRANTS.ID =CO_INSTITUTIONS.GRA_ID and FC_CODE=? and IS_LEAD='Y' " + 
       "         and  " + 
       "              (   (VERSION='Final' and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from " + 
       "                    APPROVALS where VERSION='Final' or APPROVAL_TYPE='Denied' or APPROVAL_TYPE='Declined') " + 
       "                    and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where " + 
       "                    VERSION='Final' group by GRA_ID) " + 
       "                  ) " + 
       "                  or " + 
       "                  (VERSION='Final Year2' and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from " + 
       "                    APPROVALS where VERSION='Final Year2' or VERSION='Ready For Year3') " + 
       "                    and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where " + 
       "                    VERSION='Final Year2' group by GRA_ID) " + 
       "                  )  " + 
       "                  or " + 
       "                  (VERSION='Final Year3' and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from " + 
       "                    APPROVALS where VERSION='Final Year3') " + 
       "                    and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where " + 
       "                    VERSION='Final Year3' group by GRA_ID) " + 
       "                  )  " + 
       "              )   " + 
       "          order by popular_name  ");    
      }
      else{
       //get all final applications submitted - that were not yet approved        
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, VERSION, "+
        " FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name "+
        " from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS left join SED_INSTITUTIONS on "+
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID=GRANT_SUBMISSIONS.GRA_ID "+
        " and GRANTS.ID =CO_INSTITUTIONS.GRA_ID and VERSION='Final' and FC_CODE=? and IS_LEAD='Y' "+
        " and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from APPROVALS where VERSION='Final' or "+
	" APPROVAL_TYPE='Denied' or APPROVAL_TYPE='Declined') and date_submitted in (select max(DATE_SUBMITTED) from "+
        " GRANT_SUBMISSIONS where VERSION='Final' group by GRA_ID) order by popular_name ");
      }
        
      ps.setInt(1, fccode);
      rs = ps.executeQuery();   
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));
        gb.setInstID(rs.getLong("INST_ID"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));        
        results.add(gb);
      }      
      
      Close(rs);
      Close(ps);
      
      if(fccode==40 || fccode==42)//only for adult/family lit
      {
          //get all interim apps submitted - that were not yet approved        
          ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, VERSION, "+
            " FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name "+
            " from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS "+
            " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
            " where GRANTS.ID =GRANT_SUBMISSIONS.GRA_ID and GRANTS.ID =CO_INSTITUTIONS.GRA_ID "+
            " and VERSION='Interim' and FC_CODE=? and IS_LEAD='Y' "+
            " and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from APPROVALS where VERSION='Interim' or "+
            " APPROVAL_TYPE='Denied') "+
            " and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS group by GRA_ID) "+
            " order by date_submitted desc ");
          ps.setInt(1, fccode);    
          rs = ps.executeQuery();
       
          while(rs.next()){
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("GRA_ID"));
            gb.setInstName(rs.getString("POPULAR_NAME"));
            gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
            gb.setFycode(rs.getInt("FY_CODE"));
            gb.setFccode(rs.getInt("FC_CODE"));
            gb.setTitle(rs.getString("NAME"));
              SubmitBean sb = new SubmitBean();
              sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
              sb.setVersionSubmitted(rs.getString("VERSION"));
            gb.setSubmissionBean(sb);
            gb.setNeedApproval(rs.getString("AWAITING_APPR"));            
            results.add(gb);
          }
      }
           
    }catch (Exception ex){
        System.err.println("error getSubmittedFinalApps() " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
    public Vector getAmendmentsSubmitted(int fccode)
    {  
       Vector results = new Vector();     
       try{
        conn = initializeConn();   
        //get any fs10a amendment submissions
        if(fccode==80){//for lgrmif, get project category
         ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, VERSION, descr, " + 
         " FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name " + 
         " from GRANTS, GRANT_SUBMISSIONS, PROJECT_CATEGORIES, CO_INSTITUTIONS " + 
         " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
         " where GRANTS.ID =GRANT_SUBMISSIONS.GRA_ID and " + 
         " GRANTS.PC_ID = PROJECT_CATEGORIES.ID and GRANTS.ID =CO_INSTITUTIONS.GRA_ID " + 
         " and VERSION='Amendment' and FC_CODE=? and IS_LEAD='Y' and (grants.fs10a_yn=0 or grants.fs10a_yn is null) " + 
         " and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS " + 
         " where VERSION='Amendment' group by GRA_ID) " + 
         " order by date_submitted desc ");
        }
        else{
         ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, VERSION, "+
           " FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name "+
           " from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS "+
           " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
           " where GRANTS.ID =GRANT_SUBMISSIONS.GRA_ID and GRANTS.ID =CO_INSTITUTIONS.GRA_ID "+
           " and VERSION='Amendment' and FC_CODE=? and IS_LEAD='Y' and (grants.fs10a_yn=0 or grants.fs10a_yn is null) "+
           " and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS "+
           " where VERSION='Amendment' group by GRA_ID) "+
           " order by date_submitted desc ");
        }
         ps.setInt(1, fccode);    
         rs = ps.executeQuery();
         
         while(rs.next())
         {
           GrantBean gb = new GrantBean();
           gb.setGrantid(rs.getLong("GRA_ID"));
           gb.setInstName(rs.getString("POPULAR_NAME"));
           gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
           gb.setFycode(rs.getInt("FY_CODE"));
           gb.setFccode(rs.getInt("FC_CODE"));
           gb.setInstID(rs.getLong("INST_ID"));
             
           if(gb.getFccode()==20){  
        	 //per BL; need 4 digit proj num to be same every year for stateaid
           	 DBHandler dbh = new DBHandler();
               gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
           }
             
           gb.setTitle(rs.getString("NAME"));
           if(fccode==80)
                 gb.setTitle(rs.getString("descr"));
             SubmitBean sb = new SubmitBean();
             sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
             sb.setVersionSubmitted(rs.getString("VERSION"));
           gb.setSubmissionBean(sb);
           gb.setNeedApproval(rs.getString("AWAITING_APPR"));
           
           results.add(gb);
         }
          
    }catch (Exception ex){
        System.err.println("error getAmendmentsSubmitted() " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
    } 
                    

  public Vector getWaitingForFinalApplications(int fccode)
  {  
     Vector results = new Vector();     
     try{
      conn = initializeConn();      
        
      if(fccode==80){//for lgrmif, need 'project category' table/fields
          ps =conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, " + 
          " VERSION, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
          " initcap(POPULAR_NAME) as popular_name, descr from GRANTS, GRANT_SUBMISSIONS, "+
          " project_categories, CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id "+
          " =co_institutions.inst_id where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID and "+
          " GRANT_SUBMISSIONS.GRA_ID= CO_INSTITUTIONS.GRA_ID and project_categories.id=grants.pc_id " + 
          " and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' and GRANT_SUBMISSIONS.GRA_ID "+
          " in (select GRA_ID from APPROVALS where VERSION='Initial') and GRANT_SUBMISSIONS.GRA_ID "+
          //removed the max(datesubmit) 2/25/11 because appr amendments are not displaying in 'waiting for final' list
          " not in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Final') "+
          //" and DATE_SUBMITTED in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS group by GRA_ID) "+
          " order by popular_name");
      }
      else if(fccode==40 || fccode==42){
      //changed 4/4/11 -lit now has finalyr1 and finalyr2 submission/appr, so 'waiting for final'
      //needs to include waiting for yr2 submission
      //modified 6/19/14 to get max date_submitted; otherwise multiple rows for each submit record
      //changed 8/17/15 to handle "waiting for year 3"
         ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, " + 
         "   VERSION, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
         "   initcap(POPULAR_NAME) as popular_name from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS " + 
         "   left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
         "   where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID and GRANT_SUBMISSIONS.GRA_ID= CO_INSTITUTIONS.GRA_ID " + 
         "   and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' and FY_CODE>13 " + 
         "   and " + 
         "        (( GRANT_SUBMISSIONS.GRA_ID in (select GRA_ID from APPROVALS where VERSION='Initial') " + 
         "          and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Final Year2') " + 
         "        ) " + 
         "        or " + 
         "        ( GRANT_SUBMISSIONS.GRA_ID in (select GRA_ID from APPROVALS where VERSION='Final Year2') " + 
         "          and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Final Year3') " + 
         "        )) " + 
         "   and ( date_submitted = (select max(date_submitted) from grant_submissions where " + 
         "                                grants.id=grant_submissions.gra_id and version='Initial') )  " + 
         "   order by popular_name"); 
      }
      else{
      //get all grants that had intial submitted/approved - but final has not been submitted
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, "+
        " VERSION, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, "+
        " initcap(POPULAR_NAME) as popular_name from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS "+
        " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
        " where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID and GRANT_SUBMISSIONS.GRA_ID= CO_INSTITUTIONS.GRA_ID "+
        " and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' "+
        " and GRANT_SUBMISSIONS.GRA_ID in (select GRA_ID from APPROVALS where VERSION='Initial') "+
        " and GRANT_SUBMISSIONS.GRA_ID not in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Final') "+
        //changed 6/17/10 to remove max(datesubmit) so that amendments fall into the 'final not submit' list
        //" and DATE_SUBMITTED in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS group by GRA_ID) order by popular_name");
                                 
       //modified 8/5/15 to eliminate duplicate projects on cp dg "waiting final" list
       " and ( date_submitted = (select max(date_submitted) from grant_submissions where "+
               "        grants.id=grant_submissions.gra_id and version='Initial') ) "+
        " order by popular_name");
      }
      ps.setInt(1, fccode);
      rs = ps.executeQuery();
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));     
        gb.setInstID(rs.getLong("INST_ID"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
           
    }catch(Exception e){
      System.err.println("error getWaitingForFinalApplications() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return results;

  }  
  
  
  //new 8/14/15 category per KBALSEN, for final year2 completed (not approved) and ready for year 3.
  public Vector getLitFinalCompleteReadyNextYear(int fccode)
  {  
     Vector results = new Vector();     
     try{
      conn = initializeConn();      
              
         ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, DATE_SUBMITTED, " + 
         "   VERSION, FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
         "   initcap(POPULAR_NAME) as popular_name from GRANTS, GRANT_SUBMISSIONS, CO_INSTITUTIONS " + 
         "   left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
         "   where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID and GRANT_SUBMISSIONS.GRA_ID= CO_INSTITUTIONS.GRA_ID " + 
         "   and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y' " + 
         "   and GRANT_SUBMISSIONS.GRA_ID in (select GRA_ID from APPROVALS where VERSION='Ready For Year3') " + 
         "   and ( date_submitted = (select max(date_submitted) from grant_submissions where " + 
         "                                grants.id=grant_submissions.gra_id and version='Initial') )  " + 
         "   order by popular_name"); 
      
      ps.setInt(1, fccode);
      rs = ps.executeQuery();
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));     
        gb.setInstID(rs.getLong("INST_ID"));
                       
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
           
    }catch(Exception e){
      System.err.println("error getLitFinalCompleteReadyNextYear() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return results;
  }  
  
  
  
  
  public HashMap getClosedGrants(int fccode)
  {  
    HashMap results = new HashMap();
    Vector finappr = new Vector();
    Vector deny = new Vector();
    int yearCode= 0;    
    try{
      conn = initializeConn();
      
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      int thisyear = cal.get(Calendar.YEAR);
      String year = Integer.toString(thisyear);

      //Get the code for the current fiscal year.
      ps = conn.prepareStatement("select CODE from FISCAL_YEARS where DESCRIPTION=?");
      ps.setString(1, year);
      rs = ps.executeQuery();
      while(rs.next()){
        yearCode = rs.getInt(1);
      }
      Close(rs);
      Close(ps);
            
      //changed 4/4/11 with diff query for lit.  lit now has finalyr1 and finalyr2 approval,
      //so the 'closed' grants need both yr1 and y2 approval OR the award was DECLINED.
      //Otherwise, the grant will move to 'closed' status after yr1 approval.
      //modified 8/14/15 - now need 3 years of final approval before closing
      if(fccode==40 || fccode==42){
          ps =conn.prepareStatement("select distinct GRANTS.ID, NAME, FC_CODE, FY_CODE, PROJ_SEQ_NUM, " + 
          " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, descr from CO_INSTITUTIONS, "+
          " sed_institutions, grants left join project_categories on grants.pc_id=project_categories.id " + 
          " where GRANTS.ID=CO_INSTITUTIONS.GRA_ID and sed_institutions.inst_id=co_institutions.inst_id " + 
          " and FY_code between ? and ? and IS_LEAD='Y' and FC_CODE=? and GRANTS.ID in (select "+
          " GRA_ID from APPROVALS where VERSION='Final Year3' or APPROVAL_TYPE='Declined') order by popular_name");              
      }else if(fccode==80){
          //get grants for fy that were closed (but not declined)FC wants 2 separate tables
          ps =conn.prepareStatement("select distinct GRANTS.ID, NAME, FC_CODE, FY_CODE, PROJ_SEQ_NUM, " + 
          " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, descr from CO_INSTITUTIONS, "+
          " sed_institutions, grants left join project_categories on grants.pc_id=project_categories.id " + 
          " where GRANTS.ID=CO_INSTITUTIONS.GRA_ID and sed_institutions.inst_id=co_institutions.inst_id " + 
          " and FY_code between ? and ? and IS_LEAD='Y' and FC_CODE=? and GRANTS.ID in (select "+
          " GRA_ID from APPROVALS where VERSION='Final') order by popular_name");              
      }else{
          //get all grants for current fy that have final approved OR were DECLINED
          ps =conn.prepareStatement("select distinct GRANTS.ID, NAME, FC_CODE, FY_CODE, PROJ_SEQ_NUM, " + 
          " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, descr from CO_INSTITUTIONS, "+
          " sed_institutions, grants left join project_categories on grants.pc_id=project_categories.id " + 
          " where GRANTS.ID=CO_INSTITUTIONS.GRA_ID and sed_institutions.inst_id=co_institutions.inst_id " + 
          " and FY_code between ? and ? and IS_LEAD='Y' and FC_CODE=? and GRANTS.ID in (select "+
          " GRA_ID from APPROVALS where VERSION='Final' or APPROVAL_TYPE='Declined') order by popular_name");              
      }
      ps.setInt(1, yearCode-1);
      ps.setInt(2, yearCode+1);
      ps.setInt(3, fccode);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));   
        gb.setStatus("Closed");
        gb.setInstID(rs.getLong("INST_ID"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
               
        finappr.add(gb);
      }      
      
      Close(rs);
      Close(ps);
            
      //get all grants for current fy where initial/final was denied    
       ps=conn.prepareStatement("select distinct GRANTS.ID, NAME, FC_CODE, AWAITING_APPR, " + 
        " FY_CODE, PROJ_SEQ_NUM, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, descr " + 
        " from CO_INSTITUTIONS, sed_institutions, GRANTS left join project_categories on "+
        " grants.pc_id=project_categories.id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and "+
        " sed_institutions.inst_id=co_institutions.inst_id and FY_code between ? and ? and "+
        " IS_LEAD='Y' and FC_CODE=? and GRANTS.ID in (select GRA_ID from APPROVALS where "+
        " APPROVAL_TYPE='Denied') order by popular_name");          
      ps.setInt(1, yearCode-1);
      ps.setInt(2, yearCode+1);
      ps.setInt(3, fccode);
      rs = ps.executeQuery();      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        if(fccode==80)
            gb.setTitle(rs.getString("descr"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));   
        gb.setStatus("Denied");    
        gb.setInstID(rs.getLong("INST_ID"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
                      
        deny.add(gb);
      }
      
     results.put(new Integer(1), finappr);
     results.put(new Integer(2), deny);           
    }
    catch(Exception e){
      System.err.println("error getClosedGrants() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(ps);
    }
    return results;
  }
  
  
  
    public Vector getDeclinedGrants(int fccode)
    {  
      Vector allgrants = new Vector();
      int yearCode= 0;   
      try{
        conn = initializeConn();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int thisyear = cal.get(Calendar.YEAR);
        String year = Integer.toString(thisyear);

        //Get the code for the current fiscal year.
        ps = conn.prepareStatement("select CODE from FISCAL_YEARS where DESCRIPTION=?");
        ps.setString(1, year);
        rs = ps.executeQuery();
        while(rs.next()){
          yearCode = rs.getInt(1);
        }
        Close(rs);
        Close(ps);
              
        //get all grants for current fy that were DECLINED
        ps =conn.prepareStatement("select distinct GRANTS.ID, NAME, FC_CODE, FY_CODE, PROJ_SEQ_NUM, " + 
        " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, descr from CO_INSTITUTIONS, "+
        " sed_institutions, grants left join project_categories on grants.pc_id=project_categories.id " + 
        " where GRANTS.ID=CO_INSTITUTIONS.GRA_ID and sed_institutions.inst_id=co_institutions.inst_id " + 
        " and FY_code between ? and ? and IS_LEAD='Y' and FC_CODE=? and GRANTS.ID in (select "+
        " GRA_ID from APPROVALS where APPROVAL_TYPE='Declined') order by popular_name");              
        ps.setInt(1, yearCode-1);
        ps.setInt(2, yearCode+1);
        ps.setInt(3, fccode);
        rs = ps.executeQuery();
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setTitle(rs.getString("NAME"));
          if(fccode==80)
              gb.setTitle(rs.getString("descr"));
          gb.setInstName(rs.getString("POPULAR_NAME"));
          gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
          gb.setFycode(rs.getInt("FY_CODE"));
          gb.setFccode(rs.getInt("FC_CODE"));    
          gb.setInstID(rs.getLong("INST_ID"));
            
          if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	DBHandler dbh = new DBHandler();
              gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
          }
            
          gb.setStatus("Declined");
          allgrants.add(gb);
        }      
        
      }catch(Exception e){
        System.err.println("error getDeclinedGrants() " + e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(conn);
        Close(ps);
      }
      return allgrants;
    }
    
    
    
    
    
  /**
     *1/20/16 new for new literacy home page per KBALSEN. get all apps for FY that had "initial submit", regardless
     * if initial was approved.
     * The fn above getSubmittedApplications() gets apps where "intial submitted" but not approved.
     * @param fccode
     * @param fycode
     * @return
     */
  public Vector getSubmittedRegardlessOfApproval(int fccode, int fycode)
  {        
     
    Vector results = new Vector(); //hold all initial grants submitted
           
    try {
       conn = initializeConn();      
      
      //get all initial applications submitted 
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, VERSION, DATE_SUBMITTED, \n" + 
      "           FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name \n" + 
      "             from grant_submissions, grants, co_institutions  \n" + 
      "              left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id \n" + 
      "              where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID  \n" + 
      "              and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?   \n" + 
      "               and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where VERSION='Initial' group by GRA_ID) \n" + 
      "               and grant_submissions.gra_id not in (\n" + 
      "               \n" + 
      "               select distinct GRANT_SUBMISSIONS.GRA_ID\n" + 
      "               from grant_submissions, grants, co_institutions  \n" + 
      "               left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id  \n" + 
      "               where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID   \n" + 
      "              and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?    \n" + 
      "               and grant_submissions.gra_id in (select GRA_ID from APPROVALS where VERSION='Initial' and APPROVAL_TYPE='Approve')   \n" + 
      "               and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where VERSION='Initial' group by GRA_ID)  \n" + 
      "               \n" + 
      "               ) \n" + 
      "            order by popular_name");      
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setInt(3, fccode);
      ps.setInt(4, fycode);
      rs = ps.executeQuery();      
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));    
        gb.setInstID(rs.getLong("INST_ID"));          
               
        gb.setTitle(rs.getString("NAME"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
        
    } catch (Exception ex){
        System.err.println("error getSubmittedRegardlessOfApproval()  " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
  
  
  
  /**
     *1/20/16 new for new literacy home page per KBALSEN. get all apps for FY where initial = approved
     * @param fccode
     * @param fycode
     * @return
     */
  public Vector getInitialApproved(int fccode, int fycode)
  {        
    Vector results = new Vector(); 
         
    try {
       conn = initializeConn();      
            
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, VERSION, DATE_SUBMITTED, " + 
      "       FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name " + 
      "         from grant_submissions, grants, co_institutions " + 
      "         left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
      "         where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID  " + 
      "         and VERSION='Initial' and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?   " + 
      "         and grant_submissions.gra_id in (select GRA_ID from APPROVALS where VERSION='Initial' and APPROVAL_TYPE='Approve')  " + 
      "         and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where VERSION='Initial' group by GRA_ID)   " + 
      "      order by popular_name ");      
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      rs = ps.executeQuery();      
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));    
        gb.setInstID(rs.getLong("INST_ID"));          
               
        gb.setTitle(rs.getString("NAME"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
        
    } catch (Exception ex){
        System.err.println("error getInitialApproved()  " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
  
  
  
  
  /**
     *new 2/16/16 - for kbalsen's new workflow on admin home page - get all final/finalyear2/finalyear3 submitted
     * but not approved.
     * @param fccode
     * @param fycode
     * @param year
     * @return
     */
  public ArrayList<GrantBean> getLitFinalSubmittedNotApproved(int fccode, int fycode, int year)
  {        
    ArrayList<GrantBean> results = new ArrayList<GrantBean>();   
    try {
        String submitVersion="";
        switch(year){
        case 1:
            submitVersion = "Final";
            break;
        case 2:
            submitVersion = "Final Year2";
            break;
        case 3:
            submitVersion = "Final Year3";
            break;
        }
        
       conn = initializeConn();      
      
      //get all "final" submitted but not approved
      ps = conn.prepareStatement("select distinct GRANT_SUBMISSIONS.GRA_ID, VERSION, DATE_SUBMITTED,  \n" + 
      "           FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name   \n" + 
      "             from grant_submissions, grants, co_institutions   \n" + 
      "             left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id   \n" + 
      "            where GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID   \n" + 
      "             and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?  and VERSION=?   \n" + 
      "             and date_submitted in (select max(DATE_SUBMITTED) from GRANT_SUBMISSIONS where VERSION=? group by GRA_ID) \n" + 
      "             and GRANTS.ID not in (select gra_id from APPROVALS where VERSION=?) \n" + 
      "             and  grant_submissions.gra_id not in (\n" + 
      "             \n" + 
      "             select distinct APPROVALS.GRA_ID   \n" + 
      "                  from approvals, grants, co_institutions   \n" + 
      "                  left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id  \n" + 
      "                  where GRANTS.ID = APPROVALS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID  \n" + 
      "                  and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?   and VERSION= ?  \n" + 
      "                  and APPROVALS.gra_id in (select GRA_ID from GRANT_SUBMISSIONS where VERSION=?)   \n" + 
      "                  and date_accepted in (select max(DATE_ACCEPTED) from APPROVALS where VERSION=? and APPROVAL_TYPE='Approve' group by GRA_ID)  \n" + 
      "             ) \n" + 
      "          order by popular_name");      
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setString(3, submitVersion);
      ps.setString(4, submitVersion);
      ps.setString(5, submitVersion);
      ps.setInt(6, fccode);
      ps.setInt(7, fycode);
      ps.setString(8, submitVersion);
      ps.setString(9, submitVersion);
      ps.setString(10, submitVersion);
      rs = ps.executeQuery();      
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));    
        gb.setInstID(rs.getLong("INST_ID"));          
               
        gb.setTitle(rs.getString("NAME"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
        
    } catch (Exception ex){
        System.err.println("error getLitFinalSubmittedNotApproved()  " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
  
  
  
  
  
  public ArrayList<GrantBean> getLitFinalApproved(int fccode, int fycode, int year)
  {        
    ArrayList<GrantBean> results = new ArrayList<GrantBean>(); 
           
    try {
        String submitVersion="";
        switch(year){
        case 1:
            submitVersion = "Final";
            break;
        case 2:
            submitVersion = "Final Year2";
            break;
        case 3:
            submitVersion = "Final Year3";
            break;
        }
        
       conn = initializeConn();      
      
      //get all "final" approved (either year 1/2/3)
      ps = conn.prepareStatement("select distinct APPROVALS.GRA_ID, VERSION, DATE_ACCEPTED, " + 
      "            FC_CODE, AWAITING_APPR, FY_CODE, PROJ_SEQ_NUM, NAME, CO_INSTITUTIONS.INST_ID, " + 
      "            initcap(POPULAR_NAME) as popular_name  " + 
      "            from approvals, grants, co_institutions  " + 
      "            left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
      "            where GRANTS.ID = APPROVALS.GRA_ID  and GRANTS.ID = CO_INSTITUTIONS.GRA_ID  " + 
      "            and FC_CODE=? and IS_LEAD='Y'  and FY_CODE=?   and VERSION=?  " + 
      "            and APPROVALS.gra_id in (select GRA_ID from GRANT_SUBMISSIONS where VERSION=?)   " + 
      "            and date_accepted in (select max(DATE_ACCEPTED) from APPROVALS where VERSION=? and APPROVAL_TYPE='Approve' group by GRA_ID) " + 
      "            order by popular_name");      
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      ps.setString(3, submitVersion);
      ps.setString(4, submitVersion);
      ps.setString(5, submitVersion);
      rs = ps.executeQuery();      
        
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("GRA_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));    
        gb.setInstID(rs.getLong("INST_ID"));          
               
        gb.setTitle(rs.getString("NAME"));
          SubmitBean sb = new SubmitBean();
          sb.setDateSubmitted(rs.getDate("DATE_ACCEPTED"));//this is date of approval; not submission
          sb.setVersionSubmitted(rs.getString("VERSION"));
        gb.setSubmissionBean(sb);
        gb.setNeedApproval(rs.getString("AWAITING_APPR"));
        
        results.add(gb);
      }
        
    } catch (Exception ex){
        System.err.println("error getLitFinalApproved()  " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return results;
  }
  
}