/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UpdateFinalAction.java
 * Creation/Modification History  :
 *
 * SH   9/25/07      Created
 *
 * Description
 * This servlet will route to a save servlet for any budget expenses to be saved. It
 * does not validate expense records because only the 'expSubmitted' field needs to
 * be saved and it is an optional field. 
 *****************************************************************************/
package services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mypackage.BudgetCollectionBean;
import mypackage.OtherExpBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class UpdateFinalAction extends Action 
{
  public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception
  {        
     return mapping.findForward("success");     
  }  
}