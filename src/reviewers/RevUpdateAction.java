/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RevUpdateAction.java
 * Creation/Modification History  :
 *
 * SH   3/11/08      Created
 *
 * Description
 * This action class is used to update co/di/li/lg reviewer contact info. This class
 * validates all required fields, the reviewer cannot update grantprogram, username.
 * LG reviewers can update thier expertise, and ssn.
 *****************************************************************************/
package reviewers;
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

public class RevUpdateAction extends Action
{
   public ActionForward execute( ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception{    
     
     HttpSession sess = request.getSession();   
     String program="";
     
    //check for session timeout
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew())
      return mapping.findForward("timeout");
        
    
    try{                
        //get the entries the user made on the actionform 
        ActionForm myform = (ActionForm) request.getAttribute("ReviewerBean");
        //cast the action form to a ReviewerBean 
        ReviewerBean rb = (ReviewerBean) myform;
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
        int outcome = rdb.updateReviewer(rb, userb, false);
        
        request.setAttribute("saveStatus", new Integer(outcome));
        request.setAttribute("newReviewer", rb);
    
    } catch(Exception e){
      System.out.println("error RevUpdateAction "+ e.getMessage().toString());
    }
  
    return(mapping.findForward("success"+program));    
    }
}