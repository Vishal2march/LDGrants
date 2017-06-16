/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UpdateRevCommentAction.java
 * Creation/Modification History  :
 *
 * SH   1/18/08      Created
 *
 * Description
 * This action is used by CO admin to update the CO reviewer comment. Comment
 * is received as a CommentBean.  2/14/08 now used for DI admin rev comment. 
 *****************************************************************************/
package services;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.CommentBean;
import mypackage.DBHandler;
import mypackage.ReviewerAssignDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateRevCommentAction extends Action
{
  
   public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
       HttpSession sess = request.getSession();
       String finalTarget="";
    
       //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())
        {      
          request.setAttribute("sessionTime", "true");
          return mapping.findForward("timeout");
        }
        
        
        try{
             CommentBean cb = (CommentBean) form;
             UserBean userb  = (UserBean) sess.getAttribute("lduser");
             
             ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
             int outcome = rab.updateRevComment(userb, cb);
             
             //get all comments/scores for grant and return to page            
             String grantnum = (String) sess.getAttribute("grantid");
             long grantid = Long.parseLong(grantnum);
             
            Vector allRevs= new Vector();
             if(cb.getModule()!=null && cb.getModule().equals("co"))
             {
                 DBHandler dbh = new DBHandler();
                 int fy = dbh.getFiscalYearForGrant(grantid);
                 allRevs = rab.getScoresForCoGrant(grantid, fy);                
             }
             else if(cb.getModule()!=null && cb.getModule().equals("di"))
                 allRevs = rab.getScoresForDiWeighted(grantid);
                             
             Vector allComments = rab.getCommentsForAdmin(grantid);
              
              request.setAttribute("allAssigned", allRevs);
              request.setAttribute("allComments", allComments);
                        
                        
            if(cb.getModule()!=null && cb.getModule().equals("co"))  
            {
              finalTarget="coord";
              request.setAttribute("module", "co");
            }
            else if(cb.getModule()!=null && cb.getModule().equals("di"))
            {
              finalTarget="disc";
              request.setAttribute("module", "di");
            }
            
        
        }catch(Exception e)
        {   System.out.println("error UpdateRevComment "+e.getMessage().toString());  
            return (mapping.findForward("fail"));
        }
        
        return mapping.findForward(finalTarget);   
    }
}