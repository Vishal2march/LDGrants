package construction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.TotalsBean;
import mypackage.UserBean;

public class ConstructAllocationDbBean {
    public ConstructAllocationDbBean() {
    }
        
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    NumberFormat numF = new DecimalFormat("##,###,###.##");
    Format formatter = new SimpleDateFormat("MM/dd/yyyy");  
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
    
    /**
     * added fc_code param to reuse for cn/lit
     * @param fycode
     * @param fccode
     * @return
     */
    public ArrayList getAllAllocationsForYear(int fycode, int fccode)
    {
         ArrayList allrecs=new ArrayList();  
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select sfd.id, sfd.due_date, sfd.initial_allocation, sfd.fy_code, sfd.fc_code," + 
            "sfd.final_allocation, sfd.final_recommendation, lsm_id, description, inst_id_has, initcap(popular_name) as popular_name " + 
            "from system_fiscalyear_details sfd " + 
            "left join fiscal_years on sfd.fy_code=fiscal_years.code, " + 
            "ldstateaid.library_system_mappings lsm " + 
            "left join sed_institutions on lsm.inst_id_has=sed_institutions.inst_id " + 
            "where sfd.lsm_id = lsm.id and sfd.fy_code=? and sfd.fc_code=? order by popular_name");   
            ps.setInt(1, fycode);
            ps.setInt(2, fccode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                AllocationYearBean ab = new AllocationYearBean();
                ab.setSystemYearDetailId(rs.getLong("id"));
                ab.setDueDateStr(rs.getString("due_date"));
                ab.setInitialAlloc(rs.getLong("initial_allocation"));
                ab.setInitialAllocStr(rs.getString("initial_allocation"));
                ab.setAdditionalAlloc(rs.getLong("final_allocation"));
                ab.setAdditionalAllocStr(rs.getString("final_allocation"));
                ab.setFinalRecommend(rs.getLong("final_recommendation"));
                ab.setFinalRecommendStr(rs.getString("final_recommendation"));
                ab.setFycode(rs.getInt("fy_code"));
                ab.setFccode(rs.getInt("fc_code"));
                ab.setLibrarySystemMapId(rs.getLong("lsm_id"));
                ab.setYear(rs.getString("description"));
                ab.setSystemInstId(rs.getLong("inst_id_has"));
                ab.setSystemName(rs.getString("popular_name"));
                
                String myDate =null;
                if(rs.getDate("due_date")!=null)
                   myDate = formatter.format(rs.getDate("due_date"));                
                ab.setDueDateStr(myDate);
                
                allrecs.add(ab);
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getAllAllocationsForYear() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    /**
     * get all allocation/due date records for all FY's for given PLS.
     * modified 10/4/12 to use only for construction
     * @param systemInstId
     * @return
     */
    public ArrayList getAllAllocationsForSystem(long systemInstId)
    {
         ArrayList allrecs=new ArrayList();  
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select sfd.id as id, sfd.due_date as sysduedate, "+
            " initial_allocation, sfd.fy_code, description, app_dates.due_date as appduedate, " + 
            "lsm_id, inst_id_has, initcap(popular_name) as popular_name " + 
            "from system_fiscalyear_details sfd, fiscal_years " + 
            "left join app_dates on fiscal_years.code=app_dates.fy_code and app_dates.fc_code=86, " + 
            "ldstateaid.library_system_mappings lsm " + 
            "left join sed_institutions on lsm.inst_id_has=sed_institutions.inst_id " + 
            "where sfd.fy_code=fiscal_years.code and sfd.lsm_id= lsm.id " + 
            "and sfd.fc_code=86 and inst_id_has=?  order by sfd.fy_code");   
            ps.setLong(1, systemInstId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                AllocationYearBean ab = new AllocationYearBean();
                ab.setSystemYearDetailId(rs.getLong("id"));
                
                String myDate =null;
                if(rs.getDate("sysduedate")!=null)
                   myDate = formatter.format(rs.getDate("sysduedate"));                
                ab.setDueDateStr(myDate);
                
                myDate=null;
                if(rs.getDate("appduedate")!=null)
                    myDate=formatter.format(rs.getDate("appduedate"));
                ab.setProgramDueDateStr(myDate);
                
                ab.setInitialAlloc(rs.getLong("initial_allocation"));
                ab.setInitialAllocStr(rs.getString("initial_allocation"));
                ab.setFycode(rs.getInt("fy_code"));
                ab.setLibrarySystemMapId(rs.getLong("lsm_id"));
                ab.setYear(rs.getString("description"));
                ab.setSystemInstId(rs.getLong("inst_id_has"));
                ab.setSystemName(rs.getString("popular_name"));
                allrecs.add(ab);
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getAllAllocationsForSystem() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    public AllocationYearBean getAllocationRecord(long systemYearDetailId)
    {
        AllocationYearBean ab = new AllocationYearBean();
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select * from system_fiscalyear_details sfd where id=?");   
            ps.setLong(1, systemYearDetailId);
            rs = ps.executeQuery();
            
            while(rs.next()){                
                ab.setSystemYearDetailId(rs.getLong("id"));
                ab.setFycode(rs.getInt("fy_code"));
                ab.setFccode(rs.getInt("fc_code"));
                ab.setYear("20"+ab.getFycode());
                ab.setLibrarySystemMapId(rs.getLong("lsm_id"));
                ab.setInitialAlloc(rs.getLong("initial_allocation"));
                ab.setAdditionalAlloc(rs.getLong("final_allocation"));
                ab.setFinalRecommend(rs.getLong("final_recommendation"));
                
                String myDate =null;
                if(rs.getDate("due_date")!=null)
                   myDate = formatter.format(rs.getDate("due_date"));                
                ab.setDueDateStr(myDate);
                                
                ab.setInitialAllocStr(rs.getString("initial_allocation"));
                if(ab.getInitialAllocStr()!=null && !ab.getInitialAllocStr().equals("")){
                  long amt = Long.parseLong(ab.getInitialAllocStr());
                  ab.setInitialAllocStr(numF.format(amt));
                }      
                
                ab.setAdditionalAllocStr(rs.getString("final_allocation"));
                if(ab.getAdditionalAllocStr()!=null && !ab.getAdditionalAllocStr().equals("")){
                  long amt = Long.parseLong(ab.getAdditionalAllocStr());
                  ab.setAdditionalAllocStr(numF.format(amt));
                }    
                
                ab.setFinalRecommendStr(rs.getString("final_recommendation"));
                if(ab.getFinalRecommendStr()!=null && !ab.getFinalRecommendStr().equals("")){
                  long amt = Long.parseLong(ab.getFinalRecommendStr());
                  ab.setFinalRecommendStr(numF.format(amt));
                }    
                
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getAllocationRecord() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return ab;
    }   
    
    
    public long getIdExistingAllocation(int fycode, long librarySystemMapId, int fccode)
    {
        long systemYearId=0;
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select id from system_fiscalyear_details sfd "+
            " where fy_code=? and lsm_id=? and fc_code=?");  
            ps.setInt(1, fycode);
            ps.setLong(2, librarySystemMapId);
            ps.setInt(3, fccode);
            rs = ps.executeQuery();
            
            while(rs.next()){                
                systemYearId=rs.getLong("id");
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getIdExistingAllocation() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return systemYearId;
    }   
    
    
    public int insertSystemAllocationRecord(AllocationYearBean ab, UserBean ub)
    {
         int outcome=0;         
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("insert into SYSTEM_FISCALYEAR_DETAILS (ID, "+
            " INITIAL_ALLOCATION, FY_CODE, LSM_ID, DATE_CREATED, CREATED_BY, FINAL_ALLOCATION, FC_CODE, FINAL_RECOMMENDATION) "+
            " values (SFD_SEQ.nextval, ?, ?, ?, SYSDATE, ?, ?, ?, ?)");   
            
             //parse out an $ or decimals in amount field
             String amtalloc = ab.getInitialAllocStr();
             long amtalloc_num = 0;
             if(amtalloc!= null && !amtalloc.equals(""))
               amtalloc_num = dbh.parseLongAmtNoDecimal(amtalloc);
               
            String additalloc = ab.getAdditionalAllocStr();
            long additalloc_num=0;
            if(additalloc!=null && !additalloc.equals(""))
                additalloc_num = dbh.parseLongAmtNoDecimal(additalloc);
             
           String finalrecom = ab.getFinalRecommendStr();
           long finalrecom_num=0;
           if(finalrecom!=null && !finalrecom.equals(""))
               finalrecom_num = dbh.parseLongAmtNoDecimal(finalrecom);
                           
            ps.setLong(1, amtalloc_num);
            ps.setInt(2, ab.getFycode());
            ps.setLong(3, ab.getLibrarySystemMapId());
            ps.setString(4, ub.getUserid());        
            ps.setLong(5, additalloc_num);
            ps.setInt(6, ab.getFccode());
            ps.setLong(7, finalrecom_num);
            outcome=ps.executeUpdate();
                                      
         } catch (Exception ex){
            System.err.println("error insertSystemAllocationRecord() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return outcome;
    }
    
    
    public int updateSystemAllocationRecord(AllocationYearBean ab, UserBean ub)
    {
         int outcome=0;         
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("update SYSTEM_FISCALYEAR_DETAILS set "+
            " INITIAL_ALLOCATION=?, FY_CODE=?, LSM_ID=?, DATE_MODIFIED=sysdate, "+
            " MODIFIED_BY=?, FINAL_ALLOCATION=?, FC_CODE=?, FINAL_RECOMMENDATION=? where ID=?");   
            
             //parse out an $ or decimals in amount field
             String amtalloc = ab.getInitialAllocStr();
             long amtalloc_num = 0;
             if(amtalloc!= null && !amtalloc.equals(""))
               amtalloc_num = dbh.parseLongAmtNoDecimal(amtalloc);
             
             String additalloc = ab.getAdditionalAllocStr();
             long additalloc_num=0;
             if(additalloc!=null && !additalloc.equals(""))
                 additalloc_num = dbh.parseLongAmtNoDecimal(additalloc);
             
             String finalrecom = ab.getFinalRecommendStr();
             long finalrecom_num=0;
             if(finalrecom!=null && !finalrecom.equals(""))
                 finalrecom_num = dbh.parseLongAmtNoDecimal(finalrecom);
                 
            ps.setLong(1, amtalloc_num);
            ps.setInt(2, ab.getFycode());
            ps.setLong(3, ab.getLibrarySystemMapId());
            ps.setString(4, ub.getUserid());   
            ps.setLong(5, additalloc_num);
            ps.setInt(6, ab.getFccode());
            ps.setLong(7, finalrecom_num);
            ps.setLong(8, ab.getSystemYearDetailId());
            outcome=ps.executeUpdate();
                                      
         } catch (Exception ex){
            System.err.println("error updateSystemAllocationRecord() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return outcome;
    }
    
    public int updateSystemDueDate(AllocationYearBean ab, UserBean ub)
    {
         int outcome=0;         
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("update SYSTEM_FISCALYEAR_DETAILS set "+
            " DUE_DATE=to_date(?, 'mm/dd/yyyy'), DATE_MODIFIED=sysdate, "+
            " MODIFIED_BY=? where ID=?");                           
            ps.setString(1, ab.getDueDateStr());
            ps.setString(2, ub.getUserid());   
            ps.setLong(3, ab.getSystemYearDetailId());
            outcome=ps.executeUpdate();
                                      
         } catch (Exception ex){
            System.err.println("error updateSystemDueDate() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return outcome;
    }
    
    public AllocationYearBean calcRequestApprAmounts(long grantid, int fycode)
    {
       AllocationYearBean ab = new AllocationYearBean();
       try{
            //get total amt requested from project budget page totals
            BudgetDBHandler bdh = new BudgetDBHandler();
            ab.setAmountRequested(bdh.calcTotalAmtRequested(grantid, 0));   
            
            if(fycode < 13){
                //calc max 50% that can be awarded based on amt_requested
                if(ab.getAmountRequested()>0){
                    double maxamt = ab.getAmountRequested()/2;
                    int maxamtint = (int)Math.round(maxamt);
                    ab.setMaxRequestCost(maxamtint);
                }
            }else{            
                //calc max 75% that can be awarded based on amt_requested, starting 2012/13
                if(ab.getAmountRequested()>0){
                    double maxamt = ab.getAmountRequested()* 0.75;
                    int maxamtint = (int)Math.round(maxamt);
                    ab.setMaxRequestCost(maxamtint);
                }
            }
            
            //get cost requested (c) from coversheet
            conn = initializeConn();
            ps = conn.prepareStatement("select * from building_funds where fs_id=2 and "+
            " gb_id in (select id from GRANT_BUILDINGS where gra_id=?)");
            ps.setLong(1, grantid);
            rs = ps.executeQuery();
            while(rs.next()){              
                ab.setRequestCost(rs.getInt("amount_received"));                    
            }
            
        }catch (Exception ex){
          System.err.println("error calcRequestApprAmounts() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return ab;    
    }    
    
    
    public TotalsBean calcMaxAmtSysRecommend(long grantid, TotalsBean tb, int fycode)
    {
       try{            
          if(fycode <13){                
             //calc max 50% award based on total_amt_requested from budget tabs
             if(tb.getTotAmtReq()>0){
                double maxamt = tb.getTotAmtReq()/2;
                int maxamtint = (int)Math.round(maxamt);
                tb.setMaxRequestCost(maxamtint);
             }
          }
          else{
              //calc max 75% award based on amt_requested, starting 2012/13
              if(tb.getTotAmtReq()>0){
                  double maxamt = tb.getTotAmtReq()* 0.75;
                  int maxamtint = (int)Math.round(maxamt);
                  tb.setMaxRequestCost(maxamtint);
              }
          }
            
            //get requested cost (c) from coversheet
            conn = initializeConn();
            ps = conn.prepareStatement("select * from building_funds where fs_id=2 and "+
            " gb_id in (select id from GRANT_BUILDINGS where gra_id=?)");
            ps.setLong(1, grantid);
            rs = ps.executeQuery();
            while(rs.next()){              
                tb.setRequestCost(rs.getInt("amount_received"));                    
            }
            
            //give admin warning if total_approved is more than the max amt
            if(tb.getTotAmtAppr() > tb.getMaxRequestCost())
                tb.setWarning(true);
                
        }catch (Exception ex){
          System.err.println("error calcMaxAmtSysRecommend() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return tb;    
    }    
    
    
    /**
     * Method calculates amt allocated to PLS for this FY. Also, amount recommended so far
     * for this PLS and FY.
     * modified 10/4/12 to filter for construction only
     * @param systemInstId
     * @param fycode
     * @return
     */
    public AllocationYearBean calcAllocAndAwardForPlsFy(long systemInstId, int fycode)
    {
       AllocationYearBean ab = new AllocationYearBean();
       try{
            //get amt available to the pls
            conn = initializeConn();
            ps = conn.prepareStatement("select * from system_fiscalyear_details where " + 
            " fy_code=? and fc_code=86 and lsm_id in (select id from ldstateaid.library_system_mappings " + 
            " where inst_id_has=? and inst_id_has=inst_id and end_date is null)");
            ps.setInt(1, fycode);
            ps.setLong(2, systemInstId);
            rs = ps.executeQuery();
            while(rs.next()){              
                ab.setInitialAlloc(rs.getLong("initial_allocation"));                    
            }
            
            //get amt recommended so far by this pls
            ps = conn.prepareStatement("select sum(recommend_amt) as sum_recommend from system_grant_assigns " + 
            "   where gra_id in (select id from grants where fc_code=86 and fy_code=?) " + 
            "   and lsm_id in (select id from ldstateaid.library_system_mappings " + 
            "   where inst_id_has=?)");
            ps.setInt(1, fycode);
            ps.setLong(2, systemInstId);
            rs = ps.executeQuery();
            while(rs.next()){              
                ab.setTallyAmountRecommend(rs.getLong("sum_recommend"));                    
            }
            
            ab.setDifferenceAllocation( (ab.getInitialAlloc() - ab.getTallyAmountRecommend() ));
        }catch (Exception ex){
          System.err.println("error calcAllocAndAwardForPlsFy() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return ab;    
    }    
    
    
    public long calcAmtRecommendForPlsFy(long systemInstId, int fycode)
    {
       long amtrecommend=0;
       try{
            conn = initializeConn();
            
            //get amt recommended so far by this pls
            ps = conn.prepareStatement("select sum(recommend_amt) as sum_recommend from system_grant_assigns " + 
            "   where gra_id in (select id from grants where fc_code=86 and fy_code=?) " + 
            "   and lsm_id in (select id from ldstateaid.library_system_mappings " + 
            "   where inst_id_has=?)");
            ps.setInt(1, fycode);
            ps.setLong(2, systemInstId);
            rs = ps.executeQuery();
            while(rs.next()){              
                amtrecommend = rs.getLong("sum_recommend");                    
            }
            
        }catch (Exception ex){
          System.err.println("error calcAmtRecommendForPlsFy() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return amtrecommend;    
    }    
    
}
