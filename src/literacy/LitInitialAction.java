/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  LiInitialAppAction.java
 * Creation/Modification History  :
 * SH   8/1/08      Created
 *
 * Description
 * This action will route links to all portions of initial application for LI such
 * as narratives, budget, etc. Currently FL/AL.
 *****************************************************************************/
package literacy;
import clobpackage.ClobBean;

import construction.AllocationYearBean;

import construction.ConstructAllocationDbBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.BudgetDeleteBean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.PartInstDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LitInitialAction extends DispatchAction
{
  
  private DBHandler dbh = new DBHandler();
  private BudgetDBHandler bdh = new BudgetDBHandler();
  
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
      if(!checkGrantId(sess, request))
            return mapping.findForward("idmissing");  
            
      String prog = request.getParameter("p");  
      try{                            
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          UserBean userb = (UserBean) sess.getAttribute("lduser"); 
    
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);  
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);      
          
        //added 1/2/13 to check for and add new sedref director fk to grant_admins table
        OfficerDBbean odb = new OfficerDBbean();
        odb.verifyCurrentDirector(grantid, gb.getInstID(), userb);
          
          //8/13/15 per KBALSEN; get yearly allocation and print on checklist
          Vector<AllocationYearBean> allalloc = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
          request.setAttribute("allAllocAmounts", allalloc);
                    
      }catch(Exception e){System.out.println("error LitInitial "+e.getMessage().toString());}
      
      if(prog!=null && prog.equalsIgnoreCase("fl"))
        return mapping.findForward("fchecklist");
      else if(prog!=null && prog.equalsIgnoreCase("al"))
        return mapping.findForward("achecklist");
      else
        return mapping.findForward("error");
    }
    
    
     public ActionForward coversheet(ActionMapping mapping, ActionForm form,
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
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);

            GrantBean gb=dbh.getRecordBean(grantid); 
            sess.setAttribute("thisGrant", gb);      
                        
            //get the senate, congress districts, etc.
            DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
            request.setAttribute("distBean", db);
            
            OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
            request.setAttribute("libDirectorBean", libdirectorBean); 
            
            //get all info that can be updated using struts form
            CoversheetBean csb = new CoversheetBean();
            csb.setGrantid(grantid);        
            csb =dbh.getProjectNameReligAffil(csb);        
            csb= odb.getProjectManager(csb);        
            csb= odb.getAdditionalContact(csb, 3);
            request.setAttribute("coversheetBean", csb);     
            
            AppStatusBean as = dbh.getApplicationStatus(grantid);
            as.setGrantprogram(prog);
            request.setAttribute("appStatus", as);
            
            if(as.getFycode()<14){
              //get total amount requested by fiscal year
              Vector allsums =bdh.getTotAmtReqLiFYPeriod(grantid, gb.getFycode(), gb.getFccode());
              request.setAttribute("allsums", allsums);
            }
            else{
                //get allocation amounts
                Vector allocs = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
                request.setAttribute("allAlloc", allocs);
            }
        
        }catch(Exception e){
          System.out.println("LitInitialAction "+e.getMessage().toString());
        }
              
        if(prog!=null && prog.equalsIgnoreCase("fl"))
          return mapping.findForward("fcoversheet");   
        else if(prog!=null && prog.equalsIgnoreCase("al"))
          return mapping.findForward("acoversheet");
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
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
            
          //get all attachments for grantid
          Vector results = dbh.getAllDocuments(grantid);
          request.setAttribute("allDocs", results);
       }
       catch(Exception e){
         System.out.println("error LitInitialAction "+e.getMessage().toString());
       }
       if(prog.equals("fl"))
          return mapping.findForward("fattachment");
       else if(prog.equals("al"))
          return mapping.findForward("aattachment");
      else
          return mapping.findForward("error");
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
      String prog = request.getParameter("p");
      String newnarrs = "";//added 1/15/16 - check FY; send to new narrative page for 16-19 if needed
      
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);
          
          if(asb.getFycode() >16)
              newnarrs = "new";
          
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
          pdb.setModule(prog+"narr");
          request.setAttribute("projNarrative", pdb); 
          
          
                    
      }catch(Exception e){
        System.out.println("LitInitialAction "+e.getMessage().toString());
      }
        //9/27/10 separate 'summary descr' narrative on its own jsp, not on the liNarrative.jsp
        return mapping.findForward(prog+"summarynarr"+newnarrs);
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
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
          
          int tab = 1;//starting tab for FY <2012-13, personal services
          if(asb.getFycode()>13)
            tab = 4;//starting tab for FY2013-14, purchased serv          
          
          
          GrantBean gb = dbh.getRecordBean(grantid);
          if(asb.getFycode()>13){              
              Vector warns = bdh.getTotAmtReqLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
              request.setAttribute("fyWarnings", warns);
          }
          else{
              Vector warns = bdh.getTotAmtReqLiFYPeriod(grantid, asb.getFycode(), asb.getFccode());
              request.setAttribute("fyWarnings", warns);
          }
          
          //project budget exp are saved in expense category beans 
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, true);
          request.setAttribute("BudgetCollectionBean", bc);
           
          HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
          request.setAttribute("totalsMap", tb);
          
          //budget split into intial and final pages
         if(asb.isPendingReview() || asb.isInitialappr())
            finalTarget = prog+"expense"+tab;//final budget (expenses)
         else
            finalTarget = prog+"budget"+tab;//initial budget    
            
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
          pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(narrativeTypeId, asb.getFccode(), asb.getFycode()));          
          request.setAttribute("projNarrative", pdb);   
      
      }catch(Exception e){
        System.out.println("LitInitialAction "+e.getMessage().toString());
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
    
    
    
    public ActionForward bcert(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
              
      if(!checkGrantId(sess, request))         
        return mapping.findForward("idmissing");
        
      String prog = request.getParameter("p");
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh. getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);  
      }catch(Exception e){System.out.println("error LitInitialAction "+e.getMessage().toString());};
      
      return mapping.findForward(prog+"bcert");
    }
    
    public ActionForward auth(ActionMapping mapping, ActionForm form,
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
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean); 
    
          //get inst address info, grant
          GrantBean gb=dbh.getRecordBean(grantid); 
          sess.setAttribute("thisGrant", gb);      
                             
          CoversheetBean csb = new CoversheetBean();
          csb.setGrantid(grantid);        
          csb =dbh.getProjectNameReligAffil(csb);        
          csb= odb.getProjectManager(csb);        
          request.setAttribute("coversheetBean", csb);    
          
          //get total amount requested by fiscal year
          Vector allsums =bdh.getTotAmtReqLiFYPeriod(grantid, gb.getFycode(), gb.getFccode());
          request.setAttribute("allsums", allsums);
          
          //get the senate, congress districts, etc.
          DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
          request.setAttribute("distBean", db);
            
          PartInstDBbean pdb = new PartInstDBbean();
          Vector allParts = pdb.getPartInstAddressInfo(grantid);
          request.setAttribute("allParts", allParts);
        
        }catch(Exception e){
          System.out.println("error LitInitialAction "+e.getMessage().toString());
        }
      
      if(prog.equals("fl"))
        return mapping.findForward("fauth");
      else if(prog.equals("al"))
        return mapping.findForward("aauth");
      else
        return mapping.findForward("error");
    }
    
    
    public ActionForward partinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String prog = request.getParameter("p");
      String finalTarget="";
      try{              
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          GrantBean gb=dbh.getRecordBean(grantid); 
          request.setAttribute("thisGrant", gb);     
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          asb.setGrantprogram(prog);
          request.setAttribute("appStatus", asb);
          
          
          if(gb.getFycode()<14)
          {     //old method
                PartInstDBbean pdb = new PartInstDBbean();   
                Vector allParts = pdb.getPartInstAddressInfo(grantid);
                request.setAttribute("allParts", allParts);
                
                if(prog!=null){
                    finalTarget=prog+"partinst";
                    //alpartinst  or flpartinst
                }                
          }
          else
          {
            //new method starting fy 2013-14             
             //get the narrative from the db and set to descrBean in session
              ClobBean cb = new ClobBean();
              cb.setGrantid(grantid);
              cb.openOracleConnection();      
              cb.getClobNarrative(107); 
              cb.closeOracleConnection();
               
              DescriptionBean pdb = new DescriptionBean();
              pdb.setNarrative(cb.getData());
              pdb.setId(cb.getNarrID());
              pdb.setNarrTypeID(107);  
              NarrativeDBbean ndb = new NarrativeDBbean();
              pdb.setNarrativeDescr(ndb.getNarrativeInstructionsLi(107, asb.getFccode(), asb.getFycode()));
              pdb.setNarrativeTitle(ndb.getNarrativeTitle(107));
              pdb.setModule(prog+"partnarr");
              request.setAttribute("projNarrative", pdb);    
            
                          
              if(prog!=null)
                finalTarget=prog+"newparticipant";              
          }      
          
      }catch(Exception e){
        System.out.println("error LitInitialAction "+e.getMessage().toString());
      }    
      return mapping.findForward(finalTarget);
    }
    
    
   public ActionForward payeeinfo(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {   
    String module="";
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
      
    if(!checkGrantId(sess, request))
      return mapping.findForward("idmissing");       
      
    try{
      module = request.getParameter("p");
    }catch(Exception e){System.out.println("error LitInitialAction "+e.getMessage().toString());}
                 
    return mapping.findForward(module+"payeeinfo");
  }
    
    
    public ActionForward confirmbdgtdelete(ActionMapping mapping, ActionForm form,
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
          bb.setTab(Integer.parseInt(request.getParameter("tab").trim()));
          bb.setModule(request.getParameter("p"));
          //get descr of record
          bb.setDescription(bdh.getBudgetRecordDesc(bb.getTab(), bb.getId()) );
          
          request.setAttribute("deleteBean", bb);
      }
      catch(Exception e) {
        log.error("LitInitialAction "+e.getMessage().toString());
      }        
      return mapping.findForward(module+"confirmbdgtdelete");      
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
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
    
    
    
    
    
  /**
     * new action 2/2/16 per kbalsen - displays certification form
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward certform(ActionMapping mapping, ActionForm form,
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
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");//gets sedref entry from sed_admin_positions table
        request.setAttribute("libDirectorBean", libdirectorBean); 
  
        //get inst address info, grant
        GrantBean gb=dbh.getRecordBean(grantid); 
        sess.setAttribute("thisGrant", gb);      
        
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
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        asb.setGrantprogram(prog);
        request.setAttribute("appStatus", asb);                
             
      }catch(Exception e){
        System.out.println("error LitInitialAction "+e.getMessage().toString());
      }
    
    if(prog.equals("fl"))
      return mapping.findForward("flcertform");
    else if(prog.equals("al"))
      return mapping.findForward("alcertform");
    else
      return mapping.findForward("error");
  }
  
}