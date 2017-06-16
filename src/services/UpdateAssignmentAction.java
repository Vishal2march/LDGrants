package services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerAssignBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateAssignmentAction extends Action
{
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception
    {
      HttpSession sess = request.getSession();
      ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();
        
      try{
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        RevAssignCollectionBean allItems = (RevAssignCollectionBean) form;
     
        // get the list of assignment records 
        List assignList = allItems.getAllAssignRecords();   
        
        if(assignList!=null)
        {
            for(int i=0; i<assignList.size(); i++)
            {
              ReviewerAssignBean rab = (ReviewerAssignBean)assignList.get(i);   
              
              if(rab.isRevassign() && rab.getAssignid()==0)              
                rdb.assignReviewer(rab.getGraid(), rab.getRevid(), userb); //assign reviewer              
              else if(!rab.isRevassign() && rab.getAssignid()>0)                    
                rdb.deleteRevAssign(rab.getAssignid());//delete assignment              
            }
        }
        
        request.removeAttribute("AssignCollectionBean");//to prevent displaying form again
            
      }catch(Exception e){
        System.out.println("error UpdateAssignmentAction "+e.getMessage().toString());
      }
      return mapping.findForward("success");
    }
  
}