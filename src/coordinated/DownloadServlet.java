 /******************************************************************************
  * @author  : Anton Keller
  * @Version : 2.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  DownloadServlet.java
  * Creation/Modification History  :
  *
  * AK               Created
  * SH   6/13/07     Modified
  *      7/2/07      Modified b/c could not download files from srv03, they came up blank
  *      7/21/14    Modified to allow download of office 2010 docs (.docx and .xlsx)
  * Description
  * This class will download the file from the uploads table.  It displays a popup
  * that asks the user to choose an application to download 
  *****************************************************************************/
 package coordinated;


 import javax.servlet.*;
 import javax.servlet.http.*;
 import java.io.IOException;
 import java.io.*;
 import java.util.*;
 import java.sql.*;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import java.sql.ResultSet;
 import javax.sql.DataSource;
 import javax.naming.InitialContext;
 import javax.naming.Context;
 import javax.naming.NamingException;

 // Needed since we will be using Oracle's BLOB, part of Oracle's JDBC extended
 // classes. Keep in mind that we could have included Java's JDBC interfaces
 // java.sql.Blob which Oracle does implement. The oracle.sql.BLOB class 
 // provided by Oracle does offer better performance and functionality.
 import oracle.jdbc.OracleResultSet;
 // Needed for Oracle JDBC Extended Classes
 import oracle.jdbc.*;
 import oracle.jdbc.driver.*;
 import oracle.sql.*;


public class DownloadServlet extends HttpServlet 
 {
        
   private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

   public void init(ServletConfig config) throws ServletException
   {
     super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     ResultSet rs= null;               //results from database
     PreparedStatement select= null;   //select stmt
     Connection conn= null;            //connection to the database    
     String myfilename = "";
     String mimeType = "";
     int docSize = 0;
     InputStream blobInputStream = null;      
     BLOB image = null;
     byte[] file = null;//newsd
     String newid= new String(); 
          
     try {
       //get a connection to the database
       conn= initializeConn();
       newid= request.getParameter("doc_id");
       
       //create a sql string
       String select_str= "SELECT blob_content, name, doc_type, doc_size FROM uploads where id=?";
       select= conn.prepareStatement( select_str );
       select.setString(1, newid);
       rs= select.executeQuery();

       //cycle through the results
       if ((rs !=null) && rs.next()) 
       {          
         ////////////new code 11/28/12-> trying to fix classcastexception in 11g
         //  weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet)rs;  
         //  OracleResultSet oracleResultSet = (OracleResultSet)wlsResultSet.getVendorObj();  
         //  image = ( (oracle.jdbc.OracleResultSet) oracleResultSet).getBLOB("blob_content");
         //  blobInputStream = image.getBinaryStream();
         ///////////end new code section
                  
         /*this is old version of code, prior to 11/28/12, which worked in oracle 9, 10g, 
         * but not 11g. throws classcastexception b/c weblogicserver wraps clob/blob types  
          image = ((OracleResultSet) rs).getBLOB("blob_content");*/
                          
           //new code 7/21/14 to allow office 2010 attachments (.docx, .xlsx)  
           myfilename = rs.getString("name");
           mimeType = rs.getString("doc_type");
           docSize = rs.getInt("doc_size");
           file = rs.getBytes("blob_content");//newsd
       }
    
     /*  blobInputStream = new ByteArrayInputStream(file);//newsd
       response.setContentType(mimeType);         
       response.setHeader("Content-disposition", "attachment; filename=" + myfilename);
       response.setBufferSize(4096);
       response.getOutputStream().write(file);*/
         
        
    
         response.setContentType(mimeType);         
         response.setHeader("Content-disposition", "attachment; filename=" + myfilename);
         
         blobInputStream = new ByteArrayInputStream(file);
         
         ServletOutputStream output = response.getOutputStream();
                  
         //byte[] b = new byte[1024];
         while ((blobInputStream != null) && (blobInputStream.read(file) >= 0)  ) {
             output.write(file);
         }
         
         output.flush();
         output.close();
                 
         if (blobInputStream != null) {
             blobInputStream.close();
         }
     }catch(Exception e) 
     { 
       e.printStackTrace(); 
     }
     finally 
     { 
       closePreparedStmt(select); 
       closeConn(conn); 
    }
         
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     doGet(request, response);
   }
   
   
    private static Connection initializeConn() throws SQLException, NamingException {

     //look up the datasource and return a connection
     Context namingContext = new InitialContext();
     DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
     return ds.getConnection();
   }

   /**
    * This method closes a preparedStatement after database access. When a 
    * Statement object is closed, its current ResultSet object, if one 
    * exists, is also closed.
    * @param pStmt- the preparedStatement to close.
    */
   public static void closePreparedStmt(PreparedStatement pStmt) {
     if (pStmt != null) {
       try{
         pStmt.close();
       }catch (SQLException e) { e.printStackTrace(); }
     }
   }

   /**
    * This method closes the database connection after database access.
    * @param connection- a connection to a database.
    */
   public static void closeConn(Connection connection) {
     if (connection != null) {
       try{
         connection.close();
       }catch (SQLException e) { e.printStackTrace(); }
     }
   }
   
      
 }