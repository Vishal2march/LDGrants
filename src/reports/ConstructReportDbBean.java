package reports;

import construction.AllocationYearBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Collections;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import mypackage.DBHandler;
import mypackage.GrantBean;

public class ConstructReportDbBean {
    public ConstructReportDbBean() {
    }
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
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
     * construction - list all PLS's, allocations, and $ awarded so far for FY
     * modified 10/4/12 to include new sfd.fc_code property for filtering
     * @param fycode
     * @return
     */
    public ArrayList getSysAllocationsAwardsForYear(int fycode)
    {
         ArrayList allrecs=new ArrayList();          
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select sfd.id, sfd.due_date, sfd.initial_allocation, sfd.fy_code, " + 
            "sfd.final_allocation, lsm_id, description, inst_id_has, initcap(popular_name) as popular_name " + 
            "from system_fiscalyear_details sfd " + 
            "left join fiscal_years on sfd.fy_code=fiscal_years.code, " + 
            "ldstateaid.library_system_mappings lsm " + 
            "left join sed_institutions on lsm.inst_id_has=sed_institutions.inst_id " + 
            "where sfd.lsm_id = lsm.id and sfd.fy_code=? and sfd.fc_code=86 order by popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                AllocationYearBean ab = new AllocationYearBean();
                ab.setSystemYearDetailId(rs.getLong("id"));
                ab.setInitialAlloc(rs.getLong("initial_allocation"));
                ab.setInitialAllocStr(rs.getString("initial_allocation"));
                ab.setAdditionalAlloc(rs.getLong("final_allocation"));
                ab.setAdditionalAllocStr(rs.getString("final_allocation"));
                ab.setFycode(rs.getInt("fy_code"));
                ab.setLibrarySystemMapId(rs.getLong("lsm_id"));
                ab.setYear(rs.getString("description"));
                ab.setSystemInstId(rs.getLong("inst_id_has"));
                ab.setSystemName(rs.getString("popular_name"));                                
                allrecs.add(ab);
            }                        
            
            //calc amt awarded so far by system
             ps = conn.prepareStatement("select sum(recommend_amt) as sum_recommend from system_grant_assigns " + 
             "   where rating_complete=1 and gra_id in (select id from grants where fc_code=86 and fy_code=?) " + 
             "   and lsm_id in (select id from ldstateaid.library_system_mappings " + 
             "   where inst_id_has=?)");
             
