/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerNavAction.java
 * Creation/Modification History  : *
 * SH   3/18/09      Created
 *
 * Description
 * This action class routes co/di/lit/lg reviewer calls to home,participation,contact
 * info,assignments,rating forms, and submit pages.
 *****************************************************************************/
package reviewers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppDatesBean;
import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.EligibilityDbBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.PanelBean;
import mypackage.PanelDbBean;
import mypackage.PanelReviewBean;
import mypackage.RatingBean;
import mypackage.ReviewAssignMaxBean;
import mypackage.ReviewerAssignBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.ReviewerBean;
import mypackage.ReviewerDBbean;
import mypackage.ReviewerEmailBean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReviewerNavAction extends DispatchAction
{
  private ReviewerDBbean rdb = new ReviewerDBbean();
  private ReviewerAssignDBbean rab = new ReviewerAssignDBbean(); 
  private  DBHandler dbh = new DBHandler();
  
  public ActionForward home(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{
            //find reviewer info, based on LDAP username, set rev info to sess in lduser 
            ReviewerBean rb = rdb.findReviewerInfo(request);
            request.setAttribute("newReviewer", rb);             
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
                
        return mapping.findForward("home");
    }
    
    
    
    public ActionForward revinfo(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        if(!checkReviewerFoundDb(sess))
          return mapping.findForward("home");
          
        try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String program = request.getParameter("p");
          
          //get all info about reviewer and send to update page    
         // boolean decryptssn = program.equals("lg");
          ReviewerBean rb = rdb.getReviewerInfo(userb.getRevid(), true);
          rb.setGrantprogram(program);
          request.setAttribute("ReviewerBean", rb);
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("revinfo");
    }
    
    
    public ActionForward participation(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        if(!checkReviewerFoundDb(sess))
          return mapping.findForward("home");
        
        try{  
          //get the reviewer currently logged in
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          long revid = userb.getRevid();    
            
          //get all assign_max records for reviewer and display
          Vector allRecords = rdb.getGrantAssignMax(revid);
          request.setAttribute("allAccepted", allRecords);
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("participation");
    }
    
    
    public ActionForward record(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
         
        try{       
          //get the id to update
          String id = request.getParameter("id");
          String p = request.getParameter("p");
          
          ReviewAssignMaxBean rb = rdb.getAssignMaxRecord(Integer.parseInt(id));
          //rb.setTask("update");
          rb.setModule(p);
          request.setAttribute("ReviewAssignMaxBean", rb);
          
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("record");
    }
    
    
    public ActionForward addrecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{
            String p = request.getParameter("p");
            
            ReviewAssignMaxBean rb = new ReviewAssignMaxBean();
            //rb.setTask("add");
            rb.setModule(p);
            rb.setFycode(16);//default to 2016
            request.setAttribute("ReviewAssignMaxBean", rb);
            
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("addrecord");
    }
    
    /**
     * For LG reviewer only.  This allows lg rev to search for grants assigned to their
     * panel for given fy. 
     */
    public ActionForward panelsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{                        
            String fy = request.getParameter("fycode");
            if(fy!=null && !fy.equals("")) 
            {
                PanelDbBean pdb = new PanelDbBean();
                //find out which panel reviewer is assigned to for given fy
                UserBean userb = (UserBean) sess.getAttribute("lduser");
                PanelBean p = pdb.getPanelForReviewerFy(Integer.parseInt(fy), userb.getRevid());
                request.setAttribute("panel", p);
                
                //get grants assigned to this panel                
                ArrayList<GrantBean> panelgrant = pdb.getGrantsForPanel(p.getId());
                
                
                //for each panel_grant; get avg of all reviewer athome scores
                for(GrantBean g: panelgrant){
                  
                  //get averages of all reviewer ratings/recommended amts for this grant
                  RatingBean rb =rab.getLgAvgRatingByCategory(g.getPanelgrantId(), g.getFycode());
                  g.setScoreStr(rb.getScoreStr());
                  g.setScore(Double.parseDouble(g.getScoreStr()));                    
                }           
              //per DM 9/14/14 sort by category; then score desc
              Collections.sort(panelgrant, GrantBean.CategoryScoreComparator);
              request.setAttribute("panelGrants", panelgrant);
            
            }
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("panelsearch");
    }
    
    /**
     * For lg reviewers, this will generate deliberation form - get average ratings
     * for all reviewers for all categories for given grant. 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward delibform(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
        String finalTarget="delibform";
        try{                        
            String grantnum = request.getParameter("id");
            String pangraid = request.getParameter("pgid");
            long grantid = Long.parseLong(grantnum);
            sess.setAttribute("grantid", grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            sess.setAttribute("thisGrant", gb);  
                                   
            //get any comments/ratings panel has made for this grant
           PanelDbBean pdb = new PanelDbBean();
           PanelReviewBean pb = pdb.getDeliberationPanelGrant(Long.parseLong(pangraid));
           
           BudgetDBHandler bdh = new BudgetDBHandler(); 
           int totalAmtAppr = bdh.calcTotalAmtApproved(grantid, 0);//from budget pages
           pb.setTotamtappr(totalAmtAppr);
           pb.setTotamtreq(bdh.calcTotalAmtRequested(grantid, 0));       
            
            //get bonus scoring - for projects older than 2011-12
            CoversheetBean csb =new CoversheetBean();
             if(gb.getFycode()<12){
                 EligibilityDbBean edb = new EligibilityDbBean();
                 csb = edb.getLgBonusScoring(grantid);
                 request.setAttribute("CoverBean", csb);                   
             }         
             
            //new delibforms for 11-12 and 12-13 and 14-15 and 15-16 FY's 
            if(gb.getFycode()>=16)
                finalTarget="delibform1516";
            else if(gb.getFycode()==15)
                finalTarget="delibform1415";
            else if(gb.getFycode()>=13)
                finalTarget="delibform1213";
            else if(gb.getFycode()==12)
              finalTarget="delibformnew";            
            
                            
                       
           //get averages of all reviewer ratings/recommended amts for this grant
           RatingBean rb =rab.getLgAvgRatingByCategory(Long.parseLong(pangraid), gb.getFycode());
           
           if(gb.getFycode()>12){
             //get panel ratings for this grant; if rating doesnot exist->use avg rating
             rb = pdb.getDeliberationRatings(Long.parseLong(pangraid), rb);
             rb.setGraid(pb.getGrantid());
             rb.setPanelgrantId(pb.getPanelgrantid());
             rb.setStatus(pb.getStatus());
             //starting fy 2012-13, panel score is read-only; sum of delib rating categories
             pb.setFinalscore(rb.getFinalScoreSum());
           }
           request.setAttribute("RatingBean", rb);
           
           //find out if application was approved or denied
           ApprovalsDBbean adb = new ApprovalsDBbean();
           pb.setInitialappr( adb.isInitialAppApproved(grantid) );
           pb.setBonuspts(csb.getScore());             
           request.setAttribute("PanelReviewBean", pb);
            
                  
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward(finalTarget);
    }
    
    
    /**
     * for lgrmif reviewers who are also rao's, this loads the region search page
     * so they can find decision notes for their region.
     */
    public ActionForward loadregion(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{      
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);
            
            ArrayList allregs = dbh.dropDownRegions();
            sess.setAttribute("allRegions", allregs);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("regionpage");
    }
    
    
    public ActionForward regionsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{      
           String fy = request.getParameter("fycode");
           String rt = request.getParameter("regiontype");
           
           PanelDbBean pdb = new PanelDbBean();
           ArrayList allgrants=pdb.getGrantsForRegion(Integer.parseInt(fy), Integer.parseInt(rt));
           request.setAttribute("allGrants", allgrants);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("regionpage");
    }
    
    
    public ActionForward selectdecision(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{      
           String grantid = request.getParameter("id");
           sess.setAttribute("grantid", grantid);
           String pgid = request.getParameter("pgid");
            
           GrantBean gb=dbh.getRecordBean(Long.parseLong(grantid)); 
           request.setAttribute("thisGrant", gb);  
            
           PanelDbBean pdb = new PanelDbBean();
           PanelReviewBean pb = pdb.getDeliberationPanelGrant(Long.parseLong(pgid));
           request.setAttribute("PanelReviewBean", pb);
           
           //get bonus scoring
           EligibilityDbBean edb = new EligibilityDbBean();
           CoversheetBean csb = edb.getLgBonusScoring(Long.parseLong(grantid));
           request.setAttribute("CoverBean", csb);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());}        
        return mapping.findForward("decision");
    }
    
    
    public ActionForward updatedecision(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{      
          UserBean userb = (UserBean)sess.getAttribute("lduser");
          
          //get new decision notes
           ActionForm myform = (ActionForm) request.getAttribute("PanelReviewBean");
           PanelReviewBean p = (PanelReviewBean) myform;
           
           //update decision notes
            PanelDbBean pdb = new PanelDbBean();
            pdb.updateDecisionNotes(p, userb);
          
           //get updated data and redisplay  
           GrantBean gb=dbh.getRecordBean(p.getGrantid()); 
           request.setAttribute("thisGrant", gb);              
           
           PanelReviewBean pb = pdb.getDeliberationPanelGrant(p.getPanelgrantid());
           request.setAttribute("PanelReviewBean", pb);
           
           //get bonus scoring
           EligibilityDbBean edb = new EligibilityDbBean();
           CoversheetBean csb = edb.getLgBonusScoring(p.getGrantid());
           request.setAttribute("CoverBean", csb);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());}        
        return mapping.findForward("decision");
    }
    
    
    public ActionForward assignments(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
        
        if(!checkReviewerFoundDb(sess))
          return mapping.findForward("home");
        
        try{
          String p = request.getParameter("p");
          request.setAttribute("p", p);
          
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
          
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("assignments");
    }
    
    
    public ActionForward findassign(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
        
        if(!checkReviewerFoundDb(sess))
          return mapping.findForward("home");
        String p = "";
        try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          p = request.getParameter("pr");//fund code parameter
          request.setAttribute("p", p);
          String fc = determineFundCode(p);
          String fy = request.getParameter("fycode");
          
          //get all grants assigned to reviewer for program
          Vector allAssigned = rab.getAllAssignForReviewer(userb.getRevid(), fc, Integer.parseInt(fy));
          request.setAttribute("allAssigned", allAssigned);
            
          if(fc=="80"){
            
            //per DM 9/14/15 sort by category
            Collections.sort(allAssigned, ReviewerAssignBean.CategoryProjectComparator);
            request.setAttribute("allAssigned", allAssigned);
          }
            
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward(p+"assignments");
    }
    
     public ActionForward grant(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
          //set the grantid and instid that reviewer picked
          String grantnum = request.getParameter("id");
          String assignid = request.getParameter("assign");
          sess.setAttribute("grantid", grantnum);
          sess.setAttribute("assignid", assignid);
          long grantid = Long.parseLong(grantnum);

          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);           
          Vector allDocuments = dbh.getAllDocuments(grantid);
          request.setAttribute("allDocs", allDocuments);
          
          AppDatesBean a = rdb.getDueDateReviews(gb.getFccode(), grantid);
          request.setAttribute("revDue", a);
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("grant");
    }
    
    
    public ActionForward rating(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
      String finalTarget="rating";
       
      try{ 
          //set the grantid and instid that reviewer picked
          String grantnum = request.getParameter("id");
          String assignid = request.getParameter("assign");
          sess.setAttribute("grantid", grantnum);
          sess.setAttribute("assignid", assignid);
          long grantid = Long.parseLong(grantnum);
          
          //get info about grant 
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);  
                    
          String program = request.getParameter("p");
          //get all rating scores and whether ratings have been submitted yet
          RatingBean rb = new RatingBean();
          if(gb.getFccode()==80)//for lgrmif 
          {
              rb = rab.getGrantRatingsForLGReviewer(Long.parseLong(assignid));
                            
              //lgrmif needs bonus pts if fy is 2011 or earlier
              if(gb.getFycode()<12){
                  EligibilityDbBean edb = new EligibilityDbBean();
                  CoversheetBean csb = edb.getLgBonusScoring(grantid);
                  request.setAttribute("CoverBean", csb);             
                  rb.setBonuspts(csb.getScore());
              }
              
              BudgetDBHandler bdh = new BudgetDBHandler(); 
              rb.setTotamtreq(bdh.calcTotalAmtRequested(grantid, 0));              
              //check if due date for lg reviews is ok
              AppDatesBean a = rdb.verifyReviewDue(gb.getFccode(), gb.getFycode());
              request.setAttribute("revDue", a);
          }
          else{
              rb = rab.getGrantRatingsForReviewer(Long.parseLong(assignid));
              AppDatesBean a = rdb.getDueDateReviews(gb.getFccode(), grantid);
              request.setAttribute("revDue", a);
              
              if(gb.getFccode()==5){//for dg only
                  Vector allDocuments = dbh.getAllDocuments(grantid);
                  request.setAttribute("allDocs", allDocuments);
              }
          }
              
          rb.setModule(program);
          request.setAttribute("RatingBean", rb);
                    
          //navigate to 1 of 2 rating forms based on education app for CO
          if(gb.getFccode()==7){
            if(gb.isEducationapp())
              finalTarget="ratingeduc";
          }         
          else if(gb.getFccode()==80){//4 rating forms for lgrmif (before FY 2011, after 2011, 2014-15, 2015-16)
              if(gb.getFycode()>15)//for 2015-16
                  finalTarget="rating1516";
              else if(gb.getFycode()>14)//for 2014-15
                  finalTarget="rating1415";
              else if(gb.getFycode()>11)
                finalTarget="ratingnew";
              else
                  finalTarget="rating";
          }
          else if(gb.getFccode()==5){//4 DI rating forms (for educ, and before/after FY2013)
                  if(gb.getFycode()>12){
                      if(gb.isEducationapp())
                        finalTarget="ratingeducnew";
                      else
                        finalTarget="ratingnew";
                  }
                  else{//FYcode is <=12
                      if(gb.isEducationapp())
                        finalTarget="ratingeduc";
                      else
                        finalTarget="rating";
                  }              
          }
                   
      }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
      return mapping.findForward(finalTarget);
    }
       
    
    public ActionForward submit(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
      try{
           //get info about grantid 
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           GrantBean gb=dbh.getRecordBean(grantid); 
           sess.setAttribute("thisGrant", gb);  
              
            //get info about the rating for this grant
            String assignid= (String)sess.getAttribute("assignid"); 
            String program = request.getParameter("p");
                        
            ReviewerAssignBean rb = rab.getAssignmentInfo(Long.parseLong(assignid), program);
            rb.setModule(program);
            
            if(gb.getFccode()==80){//for lgrmif do not allow submit if score <60 and rec=F
             
                 //get reviewer's final score and bonus points
                 RatingBean rateb = new RatingBean();
                 rateb = rab.getGrantRatingsForLGReviewer(Long.parseLong(assignid));
                 EligibilityDbBean edb = new EligibilityDbBean();
                 CoversheetBean csb = edb.getLgBonusScoring(grantid);
                 rateb.setBonuspts(csb.getScore());
                 
                 //if score plus bonus is < 60, then recommend=N; else do not allow submit
                 if((rateb.sumscore + rateb.bonuspts) < 60){
                     if( rateb.getRecommendation()!=null){
                        if(!rateb.getRecommendation().equalsIgnoreCase("N"))
                        rb.setLgMinimumScore(true);
                     }
                 }
            }            
            request.setAttribute("assignBean", rb);
            
            
            AppDatesBean a = rdb.verifyReviewDue(gb.getFccode(), gb.getFycode());
            request.setAttribute("revDue", a);
            
        }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
        
        return mapping.findForward("submit");
    }
    
    public ActionForward unsubmit(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
       
       try{         
          //get info about grantid 
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           GrantBean gb=dbh.getRecordBean(grantid); 
           sess.setAttribute("thisGrant", gb);  
              
            //get info about the rating for this grant
            String assignid= (String)sess.getAttribute("assignid");  
            ReviewerAssignBean rb = rab.getAssignmentInfo(Long.parseLong(assignid), "li");
            rb.setModule("li");
            request.setAttribute("assignBean", rb);
            
       }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
      return mapping.findForward("unsubmit");
    }
    
    
    public ActionForward dounsubmit(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
       
       try{   
          //get assignid to unsubmit
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String assignid = request.getParameter("assignid");    
          
          //unsubmit the rating form      
          int outcome = rab.unsubmitRatingForm(userb, Long.parseLong(assignid), false, "lit");    
                    
          //return to reviewer assignments
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
          request.setAttribute("p", "li");
            
       }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
      return mapping.findForward("assignments");
    }
    
    
    
    public ActionForward dosubmit(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession(); 
        String module="";
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
       
       try{   
           //get all info about assign id that reviewer wants to submit
           String assignid = request.getParameter("assignid");     
           module = request.getParameter("module");
           request.setAttribute("p", module);
           ReviewerAssignBean rb = rab.getAssignmentInfo(Long.parseLong(assignid), module);
           
           //get info about reviewer
           UserBean userb = (UserBean) sess.getAttribute("lduser");
           
           ReviewerBean r = new ReviewerBean();      
           r.setRevid(userb.getRevid());
           r.setUsername(userb.getUserid());
           r.setFname(userb.getFname());
           r.setLname(userb.getLname());
           r.setEmail(userb.getEmail());
           ReviewerAssignBean[] assignarray = {rb};
           r.setReviewerAssigns(assignarray);
           
           //submit the rating form      
           int outcome = rab.unsubmitRatingForm(userb, Long.parseLong(assignid), true, module);         
           
           if(module !=null && module.equals("lg")) 
           {
                //confirm submit rating email
                 ReviewerEmailBean ebean = new ReviewerEmailBean();
                 boolean status = ebean.sedemsLgRatingSubmitEmail(r, userb);                    
           }
           else if(module !=null && module.equals("di"))
           {
              ReviewerEmailBean ebean = new ReviewerEmailBean();
              boolean status = ebean.sedemsRatingSubmitEmail(r, userb);           
           }
           else if(module !=null && module.equals("co"))
           {      
               ReviewerEmailBean ebean = new ReviewerEmailBean();
               boolean status = ebean.sedemsRatingSubmitEmail(r, userb);                 
           }
           /*else if(module != null && module.equals("li"))
           {
                ArrayList allyears = dbh.dropDownFiscalYears();
                sess.setAttribute("dropDownList", allyears);
           }*/
           //return to assignments for reviewer
           ArrayList allyears = dbh.dropDownFiscalYearsDesc();
           sess.setAttribute("dropDownList", allyears);
           
       }catch(Exception e){System.out.println("error ReviewerNavAction "+e.getMessage().toString());};
      return mapping.findForward("assignments"+module);
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
    
    
    //for LGRMIF reviewers to update budget approval amounts
    public ActionForward budget(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{         
        String grantnum = request.getParameter("id");
        long grantid= Long.parseLong(grantnum);
        sess.setAttribute("grantid", grantnum);
        sess.setAttribute("pagemodule", "lgr");
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);  
        
          GrantBean gb=dbh.getRecordBean(grantid); 
          request.setAttribute("thisGrant", gb);  
        
        BudgetCollectionBean bb= dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, 1);
        request.setAttribute("BudgetCollectionBean", bb);    
        
        TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
        request.setAttribute("totalsBean", tb1);    
        
        BudgetDBHandler bdh = new BudgetDBHandler();
        TotalsBean tb = bdh.calcLgAmtApprForPanel( grantid, asb.getFycode(), tb1.getTotAmtAppr());
        request.setAttribute("fyTotal", tb);
        
        NarrativeDBbean ndb = new NarrativeDBbean();
        ndb.getLgBudgetNarrative(request, "1", grantid);
        
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("budget");
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
    
    
    public ActionForward attachment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");    
                        
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        Vector results = dbh.getAllDocuments(grantid);
         request.setAttribute("allDocs", results);
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }           
      return mapping.findForward("attachment");      
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        timeout = true;
      }
      
      return timeout;
    }
    
    public String determineFundCode(String program)
    {
      String fccode="0";
      try{
        if(program.equals("lg"))
          fccode="80";
        else if(program.equals("co"))
          fccode="7";
        else if(program.equals("di"))
          fccode="5";
        else if(program.equals("li"))
          fccode="40,42";        
        
      }catch(Exception e){
        System.out.println("error ReviewerNavAction "+e.getMessage().toString());
      }
      return fccode;
    }
    
    public boolean checkReviewerFoundDb(HttpSession sess)
    {
      UserBean userb = (UserBean) sess.getAttribute("lduser"); 
      boolean revfound = userb.isReviewerfound();//is rev ldap account found in reviewers table
      return revfound;
    }
}