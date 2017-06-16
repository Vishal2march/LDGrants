package stateaid;

import coordinated.AdminDB;


import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.RevAssignCollectionBean;
import mypackage.TotalsBean;

import mypackage.UserBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminAllocationAction extends DispatchAction{
    
  private  DBHandler dbh = new DBHandler();
  private AllocationDbBean adb = new AllocationDbBean();  
  private Log log = LogFactory.getLog(this.getClass());
  
  
  
  public ActionForward viewAlloc(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        String fy = request.getParameter("fy");
        int fycode = Integer.parseInt(fy);          
                 
        //get the 2 stateaid apps for selected FY (if apps exist already)
        List allapps = adb.getStateaidAppsForFy(fycode);       
          
        RevAssignCollectionBean rc= new RevAssignCollectionBean(); 
        rc.setAllStateaidApps(allapps);
        rc.setFycode(fycode);
        
        request.setAttribute("AssignCollectionBean", rc);       
          
                      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
                
      return mapping.findForward("viewAlloc");            
    }
  
  
  
  
  
  public ActionForward addUpdateAlloc(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        UserBean userb = (UserBean)sess.getAttribute("lduser");
          
        ActionForm myform = (ActionForm) request.getAttribute("AssignCollectionBean");
        RevAssignCollectionBean allbeans = (RevAssignCollectionBean) myform;  
                          
        //get all stateaid apps from list
        if(allbeans.getAllStateaidApps()!=null){
            //loop through all apps and update the allocation amt in grants table
            adb.addUpdateAllocation(allbeans.getAllStateaidApps(), userb.getUserid());
        }
          
          
        ////////////refresh data and redisplay on page       
        int fycode = allbeans.getFycode();          
                  
        //get the 2 stateaid apps for selected FY (if apps exist already)
        List allapps = adb.getStateaidAppsForFy(fycode);       
          
        RevAssignCollectionBean rc= new RevAssignCollectionBean(); 
        rc.setAllStateaidApps(allapps);
        rc.setFycode(fycode);
        
        request.setAttribute("AssignCollectionBean", rc);               
                      
      }catch(Exception e) { log.error(e.getMessage().toString()); }
                
      return mapping.findForward("viewAlloc");            
    }
  
  
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT AdminAllocationAction");
        timeout = true;
      }
      
      return timeout;
    }
    
    
}
