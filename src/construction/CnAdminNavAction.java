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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CnAdminNavAction extends DispatchAction {
    
        private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
        private DBHandler dbh = new DBHandler();
        private ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
        private ConstructionDBbean cdb = new ConstructionDBbean();
        private Log log = (Log)LogFactory.getLog(this.getClass());     
     
     
     
    public ActionForward home(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               //load FY drop down box; admin search for submitted apps by FY
               ArrayList allyears = dbh.dropDownFiscalYearsDesc();
               sess.setAttribute("dropDownList", allyears);   
                      
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("home");
     }
     
     
    public ActionForward getSubmitted(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               //get Fy from user; find all submitted cn apps for Fy
               String fy = request.getParameter("fycode");
               
               Vector allapps = cdb.getAppsSubmittedToLdForFy(Integer.parseInt(fy));
               request.setAttribute("allGrants", allapps);
               
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
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
          
          AppStatusBean asb= dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          SubmissionDBbean sb = new SubmissionDBbean();
          Vector allSubmissions = sb.getSubmissions(grantid); 
          request.setAttribute("allSubmissions", allSubmissions);
          
          //determine if system_assign exists
           if (cdb.doesSystemAssignExist(grantid)) {
               
               //get all info about system assignment
               SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
               request.setAttribute("assignBean", sab);              
           }
            
          //added 2/3/16 to check for and add new sedref director fk to grant_admins table
          UserBean userb = (UserBean) sess.getAttribute("lduser");  
          OfficerDBbean odb = new OfficerDBbean();
          odb.verifyCurrentDirector(grantid, gb.getInstID(), userb);
            
          
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("checklist");      
      }
     
  //for dasny - read only view of narratives
   public ActionForward narrative(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
   {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          request.setAttribute("thisGrant", gb);  
          
          NarrInstructionBean nib = new NarrInstructionBean();  
          int[] allTypes= nib.getConNarrTypes();
                             
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
              request.setAttribute(newBean, db);   
            }            
            cb.closeOracleConnection();
            
        }catch(Exception e){ 
            System.out.println("error CnAdminNavAction "+e.getMessage().toString());
            log.error(e.getMessage().toString()); 
        }      
        return mapping.findForward("narrative");      
   } 
   
   //admin - get 1st narrative type for editing
    public ActionForward narrativeadmin(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                          
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
            request.setAttribute("projNarrative", pdb);             
            
        }catch(Exception e){ 
            System.out.println("error CnAdminNavAction:narrativeadmin "+e.getMessage().toString());
            log.error(e.getMessage().toString()); 
        }      
        return mapping.findForward("narrative");      
      } 
      
 
    public ActionForward budget(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb); 
                     
            BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, 4);
            request.setAttribute("BudgetCollectionBean", bc);
                          
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
            tb.setFycode(gb.getFycode());
            //project cost, max cost
            tb = cadb.calcMaxAmtSysRecommend(grantid, tb, gb.getFycode());
            //system amt_recommended
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            tb.setSysAmtRecommended(sab.getRecommendAmt());
            request.setAttribute("totalsBean", tb);
                    
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("budget");      
      }
       
    
    //this action called from cn admin checklist page   
    public ActionForward budgetadmin(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb); 
                     
            BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, 4);
            request.setAttribute("BudgetCollectionBean", bb);
                                                              
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
            //project cost, max cost
            tb = cadb.calcMaxAmtSysRecommend(grantid, tb, gb.getFycode());
            //system amt_recommended
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            tb.setSysAmtRecommended(sab.getRecommendAmt());
            request.setAttribute("totalsBean", tb);
                    
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("budget");      
      }
       
    public ActionForward coversheet(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
            
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb); 
            
            //get the senate, congress districts, etc.          
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);
            
            OfficerDBbean odb = new OfficerDBbean();
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            request.setAttribute("libDirectorBean", libdirectorBean);   
            
            //get the shpo exemption narrative
            ClobBean clobb = new ClobBean();
            clobb.setGrantid(grantid);
            clobb.openOracleConnection();      
            clobb.getClobNarrative(117); 
            clobb.closeOracleConnection();        
            
            ConstructionDBbean cdb = new ConstructionDBbean();
            CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
            ab.setGrantId(grantid);
            //set shpo exempt narr fields
            ab.setShpoExemption(clobb.getData());
            ab.setShpoExemptionId(clobb.getNarrID());
            ab.setShpoExemptionNarrTypeId(117);
            ab.setGroundDisturb(asb.isGroundDisturb());
            
            //set all other fields
            ab.setProjectTitle(gb.getTitle());
            ab.setInstId(gb.getInstID());
            ab.setGrantId(gb.getGrantid());
            ab.setFycode(gb.getFycode());
            ab = cdb.determineCostAmts(ab, grantid);
            ab= odb.getCnProjectManager(ab);
            ab= odb.getCnDirector(ab);  
            ab= cdb.getBuildingProjectTypes(ab);//building_projects table
            request.setAttribute("applicationFormBean", ab);  
                       
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("coversheet");      
      }      
      
      
    public ActionForward evalform(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
            
            //get all info about system assignment
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            request.setAttribute("assignBean", sab);
            
            //get amtreq, 50%, total pls allocation, amtrecommend so far, etc
            AllocationYearBean ab = cadb.calcRequestApprAmounts(grantid, gb.getFycode());//amtreq & 50% amt
            AllocationYearBean ab2 = cadb.calcAllocAndAwardForPlsFy(sab.getSystemInstId(), gb.getFycode());
            
            //get all eval form comments and answers
            CnRatingFormBean rb = cdb.getCnRatingComments(sab.getAssignmentId());
            rb.setGrantid(grantid);
            rb.setAssignmentid(sab.getAssignmentId());
            rb.setFycode(gb.getFycode());
            rb.setAmtrecommended(sab.getRecommendAmt());
            rb.setAmtrecommendedStr(sab.getRecommendAmtStr());
            rb.setProjectrecommend(sab.isRecommendation());
            rb.setAmountRequested(ab.getAmountRequested());//from alloc bean
            rb.setRequestCost(ab.getRequestCost());//from alloc bean
            rb.setMaxRecommendAmt(ab.getMaxRequestCost());//from alloc bean
            rb.setInitialAlloc(ab2.getInitialAlloc());//from alloc bean
            rb.setTallyAmountRecommend(ab2.getTallyAmountRecommend());//from alloc bean
            rb = cdb.getCnRatingAnswers(rb, sab.getAssignmentId());
            request.setAttribute("reviewFormBean", rb);
                                 
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("evalform");      
    }      
    
    /**
     * for dasny - they get the eval & match forms in a single jsp view.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward evalmatchform(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{ 
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb=dbh.getRecordBean(grantid); 
            request.setAttribute("thisGrant", gb);  
            
            //get all info about system assignment
            SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
            request.setAttribute("assignBean", sab);
            
            //get amtreq, 50%, total pls allocation, amtrecommend so far, etc
            AllocationYearBean ab = cadb.calcRequestApprAmounts(grantid, gb.getFycode());//amtreq & 50% amt
            AllocationYearBean ab2 = cadb.calcAllocAndAwardForPlsFy(sab.getSystemInstId(), gb.getFycode());
            
            //get all eval form comments and answers
            CnRatingFormBean rb = cdb.getCnRatingComments(sab.getAssignmentId());
            rb.setGrantid(grantid);
            rb.setAssignmentid(sab.getAssignmentId());
            rb.setFycode(gb.getFycode());
            rb.setAmtrecommended(sab.getRecommendAmt());
            rb.setAmtrecommendedStr(sab.getRecommendAmtStr());
            rb.setProjectrecommend(sab.isRecommendation());
            rb.setAmountRequested(ab.getAmountRequested());//from alloc bean
            rb.setRequestCost(ab.getRequestCost());//from alloc bean
            rb.setMaxRecommendAmt(ab.getMaxRequestCost());//from alloc bean
            rb.setInitialAlloc(ab2.getInitialAlloc());//from alloc bean
            rb.setTallyAmountRecommend(ab2.getTallyAmountRecommend());//from alloc bean
            rb = cdb.getCnRatingAnswers(rb, sab.getAssignmentId());
            request.setAttribute("reviewFormBean", rb);
            
            //get the senate, congress districts, etc.
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);
            
            //get total cost, amt recommend, %
            gb = cdb.getAmtRecommendCostForGrant(gb, grantid);
            request.setAttribute("thisGrant", gb);  
            
            //get the narrative from the db and set to descrBean
            ClobBean cb = new ClobBean();
            cb.setGrantid(grantid);
            cb.openOracleConnection();      
            cb.getClobNarrative(95); 
            cb.closeOracleConnection();
              
            DescriptionBean pdb = new DescriptionBean();
            pdb.setNarrative(cb.getData());
            request.setAttribute("projNarrative", pdb);
            
        }catch(Exception e) { log.error(e.getMessage().toString()); }
        return mapping.findForward("evalform");      
    }      
    
    
    public ActionForward reports(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
            ArrayList allyears = dbh.dropDownFiscalYears();
            sess.setAttribute("dropDownList", allyears);
            
            //prepopulate pls search
            ArrayList allpls = dbh.dropDownLibrarySystems();
            sess.setAttribute("dropDownSystems", allpls);    
                         
        }catch(Exception e){ log.error(e.getMessage().toString());}        
        return mapping.findForward("reports");          
    }
     
    public ActionForward allocation(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               ArrayList allyears = dbh.dropDownFiscalYears();
               sess.setAttribute("dropDownList", allyears);   
                      
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("allocation");
     }
     
     
     
    public ActionForward listAllocations(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               String fy = request.getParameter("fycode");
               int fycode = Integer.parseInt(fy);
               String fc = request.getParameter("fccode");
               int fccode = Integer.parseInt(fc);
               
               ArrayList allrecs = cadb.getAllAllocationsForYear(fycode, fccode);
               request.setAttribute("allAllocations", allrecs);
                                     
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("allocation");
     }
     
     
     
    public ActionForward newAlloc(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               String fc = request.getParameter("fc");
               int fccode = Integer.parseInt(fc);
               
               //get basic info for new blank allocation record
               AllocationYearBean ab = new AllocationYearBean();
               //get and set all fy's for dropdown
               ab.setAllPossibleYears(dbh.dropDownFiscalYears());
               //get and set all systems for dropdown
               ab.setAllPossibleSystems(dbh.dropDownLibrarySystems());
               //set fund
               ab.setFccode(fccode);
               request.setAttribute("allocBean", ab);
                                                    
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("updatealloc");
     }
     
     
    public ActionForward allocrecord(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {      
               String allocid = request.getParameter("id");
               long systemYearDetailId = Long.parseLong(allocid);
               
               AllocationYearBean ab = cadb.getAllocationRecord(systemYearDetailId);
                
               //populate fy and system dropdowns
               ab.setAllPossibleYears(dbh.dropDownFiscalYears());
               ab.setAllPossibleSystems(dbh.dropDownLibrarySystems());
               request.setAttribute("allocBean", ab);
                                                                   
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("updatealloc");
     }
     
     
    public ActionForward saveAllocation(ActionMapping mapping, ActionForm form,
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
               
               int outcome=0;
               if(ab.getSystemYearDetailId()==0){
                    //check if this system/FY/FC already exists
                    long id=cadb.getIdExistingAllocation(ab.getFycode(), ab.getLibrarySystemMapId(), ab.getFccode());
                    if(id==0)
                        outcome=cadb.insertSystemAllocationRecord(ab, userb);
                    else
                        request.setAttribute("recordExists", "true");
               }
               else
                    outcome=cadb.updateSystemAllocationRecord(ab, userb);
                    
                //get all alloc records for FY user was just working on
               ArrayList allrecs = cadb.getAllAllocationsForYear(ab.getFycode(), ab.getFccode());
               request.setAttribute("allAllocations", allrecs);
                                                    
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("allocation");
     }
     
     
    public ActionForward loadSearch(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                  
         try{ 
             //prepopulate fy search 
             ArrayList allyears = dbh.dropDownFiscalYearsDesc();
             sess.setAttribute("dropDownList", allyears);      
             
             //prepopulate pls search
             ArrayList allpls = dbh.dropDownLibrarySystems();
             sess.setAttribute("dropDownSystems", allpls);            
             
         }catch(Exception e){
            System.out.println("error CnAdminNavAction:loadSearch "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("searchapps");
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
      
    /**
     * otherfunds action is used to display fund records to dasny
     */
    public ActionForward otherfunds(ActionMapping mapping, ActionForm form,
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
          
          ArrayList allfunds = cdb.getFundsForPrintview(grantid);
          request.setAttribute("allFunds", allfunds);      
      
      }catch(Exception e) {
        log.error("error CnAdminNavAction:otherfunds "+e.getMessage().toString());
      }        
      return mapping.findForward("otherfunds");      
    }
    
    /**
     * additionalfunds used to display records to cn admin.
     */
    public ActionForward additionalfunds(ActionMapping mapping, ActionForm form,
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
          
          ArrayList allfunds = cdb.getAllFundsForProject(grantid);
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllFundSources(allfunds);
          request.setAttribute("BudgetCollectionBean", bc);              
      }
      catch(Exception e) {
          System.out.println("error CnAdminNavAction:additionalfunds "+ e.getMessage().toString());
          log.error(e.getMessage().toString());
      }        
      return mapping.findForward("otherfunds");      
    }
    
    
    public ActionForward unlock(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                          
         try{
          UserBean ub = (UserBean) sess.getAttribute("lduser");
           
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
             AppStatusBean as = dbh.getApplicationStatus(grantid);
             request.setAttribute("appStatus", as);
                       
          String lockoutvar = request.getParameter("lockout");
          boolean lockout = Boolean.parseBoolean(lockoutvar);
    
          //this code will un/lock ALL portions (cover, narrs,budget) of app for member lib
          AppStatusBean asb = new AppStatusBean();
          asb.setCoversheetyn(lockout);
          asb.setProjdescyn(lockout);
          asb.setAmtreqyn(lockout);
          asb.setLockapp(lockout);
          //IF finalexp was submitted - also handle lock/unlock of final exp page
          if(as.isFinalsubmitted()){
            asb.setFinalnarrativeyn(lockout);
          }
          dbh.unLockConstructAppForMember(grantid, ub, asb);//for construction only
          cdb.lockAppForSystemEdit(grantid, ub.getUserid(), lockout);
          
           //return to reviewer checklist page          
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb);
           
             SubmissionDBbean sb = new SubmissionDBbean();
             Vector allSubmissions = sb.getSubmissions(grantid); 
             request.setAttribute("allSubmissions", allSubmissions);
             
             //determine if system_assign exists
              if (cdb.doesSystemAssignExist(grantid)) {
                  
                  //get all info about system assignment
                  SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                  request.setAttribute("assignBean", sab);              
              }
    
         }catch(Exception e){
            System.out.println("error CnAdminNavAction:unlock "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("checklist");
    }
    
    
    /**
     * action will unsubmit a grant back to pls - allows admin to remove a grant
     * from the 'admin home page' list
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward unsubmitpls(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
         HttpSession sess = request.getSession(); 
         if(checkSessionTimeout(sess))
           return mapping.findForward("timeout");
                          
         try{
          UserBean ub = (UserBean) sess.getAttribute("lduser");           
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
                  
          //unsubmit back to pls, and unlock for pls edit
          cdb.unsubmitToPls(grantid, ub.getUserid());
          
           //return to reviewer checklist page
           AppStatusBean as = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", as);
           
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb);
           
             SubmissionDBbean sb = new SubmissionDBbean();
             Vector allSubmissions = sb.getSubmissions(grantid); 
             request.setAttribute("allSubmissions", allSubmissions);
             
             //determine if system_assign exists
              if (cdb.doesSystemAssignExist(grantid)) {                  
                  //get all info about system assignment
                  SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                  request.setAttribute("assignBean", sab);              
              }
    
         }catch(Exception e){
            System.out.println("error CnAdminNavAction:unsubmitpls "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("checklist");
    }
    
    
    public ActionForward searchDasnyGrants(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               String fy = request.getParameter("fycode");
               
               Vector allapps = cdb.getAppsSubmittedToDasnyFy(Integer.parseInt(fy));
               request.setAttribute("allProjects", allapps);
               
           } catch (Exception e) {
              System.out.println("error CnAdminNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("home");
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
            
         CoversheetBean csb = new CoversheetBean();      
         csb.setModule("cn");//construction only
         csb.setGrantid(grantid);       
         OfficerDBbean odb = new OfficerDBbean();
         csb= odb.getProjectManager(csb);
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("projManager");      
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
          BudgetDBHandler bdh = new BudgetDBHandler();
          bb.setDescription(bdh.getBudgetRecordDesc(bb.getTab(), bb.getId()) );
          
          request.setAttribute("deleteBean", bb);
      }
      catch(Exception e) {
        log.error("CnAdminNavAction:confirmbdgtdelete "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmbdgtdelete");      
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
            System.out.println("error CnAdminNavAction:matchform "+e.getMessage().toString());
            log.error(e.getMessage().toString());
         }         
         return mapping.findForward("matchform");
    }
    
    
    public ActionForward finalexpense(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");    
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
           
          //get all expense records for this fs code
          ArrayList allexp = cdb.getFinalExpensesForCode(grantid, 40);
          
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllFinalExpenses(allexp);
          request.setAttribute("BudgetCollectionBean", bc);    
          
          //get expense totals for bottom of page
          TotalsBean tb = cdb.getFinalExpenseTotalsForCode(grantid, 40);
          request.setAttribute("expenseTotals", tb);
      }
      catch(Exception e) {
        log.error("CnAdminNavAction "+e.getMessage().toString());
      }        
      return mapping.findForward("finalexpense");      
    }
    
    
    
    public ActionForward verifyinitial(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession s = request.getSession();  
        if(checkSessionTimeout(s))
          return mapping.findForward("timeout");
          
        try{         
            String grantnum = (String) s.getAttribute("grantid");
            long grantid= Long.parseLong(grantnum);
           
                                                
            //check for missing narratives
            SubmissionDBbean sdb = new SubmissionDBbean();
            Vector missingNar = sdb.checkMissingNarratives(grantid, 86);
            if(missingNar.size()>0)
                request.setAttribute("missingNarr", missingNar);  
                
            //check for missing budget-verify total amtReq >0  
            BudgetDBHandler bdh = new BudgetDBHandler();             
            int totreq = bdh.calcTotalAmtRequested(grantid, 0);                
            if(totreq<1)
              request.setAttribute("missingBudg", "true");
                
            
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
                                                
            //check for missing attachments            
            Vector alldocs = dbh.getAllDocuments(grantid);
            if(alldocs.size()<1)
                request.setAttribute("missingAttach", "true");
            
            // verify required attachments
            Vector missingAttach = sdb.checkMissingCnAttach(alldocs, cab);
            if(missingAttach.size()>0)
                request.setAttribute("requiredAttach", missingAttach);
                
                             
        }catch(Exception e){
        System.out.println("error CnAdminNavAction "+e.getMessage().toString());
        }
      return mapping.findForward("verifyinitial");        
    }      
    
    
    
    public ActionForward completion(ActionMapping mapping, ActionForm form,
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
              
         //get pm info
         OfficerDBbean odb = new OfficerDBbean();
         OfficerBean pmBean = odb.getProjectManager(grantid);    
         request.setAttribute("managerBean", pmBean);       
          
         //get grant amt and proj cost data
         long buildingGrantId= cdb.doesGrantBuildingExist(grantid);
         
         FinalExpenseBean fb = cdb.getCompletionFormAmounts(grantid, buildingGrantId);
         request.setAttribute("finalExpenseBean", fb);
            
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("completion");      
    }
    
  
  //new 6/5/15 per LWebb; add mwbe requirements; allow admin to edit
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
             AppStatusBean asb= dbh.getApplicationStatus(ab.getGrantid());
             request.setAttribute("appStatus", asb);
             
             SubmissionDBbean sb = new SubmissionDBbean();
             Vector allSubmissions = sb.getSubmissions(ab.getGrantid()); 
             request.setAttribute("allSubmissions", allSubmissions);
             
             //determine if system_assign exists
              if (cdb.doesSystemAssignExist(ab.getGrantid())) {
                  
                  //get all info about system assignment
                  SystemAssignBean sab = cdb.getSystemAssignRecord(ab.getGrantid());
                  request.setAttribute("assignBean", sab);              
              }
                                                               
         } catch (Exception e) {
            System.out.println("error CnAdminNavAction "+e.getMessage());
            log.error(e.getMessage().toString());
         }   
         return mapping.findForward("checklist");
   }
    
    
     
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
          
}///class