package emailws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.DBHandler;
import mypackage.EmailHelpBean;
import mypackage.EmailMassNotifyBean;
import mypackage.EmailNotificationBean;
import mypackage.GrantBean;
import mypackage.RevAssignCollectionBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.StandardTemplate;
import sedems.WorkingTemplateQuery;

public class LitAdminEmail extends DispatchAction
{
  private EmailMassNotifyBean eb = new EmailMassNotifyBean();
  private EmailNotificationBean enb = new EmailNotificationBean();
  
  
  public ActionForward viewgrants(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String fc = request.getParameter("fc");
          String fy = request.getParameter("fy");
          int fccode = Integer.parseInt(fc);
          int fycode = Integer.parseInt(fy);
          String type = request.getParameter("type");
          int stId = 0;
          String appType = "";
          
          if(type.equalsIgnoreCase("Initial"))
          {
            appType = "Approve";
            stId = 482;
          }
          else if(type.equalsIgnoreCase("All"))
          {
            appType = "Denied";
            stId=481;
          }
            
          Vector allGrants = eb.getEmailGrants(fycode, fccode, type, appType);
          request.setAttribute("allGrants", allGrants);
          EmailHelpBean ehb = new EmailHelpBean();
          ehb.setFycode(fycode);
          ehb.setFccode(fccode);
          ehb.setApprovalType(type);
          request.setAttribute("emailHelp", ehb);    
          
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          StandardTemplate st = enb.findStandardTemplate(userb, stId );
          request.setAttribute("stemplate", st);
          
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("viewgrants");        
    }
    
    
    public ActionForward loademail(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
            DBHandler dbh = new DBHandler();
            ArrayList allyears = dbh.dropDownFiscalYears();
            sess.setAttribute("dropDownList", allyears);
            
        }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
        return mapping.findForward("viewgrants");        
      }
    
    
  /**
   * This action will create a new wt for lit approval/denial mass email, and add all
   * recipients matching the appr/deny fy criteria.  Then display wt for editing.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward template(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession();       
      EmailHelpBean ehb = new EmailHelpBean();
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String fc = request.getParameter("fc");
          String fy = request.getParameter("fy");
          int fccode = Integer.parseInt(fc);
          int fycode = Integer.parseInt(fy);
          String type = request.getParameter("type");
          String appType = "";
          
          if(type.equalsIgnoreCase("Initial"))
            appType = "Approve";
          else if(type.equalsIgnoreCase("All"))
            appType = "Denied";
            
          Vector allGrants = eb.getEmailGrants(fycode, fccode, type, appType);
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          ehb.setFccode(fccode);
          ehb.setFycode(fycode);
          ehb.setFrom(request.getParameter("from"));
          ehb.setCc(request.getParameter("cc"));
          
          int newwtid =0;
          if(type.equalsIgnoreCase("Initial"))
             newwtid=eb.processLitInitialApproval(allGrants, userb, ehb);
          else if(type.equalsIgnoreCase("All"))
            newwtid=eb.processLitAppDenied(allGrants, userb, ehb);      
            
          //get all info about wt and display for edit
          EmailHelpBean eb = getUnsentTemplate(userb, newwtid);
          request.setAttribute("emailHelpBean", eb);        
          
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("template");        
    }
    
                
    public ActionForward recipientspage(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    { 
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        
        DBHandler dbh = new DBHandler();
        ArrayList allyears = dbh.dropDownFiscalYears();
        sess.setAttribute("dropDownList", allyears);
        
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("viewrecipients");
    }
    
    
    public ActionForward indivrecipient(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    { 
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        
        DBHandler dbh = new DBHandler();
        ArrayList allyears = dbh.dropDownFiscalYears();
        sess.setAttribute("dropDownList", allyears);
        
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("indivrecipient");
    }
    
    
    
    public ActionForward matchrecipients(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      EmailAssistDBbean edb = new EmailAssistDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String fc = request.getParameter("fc");
        String fy = request.getParameter("fy");
        String wtid = request.getParameter("wtid");
        
        Vector selectgroup = edb.getRecipientsFyFc(Integer.parseInt(fy), Integer.parseInt(fc));       
        selectgroup = edb.getRecipFyFcContacts(Integer.parseInt(fy), Integer.parseInt(fc), selectgroup);
        Collections.sort(selectgroup, GrantBean.InstitutionComparator);
        
        RevAssignCollectionBean rc= new RevAssignCollectionBean(); 
        rc.setAllPotentialGrants(selectgroup);
        rc.setWorkingTemplateId(Integer.parseInt(wtid));
        
        request.setAttribute("AssignCollectionBean", rc);       
                
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("indivrecipient");
    }
    
    
    public ActionForward viewrecipients(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      EmailAssistDBbean edb = new EmailAssistDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String fc = request.getParameter("fc");
        String fy = request.getParameter("fy");
        String atype = request.getParameter("type");
        String wtid = request.getParameter("wtid");
        ArrayList selectgroup=new ArrayList();
        
        if(atype.equalsIgnoreCase("Reviewer"))
          selectgroup = edb.getReviewerRecipGroup("40,42");
        else{
          selectgroup = edb.getRecipientGroup(Integer.parseInt(fy), Integer.parseInt(fc), atype);
          selectgroup = edb.getRecipientAddContact(Integer.parseInt(fy), Integer.parseInt(fc), atype, selectgroup);
          Collections.sort(selectgroup, GrantBean.InstitutionComparator);
        }
        
        request.setAttribute("selectgroup", selectgroup);
        request.setAttribute("id", wtid);
        request.setAttribute("fy", fy);
        request.setAttribute("fc", fc);
        request.setAttribute("atype", atype);
        
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("viewrecipients");
    }
    
    
       
    public ActionForward addrecipients(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      EmailAssistDBbean edb = new EmailAssistDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        UserBean userb = (UserBean)sess.getAttribute("lduser");
        String fc = request.getParameter("fc");
        String fy = request.getParameter("fy");
        String atype = request.getParameter("atype");
        String wtid = request.getParameter("wtid");
        String cc = request.getParameter("cc");
        ArrayList selectgroup=new ArrayList();
        
        if(atype.equalsIgnoreCase("Reviewer"))
          selectgroup = edb.getReviewerRecipGroup("40,42");
        else{
          selectgroup = edb.getRecipientGroup(Integer.parseInt(fy), Integer.parseInt(fc), atype);
          selectgroup = edb.getRecipientAddContact(Integer.parseInt(fy), Integer.parseInt(fc), atype, selectgroup);
        }
         edb.createLogsVarsForRecipients(selectgroup, cc, Integer.parseInt(wtid), userb);
        
        //return to viewunsentmail page
        getUnsentEmails(request, userb);
        
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return mapping.findForward("unsentmail");
    }
            
    //-----------------------------------------------------------------
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT LitAdminEmail");
        timeout = true;
      }      
      return timeout;
    }
    
    public void getUnsentEmails(HttpServletRequest request, UserBean userb)
    {
      try{
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          
          WorkingTemplateQuery[] wtq=ez.listUnsentMail();
          Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
          request.setAttribute("wtBeans", wtq);                 
          
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      
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
          }
        }
        ehb.setEmailLogs(wtq.getEmailLogs());
        ehb.setMessageVars(wtq.getMessagevariables());
      }catch(Exception e){System.out.println("error LitAdminEmail "+e.getMessage().toString());}
      return ehb;
    }
    
}