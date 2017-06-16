package reports;

import construction.ConstructAllocationDbBean;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ConstructReportAction extends DispatchAction{
    
    private ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
    private ConstructReportDbBean crdb = new ConstructReportDbBean();
    
    
    public ActionForward duedates(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
              ArrayList allrecs = cadb.getAllAllocationsForYear(fycode, 86);
              s.setAttribute("allAllocations", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("duedates");        
      }
    
    
    public ActionForward systemalloc(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
              ArrayList allrecs = crdb.getSysAllocationsAwardsForYear(fycode);
              s.setAttribute("allAllocations", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("systemalloc");        
      }
      
      
    public ActionForward awardbysystem(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             //get apps submitted by PLS; with amt req, amt recommend, %
             ArrayList allrecs = crdb.getSysRecomendPercentByFy(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("awardbysystem");        
      }
    
  
  /**
     *This action generates SHPO report.  2/25/16 per request from Lynne Webb.  This lists all libraries 
     * that answered yes to any of the following questions from coversheet ( is building historic landmark, is building 
     * in historic district, is building >50 years old, is ground disturbance involved).
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward shporpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
            //tables containing the requested reporting fields:
            //SED_BUILDINGS - historic district; historic landmark
            //GRANT_BUILDINGS - over 50 years old
            //GRANTS.PAID_IN_FULL_YN - ground distrubance
            
           //get apps submitted by PLS; with any of 4 shpo flags set
           ArrayList allrecs = crdb.getLibrariesNeedingShpo(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("shporpt");        
    }
  
      
      
    public ActionForward awardbyldadmin(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             //get apps submitted by PLS; with amt req, LD award amt, %
             ArrayList allrecs = crdb.getLDAwardPercentForFy(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("awardbyldadmin");        
      }
      
    public ActionForward dasnyawardbysystem(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception  
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             //get apps apprvd by LD, submitted to dasny; with amt req, amt recommend, %
             //uses same jsp as 'awardbyldadmin', but a different sql query
             ArrayList allrecs = crdb.getLdAwardPercentByFyForDasny(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("awardbyldadmin");        
      }
      
      
    public ActionForward submittodasny(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             //get apps submitted by PLS; with amt req, LD award amt, %
             ArrayList allrecs = crdb.getAppsSubmittedToDasny(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("submittodasny");        
      }
    
    
    public ActionForward dasnyreviewlist(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             //get apps submitted by PLS; with amt req, LD award amt, %
             ArrayList allrecs = crdb.getAppsSubmitApprByDasny(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("dasnyreviewlist");        
      }
      
      
    public ActionForward dasnyawarddesc(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
              //get apps submitted to LD; amt recommend, startdate, projdesc
              ArrayList allrecs = crdb.getAmtRecommendProjDescList(fycode);
              s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("dasnyawarddesc");        
      }
      
      
    public ActionForward reducematchsystem(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              String systemid = request.getParameter("sysInstId");
              long sysinstid = Long.parseLong(systemid);
              
              ArrayList allrecs = crdb.getReducedMatchReportForSystem(fycode, sysinstid);
              s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("reducematchrpt");         
      }
      
    public ActionForward reducematchrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
               
              ArrayList allrecs = crdb.getAllReducedMatchReportsForFy(fycode);
              s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("reducematchrpt");        
      }
      
  /**
     *5/13/13 per MLT, reduced match based on %funding calculation, not t/f db field
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward reducematchcalcrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
             
            ArrayList allrecs = crdb.getReducedMatchReportsFromCalc(fycode);
            s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction "+e.getMessage().toString());
        }
        return mapping.findForward("reducematchrpt");        
    }
      
    public ActionForward projnumrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception  
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             ArrayList allrecs = crdb.getRecommendProjNumSort(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("projnumrpt");        
      }
      
      
    public ActionForward finalsubmitrpt(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception  
      {               
          HttpSession s = request.getSession();
          try{         
              String fy = request.getParameter("fy");
              int fycode = Integer.parseInt(fy);
              
             ArrayList allrecs = crdb.getFinalSubmitted(fycode);
             s.setAttribute("allApplications", allrecs);
          
          }catch(Exception e){
            System.out.println("error ConstructReportAction "+e.getMessage().toString());
          }
          return mapping.findForward("finalsubmitrpt");        
      }
    
    
  public ActionForward finalnotsubmitrpt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
           ArrayList allrecs = crdb.getFinalNotSubmitted(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.finalnotsubmitrpt "+e.getMessage().toString());
        }
        return mapping.findForward("finalnotsubmitrpt");        
    }
  
  
  /**
     *query all construction apps for selected FY where cert_occupancy=true and initial app
     * submitted to PLS
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward certoccupy(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
            //get all apps submitted to LD; where cert_occupancy=true
           ArrayList allrecs = crdb.getCertOccupancyProjects(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.certoccupy "+e.getMessage().toString());
        }
        return mapping.findForward("certoccupy");        
    }
  
  
  public ActionForward bondapp(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
            //get all apps submitted to LD; where bonding=true
           ArrayList allrecs = crdb.getBondedProjects(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.bondapp "+e.getMessage().toString());
        }
        return mapping.findForward("bondapp");        
    }
  
  /**
     *get submitted cn apps for FY, with total project cost (part A of Application Form)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
  public ActionForward projcosttotal(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
            //get all apps submitted to LD; with total project cost - part A
           ArrayList allrecs = crdb.getProjectCostApplications(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.projcosttotal "+e.getMessage().toString());
        }
        return mapping.findForward("projcosttotal");        
    }
  
  
  public ActionForward projcategory(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            
            //get all apps submitted to LD; with all proj categories
           ArrayList allrecs = crdb.getProjectCategoryForApps(fycode);
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.projcategory "+e.getMessage().toString());
        }
        return mapping.findForward("projcategory");        
    }
  
  
  public ActionForward reducematchcriteria(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception  
    {               
        HttpSession s = request.getSession();
        try{         
            String fy = request.getParameter("fy");
            int fycode = Integer.parseInt(fy);
            String isFilter = request.getParameter("filterMatch");
                        
                        System.out.println("filterMatch "+isFilter);
                        
            //get all apps submitted to LD; with all reduce matches
           ArrayList allrecs = crdb.getReduceMatchCriteriaRpt(fycode, Boolean.parseBoolean(isFilter));            
           s.setAttribute("allApplications", allrecs);
        
        }catch(Exception e){
          System.out.println("error ConstructReportAction.reducematchcriteria "+e.getMessage().toString());
        }
        return mapping.findForward("reducematchcriteria");        
    }
}
