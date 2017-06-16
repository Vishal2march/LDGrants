 /******************************************************************************
  * @author  : Stefanie Husak
  * @Version : 1.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  AttachmentAction.java
  * Creation/Modification History  :
  * SH   4/1/09      Created
  *
  * Description
  * This dispatchaction will handle add/delete document, and route to confirm
  * delete doc page for co/di/fl/al/lg/cn apcnts.
  *****************************************************************************/
 package lgrmif;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.Hashtable;
 import java.util.Vector;
 import javax.naming.Context;
 import javax.naming.InitialContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import javax.sql.DataSource;
 import javazoom.upload.MultipartFormDataRequest;
 import javazoom.upload.UploadFile;
 import mypackage.AppStatusBean;
 import mypackage.DBHandler;
 import mypackage.UploadDocBean;
 import mypackage.UserBean;

 import oracle.jdbc.OracleResultSet;

 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.actions.DispatchAction;

 public class AttachmentAction extends DispatchAction
 {
    private DBHandler dbh = new DBHandler();
   
   
   public ActionForward confirmdel(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
     {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
        String program="";
        
        try{
          program = request.getParameter("prog");
          
          UploadDocBean ub = new UploadDocBean();         
          String id = request.getParameter("docid");
          ub.setId(Integer.parseInt(id));         
          ub.setProgram(program);
          
          request.setAttribute("docBean", ub);
                   
        }catch(Exception e){System.out.println("error AttachmentAction "+e.getMessage().toString());}
        
        return mapping.findForward(program+"confirm");
     }
     
     
   public ActionForward delete(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
     {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
        String program="";
        
        try{
           program = request.getParameter("prog");
           String doc = request.getParameter("docid");   
           int docid = Integer.parseInt(doc);
           
           //UserBean userb = (UserBean) sess.getAttribute("lduser");  
           String grantnum= (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           //delete doc
           int outcome = dbh.deleteDocument(docid);
           
           //get uploaded documents for this grant      
           Vector results = dbh.getAllDocuments(grantid);
           request.setAttribute("allDocs", results);
           request.setAttribute("deleteStatus", new Integer(outcome) );
           
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
                   
        }catch(Exception e){System.out.println("error AttachmentAction "+e.getMessage().toString());}
        
        return mapping.findForward(program+"attach");
     }
     
     
     
   public ActionForward addattach(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
     {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
        String program="";
               
        try{         
          
          int amount = -1;//num of files that have been uploaded
          if (MultipartFormDataRequest.isMultipartFormData(request))
          {
              // Uses MultipartFormDataRequest to parse the HTTP request.
              MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
              
              if (mrequest != null) 
              {     
                     //get program user is working on (di/co/fl/al/cn)
                     program = mrequest.getParameter("prog");
                     
                     Hashtable files = mrequest.getFiles();
                     if ( (files != null) && (!files.isEmpty()) )
                     {
                         UploadFile file = (UploadFile) files.get("uploadfile");
                         if (file != null ) 
                           System.out.println("Uploading file: "+file.getFileName()+" ("+file.getFileSize()+" bytes)"+" Content Type : "+file.getContentType());
                         
                         
                         
                         if(file.getFileName()!=null)
                         {
                           Context namingContext = new InitialContext();
                           DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
                   
                           PreparedStatement insert= null;   //update stmt
                           Connection conn= null;            //connection to the database
                           int result= 0;                    //transaction status
                           String availableid="";
                           
                           String currentUser;
                           String docName;
                           String docType;
                           long fileSize;
                           long grantID;
                           
                           //get the desc that user may have entered for file
                           String filedesc = "";
                           if(mrequest.getParameter("filedesc")!=null)
                             filedesc = mrequest.getParameter("filedesc");
                  
                           try 
                             {
                             //get a connection to the database
                             conn= ds.getConnection();
                             conn.setAutoCommit(false); 
                           
                             PreparedStatement getid= null;    
                             //get the next primary key id to insert
                             getid= conn.prepareStatement("SELECT upload_seq.nextval from dual" );
                             ResultSet currentid = getid.executeQuery();
                             currentid.next();
                             availableid = currentid.getString(1);
                             
                             
                             //get info about user that is logged on
                             UserBean userb = (UserBean) sess.getAttribute("lduser");
                             currentUser = userb.getUserid();
                             //get grant id and parameters for file to be uploaded
                             grantID = Long.parseLong((String) sess.getAttribute("grantid"));
                             docName = file.getFileName();
                               
                             //parse out any apostrophe/ampersand/comma in the file name
                             docName = parseFileName(docName);
                               
                             docType = file.getContentType();
                             fileSize = file.getFileSize();
                                        
                             //insert an empty blob for this file
                             insert= conn.prepareStatement( "insert into uploads " + 
                               " (id,gra_id,created_by,date_created,name,blob_content,doc_type,doc_size, dad_charset) " +
                               " values (?,?,?,sysdate,?,empty_blob(),?,?,?)");
                             insert.setInt(1, Integer.parseInt(availableid));
                             insert.setLong(2, grantID);
                             insert.setString(3, currentUser.toString());
                             insert.setString(4, docName.toString());
                             insert.setString(5, docType);
                             insert.setLong(6, fileSize);
                             insert.setString(7, filedesc);
                             result= insert.executeUpdate();
                                                       
                             //now insert the actual file content stream
                             PreparedStatement selectblob= null;
                             selectblob= conn.prepareStatement("SELECT blob_content FROM uploads WHERE id = ? for update");
                             selectblob.setInt(1, (Integer.parseInt(availableid)));
                             ResultSet rset = selectblob.executeQuery();
                             rset.next();
                               
                             ////////////new code 11/28/12-> trying to fix classcastexception in 11g
                               weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet)rset;  
                               OracleResultSet oracleResultSet = (OracleResultSet)wlsResultSet.getVendorObj();  
                               oracle.sql.BLOB blob = ( (oracle.jdbc.OracleResultSet) oracleResultSet).getBLOB(1);
                             ///////////end new code section
                               
                             /*this is old version of code, prior to 11/28/12, which worked in oracle 9, 10g, 
                              * but not 11g. throws classcastexception b/c weblogicserver wraps clob/blob types */  
                             //oracle.sql.BLOB blob = ( (oracle.jdbc.OracleResultSet) rset).getBLOB(1);
                               
                             OutputStream outstream= blob.getBinaryOutputStream();
                             InputStream instream= file.getInpuStream();
                             int size = 1024;
                             byte[] buffer = new byte[size];
                             int length = -1;
                            
                             while ((length = instream.read(buffer)) != -1)
                             {
                                 outstream.write(buffer, 0, length);
                             }
                             instream.close();
                             outstream.close();
                             amount  = 1;//file has been uploaded
                               
                             int l = (int)blob.length();
                             // print the length of the blob
                             System.out.println("blob length:"+buffer[0]+"kkkkk"+buffer[1]);
                             }
                             catch(Exception e) 
                             { 
                               e.printStackTrace();
                             }
                             finally 
                             { 
                               if (insert != null) 
                               {
                                 try{insert.close();}catch (SQLException e) { e.printStackTrace(); }
                               }
                 
                               if (conn!= null) 
                               {
                                 conn.commit();
                                 try{ conn.close();}catch (SQLException e) { e.printStackTrace(); }                
                               }                
                               
                             }
                         }//end if FileName==null  
                         
                     }//end if files isempty        
              }
           
           }   
        }catch(Exception e){System.out.println("error AttachmentAction "+e.getMessage().toString());}
        
        return mapping.findForward(program+"add");
     }    
     
     
     
  /**
     *added method 7/22/14 to parse out comma/ampersand/apostrophe from file name
     * @param fileName
     * @return
     */
   public String parseFileName(String fileName)
   {
     String tmpString = "";
     
     try{
         char[] amtString = fileName.toCharArray();//convert string to array of char
         Vector newAmtString = new Vector();//vector to hold new filename
         
         //loop on all items in the old string array
         for(int i=0; i<amtString.length; i++) 
         {
             //check if char is amp/comma/apostrph.  - if not; then add to new vector
             if(amtString[i]!='\'' &&  amtString[i]!= '&'  &&  amtString[i]!= ',' )
             {  
                 //cannot add char to vector - must wrap in a character object
                 newAmtString.add(new Character(amtString[i]) ); //it works!
             }             
         }         
         
         //now convert all the numbers in the vector back to a string
         for(int i=0; i<newAmtString.size();i++){
           tmpString+= newAmtString.get(i);
         }       
         
     }catch(Exception e){System.out.println("error parseFileName "+e.getMessage().toString());}
     return tmpString;
   } 
     
     
     
     public boolean checkSessionTimeout(HttpSession sess)
     {
       boolean timeout = false;
       //check for session timeout
       boolean userID = (sess.getAttribute("lduser") != null);
       if (!userID && sess.isNew())
       {      
         timeout = true;
       }      
       return timeout;
     }
 }