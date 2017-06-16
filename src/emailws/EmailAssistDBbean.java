package emailws;

import construction.ConstructionDBbean;

import java.io.InputStream;

import java.net.URL;

import java.net.URLConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mypackage.AppDatesDBbean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.DbName;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.ReviewerEmailBean;
import mypackage.UserBean;

import sedems.AttachmentSend;
import sedems.EmailLogQuery;
import sedems.EmailLogSend;
import sedems.EmailVarSend;
import sedems.MessageVarQuery;
import sedems.RecipientSend;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class EmailAssistDBbean 
{
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  
  public EmailAssistDBbean()
  {
  }
  
  
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
  
  public String formatProjectNumber(int fccode, int fycode, long projseq)
  {
    String projNum="";
    try{    
      if(fccode==5 || fccode==6 || fccode==7)   
        projNum = "030" + fccode + "-" + fycode + "-" + projseq;  
      else if(fccode==80)
          projNum = "05" + fccode + "-" + fycode + "-" + projseq;
      else
        projNum = "03" + fccode + "-" + fycode + "-" + projseq;  
      
    }catch(Exception e){
      System.out.println("error formatProjectNumber "+e.getMessage().toString());
    }
    return projNum;
  }
  
  

  public ArrayList getReviewerRecipGroup(String fundCodeList)
  {  
    ArrayList allrecs = new ArrayList();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select reviewers.id, fname, lname, user_id, contact_value from reviewers, "+
      " contacts where reviewers.id = contacts.rev_id and active='Y' and contact_type_code=3 and "+
      " reviewers.id in (select rev_id from reviewer_programs where fc_code in ("+fundCodeList+") ) ");
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
          OfficerBean pm = new OfficerBean();
          pm.setStaffID(rs.getLong("id"));
          pm.setFname(rs.getString("fname"));
          pm.setLname(rs.getString("lname"));
          pm.setEmail(rs.getString("contact_value"));
          pm.setUserID(rs.getString("user_id"));
        gb.setProjectManager(pm);
        gb.setEmail(pm.getEmail());
        gb.setName(pm.getFname() + " "+ pm.getLname());
        gb.setUserid(pm.getUserID());
        gb.setStaffid(pm.getStaffID());
        allrecs.add(gb);
      }        
      
    }
    catch(Exception e){
      System.err.println("error getReviewerRecipGroup() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return allrecs;
  }
  
  
  
    public ArrayList getReviewerAssignGroup(int fccode, int fycode)
    {  
      ArrayList allrecs = new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select reviewers.id, fname, lname, user_id, contact_value, name from reviewers, " + 
        "contacts, panel_reviewers, ldgrants.panels where reviewers.id = contacts.rev_id and " + 
        "reviewers.id=panel_reviewers.rev_id and panel_reviewers.pan_id=panels.id " + 
        "and active='Y' and contact_type_code=3 and panels.fy_code=? and " + 
        "reviewers.id in (select rev_id from reviewer_programs where fc_code =? ) ");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
            OfficerBean pm = new OfficerBean();
            pm.setStaffID(rs.getLong("id"));
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
            pm.setUserID(rs.getString("user_id"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          gb.setUserid(pm.getUserID());
          gb.setStaffid(pm.getStaffID());
          gb.setTitle(rs.getString("name"));
          allrecs.add(gb);
        }        
        
      }
      catch(Exception e){
        System.err.println("error getReviewerAssignGroup() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
    
    

  public ArrayList getRecipientGroup(int fycode, int fccode, String approvalType)
  {  
    ArrayList allrecs = new ArrayList();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
      "lname, contact_value, initcap(popular_name) as popular_name from grants, project_managers, "+
      " contacts, co_institutions left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id "+
      " where grants.prm_id=project_managers.id and project_managers.id=contacts.prm_id " + 
      " and grants.id=co_institutions.gra_id and fy_code=? and fc_code=? and is_lead='Y' and "+
      " contact_type_code=3 and grants.id in (select gra_id from approvals where approval_type=?) order by popular_name");
      ps.setInt(1, fycode);
      ps.setInt(2, fccode);
      ps.setString(3, approvalType);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("id"));
        gb.setTitle(rs.getString("name"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));
          OfficerBean pm = new OfficerBean();
          pm.setFname(rs.getString("fname"));
          pm.setLname(rs.getString("lname"));
          pm.setEmail(rs.getString("contact_value"));
        gb.setProjectManager(pm);
        gb.setEmail(pm.getEmail());
        gb.setName(pm.getFname() + " "+ pm.getLname());
        allrecs.add(gb);
      }        
      
    }
    catch(Exception e){
      System.err.println("error getRecipientGroup() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return allrecs;
  }
  
  /**
     * method will get the proj manager of each construction grant approved/denied 
     * to create a recipient list for sending email
     * @param fycode
     * @param awardType
     * @return
     */
    public HashMap getRecipientGroupConstruction(int fycode, String awardType)
    {  
      ArrayList allrecs = new ArrayList();
      HashMap results = new HashMap();
      try{
        conn = initializeConn();
        String version="", apprType="";
        
        if(awardType!=null && awardType.equalsIgnoreCase("approve")){
            version="Initial";
            apprType="Approve";
        }
        else{
            version="All";
            apprType="Denied";
        }
        
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
        " lname, contact_value, initcap(sed_institutions.popular_name) as popular_name, building_name, " + 
        " initcap(sysinst.popular_name) as system_name from grants, " + 
        " system_grant_assigns sga, grant_buildings, sed_buildings, ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on " + 
        " lsm.inst_id_has=sysinst.inst_id, project_managers, " + 
        " contacts, co_institutions " + 
        " left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        " where grants.prm_id=project_managers.id and project_managers.id=contacts.prm_id " + 
        " and grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
        " and grants.id=grant_buildings.gra_id and grant_buildings.bldg_id=sed_buildings.bldg_id " + 
        " and grants.id=co_institutions.gra_id and fy_code=? and fc_code=86 and is_lead='Y' and " + 
        " contact_type_code=3 and grants.id in (select gra_id from approvals where approval_type=? " + 
        " and version=?) order by system_name, popular_name");
        ps.setInt(1, fycode);
        ps.setString(2, apprType);
        ps.setString(3, version);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
          gb.setBuildingName(rs.getString("building_name"));
          gb.setSystemName(rs.getString("system_name"));
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          
          allrecs.add(gb);//arraylist
          results.put(new Long(gb.getGrantid()), gb);//hashmap
        }        
        
      }catch(Exception e){
        System.err.println("error getRecipientGroupConstruction() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return results;
    }
  
  /**
     * method will get the email recipient group for construction award/deny emails - of
     * the PLS director (from sedref) of each grant that was approved/denied. the method above
     * 'getRecipientGroupConstruction()' gets the proj manager of each grant appr/denied.
     * modified 4/28/14 to get sedref admin_pos of additional contact; then ceo (if it exists)
     * per LW
     * @param fycode
     * @param awardType
     * @param allrecs
     * @return
     */
    public HashMap getRecipientGroupConstructionPLS(int fycode, String awardType, HashMap allrecs)
    {        
      try{
        conn = initializeConn();
        String version="", apprType="";
        
        if(awardType!=null && awardType.equalsIgnoreCase("approve")){
            version="Initial";
            apprType="Approve";
        }else{
            version="All";
            apprType="Denied";
        }
        //modified to get 'additional contact' and 'ceo' separately
        ps = conn.prepareStatement("select  grants.id, name, proj_seq_num, grants.fy_code, grants.fc_code, " + 
        "initcap(sed_institutions.popular_name) as popular_name, " + 
        " initcap(sysinst.popular_name) as system_name, fname, lname, contact_value, lsm_id as mapping_id, " + 
        " sga.id as assignid,  co_institutions.inst_id,  " + 
        " inst_id_has from grants, system_grant_assigns sga, " + 
        " ldstateaid.library_system_mappings lsm left join sed_institutions sysinst on " + 
        " lsm.inst_id_has=sysinst.inst_id " + 
        " left join sed_admin_positions sap on sysinst.inst_id=sap.inst_id " + 
        " left join sed_contact_info sco on sap.admin_pos_id=sco.admin_pos_id, " + 
        " co_institutions  " + 
        " left join sed_institutions on co_institutions.inst_id=sed_institutions.inst_id " + 
        " where grants.id=co_institutions.gra_id and grants.id=sga.gra_id  and sga.lsm_id=lsm.id " + 
        " and grants.fy_code=? and grants.fc_code=86 and is_lead='Y' and (admin_pos_type_code=?) " + 
        " and grants.id in (select gra_id from approvals where approval_type=? " + 
        " and version=?)  and contact_type_code=3 " + 
        " order by system_name, popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, 4);//additional contact
        ps.setString(3, apprType);
        ps.setString(4, version);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
                   
          long grantid = rs.getLong("id");
         
          //if graid already exists in library list; add PLS director info to record
          if(allrecs.containsKey(grantid)){
              GrantBean gb = (GrantBean) allrecs.get(grantid);
              
              //add the pls director info
              gb.setPlsEmail(rs.getString("contact_value"));
              gb.setPlsName(rs.getString("fname") +" "+ rs.getString("lname"));
          }
        }
          
        
        //now get ceo type (if inst has both types; addcontact will be overridden by ceo)         
        ps.setInt(1, fycode);
        ps.setInt(2, 1);//ceo
        ps.setString(3, apprType);
        ps.setString(4, version);
        rs = ps.executeQuery();        
        while(rs.next())
        {
                   
          long grantid = rs.getLong("id");
         
          //if graid already exists in library list; add PLS director info to record
          if(allrecs.containsKey(grantid)){
              GrantBean gb = (GrantBean) allrecs.get(grantid);
              
              //add the pls director info
              gb.setPlsEmail(rs.getString("contact_value"));
              gb.setPlsName(rs.getString("fname") +" "+ rs.getString("lname"));
          }
        }
        
         
      }catch(Exception e){
        System.err.println("error getRecipientGroupConstructionPLS() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
    
  
    public ArrayList getRecipientAddContact(int fycode, int fccode, String approvalType, ArrayList allrecs)
    {        
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
        "lname, contact_value, initcap(popular_name) as popular_name from grants, grant_staffs, " + 
        " contacts, co_institutions left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        " where grants.id=grant_staffs.gra_id and grant_staffs.id=contacts.gs_id " + 
        " and grants.id=co_institutions.gra_id and fy_code=? and fc_code=? and is_lead='Y' and " + 
        " contact_type_code=3 and grants.id in (select gra_id from approvals " + 
        " where approval_type=?) order by popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        ps.setString(3, approvalType);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          allrecs.add(gb);
        }        
        
      }
      catch(Exception e){
        System.err.println("error getRecipientAddContact() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
  
    public ArrayList getRecipientGroupRMO(int fycode, int fccode, String approvalType, ArrayList allrecs)
    {  
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
        " lname, contact_value, initcap(popular_name) as popular_name from grants, grant_staffs, "+
        " contacts, co_institutions left join sed_institutions on sed_institutions.inst_id="+
        " co_institutions.inst_id where grants.id=grant_staffs.gra_id and grant_staffs.id=contacts.gs_id " + 
        "and grants.id = co_institutions.gra_id and fy_code=? and fc_code=? and is_lead='Y' and "+
        " st1_id=1 and contact_type_code=3 and grants.id in (select gra_id from approvals " + 
        "where approval_type=?)");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        ps.setString(3, approvalType);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          allrecs.add(gb);
        }                
      }
      catch(Exception e){
        System.err.println("error getRecipientGroupRMO() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
  
  
    public ArrayList getRecipientPresOfficer(int fycode, int fccode, String approvalType)
    {  
      ArrayList allrecs = new ArrayList();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num,  "+
        "initcap(popular_name) as popular_name from grants, co_institutions left join "+
        " sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
        " where  grants.id=co_institutions.gra_id and fy_code=? and fc_code=? and is_lead='Y' and " + 
        " grants.id in (select gra_id from approvals where approval_type=?)" + 
        " order by popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        ps.setString(3, approvalType);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
          allrecs.add(gb);
        }        
        
        
        ps = conn.prepareStatement("select initcap(fname) as fname, initcap(lname) as lname, contact_value " + 
        "  from SED_ADMIN_POSITIONS, SED_CONTACT_INFO where " + 
        " SED_ADMIN_POSITIONS.admin_pos_id = SED_CONTACT_INFO.admin_pos_id  and " + 
        " CONTACT_TYPE_CODE=3 and SED_ADMIN_POSITIONS.ADMIN_POS_ID in " + 
        " (select ADMIN_POS_ID from GRANT_ADMINS where gra_id=? and END_DATE is null" + 
        "  and TITLE='Preservation Officer')");
        
        for(int i=0; i<allrecs.size(); i++)
        {
            GrantBean g = (GrantBean) allrecs.get(i);
            ps.setLong(1, g.getGrantid());
            rs = ps.executeQuery();            
            while(rs.next())
            {
                OfficerBean pm = new OfficerBean();
                pm.setFname(rs.getString("fname"));
                pm.setLname(rs.getString("lname"));
                pm.setEmail(rs.getString("contact_value"));
                
                g.setProjectManager(pm);
                g.setEmail(pm.getEmail());
                g.setName(pm.getFname() + " "+ pm.getLname());
            }
        }
        
      }
      catch(Exception e){
        System.err.println("error getRecipientPresOfficer() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
    
        
    
    
  public ArrayList getRecipientSedrefCeo(int fycode, int fccode, String approvalType)
  {  
    ArrayList allrecs = new ArrayList();
    try{
      conn = initializeConn();
    
      ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, co_institutions.inst_id, "+
      "initcap(popular_name) as popular_name from grants, co_institutions left join "+
      " sed_institutions on sed_institutions.inst_id=co_institutions.inst_id " + 
      " where  grants.id=co_institutions.gra_id and fy_code=? and fc_code=? and is_lead='Y' and " + 
      " grants.id in (select gra_id from approvals where approval_type=?)" + 
      " order by popular_name");
      ps.setInt(1, fycode);
      ps.setInt(2, fccode);
      ps.setString(3, approvalType);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("id"));
        gb.setTitle(rs.getString("name"));
        gb.setFccode(rs.getInt("fc_code"));
        gb.setFycode(rs.getInt("fy_code"));
        gb.setProjseqnum(rs.getLong("proj_seq_num"));
        gb.setInstName(rs.getString("popular_name"));
        gb.setInstID(rs.getLong("inst_id"));
          
        if(gb.getFccode()==20){  
        	//per BL; need 4 digit proj num to be same every year for stateaid
        	DBHandler dbh = new DBHandler();
            gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
        }
          
        allrecs.add(gb);
      }        
      
      
      ps = conn.prepareStatement("select initcap(fname) as fname, initcap(lname) as lname, contact_value " + 
      "  from SED_ADMIN_POSITIONS, SED_CONTACT_INFO where " + 
      " SED_ADMIN_POSITIONS.admin_pos_id = SED_CONTACT_INFO.admin_pos_id  and " + 
      " CONTACT_TYPE_CODE=3 and SED_ADMIN_POSITIONS.ADMIN_POS_ID in " + 
      " (select ADMIN_POS_ID from GRANT_ADMINS where gra_id=? and END_DATE is null" + 
      "  and TITLE='Preservation Officer')");
      
      for(int i=0; i<allrecs.size(); i++)
      {
          GrantBean g = (GrantBean) allrecs.get(i);
          ps.setLong(1, g.getGrantid());
          rs = ps.executeQuery();            
          while(rs.next())
          {
              OfficerBean pm = new OfficerBean();
              pm.setFname(rs.getString("fname"));
              pm.setLname(rs.getString("lname"));
              pm.setEmail(rs.getString("contact_value"));
              
              g.setProjectManager(pm);
              g.setEmail(pm.getEmail());
              g.setName(pm.getFname() + " "+ pm.getLname());
          }
          
          //TEMP SINCE CJH/NYHS @ PREF DO NOT HAVE CONTACT INFO
          /*OfficerBean pm = new OfficerBean();
          pm.setFname("Bob"+i);
          pm.setLname("Doe"+i);
          pm.setEmail("stefanie.husak@nysed.gov");
          g.setProjectManager(pm);
          g.setEmail(pm.getEmail());
          g.setName(pm.getFname() + " "+ pm.getLname());*/
      }
      
    }
    catch(Exception e){
      System.err.println("error getRecipientPresOfficer() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
    return allrecs;
  }
    
    
    public Vector getRecipientsFyFc(int fycode, int fccode)
    {  
      Vector allrecs = new Vector();
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
        " lname, contact_value, initcap(popular_name) as popular_name " + 
        "  from grants, project_managers, contacts, co_institutions " + 
        " left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id where  " + 
        " grants.prm_id=project_managers.id and project_managers.id=contacts.prm_id and " + 
        " co_institutions.gra_id=grants.id and fy_code=? and fc_code=? and " + 
        " contact_type_code=3 and is_lead='Y' and grants.id in (select gra_id from grant_submissions " + 
        " where version='Initial') order by popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          allrecs.add(gb);
        }        
        
      }
      catch(Exception e){
        System.err.println("error getRecipientsFyFc() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
    
    
    public Vector getRecipFyFcContacts(int fycode, int fccode, Vector allrecs)
    {  
      try{
        conn = initializeConn();
      
        ps = conn.prepareStatement("select grants.id, name, fc_code, fy_code, proj_seq_num, fname, " + 
        " lname, contact_value, initcap(popular_name) as popular_name " + 
        "  from grants, grant_staffs, contacts, co_institutions " + 
        " left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id where " + 
        " grants.id=grant_staffs.gra_id and grant_staffs.id=contacts.gs_id and " + 
        " co_institutions.gra_id=grants.id and fy_code=? and fc_code=? and " + 
        " contact_type_code=3 and is_lead='Y' and grants.id in (select gra_id from grant_submissions " + 
        " where version='Initial') order by popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, fccode);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          GrantBean gb = new GrantBean();
          gb.setGrantid(rs.getLong("id"));
          gb.setTitle(rs.getString("name"));
          gb.setFccode(rs.getInt("fc_code"));
          gb.setFycode(rs.getInt("fy_code"));
          gb.setProjseqnum(rs.getLong("proj_seq_num"));
          gb.setInstName(rs.getString("popular_name"));
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));
            pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setEmail(pm.getEmail());
          gb.setName(pm.getFname() + " "+ pm.getLname());
          allrecs.add(gb);
        }        
        
      }catch(Exception e){
        System.err.println("error getRecipFyFcContacts() "+ e.getMessage().toString()); }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return allrecs;
    }
    
    
    private String parseRecipientsComma(String r)
    {        
        try{
            if(r!=null && !r.equals(""))
                r = r.replace(';', ',');
        }catch(Exception e){System.out.println("error parseRecipientsComma "+e.getMessage().toString());}
        return r;
    }
  
  public void createLogsVarsForRecipients(List allRecipients, String cc, int wtid, UserBean userb)
  {
    Vector myEmailLogs=new Vector();
    
    try{
        //split the cc emails into comma separated (in case they are semicolon separated)
        cc = parseRecipientsComma(cc);
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
        //get the wt details
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
        
        DbName dbn = new DbName();
        
        //create email logs and vars for each selected reicpient
        for(int j=0; j<allRecipients.size(); j++)
        {
            GrantBean gb = (GrantBean) allRecipients.get(j);  
                                                  
            //create an emaillog including "to" address
            String too="";
            if(dbn.production==true)
                too = gb.getEmail();
            else
                too = "stefanie.husak@nysed.gov";
           EmailLogSend els=ez.createEmailLog(too, cc ,"", userb.getUserid(), wtid);
                         
            //create values for any vars
            MessageVarQuery[] mvqs=wtq.getMessagevariables();
            EmailVarSend evs=null;
            //loop thru each message var
            Vector vVars=new Vector();
            for(int i=0;i<mvqs.length;i++)
            {
               evs=new EmailVarSend();
               evs.setMessageVarId(mvqs[i].getId());
               if(mvqs[i].getName().equals("$$managerName")){
                 evs.setValue(gb.getName());
               }
               else if(mvqs[i].getName().equals("$$projectNum"))
               { 
                 //get the project number for the grant
                 String proposalnum = formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                 evs.setValue(proposalnum);
               }         
               else if(mvqs[i].getName().equals("$$grantId")) {
                   //get the grant id, sed to sedems
                   evs.setValue(String.valueOf(gb.getGrantid()));
               }
               else if(mvqs[i].getName().equals("$$program")) {
                   if(gb.getFccode()==40)
                     evs.setValue("Adult Literacy Library Services");
                   else if(gb.getFccode()==42)
                     evs.setValue("Family Literacy Library Services");
               }
               else if(mvqs[i].getName().equals("$$amtApproved")) {
                   BudgetDBHandler bdh = new BudgetDBHandler();
                   NumberFormat numF = NumberFormat.getIntegerInstance(Locale.US);  
                   int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);
                   evs.setValue("$"+ numF.format(amtappr));
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                
            //add to vector of all email logs
            myEmailLogs.add(els);      
          }
           
          //finally, insert all email logs under this wt
          ez.updateEmailRecipients(wtid,myEmailLogs);           
           
    }catch(Exception e){
      System.err.println("error createLogsVarsForRecipients() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
  }
   
  
    public boolean createLgRecipientsVars(List allRecipients, String cc, int fycode, int wtid, UserBean userb)
    {
      Vector myEmailLogs=new Vector();
      int totalfund=0;
      boolean attachDecision =false;
        
      try{
          DbName dbn = new DbName();
          NumberFormat numF = NumberFormat.getIntegerInstance(Locale.US); 
          BudgetDBHandler bdh = new BudgetDBHandler();
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          //get the wt details
          WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
          //get the message vars for template
          MessageVarQuery[] mvqs=wtq.getMessagevariables();
          
          //if this template needs a fund amount-calc it
           for(int i=0;i<mvqs.length;i++){
               if(mvqs[i].getName().equals("$$fundAmt")){
                AppDatesDBbean adb = new AppDatesDBbean();
                totalfund = adb.getTotalFund(fycode, 80);               
               }
           }
          
          //if this template is an award/deny email, it needs decision notes attached          
          if(wtq.getStandardTempId()==762 || wtq.getStandardTempId()==763)
                attachDecision=true;
                
          
          //create email logs and vars for each selected reicpient
          for(int j=0; j<allRecipients.size(); j++)
          {
              GrantBean gb = (GrantBean) allRecipients.get(j);  
              if(gb.isSendmail()){//if user checked off this record for sending email
                                
              //create an emaillog including "to" address
              String too="";
              if(dbn.production==true)
                  too = gb.getEmail();
              else
                  too = "stefanie.husak@nysed.gov";
             EmailLogSend els=ez.createEmailLog(too, cc ,"", userb.getUserid(), wtid);
                          
                       
              EmailVarSend evs=null;
              //loop thru each message var -create values for any vars   
              Vector vVars=new Vector();
              for(int i=0;i<mvqs.length;i++)
              {
                 evs=new EmailVarSend();
                 evs.setMessageVarId(mvqs[i].getId());
                 if(mvqs[i].getName().equals("$$managerName")){
                   evs.setValue(gb.getName());
                 }
                 else if(mvqs[i].getName().equals("$$reviewerName")){
                     evs.setValue(gb.getName());
                 }
                 else if(mvqs[i].getName().equals("$$username")) {
                     evs.setValue(gb.getUserid());
                 }
                 else if(mvqs[i].getName().equals("$$projectNum"))
                 { 
                   //get the project number for the grant
                   String proposalnum = formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                   evs.setValue(proposalnum);
                 }         
                 else if(mvqs[i].getName().equals("$$grantId")) {
                     evs.setValue(String.valueOf(gb.getGrantid()));
                 }
                 else if(mvqs[i].getName().equals("$$fundAmt")){
                     evs.setValue("$" + numF.format(totalfund));
                 }
                 else if(mvqs[i].getName().equals("$$amtApproved")) {                                           
                     int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);
                     evs.setValue("$"+ numF.format(amtappr));
                 }
                 else if(mvqs[i].getName().equals("$$panelName")){
                     evs.setValue(gb.getTitle());
                 }
                 vVars.add(evs);
              }
              EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
              els.setEmailVariables(vararray);//add var values to the email log
                  
              //add to vector of all email logs
              myEmailLogs.add(els);      
              }
            }
             
            //finally, insert all email logs under this wt
            ez.updateEmailRecipients(wtid,myEmailLogs);           
            
                         
      }catch(Exception e){
        System.err.println("error createLgRecipientsVars() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return attachDecision;
    }
    
    
    
    
    public boolean createCnRecipientsVars(List allRecipients, String cc, int wtid, UserBean userb)
    {
      Vector myEmailLogs=new Vector();
              
      try{
          DbName dbn = new DbName();
          NumberFormat numF = NumberFormat.getIntegerInstance(Locale.US); 
          ConstructionDBbean cdb = new ConstructionDBbean();
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());
          //get the wt details
          WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
          //get the message vars for template
          MessageVarQuery[] mvqs=wtq.getMessagevariables();
        
                   
          //create email logs and vars for each selected reicpient
          for(int j=0; j<allRecipients.size(); j++)
          {
              GrantBean gb = (GrantBean) allRecipients.get(j);  
              if(gb.isSendmail()){//if user checked off this record for sending email
                 
               String emailcc = cc; 
               //cc is a combo of PLS director & a typed-in cc address
               if(gb.getPlsEmail()!=null){
                   emailcc=emailcc + "," + gb.getPlsEmail();
               }
               
              //create an emaillog including "to" address
              String too="";
              if(dbn.production==true)
                  too = gb.getEmail();
              else
                  too = "stefanie.husak@nysed.gov";
             EmailLogSend els=ez.createEmailLog(too, emailcc ,"", userb.getUserid(), wtid);
                                     
                                     
              EmailVarSend evs=null;
              //loop thru each message var -create values for any vars   
              Vector vVars=new Vector();
              for(int i=0;i<mvqs.length;i++)
              {
                 evs=new EmailVarSend();
                 evs.setMessageVarId(mvqs[i].getId());
                 if(mvqs[i].getName().equals("$$managerName")){
                   evs.setValue(gb.getName());
                 }
                 else if(mvqs[i].getName().equals("$$projectNum"))
                 { 
                   //get the project number for the grant
                   String proposalnum = formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                   evs.setValue(proposalnum);
                 }         
                 else if(mvqs[i].getName().equals("$$grantId")) {
                     evs.setValue(String.valueOf(gb.getGrantid()));
                 }                
                 else if(mvqs[i].getName().equals("$$amtApproved")) {                                           
                     /* 4/4/12 for CN - MLT wants to use the system recommended amt
                      * instead of the budget tab amt approved
                      * int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);*/
                      long amtrec = cdb.getAmtRecommendedForGrant(gb.getGrantid());
                     evs.setValue("$"+ numF.format(amtrec));
                 }
                 else if(mvqs[i].getName().equals("$$projectTitle")){
                     evs.setValue(gb.getTitle());
                 }
                 else if(mvqs[i].getName().equals("$$instName")){
                     evs.setValue(gb.getInstName());
                 }
                 else if(mvqs[i].getName().equals("$$buildingName")){
                    evs.setValue(gb.getBuildingName());
                 }
                 vVars.add(evs);
              }
              EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
              els.setEmailVariables(vararray);//add var values to the email log
                  
              //add to vector of all email logs
              myEmailLogs.add(els);      
              }
            }
            
            //System.out.println("trying to hit sedems"); 
            //finally, insert all email logs under this wt
            ez.updateEmailRecipients(wtid,myEmailLogs);             
                         
      }catch(Exception e){
        System.err.println("error createCnRecipientsVars() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
      return true;
    }
    
    
    public void createCpRecipientsVars(List allRecipients, String cc, int wtid, UserBean userb)
    {
      Vector myEmailLogs=new Vector();
        
      try{
          DbName dbn = new DbName();
          NumberFormat numF = NumberFormat.getIntegerInstance(Locale.US); 
          BudgetDBHandler bdh = new BudgetDBHandler();
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          //split the cc emails into comma separated (in case they are semicolon separated)
          cc = parseRecipientsComma(cc);
          
          //get the wt details
          WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
          //get the message vars for template
          MessageVarQuery[] mvqs=wtq.getMessagevariables();
          
                   
          //create email logs and vars for each selected reicpient
          for(int j=0; j<allRecipients.size(); j++)
          {
              GrantBean gb = (GrantBean) allRecipients.get(j);  
              if(gb.isSendmail()){//if user checked off this record for sending email
                                
              //create an emaillog including "to" address
              String too="";
              if(dbn.production==true)
                  too = gb.getEmail();
              else
                  too = "stefanie.husak@nysed.gov";
             EmailLogSend els=ez.createEmailLog(too, cc ,"", userb.getUserid(), wtid);
                          
                       
              EmailVarSend evs=null;
              //loop thru each message var -create values for any vars   
              Vector vVars=new Vector();
              for(int i=0;i<mvqs.length;i++)
              {
                 evs=new EmailVarSend();
                 evs.setMessageVarId(mvqs[i].getId());
                 if(mvqs[i].getName().equals("$$managerName")){
                   evs.setValue(gb.getName());
                 }
                 else if(mvqs[i].getName().equals("$$projectNum"))
                 { 
                   //get the project number for the grant
                   String proposalnum = formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                   evs.setValue(proposalnum);
                 }         
                 else if(mvqs[i].getName().equals("$$grantId")) {
                     evs.setValue(String.valueOf(gb.getGrantid()));
                 }                 
                 else if(mvqs[i].getName().equals("$$amtApproved")) {                                           
                     int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);
                     evs.setValue("$"+ numF.format(amtappr));
                 }
                 vVars.add(evs);
              }
              EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
              els.setEmailVariables(vararray);//add var values to the email log
                  
              //add to vector of all email logs
              myEmailLogs.add(els);      
              }
            }
             
            //finally, insert all email logs under this wt
            ez.updateEmailRecipients(wtid,myEmailLogs);           
            
                         
      }catch(Exception e){
        System.err.println("error createCpRecipientsVars() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
    }
    
    
  public void createCpReviewerVars(List allRecipients, String cc, int wtid, UserBean userb)
  {
    Vector myEmailLogs=new Vector();
      
    try{
        DbName dbn = new DbName();
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
        //split the cc emails into comma separated (in case they are semicolon separated)
        cc = parseRecipientsComma(cc);
        
        //get the wt details
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
        //get the message vars for template
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        
                 
        //create email logs and vars for each selected reicpient
        for(int j=0; j<allRecipients.size(); j++)
        {
            GrantBean gb = (GrantBean) allRecipients.get(j);  
            if(gb.isSendmail()){//if user checked off this record for sending email
                              
            //create an emaillog including "to" address
            String too="";
            if(dbn.production==true)
                too = gb.getEmail();
            else
                too = "stefanie.husak@nysed.gov";
            EmailLogSend els=ez.createEmailLog(too, cc ,"", userb.getUserid(), wtid);
                        
                     
            EmailVarSend evs=null;
            //loop thru each message var -create values for any vars   
            Vector vVars=new Vector();
            for(int i=0;i<mvqs.length;i++)
            {
               evs=new EmailVarSend();
               evs.setMessageVarId(mvqs[i].getId());
               if(mvqs[i].getName().equals("$$reviewerName")){
                 evs.setValue(gb.getName());
               }
               else if(mvqs[i].getName().equals("$$program"))
               { 
                  evs.setValue(gb.getProgram());
               }         
               else if(mvqs[i].getName().equals("$$revId")) {
                   evs.setValue(String.valueOf(gb.getReviewerId()));
               }                 
               else if(mvqs[i].getName().equals("$$username")) {                                           
                   evs.setValue(gb.getUserid());
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                
            //add to vector of all email logs
            myEmailLogs.add(els);      
            }
          }
           
          //finally, insert all email logs under this wt
          ez.updateEmailRecipients(wtid,myEmailLogs);           
          
                       
    }catch(Exception e){
      System.err.println("error createCpReviewerVars() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
  }
    
    
    
  public void createCpReviewerAssignVars(List allRecipients, String cc, int wtid, UserBean userb, int fycode, int fccode, String duedate)
  {
    Vector myEmailLogs=new Vector();
    ReviewerEmailBean reb = new ReviewerEmailBean();
      
    try{
        DbName dbn = new DbName();
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
        //split the cc emails into comma separated (in case they are semicolon separated)
        cc = parseRecipientsComma(cc);
        
        //get the wt details
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
        //get the message vars for template
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        
                 
        //create email logs and vars for each selected reicpient
        for(int j=0; j<allRecipients.size(); j++)
        {
            GrantBean gb = (GrantBean) allRecipients.get(j);  
            if(gb.isSendmail()){//if user checked off this record for sending email
                              
            //get each project number assigned to this reviewer
            ArrayList grantnums = reb.getAssignedGrantInfo(gb.getReviewerId(), fycode , fccode);
            StringBuffer msg = new StringBuffer();              
            //print out all grant project nums they have been assigned
            for(int k=0; k<grantnums.size(); k++)
              msg.append((String) grantnums.get(k) + "\n");
            
                        
            //create an emaillog including "to" address
            String too="";
            if(dbn.production==true)
                too = gb.getEmail();
            else
                too = "stefanie.husak@nysed.gov";
            EmailLogSend els=ez.createEmailLog(too, cc ,"", userb.getUserid(), wtid);
                        
                     
            EmailVarSend evs=null;
            //loop thru each message var -create values for any vars   
            Vector vVars=new Vector();
            for(int i=0;i<mvqs.length;i++)
            {
               evs=new EmailVarSend();
               evs.setMessageVarId(mvqs[i].getId());
               if(mvqs[i].getName().equals("$$reviewerName")){
                 evs.setValue(gb.getName());
               }
               else if(mvqs[i].getName().equals("$$program"))
               { 
                  evs.setValue(gb.getProgram());
               }         
               else if(mvqs[i].getName().equals("$$revId")) {
                   evs.setValue(String.valueOf(gb.getReviewerId()));
               }                 
               else if(mvqs[i].getName().equals("$$username")) {                                           
                   evs.setValue(gb.getUserid());
               }
               else if(mvqs[i].getName().equals("$$projectNum")){ 
                 evs.setValue(msg.toString());
               }
               else if(mvqs[i].getName().equals("$$dueDate")){
                 evs.setValue(duedate);
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                
            //add to vector of all email logs
            myEmailLogs.add(els);      
            }
          }
           
          //finally, insert all email logs under this wt
          ez.updateEmailRecipients(wtid,myEmailLogs);           
          
                       
    }catch(Exception e){
      System.err.println("error createCpReviewerAssignVars() "+ e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }
  }
    
    
    public void handleDecisionAttachments(int wtid, UserBean ub)
    {
        try{
            //get the wt details
            SEDEMSEZClass ez=new SEDEMSEZClass(ub.getUserid(), ub.getArchivesAuthBean());
            WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wtid);
            
            //grab the attachment encoder
            AttachmentEncodeBean aeb = new AttachmentEncodeBean();
            
            //for testing - just see if we can add same attach to all emaillogs
            String url = "http://149.10.162.237:8993/ldgrants/lgrmif/decisionSummary.jsp?wtid="+wtid;
            URL u = new URL(url);
            URLConnection uc = u.openConnection();
            uc.connect();
            InputStream in = uc.getInputStream();
            
            System.out.println("got file");
            AttachmentSend as = new AttachmentSend();
            as.setName("test.html");
            as.setDocumentType("Content-type: text/html");
            as.setFileBase64(aeb.getBase64String(in));//convert attachment to mime
            as.setTemplateId(wtid);  
            as.setId(0);
            System.out.println("length "+uc.getContentLength());
            as.setDocumentSize(uc.getContentLength());
    
            in.close();
                        
            EmailLogQuery[] elq = wtq.getEmailLogs();            
            if(elq!=null && elq.length>0){
                for(int i=0; i<elq.length; i++){
                    
                    EmailLogQuery e = (EmailLogQuery) elq[i];
                    int elid = e.getId();
                    
                    //testing only
                     as.setEmailLogId(elid);
                     
                    //add attach to email log
                    ez.newAttachment(wtid, as);
                    
                     
                    //need to get a grantid from the variables or on jsp page??             
                }
            }
            
        }catch(Exception e){
        System.err.println("error handleDecisionAttachments() "+ e.getMessage().toString());
      }
      finally{
        Close(rs);
        Close(ps);
        Close(conn);
      }
    }
  
  
}
