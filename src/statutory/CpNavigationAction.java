/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CpNavigationAction.java
 * Creation/Modification History  :
 *
 * SH   1/15/09      Created
 *
 * Description
 * This dispatch action will get coversheet, checklist, narratives, budget, etc, 
 * for sa/co/di/lg/cn applicant. 
 *****************************************************************************/
package statutory;
import clobpackage.ClobBean;

import construction.CnApplicationBean;

import construction.ConstructionDBbean;

import construction.FinalExpenseBean;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppDatesBean;
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
import mypackage.DbName;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.DropDownListBean;
import mypackage.EligibilityDbBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.PanelDbBean;
import mypackage.PanelReviewBean;
import mypackage.PartCollectionBean;
import mypackage.PartInstDBbean;
import mypackage.RatingBean;
import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.SedrefInstBean;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.SubmissionDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import mypackage.VendorDBbean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CpNavigationAction extends DispatchAction
{
  private DBHandler dbh = new DBHandler();
  private BudgetDBHandler bdh = new BudgetDBHandler();
  private ConstructionDBbean cdb = new ConstructionDBbean();
  private Log log = LogFactory.getLog(this.getClass());
  
  
  public ActionForward attachment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        if(!checkGrantId(sess, request))         
          return mapping.findForward("idmissing");
       
       try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
           
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);

          Vector results = dbh.getAllDocuments(grantid);
          request.setAttribute("allDocs", results);
       }catch(Exception e){log.error(e.getMessage().toString());}
       
       return mapping.findForward("attachment");
    }
    
    
    
    public ActionForward finalrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");      
         
      DescriptionBean pdb = new DescriptionBean();     
      NarrativeDBbean ndb = new NarrativeDBbean();
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          //get the final narrative
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();
          cb.getClobNarrative(2); //2 is narrative type id for final narrative
          cb.closeOracleConnection();
          
          //set to bean the narr, narrID, 
          pdb.setNarrative(cb.getData());
          pdb.setGrantid(grantid);
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(2);     
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(2));
          
          if(asb.getFccode()==80)//lgrmif instructions
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLg(2, asb.getFycode()));
          else if(asb.getFccode()==7)//co
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCo(2));
          else if(asb.getFccode()==5)//di
            pdb.setNarrativeDescr(ndb.getNarrativeInstructions(2));
            
          //co/di/lg uses same jsp for init/final narr - determine whether init/final needs locking
          pdb.setLockNarrative(ndb.determineNarrativeLock(asb, 2));
          request.setAttribute("projNarrative", pdb);
                    
      }
      catch(Exception e) {
        log.error(e.getMessage().toString());
      }        
      return mapping.findForward("finalrpt");      
    }
  
  
  
  public ActionForward checklist(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");            
            
      //links on home page have grantid parameter that needs to be set to session
      if(request.getParameter("grantid")!=null)
      {
        String chosenGrant = request.getParameter("grantid");
        sess.setAttribute("grantid", chosenGrant);
      }            
     
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");         
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          UserBean userb = (UserBean) sess.getAttribute("lduser");  
     
          GrantBean gb = dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb); 
           
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          if(gb.getFccode()==86){
            asb.setSystemName(gb.getSystemName());
            asb.setSystemInstId(gb.getSystemInstId());
            asb = cdb.determineSysDueDateForMember(asb, gb.getFycode());
          }
          request.setAttribute("appStatus", asb);
          
          //added 10/26/09 to check for and add new sedref director fk to grant_admins table
          OfficerDBbean odb = new OfficerDBbean();
          odb.verifyCurrentDirector(grantid, gb.getInstID(), userb);
                    
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("checklist");
    }
    
    
    public ActionForward appstatus(ActionMapping mapping, ActionForm form,
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
          
          //get all submission and approval info
          CommentDBbean cdb = new CommentDBbean();
          ApprovalsDBbean adb = new ApprovalsDBbean();
          SubmissionDBbean sb = new SubmissionDBbean();
          
          Vector allSubmissions = sb.getSubmissions(grantid);
          Vector allApprovals = adb.getApprovals(grantid);
          allApprovals = adb.getAmountForApprovals(allApprovals);
          Vector allComments = cdb.getAdminComments(grantid);
          
          request.setAttribute("allSubmissions", allSubmissions);
          request.setAttribute("allApprovals", allApprovals);
          request.setAttribute("allComments", allComments);
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("appstatus");
    }
    
    
    public ActionForward auth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");       
        
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");     
          
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb); 
           
          AuthorizationsDBbean ab = new AuthorizationsDBbean();
          Vector grantAuth = ab.getGrantAuthorizations(grantid);
          request.setAttribute("grantAuth", grantAuth);
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("auth");
    }
    
    
  public ActionForward finalsignoff(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");            
          
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
               
          //get library director, preservation officer info
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
          sess.setAttribute("presOfficerBean", presOfficerBean);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          sess.setAttribute("libDirectorBean", libdirectorBean);  
          
          //get any previous authorizations for this grant
          AuthorizationsDBbean ab = new AuthorizationsDBbean();
          Vector grantAuth = ab.getGrantAuthorizations(grantid);
          request.setAttribute("grantAuth", grantAuth);
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("finalsignoff");
  }
  
  
   public ActionForward finalauth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");     
        
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");     
          
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb); 
               
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("finalauth");
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
  
    
  public ActionForward fs10a(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");            
          
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
     
          FS10DBbean fs = new FS10DBbean();
          ArrayList allFSRecords = fs.getFSARecords(grantid);   
          
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllAmendRecords(allFSRecords);
          request.setAttribute("BudgetCollectionBean", bc);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("fs10a");
  }
    
        
  public ActionForward budget(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      String finalTarget="budget";
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");
      
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          UserBean userb = (UserBean) sess.getAttribute("lduser");    
                    
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
          
          if(gb.getFccode()==6 && userb.getPrgsa().equals("read"))//statutory read only
          {
            BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, false, asb, false, 0);
            request.setAttribute("budgetBean", bb);
            
            //get any FS10A amendment records
            FS10DBbean fs = new FS10DBbean();
            ArrayList allFSRecords = fs.getFSARecords(grantid);
            request.setAttribute("allFSRecords", allFSRecords);
            finalTarget="readbudget";
          }
          else if(gb.getFccode()==80)//lgrmif
          {
            //get project budget records for 1st tab only
            BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, 1, asb);
            request.setAttribute("BudgetCollectionBean", bc);    
            
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLgBudgetNarrative(request,"1", grantid);
          }
          else if(gb.getFccode()==86)//construction
          {
              //construction uses only tabs 4,5,6 - so start with #4-and split categories
              BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, 4, true);
              request.setAttribute("BudgetCollectionBean", bc);
          }
          else{
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, 1, false);
            request.setAttribute("BudgetCollectionBean", bc);
          }
          
          if(gb.getFccode()==7)
          {
            if(userb.getPrgco().equals("read"))
              finalTarget="readbudget";
            else if(asb.isPendingReview() || asb.isInitialappr())
              finalTarget="expbudget";
          }
          
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);          
                       
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward(finalTarget);      
    }
    
    
    public ActionForward narrative(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");
            
      DescriptionBean pdb = new DescriptionBean();
      NarrativeDBbean ndb = new NarrativeDBbean();     
      int narrTypeID=0;
      
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        GrantBean gb=dbh.getRecordBean(grantid); 
        request.setAttribute("thisGrant", gb);  
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
        
        if(gb.getFccode()==5)
        { //discretionary
          narrTypeID=19;
          pdb.setNarrativeDescr(ndb.getNarrativeInstructions(narrTypeID));
        }
        else if(gb.getFccode()==6)
        { //statutory 
          narrTypeID=3;
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsSa(narrTypeID));  
        }
        else if(gb.getFccode()==20)
        { //stateaid cjh/nyhs
          narrTypeID=3;
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsStaid(narrTypeID));  
        }
        else if(gb.getFccode()==7)
        { //coordinated
          narrTypeID=1;
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCo(narrTypeID));  
        }
        else if(gb.getFccode()==80)
        {  //lgrmif
          narrTypeID=69;
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLg(narrTypeID, asb.getFycode()));          
        }
        else if(gb.getFccode()==86) {
            //construction, abstract narrative
            narrTypeID=95;
            pdb.setNarrativeDescr(ndb.getNarrativeInstructionsCn(narrTypeID));
        }
          
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
          
          //co/di/lg uses same jsp for init/final narr - determine whether init/final needs locking
          pdb.setLockNarrative(ndb.determineNarrativeLock(asb, narrTypeID));
          request.setAttribute("projNarrative", pdb);    
                    
      }catch(Exception e){ log.error(e.getMessage().toString()); }      
      return mapping.findForward("narrative");      
    }
    
    
    public ActionForward coversheet(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");       
        
        if(!checkGrantId(sess, request))
          return mapping.findForward("idmissing");        
              
        try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);  
          
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
          request.setAttribute("presOfficerBean", presOfficerBean);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);   
          
          if(gb.getFccode()==80)//lgrmif
          {
            //get the senate, congress districts, etc.
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);
            
            //get all info that can be updated using struts form
            ClobBean clobb = new ClobBean();
            clobb.setGrantid(grantid);
            clobb.openOracleConnection();      
            clobb.getClobNarrative(1); 
            clobb.closeOracleConnection();     
            
            CoversheetBean csb = new CoversheetBean();
            csb.setFycode(gb.getFycode());
            csb.setSedrefinstid(gb.getInstID());
            csb.setSummaryDesc(clobb.getData());
            csb.setNarrId(clobb.getNarrID());
            csb.setNarrTypeId(1);
            csb.setGrantid(grantid);                 
            csb= odb.getProjectManager(csb);
            csb= odb.getAdditionalContact(csb, 1);
            
            EligibilityDbBean edb = new EligibilityDbBean();
            csb = edb.getArchivesGovtInfo(csb);
            csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());
            
            
            csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));              
            request.setAttribute("coversheetBean", csb);
            HashMap listmap =dbh.populateDropDownList(request);
            sess.setAttribute("dropDownLists", listmap);               
          }
          else if(gb.getFccode()==86)//construction
          {
              //get the senate, congress districts, etc.
              DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
              request.setAttribute("distBean", db);     
              
              //get shpo flag
              AppStatusBean appbean = dbh.getGrantStatusYn(grantid);
              
              //get the shpo exemption narrative
              ClobBean cb = new ClobBean();
              cb.setGrantid(grantid);
              cb.openOracleConnection();      
              cb.getClobNarrative(117); 
              cb.closeOracleConnection();             
                
              CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
              ab.setGrantId(grantid);
              //set shpo exempt narr fields
              ab.setShpoExemption(cb.getData());
              ab.setShpoExemptionId(cb.getNarrID());
              ab.setShpoExemptionNarrTypeId(117);
              ab.setGroundDisturb(appbean.isGroundDisturb());
              
              //set all other fields
              ab.setProjectTitle(gb.getTitle());
              ab.setInstId(gb.getInstID());
              ab.setFycode(gb.getFycode());
              ab = cdb.determineCostAmts(ab, grantid);
              ab= odb.getCnProjectManager(ab);
              ab= odb.getCnDirector(ab);  
              ab= cdb.getBuildingProjectTypes(ab);//building_projects table
              request.setAttribute("applicationFormBean", ab);   
          }
          else if(gb.getFccode()==5)//discretionary
          {
            //get the senate, congress districts, etc.
             DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);
            
            //get all info that can be updated using struts form
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
            
            EligibilityDbBean edb = new EligibilityDbBean();
            csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());          
            request.setAttribute("coversheetBean", csb);   
          }
          else if(gb.getFccode()==6 || gb.getFccode()==20)//statutory & stateaid cjh/nyhs
          {
            //get summary description narrative 
            ClobBean cb = new ClobBean();
            cb.setGrantid(grantid);
            cb.openOracleConnection();      
            cb.getClobNarrative(1); 
            cb.closeOracleConnection();             
                   
            DescriptionBean db = new DescriptionBean();
            db.setNarrative(cb.getData());
            db.setId(cb.getNarrID());
            db.setNarrTypeID(1);
            request.setAttribute("projNarrative", db);  
          }
          else if(gb.getFccode()==7)//coordinated
          {
            //get all inst and sedref id's for dropdowns
              SedrefInstBean sb = new SedrefInstBean();
              Vector allInst = sb.getAllInstitutions();        
              sess.setAttribute("allInst", allInst);
              
              CoversheetBean csb = new CoversheetBean();
              csb.setGrantid(grantid);   
              csb = dbh.getProjectNameReligAffil(csb);     
              csb= odb.getProjectManager(csb);        
              request.setAttribute("coversheetBean", csb);                
              
              //remove old part inst's from session
              sess.removeAttribute("instBean1");
              sess.removeAttribute("instBean2");
              sess.removeAttribute("instBean3");
              sess.removeAttribute("instBean4");
              sess.removeAttribute("instBean5");
              sess.removeAttribute("instBean6");
              sess.removeAttribute("instBean7");
              sess.removeAttribute("instBean8");
              sess.removeAttribute("instBean9");
              sess.removeAttribute("instBean10");
              
              PartInstDBbean pdb = new PartInstDBbean();
              Vector allPartInst = pdb.getPartLibrariesCoord(request);
              request.setAttribute("allPartInst", allPartInst);            
          }
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
        
        }
        catch(Exception e){log.error(e.getMessage().toString()); }              
        return mapping.findForward("coversheet");
    }
    

    //FOR LG PARTICIPATING INSTITUITONS
    public ActionForward lgpartinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");                
      
      try{
        PartInstDBbean pdb = new PartInstDBbean();          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
        Vector allParts = pdb.getPartInstAddressInfo(grantid);
        PartCollectionBean pcb = pdb.getEligibleForParts(allParts);
        request.setAttribute("PartCollectionBean", pcb);
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
          
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("partinst");
    }
    
    
    public ActionForward confirmbdgtdelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
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
        log.error("CpNavigationAction "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmbdgtdelete");      
    }
    
    
    public ActionForward confirmamenddelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");               
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          BudgetDeleteBean bb = new BudgetDeleteBean();
          bb.setGrantid(grantid);
          bb.setId(Long.parseLong(request.getParameter("id").trim()));
          bb.setModule(request.getParameter("p"));
          //get descr of amendment record
          FS10DBbean fdb = new FS10DBbean();
          bb.setDescription(fdb.getAmendmentRecordDesc(bb.getId()));
          
          request.setAttribute("deleteBean", bb);
      }
      catch(Exception e) {
        log.error("CpNavigationAction "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmamenddelete");      
    }
    
    
//-------------------the following dispatchactions needed for DI apcnt----------    
    public ActionForward partinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");                
      
      try{
        PartInstDBbean pdb = new PartInstDBbean();          
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
        Vector allParts = pdb.getPartInstAddressInfo(grantid);
        request.setAttribute("allParts", allParts);
      }catch(Exception e){log.error(e.getMessage().toString());}
      
      return mapping.findForward("partinst");
    }
    
  /**
   * FS dispatch action for use with DI/LG- displays all fs documents in html/pdf
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
  public ActionForward fs(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");         
    
    if(!checkGrantId(sess, request))
      return mapping.findForward("idmissing");      
    
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb); 
                    
    }catch(Exception e){log.error(e.getMessage().toString());}
     
    return mapping.findForward("fs");
  }
  
  /**
     * dispatch action for LG 8/19/10. Frank C wants fs20 to appear separately from
     * the other FS forms. 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward fs20(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");         
      
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");      
       
      return mapping.findForward("fs20");
    }
  
  
    public ActionForward acceptform(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession sess = request.getSession();
       if(checkSessionTimeout(sess))
         return mapping.findForward("timeout");     
         
       if(!checkGrantId(sess, request))
         return mapping.findForward("idmissing");     
           
       try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           GrantBean gb=dbh.getRecordBean(grantid); 
           sess.setAttribute("thisGrant", gb); 
           
           TotalsBean tb = new TotalsBean();
           tb = bdh.calcTotalAmtApprCategorized(grantid, tb, 0);
           tb = bdh.calcAmtExpSuppEquip(grantid, tb, 0, true);
           tb = bdh.calcAmtExpProfSupport(grantid, tb, 0, true);
           tb = bdh.calcAmtExpPurchBoces(grantid, tb, 0, true);
           request.setAttribute("totalsBean", tb);
                
       }catch(Exception e){log.error(e.getMessage().toString());}
       
       return mapping.findForward("acceptform");
    }
        
    
  public ActionForward microform(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
            
    if(!checkGrantId(sess, request))         
      return mapping.findForward("idmissing");
    
    try{
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
         
      GrantBean gb=dbh.getRecordBean(grantid); 
      sess.setAttribute("thisGrant", gb);  
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("microform");
  }
  
  
  public ActionForward coopagree(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
            
    if(!checkGrantId(sess, request))         
      return mapping.findForward("idmissing");
      
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("coopagree");
  }
  
  
  //1/10/14 prequalification for cp dg
  public ActionForward prequal(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
            
    if(!checkGrantId(sess, request))         
      return mapping.findForward("idmissing");
      
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("prequal");
  }
  
  
  
  //1/10/14 mwbe for cp dg & lgrmif
  public ActionForward mwbe(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
            
    if(!checkGrantId(sess, request))         
      return mapping.findForward("idmissing");
      
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
           
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("mwbe");
  }
  
  
   public ActionForward payeeinfo(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
      
    if(!checkGrantId(sess, request))
      return mapping.findForward("idmissing");       
                 
    return mapping.findForward("payeeinfo");
  }
  
  
    public ActionForward lgappendix(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");
       
     if(!checkGrantId(sess, request))
       return mapping.findForward("idmissing");       
               
      request.setAttribute("lgfrm", "a");
     return mapping.findForward("lgforms");
    }
    
    public ActionForward printlink(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
       HttpSession sess = request.getSession(); 
       if(checkSessionTimeout(sess))
         return mapping.findForward("timeout");
         
       if(!checkGrantId(sess, request))
         return mapping.findForward("idmissing");       
        
        try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
              
           GrantBean gb=dbh.getRecordBean(grantid); 
           sess.setAttribute("thisGrant", gb);  
        }catch(Exception e){System.out.println("error CpNavigationAction.printlink");}
         
                
        return mapping.findForward("printlink");
    }
    
    
    public ActionForward lgvq(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");
       
     if(!checkGrantId(sess, request))
       return mapping.findForward("idmissing");       
                  
     try{
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            AppStatusBean asb = dbh.getApplicationStatus(grantid);
            request.setAttribute("appStatus", asb);
         
            VendorDBbean vdb = new VendorDBbean();
            ArrayList allquotes = vdb.getVendorQuotesForGrant(grantid);
             RevAssignCollectionBean rc = new RevAssignCollectionBean();
             rc.setAllVendorRecords(allquotes);
             request.setAttribute("AssignCollectionBean", rc);
            
     }catch(Exception e){log.error("CPNavigationAction "+e.getMessage().toString());}
     return mapping.findForward("lgvq");
    }

    //imaging microfilming form
    public ActionForward lgim(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");
       
     if(!checkGrantId(sess, request))
       return mapping.findForward("idmissing");       
     
     try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         sess.setAttribute("thisGrant", gb);  
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
          
         //get all statistics
         StatisticDBbean sdb = new StatisticDBbean();
         StatisticBean sb = sdb.getStatisticsForGrant(grantid);
         request.setAttribute("StatBean",sb);
         
     }catch(Exception e){log.error("CPNavigationAction "+e.getMessage().toString());}
     return mapping.findForward("lgim");
    }
    
    
    public ActionForward lgfinaleduc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
     HttpSession sess = request.getSession(); 
     if(checkSessionTimeout(sess))
       return mapping.findForward("timeout");
       
     if(!checkGrantId(sess, request))
       return mapping.findForward("idmissing");       
                  
     request.setAttribute("lgfrm", "f");
     return mapping.findForward("lgforms");
    }

  public ActionForward statistic(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        String finalTarget = "statistic";
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        if(!checkGrantId(sess, request))         
          return mapping.findForward("idmissing");
       
       try{
           String grantnum= (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           GrantBean gb=dbh.getRecordBean(grantid); 
           sess.setAttribute("thisGrant", gb);  
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
            
            //get all statistics 
            StatisticDBbean sdb = new StatisticDBbean();
            StatisticBean sb = sdb.getStatisticsForGrant(grantid);
            request.setAttribute("StatBean",sb);
           
           //lgrmif now has 2 statistics forms; new form starting FY2013-14
           if(gb.getFccode()==80){
              if(gb.getFycode()>13)
                  finalTarget = "statisticnew";
           }
       }
       catch(Exception e){ log.error("CpNavigationAction "+e.getMessage().toString());
       }
      return mapping.findForward(finalTarget);
    }
    
    
    
  public ActionForward decnotes(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {         
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
           
     try{
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         
         GrantBean gb=dbh.getRecordBean(grantid); 
         sess.setAttribute("thisGrant", gb);  
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
          
         PanelDbBean pdb = new PanelDbBean();
         PanelReviewBean pb = pdb.getDecisionSummary(grantid);
         request.setAttribute("panelBean", pb);
     }
     catch(Exception e){ log.error("CpNavigationAction "+e.getMessage().toString());
     }
    return mapping.findForward("decnotes");
  }
  
  //FOLLOWING FOR CONSTRUCTION ONLY  
    public ActionForward otherfunds(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
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
      }
      catch(Exception e) {
        log.error("CpNavigationAction "+e.getMessage().toString());
      }        
      return mapping.findForward("otherfunds");      
    }
    
    
    //for construction only 8/18/16 per mwaltz
    public ActionForward smartgrowth(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
     {   
       HttpSession sess = request.getSession(); 
       if(checkSessionTimeout(sess))
         return mapping.findForward("timeout");         
       
       if(!checkGrantId(sess, request))
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
       
       if(!checkGrantId(sess, request))
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
       
       if(!checkGrantId(sess, request))
         return mapping.findForward("idmissing");      
       
       try{
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           
           GrantBean gb=dbh.getRecordBean(grantid); 
           request.setAttribute("thisGrant", gb); 
                       
       }catch(Exception e){log.error(e.getMessage().toString());}
        
       return mapping.findForward("seaf");
     }
    
    
    
    
    //for CN - final budget expenses - separate from initial budget
    public ActionForward finalexpense(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");               
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
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
        log.error("CpNavigationAction "+e.getMessage().toString());
      }        
      return mapping.findForward("finalexpense");      
    }
    
    
    //for CN - update manager info during final checklist
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
         OfficerDBbean odb = new OfficerDBbean();
         csb= odb.getProjectManager(csb);
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("projManager");      
    }
    
    
    //for CN - new completion form - 2 data entry fields
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
            
         AppStatusBean asb = dbh.getApplicationStatus(grantid);
         request.setAttribute("appStatus", asb);
          
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
    
    
    public boolean checkGrantId(HttpSession sess, HttpServletRequest request)
    { 
        boolean hasGrantId = true;
        int fccode=0;
        
        try{
          if(sess.getAttribute("grantid")==null || sess.getAttribute("grantid").equals(""))
          {
             hasGrantId = false;
             
             String program=request.getParameter("m");
             if(program.equals("lg"))
              fccode=80;
             else if(program.equals("sa"))
              fccode=6;
             else if(program.equals("co"))
              fccode=7;
             else if(program.equals("di"))
              fccode=5;
             else if(program.equals("cn"))
              fccode=86;
                           
             UserBean userb= (UserBean) sess.getAttribute("lduser");
             HashMap results = dbh.getGrantsForProgramHome(userb.getInstid(), fccode);
            
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
              request.setAttribute("chooseGrant", Boolean.valueOf("true"));
                           
          }   
        
        }catch(Exception e){ log.error(e.getMessage().toString());   }
        
        return hasGrantId;
    }
    
    
    
  public ActionForward assurance(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    HttpSession sess = request.getSession();
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");       
      
    if(!checkGrantId(sess, request))
      return mapping.findForward("idmissing");     
        
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb); 
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
         
        AuthorizationsDBbean adb = new AuthorizationsDBbean();
        AuthorizationBean ab = adb.getGrantAssurance(grantid);
        request.setAttribute("authorizationBean", ab);
    }catch(Exception e){log.error(e.getMessage().toString());}
    
    return mapping.findForward("assurance");
  }
  
  
  
}
