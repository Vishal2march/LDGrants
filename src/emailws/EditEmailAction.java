/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  EditEmailAction.java
 * Creation/Modification History  : 
 * SH   12/30/09     Created
 *
 * Description
 * This action is used for cp/lg/cn admin to update an email template. 
 *****************************************************************************/
package emailws;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.EmailHelpBean;
import mypackage.UserBean;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sedems.AuthenticateBean;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class EditEmailAction extends Action
{
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {        
      HttpSession sess = request.getSession();
      String module="";
      
      try{              
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
          
        EmailHelpBean ehb = (EmailHelpBean) form;
        module = ehb.getProgram();
        System.out.println(module);
        ActionErrors aerrors =ehb.validate(mapping, request);
        if(aerrors!=null && aerrors.size()>0)
        {
          request.setAttribute(Globals.ERROR_KEY, aerrors); 
          return (mapping.findForward("template"));
        }
    
                 
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        AuthenticateBean ab=null;
        if(module!=null && module.equals("lg"))
            ab =userb.getArchivesAuthBean();
        else if(module!=null && module.equals("cn"))
            ab = userb.getConstructionAuthBean();
        else
            ab =userb.getAuthBean();
            
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), ab);
        int id=0;
        String emsVars = "$$grantId";
        
        if(ehb.getWtid()==0){
            //create a new wt that is not based on a st
            id=ez.draftTemplateAndVars(ehb.getFrom(), "", ehb.getSubject(),ehb.getMessage(), emsVars);
        }else{
          //do update wt and vars ez method: DOES NOT UPDATE MVARS
          id=ez.updateEmail(ehb.getFrom(), "", ehb.getMessage(), ehb.getSubject(), ehb.getWtid());
        }
        
        //get updated wt and redisplay
        EmailHelpBean eb = getUnsentTemplate(userb, id);
        eb.setProgram(module);
        request.setAttribute("emailHelpBean", eb);      
        
      }catch(Exception e){
        System.out.println("error EditEMailAction "+e.getMessage().toString());
      }      
      return mapping.findForward("template");      
    }
    
    
      
    public EmailHelpBean getUnsentTemplate(UserBean userb, int id)
    {
      EmailHelpBean ehb = new EmailHelpBean();
      try{       
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
            
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(id);
        ehb.setFrom(wtq.getFromEmail());
        ehb.setSubject(wtq.getSubject());
        ehb.setMessage(wtq.getMessage());
        ehb.setWtid(wtq.getId());
        MessageVarQuery mvq[] = wtq.getMessagevariables();
        if(mvq!=null)
        {
          for(int i=0; i<mvq.length; i++)
          {
            MessageVarQuery mv = mvq[i];
            if(mv.getName().equalsIgnoreCase("$$managerName"))
            {
              ehb.setManagerName(true);
              ehb.setManagerNameId(mv.getId());
            }
            if(mv.getName().equalsIgnoreCase("$$projectNum"))
            {
              ehb.setProjectNum(true);
              ehb.setProjectNumId(mv.getId());
            }
              if(mv.getName().equalsIgnoreCase("$$grantId")) {
                  ehb.setGrantNum(true);
                  ehb.setGrantId(mv.getId());
              }
              if(mv.getName().equalsIgnoreCase("$$amtApproved")) {
                  ehb.setAmtapproved(true);
                  ehb.setAmtapprovedId(mv.getId());
              }
              if(mv.getName().equalsIgnoreCase("$$program")) {
                  ehb.setProgramName(true);
                  ehb.setProgramId(mv.getId());
              }
              if(mv.getName().equalsIgnoreCase("$$fiscalYear")) {
                  ehb.setFiscalYear(true);
                  ehb.setFiscalYearId(mv.getId());
              }
          }
        }
        ehb.setEmailLogs(wtq.getEmailLogs());
        ehb.setMessageVars(wtq.getMessagevariables());
      }catch(Exception e){System.out.println("error EditEmailAction "+e.getMessage().toString());}
      return ehb;
    }   
}