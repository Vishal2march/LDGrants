/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CommentDBbean.java
 * Creation/Modification History  :
 *
 * SH   7/6/07      Created
 *
 * Description
 * This class will handle db updates in admin_comments table for the given admin comment record. 
 * It has methods to save, update, delete, or select admin comments for a grant id.
 *****************************************************************************/
package mypackage;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;


public class CommentDBbean 
{
  public CommentDBbean()
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

  public int deleteAdminComment(long commentid)
  {
    int outcome = 0;
    
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("delete from ADMIN_COMMENTS where ID=? ");
      ps.setLong(1, commentid);
      outcome = ps.executeUpdate();
    
    }catch(Exception e){
      System.err.println("error deleteAdminComment(): "+ e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    } 
    
    return outcome;
  }


  public int addApcntComment(long grantid, UserBean userb, String comment)
  {
    int outcome=0;
        
    try{        
      conn = initializeConn();
      
      //CHANGE_COMPLETED FIELD Y WILL INDICATE AN APPLICANT COMMENT
      ps = conn.prepareStatement("insert into ADMIN_COMMENTS (ID, GRA_ID, ADMC_COMMENT, CHANGE_COMPLETED, "+
                " CREATED_BY, DATE_CREATED) values (ADMIN_COMMENT_SEQ.NEXTVAL, ?,?,?,?,SYSDATE) ");
      ps.setLong(1, grantid);
      ps.setString(2, comment);
      ps.setString(3, "Y");
      ps.setString(4, userb.getUserid());      
      outcome = ps.executeUpdate();
     
    } catch(Exception e) {
      System.err.println("error addApcntComment() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }        
    return outcome;
  }
  
  
  
  public int saveAdminComment(long grantid, UserBean userb, String comment  )
  {
    int outcome=0;
        
    try{        
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into ADMIN_COMMENTS (ID, GRA_ID, ADMC_COMMENT, "+
                " CREATED_BY, DATE_CREATED) values (ADMIN_COMMENT_SEQ.NEXTVAL, ?,?,?,SYSDATE) ");
      ps.setLong(1, grantid);
      ps.setString(2, comment);
      ps.setString(3, userb.getUserid());
      
      outcome = ps.executeUpdate();
     
    } catch(Exception e) {
      System.err.println("error saveAdminComment() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }        
    return outcome;
  }

  public int updateAdminComment(HttpServletRequest request)
  {
    HttpSession sess = request.getSession();
    boolean reqerror = false;
    int outcome = 0;
    
    //Get some of the values from session to put into tables 
    UserBean userb = (UserBean) sess.getAttribute("lduser");
    String userid = userb.getUserid();
      
    
    try {
      conn = initializeConn();     
      ps = conn.prepareStatement("update ADMIN_COMMENTS set ADMC_COMMENT=?, DATE_MODIFIED=SYSDATE, "+
        "MODIFIED_BY=? where ID=?");
        
      //get the number of admin comm records
      int numRecs = 0;
      String rows = request.getParameter("rows");
      if(rows!=null && !rows.equals(""))
        numRecs = Integer.parseInt(rows);
    
      //loop on the number of admin comm records and get info that user updated
      for(int i=0;i<numRecs; i++)
      {
        //get comment id of each record
        String id = (String)request.getParameter("acId_"+i);
        if(id == null || id.equals(""))
          reqerror = true;
          
        if(reqerror)//if no id PK for table, then can't update
        {
          outcome = 0;
          reqerror = false;
        }
        else
        {
          ps.setString(1, (String)request.getParameter("acComment_"+i));
          ps.setString(2, userid);
          ps.setString(3, id);
          outcome = ps.executeUpdate();
        }
        
      }
          
    } 
    catch(Exception e){
      System.err.println("error updateAdminComment() " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }    
    return outcome;
  }
  
  
  public CommentBean getComment(long comID)
  {
     CommentBean cb = new CommentBean();
     try{
        conn = initializeConn();
        ps = conn.prepareStatement("select * from ADMIN_COMMENTS where ID=?");
        ps.setLong(1, comID);        
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          cb.setId(rs.getLong("ID"));
          cb.setGrantid(rs.getLong("GRA_ID"));
          cb.setComment(rs.getString("ADMC_COMMENT"));
          cb.setCreatedby(rs.getString("CREATED_BY"));
          cb.setCommentdate(rs.getDate("DATE_CREATED"));
        }        
     }
     catch(Exception e){
      System.err.println("error getComment " + e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return cb;
  }


  
  
  //changed 10/17/08 to filter for admin comments only
  public Vector getAdminComments(long grantid)
  {
    Vector results = new Vector();
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from ADMIN_COMMENTS where GRA_ID=? "+
      " and change_completed is null order by DATE_CREATED desc ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        CommentBean cb = new CommentBean();
        cb.setId(rs.getLong("ID"));
        cb.setGrantid(rs.getLong("GRA_ID"));
        cb.setComment(rs.getString("ADMC_COMMENT"));
        cb.setCreatedby(rs.getString("CREATED_BY"));
        cb.setCommentdate(rs.getDate("DATE_CREATED"));
        
        results.add(cb);
      }
            
    }catch(Exception e){
      System.err.println("error getAdminComments() "+ e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return results;  
  }
  
  //10/17/08 CHANGE_COMPLETED=Y INDICATES APCNT COMMENT
  public Vector getApcntComments(long grantid)
  {
    Vector results = new Vector();
    
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("select * from ADMIN_COMMENTS where GRA_ID=? "+
      " and change_completed='Y' order by DATE_CREATED desc ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        CommentBean cb = new CommentBean();
        cb.setId(rs.getLong("ID"));
        cb.setGrantid(rs.getLong("GRA_ID"));
        cb.setComment(rs.getString("ADMC_COMMENT"));
        cb.setCreatedby(rs.getString("CREATED_BY"));
        cb.setCommentdate(rs.getDate("DATE_CREATED"));        
        results.add(cb);
      }
            
    }catch(Exception e){
      System.err.println("error getAdminComments() "+ e.getMessage().toString());
    }
    finally {
      Close(conn);
      Close(ps);
      Close(rs);
    }
    return results;  
  }
  
  
  
}