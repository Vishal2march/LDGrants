package emailws;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.EmailHelpBean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class CnNewTemplateAction extends Action{
    
    
    
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {        
      HttpSession sess = request.getSession();      
      
      try{              
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
        EmailHelpBean ehb = (EmailHelpBean) form;
        String emsVars = "$$grantId";//default for all
    
        if(ehb.isManagerName())
          emsVars+= ",$$managerName";          
        if(ehb.isProjectNum())
          emsVars+= ",$$projectNum";
        if(ehb.isAmtapproved())
            emsVars+= ",$$amtApproved";
        if(ehb.isInstName())
            emsVars+=",$$instName";
        if(ehb.isBuildingName())
            emsVars+=",$$buildingName";
        if(ehb.isProjectTitle())
            emsVars+=",$$projectTitle";
          
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());
                      
        int id = ez.draftTemplateAndVars(ehb.getFrom(), "", ehb.getSubject(),ehb.getMessage(), emsVars);
                         
        //get all unsent mail and display
        WorkingTemplateQuery[] wtq=ez.listUnsentMail();
        Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
        request.setAttribute("wtBeans", wtq);  
        
      }catch(Exception e){
        System.out.println("error CnNewTemplateAction "+e.getMessage().toString());
      }      
      return mapping.findForward("success");      
    }
}
