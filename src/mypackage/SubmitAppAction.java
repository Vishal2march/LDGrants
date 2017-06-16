/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SubmitAppAction.java
 * Creation/Modification History  :
 *
 * SH   8/14/09   Created
 *
 * Description
 * This dispatch action will check if initial or final application items are missing
 * and show confirm submit page for lg/di/fl/al/cn. Also used to submit initial/final
 * apps for sa/co/di/lg/cn.
 *****************************************************************************/
package mypackage;

import clobpackage.ClobBean;

import construction.AllocationYearBean;
import construction.CnApplicationBean;
import construction.ConstructionDBbean;

import construction.SystemAssignBean;
import gov.nysed.oce.ldgrants.grants.common.service.GrantSubmissionService;
import gov.nysed.oce.ldgrants.user.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import statutory.ApcntHomeAction;


public class SubmitAppAction extends DispatchAction{
    private SubmissionDBbean sdb = new SubmissionDBbean();
    private DBHandler dbh = new DBHandler();
      
    public ActionForward verifyinitial(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = request.getParameter("id");
            long grantid= Long.parseLong(grantnum);
            String fund = request.getParameter("fund");
            int fccode= Integer.parseInt(fund);
                                                
            //check for missing narratives
            Vector missingNar = sdb.checkMissingNarratives(grantid, fccode);
            if(missingNar.size()>0)
                request.setAttribute("missingNarr", missingNar);  
                
            //check for missing budget
            BudgetDBHandler bdh = new BudgetDBHandler();
            int totreq=0;
            if(fccode==40 || fccode==42){//adult/family lit
                GrantBean gb=dbh.getRecordBean(grantid);
                /*rmvd 10/16/12 - no longer using this fn starting FY2013-14
                Vector<TotalsBean> totalsBeans = bdh.getTotAmtReqLiFYPeriod(grantid, gb.getFycode(), gb.getFccode());
                //if size is !=2, then money was not requested for years 1 and 2
                if(totalsBeans.size()<2)
                    request.setAttribute("incompleteBudget", totalsBeans);
                    
                for(TotalsBean tb: totalsBeans){
                    totreq += tb.getTotAmtReq();//need sum of totAmtReq for both years
                    if(tb.isWarning()){//warning msg means amtreq not within min/max amts for 1 or both FY's
                      request.setAttribute("incompleteBudget", totalsBeans);
                    }
                } */  
                
                /*removed per KBALSEN 1/19/16 - she is having them submit this budget page later
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
                }*/
                 
            }else{//all other programs -verify total amtReq >0                
                totreq = bdh.calcTotalAmtRequested(grantid, 0);   
                //1/13/14 new for cpdg - if amt >25k, remind about required mwbe docs
                if(fccode==5 && totreq>25000)
                      request.setAttribute("warningMwbe", "true");
                
                
                if(fccode==20){//for stateaid
                  //check that amtReq <= approp amt from ldac
                  GrantBean gb=dbh.getRecordBean(grantid);
                  if(totreq > gb.getLdacAppropAmt())
                      request.setAttribute("overBudget", "true");
                  
                  //also for stateaid - check for assurance record
                  AuthorizationsDBbean adb = new AuthorizationsDBbean();
                  AuthorizationBean ab = adb.getGrantAssurance(grantid);
                  if(ab.getId()==0)
                      request.setAttribute("missingAssurance", "true");
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
            
            //check for missing attachments - all programs except sa/lg/al/fl/stateaid             
            if(fccode!=6 && fccode!=80 && fccode!=40 && fccode!=42  && fccode!=20){
              Vector alldocs = dbh.getAllDocuments(grantid);
              if(alldocs.size()<1)
                  request.setAttribute("missingAttach", "true");
               
               //FOR CONSTRUCTION
               if(fccode==86){
                   ConstructionDBbean cdb = new ConstructionDBbean();
                   //check for at least 1 fund source record
                   ArrayList allfunds = cdb.getAllFundsForProject(grantid);
                   if(allfunds.size()<1)
                        request.setAttribute("missingFunds", "true");
                        
                   //check for coversheet (building & cost info)
                   GrantBean gb=dbh.getRecordBean(grantid);
                   CnApplicationBean cab= cdb.getBuildingGrantInfo(grantid);
                   cab.setFycode(gb.getFycode());
                   if(cab.getGrantBuildingId()==0)
                        request.setAttribute("missingBuilding", "true");
                   else
                       cab = cdb.determineCostAmts(cab, grantid);//get $ amts
                       
                  //check for proj categories
                  HashMap allcategories = cdb.getExistingProjectTypes(grantid);
                  if(allcategories.size()==0)
                     request.setAttribute("missingProjCategory", "true");
                       
                   //validate costreq is <= 50% (or 75% after FY12) of cost of proj(amtreq)
                   if(cab.getRequestCost() > cab.getMaxRequestCost())
                        request.setAttribute("wrongFund", "true");                                                                      
                                                
                   //for construction; verify required attachments
                   Vector missingAttach = sdb.checkMissingCnAttach(alldocs, cab);
                   if(missingAttach.size()>0)
                        request.setAttribute("requiredAttach", missingAttach);
                   
                   //6/10/15 per LWebb; for construct; if request>$25k then need mwbe answer
                   //6/22/15; moved mwbe to separate section to be completed AFTER app submitted
                 /*  if(cab.getRequestCost()>25000){                      
                      String mwbe = dbh.getMwbeParticipation(grantid);
                      if(mwbe==null || mwbe=="")
                          request.setAttribute("missingMwbe", "true");
                   } */
                                                           
               }
                  
            }
            
            //check for missing coversheet lgrmif only (PM, RMO, region, govtype, category req)
            if(fccode==80){                                
                CoversheetBean csb = new CoversheetBean();
                GrantBean gb=dbh.getRecordBean(grantid); 
                csb.setSedrefinstid(gb.getInstID());
                csb.setGrantid(grantid);  
                csb.setModule("lg");
                OfficerDBbean odb = new OfficerDBbean();
                csb= odb.getProjectManager(csb);
                csb= odb.getAdditionalContact(csb, 1);
                
                EligibilityDbBean edb = new EligibilityDbBean();
                csb = edb.getArchivesGovtInfo(csb);
                csb = edb.getInstEligibilityForCoversheet(csb, 80);
                csb.setAmtrequested(totreq);              
                
                //validate coversheet bean
                 ActionErrors ae = null;  
                 ae = csb.validate(mapping, request);
                if(ae !=null && (ae.size() > 0))
                    request.setAttribute("missingCover", "true");
                    
                //validate that if 1 app already submitted, then 1 of the 2 apps must
                //be disastermgmt category (either this one or the submitted one)
                //EXCEPT for DORIS  
                //mod 10/4/13 - 62 counties can have 2 apps (without disaster) as long as they are a county
                if(gb.getInstID()!=800000047654L)
                    dbh.validateOneDisasterApp(gb, csb);
                
                //validate if GIS project; then a county must be lead or co-apcnt
                /*REMOVED 9/27/11 per FC
                 * if(csb.getProjcategoryId()==8){
                    if(! dbh.validateCountyForGISCategory(grantid))
                        request.setAttribute("missingGisCounty", "true");                    
                }*/
                    
                //check that total amt_requested does not exceed app_type limits
                TotalsBean tb = new TotalsBean();
                request.setAttribute("amtReq", totreq);
                
                //doris institution & admin project category have limit of $200k
                if(csb.getSedrefinstid()==800000047654L && csb.getProjcategoryId()==11 ) {
                    if(totreq > 200000)
                        request.setAttribute("incorrectBudg", "true");
                }
                /*else if(csb.isCooperative()){
                    if(totreq > tb.getLgCoopLimit())
                        request.setAttribute("incorrectBudg", "true");
                } modified 10/3/13 with version below; cooperative is now demonstration starting FY14-15*/
                else if(csb.isCooperative()){
                    if(csb.isPlanningDemo()){
                        if(totreq > tb.getLgPlanLimit())
                          request.setAttribute("incorrectBudg", "true");
                    }
                    else if(csb.isImplementDemo()){
                        if(totreq > tb.getLgImplementLimit())
                          request.setAttribute("incorrectBudg", "true");
                    }
                }
                else if(csb.isSharedserv()){
                    if(totreq > tb.getLgSharedLimit())
                        request.setAttribute("incorrectBudg", "true");
                }
                else{//individual project
          
                  //check that total amt_requested does not exceed app_type limits
                    if(totreq > tb.getLgIndivLimit())//everyone else has $50k indiv limit
                        request.setAttribute("incorrectBudg", "true");
                }
            }                        
            //set values for lit
            if(fccode==40)
                request.setAttribute("prog", "al");
            else if(fccode==42)
                request.setAttribute("prog", "fl");              
              
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifyinitial");        
    }      
   
   
   
    public ActionForward verifyfinal(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = request.getParameter("id");
            long grantid= Long.parseLong(grantnum);
            String fund = request.getParameter("fund");
            int fccode= Integer.parseInt(fund);
                        
            //check for missing expenses
            BudgetDBHandler bdh = new BudgetDBHandler();
            int totexp=0;
                        
            if(fccode==86){//construction expenses in expenditures table
                totexp = bdh.calcCnFinalExpenseTotal(grantid);
            }
            else if(fccode==40 || fccode==42){//literacy; verify exp <= appropriation
            	// get grant fy
        		DBHandler dbh = new DBHandler();
        		GrantBean gb = dbh.getRecordBean(grantid);
        		
        		//calc total expenses in budget tables
                totexp = bdh.calcTotalExpSubmitted(grantid, gb.getFycode());    
        		
            	// get the final appropriation for this inst/FY
        		AllocationYearBean allocationYearBean = bdh.getAllocationForInstFy(gb.getFycode(), fccode, gb.getInstID());
        		if(totexp > allocationYearBean.getFinalRecommend())
        			request.setAttribute("overBudget", "true");
        		
            }
            else{
                //all other programs - check for expenses in budget tables
                totexp = bdh.calcTotalExpSubmitted(grantid, 0);    
                
                //check that expenses do NOT exceed amt approved
                int totappr = bdh.calcTotalAmtApproved(grantid, 0);
                if(totexp > totappr)//expenses must be <= amtappr
                    request.setAttribute("overBudget", "true");
            }
            if(totexp<1)
               request.setAttribute("missingBudg", "true");
              
                            
            //check for missing final rpt narrative - all programs except lit/cn
            Vector missingNarr = new Vector();
            if(fccode==40){
                request.setAttribute("prog", "al");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 1, 40);
            }
            else if(fccode==42){
                request.setAttribute("prog", "fl");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 1, 42);
            }
            else if(fccode!=86){//note 3/19/12: cn does not use final narratives
                missingNarr = sdb.checkMissingFinalRpt(grantid);                
            }
            if(missingNarr.size()>0)
                request.setAttribute("missingNarr", missingNarr); 
             
                
            //cn check for missing attachments
            if(fccode==86){
                Vector alldocs = dbh.getAllDocuments(grantid);
                //completion form and final photos are required
                Vector missingAttach = sdb.checkMissingCnFinalAttach(alldocs);
                if(missingAttach.size()>0)
                     request.setAttribute("requiredFinalAttach", missingAttach);                
            }
            
            //lgrmif check for missing statistics
            if(fccode==80){
                Vector missingStat = sdb.checkMissingLgStatistics(grantid);
                if(missingStat.size()==0)
                    request.setAttribute("missingStat", "true");
            }
                       
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifyfinal");        
    }      
   
   
    public ActionForward verifyFinalYr2(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = request.getParameter("id");
            long grantid= Long.parseLong(grantnum);
            String fund = request.getParameter("fund");
            int fccode= Integer.parseInt(fund);
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
                        
            //check for missing expenses yr 2
            BudgetDBHandler bdh = new BudgetDBHandler();
            int totexp = bdh.calcTotalExpSubmitted(grantid, fycode);
            if(totexp<1)
              request.setAttribute("missingBudg", "true");
              
            //check that expenses do NOT exceed yr 2 amt approved
            int totappr = bdh.calcTotalAmtApproved(grantid, fycode);
            if(totexp > totappr)//expenses must be <= amtappr
                request.setAttribute("overBudget", "true");
              
            //check for missing final rpt
            Vector missingNarr = new Vector();
            if(fccode==40){
                request.setAttribute("prog", "al");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 2, 40);
            }
            else if(fccode==42){
                request.setAttribute("prog", "fl");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 2, 42);
            }            
            if(missingNarr.size()>0)
                request.setAttribute("missingNarr", missingNarr); 
                                  
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifyFinalYr2");        
    }      
    
    //used to verify literacy year 3 final reports before submit
    public ActionForward verifyFinalYr3(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = request.getParameter("id");
            long grantid= Long.parseLong(grantnum);
            String fund = request.getParameter("fund");
            int fccode= Integer.parseInt(fund);
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
                        
            //check for missing expenses yr 3
            BudgetDBHandler bdh = new BudgetDBHandler();
            int totexp = bdh.calcTotalExpSubmitted(grantid, fycode);
            if(totexp<1)
              request.setAttribute("missingBudg", "true");
              
            //check that expenses do NOT exceed yr 3 amt approved
            int totappr = bdh.calcTotalAmtApproved(grantid, fycode);
            if(totexp > totappr)//expenses must be <= amtappr
                request.setAttribute("overBudget", "true");
              
            //check for missing final rpt
            Vector missingNarr = new Vector();
            if(fccode==40){
                request.setAttribute("prog", "al");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 3, 40);
            }
            else if(fccode==42){
                request.setAttribute("prog", "fl");
                missingNarr = sdb.checkMissingLitFinalNarr(grantid, 3, 42);
            }            
            if(missingNarr.size()>0)
                request.setAttribute("missingNarr", missingNarr); 
                                  
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifyFinalYr3");        
    }      
   
   
    public ActionForward submitinitial(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{        
            UserBean userb = (UserBean) s.getAttribute("lduser");
            String grantnum = (String) s.getAttribute("grantid");
            long grantid=Long.parseLong(grantnum);            
            String fund=request.getParameter("prog");
            int fccode = Integer.parseInt(fund);
                  
            // update fields in grant and grant_submissions table
            int result = sdb.submitInitialApp(grantid, userb, fccode); 
                        
            if(fccode==80){//lg sends email to both PM and RMO
              EmailNotificationBean eb = new EmailNotificationBean();          
              boolean emailResult = eb.sedemsLgAppSubmitted(userb, grantid);
            }
            else if(fccode==86){
                //send email notifying that app submitted to PLS
                EmailNotificationBean eb = new EmailNotificationBean();       
                boolean emailResult = eb.sedemsConstructAppSubmitted(userb, grantid, fccode);
            }
            else if(fccode==20){
              ;
              //for stateaid; no email to send
            }
            else{//cp sends email to either PO or PM              
              EmailNotificationBean eb = new EmailNotificationBean();          
              boolean emailResult = eb.sedemsCpAppSubmitted(userb, grantid, fccode);
            }
              
              
            if(fccode==7 || fccode==5){//for cp coor and disc
              //assign program officer (currently BLilley) as reviewer when grant is submitted     
              ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
              int outcome = rab.assignPrgmOfficerAsRev(grantid, userb);     
            }            
            else if(fccode==86){//for construction
                
                 ConstructionDBbean cdb = new ConstructionDBbean();
                 //check if an assignment record has been created, if not- create one now
                 if (!cdb.doesSystemAssignExist(grantid)) {
                    GrantBean gb = dbh.getRecordBean(grantid);//get inst_id
                                  
                    long libsysmap = cdb.getLibrarySystemMapId(gb.getInstID());//get librarySystemMap 
                      
                    result = cdb.insertAssignRecord(userb, grantid, libsysmap);//create record
                 }
            }
            
            //for all programs except cn -> generate homepage; cn goes to confirm page
            if(fccode==86){
                GrantBean gb = dbh.getRecordBean(grantid);
                request.setAttribute("thisGrant", gb); 
            }else
                generateHomepage(fccode, request, s); 
                                      
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("submitinitial");        
    }      
   
   
   
    public ActionForward submitfinal(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{        
            UserBean userb = (UserBean) s.getAttribute("lduser");
            String grantnum = (String) s.getAttribute("grantid");
            long grantid=Long.parseLong(grantnum);            
            String fund=request.getParameter("prog");
            int fccode = Integer.parseInt(fund);
                  
            // update fields in grant and grant_submissions table
            int result = sdb.submitFinalApp(grantid, userb, false, 0);  
                       
            if(fccode==80){//lg emails go to PM and RMO
              EmailNotificationBean eb = new EmailNotificationBean();          
              boolean emailResult = eb.sedemsLgFinalSubmit(userb, grantid);
            }
            else if(fccode==86){//construction - email PM, cc Linda&Kim
              EmailNotificationBean eb = new EmailNotificationBean();   
              boolean emailResult = eb.sedemsConstructFinalSubmit(userb, grantid);
            }
            else if(fccode==20){
              ;
              //for stateaid; no emails
            }
            else{
              EmailNotificationBean eb = new EmailNotificationBean();          
              boolean emailResult = eb.sedemsCpFinalAppSubmit(userb, grantid, fccode);        
            }
                     
            generateHomepage(fccode, request, s);                               
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("submitfinal");        
    }      
    
    
    public ActionForward submitamend(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{        
            UserBean userb = (UserBean) s.getAttribute("lduser");
            String grantnum = (String) s.getAttribute("grantid");
            long grantid=Long.parseLong(grantnum);            
            String fund=request.getParameter("prog");
            int fccode = Integer.parseInt(fund);
                  
            // update fields in grant and grant_submissions table
            int result = sdb.submitAmendment(grantid, userb);  
                                   
            EmailNotificationBean eb = new EmailNotificationBean();   
            if(fccode==80){
                boolean emailResult = eb.sedemsLgAmendmentSubmit(userb, grantid);
            }else{
                boolean emailResult = eb.sedemsAmendmentSubmit(userb, grantid);      
            }
                                                               
            generateHomepage(fccode, request, s);                               
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("submitamend");        
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
            if(submittype.equals("initial"))   { 
              //old method; does not update FMT_ID	
              //result = sdb.submitInitialApp(grantid, userb, fccode); 
              //new method - inserts FMT_ID fk.
              GrantSubmissionService grantService = new GrantSubmissionService();
	          //get new user object	      	  
	      	  User user = new User();
	      	  user.setUserId(userb.getUserid());
	      	  user.setCreatedBy(userb.getUserid());
	      	  user.setModifiedBy(userb.getUserid());
              grantService.submitInitial(user, grantid);              
            }
            else if(submittype.equals("interim"))
              result = sdb.submitLiInterim(grantid, userb);
            else if(submittype.equals("final")){
              //old method; does not update FMT_ID
              //result = sdb.submitFinalApp(grantid, userb, false, 0); 
              //new method - inserts FMT_ID fk.
              GrantSubmissionService grantService = new GrantSubmissionService();
  	          //get new user object	      	  
  	      	  User user = new User();
  	      	  user.setUserId(userb.getUserid());
  	      	  user.setCreatedBy(userb.getUserid());
  	      	  user.setModifiedBy(userb.getUserid());
              grantService.submitFinalYear1(user, grantid);   
            }
            else if(submittype.equals("amend"))
              result = sdb.submitAmendment(grantid, userb);
            else if(submittype.equals("finalyr2"))
              result = sdb.submitFinalApp(grantid, userb, true, 2);
            else if(submittype.equals("finalyr3"))
              result = sdb.submitFinalApp(grantid, userb, true, 3);
              
            if(result>0){
                LitEmailNotifyBean eb = new LitEmailNotifyBean();
                if(submittype.equals("amend"))
                    eb.sendLiAmendmentSubmit(userb, grantid);
                else if(submittype.equals("initial"))//on initial submit; cc kbalsen
                    eb.sendLiSubmit(userb, grantid, true);
                else                
                    eb.sendLiSubmit(userb, grantid, true); //2/11/16 on final submit; cc kbalsen too
            }                                   
            generateHomepage(fccode, request, s);
                          
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("submitliteracy"+prog);        
    }      
    
   
  public boolean checkSessionTimeout(HttpSession sess)
  {
    boolean timeout = false;
    //check for session timeout
    boolean userID = (sess.getAttribute("lduser") != null);
    if (!userID && sess.isNew())
    {      
      timeout = true;
    }      
    return timeout;
  }
      
   
  public void generateHomepage(int fccode, HttpServletRequest request, HttpSession sess)
  {
      try{        
          //remove old grants that the user was viewing
          sess.removeAttribute("grantid");             
          UserBean userb = (UserBean) sess.getAttribute("lduser");
                
          switch(fccode){
              case 5:
                userb.setReadaccess(userb.getPrgdi().equals("read"));
                break;
              case 7:
                userb.setReadaccess(userb.getPrgco().equals("read"));
                break;
              case 40:
                userb.setReadaccess(userb.getPrgal().equals("read"));
                break;
              case 42:
                userb.setReadaccess(userb.getPrgfl().equals("read"));
                break;
              case 80:
                userb.setReadaccess(userb.getPrglg().equals("read"));
                break;
              case 86:
                userb.setReadaccess(userb.getPrgconstruction().equals("read"));
                break;
              case 20:
                userb.setReadaccess(userb.getPrgNycStateaid().equals("read"));
                break;
          }
          sess.setAttribute("lduser", userb);
         
        
          //find all grants for this user's instID
          HashMap results =dbh.getGrantsForProgramHome(userb.getInstid(), fccode);  
          if(fccode==80 || fccode==86) {
              results = dbh.getSubmitStatusForHome(results);
          }
          if(results.containsKey(new Integer(1)))//curr grants exist
          {
            Vector grants = (Vector) results.get(new Integer(1));
            request.setAttribute("allGrants", grants);
          }
          if(results.containsKey(new Integer(2)))
          {
            Vector grants = (Vector) results.get(new Integer(2));
            request.setAttribute("partGrants", grants); 
          } 
                      
          ApcntHomeAction ahome = new ApcntHomeAction();
          AppDatesBean ab =  ahome.verifyCreateAppAcceptable(fccode, userb.getInstid());
          request.setAttribute("appDates", ab);
                                  
          if(fccode==7)
          {
              //get the total amount of money available for this fy
              BudgetDBHandler bdh = new BudgetDBHandler();
              TotalsBean tb = bdh.getTotalCoAmtAvailableForFY(ab.getFycode());
              request.setAttribute("totAvail", tb);
              //get total requested so far for this fy
              tb = bdh.getTotCoAmtReqForFy(ab.getFycode());
              request.setAttribute("totReq", tb);       
          }
              
      }catch(Exception e){
        System.out.println("error SubmitAppAction generateHomepage "+e.getMessage().toString());
      }
  }
  
  
  
  
  
  
  
    public ActionForward saveMwbe(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               UserBean userb = (UserBean) sess.getAttribute("lduser");
               //get the form info user entered
               ActionForm myform = (ActionForm) request.getAttribute("appStatus");
               AppStatusBean ab = (AppStatusBean) myform;
               
               DBHandler dbh = new DBHandler();
               dbh.saveMwbeParticipation(ab, ab.getGrantid(), userb);
               
               //---------------------------
               //need appstatus bean to determin whether app is lock/unlock               
               GrantBean gb = dbh.getRecordBean(ab.getGrantid());
               sess.setAttribute("thisGrant", gb); 
                
               AppStatusBean asb = dbh.getApplicationStatus(ab.getGrantid());
               if(gb.getFccode()==86){
                 asb.setSystemName(gb.getSystemName());
                 asb.setSystemInstId(gb.getSystemInstId());
                 ConstructionDBbean cdb = new ConstructionDBbean();
                 asb = cdb.determineSysDueDateForMember(asb, gb.getFycode());
               }
               request.setAttribute("appStatus", asb);
             
                                                                   
           } catch (Exception e) {
              System.out.println("error SubmitAppAction:saveMwbe "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("checklist");
     }
    
    
    
    public ActionForward verifymwbe(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = request.getParameter("id");
            long grantid= Long.parseLong(grantnum);
            //String fund = request.getParameter("fund");
            //int fccode= Integer.parseInt(fund);
            
            GrantBean gb=dbh.getRecordBean(grantid);
                        
            ConstructionDBbean cdb = new ConstructionDBbean();
            CnApplicationBean cab= cdb.getBuildingGrantInfo(grantid);
            cab.setFycode(gb.getFycode());
            if(cab.getGrantBuildingId()==0)
                 request.setAttribute("missingBuilding", "true");
            else
                cab = cdb.determineCostAmts(cab, grantid);//get $ amts
          
                        
          //6/10/15 per LWebb; for construct; if request>$25k then need mwbe answer
            if(cab.getRequestCost()>25000){
             String mwbe = dbh.getMwbeParticipation(grantid);
             if(mwbe==null || mwbe=="")
                 request.setAttribute("missingMwbe", "true");
          }
           
                       
        }catch(Exception e){
        System.out.println("error SubmitAppAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifymwbe");        
    }      
    
    
    
  public ActionForward submitmwbe(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
        
      try{        
          UserBean userb = (UserBean) s.getAttribute("lduser");
          String grantnum = (String) s.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);            
          String fund=request.getParameter("prog");
          int fccode = Integer.parseInt(fund);
                
          // update fields in grant_submissions table
          int result = sdb.submitMwbe(grantid, userb);  
                     
          if(fccode==86){//construction - email to Lynne/Linda/mwbe
            EmailNotificationBean eb = new EmailNotificationBean();   
            boolean emailResult = eb.sedemsConstructMwbeSubmit(userb, grantid);
          }
                             
          generateHomepage(fccode, request, s);  
          
      }catch(Exception e){
      System.out.println("error SubmitAppAction "+e.getMessage().toString());
      }
    return mapping.findForward("submitmwbe");        
  }      
  
  
  
  ///////////ACTIONS BELOW FOR LITERACY STARTING FY2016-19///////////////////
  
  
  
  public ActionForward verifyinitiallit(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      HttpSession s = request.getSession();  
      if(checkSessionTimeout(s))
        return mapping.findForward("timeout");
      String finalTarget="";
        
      try{         
          String grantnum = request.getParameter("id");
          long grantid= Long.parseLong(grantnum);
          String prog = request.getParameter("p");
          String directorcert = request.getParameter("directorcert");          
                   
          //get inst address info, grant
          GrantBean gb=dbh.getRecordBean(grantid); 
          request.setAttribute("thisGrant", gb);      
            
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);   
          
          
          //only proceed if user checked off checkbox
          if(directorcert==null){
                            
             //user did not check checkbox; send back to cert form, display message
             finalTarget = "certform"+prog;
             request.setAttribute("certwarning", "true");//display error message re: checkbox
              
            OfficerDBbean odb = new OfficerDBbean();
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");//gets sedref entry from sed_admin_positions table
            request.setAttribute("libDirectorBean", libdirectorBean); 
                                      
            //2/2/16 per KBALSEN; get yearly edlaw amt  
            BudgetDBHandler bdh = new BudgetDBHandler();
            AllocationYearBean ab = bdh.getAllocationForInstFy(gb.getFycode(), gb.getFccode(), gb.getInstID());
            request.setAttribute("allocAmount", ab);
                          
            //get the senate, congress districts, etc.
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);                        
          }
          else{
              //user checked checkbox - validate for literacy 2016-19
          
              finalTarget = "verifyinitial" + prog;//name of action mapping
                                                             
              //check for missing narratives
              Vector missingNar = sdb.checkMissingNarratives(grantid, gb.getFccode());
              if(missingNar.size()>0)
                  request.setAttribute("missingNarr", missingNar);
          }
                 
                                      
          //set values for lit
          if(gb.getFccode()==40)
              request.setAttribute("prog", "al");
          else if(gb.getFccode()==42)
              request.setAttribute("prog", "fl");              
            
      }catch(Exception e){
      System.out.println("error SubmitAppAction "+e.getMessage().toString());
      }
    return mapping.findForward(finalTarget);        
  }      
  
  
  
  
  public ActionForward submitliteracycert(ActionMapping mapping, ActionForm form,
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
          prog = request.getParameter("p");
                          
          int fccode=0;
          if(prog.equals("fl"))
            fccode=42;
          else if(prog.equals("al"))
            fccode=40;
                                       
          //new for 16-19 per KB, insert row into authorizations table for "cert form" checkbox
          AuthorizationsDBbean ab = new AuthorizationsDBbean();
          int certform = ab.insertLDAuthorization(grantid, userb);//library director   
          
          //insert row into grant_submissions table     - old method                        
          //int submitapp = sdb.submitInitialApp(grantid, userb, fccode); 
          //new method - inserts FMT_ID fk.
          GrantSubmissionService grantService = new GrantSubmissionService();
          //get new user object	      	  
      	  User user = new User();
      	  user.setUserId(userb.getUserid());
      	  user.setCreatedBy(userb.getUserid());
      	  user.setModifiedBy(userb.getUserid());
          int submitapp = grantService.submitInitial(user, grantid);   
          
            
          if(certform>0  &&  submitapp>0){
            //on initial submit; send PM confirmation email, cc kbalsen
            LitEmailNotifyBean eb = new LitEmailNotifyBean();            
            eb.sendLiSubmit(userb, grantid, true);
          }                                   
          generateHomepage(fccode, request, s);
                        
      }catch(Exception e){
      System.out.println("error SubmitAppAction "+e.getMessage().toString());
      }
      return mapping.findForward("home"+prog);       
  }      
  
  
}
