/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  CnAddRecipientAction.java
 * Creation/Modification History  : *
 * SH   3/26/12     Created
 *
 * Description
 * This action is used on cn admin pagae to add recipients to a workingtemplate.
 * It will add each email address chosen, and any variables needed by the wt. 
 *****************************************************************************/
package emailws;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.RevAssignCollectionBean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class CnAddRecipientAction extends Action{
    
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception
      {        
        HttpSession sess = request.getSession();        
        
        try{              
          boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
          if (!userID && sess.isNew())         
            mapping.findForward("timeout");
           
          EmailAssistDBbean edb = new EmailAssistDBbean();          
          UserBean userb = (UserBean)sess.getAttribute("lduser");
          
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());
          RevAssignCollectionBean rc = (RevAssignCollectionBean) form;   
          
          if(rc.getAllPotentialGrants()!=null) {              
              boolean mailsent = edb.createCnRecipientsVars(rc.getAllPotentialGrants(), 
                    rc.getEmailAddress(), rc.getWorkingTemplateId(), userb);                              
          }          
                  
          //return to unsent mail page          
          WorkingTemplateQuery[] wtq=ez.listUnsentMail();
          Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
          request.setAttribute("wtBeans", wtq);                 
           
      }catch(Exception e){
        System.out.println("error CnAddRecipientAction "+e.getMessage().toString());
      }  
      return mapping.findForward("success");
    }
    
}
