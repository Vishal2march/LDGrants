/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AppDatesDBbean.java
 * Creation/Modification History  :
 *
 * SH   10/1/07      Created
 *
 * Description
 * This class will handle db updates to app_dates table.  It will insert/update
 * records from table, and get all app date records.
 *****************************************************************************/
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

public class AppDatesDBbean 
{
  public AppDatesDBbean()
  {
  }
  
  NumberFormat numF = new DecimalFormat("##,###,###.##");
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

  public int addAppDateRecord(AppDatesBean ab, UserBean lduser)
  {
    int outcome =0;
    int nextid = 0;    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select max(ID) as maxnum from APP_DATES ");
      rs = ps.executeQuery();
      while(rs.next()){
        nextid = rs.getInt("maxnum") +1;
      }
      
      Close(rs);
      Close(ps);
            
      String fund = ab.getTotalfundStr();
      int fund_int = 0;
      if(fund!= null && !fund.equals(""))
      {
        DBHandler dbh = new DBHandler();
        fund_int = dbh.parseCurrencyAmtNoDecimal(fund);
      }
      
      ps = conn.prepareStatement("insert into APP_DATES (ID, START_DATE, DUE_DATE, FC_CODE, "+
      " FY_CODE, DATE_CREATED, CREATED_BY, TOTAL_FUND, FINAL_RPT_DATE, INTERIM_RPT_DATE, END_DATE, EXTENSION_DATE) values "+
      " (?,to_date(?, 'mm/dd/yy'),to_date(?, 'mm/dd/yy hh:mi pm'),?,?,sysdate,?,?, to_date(?, 'mm/dd/yy hh:mi pm'), "+
      " to_date(?, 'mm/dd/yy'), to_date(?, 'mm/dd/yy hh:mi pm'), to_date(?, 'mm/dd/yy') )");
      ps.setInt(1, nextid);
      ps.setString(2, ab.getStartdateStr());
      ps.setString(3, ab.getDuedateStr());
      ps.setInt(4, ab.getFccode());
      ps.setInt(5, ab.getFycode());
      ps.setString(6, lduser.getUserid());
      ps.setInt(7, fund_int);
      ps.setString(8, ab.getFinalrptdateStr());
      ps.setString(9, ab.getInterimrptdateStr());
      ps.setString(10, ab.getReviewdateStr());
      ps.setString(11, ab.getExtensiondateStr());
      
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error addAppDateRecord() "+e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }    
    return outcome;
  }

