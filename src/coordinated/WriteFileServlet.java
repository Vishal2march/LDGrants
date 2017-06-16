package coordinated;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.io.Writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.sql.DataSource;

import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import mypackage.DBHandler;

import oracle.sql.BLOB;

public class WriteFileServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
      ResultSet rs= null;               //results from database
      PreparedStatement select= null;   //select stmt
      Connection conn= null;            //connection to the database    
      String fileName = "";
      String mimeType = "";
      int docSize = 0;      
      byte[] file = null;//newsd
      LookAndFeel previousLF = null;
      
           
      try {
        String grantid = request.getParameter("gid");
        
        //get original look/feel
        previousLF = UIManager.getLookAndFeel();
          
        //set windows look & feel  
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
          
        JFrame parentframe = new JFrame();//put dialog in a frame so it always appears on top (not behind) browser
        parentframe.setVisible(true);
        parentframe.setAlwaysOnTop(true);
          
        //show save file dialog box; allow selecting directories/folder only
        JFileChooser fc = new JFileChooser();  
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select folder for attachment download");
          
        //using showdialog instead of showsavedialog b/c this one allows a 'new folder' option
        int returnVal = fc.showDialog(parentframe, "Select Folder");
          
        //get folder that user chose
        File chosenDirectory =  fc.getSelectedFile();
        //System.out.println("chosendirec "+chosenDirectory.getPath());
          
                  
                  
        //if user did not select file; don't write documents
        if(chosenDirectory!=null){
            
            //get a connection to the database
            conn= initializeConn();
                        
            //get all attachments for this grant
            select= conn.prepareStatement("SELECT blob_content, name, doc_type, doc_size FROM uploads where gra_id=?");
            select.setString(1, grantid);
            rs= select.executeQuery();
    
            //cycle through the results
            while((rs !=null) && rs.next()) 
            {                                
                //new code 7/21/14 to allow office 2010 attachments (.docx, .xlsx)  
                fileName = rs.getString("name");
                mimeType = rs.getString("doc_type");
                docSize = rs.getInt("doc_size");
                file = rs.getBytes("blob_content");
                
                //create file in selected folder; with name of this doc
                File actualFile = new File (chosenDirectory, fileName);
                
                FileOutputStream out = new FileOutputStream( actualFile );     
                out.write(file);//write byte[] to file 
                out.close();            
            }
        }
              
      /////////////////
        //go back and redisplay the attachment list page
        DBHandler dbh = new DBHandler();
        Vector results = dbh.getAllDocuments(Long.parseLong(grantid));
        request.setAttribute("allDocs", results);
          
        //go back to old LF  
        UIManager.setLookAndFeel(previousLF);
        //close out parent frame (otherwise user has to click on the x to close window)        
        parentframe.dispose();
        
        RequestDispatcher rd = request.getRequestDispatcher("CnAdminAttachment.do");
        rd.forward(request, response);   
        return;
        
          
      }catch(Exception e){ 
        e.printStackTrace(); 
      }
      finally { 
          try{
              //go back to old LF  
              UIManager.setLookAndFeel(previousLF);
              
          }catch(Exception e){System.out.println(e.getMessage().toString());}
        
        closePreparedStmt(select); 
        closeConn(conn); 
        closeResultSet(rs);
      }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
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
  
  public void closeResultSet(ResultSet rs)
  {
   try{
     if(rs != null){
       rs.close();
     }
   }catch(Exception e){System.out.println(e);}
  }
}
