/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RecipientsAction.java
 * Creation/Modification History  : *
 * SH   12/30/09     Created
 *
 * Description
 * This action is used on lgrmif admin pagae to add recipients to a workingtemplate.
 * It will add each email address chosen, and any variables needed by the wt. 
 * Note: if fc wants decision notes attachment- add code here. 
 *****************************************************************************/
package emailws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.GrantBean;
import mypackage.RevAssignCollectionBean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class RecipientsAction extends Action {
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
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getArchivesAuthBean());
          RevAssignCollectionBean rc = (RevAssignCollectionBean) form;   
          
          if(rc.getAllPotentialGrants()!=null) {              
              boolean needdecision = edb.createLgRecipientsVars(rc.getAllPotentialGrants(), 
                    rc.getEmailAddress(), rc.getFycode(), rc.getWorkingTemplateId(), userb);
                          
              /*if(needdecision){//either approve/deny email, so attach decision notes for each EL
                  System.out.println("handling attachments");
                  //edb.handleDecisionAttachments(rc.getWorkingTemplateId(), userb);
              }*/                           
          }          
                  
          //return to unsent mail page          
          WorkingTemplateQuery[] wtq=ez.listUnsentMail();
          Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
          request.setAttribute("wtBeans", wtq);                 
           
      }catch(Exception e){
        System.out.println("error RecipientsAction "+e.getMessage().toString());
      }  
      return mapping.findForward("success");
    }
}
