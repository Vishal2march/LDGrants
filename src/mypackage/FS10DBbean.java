/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  FS10DBbean.java
 * Creation/Modification History  :
 *
 * SH   6/27/07      Created
 * GH 03/17/2017	 Modified
 *
 * Description
 * This class will handle all insert/update/delete/selects from the FS10_records
 * table in the db.
 *****************************************************************************/
package mypackage;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import oracle.jdbc.OracleDriver;

public class FS10DBbean 
{
  public FS10DBbean()
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
  
  public ArrayList generateExpenseCodes(){
    ArrayList allcodes = new ArrayList();
    
    DropDownListBean dd = new DropDownListBean();
    dd.setId(1);
    dd.setDescription("Personal Services -Professional Staff");
    allcodes.add(dd);
    
    dd = new DropDownListBean();
    dd.setId(7);
    dd.setDescription("Personal Services -Support Staff");
    allcodes.add(dd);
            
    dd = new DropDownListBean();
    dd.setId(2);
    dd.setDescription("Employee Benefits");
    allcodes.add(dd);
    
      dd = new DropDownListBean();
      dd.setId(3);
      dd.setDescription("Contracted Services");
      allcodes.add(dd);

      dd = new DropDownListBean();
      dd.setId(4);
      dd.setDescription("Supplies - Materials");
      allcodes.add(dd);
      
      dd = new DropDownListBean();
      dd.setId(8);
      dd.setDescription("Equipment");
      allcodes.add(dd);
    
      /*this category no longer used for c/p or any other program
       * dd = new DropDownListBean();
      dd.setId(5);
      dd.setDescription("Other Expenses");
      allcodes.add(dd);*/

      dd = new DropDownListBean();
      dd.setId(6);
      dd.setDescription("Travel Expenses");
      allcodes.add(dd);
      
      dd = new DropDownListBean();
      dd.setId(9);
      dd.setDescription("Minor Remodeling");
      allcodes.add(dd);
      
      return allcodes;
  }
  
  
   /**
   * This method will get all FS10 records for the given grant id.
   * It also generates the drop down list of all expense codes -modified 1/28/11 for breakdown
   * of supplies vs equip, proff vs salaries, etc. to assist with pre-populating FS10A.
   * @param grantid
   * @return ArrayList
   */
  public ArrayList getFSARecords(long grantid)
  {
    ArrayList results = new ArrayList();
    
    try{
      //this method will get all FS budget categories- modified 1/28/11
      ArrayList allexpcodes = generateExpenseCodes();
      
      conn = initializeConn();
      ps = conn.prepareStatement("select * from FS10_RECORDS where GRA_ID=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        FS10Bean fb = new FS10Bean();
        fb.setAllExpenseCodes(allexpcodes);
        fb.setId(rs.getLong("ID"));
        fb.setDescription(rs.getString("DESCRIPTION"));
        fb.setAmountincr(rs.getInt("AMOUNT_INCR"));
        fb.setAmountdecr(rs.getInt("AMOUNT_DECR"));
        fb.setAmountincrStr(rs.getString("AMOUNT_INCR"));
        fb.setAmountdecrStr(rs.getString("AMOUNT_DECR"));
        fb.setExpcode(rs.getInt("EXP_CODE"));
        fb.setExpendTypeId(rs.getLong("EXT_ID"));
        int expcode = rs.getInt("EXP_CODE");

        switch(expcode)
        {
        case 1:
            fb.setExpname("Personal Services -Professional Staff");
            fb.setExpendTypeId(15L);
            break;
          case 2:
            fb.setExpname("Employee Benefits");
            fb.setExpendTypeId(80L);
            break;
          case 3:
            fb.setExpname("Contracted Services");
            fb.setExpendTypeId(40L);
            break;
          case 4:
            fb.setExpname("Supply - Materials");
            fb.setExpendTypeId(45L);
            break;
          case 5:
            fb.setExpname("Other Expense");//no longer used
            fb.setExpendTypeId(0L);
            break;
          case 6:
              fb.setExpname("Travel");
              fb.setExpendTypeId(46L);
              break;
          case 7:
              fb.setExpname("Personal Services -Support Staff");
              fb.setExpendTypeId(16L);
              break;
          case 8:
              fb.setExpname("Equipment");
              fb.setExpendTypeId(20L);
              break;
          case 9:
              fb.setExpname("Minor Remodeling");
              fb.setExpendTypeId(30L);
              break;  
        }
        fb.setDatecreated(rs.getDate("DATE_CREATED"));
        fb.setCreatedby(rs.getString("CREATED_BY"));
        fb.setFycode(rs.getInt("fy_code"));
        results.add(fb);
      }  
      
    }catch(Exception e){
      System.err.println("error getFSARecords() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    }  
    return results;
  }
   
   
   
   
   
  /**
  * This method will get all FS10 records for the given grant id.
  * It also generates the drop down list of all expense codes -modified 1/28/11 for breakdown
  * of supplies vs equip, proff vs salaries, etc. to assist with pre-populating FS10A.
  * @param grantid
  * @return ArrayList
  */
  public ArrayList getFSARecordsForFy(long grantid, int fycode)
  {
   ArrayList results = new ArrayList();
   
   try{
     //this method will get all FS budget categories- modified 1/28/11
     ArrayList allexpcodes = generateExpenseCodes();
     
     conn = initializeConn();
     ps = conn.prepareStatement("select * from FS10_RECORDS where GRA_ID=? and fy_code=?");
     ps.setLong(1, grantid);
     ps.setInt(2, fycode);
     rs = ps.executeQuery();
     
     while(rs.next())
     {
       FS10Bean fb = new FS10Bean();
       fb.setAllExpenseCodes(allexpcodes);
       fb.setId(rs.getLong("ID"));
       fb.setDescription(rs.getString("DESCRIPTION"));
       fb.setAmountincr(rs.getInt("AMOUNT_INCR"));
       fb.setAmountdecr(rs.getInt("AMOUNT_DECR"));
       fb.setAmountincrStr(rs.getString("AMOUNT_INCR"));
       fb.setAmountdecrStr(rs.getString("AMOUNT_DECR"));
       fb.setExpcode(rs.getInt("EXP_CODE"));
       fb.setExpendTypeId(rs.getLong("EXT_ID"));
       int expcode = rs.getInt("EXP_CODE");
       switch(expcode)
       {
         case 1:
           fb.setExpname("Personal Services -Professional Staff");
           fb.setExpendTypeId(15L);
           break;
         case 2:
           fb.setExpname("Employee Benefits");
           fb.setExpendTypeId(80L);
           break;
         case 3:
           fb.setExpname("Contracted Services");
           fb.setExpendTypeId(40L);
           break;
         case 4:
           fb.setExpname("Supply - Materials");
           fb.setExpendTypeId(45L);
           break;
         case 5:
           fb.setExpname("Other Expense");//no longer used
           fb.setExpendTypeId(0L);
           break;
         case 6:
             fb.setExpname("Travel");
             fb.setExpendTypeId(46L);
             break;
         case 7:
             fb.setExpname("Personal Services -Support Staff");
             fb.setExpendTypeId(16L);
             break;
         case 8:
             fb.setExpname("Equipment");
             fb.setExpendTypeId(20L);
             break;
         case 9:
             fb.setExpname("Minor Remodeling");
             fb.setExpendTypeId(30L);
             break;        
       }
       fb.setDatecreated(rs.getDate("DATE_CREATED"));
       fb.setCreatedby(rs.getString("CREATED_BY"));
       fb.setFycode(rs.getInt("fy_code"));
       results.add(fb);
     }  
     
   }catch(Exception e){
     System.err.println("error getFSARecordsForFy() "+ e.getMessage().toString());      
   }
   finally{
     Close(conn);
     Close(ps);
     Close(rs);
   }  
   return results;
  }  
   
   
   
  
  
    public String getAmendmentRecordDesc(long amendmentId)
    {
      String desc = "";      
      try{
        conn = initializeConn();
        ps = conn.prepareStatement("select * from FS10_RECORDS where ID=?");
        ps.setLong(1, amendmentId);
        rs = ps.executeQuery();
        
        while(rs.next()){
          desc = rs.getString("description");
        }  
        
      }catch(Exception e){
        System.err.println("error getAmendmentRecordDesc() "+ e.getMessage().toString());      
      }
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      }  
      return desc;
    }
  
  public int addFSARecord( UserBean userb, long grantid )
  {    
    int outcome =0;    
        
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("insert into FS10_RECORDS (ID, DATE_CREATED, CREATED_BY, GRA_ID) "+
                " values (FS10_SEQ.NEXTVAL, SYSDATE, ?,?) ");
      ps.setString(1, userb.getUserid());
      ps.setLong(2, grantid);
      outcome = ps.executeUpdate();     
      
    }catch(Exception e){
      System.err.println("error addFSARecord() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    }    
    return outcome;
  }
 
 
   public int updateFSARecord(List amendrecords, UserBean userb)
  {
    DBHandler dbh = new DBHandler();  
    int outcome =0;    
        
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("update FS10_RECORDS set DESCRIPTION=?, AMOUNT_INCR=?, "+
        " AMOUNT_DECR=?, EXP_CODE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, FY_CODE=? "+
        " where ID=? ");        
        
      //get the number records
      int numRecs = 0;
      if(amendrecords !=null)
        numRecs = amendrecords.size();
     
       
      //loop on the number of fs10a records and get all info
      for(int i=0;i<numRecs; i++)
      {
        FS10Bean fs = (FS10Bean) amendrecords.get(i);
              
        String amtincr = fs.getAmountincrStr();
        int incr_int = 0;
        if(amtincr!= null && !amtincr.equals(""))
          incr_int = dbh.parseCurrencyAmtNoDecimal(amtincr);
                              
        String amtdecr = fs.getAmountdecrStr();
        int dec_int = 0;
        if(amtdecr!= null && !amtdecr.equals(""))
          dec_int = dbh.parseCurrencyAmtNoDecimal(amtdecr);//this will get rid of any commas, decimals or $ in the value
                                       
        //set params and update
        ps.setString(1, fs.getDescription());
        ps.setInt(2, incr_int);
        ps.setInt(3, dec_int);
        ps.setInt(4, fs.getExpcode());
        ps.setString(5, userb.getUserid());
        ps.setInt(6, fs.getFycode());
        ps.setLong(7, fs.getId());                   
        outcome = ps.executeUpdate();
      }
                        
    }catch(Exception e){
      System.err.println("error updateFSARecord() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    } 
    return outcome;
  }

  
  public int deleteFS10Record(int id)
  {        
    int outcome =0;       
         
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("delete from FS10_RECORDS where ID=?");
      ps.setInt(1, id);
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error deleteFS10Record() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    }      
    return outcome;
  }
    

  public int updateFS10Approval(UserBean userb, long grantid, boolean fs10approval)
  {
    int outcome = 0;   
        
    try{
      conn = initializeConn();      
      ps = conn.prepareStatement("update GRANTS set FS10A_YN=?, DATE_MODIFIED=sysdate, "+
              " MODIFIED_BY = ? where ID=?");
      ps.setBoolean(1, fs10approval);
      ps.setString(2, userb.getUserid());
      ps.setLong(3, grantid);
      outcome = ps.executeUpdate();
           
    }catch(Exception e){
      System.err.println("error updateFS10Approval() "+ e.getMessage().toString());      
    }
    finally{
      Close(conn);
      Close(ps);
    }    
    return outcome;
  }
    
}
