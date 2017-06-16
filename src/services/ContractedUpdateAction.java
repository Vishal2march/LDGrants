/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ContractedUpdateAction.java
 * Creation/Modification History  :
 *
 * SH   9/25/07      Created
 *
 * Description
 * This servlet will handle validation for saving contracted budget records.  If
 * validation successful then forward to servlet to save to db.  Otherwise return
 * to page and display error message. 
 *****************************************************************************/
package services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mypackage.BudgetCollectionBean;
import mypackage.ContractedBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ContractedUpdateAction extends Action
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
        List expList = allItems.getAllContractRecords();
                        
        try{
             if(expList !=null)
             {
                  int size = expList.size();
            
                  for(int i=0; i<expList.size(); i++)
                  {
                      ContractedBean cb = (ContractedBean)expList.get(i);   
                      
                      if(!cb.isCategoryTotal())
                        ae = cb.validate(mapping, request);//validate() returns ActionErrors
                      
                      if(ae !=null && (ae.size() > 0))
                      {
                        request.setAttribute(Globals.ERROR_KEY, ae); 
                        if(cb.getModule()!=null && cb.getModule().equals("lg"))
                          return (mapping.findForward("fail"+cb.getTypeCode()));//for lgrmif
                        else
                          return (mapping.findForward("fail"));
                      }               
                  }               
              
                  //if we get here; all validation has passed
                  return (mapping.findForward("success"));
             }
             else
             {
               return (mapping.findForward("fail"));//no contracted records to save               
             }
             
        
        }catch(Exception e)
        {   System.out.println("error ContractedUpdateAction "+e.getMessage().toString());  
            return (mapping.findForward("fail"));
        }
          
    }
}