 /******************************************************************************
  * @author  : Stefanie Husak
  * @Version : 2.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  ConstructionDBbean.java
  * Creation/Modification History  :
  * DC  10/2010      Created 
  * SH Modified
  *
  * This class has most of the select/insert/update/delete queries for construction-
  * specific tables (ie building, system_grant_assigns, etc)
  *****************************************************************************/
package construction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import java.text.SimpleDateFormat;

import java.util.Vector;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;


import javax.sql.DataSource;

import mypackage.AppStatusBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.DropDownListBean;
import mypackage.GrantBean;
import mypackage.TotalsBean;
import mypackage.UserBean;

public class ConstructionDBbean {
    public ConstructionDBbean() {
    }
       
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    NumberFormat numF = new DecimalFormat("#,###,###.##");
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
     *SH- method will check the ldstateaid mapping table to determine if given institution id
     * is a library system. 
     * @param inst_id
     * @return
     */
    public boolean isLibrarySystem(long inst_id)
    {    
      boolean instIsSystem = false;
      try {    
        conn = initializeConn();
                  
        ps = conn.prepareStatement("select * from LDSTATEAID.LIBRARY_SYSTEM_MAPPINGS where INST_ID_HAS=?");
        ps.setLong(1, inst_id);
        rs = ps.executeQuery();
        
        while(rs.next()){
            instIsSystem = true;
        }
                              
      } catch (Exception ex){
          System.err.println("error isLibrarySystem()  " + ex.toString());
          return false;
      }
      finally{
        Close(conn);
        Close(rs);
        Close(ps);
      }    
     return instIsSystem;
    }
    
  /**
     * SH method will get all construction apps submitted by given PLS's member institutions
     * for the given FY. To display on the cn reviewer 'member apps list' page.
     * @param fy
     * @param systemID
     * @return
     */
  public Vector getSubmittedMemberApps(int fy, long systemID) 
  {        
    Vector results = new Vector();
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, "+
        " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name , " + 
        " fiscal_years.description, ldgrants.budgettotalsview.totreq " + 
        " from grants, fiscal_years, ldgrants.budgettotalsview, co_institutions " + 
        " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
        " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id and " + 
        " grants.id=ldgrants.budgettotalsview.gra_id  " + 
        " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
        " and grants.id in (select gra_id from grant_submissions where version='Initial') " + 
        " and sed_institutions.inst_id in (select inst_id from " + 
        " ldstateaid.library_system_mappings where inst_id_has=?) order by popular_name");           
          ps.setInt(1,fy);
          ps.setLong(2, systemID);
          rs = ps.executeQuery();      
          while(rs.next())
          {
            GrantBean gb = new GrantBean();
            gb.setGrantid( rs.getLong("ID"));      
            gb.setName(rs.getString("name"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFiscalYear(rs.getInt("description"));//fiscal year
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setInstName(rs.getString("popular_name"));     
            gb.setAmountReq(rs.getInt("totreq"));
            results.add(gb);
          }              
        }
        catch(Exception e){ System.out.println(e);
          System.err.println("error getSubmittedMemberApps "+ e.getMessage().toString());
        }
        finally{
          Close(rs);
          Close(ps);
          Close(conn);
        }          
        return results;
    }
    
   
   
