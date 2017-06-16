 /******************************************************************************
  * @author  : Stefanie Husak
  * @Version : 2.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  ClobBean.java
  * Creation/Modification History  :
  *
  * SH   3/10/07      Modified
  *
  * Description
  * This class will use streams to read in data that is stored in clob fields in 
  * the project_narratives table.  Code found online and modified.  
  *****************************************************************************/
 package clobpackage;

 import java.sql.*;
 import java.io.*;
 import java.util.*;
 import javax.naming.*;

 import javax.sql.*;
 import oracle.sql.*;

 import oracle.jdbc.*;

  
 public class ClobBean  {

     private String          data                = null;
     private Connection      conn                = null;
     
   public String currentUser;
   public long grantid;
   public long narrID;
   
    // Create a property object to hold the username, password and
   // the new property SetBigStringTryClob.
   private Properties props = new Properties();
  
     
     public String getData() 
     {
       return data; 
     }
    
     public void setData(String data) 
     {
       this.data = data;
     }   
   
     public void setData(String data, int grant_ID, String currentUser)
     {
       this.data = data;
       this.grantid = grant_ID;
       this.currentUser = currentUser;
     }
     
   
     
     public void openOracleConnection()
             throws    SQLException
                     , IllegalAccessException
                     , InstantiationException
                     , ClassNotFoundException,NamingException {
       try {
                         
             Context namingContext = new InitialContext();
             DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
             conn = ds.getConnection();

         } catch (Exception e) {
             System.err.println("Exception: (Open Connection).");
             e.printStackTrace();
             //throw e;
         }   
     }
     
     public void closeOracleConnection() throws SQLException {
         
         try {
             conn.close();
         } catch (SQLException e) {
             System.err.println("SQL Exception: (Closing Connection).");
             e.printStackTrace();
             if (conn != null) {
                 try {
                     conn.rollback();
                 } catch (SQLException e2) {
                     System.err.println("SQL (Rollback Failed) Exception.");
                     e2.printStackTrace();
                 }
             }
             throw e;
         }
     }
     
     
   private Connection cn() throws SQLException, NamingException 
   {
     
     Context namingContext = new InitialContext();
     DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
    
      return ds.getConnection();
   }

   public long getGrantid()
   {
     return grantid;
   }
  

   public boolean isEmpty(int grant_ID)
   {
     boolean answer = true;
     try  
     {          
       openOracleConnection();
       if(getData().toString().trim().length()==0)
       {
         answer = true;
       }
       else
       {
         answer = false;
       }
     }
     catch(Exception e)
     {
       System.err.println(e.getMessage());
     }
     return answer;
   }

   public void setGrantid(long grantid)
   {
     this.grantid = grantid;
   }

   public String getCurrentUser()
   {
     return currentUser;
   }

   public void setCurrentUser(String currentUser)
   {
     this.currentUser = currentUser;
   }

   /**
    * Generic method to get data from a clob field in the project narratives table
    * @throws javax.naming.NamingException
    * @throws java.sql.SQLException
    * @throws java.io.IOException
    * @return 
    * @param narrativeType
    */
   public void getClobNarrative(int narrativeType)
     throws IOException, SQLException, NamingException, Exception {

         StringWriter        outputStringWriter          = null;
         BufferedWriter      outputBufferedWriter        = null;
         String              sqlText                     = null;
         Statement           stmt                        = null;
         ResultSet           rset                        = null;
         CLOB               narrative                   = null;
         long                clobLength;
         long                position;
         int                 chunkSize;
         char[]              textBuffer;
         int                 charsRead                   = 0;
         int                 totCharsRead                = 0;
         int                 totCharsWritten             = 0;

         try {
             if (conn.isClosed()) {
               conn = cn();
             }
             
             stmt = conn.createStatement();

             outputStringWriter = new StringWriter();
             outputBufferedWriter = new BufferedWriter(outputStringWriter);
            
            sqlText = " SELECT ID, NARRATIVE_DESCR FROM PROJECT_NARRATIVES " +
                      " WHERE NAT_ID = " + narrativeType +
                      " AND GRA_ID = " + this.grantid +" FOR UPDATE";
           
             rset = stmt.executeQuery(sqlText);
             if(rset.next())//test for record otherwise exhausedResultSet Exception
             {
               this.setNarrID(rset.getLong("ID"));
               
               ////////////new code 11/28/12-> trying to fix classcastexception in 11g
               weblogic.jdbc.wrapper.ResultSet wlsResultSet = (weblogic.jdbc.wrapper.ResultSet)rset;  
               OracleResultSet oracleResultSet = (OracleResultSet)wlsResultSet.getVendorObj();  
               narrative = ((OracleResultSet) oracleResultSet).getCLOB("NARRATIVE_DESCR");
               ///////////end new code section
               
               
               /* narrative is type oracle.sql.CLOB
                * this is old version of code, prior to 11/28/12, which worked in oracle 9, 10g, 
                * but not 11g. throws classcastexception b/c weblogicserver wraps clob/blob types
                */
              // narrative = ((OracleResultSet) rset).getCLOB("NARRATIVE_DESCR");
               
                             
               clobLength = narrative.length();
               chunkSize = narrative.getChunkSize();
               textBuffer = new char[chunkSize];
               
               for (position = 1; position <= clobLength; position += chunkSize)
               {                
                   // Loop through while reading a chunk of data from the CLOB
                   // column using the getChars() method. This data will be stored
                   // in a temporary buffer that will be written to disk.
                   charsRead = narrative.getChars(position, chunkSize, textBuffer);
   
                   // Now write the buffer to disk.
                   outputBufferedWriter.write(textBuffer, 0, charsRead);
                   
                   totCharsRead += charsRead;
                   totCharsWritten += charsRead;
               }
             
             }
             
             outputBufferedWriter.close();            
             setData(outputStringWriter.toString());
                                    
             conn.commit(); 
             rset.close();
             stmt.close();            
             
         } catch (IOException e) {
             System.err.println("I/O Exception getClobNarrative(): ");
             e.printStackTrace();
             throw e;
         } catch (SQLException e) {
             System.err.println("SQL Exception getClobNarrative(): ");
             e.printStackTrace();
             throw e;
         } catch(Exception e) {
           System.err.println("Exception getClobNarrative(): ");
           e.printStackTrace();
           throw e;
         }
         finally
         {          
           closeOracleConnection();
         }
   }


   public void setNarrID(long narrID)
   {
     this.narrID = narrID;
   }

   public long getNarrID()
   {
     return narrID;
   }
 }