  public Vector getAllAppDateRecords(String fundCodeList)
  {
    Vector results = new Vector();
    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select APP_DATES.ID, APP_DATES.START_DATE, TOTAL_FUND, "+
      " DUE_DATE, FY_CODE, FC_CODE, FISCAL_YEARS.DESCRIPTION as fydesc, FUND_CODES.DESCRIPTION as fcdesc, EXTENSION_DATE "+
      " from APP_DATES, FISCAL_YEARS, FUND_CODES where FUND_CODES.CODE=APP_DATES.FC_CODE and "+
      " FISCAL_YEARS.CODE =APP_DATES.FY_CODE and fc_code in ("+fundCodeList+") order by fy_code desc, fc_code ");
      rs = ps.executeQuery();
      while(rs.next())
      {
        AppDatesBean ab = new AppDatesBean();
        ab.setId(rs.getLong("ID"));
        ab.setFycode(rs.getInt("FY_CODE"));
        ab.setFccode(rs.getInt("FC_CODE"));
        ab.setFiscalyear(rs.getString("fydesc"));
        ab.setFundcode(rs.getString("fcdesc"));
        ab.setStartdate(rs.getDate("START_DATE"));
        ab.setDuedate(rs.getDate("DUE_DATE"));
        ab.setStartdateStr(rs.getString("START_DATE"));
        ab.setDuedateStr(rs.getString("DUE_DATE"));
        ab.setTotalfund(rs.getInt("TOTAL_FUND"));
        ab.setTotalfundStr(rs.getString("TOTAL_FUND"));
        
        if(ab.getTotalfundStr()!=null && !ab.getTotalfundStr().equals(""))
        {
          long fund = Long.parseLong(ab.getTotalfundStr());
          ab.setTotalfundStr(numF.format(fund));
        }          
        results.add(ab);        
      }      
      
    }catch(Exception e){
      System.err.println("error getAllAppDateRecords() "+e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return results;
  }

  public AppDatesBean getDateRecord(long id)
  {
    AppDatesBean ab = new AppDatesBean();
    Format formatter = new SimpleDateFormat("MM/dd/yyyy");
    Format formathour = new SimpleDateFormat("MM/dd/yyyy h:mm a");
      
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from APP_DATES where ID=?");
      ps.setLong(1, id);
      rs = ps.executeQuery();
      while(rs.next())
      {
        ab.setId(rs.getLong("ID"));
        if(rs.getDate("DUE_DATE")!=null)
        {
            //System.out.println("whith h: "+ formathour.format(rs.getTimestamp("due_date")));
            ab.setDuedateStr(formathour.format(rs.getTimestamp("due_DATE") ));      
        }
                     
        if(rs.getDate("START_DATE")!=null)     
            ab.setStartdateStr(formatter.format(rs.getDate("START_DATE")) );
        
        if(rs.getDate("INTERIM_RPT_DATE")!=null)
            ab.setInterimrptdateStr(formatter.format(rs.getDate("INTERIM_RPT_DATE")) );
        
        //7/26/11 for lgrmif, FC wants date/time final rpt due date   
        if(rs.getDate("FINAL_RPT_DATE")!=null)
            ab.setFinalrptdateStr(formathour.format(rs.getTimestamp("FINAL_RPT_DATE")) );
        
        if(rs.getDate("END_DATE")!=null)
            ab.setReviewdateStr(formathour.format(rs.getTimestamp("END_DATE")));
        
        if(rs.getDate("EXTENSION_DATE")!=null)
            ab.setExtensiondateStr(formatter.format(rs.getDate("EXTENSION_DATE")));
        
        ab.setDuedate(rs.getTimestamp("DUE_DATE"));
        ab.setStartdate(rs.getDate("START_DATE"));
        ab.setInterimrptdate(rs.getDate("INTERIM_RPT_DATE"));
        ab.setFinalrptdate(rs.getDate("FINAL_RPT_DATE"));
        ab.setReviewdate(rs.getDate("end_date"));
        ab.setExtensiondate(rs.getDate("EXTENSION_DATE"));
        ab.setFccode(rs.getInt("FC_CODE"));
        ab.setFycode(rs.getInt("FY_CODE"));
        ab.setTotalfund(rs.getInt("TOTAL_FUND"));
        ab.setTotalfundStr(rs.getString("TOTAL_FUND"));
        
        if(ab.getTotalfundStr()!=null && !ab.getTotalfundStr().equals("")){
          long fund = Long.parseLong(ab.getTotalfundStr());
          ab.setTotalfundStr(numF.format(fund));
        }
      }
      
    }catch(Exception e){
      System.err.println("error getDateRecord() "+e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }       
    return ab;
  }

  //modified 7/26/11 to save date/time for final_rpt_date per FC
  public int updateDateRecord(AppDatesBean ab, UserBean lduser)
  {
    int outcome =0;
        
    try{
      String fund = ab.getTotalfundStr();
      int fund_int = 0;
      if(fund!= null && !fund.equals(""))
      {
        DBHandler dbh = new DBHandler();
        fund_int = dbh.parseCurrencyAmtNoDecimal(fund);
      }
              
      conn = initializeConn();
      ps = conn.prepareStatement("update APP_DATES set FC_CODE=?, FY_CODE=?, START_DATE=to_date(?, 'mm/dd/yy'), "+
      " DUE_DATE=to_date(?, 'mm/dd/yy hh:mi pm'), DATE_MODIFIED=SYSDATE, MODIFIED_BY=?, TOTAL_FUND=?, "+
      " INTERIM_RPT_DATE=to_date(?, 'mm/dd/yy'), FINAL_RPT_DATE=to_date(?, 'mm/dd/yy hh:mi pm'), "+
      " end_date=to_date(?, 'mm/dd/yy hh:mi pm'), "+
      " extension_date = to_date(?, 'mm/dd/yy')  where ID=? ");
      ps.setInt(1, ab.getFccode());
      ps.setInt(2, ab.getFycode());
      ps.setString(3, ab.getStartdateStr());
      ps.setString(4, ab.getDuedateStr());
      ps.setString(5, lduser.getUserid());
      ps.setInt(6, fund_int);
      ps.setString(7, ab.getInterimrptdateStr());
      ps.setString(8, ab.getFinalrptdateStr());
      ps.setString(9, ab.getReviewdateStr());
      ps.setString(10, ab.getExtensiondateStr());
      ps.setLong(11, ab.getId());
      
      outcome = ps.executeUpdate();
      
    }catch(Exception e){
      System.err.println("error updateDateRecord() "+e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }     
    return outcome;
  }  
  
  
  public int getTotalFund(int fycode, int fccode){
      int totalamt =0;
          
      try{                
        conn = initializeConn();
        ps = conn.prepareStatement("select total_fund from app_dates where fy_code=? "+
        "and fc_code=?");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        rs = ps.executeQuery();
        
        while(rs.next()){
            totalamt = rs.getInt("total_fund");
        }
        
      }catch(Exception e){
        System.err.println("error getTotalFund() "+e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }     
      return totalamt;
    }
  
  
  
  
  
  public AppDatesBean searchDateByFundFiscalYear(long fyCode, long fcCode)
  {
    AppDatesBean ab = new AppDatesBean();
    Format formatter = new SimpleDateFormat("MM/dd/yyyy");
    Format formathour = new SimpleDateFormat("MM/dd/yyyy h:mm a");
      
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from APP_DATES where fy_code=? and fc_code=?");
      ps.setLong(1, fyCode);
      ps.setLong(2, fcCode);
      rs = ps.executeQuery();
      while(rs.next())
      {
        ab.setId(rs.getLong("ID"));
        if(rs.getDate("DUE_DATE")!=null)
        {
            //System.out.println("whith h: "+ formathour.format(rs.getTimestamp("due_date")));
            ab.setDuedateStr(formathour.format(rs.getTimestamp("due_DATE") ));      
        }
                     
        if(rs.getDate("START_DATE")!=null)     
            ab.setStartdateStr(formatter.format(rs.getDate("START_DATE")) );
        
        if(rs.getDate("INTERIM_RPT_DATE")!=null)
            ab.setInterimrptdateStr(formatter.format(rs.getDate("INTERIM_RPT_DATE")) );
        
        //7/26/11 for lgrmif, FC wants date/time final rpt due date   
        if(rs.getDate("FINAL_RPT_DATE")!=null)
            ab.setFinalrptdateStr(formathour.format(rs.getTimestamp("FINAL_RPT_DATE")) );
        
        if(rs.getDate("END_DATE")!=null)
            ab.setReviewdateStr(formathour.format(rs.getTimestamp("END_DATE")));
        
        if(rs.getDate("EXTENSION_DATE")!=null)
            ab.setExtensiondateStr(formatter.format(rs.getDate("EXTENSION_DATE")));
        
        ab.setDuedate(rs.getTimestamp("DUE_DATE"));
        ab.setStartdate(rs.getDate("START_DATE"));
        ab.setInterimrptdate(rs.getDate("INTERIM_RPT_DATE"));
        ab.setFinalrptdate(rs.getDate("FINAL_RPT_DATE"));
        ab.setReviewdate(rs.getDate("end_date"));
        ab.setExtensiondate(rs.getDate("EXTENSION_DATE"));
        ab.setFccode(rs.getInt("FC_CODE"));
        ab.setFycode(rs.getInt("FY_CODE"));
        ab.setTotalfund(rs.getInt("TOTAL_FUND"));
        ab.setTotalfundStr(rs.getString("TOTAL_FUND"));
        
        if(ab.getTotalfundStr()!=null && !ab.getTotalfundStr().equals("")){
          long fund = Long.parseLong(ab.getTotalfundStr());
          ab.setTotalfundStr(numF.format(fund));
        }
      }
      
    }catch(Exception e){
      System.err.println("error getDateRecord() "+e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }       
    return ab;
  }
      


}
  
  
