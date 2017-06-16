package literacy;
import clobpackage.ClobBean;

import construction.AllocationYearBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.BudgetDeleteBean;
import mypackage.CommentDBbean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.SubmissionDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LitFinalAction extends DispatchAction
{
  private DBHandler dbh = new DBHandler();
  
  
   public ActionForward finalauth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
              
      if(!checkGrantId(sess, request))         
        return mapping.findForward("idmissing");
        
      
     OfficerDBbean odb = new OfficerDBbean();     
     String prog = request.getParameter("p");
           
      try{
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
      
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean); 
        
          OfficerBean pm = odb.getProjectManager(grantid);
          request.setAttribute("projManagerBean", pm);
        
          //get inst address info, grant
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);                                
                           
        }catch(Exception e){
          System.out.println("LitFinalAction "+e.getMessage().toString());
        }
      
      if(prog.equals("fl"))
        return mapping.findForward("fauth");
      else if(prog.equals("al"))
        return mapping.findForward("aauth");
      else
        return mapping.findForward("error");
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
      String prog = request.getParameter("p");
      String finalTarget="";
            
      try{
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);
          
          int narrativeTypeId=0;
          if(asb.getFycode()<16){
              narrativeTypeId=41;//everything prior to 2016-19
              finalTarget = prog+"finalrpt";
          }
          else {
              //starting 2016-19 final reports
              String year = request.getParameter("y");
              int rptyear = Integer.parseInt(year);
              
              //get final narrative type id based on reporting year (1/2/3)
              switch(rptyear){
                case 1:
                    narrativeTypeId=122;
                    finalTarget = prog+"finalrptyear1";
                    break;
                case 2:
                    narrativeTypeId=123;
                    finalTarget = prog+"finalrptyear2";
                    break;
                case 3:
                    narrativeTypeId=124;
                    finalTarget = prog+"finalrptyear3";
                    break;
              }
          }
                    
          //get the narrative from the db and set to descrBean in session
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(narrativeTypeId); 
          cb.closeOracleConnection();
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(narrativeTypeId);  
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(narrativeTypeId));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrativeTypeId, asb.getFccode(), asb.getFycode()));     
          pdb.setModule(prog+"finrpt");
          request.setAttribute("projNarrative", pdb);    
                    
      }catch(Exception e){
        System.out.println("LitFinalAction "+e.getMessage().toString());
      }      
      return mapping.findForward(finalTarget);
    }
    
  
  public ActionForward budget(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");
      
      String finalTarget="";
      try{
          String prog=request.getParameter("p");
          request.setAttribute("p", prog);
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
      
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          int tab = 1;//starting tab for FY <2012-13, personal services
          if(asb.getFycode()>13)
            tab = 4;//starting tab for FY2013-14, purchased serv          
          
          
          //project budget exp are saved in expense category beans 
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, true);
          request.setAttribute("BudgetCollectionBean", bc);
                  
          GrantBean gb = dbh.getRecordBean(grantid);
          HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
          request.setAttribute("totalsMap", tb);
          
          BudgetDBHandler bdh = new BudgetDBHandler();          
          if(asb.getFycode()>13){
              Vector warns = bdh.getTotAmtReqLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
              request.setAttribute("fyWarnings", warns);
          }
          else{
              Vector warns = bdh.getTotAmtReqLiFYPeriod(grantid, asb.getFycode(), asb.getFccode());
              request.setAttribute("fyWarnings", warns);
          }
          
           
          //budget split into intial and final pages
         if(asb.isPendingReview() || asb.isInitialappr())
            finalTarget = prog+"expense"+tab;//final budget (expenses)
         else
            finalTarget = prog+"budget"+tab;//initial budget    
         
         //get coresponding budget narr for tab
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrativeTypeId, asb.getFccode(), asb.getFycode()));          
          request.setAttribute("projNarrative", pdb);               
          
      }catch(Exception e){
        System.out.println("LitFinalAction "+e.getMessage().toString());
      }
             
      return mapping.findForward(finalTarget);      
    }
  
  
  
  public ActionForward interim(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      if(!checkGrantId(sess, request))
        return mapping.findForward("idmissing");        
        
      DescriptionBean pdb = new DescriptionBean();
      NarrativeDBbean ndb = new NarrativeDBbean();      
      String prog = request.getParameter("p");
            
      try{
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
      
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);
          
          //get the narrative from the db and set to descrBean in session
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(52); 
          cb.closeOracleConnection();
          
          pdb.setNarrative(cb.getData());
          pdb.setId(cb.getNarrID());
          pdb.setNarrTypeID(52);  
          pdb.setNarrativeTitle(ndb.getNarrativeTitle(52));
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(52, asb.getFccode(), asb.getFycode()));   
          pdb.setModule(prog+"interim");
          request.setAttribute("projNarrative", pdb);    
                    
      }catch(Exception e){
        System.out.println("LitFinalAction "+e.getMessage().toString());
      }
      
      return mapping.findForward(prog+"interim");
    }
    
    
     public ActionForward intauth(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
              
      if(!checkGrantId(sess, request))         
        return mapping.findForward("idmissing");
        
     OfficerDBbean odb = new OfficerDBbean();        
     String prog = request.getParameter("p");
     
      try{
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean); 
        
          OfficerBean pm = odb.getProjectManager(grantid);
          request.setAttribute("projManagerBean", pm);
        
          //get inst address info, grant
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);                                
                           
        }catch(Exception e){
          System.out.println("LitFinalAction "+e.getMessage().toString());
        }
      
      return mapping.findForward(prog+"intauth");
    }
  
  public ActionForward checklist(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
      HttpSession sess = request.getSession();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");            
            
      //links on home page have grantid parameter that needs to be set to session
      if(request.getParameter("id")!=null)
      {
        String chosenGrant = request.getParameter("id");
        sess.setAttribute("grantid", chosenGrant);
      }       
      
      String prog = request.getParameter("p");
      try{      
     
        if(!checkGrantId(sess, request))
          return mapping.findForward("idmissing");         
              
        String grantnum= (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
             
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);  
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        asb.setGrantprogram(prog);
        request.setAttribute("appStatus", asb);  
          
        //8/13/15 per KBALSEN; get yearly allocation and print on checklist
        BudgetDBHandler bdh = new BudgetDBHandler();
        Vector<AllocationYearBean> allalloc = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
        request.setAttribute("allAllocAmounts", allalloc);
      
      }catch(Exception e){System.out.println("error LitFinal "+e.getMessage());}
      
      if(prog!=null && prog.equalsIgnoreCase("fl"))
        return mapping.findForward("fchecklist");
      else if(prog!=null && prog.equalsIgnoreCase("al"))
        return mapping.findForward("achecklist");
      else
        return mapping.findForward("error");
        
    }
  
  
  public ActionForward attachment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        if(!checkGrantId(sess, request))         
          return mapping.findForward("idmissing");
       
       String prog = request.getParameter("p");
       
       try{
           String grantnum= (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
            
            //get all attachments for grantid
            Vector results = dbh.getAllDocuments(grantid);
            request.setAttribute("allDocs", results);
       }
       catch(Exception e){
         System.out.println("error LitFinalAction "+e.getMessage().toString());
       }
       if(prog.equals("fl"))
          return mapping.findForward("fattachment");
       else if(prog.equals("al"))
          return mapping.findForward("aattachment");
      else
          return mapping.findForward("error");
    }
    
    
    public ActionForward statisticsrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
                
        if(!checkGrantId(sess, request))         
          return mapping.findForward("idmissing");
       
       String prog = request.getParameter("p");
       String finalTarget="";
       
       try{
           String grantnum= (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           AppStatusBean asb = dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
            
            //get all statistics 
            StatisticDBbean sdb = new StatisticDBbean();
            StatisticBean sb = sdb.getStatisticsForGrant(grantid);
            sb.setModule(prog);
            request.setAttribute("StatBean",sb);
           
           if(asb.getFccode()==42 && asb.getFycode()>13)
               finalTarget="flnewstatistics";//new stat form for FL starting fy2013-14
           else
              finalTarget=prog+"statistics";
       }
       catch(Exception e){
         System.out.println("error LitFinalAction "+e.getMessage().toString());
       }
      return mapping.findForward(finalTarget);
    }
    
    
    public ActionForward fs(ActionMapping mapping, ActionForm form,
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
        
      }catch(Exception e){{log.error(e.getMessage().toString());}  }
        
      String prog = request.getParameter("p");
      return mapping.findForward(prog+"fs");
    }
    
    
    public ActionForward fs10a(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
        HttpSession sess = request.getSession();
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");            
        
        String prog = request.getParameter("p");
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
               
            GrantBean gb=dbh.getRecordBean(grantid); 
            sess.setAttribute("thisGrant", gb);                
           
        }catch(Exception e){log.error(e.getMessage().toString());}
        
        return mapping.findForward(prog+"fs10a");
    }
  
  
  public ActionForward status(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        String prog = request.getParameter("p");
        try{
          //get all submission and approval info
          CommentDBbean cdb = new CommentDBbean();
          ApprovalsDBbean adb = new ApprovalsDBbean();
          SubmissionDBbean sb = new SubmissionDBbean();
          
          String grantnum= (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          Vector allSubmissions = sb.getSubmissions(grantid);
          Vector allApprovals = adb.getApprovals(grantid);
          allApprovals = adb.getAmountForApprovals(allApprovals);
          Vector allComments = new Vector();//CA 11/18/08 do not show admin comments to apcnt
          Vector apcntComm = cdb.getApcntComments(grantid);
          
          request.setAttribute("allSubmissions", allSubmissions);
          request.setAttribute("allApprovals", allApprovals);
          request.setAttribute("allComments", allComments);
          request.setAttribute("apcntComments", apcntComm);
          
        }catch(Exception e){
          System.out.println("error LitFinalAction "+e.getMessage().toString());
        }
        
        return mapping.findForward(prog+"status");          
    }
    
    
    public ActionForward confirmamenddelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      String module = "";
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
          if(asb.getFccode()==40)
            module ="al";
          else
            module ="fl";
            
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
        log.error("LitFinalAction "+e.getMessage().toString());
      }        
      return mapping.findForward(module+"confirmamenddelete");      
    }
    
    
    //new 10/29/12 update manager info during final checklist
    public ActionForward viewProjManager(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = "";
      try{ 
         String grantnum= (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
         module = request.getParameter("p");
         CoversheetBean csb = new CoversheetBean();      
         csb.setModule(module);
         csb.setGrantid(grantid);    
         OfficerDBbean odb = new OfficerDBbean();
         csb= odb.getProjectManager(csb);
         csb= odb.getAdditionalContact(csb,3);
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward(module+"projManager");      
    }
    
    //new 10/29/12 update manager info during final checklist
    public ActionForward updateProjManager(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = "";
      try{ 
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          CoversheetBean cb = (CoversheetBean) request.getAttribute("coversheetBean");//get pm info 
          module = cb.getModule();
          
          //validate the data
          ActionErrors ae = cb.validateProjManager(mapping, request);
          if(ae !=null && (ae.size() > 0))
          {
             request.setAttribute(Globals.ERROR_KEY, ae); 
             return (mapping.findForward(module+"projManager"));
          }  
          
          ae = cb.validateContact(mapping, request);
          if(ae !=null && (ae.size() > 0))
          {
             request.setAttribute(Globals.ERROR_KEY, ae); 
             return (mapping.findForward(module+"projManager"));
          } 
          
          //update prjoect manager 
          OfficerDBbean odb = new OfficerDBbean();
          UserBean lduser = (UserBean) sess.getAttribute("lduser");        
          if(cb.getPmId()==0)
             odb.insertProjectManager(grantid, lduser, cb);
          else
             odb.updateProjectManager(lduser, cb);
             
          //update additional contact   
          if(cb.getRmoId()==0)
              odb.insertAdditionalContact(grantid, lduser, cb, 3);
          else
              odb.updateAdditionalContact(lduser, cb);    
             
         //refresh variables and redisplay
         GrantBean gb = dbh.getRecordBean(grantid);
         request.setAttribute("thisGrant", gb); 
            
         CoversheetBean csb = new CoversheetBean();      
         csb.setModule(module);
         csb.setGrantid(grantid);          
         csb= odb.getProjectManager(csb);
         csb= odb.getAdditionalContact(csb,3);
         request.setAttribute("coversheetBean", csb);          
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward(module+"projManager");      
    }
  
   public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      boolean userID = (sess.getAttribute("lduser") != null); 
      if (!userID && sess.isNew())
        timeout = true;
           
      return timeout;
    }
    
    public boolean checkGrantId(HttpSession sess, HttpServletRequest request)
    { 
        boolean hasGrantId = true;
        if(sess.getAttribute("grantid")==null || sess.getAttribute("grantid").equals(""))
        {
          hasGrantId = false;
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          
          HashMap results = dbh.getGrantsForProgramHome(userb.getInstid(), 42);
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
        return hasGrantId;
    }
  
  
  
}