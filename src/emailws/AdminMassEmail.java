/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminMassEmail.java
 * Creation/Modification History  :
 *
 * SH   8/14/08      Created
 *
 * Description
 * This dispatch action will handle the sa/co/di admin mass mailings for app approved,
 * denied, final approved, pending OSC approval, and provisional approval emails.
 *****************************************************************************/
package emailws;

import java.util.ArrayList;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import mypackage.DBHandler;
import mypackage.EmailHelpBean;
import mypackage.EmailMassNotifyBean;

import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.ReviewerEmailBean;
import mypackage.UserBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import sedems.AuthenticateBean;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class AdminMassEmail extends DispatchAction
{
  //EmailMassNotifyBean eb = new EmailMassNotifyBean();
    
    
    /**
     * This action loads the cp admin page to search for recipients.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadrecips(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");      
          
        try{                    
            DBHandler dbh = new DBHandler();
            ArrayList allyears = dbh.dropDownFiscalYearsDesc();
            sess.setAttribute("dropDownList", allyears);
            
            //pass along the selected wt
            String wtid = (String) request.getParameter("id");
            request.setAttribute("wtid", wtid);
            
        }catch(Exception e){
          System.out.println("error AdminMassEmail "+e.getMessage().toString());
        }
        return mapping.findForward("recipients");      
    }
          
    /*commented out 3/5/10
     * public ActionForward loademail(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        String program="";
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");      
          
        try{                    
            program = request.getParameter("m");
            DBHandler dbh = new DBHandler();
            ArrayList allyears = dbh.dropDownFiscalYears();
            sess.setAttribute("dropDownList", allyears);
            
        }catch(Exception e){
          System.out.println("error AdminMassEmail "+e.getMessage().toString());
        }
        return mapping.findForward("send"+program);      
    }*/
    
  
  /*commented out 3/5/10.  This method would get all matching grants for cp admin
   * (projects appr/denied for fy for fc).  It would list all matching projects, and admin
   * could click to send mail to matches.  
   * public ActionForward listall(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      String program="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");      
        
      try{
      
        UserBean userb = (UserBean) sess.getAttribute("lduser");            
        String fycode = request.getParameter("fycode");   
        String type = request.getParameter("type");
        program = request.getParameter("m");
        String version = "", msg="", appType="";
        
      
        if(type.equalsIgnoreCase("Initial")){
            msg = "an application approved ";
            appType = "Approve";
            version = "Initial";
        }
        else if(type.equalsIgnoreCase("Final")){
          msg = "a final report approved ";
          appType = "Approve";
          version = "Final";
        }
        else if(type.equalsIgnoreCase("OSC")){
          msg = "an intent to award pending OSC approval ";
          appType = "Approve";
          version = "Initial";
        }
        else if(type.equalsIgnoreCase("Prov")){
            msg = "a Provisional Award ";
            appType = "Approve";
            version = "Initial";
        }
        else if(type.equalsIgnoreCase("All")){
          msg = "an application denied ";
          appType = "Denied";
          version = "All";
        }
      
        int fy = Integer.parseInt(fycode);
        int fc = determineFundCode(program);
        Vector allGrants = eb.getEmailGrants(fy, fc, version, appType);
        request.setAttribute("allGrants", allGrants);
        
        request.setAttribute("emailType", msg);
        request.setAttribute("type", type);
        request.setAttribute("fycode", new Integer(fy));              
        
      }catch(Exception e){
        System.out.println("error AdminMassEmail "+e.getMessage().toString());
      }
      return mapping.findForward("listall"+program);      
  }*/
        
    
  /*commented out 3/5/10.  Used for sending cp admin mass mails.  Now using SEDEMSACTION
   * class 'confirmsend' and 'sendwt' dispatch actions instead of this.
   * public ActionForward send(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
      String program="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");      
        
      try{
      
        UserBean userb = (UserBean) sess.getAttribute("lduser");            
        String fycode = request.getParameter("fycode");   
        String type = request.getParameter("type");
        program = request.getParameter("m");
        String version = "", msg="", appType="";
        
      
        if(type.equalsIgnoreCase("Initial")){
            msg = "an application approved ";
            appType = "Approve";
            version = "Initial";
        }
        else if(type.equalsIgnoreCase("Final")){
          msg = "a final report approved ";
          appType = "Approve";
          version = "Final";
        }
        else if(type.equalsIgnoreCase("OSC")){
          msg = "an intent to award pending OSC approval ";
          appType = "Approve";
          version = "Initial";
        }
          else if(type.equalsIgnoreCase("Prov")){
              msg = "a Provisional Award ";
              appType = "Approve";
              version = "Initial";
          }
        else if(type.equalsIgnoreCase("All")){
          msg = "an application denied ";
          appType = "Denied";
          version = "All";
        }
      
        int fy = Integer.parseInt(fycode);
        int fc = determineFundCode(program);
        Vector allGrants = eb.getEmailGrants(fy, fc, version, appType);
               
        //send the appropriate email to all PO or PM
        boolean msgsent;
        if(type.equalsIgnoreCase("Initial"))
          msgsent = eb.processInitialApproval(allGrants, userb);
        else if(type.equalsIgnoreCase("Final"))
          msgsent = eb.processFinalApproval(allGrants, userb);
        else if(type.equalsIgnoreCase("All")) //denied
          msgsent = eb.processAppDenied(allGrants, userb);
        else if(type.equalsIgnoreCase("OSC"))
          msgsent = eb.processCoIntentAward(allGrants, userb);     
        else if(type.equalsIgnoreCase("Prov"))
            msgsent = eb.processDiProvisionalAward(allGrants, userb);
        
      }catch(Exception e){
        System.out.println("error AdminMassEmail "+e.getMessage().toString());
      }
      return mapping.findForward("send"+program);      
  }*/
  
  
  /**
     * This action will create a new wt for cp admin based on the cp program and email 
     * type chosen.  Email types include initial/final approve/deny emails, provisional/osc
     * emails.  Creates wt instance of st; then display template for editing
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward newtemplate(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        int stid =0, wtid=0;
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{
          String emailtype = request.getParameter("type");
          String program = request.getParameter("fccode");
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          if(emailtype.equalsIgnoreCase("final"))
              stid=405;
          else if(emailtype.equalsIgnoreCase("prov"))
              stid=701;
          else if(emailtype.equalsIgnoreCase("custom"))
              stid=0;//doesn't use a st, will forward to blank template
          else if(emailtype.equalsIgnoreCase("osc"))
              stid=407;
          else if(emailtype.equalsIgnoreCase("deny"))
          {
              if(program!=null && program.equals("5"))//discretionary
                stid=865;
              else
                stid=406;//statutory/coordinated
          }
          else if(emailtype.equalsIgnoreCase("initial"))
          {
              if(program!=null &  program.equals("5"))//discretionary
                stid=864;
              else
                stid=404;//statutory/coordinated
          }
          else if(emailtype.equalsIgnoreCase("participate")){//new 3/20/13 adding reviewer emails
            
              stid=443;//used for both co/di
          }
          else if(emailtype.equalsIgnoreCase("partremind")){//new 3/20/13 adding reviewer emails
            
              stid=444;//used for both co/di
          }
          else if(emailtype.equalsIgnoreCase("assignment")){//new 3/20/13 adding reviewer emails
            
              stid=442;//used for both co/di
          }
          else if(emailtype.equalsIgnoreCase("staidInitial")){//new 12/1/14 adding state aid emails
              
              stid=1405;
          }
          else if(emailtype.equalsIgnoreCase("staidFinal")){//new 12/1/14 adding state aid emails
            
              stid=1406;
          }
          
          
          //create the wt from the st
          if(stid!=0)
            wtid =ez.draftStandardEmail(stid, "barbara.lilley@nysed.gov", "");
             
          //get the wt and display for editing
          EmailHelpBean eb = getUnsentTemplate(userb, wtid, userb.getAuthBean());
          request.setAttribute("emailHelpBean", eb);  
          
        }catch(Exception e){
          System.out.println("error AdminMassEmail "+e.getMessage().toString());
        }
        return mapping.findForward("edittemplate");      
    }
    
    
    /**
     * This action used for cp admin to search for recipient group (PM of appr/deny projects
     * for fy/fc).  Gets PM or PO based on fc. Display in actionform.
     * modified 3/21/13 to add cp reviewer recipient group
     * modified 12/2/14 to add stateaid (cjh/nyhs) recipient - sedref ceo 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchrecips(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");      
        ReviewerEmailBean reb = new ReviewerEmailBean();
          
        try{ 
          String fycode = request.getParameter("fy");   
          String fccode = request.getParameter("fc");
          String type = request.getParameter("type");  
          String wtid = request.getParameter("wtid");
          request.setAttribute("wtid", wtid);
          int fy = Integer.parseInt(fycode);
          int fc = Integer.parseInt(fccode);
                  
         EmailAssistDBbean edb = new EmailAssistDBbean();
         ArrayList selectgroup = new ArrayList();
         
         //based on recipient type - reviewer or projectmanager
         if(type!=null && !type.equals("")){
             
             if(type.equalsIgnoreCase("reviewer")){   //ALL ACTIVE REVIEWERS
               
                selectgroup = reb.getReviewerRecipGroup(fc);
             }
             else if(type.equalsIgnoreCase("assignrev")){   //REVIEWERS ASSIGNED TO PROJECT
             
                selectgroup = reb.getReviewerAssignRecipGroup(fy, fc);
             }
             else{   //PROJECTMANAGER EMAILS
             
               if(fc==5)//discretionary-send to PM
                  selectgroup = edb.getRecipientGroup(fy, fc, type);
               else if(fc==20){
                                      
                    //stateaid cjh/nyhs
                    selectgroup = edb.getRecipientSedrefCeo(fy, fc, type);                   
               }
               else//statutory/coord - send to PO from sedref
                  selectgroup = edb.getRecipientPresOfficer(fy, fc, type);
             }
         }          
          
         RevAssignCollectionBean rc = new RevAssignCollectionBean();
         rc.setAllPotentialGrants(selectgroup);
         rc.setWorkingTemplateId(Integer.parseInt(wtid));
         rc.setFycode(fy);
         rc.setFccode(fc);
         rc.setApprovalType(type);
         request.setAttribute("AssignCollectionBean", rc);
            
        }catch(Exception e){
          System.out.println("error AdminMassEmail "+e.getMessage().toString());
        }
        return mapping.findForward("recipients");      
    }
    
    
    /**
     * This action is used by cp admin to add the selected recipients to template. Creates
     * emaillog for each rec. with any variable substitution. 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addrecips(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");      
          
        try{ 
            EmailAssistDBbean edb = new EmailAssistDBbean();          
            UserBean userb = (UserBean)sess.getAttribute("lduser");
            
            ActionForm myform = (ActionForm) request.getAttribute("AssignCollectionBean");
            RevAssignCollectionBean rc = (RevAssignCollectionBean) myform;  
            
            //modified 3/21/13 - to handle both reviewer and projmanager recip groups            
            if(rc.getApprovalType().equalsIgnoreCase("reviewer"))
            {
                //REVIEWER RECIP GROUP for participation emails
                if(rc.getAllPotentialGrants()!=null){
                    edb.createCpReviewerVars(rc.getAllPotentialGrants(), rc.getEmailAddress(), 
                                             rc.getWorkingTemplateId(), userb);                     
                }
            }
            else if(rc.getApprovalType().equalsIgnoreCase("assignrev"))
            {
                //REVIEWER RECIP GROUP for assignment emails
                if(rc.getAllPotentialGrants()!=null){
                    edb.createCpReviewerAssignVars(rc.getAllPotentialGrants(), rc.getEmailAddress(), 
                                      rc.getWorkingTemplateId(), userb, rc.getFycode(), rc.getFccode(), rc.getDuedate());
                }   
                
                //set the review due date to fund_record so rev's are locked out after due date
                ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();  
                int result = rdb.setReviewDueDate(rc.getDuedate(), rc.getFycode(), rc.getFccode());
            }
            else{
                //PROJMANAGER RECIP GROUP
                if(rc.getAllPotentialGrants()!=null) {              
                     edb.createCpRecipientsVars(rc.getAllPotentialGrants(), 
                           rc.getEmailAddress(), rc.getWorkingTemplateId(), userb);
                 }                  
            }            
                      
            
        }catch(Exception e){
          System.out.println("error AdminMassEmail "+e.getMessage().toString());
        }
        return mapping.findForward("unsentmail");      
    }
        
    
  public boolean checkSessionTimeout(HttpSession sess)
  {
    boolean timeout = false;
    //check for session timeout
    boolean userID = (sess.getAttribute("lduser") != null);
    if (!userID && sess.isNew()){      
      timeout = true;
    }      
    return timeout;
  }
  
  
  /*commented out 3/5/10
   * public int determineFundCode(String program)
  {
    int fccode=0;
    try{
      if(program.equals("sa"))
        fccode=6;
      else if(program.equals("co"))
        fccode=7;
      else if(program.equals("di"))
        fccode=5;      
      
    }catch(Exception e){System.out.println("error determineFundCode "+e.getMessage());}
    return fccode;
  }*/
  
  
  
    public EmailHelpBean getUnsentTemplate(UserBean userb, int id, AuthenticateBean ab)
    {
      EmailHelpBean ehb = new EmailHelpBean();
      try{       
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), ab);
            
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
              if(mv.getName().equalsIgnoreCase("$$reviewerName"))
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
      }catch(Exception e){System.out.println("error AdminMassEmail "+e.getMessage().toString());}
      return ehb;
    }
  
}
