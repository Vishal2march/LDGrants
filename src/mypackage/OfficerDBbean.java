/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  OfficerDBbean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will handle all database transactions for getting the preservation officers
 * and library directors assigned to a grant, and also get those officers contact info
 * such as email,phone. All info comes from SEDREF and is stored in beans. 
 *****************************************************************************/
package mypackage;

import construction.CnApplicationBean;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.servlet.http.*;
import javax.sql.*;


public class OfficerDBbean 
{
  public OfficerDBbean()
  {
  }  
  
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
  
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
  

  public int verifyCurrentDirector(long grantid, long instid, UserBean userb)
  {
    int curradmin=0, sedrefadmin=0, outcome=0;
    long currentid=0;
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select ADMIN_POS_ID, id from GRANT_ADMINS where GRA_ID=? and "+
      " TITLE='Library Director' and END_DATE is null");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      while(rs.next()){
        curradmin = rs.getInt("admin_pos_id");
        currentid = rs.getLong("id");
      }
      
     /* commentout 8/19/10 -if record has both type 1 and 4; the type 4 is selected instead of type1
      * ps = conn.prepareStatement("select admin_pos_id from SED_ADMIN_POSITIONS where INST_ID=? and "+
      " (ADMIN_POS_TYPE_CODE =1 or ADMIN_POS_TYPE_CODE=4) and inactive_date is null");*/
      ps = conn.prepareStatement("select admin_pos_id from SED_ADMIN_POSITIONS where INST_ID=? and "+
            " (ADMIN_POS_TYPE_CODE =1) and inactive_date is null");      
      ps.setLong(1, instid);
      rs = ps.executeQuery();
      while(rs.next()){
        sedrefadmin = rs.getInt("admin_pos_id");
      }
      
