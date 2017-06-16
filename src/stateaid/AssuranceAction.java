package stateaid;


import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.AuthorizationBean;
import mypackage.AuthorizationsDBbean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;

import mypackage.GrantBean;
import mypackage.UserBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssuranceAction extends Action
{
  private DBHandler dbh = new DBHandler();
  private Log log = LogFactory.getLog(this.getClass());
  
  
  public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
  {         
       HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
        
       try{
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
         UserBean userb = (UserBean) sess.getAttribute("lduser");  
           
        AuthorizationBean authb = (AuthorizationBean) request.getAttribute("authorizationBean");
        System.out.println("id "+authb.getGrantid());
        
        AuthorizationsDBbean adb = new AuthorizationsDBbean();
        int outcome =0;
        //insert or update into db
        if(authb.getId()==0)//insert
           outcome = adb.insertAssurance(authb, userb);
        else                //update
           outcome = adb.updateAssurance(authb, userb);
           
        //////////////refresh and reload page
         GrantBean gb=dbh.getRecordBean(grantid); 
         sess.setAttribute("thisGrant", gb); 
          
         
         AuthorizationBean ab = adb.getGrantAssurance(grantid);
         request.setAttribute("authorizationBean", ab);           
           
       }catch(Exception e){log.error(e.getMessage().toString());}
       
       return mapping.findForward("success");
    }
  
  
  
  
  
  
  public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null);
      if (!userID && sess.isNew())
      {      
        timeout = true;
      }      
      return timeout;
    }
  
 
}
