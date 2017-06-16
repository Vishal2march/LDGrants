/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReportDBbean.java
 * Creation/Modification History  :
 *
 * SH   11/19/07      Created
 *
 * Description
 * This class has methods to get data for all internal admin reports.  
 * These reports will all need to be created in Cognos once its up and running. 
 *****************************************************************************/
package reports;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import mypackage.ReviewerBean;
import oracle.jdbc.OracleDriver;
import oracle.sql.CLOB;
import mypackage.*;

public class ReportDBbean 
{
  public ReportDBbean()
  {
  }
  
  Connection conn;
  PreparedStatement prestmt;
  ResultSet rs;
  ResultSet rset;
  PreparedStatement ps;
 
  
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


  public FiscalYearBean getFiscalYearInfo(String fycode)
  {
    FiscalYearBean fb = new FiscalYearBean();
    
     try {
      conn = initializeConn();   
      prestmt = conn.prepareStatement("select * from fiscal_years where code=?");
      prestmt.setString(1, fycode);
      
      rs = prestmt.executeQuery();
      while(rs.next())
      {
        fb.setFycode(rs.getInt("code"));
        fb.setYear(rs.getString("description"));
        fb.setStartdate(rs.getDate("start_date"));
        fb.setEnddate(rs.getDate("end_date"));
      }
      
    }catch(Exception e){
      System.err.println("error getFiscalYearInfo() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(prestmt);
    }    
    return fb;
  }

 

  /**
   * Rpt Reviewer Availability and Number of Assignments for Fiscal Year 
   * This will get reviewer info and num grants accepted for selected fy, and then count the
   * number of grants already assigned for this selected fy
   * 2/26/08 modified to get reviewer participation comment and display on jsp
   * @return 
   * @param fycode
   */
  public Vector getAcceptedAssignedForFy(String fycode, String grantprogram)
  {
    Vector results = new Vector();//temp to hold first part of query results
    Vector allRev = new Vector();   
    int fccode =0;
    
    try{      
      conn = initializeConn();   
      
      //get the reviewer info and num accepted for fy
      prestmt = conn.prepareStatement("select fname, lname, interest, num_accepted, description, rev_id, title, affiliation from "+
      " reviewers, grant_assign_maxes where reviewers.id = grant_assign_maxes.rev_id and fy_code=? and grant_program=? order by lname ");
      prestmt.setString(1, fycode);
      prestmt.setString(2, grantprogram);
      
      rs = prestmt.executeQuery();
      while(rs.next())
      {        
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));
        rb.setInterest(rs.getString("interest"));
        rb.setRevid(rs.getLong("rev_id"));
        rb.setTitle(rs.getString("title"));
        rb.setAffiliation(rs.getString("affiliation"));
        
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setNumaccepted(rs.getInt("num_accepted"));
          rab.setComment(rs.getString("description"));
          
          ReviewerAssignBean[] allassign={rab};
          rb.setReviewerAssigns(allassign);
        
        results.add(rb);
      }

      //get fund code for grant program
      if(grantprogram.equalsIgnoreCase("discretionary"))
        fccode =5;
      else if(grantprogram.equalsIgnoreCase("lgrmif"))
        fccode =80; 
      else
        fccode =7;
      
      //get reviewer num assigned for fy for this grant program (some rev do both co and di)
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select count(grant_assigns.id) as numassign, rev_id from "+
      " grant_assigns left join grants on grants.id= grant_assigns.gra_id where fy_code=? and fc_code=? "+
      " and rev_id=? group by rev_id  ");
      prestmt.setString(1, fycode);
      prestmt.setInt(2, fccode);
      
