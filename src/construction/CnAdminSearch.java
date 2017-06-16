package construction;

import java.util.Collections;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.DBHandler;
import mypackage.GrantBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CnAdminSearch extends DispatchAction{
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
    
    
    public ActionForward fiscalyear(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String year = request.getParameter("fycode");
        Vector allGrants = dbh.getGrantsForYear(year, "86");
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error CnAdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward("searchapps");   
    }
    
    
    public ActionForward systemsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String systemid = request.getParameter("sysInstId");
        Vector allGrants = cdb.getMemberAppsForSystem(Long.parseLong(systemid));
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error CnAdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward("searchapps");   
    }
    
    
    public ActionForward instname(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
         String inst = request.getParameter("instname"); 
         Vector allGrants = dbh.searchGrantsByInst(inst, "86");       
                 
         Collections.sort(allGrants, GrantBean.FiscalYearComparator);        
         request.setAttribute("allGrants", allGrants);       
      
      }catch(Exception e) {
        System.out.println("error CnAdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward("searchapps");   
    }
    
    
    public ActionForward projnumber(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String projnum = request.getParameter("projnum");
        Vector allGrants = dbh.getGrantsByNum(projnum);
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward("searchapps");   
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT CnAdminSearch");
        timeout = true;
      }
      
      return timeout;
    }    
    
}
