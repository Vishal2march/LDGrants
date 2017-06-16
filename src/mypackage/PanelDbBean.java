package mypackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import mypackage.PanelBean;

public class PanelDbBean 
{
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
  
  public PanelDbBean()
  {
  }
  
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
  
  public int addPanel(PanelBean p, UserBean userb)
  {
    int outcome=0;
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("insert into ldgrants.panels (id, name, descr, amt_available, "+
      " fy_code, created_by, date_created, status) values (pan_seq.nextval, ?, ?, ?, ?, ?, sysdate,?)");
      ps.setString(1, p.getName());
      ps.setString(2, p.getDescr());
      ps.setInt(3, p.getAmtavailable());
      ps.setInt(4, p.getFycode());
      ps.setString(5, userb.getUserid());
      ps.setString(6, p.getStatus());
      outcome = ps.executeUpdate();
      
    }catch(Exception e){System.out.println("error addPanel() "+e.getMessage().toString());}
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    } 
    return outcome;
  } 
  
  
public int deletePanel(long panelid ) {
    int outcome =0;
    try{
        conn = initializeConn();
        ps = conn.prepareStatement("delete from ldgrants.panels where id=?");
        ps.setLong(1, panelid);
        outcome =ps.executeUpdate();        
    
    }catch(Exception e){System.out.println("error deletePanel() "+e.getMessage().toString());}
    finally{
        Close(conn);
        Close(ps);
        Close(rs);
    } 
    return outcome;
}
    
  
  public int updatePanel(PanelBean p, UserBean userb)
  {
    int outcome=0;
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("update ldgrants.panels set name=?, descr=?, amt_available=?, "+
      " fy_code=?, modified_by=?, date_modified=sysdate, status=? where id=?");
      ps.setString(1, p.getName());
      ps.setString(2, p.getDescr());
      ps.setInt(3, p.getAmtavailable());
      ps.setInt(4, p.getFycode());
      ps.setString(5, userb.getUserid());
      ps.setString(6, p.getStatus());
      ps.setLong(7, p.getId());
      outcome = ps.executeUpdate();
      
    }catch(Exception e){System.out.println("error updatePanel() "+e.getMessage().toString());}
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    } 
    return outcome;
  } 
  
  public ArrayList getPanelsForYear(int fycode)
  {
    ArrayList results = new ArrayList();
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from ldgrants.panels, fiscal_years where "+
      " ldgrants.panels.fy_code=fiscal_years.code and fy_code=? order by id");
      ps.setInt(1, fycode);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        SearchResultBean sb = new SearchResultBean();
        sb.setPanelid(rs.getLong("id"));
        sb.setAffiliation(rs.getString("name"));
        sb.setDescription(rs.getString("descr"));
        sb.setYear(rs.getString("description"));
        sb.setFycode(fycode);
        results.add(sb);
      }
      
    }catch(Exception e){System.out.println("error getPanelsForYear() "+e.getMessage().toString());}
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    } 
    return results;
  }
  
  public PanelBean getPanelRecord(long id)
  {
    PanelBean pb = new PanelBean();
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("select * from ldgrants.panels where id=?");
      ps.setLong(1, id);
      rs = ps.executeQuery();
      
      while(rs.next())
      {        
        pb.setId(rs.getLong("id"));
        pb.setName(rs.getString("name"));
        pb.setDescr(rs.getString("descr"));
        pb.setAmtavailable(rs.getInt("amt_available"));
        pb.setFycode(rs.getInt("fy_code"));
        pb.setStatus(rs.getString("status"));
      }
      
    }catch(Exception e){System.out.println("error getPanelRecord() "+e.getMessage().toString());}
    finally{
      Close(conn);
      Close(ps);
      Close(rs);
    } 
    return pb;
  }
  
  
  
  
    public ArrayList findReviewersByLname(String lname, long panelid, int fycode)
    {
      ArrayList allrev = new ArrayList();
      try{
        conn = initializeConn();
        ps = conn.prepareStatement("select reviewers.id, fname, lname, affiliation, reviewers.active from "+
        " reviewers, reviewer_programs where reviewers.id = reviewer_programs.rev_id " + 
        " and fc_code=80 and upper(lname) like upper(?)");
        ps.setString(1, lname.trim()+"%");
        rs = ps.executeQuery();
        
        while(rs.next())
        {        
          SearchResultBean sb = new SearchResultBean();
          sb.setRevid(rs.getLong("id"));
          sb.setFname(rs.getString("fname"));
          sb.setLname(rs.getString("lname"));
          sb.setAffiliation(rs.getString("affiliation"));
          sb.setActive(rs.getString("active"));
          sb.setPanelid(panelid);
            
          //new 7/11/14: allow assignment if active reviewer = true
          if(sb.getActive()!=null && sb.getActive().equalsIgnoreCase("Y"))
                sb.setAcceptreview(true);            
            
          allrev.add(sb);
        }
        
        
        //modified 7/11/14 per DM: make assignment independent of participation form; allow assign if active reviewer
        //this section no longer needed; it checked participation form response, and participation form no longer used
        /*
        ps = conn.prepareStatement("select num_accepted from grant_assign_maxes where fy_code=? "+
            " and rev_id=? and grant_program='lgrmif'");
        ps.setInt(1, fycode);        
        for(int i=0; i<allrev.size(); i++) 
        {
            SearchResultBean s = (SearchResultBean) allrev.get(i);
            ps.setLong(2, s.getRevid());
            rs = ps.executeQuery();
            while(rs.next()){
             int revreply = rs.getInt("num_accepted");
             s.setAcceptreview(revreply==1);
            }
        }*/
        
        
        
        //see if reviewer is already to assigned to any panel for this fy
        ps = conn.prepareStatement("select panel_reviewers.id as pr_id, ldgrants.panels.id as "+
        " pan_id, rev_id, fy_code from panel_reviewers, ldgrants.panels where " + 
        " panel_reviewers.pan_id=panels.id and fy_code=? and rev_id=? ");
        ps.setInt(1, fycode);
        for(int i=0; i<allrev.size(); i++) 
        {
            SearchResultBean s = (SearchResultBean) allrev.get(i);
            ps.setLong(2, s.getRevid());
            rs = ps.executeQuery();
            while(rs.next()) {
                s.setAssignpanel(true);
            }
        }        
        
      }catch(Exception e){System.out.println("error findReviewersByLname() "+e.getMessage().toString());}
      finally{
        Close(conn);
        Close(ps);
        Close(rs);
      } 
      return allrev;
    }
    
    
    
    public int addGrantToPanel(long grantid, long panelid, UserBean lduser ) {
        int outcome =0;
        try{
            conn = initializeConn();
            ps = conn.prepareStatement("insert into panel_grants (id, gra_id, pan_id, " + 
            " date_created, created_by) values (pg_seq.nextval, ?, ?, sysdate, ?)");
            ps.setLong(1, grantid);
            ps.setLong(2, panelid);
            ps.setString(3, lduser.getUserid());
            outcome =ps.executeUpdate();        
            
        }catch(Exception e){System.out.println("error addGrantToPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    
    public int addReviewerToPanel(int revid, long panelid, UserBean lduser ) {
        int outcome =0;
        try{
            conn = initializeConn();
            ps = conn.prepareStatement("insert into panel_reviewers (id, rev_id, pan_id, "+
            " date_created, created_by) values (pr_seq.nextval, ?,?, sysdate, ?)");
            ps.setInt(1, revid);
            ps.setLong(2, panelid);
            ps.setString(3, lduser.getUserid());
            outcome =ps.executeUpdate();        
        
        }catch(Exception e){System.out.println("error addReviewerToPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    public int deleteReviewerFromPanel(long panelreviewerid ) {
        int outcome =0;
        try{
            conn = initializeConn();
            ps = conn.prepareStatement("delete from panel_reviewers where id=?");
            ps.setLong(1, panelreviewerid);
            outcome =ps.executeUpdate();        
        
        }catch(Exception e){System.out.println("error deleteReviewerFromPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    public int deleteGrantFromPanel(long panelgrantid ) {
        int outcome =0;
        try{
            conn = initializeConn();
            ps = conn.prepareStatement("delete from panel_grants where id=?");
            ps.setLong(1, panelgrantid);
            outcome =ps.executeUpdate();        
            
        }catch(Exception e){System.out.println("error deleteGrantFromPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    public ArrayList getReviewersForPanel(long panelId) 
    {
        ArrayList allrev = new ArrayList();
        try {
            conn = initializeConn();
            ps = conn.prepareStatement("select reviewers.id, fname, lname, title, affiliation, panel_reviewers.id as prid "+
            " from reviewers, panel_reviewers where reviewers.id=panel_reviewers.rev_id and pan_id=?");
            ps.setLong(1, panelId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                ReviewerBean rb = new ReviewerBean();
                rb.setRevid(rs.getLong("id"));
                rb.setFname(rs.getString("fname"));
                rb.setLname(rs.getString("lname"));
                rb.setTitle(rs.getString("title"));
                rb.setAffiliation(rs.getString("affiliation"));
                rb.setPanelreviewerId(rs.getLong("prid"));
                allrev.add(rb);
            }
            
        }catch(Exception e){System.out.println("error getReviewersForPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return allrev;
    }
    
    
    public ArrayList getGrantsForPanel(long panelId) 
    {
        ArrayList allgrants = new ArrayList();
        try {
            conn = initializeConn();
            ps = conn.prepareStatement("select grants.id, fc_code, fy_code, proj_seq_num, " + 
            " pc_id, initcap(popular_name) as popular_name, descr, panel_grants.id as pgid " + 
            " from grants, project_categories, panel_grants, co_institutions left join "+
            " sed_institutions on sed_institutions.inst_id= co_institutions.inst_id where "+
            " grants.id=co_institutions.gra_id and grants.pc_id= project_categories.id and " + 
            " grants.id=panel_grants.gra_id and is_lead='Y' and  pan_id=? order by proj_seq_num");
            ps.setLong(1, panelId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                GrantBean gb = new GrantBean();
                gb.setGrantid(rs.getLong("id"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setInstName(rs.getString("popular_name"));
                gb.setProjcategory(rs.getString("descr"));
                gb.setPanelgrantId(rs.getLong("pgid"));
                allgrants.add(gb);
            }
            
        }catch(Exception e){System.out.println("error getGrantsForPanel() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return allgrants;
    }  
  
  
    public PanelBean getPanelForReviewerFy(int fycode, long revid) 
    {
        PanelBean pb = new PanelBean();
        try {
            conn = initializeConn();
            ps = conn.prepareStatement("select * from ldgrants.panels where fy_code =? and id in "+
            "(select pan_id from panel_reviewers where rev_id=?)");
            ps.setInt(1, fycode);
            ps.setLong(2, revid);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                pb.setId(rs.getLong("id"));
                pb.setName(rs.getString("name"));
                pb.setDescr(rs.getString("descr"));
                pb.setStatus(rs.getString("status"));
                pb.setAmtavailable(rs.getInt("amt_available"));
            }
            
        }catch(Exception e){System.out.println("error getPanelForReviewerFy() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return pb;
    }
        
    
    public PanelReviewBean getDeliberationPanelGrant(long panelgrantId) 
    {
        PanelReviewBean rb = new PanelReviewBean();
        try {
            conn = initializeConn();
            ps = conn.prepareStatement("select panels.id, status, panel_grants.id as pg_id, "+
            " final_score, recommend_amt, recommendation, justification, decision_notes, gra_id, bonus_points "+
            " from ldgrants.panels, panel_grants where panels.id=panel_grants.pan_id and "+
            " panel_grants.id=?");
            ps.setLong(1, panelgrantId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                rb.setGrantid(rs.getLong("gra_id"));
                rb.setPanelgrantid(rs.getLong("pg_id"));
                rb.setPanelid(rs.getLong("id"));
                rb.setStatus(rs.getString("status"));
                rb.setFinalscore(rs.getInt("final_score"));
                rb.setRecommendation(rs.getString("recommendation"));
                rb.setRecommendamt(rs.getLong("recommend_amt"));
                rb.setRecommendamtStr(rs.getString("recommend_amt"));
                rb.setJustification(rs.getString("justification"));
                rb.setDecisionnotes(rs.getString("decision_notes"));
                //rb.setRaocomments(rs.getString("rao_comments"));
                rb.setBonusScore(rs.getInt("bonus_points"));//new 11/12/15
            }
            
        }catch(Exception e){System.out.println("error getDeliberationPanelGrant() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return rb;
    }
    
    
    /**
     * Original method to update everything on deliberation form. 
     * Not used starting 5/4/10 per FC - he now wants the delib form 
     * split in 2 sections to save recommamt and decision seperately. 
     * @param pb
     * @param ub
     * @return
     */
    public int updateDeliberationPanelGrant(PanelReviewBean pb, UserBean ub) 
    {
        int outcome=0;
        try {
            int recamt = 0;
            DBHandler dbh = new DBHandler();
            if(pb.getRecommendamtStr()!=null &&  !pb.getRecommendamtStr().equals(""))
                recamt = dbh.parseCurrencyAmtNoDecimal(pb.getRecommendamtStr());
                
            conn = initializeConn();
            ps = conn.prepareStatement("update panel_grants set final_score=?, recommendation=?, "+
            " recommend_amt=?, justification=?, decision_notes=?, "+
            " date_modified=sysdate, modified_by =? where id=?");                        
            ps.setInt(1, pb.getFinalscore());
            ps.setString(2, pb.getRecommendation());
            ps.setInt(3, recamt);
            ps.setString(4, pb.getJustification());
            ps.setString(5, pb.getDecisionnotes());
            ps.setString(6, ub.getUserid());
            ps.setLong(7, pb.getPanelgrantid());
            outcome = ps.executeUpdate();
                        
        }catch(Exception e){System.out.println("error updateDeliberationPanelGrant() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    /**
     * Method used to update the recommamt and final panel score from delib form -
     * Does not update decision notes. per FC 5/4/10
     * @param pb
     * @param ub
     * @return
     */
    public int updateRecommendation(PanelReviewBean pb, UserBean ub) 
    {
        int outcome=0;
        try {
            int recamt = 0;
            DBHandler dbh = new DBHandler();
            if(pb.getRecommendamtStr()!=null &&  !pb.getRecommendamtStr().equals(""))
                recamt = dbh.parseCurrencyAmtNoDecimal(pb.getRecommendamtStr());
                
            conn = initializeConn();
            ps = conn.prepareStatement("update panel_grants set final_score=?, recommendation=?, "+
            " recommend_amt=?, date_modified=sysdate, modified_by =?, bonus_points=? where id=?");                        
            ps.setInt(1, pb.getFinalscore());
            ps.setString(2, pb.getRecommendation());
            ps.setInt(3, recamt);
            ps.setString(4, ub.getUserid());
            ps.setInt(5, pb.getBonusScore());
            ps.setLong(6, pb.getPanelgrantid());
            outcome = ps.executeUpdate();
                        
        }catch(Exception e){System.out.println("error updateRecommendation() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }


    public int updateDelibDecision(PanelReviewBean pb, UserBean ub) 
    {
        int outcome=0;
        try {                
            conn = initializeConn();
            ps = conn.prepareStatement("update panel_grants set decision_notes=?, justification=?, " +
            " modified_by=?, date_modified=sysdate where id=?");
            ps.setString(1, pb.getDecisionnotes());
            ps.setString(2, pb.getJustification());
            ps.setString(3, ub.getUserid());
            ps.setLong(4, pb.getPanelgrantid());
            outcome=ps.executeUpdate();      
                        
        }catch(Exception e){System.out.println("error updateDelibDecision() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
    
    /**
     * This method used by rao to update decision notes for their region after
     * the panel review meeting ended.
     * @param pb
     * @param ub
     * @return
     */
    public int updateDecisionNotes(PanelReviewBean pb, UserBean ub) 
    {
        int outcome=0;
        try {                
            conn = initializeConn();
            ps = conn.prepareStatement("update panel_grants set decision_notes=?, modified_by=?, "+
            " date_modified=sysdate where id=?");
            ps.setString(1, pb.getDecisionnotes());
            ps.setString(2, ub.getUserid());
            ps.setLong(3, pb.getPanelgrantid());
            outcome=ps.executeUpdate();      
                        
        }catch(Exception e){System.out.println("error updateDecisionNotes() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return outcome;
    }
        
    public ArrayList handlePotentialPanelGrants(long projcategoryId, long panelid, int fycode)
    {
      ArrayList results=null;
      
      try{      
        /*get all possible submitted grants for this category
         also checks if grant was assigned to panel 7/23/09 */
        results = findGrantsByCategory(projcategoryId, panelid, fycode);
        
        //get any grants already assigned to panel
       // ArrayList allAssign = getGrantsForPanel(panelid);              
        //combine 2 datasets
        /*for(int i=0; i<results.size(); i++){
          Iterator ait = allAssign.iterator();
          GrantBean gb = (GrantBean) results.get(i);
          while(ait.hasNext()){
            GrantBean agb = (GrantBean) ait.next();
            if(agb.getGrantid()==gb.getGrantid()){
              gb.setPanelgrantId(agb.getPanelgrantId());
              gb.setAssignpanel(true);
            }
          }
        }*/
        
      }catch(Exception e){
      System.out.println("error handlePotentialAssignments() "+e.getMessage().toString());}
      return results; 
    }


    public ArrayList findGrantsByCategory(long projcategoryId, long panelid, int fycode) 
    {
        ArrayList allgrants = new ArrayList();
        try {
            conn = initializeConn();
            
            if(projcategoryId==0){
                ps = conn.prepareStatement("select grants.id, fc_code, fy_code, proj_seq_num, "+
                " initcap(popular_name) as popular_name, pc_id, descr from grants, project_categories, "+
                " co_institutions  left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id "+
                " where grants.id = co_institutions.gra_id and grants.pc_id=project_categories.id and "+
                " co_institutions.is_lead='Y' and fc_code=80 and fy_code=? and doris_flag=1 and "+
                " grants.id in (select gra_id from grant_submissions where version='Initial') order by popular_name");
                ps.setInt(1, fycode);                
            }
            else{
                String querystr = "select grants.id, fc_code, fy_code, proj_seq_num, "+
                " initcap(popular_name) as popular_name, pc_id, descr from grants, project_categories, "+
                " co_institutions  left join sed_institutions on sed_institutions.inst_id=co_institutions.inst_id "+
                " where grants.id = co_institutions.gra_id and grants.pc_id=project_categories.id and "+
                " co_institutions.is_lead='Y' and fc_code=80 and pc_id=? and fy_code=? ";
                if(projcategoryId !=11)//11 is doris admin, they will have doris_flag==1
                    querystr +=" and doris_flag !=1 "; 
                querystr += " and grants.id in (select gra_id from grant_submissions where "+
                " version='Initial') order by popular_name";
                
                ps = conn.prepareStatement(querystr);
                ps.setLong(1, projcategoryId);
                ps.setInt(2, fycode);
            }
            rs = ps.executeQuery();
            
            while(rs.next()) {
                GrantBean gb = new GrantBean();
                gb.setGrantid(rs.getLong("id"));
                gb.setInstName(rs.getString("popular_name"));
                gb.setProjcategory(rs.getString("descr"));
                gb.setFccode(rs.getInt("fc_code"));
                gb.setFycode(rs.getInt("fy_code"));
                gb.setProjseqnum(rs.getLong("proj_seq_num"));
                gb.setPanelId(panelid);                                
                allgrants.add(gb);
            }
            
            
            ps = conn.prepareStatement("select panel_grants.id, gra_id, pan_id from panel_grants "+
            " where gra_id=? ");
            for(int i=0; i<allgrants.size(); i++) 
            {
                GrantBean g = (GrantBean)allgrants.get(i);
                ps.setLong(1, g.getGrantid());
                rs = ps.executeQuery();
                //results only if grant is assigned to some panel: record exists in panel_grants
                while(rs.next()) {
                    if(panelid==rs.getLong("pan_id")){
                        g.setAssignpanel(true);//grant already assigned to this panel
                        g.setPanelgrantId(rs.getLong("id"));
                    }
                    else 
                        g.setAssigndiffpanel(true);//grant already assigned to different panel
                }
            }
            
                      
        }catch(Exception e){System.out.println("error findGrantsByCategory() "+e.getMessage().toString());}
        finally{
            Close(conn);
            Close(ps);
            Close(rs);
        } 
        return allgrants;
    }
    
    
    public ArrayList getPanelMoneyAmounts(int fycode)
    {
      ArrayList results=new ArrayList();      
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("select * from ldgrants.LGRMIF_PANELTOTALSVIEW where fy_code=? order by id");
        ps.setInt(1, fycode);
        rs = ps.executeQuery();
        while(rs.next()){
            PanelBean pb = new PanelBean();
            pb.setId(rs.getLong("id"));
            pb.setName(rs.getString("name"));
            pb.setStatus(rs.getString("status"));
            pb.setFycode(rs.getInt("fy_code"));
            pb.setAmtavailable(rs.getInt("amt_available"));
            pb.setAmtapproved(rs.getInt("totapproval"));
            pb.setAmtdifference(rs.getInt("amtdiff"));
            results.add(pb);
        }
        
      }catch(Exception e){
      System.out.println("error getPanelMoneyAmounts() "+e.getMessage().toString());}
      finally{
            Close(conn);
            Close(ps);
            Close(rs);
      } 
      return results;
    }
    
    /**
     * Method will get all grants for region/fy. Results are displayed to lgrmif reviewers
     * who are also rao's. Rao's can choose grant to update decision notes.
     * @param fycode
     * @param regiontype
     * @return
     */
    public ArrayList getGrantsForRegion(int fycode, int regiontype)
    {
      ArrayList results=new ArrayList();      
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("select grants.id as gra_id, panel_grants.id, grants.fy_code, fc_code, " + 
        " proj_seq_num, initcap(POPULAR_NAME) as popular_name, co_institutions.inst_id, " + 
        " project_categories.descr as projcatdescr, doris_name, doris_flag, panels.status from " + 
        " sed_institutions, co_institutions, panel_grants  " + 
        " left join ldgrants.panels on ldgrants.panels.id=panel_grants.pan_id " + 
        " , govt_infos, grants left join project_categories  " + 
        " on project_categories.id=grants.pc_id where sed_institutions.INST_ID= co_institutions.INST_ID  " + 
        " and co_institutions.GRA_ID = grants.ID and grants.id=panel_grants.gra_id and grants.id=  " + 
        " govt_infos.gra_id and is_lead='Y' and fc_code=80 and grants.fy_code=? and rt_id=? order by popular_name");
        ps.setInt(1, fycode);
        ps.setInt(2, regiontype);
        rs = ps.executeQuery();
        while(rs.next()){
            GrantBean gb = new GrantBean();
            gb.setGrantid(rs.getLong("gra_id"));
            gb.setPanelgrantId(rs.getLong("id"));
            gb.setFycode(rs.getInt("fy_code"));
            gb.setFccode(rs.getInt("fc_code"));
            gb.setProjseqnum(rs.getLong("proj_seq_num"));
            gb.setInstName(rs.getString("popular_name"));
            gb.setInstID(rs.getLong("inst_id"));
            gb.setProjcategory(rs.getString("projcatdescr"));
            gb.setStatus(rs.getString("status"));
            boolean isDoris = rs.getBoolean("doris_flag");
            if(isDoris)
                gb.setInstName(gb.getInstName() + " - "  +rs.getString("doris_name"));
            results.add(gb);
        }
     
      }catch(Exception e){
      System.out.println("error getGrantsForRegion() "+e.getMessage().toString());}
      finally{
            Close(conn);
            Close(ps);
            Close(rs);
      } 
      return results;
    }
    
        
    public PanelReviewBean getDecisionSummary(long grantid)
    {
      PanelReviewBean pb = new PanelReviewBean();
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("select decision_notes from panel_grants where gra_id=?");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        while(rs.next()){
            pb.setDecisionnotes(rs.getString("decision_notes"));
        }
     
      }catch(Exception e){
      System.out.println("error getDecisionSummary() "+e.getMessage().toString());}
      finally{
            Close(conn);
            Close(ps);
            Close(rs);
      } 
      return pb;
    }
    
    
    /**
     * get all ratings saved by panel deliberation for given grant.
     * note: these ratings from db are weighted already (individual reviewer ratings
     * from the db are not weighted)
     * @param panelgrantid
     * @return
     */
    public RatingBean getDeliberationRatings(long panelgrantid, RatingBean rb)
    {
      int sumscore=0, ascore =0;   
      boolean ratingsExist=false;
      try{
        conn = initializeConn();
                
        //get delib scores by category for panel for this grant
        ps = conn.prepareStatement("select * from REVIEWER_RATINGS where PG_ID=?");
        ps.setLong(1, panelgrantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          ratingsExist=true;
          int ratid = rs.getInt("RAT_ID");
          ascore = rs.getInt("score");
          sumscore += ascore;
          
          switch(ratid)
          {
            case 42:
                rb.setProblemDelibStr(String.valueOf(ascore));
                rb.setProblemDelib(ascore);
                break;
            case 43:
                rb.setRecordsDelibStr(String.valueOf(ascore));
                rb.setRecordsDelib(ascore);
                break;
            case 44:
                rb.setOtherfundDelibStr(String.valueOf(ascore));
                rb.setOtherfundDelib(ascore);
                break;
            case 45:
                rb.setOutcomeDelibStr(String.valueOf(ascore));
                rb.setOutcomeDelib(ascore);
                break;
            case 46:
                rb.setRecordprogramDelibStr(String.valueOf(ascore));
                rb.setRecordprogramDelib(ascore);
                break;
            case 47:
                rb.setTimetableDelibStr(String.valueOf(ascore));
                rb.setTimetableDelib(ascore);
                break;
            case 48:
                rb.setProjcategoryDelibStr(String.valueOf(ascore));
                rb.setProjcategoryDelib(ascore);
                break;
            case 49:
                rb.setStaffsupportDelibStr(String.valueOf(ascore));
                rb.setStaffsupportDelib(ascore);
                break;
            case 50:
                rb.setGovtsupportDelibStr(String.valueOf(ascore));
                rb.setGovtsupportDelib(ascore);
                break;
            case 51:
                rb.setLongrangeDelibStr(String.valueOf(ascore));
                rb.setLongrangeDelib(ascore);
                break;
            case 53:
                rb.setImproveservDelibStr(String.valueOf(ascore));
                rb.setImproveservDelib(ascore);
                break;
            case 54:
                rb.setExpendituresDelibStr(String.valueOf(ascore));
                rb.setExpendituresDelib(ascore);
                break;                
          }
        }//end while loop
        
        if(ratingsExist){
            sumscore = Math.round(sumscore);//round to nearest integer
            DecimalFormat myFormatter = new DecimalFormat("###");//do not print decimal point
            rb.setFinalScoreSumStr(myFormatter.format(sumscore));  
            rb.setFinalScoreSum(sumscore);
            //System.out.println("avg delib score "+sumscore);
        }
        else{
            //delib form ratings not saved to db yet; initialize to avg rating
            rb.setProblemDelibStr(rb.getProblemStr());
            rb.setRecordsDelibStr(rb.getRecordsStr());           
            rb.setOtherfundDelibStr(rb.getOtherfundStr());            
            rb.setOutcomeDelibStr(rb.getOutcomeStr());
            rb.setRecordprogramDelibStr(rb.getRecordprogramStr());            
            rb.setTimetableDelibStr(rb.getTimetableStr());            
            rb.setProjcategoryDelibStr(rb.getProjcategoryStr());            
            rb.setStaffsupportDelibStr(rb.getStaffsupportStr());
            rb.setGovtsupportDelibStr(rb.getGovtsupportStr());           
            rb.setLongrangeDelibStr(rb.getLongrangeStr());            
            rb.setImproveservDelibStr(rb.getImproveservStr());            
            rb.setExpendituresDelibStr(rb.getExpendituresStr());
            //set final delib total to the avg total score
            rb.setFinalScoreSumStr(rb.getScoreStr());
            rb.setFinalScoreSum(Integer.parseInt(rb.getScoreStr()));
        }
                
      }catch(Exception e){
        System.err.println("error getDeliberationRatings() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }    
      return rb;
    }
        
    
 public RatingBean handleSaveDelibRatings(RatingBean rb, HashMap allRateTypes, String userid)
 {        
    try{                
        HashMap updatescore = new HashMap();
        HashMap insertscore = new HashMap();
        
        if(rb.getProblemDelibStr()!=null && !rb.getProblemDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(42)))
            updatescore.put(new Integer(42), rb.getProblemDelibStr());
          else
            insertscore.put(new Integer(42), rb.getProblemDelibStr());
        }
        
        if(rb.getRecordsDelibStr()!=null && !rb.getRecordsDelibStr().equals(""))
        {
          if(allRateTypes.containsKey(new Integer(43)))
              updatescore.put(new Integer(43), rb.getRecordsDelibStr());
           else
              insertscore.put(new Integer(43), rb.getRecordsDelibStr());
        }
        
        if(rb.getOtherfundDelibStr()!=null && !rb.getOtherfundDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(44)))
            updatescore.put(new Integer(44), rb.getOtherfundDelibStr());
          else
            insertscore.put(new Integer(44), rb.getOtherfundDelibStr());
        }    
    
        if(rb.getOutcomeDelibStr()!=null && !rb.getOutcomeDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(45)))
              updatescore.put(new Integer(45), rb.getOutcomeDelibStr());
           else
              insertscore.put(new Integer(45), rb.getOutcomeDelibStr());
        }
        
        if(rb.getRecordprogramDelibStr()!=null && !rb.getRecordprogramDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(46)))
              updatescore.put(new Integer(46), rb.getRecordprogramDelibStr());
           else
              insertscore.put(new Integer(46), rb.getRecordprogramDelibStr());
        }
        
        if(rb.getImproveservDelibStr()!=null && !rb.getImproveservDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(53)))
            updatescore.put(new Integer(53), rb.getImproveservDelibStr());
          else
            insertscore.put(new Integer(53), rb.getImproveservDelibStr());
        }
        
        if(rb.getTimetableDelibStr()!=null && !rb.getTimetableDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(47)))
              updatescore.put(new Integer(47), rb.getTimetableDelibStr());
           else
              insertscore.put(new Integer(47), rb.getTimetableDelibStr());
        }
        
        if(rb.getProjcategoryDelibStr()!=null && !rb.getProjcategoryDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(48)))
              updatescore.put(new Integer(48), rb.getProjcategoryDelibStr());
           else
              insertscore.put(new Integer(48), rb.getProjcategoryDelibStr());
        }
        
        if(rb.getStaffsupportDelibStr()!=null && !rb.getStaffsupportDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(49)))
              updatescore.put(new Integer(49), rb.getStaffsupportDelibStr());
           else
              insertscore.put(new Integer(49), rb.getStaffsupportDelibStr());
        }
        
        if(rb.getGovtsupportDelibStr()!=null && !rb.getGovtsupportDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(50)))
              updatescore.put(new Integer(50), rb.getGovtsupportDelibStr());
           else
              insertscore.put(new Integer(50), rb.getGovtsupportDelibStr());
        }
        
        if(rb.getLongrangeDelibStr()!=null && !rb.getLongrangeDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(51)))
              updatescore.put(new Integer(51), rb.getLongrangeDelibStr());
           else
              insertscore.put(new Integer(51), rb.getLongrangeDelibStr());
        }
        
        if(rb.getExpendituresDelibStr()!=null && !rb.getExpendituresDelibStr().equals(""))
        {               
          if(allRateTypes.containsKey(new Integer(54)))
            updatescore.put(new Integer(54), rb.getExpendituresDelibStr());
          else
            insertscore.put(new Integer(54), rb.getExpendituresDelibStr());
        }
        
        
     //////insert and update each record in hashtable   
        conn = initializeConn();  
        PreparedStatement psinsert=null;
        int rattype=0, score=0;
               
        ps = conn.prepareStatement("update REVIEWER_RATINGS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
        " where PG_ID=? and RAT_ID=? ");
        psinsert = conn.prepareStatement("insert into REVIEWER_RATINGS (ID, SCORE, DATE_CREATED, "+
        " CREATED_BY, PG_ID, RAT_ID) values (REV_RATING_SEQ.nextval, ?, sysdate,?,?,?) ");
           
        Iterator j = insertscore.keySet().iterator(); 
        while(j.hasNext()) {
            rattype = ((Integer) j.next()).intValue();
            
            score = Integer.parseInt(((String) insertscore.get(new Integer(rattype))).trim());
            psinsert.setInt(1, score); 
            psinsert.setString(2, userid);
            psinsert.setLong(3, rb.getPanelgrantId());
            psinsert.setInt(4, rattype);   
            psinsert.addBatch();
        }
               
        Iterator i = updatescore.keySet().iterator(); 
        while(i.hasNext()){        
            rattype = ((Integer) i.next()).intValue();
            score = Integer.parseInt( ((String) updatescore.get(new Integer(rattype))).trim());    
            ps.setInt(1, score); 
            ps.setString(2, userid);
            ps.setLong(3, rb.getPanelgrantId());
            ps.setInt(4, rattype);   
            ps.addBatch();
        }
        
        ps.executeBatch();
        psinsert.executeBatch();
        Close(psinsert);        
    
    }catch(Exception e){
      System.err.println("error handleSaveDelibRatings() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);      
    }    
    return rb;
 }
    
    

}