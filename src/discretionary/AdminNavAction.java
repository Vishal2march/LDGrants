/******************************************************************************
 * @Author: Chiranjeevi Saride
 * @version: 3.0
 * 
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminNavAction.java
 * Creation/Modification History  :
 * SH   4/21/16      Modified
 *
 * Description: Jira ID #LD-61 
 * update such that a grant application will only show in either submitted or approved. 
 * For example, once the application is approved, it should not appear in submitted, and should only show in approved.
 * Similar with the yearly reporting, only show in submitted or approved. After a yearly report has been approved, 
 * then it should not show in submitted, and only show in approved.
 * 
 * *****************************************************************************/

/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminNavAction.java
 * Creation/Modification History  :
 * SH   1/13/09      Created
 *
 * Description
 * This servlet will route sa,co,di/fl/al/lg admin to the admin home, checkstatus, 
 * coversheet, attachments, budget screens. 
 *****************************************************************************/
package discretionary;
import clobpackage.ClobBean;

import construction.AllocationYearBean;
import construction.CnApplicationBean;
import construction.ConstructionDBbean;

import coordinated.AdminDB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.AuthorizationBean;
import mypackage.AuthorizationsDBbean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.BudgetDeleteBean;
import mypackage.CommentDBbean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.EligibilityDbBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.LitEmailNotifyBean;
import mypackage.NarrInstructionBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.PanelDbBean;
import mypackage.PanelReviewBean;
import mypackage.PartInstDBbean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.SubmissionDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminNavAction extends DispatchAction
{
   
  private  DBHandler dbh = new DBHandler();
  private OfficerDBbean odb = new OfficerDBbean();
  private BudgetDBHandler bdh = new BudgetDBHandler();  
  private Log log = LogFactory.getLog(this.getClass());
 
  public ActionForward home(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        String fc = request.getParameter("m");
        int fccode = Integer.parseInt(fc);
         
        AdminDB adb = new AdminDB();
        Vector allGrants = adb.getSubmittedApplications(fccode);          
        
        if(fccode==7 && (allGrants.size()>0))//for coordinated only
        {
          //get the total requested for each grant
          for(int i=0; i< allGrants.size(); i++)
          {
            GrantBean gb = (GrantBean) allGrants.get(i);
            int amt = bdh.calcTotalAmtRequested(gb.getGrantid(), gb.getFycode());
            int apramt =bdh.calcTotalAmtApproved(gb.getGrantid(), gb.getFycode());
            gb.setTotamtreq(amt);
            gb.setTotamtappr(apramt);
          }
          
           //get total amount available for fy (amtavail - amtAppr)
            GrantBean g = (GrantBean) allGrants.get(0);
            TotalsBean tb = bdh.getTotalCoAmtAvailableForFY(g.getFycode());     
            request.setAttribute("totAvail", tb);
        }
        request.setAttribute("allGrants", allGrants); 
        
        Vector finGrants = adb.getSubmittedFinalApps(fccode);
        request.setAttribute("finalGrants", finGrants);        
        
        Vector apprGrants = adb.getWaitingForFinalApplications(fccode);
        request.setAttribute("apprGrants", apprGrants);
          
        if(fccode==40 || fccode==42){
          Vector yr3readyGrants = adb.getLitFinalCompleteReadyNextYear(fccode);
          request.setAttribute("year3Grants", yr3readyGrants);
        }
          
        
        if(fccode==6 || fccode==7 || fccode==5 || fccode==80){
            Vector amendGrants = adb.getAmendmentsSubmitted(fccode);
            request.setAttribute("amendGrants", amendGrants);
        }
        
          //get grants closed for this FY (final app approved or denied or declined)
          HashMap results = adb.getClosedGrants(fccode);
          if(results.containsKey(new Integer(1)))//finappr grants exist
          {
            Vector grants = (Vector) results.get(new Integer(1));
            Collections.sort(grants, GrantBean.FiscalYearCodeComparator);
            request.setAttribute("closeGrants", grants);
          }
          if(results.containsKey(new Integer(2)))
          {
            Vector grants = (Vector) results.get(new Integer(2));
            Collections.sort(grants, GrantBean.FiscalYearCodeComparator);
            request.setAttribute("denyGrants", grants); 
          } 
          
        if(fccode==80 || fccode==5){
            //for lgrmif/disc; get declined grants and display in separate list
            Vector declinedgrants = adb.getDeclinedGrants(fccode);
            request.setAttribute("declineGrants", declinedgrants);
        }
                      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
                
      return mapping.findForward("home");            
    }
  
  
  public ActionForward grant(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
        //set the grant id in session
        String grantnum= request.getParameter("id");
        sess.setAttribute("grantid", grantnum);
        long grantid = Long.parseLong(grantnum);
          
        // sets the bean 'thisGrant' to the session          
        GrantBean gb = dbh.getRecordBean(grantid);
        sess.setAttribute("thisGrant", gb);
        //for new struts2 admin pages - need "grantBean" variable in session manager
        sess.setAttribute("grantBean", gb);
        
        AppStatusBean asb= dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
        
        SubmissionDBbean sb = new SubmissionDBbean();
        Vector allSubmissions = sb.getSubmissions(grantid); 
        request.setAttribute("allSubmissions", allSubmissions);
          
        //added 3/18/13 to check for and add new sedref director fk to grant_admins table
        UserBean userb = (UserBean) sess.getAttribute("lduser");  
        OfficerDBbean odb = new OfficerDBbean();
        odb.verifyCurrentDirector(grantid, gb.getInstID(), userb);
        
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("grant");      
    }
  
  
  
  public ActionForward checklist(ActionMapping mapping, ActionForm form,
	         HttpServletRequest request, HttpServletResponse response) throws Exception 
	    {       
	      HttpSession sess = request.getSession(); 
	      if(checkSessionTimeout(sess))
	        return mapping.findForward("timeout");
	        
	      try{ 
	    	if(sess.getAttribute("grantid")==null || sess.getAttribute("grantid").equals(""))
	    	        return mapping.findForward("newhome");//need to choose a grant first
	    	
	        //get the grant id from session
	    	String grantnum= (String) sess.getAttribute("grantid");
	        long grantid = Long.parseLong(grantnum);
	          
	        // sets the bean 'thisGrant' to the session          
	        GrantBean gb = dbh.getRecordBean(grantid);
	        sess.setAttribute("thisGrant", gb); 
	        
	        AppStatusBean asb= dbh.getApplicationStatus(grantid);
	        request.setAttribute("appStatus", asb);
	        
	        SubmissionDBbean sb = new SubmissionDBbean();
	        Vector allSubmissions = sb.getSubmissions(grantid); 
	        request.setAttribute("allSubmissions", allSubmissions);
	          
	        //added 3/18/13 to check for and add new sedref director fk to grant_admins table
	        UserBean userb = (UserBean) sess.getAttribute("lduser");  
	        OfficerDBbean odb = new OfficerDBbean();
	        odb.verifyCurrentDirector(grantid, gb.getInstID(), userb);
	        
	      }catch(Exception e) { log.error(e.getMessage().toString()); }
	      return mapping.findForward("grant");      
	    }
  
  
  public ActionForward attachment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
        Vector results = dbh.getAllDocuments(grantid);
        request.setAttribute("allDocs", results);
      
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("attachment");      
    }
    
    public ActionForward statistics(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
      String finalTarget="";
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
          //get all statistics 
          StatisticDBbean sdb = new StatisticDBbean();
          StatisticBean sb = sdb.getStatisticsForGrant(grantid);
          request.setAttribute("StatBean",sb);
          
        if(gb.getFccode()==42 && gb.getFycode()>13)
            finalTarget="statisticsnew";//new stat form for FL starting fy2013-14
        else
           finalTarget="statistics";
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward(finalTarget);      
    }
    
    
    public ActionForward viewProjManager(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
         String module = request.getParameter("m");
         CoversheetBean csb = new CoversheetBean();      
         csb.setModule(module);
         csb.setGrantid(grantid);                 
         csb= odb.getProjectManager(csb);
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("projManager");      
    }
    
    
    public ActionForward viewContact(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
         String module = request.getParameter("m");
         CoversheetBean csb = new CoversheetBean();      
         csb.setModule(module);
         csb.setGrantid(grantid);                 
         csb= odb.getAdditionalContact(csb,3 );
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("contact");      
    }
    
    
    //LGRMIF admin view scores/assigns-links to update/delete ratings, conflictInterest
    public ActionForward scores(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         Vector allassign = rab.getAllAssignForLgGrant(grantid);
         request.setAttribute("allAssigns", allassign);
          
         long panelgrantid=0;
         if(allassign!=null && allassign.size()>0)
            panelgrantid= ((RatingBean) allassign.get(0)).getPanelgrantId();
         request.setAttribute("panelgrant", String.valueOf(panelgrantid));
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("scores");      
    }
    
    //LGRMIF conflict interest true/false
    public ActionForward updateconflict(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         UserBean userb = (UserBean) sess.getAttribute("lduser");
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         String panelrevassignid = request.getParameter("praid");
         String currentconflict = request.getParameter("conf");
         boolean currconf=false;
         if(currentconflict !=null)
            currconf = Boolean.valueOf(currentconflict);
         
         //update t/f conflict interest
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         rab.updateConflictInterest(userb, Long.parseLong(panelrevassignid), currconf);
         
          //return to grant checkstatus page
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb); 
           AppStatusBean asb= dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
           
           SubmissionDBbean sb = new SubmissionDBbean();
           Vector allSubmissions = sb.getSubmissions(grantid); 
           request.setAttribute("allSubmissions", allSubmissions);
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("grant");      
    }
    
    
    //for lgrmif admin to update the at-home recommendation and amt
    public ActionForward evalform(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         String panelrevassignid = request.getParameter("praid");
                  
         //get comments, recommendations
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         RatingBean rb = rab.getGrantRatingsForLGReviewer(Long.parseLong(panelrevassignid));
         EligibilityDbBean edb = new EligibilityDbBean();
         CoversheetBean csb = edb.getLgBonusScoring(grantid);//lgrmif needs bonus pts     
         request.setAttribute("CoverBean", csb);      
         rb.setBonuspts(csb.getScore());
         
         rb.setTotamtreq(bdh.calcTotalAmtRequested(grantid, 0));    
         request.setAttribute("RatingBean", rb);
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("evalform");      
    }
    
    
    //lgrmif admin can update recomm and amt from reviewer's eval form
    public ActionForward updateeval(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         UserBean userb = (UserBean) sess.getAttribute("lduser");
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);         
         ActionForm myform = (ActionForm) request.getAttribute("RatingBean");
         RatingBean rb = (RatingBean) myform;
         
         ActionErrors ae = rb.validateLgAdminRating(mapping, request);
         if(ae !=null && (ae.size() > 0)){
            request.setAttribute(Globals.ERROR_KEY, ae); 
            return (mapping.findForward("evalform"));
         }  
         
          //update recommendation, amt
          ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
          rb.setUserid(userb.getUserid());
          rdb.updateLgRecommendation(rb);
          
          //get all assigns and redisplay
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
             
          Vector allassign = rdb.getAllAssignForLgGrant(grantid);
          request.setAttribute("allAssigns", allassign);
           
          long panelgrantid=0;
          if(allassign!=null && allassign.size()>0)
             panelgrantid= ((RatingBean) allassign.get(0)).getPanelgrantId();
          request.setAttribute("panelgrant", String.valueOf(panelgrantid));
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("scores");      
    }
    
    
    //lgrmif admin can update recomm and amt from delib form
    public ActionForward delibform(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= request.getParameter("gid");
         long grantid = Long.parseLong(grantnum);     
         String pangraid = request.getParameter("pgid");
                          
         GrantBean gb=dbh.getRecordBean(grantid); 
         request.setAttribute("thisGrant", gb);   
          
         //get any comments/ratings panel has made for this grant
         PanelDbBean pdb = new PanelDbBean();
         PanelReviewBean pb = pdb.getDeliberationPanelGrant(Long.parseLong(pangraid));
          
         int totalAmtAppr = bdh.calcTotalAmtApproved(grantid, 0);//from budget pages
         pb.setTotamtappr(totalAmtAppr);
         pb.setTotamtreq(bdh.calcTotalAmtRequested(grantid, 0)); 
          
         //get bonus scoring
          CoversheetBean csb =new CoversheetBean();
          if(gb.getFycode()<12){
             EligibilityDbBean edb = new EligibilityDbBean();
             csb = edb.getLgBonusScoring(grantid);
             request.setAttribute("CoverBean", csb);                   
          }    
                   
         //find out if application was approved or denied   
         ApprovalsDBbean adb = new ApprovalsDBbean();
         pb.setInitialappr( adb.isInitialAppApproved(grantid) );
         pb.setBonuspts(csb.getScore());             
         request.setAttribute("PanelReviewBean", pb);
          
         //get averages of all reviewer ratings/recommended amts for this grant
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         RatingBean rb =rab.getLgAvgRatingByCategory(Long.parseLong(pangraid), gb.getFycode());
         request.setAttribute("RatingBean", rb);
                 
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("delibform");      
    }
    
    
    public ActionForward updatedelib(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);     
          ActionForm myform = (ActionForm) request.getAttribute("PanelReviewBean");
          PanelReviewBean rb = (PanelReviewBean) myform;
          
          ActionErrors ae = rb.validate(mapping, request);
          if(ae !=null && (ae.size() > 0)){
             request.setAttribute(Globals.ERROR_KEY, ae); 
             return (mapping.findForward("delibform"));
          }  
                  
          //update panel score/justifications
          PanelDbBean pdb = new PanelDbBean();
          rb.setUserid(userb.getUserid());   
          pdb.updateDeliberationPanelGrant(rb, userb);
          
          //get all assigns and redisplay
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
             
          ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
          Vector allassign = rdb.getAllAssignForLgGrant(grantid);
          request.setAttribute("allAssigns", allassign);
           
          long panelgrantid=0;
          if(allassign!=null && allassign.size()>0)
             panelgrantid= ((RatingBean) allassign.get(0)).getPanelgrantId();
          request.setAttribute("panelgrant", String.valueOf(panelgrantid));
          
                 
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("scores");      
    }
    
    //for lgrmif admin to remove any ratings/comments reviewer made
    public ActionForward deleterating(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         UserBean userb = (UserBean) sess.getAttribute("lduser");
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         String panelrevassignid = request.getParameter("praid");
                  
         //delete ratings, comments, recommendations
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         rab.deleteLgRating(userb, Long.parseLong(panelrevassignid));
         
          //return to grant checkstatus page
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb); 
           AppStatusBean asb= dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
           
           SubmissionDBbean sb = new SubmissionDBbean();
           Vector allSubmissions = sb.getSubmissions(grantid); 
           request.setAttribute("allSubmissions", allSubmissions);
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("grant");      
    }
    
    
    public ActionForward unsubmit(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         UserBean userb = (UserBean) sess.getAttribute("lduser");
         String panelrevassignid = request.getParameter("praid");
         
         //set rating-complete = false            
         ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
         rab.unsubmitRatingForm(userb, Long.parseLong(panelrevassignid), false, "lg");
         
         //return to grant checkstatus page
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
          AppStatusBean asb= dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          SubmissionDBbean sb = new SubmissionDBbean();
          Vector allSubmissions = sb.getSubmissions(grantid); 
          request.setAttribute("allSubmissions", allSubmissions);
         
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("grant");      
    }
    
    
    //lgrmif admin update bonus points, PM, RMO, category, region, etc.
     public ActionForward bonuspts(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
        
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb); 
        
        CoversheetBean csb = new CoversheetBean();
        csb.setSedrefinstid(gb.getInstID());
        csb.setGrantid(grantid);          
        csb.setFycode(gb.getFycode());
        csb= odb.getProjectManager(csb);
        csb= odb.getAdditionalContact(csb, 1);
         
        EligibilityDbBean edb = new EligibilityDbBean();
        csb = edb.getArchivesGovtInfo(csb);
        request.setAttribute("coversheetBean", csb);
        
        HashMap listmap =dbh.populateDropDownList(request);
        sess.setAttribute("dropDownLists", listmap);  
      
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("bonuspts");      
    }
    
    
    
    public ActionForward updatebonuspts(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
        UserBean userb = (UserBean) sess.getAttribute("lduser");  
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
        int outcome=0; 
        
        EligibilityDbBean edb = new EligibilityDbBean();
        ActionForm myform = (ActionForm) request.getAttribute("coversheetBean");
        CoversheetBean adminform = (CoversheetBean) myform;
        
          //insert or update the pm
          if(adminform.getPmId()==0)
            outcome = odb.insertProjectManager(grantid, userb, adminform);
          else
            outcome = odb.updateProjectManager(userb, adminform);
            
          //insert or update the RMO
          if(adminform.getRmoId()==0)
            outcome = odb.insertAdditionalContact(grantid, userb, adminform, 1);
          else
            outcome = odb.updateAdditionalContact(userb, adminform);
                   
        //update proj category and bonus pts (in govt_infos)
        edb.updateLgBonusScoring(adminform, userb);
        
        //get all info and redisplay admin update cover page
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb); 
                         
          CoversheetBean csb = new CoversheetBean();
          csb.setSedrefinstid(gb.getInstID());
          csb.setGrantid(grantid);                 
          csb.setFycode(gb.getFycode());
          csb= odb.getProjectManager(csb);
          csb= odb.getAdditionalContact(csb, 1);           
          csb = edb.getArchivesGovtInfo(csb);//everything from govt_infos table
          request.setAttribute("coversheetBean", csb);
          
        HashMap listmap =dbh.populateDropDownList(request);
        sess.setAttribute("dropDownLists", listmap);  
                    
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("bonuspts");      
    }
    
    public ActionForward budget(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      String finalTarget="budget";
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String m=request.getParameter("m");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
          
          boolean splittypecodes=false;
          //al/fl now split by type codes 5/26/10
          if(asb.getFccode()==80 || asb.getFccode()==40 || asb.getFccode()==42) 
            splittypecodes=true;
            
          int tab = 1;//starting tab for all programs personal services
          
          if(asb.getFccode()==40 || asb.getFccode()==42){//literacy only
            if(asb.getFycode()>13)
              tab = 4;//starting tab for FY2013-14, purchased serv  
          }            
          
          BudgetCollectionBean bb= dbh.getBudgetBeanRecords(grantid, 0, true, asb, splittypecodes, tab);
          request.setAttribute("BudgetCollectionBean", bb);
                                    
             
          if(m.equals("lg"))
          {
            sess.setAttribute("pagemodule", "lg");
            TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb1);    
            
            TotalsBean tb = bdh.calcLgAmtApprForPanel(grantid, asb.getFycode(), tb1.getTotAmtAppr());
            request.setAttribute("fyTotal", tb);
            
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLgBudgetNarrative(request, "1", grantid);
          }
          else if(m.equals("di"))
          {
            TotalsBean tb = bdh.calcDiFyTotalAmtAppr(asb.getFycode());
            request.setAttribute("fyTotal", tb);
            
            TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb1);       
          }          
          else if(m.equals("co"))
          {
            TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb1);       
          
            //added 2/17/11-totals for each year of project
            HashMap tb = bdh.calcFyBudgetTotals(grantid, asb.getFycode(), true, asb);
            request.setAttribute("totalsMap", tb);
              
            //the amt appr cannot go over 350k per fiscal year
            Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
            request.setAttribute("fyTotals", totals); 
          }
          else if(m.equals("sa"))
          {
            TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb1);       
          
            FS10DBbean fs = new FS10DBbean();
            ArrayList allFSRecords = fs.getFSARecords(grantid);        
            sess.setAttribute("allFSRecords", allFSRecords);
            sess.setAttribute("fsaSize", new Integer(allFSRecords.size()) );
          }
          else if(m.equals("staid")) 
          {
            TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb1);       
          }
          else if(m.equals("fl") || m.equals("al"))
          {
            request.setAttribute("p", m);//set fl/al program-needed for admin budget tabs
            finalTarget="budget"+tab;
            
            GrantBean gb = dbh.getRecordBean(grantid);
            HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, true, asb, gb.getInstID());
            request.setAttribute("totalsMap", tb);
            
            //starting FY2013-14; check that award is not over approp for given grant
            if(asb.getFycode()>13){
                Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
                request.setAttribute("fyWarnings", warns);
            }
            
            //check total amt appr cannot go over 200k (program max amt) per fiscal year
            Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
            request.setAttribute("fyTotals", totals);  
                       
            //get cooresponding budget narr for tab
            int narrativeTypeId=35;
            if(asb.getFycode()>13)
               narrativeTypeId=38;//starting FY13-14; use purchased services
            
            DescriptionBean pdb = new DescriptionBean();
            NarrativeDBbean ndb = new NarrativeDBbean();      
            ClobBean cb = new ClobBean();
            cb.setGrantid(grantid);
            cb.openOracleConnection();      
            cb.getClobNarrative(narrativeTypeId); 
            cb.closeOracleConnection();
            
            pdb.setNarrative(cb.getData());
            pdb.setId(cb.getNarrID());
            pdb.setNarrTypeID(narrativeTypeId);  
            pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrativeTypeId));
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrativeTypeId, gb.getFccode(), gb.getFycode()));          
            request.setAttribute("projNarrative", pdb);            
          }
      
      }catch(Exception e){log.error(e.getMessage().toString());}
      return mapping.findForward(finalTarget);        
    }
    
    
    public ActionForward finalrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          int narrTypeId=2;//2 is the narrative type id for final narrative for most programs
          
          NarrativeDBbean ndb = new NarrativeDBbean();  
          //DescriptionBean used on jsp to display
          DescriptionBean pdb = new DescriptionBean();
          
          //get grant info
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb);   
          
          if(gb.getFccode()==40 || gb.getFccode()==42){//adult/family lit
            if(gb.getFycode() >16)
                narrTypeId =122;//new final narratives for lit starting 2016-19
            
                pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeId));
                pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(1, gb.getFccode(), gb.getFycode()));                  
          }
          
          //get the final narrative
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();
          cb.getClobNarrative(narrTypeId); 
          cb.closeOracleConnection();
          
          //DescriptionBean used on jsp to display
          pdb.setNarrative(cb.getData()); 
          request.setAttribute("projNarrative", pdb);
      
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("finalrpt");
    }
    
    
    
    public ActionForward auth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb); 
          
          AuthorizationsDBbean adb = new AuthorizationsDBbean();
          
          if(gb.getFccode()==20)
          {            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb);
                         
            AuthorizationBean ab = adb.getGrantAssurance(grantid);
            request.setAttribute("authorizationBean", ab);
          }
          else
          {
              Vector grantAuth = adb.getGrantAuthorizations(grantid);
              request.setAttribute("grantAuth", grantAuth);
          }
          
              
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("auth");
    }
    
    public ActionForward fs10a(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{         
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
            
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb); 
        
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb); 
          
        FS10DBbean fs = new FS10DBbean();
        ArrayList allFSRecords = fs.getFSARecords(grantid);
        request.setAttribute("allFSRecords", allFSRecords);
        
      }catch(Exception e) { log.error(e.getMessage().toString());}
      return mapping.findForward("fs10a");
    }
    
    
     public ActionForward fs(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{         
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid= Long.parseLong(grantnum);
            
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb);   
        
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("fs");
    }
      
      
    public ActionForward comments(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");            
            
        try{
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
       
            ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
            Vector allCom = rdb.getCommentsForApcnt(grantid);
            request.setAttribute("allComments", allCom);        
            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb); 
                  
            if(asb.getFccode()==7){
              RatingBean rb = rdb.getCatSumScoresByGrant(grantid);
              request.setAttribute("totScores", rb);        
            }
            else if(asb.getFccode()==5){
              RatingBean rb = rdb.getCatSumScoresDi(grantid);
              request.setAttribute("totScores", rb);
            }          
            
        }catch(Exception e){log.error(e.getMessage().toString());}
        
        return mapping.findForward("comments");
    }   
      
      
          
    public ActionForward coversheet(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid= Long.parseLong(grantnum);
            PartInstDBbean pdb = new PartInstDBbean();        
       
            //get lead grant info
            GrantBean gb = dbh.getRecordBean(grantid);
            request.setAttribute("thisGrant", gb);   
                      
            //get the library director
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            request.setAttribute("libDirectorBean", libdirectorBean); 
            
            OfficerBean po = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", po);
                        
            //get participating inst        
            Vector allPartInst = pdb.getParticipants(grantid);
            request.setAttribute("allPartInst", allPartInst);
            
           if(gb.getFccode()==80)
           {
              ClobBean clobb = new ClobBean();
              clobb.setGrantid(grantid);
              clobb.openOracleConnection();      
              clobb.getClobNarrative(1); 
              clobb.closeOracleConnection();     
              
              CoversheetBean csb = new CoversheetBean();
              csb.setSummaryDesc(clobb.getData());           
              csb.setGrantid(grantid);                 
              csb= odb.getProjectManager(csb);
              csb= odb.getAdditionalContact(csb, 1);
              csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
              
              EligibilityDbBean edb = new EligibilityDbBean();
              csb = edb.getArchivesGovtInfo(csb);
              csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());            
              request.setAttribute("coversheetBean", csb);  
              
              //get the senate, congress districts, etc.          
              DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
              request.setAttribute("distBean", db);
           }
           else if(gb.getFccode()==5)
           {
                //get summary desc, project manager info 
                ClobBean clobb = new ClobBean();
                clobb.setGrantid(grantid);
                clobb.openOracleConnection();      
                clobb.getClobNarrative(1); 
                clobb.closeOracleConnection();     
                
                CoversheetBean csb = new CoversheetBean();
                csb.setSummaryDesc(clobb.getData());
                csb.setNarrId(clobb.getNarrID());
                csb.setNarrTypeId(1);
                csb.setGrantid(grantid);        
                csb =dbh.getProjectNameReligAffil(csb);        
                csb= odb.getProjectManager(csb);  
                csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
                                        
                EligibilityDbBean edb = new EligibilityDbBean();
                csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());
                request.setAttribute("coversheetBean", csb);   
                          
                //get the senate, congress districts, etc. based on grant
                DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
                request.setAttribute("distBean", db);
                
               SubmissionDBbean sb = new SubmissionDBbean();
               Vector allSubmissions = sb.getSubmissions(grantid);   
               request.setAttribute("allSubmissions", allSubmissions);
            }
            else if(gb.getFccode()==7)
            {
              //get total amount requested by fiscal year
              Vector allsums =bdh.getTotAmtReqCoFYPeriod(grantid, gb.getFycode());
              request.setAttribute("allsums", allsums);
              
              OfficerBean ob = odb.getProjectManager(grantid);
              request.setAttribute("projManagerBean", ob);
              
              SubmissionDBbean sb = new SubmissionDBbean();
               Vector allSubmissions = sb.getSubmissions(grantid);   
               request.setAttribute("allSubmissions", allSubmissions);
               
               AuthorizationsDBbean ab = new AuthorizationsDBbean();
               Vector grantAuth = ab.getGrantAuthorizations(grantid);
               request.setAttribute("grantAuth", grantAuth);          
            }
            else if(gb.getFccode()==40 || gb.getFccode()==42)
            {
              //get the senate, congress districts, etc.
              DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
              request.setAttribute("distBean", db);
              
              //get all coverpage info
              CoversheetBean csb = new CoversheetBean();
              csb.setGrantid(grantid);        
              csb =dbh.getProjectNameReligAffil(csb);        
              csb= odb.getProjectManager(csb); 
              csb= odb.getAdditionalContact(csb, 3);
              request.setAttribute("coversheetBean", csb);     
              
              if(gb.getFycode()>13){
                  //new method starting fy 2013-14
                    DescriptionBean ddb = new DescriptionBean();
                    //get the narrative from the db and set to descrBean in session
                    ClobBean cb = new ClobBean();
                    cb.setGrantid(grantid);
                    cb.openOracleConnection();      
                    cb.getClobNarrative(107); 
                    cb.closeOracleConnection();
                    
                    ddb.setNarrative(cb.getData());
                    ddb.setId(cb.getNarrID());
                    request.setAttribute("projNarrative", ddb);  
              }
            }
            else if(gb.getFccode()==6)
            {
              //get the summary descr
              ClobBean cb = new ClobBean();
              cb.setGrantid(grantid);
              cb.openOracleConnection();
              cb.getClobNarrative(1); //1 is narrative type id for summary descr
              cb.closeOracleConnection();
              
              //set to bean the narr
              DescriptionBean sb = new DescriptionBean();
              sb.setNarrative(cb.getData()); 
              request.setAttribute("projNarrative", sb);
            }
    
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("coversheet");      
    }    
    
    public ActionForward appstatus(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
          //get all submission and approval info
          CommentDBbean cdb = new CommentDBbean();
          ApprovalsDBbean adb = new ApprovalsDBbean();
          SubmissionDBbean sb = new SubmissionDBbean();
          
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          Vector allSubmissions = sb.getSubmissions(grantid);
          Vector allApprovals = adb.getApprovals(grantid);
          allApprovals = adb.getAmountForApprovals(allApprovals);
          Vector allComments = cdb.getAdminComments(grantid);
          Vector apcntComm = cdb.getApcntComments(grantid);
          
          request.setAttribute("allSubmissions", allSubmissions);
          request.setAttribute("allApprovals", allApprovals);
          request.setAttribute("allComments", allComments);
          request.setAttribute("apcntComments", apcntComm);
          
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("appstatus");          
    }
       
    
  //1/10/14 prequalification for cp dg
  public ActionForward prequal(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
    
      
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("prequal");
  }
    
    
  //1/10/14 mwbe for cp dg
  public ActionForward mwbe(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
            
         
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
       
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("mwbe");
  }
    
    
    public ActionForward reports(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);
            
            ArrayList allcounties = dbh.dropDownCounties();
            request.setAttribute("dropDownCounties", allcounties);
            
            ArrayList allregs = dbh.dropDownRegionsAll();
            request.setAttribute("dropDownRegions", allregs);
             
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
    
    public ActionForward confirmbdgtdelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          BudgetDeleteBean bb = new BudgetDeleteBean();
          bb.setGrantid(grantid);
          bb.setId(Long.parseLong(request.getParameter("id").trim()));
          bb.setTab(Integer.parseInt(request.getParameter("tab").trim()));
          bb.setModule(request.getParameter("p"));
          //get descr of record
          bb.setDescription(bdh.getBudgetRecordDesc(bb.getTab(), bb.getId()) );
          
          request.setAttribute("deleteBean", bb);
      }
      catch(Exception e) {
        log.error("AdminNavAction "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmbdgtdelete");      
    }
    
    
    
    
    
  public ActionForward narrative(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
              
      DescriptionBean pdb = new DescriptionBean();
      NarrativeDBbean ndb = new NarrativeDBbean();      
      String prog = request.getParameter("p");
      String finalTarget = "narrative";
      
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);
          
          if(asb.getFccode()==40 || asb.getFccode()==42){//new 1/19/16 for LITERACY split narrs for 2016-19
              if(asb.getFycode()>16)
                  finalTarget="narrativenew";
          }
          
          if(asb.getFccode()==80)
          {
            //LGRMIF ONLY
            NarrInstructionBean nib = new NarrInstructionBean();
            int[] allTypes = nib.getLgNarrTypes();
              
            //set up the clob bean to retrieve narratives
            ClobBean cb = new ClobBean();
            cb.setGrantid(grantid);
            cb.openOracleConnection();     
                       
            for(int i=0; i <allTypes.length; i++)//for each narrativeTypeId
            {                   
              DescriptionBean db = new DescriptionBean();
              String narrString = Integer.toString(allTypes[i]);
              cb.getClobNarrative(allTypes[i]); 
              db.setNarrative(cb.getData());              
              
              String newBean = "projNarr"+narrString;
              sess.setAttribute(newBean, db);   
            }
            
            //adding finalRpt narr to lgrmif html/pdf narr printout per FC 8/8/11
            DescriptionBean db = new DescriptionBean();
            cb.getClobNarrative(2); 
            db.setNarrative(cb.getData());              
            sess.setAttribute("projNarr2", db);        
            
            cb.closeOracleConnection();
              
          }
          else if(asb.getFccode()==20){
            
            NarrInstructionBean nib = new NarrInstructionBean();
            int[] allTypes = nib.getStaidNarrTypes();
              
            //set up the clob bean to retrieve narratives
            ClobBean cb = new ClobBean();
            cb.setGrantid(grantid);
            cb.openOracleConnection();     
                       
            for(int i=0; i <allTypes.length; i++)//for each narrativeTypeId
            {                   
              DescriptionBean db = new DescriptionBean();
              String narrString = Integer.toString(allTypes[i]);
              cb.getClobNarrative(allTypes[i]); 
              db.setNarrative(cb.getData());              
              
              String newBean = "projNarr"+narrString;
              sess.setAttribute(newBean, db);   
            }            
            cb.closeOracleConnection();
          }
          else{
          
              //get the narrative from the db and set to descrBean in session
              ClobBean cb = new ClobBean();
              cb.setGrantid(grantid);
              cb.openOracleConnection();      
              cb.getClobNarrative(1); 
              cb.closeOracleConnection();
              
              pdb.setNarrative(cb.getData());
              pdb.setId(cb.getNarrID());
              pdb.setNarrTypeID(1);  
              pdb.setNarrativeTitle(ndb.getNarrativeTitle(1));
              pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(1, asb.getFccode(), asb.getFycode()));  
              pdb.setModule(prog+"adminnarr");
              request.setAttribute("projNarrative", pdb);  
          }
                    
      }catch(Exception e){
        System.out.println("AdminNavAction:narrative "+e.getMessage().toString());
      }
        
        return mapping.findForward(finalTarget);
    }
    
    
    
    
    
  /**
     *8/3/13 SH - new action for lit admin to view submit warnings
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward verifyinitial(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{         
          String grantnum = (String) s.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          
          GrantBean gb = dbh.getRecordBean(grantid);
          int fccode= gb.getFccode();
                                              
          //check for missing narratives
          SubmissionDBbean sdb = new SubmissionDBbean();
          Vector missingNar = sdb.checkMissingNarratives(grantid, fccode);
          if(missingNar.size()>0)
              request.setAttribute("missingNarr", missingNar);  
              
          //check for missing budget
          BudgetDBHandler bdh = new BudgetDBHandler();
          int totreq=0;
          if(fccode==40 || fccode==42){//adult/family lit
                                 
              //starting fiscal year 2013-14
              Vector<AllocationYearBean> totalsBeans = bdh.getTotAmtReqLitAllocation(grantid, gb.getFycode(), gb.getFccode(), gb.getInstID());
              for(AllocationYearBean tb: totalsBeans){
                  totreq += tb.getAmountRequested();//need sum of totAmtReq for 3 years
                  if(tb.getAmountRequested()==0){//no money requested for given year
                    request.setAttribute("incompleteBudget", "true");
                  }
                  else if(tb.isWarning()){//went over allocation for given year
                      request.setAttribute("incompleteBudget", "true");
                  }
              }               
          }
          if(totreq<1)
            request.setAttribute("missingBudg", "true");
          
          
          //10/23/12 adult lit only - must have participants; starting FY13-14  
          if(fccode==40){              
            Vector allParts = sdb.checkLitParticipantNarr(grantid); 
            if(allParts.size()>0)
                request.setAttribute("missingParticipant", "true");
          }
                                     
          //set values for lit
          if(fccode==40)
              request.setAttribute("prog", "al");
          else if(fccode==42)
              request.setAttribute("prog", "fl");              
            
      }catch(Exception e){
      System.out.println("error AdminNavAction "+e.getMessage().toString());
      }
    return mapping.findForward("verifyinitial");        
  }      
    
    
    
    
  public ActionForward submitliteracy(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      String prog="";
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{        
          UserBean userb = (UserBean) s.getAttribute("lduser");
          String grantnum = (String) s.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);            
          String submittype = request.getParameter("t");
          prog = request.getParameter("p");
          String cmt = request.getParameter("acomment");
                
          int fccode=0;
          if(prog.equals("fl"))
            fccode=42;
          else if(prog.equals("al"))
            fccode=40;
                           
                          
          if(cmt!=null && !cmt.equals("")){  //add optional comment
            CommentDBbean cdb = new CommentDBbean();
            cdb.addApcntComment(grantid, userb, cmt);
          }
            
          int result =0;
          SubmissionDBbean sdb = new SubmissionDBbean();
          if(submittype.equals("initial"))    
            result = sdb.submitInitialApp(grantid, userb, fccode); 
                      
          if(result>0){
              LitEmailNotifyBean eb = new LitEmailNotifyBean();
              if(submittype.equals("initial"))//on initial submit; cc KBALSEN
                  eb.sendLiSubmit(userb, grantid, true);              
          }                    
          
          //refresh vars and return to admin checkstatus page  
          AppStatusBean asb= dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          SubmissionDBbean sb = new SubmissionDBbean();
          Vector allSubmissions = sb.getSubmissions(grantid); 
          request.setAttribute("allSubmissions", allSubmissions);
                        
      }catch(Exception e){
      System.out.println("error SubmitAppAction "+e.getMessage().toString());
      }
    return mapping.findForward(prog+"grant");        
  }      
  
  
  public ActionForward revertbudget(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{         
          UserBean userb = (UserBean) s.getAttribute("lduser");
          String grantnum = (String) s.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
                    
          //reset lock to 0; reset budget complete to 0; this "should" force apcnt back to initial budget
          dbh.revertLitBudgetToInitial(grantid, userb);
                              
                              
          //redisplay checklist page                 
          AppStatusBean asb= dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          SubmissionDBbean sb = new SubmissionDBbean();
          Vector allSubmissions = sb.getSubmissions(grantid); 
          request.setAttribute("allSubmissions", allSubmissions);                
                        
            
      }catch(Exception e){
      System.out.println("error AdminNavAction "+e.getMessage().toString());
      }
    return mapping.findForward("grant");        
  }      
    
    
    
    
  //new 1/20/16 for literacy admin per KBALSEN
  public ActionForward loadLitAdminHome(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{                   
          //load FY drop down box; admin search for submitted apps by FY
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          s.setAttribute("dropDownList", allyears);   
                          
      }catch(Exception e){
      System.out.println("error AdminNavAction.loadLitAdminHome "+e.getMessage().toString());
      }
    return mapping.findForward("newhome");        
  }      
    
    
  //new 1/20/16 for literacy admin per KBALSEN
  //new lit home page has 2 categories: app_submit and app_approved
  public ActionForward litAdminHomeSearch(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{         
          String fy = (String) request.getParameter("fycode");
          String mod = (String) request.getParameter("mod");
          int fccode=0;
          
          if(mod!=null &&  !mod.equals("")){
            if(mod.equalsIgnoreCase("fl"))
                fccode=42;
            else
                fccode=40;//adult lit
          }
                    
          AdminDB adb = new AdminDB();       
          Vector submitted = adb.getSubmittedRegardlessOfApproval(fccode, Integer.parseInt(fy));
          request.setAttribute("allGrants", submitted);
          
          Vector approved = adb.getInitialApproved(fccode, Integer.parseInt(fy));
          request.setAttribute("approvedGrants", approved);     
          
          ArrayList yr1submit = adb.getLitFinalSubmittedNotApproved(fccode, Integer.parseInt(fy), 1);
          request.setAttribute("finalSubmit1", yr1submit);
          
          ArrayList yr2submit = adb.getLitFinalSubmittedNotApproved(fccode, Integer.parseInt(fy), 2);
          request.setAttribute("finalSubmit2", yr2submit);
            
          ArrayList yr3submit = adb.getLitFinalSubmittedNotApproved(fccode, Integer.parseInt(fy), 3);
          request.setAttribute("finalSubmit3", yr3submit);
          
          ArrayList yr1approve = adb.getLitFinalApproved(fccode, Integer.parseInt(fy), 1);
          request.setAttribute("approved1", yr1approve);
          
          ArrayList yr2approve = adb.getLitFinalApproved(fccode, Integer.parseInt(fy), 2);
          request.setAttribute("approved2", yr2approve);
            
          ArrayList yr3approve = adb.getLitFinalApproved(fccode, Integer.parseInt(fy), 3);
          request.setAttribute("approved3", yr3approve);
          
                    
      }catch(Exception e){
      System.out.println("error AdminNavAction.litAdminHomeSearch "+e.getMessage().toString());
      }
    return mapping.findForward("newhome");        
  }      
  
    
    
    
  //new 2/5/16 for literacy admin to view certform data
  public ActionForward certform(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
                
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        OfficerDBbean odb = new OfficerDBbean();    
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");//gets sedref entry from sed_admin_positions table
        request.setAttribute("libDirectorBean", libdirectorBean); 
  
        //get inst address info, grant
        GrantBean gb=dbh.getRecordBean(grantid); 
        request.setAttribute("thisGrant", gb);      
        
        CoversheetBean csb = new CoversheetBean();
        csb.setGrantid(gb.getGrantid());
        csb= odb.getProjectManager(csb);
        request.setAttribute("coversheetBean", csb);
          
        //2/2/16 per KBALSEN; get yearly edlaw amt        
        AllocationYearBean ab = bdh.getAllocationForInstFy(gb.getFycode(), gb.getFccode(), gb.getInstID());
        request.setAttribute("allocAmount", ab);
                      
        //get the senate, congress districts, etc.
        DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
        request.setAttribute("distBean", db);
        
        //get every instance of certform checkbox checked (from authorizations table)
        AuthorizationsDBbean adb = new AuthorizationsDBbean();     
        Vector grantAuth = adb.getGrantAuthorizations(grantid);
        request.setAttribute("grantAuth", grantAuth);
       
             
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb);
            if (grantAuth.size() > 0) {
                Vector<AuthorizationBean> aVector = grantAuth;
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date maxDate = formatter.parse("01/01/1000");
                for (AuthorizationBean gBean : aVector) {
                    if (maxDate != null && gBean.getAuthdate().compareTo(maxDate) > 0) {
                        maxDate = gBean.getAuthdate();
                        System.out.println("MAX Date: " + maxDate);
                    }
                }
                request.setAttribute("maxDate", maxDate);
            } else {
                request.setAttribute("maxDate", "");
            }
      }catch(Exception e){
        System.out.println("error AdminNavAction "+e.getMessage().toString());
      }
      return mapping.findForward("certform");
  }
  
  
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT AdminNavAction");
        timeout = true;
      }
      
      return timeout;
    }
}