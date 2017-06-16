/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  PersonalUpdateAction.java
 * Creation/Modification History  :
 *
 * SH   9/25/07      Created
 *
 * Description
 * This servlet will validate all personal budget records on the page.  If successful
 * it forwards to save, otherwise returns and displays error message. 
 *****************************************************************************/
package services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mypackage.BudgetCollectionBean;
import mypackage.PersonalBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PersonalUpdateAction extends Action
{
   public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception
    {    
        ActionErrors ae = null;     
        BudgetCollectionBean allItems = (BudgetCollectionBean) form;
     
        //get the list of expenses from the form
        List expList = allItems.getAllPersRecords();
            
        try{
            if(expList !=null)
            {
                int size = expList.size();
       
                for(int i=0; i<expList.size(); i++)
                {
                  PersonalBean pb = (PersonalBean)expList.get(i);   
                    
                  if(!pb.isCategoryTotal())//SH8/30/13 don't validate admin award records
                      ae = pb.validate(mapping, request);//validate() returns ActionErrors
                  
                  
                  if(ae !=null && (ae.size() > 0))
                  {
                    request.setAttribute(Globals.ERROR_KEY, ae); 
                    
                    if(pb.getModule()!=null && 
                        (pb.getModule().equals("lg") || pb.getModule().equals("al") || pb.getModule().equals("fl") ))
                      return (mapping.findForward("fail"+pb.getTypeCode()));//special mapping for lg/lit
                    else
                      return (mapping.findForward("fail"));
                  }                       
                }   
                
                //if we get here; all validation has passed
                return (mapping.findForward("success"));
            
            }
            else
            {
              return (mapping.findForward("fail"));//no personal records so nothing to save              
            }
                        
        }catch(Exception e)
        {
          System.out.println("error PersonalUpdateAction "+e.getMessage().toString());
          return (mapping.findForward("fail"));
        }
        
   
     
  }
  
  
}