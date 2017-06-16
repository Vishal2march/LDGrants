/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LgReportAction.java
 * Creation/Modification History  :
 *
 * Description
 * These dispatch actions are used to generate reports for lg admin and reviewers.
 *****************************************************************************/
package reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import mypackage.ApprovalsDBbean;
import mypackage.BudgetDBHandler;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.EligibilityDbBean;
import mypackage.FiscalYearBean;

import mypackage.GrantBean;
import mypackage.PanelDbBean;
import mypackage.PanelReviewBean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.SearchResultBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LgReportAction extends DispatchAction {
    ReportDBbean rdb = new ReportDBbean();


    public ActionForward revcontact(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
             //get all reviewers, addresses, phone and email
             Vector allRev = rdb.getRevContactInfo("80", false);
             request.setAttribute("allRev", allRev);
          
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("revcontact");        
      }
    
    
  public ActionForward revidinfo(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {                                      
        try{         
           HttpSession s = request.getSession();
            
           //get only active reviewers, with ssn and vendorId
           Vector allRev = rdb.getRevContactInfo("80", true);
           s.setAttribute("allRev", allRev);
            System.out.println("size "+allRev.size());
        
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("revidinfo");        
    }
  
  
      
    public ActionForward availability(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {            
          HttpSession s = request.getSession();
          try{         
              String fycode = request.getParameter("fycode");
              
               //get all reviewers participation for selected fy
               /*changed 2/18/10 per fc to get all reviewers, and participation if applicable
                * Vector allRev = rdb.getAcceptedAssignedForFy(fycode, "lgrmif");
               request.setAttribute("allRev", allRev);*/
               
                Vector allRev = rdb.getAllReviewerAvailability(Integer.parseInt(fycode),80, "lgrmif");
                s.setAttribute("allRev", allRev);
                                                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("availability");        
      }
      
      
    public ActionForward revassign(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession(); 
                             
        try{         
            String fycode = request.getParameter("revfycode");
            PanelDbBean pdb = new PanelDbBean();
            
            //ArrayList allPanel = pdb.getPanelsForYear(Integer.parseInt(fycode));
            ArrayList allRev = rdb.getPanelReviewersForFy(Integer.parseInt(fycode));
            s.setAttribute("allRevPanels", allRev);
            
             //get info about selected fy
             FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
             request.setAttribute("fybean", fb);         
        
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("revassign");        
    }
    
    
    public ActionForward graassign(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession(); 
                             
        try{         
            String fycode = request.getParameter("grafycode");
            ArrayList allRev = rdb.getPanelGrantsForFy(Integer.parseInt(fycode));
            s.setAttribute("allGrantPanels", allRev);
            
             //get info about selected fy
             FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
             request.setAttribute("fybean", fb);         
        
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("graassign");        
    }
    
      
    //updated 8/23/10 to include region
    public ActionForward applications(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fyapps");
              String isdoris = request.getParameter("doris");
              
               //get submitted grants during fy 
               Vector subGrants = rdb.getLgInitialGrants(fycode, 80, Boolean.parseBoolean(isdoris));
               Collections.sort(subGrants, GrantBean.InstitutionComparator);//sort by inst name
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
               request.setAttribute("rptname", " applications received ");
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("applications");        
      }
      
    //updated 8/23/10 to include region
    public ActionForward awardlist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("awardfy");
              String isdoris = request.getParameter("dorisaw");
              
               //get approved grants during fy 
               Vector subGrants = rdb.getAwardList(Integer.parseInt(fycode), 80, Boolean.parseBoolean(isdoris));
               Collections.sort(subGrants, GrantBean.InstitutionComparator);//sort by inst name
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
              request.setAttribute("rptname", " award list ");
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("applications");        
      }
            
            
    public ActionForward fundlist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fundlistfy");
               
               //get approved grants during fy 
               Vector subGrants = rdb.getFullModNoFundList(Integer.parseInt(fycode), 80);
               Collections.sort(subGrants, GrantBean.InstitutionComparator);//sort by inst name
               s.setAttribute("allGrants", subGrants);
                              
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("fundlist");        
      }
            
    public ActionForward categoryapps(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fycategapp");
                            
               //get count of grants per projcategory
               Vector subGrants = rdb.getApplicationsByCategory(Integer.parseInt(fycode));
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
               request.setAttribute("rptname", " applications submitted");
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("categoryawards");        
      }
      
    public ActionForward categoryawards(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fycateg");
                            
               //get count of grant awards by projcategory
               Vector subGrants = rdb.getAwardsByCategory(Integer.parseInt(fycode));
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
               request.setAttribute("rptname", " awards");
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("categoryawards");        
      }
      
      
    public ActionForward regionawards(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fyreg");
                            
               //get count of grant awards by region
               Vector subGrants = rdb.getAwardsByRegion(Integer.parseInt(fycode));
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("regionawards");        
      }
      
      
    public ActionForward countyawards(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fycnty");
                            
               //get count of grant awards by county
               Vector subGrants = rdb.getAwardsByCounty(Integer.parseInt(fycode));
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("countyawards");        
      }
      
      
    public ActionForward govtypeawards(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fygovtype");
                            
               //get count of grant awards by gov type
               Vector subGrants = rdb.getAwardsByGovtype(Integer.parseInt(fycode));
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("govtypeawards");        
      }
      
    public ActionForward nonsubgrants(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("nonsubfy");
                            
              Vector allgrants = rdb.getUnsubmittedGrants(Integer.parseInt(fycode), 80);
              s.setAttribute("allGrants", allgrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
               request.setAttribute("prefc", "05");
                               
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("nonsubgrants");        
      }
      
   public ActionForward revcomment(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {                                      
        try{         
            String grantnum = request.getParameter("gid");
            String projnum = request.getParameter("pn");
                      
            DBHandler dbh = new DBHandler();
            GrantBean gb=new GrantBean();
            
            if(grantnum!=null)            
                gb = dbh.getRecordBean(Long.parseLong(grantnum));
            else if(projnum!=null)
                gb = rdb.getBasicGrantInfo(projnum);
            request.setAttribute("thisGrant", gb); 
          
            //get reviewer's assigned, and comments
            ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
            ArrayList allcomm = rab.getAllLgCommentsForGrant(gb.getGrantid());
            
            CoversheetBean csb =new CoversheetBean();
            if(gb.getFycode()<12){//starting in 2011-12 new eval form w/ no bonus pts
                //get bonus pts
                EligibilityDbBean edb = new EligibilityDbBean();
                csb = edb.getLgBonusScoring(gb.getGrantid());
            }
            
            //get reviewer's total score plus bonus
            System.out.println("fy"+gb.getFycode());
            allcomm = rdb.getLgRevScoresForGrant(allcomm, csb.getScore(), gb.getFycode());   
            //get the avg score
            int avgscore=0;
            if(allcomm.size()>0){
                RatingBean r = (RatingBean) allcomm.get(0);
                avgscore = r.getAvgscore();
            }
            request.setAttribute("avgScore", avgscore);
            request.setAttribute("allComments", allcomm);            
             
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("revcomment");        
    }
    
    
    public ActionForward revrating(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {                         
        String finalTarget="revratingnew";
        try{         
            String grantnum = request.getParameter("gid");
            String projnum = request.getParameter("pn");
                      
            DBHandler dbh = new DBHandler();
            GrantBean gb=new GrantBean();
            
            if(grantnum!=null)            
                gb = dbh.getRecordBean(Long.parseLong(grantnum));
            else if(projnum!=null)
                gb = rdb.getBasicGrantInfo(projnum);
            request.setAttribute("thisGrant", gb); 
          
            ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
            ArrayList allcomm = rab.getAllLgRatingsForGrant(gb.getGrantid(), gb.getFycode());
            request.setAttribute("allRatings", allcomm);      
            
            if(gb.getFycode() <12){//new eval form starting in 2011-2012
                EligibilityDbBean edb = new EligibilityDbBean();
                CoversheetBean csb = edb.getLgBonusScoring(gb.getGrantid());
                request.setAttribute("CoverBean", csb);
            
                finalTarget="revrating";
            }
                  
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward(finalTarget);        
    }
    
    
    public ActionForward delibsummary(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {                    
        String finalTarget="delibsummary";
        try{         
            String grantnum = request.getParameter("gid");
            long grantid = Long.parseLong(grantnum);
            String pangraid= request.getParameter("pgid");
                      
            DBHandler dbh = new DBHandler();            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
                                   
            //get any comments/ratings panel has made for this grant
            PanelDbBean pdb = new PanelDbBean();
            PanelReviewBean pb = pdb.getDeliberationPanelGrant(Long.parseLong(pangraid));
            
            BudgetDBHandler bdh = new BudgetDBHandler();
            int totalAmtAppr = bdh.calcTotalAmtApproved(grantid, 0);//from budget pages
            pb.setTotamtappr(totalAmtAppr);
            
             //get bonus scoring
             CoversheetBean csb =new CoversheetBean();
              if(gb.getFycode()<12){
                  EligibilityDbBean edb = new EligibilityDbBean();
                  csb = edb.getLgBonusScoring(grantid);
                  request.setAttribute("CoverBean", csb);                   
              }         
              
            if(gb.getFycode()>=16)
                finalTarget="delibsummary1516";
            else if(gb.getFycode()==15)
                 finalTarget="delibsummary1415";
            else if(gb.getFycode()>=13)
                finalTarget="delibsummary1213";
            else if(gb.getFycode()==12)
               finalTarget="delibsummarynew";
             
                        
            //get averages of all reviewer ratings/recommended amts for this grant
            ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
            RatingBean rb =rab.getLgAvgRatingByCategory(Long.parseLong(pangraid), gb.getFycode());
            
            if(gb.getFycode()>12){
              //get panel ratings for this grant; if rating doesnot extist->use avg rating
              rb = pdb.getDeliberationRatings(Long.parseLong(pangraid), rb);
              rb.setGraid(pb.getGrantid());
              rb.setPanelgrantId(pb.getPanelgrantid());
              //starting fy 2012-13, panel score is read-only; sum of delib rating categories
              pb.setFinalscore(rb.getFinalScoreSum());
            }
            request.setAttribute("RatingBean", rb);
            
            
            //find out if application was approved or denied
            ApprovalsDBbean adb = new ApprovalsDBbean();
            pb.setInitialappr( adb.isInitialAppApproved(grantid) );
            pb.setBonuspts(csb.getScore());             
            request.setAttribute("PanelReviewBean", pb);
             
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward(finalTarget);        
    }
    
    
    public ActionForward regionlist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("regfy");
              String regtype = request.getParameter("regnum");
              
               //get grants for region, including PM info
               Vector allapps = rdb.getGrantsSubmittedRegionFy(Integer.parseInt(fycode), Integer.parseInt(regtype));
               Vector allgrants = rdb.getProjectManagerInfo(allapps);
               s.setAttribute("allGrants", allgrants);
                                              
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("regionlist");        
      }
      
    //updated 8/23/10 to include region
    public ActionForward countylist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("cofy");
              String countytype = request.getParameter("conum");
              
               //get grants for county
               Vector allapps = rdb.getGrantSubmittedCounty(Integer.parseInt(fycode), Integer.parseInt(countytype));
               s.setAttribute("allGrants", allapps);
                                             
              FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
              request.setAttribute("fybean", fb);
              request.setAttribute("rptname", " applications ");
              
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("applications");        
      }
            
      
    public ActionForward finalrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("finalrptfy");
              String finalyn = request.getParameter("finalrptyn");
              
               //get grants where init=appr but final is/not submitted
               Vector allapps = rdb.getFinalRptNotSubmitted(Integer.parseInt(fycode), Integer.parseInt(finalyn));
               s.setAttribute("allGrants", allapps);                                             
                           
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("finalrpt");        
      }
      
    public ActionForward peoplerpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("peoplefy");
                           
               //get grants submitted, with PD, RMO, CEO names
               Vector allapps = rdb.getContactsForSubmitGrants(Integer.parseInt(fycode), 80);
               s.setAttribute("allGrants", allapps);                                             
                           
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("peoplerpt");        
      }
      
    public ActionForward summarydescr(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("descfy");
                           
               //get grants submitted, with summ descr narrative
               Vector allapps = rdb.getGrantsSummaryDescr(Integer.parseInt(fycode), 80);
               s.setAttribute("allGrants", allapps);                                             
                           
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("summarydescr");        
      }
      
      
    public ActionForward cooprpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("coopfy");
              
               //get grants where iscoop=true; uses db function to get participants
               Vector allapps = rdb.getCooperativeGrants(Integer.parseInt(fycode), 80);
               s.setAttribute("allGrants", allapps);    
               
              FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
              request.setAttribute("fybean", fb);
              request.setAttribute("rptname", " Cooperative grants ");
              request.setAttribute("cooperative", "true");
                           
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("applications");        
      }
      
      
    public ActionForward allregion(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("allregfy");
              
               //get grants for all regions
               Vector allapps = rdb.getGrantsSubmittedRegionFy(Integer.parseInt(fycode), 0);
               s.setAttribute("allGrants", allapps);                
                
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("allregion");        
      }
      
    //updated 8/23/10 for region type
    public ActionForward denylist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("denyfy");
              
               //get denied grants during fy 
               Vector subGrants = rdb.getDeniedProjects(Integer.parseInt(fycode), 80);
               Collections.sort(subGrants, GrantBean.InstitutionComparator);//sort by inst name
               s.setAttribute("allGrants", subGrants);
               
               FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
               request.setAttribute("fybean", fb);
              request.setAttribute("rptname", " denied applications ");
                 
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("applications");        
      }
      
      
    public ActionForward countfmn(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("fmnfy");
              
              SearchResultBean srb = rdb.getCountFullModifyNone(Integer.parseInt(fycode));
              s.setAttribute("ResultBean", srb);
              
              FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
              request.setAttribute("fybean", fb);
               
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("countfmn");        
      }
      
      
    public ActionForward scoreorder(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("scorefy");
              String panelid = request.getParameter("panelNum");
              
              Vector allgrants = rdb.getLgScoreOrder(Integer.parseInt(fycode), Long.parseLong(panelid));
              s.setAttribute("allGrants", allgrants);
                             
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("scoreorder");        
      }
    
    
  /**
     *new 9/15/15 per DMEADOWS; get average of all at-home reviewer scores, then list each project 
     * in score order of at-home scores for selected panel
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward scoreorderathome(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {                                      
        try{         
            HttpSession s = request.getSession();
                        
            String panel = request.getParameter("panelNumAtHome");
                          
            //get all grants/reviewers for panel (this is same as "scorerecpanel" action)
            Vector allRevAssign = rdb.getRevRecommendForPanel(Long.parseLong(panel));               
            Vector allrecords = rdb.getLgRevScoresForPanel(allRevAssign);
            Collections.sort(allrecords, ReviewerAssignBean.ProjectNumComparator);
                        
            
            //parse list; get average of all reviewer scores per grant_id; only 1 row per grant_id
            List panelScores = rdb.parseReviewerScoresToAverage(allrecords);
            Collections.sort(panelScores, ReviewerAssignBean.CategoryComparator);
            s.setAttribute("allRevAssign", panelScores);
                           
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("scoreorderathome");        
    }
      
      
    public ActionForward budgetcateg(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("categfy");
              
              Vector allgrants = rdb.getBudgetCategoryTotals(Integer.parseInt(fycode));
              s.setAttribute("allGrants", allgrants);
                             
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("budgetcateg");        
      }
      
      
    public ActionForward directorrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {                                      
          try{         
              HttpSession s = request.getSession();
              String fycode = request.getParameter("dirfy");
              
              Vector allgrants = rdb.getDirectorMissingApps(Integer.parseInt(fycode), 80);
              s.setAttribute("allGrants", allgrants);
                             
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("directorrpt");        
      }
      
      
    public ActionForward bonusptsrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {            
          HttpSession s = request.getSession();
          try{         
              String fycode = request.getParameter("bonusfy");
              
               //get all reviewers participation and assignments for selected fy
               Vector allRev = rdb.getBonusQuestions(Integer.parseInt(fycode), 80);
               s.setAttribute("allGrants", allRev);
               
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("bonuspts");        
      }
      
      
      
    public ActionForward scorerecpanel(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {            
          HttpSession s = request.getSession();
          try{         
              //String fycode = request.getParameter("scorerecfy");
              String panel = request.getParameter("panelId");
                            
              //get all grants/reviewers for panel
              Vector allRevAssign = rdb.getRevRecommendForPanel(Long.parseLong(panel));               
              Vector allrecords = rdb.getLgRevScoresForPanel(allRevAssign);
              s.setAttribute("allRevAssign", allrecords);
               
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("scorerecpanel");        
      }
      
      
    public ActionForward decnotes(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {            
          HttpSession s = request.getSession();
          try{         
              String fycode = request.getParameter("decnotesfy");
              String fund = request.getParameter("decnotesfund");
                            
              //get all pm/rmo for all matching grants
              Vector allgrants = rdb.getDecisionByRecomm(Integer.parseInt(fycode), fund);
              Vector allrecs = rdb.getRmoEmailForGrants(allgrants);
              s.setAttribute("allDecNotes", allrecs);
               
          }catch(Exception e){
            System.out.println("error LgReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("decnotes");        
      }
      
    public ActionForward apptyperpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception {
           HttpSession s = request.getSession();
                     
           try{
                String fycode = request.getParameter("apptypefy");
                String apptypestr = request.getParameter("apptype");
                int apptype = Integer.parseInt(apptypestr);
           
                if(apptype==1){//cooperative
                   //get grants where iscoop=true; uses db function to get participants
                                        
                    Vector allapps = new Vector();
                    if(Integer.parseInt(fycode)<15) {//starting 14-15 no more cooperative; demo instead
                       allapps = rdb.getCooperativeGrants(Integer.parseInt(fycode), 80);
                    }
                    
                   s.setAttribute("allGrants", allapps);    
                                      
                   request.setAttribute("rptname", " Cooperative grants ");
                   request.setAttribute("cooperative", "true");
                }
                else if(apptype==2){//shared services
                    Vector allapps = rdb.getSharedServicesGrants(Integer.parseInt(fycode), 80);
                    s.setAttribute("allGrants", allapps);
                    
                    request.setAttribute("rptname", " Shared Services grants ");
                }
                else if(apptype==3){//individual 
                    Vector allapps = rdb.getIndividualTypeGrants(Integer.parseInt(fycode), 80);
                    s.setAttribute("allGrants", allapps);
                    
                    request.setAttribute("rptname", " Individual grants ");
                }                
               else if(apptype==4)//demo
                {
                  //demo projects starting 14-15 reuse old 'cooperative' attribute
                    
                  Vector allapps = new Vector();
                  if(Integer.parseInt(fycode)>14){//demo  starting in 2014-15
                      allapps = rdb.getCooperativeGrants(Integer.parseInt(fycode), 80);
                  }
                  s.setAttribute("allGrants", allapps);
                  
                  request.setAttribute("rptname", " Demonstration grants ");
                }
                FiscalYearBean fb = rdb.getFiscalYearInfo(fycode);
                request.setAttribute("fybean", fb);
                 
           }catch(Exception e){
               System.out.println("error LgReportAction "+e.getMessage().toString());
           }
           return mapping.findForward("applications");                  
    }
    
  public ActionForward statisticrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {            
        HttpSession s = request.getSession();
        try{         
            String fycode = request.getParameter("statfy");
                                      
            //get all lgrmif statistics where final report submitted
            Vector allStatistics = rdb.getStatisticsRptFinalSubmit(Integer.parseInt(fycode));               
            s.setAttribute("allStats", allStatistics);
             
        }catch(Exception e){
          System.out.println("error LgReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("statisticrpt");        
    }
    
    
    
}
