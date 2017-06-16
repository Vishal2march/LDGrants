/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveChecklistAction.java
 * Creation/Modification History  :
 *
 * SH   7/13/08      Modified
 *
 * Description
 * This servlet will handle the save progress button clicks for initial and final
 * checklist options for DI/SA/CO/FL/AL/LG/CN (separate action mappings).
 *****************************************************************************/
package discretionary;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveChecklistAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
        
        try{
        DBHandler dbh = new DBHandler();                
        AppStatusBean asb = (AppStatusBean) form; //asb should have projdescComp filled in
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        String grantid = (String) sess.getAttribute("grantid");
        
        //get fiscal year, fund code, proj num... etc.
        GrantBean gb = dbh.getRecordBean(Long.parseLong(grantid));
        sess.setAttribute("thisGrant", gb); 
                           
        int outcome=0;
        if(asb.getApplicationType().equals("initial"))
          outcome = dbh.saveInitialProgress(asb, userb, gb.getFccode()); 
        else if(asb.getApplicationType().equals("final"))
          outcome = dbh.saveFinalProgress(asb, userb, gb.getFccode());        
                  
        AppStatusBean as = dbh.getApplicationStatus(Long.parseLong(grantid));
        request.setAttribute("appStatus", as);
                     
        }catch(Exception e){System.out.println("error SaveChecklistAction "+e.getMessage().toString());}
        
        //route back to checklist, either sa/co/di/al/fl/lg/cn
        return mapping.findForward("success");
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT SaveChecklistAction");
        timeout = true;
      }      
      return timeout;
    }
    
}