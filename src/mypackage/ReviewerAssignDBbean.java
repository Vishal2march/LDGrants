/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerAssignDBBean.java
 * Creation/Modification History  : *
 * SH   8/1/07      Created
 *
 * Description
 * This class has methods related to reviewer assignments, such as creating/deleting
 * an assignment, and getting all assignments for a reviewer or for  a grant.
 *****************************************************************************/
package mypackage;
import java.sql.*;

import java.text.DecimalFormat;

import java.util.*;
import java.util.Date;
import javax.naming.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import mypackage.ReviewerAssignBean;
import oracle.jdbc.OracleDriver;

public class ReviewerAssignDBbean 
{
  public ReviewerAssignDBbean()
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



  /**
   * Get grant and review info for all grants a reviewer has been assigned for a given grant program.
   * Used to display all reviewer assignments for reviewers
   * @return 
   * @param fundCodeList added fundcode filter 4/4/08
   * @param revid
   */
  public Vector getAllAssignForReviewer(long revid, String fundCodeList, int fycode)
  {
    Vector allAssign = new Vector();    
    try{
      conn = initializeConn();
      
      if(fundCodeList!=null && fundCodeList.equals("80")) //lgrmif
      {
          ps = conn.prepareStatement("select panel_reviewer_assigns.ID, RATING_COMPLETE, PR_ID, PG_ID, conflict_interest," + 
          " PANEL_GRANTS.GRA_ID, PROJ_SEQ_NUM, FY_CODE, FC_CODE, CO_INSTITUTIONS.INST_ID, " + 
          " initcap(POPULAR_NAME) as popular_name, descr from " + 
          " PANEL_REVIEWER_ASSIGNS, PANEL_REVIEWERS, PANEL_GRANTS, GRANTS, PROJECT_CATEGORIES, CO_INSTITUTIONS " + 
          " left join SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id " + 
          " where PANEL_REVIEWER_ASSIGNS.PG_ID=PANEL_GRANTS.ID and " + 
          " PANEL_REVIEWER_ASSIGNS.PR_ID = PANEL_REVIEWERS.ID and " + 
          " PANEL_GRANTS.GRA_ID=GRANTS.ID and PROJECT_CATEGORIES.ID=GRANTS.PC_ID and " + 
          " CO_INSTITUTIONS.GRA_ID = GRANTS.ID and fy_code=? and REV_ID=? and IS_LEAD='Y' order by proj_seq_num");
          ps.setInt(1, fycode);
          ps.setLong(2, revid);
          rs = ps.executeQuery();
          
          while(rs.next())
          {
            ReviewerAssignBean rb = new ReviewerAssignBean();
            rb.setAssignid(rs.getLong("ID"));
            rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
            rb.setRevid(rs.getLong("PR_ID"));
            rb.setPanelGrantId(rs.getLong("PG_ID"));
            rb.setGraid(rs.getLong("GRA_ID"));
            rb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
            rb.setFycode(rs.getInt("FY_CODE"));
            rb.setFccode(rs.getInt("FC_CODE"));
            rb.setInstid(rs.getLong("INST_ID"));
            rb.setInstname(rs.getString("popular_name"));     
            rb.setTitle(rs.getString("descr"));//project_categories.descr
            rb.setConflictinterest(rs.getBoolean("conflict_interest"));
            allAssign.add(rb);        
          }            
      }
      else
      {
          ps = conn.prepareStatement("select GRANT_ASSIGNS.ID, RATING_COMPLETE, COMMENT_COMPLETE, REV_ID, "+
          " GRANT_ASSIGNS.GRA_ID, NAME, PROJ_SEQ_NUM, FY_CODE, FC_CODE, CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name from "+
          " GRANT_ASSIGNS, GRANTS, CO_INSTITUTIONS left join SED_INSTITUTIONS on "+
          " sed_institutions.inst_id=co_institutions.inst_id where GRANT_ASSIGNS.GRA_ID=GRANTS.ID and "+
          " CO_INSTITUTIONS.GRA_ID = GRANTS.ID and fy_code=? and REV_ID=? and IS_LEAD='Y' and FC_CODE in ("+fundCodeList+") "+
          " order by popular_name");
          ps.setInt(1, fycode);
          ps.setLong(2, revid);
          rs = ps.executeQuery();
          
          while(rs.next())
          {
            ReviewerAssignBean rb = new ReviewerAssignBean();
            rb.setAssignid(rs.getLong("ID"));
            rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
            rb.setCommentcomp(rs.getBoolean("COMMENT_COMPLETE"));
            rb.setRevid(rs.getLong("REV_ID"));
            rb.setGraid(rs.getLong("GRA_ID"));
            rb.setTitle(rs.getString("NAME"));
            rb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
            rb.setFycode(rs.getInt("FY_CODE"));
            rb.setFccode(rs.getInt("FC_CODE"));
            rb.setInstid(rs.getLong("INST_ID"));
            rb.setInstname(rs.getString("popular_name"));                        
            allAssign.add(rb);        
          }            
      }
      
    } catch(Exception e){
      System.err.println("error getAllAssignForReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allAssign;
  }
  
  
  public Vector getAllAssignForLgGrant(long grantid) {
      Vector allAssign = new Vector();    
      try{
        conn = initializeConn();        
        ps = conn.prepareStatement("select pra.id, final_score, recommendation, recommend_amt, " + 
        " rating_complete, pr_id, pg_id, conflict_interest, fname, lname from panel_reviewer_assigns pra, " + 
        " panel_reviewers pr, reviewers where pra.pr_id=pr.id and pr.rev_id = reviewers.id and " + 
        " pg_id in (select id from panel_grants where gra_id=?)");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          RatingBean rb = new RatingBean();
          String name = rs.getString("fname") +" "+rs.getString("lname");
          rb.setName(name);
          rb.setRatingcomp(rs.getBoolean("rating_complete"));
          rb.setRecommendation(rs.getString("recommendation"));
          rb.setRecommendamt(rs.getLong("recommend_amt"));
          rb.setGrantassign(rs.getLong("id"));     
          rb.setConflictinterest(rs.getBoolean("conflict_interest"));
          rb.setPanelgrantId(rs.getLong("pg_id"));
          allAssign.add(rb);
        }      
        
      }
      catch(Exception e){
        System.err.println("error getAllAssignForLgGrant() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }
      return allAssign;
  }

  public Vector getAllAssignForGrant(long grantid)
  {  
    Vector allAssign = new Vector();    
    try{
      conn = initializeConn();
      
      ps = conn.prepareStatement("select GRANT_ASSIGNS.ID, RATING_COMPLETE, GRANT_ASSIGNS.DATE_CREATED, "+
       " GRA_ID, FNAME, LNAME, INTEREST from GRANT_ASSIGNS, REVIEWERS where GRA_ID=? "+
       " and REVIEWERS.ID = GRANT_ASSIGNS.REV_ID");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setInterest(rs.getString("INTEREST"));
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setAssignid(rs.getLong("ID"));
          rab.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
          rab.setDateassigned(rs.getDate("DATE_CREATED"));
          rab.setGraid(rs.getLong("GRA_ID"));    
          
        ReviewerAssignBean[] assignarray = {rab};
        rb.setReviewerAssigns(assignarray);
               
        allAssign.add(rb);
      }      
      
    }
    catch(Exception e){
      System.err.println("error getAllAssignForGrant() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allAssign;
  }

  public int assignReviewer(long grantid, long revid, UserBean userb )
  {
    int outcome =0;        
    try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("insert into GRANT_ASSIGNS (ID, REV_ID, GRA_ID, DATE_CREATED, "+
        " CREATED_BY) values (GRA_ASSIGN_SEQ.nextval, ?,?,SYSDATE,?)");
      ps.setLong(1, revid);
      ps.setLong(2, grantid);
      ps.setString(3, userb.getUserid());      
      outcome = ps.executeUpdate();      
            
    } catch(Exception e){
      System.err.println("error assignReviewer() " + e.getMessage().toString());
    }
    finally {
      Close(ps);
      Close(conn);
    }
    return outcome;
  }

  public int deleteRevAssign(long id)
  {    
    int outcome =0;       
    try{      
      conn = initializeConn();
      
      ps = conn.prepareStatement("delete from GRANT_ASSIGNS where ID=?");
      ps.setLong(1, id);           
      outcome = ps.executeUpdate();                  
    }
    catch(Exception e){
      System.err.println("error deleteRevAssign() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }


  /**
   * This method will get each individual score a reviewer has assigned to the given
   * grant. Searches based on the assignment id (combo of revID and grantID).  
   * @return 
   * @param assignid
   */
  public RatingBean getGrantRatingsForReviewer(long assignid)
  {
    RatingBean rb = new RatingBean();
    int sumscore=0;
    
    try {
      conn = initializeConn();
      
      //get reviewer comment and info about assignment
      ps = conn.prepareStatement("select GRANT_ASSIGNS.ID, RATING_COMPLETE, "+
        " REV_ID, GRA_ID, REVC_COMMENT from GRANT_ASSIGNS left join REVIEWER_COMMENTS on "+
        " GRANT_ASSIGNS.ID = REVIEWER_COMMENTS.GRAAS_ID where GRANT_ASSIGNS.ID=? ");
      ps.setLong(1, assignid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {       
        rb.setGrantassign(rs.getLong("ID"));
        rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
        rb.setRevid(rs.getLong("REV_ID"));
        rb.setGraid(rs.getLong("GRA_ID"));
        rb.setComment(rs.getString("REVC_COMMENT"));        
      }      
      ps.clearParameters();
      
      //get all rating scores for this assignment
      ps = conn.prepareStatement("select * from REVIEWER_RATINGS, RATING_TYPES where "+
        " GRAAS_ID=? and REVIEWER_RATINGS.RAT_ID=RATING_TYPES.ID ");
      ps.setLong(1, assignid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        int ratid = rs.getInt("RAT_ID");
        sumscore += rs.getInt("SCORE");
        
        switch(ratid)
        {
          case 1:
            rb.setAppropriate(rs.getInt("SCORE"));
            rb.setAppropriateStr(rs.getString("SCORE"));
            break;
          case 2:
            rb.setSignificance(rs.getInt("SCORE"));
            rb.setSignificanceStr(rs.getString("SCORE"));
            break;
          case 3:
            rb.setInvolvement(rs.getInt("SCORE"));
            rb.setInvolvementStr(rs.getString("SCORE"));
            break;
          case 4:
            rb.setCoordination(rs.getInt("SCORE"));
            rb.setCoordinationStr(rs.getString("SCORE"));
            break;
          case 5:
            rb.setBibliographic(rs.getInt("SCORE"));
            rb.setBibliographicStr(rs.getString("SCORE"));
            break;
          case 6:
            rb.setOnlinedb(rs.getInt("SCORE"));
            rb.setOnlinedbStr(rs.getString("SCORE"));
            break;
          case 7:
            rb.setTimetable(rs.getInt("SCORE"));
            rb.setTimetableStr(rs.getString("SCORE"));
            break;
          case 8:
            rb.setSoundness(rs.getInt("SCORE"));
            rb.setSoundnessStr(rs.getString("SCORE"));
            break;
          case 9:
            rb.setEquipment(rs.getInt("SCORE"));
            rb.setEquipmentStr(rs.getString("SCORE"));
            break;
          case 10:
            rb.setPersonnel(rs.getInt("SCORE"));
            rb.setPersonnelStr(rs.getString("SCORE"));
            break;
          case 11:
            rb.setStorage(rs.getInt("SCORE"));
            rb.setStorageStr(rs.getString("SCORE"));
            break;
          case 12:
            rb.setConsistentdesc(rs.getInt("SCORE"));
            rb.setConsistentdescStr(rs.getString("SCORE"));
            break;
          case 13:
            rb.setConsistentexp(rs.getInt("SCORE"));
            rb.setConsistentexpStr(rs.getString("SCORE"));
            break;
          case 14:
            rb.setCosteffective(rs.getInt("SCORE"));
            rb.setCosteffectiveStr(rs.getString("SCORE"));
            break;
          case 15:
            rb.setOverallscore(rs.getInt("SCORE"));
            rb.setOverallscoreStr(rs.getString("SCORE"));
            break;   
          case 16:
            rb.setStaffsupport(rs.getInt("SCORE"));
            rb.setStaffsupportStr(rs.getString("SCORE"));
            break;
          case 17:
            rb.setFinancialsupport(rs.getInt("SCORE"));
            rb.setFinancialsupportStr(rs.getString("SCORE"));
            break;
          case 18:
            rb.setAvailability(rs.getInt("SCORE"));
            rb.setAvailabilityStr(rs.getString("SCORE"));
            break;
          case 19:
            rb.setSecurity(rs.getInt("SCORE"));
            rb.setSecurityStr(rs.getString("SCORE"));
            break;
          case 20:
            rb.setDisaster(rs.getInt("SCORE"));
            rb.setDisasterStr(rs.getString("SCORE"));
            break;
          case 21:
            rb.setInstcp(rs.getInt("SCORE"));
            rb.setInstcpStr(rs.getString("SCORE"));
            break;
          case 22:
            rb.setCoopactivities(rs.getInt("SCORE"));
            rb.setCoopactivitiesStr(rs.getString("SCORE"));
            break;
          case 23:
            rb.setAbstractInt(rs.getInt("SCORE"));
            rb.setAbstractStr(rs.getString("SCORE"));
            break;
          case 24:
            rb.setGroupneed(rs.getInt("SCORE"));
            rb.setGroupneedStr(rs.getString("SCORE"));
            break;
          case 25:
            rb.setLongrange(rs.getInt("SCORE"));
            rb.setLongrangeStr(rs.getString("SCORE"));
            break;
          case 26:
            rb.setLevelservice(rs.getInt("SCORE"));
            rb.setLevelserviceStr(rs.getString("SCORE"));
            break;
          case 27:
            rb.setCooporganization(rs.getInt("SCORE"));
            rb.setCooporganizationStr(rs.getString("SCORE"));
            break;
          case 28:
            rb.setGoal(rs.getInt("SCORE"));
            rb.setGoalStr(rs.getString("SCORE"));
            break;
          case 29:
            rb.setActivities(rs.getInt("SCORE"));
            rb.setActivitiesStr(rs.getString("SCORE"));
            break;
          case 30:
            rb.setOutput(rs.getInt("SCORE"));
            rb.setOutputStr(rs.getString("SCORE"));
            break;
          case 31:
            rb.setMeasureoutput(rs.getInt("SCORE"));
            rb.setMeasureoutputStr(rs.getString("SCORE"));
            break;
          case 32:
            rb.setOutcome(rs.getInt("SCORE"));
            rb.setOutcomeStr(rs.getString("SCORE"));
            break;
          case 33:
            rb.setMeasureoutcome(rs.getInt("SCORE"));
            rb.setMeasureoutcomeStr(rs.getString("SCORE"));
            break;
          case 34:
            rb.setContinuation(rs.getInt("SCORE"));
            rb.setContinuationStr(rs.getString("SCORE"));
            break;
          case 35:
            rb.setSharing(rs.getInt("SCORE"));
            rb.setSharingStr(rs.getString("SCORE"));
            break;
          case 36:
            rb.setBudget(rs.getInt("SCORE"));
            rb.setBudgetStr(rs.getString("SCORE"));
            break;
          case 37:
            rb.setOtherfund(rs.getInt("SCORE"));
            rb.setOtherfundStr(rs.getString("SCORE"));
            break;
          case 38:
            rb.setGroupneed(rs.getInt("SCORE"));
            rb.setGroupneedStr(rs.getString("SCORE"));
            break;
          case 39:
            rb.setGoal(rs.getInt("SCORE"));
            rb.setGoalStr(rs.getString("SCORE"));
            break;
          case 40:
            rb.setPublicity(rs.getInt("SCORE"));
            rb.setPublicityStr(rs.getString("SCORE"));
            break;
          case 41:
            rb.setSharing(rs.getInt("SCORE"));
            rb.setSharingStr(rs.getString("SCORE"));
            break;
        }
      }//end while loop
      
      rb.setSumscore(sumscore);
           
    }
    catch(Exception e){
      System.err.println("error getGrantRatingsForReviewer() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
  
    return rb;
  }

    /**
     * Method will get all comments/rating records for LG reviewer 
     * for the given reviewer/grant assignment.
     * @param assignid
     * @return
     */
    public RatingBean getGrantRatingsForLGReviewer(long assignid)
    {
      RatingBean rb = new RatingBean();
      int sumscore=0;      
      try{
        conn = initializeConn();
        
        //get info about assignment
        ps = conn.prepareStatement("select pga.ID, pga.FINAL_SCORE, pga.RECOMMENDATION, "+
        " pga.RECOMMEND_AMT, pga.RATING_COMPLETE, pga.BONUS_POINTS, PR_ID, PG_ID, GRA_ID, conflict_interest, fy_code from "+
        " PANEL_REVIEWER_ASSIGNS pga, PANEL_GRANTS left join grants on panel_grants.gra_id=grants.id "+
        " where pga.PG_ID= PANEL_GRANTS.ID and pga.ID=?");
        ps.setLong(1, assignid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {       
          rb.setGrantassign(rs.getLong("ID"));
          rb.setRecommendation(rs.getString("recommendation"));
          rb.setRecommendamt(rs.getLong("recommend_amt"));
          rb.setRecommendamtStr(rs.getString("recommend_amt"));
          rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
          rb.setPanelreviewerId(rs.getLong("pr_id"));
          rb.setPanelgrantId(rs.getLong("pg_id"));
          //rb.setRevid(rs.getLong("REV_ID"));
          rb.setGraid(rs.getLong("GRA_ID")); 
          rb.setFycode(rs.getInt("fy_code"));
          rb.setConflictinterest(rs.getBoolean("conflict_interest"));
          rb.setBonusScore(rs.getInt("BONUS_POINTS"));//added 11/12/15
        }      
        ps.clearParameters();
        
        //get all rating scores for this assignment
        ps = conn.prepareStatement("select * from REVIEWER_RATINGS where PRA_ID=?");
        ps.setLong(1, assignid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          int ratid = rs.getInt("RAT_ID");
          sumscore += rs.getInt("SCORE");
          
          switch(ratid)
          {
            case 36:
              rb.setBudget(rs.getInt("SCORE"));//no longer used 9/9/10
              rb.setBudgetStr(rs.getString("SCORE"));
              sumscore += (rs.getInt("SCORE")*2);//weighted *3
              break;
            case 42:
                rb.setProblem(rs.getInt("SCORE"));
                rb.setProblemStr(rs.getString("SCORE"));
                if(rb.getFycode()<16)//starting with 2015-16; not weighted
                  sumscore += rs.getInt("SCORE");//weighted * 2
                break;
            case 43:
                rb.setRecords(rs.getInt("SCORE"));
                rb.setRecordsStr(rs.getString("SCORE"));
                if(rb.getFycode()>14)
                    sumscore += rs.getInt("SCORE");//weighted *2 starting FY2014-15
                break;
            case 44:
                rb.setOtherfund(rs.getInt("SCORE"));
                rb.setOtherfundStr(rs.getString("SCORE"));
                break;
            case 45:
                rb.setOutcome(rs.getInt("SCORE"));
                rb.setOutcomeStr(rs.getString("SCORE"));   
                if(rb.getFycode()>15)
                    sumscore += rs.getInt("SCORE");//weighted *2 starting FY2015-16
                break;
            case 46:
                rb.setRecordprogram(rs.getInt("SCORE"));
                rb.setRecordprogramStr(rs.getString("SCORE"));
                if(rb.getFycode()>14)
                   sumscore += rs.getInt("SCORE");//weighted *2 starting FY2014-15
                break;
            case 47:
                rb.setTimetable(rs.getInt("SCORE"));
                rb.setTimetableStr(rs.getString("SCORE"));
                sumscore += (rs.getInt("SCORE")*2);//weighted  *3
                break;
            case 48:
                rb.setProjcategory(rs.getInt("SCORE"));
                rb.setProjcategoryStr(rs.getString("SCORE"));
                if(rb.getFycode()>15)
                  sumscore += (rs.getInt("SCORE")*2);//weighted  *3 starting FY2015-16
                else
                  sumscore += rs.getInt("SCORE");//weighted *2
                break;
            case 49:
                rb.setStaffsupport(rs.getInt("SCORE"));//no longer used starting 2015-16
                rb.setStaffsupportStr(rs.getString("SCORE"));
                break;
            case 50:
                rb.setGovtsupport(rs.getInt("SCORE"));
                rb.setGovtsupportStr(rs.getString("SCORE"));
                break;
            case 51:
                rb.setLongrange(rs.getInt("SCORE"));
                rb.setLongrangeStr(rs.getString("SCORE"));
                break;
            case 52:
                rb.setReasonablecost(rs.getInt("SCORE"));//no longer used 9/9/10
                rb.setReasonablecostStr(rs.getString("SCORE"));
                sumscore += (rs.getInt("SCORE") *2);//weighted *3
                break;
            case 53:
                  rb.setImproveserv(rs.getInt("SCORE"));
                  rb.setImproveservStr(rs.getString("SCORE"));//new for 10-11 fy
                  break;
            case 54:
                  rb.setExpenditures(rs.getInt("SCORE"));
                  rb.setExpendituresStr(rs.getString("SCORE"));
                  sumscore += (rs.getInt("SCORE") *4);//new for 10-11 fy, weighted *5
                  break;
          }
        }//end while loop
        
        rb.setSumscore(sumscore);
        
        //get comments for lg rating form
        ps = conn.prepareStatement("select * from reviewer_comments where pra_id=?");
        ps.setLong(1, assignid);
        rs = ps.executeQuery();
        while(rs.next()){
            switch(rs.getInt("ct_id")) {
                case 1:
                    rb.setProblemcomment(rs.getString("revc_comment"));
                    break;
                case 2:
                    rb.setResultcomment(rs.getString("revc_comment"));
                    break;
                case 3:
                    rb.setWorkcomment(rs.getString("revc_comment"));
                    break;
                case 4:
                    rb.setSupportcomment(rs.getString("revc_comment"));
                    break;
                case 5:
                    rb.setBudgetcomment(rs.getString("revc_comment"));
                    break;
                case 17:
                    rb.setComment(rs.getString("revc_comment"));
                    break;
            }
        }        
        
      }catch(Exception e){
        System.err.println("error getGrantRatingsForLGReviewer() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }    
      return rb;
    }
 
 
  public ArrayList getAllLgRatingsForGrant(long grantid, int fycode)
  {
    ArrayList allratings = new ArrayList();
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("select pra.id, final_score, recommendation, recommend_amt, "+
        " rating_complete, pr_id, pg_id, fname, lname, bonus_points from panel_reviewer_assigns pra, "+
        " panel_reviewers pr, reviewers where pra.pr_id=pr.id and pr.rev_id = reviewers.id and "+
        " rating_complete=1 and pg_id in (select id from panel_grants where gra_id=?)");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          RatingBean rb = new RatingBean();
          rb.setGrantassign(rs.getLong("id"));
          rb.setRecommendation(rs.getString("recommendation"));
          rb.setRecommendamt(rs.getLong("recommend_amt"));
          rb.setRatingcomp(rs.getBoolean("rating_complete"));
          rb.setPanelgrantId(rs.getLong("pg_id"));
          rb.setPanelreviewerId(rs.getLong("pr_id"));
          String name = rs.getString("fname") + " " + rs.getString("lname");
          rb.setName(name);
          rb.setGraid(grantid);
          rb.setBonusScore(rs.getInt("bonus_points"));//new 11/12/15
          allratings.add(rb);
        }
        
        
        ps = conn.prepareStatement("select * from reviewer_ratings where pra_id=?");        
        for(int i=0; i<allratings.size(); i++)
        {
          int sumscore=0;
          RatingBean r = (RatingBean) allratings.get(i);
          ps.setLong(1, r.getGrantassign());
          rs = ps.executeQuery();
          
          while(rs.next())
          {
            sumscore += rs.getInt("score"); 
            switch(rs.getInt("rat_id"))
            {
              case 36:
                r.setBudget(rs.getInt("SCORE"));                
                sumscore += (rs.getInt("SCORE")*2);//weighted *3
                break;
              case 42:
                  r.setProblem(rs.getInt("SCORE"));
                  if(fycode<16)//starting with 2015-16; not weighted
                    sumscore += rs.getInt("SCORE");//weighted * 2
                  break;
              case 43:
                  r.setRecords(rs.getInt("SCORE"));
                  if(fycode >14)
                    sumscore += rs.getInt("score");//weighted*2 starting FY14-15
                  break;
              case 44:
                  r.setOtherfund(rs.getInt("SCORE"));
                  break;
              case 45:
                  r.setOutcome(rs.getInt("SCORE"));  
                  if(fycode >15)
                    sumscore += rs.getInt("score");//weighted*2 starting FY15-16
                  break;
              case 46:
                  r.setRecordprogram(rs.getInt("SCORE"));
                  if(fycode >14)
                    sumscore += rs.getInt("score");//weighted*2 starting FY14-15
                  break;
              case 47:
                  r.setTimetable(rs.getInt("SCORE"));
                  sumscore += (rs.getInt("SCORE")*2);//weighted  *3
                  break;
              case 48:
                  r.setProjcategory(rs.getInt("SCORE"));
                  if(fycode > 15)
                      sumscore += (rs.getInt("SCORE")*2);//weighted *3 for 2015-16
                  else
                    sumscore += rs.getInt("SCORE");//weighted *2
                  break;
              case 49:
                  r.setStaffsupport(rs.getInt("SCORE"));
                  break;
              case 50:
                  r.setGovtsupport(rs.getInt("SCORE"));
                  break;
              case 51:
                  r.setLongrange(rs.getInt("SCORE"));
                  break;
              case 52:
                  r.setReasonablecost(rs.getInt("SCORE"));
                  sumscore += (rs.getInt("SCORE") *2);//weighted *3
                  break;
              case 53:
                  r.setImproveserv(rs.getInt("score"));
                  break;
              case 54:
                  r.setExpenditures(rs.getInt("score"));
                  sumscore += (rs.getInt("score") *4);//weighted *5
                  break;
            }
            r.setScore(sumscore);
          }          
        }
        
      }catch(Exception e){
          System.err.println("error getAllLgRatingsForGrant() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }    
      return allratings;  
  }
 
   public ArrayList getAllLgCommentsForGrant(long grantid)
   {  
      ArrayList allcomms = new ArrayList();
      try{      
        conn = initializeConn();
        /*ps = conn.prepareStatement("select * from panel_reviewer_assigns where rating_complete=1 "+
        " and pg_id in (select id from panel_grants where gra_id=?)");*/
        ps = conn.prepareStatement("select pra.id, final_score, recommendation, recommend_amt, "+
        " rating_complete, pr_id, pg_id, fname, lname, bonus_points from panel_reviewer_assigns pra, "+
        " panel_reviewers pr, reviewers where pra.pr_id=pr.id and pr.rev_id = reviewers.id and "+
        " rating_complete=1 and pg_id in (select id from panel_grants where gra_id=?)");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          RatingBean rb = new RatingBean();
          rb.setGrantassign(rs.getLong("id"));
          rb.setRecommendation(rs.getString("recommendation"));
          rb.setRecommendamt(rs.getLong("recommend_amt"));
          rb.setRatingcomp(rs.getBoolean("rating_complete"));
          rb.setPanelgrantId(rs.getLong("pg_id"));
          rb.setPanelreviewerId(rs.getLong("pr_id"));
          rb.setBonusScore(rs.getInt("bonus_points"));//new 11/12/15
          String name = rs.getString("fname") + " " + rs.getString("lname");
          rb.setName(name);
          rb.setGraid(grantid);
          allcomms.add(rb);
        }
         
         
        ps = conn.prepareStatement("select * from reviewer_comments where pra_id=?");
        for(int i=0; i<allcomms.size(); i++)
        {
          RatingBean r = (RatingBean) allcomms.get(i);  
          ps.setLong(1, r.getGrantassign());
          rs = ps.executeQuery();
          
          while(rs.next())
          {
            switch(rs.getInt("ct_id")) {
                case 1:
                    r.setProblemcomment(rs.getString("revc_comment"));
                    break;
                case 2:
                    r.setResultcomment(rs.getString("revc_comment"));
                    break;
                case 3:
                    r.setWorkcomment(rs.getString("revc_comment"));
                    break;
                case 4:
                    r.setSupportcomment(rs.getString("revc_comment"));
                    break;
                case 5:
                    r.setBudgetcomment(rs.getString("revc_comment"));
                    break;
                case 17:
                    r.setComment(rs.getString("revc_comment"));
                    break;
            }
          }
        }
                
      }catch(Exception e){
          System.err.println("error getAllLgCommentsForGrant() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }    
      return allcomms;
   }
 

/**
     * Method will get category avg rating for lg reviewers.  Also get avg
     * amt recommended per fc 2/12/10. Changed to round to integer per fc 4/15/10,
     * previously rounded 1 decimal. Used on deliberation form for summary of at-home scores
     * modified 8/25/14 SH with new weights for 2015-16 apps
     * @param panelgrantid
     * @return
     */
    public RatingBean getLgAvgRatingByCategory(long panelgrantid, int fycode)
    {
      RatingBean rb = new RatingBean();
      int sumscore=0, ascore =0;      
      try{
        conn = initializeConn();
        rb.setPanelgrantId(panelgrantid);
                
        //get average rating scores by category for all submitted reviewer ratings for this grant
        ps = conn.prepareStatement("select round(avg(score), 0) as ascore, rat_id from reviewer_ratings "+
        " where pra_id in (select id from panel_reviewer_assigns where pg_id=? and "+
        " rating_complete=1) group by rat_id");
        ps.setLong(1, panelgrantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          int ratid = rs.getInt("RAT_ID");
          ascore = rs.getInt("ascore");
          sumscore += ascore;
          
          switch(ratid)
          {
            case 36:
              rb.setBudgetStr( String.valueOf( ascore*3));
              sumscore += (ascore*2);//weighted *3
              break;
            case 42:
                rb.setProblemStr(String.valueOf(ascore));
                if(fycode <16){
                    sumscore += ascore;//weighted * 2  only for prior to 2015-16
                    rb.setProblemStr(String.valueOf(ascore *2));
                }
                break;
            case 43:
                rb.setRecordsStr(String.valueOf(ascore));
                if(fycode > 14){
                    sumscore += ascore;//weighted*2 starting 2014-15
                    rb.setRecordsStr(String.valueOf(ascore*2));
                }
                break;
            case 44:
                rb.setOtherfundStr(String.valueOf(ascore));
                break;
            case 45:
                rb.setOutcomeStr(String.valueOf(ascore));    
                if(fycode >15){
                    rb.setOutcomeStr(String.valueOf(ascore*2)); 
                    sumscore += ascore;//weighted*2 starting 2015-16
                }                    
                break;
            case 46:
                rb.setRecordprogramStr(String.valueOf(ascore));
                if(fycode >14){
                      sumscore += ascore;//weighted*2 starting 2014-15
                      rb.setRecordprogramStr(String.valueOf(ascore*2));
                }
                break;
            case 47:
                rb.setTimetableStr(String.valueOf(ascore*3));
                sumscore += (ascore*2);//weighted  *3
                break;
            case 48:
                if(fycode >15){
                    rb.setProjcategoryStr(String.valueOf(ascore*3));//weighted *3 starting fy2015-16
                    sumscore += (ascore*2);//weighted  *3
                }
                else{
                    rb.setProjcategoryStr(String.valueOf(ascore*2));
                    sumscore += (ascore);//weighted *2
                }
                break;
            case 49:
                rb.setStaffsupportStr(String.valueOf(ascore));
                break;
            case 50:
                rb.setGovtsupportStr(String.valueOf(ascore));
                break;
            case 51:
                rb.setLongrangeStr(String.valueOf(ascore));
                break;
            case 52:
                rb.setReasonablecostStr(String.valueOf(ascore *3));
                sumscore += (ascore *2);//weighted *3
                break;
            case 53:
                rb.setImproveservStr(String.valueOf(ascore));
                break;
            case 54:
                rb.setExpendituresStr(String.valueOf(ascore *5));
                sumscore += (ascore *4);//weighted *5
                break;                
          }
        }//end while loop
        
        sumscore = Math.round(sumscore);//round to nearest integer
        DecimalFormat myFormatter = new DecimalFormat("###");//do not print decimal point
        rb.setScoreStr(myFormatter.format(sumscore));       
        
        
        ps = conn.prepareStatement("select round(avg(recommend_amt), 0) as recommend_amt from "+
        " panel_reviewer_assigns where pg_id =? and rating_complete=1");
        ps.setLong(1, panelgrantid);
        rs = ps.executeQuery();
        while(rs.next()){
            rb.setRecommendamt(rs.getLong("recommend_amt"));
        }
        
      }catch(Exception e){
        System.err.println("error getLgAvgRatingByCategory() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }    
      return rb;
    }

  public boolean hasRatingRecord(long assignid, int ratingtype)
  {
    boolean hasRating=false;        
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("select * from REVIEWER_RATINGS where GRAAS_ID=? and RAT_ID=?");
      ps.setLong(1, assignid); 
      ps.setInt(2, ratingtype);
      rs = ps.executeQuery();      
      while(rs.next()){
        hasRating = true;
      }
            
    }catch(Exception e){
      System.err.println("error hasRatingRecord() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return hasRating;
  }
  
  
    /**
     * Get all the rating_type_ids in REVIEWER-RATINGS table for
     * co/di/lit/lg/CN reviewer, and lg panel deliberation.
     * Used as helper method before insert/update rating (save rating form).
     * @param assignid
     * @return
     */
    public HashMap getExistingRatingTypes(long assignid, String module)
    {
      HashMap allRateTypes = new HashMap();          
      try {      
        conn = initializeConn();
        
        if(module!=null && module.equals("lg"))
            ps = conn.prepareStatement("select * from REVIEWER_RATINGS where PRA_ID=?");
        else if(module!=null && module.equals("cn"))
            ps = conn.prepareStatement("select * from REVIEWER_RATINGS where SGA_ID=?");
        else if(module!=null && module.equals("delib"))
            ps = conn.prepareStatement("select * from REVIEWER_RATINGS where PG_ID=?");          
        else
            ps = conn.prepareStatement("select * from REVIEWER_RATINGS where GRAAS_ID=?");
        
        ps.setLong(1, assignid); 
        rs = ps.executeQuery();
        
        while(rs.next()){
          int id = rs.getInt("rat_id");
          allRateTypes.put(new Integer(id), "true");
        }
              
      }catch(Exception e){
        System.err.println("error getExistingRatingTypes() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }
      return allRateTypes;
    }
    
    
    public HashMap getExistingCommentTypes(long assignid, String module)
    {
      HashMap allCommTypes = new HashMap();          
      try {      
        conn = initializeConn();
        
        if(module!=null && module.equals("lg"))
            ps = conn.prepareStatement("select * from REVIEWER_COMMENTS where PRA_ID=?");
        else if(module!=null && module.equals("cn"))
            ps = conn.prepareStatement("select * from REVIEWER_COMMENTS where SGA_ID=?");
        else
            ps = conn.prepareStatement("select * from REVIEWER_COMMENTS where GRAAS_ID=?");
        
        ps.setLong(1, assignid); 
        rs = ps.executeQuery();
        
        while(rs.next()){
          int id = rs.getInt("ct_id");
          allCommTypes.put(new Integer(id), "true");
        }
              
      }catch(Exception e){
        System.err.println("error getExistingCommentTypes() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }
      return allCommTypes;
    }
    

  public int insertRating(RatingBean rb)
  {
    int outcome = 0;
    int score = 0;
    
    try {      
      score = Integer.parseInt( rb.getScoreStr().trim() );      
      conn = initializeConn();
      
      if(rb.getModule()!=null && rb.getModule().equals("lg")) {
          ps = conn.prepareStatement("insert into REVIEWER_RATINGS (ID, SCORE, DATE_CREATED, "+
          " CREATED_BY, PRA_ID, RAT_ID) values (REV_RATING_SEQ.nextval, ?,sysdate,?,?,?) ");
      }
      else{
        ps = conn.prepareStatement("insert into REVIEWER_RATINGS (ID, SCORE, DATE_CREATED, "+
        " CREATED_BY, GRAAS_ID, RAT_ID) values (REV_RATING_SEQ.nextval, ?,sysdate,?,?,?)");
      }
      
      ps.setInt(1, score); 
      ps.setString(2, rb.getUserid());
      ps.setLong(3, rb.getGrantassign());
      ps.setInt(4, rb.getRatingtype());       
      outcome = ps.executeUpdate();            
    }
    catch(Exception e){
      System.err.println("error insertRating() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }
  
  

  public int updateRating(RatingBean rb)
  {
    int outcome = 0;
    int score = 0;
    
    try{      
      score = Integer.parseInt( rb.getScoreStr().trim());    
      conn = initializeConn();      
      
      if(rb.getModule()!=null && rb.getModule().equals("lg")) {
          ps = conn.prepareStatement("update REVIEWER_RATINGS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
          " where PRA_ID=? and RAT_ID=? ");
      }
      else {
        ps = conn.prepareStatement("update REVIEWER_RATINGS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
        " where GRAAS_ID=? and RAT_ID=? ");
      }
      
      ps.setInt(1, score); 
      ps.setString(2, rb.getUserid());
      ps.setLong(3, rb.getGrantassign());
      ps.setInt(4, rb.getRatingtype());       
      outcome = ps.executeUpdate();     
      
    }catch(Exception e){
      System.err.println("error updateRating() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }
  
  
  public void saveLgRating(HashMap insertRatings, HashMap updateRatings, RatingBean rb)
  {
      int score = 0, rattype=0;
      PreparedStatement psinsert =null;
     
      try{          
        conn = initializeConn();      
               
        ps = conn.prepareStatement("update REVIEWER_RATINGS set SCORE=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
        " where PRA_ID=? and RAT_ID=? ");
        psinsert = conn.prepareStatement("insert into REVIEWER_RATINGS (ID, SCORE, DATE_CREATED, "+
        " CREATED_BY, PRA_ID, RAT_ID) values (REV_RATING_SEQ.nextval, ?, sysdate,?,?,?) ");
           
        Iterator j = insertRatings.keySet().iterator(); 
        while(j.hasNext()) {
            rattype = ((Integer) j.next()).intValue();
            
            score = Integer.parseInt( ((String) insertRatings.get(new Integer(rattype))).trim());
            psinsert.setInt(1, score); 
            psinsert.setString(2, rb.getUserid());
            psinsert.setLong(3, rb.getGrantassign());
            psinsert.setInt(4, rattype);   
            psinsert.addBatch();
        }
                
          Iterator i = updateRatings.keySet().iterator(); 
          while(i.hasNext()){        
              rattype = ((Integer) i.next()).intValue();
              score = Integer.parseInt( ((String) updateRatings.get(new Integer(rattype))).trim());    
              ps.setInt(1, score); 
              ps.setString(2, rb.getUserid());
              ps.setLong(3, rb.getGrantassign());
              ps.setInt(4, rattype);   
              ps.addBatch();
          }
        
        ps.executeBatch();
        psinsert.executeBatch();
        
      }catch(Exception e){
        System.err.println("error saveLgRating() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(psinsert);
        Close(conn);
      }
   }
      
    
  public int updateComment(RatingBean rb)
  {
    int outcome = 0;
    boolean hasRecord = false;
    
    try {      
      conn = initializeConn();
      
      ps = conn.prepareStatement("select id from REVIEWER_COMMENTS where GRAAS_ID=?");
      ps.setLong(1, rb.getGrantassign());
      rs = ps.executeQuery();
      while(rs.next()){
        hasRecord = true;
      }
      ps.clearParameters();
      
      if(hasRecord)
      {
        ps = conn.prepareStatement("update REVIEWER_COMMENTS set REVC_COMMENT=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
          " where GRAAS_ID=? ");
        ps.setString(1, rb.getComment()); 
        ps.setString(2, rb.getUserid());
        ps.setLong(3, rb.getGrantassign());
                 
        outcome = ps.executeUpdate();      
      }
      else
      {            
        ps = conn.prepareStatement("insert into REVIEWER_COMMENTS (ID, REVC_COMMENT, DATE_CREATED, CREATED_BY, GRAAS_ID) "+
          " values (REV_COMMENT_SEQ.nextval, ?,sysdate,?,?) ");
        ps.setString(1, rb.getComment());
        ps.setString(2, rb.getUserid());
        ps.setLong(3, rb.getGrantassign());
        
        outcome = ps.executeUpdate();
      }
      
    } catch(Exception e){
      System.err.println("error updateComment() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
  }



    public int updateLgrmifComment(RatingBean rb, HashMap commentTypes)
    {
      int outcome = 0;    
      PreparedStatement psinsert = null;
      
      try{      
        conn = initializeConn();
        ps = conn.prepareStatement("update REVIEWER_COMMENTS set REVC_COMMENT=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
            " where PRA_ID=? and CT_ID=? ");
        psinsert = conn.prepareStatement("insert into REVIEWER_COMMENTS (ID, REVC_COMMENT, "+
          " DATE_CREATED, CREATED_BY, PRA_ID, CT_ID) values (REV_COMMENT_SEQ.nextval, ?, "+
          " sysdate, ?, ?, ?)");
          
        if(rb.getProblemcomment()!=null && !rb.getProblemcomment().equals("")) {
            if(commentTypes.containsKey(new Integer(1))) {
                ps.setString(1, rb.getProblemcomment());
                ps.setString(2, rb.getUserid());
                ps.setLong(3, rb.getGrantassign());
                ps.setInt(4, 1);
                ps.addBatch();
            }
            else {
                psinsert.setString(1, rb.getProblemcomment());
                psinsert.setString(2, rb.getUserid());
                psinsert.setLong(3, rb.getGrantassign());
                psinsert.setInt(4, 1);
                psinsert.addBatch();
            }
        }
        
       if(rb.getResultcomment()!=null && !rb.getResultcomment().equals("")) {
          if(commentTypes.containsKey(new Integer(2))) {
              ps.setString(1, rb.getResultcomment());
              ps.setString(2, rb.getUserid());
              ps.setLong(3, rb.getGrantassign());
              ps.setInt(4, 2);
              ps.addBatch();
          }
          else {
              psinsert.setString(1, rb.getResultcomment());
              psinsert.setString(2, rb.getUserid());
              psinsert.setLong(3, rb.getGrantassign());
              psinsert.setInt(4, 2);
              psinsert.addBatch();
          }
      }
      
      if(rb.getWorkcomment()!=null && !rb.getWorkcomment().equals("")) {
         if(commentTypes.containsKey(new Integer(3))) {
             ps.setString(1, rb.getWorkcomment());
             ps.setString(2, rb.getUserid());
             ps.setLong(3, rb.getGrantassign());
             ps.setInt(4, 3);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getWorkcomment());
             psinsert.setString(2, rb.getUserid());
             psinsert.setLong(3, rb.getGrantassign());
             psinsert.setInt(4, 3);
             psinsert.addBatch();
         }
      }
      
      
      if(rb.getSupportcomment()!=null && !rb.getSupportcomment().equals("")) {
         if(commentTypes.containsKey(new Integer(4))) {
             ps.setString(1, rb.getSupportcomment());
             ps.setString(2, rb.getUserid());
             ps.setLong(3, rb.getGrantassign());
             ps.setInt(4, 4);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getSupportcomment());
             psinsert.setString(2, rb.getUserid());
             psinsert.setLong(3, rb.getGrantassign());
             psinsert.setInt(4, 4);
             psinsert.addBatch();
         }
      }
      
      if(rb.getBudgetcomment()!=null && !rb.getBudgetcomment().equals("")) {
         if(commentTypes.containsKey(new Integer(5))) {
             ps.setString(1, rb.getBudgetcomment());
             ps.setString(2, rb.getUserid());
             ps.setLong(3, rb.getGrantassign());
             ps.setInt(4, 5);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getBudgetcomment());
             psinsert.setString(2, rb.getUserid());
             psinsert.setLong(3, rb.getGrantassign());
             psinsert.setInt(4, 5);
             psinsert.addBatch();
         }
      }
            
      if(rb.getComment()!=null && !rb.getComment().equals("")) {
         if(commentTypes.containsKey(new Integer(17))) {
             ps.setString(1, rb.getComment());
             ps.setString(2, rb.getUserid());
             ps.setLong(3, rb.getGrantassign());
             ps.setInt(4, 17);
             ps.addBatch();
         }
         else {
             psinsert.setString(1, rb.getComment());
             psinsert.setString(2, rb.getUserid());
             psinsert.setLong(3, rb.getGrantassign());
             psinsert.setInt(4, 17);
             psinsert.addBatch();
         }
      }
      
      ps.executeBatch();
      psinsert.executeBatch();
        
      } catch(Exception e){
        System.err.println("error updateLgrmifComment() " + e.getMessage().toString());
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
   * This will SUBMIT or UNSUBMIT rating form depending on submityn parameter
   * @return 
   * @param submityn
   * @param assignid
   * @param userb
   */
  public int unsubmitRatingForm(UserBean userb, long assignid, boolean submityn, String module)
  {
    int outcome =0;
    
    try{      
      conn = initializeConn();
      
      if(module!=null && module.equals("lg")) {
          ps = conn.prepareStatement("update PANEL_REVIEWER_ASSIGNS set RATING_COMPLETE=?, "+ 
          "  DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");
          ps.setBoolean(1, submityn);
          ps.setString(2, userb.getUserid());
          ps.setLong(3, assignid);
      }
      else
      {
          ps = conn.prepareStatement("update GRANT_ASSIGNS set RATING_COMPLETE=?, COMMENT_COMPLETE=?, "+
            " DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");
          ps.setBoolean(1, submityn);
          ps.setBoolean(2, submityn);
          ps.setString(3, userb.getUserid());
          ps.setLong(4, assignid);
      }      
      outcome = ps.executeUpdate();      
      
    } catch(Exception e){
      System.err.println("error unsubmitRatingForm() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
    }
    return outcome;
  }
  
  
    public int updateConflictInterest(UserBean userb, long assignid, boolean conflictyn)
    {
      int outcome =0;      
      try{      
        conn = initializeConn();
        
        ps = conn.prepareStatement("update PANEL_REVIEWER_ASSIGNS set conflict_interest=?, "+
          " DATE_MODIFIED=SYSDATE, MODIFIED_BY=? where ID=?");
        ps.setBoolean(1, conflictyn);
        ps.setString(2, userb.getUserid());
        ps.setLong(3, assignid);          
        outcome = ps.executeUpdate();      
        
      } catch(Exception e){
        System.err.println("error updateConflictInterest() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
    
    
    public int deleteLgRating(UserBean userb, long assignid)
    {
      int outcome =0;      
      try{      
        conn = initializeConn();
        
        ps = conn.prepareStatement("delete from REVIEWER_RATINGS where pra_id=?");
        ps.setLong(1, assignid);          
        outcome = ps.executeUpdate();     
        
        ps = conn.prepareStatement("delete from REVIEWER_COMMENTS where pra_id=?");
        ps.setLong(1, assignid);          
        outcome = ps.executeUpdate();   
          
        ps = conn.prepareStatement("update PANEL_REVIEWER_ASSIGNS set rating_complete=0, " +
        " final_score=0, recommend_amt=0, date_modified=sysdate, " +
        "modified_by=? where id=?");
        ps.setString(1, userb.getUserid());
        ps.setLong(2, assignid);
        outcome = ps.executeUpdate();
        
      } catch(Exception e){
        System.err.println("error deleteLgRating() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
    

  public ReviewerAssignBean getAssignmentInfo(long assignid, String program)
  {
     ReviewerAssignBean rb = new ReviewerAssignBean();        
    try{      
      conn = initializeConn();
      
      if(program!=null && program.equals("lg")) 
      {
        ps = conn.prepareStatement("select PANEL_REVIEWER_ASSIGNS.ID, RATING_COMPLETE, GRA_ID, "+
        " REV_ID, PROJ_SEQ_NUM, FY_CODE, FC_CODE, conflict_interest, panel_reviewer_assigns.recommendation from " + 
        " PANEL_REVIEWER_ASSIGNS, PANEL_REVIEWERS, PANEL_GRANTS, GRANTS where " + 
        " PANEL_REVIEWER_ASSIGNS.PG_ID = PANEL_GRANTS.ID and PANEL_GRANTS.GRA_ID = GRANTS.ID and "+ 
        " PANEL_REVIEWER_ASSIGNS.PR_ID= PANEL_REVIEWERS.ID and PANEL_REVIEWER_ASSIGNS.ID=?");   
        ps.setLong(1, assignid);
        rs = ps.executeQuery();          
        while(rs.next())
        {
            rb.setAssignid(rs.getLong("ID"));
            rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
            rb.setRevid(rs.getLong("REV_ID"));
            rb.setGraid(rs.getLong("GRA_ID"));
            rb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
            rb.setFycode(rs.getInt("FY_CODE"));
            rb.setFccode(rs.getInt("FC_CODE"));
            rb.setConflictinterest(rs.getBoolean("conflict_interest"));
            rb.setRecommendation(rs.getString("recommendation"));
        }
        
      }
      else
      {
         ps = conn.prepareStatement("select GRANT_ASSIGNS.ID, RATING_COMPLETE, REV_ID, GRA_ID, "+
         " PROJ_SEQ_NUM, FY_CODE, FC_CODE from GRANT_ASSIGNS, GRANTS where "+
         " GRANTS.ID=GRANT_ASSIGNS.GRA_ID and  GRANT_ASSIGNS.ID=? ");
          ps.setLong(1, assignid);
          rs = ps.executeQuery();          
          while(rs.next())
          {
            rb.setAssignid(rs.getLong("ID"));
            rb.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
            rb.setRevid(rs.getLong("REV_ID"));
            rb.setGraid(rs.getLong("GRA_ID"));
            rb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
            rb.setFycode(rs.getInt("FY_CODE"));
            rb.setFccode(rs.getInt("FC_CODE"));
          }      
      }
                  
    }catch(Exception e){
      System.err.println("error getAssignmentInfo() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return rb;
  }

  /**
   * This method will get the reviewer names, the number of grants willing to review, and the number
   * of grants assigned for a given fiscal year.  The fiscal year is based on the fy of the grant you
   * are working on. Info is displayed on the admin assign reviewers page so admin knows who the eligible
   * reviewers are. 
   * @return 
   * @param grantid, grantprogram
   */
  public Vector getAcceptedAssignedByYear(long grantid, String grantprogram)
  {
    Vector results = new Vector();//temp to hold first part of query results
    Vector allRev = new Vector();
    int fycode = 0;
    int fccode=0;
    
    try{      
      conn = initializeConn();
      
      //get the fy of this grant
      ps = conn.prepareStatement("select fy_code from grants where id=?");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      while(rs.next())
      {
        fycode = rs.getInt("fy_code");
      }
      ps.clearParameters();
      
      
      //get the reviewer info and num accepted for fy
      ps = conn.prepareStatement("select fname, lname, interest, num_accepted, rev_id from "+
      " reviewers, grant_assign_maxes where reviewers.id = grant_assign_maxes.rev_id and fy_code=? and grant_program=? order by lname");
      ps.setInt(1, fycode);
      ps.setString(2, grantprogram);
      rs = ps.executeQuery();
      while(rs.next())
      {
        /*ReviewerAssignBean rb = new ReviewerAssignBean();
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));
        rb.setInterest(rs.getString("interest"));
        rb.setRevid(rs.getLong("rev_id"));
        rb.setNumaccepted(rs.getInt("num_accepted")); */        
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));
        rb.setInterest(rs.getString("interest"));
        rb.setRevid(rs.getLong("rev_id"));
        
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setNumaccepted(rs.getInt("num_accepted"));
        ReviewerAssignBean[] allassign = {rab};
        rb.setReviewerAssigns(allassign);
        
        results.add(rb);
      }
     
     if(grantprogram.equalsIgnoreCase("coordinated"))
        fccode=7;
     else
        fccode=5;
            
      //get the num assigned so far for fy for this reviewer
      ps.clearParameters();
      ps = conn.prepareStatement("select count(grant_assigns.id) as numassign, rev_id from "+
      " grant_assigns left join grants on grants.id= grant_assigns.gra_id where fy_code=? and fc_code=? "+
      " and rev_id=? group by rev_id  ");
      ps.setInt(1, fycode);
      ps.setInt(2, fccode);
      
      for(int i=0; i<results.size(); i++)
      {
        //ReviewerAssignBean rb = (ReviewerAssignBean) results.get(i);
        ReviewerBean rb = (ReviewerBean) results.get(i);
        
        ps.setLong(3, rb.getRevid());
        rs = ps.executeQuery();
        while(rs.next())
          rb.reviewerAssigns[0].setNumassigned(rs.getInt("numassign"));
        allRev.add(rb);
      }
    
    }catch(Exception e){
      System.err.println("error getAcceptedAssignedByYear() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allRev;
  }

  

   /**
    * This method will get all reviewers name, interest and total score for a CO grant. 
    * Only get score if rating submitted. The score is the sum of all ratings given by each 
    * reviewer.  Changed 9/8/09, 09-10 have weighted budget ratings and no more overall rating.
    * @return 
    * @param grantid
    */
   public Vector getScoresForCoGrant(long grantid, int fycode)
   {
     Vector allAssign = new Vector();
     
     try {
       conn = initializeConn();
       
       if(fycode <=9)
       {
       // old code that would get sum of all scores
         ps = conn.prepareStatement("select FNAME, LNAME, REV_ID, INTEREST, RATING_COMPLETE, sum(score) as totscore from  "+
         " REVIEWERS, GRANT_ASSIGNS left join reviewer_ratings on grant_assigns.id=reviewer_ratings.graas_id "+
         " where GRA_ID=? and REVIEWERS.ID = GRANT_ASSIGNS.REV_ID group by fname, lname, rev_id, interest, rating_complete");
       }
       else{
           //new 9/8/09 to get weighted score for CO grants starting in 09-10
           ps= conn.prepareStatement("select rev_id, FNAME, LNAME, INTEREST, RATING_COMPLETE,  "+
           " sum(case rat_id when 12 then (score*2) when 13 then (score*2) when 14 then (score*2) "+
           " else score end) totscore from REVIEWERS, GRANT_ASSIGNS left join reviewer_ratings on "+
           " reviewer_ratings.graas_id=grant_assigns.id where GRA_ID=? and REVIEWERS.ID = "+
           " GRANT_ASSIGNS.REV_ID group by rev_id, fname, lname, interest, rating_complete");
       }
       
       ps.setLong(1, grantid);
       rs = ps.executeQuery();        
       while(rs.next())
       {
         ReviewerBean rb = new ReviewerBean();
         rb.setFname(rs.getString("FNAME"));
         rb.setLname(rs.getString("LNAME"));
         rb.setInterest(rs.getString("INTEREST"));
         rb.setRevid(rs.getLong("REV_ID"));
         
           ReviewerAssignBean rab = new ReviewerAssignBean();
           rab.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
           rab.setGraid(grantid);
           //only get score if rating has been submitted
           if(rab.isRatingcomp() )
             rab.setScore(rs.getString("TOTSCORE"));
           else
             rab.setScore("0");  
         ReviewerAssignBean[] allassigns = {rab};          
         rb.setReviewerAssigns(allassigns);            
         allAssign.add(rb);
       }       
         
     } catch(Exception e) {
       System.err.println("error getScoresForCoGrant() " + e.getMessage().toString());
     }
     finally{
       Close(ps);
       Close(conn);
       Close(rs);
     }
     return allAssign;
   }

  public Vector getCommentsForApcnt(long grantid)
  {
    Vector results = new Vector();
    
    try{     
      //get everyone that is assigned to review this grant
      Vector allAssigned = getAllAssignForGrant(grantid);
            
      conn = initializeConn();
      ps = conn.prepareStatement("select revc_comment from REVIEWER_COMMENTS where GRAAS_ID=?");
      
      //get the comment from each reviewer ONLY if they have submitted
      for(int i=0; i<allAssigned.size(); i++)
      {
        //ReviewerAssignBean rb = (ReviewerAssignBean) allAssigned.get(i);
        ReviewerBean rb = (ReviewerBean) allAssigned.get(i);
        
        if(rb.reviewerAssigns[0].isRatingcomp())
        {
          ps.setLong(1, rb.reviewerAssigns[0].getAssignid());
          rs = ps.executeQuery();
          while(rs.next())
          {
            CommentBean cb = new CommentBean();
            cb.setComment(rs.getString("REVC_COMMENT"));
            results.add(cb);
          }
        }
      }
      
      
    }catch(Exception e){
      System.err.println("error getCommentsForApcnt() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    
    return results;
  }

  /**
   * Method will get the sum of all submitted reviewer scores by rating type for a given grant. This is
   * then used on jsp's to calculate the average score by category.(see coViewComments page)
   * @return 
   * @param grantid
   */
  public RatingBean getCatSumScoresByGrant(long grantid)
  {
    RatingBean rb = new RatingBean();
    int sumscore=0;
    
    try{
      conn = initializeConn();
      
      //get all rating scores for this assignment
      ps = conn.prepareStatement("select sum(score) as score, rat_id, count(graas_id) as revs from "+
        " reviewer_ratings, grant_assigns where gra_id=? and reviewer_ratings.GRAAS_ID = "+
        " grant_assigns.id and rating_complete=1 group by reviewer_ratings.RAT_ID");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();      
   
      if(!rs.next())//if no rows returned
      {
        rb.setRatingtype(1);
      }
      
      while(rs.next())
      {
        int ratid = rs.getInt("RAT_ID");
        sumscore += rs.getInt("SCORE");
        rb.setRatingtype(rs.getInt("REVS"));//query returns num of reviewers who submitted ratings, not actual revid
        
        switch(ratid)
        {
          case 1:
            rb.setAppropriate(rs.getInt("SCORE"));
            break;
          case 2:
            rb.setSignificance(rs.getInt("SCORE"));
            break;
          case 3:
            rb.setInvolvement(rs.getInt("SCORE"));
            break;
          case 4:
            rb.setCoordination(rs.getInt("SCORE"));
            break;
          case 5:
            rb.setBibliographic(rs.getInt("SCORE"));
            break;
          case 6:
            rb.setOnlinedb(rs.getInt("SCORE"));
            break;
          case 7:
            rb.setTimetable(rs.getInt("SCORE"));
            break;
          case 8:
            rb.setSoundness(rs.getInt("SCORE"));
            break;
          case 9:
            rb.setEquipment(rs.getInt("SCORE"));
            break;
          case 10:
            rb.setPersonnel(rs.getInt("SCORE"));
            break;
          case 11:
            rb.setStorage(rs.getInt("SCORE"));
            break;
          case 12:
            rb.setConsistentdesc(rs.getInt("SCORE"));
            break;
          case 13:
            rb.setConsistentexp(rs.getInt("SCORE"));
            break;
          case 14:
            rb.setCosteffective(rs.getInt("SCORE"));
            break;
          case 15:
            rb.setOverallscore(rs.getInt("SCORE"));
            break;          
          case 38://for education apps
              rb.setGroupneed(rs.getInt("SCORE"));
              break;
          case 39:
              rb.setGoal(rs.getInt("SCORE"));
              break;
          case 40:
              rb.setPublicity(rs.getInt("SCORE"));
              break;
          case 41:
              rb.setSharing(rs.getInt("SCORE"));
              break;
        }
      }//end while loop
      
      rb.setSumscore(sumscore);
           
    }
    catch(Exception e){
      System.err.println("error getCatSumScoreByGrant() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
  
    return rb;
  }

  /**
   * Method will get all the Co or Di grants and reviewers that have been assigned to that grant for
   * the given fiscalyear
   * @return 
   * @param fycode
   */
  public Vector getAssignForFyFc(int fycode, int fccode)
  {
    Vector results = new Vector();    
    try{             
      conn = initializeConn();

      ps = conn.prepareStatement("select distinct grant_assigns.id, gra_id, fy_code, fc_code, "+
      " proj_seq_num, name, fname, lname, user_id, contact_value from grants, grant_assigns, reviewers "+
      " left join contacts on reviewers.id = contacts.rev_id where contact_type_code=3 and fy_code=? "+
      " and fc_code=? and grant_assigns.gra_id = grants.id and grant_assigns.rev_id=reviewers.ID order by user_id");
           
      ps.setInt(1, fycode);
      ps.setInt(2, fccode);
      rs = ps.executeQuery();
      while(rs.next())
      {
        /*ReviewerAssignBean rb = new ReviewerAssignBean();
        rb.setFycode(rs.getInt("fy_code"));
        rb.setFccode(rs.getInt("fc_code"));
        rb.setProjseqnum(rs.getLong("proj_seq_num"));
        rb.setTitle(rs.getString("name"));
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));  */
        
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("fname"));
        rb.setLname(rs.getString("lname"));
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setFycode(rs.getInt("fy_code"));
          rab.setFccode(rs.getInt("fc_code"));
          rab.setProjseqnum(rs.getLong("proj_seq_num"));
          rab.setTitle(rs.getString("name"));
          
        ReviewerAssignBean[] allassign = {rab};
        rb.setReviewerAssigns(allassign);
        
        results.add(rb);        
      }    
              
    }catch(Exception e) {
      System.err.println("error getAssignForFyFc() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return results;
  }

  /**
   * Method will get name and email of reviewers that have received assignements for
   * the given fy. This method is used as a helper to send Assignment Emails.
   * @return 
   * @param fycode, fccode
   */
  public Vector getRevForFyFc(int fycode, int fccode)
  {  
     Vector results = new Vector();
    try{             
      conn = initializeConn();

      ps = conn.prepareStatement("select reviewers.id, salutation, fname, lname, user_id, contact_value from "+
      " reviewers, contacts where reviewers.id = contacts.rev_id and contact_type_code=3 and rev_id in "+
      " (select rev_id from grant_assigns, grants where grant_assigns.GRA_ID=grants.id and fy_code=? and fc_code=?)  ");
           
      ps.setInt(1, fycode);
      ps.setInt(2, fccode);
      rs = ps.executeQuery();
      while(rs.next())
      {        
        ReviewerBean rb = new ReviewerBean();
        rb.setRevid(rs.getLong("ID"));
        rb.setSalutation(rs.getString("SALUTATION"));
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setUsername(rs.getString("USER_ID"));
        rb.setEmail(rs.getString("CONTACT_VALUE"));
        
        results.add(rb);        
      }    
              
    }catch(Exception e){
      System.err.println("error getRevForFY() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    
    return results;
  }

  public int assignPrgmOfficerAsRev(long grantid, UserBean userb)
  {
    int outcome =0; 
    long revid = 0;
    
    try{
      conn = initializeConn();
      
      //get the revID of the program officer (should prob have a field designating po, not this query
      // b/c the PO will change eventually, then this needs to be updated/redeployed)
      ps = conn.prepareStatement("select id from reviewers where user_id='blilley' ");
      rs = ps.executeQuery();
      
      while(rs.next())
        revid = rs.getLong("id");
        
        
      //assign the po to review this Co grant
      ps.clearParameters();
      ps = conn.prepareStatement("insert into GRANT_ASSIGNS (ID, REV_ID, GRA_ID, DATE_CREATED, "+
        " CREATED_BY) values (GRA_ASSIGN_SEQ.nextval, ?,?,SYSDATE,?)");
      ps.setLong(1, revid);
      ps.setLong(2, grantid);
      ps.setString(3, userb.getUserid());
      
      outcome = ps.executeUpdate();      
            
    } catch(Exception e){
      System.err.println("error assignPrgmOfficerAsRev() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
  }

  public Vector getCommentsForAdmin(long grantid)
  {
    Vector results = new Vector();
    
    try {         
      conn = initializeConn();
      ps = conn.prepareStatement("select RATING_COMPLETE, GRA_ID, REV_ID, FNAME, LNAME, "+
      " REVIEWER_COMMENTS.ID, REVC_COMMENT from GRANT_ASSIGNS, REVIEWERS, REVIEWER_COMMENTS where "+
      " GRA_ID=? and RATING_COMPLETE=1 and REVIEWERS.ID = GRANT_ASSIGNS.REV_ID and "+
      " GRANT_ASSIGNS.ID = REVIEWER_COMMENTS.GRAAS_ID");
      ps.setLong(1, grantid);           
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new ReviewerBean();
        rb.setRevid(rs.getLong("REV_ID"));
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setCommentId(rs.getLong("ID"));
          rab.setComment(rs.getString("REVC_COMMENT"));  
          rab.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
          rab.setGraid(rs.getLong("GRA_ID"));
          
        ReviewerAssignBean[] allassign = {rab};
        rb.setReviewerAssigns(allassign);        
        
        results.add(rb);
      }     
      
    }catch(Exception e){
      System.err.println("error getCommentsForAdmin() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }    
    return results;
  }

  public CommentBean getRevComment(long commentId)
  {
    CommentBean cb = new CommentBean();
    
    try {
      conn = initializeConn();
      ps = conn.prepareStatement("select * from reviewer_comments where id=?");
      ps.setLong(1, commentId);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        cb.setId(rs.getLong("id"));
        cb.setComment(rs.getString("revc_comment"));
      }
    
    }catch(Exception e){
      System.err.println("error getRevComment() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return cb;
  }

  public int updateRevComment(UserBean userb, CommentBean cb)
  {
    int outcome = 0;
    
    try {      
        conn = initializeConn();      
     
        ps = conn.prepareStatement("update REVIEWER_COMMENTS set REVC_COMMENT=?, DATE_MODIFIED=SYSDATE, MODIFIED_BY=? "+
          " where ID=? ");
        ps.setString(1, cb.getComment()); 
        ps.setString(2, userb.getUserid());
        ps.setLong(3, cb.getId());
                 
        outcome = ps.executeUpdate();                  
            
    } catch(Exception e){
      System.err.println("error updateRevComment() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return outcome;
  }


  /**
   * DI reveiwer rating form has sections weighted.  Method calculates weights for Di
   * admin, di reports. Used for both DI rating forms (normal and education)
   * @return 
   * @param grantid
   */
  public Vector getScoresForDiWeighted(long grantid)
  {
    Vector allAssign = new Vector();
    
    try {
      conn = initializeConn();
      
      ps = conn.prepareStatement("select REVIEWERS.ID AS rid, FNAME, LNAME, INTEREST, GRANT_ASSIGNS.ID, "+
        " RATING_COMPLETE from REVIEWERS, GRANT_ASSIGNS where GRA_ID=? and REVIEWERS.ID = "+
        " GRANT_ASSIGNS.REV_ID ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setInterest(rs.getString("INTEREST"));
        rb.setRevid(rs.getLong("rid"));
        
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
          rab.setAssignid(rs.getLong("id"));
          rab.setGraid(grantid);
        
        ReviewerAssignBean[] allassigns = {rab};
        rb.setReviewerAssigns(allassigns);
        
        allAssign.add(rb);
      }      
      
      ps.clearParameters();
      ps = conn.prepareStatement("select * from reviewer_ratings where graas_id=?");
            
      for(int i=0; i<allAssign.size(); i++)
      {
        double sumscore =0;
        //ReviewerAssignBean r = (ReviewerAssignBean) allAssign.get(i);
        ReviewerBean r = (ReviewerBean) allAssign.get(i);
        ReviewerAssignBean[] ra = r.getReviewerAssigns();
        
        if(ra[0].isRatingcomp())
        {
              ps.setLong(1, ra[0].getAssignid());
              rs = ps.executeQuery();
              while(rs.next())
              {
                int ratType = rs.getInt("rat_id");
                int score = rs.getInt("score");
                
                switch(ratType){
                  case 1:
                  case 2:
                  case 7:
                  case 8:
                  case 10:
                      sumscore = sumscore + (score*3);
                      break;
                  case 12:
                  case 13:
                  case 14:
                      sumscore = sumscore + (score*2.23);
                      break;
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                      sumscore = sumscore + (score*3.25);
                      break;
                  default:
                      sumscore = sumscore + score;
                      break;
                }
              }
        }
        int roundscore = (int)Math.round(sumscore);
        ra[0].setScore(Integer.toString(roundscore));                
      }
      
    } catch(Exception e) {
      System.err.println("error getScoresForDiWeighted() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allAssign;
  }



  /**
   * Get all reviewers assigned to al/fl grant, and all scores (only if they submitted form).
   * Total score is sum of all scores EXCEPT for the overall score of 1-10. 
   * @return 
   * @param grantid
   */
  public Vector getScoresForLit(long grantid)
  {
    Vector allAssign = new Vector();
    
    try {
      conn = initializeConn();      
      ps = conn.prepareStatement("select REVIEWERS.ID AS rid, FNAME, LNAME, GRANT_ASSIGNS.ID, "+
        " RATING_COMPLETE from REVIEWERS, GRANT_ASSIGNS where GRA_ID=? and REVIEWERS.ID = "+
        " GRANT_ASSIGNS.REV_ID ");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new ReviewerBean();
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setRevid(rs.getLong("rid"));
          ReviewerAssignBean rab = new ReviewerAssignBean();
          rab.setRatingcomp(rs.getBoolean("RATING_COMPLETE"));
          rab.setAssignid(rs.getLong("id"));
          rab.setGraid(grantid);
        
        ReviewerAssignBean[] assignarray = {rab};
        rb.setReviewerAssigns(assignarray);
        
        allAssign.add(rb);
      }      
      
      ps.clearParameters();
      ps = conn.prepareStatement("select * from reviewer_ratings where graas_id=?");
            
      for(int i=0; i<allAssign.size(); i++)
      {
        int sumscore =0;
        //ReviewerAssignBean r = (ReviewerAssignBean) allAssign.get(i);
        ReviewerBean r = (ReviewerBean) allAssign.get(i);
        
        if(r.reviewerAssigns[0].isRatingcomp())
        {
              ps.setLong(1, r.reviewerAssigns[0].getAssignid());
              rs = ps.executeQuery();
              while(rs.next())
              {
                int ratType = rs.getInt("rat_id");
                int score = rs.getInt("score");
                
                switch(ratType){                  
                  case 15:
                      r.reviewerAssigns[0].setOverallScore(Integer.toString(score));
                      break;
                  default:
                      sumscore = sumscore + score;
                      break;
                }
              }
        }
        r.reviewerAssigns[0].setScore(Integer.toString(sumscore));                
      }
      
    } catch(Exception e) {
      System.err.println("error getScoresForLit() " + e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }
    return allAssign;
  }

  /**
   * Method used to print DI scores by category to DI applicants.
   * @return 
   * @param grantid
   */
  public RatingBean getCatSumScoresDi(long grantid)
  {
    HashMap results = new HashMap(); //hold assignment id's, rev score
    RatingBean rb = new RatingBean();
    double instcp =0;
    double access =0;
    double research =0;
    double planwork =0;
    double contrib =0;
    double budget =0;
    double overall =0;
    double educ =0;
    
    try{
        conn = initializeConn();      
        //get all submitted assignments for this grant
        ps = conn.prepareStatement("select id, rating_complete from grant_assigns where gra_id=? "+
        " and rating_complete=1");            
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next()){
          results.put(rs.getString("id"), new Integer(0));//grant_assigns.id
        }     
    //-----------------------------------------------------------------------        
        ps.clearParameters();
        ps = conn.prepareStatement("select * from reviewer_ratings where graas_id=?");
        
        //calc the avg score from each rev assignment; also avg score by category
        Iterator it = results.keySet().iterator();
        while(it.hasNext())
        {
          double sumscore = 0;  
          String assignid = (String) it.next();
          ps.setString(1, assignid);
          rs = ps.executeQuery();
          
          while(rs.next())
          {
            int ratType = rs.getInt("rat_id");
            int score = rs.getInt("score");
            
            switch(ratType){//get total score for reviewer
              case 1:
              case 2:
              case 7:
              case 8:
              case 10:
                  sumscore = sumscore + (score*3);
                  break;
              case 12:
              case 13:
              case 14:
                  sumscore = sumscore + (score*2.23);
                  break;
              case 38:
              case 39:
              case 40:
              case 41:
                  sumscore = sumscore + (score*3.25);
                  break;
              default:
                  sumscore = sumscore + score;
                  break;
            }           
            
            switch(ratType){//get total score for category
              case 21:
              case 11:
              case 20:
              case 19:
              case 22:
                instcp += score;//category 1 Inst commitment to cp work
                break;
              case 18:
              case 5:
                access += score;//category 2 accessibility of collections
                break;
              case 1:
              case 2:
                research = research + (score*3);//category 3 research value of materials
                break;
              case 7:
              case 8:
              case 10:
                planwork = planwork + (score*3);//category 4 plan of work
                break;
              case 16:
              case 17:
                contrib += score;//category 5 inst contribution
                break;
              case 12:
              case 13:
              case 14:
                budget = budget + (score*2.23);//cat 6 budget
                break;
              case 38:
              case 39:
              case 40:
              case 41:
                educ = educ + (score*3.25);//education app category
                break;
              case 15:
                overall += score;
                break;
            }//end switch stmt
          }                
          int roundscore = (int)Math.round(sumscore);
          results.put(assignid, new Integer(roundscore));        
        }
                       
      //calc the avg score of all reviewer scores
      int totalReviews = results.size();
      if(results.size() >0)
      {        
        int totalScore = 0;
        Iterator scoreIt = results.keySet().iterator();
        while(scoreIt.hasNext())
        {
          totalScore += ((Integer) results.get(scoreIt.next())).intValue();
        }
        int avgscore = totalScore/totalReviews;
        rb.setScoreStr(Integer.toString(avgscore));
      }           
      
      //calc avg score by category
      rb.setInstcpStr(Double.toString(instcp/totalReviews));
      rb.setAvailabilityStr(Double.toString(access/totalReviews));
      rb.setSignificanceStr(Double.toString(research/totalReviews));
      rb.setTimetableStr(Double.toString(planwork/totalReviews));
      rb.setFinancialsupportStr(Double.toString(contrib/totalReviews));
      rb.setConsistentexpStr(Double.toString(budget/totalReviews));
      rb.setOverallscoreStr(Double.toString(overall/totalReviews));
      rb.setEducationStr(Double.toString(educ/totalReviews));      
      
    }catch(Exception e){
      System.out.println("error getCatSumScoresDi() "+e.getMessage().toString());
    }
    finally{
      Close(conn);
      Close(rs);
      Close(ps);
    }      
    return rb;
  }

  public int setReviewDueDate(String duedate, int fycode, int fccode)
  {
    int outcome =0;
    
    try{
      conn = initializeConn();
      ps = conn.prepareStatement("update app_dates set end_date=to_date(?, 'mm/dd/yy') where fc_code=? and fy_code=?");
      ps.setString(1, duedate);
      ps.setInt(2, fccode);
      ps.setInt(3, fycode);
      
      outcome = ps.executeUpdate();
    }
    catch(Exception e){
      System.out.println("error setReviewerDueDate "+e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }  
    return outcome;
  }

 
  
  public ArrayList listPotentialAssignments(int fycode, int fccode, long revid)
  {
    ArrayList results = new ArrayList();    
    try{
      conn = initializeConn();
            
      ps = conn.prepareStatement("select distinct GRANTS.ID, FC_CODE, FY_CODE, PROJ_SEQ_NUM, NAME, "+
        " CO_INSTITUTIONS.INST_ID, initcap(POPULAR_NAME) as popular_name from GRANTS, CO_INSTITUTIONS left join "+
        " SED_INSTITUTIONS on sed_institutions.inst_id=co_institutions.inst_id where GRANTS.ID = "+
        " CO_INSTITUTIONS.GRA_ID and FC_CODE=? and FY_CODE=? and IS_LEAD='Y' and GRANTS.ID in "+
        " (select GRA_ID from GRANT_SUBMISSIONS where VERSION='Initial' )");
      ps.setInt(1, fccode);
      ps.setInt(2, fycode);
      rs = ps.executeQuery();
      while(rs.next())
      {
        ReviewerAssignBean rab = new ReviewerAssignBean();
        rab.setGraid(rs.getLong("id"));
        rab.setFccode(rs.getInt("fc_code"));
        rab.setFycode(rs.getInt("fy_code"));
        rab.setProjseqnum(rs.getInt("proj_seq_num"));
        rab.setTitle(rs.getString("name"));
        rab.setInstname(rs.getString("popular_name"));
        rab.setRevid(revid);       
        results.add(rab);
      }
            
    }
    catch(Exception e){
      System.out.println("error listPotentialAssignments() "+e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    }  
    return results;
  }
  
  //updated 11/12/15 to add bonus pts
    public int updateLgRecommendation(RatingBean rb)
    {
      int outcome = 0;            
      try{        
        long recamt = 0;
        DBHandler dbh = new DBHandler();
        if(rb.getRecommendamtStr()!=null &&  !rb.getRecommendamtStr().equals(""))
            recamt = dbh.parseCurrencyAmtNoDecimal(rb.getRecommendamtStr());
        conn = initializeConn();              
       
        ps = conn.prepareStatement("update panel_reviewer_assigns set recommendation=?, "+
        " recommend_amt=?, date_modified=sysdate, modified_by=?, bonus_points=? where id=?");       
        ps.setString(1, rb.getRecommendation()); 
        ps.setLong(2, recamt);
        ps.setString(3, rb.getUserid());
        ps.setInt(4, rb.getBonusScore());
        ps.setLong(5, rb.getGrantassign());       
        outcome = ps.executeUpdate();     
        
      }catch(Exception e){
        System.err.println("error updateLgRecommendation() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
      }
      return outcome;
    }
    
  
  public ArrayList handlePotentialAssignments(int fycode, int fccode, int revid)
  {
    ArrayList results=null;
    
    try{      
      results = listPotentialAssignments(fycode, fccode, revid);
      //get any grants already assigned
      Vector allAssign = getAllAssignForReviewer(revid, Integer.toString(fccode), fycode);
            
      //combine 2 datasets
      for(int i=0; i<results.size(); i++)
      {
        Iterator ait = allAssign.iterator();
        ReviewerAssignBean rab = (ReviewerAssignBean) results.get(i);
        while(ait.hasNext())
        {
          ReviewerAssignBean rb = (ReviewerAssignBean) ait.next();
          if(rb.getGraid()==rab.getGraid())
          {
            rab.setAssignid(rb.getAssignid());
            rab.setRevassign(true);
          }
        }
      }
      
    }catch(Exception e){
    System.out.println("error handlePotentialAssignments() "+e.getMessage().toString());}
    return results; 
  }


}