   /**
     * SH-method will get all member apps assigned to this system for this Fy. used
     * to populate the table on the 'submit to LD' page.
     * method assumes that a system_grant_assign record exists
     * @param fycode
     * @param systemID
     * @return
     */
    public Vector getMembersForSubmitList(int fycode, long systemID) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " fiscal_years.description, ldgrants.budgettotalsview.totreq, " + 
          " sga.id as assignid, recommend_amt, rating_complete " + 
          " from grants, fiscal_years, system_grant_assigns sga, ldgrants.budgettotalsview, "+
          " co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id and " + 
          " grants.id=sga.gra_id and grants.id=ldgrants.budgettotalsview.gra_id " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial') " + 
          " and sed_institutions.inst_id in (select inst_id from " + 
          " ldstateaid.library_system_mappings where inst_id_has=?) order by popular_name");           
            ps.setInt(1,fycode);
            ps.setLong(2, systemID);
            rs = ps.executeQuery();      
            while(rs.next())
            {
              SystemAssignBean sb = new SystemAssignBean();
              sb.setGrantId( rs.getLong("ID"));      
              sb.setProjName(rs.getString("name"));
              sb.setProjectSeqNum(rs.getLong("proj_seq_num"));
              sb.setFycode(rs.getInt("fy_code"));
              sb.setFccode(rs.getInt("fc_code"));
              sb.setInstName(rs.getString("popular_name"));     
              sb.setFiscalYear(rs.getInt("description"));//fiscal year
              sb.setAmtrequested(rs.getLong("totreq"));//total from budget tabs                             
              sb.setAssignmentId(rs.getLong("assignid"));
              sb.setRatingComplete(rs.getBoolean("rating_complete"));
              sb.setRecommendAmt(rs.getLong("recommend_amt"));              
              sb.setRecommendAmtStr(rs.getString("recommend_amt"));
              if(sb.getRecommendAmtStr()!=null && !sb.getRecommendAmtStr().equals("")){
                  long amtrec = Long.parseLong(sb.getRecommendAmtStr());
                  sb.setRecommendAmtStr(numF.format(amtrec));
              }
              
              if(sb.getFycode()<13){
                   //calc max 50% that can be awarded based on amt_requested
                   if(sb.getAmtrequested()>0){
                      double maxamt = sb.getAmtrequested()/2;
                      int maxamtint = (int)Math.round(maxamt);
                      sb.setMaxRequestCost(maxamtint);
                   }                  
              }
              else{
                  //calc max 75% starting in fy 2012-13
                   if(sb.getAmtrequested()>0){
                       double maxamt = sb.getAmtrequested()* 0.75;
                       int maxamtint = (int)Math.round(maxamt);
                       sb.setMaxRequestCost(maxamtint);
                   }
              }                              
              results.add(sb);
            }              
          }
          catch(Exception e){
            System.out.println("error getMembersForSubmitList "+ e.getMessage().toString());
          }
          finally{
            Close(rs);
            Close(ps);
            Close(conn);
          }          
          return results;
      }
   
   
   
    public Vector getAppsSubmittedToLdForFy(int fycode) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id, " + 
          " sga.id as assignid, recommend_amt, rating_complete, co_institutions.inst_id, "+
          " inst_id_has, case (select version from approvals where gra_id=grants.id and version='DASNY') " + 
          " when 'DASNY' then 1 else 0 end dasnyapprove   from grants, fiscal_years, system_grant_assigns sga, " + 
          " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on "+
          " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id and " + 
          " grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1 " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial') " + 
          " order by system_name, popular_name");           
          ps.setInt(1,fycode);
          rs = ps.executeQuery();   
            
            while(rs.next())
            {
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
              gb.setDasnyApprove(rs.getBoolean("dasnyapprove"));
              results.add(gb);
            }              
      }
      catch(Exception e){
        System.out.println("error getAppsSubmittedToLdForFy "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }          
      return results;
    }
      
      
      
    public Vector getAppsSubmittedToDasnyFy(int fycode) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " initcap(sysinst.popular_name) as system_name, fiscal_years.description, lsm_id as mapping_id, " + 
          " sga.id as assignid, recommend_amt, rating_complete, co_institutions.inst_id, "+
          " inst_id_has from grants, fiscal_years, system_grant_assigns sga, " + 
          " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on "+
          " lsm.inst_id_has=sysinst.inst_id, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id and " + 
          " grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
          " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and rating_complete=1 " + 
          " and grants.id in (select gra_id from grant_submissions where version='Initial') " + 
          " and grants.id in (select gra_id from grant_submissions where version='DASNY') " +
          " order by system_name, popular_name");           
          ps.setInt(1,fycode);
          rs = ps.executeQuery();   
            
            while(rs.next())
            {
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
              results.add(gb);
            }              
      }
      catch(Exception e){
        System.out.println("error getAppsSubmittedToDasnyFy "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }          
      return results;
    }
      
     
    public Vector getMemberAppsForFy(int fycode, long systemID) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " fiscal_years.description from grants, fiscal_years, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id and " + 
          " grants.fy_code=? and grants.fc_code=86 and is_lead='Y' " + 
          " and sed_institutions.inst_id in (select inst_id from " + 
          " ldstateaid.library_system_mappings where inst_id_has=?) order by popular_name");           
            ps.setInt(1,fycode);
            ps.setLong(2, systemID);
            rs = ps.executeQuery();   
            
            while(rs.next())
            {
              SystemAssignBean sb = new SystemAssignBean();
              sb.setGrantId( rs.getLong("ID"));      
              sb.setProjName(rs.getString("name"));
              sb.setProjectSeqNum(rs.getLong("proj_seq_num"));
              sb.setFycode(rs.getInt("fy_code"));
              sb.setFccode(rs.getInt("fc_code"));
              sb.setInstName(rs.getString("popular_name"));     
              sb.setFiscalYear(rs.getInt("description"));//fiscal year                               
              results.add(sb);
            }              
          }
          catch(Exception e){
            System.out.println("error getMemberAppsForFy "+ e.getMessage().toString());
          }
          finally{
            Close(rs);
            Close(ps);
            Close(conn);
          }          
          return results;
      }
      
      
           
    public Vector getMemberAppsForSystem(long systemID) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " fiscal_years.description from grants, fiscal_years, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id " + 
          " and grants.fc_code=86 and is_lead='Y' " + 
          " and sed_institutions.inst_id in (select inst_id from " + 
          " ldstateaid.library_system_mappings where inst_id_has=?) order by popular_name");           
            ps.setLong(1, systemID);
            rs = ps.executeQuery();   
            
            while(rs.next())
            {
              GrantBean gb = new GrantBean();
              gb.setGrantid( rs.getLong("ID"));      
              gb.setTitle(rs.getString("name"));
              gb.setProjseqnum(rs.getLong("proj_seq_num"));
              gb.setFycode(rs.getInt("fy_code"));
              gb.setFccode(rs.getInt("fc_code"));
              gb.setInstName(rs.getString("popular_name"));     
              gb.setFiscalyear(rs.getString("description"));//fiscal year                               
              results.add(gb);
            }              
          }
          catch(Exception e){
            System.out.println("error getMemberAppsForSystem "+ e.getMessage().toString());
          }
          finally{
            Close(rs);
            Close(ps);
            Close(conn);
          }          
          return results;
      }
    
      
    public Vector getAppsForMember(long memberInstId, long systemInstId) 
    {        
      Vector results = new Vector();
      try{
          conn = initializeConn();
          ps = conn.prepareStatement("select distinct grants.id, name, proj_seq_num, " + 
          " grants.fy_code, grants.fc_code, initcap(sed_institutions.popular_name) as popular_name, " + 
          " fiscal_years.description from grants, fiscal_years, co_institutions " + 
          " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id  " + 
          " where grants.fy_code=fiscal_years.code and grants.id=co_institutions.gra_id " + 
          " and grants.fc_code=86 and is_lead='Y' and co_institutions.inst_id=? " + 
          " and sed_institutions.inst_id in (select inst_id from " + 
          " ldstateaid.library_system_mappings where inst_id_has=?) order by fy_code desc");           
            ps.setLong(1, memberInstId);
            ps.setLong(2, systemInstId);
            rs = ps.executeQuery();   
            
            while(rs.next())
            {
              SystemAssignBean sb = new SystemAssignBean();
              sb.setGrantId( rs.getLong("ID"));      
              sb.setProjName(rs.getString("name"));
              sb.setProjectSeqNum(rs.getLong("proj_seq_num"));
              sb.setFycode(rs.getInt("fy_code"));
              sb.setFccode(rs.getInt("fc_code"));
              sb.setInstName(rs.getString("popular_name"));     
              sb.setFiscalYear(rs.getInt("description"));//fiscal year                               
              results.add(sb);
            }              
          }
          catch(Exception e){
            System.out.println("error getAppsForMember "+ e.getMessage().toString());
          }
          finally{
            Close(rs);
            Close(ps);
            Close(conn);
          }          
          return results;
      }
      
   
   /**
     * SH method will check to see if there is a system_grant_assign record for given grant
     * @param grantid
     * @return
     */
   public boolean doesSystemAssignExist(long grantid)
   {
      boolean recordExists = false;
       try {    
         conn = initializeConn();                   
         ps = conn.prepareStatement("select ID from SYSTEM_GRANT_ASSIGNS where GRA_ID=?");
         ps.setLong(1, grantid);
         rs = ps.executeQuery();
         
         while(rs.next()){
             recordExists = true;
         }
                               
      } catch (Exception ex){
           System.err.println("error doesSystemAssignExist()  " + ex.toString());
           return false;
      }
      finally{
         Close(conn);
         Close(rs);
         Close(ps);
      }   
      return recordExists;
   }
   
   
    public long doesGrantBuildingExist(long grantid)
    {
        long gbId=0;
        try {    
          conn = initializeConn();                   
          ps = conn.prepareStatement("select ID from GRANT_BUILDINGS where GRA_ID=?");
          ps.setLong(1, grantid);
          rs = ps.executeQuery();
          
          while(rs.next()){
              gbId = rs.getLong("id");
          }
                                
       } catch (Exception ex){
            System.err.println("error doesGrantBuildingExist()  " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return gbId;
    }
   
   
   /**
     * SH- method will get all system_grant_assign info for this grantid
     * @param grantid
     * @return
     */
    public SystemAssignBean getSystemAssignRecord(long grantid)
    {
       SystemAssignBean sab = new SystemAssignBean();
       
        try {    
          conn = initializeConn();                   
          ps = conn.prepareStatement("select sga.id as assignment_id, sga.rating_complete, sga.director_auth, "+
          " sga.recommend_amt, sga.recommendation, sga.gra_id, lsm_id, inst_id, inst_id_has, " + 
          " sga.reduce_match, sga.match_justification "+
          " from system_grant_assigns sga, ldstateaid.library_system_mappings " + 
          " where sga.gra_id=? and sga.lsm_id = library_system_mappings.id");
          ps.setLong(1, grantid);
          rs = ps.executeQuery();
          
          while(rs.next()){
              sab.setAssignmentExists(true);
              sab.setAssignmentId(rs.getLong("assignment_id"));
              sab.setRatingComplete(rs.getBoolean("rating_complete"));
              sab.setSystemLockOut(rs.getBoolean("director_auth"));
              sab.setRecommendation(rs.getBoolean("recommendation"));
              sab.setRecommendAmt(rs.getLong("recommend_amt"));      
              sab.setRecommendAmtStr(rs.getString("recommend_amt"));
              if(sab.getRecommendAmtStr()!=null && !sab.getRecommendAmtStr().equals("")){
                long amtrec = Long.parseLong(sab.getRecommendAmtStr());
                sab.setRecommendAmtStr(numF.format(amtrec));
              }
              
              sab.setGrantId(rs.getLong("gra_id"));
              sab.setLibSysMappingId(rs.getLong("lsm_id"));
              sab.setInstId(rs.getLong("inst_id"));
              sab.setSystemInstId(rs.getLong("inst_id_has"));
              sab.setReduceMatchExists(rs.getBoolean("reduce_match"));
              sab.setMatchJustification(rs.getString("match_justification"));
          }
                                
       } catch (Exception ex){
            System.err.println("error getSystemAssignRecord()  " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return sab;
    }
   
   
  public SystemAssignBean getReduceMatchTypes(SystemAssignBean ab)
  {    
     try {    
       conn = initializeConn();                  
       ps = conn.prepareStatement("select * from grant_reduce_matches where sga_id=?");
       ps.setLong(1, ab.getAssignmentId()); 
       rs = ps.executeQuery();
       
       while(rs.next()){
           int ptype = rs.getInt("rmt_id");
           switch(ptype){
               case 1:
                  ab.setLunchEligible(true);
                  break;
               case 2:
                  ab.setPovertyRate(true);
                  break;
               case 3:
                  ab.setUnemployment(true);
                  break;
               case 4:
                  ab.setEducation(true);
                  break;
               case 5:
                  ab.setEnglishLang(true);
                  break;
               case 6:
                  ab.setHousing(true);
                  break;
               case 7:
                  ab.setOtherRate(true);
                  ab.setOtherDescr(rs.getString("Criteria_descr"));
                  break;
           }             
       }
                                   
     } catch (Exception ex){
         System.out.println("error getReduceMatchTypes() " + ex.toString());
     }
     finally{
       Close(conn);
       Close(rs);
       Close(ps);
     }    
    return ab;
  }
  
  
    
    /**
     * SH - get the recommend_amt - used for variable substitution in construct emails
     * and fs10f forms.
     * @param grantid
     * @return
     */
    public long getAmtRecommendedForGrant(long grantid)
    {
       long amtrec=0;       
        try {    
          conn = initializeConn();                   
          ps = conn.prepareStatement("select sga.id as assignment_id, sga.rating_complete, " + 
          " sga.recommend_amt,  sga.gra_id from system_grant_assigns sga " + 
          " where sga.gra_id=?");
          ps.setLong(1, grantid);
          rs = ps.executeQuery();
          
          while(rs.next()){             
               amtrec = rs.getLong("recommend_amt");      
          }
                                
       } catch (Exception ex){
            System.err.println("error getAmtRecommendedForGrant()  " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return amtrec;
    }
    
    
    public GrantBean getAmtRecommendCostForGrant(GrantBean gb, long grantid)
    {
        DecimalFormat df = new DecimalFormat("#.##%");

        try {    
          conn = initializeConn();                   
          ps = conn.prepareStatement("select sga.id as assignment_id, sga.rating_complete,  " + 
          " sga.recommend_amt,  sga.gra_id, ldgrants.BUDGETTOTALSVIEW.totreq " + 
          " from system_grant_assigns sga, ldgrants.BUDGETTOTALSVIEW " + 
          " where sga.gra_id=ldgrants.BUDGETTOTALSVIEW.gra_id and sga.gra_id=?");
          ps.setLong(1, grantid);
          rs = ps.executeQuery();          
          while(rs.next()){             
               gb.setTotalRecommend(rs.getLong("recommend_amt"));     
               gb.setTotalRequest(rs.getLong("totreq"));
          }
          
           if(gb.getTotalRequest()>0){                    
               double percentaward=((double) gb.getTotalRecommend()/gb.getTotalRequest());
               gb.setRecommendPercent(percentaward);
               //need to format percent to 2 decimal places
               gb.setRecommendPercentStr(df.format(percentaward));
           }
                                
       } catch (Exception ex){
            System.err.println("error getAmtRecommendCostForGrant()  " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return gb;
    }
    
    
   /**
     * SH- method determines the library/system mapping ID from stateaid table
     * based on the member's sedref inst_id
     * @param instid
     * @return
     */
   public long getLibrarySystemMapId(long instid)
   {
        long id =0;
        try {    
          conn = initializeConn();                    
          ps = conn.prepareStatement("select ID, INST_ID_HAS from LDSTATEAID.LIBRARY_SYSTEM_MAPPINGS "+
          " where INST_ID=? and END_DATE is null");      
          ps.setLong(1, instid);
          rs = ps.executeQuery();          
          while(rs.next()){
              id = rs.getLong("id");
          }
                                
        } catch (Exception ex){
            System.err.println("error getLibrarySystemMapId() " + ex.toString());
        }
        finally{
          Close(conn);
          Close(rs);
          Close(ps);
        }   
        return id;
   }
      
 
   /**
     * Sh-method will insert new system_grant_assign record, tying the grant app
     * with the libSysMapping that is supposed to review the app
     * @param ub
     * @param grantid
     * @param libmap
     * @return
     */
    public int insertAssignRecord(UserBean ub, long grantid, long libmap)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("INSERT INTO SYSTEM_GRANT_ASSIGNS( " +
            " ID, date_created, created_by, gra_id, lsm_id) " +
            " values(SGA_SEQ.nextval, sysdate, ?, ?, ?)");        
            ps.setString(1, ub.getUserid());
            ps.setLong(2, grantid);
            ps.setLong(3, libmap);            
            outcome = ps.executeUpdate(); 
                                  
        } catch (Exception ex){
              System.err.println("error insertAssignRecord()  " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
  
    /**
     * SH-method will get all reviewer comments for this cnReviewer assignment,
     * and set to bean for display/update on cnRev eval form.
     * @param systemAssignId
     * @return
     */
  public CnRatingFormBean getCnRatingComments(long systemAssignId)
  {
     CnRatingFormBean fb = new CnRatingFormBean();
     try {    
        conn = initializeConn();
        ps = conn.prepareStatement("select * from REVIEWER_COMMENTS where SGA_ID=?");
        ps.setLong(1,systemAssignId);
        rs = ps.executeQuery();
        
        while(rs.next()){
          int commid = rs.getInt("CT_ID");
          switch(commid){
                case 6:
                    fb.setCommentone(rs.getString("REVC_COMMENT"));
                    break;
                case 7:
                    fb.setCommenttwo(rs.getString("REVC_COMMENT"));
                    break;
                case 8:
                    fb.setCommentthree(rs.getString("REVC_COMMENT"));
                    break;
                case 9:
                    fb.setCommentfour(rs.getString("REVC_COMMENT"));
                    break;
                case 10:
                    fb.setCommentfive(rs.getString("REVC_COMMENT"));
                    break;
                case 11:
                    fb.setCommentsix(rs.getString("REVC_COMMENT"));
                    break;
                case 12:
                    fb.setCommentseven(rs.getString("REVC_COMMENT"));
                    break;
                case 13:
                    fb.setCommenteight(rs.getString("REVC_COMMENT"));
                    break;
                case 14:
                    fb.setCommentnine(rs.getString("REVC_COMMENT"));
                    break;
                case 15:
                    fb.setCommentten(rs.getString("REVC_COMMENT"));
                    break;
                case 16:
                    fb.setCommenteleven(rs.getString("REVC_COMMENT"));
                    break;
                case 18:
                    fb.setCommenttwelve(rs.getString("REVC_COMMENT"));
                    break;
          }
        }              
                           
     } catch (Exception ex){
       System.err.println("error getCnRatingComments()  " + ex.toString());
     }
     finally{
        Close(conn);
        Close(rs);
        Close(ps);
     } 
     return fb;
  }

 
  /**
     * SH-method will get all ratings(y/n answers) for cnreviewer rating form.
     * @param fb
     * @param assignId
     * @return
     */
  public CnRatingFormBean getCnRatingAnswers(CnRatingFormBean fb, long assignId)
  {  
     try {    
         conn = initializeConn();
         ps = conn.prepareStatement("select * from REVIEWER_RATINGS where SGA_ID=? ");
         ps.setLong(1, assignId);     
         rs = ps.executeQuery();
     
         while(rs.next())
         {
            int ratetype = rs.getInt("RAT_ID");            
            switch(ratetype){
                case 64:
                    fb.setQuestionone(rs.getBoolean("score"));
                    break;
                case 65:
                    fb.setQuestiontwo(rs.getBoolean("score"));
                    break;
                case 55:
                    fb.setQuestionthree(rs.getBoolean("score"));
                    break;
                case 56:
                    fb.setQuestionfour(rs.getBoolean("score"));
                    break;
                case 57:
                    fb.setQuestionfive(rs.getBoolean("score"));
                    break;
                case 58:
                    fb.setQuestionsix(rs.getBoolean("score"));
                    break;
                case 59:
                    fb.setQuestionseven(rs.getBoolean("score"));
                    break;
                case 60:
                    fb.setQuestioneight(rs.getBoolean("score"));
                    break;
                case 61:
                    fb.setQuestionnine(rs.getBoolean("score"));
                    break;
                case 62:
                    fb.setQuestionten(rs.getBoolean("score"));
                    break;
                case 63:
                    fb.setQuestioneleven(rs.getBoolean("score"));
                    break;             
                case 66:
                    fb.setQuestiontwelve(rs.getBoolean("score"));
                    break;
            }
         }                                         
     } catch (Exception ex){
       System.err.println("error getCnRatingAnswers() " + ex.toString());
     }
     finally{
         Close(conn);
         Close(rs);
         Close(ps);
     }
     return fb;
  }
    
    
    
    /**
     * SH-method will update t/f recommendation, and amt_recommended
     * @param rb
     * @param username
     * @return
     */
    public int updateSystemRecomendation(CnRatingFormBean rb, String username)
    {
        int outcome=0;  
        try {    
            conn = initializeConn();       
            ps = conn.prepareStatement("update SYSTEM_GRANT_ASSIGNS SET RECOMMENDATION=?, " +
            " RECOMMEND_AMT=?, MODIFIED_BY=?, DATE_MODIFIED=sysdate WHERE ID=?");
            
            //parse out an $ or decimals in amt_recommended
            String amtrec = rb.getAmtrecommendedStr();
            long amtrec_num = 0;
            if(amtrec!= null && !amtrec.equals(""))
              amtrec_num = dbh.parseLongAmtNoDecimal(amtrec);
                           
            ps.setBoolean(1, rb.isProjectrecommend());
            ps.setLong(2, amtrec_num);
            ps.setString(3, username);
            ps.setLong(4,rb.getAssignmentid());            
            outcome = ps.executeUpdate(); 
             
      } catch (Exception ex){
          System.err.println("error updateSystemRecomendation  " + ex.toString());
      }
      finally{
        Close(conn);
        Close(rs);
        Close(ps);
      }
      return outcome;
    }
    
    
    /**
     * SH - update the reduce_match and reduced_justification fields 4/24/12
     * @param sb
     * @param username
     * @return
     */
    public int updateReduceJustification(SystemAssignBean sb, String username)
    {
        int outcome=0;  
        try {    
            conn = initializeConn();       
            ps = conn.prepareStatement("update SYSTEM_GRANT_ASSIGNS SET reduce_match=?, " +
            " match_justification=?, MODIFIED_BY=?, DATE_MODIFIED=sysdate WHERE ID=?");
                                                   
            ps.setBoolean(1, sb.isReduceMatchExists());
            ps.setString(2, sb.getMatchJustification());
            ps.setString(3, username);
            ps.setLong(4, sb.getAssignmentId());            
            outcome = ps.executeUpdate(); 
             
      } catch (Exception ex){
          System.err.println("error updateReduceJustification " + ex.toString());
      }
      finally{
        Close(conn);
        Close(rs);
        Close(ps);
      }
      return outcome;
    }
    
    
    
    
  public int updateReduceMatchCriteria(SystemAssignBean sb, String username)
  {
      int outcome=0;  
      try {    
          conn = initializeConn();       
          ps = conn.prepareStatement("update SYSTEM_GRANT_ASSIGNS SET reduce_match=?, " +
          " match_justification=?, MODIFIED_BY=?, DATE_MODIFIED=sysdate WHERE ID=?");
                                                 
          ps.setBoolean(1, sb.isReduceMatchExists());
          ps.setString(2, sb.getMatchJustification());
          ps.setString(3, username);
          ps.setLong(4, sb.getAssignmentId());            
          outcome = ps.executeUpdate(); 
           
    } catch (Exception ex){
        System.err.println("error updateReduceMatchCriteria " + ex.toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }
    return outcome;
  }
  
  
  
  
  /**
     *Method will get all existing construction reduce match criteria.  Then depending on if each criteria was 
     * checked/unchecked on form; either insert/update/delete in reduce match table. new 5/19/14 for cn FY2014-15
     * @param cb
     * @param ub
     */
  public void handleSaveReduceMatchCriteria(SystemAssignBean cb, UserBean ub)
  {
   PreparedStatement psdelete = null, psotherinsert=null, psotherupdate=null;
        
   try{
      //get any existing reduce matches already in db
      HashMap projmap = getExistingReduceMatchTypes(cb.getAssignmentId());
      
      conn = initializeConn();
      ps = conn.prepareStatement("insert into GRANT_REDUCE_MATCHES (ID, RMT_ID, SGA_ID, DATE_CREATED, "+
      "CREATED_BY) values (GRM_SEQ.NEXTVAL, ?, ?, sysdate, ?)");
             
      psdelete = conn.prepareStatement("delete from GRANT_REDUCE_MATCHES where RMT_ID=? "+
      " and SGA_id=?");
      
      //for reduce match type = other; need to insert description
      psotherinsert = conn.prepareStatement("insert into GRANT_REDUCE_MATCHES (ID, RMT_ID, SGA_ID, DATE_CREATED, "+ 
      "  CREATED_BY, CRITERIA_DESCR) values (GRM_SEQ.NEXTVAL, ?, ?, sysdate, ?, ?)");
       
      //for reduce match type = other; need to update description
      psotherupdate = conn.prepareStatement("update GRANT_REDUCE_MATCHES set CRITERIA_DESCR=?, date_modified=sysdate, "+
      " modified_by=? where rmt_id=? and sga_id=?");
      
      if(cb.isLunchEligible()){//lunch checked
          if( !projmap.containsKey(new Integer(1))){//record TBAdded
              ps.setInt(1, 1);
              ps.setLong(2, cb.getAssignmentId());
              ps.setString(3, ub.getUserid());
              ps.addBatch();
          }
      }else{// NOT checked
          if(projmap.containsKey(new Integer(1)) ){//record TBDeleted
              psdelete.setInt(1, 1);
              psdelete.setLong(2, cb.getAssignmentId());
              psdelete.addBatch();
          }            
      }
      
       if(cb.isPovertyRate()){//poverty checked
           if( !projmap.containsKey(new Integer(2))){// record TBAdded
               ps.setInt(1, 2);
               ps.setLong(2, cb.getAssignmentId());
               ps.setString(3, ub.getUserid());
               ps.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(2)) ){// record TBDeleted
               psdelete.setInt(1, 2);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }
       
       if(cb.isUnemployment()){//unemployment checked
           if( !projmap.containsKey(new Integer(3))){// record TBAdded
               ps.setInt(1, 3);
               ps.setLong(2, cb.getAssignmentId());
               ps.setString(3, ub.getUserid());
               ps.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(3)) ){// record TBDeleted
               psdelete.setInt(1, 3);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }
       
       if(cb.isEducation()){//education checked
           if( !projmap.containsKey(new Integer(4))){// record TBAdded
               ps.setInt(1, 4);
               ps.setLong(2, cb.getAssignmentId());
               ps.setString(3, ub.getUserid());
               ps.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(4)) ){// record TBDeleted
               psdelete.setInt(1, 4);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }
                
       if(cb.isEnglishLang()){//english lang checked
           if( !projmap.containsKey(new Integer(5))){// record TBAdded
               ps.setInt(1, 5);
               ps.setLong(2, cb.getAssignmentId());
               ps.setString(3, ub.getUserid());
               ps.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(5)) ){// record TBDeleted
               psdelete.setInt(1, 5);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }
       
       if(cb.isHousing()){//housing checked
           if( !projmap.containsKey(new Integer(6))){// record TBAdded
               ps.setInt(1, 6);
               ps.setLong(2, cb.getAssignmentId());
               ps.setString(3, ub.getUserid());
               ps.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(6)) ){// record TBDeleted
               psdelete.setInt(1, 6);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }
       
       if(cb.isOtherRate()){//other checked
           if( !projmap.containsKey(new Integer(7))){// record TBAdded
               psotherinsert.setInt(1, 7);
               psotherinsert.setLong(2, cb.getAssignmentId());
               psotherinsert.setString(3, ub.getUserid());
               psotherinsert.setString(4, cb.getOtherDescr());
               psotherinsert.addBatch();
           }
           else{//record TBUpdated
               psotherupdate.setString(1, cb.getOtherDescr());
               psotherupdate.setString(2, ub.getUserid());
               psotherupdate.setInt(3, 7);
               psotherupdate.setLong(4, cb.getAssignmentId());
               psotherupdate.addBatch();
           }
       }else{// NOT checked
           if(projmap.containsKey(new Integer(7)) ){// record TBDeleted
               psdelete.setInt(1, 7);
               psdelete.setLong(2, cb.getAssignmentId());
               psdelete.addBatch();
           }            
       }        
      
      ps.executeBatch();
      psdelete.executeBatch();
      psotherinsert.executeBatch();
      psotherupdate.executeBatch();
          
   }catch(Exception e){
      System.err.println("error handleSaveReduceMatchCriteria() " + e.getMessage().toString());
   }
   finally{
          Close(ps);
          Close(conn);
          Close(rs);
          Close(psdelete);
          Close(psotherinsert);
          Close(psotherupdate);
   }      
  }
  
    
  
  /**
     * SH-method will loop through collection of all cnRev assignments, and update
     * the recomendAmt and 'submit to LD' fields. 
     * modified 9/27/11-'director_auth' field is used to lock app to systems. admin can
     * modify 'director_auth' to unlock app for systems.
     * @param allAssigns
     * @param userid
     * @return
     */
   public int handleSystemSubmitMember(List allAssigns, String userid)
   {    
      int outcome=0;
      try {    
        conn = initializeConn();                  
        ps = conn.prepareStatement("update system_grant_assigns set rating_complete=?, "+
        "recommend_amt=?, recommendation=?, date_modified=sysdate, modified_by=?, "+
        " director_auth=? where ID=?");
        
        if(allAssigns!=null){
            
            for(int i=0; i<allAssigns.size(); i++){
                SystemAssignBean sb = (SystemAssignBean) allAssigns.get(i);
                
                //parse out an $ or decimals in amt_recommended
                String amtrec = sb.getRecommendAmtStr();
                long amtrec_num = 0;
                if(amtrec!= null && !amtrec.equals(""))
                  amtrec_num = dbh.parseLongAmtNoDecimal(amtrec);
                  
                ps.setBoolean(1, sb.isRatingComplete());
                ps.setLong(2, amtrec_num);
                ps.setBoolean(3, (amtrec_num>0) );
                ps.setString(4, userid);
                ps.setBoolean(5, sb.isRatingComplete());
                ps.setLong(6, sb.getAssignmentId());
                ps.addBatch();
            }            
        }        
        ps.executeBatch();
                                    
      } catch (Exception ex){
          System.out.println("error handleSystemSubmitMember() " + ex.toString());
      }
      finally{
        Close(conn);
        Close(rs);
        Close(ps);
      }    
     return outcome;
   }
  
     
    /**
     * SH-method will check if app is being submitted to LD; if true, then update fields
     * to lock out members/systems from editing this app.
     * @param allAssigns
     * @param userid
     * @return
     */
    public int handleLockAllLdSubmissions(List allAssigns, String userid)
    {    
       int outcome=0;
       try {    
         conn = initializeConn();                  
         ps = conn.prepareStatement("update GRANTS set COVERSHEET_COMP=1, DESCRIPTION_COMP=1, " + 
         " BUDGET_COMP=1, AUTH_COMP=1, FS20_COMP=1, LOCK_APP=1, MICROFORM_YN=1, "+
         " RELIGIOUS_AFILL=1, ATTACH_COMP=1, SHPO_COMP=1, SEAF_COMP=1, PAYEE_COMP=1, PHOTO_COMP=1, " + 
         " DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?");
         
         if(allAssigns!=null){
             
             for(int i=0; i<allAssigns.size(); i++){
                 SystemAssignBean sb = (SystemAssignBean) allAssigns.get(i);
                 
                 //if ready to submit to LD; lock out system and member from editing app
                 if(sb.isRatingComplete()){
                     ps.setString(1, userid);
                     ps.setLong(2, sb.getGrantId());
                     ps.addBatch();
                 }
             }            
         }        
         ps.executeBatch();
                                     
       } catch (Exception ex){
           System.out.println("error handleLockAllLdSubmissions() " + ex.toString());
       }
       finally{
         Close(conn);
         Close(rs);
         Close(ps);
       }    
      return outcome;
    }
    
    
    
    public int lockAppForSystemEdit(long grantid, String userid, boolean lockvar)
    {    
       int outcome=0;
       try {    
         conn = initializeConn();                  
         ps = conn.prepareStatement("update system_grant_assigns set date_modified=sysdate, "+
         " modified_by=?, director_auth=? where gra_id=?");
         
         ps.setString(1, userid);
         ps.setBoolean(2, lockvar);
         ps.setLong(3, grantid);
         outcome = ps.executeUpdate();        
                                     
       } catch (Exception ex){
           System.out.println("error handleSystemSubmitMember() " + ex.toString());
       }
       finally{
         Close(conn);
         Close(rs);
         Close(ps);
       }    
      return outcome;
    }
    
    /**
     * 
     * @param grantid
     * @param userid
     * @return
     */
    public int unsubmitToPls(long grantid, String userid)
    {    
       int outcome=0;
       try {    
         conn = initializeConn();                  
         ps = conn.prepareStatement("update system_grant_assigns set date_modified=sysdate, "+
         " modified_by=?, director_auth=0, rating_complete=0 where gra_id=?");
         
         ps.setString(1, userid);
         ps.setLong(2, grantid);
         outcome = ps.executeUpdate();        
                                     
       } catch (Exception ex){
           System.out.println("error unsubmitToPls() " + ex.toString());
       }
       finally{
         Close(conn);
         Close(rs);
         Close(ps);
       }    
      return outcome;
    }
    
    
    public CnApplicationBean getBuildingGrantInfo(long grantid)
    {    
       Format yearformat = new SimpleDateFormat("yyyy");
       CnApplicationBean ab = new CnApplicationBean();
       
       try {    
         conn = initializeConn();                  
         ps = conn.prepareStatement("select * from grant_buildings where gra_id=?");
         ps.setLong(1, grantid); 
         rs = ps.executeQuery();
         while(rs.next()){
             ab.setGrantBuildingId(rs.getLong("id"));
             ab.setGrantId(grantid);
             ab.setProgAccess(rs.getBoolean("prog_access"));
             ab.setSiteOwned(rs.getInt("site_owned"));                
             ab.setHistoricDist(rs.getBoolean("historic"));
             ab.setOver50(rs.getBoolean("over50"));
             ab.setSchoolOwned(rs.getBoolean("school_own"));
             ab.setSchoolDistrict(rs.getString("sch_district"));
             ab.setOverCost(rs.getBoolean("over_cost"));
             ab.setBondPaid(rs.getBoolean("bonded"));
             ab.setCertOccupancy(rs.getBoolean("cert_occupancy"));
             
             String expDate =null;
             if(rs.getDate("expect_start")!=null)
                expDate = formatter.format(rs.getDate("expect_start"));                
             ab.setExpectStartDate(expDate);
             
             String startDate =null;
             if(rs.getDate("proj_started")!=null)
                startDate = formatter.format(rs.getDate("proj_started"));                
             ab.setProjStartedDate(startDate);
             
             String compDate=null;
             if(rs.getDate("proj_complete")!=null)
                compDate = formatter.format(rs.getDate("proj_complete"));                
             ab.setProjCompleteDate(compDate);
             
             ab.setBuildingId(rs.getLong("bldg_id"));
         }
         
                  
         ps.clearParameters();
         ps = conn.prepareStatement("select sed_buildings.bldg_id, building_name, "+
         " number_of_floors, year_of_construction, building_square_footage, " + 
         "leased, ada_accessible, historic_district, historic_landmark, sed_buildings.bldg_type_code, " + 
         " addresses.id as addrid, addr_line1, city, state, zipcode, is_active, description " + 
         " from SED_BUILDINGS, ADDRESSES, SED_BUILDING_TYPES where " + 
         "SED_BUILDINGS.BLDG_ID=ADDRESSES.BLDG_ID and SED_BUILDINGS.BLDG_TYPE_CODE= " + 
         "SED_BUILDING_TYPES.BLDG_TYPE_CODE and sed_buildings.bldg_id=?");
         ps.setLong(1, ab.getBuildingId());
         rs = ps.executeQuery();
         while(rs.next()){
             ab.setBuildingName(rs.getString("building_name"));
             ab.setNumberBuildingFloors(rs.getInt("number_of_floors"));
             ab.setNumberBuildingFloorsStr(rs.getString("number_of_floors"));
             ab.setDateConstructed(rs.getDate("year_of_construction"));
             
             ab.setDateConstructedStr(rs.getString("year_of_construction"));
             if(ab.getDateConstructedStr()!=null)
                ab.setDateConstructedStr(yearformat.format(rs.getDate("year_of_construction")));
             
             ab.setBuildingSquareFootage(rs.getLong("building_square_footage"));
             ab.setBuildingSquareFootageStr(rs.getString("building_square_footage"));
             ab.setLibOwned(rs.getInt("leased"));
             ab.setPhysicalAccess(rs.getBoolean("ada_accessible"));
             ab.setHistoricDist(rs.getBoolean("historic_district"));
             ab.setHistoricLandmark(rs.getBoolean("historic_landmark"));
             ab.setBuildingTypeCode(rs.getInt("bldg_type_code"));
             ab.setAddressId(rs.getLong("addrid"));
             ab.setBuildingStreet(rs.getString("addr_line1"));
             ab.setBuildingCity(rs.getString("city"));
             ab.setBuildingState(rs.getString("state"));
             ab.setBuildingZip(rs.getString("zipcode"));
             ab.setBuildingType(rs.getString("description"));
         }
                                     
       } catch (Exception ex){
           System.out.println("error getBuildingGrantInfo() " + ex.toString());
       }
       finally{
         Close(conn);
         Close(rs);
         Close(ps);
       }    
      return ab;
    }
    
    
    public CnApplicationBean getBuildingProjectTypes(CnApplicationBean ab)
    {    
       try {    
         conn = initializeConn();                  
         ps = conn.prepareStatement("select * from building_projects where gb_id=?");
         ps.setLong(1, ab.getGrantBuildingId()); 
         rs = ps.executeQuery();
         
         while(rs.next()){
             int ptype = rs.getInt("pt_id");
             switch(ptype){
                 case 1:
                    ab.setNewConstruction(true);
                    break;
                 case 2:
                    ab.setExpansion(true);
                    break;
                 case 3:
                    ab.setAcquisition(true);
                    break;
                 case 4:
                    ab.setRenovation(true);
                    break;
                 case 5:
                    ab.setConservation(true);
                    break;
                 case 6:
                    ab.setAccess(true);
                    break;
                 case 7:
                    ab.setOtherProject(true);
                    break;
                 case 8:
                     ab.setBroadband(true);
                     break;
             }             
         }
                                     
       } catch (Exception ex){
           System.out.println("error getBuildingProjectTypes() " + ex.toString());
       }
       finally{
         Close(conn);
         Close(rs);
         Close(ps);
       }    
      return ab;
    }
    
    public HashMap getExistingProjectTypes(long grantid)
    {
      HashMap allProjTypes = new HashMap();          
      try {      
        conn = initializeConn();
        
        ps = conn.prepareStatement("select * from BUILDING_PROJECTS where GB_ID in "+
        "(select id from grant_buildings where gra_id=?)");        
        ps.setLong(1, grantid); 
        rs = ps.executeQuery();
        
        while(rs.next()){
          int id = rs.getInt("pt_id");
          allProjTypes.put(new Integer(id), "true");
        }
              
      }catch(Exception e){
        System.err.println("error getExistingProjectTypes() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }
      return allProjTypes;
    }
    
    
  public HashMap getExistingReduceMatchTypes(long assignmentId)
  {
    HashMap allProjTypes = new HashMap();          
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from GRANT_REDUCE_MATCHES where SGA_ID=? ");        
      ps.setLong(1, assignmentId); 
      rs = ps.executeQuery();
      
      while(rs.next()){
        int id = rs.getInt("rmt_id");
        allProjTypes.put(new Integer(id), "true");
      }
            
    }catch(Exception e){
      System.err.println("error getExistingReduceMatchTypes() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allProjTypes;
  }
  
  
    
    //SH note this assumes the appbean has the grant_building_id (even if the record was just created)
  public void handleSaveBuildingProjectTypes(CnApplicationBean cb, long grantid, UserBean ub)
  {
     PreparedStatement psdelete = null;
          
     try{
        //get any existing project types already in db
        HashMap projmap = getExistingProjectTypes(grantid);
        
        conn = initializeConn();
        ps = conn.prepareStatement("insert into BUILDING_PROJECTS (ID, PT_ID, GB_ID, DATE_CREATED, "+
        "CREATED_BY) values (BP_SEQ.NEXTVAL, ?, ?, sysdate, ?)");
        
        psdelete = conn.prepareStatement("delete from BUILDING_PROJECTS where pt_id=? "+
        " and gb_id=?");
        
        if(cb.isNewConstruction()){//new construct checked
            if( !projmap.containsKey(new Integer(1))){//newconstruct record TBAdded
                ps.setInt(1, 1);
                ps.setLong(2, cb.getGrantBuildingId());
                ps.setString(3, ub.getUserid());
                ps.addBatch();
            }
        }else{//new construct NOT checked
            if(projmap.containsKey(new Integer(1)) ){//new construct record TBDeleted
                psdelete.setInt(1, 1);
                psdelete.setLong(2, cb.getGrantBuildingId());
                psdelete.addBatch();
            }            
        }
        
         if(cb.isExpansion()){//expansion checked
             if( !projmap.containsKey(new Integer(2))){//expansion record TBAdded
                 ps.setInt(1, 2);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//expansion NOT checked
             if(projmap.containsKey(new Integer(2)) ){//expansion record TBDeleted
                 psdelete.setInt(1, 2);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }
         
         if(cb.isAcquisition()){//acquisition checked
             if( !projmap.containsKey(new Integer(3))){//acquisition record TBAdded
                 ps.setInt(1, 3);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//acquisition NOT checked
             if(projmap.containsKey(new Integer(3)) ){//acquisition record TBDeleted
                 psdelete.setInt(1, 3);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }
         
         if(cb.isRenovation()){//renovation checked
             if( !projmap.containsKey(new Integer(4))){//renovation record TBAdded
                 ps.setInt(1, 4);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//renovation NOT checked
             if(projmap.containsKey(new Integer(4)) ){//renovation record TBDeleted
                 psdelete.setInt(1, 4);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }
                  
         if(cb.isConservation()){//conservation checked
             if( !projmap.containsKey(new Integer(5))){//conservation record TBAdded
                 ps.setInt(1, 5);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//conservation NOT checked
             if(projmap.containsKey(new Integer(5)) ){//conservation record TBDeleted
                 psdelete.setInt(1, 5);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }
         
         if(cb.isAccess()){//accessibility checked
             if( !projmap.containsKey(new Integer(6))){//accessibility record TBAdded
                 ps.setInt(1, 6);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//accessibility NOT checked
             if(projmap.containsKey(new Integer(6)) ){//accessibility record TBDeleted
                 psdelete.setInt(1, 6);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }
         
         if(cb.isOtherProject()){//otherproject checked
             if( !projmap.containsKey(new Integer(7))){//otherproject record TBAdded
                 ps.setInt(1, 7);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//otherproject NOT checked
             if(projmap.containsKey(new Integer(7)) ){//otherproject record TBDeleted
                 psdelete.setInt(1, 7);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }        
         
         if(cb.isBroadband()){//broadband checked
             if( !projmap.containsKey(new Integer(8))){//broadband record TBAdded
                 ps.setInt(1, 8);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setString(3, ub.getUserid());
                 ps.addBatch();
             }
         }else{//broadband NOT checked
             if(projmap.containsKey(new Integer(8)) ){//broadband record TBDeleted
                 psdelete.setInt(1, 8);
                 psdelete.setLong(2, cb.getGrantBuildingId());
                 psdelete.addBatch();
             }            
         }        
        
        ps.executeBatch();
        psdelete.executeBatch();
            
     }catch(Exception e){
        System.err.println("error handleSaveBuildingProjectTypes() " + e.getMessage().toString());
     }
     finally{
            Close(ps);
            Close(conn);
            Close(rs);
            Close(psdelete);
     }      
  }
  
  
  public void handleSaveCostAmounts(CnApplicationBean cb, UserBean ub)
  {
       PreparedStatement psupdate = null;
            
       try{                   
          conn = initializeConn();
          ps = conn.prepareStatement("insert into BUILDING_FUNDS (ID, AMOUNT_RECEIVED, "+
          " GB_ID, FS_ID, DATE_CREATED, CREATED_BY) values (BF_SEQ.NEXTVAL, ?, ?,?, sysdate, ?)");
          
          psupdate = conn.prepareStatement("update BUILDING_FUNDS set AMOUNT_RECEIVED=?, "+
          " DATE_MODIFIED=sysdate, MODIFIED_BY=? where ID=?");
          
          if(cb.getTotalCostStr()!=null && !cb.getTotalCostStr().equals("")){
              
              if(cb.getTotalCostFundId()==0){//insert totalcost                  
                 //parse out an $ or decimals in totalcost
                 long totcost_num= dbh.parseLongAmtNoDecimal(cb.getTotalCostStr());
                 ps.setLong(1, totcost_num);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setInt(3, 1);//FS_ID
                 ps.setString(4, ub.getUserid());
                 ps.addBatch();                 
              }
              else{//update totalcost
                 //parse out an $ or decimals in totalcost
                 long totcost_num= dbh.parseLongAmtNoDecimal(cb.getTotalCostStr());
                 psupdate.setLong(1, totcost_num);
                 psupdate.setString(2, ub.getUserid());
                 psupdate.setLong(3, cb.getTotalCostFundId());
                 psupdate.addBatch();
              }
          }
          
          if(cb.getRequestCostStr()!=null && !cb.getRequestCostStr().equals("")){
          
              if(cb.getRequestCostFundId()==0){//insert reqcost                  
                 //parse out an $ or decimals in reqcost
                 long reqcost_num= dbh.parseLongAmtNoDecimal(cb.getRequestCostStr());
                 ps.setLong(1, reqcost_num);
                 ps.setLong(2, cb.getGrantBuildingId());
                 ps.setInt(3, 2);//FS_ID
                 ps.setString(4, ub.getUserid());
                 ps.addBatch();                 
              }
              else{//update reqcost
                 //parse out an $ or decimals in reqcost
                 long reqcost_num= dbh.parseLongAmtNoDecimal(cb.getRequestCostStr());
                 psupdate.setLong(1, reqcost_num);
                 psupdate.setString(2, ub.getUserid());
                 psupdate.setLong(3, cb.getRequestCostFundId());
                 psupdate.addBatch();
              }         
          }
          
          ps.executeBatch();
          psupdate.executeBatch();
          
       }catch(Exception e){
        System.err.println("error handleSaveCostAmounts() " + e.getMessage().toString());
       }
       finally{
           Close(ps);
           Close(conn);
           Close(rs);
           Close(psupdate);
       }      
  }
          
    
  public long insertGrantBuilding(UserBean ub, long grantid, CnApplicationBean ab)
  {
     int outcome =0; 
     long newGrantBuildingId=0;
     
     try {    
        conn = initializeConn();
   
        ps = conn.prepareStatement("INSERT INTO GRANT_BUILDINGS(ID, PROG_ACCESS, SITE_OWNED, "+
        " HISTORIC, OVER50, SCHOOL_OWN, SCH_DISTRICT, OVER_COST, BONDED, EXPECT_START, "+
        "PROJ_STARTED, PROJ_COMPLETE, DATE_CREATED, CREATED_BY, GRA_ID, BLDG_ID, cert_occupancy) values (gb_seq.nextval, "+
        "?, ?, ?, ?, ?, ?, ?, ?, to_date(?, 'mm/dd/yyyy'), to_date(?, 'mm/dd/yyyy'), "+
        " to_date(?, 'mm/dd/yyyy'), sysdate, ?, ?, ?, ?)");        
        ps.setBoolean(1, ab.isProgAccess());        
        ps.setInt(2, ab.getSiteOwned() );//site_owned
        
        ps.setBoolean(3, ab.isHistoricDist());
        ps.setBoolean(4, ab.isOver50());
        ps.setBoolean(5, ab.isSchoolOwned());
        ps.setString(6, ab.getSchoolDistrict());
        ps.setBoolean(7, ab.isOverCost());
        ps.setBoolean(8, ab.isBondPaid());
        ps.setString(9, ab.getExpectStartDate());
        ps.setString(10, ab.getProjStartedDate());
        ps.setString(11, ab.getProjCompleteDate());
        ps.setString(12, ub.getUserid());
        ps.setLong(13, grantid);
        ps.setLong(14, ab.getBuildingId());
        ps.setBoolean(15, ab.isCertOccupancy());
        outcome = ps.executeUpdate(); 
        
        ps = conn.prepareStatement("select id from grant_buildings where gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        while(rs.next()){
            newGrantBuildingId = rs.getLong("id");
        }
                                  
     } catch (Exception ex){
        System.err.println("error insertGrantBuilding() " + ex.toString());
     }
     finally{
        Close(conn);
        Close(rs);
        Close(ps);
     }   
     return newGrantBuildingId;
  }
    
    
  public int updateGrantBuilding(UserBean ub, CnApplicationBean ab)
  {
       int outcome =0;           
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("update GRANT_BUILDINGS set PROG_ACCESS=?, SITE_OWNED=?, "+
          " HISTORIC=?, OVER50=?, SCHOOL_OWN=?, SCH_DISTRICT=?, OVER_COST=?, BONDED=?, "+
          " EXPECT_START=to_date(?, 'mm/dd/yyyy'), PROJ_STARTED=to_date(?, 'mm/dd/yyyy'), "+
          " PROJ_COMPLETE=to_date(?, 'mm/dd/yyyy'), DATE_MODIFIED=sysdate, MODIFIED_BY=?, "+
          " cert_occupancy=? where ID=?");        
          ps.setBoolean(1, ab.isProgAccess());
          ps.setInt(2, ab.getSiteOwned());//site_owned
          
          ps.setBoolean(3, ab.isHistoricDist());
          ps.setBoolean(4, ab.isOver50());
          ps.setBoolean(5, ab.isSchoolOwned());
          ps.setString(6, ab.getSchoolDistrict());
          ps.setBoolean(7, ab.isOverCost());
          ps.setBoolean(8, ab.isBondPaid());
          ps.setString(9, ab.getExpectStartDate());
          ps.setString(10, ab.getProjStartedDate());
          ps.setString(11, ab.getProjCompleteDate());
          ps.setString(12, ub.getUserid());
          ps.setBoolean(13, ab.isCertOccupancy());
          ps.setLong(14, ab.getGrantBuildingId());
          outcome = ps.executeUpdate(); 
                                    
       } catch (Exception ex){
          System.err.println("error updateGrantBuildingRecord() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return outcome;
  }
  
  
    public long insertBuilding(UserBean ub, CnApplicationBean ab)
    {
       int outcome =0;       
       long newBuildingId =0;       
       try {    
          conn = initializeConn();
          
          //need new building record
          ps = conn.prepareStatement("insert into SED_BUILDINGS ( BLDG_ID, BUILDING_NAME, "+
          "NUMBER_OF_FLOORS, YEAR_OF_CONSTRUCTION, BUILDING_SQUARE_FOOTAGE, LEASED, "+
          " ADA_ACCESSIBLE, HISTORIC_DISTRICT, HISTORIC_LANDMARK, " + 
          "BLDG_TYPE_CODE, CREATED_BY, DATE_CREATED) values (BLDG_SEQ.nextval, ?, ?, to_date(?, 'mm/dd/yyyy'), " +
          "?, ?, ?, ?, ?, ?, ?, sysdate)");   
          
          if(ab.getBuildingSquareFootageStr()!= null && !ab.getBuildingSquareFootageStr().equals(""))
              ab.setBuildingSquareFootage(dbh.parseLongAmtNoDecimal(ab.getBuildingSquareFootageStr()) );
           
          if(ab.getNumberBuildingFloorsStr()!= null && !ab.getNumberBuildingFloorsStr().equals(""))
              ab.setNumberBuildingFloors(dbh.parseCurrencyAmtNoDecimal(ab.getNumberBuildingFloorsStr()) );
           
          
          ps.setString(1, ab.getBuildingName());        
          ps.setInt(2, ab.getNumberBuildingFloors() );          
          ps.setString(3, "1/1/"+ab.getDateConstructedStr());//year of construct          
          ps.setLong(4, ab.getBuildingSquareFootage());
          ps.setInt(5, ab.getLibOwned());//leased
          ps.setBoolean(6, ab.isPhysicalAccess());
          ps.setBoolean(7, ab.isHistoricDist());
          ps.setBoolean(8, ab.isHistoricLandmark());
          ps.setInt(9, ab.getBuildingTypeCode());
          ps.setString(10, ub.getUserid());
          outcome = ps.executeUpdate(); 
          
          //get the newly inserted buildingId
          ps = conn.prepareStatement("select BLDG_SEQ.currval from SED_BUILDINGS");
          rs = ps.executeQuery();
          while(rs.next()){
              newBuildingId = rs.getLong(1);
          }
                    
          //need to associate new building with its parent institution
          ps = conn.prepareStatement("insert into SED_INSTITUTION_BUILDINGS (INST_BLDG_ID, "+
          "INST_ID, BLDG_ID, ACTIVE_DATE, DATE_CREATED, CREATED_BY) values "+
          "(INBLDG_SEQ.nextval, ?, ?, sysdate, sysdate, ?)");
          ps.setLong(1, ab.getInstId());
          ps.setLong(2, newBuildingId);
          ps.setString(3, ub.getUserid());
          outcome = ps.executeUpdate();          
                                    
       } catch (Exception ex){
          System.err.println("error insertBuilding() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return newBuildingId;
    }
    
    
    public int updateBuildingAndAddress(UserBean ub, CnApplicationBean ab)
    {
       int outcome =0;              
       try {    
          conn = initializeConn();
          
          //update building 
          ps = conn.prepareStatement("update SED_BUILDINGS set BUILDING_NAME=?, "+
          "NUMBER_OF_FLOORS=?, YEAR_OF_CONSTRUCTION=to_date(?, 'mm/dd/yyyy'), "+
          " BUILDING_SQUARE_FOOTAGE=?, LEASED=?, ADA_ACCESSIBLE=?, HISTORIC_DISTRICT=?, "+
          " HISTORIC_LANDMARK=?, BLDG_TYPE_CODE=?, modified_BY=?, DATE_modified=sysdate "+
          " where BLDG_ID=?");   
          
           if(ab.getBuildingSquareFootageStr()!= null && !ab.getBuildingSquareFootageStr().equals(""))
               ab.setBuildingSquareFootage(dbh.parseLongAmtNoDecimal(ab.getBuildingSquareFootageStr()) );
            
           if(ab.getNumberBuildingFloorsStr()!= null && !ab.getNumberBuildingFloorsStr().equals(""))
               ab.setNumberBuildingFloors(dbh.parseCurrencyAmtNoDecimal(ab.getNumberBuildingFloorsStr()) );
                      
          ps.setString(1, ab.getBuildingName());        
          ps.setInt(2, ab.getNumberBuildingFloors() );          
          ps.setString(3, "1/1/"+ab.getDateConstructedStr());//year of construct          
          ps.setLong(4, ab.getBuildingSquareFootage());
          ps.setInt(5, ab.getLibOwned());//leased
          ps.setBoolean(6, ab.isPhysicalAccess());
          ps.setBoolean(7, ab.isHistoricDist());
          ps.setBoolean(8, ab.isHistoricLandmark());
          ps.setInt(9, ab.getBuildingTypeCode());
          ps.setString(10, ub.getUserid());
          ps.setLong(11, ab.getBuildingId());
          outcome = ps.executeUpdate(); 
          
          //check if address needs to be inserted or updated
          if(ab.getAddressId()==0)
          {
             ps = conn.prepareStatement("insert into ADDRESSES (ID, ADDR_LINE1, CITY, STATE, "+
             " ZIPCODE, IS_ACTIVE, DATE_CREATED, CREATED_BY, BLDG_ID, ADDR_TYPE_CODE) values "+
             " (ADDRESS_SEQ.nextval, ?,?,?,?,'Y',sysdate,?, ?,'2') ");   
              ps.setString(1, ab.getBuildingStreet());        
              ps.setString(2, ab.getBuildingCity() );          
              ps.setString(3, ab.getBuildingState());   
              ps.setString(4, ab.getBuildingZip());
              ps.setString(5, ub.getUserid());
              ps.setLong(6, ab.getBuildingId());
              outcome = ps.executeUpdate();            
          }
          else{
              //update address
               ps = conn.prepareStatement("update ADDRESSES set ADDR_LINE1=?, CITY=?, STATE=?, "+
               " ZIPCODE=?, DATE_MODIFIED=sysdate, MODIFIED_BY=? where BLDG_ID=?");
               ps.setString(1, ab.getBuildingStreet());        
               ps.setString(2, ab.getBuildingCity() );          
               ps.setString(3, ab.getBuildingState());   
               ps.setString(4, ab.getBuildingZip());
               ps.setString(5, ub.getUserid());
               ps.setLong(6, ab.getBuildingId());
               outcome = ps.executeUpdate();  
          }
                                                      
       } catch (Exception ex){
          System.err.println("error updateBuildingAndAddress() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return outcome;
    }
    
    
    public int insertBuildingAddress(UserBean ub, CnApplicationBean ab)
    {
       int outcome =0;       
       
       try {    
          conn = initializeConn();
          
          ps = conn.prepareStatement("insert into ADDRESSES (ID, ADDR_LINE1, CITY, STATE, "+
          " ZIPCODE, IS_ACTIVE, DATE_CREATED, CREATED_BY, BLDG_ID, ADDR_TYPE_CODE) values "+
          " (ADDRESS_SEQ.nextval, ?,?,?,?,'Y',sysdate,?, ?,'2') ");   
          
          ps.setString(1, ab.getBuildingStreet());        
          ps.setString(2, ab.getBuildingCity() );          
          ps.setString(3, ab.getBuildingState());   
          ps.setString(4, ab.getBuildingZip());
          ps.setString(5, ub.getUserid());
          ps.setLong(6, ab.getBuildingId());
          outcome = ps.executeUpdate(); 
                                            
       } catch (Exception ex){
          System.err.println("error insertBuildingAddress() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return outcome;
    }
    
    public CnApplicationBean determineCostAmts(CnApplicationBean ab, long grantid)
    {
       try{
            //get total amt requested from project budget page totals
            BudgetDBHandler bdh = new BudgetDBHandler();
            ab.setRequestedAmt(bdh.calcTotalAmtRequested(grantid, 0));   
            
            if(ab.getFycode() <13){                
                //calc max 50% that can be awarded based on amt_requested
                if(ab.getRequestedAmt()>0){
                    double maxamt = ab.getRequestedAmt()/2;
                    int maxamtint = (int)Math.round(maxamt);
                    ab.setMaxRequestCost(maxamtint);
                }
            }else{            
                //calc max 75% that can be awarded based on amt_requested, starting 2012/13
                if(ab.getRequestedAmt()>0){
                    double maxamt = ab.getRequestedAmt()* 0.75;
                    int maxamtint = (int)Math.round(maxamt);
                    ab.setMaxRequestCost(maxamtint);
                }
            }
            
            //get total project cost(a) and cost requested (c) for coversheet
            conn = initializeConn();
            ps = conn.prepareStatement("select * from building_funds where gb_id=? and "+
            " (fs_id=1 or fs_id=2)");
            ps.setLong(1, ab.getGrantBuildingId());
            rs = ps.executeQuery();
            while(rs.next()){
                int fundsource=rs.getInt("fs_id");
                                
                if(fundsource==1){
                    ab.setTotalCostStr(rs.getString("amount_received"));
                    ab.setTotalCost(rs.getInt("amount_received"));
                    ab.setTotalCostFundId(rs.getLong("id"));
                    
                    if(ab.getTotalCostStr()!=null && !ab.getTotalCostStr().equals("")){
                       long totcost = Long.parseLong(ab.getTotalCostStr());
                       ab.setTotalCostStr(numF.format(totcost));
                    }
                }
                else if(fundsource==2){
                    ab.setRequestCostStr(rs.getString("amount_received"));
                    ab.setRequestCost(rs.getInt("amount_received"));
                    ab.setRequestCostFundId(rs.getLong("id"));
                    
                    if(ab.getRequestCostStr()!=null && !ab.getRequestCostStr().equals("")){
                       long reqcost = Long.parseLong(ab.getRequestCostStr());
                       ab.setRequestCostStr(numF.format(reqcost));
                    }
                }
            }
            
        }catch (Exception ex){
          System.err.println("error determineCostAmts() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return ab;    
    }    
    
    /**
     * Method gets all possible funds for drop down list; and any fund records
     * for given grant_id. used for apcnt/rev add/update additional funds page.
     * @param grantid
     * @return
     */
    public ArrayList getAllFundsForProject(long grantid)
    {
         ArrayList allfunds=new ArrayList();  
         //get all fund sources to populate dropdown list
         ArrayList allfundsources = generateFundSourcesList();
        
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select * from BUILDING_FUNDS where " + 
            " GB_ID in (select ID from GRANT_BUILDINGS where GRA_ID=?) and " + 
            " FS_ID in (select ID from FUND_SOURCES where ISACTIVE=1)");   
            ps.setLong(1, grantid);
            rs = ps.executeQuery();
            
            while(rs.next()){
                FundBean fb = new FundBean();
                fb.setAllPossibleFundSources(allfundsources);
                fb.setBuildingFundId(rs.getLong("ID"));
                fb.setAmountReceived(rs.getLong("amount_received"));
                fb.setAmountReceivedStr(rs.getString("amount_received"));
                fb.setDescription(rs.getString("DESCRIPTION"));
                fb.setFundSourceId(rs.getInt("FS_ID"));
                fb.setGrantBuildingId(rs.getLong("GB_ID"));
                fb.setGrantId(grantid);
                
                if(fb.getAmountReceivedStr()!=null && !fb.getAmountReceivedStr().equals("")){
                  long amtrec = Long.parseLong(fb.getAmountReceivedStr());
                  fb.setAmountReceivedStr(numF.format(amtrec));
                }
                allfunds.add(fb);
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getAllFundsForProject() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allfunds;
    }
    
    
    /**
     * method gets all fund records for given grant_id; used for printview page
     * @param grantid
     * @return
     */
    public ArrayList getFundsForPrintview(long grantid)
    {
         ArrayList allfunds=new ArrayList();  
                
         try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("select bf.id, amount_received, date_received, "+
            " bf.description, fs_id, gb_id, fund_sources.description as fundsource " + 
            " from BUILDING_FUNDS bf, fund_sources where bf.fs_id=fund_sources.id and " + 
            " GB_ID in (select ID from GRANT_BUILDINGS where gra_id=?) and " + 
            " FS_ID in (select ID from FUND_SOURCES where ISACTIVE=1)");   
            ps.setLong(1, grantid);
            rs = ps.executeQuery();
            
            while(rs.next()){
                FundBean fb = new FundBean();
                fb.setBuildingFundId(rs.getLong("ID"));
                fb.setAmountReceived(rs.getLong("amount_received"));
                fb.setAmountReceivedStr(rs.getString("amount_received"));
                fb.setDescription(rs.getString("DESCRIPTION"));
                fb.setFundSourceId(rs.getInt("FS_ID"));
                fb.setGrantBuildingId(rs.getLong("GB_ID"));
                fb.setGrantId(grantid);
                fb.setFundSource(rs.getString("fundsource"));
                
                if(fb.getAmountReceivedStr()!=null && !fb.getAmountReceivedStr().equals("")){
                  long amtrec = Long.parseLong(fb.getAmountReceivedStr());
                  fb.setAmountReceivedStr(numF.format(amtrec));
                }
                allfunds.add(fb);
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getFundsForPrintview() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allfunds;
    }
    
    
    public ArrayList generateFundSourcesList()
    {
      ArrayList allcodes = new ArrayList();      
      try {    
           conn = initializeConn();
        
           ps = conn.prepareStatement("select * from FUND_SOURCES where ISACTIVE=1 order by id");   
           rs = ps.executeQuery();
           while(rs.next()){
               DropDownListBean dd = new DropDownListBean();
               dd.setId(rs.getInt("id"));
               dd.setDescription(rs.getString("description"));
               allcodes.add(dd);
           }            
                                     
        } catch (Exception ex){
           System.err.println("error generateFundSourcesList() " + ex.toString());
        }
        finally{
           Close(conn);
           Close(rs);
           Close(ps);
        }            
        return allcodes;
    }
    
    
    /*public int insertBlankGrantBuildingRecord(UserBean ub, long grantid)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
            //REFACTOR building id fk
            ps = conn.prepareStatement("INSERT INTO GRANT_BUILDINGS(ID, DATE_CREATED, "+
            " CREATED_BY, GRA_ID, BLDG_ID) values (gb_seq.nextval, "+
            " sysdate, ?, ?, 1)");            
            ps.setString(1, ub.getUserid());
            ps.setLong(2, grantid);     
            outcome = ps.executeUpdate(); 
                                  
        } catch (Exception ex){
              System.err.println("error insertBlankGrantBuildingRecord()  " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }*/
    
    public int insertFundRecord(UserBean ub, long grantBuildingId)
    {
        int outcome =0;           
        try {    
            //create a blank fund record
            conn = initializeConn();
       
            ps = conn.prepareStatement("INSERT INTO BUILDING_FUNDS( " +
            " ID, date_created, created_by, fs_id, gb_id) " +
            " values(BF_SEQ.nextval, sysdate, ?, ?, ?)");        
            ps.setString(1, ub.getUserid());
            ps.setInt(2, 3);//default fund_source
            ps.setLong(3, grantBuildingId);            
            outcome = ps.executeUpdate(); 
                                  
        } catch (Exception ex){
              System.err.println("error insertFundRecord()  " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
        
    public int updateFundRecords(List allrecords, UserBean ub)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("update BUILDING_FUNDS set AMOUNT_RECEIVED=?, "+
            " DESCRIPTION=?, DATE_MODIFIED=sysdate, MODIFIED_BY=?, FS_ID=? where ID=?");     
            
            if(allrecords!=null){
                for(int i=0; i<allrecords.size(); i++){
                    FundBean f = (FundBean) allrecords.get(i);
                    
                    long amtrec_num=0;
                    if(f.getAmountReceivedStr()!=null && !f.getAmountReceivedStr().equals(""))
                        amtrec_num= dbh.parseLongAmtNoDecimal(f.getAmountReceivedStr());
                    
                    ps.setLong(1, amtrec_num);
                    ps.setString(2, f.getDescription());
                    ps.setString(3, ub.getUserid());
                    ps.setInt(4, f.getFundSourceId());
                    ps.setLong(5, f.getBuildingFundId());
                    ps.addBatch();
                }           
                ps.executeBatch();
            }
                                  
        } catch (Exception ex){
              System.err.println("error updateFundRecords() " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
    
    public int deleteFundRecord(long fundId)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("delete from BUILDING_FUNDS where ID=?");   
            ps.setLong(1, fundId);
            outcome = ps.executeUpdate();
                                                
        } catch (Exception ex){
              System.err.println("error deleteFundRecord() " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
    
    /**
     * get the system due date to display to member library-construction only
     * modified 10/4/12 to filter on new fc_code property
     * @param asb
     * @param fycode
     * @return
     */
    public AppStatusBean determineSysDueDateForMember(AppStatusBean asb, int fycode)
    {         
         try {    
           conn = initializeConn();   
           //get lsm_id of the PLS
           ps = conn.prepareStatement("select due_date from system_fiscalyear_details " + 
           "where fy_code=? and fc_code=86 and lsm_id in (select id from "+
           " ldstateaid.library_system_mappings where inst_id=inst_id_has "+
           " and inst_id=?)");    
           ps.setInt(1, fycode);
           ps.setLong(2, asb.getSystemInstId());
           rs = ps.executeQuery();          
           while(rs.next()){
               asb.setSystemDueDate(rs.getDate("due_date"));
           }
                                 
         } catch (Exception ex){
             System.err.println("error determineSysDueDateForMember() " + ex.toString());
         }
         finally{
           Close(conn);
           Close(rs);
           Close(ps);
         }   
         return asb;
    }
    
 
  /**
     * SH-method will check all 11 cnRev comments for user input, and then insert/update
     * depending on whether the comment_type already exists in the db.
     * @param rb
     * @param commentTypes -existing comment records for given system_assign_id
     * @param username
     * @return
     */
   public int updateCnReviewerComment(CnRatingFormBean rb, HashMap commentTypes, String username)
   {
     int outcome = 0;    
     PreparedStatement psinsert = null;
     
     try{      
       conn = initializeConn();
       ps = conn.prepareStatement("update REVIEWER_COMMENTS set REVC_COMMENT=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
           " where SGA_ID=? and CT_ID=? ");
       psinsert = conn.prepareStatement("insert into REVIEWER_COMMENTS (ID, REVC_COMMENT, "+
         " DATE_CREATED, CREATED_BY, SGA_ID, CT_ID) values (REV_COMMENT_SEQ.nextval, ?, "+
         " sysdate, ?, ?, ?)");
         
       if(rb.getCommentone()!=null && !rb.getCommentone().equals("")) {
           if(commentTypes.containsKey(new Integer(6))) {
               ps.setString(1, rb.getCommentone());
               ps.setString(2, username);
               ps.setLong(3, rb.getAssignmentid());
               ps.setInt(4, 6);
               ps.addBatch();
           }
           else {
               psinsert.setString(1, rb.getCommentone());
               psinsert.setString(2, username);
               psinsert.setLong(3, rb.getAssignmentid());
               psinsert.setInt(4, 6);
               psinsert.addBatch();
           }
       }
       
      if(rb.getCommenttwo()!=null && !rb.getCommenttwo().equals("")) {
          if(commentTypes.containsKey(new Integer(7))) {
              ps.setString(1, rb.getCommenttwo());
              ps.setString(2, username);
              ps.setLong(3, rb.getAssignmentid());
              ps.setInt(4, 7);
              ps.addBatch();
          }
          else {
              psinsert.setString(1, rb.getCommenttwo());
              psinsert.setString(2, username);
              psinsert.setLong(3, rb.getAssignmentid());
              psinsert.setInt(4, 7);
              psinsert.addBatch();
          }
      }
     
     if(rb.getCommentthree()!=null && !rb.getCommentthree().equals("")) {
         if(commentTypes.containsKey(new Integer(8))) {
             ps.setString(1, rb.getCommentthree());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 8);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentthree());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 8);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommentfour()!=null && !rb.getCommentfour().equals("")) {
         if(commentTypes.containsKey(new Integer(9))) {
             ps.setString(1, rb.getCommentfour());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 9);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentfour());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 9);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommentfive()!=null && !rb.getCommentfive().equals("")) {
         if(commentTypes.containsKey(new Integer(10))) {
             ps.setString(1, rb.getCommentfive());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 10);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentfive());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 10);
             psinsert.addBatch();
         }
     }
           
     if(rb.getCommentsix()!=null && !rb.getCommentsix().equals("")) {
         if(commentTypes.containsKey(new Integer(11))) {
             ps.setString(1, rb.getCommentsix());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 11);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentsix());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 11);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommentseven()!=null && !rb.getCommentseven().equals("")) {
         if(commentTypes.containsKey(new Integer(12))) {
             ps.setString(1, rb.getCommentseven());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 12);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentseven());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 12);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommenteight()!=null && !rb.getCommenteight().equals("")) {
         if(commentTypes.containsKey(new Integer(13))) {
             ps.setString(1, rb.getCommenteight());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 13);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommenteight());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 13);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommentnine()!=null && !rb.getCommentnine().equals("")) {
         if(commentTypes.containsKey(new Integer(14))) {
             ps.setString(1, rb.getCommentnine());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 14);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentnine());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 14);
             psinsert.addBatch();
         }
     }

     if(rb.getCommentten()!=null && !rb.getCommentten().equals("")) {
         if(commentTypes.containsKey(new Integer(15))) {
             ps.setString(1, rb.getCommentten());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 15);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommentten());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 15);
             psinsert.addBatch();
         }
     }
     
     if(rb.getCommenteleven()!=null && !rb.getCommenteleven().equals("")) {
         if(commentTypes.containsKey(new Integer(16))) {
             ps.setString(1, rb.getCommenteleven());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 16);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommenteleven());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 16);
             psinsert.addBatch();
         }
     }
                  
     //new for question 12; 2/24/16
     if(rb.getCommenttwelve()!=null && !rb.getCommenttwelve().equals("")) {
         if(commentTypes.containsKey(new Integer(18))) {
             ps.setString(1, rb.getCommenttwelve());
             ps.setString(2, username);
             ps.setLong(3, rb.getAssignmentid());
             ps.setInt(4, 18);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getCommenttwelve());
             psinsert.setString(2, username);
             psinsert.setLong(3, rb.getAssignmentid());
             psinsert.setInt(4, 18);
             psinsert.addBatch();
         }
     }
              
     ps.executeBatch();
     psinsert.executeBatch();
       
     } catch(Exception e){
       System.err.println("error updateCnReviewerComment() " + e.getMessage().toString());
     }
     finally{
       Close(ps);
       Close(psinsert);
       Close(conn);
       Close(rs);
     }
     return outcome;
   }
   
   /**
     * SH-method will insert/update the t/f answers from cnreviewer rating form based on
     * whether record exists for given rating_type_id.
     * @param rb
     * @param ratingTypes
     * @param username
     * @return
     */
    public int updateCnReviewerAnswer(CnRatingFormBean rb, HashMap ratingTypes, String username)
    {
      int outcome = 0;    
      PreparedStatement psinsert = null;
      
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("update REVIEWER_RATINGS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
            " where SGA_ID=? and RAT_ID=?");
        psinsert = conn.prepareStatement("insert into REVIEWER_RATINGS (ID, SCORE, "+
          " DATE_CREATED, CREATED_BY, SGA_ID, RAT_ID) values (REV_RATING_SEQ.nextval, ?, "+
          " sysdate, ?, ?, ?)");
        
        if(ratingTypes.containsKey(new Integer(64))){
            ps.setBoolean(1, rb.isQuestionone());
            ps.setString(2, username);
            ps.setLong(3, rb.getAssignmentid());
            ps.setInt(4, 64);
            ps.addBatch();
        }
       else{
           psinsert.setBoolean(1, rb.isQuestionone());
           psinsert.setString(2, username);
           psinsert.setLong(3, rb.getAssignmentid());
           psinsert.setInt(4, 64);
           psinsert.addBatch();
       }
       
      if(ratingTypes.containsKey(new Integer(65))){
          ps.setBoolean(1, rb.isQuestiontwo());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 65);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestiontwo());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 65);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(55))){
          ps.setBoolean(1, rb.isQuestionthree());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 55);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionthree());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 55);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(56))){
          ps.setBoolean(1, rb.isQuestionfour());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 56);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionfour());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 56);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(57))){
          ps.setBoolean(1, rb.isQuestionfive());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 57);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionfive());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 57);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(58))){
          ps.setBoolean(1, rb.isQuestionsix());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 58);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionsix());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 58);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(59))){
          ps.setBoolean(1, rb.isQuestionseven());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 59);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionseven());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 59);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(60))){
          ps.setBoolean(1, rb.isQuestioneight());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 60);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestioneight());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 60);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(61))){
          ps.setBoolean(1, rb.isQuestionnine());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 61);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionnine());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 61);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(62))){
          ps.setBoolean(1, rb.isQuestionten());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 62);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestionten());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 62);
         psinsert.addBatch();
      }
      
      if(ratingTypes.containsKey(new Integer(63))){
          ps.setBoolean(1, rb.isQuestioneleven());
          ps.setString(2, username);
          ps.setLong(3, rb.getAssignmentid());
          ps.setInt(4, 63);
          ps.addBatch();
      }
      else{
         psinsert.setBoolean(1, rb.isQuestioneleven());
         psinsert.setString(2, username);
         psinsert.setLong(3, rb.getAssignmentid());
         psinsert.setInt(4, 63);
         psinsert.addBatch();
      }    
          
        //new 2/24/16 for question 12
        if(ratingTypes.containsKey(new Integer(66))){
            ps.setBoolean(1, rb.isQuestiontwelve());
            ps.setString(2, username);
            ps.setLong(3, rb.getAssignmentid());
            ps.setInt(4, 66);
            ps.addBatch();
        }
        else{
           psinsert.setBoolean(1, rb.isQuestiontwelve());
           psinsert.setString(2, username);
           psinsert.setLong(3, rb.getAssignmentid());
           psinsert.setInt(4, 66);
           psinsert.addBatch();
        }    
    
      ps.executeBatch();
      psinsert.executeBatch();
        
      } catch(Exception e){
        System.err.println("error updateCnReviewerAnswer() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(psinsert);
        Close(conn);
        Close(rs);
      }
      return outcome;
    }
    
    
    public ArrayList getFinalExpensesForCode(long grantid, int fscode)
    {
         ArrayList allexps=new ArrayList();  
        
         try {    
            conn = initializeConn();       
            
            String filtersql = "select * from GRANT_EXPENDITURES where " + 
            " gra_id=? and et_id=?";
            String allsql = "select * from GRANT_EXPENDITURES where " + 
            " gra_id=?";
            String allgrantfundsql = "select * from GRANT_EXPENDITURES where "+
            " gra_id=? and grant_fund=1";
            
            if(fscode==0){//get all codes
                ps = conn.prepareStatement(allsql); 
                ps.setLong(1, grantid);
            }
            else if(fscode==-1){//get all codes; only where grant_fund=true (for FS10f form)
                ps = conn.prepareStatement(allgrantfundsql);
                ps.setLong(1, grantid);        
            }
            else{//get selected FS code
                ps = conn.prepareStatement(filtersql); 
                ps.setLong(1, grantid);
                ps.setInt(2, fscode);
            }         
            rs = ps.executeQuery();
            
            while(rs.next()){
                FinalExpenseBean fb = new FinalExpenseBean();
                fb.setId(rs.getLong("ID"));
                fb.setGrantId(grantid);
                fb.setExpenseId(rs.getLong("et_id"));
                fb.setJournalEntry(rs.getString("journal_entry"));
                fb.setProvider(rs.getString("provider"));
                fb.setDescription(rs.getString("DESCRIPTION"));
                fb.setGrantFundYn(rs.getBoolean("grant_fund"));
                fb.setBeginDate(rs.getDate("begin_date"));
                fb.setExpAmount(rs.getInt("expenditure"));
                fb.setExpAmountStr(rs.getString("expenditure"));
                
                if(fb.getExpAmountStr()!=null && !fb.getExpAmountStr().equals("")){
                  long amtrec = Long.parseLong(fb.getExpAmountStr());
                  fb.setExpAmountStr(numF.format(amtrec));
                }
                                
                String expDate =null;
                if(rs.getDate("begin_date")!=null)
                   expDate = formatter.format(rs.getDate("begin_date"));                
                fb.setBeginDateStr(expDate);
                allexps.add(fb);
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getFinalExpensesForCode() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return allexps;
    }
    
    
    public TotalsBean getFinalExpenseTotalsForCode(long grantid, int fscode)
    {
         TotalsBean tb = new TotalsBean();
        
         try {    
            conn = initializeConn();       
            
            ps = conn.prepareStatement("select sum(expenditure) as totexp, grant_fund from "+
            " grant_expenditures where gra_id=? and et_id=? group by grant_fund"); 
            ps.setLong(1, grantid);
            ps.setInt(2, fscode);
                  
            rs = ps.executeQuery();            
            while(rs.next()){
                boolean grantFundYn = rs.getBoolean("grant_fund");
                
                if(grantFundYn){
                    tb.setGrantFundTotal(rs.getLong("totexp"));
                }
                else{
                    tb.setMatchFundTotal(rs.getLong("totexp"));
                }
            }
            long sumtotal = tb.getGrantFundTotal()+tb.getMatchFundTotal();
            tb.setTotalExpenses(sumtotal);
                                                  
         } catch (Exception ex){
            System.err.println("error getFinalExpenseTotalsForCode() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return tb;
    }
    
    
    public TotalsBean getFinalExpenseTotalsAllCodes(long grantid)
    {
         TotalsBean tb = new TotalsBean();
        
         try {    
            conn = initializeConn();       
            
            ps = conn.prepareStatement("select sum(expenditure) as totexp, grant_fund, et_id "+
            " from grant_expenditures where gra_id=? group by grant_fund, et_id"); 
            ps.setLong(1, grantid);
                             
            rs = ps.executeQuery();            
            while(rs.next()){
                boolean grantFundYn = rs.getBoolean("grant_fund");
                int expType = rs.getInt("et_id");
                
                switch(expType){
                    case 20:
                        if(grantFundYn)
                            tb.setGrantFundEquip(rs.getLong("totexp"));
                        else
                            tb.setMatchFundEquip(rs.getLong("totexp"));
                        break;
                    case 40:
                        if(grantFundYn)
                            tb.setGrantFundPurchased(rs.getLong("totexp"));
                        else
                            tb.setMatchFundPurchased(rs.getLong("totexp"));
                        break;
                    case 45:
                        if(grantFundYn)
                            tb.setGrantFundSupply(rs.getLong("totexp"));
                        else
                            tb.setMatchFundSupply(rs.getLong("totexp"));
                        break;                        
                }                
            }
            long granttotal = tb.getGrantFundEquip()+tb.getGrantFundPurchased()+tb.getGrantFundSupply();
            tb.setGrantFundTotal(granttotal);
            long matchtotal = tb.getMatchFundEquip()+tb.getMatchFundPurchased()+tb.getMatchFundSupply();
            tb.setMatchFundTotal(matchtotal);
            tb.setTotalExpenses(granttotal+matchtotal);
                                                  
         } catch (Exception ex){
            System.err.println("error getFinalExpenseTotalsAllCodes() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return tb;
    }
    
    
    public int insertFinalExpenseRecord(UserBean ub, long grantid, int fscode)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("INSERT INTO GRANT_EXPENDITURES( " +
            " ID, date_created, created_by, gra_id, ET_id) " +
            " values(GE1_SEQ.nextval, sysdate, ?, ?, ?)");        
            ps.setString(1, ub.getUserid());
            ps.setLong(2, grantid);
            ps.setInt(3, fscode);            
            outcome = ps.executeUpdate(); 
                                  
        } catch (Exception ex){
              System.err.println("error insertFinalExpenseRecord()  " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
    
    public int updateExpenseRecords(List allrecords, String userid)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();
       
            ps = conn.prepareStatement("UPDATE GRANT_EXPENDITURES SET " +
            " journal_entry=?, provider=?, expenditure=?, date_modified=sysdate, "+
            " modified_by=?, begin_date=to_date(?, 'mm/dd/yyyy'), grant_fund=? where id=?");      
            
            //get the number records
            int numRecs = 0;
            if(allrecords !=null)
              numRecs = allrecords.size();
                         
            //loop on each records and update db
            for(int i=0;i<numRecs; i++)
            {
                FinalExpenseBean fb = (FinalExpenseBean) allrecords.get(i);
                
                String amtexp = fb.getExpAmountStr();
                int exp_int = 0;
                if(amtexp!= null && !amtexp.equals(""))
                  exp_int = dbh.parseCurrencyAmtNoDecimal(amtexp);
                  
                ps.setString(1, fb.getJournalEntry());
                ps.setString(2, fb.getProvider());
                ps.setInt(3, exp_int);  
                ps.setString(4, userid);
                ps.setString(5, fb.getBeginDateStr());
                ps.setBoolean(6, fb.isGrantFundYn());
                ps.setLong(7, fb.getId());
                outcome = ps.executeUpdate(); 
            }
                                  
        } catch (Exception ex){
              System.err.println("error updateExpenseRecords()  " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
    public String getExpenseDescription(long id)
    {
         String desc = "";
         try {    
            conn = initializeConn();       
            ps = conn.prepareStatement("select provider from GRANT_EXPENDITURES where " + 
            " id=?");   
            ps.setLong(1, id);
            rs = ps.executeQuery();            
            while(rs.next()){
                desc = rs.getString("provider");
            }            
                                      
         } catch (Exception ex){
            System.err.println("error getExpenseDescription() " + ex.toString());
         }
         finally{
            Close(conn);
            Close(rs);
            Close(ps);
         }   
         return desc;
    }
    
    
    public int deleteExpenseRecord(long id)
    {
        int outcome =0;           
        try {    
            conn = initializeConn();       
            ps = conn.prepareStatement("delete from GRANT_EXPENDITURES where ID=?");   
            ps.setLong(1, id);
            outcome = ps.executeUpdate();
                                                
        } catch (Exception ex){
              System.err.println("error deleteExpenseRecord() " + ex.toString());
        }
        finally{
            Close(conn);
            Close(rs);
            Close(ps);
        }   
        return outcome;
    }
    
    
    public FinalExpenseBean getCompletionFormAmounts(long grantid, long buildingGrantId)
    {
       FinalExpenseBean fb = new FinalExpenseBean();
       try{           
       
            fb.setGrantId(grantid);
            fb.setBuildingGrantId(buildingGrantId);
            
            //get total project cost and grant amount for completion form
            conn = initializeConn();
            ps = conn.prepareStatement("select * from building_funds where gb_id=? and "+
            " (fs_id=15 or fs_id=16)");
            ps.setLong(1, buildingGrantId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                int fundsource=rs.getInt("fs_id");
                                
                if(fundsource==15){
                    fb.setGrantAmountStr(rs.getString("amount_received"));
                    fb.setGrantAmount(rs.getLong("amount_received"));
                    fb.setGrantAmountFundId(rs.getLong("id"));
                                        
                    if(fb.getGrantAmountStr()!=null && !fb.getGrantAmountStr().equals("")){
                       long totcost = Long.parseLong(fb.getGrantAmountStr());
                       fb.setGrantAmountStr(numF.format(totcost));
                    }
                }
                else if(fundsource==16){
                    fb.setProjectCostStr(rs.getString("amount_received"));
                    fb.setProjectCost(rs.getInt("amount_received"));
                    fb.setProjectCostFundId(rs.getLong("id"));
                     
                    if(fb.getProjectCostStr()!=null && !fb.getProjectCostStr().equals("")){
                       long reqcost = Long.parseLong(fb.getProjectCostStr());
                       fb.setProjectCostStr(numF.format(reqcost));
                    }
                }
            }
            
        }catch (Exception ex){
          System.err.println("error getCompletionFormAmounts() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return fb;    
    }    
    
    
    public void handleSaveCompletionAmounts(FinalExpenseBean fb, UserBean ub)
    {
         PreparedStatement psupdate = null;
              
         try{                   
            conn = initializeConn();
            ps = conn.prepareStatement("insert into BUILDING_FUNDS (ID, AMOUNT_RECEIVED, "+
            " GB_ID, FS_ID, DATE_CREATED, CREATED_BY) values (BF_SEQ.NEXTVAL, ?, ?,?, sysdate, ?)");
            
            psupdate = conn.prepareStatement("update BUILDING_FUNDS set AMOUNT_RECEIVED=?, "+
            " DATE_MODIFIED=sysdate, MODIFIED_BY=? where ID=?");
            
            if(fb.getGrantAmountStr()!=null && !fb.getGrantAmountStr().equals("")){
                
                if(fb.getGrantAmountFundId()==0){//insert               
                   //parse out an $ or decimals 
                   long totcost_num= dbh.parseLongAmtNoDecimal(fb.getGrantAmountStr());
                   ps.setLong(1, totcost_num);
                   ps.setLong(2, fb.getBuildingGrantId());
                   ps.setInt(3, 15);//FS_ID
                   ps.setString(4, ub.getUserid());
                   ps.addBatch();                 
                }
                else{//update 
                   //parse out an $ or decimals 
                   long totcost_num= dbh.parseLongAmtNoDecimal(fb.getGrantAmountStr());
                   psupdate.setLong(1, totcost_num);
                   psupdate.setString(2, ub.getUserid());
                   psupdate.setLong(3, fb.getGrantAmountFundId());
                   psupdate.addBatch();
                }
            }
            
            if(fb.getProjectCostStr()!=null && !fb.getProjectCostStr().equals("")){
            
                if(fb.getProjectCostFundId()==0){//insert                  
                   //parse out an $ or decimals
                   long reqcost_num= dbh.parseLongAmtNoDecimal(fb.getProjectCostStr());
                   ps.setLong(1, reqcost_num);
                   ps.setLong(2, fb.getBuildingGrantId());
                   ps.setInt(3, 16);//FS_ID
                   ps.setString(4, ub.getUserid());
                   ps.addBatch();                 
                }
                else{//update 
                   //parse out an $ or decimals
                   long reqcost_num= dbh.parseLongAmtNoDecimal(fb.getProjectCostStr());
                   psupdate.setLong(1, reqcost_num);
                   psupdate.setString(2, ub.getUserid());
                   psupdate.setLong(3, fb.getProjectCostFundId());
                   psupdate.addBatch();
                }         
            }
            
            ps.executeBatch();
            psupdate.executeBatch();
            
         }catch(Exception e){
          System.err.println("error handleSaveCompletionAmounts() " + e.getMessage().toString());
         }
         finally{
             Close(ps);
             Close(conn);
             Close(rs);
             Close(psupdate);
         }      
    }
    
    
    
    
    
  public int updateGroundDisturbanceFlag(boolean grounddisturb, long grantid, UserBean ub)
  {
       int outcome =0;           
       try {    
          conn = initializeConn();
     
          ps = conn.prepareStatement("update GRANTS set PAID_IN_FULL_YN=?,  "+
          " DATE_MODIFIED=sysdate, MODIFIED_BY=?   where ID=?");        
          ps.setBoolean(1, grounddisturb);
          ps.setString(2, ub.getUserid());
          ps.setLong(3, grantid);
          outcome = ps.executeUpdate(); 
                                    
       } catch (Exception ex){
          System.err.println("error updateGroundDisturbanceFlag() " + ex.toString());
       }
       finally{
          Close(conn);
          Close(rs);
          Close(ps);
       }   
       return outcome;
  }
    
}
