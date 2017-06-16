package services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import mypackage.BudgetCollectionBean;

import mypackage.RevAssignCollectionBean;

import mypackage.SupplyBean;

import mypackage.UserBean;
import mypackage.VendorBean;

import mypackage.VendorDBbean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VendorQuoteAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception
      {      
        HttpSession s = request.getSession();
        ActionErrors ae = null;     
        VendorDBbean vdb = new VendorDBbean();
          
        try {
          UserBean userb = (UserBean)s.getAttribute("lduser");
          RevAssignCollectionBean allItems = (RevAssignCollectionBean) form;
       
          // get the list of vq from the form
          List venList = allItems.getAllVendorRecords();
          if(venList!=null){              
     
                for(int i=0; i<venList.size(); i++)
                {
                  VendorBean v = (VendorBean)venList.get(i);   
                  ae = v.validateQuote(mapping, request);//validate() returns ActionErrors
                  
                  if(ae !=null && (ae.size() > 0))
                  {
                    request.setAttribute(Globals.ERROR_KEY, ae); 
                    return (mapping.findForward("fail"));
                  }                       
                }   
                
                
                //if validation passed, then insert/update the quotes
                 for(int i=0; i<venList.size(); i++)
                 {
                   VendorBean v = (VendorBean)venList.get(i);   
                   if(v.getVendorquoteId()==0)
                      vdb.insertVendorQuote(v, userb);
                    else
                        vdb.updateVendorQuote(v, userb);
                 }
          }
            
        //get all quotes and redisplay
        String grantnum = (String) s.getAttribute("grantid");
        ArrayList allquotes = vdb.getVendorQuotesForGrant(Long.parseLong(grantnum));
        RevAssignCollectionBean rc = new RevAssignCollectionBean();
        rc.setAllVendorRecords(allquotes);
        request.setAttribute("AssignCollectionBean", rc);           
            
        }catch(Exception e)  {
          System.out.println("error VendorQuoteAction "+e.getMessage().toString());
          return (mapping.findForward("fail"));
        }
        return mapping.findForward("success");
    }

}
