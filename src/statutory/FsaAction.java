package statutory;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.DBHandler;
import mypackage.FS10DBbean;

import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class FsaAction extends DispatchAction{
    private FS10DBbean fs = new FS10DBbean();
    private DBHandler dbh = new DBHandler();
   
   
    public ActionForward approvefsa(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
      String mod="";
      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          mod=request.getParameter("mod");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
          
          //get whether checkbox was selected to approve fs10a
          boolean fs10Appr=false;
          if(request.getParameter("fs10Ckbx") !=null)
            fs10Appr = true;
                     
          //save or delete fs10 approval based on checkbox
          fs.updateFS10Approval(userb, grantid, fs10Appr);  
          
          //get the fs10a records to display
          ArrayList allFSRecords = fs.getFSARecords(grantid);   
          request.setAttribute("allFSRecords", allFSRecords);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error FsaAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod+"adminfsa");          
    }
   
      
    public ActionForward updatefsa(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
      String mod="";
      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          mod = request.getParameter("mod");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
          
          ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
          //cast the action form to a BudgetCollectionBean 
          BudgetCollectionBean b = (BudgetCollectionBean) myform;
                    
          int outcome = fs.updateFSARecord(b.getAllAmendRecords(), userb);
          
          //get the fs10a records to display
          ArrayList allFSRecords = fs.getFSARecords(grantid);   
          
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllAmendRecords(allFSRecords);
          request.setAttribute("BudgetCollectionBean", bc);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error FsaAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod+"updatefsa");          
    }
    
    
    public ActionForward addrecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
      String mod="";
      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          mod = request.getParameter("mod");//sa/co/di/lg/fl/al
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
                    
          int outcome = fs.addFSARecord(userb, grantid);
          
          //get the fs10a records to display
          ArrayList allFSRecords = fs.getFSARecords(grantid);   
          
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllAmendRecords(allFSRecords);
          request.setAttribute("BudgetCollectionBean", bc);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error FsaAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod+"updatefsa");          
    }
    
    
    public ActionForward deleterecord(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {   
      HttpSession sess = request.getSession();  
      if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
      String mod="";
      try{
          mod=request.getParameter("mod");
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid=Long.parseLong(grantnum);
                    
          String id = request.getParameter("id");
          int outcome = fs.deleteFS10Record(Integer.parseInt(id));
          
          //get the fs10a records to display
           ArrayList allFSRecords = fs.getFSARecords(grantid);   
           
           BudgetCollectionBean bc = new BudgetCollectionBean();
           bc.setAllAmendRecords(allFSRecords);
           request.setAttribute("BudgetCollectionBean", bc);
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);      
          request.setAttribute("appStatus", asb);                
      }
      catch(Exception e){
        System.out.println("error FsaAction "+e.getMessage().toString());
      }          
      return mapping.findForward(mod+"updatefsa");          
    }
    
   
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT ParticipantAddSearchAction");
        timeout = true;
      }      
      return timeout;
    }
}
