package mypackage;
import java.sql.*;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdbc.OracleDriver;

public class EligibilityDbBean 
{
  public EligibilityDbBean()
  {
  }  
  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  NumberFormat numF = new DecimalFormat("#,###,###.##");

    
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
       * Method will get all eligibilty records for grant. Then insert/update/delete based on 
       * user's submitted eligibility records. Used for LGRMIF coversheet eligibility. Uses
       * the GRA_ID arc foreign key in GRANT_ELIGIBLE
       * @param request
       * @param csb
       */
    public void handleLgEligiblityItems(CoversheetBean csb, HttpServletRequest request)
    {    
      HttpSession sess = request.getSession();
      
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
        UserBean userb = (UserBean) sess.getAttribute("lduser");
      
        //get any eligibility info already in the database
        //key = eligiblity id     value = grant_eligiblies.id primary key
        HashMap allelig = getInstEligibility(grantid);      
        
        
        if(csb.getRmoAppointedint()==0 || csb.getRmoAppointedint()==1)//lgrmif 'rmo' is Y or N
        {
            if(csb.getRmoAppointedint()==1)//RMO=No
                csb.setRmoDate(null);
            if(csb.getRmoDate()!=null && !csb.getRmoDate().equals(""))//user enters year, default to date
                csb.setRmoDate("1/1/"+ csb.getRmoDate());
                
            if(allelig.containsKey(new Integer(6)))//record exists, update it
            {
              int theid = ((Integer) allelig.get(new Integer(6))).intValue();
              updateInstEligible(userb, theid, csb.getRmoDate());
            }
            else//record does not exist, insert it
              insertInstEligible(grantid, userb, 6, csb.getRmoDate());   
              
              if(allelig.containsKey(new Integer(8))) {//record exists for N/A, delete it
                 int theid = ((Integer) allelig.get(new Integer(8))).intValue();
                 deleteInstEligible(theid);
              }
        }
        else if(csb.getRmoAppointedint()==2)//lgrmif 'rmo' is N/A
        {
            if(allelig.containsKey(new Integer(6)))//record exists for Y or N, delete it
            {
              int theid = ((Integer) allelig.get(new Integer(6))).intValue();
              deleteInstEligible(theid);
            } 
            
            if(!allelig.containsKey(new Integer(8)))//if no record for N/A, then insert it
                insertInstEligible(grantid, userb, 8, null);
        }
        
//--------------------------------------------------------------------------------        
        if(csb.getScheduleAdoptedint()==0 || csb.getScheduleAdoptedint()==1)//'schedule' is y or n
        {
            if(csb.getScheduleAdoptedint()==1)//1=No
                csb.setScheduleDate(null);
            if(csb.getScheduleDate()!=null && !csb.getScheduleDate().equals(""))
                csb.setScheduleDate("1/1/"+ csb.getScheduleDate());
                
            if(allelig.containsKey(new Integer(7)))//record exists, update it
            {
              int theid = ((Integer) allelig.get(new Integer(7))).intValue();
              updateInstEligible(userb, theid, csb.getScheduleDate());
            }
            else//record does not exist, insert it
              insertInstEligible(grantid, userb, 7, csb.getScheduleDate());     
              
            if(allelig.containsKey(new Integer(9))){//record exists for N/A, delete it
                int theid = ((Integer) allelig.get(new Integer(9))).intValue();
                deleteInstEligible(theid);
            }
        }
        else if(csb.getScheduleAdoptedint()==2)//lgrmif item = N/A
        {
            if(allelig.containsKey(new Integer(7)))//record exists for Y or N, delete it
            {
              int theid = ((Integer) allelig.get(new Integer(7))).intValue();
              deleteInstEligible(theid);
            }             
            
            if(!allelig.containsKey(new Integer(9)))//if no record for N/A, then insert it
                insertInstEligible(grantid, userb, 9, null);
        }
    
    }catch(Exception e){
        System.out.println("error handleLgEligibilityItems() "+e.getMessage().toString());
    }
    finally{
        Close(conn);
        Close(ps);
        Close(rs);
    }
}


  /**
   * Method will get all eligibilty records for grant. Then insert/update/delete based on 
   * user's submitted eligibility records. Used for CP DISC coversheet eligibility. Uses
   * the GRA_ID arc foreign key in GRANT_ELIGIBLE
   * @param request
   * @param csb
   */
  public void handleEligiblityCheckboxes(CoversheetBean csb, HttpServletRequest request)
  {    
    HttpSession sess = request.getSession();
    
    try{
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid= Long.parseLong(grantnum);
      UserBean userb = (UserBean) sess.getAttribute("lduser");
    
      //get any eligibility info already in the database
      //key = eligiblity id     value = grant_eligiblies.id primary key
      HashMap allelig = getInstEligibility(grantid);      
      
      if(csb.isChartered())//item checked
      {
        if(allelig.containsKey(new Integer(1)))//record exists, update it
        {
          int theid = ((Integer) allelig.get(new Integer(1))).intValue() ;
          updateInstEligible(userb, theid, csb.getCharterdate());
        }
        else//record does not exist, insert it
          insertInstEligible(grantid, userb, 1, csb.getCharterdate());
      }
      else//item not checked
      {
        if(allelig.containsKey(new Integer(1)))//record exists, delete it
        {
          int theid = ((Integer) allelig.get(new Integer(1))).intValue() ;
          deleteInstEligible(theid);
        }
      }
      
            
      if(csb.isAccepted())//item checked
      {
        if(allelig.containsKey(new Integer(2)))//record exists, update it
        {
          int theid = ((Integer) allelig.get(new Integer(2))).intValue() ;
          updateInstEligible(userb, theid, csb.getAcceptdate());
        }
        else//record does not exist, insert it
          insertInstEligible(grantid, userb, 2, csb.getAcceptdate());
      }
      else//item not checked
      {
        if(allelig.containsKey(new Integer(2)))//record exists, delete it
        {
          int theid = ((Integer) allelig.get(new Integer(2))).intValue() ;
          deleteInstEligible(theid);
        }
      }
      
      
      
      if(csb.isCharity())//item checked
      {
        if(allelig.containsKey(new Integer(3)))//record exists, update it
        {
          int theid = ((Integer) allelig.get(new Integer(3))).intValue() ;
          updateInstEligible(userb, theid, csb.getCharitydate());
        }
        else//record does not exist, insert it
          insertInstEligible(grantid, userb, 3, csb.getCharitydate());
      }
      else//item not checked
      {
        if(allelig.containsKey(new Integer(3)))//record exists, delete it
        {
          int theid = ((Integer) allelig.get(new Integer(3))).intValue() ;
          deleteInstEligible(theid);
        }
      }
      
      
      if(csb.isNotprofit())//item checked
      {
        if(allelig.containsKey(new Integer(4)))//record exists, update it
        {
          int theid = ((Integer) allelig.get(new Integer(4))).intValue();
          updateInstEligible(userb, theid, csb.getNotprofitdate());
        }
        else//record does not exist, insert it
          insertInstEligible(grantid, userb, 4, csb.getNotprofitdate());
      }
      else//item not checked
      {
        if(allelig.containsKey(new Integer(4)))//record exists, delete it
        {
          int theid = ((Integer) allelig.get(new Integer(4))).intValue();
          deleteInstEligible(theid);
        }
      }
      
      
      if(csb.isOther())//'other' item checked
      {
        //if record exists, nothing to update- other does NOT have date associated w/ it
        if(! allelig.containsKey(new Integer(5)))
        {
          //record does not exist, insert it
          insertInstEligible(grantid, userb, 5, null);
        }
      }
      else//item not checked
      {
        if(allelig.containsKey(new Integer(5)))//record exists, delete it
        {
          int theid = ((Integer) allelig.get(new Integer(5))).intValue();
          deleteInstEligible(theid);
        }
      }
      
    }catch(Exception e){
      System.out.println("error handleEligibilityCheckboxes() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
  }


  /**
   * Method will get LGRMIF participant eligiblity records. Then compare against the
   * list of user edited records.  It will handle the insert/update/delete of the
   * user's records. Uses the COI_ID arc foreign key in GRANT_ELIGIBLE
   * @param userb
   * @param participants
   */
  public void handleParticipantEligibility(List participants, UserBean userb)
  {
    try{    
      for(int i=0; i<participants.size(); i++)
      {
          PartInstBean p = (PartInstBean) participants.get(i);
          
          //get any eligibility info already in the database
          //key = eligiblity id     value = grant_eligiblies.id primary key
          HashMap allelig = getParticipantEligibility(p.getId());    
                    
                    
          if(p.getRmoappointed()==0 || p.getRmoappointed()==1)//lgrmif 'rmo' is Y or N
          {
              if(p.getRmoappointed()==1)// 1=No
                p.setRmodate(null);
              if(p.getRmodate()!=null && !p.getRmodate().equals(""))//user enters year, default to date
                  p.setRmodate("1/1/"+ p.getRmodate());
                  
              if(allelig.containsKey(new Integer(6)))//record exists, update it
              {
                int theid = ((Integer) allelig.get(new Integer(6))).intValue();
                updateInstEligible(userb, theid, p.getRmodate());
              }
              else//record does not exist, insert it
                insertParticipantEligible(p.getId(), userb, 6, p.getRmodate());  
                
                if(allelig.containsKey(new Integer(8))){//record exists for N/A, delete it
                    int theid = ((Integer) allelig.get(new Integer(8))).intValue();
                    deleteInstEligible(theid);                    
                }
          }
          else if(p.getRmoappointed()==2)// 2 = N/A
          {
              if(allelig.containsKey(new Integer(6)))//record exists for Y or N, delete it
              {
                int theid = ((Integer) allelig.get(new Integer(6))).intValue();
                deleteInstEligible(theid);
              }        
              
              if(!allelig.containsKey(new Integer(8)))//if no record for N/A, then insert it
                insertParticipantEligible(p.getId(), userb, 8, p.getRmodate());
          }
//-------------------------------------------------------------------------------------

          
          if(p.getScheduleadopted()==0 || p.getScheduleadopted()==1)//lgrmif 'schedule' is Y or N
          {
              if(p.getScheduleadopted()==1)//1=No
                 p.setScheduledate(null);
              if(p.getScheduledate()!=null && !p.getScheduledate().equals(""))
                  p.setScheduledate("1/1/"+ p.getScheduledate());//user enters year, default to date
                  
              if(allelig.containsKey(new Integer(7)))//record exists, update it
              {
                int theid = ((Integer) allelig.get(new Integer(7))).intValue();
                updateInstEligible(userb, theid, p.getScheduledate());
              }
              else//record does not exist, insert it
                insertParticipantEligible(p.getId(), userb, 7, p.getScheduledate());     
                
                
              if(allelig.containsKey(new Integer(9))){//record exists for N/A, delete it
                  int theid = ((Integer) allelig.get(new Integer(9))).intValue();
                  deleteInstEligible(theid);
              }
          }
          else if(p.getScheduleadopted()==2)// 2=N/A
          {
              if(allelig.containsKey(new Integer(7)))//record exists for Y or N, delete it
              {
                int theid = ((Integer) allelig.get(new Integer(7))).intValue();
                deleteInstEligible(theid);
              }         
              
              if(!allelig.containsKey(new Integer(9)))//if no record for N/A, then insert it
                 insertParticipantEligible(p.getId(), userb, 9, p.getScheduledate());     
          }         
      }
      
    }catch(Exception e){
      System.out.println("error handleParticipantEligibilty() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
  }
  
  
  /**
   * Retrieve lg participant eligibility records from GRANT_ELIGIBLES where
   * COI_ID has value.
   * @return 
   * @param coinstid
   */
  public HashMap getParticipantEligibility(long coinstid)
  {
    HashMap allelig = new HashMap();
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from grant_eligibles where coi_id=?");
      ps.setLong(1, coinstid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int id = rs.getInt("id");
        int eltype = rs.getInt("inse_id");
        allelig.put(new Integer(eltype), new Integer(id) );
      }
      
    }
    catch(Exception e){
      System.out.println("error getParticipantEligibility() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return allelig;
  }
  
  
  /**
   * Retrieve di/lg applicant grant eligiblity from GRANT_ELIGIBLE where GRA_ID has value.
   * @return 
   * @param grantid
   */
  public HashMap getInstEligibility(long grantid)
  {
    HashMap allelig = new HashMap();
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from grant_eligibles where gra_id=? and coi_id is null");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int id = rs.getInt("id");
        int eltype = rs.getInt("inse_id");
        allelig.put(new Integer(eltype), new Integer(id) );
      }
      
    }
    catch(Exception e){
      System.out.println("error getInstEligibility() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return allelig;
  }
   

  /**
   * Update the eligibile date for an eligiblity record.
   * @return 
   * @param eligdate
   * @param id
   * @param userb
   */
  public int updateInstEligible(UserBean userb, int id, String eligdate)
  {
     int outcome =0;
     
     try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("update grant_eligibles set eligible_date=to_date(?, 'mm/dd/yyyy'), "+
      " modified_by=?, date_modified=sysdate where id=?");
      ps.setString(1, eligdate);
      ps.setString(2, userb.getUserid());
      ps.setInt(3, id);
      outcome = ps.executeUpdate();
      
    }
    catch(Exception e){
      System.out.println("error updateInstEligible() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome;
  }


  /**
   * Insert a di/lg applicant grant eligible record, must contain GRA_ID.
   * @return 
   * @param eligdate
   * @param eligtype
   * @param userb
   * @param grantid
   */
  public int insertInstEligible(long grantid, UserBean userb, int eligtype, String eligdate)
  {
    int outcome =0;
       
    try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into grant_eligibles (id, eligible_date, date_created, "+
      " created_by, gra_id, inse_id) values (ge_seq.nextval, to_date(?, 'mm/dd/yyyy'), sysdate, ?,?,?) ");
      ps.setString(1, eligdate);
      ps.setString(2, userb.getUserid());
      ps.setLong(3, grantid);
      ps.setInt(4, eligtype);
      outcome = ps.executeUpdate();    
     
     }
    catch(Exception e){
      System.out.println("error insertEligible() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome; 
  }
  
  
  /**
   * Insert a lg participant eligible record, must contain COI_ID
   * @return 
   * @param eligdate
   * @param eligtype
   * @param userb
   * @param coinstid
   */
  public int insertParticipantEligible(long coinstid, UserBean userb, int eligtype, String eligdate)
  {
    int outcome =0;
       
    try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into grant_eligibles (id, eligible_date, date_created, "+
      " created_by, coi_id, inse_id) values (ge_seq.nextval, to_date(?, 'mm/dd/yyyy'), sysdate, ?,?,?) ");
      ps.setString(1, eligdate);
      ps.setString(2, userb.getUserid());
      ps.setLong(3, coinstid);
      ps.setInt(4, eligtype);
      outcome = ps.executeUpdate();    
     
     }catch(Exception e){
      System.out.println("error insertParticipantEligible() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome; 
  }
  

  public int deleteInstEligible(int id)
  {
    int outcome =0;
     
     try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("delete from grant_eligibles where id=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();   
      
    }
    catch(Exception e){
      System.out.println("error deleteEligible() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome;
  }



  /**
   * Get di/lg applicant grant eligible records into coversheetbean format for add/update
   * on the cover action form.
   * @return 
   * @param cb
   */
  public CoversheetBean getInstEligibilityForCoversheet(CoversheetBean cb, int fccode)
  {
    Format formatter = new SimpleDateFormat("MM/dd/yyyy");  
    Format yearformat = new SimpleDateFormat("yyyy");
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from grant_eligibles where gra_id=? and coi_id is null");
      ps.setLong(1, cb.getGrantid());
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int eltype = rs.getInt("inse_id");
        
        String theDate = null;
        if(fccode==80 && rs.getDate("ELIGIBLE_DATE")!=null)
            theDate = yearformat.format(rs.getDate("eligible_date"));//lgrmif needs year
        else if(rs.getDate("eligible_date")!=null)
            theDate = formatter.format(rs.getDate("eligible_date"));//all others need m/d/y
            
        switch(eltype)
        {         
          case 1:             
            cb.setCharterdate(theDate);
            cb.setChartered(true);
            break;
          case 2:
            cb.setAcceptdate(theDate);
            cb.setAccepted(true);
            break;
          case 3:
            cb.setCharitydate(theDate);
            cb.setCharity(true);
            break;
          case 4:
            cb.setNotprofitdate(theDate);
            cb.setNotprofit(true);
            break;
          case 5:
            cb.setOther(true);
            break;
          case 6://for lgrmif only - check logic closely before changing again 8/26/09
            cb.setRmoDate(theDate);
            if(theDate!=null)
                cb.setRmoAppointedint(0);
            else
                cb.setRmoAppointedint(1);//user choose No
            break;
          case 8://for lgrmif only - FOR RMO APPOINTED SEE CASE 6 AND 8
            cb.setRmoDate(theDate);
            cb.setRmoAppointedint(2);//user choose N/A
            break;
            
          case 7://for lgrmif only - check logic closely before changing again 8/26/09
            cb.setScheduleDate(theDate);
            if(theDate!=null)
                cb.setScheduleAdoptedint(0);
            else
                cb.setScheduleAdoptedint(1);//user choose No
            break;        
         case 9://for lgrmif only - FOR SCHEDULE SEE CASE 7 AND 9
            cb.setScheduleDate(theDate);
            cb.setScheduleAdoptedint(2);//user choose N/A
            break;
        }
      }
      
    }
    catch(Exception e){
      System.out.println("error getInstEligibleCoversheet() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return cb;
  }
  
  
  /**
   * Get LG doris app flag, project category, and all govt info from LG tables for add/update
   * on LG cover action form.
   * @return 
   * @param csb
   */
  public CoversheetBean getArchivesGovtInfo(CoversheetBean csb)
  {  
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, doris_flag, doris_name, pc_id, descr, shpo_comp, seaf_comp from "+
      " grants left join project_categories on grants.pc_id=project_categories.id where grants.id=?");
      ps.setLong(1, csb.getGrantid());
      rs = ps.executeQuery();      
      while(rs.next())
      {
        csb.setDorisName(rs.getString("doris_name"));
        csb.setDorisFlag(rs.getBoolean("doris_flag"));
        csb.setProjcategoryId(rs.getInt("pc_id"));
        csb.setProjcategoryName(rs.getString("descr"));
        csb.setPlanningDemo(rs.getBoolean("shpo_comp"));
        csb.setImplementDemo(rs.getBoolean("seaf_comp"));
      }     
      
      ps = conn.prepareStatement("select govt_infos.id, dept, population, annual_budget, ft_employees, "+
      "pt_employees, inventory_yn, email_mgmt_yn, electronic_rcds_yn, cooperative_yn, rt_id, gt_id, shared_serv_yn, "+
      "region_types.descr as regdescr, govt_types.descr as govdescr from govt_infos left join "+
      "region_types on govt_infos.rt_id=region_types.id left join govt_types on "+
      "govt_infos.gt_id=govt_types.id where govt_infos.gra_id=?");
      ps.setLong(1, csb.getGrantid());
      rs = ps.executeQuery();      
          
      while(rs.next())
      {
        csb.setDept(rs.getString("dept"));
        csb.setPopulation(rs.getLong("population"));
        csb.setPopulationStr(rs.getString("population"));
        csb.setAnnualbudget(rs.getLong("annual_budget"));
        csb.setAnnualbudgetStr(rs.getString("annual_budget"));
        csb.setFtemployees(rs.getInt("ft_employees"));
        csb.setPtemployees(rs.getInt("pt_employees"));
        csb.setFtemployeesStr(rs.getString("ft_employees"));
        csb.setPtemployeesStr(rs.getString("pt_employees"));
        csb.setInventory(rs.getBoolean("inventory_yn"));
        csb.setEmailmgmt(rs.getBoolean("email_mgmt_yn"));
        csb.setRecordsmgmt(rs.getBoolean("electronic_rcds_yn"));
        csb.setCooperative(rs.getBoolean("cooperative_yn"));
        csb.setSharedserv(rs.getBoolean("shared_serv_yn"));//added 9/2/10 for new lgrmif grant cycle
        csb.setGovtRegionId(rs.getInt("rt_id"));
        csb.setGovtTypeId(rs.getInt("gt_id"));
        csb.setGovtId(rs.getLong("id"));
        csb.setGovtRegionName(rs.getString("regdescr"));
        csb.setGovtTypeName(rs.getString("govdescr"));
      }  
      
      //new logic now that appType has 3 choices instead of 2, per FC 9/2/10
      if(csb.isSharedserv())
        csb.setApplicationType(3);
      else if(csb.isCooperative())
        csb.setApplicationType(2);//starting 10/2/13 type2=demonstration project (previously cooperative)
      else //not shared or cooperative, so its individual
        csb.setApplicationType(1);
        
        
      //new logic for demo type starting fy 2014-15 per FC  10/3/13
      if(csb.isPlanningDemo())
          csb.setDemoType(1);
      else if(csb.isImplementDemo())
          csb.setDemoType(2);
      else
          csb.setDemoType(0);
        
              
        if(csb.getAnnualbudgetStr()!=null && !csb.getAnnualbudgetStr().equals("")){
          long annbudget = Long.parseLong(csb.getAnnualbudgetStr());
          csb.setAnnualbudgetStr(numF.format(annbudget));
        }
        if(csb.getPopulationStr()!=null && !csb.getPopulationStr().equals("")){
          long popul = Long.parseLong(csb.getPopulationStr());
          csb.setPopulationStr(numF.format(popul));
        }
        if(csb.getFtemployeesStr()!=null && !csb.getFtemployeesStr().equals("")){
          long popul = Long.parseLong(csb.getFtemployeesStr());
          csb.setFtemployeesStr(numF.format(popul));
        }
        if(csb.getPtemployeesStr()!=null && !csb.getPtemployeesStr().equals("")){
          long popul = Long.parseLong(csb.getPtemployeesStr());
          csb.setPtemployeesStr(numF.format(popul));
        }
    }
    catch(Exception e){
      System.err.println("error getArchivesGovtInfo() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return csb;
  }
  
  
  
    public CoversheetBean getLgBonusScoring(long grantid)
    {  
      CoversheetBean csb = new CoversheetBean();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select govt_infos.id, inventory_yn, email_mgmt_yn, "+
        " electronic_rcds_yn, cooperative_yn from govt_infos where govt_infos.gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();      
        while(rs.next())
        {
            csb.setInventory(rs.getBoolean("inventory_yn"));
            csb.setEmailmgmt(rs.getBoolean("email_mgmt_yn"));
            csb.setRecordsmgmt(rs.getBoolean("electronic_rcds_yn"));
            csb.setCooperative(rs.getBoolean("cooperative_yn"));
            csb.setGovtId(rs.getLong("id"));
            csb.setGrantid(grantid);
        }         
        int bonusscore =0;
        if(csb.isInventory())
            bonusscore+=5;
        if(csb.isEmailmgmt())
            bonusscore+=5;
        if(csb.isRecordsmgmt())
            bonusscore+=5;
        if(csb.isCooperative())
            bonusscore+=10;
        csb.setScore(bonusscore);

      }catch(Exception e){
        System.err.println("error getLgBonusScoring() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return csb;        
    }
    
  /**
     * method for lgrmif admin to update coversheet items for apcnt, including bonus pts,
     * proj category, region, app type, govt type. per FC
     * @param cb
     * @param ub
     * @return
     */
  public int updateLgBonusScoring(CoversheetBean cb, UserBean ub)
    {  
      int outcome =0;
      try{
        conn = initializeConn();
      
        //new logic for appType now that there are 3 choices instead of 2
        if(cb.getApplicationType()==2)
          cb.setCooperative(true);
        else if(cb.getApplicationType()==3)
          cb.setSharedserv(true);
        else if(cb.getApplicationType()==1)
          ;//individual project     
      
        ps = conn.prepareStatement("update govt_infos set inventory_yn=?, email_mgmt_yn=?, "+
        " electronic_rcds_yn=?, cooperative_yn=?, shared_serv_yn=?, date_modified=sysdate, "+
        " modified_by=?, gt_id=?, rt_id=? where govt_infos.id=?");
        ps.setBoolean(1, cb.isInventory());
        ps.setBoolean(2, cb.isEmailmgmt());
        ps.setBoolean(3, cb.isRecordsmgmt());
        ps.setBoolean(4, cb.isCooperative());
        ps.setBoolean(5, cb.isSharedserv());        
        ps.setString(6, ub.getUserid());
        ps.setInt(7, cb.getGovtTypeId());
        ps.setInt(8, cb.getGovtRegionId());
        ps.setLong(9, cb.getGovtId());
        outcome = ps.executeUpdate();     
        
        //admin can update the proj category
        ps = conn.prepareStatement("update grants set pc_id=?, date_modified=sysdate, modified_by=? where id=?");
        ps.setInt(1, cb.getProjcategoryId());
        ps.setString(2, ub.getUserid());
        ps.setLong(3, cb.getGrantid());
        outcome = ps.executeUpdate(); 
        
      }catch(Exception e){
        System.err.println("error updateLgBonusScoring() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return outcome;        
    }
    
  
  public int insertArchivesGovtInfo(long grantid, UserBean userb, CoversheetBean cb, int fycode)
  {
    int outcome=0;
    DBHandler dbh = new DBHandler();
    
    try{
        //new logic for appType now that there are 3 choices instead of 2
        if(cb.getApplicationType()==3)
            cb.setSharedserv(true);
        else if(cb.getApplicationType()==2)
            cb.setCooperative(true);//cooperative is now demo starting fy2014-15; see notes for 2015/16 below
        else if(cb.getApplicationType()==1)
            ;//individual project; not shared or cooperative
        
        
        //logic for demoType starting 10/3/13 per FC for FY 14-15 ONLY
        //NOTE:  8/8/14 starting FY 15-16; demo is a separate category, not a subcategory.  
        if(cb.getDemoType()==1)
            cb.setPlanningDemo(true);
        else if(cb.getDemoType()==2)
            cb.setImplementDemo(true);
        else
            ;//not plan or implementation - not a demo project
        
        
        if(fycode>15){//starting 2015-16; if demo project, need to set category same as previous subcategory for rpting
            if(cb.getProjcategoryId()==14)
                cb.setPlanningDemo(true);
            else if(cb.getProjcategoryId()==15)
                cb.setImplementDemo(true);          
        }
        
        /*if(fycode>15){//starting FY2015-16; demo is a category (not subcategory); so need to set appType in code in order
            //for reports/queries to continue to work as is.  User no long selects demo as appType (only as category)
            if(cb.getProjcategoryId()==14){
              cb.setCooperative(true);    
              //???????set applicationtype here too???
            }
            else
                cb.setCooperative(false);                      
        }*/
        
        
                    
      conn = initializeConn();
      ps = conn.prepareStatement("insert into GOVT_INFOS (id, dept, population, annual_budget, "+
      "ft_employees, pt_employees, inventory_yn, email_mgmt_yn, electronic_rcds_yn, cooperative_yn, "+
      " rt_id, gt_id, date_created, created_by, gra_id, shared_serv_yn) values (GI_SEQ.nextval, ?, ?, ?, ?,?, "+
      " ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?)");
      
      if(cb.getAnnualbudgetStr()!= null && !cb.getAnnualbudgetStr().equals(""))
          cb.setAnnualbudget(dbh.parseLongAmtNoDecimal(cb.getAnnualbudgetStr()) );
          
      if(cb.getPopulationStr()!= null && !cb.getPopulationStr().equals(""))
            cb.setPopulation(dbh.parseLongAmtNoDecimal(cb.getPopulationStr()) );
    
      if(cb.getFtemployeesStr()!= null && !cb.getFtemployeesStr().equals(""))
            cb.setFtemployees(dbh.parseCurrencyAmtNoDecimal(cb.getFtemployeesStr()) );
             
      if(cb.getPtemployeesStr()!= null && !cb.getPtemployeesStr().equals(""))
            cb.setPtemployees(dbh.parseCurrencyAmtNoDecimal(cb.getPtemployeesStr()) );
      
      ps.setString(1, cb.getDept());
      ps.setLong(2, cb.getPopulation());
      ps.setLong(3, cb.getAnnualbudget());
      ps.setInt(4, cb.getFtemployees());
      ps.setInt(5, cb.getPtemployees());
      ps.setBoolean(6, cb.isInventory());
      ps.setBoolean(7, cb.isEmailmgmt());
      ps.setBoolean(8, cb.isRecordsmgmt());
      ps.setBoolean(9, cb.isCooperative());
      ps.setInt(10, cb.getGovtRegionId());
      ps.setInt(11, cb.getGovtTypeId());
      ps.setString(12, userb.getUserid());
      ps.setLong(13, grantid);
      ps.setBoolean(14, cb.isSharedserv());
      outcome =ps.executeUpdate();
      
      //if not doris agency - then no doris name
      if(!cb.isDorisFlag())
        cb.setDorisName(null);
      ps = conn.prepareStatement("update grants set doris_flag=?, doris_name=?, pc_id=?, date_modified=sysdate, modified_by=?, "+
                                 " shpo_comp=?, seaf_comp=? where id=?");
      ps.setBoolean(1, cb.isDorisFlag());
      ps.setString(2, cb.getDorisName());
      ps.setInt(3, cb.getProjcategoryId());
      ps.setString(4, userb.getUserid());
      ps.setBoolean(5, cb.isPlanningDemo());
      ps.setBoolean(6, cb.isImplementDemo());
      ps.setLong(7, grantid);
      outcome =ps.executeUpdate();
    
    }catch(Exception e){
      System.out.println("error insertArchivesGovtInfo() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome;
  }  
  
  
  public int updateArchivesGovtInfo(UserBean userb, CoversheetBean cb, int fycode)
  {
    int outcome=0;
    DBHandler dbh = new DBHandler();
    
    try{    
    if(cb.getAnnualbudgetStr()!= null && !cb.getAnnualbudgetStr().equals(""))
        cb.setAnnualbudget(dbh.parseLongAmtNoDecimal(cb.getAnnualbudgetStr()) );
        
    if(cb.getPopulationStr()!= null && !cb.getPopulationStr().equals(""))
          cb.setPopulation(dbh.parseLongAmtNoDecimal(cb.getPopulationStr()) );
          
    if(cb.getFtemployeesStr()!= null && !cb.getFtemployeesStr().equals(""))
          cb.setFtemployees(dbh.parseCurrencyAmtNoDecimal(cb.getFtemployeesStr()) );
           
    if(cb.getPtemployeesStr()!= null && !cb.getPtemployeesStr().equals(""))
          cb.setPtemployees(dbh.parseCurrencyAmtNoDecimal(cb.getPtemployeesStr()) );
    
     //new logic for appType now that there are 3 choices instead of 2
     if(cb.getApplicationType()==3)
        cb.setSharedserv(true);
     else if(cb.getApplicationType()==2)
        cb.setCooperative(true);//cooperative is now demo starting fy2014-15; see notes for 2015/16 below
     else if(cb.getApplicationType()==1)
        ;//individual project; not shared or cooperative
        
        
      //logic for demoType starting 10/3/13 per FC for FY 14-15 ONLY
      //NOTE:  8/8/14 starting FY 15-16; demo is a separate category, not a subcategory.  
      if(cb.getDemoType()==1)
          cb.setPlanningDemo(true);
      else if(cb.getDemoType()==2)
          cb.setImplementDemo(true);
      else
          ;//not plan or implementation - not a demo project
        
      if(fycode>15){//starting 2015-16; if demo project, need to set category same as previous subcategory for rpting
          if(cb.getProjcategoryId()==14)
              cb.setPlanningDemo(true);
          else if(cb.getProjcategoryId()==15)
              cb.setImplementDemo(true);          
      }
      
      /*if(fycode>15){//starting FY2015-16; demo is a category (not subcategory); so need to set appType in code in order
          //for reports/queries to continue to work as is.  User no long selects demo as appType (only as category)
          if(cb.getProjcategoryId()==14){
            cb.setCooperative(true);    
            //???????set applicationtypeid here too???
          }
          else
              cb.setCooperative(false);                    
      }*/
            
      conn = initializeConn();
      ps = conn.prepareStatement("update GOVT_INFOS set dept=?, population=?, annual_budget=?, "+
      "ft_employees=?, pt_employees=?, inventory_yn=?, email_mgmt_yn=?, electronic_rcds_yn=?, "+
      "cooperative_yn=?, rt_id=?, gt_id=?, date_modified=sysdate, modified_by=?, shared_serv_yn=? where id=?");
      
      ps.setString(1, cb.getDept());
      ps.setLong(2, cb.getPopulation());
      ps.setLong(3, cb.getAnnualbudget());
      ps.setInt(4, cb.getFtemployees());
      ps.setInt(5, cb.getPtemployees());
      ps.setBoolean(6, cb.isInventory());
      ps.setBoolean(7, cb.isEmailmgmt());
      ps.setBoolean(8, cb.isRecordsmgmt());
      ps.setBoolean(9, cb.isCooperative());
      ps.setInt(10, cb.getGovtRegionId());
      ps.setInt(11, cb.getGovtTypeId());
      ps.setString(12, userb.getUserid());
      ps.setBoolean(13, cb.isSharedserv());
      ps.setLong(14, cb.getGovtId());
      outcome =ps.executeUpdate();
           
      //if not doris agency - then no doris name
      if(!cb.isDorisFlag())
        cb.setDorisName(null);
      ps = conn.prepareStatement("update grants set doris_flag=?, doris_name=?, pc_id=?, date_modified=sysdate, "+
                                 "modified_by=?, shpo_comp=?, seaf_comp=? where id=?");
      ps.setBoolean(1, cb.isDorisFlag());
      ps.setString(2, cb.getDorisName());
      ps.setInt(3, cb.getProjcategoryId());
      ps.setString(4, userb.getUserid());
      ps.setBoolean(5, cb.isPlanningDemo());
      ps.setBoolean(6, cb.isImplementDemo());
      ps.setLong(7, cb.getGrantid());
      outcome =ps.executeUpdate();
            
    }catch(Exception e){
      System.out.println("error updateArchivesGovtInfo() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return outcome;
  }  
  
}