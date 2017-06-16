package construction;

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

public class SaveProjManagerAction extends Action{
   
   
    private OfficerDBbean odb = new OfficerDBbean();
    private DBHandler dbh = new DBHandler();
    
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
          ActionErrors ae = cb.validateProjManager(mapping, request);
           if(ae !=null && (ae.size() > 0))
           {
             request.setAttribute(Globals.ERROR_KEY, ae); 
             return (mapping.findForward(finalTarget+"projmanager"));
           }   
          
          UserBean lduser = (UserBean) sess.getAttribute("lduser");        
          if(cb.getPmId()==0)
             odb.insertProjectManager(grantid, lduser, cb);
          else
             odb.updateProjectManager(lduser, cb);
            
           //refresh data and redisplay                  
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb); 
           
           CoversheetBean csb = new CoversheetBean();      
           csb.setModule(finalTarget);
           csb.setGrantid(grantid);    
           OfficerDBbean odb = new OfficerDBbean();
           csb= odb.getProjectManager(csb);
           request.setAttribute("coversheetBean", csb);     
          
       }catch(Exception e){
         System.out.println("error SaveProjManagerAction  "+e.getMessage().toString());
       }       
       return mapping.findForward(finalTarget+"projmanager");         
    }  
   
   
}
