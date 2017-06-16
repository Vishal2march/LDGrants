package stateaid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import mypackage.DBHandler;
import mypackage.GrantBean;

public class AllocationDbBean {
    public AllocationDbBean() {
        super();
    }
    
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  NumberFormat numF = new DecimalFormat("#,###,###");
  
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
    try{
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try{
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

   public void Close(ResultSet rs)
  {
    try{
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }



  //need to change amt_req to amount_req for tref to pref
  public List<GrantBean> getStateaidAppsForFy(long fycode)
  {      
    ArrayList<GrantBean> allapps = new ArrayList();          
    try {
      conn = initializeConn();      
                  
      ps = conn.prepareStatement("SELECT grants.id, co_institutions.inst_id, fc_code, fy_code, amount_req, " + 
      " proj_seq_num, contract_num,  fiscal_years.description, legal_name, " + 
      " start_date, end_date, fund_codes.description AS progname " + 
      " FROM grants, co_institutions " + 
      " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id, fiscal_years, " + 
      " fund_codes WHERE fc_code=20 and fy_code=? AND is_lead = 'Y' AND grants.ID = co_institutions.gra_id " + 
      " AND grants.fy_code = fiscal_years.code AND grants.fc_code = fund_codes.code");    
      ps.setLong(1, fycode);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("id"));
        gb.setInstID(rs.getLong("inst_id"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));  
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        gb.setInstName(rs.getString("legal_name"));
        gb.setFiscalyear(rs.getString("description"));  
        gb.setProgram(rs.getString("progname"));
        gb.setContractNum(rs.getString("contract_num"));
        gb.setStartdate(rs.getDate("start_date"));//8/13/08 changed db start/end dates to 7/1-6/30
        gb.setEnddate(rs.getDate("end_date"));       
        gb.setLdacAppropAmt(rs.getLong("amount_req"));        
        gb.setLdacAppropAmtStr(rs.getString("amount_req"));
          
        if(gb.getLdacAppropAmtStr()!=null && !gb.getLdacAppropAmtStr().equals(""))
        {
          long amt = Long.parseLong(gb.getLdacAppropAmtStr());
          gb.setLdacAppropAmtStr(numF.format(amt));
        }
          
        allapps.add(gb);
      }               
             
    }catch(Exception e){
      System.err.println("error getStateaidAppsForFy() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(ps);
    }
    return allapps;
  }
  
  
  
  
  public int addUpdateAllocation(List<GrantBean> allapps, String username)
  {      
    int outcome=0;          
    try {
      DBHandler dbh = new DBHandler();
        
      conn = initializeConn();           
      String sqlUpdate = "update grants set amount_req=?, date_modified=sysdate, modified_by=? where id = ?";                  
      ps = conn.prepareStatement(sqlUpdate);    
              
      if(allapps.size()>0){
        
          for(int i=0; i<allapps.size(); i++){
              
            GrantBean gb = allapps.get(i);   
              
            //parse out $ or comma/decimal from string amount; convert to int            
            String alloc = gb.getLdacAppropAmtStr();
            int allocint=0;
            if(alloc!= null && !alloc.equals(""))
            allocint = dbh.parseCurrencyAmtNoDecimal(alloc);//this will get rid of any commas, decimals or $ in the value
                        
            ps.setInt(1, allocint);
            ps.setString(2, username);
            ps.setLong(3, gb.getGrantid());
                       
            ps.addBatch();
          }        
          ps.executeBatch();        
      }
                 
    }catch(Exception e){
      System.err.println("error addUpdateAllocation() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(ps);
    }
    return outcome;
  }
  
}
