 /******************************************************************************
  * @author  : Dan Cokely/Stefanie Husak
  * @Version : 1.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  reviewAction.java
  * Creation/Modification History  :
  * Created 3/2011       
  * Description
  * This dispatch action will route all calls for construction pls reviewers to
  * search/submit apps, and view an app/narrative/budget, etc.
  *****************************************************************************/
package construction;

import clobpackage.ClobBean;

import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import mypackage.*;
import mypackage.UserBean;
import mypackage.DBHandler;
import java.util.HashMap;
import mypackage.GrantBean;
import construction.*;
import construction.ConstructionDBbean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class reviewAction extends DispatchAction {
    
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Log log = (Log)LogFactory.getLog(this.getClass());
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
    private ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
    private BudgetDBHandler bdh = new BudgetDBHandler();
       
  
  /**
     * SH-method will get attachments for cnreviewer. 2 diff attach jsps depending
     * on whether the review was submitted to LD or not. need to update to check for 
     * LD unlocking attachments.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward attachment(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
     {         
         String finalTarget="attachment";
         HttpSession sess = request.getSession();
         if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
            
         //verify grantid is in session
         if(!checkGrantId(sess))
            return mapping.findForward("idmissing");   
         
         try {      
           String grantnum = (String)sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
    
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
    
           Vector results = dbh.getAllDocuments(grantid);
           request.setAttribute("allDocs", results);     
           
           //get all info about system assignment
           SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
           if(sab.isSystemLockOut()){
               //if assignment is submitted; cannot add/delete attachments; go to readonly pge
               finalTarget="attachmentRead";
           }
           
         } catch (Exception e) {
            System.out.println("error reviewAction:attachment "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }   
         return mapping.findForward(finalTarget);
   }
   
   
   /**
     * SH-get the first budget tab for cn reviewer
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward budget(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      //verify grantid is in session 
      if(!checkGrantId(sess))
         return mapping.findForward("idmissing");   
        
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid= Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
          
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, 3, false);
          request.setAttribute("BudgetCollectionBean", bc);
                                    
          TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb1);            
          
          //get all info about system assignment
          SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
          request.setAttribute("assignBean", sab);
          
      }catch(Exception e){
            System.out.println("error reviewAction:budget "+e.getMessage().toString());
            log.error(e.getMessage().toString());
      }
      return mapping.findForward("budget");        
    }    
    
    /**
     * SH-get first narrative for cn reviewer
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
     public ActionForward narrative(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
       {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
           
         //verify grantid is in session
         if(!checkGrantId(sess))
            return mapping.findForward("idmissing");   
                    
         DescriptionBean pdb = new DescriptionBean();
         NarrativeDBbean ndb = new NarrativeDBbean();     
         int narrTypeID=95;//this is the first narrative (abstract)
         
         try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           GrantBean gb=dbh.getRecordBean(grantid); 
           request.setAttribute("thisGrant", gb);  
           
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
                   
           //get the narrative from the db and set to descrBean
             ClobBean cb = new ClobBean();
             cb.setGrantid(grantid);
             cb.openOracleConnection();      
             cb.getClobNarrative(narrTypeID); 
             cb.closeOracleConnection();
             
             pdb.setNarrative(cb.getData());
             pdb.setId(cb.getNarrID());
             pdb.setNarrTypeID(narrTypeID);  
             pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrTypeID));  
             pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCn(narrTypeID));
             
             //co/di/lg uses same jsp for init/final narr - determine whether init/final needs locking
             pdb.setLockNarrative(ndb.determineNarrativeLock(asb, narrTypeID));
             request.setAttribute("projNarrative", pdb);    
                    
             //get all info about system assignment
             SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
             request.setAttribute("assignBean", sab);
             
         }catch(Exception e){ 
             System.out.println("error reviewAction:narrative "+e.getMessage().toString());
             log.error(e.getMessage().toString()); 
         }      
         return mapping.findForward("narrative");      
       } 
    

   /**
     * SH-action verifies user's cnrev permission, verifies inst_id is a PLS, and
     * sends to cn rev search submitted applications
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   public ActionForward memberapps(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
     {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
         String finalTarget = "memberapps";    
         
        try{
            sess.removeAttribute("grantid");//clears any old grants id in session since 
             
            //verify that user's institution is a library system & they have cnrev permiss;
            //if not, return to welcomepage
            UserBean userb = (UserBean) sess.getAttribute("lduser");
            boolean isPLS =  cdb.isLibrarySystem(userb.getInstid());
            if(!userb.isReviewconstruction() || !isPLS)
                finalTarget="welcomePage";
            
            //if user has PLS reviewer access, prepopulate the search_members page
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);           
                                               
        }catch(Exception e){
            System.out.println("error reviewAction:memberapps "+e.getMessage().toString());
            log.error(e.getMessage().toString());
        }        
        return mapping.findForward(finalTarget);
     }
     
     
   /**
     * SH-get all submitted member applications for this PLS for this FY
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   public ActionForward listMemberApps(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
       HttpSession sess = request.getSession();    
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
       try{
        UserBean ub = (UserBean) sess.getAttribute("lduser");
         
        String year =  request.getParameter("fycode");
        int fy = Integer.parseInt(year);            
       
        Vector allapps = cdb.getSubmittedMemberApps(fy, ub.instid);
        request.setAttribute("allApplications", allapps);

       }catch(Exception e){
            System.out.println("error reviewAction:listMemberApps "+e.getMessage().toString());
            log.error(e.getMessage().toString());
       }       
       return mapping.findForward("memberapps");
    }
        
        
    /**
     * SH - if PLS choose a member app, set grantid in session, display rev
     * checklist for this app. 
     * also check for system_grant_assign and create record if needed
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward checklist(ActionMapping mapping, ActionForm form,
               HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
              
        try{               
            UserBean userb = (UserBean) sess.getAttribute("lduser");
               
            //link on rev memberapps page has grantid param that needs to be set to sess
            if(request.getParameter("id")!=null){
              String chosenGrant = request.getParameter("id");
              sess.setAttribute("grantid", chosenGrant);
            }    
            
            //verify grantid is in session or user choose a grantid to put in sess
            if(!checkGrantId(sess))
              return mapping.findForward("idmissing");   
            
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
                            
            //need appstatus bean to determin whether app is lock/unlock
            AppStatusBean as = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", as);
            
            GrantBean gb = dbh.getRecordBean(grantid);//need grant's inst_id
            request.setAttribute("thisGrant", gb);
            
            //check if an assignment record has been created, if not- create one now
            if (!cdb.doesSystemAssignExist(grantid)) {
                //get the librarySystemMap; create a new system assignment record                
                long libsysmap = cdb.getLibrarySystemMapId(gb.getInstID());
                 
                int result = cdb.insertAssignRecord(userb, grantid, libsysmap);
            }
            
            //get all info about system assignment
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            request.setAttribute("assignBean", sab);
                                                                                                                    
        } catch(Exception e){
            System.out.println("error reviewAction:checklist "+e.getMessage().toString());
        }             
        return mapping.findForward("checklist");            
    }
           
    
    /**
     * action will get all items for reviewer application form
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward coversheet(ActionMapping mapping, ActionForm form,
               HttpServletRequest request, HttpServletResponse response) throws Exception 
    {      
        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
        //verify grantid is in session 
        if(!checkGrantId(sess))
          return mapping.findForward("idmissing");   
              
        try{                                        
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
                            
            //need appstatus bean to determin whether app is lock/unlock
            AppStatusBean as = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", as);
          
            //get all info about system assignment
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            request.setAttribute("assignBean", sab);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            sess.setAttribute("thisGrant", gb);  
            
            OfficerDBbean odb = new OfficerDBbean();
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            request.setAttribute("libDirectorBean", libdirectorBean);  
            
            //get the senate, congress districts, etc.
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);           
                          
            //get the shpo exemption narrative
            ClobBean clobb = new ClobBean();
            clobb.setGrantid(grantid);
            clobb.openOracleConnection();      
            clobb.getClobNarrative(117); 
            clobb.closeOracleConnection();    
                        
            CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
            ab.setGrantId(grantid);
            //set shpo exempt narr fields
            ab.setShpoExemption(clobb.getData());
            ab.setShpoExemptionId(clobb.getNarrID());
            ab.setShpoExemptionNarrTypeId(117);
            ab.setGroundDisturb(as.isGroundDisturb());
            
            //set all other fields
            ab.setProjectTitle(gb.getTitle());
            ab.setInstId(gb.getInstID());
            ab.setFycode(gb.getFycode());
            ab = cdb.determineCostAmts(ab, grantid);
            ab= odb.getCnProjectManager(ab);
            ab= odb.getCnDirector(ab);  
            ab= cdb.getBuildingProjectTypes(ab);//building_projects table
            request.setAttribute("applicationFormBean", ab);   
                                                                                                                    
        } catch(Exception e){
            System.out.println("error reviewAction:coversheet "+e.getMessage().toString());
        }             
        return mapping.findForward("coversheet");            
    }
   
   
   
    /**
     * SH-action to get all ratings/answers and comments for the given system assignment
     * and display on evaluation form for editing.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward ratingform(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
         //verify grantid is in session 
         if(!checkGrantId(sess))
            return mapping.findForward("idmissing");   
                        
         try{
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
             
             String sysassign = request.getParameter("aid");
             long assignid = Long.parseLong(sysassign);
            
             //get all info about grant application
             GrantBean gb = dbh.getRecordBean(grantid);
             request.setAttribute("thisGrant", gb);
             
             //get all info about system assignment
             SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
             request.setAttribute("assignBean", sab);
             
             //get amtreq, 50%, total pls allocation, amtrecommend so far, etc
             AllocationYearBean ab = cadb.calcRequestApprAmounts(grantid, gb.getFycode());//amtreq & 50% amt
             AllocationYearBean ab2 = cadb.calcAllocAndAwardForPlsFy(sab.getSystemInstId(), gb.getFycode());
                         
             //get all eval form comments and answers
             CnRatingFormBean rb = cdb.getCnRatingComments(assignid);
             rb.setGrantid(grantid);
             rb.setAssignmentid(assignid);
             rb.setFycode(gb.getFycode());
             rb.setAmtrecommended(sab.getRecommendAmt());
             rb.setAmtrecommendedStr(sab.getRecommendAmtStr());
             rb.setProjectrecommend(sab.isRecommendation());
             rb.setAmountRequested(ab.getAmountRequested());//from alloc bean
             rb.setRequestCost(ab.getRequestCost());//from alloc bean
             rb.setMaxRecommendAmt(ab.getMaxRequestCost());//from alloc bean
             rb.setInitialAlloc(ab2.getInitialAlloc());//from alloc bean
             rb.setTallyAmountRecommend(ab2.getTallyAmountRecommend());//from alloc bean
             rb = cdb.getCnRatingAnswers(rb, assignid);
             request.setAttribute("reviewFormBean", rb);       
             
         }catch(Exception e){
            System.out.println("error reviewAction:ratingform "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("ratingform");
    }
            
   
   
   /**
     * SH-action used by cnreviewer to unlock application. currently unlocks all portions
     * of application (appsheet,narr,budget)-maybe change to unlock each portion individually?
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward unlock(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
          
         //verify grantid is in session
         if(!checkGrantId(sess))
           return mapping.findForward("idmissing");  
           
         try{
          UserBean ub = (UserBean) sess.getAttribute("lduser");
           
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
   
          //this code will unlock ALL portions (cover, narrs,budget) of app for member lib
          AppStatusBean asb = new AppStatusBean();
          asb.setCoversheetyn(false);
          asb.setProjdescyn(false);
          asb.setAmtreqyn(false);
          dbh.handleUnlockApp(grantid, ub, asb);
          
           //return to reviewer checklist page
           AppStatusBean as = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", as);
           
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb);
           
           //get all info about system assignment
           SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
           request.setAttribute("assignBean", sab);
    
         }catch(Exception e){
            System.out.println("error reviewAction:unlock "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("checklist");
    }
    
    
    /**
     * SH-set up Fy dropdown box on reviewer 'submit to ld' page
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadSubmit(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                  
         try{
             //prepopulate the search for apps to submit to LD page
             ArrayList allyears = dbh.dropDownFiscalYearsDesc();
             sess.setAttribute("dropDownList", allyears);       
             
         }catch(Exception e){
            System.out.println("error reviewAction:loadSubmit "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("submitapps");
    }
    
        
    
    public ActionForward listMembersForSubmit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
           
         try{
           UserBean ub = (UserBean) sess.getAttribute("lduser");
    
           String year =  request.getParameter("fycode");
           int fy = Integer.parseInt(year);            
                        
           //get all apps submitted to given sysId for given FY 
           Vector allapps = cdb.getMembersForSubmitList(fy, ub.getInstid());
           
           //set all assign records to collection bean
           RevAssignCollectionBean rac = new RevAssignCollectionBean();
           rac.setAllConstructionAssigns(allapps);
           
             //get total pls allocation, amtrecommend so far
              AllocationYearBean ab = cadb.calcAllocAndAwardForPlsFy(ub.getInstid(), fy);
             request.setAttribute("allocationBean", ab);
             
             //get totals for amtreq and amtrecommend
             long totreq=0, totrecom=0;
             for(int j=0; j<allapps.size(); j++){
                 SystemAssignBean sb=(SystemAssignBean)allapps.get(j);
                 totreq+= sb.getAmtrequested();
                 totrecom+= sb.getRecommendAmt();
             }
             rac.setTotalAmountRequest(totreq);
             rac.setTotalAmountRecommend(totrecom);
             rac.setInitialAlloc(ab.getInitialAlloc());
             request.setAttribute("AssignCollectionBean", rac);
                              
             AppStatusBean asb = new AppStatusBean();
             asb.setFycode(fy);
             asb.setFccode(86);
             asb = dbh.verifyDateAcceptable(asb);
             request.setAttribute("statusBean", asb);
                          
         }catch(Exception e){System.out.println("error reviewAction:listMembersForSubmit "+
                                e.getMessage().toString());
                log.error(e.getMessage().toString());}
          
         return mapping.findForward("submitapps");
    }
    
    
    
    public ActionForward otherfunds(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {        
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess))
        return mapping.findForward("idmissing");               
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          ArrayList allfunds = cdb.getAllFundsForProject(grantid);
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllFundSources(allfunds);
          request.setAttribute("BudgetCollectionBean", bc);      
          
          //get all info about system assignment
          SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
          request.setAttribute("assignBean", sab);
      }
      catch(Exception e) {
          System.out.println("error reviewAction:otherfunds "+ e.getMessage().toString());
          log.error(e.getMessage().toString());
      }        
      return mapping.findForward("otherfunds");      
    }
    
    
    public ActionForward loadDueDates(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
                                   
           try {      
             UserBean userb = (UserBean) sess.getAttribute("lduser");
             //get system_fiscalyear_detail records for this PLS
             ArrayList allrecords = cadb.getAllAllocationsForSystem(userb.getInstid());
             
             //get the amt recommend so far for each record
             for(int i=0; i<allrecords.size(); i++){
                 AllocationYearBean a = (AllocationYearBean)allrecords.get(i);
                 a.setTallyAmountRecommend( cadb.calcAmtRecommendForPlsFy(a.getSystemInstId(), a.getFycode()));
             }
             request.setAttribute("allAllocations", allrecords);
                          
           } catch (Exception e) {
              System.out.println("error reviewAction:loadDueDates "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("duedates");
     }
     
    public ActionForward duedaterecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               String allocid = request.getParameter("id");
               long systemYearDetailId = Long.parseLong(allocid);
               //get duedate/allocation record
               AllocationYearBean ab = cadb.getAllocationRecord(systemYearDetailId);
               request.setAttribute("allocBean", ab);
                                                                   
           } catch (Exception e) {
              System.out.println("error reviewAction:duedaterecord "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("updateduedate");
     }
     
     
    public ActionForward saveDate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               UserBean userb = (UserBean) sess.getAttribute("lduser");
               //get the form info user entered
               ActionForm myform = (ActionForm) request.getAttribute("allocBean");
               AllocationYearBean ab = (AllocationYearBean) myform;
               
               int outcome=cadb.updateSystemDueDate(ab, userb);
                    
                //get all due date records for this pls
               ArrayList allrecords = cadb.getAllAllocationsForSystem(userb.getInstid());
               
               //get the amt recommend so far for each record
               for(int i=0; i<allrecords.size(); i++){
                   AllocationYearBean a = (AllocationYearBean)allrecords.get(i);
                   a.setTallyAmountRecommend( cadb.calcAmtRecommendForPlsFy(a.getSystemInstId(), a.getFycode()));
               }
               request.setAttribute("allAllocations", allrecords);
                                                    
           } catch (Exception e) {
              System.out.println("error reviewAction:saveDate "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("duedates");
     }
     
     
    /**
     * load the cn reviewer page to 'search for member applications'
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadSearch(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                  
         try{
             UserBean ub = (UserBean) sess.getAttribute("lduser");
             
             //prepopulate fy search 
             ArrayList allyears = dbh.dropDownFiscalYearsDesc();
             sess.setAttribute("dropDownList", allyears);      
             
             //prepopulate member name search
             ArrayList allmembers = dbh.dropDownMembersForSystem(ub.getInstid());
             sess.setAttribute("dropDownMembers", allmembers);
             
         }catch(Exception e){
            System.out.println("error reviewAction:loadSearch "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("searchapps");
    }
    
    
    //reduced match justification form 4/23/12
    public ActionForward matchform(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                  
         try{
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
                             
             GrantBean gb=dbh.getRecordBean(grantid); 
             gb = cdb.getAmtRecommendCostForGrant(gb, grantid);
             request.setAttribute("thisGrant", gb);  
             
             //get the senate, congress districts, etc.
             DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
             request.setAttribute("distBean", db);
             
             //get the narrative from the db and set to descrBean
             ClobBean cb = new ClobBean();
             cb.setGrantid(grantid);
             cb.openOracleConnection();      
             cb.getClobNarrative(95); 
             cb.closeOracleConnection();
               
             DescriptionBean pdb = new DescriptionBean();
             pdb.setNarrative(cb.getData());
             request.setAttribute("projNarrative", pdb);  
             
             //get the justificationExists and justification narrative
             SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
             if(gb.getFycode()>14)//starting fy14-15; new reduce match criteria/fields
                 sab = cdb.getReduceMatchTypes(sab);
             
             request.setAttribute("assignBean", sab);
                         
         }catch(Exception e){
            System.out.println("error reviewAction:matchform "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("matchform");
    }
    
    
    public ActionForward confirmbdgtdelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess))
        return mapping.findForward("idmissing");               
     
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
        log.error("reviewAction:confirmbdgtdelete "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmbdgtdelete");      
    }
    
    public ActionForward finalexpense(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess))
        return mapping.findForward("idmissing");               
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          request.setAttribute("thisGrant", gb);  
          
          //get all expense records for all fs codes
          ArrayList allexp = cdb.getFinalExpensesForCode(grantid, 0);
          request.setAttribute("allExpenses", allexp);       
          
          //get all expense totals by category & grand total
          TotalsBean tb = cdb.getFinalExpenseTotalsAllCodes(grantid);
          request.setAttribute("expenseGrandTotals", tb);          
      }
      catch(Exception e) {
        log.error("reviewAction:finalexpense "+e.getMessage().toString());
      }        
      return mapping.findForward("finalexpense");      
    }
    
    
  //new 6/5/15 per LWebb; add mwbe requirements; allow reviewer to edit
  public ActionForward saveMwbe(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
         HttpSession sess = request.getSession();
         if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
        System.out.println("INSIDE SAVE MWBE");
         try {      
             UserBean userb = (UserBean) sess.getAttribute("lduser");
             //get the form info user entered
             ActionForm myform = (ActionForm) request.getAttribute("appStatus");
             AppStatusBean ab = (AppStatusBean) myform;
             
             DBHandler dbh = new DBHandler();
             dbh.saveMwbeParticipation(ab, ab.getGrantid(), userb);
             
             //---------------------------
             //need appstatus bean to determin whether app is lock/unlock
             AppStatusBean as = dbh.getApplicationStatus(ab.getGrantid());
             request.setAttribute("appStatus", as);
             
             GrantBean gb = dbh.getRecordBean(ab.getGrantid());//need grant's inst_id
             request.setAttribute("thisGrant", gb);
             
             //check if an assignment record has been created, if not- create one now
             if (!cdb.doesSystemAssignExist(ab.getGrantid())) {
                 //get the librarySystemMap; create a new system assignment record                
                 long libsysmap = cdb.getLibrarySystemMapId(gb.getInstID());
                  
                 int result = cdb.insertAssignRecord(userb, ab.getGrantid(), libsysmap);
             }
             
             //get all info about system assignment
             SystemAssignBean sab = cdb.getSystemAssignRecord(ab.getGrantid());
             request.setAttribute("assignBean", sab);
                                                                 
         } catch (Exception e) {
            System.out.println("error reviewAction:saveMwbe "+e.getMessage());
            log.error(e.getMessage().toString());
         }   
         return mapping.findForward("checklist");
   }
    
    
  
  
//for construction only 8/18/16 per mwaltz
  public ActionForward smartgrowth(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
   {   
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");         
     
     if(!checkGrantId(sess))
       return mapping.findForward("idmissing");      
     
     try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         request.setAttribute("thisGrant", gb); 
                     
     }catch(Exception e){log.error(e.getMessage().toString());}
      
     return mapping.findForward("smartgrowth");
   }
  
  
  
//for construction only 8/18/16 per mwaltz
  public ActionForward shpo(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
   {   
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");         
     
     if(!checkGrantId(sess))
       return mapping.findForward("idmissing");      
     
     try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         request.setAttribute("thisGrant", gb); 
                     
     }catch(Exception e){log.error(e.getMessage().toString());}
      
     return mapping.findForward("shpo");
   }
  
  
  
  
  
//for construction only 8/18/16 per mwaltz
  public ActionForward seaf(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
   {   
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");         
     
     if(!checkGrantId(sess))
       return mapping.findForward("idmissing");      
     
     try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         request.setAttribute("thisGrant", gb); 
                     
     }catch(Exception e){log.error(e.getMessage().toString());}
      
     return mapping.findForward("seaf");
   }
  
  
  
  
//for construction only 8/18/16 per mwaltz
  public ActionForward assurance(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
   {   
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");         
     
     if(!checkGrantId(sess))
       return mapping.findForward("idmissing");      
     
     try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         request.setAttribute("thisGrant", gb); 
                     
     }catch(Exception e){log.error(e.getMessage().toString());}
      
     return mapping.findForward("assurance");
   }
  
  
    
//-------------------------------------------------------------------------------
    public boolean checkSessionTimeout(HttpSession sess)
    {
        boolean timeout = false;
        //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew()){      
          timeout = true;
        }      
        return timeout;
    }
      
      
    //verify that grantid was choosen, and in session
    public boolean checkGrantId(HttpSession sess)
    { 
        boolean hasGrantId = true;
        try{
          if(sess.getAttribute("grantid")==null || sess.getAttribute("grantid").equals(""))
          {
             hasGrantId = false;
             
             //prepopulate the 'list member apps' page
             ArrayList allyears = dbh.dropDownFiscalYearsDesc();
             sess.setAttribute("dropDownList", allyears);                    
          }   
        
        }catch(Exception e){ log.error(e.getMessage().toString());   }        
        return hasGrantId;
    }
       
}///action class
