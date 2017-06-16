/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ParticipatingNavAction.java
 * Creation/Modification History  :
 * SH   1/14/09      Created
 *
 * Description
 * This dispatch action will route all participating institution calls to view
 * coversheet, budget, attachments, narrs, etc for sa/co/di/fl/al/lg participants.
 *****************************************************************************/
package discretionary;
import clobpackage.ClobBean;

import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.AuthorizationsDBbean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.CommentDBbean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.EligibilityDbBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.PartInstDBbean;
import mypackage.RatingBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.SubmissionDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ParticipatingNavAction extends DispatchAction
{
  private DBHandler dbh = new DBHandler();
  
  
  
  public ActionForward coversheet(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                    
      //links on home page contain grant id that needs to be set to session to view other pages
       if(request.getParameter("id")!=null)
       {
         String grantid = request.getParameter("id");
         sess.setAttribute("grantid", grantid);
       }                 
        OfficerDBbean odb = new OfficerDBbean();
        PartInstDBbean pdb = new PartInstDBbean();        
        
        try{
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            GrantBean gb = dbh.getRecordBean(grantid);
            request.setAttribute("thisGrant", gb);   
                      
            //get the library director
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            request.setAttribute("libDirectorBean", libdirectorBean); 
            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", presOfficerBean);
                                      
            //get participating inst        
            Vector allPartInst = pdb.getParticipants(grantid);
            request.setAttribute("allPartInst", allPartInst);
            
            if(gb.getFccode()==80)//lgrmif
            {
                //get the senate, congress districts, etc. based on grant
                DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
                request.setAttribute("distBean", db);
            }
            else if(gb.getFccode()==5)//for DISCRETIONARY only
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
              BudgetDBHandler bdh = new BudgetDBHandler();
              csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
              EligibilityDbBean edb = new EligibilityDbBean();
              csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());        
              request.setAttribute("coversheetBean", csb);   
                         
               SubmissionDBbean sb = new SubmissionDBbean();
               Vector allSubmissions = sb.getSubmissions(grantid);   
               request.setAttribute("allSubmissions", allSubmissions);
               
               //get the senate, congress districts, etc. based on grant
              DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
              request.setAttribute("distBean", db);
            }
            else if(gb.getFccode()==7)//FOR COORDINATED only
            {                            
              OfficerBean ob =odb.getProjectManager(grantid);
              request.setAttribute("projManagerBean", ob);
            }
            else if(gb.getFccode()==6)//for STATUTORY only
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
            else if(gb.getFccode()==40 ||gb.getFccode()==42)//for LITERACY only
            {
              //get the senate, congress districts, etc.
              DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
              request.setAttribute("distBean", db);
              
              //get all coverpage info
              CoversheetBean csb = new CoversheetBean();
              csb.setGrantid(grantid);        
              csb =dbh.getProjectNameReligAffil(csb);        
              csb= odb.getProjectManager(csb);        
              request.setAttribute("coversheetBean", csb);     
            }
        
        }catch(Exception e) {
          System.out.println("error ParticipatingNavAction coversheet "+e.getMessage().toString());
        }    
        return mapping.findForward("coversheet");
    }
    
    
    public ActionForward narrative(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
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
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
         
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, false, asb, false, 0);
          request.setAttribute("budgetBean", bb);
          
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);   
        
         //get lead grant info         
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
         
         if(gb.getFccode()==6)//for STATUTORY only
         {
            //get any FS10A amendment records
            FS10DBbean fs = new FS10DBbean();
            ArrayList allFSRecords = fs.getFSARecords(grantid);
            request.setAttribute("allFSRecords", allFSRecords);
         }
              
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }      
      return mapping.findForward("budget");      
    }
    
    public ActionForward finalrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");    
           
      try {
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          //get the final narrative
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();
          cb.getClobNarrative(2); //2 is the narrative type id for final narrative
          cb.closeOracleConnection();
          
          //set to bean the narr
          DescriptionBean pdb = new DescriptionBean();
          pdb.setNarrative(cb.getData()); 
          request.setAttribute("projNarrative", pdb);
          
      }
      catch(Exception e) {
        System.err.println("error ParticipatingNavAction " + e.getMessage().toString());
      }              
      return mapping.findForward("finalrpt");      
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
    
    
    public ActionForward coopagree(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb);  
        
        if(gb.getFccode()==7)//if coordinated, need extra data for coopagree
          handleCoordCoopAgree(request, grantid);
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }          
      return mapping.findForward("coopagree");          
    }
    
    
    public void handleCoordCoopAgree(HttpServletRequest request, long grantid)
    {
      HttpSession sess= request.getSession();
      
      try{
        UserBean lduser = (UserBean) sess.getAttribute("lduser");
        long instid = lduser.getInstid();//instid of user signed on; only sumbit coopagree for thier inst
        
        OfficerDBbean odb = new OfficerDBbean();
        OfficerBean presOfficerBean = odb.getOfficerForInst(instid, "16");    
        sess.setAttribute("presOfficerBean", presOfficerBean);
      
        OfficerBean libdirectorBean = odb.getOfficerForInst(instid, "1");
        sess.setAttribute("libDirectorBean", libdirectorBean);  
        
        //get any cooperative agreements for this grant from the participating inst
        AuthorizationsDBbean ab = new AuthorizationsDBbean();
        Vector grantAuth = ab.getInstCoopAgreements(grantid, instid);
        request.setAttribute("grantAuth", grantAuth);
        
      }catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }
      return;
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
        Vector allComments = cdb.getAdminComments(grantid);
        
        request.setAttribute("allSubmissions", allSubmissions);
        request.setAttribute("allApprovals", allApprovals);
        request.setAttribute("allComments", allComments);
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }          
      return mapping.findForward("appstatus");          
    }
    
    
    public ActionForward ratings(ActionMapping mapping, ActionForm form,
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
        
        RatingBean rb = rdb.getCatSumScoresByGrant(grantid);//FOR COORDINATED
        request.setAttribute("totScores", rb);
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);    
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }          
      return mapping.findForward("ratings");          
    }
    
    public ActionForward auth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
         AuthorizationsDBbean ab = new AuthorizationsDBbean();
        Vector grantAuth = ab.getGrantAuthorizations(grantid);
        request.setAttribute("grantAuth", grantAuth);
      }
      catch(Exception e){
        System.out.println("error ParticipatingNavAction "+e.getMessage().toString());
      }          
      return mapping.findForward("auth");          
    }
    
//------------------------------------------------------------------------------    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT ParticipatingNavAction");
        timeout = true;
      }
      
      return timeout;
    }
}