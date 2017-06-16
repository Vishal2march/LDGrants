/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SaveReviewerAction.java
 * Creation/Modification History  :
 *
 * SH   3/11/08      Created
 *
 * Description
 * This action class is used to add or update a DI/CO/FL/AL/LG reviewer by admin. ReviewerBean
 * validates all required fields, add/update depending on rev id attribute. 
 *****************************************************************************/
package discretionary;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.ReviewerBean;
import mypackage.ReviewerDBbean;
import mypackage.UserBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveReviewerAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HttpSession sess = request.getSession();
        int outcome=0;
        String program="";
        
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
        try {      
          //cast the action form to a ReviewerBean
          ReviewerBean rb = (ReviewerBean) form;          
          program = rb.getGrantprogram();
        
          //***************************************************
          ActionErrors ae =rb.validate(mapping, request);
          if(ae!=null && ae.size()>0)
          {
            request.setAttribute(Globals.ERROR_KEY, ae); 
            return (mapping.findForward("fail"+program));
          }
          //*****************************************************        
        
          UserBean userb = (UserBean) sess.getAttribute("lduser");
        
          ReviewerDBbean rdb = new ReviewerDBbean();
          if(rb.getRevid()!=0)
            outcome = rdb.updateReviewer(rb, userb, true);
          else
            outcome = rdb.addReviewer(rb, userb);
          
          request.setAttribute("saveStatus", new Integer(outcome));
          request.setAttribute("newReviewer", rb);
                            
        }catch(Exception e){
          System.out.println("error SaveReviewerAction "+e.getMessage().toString());
          return mapping.findForward("error");
        }                
        return mapping.findForward("success"+program);
    }
}