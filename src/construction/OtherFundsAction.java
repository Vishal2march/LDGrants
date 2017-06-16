package construction;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;

import mypackage.BudgetCollectionBean;

import mypackage.DBHandler;

import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OtherFundsAction extends DispatchAction {
    
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
    
    //add new blank fund record
    public ActionForward addrecord(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {         
          HttpSession sess = request.getSession(); 
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
         String module = "";
         
         try{             
             UserBean ub = (UserBean) sess.getAttribute("lduser");
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
             module = request.getParameter("mod");
             
             if(module!=null && !module.equals("")) {
                 //if admin module - verify user permission
                 if(module.equals("admncn") && 
                    ub.admconstruction!=null && (!ub.admconstruction.equals("approve"))  )
                    return mapping.findForward("errorotherfunds");                   
             }
             
             AppStatusBean asb = dbh.getApplicationStatus(grantid);
             request.setAttribute("appStatus", asb);
                                      
             //fund record MUST be associated with a grant_building record.             
             //check if grant_building record exists
             long gbid = cdb.doesGrantBuildingExist(grantid);
             
             if(gbid==0){
                 //if no grant_building; user must fill out coversheet first
                 request.setAttribute("noBuildingError", "true");
             }
             else{
                 //create a new blank fund_record
                 cdb.insertFundRecord(ub, gbid);
             }
             
             //get any existing building/fund records
             ArrayList allfunds = cdb.getAllFundsForProject(grantid);
             BudgetCollectionBean bc = new BudgetCollectionBean();
             bc.setAllFundSources(allfunds);
             request.setAttribute("BudgetCollectionBean", bc);               
             
         }catch(Exception e){ log.error(e.getMessage().toString()); }
          return mapping.findForward(module +"otherfunds");
      }
      
    
    
    public ActionForward deleterecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      UserBean ub = (UserBean) sess.getAttribute("lduser");
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
      String mod="";
      
      try{
          mod=request.getParameter("m");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
                    
          if(mod!=null && !mod.equals("")) {
              //if admin module - verify user permission
              if(mod.equals("admncn") && 
                 ub.admconstruction!=null && (!ub.admconstruction.equals("approve"))  )
                 return mapping.findForward("errorotherfunds");                   
          }
          
          String id = request.getParameter("id");
          int outcome = cdb.deleteFundRecord(Long.parseLong(id));
          
           //get existing building/fund records
           ArrayList allfunds = cdb.getAllFundsForProject(grantid);
           BudgetCollectionBean bc = new BudgetCollectionBean();
           bc.setAllFundSources(allfunds);
           request.setAttribute("BudgetCollectionBean", bc);    
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error OtherFundsAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod +"otherfunds");          
    }
    
    
    
    public ActionForward updatefund(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      String mod = "";
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");

      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
          mod = request.getParameter("mod");
          
          if(mod!=null && !mod.equals("")) {
              //if admin module - verify user permission
              if(mod.equals("admncn") && 
                 userb.admconstruction!=null && (!userb.admconstruction.equals("approve"))  )
                 return mapping.findForward("errorotherfunds");                   
          }
          
          ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
          //cast the action form to a BudgetCollectionBean 
          BudgetCollectionBean b = (BudgetCollectionBean) myform;
                    
          int outcome = cdb.updateFundRecords(b.getAllFundSources(), userb);
                    
           //get any existing building/fund records
           ArrayList allfunds = cdb.getAllFundsForProject(grantid);
           BudgetCollectionBean bc = new BudgetCollectionBean();
           bc.setAllFundSources(allfunds);
           request.setAttribute("BudgetCollectionBean", bc);               
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error OtherFundsAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod +"otherfunds");          
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
