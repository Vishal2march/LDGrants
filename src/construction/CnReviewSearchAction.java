package construction;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.DBHandler;

import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CnReviewSearchAction extends DispatchAction{
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
        
    
    public ActionForward fysearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
       {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
         
           try {      
            UserBean ub = (UserBean)sess.getAttribute("lduser");
            
            String year = request.getParameter("fycode");
            int fycode = Integer.parseInt(year);
            
            Vector allmembers =cdb.getMemberAppsForFy(fycode, ub.getInstid());
            request.setAttribute("allApplications", allmembers);  
             
           } catch (Exception e) {
              System.out.println("error CnReviewSearchAction "+e.getMessage().toString());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("searchpage");
     }
     
     
     
    public ActionForward instsearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
       {         
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
         
           try {      
               UserBean ub = (UserBean)sess.getAttribute("lduser");
               
               String inst = request.getParameter("instid");
               long instid = Long.parseLong(inst);
               
               Vector allmembers =cdb.getAppsForMember(instid, ub.getInstid());
               request.setAttribute("allApplications", allmembers);     
                            
           } catch (Exception e) {
              System.out.println("error CnReviewSearchAction "+e.getMessage().toString());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward("searchpage");
     }
     
     
//-------------------------------------------------------------------------------
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
