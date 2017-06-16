/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AddIndivRecipient.java
 * Creation/Modification History  : *
 * SH   12/30/09     Created
 *
 * Description
 * This action is used for lit admin emails - to add individual email addresses
 * to the template.
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

public class AddIndivRecipient extends Action{

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
          RevAssignCollectionBean rc = (RevAssignCollectionBean) form;          
          ArrayList addrecips = new ArrayList();
          
          if(rc.getAllPotentialGrants()!=null) {
              for(int i=0; i<rc.getAllPotentialGrants().size(); i++) {
                  GrantBean g = (GrantBean) rc.getAllPotentialGrants().get(i);
                  if(g.isAssignpanel())
                    addrecips.add(g);
              }
          }
          
          edb.createLogsVarsForRecipients(addrecips, rc.getEmailAddress(), 
                                          rc.getWorkingTemplateId(), userb);
          
          //return to viewunsentmail page
          //getUnsentEmails(request, userb);
           SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
           
           WorkingTemplateQuery[] wtq=ez.listUnsentMail();
           Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
           request.setAttribute("wtBeans", wtq);         
      
      
      }catch(Exception e){
        System.out.println("error AddIndivRecipient "+e.getMessage().toString());
      }  
      return mapping.findForward("success");
    }
}