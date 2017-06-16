/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminSaveReview.java
 * Creation/Modification History  :
 *
 * SH   2/5/08      Created
 *
 * Description
 * This action class will route sa/co/di/fl/al/lg admin calls from the checkstatus
 * page to save review and unlock and save approval.  
 *****************************************************************************/
package discretionary;

import construction.ConstructionDBbean;
import construction.SystemAssignBean;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.SubmissionDBbean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminSaveReview extends Action
{

  private SubmissionDBbean sb = new SubmissionDBbean();
  private  DBHandler dbh = new DBHandler();
    
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
     HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
      
      String module, task, unlock ="";
      DBHandler dbh = new DBHandler();
      module = request.getParameter("p");
      
      try{          
          task = request.getParameter("task");
          unlock = request.getParameter("unlockapp"); 
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          String grantnum = (String)sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          AppStatusBean newAsb = (AppStatusBean) form;

          if(task!=null && task.equals("review"))
          {
              //Added if statment to check when unlock button is clicked to unlock initial application 04/24/2016
             if (unlock != null && unlock.equals("Unlock Application")){
                 dbh.unlockApp(grantid, userb);
             } 
             else if (unlock != null && unlock.equals("Unlock Year 1")){
            	 dbh.unlockFinalYear1(grantid, userb);
             }
             else if (unlock != null && unlock.equals("Unlock Amendment")){
            	 dbh.unlockAmendment(grantid, userb);
             }
             else {
            GrantBean gb = dbh.getRecordBean(grantid);                      
            int outcome =dbh.saveAdminReview(newAsb, grantid, userb, gb.getFccode()); 
             }
          }
          else if(task!=null && task.equals("approve"))
          {
              String permiss = "";
              if(module.equals("lg") && userb.isLgadmin())
                permiss = "approve";
              else if(module.equals("cn"))
                permiss = userb.getAdmconstruction();
              else if(module.equals("sa"))
                permiss = userb.getAdminstat();
              else if(module.equals("co"))
                permiss = userb.getAdmincoor();
              else if(module.equals("di"))
                permiss = userb.getAdmindisc();
              else if(module.equals("fl"))
                permiss = userb.getAdminfl();
              else if(module.equals("al"))
                permiss = userb.getAdminal();
              else if(module.equals("staid"))
                permiss = userb.getAdminstat();//stateaid reuses statutory entitlement
                
              if( !permiss.equals("approve"))
                return mapping.findForward("authorize");
              
              //validate if 'decline', then need date and amount declined               
              ActionErrors ae =newAsb.validateDecline(mapping, request);              
              if(ae!=null && (ae.size() >0)){
                  request.setAttribute(Globals.ERROR_KEY, ae); 
                  return (mapping.findForward(module+"checkstatus"));
              }
              
              //save admin approval
              ApprovalsDBbean adb = new ApprovalsDBbean();
              adb.saveAdminApproval(newAsb, grantid, userb);
              
              //insert/remove dasny submit for construct admin
              if(module.equals("cn")){
                //check if dasny submit record exists
                boolean hasSubmit = sb.isDasnySubmitted(grantid);
                
                if(newAsb.isDasnysubmit()==true && !hasSubmit){
                    //button checked & record does not exist ->insert record
                     sb.submitToDasny(grantid, userb);
                }
                else if(newAsb.isDasnysubmit()==false && hasSubmit){
                    //button not checked & but record exists ->delete record
                    sb.deleteDasnySubmission(grantid);
                }                                   
              }
          } 
              
          
          //get grant info to redisplay on page   
          GrantBean gb = dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb);
            
          AppStatusBean asb= dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);          
          
          Vector allSubmissions = sb.getSubmissions(grantid); 
          request.setAttribute("allSubmissions", allSubmissions);
          
          if(gb.getFccode()==86){//for construction only
                //determine if system_assign exists
                ConstructionDBbean cdb = new ConstructionDBbean();
                if (cdb.doesSystemAssignExist(grantid)) {                
                    //get all info about system assignment
                    SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                    request.setAttribute("assignBean", sab);              
                }              
          }
        
      }catch(Exception e){
        System.out.println("error AdminSaveReview "+e.getMessage().toString());
      }    
      return mapping.findForward(module +"checkstatus");
    }
      
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null);
      if (!userID && sess.isNew())
        timeout = true;
           
      return timeout;
    }
    
}