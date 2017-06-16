package mypackage;

import construction.CnApplicationBean;

import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import oracle.jdbc.OracleDriver;

public class SubmissionDBbean 
{
  public SubmissionDBbean()
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

  
  public Vector getSubmissions(long grantid)
  {
    Vector results = new Vector();          
    try{      
        conn = initializeConn();
        
        ps = conn.prepareStatement("select * from GRANT_SUBMISSIONS where GRA_ID=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          SubmitBean sb = new SubmitBean();
          sb.setGrantid(rs.getLong("GRA_ID"));
          sb.setDateSubmitted(rs.getDate("DATE_SUBMITTED"));
          sb.setUserSubmitted(rs.getString("GRAS_USER"));
          sb.setVersionSubmitted(rs.getString("VERSION"));
          
          results.add(sb);          
        }        
        
      }catch(Exception e){
        System.err.println("error getSubmissions() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(rs);
        Close(conn);
      } 
      return results;
  }
  
  
  public int submitInitialApp(long grantid, UserBean userb, int fccode)
  {
    int outcome =0;      
      try{
        conn = initializeConn();
        
        String updatestr="";
        if(fccode==86){
            updatestr="update GRANTS set COVERSHEET_COMP =1, DESCRIPTION_COMP =1, "+
            " BUDGET_COMP=1, AUTH_COMP=1, FS20_COMP=1, LOCK_APP=1,   "+
            " MICROFORM_YN=1, RELIGIOUS_AFILL=1, ATTACH_COMP=1, SHPO_COMP=1, SEAF_COMP=1, "+
            " PAYEE_COMP=1, PHOTO_COMP=1, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?";
        }
        else{
            updatestr="update GRANTS set COVERSHEET_COMP =1, DESCRIPTION_COMP =1, "+
            " BUDGET_COMP=1, AUTH_COMP=1, FS20_COMP=1, LOCK_APP=1,   "+
            " DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?";
        }
        ps = conn.prepareStatement(updatestr);
        ps.setString(1, userb.getUserid());
        ps.setLong(2, grantid);           
        outcome = ps.executeUpdate();        
        
        Close(ps);       
              
        //insert record into grant_submissions table for the new submission
        ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
          " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
          " ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());
        ps.setString(3, "Initial");
        ps.setString(4, userb.getUserid());
        outcome = ps.executeUpdate();       
      }
      catch(Exception e){
        System.err.println("error submitInitialApp() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }            
      return outcome;  
  }
  
  
  public int submitFinalApp(long grantid, UserBean userb, boolean multiYear, int grantYear)
  {
    int outcome =0;            
      try{
        conn = initializeConn();
        
        //update completed entries in the grants table
        String updatestr="update GRANTS set FINAL_NARR_COMP =1, FINAL_BUDGET_COMP =1, "+
              " SIGNOFF_COMP=1, LOCK_APP=1, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?";
        ps = conn.prepareStatement(updatestr);
        ps.setString(1, userb.getUserid());
        ps.setLong(2, grantid);        
        outcome = ps.executeUpdate();
               
        Close(ps);
        
         //update submission info in the Grant_submissions table
        ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
          " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
          " ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());
        if(multiYear){
            if(grantYear==2)
                ps.setString(3, "Final Year2");
            else
                ps.setString(3, "Final Year3");
        }
        else
            ps.setString(3, "Final");
        ps.setString(4, userb.getUserid());
        outcome = ps.executeUpdate();        
      }
      catch(Exception e){
        System.err.println("error submitFinalApp() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }                 
      return outcome;      
  }
    
    
  /**
     * 6/23/15 for construction; separate section to submit mwbe
     * @param grantid
     * @param userb
     * @return
     */
  public int submitMwbe(long grantid, UserBean userb)
  {
    int outcome =0;            
      try{
        conn = initializeConn();
       
         //insert submission info in the Grant_submissions table
        ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
          " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
          " ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());
        ps.setString(3, "MWBE");
        ps.setString(4, userb.getUserid());
        outcome = ps.executeUpdate();        
      }
      catch(Exception e){
        System.err.println("error submitMwbe() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }                 
      return outcome;      
  }
    
    
    
  
    public int submitToDasny(long grantid, UserBean userb)
    {
      int outcome =0;            
        try{
          conn = initializeConn();
         
           //insert submission info in the Grant_submissions table
          ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
            " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
            " ?, ?, SYSDATE, ?) ");
          ps.setLong(1, grantid);
          ps.setString(2, userb.getUserid());
          ps.setString(3, "DASNY");
          ps.setString(4, userb.getUserid());
          outcome = ps.executeUpdate();        
        }
        catch(Exception e){
          System.err.println("error submitToDasny() " + e.getMessage().toString());
        }
        finally{
          Close(conn);
          Close(ps);
          Close(rs);
        }                 
        return outcome;      
    }
    
    
    public int deleteDasnySubmission(long grantid)
    {
      int outcome =0;            
        try{
          conn = initializeConn();
         
           //insert submission info in the Grant_submissions table
          ps = conn.prepareStatement("delete from GRANT_SUBMISSIONS where gra_id=? "+
          " and version='DASNY'");
          ps.setLong(1, grantid);
          outcome = ps.executeUpdate();        
        }
        catch(Exception e){
          System.err.println("error deleteDasnySubmission() " + e.getMessage().toString());
        }
        finally{
          Close(conn);
          Close(ps);
          Close(rs);
        }                 
        return outcome;      
    }
    
    public boolean isDasnySubmitted(long grantid)
    {
         boolean appsubmit = false;         
         try{
           conn = initializeConn();      
           ps = conn.prepareStatement("select * from GRANT_SUBMISSIONS where GRA_ID = ? and "+
                     " VERSION = ?");                
           ps.setLong(1, grantid);
           ps.setString(2, "DASNY");
           rs = ps.executeQuery();
           while(rs.next()){
             //if a record is returned by query - then dasny-submit record exists
             appsubmit = true;
           }
         }catch(Exception e){
           System.err.println("error isDasnySubmitted() " + e.getMessage().toString());
         }
         finally{
           Close(conn);
           Close(ps);
           Close(rs);
         }   
         return appsubmit;
    }

  public Vector checkMissingNarratives(long grantid, int fccode)
  {
    Vector missingNarr = new Vector();
    String narrIds = "";
    try{
    
      switch(fccode)
      {
        case 5://discretionary
          narrIds = "1,19,20,3,16,21,22,23,24,10,25,6,7,12,13,15,17,18";
          break;
        case 7://coordinated
          narrIds = "1,6,7,8,9,10,11,12,13,14,15,16,17,18";
          break;
        case 80://lgrmif
          narrIds = "69,70,72,73,74,75,77,78";//8/12/14 rmved ID=76 for new 2015-16 narratives
          break;
        case 40:
          narrIds= "1,26,28,3,12,29,31,32,36,38,39,40,107";//starting fy 2016-19, and only AL requires 107 coord partner
          break;
        case 42://fl and al
          //narrIds = "1,3,5,8,12,26,27,28,29,30,31,32,33,34,36";9/25/12 -used for FY<14
          // narrIds = "1,3,12,26,27,28,29,30,31,32,36";//starting FY2013-14
          narrIds= "1,26,28,3,12,29,31,32,36,38,39,40";//starting fy 2016-19
          break;
        case 86://CONSTRUCTION
            narrIds = "95,94,12,91,93";//removed narrative #92 on 5/2/11
            break;
        case 20://stateaid
            narrIds = "3,93";//descr of activities and budget narrative
            break;
      }
      
      //get all narr types that do not have nar record for this grant
      conn = initializeConn();
      ps = conn.prepareStatement("Select id,narrative_name from narrative_types where id in "+
      " ("+ narrIds +") and id not in (select nat_id from project_narratives where gra_id=?) order by id");
      ps.setLong(1, grantid);
      
      rs = ps.executeQuery();
      while(rs.next()){
        missingNarr.add(rs.getString("narrative_name"));
      }
      
    }catch(Exception e){
      System.out.println("Error checkMissingNarratives "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return missingNarr;
  }  
  
  
  public Vector checkLitParticipantNarr(long grantid)
  {
    Vector missingNarr = new Vector();
    try{    
      //for lit FY starting 13-14, check for missing participant narrative
      conn = initializeConn();
      ps = conn.prepareStatement("Select id, narrative_name from narrative_types where id=107 "+
      "and id not in (select nat_id from project_narratives where gra_id=?)");
       ps.setLong(1, grantid);
      
      rs = ps.executeQuery();
      while(rs.next()){
        missingNarr.add(rs.getString("narrative_name"));
      }
      
    }catch(Exception e){
      System.out.println("Error checkLitParticipantNarr "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }   
    return missingNarr;
  }  
  
  
  /**
     * validation for submitting construct grant. 5 required attachments, 3 conditional
     * attachments. if attach is missing, an error is added to error vector.
     * @param allAttach
     * @param cab
     * @return
     */
  public Vector checkMissingCnAttach(Vector allAttach, CnApplicationBean cab)
  {
    HashMap attachMap = new HashMap();//for searching all attached docs
    Vector allerrors = new Vector();
    
    try{        
        //put all attach descriptions in hashmap for easier searching
        for(int i=0; i<allAttach.size(); i++){
            UploadDocBean ub = (UploadDocBean)allAttach.get(i);
            attachMap.put( ub.description, "true");
        }
        
        //all apcnts must have photos, smart growth, seaf, assurances, cert funds
        if(!attachMap.containsKey("Proof of Available Funds"))
            allerrors.add("Proof/Certificate of Available Funds must be electronically attached.");
        if(!attachMap.containsKey("Pre Project Photo"))
            allerrors.add("Pre Project Photos must be electronically attached.");
        if(!attachMap.containsKey("Smart Growth"))
            allerrors.add("Smart Growth Form must be electronically attached.");
        if(!attachMap.containsKey("SEAF"))
            allerrors.add("SEAF (Environmental Assessment) Form must be electronically attached.");
        if(!attachMap.containsKey("Assurances"))
            allerrors.add("Assurances must be electronically attached.");
        
        //OFP needed if 'owned by school' AND 'project over 10k'
        if(cab.isSchoolOwned() && cab.isOverCost()){
            
            if(!attachMap.containsKey("OFP"))
                allerrors.add("OFP Approval must be electronically attached if: the library building "+
                "is owned by a school district AND cost of project is over $10,000.");
        }
        
       /* 6/26/13 removed validation on shpo per MLT
        * //SHPO needed if 'in historic district' OR 'over 50 yrs old'
        if(cab.isHistoricDist() || cab.isOver50()){
            
            if(!attachMap.containsKey("SHPO"))
                allerrors.add("SHPO Approval must be electronically attached if: the library is in "+
                "a historic district OR the building is over 50 years old.");
        }*/
        
        //10 yr minimum lease needed if 'not school owned' and ('building is leased' or 'site is leased')
        if(!cab.isSchoolOwned() && (cab.getLibOwned()!=1 || cab.getSiteOwned()!=1) ){
            
            if(!attachMap.containsKey("10 Year Lease"))
                allerrors.add("Certificate of 10 Year Minimum Lease must be electronically attached if: "+
                "the library building or site is under a lease arrangement or otherwise legally available.");
        }        
        
    }catch(Exception e){
      System.out.println("error checkMissingCnAttach: "+e.getMessage().toString());
    }
    return allerrors;    
  }
  
  
    public Vector checkMissingCnFinalAttach(Vector allAttach)
    {
      HashMap attachMap = new HashMap();//for searching all attached docs
      Vector allerrors = new Vector();
      
      try{        
          //put all attach descriptions in hashmap for easier searching
          for(int i=0; i<allAttach.size(); i++){
              UploadDocBean ub = (UploadDocBean)allAttach.get(i);
              attachMap.put( ub.description, "true");
          }
          
          //all apcnts must have final photos, completion form
          if(!attachMap.containsKey("Post Project Photo"))
              allerrors.add("Post Project Photos of building project must be electronically attached.");
          
          /*6/4/12 completion form will be mailed in after ld approval
           * if(!attachMap.containsKey("Completion Form"))
              allerrors.add("Project Completion Form must be electronically attached.");
           */       
      }catch(Exception e){
        System.out.println("error checkMissingCnFinalAttach: "+e.getMessage().toString());
      }
      return allerrors;    
    }
  
  
      /**
     * 3/30/11 new method for al/fl verify final narr submit. check for all
     * final year1 narrs or final year2 narrs
     * @param grantid
     * @param fyear
     * @return
     */
    public Vector checkMissingLitFinalNarr(long grantid, int fyear, int fccode)
    {
      Vector missingNarr = new Vector();
      String narrIds = "";
      try{
      
        switch(fyear) {
          case 1://lit final year 1
            if(fccode==40)
              narrIds = "41,42,43,44,45,46,47,48,49,50,51";
            else
              narrIds = "41,44,45,46,48,49,50,51,111,114";
            break;
        
          case 2://lit final year 2
            if(fccode==40)
              narrIds = "55,56,57,58,59,61,62,63,64,65";
            else
              narrIds = "55,58,59,61,62,63,64,65,112,115";
            break;       
        
          case 3://lit final year 3
            if(fccode==40)
              narrIds = "96,97,98,99,100,102,103,104,105,106";
            else
              narrIds = "96,99,100,102,103,104,105,106,113,116";
            break;
        }
        
        //get all narr types that do not have nar record for this grant
        conn = initializeConn();
        ps = conn.prepareStatement("Select id, narrative_name from narrative_types where id in "+
        " ("+ narrIds +") and id not in (select nat_id from project_narratives where gra_id=?) order by id");
        ps.setLong(1, grantid);
        
        rs = ps.executeQuery();
        while(rs.next()){
          missingNarr.add(rs.getString("narrative_name"));
        }
        
      }catch(Exception e){
        System.out.println("Error checkMissingLitFinalNarr "+e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }   
      return missingNarr;
    }  
    
    
  /**
     * method will check if this lgrmif grant has any final statistics saved to db.
     * If an empty vector is returned=no final statistics.  If vector size>0; then
     * assume that statistics form was completed.
     * @param grantid
     * @return
     */
    public Vector checkMissingLgStatistics(long grantid)
    {
      Vector missingStat = new Vector();
      String statIds = "15,16,17,18,19,20,21,22,23,24,25,26,27,28,29";
      try{              
        //get all lgrmif final stats types in the db for this grant
        conn = initializeConn();
        ps = conn.prepareStatement("Select id from project_statistics where gra_id=? " + 
        "and st_id in ("+ statIds +")");
        ps.setLong(1, grantid);        
        rs = ps.executeQuery();
        while(rs.next()){
          missingStat.add(rs.getString("id"));
        }
        
      }catch(Exception e){
        System.out.println("Error checkMissingLgStatistics "+e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }   
      return missingStat;
    }  
  
  /**
     * Method will check for final report narr (id 2) for co/di/lg before confirm final submit.
     * @param grantid
     * @return
     */
    public Vector checkMissingFinalRpt(long grantid)
    {
      Vector missingNarr = new Vector();
      try{        
        //get all narr types that do not have nar record for this grant
        conn = initializeConn();
        ps = conn.prepareStatement("Select id, narrative_name from narrative_types where id=2 "+
        "and id not in (select nat_id from project_narratives where gra_id=?)");
        ps.setLong(1, grantid);        
        rs = ps.executeQuery();
        while(rs.next()){
          missingNarr.add(rs.getString("narrative_name"));
        }
        
      }catch(Exception e){
        System.out.println("Error checkMissingNarratives "+e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }   
      return missingNarr;
    }
  
  public int submitLiInterim(long grantid, UserBean userb)
  {
    int outcome=0;    
    try{
      conn = initializeConn();
      
      //update submission info in the Grant_submissions table
        ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
          " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
          " ?, ?, SYSDATE, ?) ");
        ps.setLong(1, grantid);
        ps.setString(2, userb.getUserid());
        ps.setString(3, "Interim");
        ps.setString(4, userb.getUserid());
        outcome = ps.executeUpdate();              
    }
    catch(Exception e){
      System.out.println("error submitLiInterim "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }  
    return outcome;
  }  
  
  
    public int submitAmendment(long grantid, UserBean userb)
    {
      int outcome=0;    
      try{
        conn = initializeConn();        
        //update submission info in the Grant_submissions table
          ps = conn.prepareStatement("insert into GRANT_SUBMISSIONS (ID, GRA_ID, DATE_SUBMITTED, "+
            " GRAS_USER, VERSION, DATE_CREATED, CREATED_BY) values (gra_sub_seq.nextval, ?, SYSDATE, "+
            " ?, ?, SYSDATE, ?) ");
          ps.setLong(1, grantid);
          ps.setString(2, userb.getUserid());
          ps.setString(3, "Amendment");
          ps.setString(4, userb.getUserid());
          outcome = ps.executeUpdate();      
          
          ps.clearParameters();
          
          //update completed entries in the grants table
          String updatestr="update GRANTS set DURATION =1, DATE_MODIFIED=sysdate, "+
          " MODIFIED_BY=?  where ID=?";
          ps = conn.prepareStatement(updatestr);
          ps.setString(1, userb.getUserid());
          ps.setLong(2, grantid);        
          outcome = ps.executeUpdate();
                 
      }
      catch(Exception e){
        System.out.println("error submitAmendment "+e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
      }  
      return outcome;
    }  
}