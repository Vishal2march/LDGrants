/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ApplicationDatesAction.java
 * Creation/Modification History  :
 *
 * SH   12/18/08      Created
 *
 * Description
 * This action class will handle all admin actions to view, add, or edit records in the
 * app_dates table.  Used for setting open application period dates. For cp and lit admin.
 *****************************************************************************/
package mypackage;

import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ApplicationDatesAction extends DispatchAction
{
  AppDatesDBbean db = new AppDatesDBbean();
  DBHandler dbh = new DBHandler();
  private Log log = LogFactory.getLog(this.getClass()); 
  
   public ActionForward viewdates(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="error";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        String module = request.getParameter("m");
        finalTarget = module+"viewdates";
        
        Vector daterecords = getDateRecords(module);
        request.setAttribute("allDateRecords", daterecords);
              
      }catch(Exception e) { log.error(e.getMessage().toString()); }                
      return mapping.findForward(finalTarget);   
    }
    
    
    public ActionForward daterecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="error";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        String module = request.getParameter("m");
        finalTarget = module+"update";
        
        String id = request.getParameter("id");
        AppDatesBean ab =db.getDateRecord(Long.parseLong(id));     
        ab.setModule(module);
        request.setAttribute("AppDatesBean", ab);
        
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
          
          ArrayList fundcodes = dbh.dropDownFundCodes();
          sess.setAttribute("dropDownFunds", fundcodes);
      
      }catch(Exception e) {log.error(e.getMessage().toString());}                
      return mapping.findForward(finalTarget);   
    }
    
    
    public ActionForward updaterecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="error";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        UserBean userb = (UserBean)sess.getAttribute("lduser");
        String module = request.getParameter("m");
        finalTarget = module+"viewdates";
        
        //get the actionform 
        ActionForm myform = (ActionForm) request.getAttribute("AppDatesBean");
        //cast the action form to a AppDatesBean 
        AppDatesBean ab = (AppDatesBean) myform;          
        int outcome = db.updateDateRecord(ab, userb);
          
        Vector daterecords = getDateRecords(module);
        request.setAttribute("allDateRecords", daterecords);
              
      }catch(Exception e) {log.error(e.getMessage().toString());}                
      return mapping.findForward(finalTarget);   
    }
    
    
    
    public ActionForward loadAdd(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="error";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{       
          finalTarget = request.getParameter("m");
          AppDatesBean adb = new AppDatesBean();
          adb.setModule(finalTarget);
          request.setAttribute("AppDatesBean", adb);
          
          ArrayList allyears = dbh.dropDownFiscalYearsDesc();
          sess.setAttribute("dropDownList", allyears);
          
          ArrayList fundcodes = dbh.dropDownFundCodes();
          sess.setAttribute("dropDownFunds", fundcodes);      
      
      }catch(Exception e) {log.error(e.getMessage().toString());}                
      return mapping.findForward(finalTarget+"add");   
    }
    
    
    public ActionForward addrecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="error";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        UserBean userb = (UserBean)sess.getAttribute("lduser");
        String module = request.getParameter("m");
        finalTarget = module+"viewdates";
        
        //get the actionform (addappdate form)
        ActionForm myform = (ActionForm) request.getAttribute("AppDatesBean");
        //cast the action form to a AppDatesBean 
        AppDatesBean ab = (AppDatesBean) myform;        
        int outcome = db.addAppDateRecord(ab, userb);
                  
        Vector daterecords = getDateRecords(module);
        request.setAttribute("allDateRecords", daterecords);
                
      }catch(Exception e) {log.error(e.getMessage().toString());}                
      return mapping.findForward(finalTarget);   
    }
    
    
        
  public Vector getDateRecords(String module)
  {
    Vector allrecords =new Vector();
    String fundcodes =null;
    
    try{
       if(module.equals("lg"))
          fundcodes="80";
       else if(module.equals("cp"))
          fundcodes="5,6,7,20";
       else if(module.equals("cn"))
          fundcodes="86";
       else if(module.equals("lit"))
          fundcodes="40,42";
        
        allrecords = db.getAllAppDateRecords(fundcodes);        
        
    }catch(Exception e) {log.error(e.getMessage().toString());}  
    return allrecords;
  }
  
  
  public boolean checkSessionTimeout(HttpSession sess)
  {
    boolean timeout = false;
    //check for session timeout
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew())
    {      
      timeout = true;
    }    
    return timeout;
  }    
    
    
}