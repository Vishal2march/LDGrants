package construction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveCompletionAction extends Action{
   
   private ConstructionDBbean cdb = new ConstructionDBbean();
   private DBHandler dbh = new DBHandler();
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception
      {        
        HttpSession sess = request.getSession();          
            
        try{              
          boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
          if (!userID && sess.isNew())         
            mapping.findForward("timeout");
                        
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          FinalExpenseBean cb = (FinalExpenseBean) form;//get completion form info 
         
          
          //validate the data
          ActionErrors ae = cb.validateCompletionForm(mapping, request);
          if(ae !=null && (ae.size() > 0))
          {
             request.setAttribute(Globals.ERROR_KEY, ae); 
             return (mapping.findForward("completion"));
          }   
          
          
          UserBean lduser = (UserBean) sess.getAttribute("lduser");        
         //save completion form data
         cdb.handleSaveCompletionAmounts(cb, lduser);
                    
          //refresh data and redisplay                  
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
           
          //get pm info
          OfficerDBbean odb = new OfficerDBbean();
          OfficerBean pmBean = odb.getProjectManager(grantid);    
          request.setAttribute("managerBean", pmBean);       
           
          //get grant amt and proj cost data
          long buildingGrantId= cdb.doesGrantBuildingExist(grantid);
           
          FinalExpenseBean fb = cdb.getCompletionFormAmounts(grantid, buildingGrantId);
          request.setAttribute("finalExpenseBean", fb);    
          
       }catch(Exception e){
         System.out.println("error SaveProjManagerAction  "+e.getMessage().toString());
       }       
       return mapping.findForward("completion");         
    }  
}
