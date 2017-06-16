/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerDBbean.java
 * Creation/Modification History  :
 *
 * SH   7/25/07      Created
 *
 * Description
 * This bean has methods to add, update, delete and select reviewers from the
 * reviewers table in db.  It also has methods to add/update/select from the 
 * reviewer_assign_max table where the reviewer can request how many propsals they
 * are willing to review each year.
 *****************************************************************************/
package mypackage;

import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdbc.OracleDriver;


public class ReviewerDBbean 
{
  public ReviewerDBbean()
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

  public int addReviewer(ReviewerBean rb, UserBean userb)
  {
    int outcome = 0;  
    try{
      conn = initializeConn();
      //add the userid as all lowercase so it can be queried on later when reviewer logs in
      ps = conn.prepareStatement("insert into REVIEWERS (ID, SALUTATION, FNAME, MNAME, LNAME, "+
      " TITLE, AFFILIATION, INTEREST, ACTIVE, DATE_CREATED, CREATED_BY, USER_ID, "+
      " rao_flag, govt_emp_flag, \"COMMENT\", SSN, vendor_num ) "+
      " values (PM_REV_SEQ.nextval, ?,?,?,?,?,?,?,'Y',SYSDATE,?, lower(?),?,?, ?,?, ?) ");
      ps.setString(1, rb.getSalutation());
      ps.setString(2, rb.getFname() );
      ps.setString(3, rb.getMname());
      ps.setString(4, rb.getLname());
      ps.setString(5, rb.getTitle());
      ps.setString(6, rb.getAffiliation());
      ps.setString(7, rb.getInterest());
      ps.setString(8, userb.getUserid());//CREATED_BY
      ps.setString(9, rb.getUsername());//USER_ID of the reviewer from ldap     
      ps.setBoolean(10, rb.isRao());
      ps.setBoolean(11, rb.isGovtemp());
      ps.setString(12, rb.getComment());
      ps.setString(13, rb.getSsn());
      ps.setString(14, rb.getVendornum());
      outcome = ps.executeUpdate();
            
      ps.clearParameters();
      ps = conn.prepareStatement("select max(ID) from REVIEWERS");
      rs = ps.executeQuery();
      while(rs.next())
        rb.setRevid(rs.getLong(1));      
      
      ps.clearParameters();
      ps = conn.prepareStatement("insert into reviewer_programs (id, fc_code, rev_id, created_by, "+
      " date_created) values (rp_seq.nextval, ?,?,?,sysdate)");
      
      if(rb.coordinated)
      {
        ps.setInt(1, 7);//fund code coordinated
        ps.setLong(2, rb.getRevid());
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }      
      if(rb.lgrmif)
      {
        ps.setInt(1, 80);//fund code lgrmif
        ps.setLong(2, rb.getRevid());
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }
      if(rb.discretionary)
      {
        ps.setInt(1, 5);//fund code discretionary
        ps.setLong(2, rb.getRevid());
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }      
      if(rb.fliteracy)
      {
        ps.setInt(1, 42);//fund code fl
        ps.setLong(2, rb.getRevid());
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }      
      if(rb.aliteracy)
      {
        ps.setInt(1, 40);//fund code al
        ps.setLong(2, rb.getRevid());
        ps.setString(3, userb.getUserid());
        outcome = ps.executeUpdate();
      }
      
      
      ps.clearParameters();
      ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, REV_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        
      ps.setInt(1, 2);//type 2 is work phone
      ps.setString(2, rb.getPhone() );
      ps.setString(3, userb.getUserid());
      ps.setLong(4, rb.getRevid());
      outcome = ps.executeUpdate();
      
      ps.setInt(1, 3);//type 3 is email
      ps.setString(2, rb.getEmail() );
      ps.setString(3, userb.getUserid());
      ps.setLong(4, rb.getRevid());
      outcome = ps.executeUpdate();
      
      if(rb.getPhoneext()!=null && !rb.getPhoneext().equals(""))
      {
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, rb.getPhoneext() );
        ps.setString(3, userb.getUserid());
        ps.setLong(4, rb.getRevid());
        outcome = ps.executeUpdate();
      }
      
      ps.clearParameters();
      ps = conn.prepareStatement("insert into ADDRESSES (ID, ADDR_LINE1, CITY, STATE, ZIPCODE, "+
        " IS_ACTIVE, DATE_CREATED, CREATED_BY, REV_ID, ADDR_TYPE_CODE) values (ADDRESS_SEQ.nextval, "+
        " ?,?,?,?,'Y',sysdate,?,?,'2')  ");
      ps.setString(1, rb.getAddress());
      ps.setString(2, rb.getCity());
      ps.setString(3, rb.getState());
      ps.setString(4, rb.getZipcode());
      ps.setString(5, userb.getUserid());
      ps.setLong(6, rb.getRevid());
      outcome = ps.executeUpdate();
      
    }
    catch(Exception e){
      System.err.println("error addReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;  
  }


  /**
   * Get all reviewers from db, filtered by grant program 
   * @return 
   * @param fundCodeList
   */
  public Vector getAllReviewers(String fundCodeList)
  {
    Vector allRev = new Vector();    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from reviewers where id in (select rev_id from "+
      " reviewer_programs where fc_code in ("+ fundCodeList +") ) order by lname");
      rs = ps.executeQuery();
      
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
        
        allRev.add(rb);
      }            
    }
    catch(Exception e){
      System.err.println("error getAllReviewers() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return allRev;
  }

  /**
   * Method will find all reviewer info in REVIEWERS table based on reviewer id
   * @return ReveiwerBean containing all info
   * @param revid
   * @param retrieveNumber -if true, select decrypted ssn for LG reviewers
   */
  public ReviewerBean getReviewerInfo(long revid, boolean retrieveNumber)
  {
    ReviewerBean rb = new ReviewerBean();
    try{
      conn = initializeConn();
      
      if(retrieveNumber)//get decrypted ssn
      {
        ps = conn.prepareStatement("select id, salutation, fname, mname, lname, title, "+
        "rao_flag, govt_emp_flag, affiliation, interest, active, user_id, \"COMMENT\", vendor_num, "+
        " SECUSR.SED_DATA_SECURE.DECRYPT(SSN) as ssnum from reviewers where id=?");
        ps.setLong(1, revid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {        
          rb.setRevid(rs.getLong("ID"));
          rb.setSalutation(rs.getString("SALUTATION"));
          rb.setFname(rs.getString("FNAME"));
          rb.setMname(rs.getString("MNAME"));
          rb.setLname(rs.getString("LNAME"));
          rb.setTitle(rs.getString("TITLE"));
          rb.setAffiliation(rs.getString("AFFILIATION"));
          rb.setInterest(rs.getString("INTEREST"));        
          rb.setActive(rs.getString("ACTIVE"));
          rb.setUsername(rs.getString("USER_ID"));
          rb.setComment(rs.getString("COMMENT"));
          rb.setRao(rs.getBoolean("rao_flag"));
          rb.setGovtemp(rs.getBoolean("govt_emp_flag"));
          rb.setSsn(rs.getString("ssnum"));
          rb.setVendornum(rs.getString("vendor_num"));
        }
      }
      else
      {
        ps = conn.prepareStatement("select * from reviewers where id=?");
        ps.setLong(1, revid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {        
          rb.setRevid(rs.getLong("ID"));
          rb.setSalutation(rs.getString("SALUTATION"));
          rb.setFname(rs.getString("FNAME"));
          rb.setMname(rs.getString("MNAME"));
          rb.setLname(rs.getString("LNAME"));
          rb.setTitle(rs.getString("TITLE"));
          rb.setAffiliation(rs.getString("AFFILIATION"));
          rb.setInterest(rs.getString("INTEREST"));        
          rb.setActive(rs.getString("ACTIVE"));
          rb.setUsername(rs.getString("USER_ID"));
          rb.setComment(rs.getString("COMMENT"));
          rb.setRao(rs.getBoolean("rao_flag"));
          rb.setGovtemp(rs.getBoolean("govt_emp_flag"));
        }
      }
            
      ps.clearParameters();
      ps = conn.prepareStatement("select * from CONTACTS where REV_ID=?");
      ps.setLong(1, rb.getRevid());
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        if(rs.getInt("CONTACT_TYPE_CODE")==2)
        {
          rb.setPhone(rs.getString("CONTACT_VALUE"));          
        }
        else if(rs.getInt("CONTACT_TYPE_CODE")==3)
        {
          rb.setEmail(rs.getString("CONTACT_VALUE"));
        }
        else if(rs.getInt("CONTACT_TYPE_CODE")==8)
        {
          rb.setPhoneext(rs.getString("CONTACT_VALUE"));
          rb.setPhoneextId(rs.getLong("ID"));
        }
      }
          
      ps = conn.prepareStatement("select * from ADDRESSES where REV_ID=?");
      ps.setLong(1, rb.getRevid());
      rs = ps.executeQuery();      
      while(rs.next())
      {
        rb.setAddress(rs.getString("ADDR_LINE1"));
        rb.setCity(rs.getString("CITY"));
        rb.setState(rs.getString("STATE"));
        rb.setZipcode(rs.getString("ZIPCODE"));        
      }      
      
          
      ps = conn.prepareStatement("select * from reviewer_programs where rev_id=?");
      ps.setLong(1, rb.getRevid());
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int fundcode = rs.getInt("fc_code");
        if(fundcode==80)
          rb.setLgrmif(true);
        else if(fundcode==7)
          rb.setCoordinated(true);
        else if(fundcode==5)
          rb.setDiscretionary(true);
        else if(fundcode==40)
          rb.setAliteracy(true);
        else if(fundcode==42)
          rb.setFliteracy(true);
      }
           
    }
    catch(Exception e){
      System.err.println("error getReviewerInfo() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return rb;
  }

  public int updateReviewer(ReviewerBean rb, UserBean userb, boolean revprograms)
  {
    int outcome = 0;  
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("update REVIEWERS set SALUTATION=?, FNAME=?, MNAME=?, LNAME=?, "+
          " TITLE=?, AFFILIATION=?, INTEREST=?, ACTIVE=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, "+
          " USER_ID= lower(?), \"COMMENT\"=?, SSN=?, rao_flag=?, govt_emp_flag=?, vendor_num=? where ID=? ");
      ps.setString(1, rb.getSalutation());
      ps.setString(2, rb.getFname() );
      ps.setString(3, rb.getMname());
      ps.setString(4, rb.getLname());
      ps.setString(5, rb.getTitle());
      ps.setString(6, rb.getAffiliation());
      ps.setString(7, rb.getInterest());
      ps.setString(8, rb.getActive());
      ps.setString(9, userb.getUserid());//MODIFIED_BY
      ps.setString(10, rb.getUsername());//USER_ID of reviewer from ldap
      ps.setString(11, rb.getComment());
      ps.setString(12, rb.getSsn());
      ps.setBoolean(13, rb.isRao());
      ps.setBoolean(14, rb.isGovtemp());
      ps.setString(15, rb.getVendornum());
      ps.setLong(16, rb.getRevid());//ID primary key of reviewer table      
      outcome = ps.executeUpdate();
  
  
      ps.clearParameters();
      ps = conn.prepareStatement("update CONTACTS set CONTACT_VALUE=?, "+
        " DATE_MODIFIED=sysdate, MODIFIED_BY=? where REV_ID=? and CONTACT_TYPE_CODE=?");        
      
      ps.setString(1, rb.getPhone() );
      ps.setString(2, userb.getUserid());
      ps.setLong(3, rb.getRevid());
      ps.setInt(4, 2);//type 2 is work phone
      outcome = ps.executeUpdate();      
      
      ps.setString(1, rb.getEmail() );
      ps.setString(2, userb.getUserid());
      ps.setLong(3, rb.getRevid());
      ps.setInt(4, 3);//type 3 is email
      outcome = ps.executeUpdate();
      
      if(rb.getPhoneextId()!=0)
      {
        ps.setString(1, rb.getPhoneext());
        ps.setString(2, userb.getUserid());
        ps.setLong(3, rb.getRevid());
        ps.setInt(4, 8);//type 8 is phoneext
        outcome = ps.executeUpdate();
      }
      else if(rb.getPhoneext()!=null && !rb.getPhoneext().equals(""))
      {
        //need to insert new phoneext
        ps = conn.prepareStatement("insert into CONTACTS (ID, CONTACT_TYPE_CODE, CONTACT_VALUE, "+
        " DATE_CREATED, CREATED_BY, REV_ID) values (CONTACT_SEQ.nextval, ?,?,sysdate,?,?)");
        ps.setInt(1, 8);//type 8 is phoneext
        ps.setString(2, rb.getPhoneext() );
        ps.setString(3, userb.getUserid());
        ps.setLong(4, rb.getRevid());
        outcome = ps.executeUpdate();        
      }
      
      ps.clearParameters();
      ps = conn.prepareStatement("update ADDRESSES set ADDR_LINE1=?, CITY=?, STATE=?, ZIPCODE=?, "+
        " DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where REV_ID=? ");
      ps.setString(1, rb.getAddress());
      ps.setString(2, rb.getCity());
      ps.setString(3, rb.getState());
      ps.setString(4, rb.getZipcode());
      ps.setString(5, userb.getUserid());
      ps.setLong(6, rb.getRevid());
      outcome = ps.executeUpdate();
            
    }
    catch(Exception e){
      System.err.println("error updateReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    
    if(revprograms)//only admin can update reviewer programs
        updateReviewerPrograms(rb, userb);        
        
    return outcome;
  }
  
  
  public void updateReviewerPrograms(ReviewerBean rb, UserBean userb)
  {
  
    try{    
      //get any grant programs the reviewer is registered for
      boolean hasCoord = false;
      boolean hasDisc = false;
      boolean hasFl = false;
      boolean hasAl = false;
      
      conn = initializeConn();
      ps = conn.prepareStatement("select * from reviewer_programs where rev_id=? and fc_code=?");
     
      ps.setLong(1, rb.getRevid());
      ps.setInt(2, 7);
      rs = ps.executeQuery();
      while(rs.next()){  hasCoord = true;  }
      
      ps.setLong(1, rb.getRevid());
      ps.setInt(2, 5);
      rs = ps.executeQuery();
      while(rs.next()){  hasDisc = true;  }
      
      ps.setLong(1, rb.getRevid());
      ps.setInt(2, 40);
      rs = ps.executeQuery();
      while(rs.next()){  hasAl = true;  }
      
      ps.setLong(1, rb.getRevid());
      ps.setInt(2, 42);
      rs = ps.executeQuery();
      while(rs.next()){  hasFl = true;  }
      Close(conn);
      Close(rs);
      Close(ps);
      
      //update grant programs with new data entry values
      if(rb.coordinated)//coord box checked
      {
        if(!hasCoord)
          addReviewerProgram(7, rb.getRevid(), userb);
      }
      else//coord box not checked
      {
        if(hasCoord)
          deleteReviewerProgram(7, rb.getRevid());
      }
      
      if(rb.discretionary)//disc box checked
      {
        if(!hasDisc)
          addReviewerProgram(5, rb.getRevid(), userb);
      }
      else//disc box not checked
      {
        if(hasDisc)
          deleteReviewerProgram(5, rb.getRevid());
      }
      
      if(rb.fliteracy)//fl box checked
      {
        if(!hasFl)
          addReviewerProgram(42, rb.getRevid(), userb);
      }
      else//fl box not checked
      {
        if(hasFl)
          deleteReviewerProgram(42, rb.getRevid());
      }
      
      if(rb.aliteracy)//al box checked
      {
        if(!hasAl)
          addReviewerProgram(40, rb.getRevid(), userb);
      }
      else//al box not checked
      {
        if(hasAl)
          deleteReviewerProgram(40, rb.getRevid());
      }
    
    }catch(Exception e){
      System.err.println("error updateReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }

  }

  public int deleteReviewer(int id)
  {
    int outcome = 0;
    
    try{
      conn = initializeConn();
      
      //Need to delete from contacts and addr before deleting reference in reviewers
      ps = conn.prepareStatement("delete from ADDRESSES where REV_ID=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();
      
      ps.clearParameters();
      ps = conn.prepareStatement("delete from CONTACTS where REV_ID=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();
      
      ps.clearParameters();
      ps = conn.prepareStatement("delete from REVIEWER_PROGRAMS where REV_ID=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();
      
      ps.clearParameters();
      ps = conn.prepareStatement("delete from REVIEWERS where ID=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();
    }
    catch(Exception e){
      System.err.println("error deleteReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    
    return outcome;
  }

  /**
   * This method will search for the reviewer's name from ldap in the ldgrants reviewers
   * table. If found, then reviewer can proceed to home page and continue with tasks, and
   * additional reviewer info is stored in userbean in session.
   * @return 
   * @param request
   */
  public ReviewerBean findReviewerInfo(HttpServletRequest request)
  {
    HttpSession sess = request.getSession();
    ReviewerBean rb = new ReviewerBean();
    boolean foundrev = false;
    
    try {
      //use the user bean to store the additional REVIEWER attributes in session
      UserBean userb = (UserBean) sess.getAttribute("lduser");
            
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from reviewers where user_id = lower(?) ");
      ps.setString(1, userb.getUserid());
      rs = ps.executeQuery();
      
      while(rs.next())
      {        
        rb.setRevid(rs.getLong("ID"));
        rb.setSalutation(rs.getString("SALUTATION"));
        rb.setFname(rs.getString("FNAME"));
        rb.setMname(rs.getString("MNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setTitle(rs.getString("TITLE"));
        rb.setAffiliation(rs.getString("AFFILIATION"));
        rb.setInterest(rs.getString("INTEREST"));        
        rb.setActive(rs.getString("ACTIVE"));
        rb.setUsername(rs.getString("USER_ID"));
        rb.setRao(rs.getBoolean("rao_flag"));
        rb.setGovtemp(rs.getBoolean("govt_emp_flag"));
        foundrev = true;
      }
      rb.setReviewerFound(foundrev);
      
      ps.clearParameters();
      ps = conn.prepareStatement("select * from CONTACTS where REV_ID=?");
      ps.setLong(1, rb.getRevid());
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        if(rs.getString("CONTACT_TYPE_CODE").equals("3"))
          rb.setEmail(rs.getString("CONTACT_VALUE"));
      }
     
     //update the userbean in session with the reviewers info
      if(userb!=null)
      {
        userb.setRevid(rb.getRevid());
        userb.setFname(rb.getFname());
        userb.setLname(rb.getLname());
        userb.setEmail(rb.getEmail());
        userb.setReviewerfound(foundrev);
        userb.setIsrao(rb.isRao());
        userb.setIsgovtemp(rb.isGovtemp());
        sess.setAttribute("lduser", userb);
      }            
      
    }catch(Exception e) {
      System.err.println("error findReviewerInfo() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }    
    return rb;
  }
  
  

  public int addReviewerMaxAssign(ReviewAssignMaxBean rb)
  {    
    int outcome =0;
    
    try{
      conn = initializeConn();      
     
      ps = conn.prepareStatement("insert into GRANT_ASSIGN_MAXES (ID, REV_ID, FY_CODE, NUM_ACCEPTED, "+
        " GRANT_PROGRAM, DATE_CREATED, CREATED_BY, DESCRIPTION) values "+
        " (GRA_ASSIGN_MAX_SEQ.nextval,?,?,?,?,sysdate,?, ?) ");
      
      ps.setLong(1, rb.getRevid());
      ps.setInt(2, rb.getFycode());
      ps.setInt(3, rb.getNumaccepted());
      ps.setString(4, rb.getGrantprogram());
      ps.setString(5, rb.getUserid());
      ps.setString(6, rb.getDescrip());
      outcome = ps.executeUpdate();              
      
    }catch(Exception e){
      System.err.println("error addReviewerMaxAssign() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
  
    return outcome;
  }

  public Vector getGrantAssignMax(long revid)
  {
    Vector allRequests = new Vector();    
    try{
      conn = initializeConn();      
      
      ps = conn.prepareStatement("select NUM_ACCEPTED, GRANT_PROGRAM, GRANT_ASSIGN_MAXES.DESCRIPTION, "+
      " FY_CODE, GRANT_ASSIGN_MAXES.ID, FISCAL_YEARS.DESCRIPTION as YearDesc from GRANT_ASSIGN_MAXES "+
      " left join FISCAL_YEARS on GRANT_ASSIGN_MAXES.FY_CODE = FISCAL_YEARS.CODE where REV_ID=? order by FY_CODE desc");
      ps.setLong(1, revid);      
      rs = ps.executeQuery();
      while(rs.next())
      {
        ReviewAssignMaxBean rb = new ReviewAssignMaxBean();
        rb.setFycode(rs.getInt("FY_CODE"));
        rb.setFiscalyear(rs.getString("YEARDESC"));
        rb.setGrantprogram(rs.getString("GRANT_PROGRAM"));
        rb.setNumaccepted(rs.getInt("NUM_ACCEPTED"));
        if(rb.getNumaccepted()>0)
          rb.setResponse("Yes");
        else
          rb.setResponse("No");
        rb.setDescrip(rs.getString("DESCRIPTION"));
        rb.setId(rs.getLong("ID"));
        
        allRequests.add(rb);
      }      
     
    }catch(Exception e){
      System.err.println("error getGrantAssignMax() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }  
    return allRequests;
  }

  public ReviewAssignMaxBean getAssignMaxRecord(int id)
  {
    
    ReviewAssignMaxBean rb = new ReviewAssignMaxBean();
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select NUM_ACCEPTED, GRANT_PROGRAM, GRANT_ASSIGN_MAXES.DESCRIPTION, "+
      " FY_CODE, GRANT_ASSIGN_MAXES.ID, FISCAL_YEARS.DESCRIPTION as YearDesc from GRANT_ASSIGN_MAXES "+
      " left join FISCAL_YEARS on GRANT_ASSIGN_MAXES.FY_CODE = FISCAL_YEARS.CODE where GRANT_ASSIGN_MAXES.ID=? ");
      
      ps.setInt(1, id);      
      rs = ps.executeQuery();
      while(rs.next())
      {        
        rb.setFycode(rs.getInt("FY_CODE"));
        rb.setFiscalyear(rs.getString("YEARDESC"));
        rb.setGrantprogram(rs.getString("GRANT_PROGRAM"));
        rb.setNumaccepted(rs.getInt("NUM_ACCEPTED"));
        if(rb.getNumaccepted()>0)
          rb.setResponse("Y");
        else
          rb.setResponse("N");
        rb.setDescrip(rs.getString("DESCRIPTION"));
        rb.setId(rs.getLong("ID"));       
      }      
     
    }catch(Exception e){
      System.err.println("error getAssignMaxRecord() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    
    return rb;
  }

  public int updateReviewerMaxAssign(ReviewAssignMaxBean rb)
  {
    int outcome = 0;
    
    try{
      conn = initializeConn();      
     
      ps = conn.prepareStatement("update GRANT_ASSIGN_MAXES set FY_CODE=?, NUM_ACCEPTED=?, "+
        " GRANT_PROGRAM=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, DESCRIPTION=? where ID=? ");
      
      ps.setInt(1, rb.getFycode());
      ps.setInt(2, rb.getNumaccepted());
      ps.setString(3, rb.getGrantprogram());
      ps.setString(4, rb.getUserid());
      ps.setString(5, rb.getDescrip());
      ps.setLong(6, rb.getId());
      outcome = ps.executeUpdate();              
      
    }catch(Exception e){
      System.err.println("error updateReviewerMaxAssign() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
    }
    
    return outcome;
  }

  public AppDatesBean getDueDateReviews(int fccode, long grantid)
  {
    AppDatesBean a = new AppDatesBean();
  
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from app_dates where fc_code=? and fy_code in "+
        " (select fy_code from grants where id=?) ");
      ps.setInt(1, fccode);
      ps.setLong(2, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        a.setFccode(rs.getInt("fc_code"));
        a.setFycode(rs.getInt("fy_code"));
        a.setEnddate(rs.getDate("end_date"));        
        a.setDuedate(rs.getTimestamp("end_date"));//for lgrmif
      }      
      
    }
    catch(Exception e){
      System.err.println("error getDueDateReviews() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }  
    return a;
  }
  
  
    public AppDatesBean verifyReviewDue(int fccode, int fycode)
    {
      AppDatesBean ab = new AppDatesBean();
      Timestamp dbDate=null;
      Timestamp duedate=null;
      try{
        conn = initializeConn();
        ps = conn.prepareStatement("select sysdate from dual");
        rs = ps.executeQuery();
        while(rs.next()){
          dbDate = rs.getTimestamp("sysdate");
        }
        //System.out.println("sys date is "+dbDate);              
        Close(rs);
        Close(ps);
         
        ps = conn.prepareStatement("select * from APP_DATES where FY_CODE=? and FC_CODE=?");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        rs = ps.executeQuery();      
        while(rs.next())
        {
            ab.setFycode(rs.getInt("fy_code"));
            ab.setFccode(rs.getInt("fc_code"));
            duedate = rs.getTimestamp("end_date");
            ab.setReviewdate(rs.getTimestamp("end_date"));
            ab.setReviewdateStr(rs.getString("end_date"));
        }
        //System.out.println("due date is "+duedate);
        Close(rs);
        Close(ps);        
        
        boolean dateOk = false;        
        if(dbDate.compareTo(duedate) <=0){//dbdate is = or before due date
           dateOk =true;
           //System.out.println("dbdate before duedate");
        }
        ab.setDateAcceptable(dateOk);             
        
      }catch(Exception e){
        System.err.println("error verifyReviewDue() " + e.getMessage().toString());
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }   
      return ab;
    }

  public int addReviewerProgram(int fccode, long revid, UserBean userb)
  {
    int outcome=0;
    try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into reviewer_programs (id, fc_code, rev_id, created_by, "+
      " date_created) values (rp_seq.nextval, ?,?,?,sysdate)");
      ps.setInt(1, fccode);
      ps.setLong(2, revid);
      ps.setString(3, userb.getUserid());
      outcome = ps.executeUpdate();
      
    }
    catch(Exception e){
      System.err.println("error addReviewerProgram() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    } 
    return outcome;
  }

  public int deleteReviewerProgram(int fccode,long revid)
  {
     int outcome=0;
    try{     
      conn = initializeConn();
      
      ps = conn.prepareStatement("delete from reviewer_programs where fc_code=? and rev_id=?");
      ps.setInt(1, fccode);
      ps.setLong(2, revid);
      outcome = ps.executeUpdate();
      
    }
    catch(Exception e){
      System.err.println("error deleteReviewerProgram() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    } 
    return outcome;
  }
  
  
  
  
  
}