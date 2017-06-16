package literacy;

import construction.FinalExpenseBean;

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

import mypackage.BudgetSummaryBean;
import mypackage.DBHandler;

public class BudgetSummaryDbBean {
    public BudgetSummaryDbBean() {
        super();
    }
    
    
    
  Connection conn;
  PreparedStatement ps;
  PreparedStatement ps2;
  ResultSet rs;
  NumberFormat numF = new DecimalFormat("#,###,###.##");
  DBHandler dbh = new DBHandler();
  
  
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
    }catch(Exception e){System.out.println(e);}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try{
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){System.out.println(e);}
  }

   public void Close(ResultSet rs)
  {
    try{
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){System.out.println(e);}
  }
   
   
   
   
   
  public ArrayList getBudgetSummariesForExp(long grantid, int expcode)
  {
       ArrayList allexps=new ArrayList();  
      
       try {    
          conn = initializeConn();       
          
          String sql = "select * from BUDGET_SUMMARY where gra_id=? and et_id=?";          
          ps = conn.prepareStatement(sql); 
          ps.setLong(1, grantid);
          ps.setInt(2, expcode);
             
          rs = ps.executeQuery();
          
          while(rs.next()){
              BudgetSummaryBean bs = new BudgetSummaryBean();
              bs.setId(rs.getLong("ID"));
              bs.setGrantId(grantid);
              bs.setExpensecode(rs.getInt("et_id"));
              bs.setFycode(rs.getInt("fy_Id"));//??everywhere else in db this is fy_code
              bs.setServiceType(rs.getString("service_type"));
              bs.setVendor(rs.getString("vendor"));
              bs.setDescription(rs.getString("DESCRIPTION"));              
              bs.setAmount(rs.getInt("amount"));
              bs.setAmountStr(rs.getString("amount"));
              
              if(bs.getAmountStr()!=null && !bs.getAmountStr().equals("")){
                long amt = Long.parseLong(bs.getAmountStr());
                bs.setAmountStr(numF.format(amt));
              }
                         
              allexps.add(bs);
          }            
                                    
       } catch (Exception ex){
          System.err.println("error BudgetSummaryDbBean.getBudgetSummariesForExp() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allexps;
  }
  
  
  
  
  public int calcBudgetSummaryTotalForFyExp(long grantid, int fycode, int expcode)
  {
       int total =0;
      
       try {    
          conn = initializeConn();       
          
          String sql = "select sum(amount) as total_amount from BUDGET_SUMMARY "+
        		  		" where gra_id=? and fy_id=? and et_id=?";          
          ps = conn.prepareStatement(sql); 
          ps.setLong(1, grantid);
          ps.setInt(2, fycode);
          ps.setInt(3, expcode);
             
          rs = ps.executeQuery();
          
          while(rs.next()){
              total = rs.getInt("total_amount");
          }            
                                    
       } catch (Exception ex){
          System.err.println("error BudgetSummaryDbBean.calcBudgetSummaryTotalForFyExp() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return total;
  }
  
  
  
  
  public int determineExpenditureType(String expenseName)
  {
       int expenditureTypeId =0;
      
       try {    
          conn = initializeConn();       
          
          String sql = "select * from EXPENDITURE_TYPES "+
        		  		" where lower(expenditure_name) like lower(?)";          
          ps = conn.prepareStatement(sql); 
          ps.setString(1, expenseName+"%");
          
             
          rs = ps.executeQuery();
          
          while(rs.next()){
        	  expenditureTypeId = rs.getInt("id");
          }            
                                    
       } catch (Exception ex){
          System.err.println("error BudgetSummaryDbBean.determineExpenditureType() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return expenditureTypeId;
  }
  
  
  
  
  
  
  public int insertOrUpdateBudgetSummary(List allrecords, String userid)
  {
      int outcome =0;           
      try {    
          conn = initializeConn();
     
          String update = "UPDATE BUDGET_SUMMARY SET " +
          " service_type=?, vendor=?, description=?, amount=?, fy_id=?, date_modified=sysdate, "+
          " modified_by=? where id=?";   
                   
          String insert = "INSERT INTO BUDGET_SUMMARY(id, amount, service_type, vendor, description, "+
                          " date_created, created_by, fy_id, et_id, gra_id)  VALUES  " +
                          " (bs_seq.nextval, ?, ?, ?, ?, sysdate, ?, ?, ?, ?) ";
          
          ps = conn.prepareStatement(insert);
          ps2 = conn.prepareStatement(update);
          
          //get the number records
          int numRecs = 0;
          if(allrecords !=null)
            numRecs = allrecords.size();
                       
          //loop on each records and update db
          for(int i=0;i<numRecs; i++)
          {
              BudgetSummaryBean bs = (BudgetSummaryBean) allrecords.get(i);
              
              String amtexp = bs.getAmountStr();
              int amt = 0;
              if(amtexp!= null && !amtexp.equals(""))
                amt = dbh.parseCurrencyAmtNoDecimal(amtexp);
              
              if(bs.getId()==0){//do insert
                ps.setInt(1, amt);
                ps.setString(2, bs.getServiceType());
                ps.setString(3, bs.getVendor());
                ps.setString(4, bs.getDescription());
                ps.setString(5, userid);
                ps.setInt(6, bs.getFycode());
                ps.setInt(7, bs.getExpensecode());
                ps.setLong(8, bs.getGrantId());
                ps.addBatch();                
              }
              else{//do update                
                  ps2.setString(1, bs.getServiceType());
                  ps2.setString(2, bs.getVendor());
                  ps2.setString(3, bs.getDescription());
                  ps2.setInt(4, amt);
                  ps2.setInt(5, bs.getFycode());
                  ps2.setString(6, userid);
                  ps2.setLong(7, bs.getId());
                  ps2.addBatch();
              }
                
          }
          
          //execute baatches
          ps.executeBatch();
          ps2.executeBatch();
                                
      } catch (Exception ex){
            System.err.println("error insertOrUpdateBudgetSummary()  " + ex.toString());
      }
      finally{
          Close(conn);
          Close(rs);
          Close(ps);
          Close(ps2);
      }   
      return outcome;
  }
  
  
  
  
  public int deleteBudgetSummary(long id)
  {
      int outcome =0;           
      try {    
          conn = initializeConn();       
          ps = conn.prepareStatement("delete from BUDGET_SUMMARY where ID=?");   
          ps.setLong(1, id);
          outcome = ps.executeUpdate();
                                              
      } catch (Exception ex){
            System.err.println("error deleteBudgetSummary() " + ex.toString());
      }
      finally{
          Close(conn);
          Close(rs);
          Close(ps);
      }   
      return outcome;
  }
  
  
  
  
  
  
}
