package literacy;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.OfficerDBbean;
import mypackage.SubmissionDBbean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminSaveContact extends Action{
    private DBHandler dbh = new DBHandler();
    private OfficerDBbean odb = new OfficerDBbean();
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception
      {        
        String finalTarget="";
        HttpSession sess = request.getSession();          
            
        try{              
          boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
          if (!userID && sess.isNew())         
            mapping.findForward("timeout");
                        
            String grantnum = (String) sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            CoversheetBean cb = (CoversheetBean) form;//get pm info 
            finalTarget = cb.getModule();
            
            //validate the data
            ActionErrors ae = cb.validateContact(mapping, request);
             if(ae !=null && (ae.size() > 0))
             {
               request.setAttribute(Globals.ERROR_KEY, ae); 
               return (mapping.findForward(finalTarget+"contact"));
             }   
            
            UserBean lduser = (UserBean) sess.getAttribute("lduser");     
            if(cb.getRmoId()==0)
                odb.insertAdditionalContact(grantid, lduser, cb, 3);
            else
                odb.updateAdditionalContact(lduser, cb);    
                    
                      
             GrantBean gb = dbh.getRecordBean(grantid);
             sess.setAttribute("thisGrant", gb); 
             
             AppStatusBean asb= dbh.getApplicationStatus(grantid);
             request.setAttribute("appStatus", asb);
             
             SubmissionDBbean sb = new SubmissionDBbean();
             Vector allSubmissions = sb.getSubmissions(grantid); 
             request.setAttribute("allSubmissions", allSubmissions);            
            
        }catch(Exception e){
          System.out.println("error AdminSaveContact "+e.getMessage().toString());
        }       
        return mapping.findForward(finalTarget+"grant");    
      }
}
