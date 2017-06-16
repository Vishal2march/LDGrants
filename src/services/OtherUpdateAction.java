/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  OtherUpdateAction.java
 * Creation/Modification History  :
 *
 * SH   9/25/07      Created
 *
 * Description
 * This servlet will handle validation for all other expense records to be saved. If successful
 * save to db otherwise return to page and display error message. 
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

public class OtherUpdateAction extends Action 
{
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception
    {
        
      ActionErrors ae = null; 
      BudgetCollectionBean allItems = (BudgetCollectionBean) form;
   
      // get the list of expenses from the form
      List expList = allItems.getAllExpRecords();
          
      try
      {
        if(expList!=null)
        {
              int size = expList.size();
              for(int i=0; i<expList.size(); i++)
              {
                OtherExpBean ob = (OtherExpBean)expList.get(i);   
                ae = ob.validate(mapping, request);//validate() returns ActionErrors
                
                if(ae !=null && (ae.size() > 0))
                {
                  request.setAttribute(Globals.ERROR_KEY, ae); 
                  return (mapping.findForward("fail"));
                }                     
              }   
              
              //if we get here; all validation has passed
              return (mapping.findForward("success"));      
        }
        else
        {
          return (mapping.findForward("fail"));
        }
        
        
      }catch(Exception e)
      {
        System.out.println("error OtherUpdateAction "+e.getMessage().toString());
        return (mapping.findForward("fail"));
      }
     
     
     
     
  }  
}