            for(int i=0; i<allrecs.size(); i++){
                AllocationYearBean ab = (AllocationYearBean) allrecs.get(i);
                ps.setInt(1, ab.getFycode());
                ps.setLong(2, ab.getSystemInstId());
                rs = ps.executeQuery();
                while(rs.next()){
                    ab.setTallyAmountRecommend(rs.getLong("sum_recommend")); 
                }
            }
                                      
         } catch (Exception ex){
            System.err.println("error getSysAllocationsAwardsForYear() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    /**
     *Query to generate construction report "Application with project cost, system recommended amount for fiscal year".
     * Modified 2/25/16 to also query BUILDING_FUNDS, on FS_ID=2. This gives the "library amount requested" field from 
     * coversheet, question C.  Added per request from LynneWebb.
     * @param fycode
     * @return
     */
    public ArrayList getSysRecomendPercentByFy(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        DecimalFormat df = new DecimalFormat("#.##%");

         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select  grants.id, name, proj_seq_num, " + 
            "             grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name,  " + 
            "             initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id, " + 
            "             sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, recommend_amt, bf.amount_received, " + 
            "             rating_complete, co_institutions.inst_id, inst_id_has  " + 
            "             from grants, ldgrants.BUDGETTOTALSVIEW, fiscal_years, system_grant_assigns sga,   " + 
            "             grant_buildings, sed_buildings,  building_funds bf,   " + 
            "             ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on   " + 
            "             lsm.inst_id_has=sysinst.inst_id, co_institutions   " + 
            "             left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id   " + 
            "             where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id and grants.fy_code=fiscal_years.code   " + 
            "             and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id    " + 
            "             and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id  " + 
            "             and bf.gb_id = grant_buildings.id and bf.fs_id=2  " + 
            "             and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1  " + 
            "             and grants.id in (select gra_id from grant_submissions where version='Initial')   " + 
            "             order by system_name, popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setFiscalYear(rs.getInt("description"));//fiscal year      
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setBuildingName(rs.getString("building_name"));
                gb.setFyOneRequest(rs.getLong("amount_received"));//this is question C on coversheet; from GRANT_FUNDS table
                
                if(gb.getTotalRequest()>0){                    
                    double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                   // System.out.println(percentaward);
                    gb.setRecommendPercent(percentaward);
                    //need to format percent to 2 decimal places
                    gb.setRecommendPercentStr(df.format(percentaward));
                }
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getSysRecomendPercentByFy() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    public ArrayList getLdAwardPercentByFyForDasny(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        DecimalFormat df = new DecimalFormat("#.##%");

         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select grants.id, name, proj_seq_num, " + 
            " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name,  " + 
            " building_name, initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id,  " + 
            " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, ldgrants.BUDGETTOTALSVIEW.totappr, " + 
            " recommend_amt, rating_complete, co_institutions.inst_id,  " + 
            " inst_id_has from grants, ldgrants.BUDGETTOTALSVIEW, fiscal_years, system_grant_assigns sga, " + 
            " grant_buildings, sed_buildings, ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on " + 
            " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
            " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
            " where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id and grants.fy_code=fiscal_years.code " + 
            " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id  " + 
            " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1  " + 
            " and grants.id in (select gra_id from grant_submissions where version='DASNY')  " + 
            " order by system_name, popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setFiscalYear(rs.getInt("description"));//fiscal year      
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalApproved(rs.getLong("totappr"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setBuildingName(rs.getString("building_name"));
                
                if(gb.getTotalRequest()>0){                    
                    double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                    //need to format percent to 2 decimal places
                    gb.setRecommendPercentStr(df.format(percentaward));
                }
                
                if(gb.getTotalApproved()>0){
                    double percentaward = ((double) gb.getTotalApproved()/gb.getTotalRequest());
                    gb.setApprovedPercentStr(df.format(percentaward));
                }
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getLdAwardPercentByFyForDasny() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    public ArrayList getAmtRecommendProjDescList(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select  grants.id, name, proj_seq_num,  " + 
            " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
            " initcap(sysinst.popular_name) as system_name, lsm_id as mapping_id, " + 
            " sga.id as assignid,  narrative_descr, " + 
            " recommend_amt, rating_complete, co_institutions.inst_id,  inst_id_has, " + 
            " expect_start, building_name, addr_line1, city, state, zipcode " + 
            " from grants, grant_buildings, sed_buildings, addresses, project_narratives, system_grant_assigns sga,  " + 
            " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on  " + 
            " lsm.inst_id_has=sysinst.inst_id, co_institutions  " + 
            " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
            " where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
            " and sed_buildings.bldg_id=addresses.bldg_id and grants.id=project_narratives.gra_id " + 
            " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1  and nat_id=95 " + 
            " and grants.id in (select gra_id from grant_submissions where version='Initial')  " + 
            " order by system_name, popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setTotalApproved(rs.getLong("recommend_amt"));
                /*Note: set recommend_amt to 2 different attributes; needed for
                displaytag formatting of dasny rpt with totals in separate column; per LWebb.
                otherwise displaytag will calc the sum 2x for each system.
                */
                gb.setBuildingName(rs.getString("building_name"));
                gb.setAddr1(rs.getString("addr_line1"));
                gb.setCity(rs.getString("city"));
                gb.setState(rs.getString("state"));
                gb.setZipcd1(rs.getString("zipcode"));
               
                gb.setStartdate(rs.getDate("expect_start"));
                gb.setSummaryDescr(rs.getString("narrative_descr"));
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getAmtRecommendProjDescList() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    /**
     * get apps submitted to dasny, along with amt req/recommend/appr, 
     * and if dasny approved, and bond council approved.  results displayed
     * on same jsp as for cn admin (although cn admin has diff query)
     * @param fycode
     * @return
     */
    public ArrayList getAppsSubmitApprByDasny(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        
         try {    
            conn = initializeConn();       
             ps = conn.prepareStatement("select  grants.id, name, proj_seq_num,  " + 
             "  grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name,  " + 
             "  building_name, initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id,  " + 
             "  sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, ldgrants.BUDGETTOTALSVIEW.totappr,  " + 
             "  rating_complete, co_institutions.inst_id, inst_id_has, sga.recommend_amt, " + 
             "  case  (select version from grant_submissions where gra_id=grants.id and version='DASNY')  " + 
             "  when 'DASNY' then 1 else 0 end dasnysubmit,  " + 
             "  case  (select version from approvals where gra_id=grants.id and version='DASNY')  " + 
             "  when 'DASNY' then 1 else 0 end dasnyapprove,  " + 
             "  case  (select version from approvals where gra_id=grants.id and version='BondCouncil') " + 
             "  when 'BondCouncil' then 1 else 0 end bondapprove  " + 
             "  from grants, ldgrants.BUDGETTOTALSVIEW, fiscal_years, system_grant_assigns sga, " + 
             "  grant_buildings, sed_buildings,  " + 
             "  ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on   " + 
             "  lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
             "  left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
             "  where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id and grants.fy_code=fiscal_years.code  " + 
             "  and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id  " + 
             "  and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
             "  and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1    " + 
             "  and grants.id in (select gra_id from grant_submissions where version='DASNY')  " + 
             "  order by system_name, popular_name");
              ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setFiscalYear(rs.getInt("description"));//fiscal year      
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalApproved(rs.getLong("totappr"));
                gb.setDasnySubmit(rs.getBoolean("dasnysubmit"));
                gb.setDasnyApprove(rs.getBoolean("dasnyapprove"));
                gb.setBondCouncilApprove(rs.getBoolean("bondapprove"));
                gb.setBuildingName(rs.getString("building_name"));
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getAppsSubmitApprByDasny() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    public ArrayList getLDAwardPercentForFy(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        DecimalFormat df = new DecimalFormat("#.##%");

         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select  grants.id, name, proj_seq_num,  " + 
            " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name,  " + 
            " building_name, initcap(sysinst.popular_name) as system_name, " + 
            " fiscal_years.description, lsm_id as mapping_id, " + 
            " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, ldgrants.BUDGETTOTALSVIEW.totappr, " + 
            " recommend_amt, rating_complete, co_institutions.inst_id, " + 
            " inst_id_has from grants, ldgrants.BUDGETTOTALSVIEW, fiscal_years, system_grant_assigns sga,  " + 
            " grant_buildings, sed_buildings, " + 
            " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on   " + 
            " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
            " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
            " where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id and grants.fy_code=fiscal_years.code " + 
            " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id  " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
            " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1   " + 
            " and grants.id in (select gra_id from grant_submissions where version='Initial')  " + 
            " order by system_name, popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setFiscalYear(rs.getInt("description"));//fiscal year      
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has")); 
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalApproved(rs.getLong("totappr"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setBuildingName(rs.getString("building_name"));
                
                if(gb.getTotalRequest()>0){                    
                    double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                     //need to format percent to 2 decimal places
                    gb.setRecommendPercentStr(df.format(percentaward));
                }
                
                if(gb.getTotalApproved()>0){
                    double percentaward = ((double) gb.getTotalApproved()/gb.getTotalRequest());
                    gb.setApprovedPercentStr(df.format(percentaward));
                }
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getLDAwardPercentForFy() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    public ArrayList getAppsSubmittedToDasny(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        
        try {    
            conn = initializeConn();
            ps = conn.prepareStatement("select  grants.id, name, proj_seq_num,  " + 
            " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name,  " + 
            " initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id,  " + 
            " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, ldgrants.BUDGETTOTALSVIEW.totappr,   " + 
            " rating_complete, co_institutions.inst_id, inst_id_has, sga.recommend_amt, " + 
            " case  (select version from grant_submissions where gra_id=grants.id and version='DASNY') " + 
            " when 'DASNY' then 1 else 0 end dasnysubmit,  " + 
            " case  (select version from approvals where gra_id=grants.id and version='DASNY') " + 
            " when 'DASNY' then 1 else 0 end dasnyapprove,  " + 
            " case  (select version from approvals where gra_id=grants.id and version='BondCouncil') " + 
            " when 'BondCouncil' then 1 else 0 end bondapprove  " + 
            " from grants, ldgrants.BUDGETTOTALSVIEW, fiscal_years, system_grant_assigns sga,  " + 
            " grant_buildings, sed_buildings,  " + 
            " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on   " + 
            " lsm.inst_id_has=sysinst.inst_id, co_institutions   " + 
            " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
            " where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id and grants.fy_code=fiscal_years.code  " + 
            " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id   " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
            " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1   " + 
            " and grants.id in (select gra_id from grant_submissions where version='Initial')  " + 
            " order by system_name, popular_name");
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setTitle(rs.getString("name"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setSystemName(rs.getString("system_name"));
                gb.setFiscalYear(rs.getInt("description"));//fiscal year      
                gb.setInstID(rs.getLong("inst_id"));
                gb.setSystemInstId(rs.getLong("inst_id_has"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalApproved(rs.getLong("totappr"));
                gb.setDasnySubmit(rs.getBoolean("dasnysubmit"));
                gb.setDasnyApprove(rs.getBoolean("dasnyapprove"));
                gb.setBondCouncilApprove(rs.getBoolean("bondapprove"));
                gb.setBuildingName(rs.getString("building_name"));
                allrecs.add(gb);
            }
            
        } catch (Exception ex){
           System.err.println("error getAppsSubmittedToDasny() " + ex.toString());
        }
        finally{
           Close(conn);
           Close(rs);
           Close(ps);
        }   
        return allrecs;
    }
    
    
/**
 * 4/27/12 for a given FY and PLS, get all reduced match
 * justifications. also, amt request, amt recommend, proj description, 
 * senate/assembly districts.
 * @param fycode
 * @param systemInstId
 * @return
 */
  public ArrayList getReducedMatchReportForSystem(int fycode, long systemInstId)
  {
    ArrayList allrecs=new ArrayList();    
    DecimalFormat df = new DecimalFormat("#.##%");

     try {    
        conn = initializeConn();
   
        ps = conn.prepareStatement("select  grants.id, name, proj_seq_num, " + 
        " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
        " initcap(sysinst.popular_name) as system_name, lsm_id as mapping_id, " + 
        " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, recommend_amt, rating_complete, " + 
        "  co_institutions.inst_id, inst_id_has, reduce_match, match_justification, "+
        " narrative_descr  " + 
        "  from grants, project_narratives, ldgrants.BUDGETTOTALSVIEW, system_grant_assigns sga,  " + 
        " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on " + 
        " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
        " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
        " where grants.id=project_narratives.gra_id and grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id  " + 
        " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id  " + 
        " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and nat_id=95 " + 
        " and lsm.inst_id_has =?  and reduce_match=1 " + 
        " order by system_name, popular_name");   
        ps.setInt(1, fycode);
        ps.setLong(2, systemInstId);
        rs = ps.executeQuery();
        
        while(rs.next()){
            GrantBean gb = new GrantBean();
            gb.setGrantid( rs.getLong("ID"));      
            gb.setTitle(rs.getString("name"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setInstName(rs.getString("popular_name"));     
            gb.setSystemName(rs.getString("system_name"));
            gb.setInstID(rs.getLong("inst_id"));
            gb.setSystemInstId(rs.getLong("inst_id_has"));
            gb.setTotalRequest(rs.getLong("totreq"));
            gb.setTotalRecommend(rs.getLong("recommend_amt"));
            gb.setSummaryDescr(rs.getString("narrative_descr"));
            gb.setReducedMatch(rs.getBoolean("reduce_match"));
            gb.setMatchJustification(rs.getString("match_justification"));
            
            if(gb.getTotalRequest()>0){                    
                double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                //need to format percent to 2 decimal places
                gb.setRecommendPercentStr(df.format(percentaward));
            }
                        
            allrecs.add(gb);
        }                        
                                              
     } catch (Exception ex){
        System.err.println("error getReducedMatchReportForSystem() " + ex.toString());
     }
     finally{
        Close(conn);
        Close(rs);
        Close(ps);
     }   
     return allrecs;
  }    
  
  
    public ArrayList getAllReducedMatchReportsForFy(int fycode)
    {
      ArrayList allrecs=new ArrayList();    
      DecimalFormat df = new DecimalFormat("#.##%");

       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " initcap(sysinst.popular_name) as system_name, lsm_id as mapping_id, " + 
          " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, recommend_amt, rating_complete, " + 
          "  co_institutions.inst_id, inst_id_has, reduce_match, match_justification, "+
          " narrative_descr  " + 
          "  from grants, project_narratives, ldgrants.BUDGETTOTALSVIEW, system_grant_assigns sga,  " + 
          " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on " + 
          " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
          " where grants.id=project_narratives.gra_id and grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id  " + 
          " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id  " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and nat_id=95 " + 
          " and reduce_match=1 " + 
          " order by system_name, popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setTitle(rs.getString("name"));
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setSystemName(rs.getString("system_name"));
              gb.setInstID(rs.getLong("inst_id"));
              gb.setSystemInstId(rs.getLong("inst_id_has"));
              gb.setTotalRequest(rs.getLong("totreq"));
              gb.setTotalRecommend(rs.getLong("recommend_amt"));
              gb.setSummaryDescr(rs.getString("narrative_descr"));
              gb.setReducedMatch(rs.getBoolean("reduce_match"));
              gb.setMatchJustification(rs.getString("match_justification"));
              
              if(gb.getTotalRequest()>0){                    
                  double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                  //need to format percent to 2 decimal places
                  gb.setRecommendPercentStr(df.format(percentaward));
              }                          
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getAllReducedMatchReportsForFy() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
    }    
    
    
  /**
     *new 5/13/13 per MLT - report will get all reduced match grants based on calculation of
     * amtaward/costproj (previous rpt was based on reduced_match=t/f)
     * @param fycode
     * @return
     */
  public ArrayList getReducedMatchReportsFromCalc(int fycode)
  {
    ArrayList allrecs=new ArrayList();    
    DecimalFormat df = new DecimalFormat("#.##%");

     try {    
        conn = initializeConn();
   
        ps = conn.prepareStatement("select  grants.id, proj_seq_num, " + 
        " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
        " initcap(sysinst.popular_name) as system_name, initcap(a.CITY) as CITY, lsm_id as mapping_id, " + 
        " sga.id as assignid, ldgrants.BUDGETTOTALSVIEW.totreq, recommend_amt, rating_complete,   " + 
        "  co_institutions.inst_id, ROUND(recommend_amt/ldgrants.BUDGETTOTALSVIEW.totreq, 3) as percentFund, " + 
        "  inst_id_has, reduce_match, building_name, bldg_type_code, match_justification, narrative_descr  " + 
        "  from grants " + 
        "  left join grant_buildings gb on grants.id=gb.gra_id " + 
        " left join sed_buildings sb on gb.bldg_id=sb.bldg_id, " + 
        " project_narratives, ldgrants.BUDGETTOTALSVIEW, system_grant_assigns sga, ldstateaid.library_system_mappings lsm " + 
        " left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id, " + 
        " co_institutions " + 
        " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
        " left join sed_addresses a on sed_institutions.inst_id = a.inst_id " + 
        " where grants.id=project_narratives.gra_id and grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id " + 
        " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id    " + 
        " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and nat_id=95 " + 
        " and ROUND(recommend_amt/ldgrants.BUDGETTOTALSVIEW.totreq, 3)>0.500 " + 
        " and reduce_match=1 and a.ADDR_TYPE_CODE = 1 " + 
        " order by system_name, popular_name");   
        ps.setInt(1, fycode);
        rs = ps.executeQuery();
        
        while(rs.next()){
            GrantBean gb = new GrantBean();
            gb.setGrantid( rs.getLong("ID"));      
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));             
            gb.setSystemName(rs.getString("system_name"));
            gb.setInstID(rs.getLong("inst_id"));
            gb.setSystemInstId(rs.getLong("inst_id_has"));
            gb.setTotalRequest(rs.getLong("totreq"));
            gb.setTotalRecommend(rs.getLong("recommend_amt"));
            gb.setSummaryDescr(rs.getString("narrative_descr"));
            gb.setReducedMatch(rs.getBoolean("reduce_match"));
            gb.setMatchJustification(rs.getString("match_justification"));
            gb.setCity(rs.getString("CITY"));
            
            gb.setInstName(rs.getString("popular_name"));   
            gb.setBuildingName(rs.getString("building_name"));
            gb.setBuildingTypeCode(rs.getInt("bldg_type_code"));
            if(gb.getBuildingTypeCode()==3 || gb.getBuildingTypeCode()==4){
              gb.setInstName(gb.getInstName() + " ("+ gb.getBuildingName() + ")");
            }
            
            if(gb.getTotalRequest()>0){                    
              //  double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                //need to format percent to 2 decimal places
              //  gb.setRecommendPercentStr(df.format(percentaward));
                gb.setRecommendPercentStr(df.format(rs.getDouble("percentFund")));
            }                          
            allrecs.add(gb);
        }                        
                                              
     } catch (Exception ex){
        System.err.println("error getReducedMatchReportsFromCalc() " + ex.toString());
     }
     finally{
        Close(conn);
        Close(rs);
        Close(ps);
     }   
     return allrecs;
  }    
    
    public ArrayList getRecommendProjNumSort(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        DecimalFormat df = new DecimalFormat("#.##%");

         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select  grants.id,  proj_seq_num, " + 
            " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
            " ldgrants.BUDGETTOTALSVIEW.totreq, recommend_amt, rating_complete, co_institutions.inst_id " + 
            " from grants, ldgrants.BUDGETTOTALSVIEW, system_grant_assigns sga, " + 
            " grant_buildings, sed_buildings, co_institutions " + 
            " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id   " + 
            " where grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id " + 
            " and grants.id=co_institutions.gra_id and grants.id=sga.gra_id  " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
            " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1  " + 
            " and grants.id in (select gra_id from grant_submissions where version='Initial')   " + 
            " order by proj_seq_num");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setInstID(rs.getLong("inst_id"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setBuildingName(rs.getString("building_name"));
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getRecommendProjNumSort() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    /**
     * method will get all construction final exps submitted, with total amt recommended 
     * and total grant_fund expenses. 
     * @param fycode
     * @return
     */
    public ArrayList getFinalSubmitted(int fycode)
    {
        ArrayList allrecs=new ArrayList();    
        Format formatter = new SimpleDateFormat("MM/dd/yyyy");  
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select GRANTS.ID, FC_CODE, FY_CODE, " + 
            " PROJ_SEQ_NUM, CO_INSTITUTIONS.INST_ID, " + 
            " initcap(POPULAR_NAME) as popular_name, building_name, date_submitted, recommend_amt, " + 
            " sum(expenditure) as totgrantexp, ldgrants.F_IS_VERSION_APPROVED(grants.id, 'Final') as finalApprId from  " + 
            " GRANTS, system_grant_assigns sga, " + 
            " grant_submissions, grant_expenditures, grant_buildings, sed_buildings, CO_INSTITUTIONS " + 
            "        left join SED_INSTITUTIONS on  sed_institutions.inst_id=co_institutions.inst_id  " + 
            " where " + 
            " GRANTS.ID = CO_INSTITUTIONS.GRA_ID  and grants.id=sga.gra_id " + 
            " and grants.id=grant_submissions.gra_id and grants.id=grant_expenditures.gra_id  " + 
            " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
            " and grants.FC_CODE=86 and grants.FY_CODE=? and IS_LEAD='Y' and grant_fund=1     " + 
            "    and ( date_submitted = (select max(date_submitted) from grant_submissions where " + 
            "                         grants.id=grant_submissions.gra_id and version='Final') )   " + 
            " group by grants.id, fc_code, fy_code, proj_seq_num, CO_INSTITUTIONS.INST_ID,   " + 
            " popular_name, building_name, date_submitted, recommend_amt " + 
            " order by popular_name");   
            ps.setInt(1, fycode);
            rs = ps.executeQuery();
            
            while(rs.next()){
                GrantBean gb = new GrantBean();
                gb.setGrantid( rs.getLong("ID"));      
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));  
                gb.setBuildingName(rs.getString("building_name"));
                gb.setInstID(rs.getLong("inst_id"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setTotalRequest(rs.getLong("totgrantexp"));
                long apprId = rs.getLong("finalApprId");//if final approval exists; this is the id of approval record
                if(apprId >0){
                  gb.setFinalApprove(true);
                }
                else
                    gb.setFinalApprove(false);
                gb.setStartdate(rs.getDate("date_submitted"));
                
                String myDate="";
                if(gb.getStartdate()!=null){
                    myDate = formatter.format(gb.getStartdate());
                }
                gb.setDateSubmitStr(myDate);
                allrecs.add(gb);
            }                        
                                                  
         } catch (Exception ex){
            System.err.println("error getFinalSubmitted() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allrecs;
    }    
    
    
    
  /**
     * construction apps with initial submitted; but final not submitted
     * @param fycode
     * @return
     */
  public ArrayList getFinalNotSubmitted(int fycode)
  {
      ArrayList allrecs=new ArrayList();    
     
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select GRANTS.ID, FC_CODE, FY_CODE, " + 
          " PROJ_SEQ_NUM, CO_INSTITUTIONS.INST_ID, " + 
          " initcap(POPULAR_NAME) as popular_name, building_name, recommend_amt " + 
          "  from     " + 
          " GRANTS, system_grant_assigns sga, " + 
          " grant_buildings, sed_buildings, CO_INSTITUTIONS " + 
          "        left join SED_INSTITUTIONS on  sed_institutions.inst_id=co_institutions.inst_id  " + 
          " where " + 
          " GRANTS.ID = CO_INSTITUTIONS.GRA_ID  and grants.id=sga.gra_id " + 
          " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          " and grants.FC_CODE=86 and grants.FY_CODE=? and IS_LEAD='Y'     " + 
          " and grants.id in " + 
          "        (select gra_id from grant_submissions where version='Initial') " + 
          " and grants.id in    " + 
          "        (select gra_id from approvals where version='Initial')  "+
          " and grants.id not in " + 
          "        (select gra_id from grant_submissions where version='Final') " + 
          " order by popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));  
              gb.setBuildingName(rs.getString("building_name"));
              gb.setInstID(rs.getLong("inst_id"));
              gb.setTotalRecommend(rs.getLong("recommend_amt"));      
              
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getFinalNotSubmitted() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  /**
     *Get all cn apps for FY, submitted to LD, where cert_occupancy=true
     * @param fycode
     * @return
     */
  public ArrayList getCertOccupancyProjects(int fycode)
  {
      ArrayList allrecs=new ArrayList();        
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  grants.id,  proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          " recommend_amt, rating_complete, co_institutions.inst_id, cert_occupancy, initcap(sysinst.popular_name) as system_name " + 
          " from grants, system_grant_assigns sga,  " + 
          " grant_buildings, sed_buildings, co_institutions " + 
          "    left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id, " + 
          " ldstateaid.library_system_mappings lsm " + 
          "    left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          " where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id  " + 
          " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          " and sga.lsm_id=lsm.id  " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          " and cert_occupancy=1  and rating_complete=1  " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial')   " + 
          " order by system_name, popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setInstID(rs.getLong("inst_id"));
              gb.setTotalRecommend(rs.getLong("recommend_amt"));
              gb.setBuildingName(rs.getString("building_name"));
              gb.setCooperative(rs.getBoolean("cert_occupancy"));
              gb.setSystemName(rs.getString("system_name"));
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getCertOccupancyProjects() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  public ArrayList getBondedProjects(int fycode)
  {
      ArrayList allrecs=new ArrayList();        
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  grants.id,  proj_seq_num,  " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          " recommend_amt, rating_complete, co_institutions.inst_id, initcap(sysinst.popular_name) as system_name, bonded  " + 
          " from grants, system_grant_assigns sga,  " + 
          " grant_buildings, sed_buildings, co_institutions " + 
          "    left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id,  " + 
          " ldstateaid.library_system_mappings lsm " + 
          "    left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          " where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id  " + 
          " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          " and sga.lsm_id=lsm.id  " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          " and rating_complete=1 and bonded=1  " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial')  " + 
          " order by system_name, popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setInstID(rs.getLong("inst_id"));
              gb.setTotalRecommend(rs.getLong("recommend_amt"));
              gb.setBuildingName(rs.getString("building_name"));
              gb.setCooperative(rs.getBoolean("bonded"));
              gb.setSystemName(rs.getString("system_name"));
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getBondedProjects() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  
  public ArrayList getProjectCostApplications(int fycode)
  {
      ArrayList allrecs=new ArrayList();        
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  grants.id,  proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          " recommend_amt, rating_complete, co_institutions.inst_id, initcap(sysinst.popular_name) as system_name, " + 
          " amount_received  " + 
          " from grants, system_grant_assigns sga,   " + 
          " grant_buildings, building_funds, sed_buildings, co_institutions " + 
          "    left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id, " + 
          " ldstateaid.library_system_mappings lsm " + 
          "    left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          " where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id  " + 
          " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          " and sga.lsm_id=lsm.id  and grant_buildings.id=building_funds.gb_id " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          " and rating_complete=1  and fs_id=1  " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial')   " + 
          " order by system_name, popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setInstID(rs.getLong("inst_id"));
              gb.setTotalRecommend(rs.getLong("recommend_amt"));
              gb.setBuildingName(rs.getString("building_name"));
              gb.setSystemName(rs.getString("system_name"));
              gb.setTotalRequest(rs.getLong("amount_received"));
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getProjectCostApplications() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  
  
  public ArrayList getProjectCategoryForApps(int fycode)
  {
      ArrayList allrecs=new ArrayList();        
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  grants.id,  proj_seq_num, " + 
          "   grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          "   recommend_amt, rating_complete, co_institutions.inst_id, initcap(sysinst.popular_name) as system_name,  " + 
          "   pt_id as projectType, description as projectDescr  " + 
          "   from grants, system_grant_assigns sga,   " + 
          "   grant_buildings, building_projects " + 
          "       left join project_types on building_projects.pt_id=project_types.id, sed_buildings, co_institutions " + 
          "      left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id, " + 
          "   ldstateaid.library_system_mappings lsm " + 
          "      left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          "   where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id  " + 
          "   and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          "   and sga.lsm_id=lsm.id   and grant_buildings.id = building_projects.gb_id " + 
          "   and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          "   and rating_complete=1  " + 
          "   and grants.id in (select gra_id from grant_submissions where version='Initial')    " + 
          "   order by grants.id ");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          long currGrantid =0;
          GrantBean gb = new GrantBean();
          while(rs.next()){
              
              long queryGrantid = rs.getLong("id");
              
              //if first record from query results
              if(rs.isFirst()){             
                gb = new GrantBean();
                gb.setGrantid(queryGrantid);
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setInstID(rs.getLong("inst_id"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setBuildingName(rs.getString("building_name"));
                gb.setSystemName(rs.getString("system_name"));
                                 
                currGrantid = queryGrantid;
              }
              else if(queryGrantid != currGrantid){//new grant_id record
                  //add previous bean record to list
                  allrecs.add(gb);
                                
                  gb = new GrantBean();
                  gb.setGrantid(queryGrantid);
                  gb.setProjseqnum(rs.getLong("proj_seq_num"));
                  gb.setFycode(rs.getInt("fy_code"));
                  gb.setFccode(rs.getInt("fc_code"));
                  gb.setInstName(rs.getString("popular_name"));     
                  gb.setInstID(rs.getLong("inst_id"));
                  gb.setTotalRecommend(rs.getLong("recommend_amt"));
                  gb.setBuildingName(rs.getString("building_name"));
                  gb.setSystemName(rs.getString("system_name"));  
                  
                  currGrantid = queryGrantid;   
              }
              else{
                  //only other option is record has same grant_id; just new project category
                  currGrantid = queryGrantid;   
              }
              
              //set project category (or multiple categories)
              int projcatId = rs.getInt("projectType");
              switch(projcatId){
              case 1:
                  gb.setNewconstruct(1);
                  break;
              case 2:
                  gb.setBuildingexpand(1);
                  break;
              case 3:
                  gb.setAcquisition(1);
                  break;
              case 4:
                  gb.setRenovation(1);
                  break;
              case 5:
                  gb.setEnergyconserve(1);
                  break;
              case 6:
                  gb.setAccessibility(1);
                  break;
              case 7:
                  gb.setSafety(1);
                  break;                
              }
             
          }
          allrecs.add(gb);
          //System.out.println("size projects "+allrecs.size());
          Collections.sort(allrecs, GrantBean.SystemNameComparator);
                                                
       } catch (Exception ex){
          System.err.println("error getProjectCategoryForApps() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  /**
     *get all cn apps submitted to LD, with all reduce match criteria records. 
     * @param fycode
     * @return
     */
  public ArrayList getReduceMatchCriteriaRpt(int fycode, boolean filterForMatch)
  {
      ArrayList allrecs=new ArrayList();     
      DecimalFormat df = new DecimalFormat("#.##%");
       try {    
          conn = initializeConn();
     
          String sql = "select  grants.id,  proj_seq_num,  " + 
          "     grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          "     recommend_amt, rating_complete, co_institutions.inst_id, initcap(sysinst.popular_name) as system_name, " + 
          "     ldgrants.BUDGETTOTALSVIEW.totreq, initcap(sed_counties.description) as countyname, rmt_id  " + 
          "     from grants, system_grant_assigns sga,   grant_reduce_matches,   " + 
          "     grant_buildings, sed_buildings, ldgrants.BUDGETTOTALSVIEW, co_institutions " + 
          "        left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          "        left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code,  " + 
          "     ldstateaid.library_system_mappings lsm  " + 
          "        left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          "     where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id   " + 
          "     and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          "     and sga.lsm_id=lsm.id      and sga.id = grant_reduce_matches.sga_id  " + 
          "     and grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id  and sga.rating_complete=1 " + 
          "     and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          "     order by grants.id, system_name";
           
           
          String filteredSql = "select  grants.id,  proj_seq_num,  " + 
          "     grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          "     recommend_amt, rating_complete, co_institutions.inst_id, initcap(sysinst.popular_name) as system_name, " + 
          "     ldgrants.BUDGETTOTALSVIEW.totreq, initcap(sed_counties.description) as countyname, rmt_id  " + 
          "     from grants, system_grant_assigns sga,   grant_reduce_matches,   " + 
          "     grant_buildings, sed_buildings, ldgrants.BUDGETTOTALSVIEW, co_institutions " + 
          "        left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          "        left join sed_counties on sed_institutions.COUNTY_CODE= sed_counties.county_code,  " + 
          "     ldstateaid.library_system_mappings lsm  " + 
          "        left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id " + 
          "     where  grants.id=co_institutions.gra_id and grants.id=sga.gra_id   " + 
          "     and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
          "     and sga.lsm_id=lsm.id      and sga.id = grant_reduce_matches.sga_id  " + 
          "     and grants.id=ldgrants.BUDGETTOTALSVIEW.gra_id   and sga.rating_complete=1 " + 
          "     and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          "     and ROUND(recommend_amt/ldgrants.BUDGETTOTALSVIEW.totreq, 3)>0.500 "+
          "     order by grants.id, system_name";
           
          if(filterForMatch)
            ps = conn.prepareStatement(filteredSql);
          else
            ps = conn.prepareStatement(sql);
          
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          long currGrantid =0;
          GrantBean gb = new GrantBean();
          while(rs.next()){
              
              long queryGrantid = rs.getLong("id");
              
              //if first record from query results
              if(rs.isFirst()){     
                 
                gb = new GrantBean();
                gb.setGrantid(queryGrantid);
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setInstName(rs.getString("popular_name"));     
                gb.setInstID(rs.getLong("inst_id"));
                gb.setTotalRecommend(rs.getLong("recommend_amt"));
                gb.setTotalRequest(rs.getLong("totreq"));
                gb.setBuildingName(rs.getString("building_name"));
                gb.setSystemName(rs.getString("system_name"));
                gb.setCounty(rs.getString("countyname"));
                if(gb.getTotalRequest()>0){                    
                    double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                   // System.out.println(percentaward);
                    gb.setRecommendPercent(percentaward);
                    //need to format percent to 2 decimal places
                    gb.setRecommendPercentStr(df.format(percentaward));
                }
                                 
                currGrantid = queryGrantid;
              }
              else if(queryGrantid != currGrantid){//new grant_id record
                  //add previous bean record to list
                  allrecs.add(gb);
                                
                  gb = new GrantBean();
                  gb.setGrantid(queryGrantid);
                  gb.setProjseqnum(rs.getLong("proj_seq_num"));
                  gb.setFycode(rs.getInt("fy_code"));
                  gb.setFccode(rs.getInt("fc_code"));
                  gb.setInstName(rs.getString("popular_name"));     
                  gb.setInstID(rs.getLong("inst_id"));
                  gb.setTotalRecommend(rs.getLong("recommend_amt"));
                  gb.setTotalRequest(rs.getLong("totreq"));
                  gb.setBuildingName(rs.getString("building_name"));
                  gb.setSystemName(rs.getString("system_name"));  
                  gb.setCounty(rs.getString("countyname"));
                  if(gb.getTotalRequest()>0){                    
                      double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
                     // System.out.println(percentaward);
                      gb.setRecommendPercent(percentaward);
                      //need to format percent to 2 decimal places
                      gb.setRecommendPercentStr(df.format(percentaward));
                  }
                  
                  currGrantid = queryGrantid;   
              }
              else{
                  //only other option is record has same grant_id; just new reduce match
                  currGrantid = queryGrantid;   
              }
              
              //set reduce match criteria (or multiple criteria)
              int rmtId = rs.getInt("rmt_id");
              switch(rmtId){
              case 1:
                  gb.setLuncheligible(1);
                  break;
              case 2:
                  gb.setPovertyrate(1);
                  break;
              case 3:
                  gb.setUnemployment(1);
                  break;
              case 4:
                  gb.setEducation(1);
                  break;
              case 5:
                  gb.setEnglishlang(1);
                  break;
              case 6:
                  gb.setHousing(1);
                  break;
              case 7:
                  gb.setOther(1);
                  break;                
              }             
          }
          allrecs.add(gb);
          //System.out.println("size projects "+allrecs.size());
           
         Collections.sort(allrecs, GrantBean.SystemNameComparator);
                                                
       } catch (Exception ex){
          System.err.println("error getReduceMatchCriteriaRpt() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
  
  
  /**
     *Query all apps submitted to LD where any of the 4 SHPO questions from Coversheet are true. Requested by Lynne Webb
     * on 2/25/16.  Tables containing the requested reporting fields:
     * SED_BUILDINGS - historic district; historic landmark
     * GRANT_BUILDINGS - over 50 years old
     * GRANTS.PAID_IN_FULL_YN - ground distrubance
     * @param fycode
     * @return
     */
  public ArrayList getLibrariesNeedingShpo(int fycode)
  {
      ArrayList allrecs=new ArrayList();    
      
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("select  g.id, name, proj_seq_num, " + 
          "             g.fy_code, g.fc_code, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
          "             initcap(sysinst.popular_name) as system_name, fy.description, lsm_id as mapping_id, " + 
          "             sga.id as assignid,  rating_complete, co_institutions.inst_id, inst_id_has,  " + 
          "             paid_in_full_yn as ground_disturb,   " + 
          "             gb.over50,  b.historic_district,  b.historic_landmark       " + 
          "             from grants g, fiscal_years fy, system_grant_assigns sga,   " + 
          "             grant_buildings gb, sed_buildings b,   " + 
          "             ldstateaid.library_system_mappings lsm   " + 
          "             left join sed_institutions sysinst on lsm.inst_id_has=sysinst.inst_id, " + 
          "             co_institutions   " + 
          "             left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
          "             where g.fy_code=fy.code  " + 
          "             and g.id=co_institutions.gra_id and g.id=sga.gra_id  and sga.lsm_id=lsm.id   " + 
          "             and g.id=gb.gra_id and gb.bldg_id=b.bldg_id  " + 
          "             and g.fy_code=? and g.fc_code=86 and is_lead='Y' and rating_complete=1  " + 
          "             and g.id in (select gra_id from grant_submissions where version='Initial')   " + 
          "             and (paid_in_full_yn=1 or gb.over50=1 or b.historic_district=1 or b.historic_landmark=1) " + 
          "             order by system_name, popular_name");   
          ps.setInt(1, fycode);
          rs = ps.executeQuery();
          
          while(rs.next()){
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setTitle(rs.getString("name"));
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setSystemName(rs.getString("system_name"));
              gb.setFiscalYear(rs.getInt("description"));//fiscal year      
              gb.setInstID(rs.getLong("inst_id"));
              gb.setSystemInstId(rs.getLong("inst_id_has"));
              gb.setBuildingName(rs.getString("building_name"));
              //shpo fields
              gb.setOver50(rs.getInt("over50"));
              gb.setHistoricDistrict(rs.getInt("historic_district"));
              gb.setHistoricLandmark(rs.getInt("historic_landmark"));
              gb.setGroundDisturb(rs.getInt("ground_disturb"));
              
              allrecs.add(gb);
          }                        
                                                
       } catch (Exception ex){
          System.err.println("error getLibrariesNeedingShpo() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return allrecs;
  }    
  
}
