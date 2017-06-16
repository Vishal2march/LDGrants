/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminSearch.java
 * Creation/Modification History  :
 *
 * SH   9/30/09      Created
 *
 * Description
 * This action class will handle cp/al/fl/lg admin options to search for grants and view results.
 *****************************************************************************/
package lgrmif;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.SedrefInstBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import reports.ReportDBbean;


public class AdminSearch extends DispatchAction
{
  private  DBHandler dbh = new DBHandler();
  
  public ActionForward instname(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String inst = request.getParameter("searchparam"); 
        prog = request.getParameter("progparam");
        String fccode = determineFundCodes(prog);
        Vector allGrants = dbh.searchGrantsByInst(inst, fccode);       
                
        Collections.sort(allGrants, GrantBean.FiscalYearComparator);        
        request.setAttribute("allGrants", allGrants);       
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }
                
      return mapping.findForward(prog+"search");   
    }
    
    
    public ActionForward fiscalyear(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String year = request.getParameter("searchparam");
        prog = request.getParameter("progparam");
        String fccode = determineFundCodes(prog);
        Vector allGrants = dbh.getGrantsForYear(year, fccode);
               
        Collections.sort(allGrants, GrantBean.InstitutionComparator);
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }
                
      return mapping.findForward(prog+"search");   
    }
    
    
    public ActionForward projectnum(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String projnum = request.getParameter("searchparam");
        prog = request.getParameter("progparam");
        Vector allGrants = dbh.getGrantsByNum(projnum);
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }
                
      return mapping.findForward(prog+"search");   
    }
    
    
    public ActionForward cpinst(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String sedrefinst = request.getParameter("searchparam");
        prog = request.getParameter("progparam");
        String fccode = determineFundCodes(prog);
        Vector allGrants = dbh.getGrantsForInst(Long.parseLong(sedrefinst), fccode);
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }
                
      return mapping.findForward(prog+"search");   
    }
    
    
    public ActionForward regionfy(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String fy = request.getParameter("searchparam");
        String reg = request.getParameter("regionparam");
        prog = request.getParameter("progparam");
        
        ReportDBbean rdb = new ReportDBbean();
        Vector allGrants = rdb.getGrantsForRegionFy(Integer.parseInt(fy), Integer.parseInt(reg));
        request.setAttribute("allGrants", allGrants);
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward(prog+"search");   
    }
    
    
    public ActionForward loadsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String prog = "";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        prog = request.getParameter("m");
        ArrayList allyears = dbh.dropDownFiscalYearsDesc();
        sess.setAttribute("dropDownList", allyears);
        
        if(prog!=null && prog.equalsIgnoreCase("cp"))
        {
          SedrefInstBean sb = new SedrefInstBean();
          Vector allInst = sb.getAllInstitutions();        
          sess.setAttribute("allInst", allInst);
        }
        else if(prog!=null && prog.equalsIgnoreCase("lg")) {
            ArrayList regs = dbh.dropDownRegions();
            sess.setAttribute("allRegions", regs);
        }
      
      }catch(Exception e) {
        System.out.println("error AdminSearch "+e.getMessage().toString());
      }                
      return mapping.findForward(prog+"search");   
    }
    
    
    
  public String determineFundCodes(String program)
  {
    String fccodes="";
    try{
       if(program!=null)
       {
          if(program.equalsIgnoreCase("lg"))
            fccodes = "80";
          else if(program.equalsIgnoreCase("cp"))
            fccodes = "5,6,7,20";
          else if(program.equalsIgnoreCase("lit"))
            fccodes = "40,42";         
       }
      
    }catch(Exception e){System.out.println("error AdminSearch "+e.getMessage().toString());};
    return fccodes;
  }
        
    
  public boolean checkSessionTimeout(HttpSession sess)
  {
    boolean timeout = false;
    //check for session timeout
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew())
    {      
      System.out.println("SESSION TIMED OUT AdminSearch");
      timeout = true;
    }
    
    return timeout;
  }    
}