      if(sedrefadmin==0){
          //no CEO type 1 record found; see if there is a type 4 record for additional contact
           ps = conn.prepareStatement("select admin_pos_id from SED_ADMIN_POSITIONS where INST_ID=? and "+
                 " (ADMIN_POS_TYPE_CODE =4) and inactive_date is null");      
           ps.setLong(1, instid);
           rs = ps.executeQuery();
           while(rs.next()){
             sedrefadmin = rs.getInt("admin_pos_id");
           }
      }
      
      
      if(curradmin!=sedrefadmin)
      {
        System.out.println("need new director contact");
        //set enddate for old director
        ps = conn.prepareStatement("update grant_admins set end_date=sysdate, date_modified= "+
        "sysdate, modified_by=? where id=?");
        ps.setString(1, userb.getUserid());
        ps.setLong(2, currentid);
        outcome = ps.executeUpdate();
        
        //add record for new director
        ps = conn.prepareStatement("insert into GRANT_ADMINS (ID, ADMIN_POS_ID, GRA_ID, DATE_CREATED, "+
" CREATED_BY, TITLE, start_date) values (GRA_ADMIN_SEQ.NEXTVAL, ?, ?, SYSDATE, ?, 'Library Director', SYSDATE)");
        ps.setInt(1, sedrefadmin);
        ps.setLong(2, grantid);
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }
      
    }catch(Exception e){
      System.out.println("error verifyCurrentDirector "+e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return outcome;
  }


  /**
   * This method will get the current preservation officer OR Library Director assigned to a grant
   * in the grant_admins table
   * @return an officerbean containing info on the current admin postition
   * @param grantid, officer_type
   */
  public OfficerBean getOfficerAssigned(long grantid, String officer_type)
  {  
    OfficerBean sb = new OfficerBean();    
    try{
      conn = initializeConn();            
     
      //changed 3/27/08 to use plsql initcap()
      ps = conn.prepareStatement("select ADMIN_POS_ID, initcap(TITLE) as TITLE, initcap(FNAME) as FNAME, initcap(MI) as MI, "+
       " initcap(LNAME) as LNAME, initcap(SED_SALUTATIONS.DESCRIPTION) as SALUT from SED_ADMIN_POSITIONS, "+
       " SED_SALUTATIONS where SED_ADMIN_POSITIONS.SAL_CODE = SED_SALUTATIONS.SAL_CODE and "+
       " SED_ADMIN_POSITIONS.ADMIN_POS_ID in (select ADMIN_POS_ID from GRANT_ADMINS where GRA_ID=? "+
       " and TITLE=? and END_DATE is null) ");
      ps.setLong(1, grantid);
      ps.setString(2, officer_type );            
      rs = ps.executeQuery();      
      while(rs.next())
      {
        sb.setFname(rs.getString("FNAME"));
        sb.setMname(rs.getString("MI"));
        sb.setLname(rs.getString("LNAME"));
        sb.setTitle(rs.getString("TITLE"));
        sb.setSedrefadminid(rs.getString("ADMIN_POS_ID")); 
        sb.setSalutation(rs.getString("SALUT"));
        sb.setGrantid(grantid);
      }
               
      ps.clearParameters();
      ps  = conn.prepareStatement("select * from SED_CONTACT_INFO where ADMIN_POS_ID=?");
      ps.setString(1, sb.getSedrefadminid());
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        if(rs.getString("CONTACT_TYPE_CODE").equals("1"))
          sb.setPhone(rs.getString("CONTACT_VALUE"));
        else if(rs.getString("CONTACT_TYPE_CODE").equals("3"))
          sb.setEmail(rs.getString("CONTACT_VALUE")); 
      }
      
      if(sb.getPhone()!=null &&  !sb.getPhone().equals(""))
      {
        String newphone = handleParsePhone(sb.getPhone());
        sb.setPhone(newphone);
      }
      
    }
    catch(Exception e){
      System.err.println("error getOfficerAssigned() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return sb;
  }

  public String handleParsePhone(String phone)
  {    
    char[] phString = phone.toCharArray();//convert string to array of char
    Vector newphoneString = new Vector();    
    
    //loop on all items in the old string array
    for(int i=0; i<phString.length; i++) 
    {
        switch(i)
        {
          case 0:
                newphoneString.add("(");
                break;
          case 3:
                newphoneString.add(")");
                break;
          case 6:
                newphoneString.add("-");
                break;
        }
        newphoneString.add(new Character(phString[i]));//cannot add char to vector - must wrap in a character object
    }    
    
    String tmpString = "";
    //now convert all the chars in the vector back to a string
    for(int i=0; i<newphoneString.size();i++)
    {
      tmpString+= newphoneString.get(i);
    }    
    return tmpString;
  }

  public OfficerBean getProjectManager(long grantid)
  {  
    OfficerBean ob = new OfficerBean();
    int hasRecord = 0;
    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from PROJECT_MANAGERS where ID in "+
        " (select PRM_ID from GRANTS where ID=?)");
      ps.setLong(1, grantid);
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        hasRecord++;
        ob.setFname(rs.getString("FNAME"));
        ob.setMname(rs.getString("MNAME"));
        ob.setLname(rs.getString("LNAME"));
        ob.setTitle(rs.getString("TITLE"));
        ob.setStaffID(rs.getLong("ID"));
      }
      
      ps.clearParameters();
      ps = conn.prepareStatement("select * from CONTACTS where PRM_ID=?");
      ps.setLong(1, ob.getStaffID());
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        if(rs.getInt("CONTACT_TYPE_CODE")==2)
          ob.setPhone(rs.getString("CONTACT_VALUE"));          
        else if(rs.getInt("CONTACT_TYPE_CODE")==3)
          ob.setEmail(rs.getString("CONTACT_VALUE"));
        else if(rs.getInt("CONTACT_TYPE_CODE")==8)
          ob.setPhoneext(rs.getString("CONTACT_VALUE"));
      }
     
    }
    catch(Exception e){
      System.err.println("error getProjectManager() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }   
    return ob;
  }

  public boolean hasProjectManagerID(long grantid)
  {
    boolean hasID = false;    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select PRM_ID from GRANTS where ID=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();      
      while(rs.next())
      {
        int id = rs.getInt("PRM_ID");
        if(id!=0)
          hasID = true;          
      }      
    }
    catch(Exception e){
      System.err.println("error hasProjectManagerID() " + e.getMessage().toString());
    }
    finally{      
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return hasID;
  }


  public OfficerBean getOfficerForInst(long instid, String adminType)
  {
    OfficerBean ob = new OfficerBean();
    try{
      conn = initializeConn();
     
      ps = conn.prepareStatement("select ADMIN_POS_ID, initcap(TITLE) as TITLE, initcap(FNAME) as FNAME, "+
      " initcap(MI) as MI, initcap(LNAME) as LNAME, initcap(SED_SALUTATIONS.DESCRIPTION) as SALUT from "+
      " SED_ADMIN_POSITIONS, SED_SALUTATIONS where SED_ADMIN_POSITIONS.SAL_CODE = SED_SALUTATIONS.SAL_CODE "+
      " and SED_ADMIN_POSITIONS.ADMIN_POS_TYPE_CODE =? and INST_ID=?");
      ps.setString(1, adminType);
      ps.setLong(2, instid );      
            
      rs = ps.executeQuery();      
      while(rs.next())
      {
        ob.setFname(rs.getString("FNAME"));
        ob.setMname(rs.getString("MI"));
        ob.setLname(rs.getString("LNAME"));
        ob.setTitle(rs.getString("TITLE"));
        ob.setSedrefadminid(rs.getString("ADMIN_POS_ID")); 
        ob.setSalutation(rs.getString("SALUT"));        
      }
    }
    catch(Exception e){
      System.err.println("error getOfficerForInst() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }      
    return ob;
  }

  public int insertProjectManager(long grantid, UserBean userb, CoversheetBean cb)
  {  
    String user_id = userb.getUserid();
    int outcome = 0;
  
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into PROJECT_MANAGERS (ID, FNAME, MNAME, LNAME, "+
          " TITLE, START_DATE, DATE_CREATED, CREATED_BY) values (PM_REV_SEQ.nextval, ?,?,?, "+
          " ?, sysdate, sysdate, ?)");
      ps.setString(1, cb.getFname() );
      ps.setString(2, cb.getMname());
      ps.setString(3, cb.getLname());
      ps.setString(4, cb.getTitle());
      ps.setString(5, user_id);
      outcome = ps.executeUpdate();
      
      ps.clearParameters();
      ps = conn.prepareStatement("select max(ID) from PROJECT_MANAGERS");
      rs = ps.executeQuery();
      while(rs.next())
        cb.setPmId(rs.getLong(1));
      
      
      ps.clearParameters();
      ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, PRM_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        
      if(cb.getPhone()!=null && !cb.getPhone().equals(""))
      {
        ps.setInt(1, 2);//type 2 is work phone
        ps.setString(2, cb.getPhone() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();
      }      
      
      if(cb.getEmail()!=null && !cb.getEmail().equals(""))
      {
        ps.setInt(1, 3);//type 3 is email
        ps.setString(2, cb.getEmail() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getPhoneext()!=null && !cb.getPhoneext().equals(""))
      {
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getPhoneext() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();
      }
            
      ps.clearParameters();
      ps = conn.prepareStatement("update GRANTS set PRM_ID=?, date_modified=sysdate where ID=?");
      ps.setLong(1, cb.getPmId());
      ps.setLong(2, grantid);
      outcome = ps.executeUpdate();            
    }
    catch(Exception e) {
      System.err.println("error insertProjectManager() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }
  
  
  
    public int insertCnProjectManager(long grantid, UserBean userb, CnApplicationBean cb)
    {  
      String user_id = userb.getUserid();
      int outcome = 0;
    
      try {
        conn = initializeConn();
        
        ps = conn.prepareStatement("insert into PROJECT_MANAGERS (ID, FNAME, LNAME, "+
            " START_DATE, DATE_CREATED, CREATED_BY) values (PM_REV_SEQ.nextval, ?,?, "+
            " sysdate, sysdate, ?)");
        ps.setString(1, cb.getManagerFirstName() );
        ps.setString(2, cb.getManagerLastName());
        ps.setString(3, user_id);
        outcome = ps.executeUpdate();
        
        ps.clearParameters();
        ps = conn.prepareStatement("select max(ID) from PROJECT_MANAGERS");
        rs = ps.executeQuery();
        while(rs.next())
          cb.setManagerId(rs.getLong(1));
        
        
        ps.clearParameters();
        ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
          " DATE_CREATED, CREATED_BY, PRM_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
          
        if(cb.getManagerPhone()!=null && !cb.getManagerPhone().equals(""))
        {
          ps.setInt(1, 2);//type 2 is work phone
          ps.setString(2, cb.getManagerPhone() );
          ps.setString(3, user_id);
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();
        }      
        
        if(cb.getManagerEmail()!=null && !cb.getManagerEmail().equals(""))
        {
          ps.setInt(1, 3);//type 3 is email
          ps.setString(2, cb.getManagerEmail() );
          ps.setString(3, user_id);
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();
        }
        
        if(cb.getManagerPhoneExt()!=null && !cb.getManagerPhoneExt().equals(""))
        {
          ps.setInt(1, 8);//type 8 is phoneext
          ps.setString(2, cb.getManagerPhoneExt() );
          ps.setString(3, user_id);
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();
        }
              
        ps.clearParameters();
        ps = conn.prepareStatement("update GRANTS set PRM_ID=? where ID=?");
        ps.setLong(1, cb.getManagerId());
        ps.setLong(2, grantid);
        outcome = ps.executeUpdate();            
      }
      catch(Exception e) {
        System.err.println("error insertCnProjectManager() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }
      return outcome;  
    }

  public int updateProjectManager(UserBean userb, CoversheetBean cb)
  {
    String userid = userb.getUserid();   
    int outcome = 0;
  
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("update PROJECT_MANAGERS set FNAME=?, MNAME=?, LNAME=?, "+
        " TITLE=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?");
      ps.setString(1, cb.getFname());
      ps.setString(2, cb.getMname());
      ps.setString(3, cb.getLname());
      ps.setString(4, cb.getTitle());
      ps.setString(5, userid);
      ps.setLong(6, cb.getPmId());
      outcome = ps.executeUpdate();            
      
      ps.clearParameters();
      ps = conn.prepareStatement("update CONTACTS set CONTACT_VALUE=?, DATE_MODIFIED=sysdate, "+
        " MODIFIED_BY=? where CONTACT_TYPE_CODE=? and PRM_ID=?");
        
      if(cb.getPhone()!=null && !cb.getPhone().equals(""))
      {
        ps.setString(1, cb.getPhone());
        ps.setString(2, userid);
        ps.setInt(3, 2);//type 2 for work phone
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getEmail()!=null && !cb.getEmail().equals(""))
      {
        ps.setString(1, cb.getEmail());
        ps.setString(2, userid);
        ps.setInt(3, 3);//type 3 for email
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();     
      }
      
      if(cb.getPhoneextId()!=0)
      {
        ps.setString(1, cb.getPhoneext());
        ps.setString(2, userid);
        ps.setInt(3, 8);//type 8 for phoneext
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();  
      }
      else if(cb.getPhoneext()!=null && !cb.getPhoneext().equals(""))
      {
        //need to insert new phoneext
        ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, PRM_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getPhoneext() );
        ps.setString(3, userb.getUserid());
        ps.setLong(4, cb.getPmId());
        outcome = ps.executeUpdate();        
      }      
    }
    catch(Exception e){
      System.err.println("error updateProjectManager() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }      
    return outcome;
  }

 
    public int updateCnProjectManager(UserBean userb, CnApplicationBean cb)
    {
      String userid = userb.getUserid();   
      int outcome = 0;
    
      try{
        conn = initializeConn();
        
        ps = conn.prepareStatement("update PROJECT_MANAGERS set FNAME=?, LNAME=?, "+
          " DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?");
        ps.setString(1, cb.getManagerFirstName());
        ps.setString(2, cb.getManagerLastName());
        ps.setString(3, userid);
        ps.setLong(4, cb.getManagerId());
        outcome = ps.executeUpdate();            
        
        ps.clearParameters();
        ps = conn.prepareStatement("update CONTACTS set CONTACT_VALUE=?, DATE_MODIFIED=sysdate, "+
          " MODIFIED_BY=? where CONTACT_TYPE_CODE=? and PRM_ID=?");
          
        if(cb.getManagerPhone()!=null && !cb.getManagerPhone().equals(""))
        {
          ps.setString(1, cb.getManagerPhone());
          ps.setString(2, userid);
          ps.setInt(3, 2);//type 2 for work phone
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();
        }
        
        if(cb.getManagerEmail()!=null && !cb.getManagerEmail().equals(""))
        {
          ps.setString(1, cb.getManagerEmail());
          ps.setString(2, userid);
          ps.setInt(3, 3);//type 3 for email
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();     
        }
        
        if(cb.getManagerPhoneExtId()!=0)
        {
          ps.setString(1, cb.getManagerPhoneExt());
          ps.setString(2, userid);
          ps.setInt(3, 8);//type 8 for phoneext
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();  
        }
        else if(cb.getManagerPhoneExt()!=null && !cb.getManagerPhoneExt().equals(""))
        {
          //need to insert new phoneext
          ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
          " DATE_CREATED, CREATED_BY, PRM_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
          ps.setInt(1, 8);//type 8 is phoneext
          ps.setString(2, cb.getManagerPhoneExt() );
          ps.setString(3, userb.getUserid());
          ps.setLong(4, cb.getManagerId());
          outcome = ps.executeUpdate();        
        }      
      }
      catch(Exception e){
        System.err.println("error updateCnProjectManager() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }      
      return outcome;
    }


  public CoversheetBean getProjectManager(CoversheetBean cb)
  {     
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from PROJECT_MANAGERS where ID in "+
        " (select PRM_ID from GRANTS where ID=?)");
      ps.setLong(1, cb.getGrantid());
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        cb.setFname(rs.getString("FNAME"));
        cb.setMname(rs.getString("MNAME"));
        cb.setLname(rs.getString("LNAME"));
        cb.setTitle(rs.getString("TITLE"));
        cb.setPmId(rs.getLong("ID"));
      }
      
      ps.clearParameters();
      ps = conn.prepareStatement("select * from CONTACTS where PRM_ID=?");
      ps.setLong(1, cb.getPmId());
      
      rs = ps.executeQuery();
      while(rs.next())
      {
        if(rs.getInt("CONTACT_TYPE_CODE")==2)
        {
          cb.setPhone(rs.getString("CONTACT_VALUE"));     
          cb.setPhoneId(rs.getLong("ID"));
        }
        else if(rs.getInt("CONTACT_TYPE_CODE")==3)
        {
          cb.setEmail(rs.getString("CONTACT_VALUE"));
          cb.setEmailId(rs.getLong("ID"));
        }
        else if(rs.getInt("CONTACT_TYPE_CODE")==8)
        {
          cb.setPhoneext(rs.getString("CONTACT_VALUE"));
          cb.setPhoneextId(rs.getLong("ID"));
        }
      }
     
    }
    catch(Exception e){
      System.err.println("error getProjectManager() " + e.getMessage().toString());
    }finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return cb;
  }
  
  
    public CnApplicationBean getCnProjectManager(CnApplicationBean cb)
    {     
      try {
        conn = initializeConn();
        
        ps = conn.prepareStatement("select * from PROJECT_MANAGERS where ID in "+
          " (select PRM_ID from GRANTS where ID=?)");
        ps.setLong(1, cb.getGrantId());
        
        rs = ps.executeQuery();
        while(rs.next())
        {
          cb.setManagerFirstName(rs.getString("FNAME"));
          cb.setManagerLastName(rs.getString("LNAME"));
          cb.setManagerId(rs.getLong("ID"));
        }
        
        ps.clearParameters();
        ps = conn.prepareStatement("select * from CONTACTS where PRM_ID=?");
        ps.setLong(1, cb.getManagerId());
        
        rs = ps.executeQuery();
        while(rs.next())
        {
          if(rs.getInt("CONTACT_TYPE_CODE")==2)
          {
            cb.setManagerPhone(rs.getString("CONTACT_VALUE"));     
            cb.setManagerPhoneId(rs.getLong("ID"));
          }
          else if(rs.getInt("CONTACT_TYPE_CODE")==3)
          {
            cb.setManagerEmail(rs.getString("CONTACT_VALUE"));
            cb.setManagerEmailId(rs.getLong("ID"));
          }
          else if(rs.getInt("CONTACT_TYPE_CODE")==8)
          {
            cb.setManagerPhoneExt(rs.getString("CONTACT_VALUE"));
            cb.setManagerPhoneExtId(rs.getLong("ID"));
          }
        }       
      }
      catch(Exception e){
        System.err.println("error getCnProjectManager() " + e.getMessage().toString());
      }finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }    
      return cb;
    }

  public CoversheetBean getAdditionalContact(CoversheetBean cb, int staffTypeId)
  {      
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from GRANT_STAFFS where gra_id=? and st1_id=?");
      ps.setLong(1, cb.getGrantid());   
      ps.setInt(2, staffTypeId);
      rs = ps.executeQuery();
      while(rs.next())
      {
        cb.setRmofname(rs.getString("FNAME"));
        cb.setRmolname(rs.getString("LNAME"));
        cb.setRmotitle(rs.getString("TITLE"));
        cb.setRmoId(rs.getLong("ID"));
      }
            
      ps = conn.prepareStatement("select * from CONTACTS where GS_ID=?");
      ps.setLong(1, cb.getRmoId());      
      rs = ps.executeQuery();
      while(rs.next())
      {
        if(rs.getInt("CONTACT_TYPE_CODE")==2)
          cb.setRmophone(rs.getString("CONTACT_VALUE"));     
        else if(rs.getInt("CONTACT_TYPE_CODE")==3)
          cb.setRmoemail(rs.getString("CONTACT_VALUE"));
        else if(rs.getInt("CONTACT_TYPE_CODE")==8)
        {
          cb.setRmophoneext(rs.getString("CONTACT_VALUE"));
          cb.setRmophoneextId(rs.getLong("ID"));
        }
      }
     
    }
    catch(Exception e){
      System.err.println("error getAdditionalContact() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return cb;
  }  
  
  
  public int insertAdditionalContact(long grantid, UserBean userb, CoversheetBean cb, int staffTypeId)
  {  
    String user_id = userb.getUserid();
    int outcome = 0;
  
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select GS_SEQ.nextval from GRANT_STAFFS");
      rs = ps.executeQuery();
      while(rs.next())
        cb.setRmoId(rs.getLong(1));      
      
      ps = conn.prepareStatement("insert into GRANT_STAFFS (ID, FNAME, LNAME, TITLE, ACTIVE, "+
      " DATE_CREATED, CREATED_BY, GRA_ID, ST1_ID) values (?, ?,?, ?,'Y', sysdate, ?, ?, ?)");
      ps.setLong(1, cb.getRmoId());
      ps.setString(2, cb.getRmofname());
      ps.setString(3, cb.getRmolname());
      ps.setString(4, cb.getRmotitle());
      ps.setString(5, user_id);      
      ps.setLong(6, grantid);
      ps.setInt(7, staffTypeId);
      outcome = ps.executeUpdate();      
          
      ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, GS_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        
      if(cb.getRmophone()!=null && !cb.getRmophone().equals(""))
      {
        ps.setInt(1, 2);//type 2 is work phone
        ps.setString(2, cb.getRmophone() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();
      }      
      
      if(cb.getRmoemail()!=null && !cb.getRmoemail().equals(""))
      {
        ps.setInt(1, 3);//type 3 is email
        ps.setString(2, cb.getRmoemail() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getRmophoneext()!=null && !cb.getRmophoneext().equals(""))
      {
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getRmophoneext() );
        ps.setString(3, user_id);
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();
      }
                       
    }
    catch(Exception e) {System.err.println("error insertAdditionalContact() " + e.getMessage().toString()); }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }
  
  
  public int updateAdditionalContact(UserBean userb, CoversheetBean cb)
  {
    String userid = userb.getUserid();   
    int outcome = 0;  
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("update GRANT_STAFFS set FNAME=?, LNAME=?, "+
        " TITLE=?, DATE_MODIFIED=sysdate, MODIFIED_BY=? where ID=?");
      ps.setString(1, cb.getRmofname());
      ps.setString(2, cb.getRmolname());
      ps.setString(3, cb.getRmotitle());
      ps.setString(4, userid);
      ps.setLong(5, cb.getRmoId());
      outcome = ps.executeUpdate();            
      
      ps.clearParameters();
      ps = conn.prepareStatement("update CONTACTS set CONTACT_VALUE=?, DATE_MODIFIED=sysdate, "+
        " MODIFIED_BY=? where CONTACT_TYPE_CODE=? and GS_ID=?");
        
      if(cb.getRmophone()!=null && !cb.getRmophone().equals(""))
      {
        ps.setString(1, cb.getRmophone());
        ps.setString(2, userid);
        ps.setInt(3, 2);//type 2 for work phone
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getRmoemail()!=null && !cb.getRmoemail().equals(""))
      {
        ps.setString(1, cb.getRmoemail());
        ps.setString(2, userid);
        ps.setInt(3, 3);//type 3 for email
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();     
      }
      
      if(cb.getRmophoneextId()!=0)
      {
        ps.setString(1, cb.getRmophoneext());
        ps.setString(2, userid);
        ps.setInt(3, 8);//type 8 for phoneext
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();  
      }
      else if(cb.getRmophoneext()!=null && !cb.getRmophoneext().equals(""))
      {
        //need to insert new phoneext
        ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, GS_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getRmophoneext() );
        ps.setString(3, userid);
        ps.setLong(4, cb.getRmoId());
        outcome = ps.executeUpdate();        
      }      
    }
    catch(Exception e){
      System.err.println("error updateAdditionalContact() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }      
    return outcome;
  }
  
  
  /**
     *This method will insert "library director" info into GRANT_STAFFS table. Used for
     * construction starting 2016-17 per LWebb.  
     * @param grantid
     * @param userb
     * @param cb
     * @return
     */
  public int insertCnDirector(long grantid, UserBean userb, CnApplicationBean cb)
  {  
    int outcome = 0;
  
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select GS_SEQ.nextval from GRANT_STAFFS");
      rs = ps.executeQuery();
      while(rs.next())
        cb.setDirectorId(rs.getLong(1));      
      
      ps = conn.prepareStatement("insert into GRANT_STAFFS (ID, FNAME, LNAME, ACTIVE, "+
      " DATE_CREATED, CREATED_BY, GRA_ID, ST1_ID) values (?, ?,?, 'Y', sysdate, ?, ?, ?)");
      ps.setLong(1, cb.getDirectorId());
      ps.setString(2, cb.getDirectorFirstName());
      ps.setString(3, cb.getDirectorLastName());
      ps.setString(4, userb.getUserid());      
      ps.setLong(5, grantid);
      ps.setInt(6, 4);//see STAFF_TYPES table; 4=library director
      outcome = ps.executeUpdate();      
          
      ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, GS_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        
      if(cb.getDirectorPhone()!=null && !cb.getDirectorPhone().equals(""))
      {
        ps.setInt(1, 2);//type 2 is work phone
        ps.setString(2, cb.getDirectorPhone());
        ps.setString(3, userb.getUserid());
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();
      }      
      
      if(cb.getDirectorEmail()!=null && !cb.getDirectorEmail().equals(""))
      {
        ps.setInt(1, 3);//type 3 is email
        ps.setString(2, cb.getDirectorEmail() );
        ps.setString(3, userb.getUserid());
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getDirectorPhoneExt()!=null && !cb.getDirectorPhoneExt().equals(""))
      {
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getDirectorPhoneExt());
        ps.setString(3, userb.getUserid());
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();
      }
                       
    }
    catch(Exception e) {System.err.println("error insertCnDirector() " + e.getMessage().toString()); }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }
  
  
  
  /**
     * update construction library director info in GRANT_STAFFS table. new for 2016-17 FY
     * per lynne webb 2/24/16
     * @param userb
     * @param cb
     * @return
     */
  public int updateCnDirector(UserBean userb, CnApplicationBean cb)
  {
    int outcome = 0;  
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("update GRANT_STAFFS set FNAME=?, LNAME=?, "+
        " DATE_MODIFIED=sysdate, MODIFIED_BY=? where ID=?");
      ps.setString(1, cb.getDirectorFirstName());
      ps.setString(2, cb.getDirectorLastName());
      ps.setString(3, userb.getUserid());
      ps.setLong(4, cb.getDirectorId());
      outcome = ps.executeUpdate();            
      
      ps.clearParameters();
      ps = conn.prepareStatement("update CONTACTS set CONTACT_VALUE=?, DATE_MODIFIED=sysdate, "+
        " MODIFIED_BY=? where CONTACT_TYPE_CODE=? and GS_ID=?");
        
      if(cb.getDirectorPhone()!=null && !cb.getDirectorPhone().equals(""))
      {
        ps.setString(1, cb.getDirectorPhone());
        ps.setString(2, userb.getUserid());
        ps.setInt(3, 2);//type 2 for work phone
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();
      }
      
      if(cb.getDirectorEmail()!=null && !cb.getDirectorEmail().equals(""))
      {
        ps.setString(1, cb.getDirectorEmail());
        ps.setString(2, userb.getUserid());
        ps.setInt(3, 3);//type 3 for email
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();     
      }
      
      if(cb.getDirectorPhoneExtId()!=0)
      {
        ps.setString(1, cb.getDirectorPhoneExt());
        ps.setString(2, userb.getUserid());
        ps.setInt(3, 8);//type 8 for phoneext
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();  
      }
      else if(cb.getDirectorPhoneExt()!=null && !cb.getDirectorPhoneExt().equals(""))
      {
        //need to insert new phoneext
        ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, GS_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, cb.getDirectorPhoneExt());
        ps.setString(3, userb.getUserid());
        ps.setLong(4, cb.getDirectorId());
        outcome = ps.executeUpdate();        
      }      
    }
    catch(Exception e){
      System.err.println("error updateCnDirector() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }      
    return outcome;
  }
  
  
  /**
     *query for construction library director info from GRANT_STAFFS table
     * @param cb
     * @return
     */
  public CnApplicationBean getCnDirector(CnApplicationBean cb)
  {      
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from GRANT_STAFFS where gra_id=? and st1_id=4");//STAFF_TYPE=4=library director
      ps.setLong(1, cb.getGrantId());   
      rs = ps.executeQuery();
      while(rs.next())
      {
        cb.setDirectorFirstName(rs.getString("FNAME"));
        cb.setDirectorLastName(rs.getString("LNAME"));
        cb.setDirectorId(rs.getLong("ID"));
      }
            
      ps = conn.prepareStatement("select * from CONTACTS where GS_ID=?");
      ps.setLong(1, cb.getDirectorId());      
      rs = ps.executeQuery();
      while(rs.next())
      {
        if(rs.getInt("CONTACT_TYPE_CODE")==2)
          cb.setDirectorPhone(rs.getString("CONTACT_VALUE"));     
        else if(rs.getInt("CONTACT_TYPE_CODE")==3)
          cb.setDirectorEmail(rs.getString("CONTACT_VALUE"));
        else if(rs.getInt("CONTACT_TYPE_CODE")==8)
        {
          cb.setDirectorPhoneExt(rs.getString("CONTACT_VALUE"));
          cb.setDirectorPhoneExtId(rs.getLong("ID"));
        }
      }
     
    }
    catch(Exception e){
      System.err.println("error getCnDirector() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return cb;
  }  
  
  
}