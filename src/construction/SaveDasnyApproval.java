package construction;

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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveDasnyApproval extends Action{
    
    
    
       
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception
      {
       HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
        
        DBHandler dbh = new DBHandler();
         
        try{          
            UserBean userb = (UserBean) sess.getAttribute("lduser");
            String grantnum = (String)sess.getAttribute("grantid");
            long grantid = Long.parseLong(grantnum);
            
            AppStatusBean newAsb = (AppStatusBean) form;
            
            //verify user permissions
            if(!userb.isDasnyReviewer())
                return mapping.findForward("authorize");
                
                
            //insert/remove dasny approval, bondcouncil approval
             ApprovalsDBbean adb = new ApprovalsDBbean();
            
              //check if dasny appr record exists
              boolean hasAppr = adb.isDasnyApproval(grantid);
              
              if(newAsb.isDasnyapproved()==true && !hasAppr){
                  //button checked & record does not exist ->insert record
                   adb.insertDasnyApproval(grantid, userb);
              }
              else if(newAsb.isDasnyapproved()==false && hasAppr){
                  //button not checked & but record exists ->delete record
                  adb.deleteDasnyApproval(grantid);
              }     
              
              //check if bondcouncil record exists
              boolean hasbond = adb.isDasnyBondComplete(grantid);
              
              if(newAsb.isBondcomplete()==true && !hasbond){
                  //btn checked, no record -> so insert record
                  adb.insertBondCouncilApproval(grantid, userb);
              }
              else if(newAsb.isBondcomplete()==false && hasbond){
                  //btn not checked, record exists -> so delete record
                  adb.deleteBondCouncilApproval(grantid);
              }
          
          //reload the dasny checklist page          
           GrantBean gb = dbh.getRecordBean(grantid);
           sess.setAttribute("thisGrant", gb); 
           
           AppStatusBean asb= dbh.getApplicationStatus(grantid);
           request.setAttribute("appStatus", asb);
                       
        }catch(Exception e){
          System.out.println("error AdminSaveReview "+e.getMessage().toString());
        }    
        return mapping.findForward("success");
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