      for(int i=0; i<results.size(); i++)
      {               
        ReviewerBean rb = (ReviewerBean) results.get(i);
        ReviewerAssignBean rab = rb.reviewerAssigns[0];
        prestmt.setLong(3, rb.getRevid());
        
        rs = prestmt.executeQuery();
        while(rs.next())
          rab.setNumassigned(rs.getInt("numassign"));
        allRev.add(rb);
        
      }
        
    }catch(Exception e){
      System.err.println("error getAcceptedAssignedForFy() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }
    return allRev;
  }
  
  
    /**
     * This will get reviewer info and num grants accepted for selected fy.
     * @return 
     * @param fycode
     */
    public Vector getAllReviewerAvailability(int fycode, int fccode, String grantprogram)
    {
      Vector results = new Vector();
           
      try{      
        conn = initializeConn();   
        
        //get all reviewers for program
        prestmt = conn.prepareStatement("select reviewer_programs.rev_id, fname, lname, title, "+
        " affiliation from reviewers, reviewer_programs where " + 
        " reviewers.id=reviewer_programs.rev_id and fc_code=? and reviewers.active='Y' order by lname");
        prestmt.setInt(1, fccode);        
        rs = prestmt.executeQuery();
        while(rs.next())
        {        
          ReviewerBean rb = new ReviewerBean();
          rb.setFname(rs.getString("fname"));
          rb.setLname(rs.getString("lname"));
          rb.setRevid(rs.getLong("rev_id"));
          rb.setTitle(rs.getString("title"));
          rb.setAffiliation(rs.getString("affiliation"));    
          rb.setAvailable(2);//to diff b/w yes/no/no response
          results.add(rb);
        }

               
        //get reviewer availability for fy for this grant program
        prestmt.clearParameters();
        prestmt = conn.prepareStatement("select num_accepted, description, fy_code, rev_id " + 
        "  from grant_assign_maxes where grant_program=? and fy_code=? and rev_id=?");
        prestmt.setString(1, grantprogram);
        prestmt.setInt(2, fycode);
        
        for(int i=0; i<results.size(); i++)
        {               
          ReviewerBean rb = (ReviewerBean) results.get(i);
          prestmt.setLong(3, rb.getRevid());
          
          rs = prestmt.executeQuery();
          while(rs.next()){
            rb.setAvailable(rs.getInt("num_accepted"));   
            rb.setComment(rs.getString("description"));
          }
        }
          
      }catch(Exception e){
        System.err.println("error getAllReviewerAvailability() " + e.getMessage().toString());
      }
      finally{
        Close(prestmt);
        Close(conn);
        Close(rs);
      }
      return results;
    }
  
  /**
   * Rpt Amount Approved for Statutory Aid for Fiscal Year 
   * This gets the total amt approved for each Sa grant for the selected fy. MODIFIED to get only
   * grants that have been submitted (previously listed all SA grants-even blank ones)
   * @return 
   * @param submittedGrants - vector containing GrantBean's
   */
  public Vector getSaAmtApprovedForFy(Vector submittedGrants)
  {
    Vector results = new Vector();
    BudgetDBHandler bdh = new BudgetDBHandler();
    try{
            
      //for each grant, get amt appr
      for(int i=0; i<submittedGrants.size(); i++)
      {
        GrantBean thegb = (GrantBean) submittedGrants.get(i);
        
        int amtappr = bdh.calcTotalAmtApproved(thegb.getGrantid(), 0);
        thegb.setTotamtappr(amtappr);
        results.add(thegb);
      }      
      
    }catch(Exception e){
      System.err.println("error getSaAmtApprovedForFy() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(prestmt);
      Close(rs);
    }      
    return results;
  }



  /**
   * Rpt Reviewer's assigned to Co or DI projects for Fiscal Year 
   * This will get reviewer info and grant info for each grant a reviewer is assigned 
   * during the selected fy. MODIFIED to filter on grant program
   * @return 
   * @param fycode, fccode
   */
  public Vector getReviewerAssignments(String fycode, String fccode)
  {
    Vector results = new Vector();    
 
    try{
      conn = initializeConn();     
      
      prestmt = conn.prepareStatement("select salutation, fname, lname, title, interest, rating_complete, "+
        " grant_assigns.gra_id, rev_id, name, proj_seq_num, fy_code, fc_code, co_institutions.inst_id, "+
        " initcap(POPULAR_NAME) as popular_name from reviewers, grant_assigns, grants, co_institutions    left join "+
        " sed_institutions on co_institutions.inst_id= sed_institutions.inst_id where reviewers.id = "+
        " grant_assigns.rev_id and  grant_assigns.gra_id=grants.id and grants.id = co_institutions.gra_id "+
        " and fy_code=? and fc_code=? and is_lead='Y' order by lname, gra_id");
      prestmt.setString(1, fycode);
      prestmt.setString(2, fccode);
      
      rs = prestmt.executeQuery();
      while(rs.next())
      {        
        ReviewerBean rb = new ReviewerBean();
        rb.setSalutation(rs.getString("salutation"));
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));
        rb.setInterest(rs.getString("interest"));
        rb.setRevid(rs.getLong("rev_id"));
                
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setRatingcomp(rs.getBoolean("rating_complete"));
          rab.setGraid(rs.getLong("gra_id"));        
          rab.setProjseqnum(rs.getLong("proj_seq_num"));
          rab.setFycode(rs.getInt("fy_code"));
          rab.setFccode(rs.getInt("fc_code"));
          rab.setInstid(rs.getLong("inst_id"));
          rab.setInstname(rs.getString("popular_name"));
          rab.setTitle(rs.getString("name"));
          
          ReviewerAssignBean[] allassign = {rab};
        rb.setReviewerAssigns(allassign);
        
        results.add(rb);        
      }
      
      
    }catch(Exception e){
      System.err.println("error getReviewerAssignments() "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(prestmt);
      Close(rs);
    }      
    return results;      
  }  
 
  public Vector getRevContactInfo(String fundCodeList, boolean checkActive)
  {
    Vector results = new Vector();    
    
    try {
      conn = initializeConn();
      
        if(checkActive){
            prestmt = conn.prepareStatement("select id, salutation, fname, mname, lname, title, affiliation, interest, " + 
            "active, vendor_num, SECUSR.SED_DATA_SECURE.DECRYPT(SSN) as ssn from reviewers " + 
            "where active='Y' and id in (select rev_id from " + 
            "    reviewer_programs where fc_code in ("+fundCodeList+")) order by lname");
        }else{
            prestmt = conn.prepareStatement("select id, salutation, fname, mname, lname, title, affiliation, interest, " + 
            "  active, vendor_num, SECUSR.SED_DATA_SECURE.DECRYPT(SSN) as ssn from reviewers " + 
            "  where id in (select rev_id from " + 
            "      reviewer_programs where fc_code in ("+fundCodeList+")) order by lname");
        }
       
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new ReviewerBean();
        rb.setRevid(rs.getLong("ID"));
        rb.setSalutation(rs.getString("SALUTATION"));
        rb.setFname(rs.getString("FNAME"));
        rb.setMname(rs.getString("MNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setTitle(rs.getString("TITLE"));
        rb.setAffiliation(rs.getString("AFFILIATION"));
        rb.setInterest(rs.getString("INTEREST"));
        rb.setActive(rs.getString("ACTIVE"));
        rb.setSsn(rs.getString("ssn"));
        rb.setVendornum(rs.getString("vendor_num"));
        results.add(rb);
      }      
      
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select * from CONTACTS where REV_ID=?");
      
      for(int i=0; i<results.size(); i++)
      {
          ReviewerBean therb = (ReviewerBean) results.get(i);
          prestmt.setLong(1, therb.getRevid());
          rs = prestmt.executeQuery();
          
          while(rs.next())
          {
            if(rs.getInt("CONTACT_TYPE_CODE")==2)
              therb.setPhone(rs.getString("CONTACT_VALUE"));          
            else if(rs.getInt("CONTACT_TYPE_CODE")==3)
              therb.setEmail(rs.getString("CONTACT_VALUE"));
          }
      }          
      
      
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select * from ADDRESSES where REV_ID=?");
      
      for(int i=0; i<results.size(); i++)
      {
          ReviewerBean therb = (ReviewerBean) results.get(i);
          prestmt.setLong(1, therb.getRevid());
          rs = prestmt.executeQuery();
          
          while(rs.next())
          {
            therb.setAddress(rs.getString("ADDR_LINE1"));
            therb.setCity(rs.getString("CITY"));
            therb.setState(rs.getString("STATE"));
            therb.setZipcode(rs.getString("ZIPCODE"));        
          }    
      }
           
    }catch(Exception e){
      System.err.println("error getRevContactInfo() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }    
    return results;
  }

   
 /* public Vector getComments(GrantBean gb)
  {
    Vector results = new Vector();    
    try {            
      conn = initializeConn();
      prestmt = conn.prepareStatement("select GRANT_ASSIGNS.ID, RATING_COMPLETE, GRA_ID, REV_ID, "+
        " FNAME, LNAME, REVC_COMMENT from GRANT_ASSIGNS, REVIEWERS, REVIEWER_COMMENTS where "+
        " GRA_ID=? and RATING_COMPLETE=1 and REVIEWERS.ID = GRANT_ASSIGNS.REV_ID and "+
        " GRANT_ASSIGNS.ID = REVIEWER_COMMENTS.GRAAS_ID");
      prestmt.setLong(1, gb.getGrantid());           
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        ReviewerAssignBean rb = new ReviewerAssignBean();
        rb.setAssignid(rs.getLong("ID"));
        rb.setRatingcomp(rs.getboolean("RATING_COMPLETE"));
        rb.setGraid(rs.getLong("GRA_ID"));
        rb.setRevid(rs.getInt("REV_ID"));
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setComment(rs.getString("REVC_COMMENT"));
        
        results.add(rb);
      }     
      
    }catch(Exception e){
      System.err.println("error getComments() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }    
    return results;
  }*/
  
  /**
   * 2/14/08 fixed bug - filter on is_lead institution
   * @return 
   * @param projnum
   */
  public GrantBean getBasicGrantInfo(String projnum)
  {
    GrantBean gb = new GrantBean();    
    try {
      conn = initializeConn();
         
      /*prestmt = conn.prepareStatement("select grants.id, name, proj_seq_num, fy_code, fc_code, "+
        " co_institutions.inst_id, initcap(POPULAR_NAME) as popular_name from grants, co_institutions left join "+
        " sed_institutions on sed_institutions.inst_id = co_institutions.inst_id where grants.id "+
        " = co_institutions.GRA_ID and proj_seq_num =? and is_lead='Y'");*/
      prestmt=conn.prepareStatement("select grants.id, name, proj_seq_num, fy_code, fc_code, " + 
      " co_institutions.inst_id, initcap(POPULAR_NAME) as popular_name, descr from co_institutions, " + 
      " sed_institutions, grants left join project_categories on grants.pc_id=project_categories.id " + 
      " where grants.id=co_institutions.GRA_ID and sed_institutions.inst_id=co_institutions.inst_id " + 
      " and proj_seq_num =? and is_lead='Y'");
      prestmt.setString(1, projnum);
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        gb.setGrantid(rs.getLong("id"));
        gb.setTitle(rs.getString("name"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setInstName(rs.getString("popular_name"));
        gb.setProjcategory(rs.getString("descr"));
      }            
    
    } catch(Exception e) {
      System.err.println("error getGrantInfo() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }    
    return gb;
  }
  

  /**
   * Rpt Scores for Coordinated Projects for Fiscal Year 
   * This will get reviewer scores for given CO grants, sort in score order. It calcs
   * the avg score - rpt only displays avg score (not individual rev scores). 
   * @return 
   */
  public Vector getCoordGrantsScores(Vector submittedGrants, int fycode)
  {    
    try {
      conn  = initializeConn();      
              
      if(fycode<=9)
      {
          //get the total scores for each grant 
          prestmt = conn.prepareStatement("select rev_id, sum(score) as sumscore from reviewer_ratings, "+
          " grant_assigns where reviewer_ratings.GRAAS_ID=grant_assigns.id and rating_complete=1 "+
          " and gra_id =? group by rev_id");          
      }
      else {
          //get weighted score for each grant
           prestmt = conn.prepareStatement("select rev_id, sum(case rat_id when 12 then (score*2) " + 
           " when 13 then (score*2) when 14 then (score*2) else score end) sumscore " + 
           " from reviewer_ratings, grant_assigns where reviewer_ratings.GRAAS_ID=grant_assigns.id " + 
           " and rating_complete=1 and gra_id =? group by rev_id");                      
      }
      
      
        for(int i=0; i<submittedGrants.size(); i++)
        {
          int count=0;
          int totscore=0;
          GrantBean gb = (GrantBean) submittedGrants.get(i);
          prestmt.setLong(1, gb.getGrantid());
          rs = prestmt.executeQuery();
          
          while(rs.next()) {
            count++;
            totscore += rs.getInt("sumscore");
          }
          if(count>0)
            totscore = totscore/count;
          
          gb.setScore(totscore);        
        }    
        
      Collections.sort(submittedGrants);
      
    } catch(Exception e) {
      System.err.println("error getCoordGrantsScores() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }
    return submittedGrants;  
  }



  /**
   * Rpt Amount Approved for Coordinated Aid for Fiscal year period 
   * This will get the total amount appr for each grant
   * for each fy in the fy range.  
   * @return 
   * @param fy2 the ending fiscal year range
   * @param fy1 the starting fy range
   */
  public Vector getCoAmtApprForFy(String fy1, String fy2)
  {  
    Vector results = new Vector();        
    try {
      conn  = initializeConn();
           
      //get all co grants with budget records within the selected fy period        
        prestmt = conn.prepareStatement("select grants.id, fc_code from grants where fc_code=7 "+
        " and GRANTS.ID in  (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' ) "+
        " and ( GRANTS.ID in (select gra_id from personal_services where fy_code  between ? and ?) or "+
        " Grants.ID in (select gra_id from employee_benefits where fy_code between ? and ?) or "+
        " Grants.ID in (select gra_id from contracted_services where fy_code between ? and ?) or "+
        " Grants.ID in (select gra_id from supp_mat_equips where fy_code between ? and ?) or "+
        " Grants.ID in (select gra_id from other_expenses where fy_code between ? and ?)   or "+
        " Grants.ID in (select gra_id from travel_expenses where fy_code between ? and ?))"); 
        prestmt.setString(1, fy1);
        prestmt.setString(2, fy2);
        prestmt.setString(3, fy1);
        prestmt.setString(4, fy2);
        prestmt.setString(5, fy1);
        prestmt.setString(6, fy2);
        prestmt.setString(7, fy1);
        prestmt.setString(8, fy2);
        prestmt.setString(9, fy1);
        prestmt.setString(10, fy2);   
        prestmt.setString(11, fy1);
        prestmt.setString(12, fy2);   
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setFccode(rs.getInt("FC_CODE"));
                  
          results.add(gb);
        }
      
            
      prestmt.clearParameters();
      //get all info for each grant; inst, title, etc.
      prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "+
     " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name from GRANTS, CO_INSTITUTIONS left join "+
     " SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = "+
     " CO_INSTITUTIONS.GRA_ID and IS_LEAD='Y' and GRANTS.ID=? ");
     
     
      //get total amtappr by fy for each grant
      ps = conn.prepareStatement("select sum(asum) as totappr, fy_code from ( "+
      " select sum(amount_approved) as asum, fy_code from personal_services where gra_id=? "+
      "  group by fy_code        union all "+
      " select sum(amount_approved) as asum, fy_code from employee_benefits where gra_id=? "+ 
      "  group by fy_code        union all "+
      " select sum(amount_approved) as asum, fy_code from contracted_services where gra_id=? "+ 
      "  group by fy_code       union all "+
      " select sum(amount_approved) as asum, fy_code from supp_mat_equips where gra_id=? "+ 
      "  group by fy_code          union all "+ 
      " select sum(amount_approved) as asum, fy_code from other_expenses where gra_id=? "+ 
      "  group by fy_code           union all " +
      " select sum(amount_approved) as asum,  fy_code from travel_expenses where gra_id=? "+
      " group by fy_code "+
      ") tmptable group by fy_code");
      
      
      //loop on all coordinated grants - get grant info 
      for(int i=0; i<results.size(); i++)
      {
        GrantBean gb = (GrantBean)results.get(i);
        prestmt.setLong(1, gb.getGrantid());       
       
        rs = prestmt.executeQuery();
        while(rs.next())
        {
          gb.setFycode(rs.getInt("FY_CODE"));
          gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
          gb.setTitle(rs.getString("NAME"));
          gb.setInstID(rs.getLong("INST_ID"));
          gb.setInstName(rs.getString("POPULAR_NAME"));
        }
        
        //get total amtappr
        ps.setLong(1, gb.getGrantid());
        ps.setLong(2, gb.getGrantid());
        ps.setLong(3, gb.getGrantid());
        ps.setLong(4, gb.getGrantid());
        ps.setLong(5, gb.getGrantid());
        ps.setLong(6, gb.getGrantid());
        
        rset = ps.executeQuery();
        ArrayList thelist = new ArrayList();
        int totalapprsum =0;
        while(rset.next())
        {      
          TotalsBean tb = new TotalsBean();
          tb.setFycode(rset.getInt("FY_CODE"));
          tb.setTotAmtAppr(rset.getInt("TOTAPPR"));
          thelist.add(tb);
          
          totalapprsum+= tb.getTotAmtAppr();
        }
        
        //set the list of approvals by fy and total appr amt
        TotalsBean[] alltotals = (TotalsBean[]) thelist.toArray(new TotalsBean[0]);
        gb.setTotalsBeans(alltotals);
        gb.setTotamtappr(totalapprsum);        
        
      }//end for loop      
      
    } catch(Exception e) {
      System.err.println("error getCoAmtApprForFy() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
      Close(rset);     
      Close(ps);     
    }
    return results;
  }


  /**
   * Rpt Amount Approved for Coordinated Aid for Fiscal year period 
   * This will get the grand totals of amt approved by fiscal year.
   * @return 
   */
  public Vector calcCoAmtApprForFy()
  {
    Vector results = new Vector();    
    try {
      conn  = initializeConn();
      prestmt = conn.prepareStatement("select sum(totappr) as sumappr, fy_code from "+
      " ldgrants.multifyproj_budgettotals_view where fc_code=7 group by fy_code");      
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        TotalsBean tb = new TotalsBean();
        tb.setFycode(rs.getInt("fy_code"));
        tb.setTotAmtAppr(rs.getInt("sumappr"));        
        results.add(tb);
      }      
    
    } catch(Exception e) {
      System.err.println("error calcCoAmtApprForFy() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }
    return results;
  }
  
  /**
     * 11/19/11 calculate totreq for each year of coordinated grant. used on new coord score
     * order rpt per BL.
     * @param allGrants, fycode
     * @return
     */
    public Vector calcAmtReqApprEachYear(Vector allGrants, int fycode)
    {
      Vector results = new Vector();    
      try {      
        conn = initializeConn();
        prestmt = conn.prepareStatement("select * from ldgrants.multifyproj_budgettotals_view "+
        " where gra_id=? order by fy_code");      
        for(int i=0; i<allGrants.size(); i++)
        {
          GrantBean gb = (GrantBean) allGrants.get(i);
          prestmt.setLong(1, gb.getGrantid());
          rs = prestmt.executeQuery();     
          int totappr=0, totreq=0;
          
          while(rs.next()){            
                if(rs.getInt("fy_code")==fycode){
                    //year 1 budget totals
                    gb.setFyOne(rs.getInt("fy_code"));
                    gb.setFyOneRequest(rs.getLong("totreq"));
                    totreq+= rs.getLong("totreq");
                    totappr+= rs.getLong("totappr");
                }
                else if(rs.getInt("fy_code")== (fycode+1)){
                    //year 2 budget totals
                    gb.setFyTwo(rs.getInt("fy_code"));
                    gb.setFyTwoRequest(rs.getLong("totreq"));
                    totreq+= rs.getLong("totreq");
                    totappr+= rs.getLong("totappr");
                }
                else{
                    //year 3 budget totals
                    gb.setFyThree(rs.getInt("fy_code"));
                    gb.setFyThreeRequest(rs.getLong("totreq"));
                    totreq+= rs.getLong("totreq");
                    totappr+= rs.getLong("totappr");
                }                   
          }    
          gb.setTotamtappr(totappr);
          gb.setTotamtreq(totreq);
          results.add(gb);        
        }          
     
      }catch(Exception e) {
         System.err.println("error calcAmtReqApprEachYear() " + e.getMessage().toString());
      }
      finally{
         Close(prestmt);
         Close(conn);
         Close(rs);
      }
      return results;
    }
    
      

  /**
   * Gets amt req/appr for each grantid, using view. 
   * @return 
   * @param allGrants Vector containing GrantBeans
   */
  public Vector calcAmtReqAmtAppr(Vector allGrants)
  {
    Vector results = new Vector();    
    try {
      /* removed 11/17/09 to use view instead
       * String select = "select sum(asum) as totappr, sum(bsum) as totreq from ( "+
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from personal_services where gra_id =?  union all "+
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from employee_benefits where gra_id =?  union all "+
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from contracted_services where gra_id =?  union all "+
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from supp_mat_equips where gra_id =?     union all "+
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from other_expenses where gra_id =?     union all " +
        " select sum(amount_approved) as asum, sum(grant_request) as bsum from travel_expenses where gra_id =? "+
        ") tmptable ";
      
      conn = initializeConn();
      prestmt = conn.prepareStatement(select);      
      for(int i=0; i<allGrants.size(); i++)
      {
        GrantBean gb = (GrantBean) allGrants.get(i);
        prestmt.setLong(1, gb.getGrantid());
        prestmt.setLong(2, gb.getGrantid());
        prestmt.setLong(3, gb.getGrantid());
        prestmt.setLong(4, gb.getGrantid());
        prestmt.setLong(5, gb.getGrantid());
        prestmt.setLong(6, gb.getGrantid());
        rs = prestmt.executeQuery();        
        while(rs.next()){
          gb.setTotamtappr(rs.getInt("totappr"));
          gb.setTotamtreq(rs.getInt("totreq"));
        }    
        results.add(gb);        
      }      */
            
     conn = initializeConn();
     prestmt = conn.prepareStatement("select gra_id, totreq, totappr from ldgrants.budgettotalsview "+
     " where gra_id=?");      
     for(int i=0; i<allGrants.size(); i++)
     {
       GrantBean gb = (GrantBean) allGrants.get(i);
       prestmt.setLong(1, gb.getGrantid());
       rs = prestmt.executeQuery();        
       while(rs.next()){
         gb.setTotamtappr(rs.getInt("totappr"));
         gb.setTotamtreq(rs.getInt("totreq"));
       }    
       results.add(gb);        
     }          
      
    }catch(Exception e) {
      System.err.println("error calcAmtReqAmtAppr() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }
    return results;
  }

  public Vector getInitialSubmitGrants(String fycode, int fccode)
  {
    Vector results = new Vector(); //will hold all initial grants submitted
           
    try {
       conn = initializeConn();      
      
      //changed 12/8/08 to include county and city names
      prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "
      +" CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, "
      +" initcap(sed_counties.description) as countyname, initcap(city) as city "
      +" from GRANTS, CO_INSTITUTIONS left join "
      +" SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "
      +" left join sed_counties on "
      +"   sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on "
      +"   sed_institutions.inst_id= sed_addresses.inst_id  where GRANTS.ID = "
      +" CO_INSTITUTIONS.GRA_ID and FC_CODE=? and FY_CODE=? and IS_LEAD='Y' and addr_type_code = 1 and GRANTS.ID in "
      +" (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' )");
      prestmt.setInt(1, fccode);
      prestmt.setString(2, fycode);
      rs = prestmt.executeQuery();      
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setFccode(rs.getInt("FC_CODE"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));
        
        results.add(gb);
      }      
      
    } catch (Exception ex){
        System.err.println("error getInitialSubmitGrants()  " + ex.toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
  
    public Vector getContactsForSubmitGrants(int fycode, int fccode)
    {
      Vector results = new Vector(); //hold all initial grants submitted
             
      try {
         conn = initializeConn();              
        prestmt = conn.prepareStatement("select grants.id, fc_code, fy_code, proj_seq_num, "+
        " initcap(popular_name) as instname, initcap(sap.fname) as ceofname, " + 
        " initcap(sap.lname) as ceolname, pm.fname as pmfname, pm.lname as pmlname, " + 
        " gs.fname as rmofname, gs.lname as rmolname, descr from grants left join "+
        " project_categories on grants.pc_id=project_categories.id, co_institutions left join "+
        " sed_institutions on co_institutions.inst_id=sed_institutions.inst_id, grant_admins "+
        " left join sed_admin_positions sap on grant_admins.admin_pos_id= sap.admin_pos_id, " + 
        " project_managers pm, grant_staffs gs where grants.id=grant_admins.gra_id and grants.prm_id=pm.id and " + 
        " grants.id=co_institutions.gra_id and grants.id=gs.gra_id and fc_code=? and fy_code=? " + 
        " and is_lead='Y' and st1_id=1 and GRANTS.ID in " + 
        " (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' )");
        prestmt.setInt(1, fccode);
        prestmt.setInt(2, fycode);
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setFccode(rs.getInt("FC_CODE"));
          gb.setFycode(rs.getInt("FY_CODE"));
          gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
          gb.setInstName(rs.getString("instname"));
          gb.setProjcategory(rs.getString("descr"));
          gb.setPmName(rs.getString("pmfname") +" "+ rs.getString("pmlname"));
          gb.setRmoName(rs.getString("rmofname") +" "+ rs.getString("rmolname"));
          gb.setCeoName(rs.getString("ceofname") +" "+ rs.getString("ceolname"));
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getContactsForSubmitGrants()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    
    
    public Vector getGrantsSummaryDescr(int fycode, int fccode)
    {
      Vector results = new Vector(); //hold all initial grants submitted
             
      try {
         conn = initializeConn();              
        prestmt = conn.prepareStatement("select grants.id, fc_code, fy_code, proj_seq_num, " + 
        " initcap(popular_name) as instname, descr, narrative_descr " + 
        " from grants left join project_categories on grants.pc_id=project_categories.id, " + 
        "  project_narratives, co_institutions left join sed_institutions " + 
        " on co_institutions.inst_id=sed_institutions.inst_id where grants.id=project_narratives.gra_id " + 
        " and grants.id=co_institutions.gra_id and is_lead='Y' and nat_id=1 " + 
        " and fc_code=? and fy_code=? and grants.id in (select gra_id from grant_submissions " + 
        " where version='Initial')");
        prestmt.setInt(1, fccode);
        prestmt.setInt(2, fycode);
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setFccode(rs.getInt("FC_CODE"));
          gb.setFycode(rs.getInt("FY_CODE"));
          gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
          gb.setInstName(rs.getString("instname"));
          gb.setProjcategory(rs.getString("descr"));
          gb.setSummaryDescr(rs.getString("narrative_descr"));
          //??how to get summary clob??
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getGrantsSummaryDescr()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
  public Vector getLgInitialGrants(String fycode, int fccode, boolean isdoris)
  {
    Vector results = new Vector(); //will hold all initial grants submitted
           
    try {
       conn = initializeConn();      
            
       prestmt = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
       " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
       " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pcat, " + 
       " region_types.descr as rtype from GRANTS, govt_infos left join region_types " + 
       " on govt_infos.rt_id=region_types.id, project_categories, " + 
       " LDGRANTS.BUDGETTOTALSVIEW, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
       " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
       " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id and " + 
       " grants.id=govt_infos.gra_id and grants.FC_CODE=? and grants.FY_CODE=? and IS_LEAD='Y' and doris_flag=? and GRANTS.ID in " + 
       " (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' )");
      prestmt.setInt(1, fccode);
      prestmt.setString(2, fycode);
      prestmt.setBoolean(3, isdoris);
      rs = prestmt.executeQuery();      
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setFccode(rs.getInt("FC_CODE"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setDorisflag(rs.getBoolean("doris_flag"));
        gb.setDorisname(rs.getString("doris_name"));
        if(gb.isDorisflag())
            gb.setInstName(rs.getString("doris_name"));
        else
            gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setProjcategory(rs.getString("pcat"));
        gb.setRegion(rs.getString("rtype"));
        gb.setTotamtreq(rs.getInt("totreq"));
        gb.setTotamtappr(rs.getInt("totappr"));
        results.add(gb);
      }      
      
    } catch (Exception ex){
        System.err.println("error getLgInitialGrants()  " + ex.toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
  
  
    public Vector getApplicationsByCategory(int fycode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();      
                
         prestmt = conn.prepareStatement("select project_categories.id, descr, count(grants.id) "+
         " as numapps, sum(totreq) as sumreq, sum(totappr) as sumappr from project_categories left join grants on "+
         " grants.pc_id=project_categories.id left join LDGRANTS.BUDGETTOTALSVIEW on grants.id= "+
         " ldgrants.budgettotalsview.gra_id where grants.fc_code=80 and grants.fy_code=? and GRANTS.ID in (select "+
         " GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial') group by project_categories.id, descr");
        prestmt.setInt(1, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setProjcategoryId(rs.getInt("id"));
          gb.setProjcategory(rs.getString("descr"));
          gb.setTotamtreq(rs.getInt("sumreq"));
          gb.setTotamtappr(rs.getInt("sumappr"));
          gb.setCountapp(rs.getInt("numapps"));          
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getApplicationsByCategory()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    
    public Vector getUnsubmittedGrants(int fycode, int fccode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();      
                
         prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, "+
         " PROJ_SEQ_NUM, NAME, grants.created_by, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) "+
         " as popular_name, initcap(sed_counties.description) as countyname, initcap(city) as city "+
         " from GRANTS, CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id= "+
         " co_institutions.inst_id left join sed_counties on sed_institutions.COUNTY_CODE= "+
         " sed_counties.county_code left join sed_addresses on sed_institutions.inst_id= "+
         " sed_addresses.inst_id  where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and FC_CODE=? and "+
         " FY_CODE=? and IS_LEAD='Y' and addr_type_code = 1 and GRANTS.ID not in (select GRA_ID "+
         " from GRANT_SUBMISSIONS where VERSION='Initial' )");
         prestmt.setInt(1, fccode);
        prestmt.setInt(2, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setTitle(rs.getString("created_by"));
          gb.setInstName(rs.getString("popular_name"));
          gb.setCounty(rs.getString("countyname"));
          gb.setCity(rs.getString("city"));
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getUnsubmittedGrants()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    public Vector getAwardsByCategory(int fycode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();                      
         prestmt = conn.prepareStatement("select project_categories.id, descr, count(grants.id) "+
         " as numapps, sum(totreq) as sumreq, sum(totappr) as sumappr from project_categories left join grants on "+
         " grants.pc_id=project_categories.id left join LDGRANTS.BUDGETTOTALSVIEW on grants.id= "+
         " ldgrants.budgettotalsview.gra_id where grants.fc_code=80 and grants.fy_code=? and GRANTS.ID in (select "+
         " GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial') and grants.id in (select gra_id from " + 
         " approvals where version='Initial' and approval_type='Approve') group by project_categories.id, descr");
        prestmt.setInt(1, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setProjcategoryId(rs.getInt("id"));
          gb.setProjcategory(rs.getString("descr"));
          gb.setTotamtreq(rs.getInt("sumreq"));
          gb.setTotamtappr(rs.getInt("sumappr"));
          gb.setCountapp(rs.getInt("numapps"));          
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getAwardsByCategory()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    
    public Vector getAwardsByRegion(int fycode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();                      
         prestmt = conn.prepareStatement("select rt_id, descr, count(grants.id) as numapps, "+
         " sum(totreq) as sumreq, sum(totappr) as sumappr from region_types, govt_infos, "+
         " grants left join LDGRANTS.BUDGETTOTALSVIEW on grants.id= "+
         " ldgrants.budgettotalsview.gra_id where  grants.id=govt_infos.gra_id and region_types.id "+
         " =govt_infos.rt_id and grants.fc_code=80 and grants.fy_code=? and GRANTS.ID in (select " + 
         "  GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial') and grants.id in (select gra_id "+
         " from approvals where version='Initial' and approval_type='Approve') group by rt_id, descr");
        prestmt.setInt(1, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setCountapp(rs.getInt("numapps"));
          gb.setTotamtreq(rs.getInt("sumreq"));
          gb.setTotamtappr(rs.getInt("sumappr"));
          gb.setTitle(rs.getString("descr"));
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getAwardsByRegion()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    
    
    public Vector getAwardsByCounty(int fycode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();                      
         prestmt = conn.prepareStatement("select count(grants.id) as numapps, initcap(sed_counties.description) "+
         " as countyname, sum(totreq) as sumreq, sum(totappr) as sumappr from grants, "+
         " co_institutions left join LDGRANTS.BUDGETTOTALSVIEW on co_institutions.gra_id= "+
         " ldgrants.budgettotalsview.gra_id left join sed_institutions on sed_institutions.inst_id= "+
         " co_institutions.inst_id left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code " + 
         " where grants.id=co_institutions.gra_id and is_lead='Y' and grants.fc_code=80 and "+
         " grants.fy_code=? and GRANTS.ID in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial') "+
         " and grants.id in (select gra_id from approvals where version='Initial' and approval_type= "+
         " 'Approve') group by initcap(sed_counties.description)");
        prestmt.setInt(1, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setCountapp(rs.getInt("numapps"));
          gb.setCounty(rs.getString("countyname"));
          gb.setTotamtreq(rs.getInt("sumreq"));
          gb.setTotamtappr(rs.getInt("sumappr"));
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getAwardsByCounty()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
    
    public Vector getAwardsByGovtype(int fycode)
    {
      Vector results = new Vector();             
      try {
         conn = initializeConn();                      
         prestmt = conn.prepareStatement("select count(grants.id) as numapps, descr, sum(totreq) "+
         " as sumreq, sum(totappr) as sumappr from govt_types, govt_infos, grants left join "+
         " LDGRANTS.BUDGETTOTALSVIEW on grants.id= ldgrants.budgettotalsview.gra_id where grants.id= "+
         " govt_infos.gra_id and govt_infos.gt_id=govt_types.id and grants.fc_code=80 and "+
         " grants.fy_code=? and GRANTS.ID in (select GRA_ID from GRANT_SUBMISSIONS where VERSION= "+
         " 'Initial') and grants.id in (select gra_id from approvals where version='Initial' and "+
         " approval_type='Approve') group by descr");
        prestmt.setInt(1, fycode);        
        rs = prestmt.executeQuery();      
        
        while(rs.next()){
          GrantBean gb = new GrantBean();
          gb.setCountapp(rs.getInt("numapps"));
          gb.setTitle(rs.getString("descr"));
          gb.setTotamtreq(rs.getInt("sumreq"));
          gb.setTotamtappr(rs.getInt("sumappr"));
          results.add(gb);
        }      
        
      } catch (Exception ex){
          System.err.println("error getAwardsByGovtype()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
    
  
    public Vector getStatusForGrants(Vector allgrants)
    {
      try {      
       conn = initializeConn();
       ps = conn.prepareStatement("select * from GRANTS where ID=?");
          
       for(int i=0; i<allgrants.size(); i++) {
           GrantBean g = (GrantBean) allgrants.get(i);
           ps.setLong(1, g.getGrantid());
           rs = ps.executeQuery();
           
           AppStatusBean asb = new AppStatusBean();
           while(rs.next()) {
               asb.setCoversheetyn(rs.getBoolean("COVERSHEET_YN"));
               asb.setProjdescyn(rs.getBoolean("PROJ_DESC_YN"));
               asb.setAmtreqyn(rs.getBoolean("AMT_REQUESTED_YN"));//admin amt requested
               asb.setExpsubyn(rs.getBoolean("EXP_SUBMITTED_YN"));//admin final expenses
               asb.setFs10ayn(rs.getBoolean("FS10A_YN"));        
               asb.setFinalnarrativeyn(rs.getBoolean("FINAL_NARRATIVE_YN"));
               asb.setInstauthyn(rs.getBoolean("INST_AUTH_YN"));
               asb.setFinalsignoffyn(rs.getBoolean("FINAL_SIGNOFF_YN"));
               asb.setFs20yn(rs.getBoolean("FS20_YN"));
               asb.setFs10fyn(rs.getBoolean("FS10F_YN"));
               asb.setInterimrptyn(rs.getBoolean("microform_yn"));
               asb.setInterimauthyn(rs.getBoolean("religious_afill"));
               asb.setStatisticsyn(rs.getBoolean("education_app"));
           }
           g.setGrantstatusBean(asb);           
       }       
        
      } catch (Exception ex){
          System.err.println("error getStatusForGrants()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return allgrants;
    }
    
    
    
    public Vector getApcntComments(Vector allgrants)
    {
      try {      
       conn = initializeConn();
       ps = conn.prepareStatement("select * from ADMIN_COMMENTS where GRA_ID=? " + 
       " and change_completed='Y' order by DATE_CREATED desc ");
          
       for(int i=0; i<allgrants.size(); i++) {
           GrantBean g = (GrantBean) allgrants.get(i);
           ps.setLong(1, g.getGrantid());
           rs = ps.executeQuery();
           
           Vector allcomments = new Vector();
           while(rs.next()) {
               CommentBean cb = new CommentBean();
               cb.setId(rs.getLong("ID"));               
               cb.setComment(rs.getString("ADMC_COMMENT"));                    
               allcomments.add(cb);
           }
           CommentBean[] allcomm = (CommentBean[]) allcomments.toArray(new CommentBean[0]);
           g.setCommentBeans(allcomm);
       }       
        
      } catch (Exception ex){
          System.err.println("error getApcntComments()  " + ex.toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return allgrants;
    }
  
  public Vector getInitialApprAllPrograms(int fycode, int countycode)
  {
    Vector results = new Vector(); //will hold all initial grants submitted
           
    try {
       conn = initializeConn();      
 
      if(countycode==0)
      {
      prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "+
       "CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, "+
       "initcap(sed_counties.description) as countyname, initcap(city) as city from GRANTS, "+
       "CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
       "left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code left join "+
       "sed_addresses on sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID=CO_INSTITUTIONS.GRA_ID "+
       "and FY_CODE=? and IS_LEAD='Y' and addr_type_code = 1 and GRANTS.ID in "+
       "(select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' ) and GRANTS.ID in "+
       "(select GRA_ID from APPROVALS where VERSION='Initial' ) order by fc_code");
        prestmt.setInt(1, fycode);
      }
      else if(fycode==0)
      {
        prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "+
        "CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, "+
        "initcap(sed_counties.description) as countyname, initcap(city) as city from GRANTS, "+
        "CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
        "left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code left join "+
        "sed_addresses on sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID=CO_INSTITUTIONS.GRA_ID "+
        "and IS_LEAD='Y' and addr_type_code = 1 and sed_institutions.county_code=? and GRANTS.ID in "+
        "(select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' ) and GRANTS.ID in "+
        "(select GRA_ID from APPROVALS where VERSION='Initial' ) order by fc_code, fy_code desc");
        prestmt.setInt(1, countycode);
      }
      else
      {
        prestmt = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "+
       "CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, "+
       "initcap(sed_counties.description) as countyname, initcap(city) as city from GRANTS, "+
       "CO_INSTITUTIONS left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id "+
       "left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code left join "+
       "sed_addresses on sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID=CO_INSTITUTIONS.GRA_ID "+
       "and FY_CODE=? and IS_LEAD='Y' and addr_type_code = 1 and sed_institutions.county_code=? and GRANTS.ID in "+
       "(select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' ) and GRANTS.ID in "+
       "(select GRA_ID from APPROVALS where VERSION='Initial' ) order by fc_code");
        prestmt.setInt(1, fycode);
        prestmt.setInt(2, countycode);
      }
      rs = prestmt.executeQuery();      
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setFccode(rs.getInt("FC_CODE"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));
        
        switch(gb.getFccode())
        {
          case 5:
              gb.setProgram("CP Discretionary");
              break;
          case 6:
              gb.setProgram("CP Statutory");
              break;
          case 7:
              gb.setProgram("CP Coordinated");
              break;
          case 40:
              gb.setProgram("Adult Literacy");
              break;
          case 42:
              gb.setProgram("Family Literacy");
              break;
          case 80:
              gb.setProgram("LGRMIF");
              break;
          case 86:
              gb.setProgram("Construction");
              break;
        }
        
        results.add(gb);
      }      
      
    } catch (Exception ex){
        System.err.println("error getInitialApprAllPrograms() " + ex.toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }

  public int getDiscGrantScores(long grantid)
  {
    HashMap results = new HashMap(); //will hold assignment id's, rev score
    int avgscore = 0;

    try {
        conn = initializeConn();      
        //get all assignments that have been completed for this grant
        prestmt = conn.prepareStatement("select id, rating_complete from grant_assigns where gra_id=? "+
        " and rating_complete=1");            
        prestmt.setLong(1, grantid);
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
          results.put(rs.getString("id"), new Integer(0));//grant_assigns.id
        }     
      
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select * from reviewer_ratings where graas_id=?");
      
      //calc the avg score from each rev assignment
      Iterator it = results.keySet().iterator();
      while(it.hasNext())
      {
        double sumscore = 0;
        String assignid = (String) it.next();
        prestmt.setString(1, assignid);
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
          int ratType = rs.getInt("rat_id");
          int score = rs.getInt("score");
          
          switch(ratType){
            case 1:
            case 2:
            case 7:
            case 8:
            case 10:
                sumscore = sumscore + (score*3);
                break;
            case 12:
            case 13:
            case 14:
                sumscore = sumscore + (score*2.23);
                break;
            case 38:
            case 39:
            case 40:
            case 41:
                sumscore = sumscore + (score*3.25);
                break;
            default:
                sumscore = sumscore + score;
                break;
          }
        }               
        int roundscore = (int)Math.round(sumscore);
        results.put(assignid, new Integer(roundscore));        
      }
      
      //now calc the avg score of all reviewer scores
      if(results.size()>0)
      {
        int totalReviews = results.size();
        int totalScore = 0;
        Iterator scoreIt = results.keySet().iterator();
        while(scoreIt.hasNext())
        {
          totalScore += ((Integer) results.get(scoreIt.next())).intValue();
        }
        avgscore = totalScore/totalReviews;
      }
      
    } catch (Exception ex){
        System.err.println("error getDiscGrantScores() " + ex.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return avgscore;
  }
  
  /**
     * Only used for lgrmif. To use for other programs -need to make proj_category and
     * region optional joins.
     * @param fycode
     * @param fccode
     * @param isdoris
     * @return
     */
  public Vector getAwardList(int fycode, int fccode, boolean isdoris) {
      Vector results = new Vector();
             
      try {
         conn = initializeConn();       
         prestmt = conn.prepareStatement("select grants.id, grants.fy_code, proj_seq_num, doris_flag, doris_name, " + 
         " grants.fc_code, initcap(POPULAR_NAME) as popular_name, totreq, totappr, project_categories.descr as pcat, " + 
         " region_types.descr as rtype from grants, " + 
         " project_categories, govt_infos left join region_types on govt_infos.rt_id=region_types.id, " + 
         "  co_institutions left join SED_INSTITUTIONS on sed_institutions.inst_id= " + 
         " co_institutions.inst_id left join ldgrants.budgettotalsview on LDGRANTS.BUDGETTOTALSVIEW.gra_id " + 
         " =co_institutions.gra_id where grants.pc_id=project_categories.id and GRANTS.ID = " + 
         " CO_INSTITUTIONS.GRA_ID and grants.id=govt_infos.gra_id and is_lead='Y' and grants.fc_code=? and grants.fy_code=? and " + 
         " doris_flag=? and GRANTS.ID in (select GRA_ID from APPROVALS where VERSION='Initial' " + 
         " and APPROVAL_TYPE='Approve' )");
         prestmt.setInt(1, fccode);
         prestmt.setInt(2, fycode);
         prestmt.setBoolean(3, isdoris);
         rs = prestmt.executeQuery();
         while(rs.next()){
             GrantBean gb = new GrantBean();
             gb.setFycode(rs.getInt("fy_code"));
             gb.setFccode(rs.getInt("fc_code"));
             gb.setProjseqnum(rs.getLong("proj_seq_num"));
             gb.setTotamtappr(rs.getInt("totappr"));
             gb.setTotamtreq(rs.getInt("totreq"));
             gb.setProjcategory(rs.getString("pcat"));
             gb.setRegion(rs.getString("rtype"));
             gb.setDorisflag(rs.getBoolean("doris_flag"));
             gb.setDorisname(rs.getString("doris_name"));
             if(gb.isDorisflag())
                 gb.setInstName(rs.getString("doris_name"));
             else
                 gb.setInstName(rs.getString("POPULAR_NAME"));
             results.add(gb);
         }         
         
      } catch (Exception ex){
          System.err.println("error getAwardList()  " + ex.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }
      return results;
  }



    public Vector getFullModNoFundList(int fycode, int fccode) {
        Vector results = new Vector();
               
        try {
           conn = initializeConn();       
           prestmt = conn.prepareStatement("select grants.id, grants.fy_code, proj_seq_num, doris_flag, doris_name, " + 
           " grants.fc_code, initcap(POPULAR_NAME) as popular_name, project_categories.descr as pcat, " + 
           " region_types.descr as rtype, recommendation from grants, panel_grants, " + 
           " project_categories, govt_infos left join region_types on govt_infos.rt_id=region_types.id, " + 
           " co_institutions left join SED_INSTITUTIONS on sed_institutions.inst_id= " + 
           " co_institutions.inst_id where grants.id=panel_grants.gra_id and grants.pc_id=project_categories.id " + 
           " and GRANTS.ID =CO_INSTITUTIONS.GRA_ID and grants.id=govt_infos.gra_id and is_lead='Y' and " + 
           " grants.fc_code=? and grants.fy_code=? and GRANTS.ID in (select GRA_ID from "+
           " grant_submissions where VERSION='Initial') order by popular_name");
           prestmt.setInt(1, fccode);
           prestmt.setInt(2, fycode);
           rs = prestmt.executeQuery();
           while(rs.next()){
               GrantBean gb = new GrantBean();
               gb.setFycode(rs.getInt("fy_code"));
               gb.setFccode(rs.getInt("fc_code"));
               gb.setProjseqnum(rs.getLong("proj_seq_num"));
               gb.setProjcategory(rs.getString("pcat"));
               gb.setRegion(rs.getString("rtype"));
               gb.setDorisflag(rs.getBoolean("doris_flag"));
               gb.setDorisname(rs.getString("doris_name"));
               gb.setRecommend(rs.getString("recommendation"));
               if(gb.isDorisflag())
                   gb.setInstName(rs.getString("doris_name"));
               else
                   gb.setInstName(rs.getString("POPULAR_NAME"));
               results.add(gb);
           }         
           
        } catch (Exception ex){
            System.err.println("error getAwardList()  " + ex.getMessage().toString());
        }
        finally {
          Close(conn);
          Close(rs);
          Close(prestmt);
        }
        return results;
    }

  public Vector getDiAwardList(String fycode)
  {
    Vector amtAppr = new Vector(); 
    Vector results = new Vector();
           
    try {
       conn = initializeConn();             
        //get the total amt approved for DI in given fy, ordered by gra_id
       /*prestmt = conn.prepareStatement("select sum(asum) as totappr, gra_id  from ( "+
        " select sum(amount_approved) as asum, gra_id from personal_services where gra_id in (select id "+
        " from grants where fc_code=5 and fy_code=?) group by gra_id     union all "+
        " select sum(amount_approved) as asum, gra_id  from employee_benefits where gra_id in (select id "+
        " from grants where fc_code=5 and fy_code=?) group by gra_id      union all "+
        " select sum(amount_approved) as asum, gra_id  from contracted_services where gra_id in (select id "+
        " from grants where fc_code=5 and fy_code=?) group by gra_id      union all "+
        " select sum(amount_approved) as asum, gra_id  from supp_mat_equips where gra_id in (select id "+
        " from grants where fc_code=5 and fy_code=?) group by gra_id       union all "+
        " select sum(amount_approved) as asum, gra_id  from other_expenses where gra_id in (select id "+
        " from grants where fc_code=5 and fy_code=?) group by gra_id       union all " + 
        " select sum(amount_approved) as asum, gra_id from travel_expenses where gra_id in (select id " + 
        " from grants where fc_code=5 and fy_code=?) group by gra_id "+
        " ) tmptable group by gra_id ");
        prestmt.setString(1, fycode);
        prestmt.setString(2, fycode);
        prestmt.setString(3, fycode);
        prestmt.setString(4, fycode);
        prestmt.setString(5, fycode);
        prestmt.setString(6, fycode);*/
        
        
        prestmt = conn.prepareStatement("select gra_id, totappr from ldgrants.budgettotalsview where " + 
        " fc_code=5 and fy_code=?");
        prestmt.setString(1, fycode);
        rs = prestmt.executeQuery();      
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("gra_id"));
          gb.setTotamtappr(rs.getInt("totappr"));          
          amtAppr.add(gb);
        }            
      
      //get all info for each grant - only if they were approved
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, "+
        " initcap(sed_counties.description) as countyname, initcap(city) as city  from GRANTS, CO_INSTITUTIONS left join SED_INSTITUTIONS on "+
        " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on "+
        " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on "+
        " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and "+
        " addr_type_code = 1 and GRANTS.ID=? and IS_LEAD='Y' and GRANTS.ID in "+
        " (select GRA_ID from APPROVALS where VERSION='Initial' and APPROVAL_TYPE='Approve' )");
                
             
      for(int i=0; i<amtAppr.size(); i++)
      {
          GrantBean gb = (GrantBean) amtAppr.get(i);
          prestmt.setLong(1, gb.getGrantid());
          rs = prestmt.executeQuery();      
          
          while(rs.next())
          {
            gb.setGrantid(rs.getLong("ID"));
            gb.setTitle(rs.getString("NAME"));
            gb.setInstID(rs.getLong("INST_ID"));
            gb.setInstName(rs.getString("POPULAR_NAME"));     
            gb.setCity(rs.getString("city"));
            gb.setCounty(rs.getString("countyname"));//sed_counties.description
            gb.setFycode(rs.getInt("fy_code"));
            results.add(gb);
          }      
      }      
      Collections.sort(results, GrantBean.InstitutionComparator);//sort by instname
      
    } catch (Exception ex){
        System.err.println("error getDiAwardList()  " + ex.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }
    return results;
  }

  public Vector getProjectManagerInfo(Vector grants)
  {
    Vector results = new Vector();
    
    try {
       conn = initializeConn();      
       
        //get the PM info for each grant
       prestmt = conn.prepareStatement("select * from PROJECT_MANAGERS, CONTACTS where PROJECT_MANAGERS.ID"+
       " =CONTACTS.PRM_ID and PROJECT_MANAGERS.ID in (select PRM_ID from GRANTS where ID=?)");
        
       for(int i=0; i<grants.size(); i++)
       {
          GrantBean rb = (GrantBean) grants.get(i);
          OfficerBean pm = new OfficerBean();
          
          prestmt.setLong(1, rb.getGrantid());          
          rs = prestmt.executeQuery();      
          while(rs.next())
          {            
            pm.setFname(rs.getString("FNAME"));
            pm.setLname(rs.getString("LNAME"));
            int contactcode = rs.getInt("contact_type_code");
            if(contactcode==2)
              pm.setPhone(rs.getString("CONTACT_VALUE"));
            else if(contactcode==3) 
              pm.setEmail(rs.getString("contact_value"));         
          }      
          
          rb.setProjectManager(pm);
          results.add(rb);
       }      
               
     } catch (Exception ex){
          System.err.println("error getProjectManagerInfo()  " + ex.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }
      return results;
  }

  /**
   * Search Di grants by grant title. Get amount approved for each grants.  This will return grants
   * that were approved and denied. Filter for Di grants submitted.  
   * modified 12/29/10 to use amt_appr_yn to determin whether to show/hide appr amts.
   * @return 
   * @param title
   */
  public Vector searchDiByTitle(String title)
  {
    Vector results = new Vector();
    
    //check for user input
    if(title==null || title.equals(""))
      return results;      
      
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, "+
      " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
      " initcap(sed_counties.description) as countyname, initcap(city) as city from " + 
      " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
      " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
      " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
      " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
      " grants.fy_code=FISCAL_YEARS.CODE and " + 
      " addr_type_code = 1 and FC_CODE=5 and IS_LEAD='Y' and upper(name) like upper(?) and grants.id in " + 
      " (select gra_id from grant_submissions where version='Initial' )");          
      prestmt.setString(1, "%"+title.trim()+"%");
      rs = prestmt.executeQuery();      
          
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));     
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));//sed_counties.description
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFiscalyear(rs.getString("fyear"));
        gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
        results.add(gb);
      }      
    
      /* removed 11/13/09 to use view instead
       * prestmt.clearParameters();
      prestmt = conn.prepareStatement("select sum(asum) as totappr from ( "+
        " select sum(amount_approved) as asum from personal_services where gra_id =?  union all "+
        " select sum(amount_approved) as asum from employee_benefits where gra_id =?  union all "+
        " select sum(amount_approved) as asum from contracted_services where gra_id =? union all "+
        " select sum(amount_approved) as asum from supp_mat_equips where gra_id =?  union all "+
        " select sum(amount_approved) as asum from other_expenses where gra_id =?  union all " +
        " select sum(amount_approved) as asum from travel_expenses where gra_id =? " +
        " ) tmptable ");
      
      for(int i=0; i<results.size(); i++)
      {
          GrantBean gb = (GrantBean) results.get(i);
          prestmt.setLong(1, gb.getGrantid());
          prestmt.setLong(2, gb.getGrantid());
          prestmt.setLong(3, gb.getGrantid());
          prestmt.setLong(4, gb.getGrantid());
          prestmt.setLong(5, gb.getGrantid());
          prestmt.setLong(6, gb.getGrantid());
          rs = prestmt.executeQuery();
          while(rs.next())
          {
            gb.setTotamtappr(rs.getInt("totappr"));
          }
      }    */
       prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
       " where gra_id=?");
       
       for(int i=0; i<results.size(); i++)
       {
            GrantBean gb = (GrantBean) results.get(i);
            prestmt.setLong(1, gb.getGrantid());
            rs = prestmt.executeQuery();
            while(rs.next()){
                if(gb.isDisplayAmts())
                    gb.setTotamtappr(rs.getInt("totamtappr"));
            }
       }      
      Collections.sort(results, GrantBean.InstitutionComparator);//sort by instname
    
      
    }catch(Exception e){
      System.out.println("error searchDiByTitle "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
    
  /**
     *new 7/17/13 per BL - query all cp dg projects by keyword title search, for 
     * given fy range.
     * @param title
     * @param startFyear
     * @param endFyear
     * @return
     */
  public Vector searchDiByTitleYear(String title, String startFyear, String endFyear)
  {
    Vector results = new Vector();
    
    //check for user input
    if(title==null || title.equals(""))
      return results;      
      
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, " + 
      " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
      " initcap(sed_counties.description) as countyname, initcap(city) as city from " + 
      " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
      " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
      " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
      " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
      " grants.fy_code=FISCAL_YEARS.CODE and fiscal_years.description between ? and ? and " + 
      " addr_type_code = 1 and FC_CODE=5 and IS_LEAD='Y' and upper(name) like upper(?) and grants.id in " + 
      " (select gra_id from grant_submissions where version='Initial') order by fiscal_years.description desc, popular_name");     
      prestmt.setString( 1, startFyear);
      prestmt.setString(2, endFyear);
      prestmt.setString(3, "%"+title.trim()+"%");
      rs = prestmt.executeQuery();      
          
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));     
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));//sed_counties.description
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFiscalyear(rs.getString("fyear"));
        gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
        results.add(gb);
      }      
    
     
       prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
       " where gra_id=?");
       
       for(int i=0; i<results.size(); i++)
       {
            GrantBean gb = (GrantBean) results.get(i);
            prestmt.setLong(1, gb.getGrantid());
            rs = prestmt.executeQuery();
            while(rs.next()){
                if(gb.isDisplayAmts())
                    gb.setTotamtappr(rs.getInt("totamtappr"));
            }
       }      
       
    }catch(Exception e){
      System.out.println("error searchDiByTitleYear "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
  
  
  public Vector searchMultiKeywordYear(String word1, String word2, String operator, String startFyear, String endFyear)
  {
    Vector results = new Vector();
    
    //check for user input
    if(word1==null || word1.equals("") || word2==null || word2.equals(""))
      return results;      
      
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, " + 
      " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
      " initcap(sed_counties.description) as countyname, initcap(city) as city from " + 
      " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
      " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
      " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
      " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
      " grants.fy_code=FISCAL_YEARS.CODE and fiscal_years.description between ? and ? and " + 
      " addr_type_code = 1 and FC_CODE=5 and IS_LEAD='Y' " +
      " and ( upper(name) like upper(?)  "+ operator +"  upper(name) like upper(?) ) and grants.id in " + 
      " (select gra_id from grant_submissions where version='Initial') order by fiscal_years.description desc, popular_name");     
      prestmt.setString( 1, startFyear);
      prestmt.setString(2, endFyear);
      prestmt.setString(3, "%"+word1.trim()+"%");
      prestmt.setString(4, "%"+word2.trim()+"%");
      rs = prestmt.executeQuery();      
          
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));     
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));//sed_counties.description
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFiscalyear(rs.getString("fyear"));
        gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
        results.add(gb);
      }      
    
     
       prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
       " where gra_id=?");
       
       for(int i=0; i<results.size(); i++)
       {
            GrantBean gb = (GrantBean) results.get(i);
            prestmt.setLong(1, gb.getGrantid());
            rs = prestmt.executeQuery();
            while(rs.next()){
                if(gb.isDisplayAmts())
                    gb.setTotamtappr(rs.getInt("totamtappr"));
            }
       }      
       
    }catch(Exception e){
      System.out.println("error searchMultiKeywordYear "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
  
  
  /**
     *new 7/18/13 per BL.  query all cp dg projects where education_app=true, for 
     * given fy range. 
     * @param startFyear
     * @param endFyear
     * @return
     */
  public Vector searchDiEducationYear(String startFyear, String endFyear)
  {
    Vector results = new Vector();
   
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, " + 
      " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
      " initcap(sed_counties.description) as countyname, initcap(city) as city from " + 
      " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
      " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
      " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
      " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
      " grants.fy_code=FISCAL_YEARS.CODE and fiscal_years.description between ? and ? and " + 
      " addr_type_code = 1 and FC_CODE=5 and IS_LEAD='Y' and education_app=1 and grants.id in " + 
      " (select gra_id from grant_submissions where version='Initial') order by fiscal_years.description desc, popular_name");     
      prestmt.setString( 1, startFyear);
      prestmt.setString(2, endFyear);
      rs = prestmt.executeQuery();      
          
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        gb.setInstID(rs.getLong("INST_ID"));
        gb.setInstName(rs.getString("POPULAR_NAME"));     
        gb.setCity(rs.getString("city"));
        gb.setCounty(rs.getString("countyname"));//sed_counties.description
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFiscalyear(rs.getString("fyear"));
        gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
        results.add(gb);
      }      
    
     
       prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
       " where gra_id=?");
       
       for(int i=0; i<results.size(); i++)
       {
            GrantBean gb = (GrantBean) results.get(i);
            prestmt.setLong(1, gb.getGrantid());
            rs = prestmt.executeQuery();
            while(rs.next()){
                if(gb.isDisplayAmts())
                    gb.setTotamtappr(rs.getInt("totamtappr"));
            }
       }      
       
    }catch(Exception e){
      System.out.println("error searchDiEducationYear "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
  
    /**
     * Search grants by institution. Get amount approved for each grants.  Return grants
     * that were approved and denied. created 1/5/11 for cp dg per BL.  
     * use amt_appr_yn to determin whether to show/hide appr amts.
     * @return 
     * @param inst
     * @param fccode
     */
    public Vector searchByInstPublicRpt(String inst, int fccode)
    {
      Vector results = new Vector();
      
      //check for user input
      if(inst==null || inst.equals(""))
        return results;      
        
      try{    
        conn = initializeConn();
        
        prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, " + 
        " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
        " initcap(sed_counties.description) as countyname, initcap(city) as city from  " + 
        " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
        " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
        " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
        " grants.fy_code=FISCAL_YEARS.CODE and " + 
        " addr_type_code = 1 and FC_CODE=? and IS_LEAD='Y' and upper(popular_name) "+
        " like upper(?) and grants.id in " + 
        " (select gra_id from grant_submissions where version='Initial') " + 
        " order by popular_name, fiscal_years.description");
        prestmt.setInt(1, fccode);
        prestmt.setString(2, "%"+inst.trim()+"%");
        rs = prestmt.executeQuery();      
            
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setTitle(rs.getString("NAME"));
          gb.setInstID(rs.getLong("INST_ID"));
          gb.setInstName(rs.getString("POPULAR_NAME"));     
          gb.setCity(rs.getString("city"));
          gb.setCounty(rs.getString("countyname"));//sed_counties.description
          gb.setFycode(rs.getInt("fy_code"));
          gb.setFiscalyear(rs.getString("fyear"));
          gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
          results.add(gb);
        }      
      
        //get amtappr for each resulting grant -only if boolean to show/hide amts is true
        prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
         " where gra_id=?");
         
         for(int i=0; i<results.size(); i++)
         {
              GrantBean gb = (GrantBean) results.get(i);
              prestmt.setLong(1, gb.getGrantid());
              rs = prestmt.executeQuery();
              while(rs.next()){
                  if(gb.isDisplayAmts())
                      gb.setTotamtappr(rs.getInt("totamtappr"));
              }
         }      
              
      }catch(Exception e){
        System.out.println("error searchByInstPublicRpt "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }
  
    public Vector applicantFyApprSearch(String fycode)
    {
      Vector results = new Vector();
      
      //check for user input
      if(fycode==null || fycode.equals(""))
        return results;      
        
      try{    
        conn = initializeConn();
        
        prestmt = conn.prepareStatement("select GRANTS.ID, NAME, FY_CODE, fiscal_years.description as fyear, " + 
        " AMT_APPROVED_YN, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name, " + 
        " initcap(sed_counties.description) as countyname, initcap(city) as city from " + 
        " GRANTS, FISCAL_YEARS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id left join sed_counties on " + 
        " sed_institutions.COUNTY_CODE= sed_counties.county_code left join sed_addresses on " + 
        " sed_institutions.inst_id= sed_addresses.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID and " + 
        " grants.fy_code=FISCAL_YEARS.CODE and AMT_APPROVED_YN=1 and " + 
        " addr_type_code = 1 and FC_CODE=5 and IS_LEAD='Y' and fy_code=? and grants.id in " + 
        " (select gra_id from grant_submissions where version='Initial' )   and grants.id in " + 
        " (select gra_id from APPROVALS where version='Initial') ");    
        int fy = Integer.parseInt(fycode);
        prestmt.setInt(1, fy);
        rs = prestmt.executeQuery();      
            
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("ID"));
          gb.setTitle(rs.getString("NAME"));
          gb.setInstID(rs.getLong("INST_ID"));
          gb.setInstName(rs.getString("POPULAR_NAME"));     
          gb.setCity(rs.getString("city"));
          gb.setCounty(rs.getString("countyname"));//sed_counties.description
          gb.setFycode(rs.getInt("fy_code"));
          gb.setFiscalyear(rs.getString("fyear"));
          gb.setDisplayAmts(rs.getBoolean("AMT_APPROVED_YN"));
          results.add(gb);
        }      
      
        
         prestmt = conn.prepareStatement("select sum(totappr) as totamtappr from ldgrants.BUDGETTOTALSVIEW "+
         " where gra_id=?");
         
         for(int i=0; i<results.size(); i++)
         {
              GrantBean gb = (GrantBean) results.get(i);
              prestmt.setLong(1, gb.getGrantid());
              rs = prestmt.executeQuery();
              while(rs.next()){
                  if(gb.isDisplayAmts())
                      gb.setTotamtappr(rs.getInt("totamtappr"));
              }
         }      
        Collections.sort(results, GrantBean.InstitutionComparator);//sort by instname
      
        
      }catch(Exception e){
        System.out.println("error applicantFyApprSearch "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }    
      return results;
    }


  public Vector getGrantsDenied(String fy, int fccode)
  {
    Vector allGrants = new Vector();
    
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select grants.id, fy_code, fc_code, proj_seq_num, name, "+
      " co_institutions.inst_id, initcap(POPULAR_NAME) as popular_name from grants, co_institutions "+
      " left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id where "+
      " fy_code=? and fc_code=? and is_lead='Y' and grants.id = co_institutions.gra_id and grants.id "+
      " in (select GRA_ID from APPROVALS where APPROVAL_TYPE='Denied' )");
      prestmt.setString(1, fy);
      prestmt.setInt(2, fccode);
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
          gb.setTitle(rs.getString("name"));
          
          allGrants.add(gb);
      }
      
    }catch(Exception e){
      System.out.println("error getGrantsDenied "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }  
    return allGrants;
  }
  
  /**
     * Used for lgrmif admin report only b/c it gets projcategory and region. 
     * @param fy
     * @param fccode
     * @return
     */
    public Vector getDeniedProjects(int fy, int fccode)
    {
      Vector allGrants = new Vector();      
      try{    
        conn = initializeConn();
        
        prestmt = conn.prepareStatement("select grants.id, grants.fy_code, grants.fc_code, proj_seq_num, name, " + 
        " co_institutions.inst_id, totreq, totappr, initcap(POPULAR_NAME) as popular_name, "+
        " project_categories.descr as pcat, region_types.descr as rtype, " + 
        " doris_flag, doris_name from LDGRANTS.BUDGETTOTALSVIEW, co_institutions, sed_institutions,  " + 
        " govt_infos left join region_types on govt_infos.rt_id=region_types.id, grants  " + 
        " left join project_categories on grants.pc_id=project_categories.id  where  " + 
        " grants.fy_code=? and grants.fc_code=? and is_lead='Y' and grants.id = co_institutions.gra_id " + 
        " and grants.id=LDGRANTS.BUDGETTOTALSVIEW.gra_id and sed_institutions.inst_id=co_institutions.inst_id " + 
        " and grants.id=govt_infos.gra_id " + 
        " and grants.id in (select GRA_ID from APPROVALS where APPROVAL_TYPE='Denied')");        
        prestmt.setInt(1, fy);
        prestmt.setInt(2, fccode);
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));            
            gb.setTitle(rs.getString("name"));
            gb.setProjcategory(rs.getString("pcat"));
            gb.setRegion(rs.getString("rtype"));
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("popular_name"));
            allGrants.add(gb);
        }
        
      }catch(Exception e){
        System.out.println("error getDeniedProjects "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(prestmt);
      }  
      return allGrants;
    }
  
  public Vector getLitScores(Vector allGrants)
  {       
    try{    
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("SELECT rev_id, SUM(CASE WHEN rat_id !=15 THEN score ELSE 0 END) as sumscore, "+
        " SUM(CASE WHEN rat_id =15 THEN score ELSE 0 END) as overallscore "+
        " FROM reviewer_ratings, grant_assigns where reviewer_ratings.GRAAS_ID=grant_assigns.id "+
        " and rating_complete=1 and gra_id=? group by rev_id order by rev_id ");
        
      for(int i=0; i<allGrants.size(); i++)
      {
          GrantBean gb = (GrantBean) allGrants.get(i);
          prestmt.setLong(1, gb.getGrantid());
          rs = prestmt.executeQuery();
          
          double avgscore=0;
          double avgrate =0;
          int count=0;
          ArrayList allRates = new ArrayList();
          
          while(rs.next())
          {
              RatingBean rb = new RatingBean();
              rb.setRevid(rs.getLong("rev_id"));
              rb.setScore(rs.getInt("sumscore"));
              rb.setOverallscore(rs.getInt("overallscore"));
              
              avgscore+=rs.getInt("sumscore");
              avgrate+=rs.getInt("overallscore");
              count++;
              allRates.add(rb);
          }
          RatingBean[] allratings = (RatingBean[]) allRates.toArray(new RatingBean[0]);
          gb.setRatingBeans(allratings);
          
          if(count>0)
          {
            avgscore = avgscore/count;
            avgrate = avgrate/count;
              
            avgscore = Math.round(avgscore * Math.pow(10, (double)2)) / Math.pow(10, (double) 2);
            avgrate = Math.round(avgrate * Math.pow(10, (double)2)) / Math.pow(10, (double) 2);//2 decimal places
            gb.setScore(avgscore);
            gb.setRating(avgrate);
          }
      }
      
    }catch(Exception e){
      System.out.println("error getLitScores "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }  
    return allGrants;
  }
  
   
  public ArrayList getPanelReviewersForFy(int fycode)
  {   
    ArrayList allRevs = new ArrayList();
    try{    
      conn = initializeConn();
      
      ps = conn.prepareStatement("select name, descr, rev_id, pan_id, fname, lname, title, "+
      " affiliation from ldgrants.panels left join panel_reviewers on panel_reviewers.pan_id= "+
      " panels.id left join reviewers on reviewers.id= panel_reviewers.rev_id where fy_code=?");
      ps.setInt(1, fycode);            
      rs = ps.executeQuery();                    
                  
      while(rs.next())
      {
          SearchResultBean rb = new SearchResultBean();
          rb.setAffiliation(rs.getString("name"));
          rb.setDescription(rs.getString("descr"));
          rb.setRevid(rs.getLong("rev_id"));
          rb.setFname(rs.getString("fname"));
          rb.setLname(rs.getString("lname"));         
          allRevs.add(rb);
      }
            
    }catch(Exception e){
      System.out.println("error getPanelReviewersForFy "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(ps);
    }  
    return allRevs;
  }
  
  
  
  public ArrayList getPanelGrantsForFy(int fycode)
  {   
    ArrayList allGrants = new ArrayList();
    try{    
      conn = initializeConn();
      
      ps = conn.prepareStatement("select panels.name, pan_id, panel_grants.gra_id, fc_code, " + 
      " grants.fy_code, proj_seq_num, co_institutions.inst_id, popular_name, project_categories.descr from grants " + 
      " left join project_categories on grants.pc_id=project_categories.id, panel_grants " + 
      " left join ldgrants.panels on panels.id=panel_grants.pan_id, co_institutions " + 
      " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id where " + 
      " grants.id=panel_grants.gra_id and grants.id= co_institutions.gra_id and " + 
      " grants.fy_code=? and is_lead='Y'");
      ps.setInt(1, fycode);            
      rs = ps.executeQuery();                    
                  
      while(rs.next())
      {
          SearchResultBean rb = new SearchResultBean();
          rb.setAffiliation(rs.getString("name"));
          rb.setProjcategory(rs.getString("descr"));
          rb.setPanelid(rs.getLong("pan_id"));
          rb.setFccode(rs.getInt("fc_code"));
          rb.setYear(rs.getString("fy_code"));
          rb.setProjectNum(rs.getString("proj_seq_num"));
          rb.setInstitution(rs.getString("popular_name"));         
          allGrants.add(rb);
      }
            
    }catch(Exception e){
      System.out.println("error getPanelGrantsForFy "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(ps);
    }  
    return allGrants;
  }
  
  /**
     * This method used for lgrmif admin search page, to search grants by region. Will get both
     * submitted and unsubmitted projects.
     * @param fycode
     * @param regiontype
     * @return
     */
    public Vector getGrantsForRegionFy(int fycode, int regiontype)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select grants.id, name, fy_code, fc_code, proj_seq_num, description, " + 
        " initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id, descr from grants, "+
        " fiscal_years, sed_institutions, co_institutions, govt_infos left join region_types on "+
        " govt_infos.rt_id=region_types.id where grants.FY_CODE= fiscal_years.CODE and "+
        " sed_institutions.INST_ID= co_institutions.INST_ID and co_institutions.GRA_ID = grants.ID "+
        " and grants.id=govt_infos.gra_id and is_lead='Y' and fc_code=80 and grants.fy_code=? and "+
        " rt_id=? order by fc_code");
        ps.setInt(1, fycode);        
        ps.setInt(2, regiontype);
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setInstID(rs.getLong("inst_id"));
            gb.setFiscalyear(rs.getString("description"));//fiscal year
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setInstName(rs.getString("popular_name"));             
            gb.setProgram("LGRMIF");         
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getGrantsForRegionFy "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    /**
     * This method used for lgrmif report. Will get only submitted grants for region.
     * @param fycode
     * @param regiontype
     * @return
     */
    public Vector getGrantsSubmittedRegionFy(int fycode, int regiontype)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();   
        
        if(regiontype==0) {
            ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
            " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
            " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " +
            "region_types.descr as rt from GRANTS, project_categories, " + 
            " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
            " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
            " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
            " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id and "+
            " grants.FC_CODE=80 and grants.FY_CODE=? and IS_LEAD='Y' and GRANTS.ID in "+
            " (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial') order by rt");
            ps.setInt(1, fycode);        
        }else{
            ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
            " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
            " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " +
            "region_types.descr as rt from GRANTS, project_categories, " + 
            " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
            " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
            " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
            " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id and "+
            " grants.FC_CODE=80 and grants.FY_CODE=? and IS_LEAD='Y' and rt_id=? and GRANTS.ID in "+
            " (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial')");
            ps.setInt(1, fycode);        
            ps.setInt(2, regiontype);
        }
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));          
            gb.setProjcategory(rs.getString("pc"));
            gb.setTitle(rs.getString("rt"));
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getGrantsSubmittedRegionFy "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    public Vector getGrantSubmittedCounty(int fycode, int countytype)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
        " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
        " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pcat, " + 
        " region_types.descr as rtype from GRANTS, project_categories, govt_infos " + 
        " left join region_types on govt_infos.rt_id=region_types.id, " + 
        " LDGRANTS.BUDGETTOTALSVIEW, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID  " + 
        " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id  " + 
        " and grants.id=govt_infos.gra_id and grants.FC_CODE=80 and grants.FY_CODE=? and IS_LEAD='Y' " + 
        "  and county_code=? and GRANTS.ID in (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial')");
        ps.setInt(1, fycode);        
        ps.setInt(2, countytype);
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));          
            gb.setProjcategory(rs.getString("pcat"));
            gb.setRegion(rs.getString("rtype"));
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getGrantSubmittedCounty "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    /**
     * for lgrmif only, unless I make fccode a param.
     * @param fycode
     * @return
     */
    public Vector getFinalRptNotSubmitted(int fycode, int submitted)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();       
        String submitStr="";
        if(submitted==1)
            submitStr=" in ";
        else
            submitStr= " not in ";
            
        ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
        " PROJ_SEQ_NUM, doris_flag, doris_name, totappr, CO_INSTITUTIONS.INST_ID, " + 
        " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " + 
        " region_types.descr as rt from GRANTS, project_categories, " + 
        " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
        " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
        " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id " + 
        " and grants.FC_CODE=80 and grants.FY_CODE=? and IS_LEAD='Y' " + 
        " and GRANTS.ID in (select GRA_ID from APPROVALS where VERSION='Initial') and grants.id "+
        submitStr +" (select gra_id from grant_submissions where version='Final')");
        
        ps.setInt(1, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));          
            gb.setProjcategory(rs.getString("pc"));
            gb.setTitle(rs.getString("rt"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getFinalRptNotSubmitted "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    public Vector getCooperativeGrants(int fycode, int fccode)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
        " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
        " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " + 
        " region_types.descr as rt, ldgrants.F_GETCOOPERATIVES(grants.id) as coopinst from GRANTS, project_categories, " + 
        " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
        " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
        " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id " + 
        " and grants.FC_CODE=? and grants.FY_CODE=? and IS_LEAD='Y' and cooperative_yn=1 " + 
        " and grants.id in (select gra_id from grant_submissions where version='Initial')");
        ps.setInt(1, fccode);
        ps.setInt(2, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));          
            gb.setProjcategory(rs.getString("pc"));
            gb.setRegion(rs.getString("rt"));
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            gb.setCooperatives(rs.getString("coopinst"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getCooperativeGrants "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    public Vector getSharedServicesGrants(int fycode, int fccode)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
        " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
        " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " + 
        " region_types.descr as rt from GRANTS, project_categories, " + 
        " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
        " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
        " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id " + 
        " and grants.FC_CODE=? and grants.fy_code=? and IS_LEAD='Y' and shared_serv_yn=1 " + 
        " and grants.id in (select gra_id from grant_submissions where version='Initial')");
        ps.setInt(1, fccode);
        ps.setInt(2, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));          
            gb.setProjcategory(rs.getString("pc"));
            gb.setRegion(rs.getString("rt"));            
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getSharedServicesGrants "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    public Vector getIndividualTypeGrants(int fycode, int fccode)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
        " PROJ_SEQ_NUM, doris_flag, doris_name, totreq, totappr, CO_INSTITUTIONS.INST_ID, " + 
        " initcap(POPULAR_NAME) as popular_name, project_categories.descr as pc, " + 
        " region_types.descr as rt, cooperative_yn, shared_serv_yn from GRANTS, project_categories, " + 
        " LDGRANTS.BUDGETTOTALSVIEW, govt_infos, region_types, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
        " sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
        " and grants.pc_id=project_categories.id and LDGRANTS.BUDGETTOTALSVIEW.GRA_ID=grants.id " + 
        " and grants.id=govt_infos.gra_id and govt_infos.rt_id=region_types.id " + 
        " and grants.FC_CODE=? and grants.fy_code=? and IS_LEAD='Y' and " + 
        " (shared_serv_yn=0 or shared_serv_yn is null) " + 
        " and (cooperative_yn=0 or cooperative_yn is null)  " + 
        " and grants.id in (select gra_id from grant_submissions where version='Initial')");
        ps.setInt(1, fccode); 
        ps.setInt(2, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));     
            gb.setProjcategory(rs.getString("pc"));
            gb.setRegion(rs.getString("rt"));            
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            allGrants.add(gb);
        }
              
      }catch(Exception e){
        System.out.println("error getIndividualTypeGrants "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    public SearchResultBean getCountFullModifyNone(int fycode)
    {   
      SearchResultBean sb = new SearchResultBean();
      try{    
        conn = initializeConn();  
        
        ps = conn.prepareStatement("select count(case recommendation when 'F' then 1 else null end) as fullfund, " + 
        "count(case recommendation when 'M' then 1 else null end) as modifyfund, " + 
        "count(case recommendation when 'N' then 1 else null end) as nofund, fy_code " + 
        "from panel_grants, grants where grants.id=panel_grants.gra_id " + 
        "and fy_code=? group by fy_code");
        ps.setInt(1, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {            
            sb.setFycode(rs.getInt("fy_code"));
            sb.setFullfund(rs.getInt("fullfund"));
            sb.setModifyfund(rs.getInt("modifyfund"));
            sb.setNofund(rs.getInt("nofund"));            
        }
              
      }catch(Exception e){
        System.out.println("error getCountFullModifyNone "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return sb;
    }
    
    /**
     * used to populate lgrmif admin report for 'projects by panel/score'.
     * modified 4/7/11 to include projcategory and total amt req per FC.
     * @param fycode
     * @param panelid
     * @return
     */
    public Vector getLgScoreOrder(int fycode, long panelid)
    {   
      Vector results = new Vector();
      try{    
        conn = initializeConn();  
        
        ps = conn.prepareStatement("select grants.id, final_score, recommendation, bonus_points, "+
        "(case inventory_yn when '1' then 5 else 0 end) as inventory, " + 
        "(case email_mgmt_yn when '1' then 5 else 0 end) as emailmgmt, " + 
        "(case electronic_rcds_yn when '1' then 5 else 0 end) as elecrecords, " + 
        "(case cooperative_yn when '1' then 10 else 0 end) as coop, " + 
        "panels.name, panels.id as pan_id, grants.fc_code, grants.fy_code, proj_seq_num, " + 
        "project_categories.descr as projcat, totreq, totappr, " + 
        "initcap(popular_name) as popular_name, doris_flag, doris_name " + 
        "from govt_infos, grants left join project_categories on project_categories.id=grants.pc_id, " + 
        "panel_grants, ldgrants.panels, LDGRANTS.BUDGETTOTALSVIEW, co_institutions " + 
        "left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        "where govt_infos.gra_id=grants.id and panel_grants.gra_id=grants.id " + 
        "and panel_grants.pan_id=panels.id and grants.id=LDGRANTS.BUDGETTOTALSVIEW.gra_id " + 
        "and grants.id=co_institutions.gra_id and is_lead='Y' and grants.fy_code=? and panels.id=? " + 
        "order by final_score desc");
        ps.setInt(1, fycode);        
        ps.setLong(2, panelid);
        rs = ps.executeQuery();                        
        while(rs.next())
        {            
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));    
            gb.setRecommend(rs.getString("recommendation"));
            gb.setProjcategory(rs.getString("projcat"));
            gb.setTotamtreq(rs.getInt("totreq"));
            gb.setTotamtappr(rs.getInt("totappr"));
            gb.setDorisflag(rs.getBoolean("doris_flag"));
            if(gb.isDorisflag())
                gb.setInstName(rs.getString("doris_name"));
            else
                gb.setInstName(rs.getString("POPULAR_NAME"));
            gb.setTitle(rs.getString("name"));//panel name
            gb.setBonusScore(rs.getInt("bonus_points"));//new 11/12/15
            
            //3/8/11 removed bonus pts for any grants for FY 2011-12 and later
            if(gb.getFycode() <12){
                int bonus = rs.getInt("inventory") + rs.getInt("emailmgmt")+ rs.getInt("elecrecords")+
                        rs.getInt("coop");
                gb.setScore(rs.getInt("final_score")+ bonus);
            }
            else
                gb.setScore(rs.getInt("final_score") + gb.getBonusScore());
                
            results.add(gb);     
        }
              
      }catch(Exception e){
        System.out.println("error getLgScoreOrder "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return results;
    }
    
    
    public Vector getBudgetCategoryTotals(int fycode)
    {   
      Vector results = new Vector();
      try{    
        conn = initializeConn();  
        
        ps = conn.prepareStatement("select budget_code_totalsview.id, fc_code, "+
        " budget_code_totalsview.fy_code, proj_seq_num, proffreq, suppreq, equipreq, " + 
        " othexpreq, purchreq, bocesreq, supplyreq, travelreq, benefitreq, proffappr, suppappr, equipappr, " + 
        " otherexpappr, purchappr, bocesappr, supplyappr, travelappr, benefitappr, " + 
        " initcap(popular_name) as popular_name, name from ldgrants.budget_code_totalsview, "+
        " panel_grants, ldgrants.panels, co_institutions left join sed_institutions " + 
        " on co_institutions.inst_id=sed_institutions.inst_id where co_institutions.gra_id= "+
        " budget_code_totalsview.id and panel_grants.gra_id=budget_code_totalsview.id and " + 
        " panel_grants.pan_id=panels.id and is_lead='Y' and budget_code_totalsview.fy_code=? " + 
        " and budget_code_totalsview.id in (select gra_id from approvals where version='Initial' and  " + 
        " approval_type='Approve') order by popular_name");
        ps.setInt(1, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {         
            int totreq=0, totappr=0;
            TotalsBean tb = new TotalsBean();
            tb.setFycode(rs.getInt("fy_code"));
            tb.setFccode(rs.getInt("fc_code"));
            tb.setProjnum(rs.getInt("proj_seq_num"));
            tb.setInstName(rs.getString("popular_name"));
            tb.setPanel(rs.getString("name"));
            
            tb.setProffAmtReq(rs.getInt("proffreq"));
            tb.setSuppAmtReq(rs.getInt("suppreq"));
            tb.setEquipAmtReq(rs.getInt("equipreq"));
            tb.setOthAmtReq(rs.getInt("othexpreq"));
            tb.setPurchAmtReq(rs.getInt("purchreq"));
            tb.setBocesAmtReq(rs.getInt("bocesreq"));
            tb.setSupplyAmtReq(rs.getInt("supplyreq"));
            tb.setTravAmtReq(rs.getInt("travelreq"));
            tb.setBenAmtReq(rs.getInt("benefitreq"));
            tb.setProffAmtAppr(rs.getInt("proffappr"));
            tb.setSuppAmtAppr(rs.getInt("suppappr"));
            tb.setEquipAmtAppr(rs.getInt("equipappr"));
            tb.setOthAmtAppr(rs.getInt("otherexpappr"));
            tb.setPurchAmtAppr(rs.getInt("purchappr"));
            tb.setBocesAmtAppr(rs.getInt("bocesappr"));
            tb.setSupplyAmtAppr(rs.getInt("supplyappr"));
            tb.setTravAmtAppr(rs.getInt("travelappr"));
            tb.setBenAmtAppr(rs.getInt("benefitappr"));
            
            totreq= tb.getProffAmtReq() + tb.getSuppAmtReq() + tb.getEquipAmtReq() +
                tb.getOthAmtReq() + tb.getPurchAmtReq() + tb.getBocesAmtReq() + 
                tb.getSupplyAmtReq() + tb.getTravAmtReq() + tb.getBenAmtReq();
            
            totappr = tb.getProffAmtAppr() + tb.getSuppAmtAppr() + tb.getEquipAmtAppr() +
                tb.getOthAmtAppr() + tb.getPurchAmtAppr() + tb.getBocesAmtAppr() +
                tb.getSupplyAmtAppr() + tb.getTravAmtAppr() + tb.getBenAmtAppr();
                
            tb.setTotAmtReq(totreq);
            tb.setTotAmtAppr(totappr);
            results.add(tb);     
        }
              
      }catch(Exception e){
        System.out.println("error getBudgetCategoryTotals "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return results;
    }
    
        
    public Vector getDirectorMissingApps(int fycode, int fccode)
    {   
      Vector results = new Vector();
      try{    
        conn = initializeConn();          
        ps = conn.prepareStatement("select grants.id, fy_code, fc_code, proj_seq_num, " + 
        "co_institutions.inst_id, initcap(popular_name) as popular_name from grants, co_institutions " + 
        "left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        "where grants.id=co_institutions.gra_id and fc_code=? and is_lead='Y' and " + 
        "fy_code=? and grants.id not in (select gra_id from grant_admins " + 
        "where title='Library Director') order by popular_name");
        ps.setInt(1, fccode);
        ps.setInt(2, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {         
            GrantBean gb = new GrantBean();
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setInstName(rs.getString("popular_name"));
            gb.setInstID(rs.getLong("inst_id"));           
            results.add(gb);     
        }
              
      }catch(Exception e){
        System.out.println("error getDirectorMissingApps "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return results;
    }
    
    
    public Vector getBonusQuestions(int fycode, int fccode)
    {   
      Vector results = new Vector();
      try{    
        conn = initializeConn();          
        ps = conn.prepareStatement("select grants.id, fy_code, fc_code, proj_seq_num, " + 
        "co_institutions.inst_id, initcap(popular_name) as popular_name, " + 
        "cooperative_yn, electronic_rcds_yn, email_mgmt_yn, inventory_yn, " + 
        "fname, lname from grants, project_managers, co_institutions " + 
        "left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        "left join govt_infos on co_institutions.gra_id=govt_infos.gra_id " + 
        "where grants.id=co_institutions.gra_id and " + 
        "grants.prm_id=project_managers.id and fc_code=? and is_lead='Y' and  " + 
        "fy_code=? and grants.id in (select gra_id from grant_submissions " + 
        "where version='Initial') order by popular_name");
        ps.setInt(1, fccode);
        ps.setInt(2, fycode);        
        rs = ps.executeQuery();                        
        while(rs.next())
        {         
            GrantBean gb = new GrantBean();
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setInstName(rs.getString("popular_name"));
            gb.setCooperative(rs.getBoolean("cooperative_yn"));
            gb.setElectronicrecords(rs.getBoolean("electronic_rcds_yn"));
            gb.setEmailmgmt(rs.getBoolean("email_mgmt_yn"));
            gb.setInventory(rs.getBoolean("inventory_yn"));
            OfficerBean ob = new OfficerBean();
            ob.setFname(rs.getString("fname"));
            ob.setLname(rs.getString("lname"));
            gb.setProjectManager(ob);
            results.add(gb);     
        }
              
      }catch(Exception e){
        System.out.println("error getBonusQuestions "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return results;
    }
    
    //modified 9/16/15 to get panels.name
    public Vector getRevRecommendForPanel(long panelId)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();
        
        ps = conn.prepareStatement("select grants.id, grants.fc_code, grants.fy_code, proj_seq_num, " + 
        "         initcap(popular_name) as popular_name, panel_grants.id as pgid, pc.descr, " + 
        "         pra.recommendation, pra.recommend_amt, pra.bonus_points, rating_complete, pra.id as pra_id, " + 
        "         fname, lname, rev_id, totreq, (case inventory_yn when '1' then 5 else 0 end) as inventory, " + 
        "         (case email_mgmt_yn when '1' then 5 else 0 end) as emailmgmt, " + 
        "         (case electronic_rcds_yn when '1' then 5 else 0 end) as elecrecords, " + 
        "         (case cooperative_yn when '1' then 10 else 0 end) as coop, panels.name as panelName " + 
        "         from grants, project_categories pc, panel_grants " + 
        "         left join ldgrants.panels on panels.id = panel_grants.pan_id, " + 
        "         panel_reviewer_assigns pra, " + 
        "         ldgrants.panelsreviewersview, ldgrants.budgettotalsview, govt_infos, co_institutions " + 
        "         left join sed_institutions on sed_institutions.inst_id= co_institutions.inst_id          " + 
        "         where grants.id=co_institutions.gra_id and  grants.pc_id= pc.id and  " + 
        "         grants.id=panel_grants.gra_id and grants.id=ldgrants.budgettotalsview.gra_id  " + 
        "         and panel_grants.id=pra.pg_id and panelsreviewersview.id=pra.pr_id  " + 
        "         and grants.id=govt_infos.gra_id and is_lead='Y' and  panel_grants.pan_id=? order by descr, grants.id, rev_id");
        ps.setLong(1, panelId);            
        rs = ps.executeQuery();                    
                    
        while(rs.next())
        {
            ReviewerAssignBean rab = new ReviewerAssignBean();
            rab.setGraid(rs.getLong("id"));
            rab.setFccode(rs.getInt("fc_code"));
            rab.setFycode(rs.getInt("fy_code"));
            rab.setProjseqnum(rs.getLong("proj_seq_num"));
            rab.setInstname(rs.getString("popular_name"));
            rab.setPanelGrantId(rs.getLong("pgid"));
            rab.setRecommendation(rs.getString("recommendation"));
            rab.setRecommendamt(rs.getInt("recommend_amt"));
            rab.setRatingcomp(rs.getBoolean("rating_complete"));
            rab.setAssignid(rs.getLong("pra_id"));
            rab.setName(rs.getString("fname") + " " +rs.getString("lname"));
            rab.setRevid(rs.getLong("rev_id"));     
            rab.setTotamtreq(rs.getInt("totreq"));
            rab.setProjcategory(rs.getString("descr"));
            rab.setTitle(rs.getString("panelName"));
            
            rab.setBonuspts(rs.getInt("bonus_points"));//new 11/12/15
            
            //3/12/11 bonus pts are for FY 10-11 and earlier
            if(rab.getFycode() <12){
                int bonuspt=rs.getInt("inventory") + rs.getInt("emailmgmt") + rs.getInt("coop") 
                    + rs.getInt("elecrecords");
                rab.setBonuspts(bonuspt);
            }
            allGrants.add(rab);
        }
              
      }catch(Exception e){
        System.out.println("error getRevRecommendForPanel "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
   
    public Vector getLgRevScoresForPanel(Vector allGrants)
    {         
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from reviewer_ratings where pra_id=?");
        
        for(int i=0; i<allGrants.size(); i++)
        {
            int sumscore=0;
            ReviewerAssignBean r = (ReviewerAssignBean) allGrants.get(i);
            ps.setLong(1, r.getAssignid());
            rs = ps.executeQuery();       
                   // System.out.println("assignid "+r.getAssignid());
            while(rs.next()){
                sumscore += rs.getInt("score"); 
                switch(rs.getInt("rat_id"))
                {
                  case 36:  
                  case 47:
                  case 52:
                    sumscore += (rs.getInt("SCORE")*2);//weighted *3
                    break;
                  case 42:
                      if(r.getFycode()<16)
                        sumscore += rs.getInt("SCORE");//weighted * 2; only for prior to fy2015-16
                      break;
                  case 48:
                      if(r.getFycode()>15)//weighted * 3 for 2015-16
                        sumscore += (rs.getInt("SCORE")*2);//weighted *3
                      else
                        sumscore += rs.getInt("SCORE");//weighted * 2
                      break;
                  case 54:
                      sumscore += (rs.getInt("score")*4);//weighted *5
                      break;
                  case 43:
                  case 46:
                      if(r.getFycode()>14)
                          sumscore += rs.getInt("SCORE");//weighted *2 starting FY2014-15
                      break;
                  case 45:
                      if(r.getFycode()>15)
                        sumscore += rs.getInt("SCORE");//weighted * 2 for 2015-16
                  default:
                    break;
                }//end switch                           
            }//end for loop
            //System.out.println("bonus "+r.getBonuspts());
            sumscore += r.getBonuspts();
            r.setScore(String.valueOf(sumscore));     
        }
              
      }catch(Exception e){
        System.out.println("error getLgRevScoresForPanel "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    
    
  /**
     * added on 9/15/15. This parses thru list of individual reviewer scores, calculates average score from 
     * all reviewers for grant_id.  New list contains only 1 row per grant_id. 
     * @param allGrants
     * @return
     */
  public List parseReviewerScoresToAverage(Vector allGrants)
  {         
    List<ReviewerAssignBean> scores = new ArrayList<ReviewerAssignBean>();
    try{    
      
      long currProjNum=0;
      ReviewerAssignBean rb = new ReviewerAssignBean();
        
      int numRatings=0;
      int sumScores=0;
      
        
      //loop on all rows in original list; list should be sorted by proj_seq_num
      for(int i=0; i<allGrants.size(); i++)
      {
          //get bean from list
          ReviewerAssignBean r = (ReviewerAssignBean) allGrants.get(i);
          
          long queryProjNum= r.getProjseqnum();
                    
          
          //if first record from list
          if(i==0){           
            //assign all fields from list bean to new bean (so don't have to do individually)
            rb = r;
                                         
            currProjNum = queryProjNum;
          }
          else if((i+1) == allGrants.size()){//if last record in list; add to new list
          	
        	  if(r.isRatingcomp()){//if rating complete
                  
                  //increment # ratings
                  numRatings++;
                  
                  //add this reviewer score to total for this proj_num
                  sumScores = sumScores + Integer.parseInt(r.getScore());            
                }
        	  
        	  //figure out the average score of all ratings for previous proj_num          
              if(numRatings>0){
                
                int avgScore = sumScores/numRatings;
                
                rb.setScore( String.valueOf(avgScore));                
              }
              //add previous proj_num/bean to new list
              scores.add(rb);
                      	  
          }
          else if(queryProjNum != currProjNum){//new proj_num record
              
              //figure out the average score of all ratings for previous proj_num          
              if(numRatings>0){
                
                int avgScore = sumScores/numRatings;
                
                rb.setScore( String.valueOf(avgScore));                
              }
              //add previous proj_num/bean to new list
              scores.add(rb);
                            
              //set up new bean record for new proj_num
              rb = r;
              numRatings =0;
              sumScores = 0;
              currProjNum = queryProjNum; 
          }
          else{
              //only other option is record has same proj_num; just new reviewer score
              currProjNum = queryProjNum;  
          }
                           
          
          if(r.isRatingcomp()){//if rating complete
            
            //increment # ratings
            numRatings++;
            
            //add this reviewer score to total for this proj_num
            sumScores = sumScores + Integer.parseInt(r.getScore());            
          }
          
      }//end for loop
            
    }catch(Exception e){
      System.out.println("error parseReviewerScoresToAverage "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(ps);
    }  
    return scores;
  }
    
    
    
    
    
    //SH 8/25/14 modified weights for fy2015-16 grant scoring
    public ArrayList getLgRevScoresForGrant(ArrayList allGrants, int bonuspts, int fycode)
    {         
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select * from reviewer_ratings where pra_id=?");
        
        int overallsum =0;//overallsum is used to calc avg of all rev scores
        for(int i=0; i<allGrants.size(); i++)
        {
            int sumscore=0;
            RatingBean r = (RatingBean) allGrants.get(i);
            ps.setLong(1, r.getGrantassign());
            rs = ps.executeQuery();       
            
            while(rs.next()){
                sumscore += rs.getInt("score"); 
                switch(rs.getInt("rat_id"))
                {                                   
                  case 36:  
                  case 47:
                  case 52:
                    sumscore += (rs.getInt("SCORE")*2);//weighted *3
                    break;
                  case 42:
                      if(fycode<16)
                        sumscore += rs.getInt("SCORE");//weighted * 2; only for prior to fy2015-16
                      break;
                  case 48:
                      if(fycode>15)//weighted * 3 for 2015-16
                        sumscore += (rs.getInt("SCORE")*2);//weighted *3
                      else
                        sumscore += rs.getInt("SCORE");//weighted * 2
                      break;
                  case 54:
                      sumscore += (rs.getInt("score")*4);//weighted *5
                      break;
                  case 43:
                  case 46:
                      if(fycode>14)
                          sumscore += rs.getInt("SCORE");//weighted *2 starting FY2014-15
                      break;
                  case 45:
                      if(fycode>15)
                        sumscore += rs.getInt("SCORE");//weighted * 2 for 2015-16
                  default:
                    break;
                }//end switch                           
            }//end while                        
            sumscore += bonuspts;//add in the bonus pts prior to 2011
            sumscore += r.getBonusScore();//new 11/12/15 bonus points starting 2016-17
            r.setScore(sumscore);     
            overallsum+= sumscore;
            //System.out.println("sumscore "+sumscore);
            //System.out.println("bonus "+bonuspts);
                
        }
        
        //divide overallsum by the #of reviews to get avg score
        int avgscore=0;
        if(overallsum>0){
            avgscore =overallsum/allGrants.size();            
        }
        //System.out.println("avscore" +avgscore);
        for(int j=0; j<allGrants.size(); j++){
            RatingBean r = (RatingBean) allGrants.get(j);
            r.setAvgscore(avgscore);
        }
                     
      }catch(Exception e){
        System.out.println("error getLgRevScoresForGrant "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    public Vector getDecisionByRecomm(int fycode, String fund)
    {   
      Vector allGrants = new Vector();
      try{    
        conn = initializeConn();
        
        ps = conn.prepareStatement("select grants.id, grants.fc_code, grants.fy_code, " + 
        "    proj_seq_num, co_institutions.inst_id, initcap(popular_name) as popular_name, " + 
        "    panel_grants.id as pgid, recommendation, final_score, recommend_amt, totreq,  " + 
        "    decision_notes, pm.fname, pm.lname, grants.prm_id, doris_name, doris_flag, " + 
        "    contact_value, " + 
        "    gs.fname as gs_fname, gs.lname as gs_lname, gs.id as gs_id " + 
        "        from grants, project_managers pm  " + 
        "    left join contacts on pm.id=contacts.prm_id,  " + 
        "    panel_grants, grant_staffs gs, ldgrants.budgettotalsview,  co_institutions left join sed_institutions on  " + 
        "    sed_institutions.inst_id= co_institutions.inst_id where grants.id=co_institutions.gra_id and " + 
        "    grants.id=panel_grants.gra_id and grants.prm_id=pm.id and gs.gra_id=grants.id  " + 
        "    and grants.id=ldgrants.budgettotalsview.gra_id  " + 
        "    and is_lead='Y' and grants.fy_code=? and recommendation=? and grants.fc_code=80 and st1_id=1 " + 
        "    and contact_type_code=3 order by popular_name");
        ps.setInt(1, fycode);  
        ps.setString(2, fund);
        rs = ps.executeQuery();                    
                    
        while(rs.next())
        {
            ReviewerAssignBean rab = new ReviewerAssignBean();
            rab.setGraid(rs.getLong("id"));
            rab.setFccode(rs.getInt("fc_code"));
            rab.setFycode(rs.getInt("fy_code"));
            rab.setProjseqnum(rs.getLong("proj_seq_num"));
            rab.setInstname(rs.getString("popular_name"));
            rab.setInstid(rs.getLong("inst_id"));
            rab.setPanelGrantId(rs.getLong("pgid"));
            rab.setRecommendation(rs.getString("recommendation"));
            rab.setDecisionNotes(rs.getString("decision_notes"));
                      
            rab.setName(rs.getString("fname") + " " +rs.getString("lname"));
            rab.setRevid(rs.getLong("prm_id"));
            rab.setEmail(rs.getString("contact_value"));
            
            rab.setRmoName(rs.getString("gs_fname") +" " + rs.getString("gs_lname"));
            rab.setRmoId(rs.getLong("gs_id"));
            rab.setTotamtreq(rs.getInt("totreq"));
            rab.setRecommendamt(rs.getInt("recommend_amt"));
            rab.setScore(rs.getString("final_score"));
            
            boolean isDoris = rs.getBoolean("doris_flag");
            if(isDoris)
                rab.setInstname(rab.getInstname() + " - "  +rs.getString("doris_name"));
            allGrants.add(rab);
        }
              
      }catch(Exception e){
        System.out.println("error getDecisionByRecomm "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    
    public Vector getRmoEmailForGrants(Vector allGrants)
    {   
      //Vector allrecs = new Vector();
      try{    
        conn = initializeConn();        
        ps = conn.prepareStatement("select contact_value from contacts where gs_id=? and contact_type_code=3");
         
        for(int i=0; i<allGrants.size(); i++){
          ReviewerAssignBean rab= (ReviewerAssignBean) allGrants.get(i);
               
            ps.setLong(1, rab.getRmoId()); 
            rs = ps.executeQuery();                    
                        
            while(rs.next()){            
                rab.setRmoEmail(rs.getString("contact_value"));
            }
        }
              
      }catch(Exception e){
        System.out.println("error getRmoEmailForGrants "+e.getMessage().toString());
      }
      finally {
        Close(conn);
        Close(rs);
        Close(ps);
      }  
      return allGrants;
    }
    
    
    
  public Vector getStatisticsRptFinalSubmit(int fycode)
  {   
    Vector allGrants = new Vector();
    try{    
      conn = initializeConn();       
      //get final apps submitted          
      ps = conn.prepareStatement("select distinct GRANTS.ID, grants.FC_CODE, grants.FY_CODE, " + 
      " PROJ_SEQ_NUM, doris_flag, doris_name,  CO_INSTITUTIONS.INST_ID, " + 
      " initcap(POPULAR_NAME) as popular_name " + 
      " from GRANTS, CO_INSTITUTIONS left join SED_INSTITUTIONS on " + 
      " sed_institutions.inst_id=co_institutions.inst_id " + 
      " where GRANTS.ID = CO_INSTITUTIONS.GRA_ID " + 
      " and grants.FC_CODE=80 and grants.FY_CODE=? and IS_LEAD='Y' " + 
      " and GRANTS.ID in (select GRA_ID from APPROVALS where VERSION='Initial') and grants.id " + 
      " in (select gra_id from grant_submissions where version='Final')");
      
      ps.setInt(1, fycode);        
      rs = ps.executeQuery();                        
      while(rs.next())
      {
          StatisticBean sb = new StatisticBean();
          sb.setGrantid(rs.getLong("id"));
          sb.setFycode(rs.getInt("fy_code"));
          sb.setFccode(rs.getInt("fc_code"));
          sb.setProjseqnum(rs.getLong("proj_seq_num"));          
          boolean isdoris = rs.getBoolean("doris_flag");
          if(isdoris)
              sb.setInstName(rs.getString("doris_name"));
          else
              sb.setInstName(rs.getString("POPULAR_NAME"));
          allGrants.add(sb);
      }
        
        
      ps.clearParameters();
      //get all statistics
      ps = conn.prepareStatement("select * from PROJECT_STATISTICS, STATISTIC_TYPES where "+
        " GRA_ID=? and PROJECT_STATISTICS.ST_ID=STATISTIC_TYPES.ID ");
      
      for(int i=0; i<allGrants.size(); i++){
      
        StatisticBean sb = (StatisticBean) allGrants.get(i);
        ps.setLong(1, sb.getGrantid());
        rs = ps.executeQuery();
          
        while(rs.next())
        {
          int typeid = rs.getInt("ST_ID");
                 
          switch(typeid)
          {
            case 15://START of LGRMIF final statistics
               sb.setInventory(rs.getInt("SCORE"));
               sb.setInventoryStr(rs.getString("SCORE"));
               break;  
            case 16:
               sb.setDestroy(rs.getInt("SCORE"));
               sb.setDestroyStr(rs.getString("SCORE"));
               break;  
            case 17:
               sb.setScan(rs.getInt("SCORE"));
               sb.setScanStr(rs.getString("SCORE"));
               break;  
            case 18:
               sb.setMicrofilm(rs.getInt("SCORE"));
               sb.setMicrofilmStr(rs.getString("SCORE"));
               break;  
            case 19:
               sb.setDestroymicrofilm(rs.getInt("SCORE"));
               sb.setDestroymicrofilmStr(rs.getString("SCORE"));
               break;  
            case 20:
               sb.setDestroyscan(rs.getInt("SCORE"));
               sb.setDestroyscanStr(rs.getString("SCORE"));
               break;  
            case 21:
               sb.setRecordsarranged(rs.getInt("SCORE"));
               sb.setRecordsarrangedStr(rs.getString("SCORE"));
               break;  
            case 22:
               sb.setRecordsdescribed(rs.getInt("SCORE"));
               sb.setRecordsdescribedStr(rs.getString("SCORE"));
               break;  
            case 23:
               sb.setInactiverecords(rs.getInt("SCORE"));
               sb.setInactiverecordsStr(rs.getString("SCORE"));
               break;  
            case 24:
               sb.setRecordsconserved(rs.getInt("SCORE"));
               sb.setRecordsconservedStr(rs.getString("SCORE"));
               break;  
            case 25:
               sb.setHours(rs.getInt("SCORE"));
               sb.setHoursStr(rs.getString("SCORE"));
               break;  
            case 26:
               sb.setImage(rs.getInt("SCORE"));
               sb.setImageStr(rs.getString("SCORE"));
               break;  
            case 27:
               sb.setOnline(rs.getInt("SCORE"));
               sb.setOnlineStr(rs.getString("SCORE"));
               break;  
            case 28:
               sb.setSeriesdescrip(rs.getInt("SCORE"));
               sb.setSeriesdescripStr(rs.getString("SCORE"));
               break;  
            case 29:
               sb.setOther(rs.getInt("SCORE"));
               sb.setOtherStr(rs.getString("SCORE"));
               sb.setOtherExplained(rs.getString("REASON"));
               break;          //end of LGRMIF final statistics
          }
        }
            
      }      
        
            
    }catch(Exception e){
      System.out.println("error getStatisticsRptFinalSubmit "+e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(ps);
    }  
    return allGrants;
  }
  
  
  
  
  
  /**
     * added 2/5/16 per KBALSEN. this report gets all apps for fy and fund (AL/FL), with abstract narrative 
     * and total edlaw amount for 3 years (from system_fiscalyear_details table)
     * @param fycode
     * @param fccode
     * @return
     */
  public ArrayList<GrantBean> getLitAppEdLawAbstract(int fycode, int fccode)
  {
    ArrayList<GrantBean> results = new ArrayList(); 
   
    try {
       conn = initializeConn();              
      prestmt = conn.prepareStatement("select g.id as grantId, g.fy_code, g.fc_code, proj_seq_num, name, i.inst_id, initcap(popular_name) as popular_name, " + 
      "          narrative_descr, lsm_id, initial_allocation, final_allocation, final_recommendation  " + 
      "          from grants g, co_institutions i   " + 
      "          left join sed_institutions si on i.inst_id=si.inst_id,  " + 
      "          project_narratives pn, system_fiscalyear_details sfd, ldstateaid.library_system_mappings lsm  " + 
      "          where g.fy_code=? and g.fc_code=?  " + 
      "          and g.id = i.gra_id   " + 
      "          and g.id = pn.gra_id and pn.nat_id=1   " + 
      "          and sfd.lsm_id = lsm.id   " + 
      "          and lsm.inst_id= i.inst_id and lsm.inst_id=lsm.inst_id_has  " + 
      "          and sfd.fc_code=g.fc_code  " + 
      "          and sfd.fy_code=g.fy_code  " + 
      "          and lsm.end_date is null and i.is_lead='Y'  " + 
      "          order by popular_name");
      prestmt.setInt(1, fycode);
      prestmt.setInt(2, fccode);
      rs = prestmt.executeQuery();      
      
      while(rs.next()){
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("grantId"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setTitle(rs.getString("name"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setInstName(rs.getString("popular_name"));
        gb.setSummaryDescr(rs.getString("narrative_descr"));
        gb.setTotalRecommend(rs.getLong("initial_allocation"));
        gb.setTotalRecommend3Year(gb.getTotalRecommend() *3);
       
        results.add(gb);
      }      
             
    } catch (Exception ex){
        System.err.println("error getLitAppEdLawAbstract()  " + ex.toString());
    }
    finally {
      Close(conn);
      Close(rs);
      Close(prestmt);
    }    
    return results;
  }
  
}