/*******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  Oracle10g JDeveloper
 * Name of the file               :  GenericUpdateAction.java
 * Creation/Modification History  :
 * shusak 4/3/07     Created  for PRHS, modified for LDGrants
 * 
 *
 * Description
 * This class is an action mapping class that is used for the struts validation done
 * in the struts-config file.  If there are no missing values in the action form, then this
 * class will send the action to the success page.
*******************************************************************************/
package mypackage;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public class GenericUpdateAction extends Action
{
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception{    
     
 
    return(mapping.findForward("success"));  
    
  }
}