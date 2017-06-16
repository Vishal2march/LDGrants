/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AuthorizationsDBbean.java
 * Creation/Modification History  :
 *
 * SH   6/26/07      Created
 *
 * Description
 * This class will handle all selects and inserts into the authorizations table.  It
 * does the inserts for the institutional authorization and final signoff pages.
 *****************************************************************************/
package mypackage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import java.text.Format;
import java.text.SimpleDateFormat;

import oracle.jdbc.OracleDriver;


public class AuthorizationsDBbean 
{
  public AuthorizationsDBbean()
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
   * This method will select all authorization records from the table for the 
   * given grant id. All records stored in a vector set to the session.
   * @return 
   * @param grantid
   */
  public Vector getGrantAuthorizations(long grantid)
  {
    Vector results = new Vector();
    Format formathour = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from AUTHORIZATIONS where GRA_ID=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        AuthorizationBean ab = new AuthorizationBean();
        ab.setName(rs.getString("NAME"));
        ab.setTitle(rs.getString("TITLE"));
        ab.setAuthdate(rs.getDate("AUTH_DATE"));
        ab.setUser(rs.getString("AUT_USER"));
        ab.setVersion(rs.getString("VERSION"));
        ab.setGrantid(rs.getLong("GRA_ID"));
          
        if(rs.getDate("AUTH_DATE")!=null)
          ab.setAuthdateStr(formathour.format(rs.getTimestamp("AUTH_DATE")));
        
        
        results.add(ab);//add this authorization record to vector
      }
           
    }
     catch(Exception e){
      System.err.println("error getGrantAuthorization() " + e.getMessage().toString());
    }  
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }    
    return results;

  }
  
  
  
  /**
     *for use with stateaid -> this will query for director assurance.
     * @param grantid
     * @return
     */
  public AuthorizationBean getGrantAssurance(long grantid)
  {
    AuthorizationBean ab = new AuthorizationBean();    
    Format formatter = new SimpleDateFormat("MM/dd/yyyy");
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from AUTHORIZATIONS where GRA_ID=? and VERSION='Assurance' ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {        
        ab.setId(rs.getLong("ID"));
        ab.setName(rs.getString("NAME"));
        ab.setTitle(rs.getString("TITLE"));
        ab.setAuthdate(rs.getDate("AUTH_DATE"));
        ab.setUser(rs.getString("AUT_USER"));
        ab.setVersion(rs.getString("VERSION"));
        //ab.setGrantid(rs.getLong("GRA_ID"));        
        ab.setAuthdateStr(formatter.format(rs.getDate("AUTH_DATE")));          
      }
      ab.setGrantid(grantid);
           
    }catch(Exception e){
      System.err.println("error getGrantAssurance() " + e.getMessage().toString());
    }  
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }    
    return ab;
  }
  
  /**
   * This method inserts a record into the authorizations table for the final report signoff,
   * only if a record does not already exit
   * @return 
   * @param grantid
   * @param userb
   */
   public int updateFinalSignoff(long grantid, UserBean userb)
  {        
    int outcome =1;
             
      try{      
        //get name of library director for grant
        String dirName = "";
        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean dirBean = odb.getOfficerAssigned(grantid, "Library Director");     
        dirName = dirBean.getFname() + " " + dirBean.getLname();     
            
        conn = initializeConn();           
        ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?) ");
       
        ps.setLong(1, grantid);
        ps.setString(2, dirName);
        ps.setString(3, "Library Director");//title
        ps.setString(4, userb.getUserid());//user
        ps.setString(5, "FinalSignoff");//version
        ps.setString(6, userb.getUserid());      
        outcome = ps.executeUpdate();     
        
      }catch(Exception e){
        System.err.println("error updateFinalSignoff() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }        
    return outcome;
  }
  
  
   /*public int updateInstAuthorization(HttpServletRequest request)
  {
    int outcome = 1;
    HttpSession sess = request.getSession();
    
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        //see if lib dir authorized the app, or pres officer authorized the app or both    
        if(request.getParameter("PresOfficer") != null)
        {     
          //preservation officer box was checked so add authorization record
          outcome = insertPOAuthorization(grantid, userb);
        }
        
        if(request.getParameter("LibDir") !=null)
        {
          //library director box was checked so add auth record
          outcome = insertLDAuthorization(grantid, userb);      
        }
        
        if(request.getParameter("ResFnd") !=null)
        {
          //research found. box was checked so add auth record
          outcome = insertRFAuthorization(grantid, userb);
        }
    }catch(Exception e){System.out.println(e.getMessage()); }
    
    return outcome;
  }*/

  
  /**
   * This method inserts an instutional authorization record for the preservation officer.
   * @return 
   * @param grantid
   * @param userb
   */
   public int insertPOAuthorization(long grantid, UserBean userb)
  {  
    int outcome =0;
    
    try{      
      //get name of preservation officer for grant
      String presName = "";
      OfficerDBbean odb = new OfficerDBbean();
      OfficerBean presBean = odb.getOfficerAssigned(grantid, "Preservation Officer");     
      presName = presBean.getFname() + " " + presBean.getLname();     
      
      conn = initializeConn();        
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?) ");       
      ps.setLong(1, grantid);
      ps.setString(2, presName);
      ps.setString(3, "Preservation Officer");//title
      ps.setString(4, userb.getUserid());//user
      ps.setString(5, "InstAuth");//version
      ps.setString(6, userb.getUserid());
      
      outcome = ps.executeUpdate();  
      
    }
     catch(Exception e){
      System.err.println("error insertPOAuthorization() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return outcome;
  }
  
  
  /**
   * This method inserts institutional authorization record for the library director.
   * 2/4/16 reuse to insert system director for literacy cert form
   * @return 
   * @param grantid
   * @param userb
   */
  public int insertLDAuthorization(long grantid, UserBean userb)
  {
    int outcome=0;
    
    try{     
      //get name of library director for grant
      String dirName = "";
      OfficerDBbean odb = new OfficerDBbean();
      OfficerBean dirBean = odb.getOfficerAssigned(grantid, "Library Director");     
      dirName = dirBean.getFname() + " " + dirBean.getLname();     
      
      conn = initializeConn();            
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?) ");
       
      ps.setLong(1, grantid);
      ps.setString(2, dirName);
      ps.setString(3, "Library Director");//title
      ps.setString(4, userb.getUserid());//user
      ps.setString(5, "InstAuth");//version
      ps.setString(6, userb.getUserid());
      
      outcome = ps.executeUpdate();     
    }
     catch(Exception e){
      System.err.println("error insertLDAuthorization() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
 
  }

  /**
   * This method will get only the cooperative agreement records from authorizations
   * table, for this inst only.  This is used to show the participating library if they
   * have completed their coop agreement.
   * @return 
   * @param grantid
   * @param instid
   */
  public Vector getInstCoopAgreements(long grantid, long instid)
  {  
    Vector results = new Vector();
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from AUTHORIZATIONS where GRA_ID=? and VERSION= " +
       " 'CoopAgreement' and AFFILIATION=?");
      
      ps.setLong(1, grantid);
      ps.setLong(2, instid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        AuthorizationBean ab = new AuthorizationBean();
        ab.setName(rs.getString("NAME"));
        ab.setTitle(rs.getString("TITLE"));
        ab.setAuthdate(rs.getDate("AUTH_DATE"));
        ab.setUser(rs.getString("AUT_USER"));
        ab.setVersion(rs.getString("VERSION"));
        ab.setGrantid(rs.getLong("GRA_ID"));
        
        results.add(ab);
      }            
            
    }catch(Exception e){
      System.err.println("error getInstCoopAgreements() " + e.getMessage().toString());
    }  
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }    
    return results; 
  
  }

  /*public int updateCoopAgreement(HttpServletRequest request, long instid)
  {
    int outcome =1;
    HttpSession sess = request.getSession();
    
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid=Long.parseLong(grantnum);
        UserBean userb = (UserBean) sess.getAttribute("lduser");
          
        if(request.getParameter("PresOfficer") != null)
        {     
          //preservation officer box was checked so add authorization record
          outcome = insertPOcoopAgree(grantid, userb, instid);
        }
        
        if(request.getParameter("LibDir") !=null)
        {
          //library director box was checked so add auth record
          outcome = insertLDcoopAgree(grantid, userb, instid);      
        }
    }catch(Exception e)
    {System.out.println(e.getMessage());}
    
    return outcome;

  }*/

  public int insertPOcoopAgree(long grantid, UserBean userb, long instid)
  {
    int outcome = 0;
    
    try{      
      //get name of preservation officer for grant
      String presName = "";
      OfficerDBbean odb = new OfficerDBbean();
      OfficerBean presBean = odb.getOfficerForInst(instid, "16");     
      presName = presBean.getFname() + " " + presBean.getLname();     
      
      conn = initializeConn();            
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY, AFFILIATION) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?,?) ");       
      ps.setLong(1, grantid);
      ps.setString(2, presName);
      ps.setString(3, "Preservation Officer");//title
      ps.setString(4, userb.getUserid());//user
      ps.setString(5, "CoopAgreement");//version
      ps.setString(6, userb.getUserid());
      ps.setLong(7, instid);
      
      outcome = ps.executeUpdate();  
      
    } catch(Exception e){
      System.err.println("error insertPOcoopAgree() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return outcome;
  }



  public int insertLDcoopAgree(long grantid, UserBean userb, long instid)
  {
    int outcome = 0;
    
    try{     
      //get name of library director for grant
      String dirName = "";
      OfficerDBbean odb = new OfficerDBbean();
      OfficerBean dirBean = odb.getOfficerForInst(instid, "1");     
      dirName = dirBean.getFname() + " " + dirBean.getLname();     
      
      conn = initializeConn();      
      
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY, AFFILIATION) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?,?) ");
       
      ps.setLong(1, grantid);
      ps.setString(2, dirName);
      ps.setString(3, "Library Director");//title
      ps.setString(4, userb.getUserid());//user
      ps.setString(5, "CoopAgreement");//version
      ps.setString(6, userb.getUserid());
      ps.setLong(7, instid);      
      
      outcome = ps.executeUpdate();      
    }
     catch(Exception e) {
      System.err.println("error insertLDcoopAgree() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
  }

  public int insertRFAuthorization(long grantid, UserBean userb)
  {
    int outcome=0;
    
    try{     
      conn = initializeConn();      
      
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) values (authorization_seq.nextval, ?,?,?,sysdate, "+
        " ?,?,sysdate,?) ");
       
      ps.setLong(1, grantid);
      ps.setString(2, "SUNY");//authorizor's name
      ps.setString(3, "Research Foundation");//title
      ps.setString(4, userb.getUserid());//user
      ps.setString(5, "InstAuth");//version
      ps.setString(6, userb.getUserid());
      
      outcome = ps.executeUpdate();     
    }
     catch(Exception e){
      System.err.println("error insertRFAuthorization() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
  }

  /**
   * Method will get all suny inst id's in file and compare inst id of logged in user to see
   * if user represents a suny institution
   * @return true or false
   * @param is - InputStream with path to the sunyinstid.txt file. Must be passed in b/c can only load
   * path from a jsp or servlet.  Would not work with classLoader.getResourceAsStream()
   * @param userInstId
   */
  public boolean isSunyInstitution(long userInstId, InputStream is)
  {  
    boolean isSuny = false;   
    
    try{
      ArrayList fileLines = new ArrayList();           
      
      BufferedReader bis = null; 
      if(is !=null)
      {
        bis = new BufferedReader(new InputStreamReader(is));
        
        String line="";
        while((line=bis.readLine())!=null)
        {
         fileLines.add(line);
        }
      }      
      if(is!=null)
        is.close();
      if(bis!=null)
        bis.close();        
        
      
      //test each suny inst id in filelines array
      String userInst = String.valueOf(userInstId);
      for(int i=0; i<fileLines.size(); i++)
      {
        String sunyinstid = (String) fileLines.get(i);        
        if(sunyinstid.equalsIgnoreCase(userInst))
          isSuny = true;
      }      
    
    }catch(Exception e){
      System.err.println("error isSunyInstitution() " + e.getMessage().toString());
    }  
    finally
    {
      return isSuny;
    }

  }
  
  
  
  /**
     * method will insert into authorizations table a 'assurance' record for stateaid (cjh/nyhs)
     * @param ab
     * @param userb
     * @return
     */
  public int insertAssurance(AuthorizationBean ab, UserBean userb)
  {
    int outcome=0;    
    try{     
      conn = initializeConn();            
      ps = conn.prepareStatement("insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) values (authorization_seq.nextval, ?, ?, ?, to_date(?, 'mm/dd/yy'), "+
        " ?,?,sysdate,?) ");
       
      ps.setLong(1, ab.getGrantid());
      ps.setString(2, ab.getName());
      ps.setString(3, ab.getTitle());
      ps.setString(4, ab.getAuthdateStr());
        
      ps.setString(5, userb.getUserid());//auth user
      ps.setString(6, ab.getVersion());//version
      ps.setString(7, userb.getUserid());
      
      outcome = ps.executeUpdate();     
    }
     catch(Exception e){
      System.err.println("error insertAssurance() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }
  
  
  public int updateAssurance(AuthorizationBean ab, UserBean userb)
  {
    int outcome=0;    
    try{     
      conn = initializeConn();            
      ps = conn.prepareStatement("update AUTHORIZATIONS set NAME=?, TITLE=?, AUTH_DATE=to_date(?, 'mm/dd/yy'), "+
        " AUT_USER=?, VERSION=?, DATE_MODIFIED=sysdate, MODIFIED_BY=? where  id=?");
       
      ps.setString(1, ab.getName());
      ps.setString(2, ab.getTitle());
      ps.setString(3, ab.getAuthdateStr());        
      ps.setString(4, userb.getUserid());//auth user
      ps.setString(5, ab.getVersion());//version
      ps.setString(6, userb.getUserid());
      ps.setLong(7, ab.getId());
      
      outcome = ps.executeUpdate();     
    }
     catch(Exception e){
      System.err.println("error updateAssurance() " + e.getMessage().toString());
    }  
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }
  
  
}