package literacy;

import construction.AllocationYearBean;

import construction.ConstructAllocationDbBean;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.DBHandler;
import mypackage.UserBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminAllocationAction extends DispatchAction{
    
    private ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
    private DBHandler dbh = new DBHandler();
    private Log log = (Log)LogFactory.getLog(this.getClass());     
    
    
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
              System.out.println("error AdminAllocationAction "+e.getMessage());
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
              System.out.println("error AdminAllocationAction "+e.getMessage());
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
              System.out.println("error AdminAllocationAction "+e.getMessage());
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
              System.out.println("error AdminAllocationAction "+e.getMessage());
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
              System.out.println("error AdminAllocationAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("allocation");
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
    
    
    
}
