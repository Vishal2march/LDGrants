/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RevParticipationAction.java
 * Creation/Modification History  : *
 * SH   3/18/09      Created
 *
 * Description
 * This action is used to add/update participation record for co/di/lg reviewers.
 * This action calls validate on participation form bean - the struts-config 
 * mapping does not.
 *****************************************************************************/
package reviewers;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.ReviewAssignMaxBean;
import mypackage.ReviewerBean;
import mypackage.ReviewerDBbean;
import mypackage.UserBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RevParticipationAction extends Action
{
  
  public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
    
      HttpSession sess = request.getSession();
      ReviewerDBbean rdb = new ReviewerDBbean();
      String program = "";
      
      try{         
          if(checkSessionTimeout(sess))
            return mapping.findForward("timeout");
            
          //get the form and validate data
          ReviewAssignMaxBean rab = (ReviewAssignMaxBean) form;
          program = rab.getModule();
          
          //***************************************
          ActionErrors ae = rab.validate(mapping, request);
          if(ae !=null && (ae.size() > 0))
          {
            request.setAttribute(Globals.ERROR_KEY, ae); 
            return mapping.findForward("fail"+program); 
          }
          //******************************************
         
           //get the reviewer that is currently logged in
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          long revid = userb.getRevid();
            
          rab.setRevid(revid);
          rab.setUserid(userb.getUserid());
               
          if(rab.getId()>0)
          {
            //update the max assign record
            int outcome = rdb.updateReviewerMaxAssign(rab);
          }
          else if(rab.getId()==0)
          {
            //insert a new maxassign record
            int outcome = rdb.addReviewerMaxAssign(rab);
          }
              
          //get all assign_max records and redisplay
          Vector allRecords = rdb.getGrantAssignMax(revid);
          request.setAttribute("allAccepted", allRecords);
      
      }catch(Exception e){
        System.out.println("error RevParticipationAction "+e.getMessage().toString());
      }    
      return mapping.findForward("success"+program);    
    }
  
  
  
//----------------------------------------------------------------------  
   public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        timeout = true;
      }      
      return timeout;
    }  
}