package emailws;
import coordinated.AdminDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.ApprovalsDBbean;
import mypackage.BudgetDBHandler;
import mypackage.CommentBean;
import mypackage.CommentDBbean;
import mypackage.DBHandler;
import mypackage.EmailHelpBean;
import mypackage.EmailNotificationBean;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import sedems.AuthenticateBean;
import sedems.EmailLogQuery;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class SedemsAction extends DispatchAction
{
  private EmailNotificationBean eb = new EmailNotificationBean();

   public ActionForward emails(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          module = request.getParameter("m");
          
           AuthenticateBean ab;
          if(module!=null && module.equals("lg"))
            ab = userb.getArchivesAuthBean();
          else if(module!=null && module.equals("cn"))
            ab = userb.getConstructionAuthBean();
          else
            ab = userb.getAuthBean();
            
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), ab);
          EmailLogQuery[] elq =ez.searchEmailVars(grantnum);
          Collections.sort(Arrays.asList(elq), EmailLogQuery.DateSentComparator);
          request.setAttribute("searchResults", elq);
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module +"emails");        
    }
    
    
    public ActionForward revsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          module = request.getParameter("m");
          String revid = request.getParameter("revid");
          
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          EmailLogQuery[] elq =ez.searchEmailVars(revid);
          Collections.sort(Arrays.asList(elq), EmailLogQuery.DateSentComparator);
          request.setAttribute("searchResults", elq);
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module +"rev");        
    }
    
    
    public ActionForward loadsearch(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          module = request.getParameter("m");
          String fundcodes = "";
          
          if(module.equals("di"))
             fundcodes="5";
           else if(module.equals("co"))
             fundcodes="7";
           else if(module.equals("li"))
             fundcodes="40,42";
           
           ReviewerDBbean rdb = new ReviewerDBbean();
           Vector allrevs = rdb.getAllReviewers(fundcodes);  
           sess.setAttribute("allReviewers", allrevs);
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module +"rev");        
    }
    
    
    public ActionForward comment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String module="";
      CommentDBbean cdb = new CommentDBbean();
      
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String commid = request.getParameter("id");
          module = request.getParameter("m");
          
          CommentBean cb = cdb.getComment(Long.parseLong(commid));   
          cb.setModule(module);
          request.setAttribute("commentBean", cb);       
                    
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module +"confirm");        
    }
    
    
    
    
    
  /**
   * Method will create a cp approve/deny/finalrpt email from admin to PO/PM.
   * Used for sa/co/di admin.  Creates wt, el, recipients, and variables.  Then displays
   * wt for editing. DOES NOT SEND EMAIL 5/21/09
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
      String module="";
      ApprovalsDBbean adb = new ApprovalsDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          UserBean userb = (UserBean) sess.getAttribute("lduser");
          module = request.getParameter("m");
          String grantnum = request.getParameter("id");
          long grantid = Long.parseLong(grantnum);
          String emailtype = request.getParameter("etype");
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
          
          int wtid=0;
          if(emailtype.equals("initial"))
            {
              boolean hasInitial = adb.isInitialAppApproved(grantid);             
              if(hasInitial)
                wtid=eb.sedemsCpInitialApproval(grantid, userb, false);//create wt,el, do not send
              else 
                return mapping.findForward(module+"needappr");
            }
            else if(emailtype.equals("final"))
            {
              boolean hasFinal = adb.isFinalAppApproved(grantid, false, 0);              
              if(hasFinal)             
                wtid=eb.sedemsCpFinalApproval(grantid, userb, false);//create wt,el, do not send         
              else
                return mapping.findForward(module+"needappr");
            }
            else if(emailtype.equals("deny"))
            {
                boolean hasDeny = adb.isAppDenied(grantid);
                if(hasDeny)
                  wtid=eb.sedemsCpAppDenied(grantid, userb, false);         
                else
                  return mapping.findForward(module+"needappr");
            }
            else if(emailtype.equals("osc"))
            {
              boolean hasAppr = adb.isInitialAppApproved(grantid);
              if(hasAppr)             
                wtid=eb.sedemsCoIntentAward(grantid, userb, false);//for coor only
              else
                return mapping.findForward(module+"needappr");
            }
             
          
          EmailHelpBean eb = getUnsentTemplate(userb, wtid, userb.getAuthBean());
          eb.setProgram(module);
          request.setAttribute("emailHelpBean", eb);             
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("template");        
    }
    
    
    public ActionForward unsentmail(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
           
      try{
          String module = request.getParameter("pr");
          UserBean userb = (UserBean) sess.getAttribute("lduser");  
          AuthenticateBean authb;
          
          if(module!=null && module.equals("lg"))
            authb = userb.getArchivesAuthBean();
          else if(module!=null && module.equals("cn"))
            authb = userb.getConstructionAuthBean();
          else
            authb = userb.getAuthBean();
            
          getUnsentEmails(request, userb, authb); 
                    
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("unsentmail");        
    }
    
    
    
  /**
   * View template option chosen from list of all unsent wt's.  Gets all wt details and
   * displays for editing.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward viewtemplate(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{      
        String id = request.getParameter("id");
        String module = request.getParameter("pr");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        AuthenticateBean abean;
        if(module!=null && module.equals("lg"))
            abean = userb.getArchivesAuthBean();
        else if(module!=null && module.equals("cn"))
            abean = userb.getConstructionAuthBean();
        else
            abean = userb.getAuthBean();
            
        EmailHelpBean eb = getUnsentTemplate(userb, Integer.parseInt(id), abean);
        request.setAttribute("emailHelpBean", eb);          
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("template");        
    }
    
    
    
    public ActionForward confirmdelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
            
      try{
        String id = request.getParameter("id");
        String module = request.getParameter("pr");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        AuthenticateBean abean;
        if(module!=null && module.equals("lg"))
            abean = userb.getArchivesAuthBean();
        else if(module!=null && module.equals("cn"))
            abean = userb.getConstructionAuthBean();
        else
            abean = userb.getAuthBean();
            
        EmailHelpBean eb = getUnsentTemplate(userb, Integer.parseInt(id), abean);
        request.setAttribute("emailBean", eb);       
        
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("confirmdelete");
    }
        
    
     public ActionForward deletewt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
       
      try{
        String id = request.getParameter("id");
        String module = request.getParameter("pr");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        AuthenticateBean abean;
        if(module!=null && module.equals("lg"))
           abean = userb.getArchivesAuthBean();
        else if(module!=null && module.equals("cn"))
            abean = userb.getConstructionAuthBean();
        else
           abean = userb.getAuthBean();
           
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), abean);        
        int outcome = ez.deleteMail(Integer.parseInt(id));
        
        //get all unsent mail and display
        getUnsentEmails(request, userb, abean);
        
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("unsentmail");
    }
    
    
    
     public void getUnsentEmails(HttpServletRequest request, UserBean userb, AuthenticateBean ab)
    {
      try{
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), ab);
          //System.out.println("calling ez.listUnsendMail()");
          WorkingTemplateQuery[] wtq=ez.listUnsentMail();
          Collections.sort(Arrays.asList(wtq), WorkingTemplateQuery.DateCreatedComparator );
          System.out.println("number emails: "+wtq.length);
          request.setAttribute("wtBeans", wtq);                 
          
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      
    }
    
    
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
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return ehb;
    }
    
    
    public ActionForward confirmsend(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        String module = request.getParameter("prog");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        AuthenticateBean abean;
        if(module!=null && module.equals("lg"))
          abean = userb.getArchivesAuthBean();
        else if(module!=null && module.equals("cn"))
          abean = userb.getConstructionAuthBean();
        else
          abean = userb.getAuthBean();
          
        EmailHelpBean eb = getUnsentTemplate(userb, Integer.parseInt(id), abean);
        eb.setProgram(module);
        request.setAttribute("emailHelpBean", eb);          
      
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("confirmsend");
    }
    
    
    /*commented out 3/5/10- used for lit/cp admin to send emails-then return to page.
     * Now using sendwt for lit/cp/lg to send emails then return to page.
     * public ActionForward sendtemplate(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      String module ="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        module=request.getParameter("prog");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
        ez.sendDraft(Integer.parseInt(id));
        
        
        if(module==null || module.equals(""))//if email was chosen from 'list of unsent mail'
        {
          module="";      
          //return to viewunsentmail page
          getUnsentEmails(request, userb, userb.getAuthBean());     
        }
        else{//return to program home page (cp only)
            int fccode=0;
            if(module.equals("sa"))
              fccode=6;
            else if(module.equals("co"))
              fccode=7;
            else if(module.equals("di"))
              fccode=5;
            getGrantsAdminHomePage(fccode, request);
        }
      
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module+"home");
    }*/
    
    
    public ActionForward sendwt(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      String module ="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        module=request.getParameter("prog");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        AuthenticateBean authb;          
          if(module!=null && module.equals("lg"))
            authb = userb.getArchivesAuthBean();
          else if(module!=null && module.equals("cn"))
            authb = userb.getConstructionAuthBean();
          else
            authb = userb.getAuthBean();            
            
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), authb);
        ez.sendDraft(Integer.parseInt(id));
                
                       
         //return to 'select mass mail page' for cp/lg; new email tmeplate page for lit            
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("selectmail");
    }
    
    
  /*public void getGrantsAdminHomePage(int fccode, HttpServletRequest request)
  {
    try{
        AdminDB adb = new AdminDB();
        Vector allGrants = adb.getSubmittedApplications(fccode);          
        
        if(fccode==7 && (allGrants.size()>0))//for coordinated only
        {
          BudgetDBHandler bh = new BudgetDBHandler();
        
          //get the total requested for each grant
          for(int i=0; i< allGrants.size(); i++)
          {
            GrantBean gb = (GrantBean) allGrants.get(i);
            int amt = bh.calcTotalAmtRequested(gb.getGrantid(), gb.getFycode());
            int apramt =bh.calcTotalAmtApproved(gb.getGrantid(), gb.getFycode());
            gb.setTotamtreq(amt);
            gb.setTotamtappr(apramt);
          }
          
           //get total amount available for fy (amtavail - amtAppr)
            GrantBean g = (GrantBean) allGrants.get(0);
            TotalsBean tb = bh.getTotalCoAmtAvailableForFY(g.getFycode());     
            request.setAttribute("totAvail", tb);
        }
        request.setAttribute("allGrants", allGrants); 
        
        Vector finGrants = adb.getSubmittedFinalApps(fccode);
        request.setAttribute("finalGrants", finGrants);        
        
        Vector apprGrants = adb.getWaitingForFinalApplications(fccode);
        request.setAttribute("apprGrants", apprGrants);
        
        //get grants closed for this FY (final app approved or denied)
        Vector closeGrants = adb.getClosedGrants(fccode);
        request.setAttribute("closeGrants", closeGrants);
    }catch(Exception e){
      System.out.println("error SedemsAction "+e.getMessage().toString());
    }
  }*/
  
  /**
     * Method used for lg admin, it will create a wt based on the st the user choose.
     * Then display the new wt for editing. -Uses archives auth bean
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward createtemplate(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      int stid =0;
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String emailtype = request.getParameter("type");
        module = request.getParameter("m");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getArchivesAuthBean());
        if(emailtype.equalsIgnoreCase("final"))
            stid=761;
        else if(emailtype.equalsIgnoreCase("deny"))
            stid=762;
        else if(emailtype.equalsIgnoreCase("initial"))
            stid=763;
        else if(emailtype.equalsIgnoreCase("assign"))
            stid=764;
        else if(emailtype.equalsIgnoreCase("particip"))
            stid=765;
        else if(emailtype.equalsIgnoreCase("amendment"))
            stid=1085;//added 8/29/11 amendment approved email per FC
        
        //create just the wt
        int wtid =ez.draftStandardEmail(stid, "archgrants@nysed.gov", "");
           
        //get the wt and display for editing
        EmailHelpBean eb = getUnsentTemplate(userb, wtid, userb.getArchivesAuthBean());
        eb.setProgram(module);
        request.setAttribute("emailHelpBean", eb);     
              
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module+"createtemplate");
    }
    
    /**
     * method used for cn admin to choose a standard template. 3/21/12
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward createcntemplate(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      int stid =0;
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String emailtype = request.getParameter("type");
        module = request.getParameter("m");
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());
        if(emailtype.equalsIgnoreCase("final"))
            stid=1127;
        else if(emailtype.equalsIgnoreCase("deny"))
            stid=1126;
        else if(emailtype.equalsIgnoreCase("initial"))
            stid=1125;
                
        //create just the wt
        int wtid =ez.draftStandardEmail(stid, "libdevgrants@nysed.gov", "");
           
        //get the wt and display for editing
        EmailHelpBean eb = getUnsentTemplate(userb, wtid, userb.getConstructionAuthBean());
        eb.setProgram(module);
        request.setAttribute("emailHelpBean", eb);     
              
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward(module+"createtemplate");
    }
    
    
    public ActionForward recipientspage(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    { 
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String id = request.getParameter("id");
        sess.setAttribute("wtid", id);
        
        DBHandler dbh = new DBHandler();
        ArrayList allyears = dbh.dropDownFiscalYearsDesc();
        sess.setAttribute("dropDownList", allyears);
        
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("recipients");
    }
    
    
    /*
     * for lgrmif admin to search for recipients to add to email. choose from reviewers,
     * or pm/rmo for fy groups.
     */
    public ActionForward viewrecipients(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      EmailAssistDBbean edb = new EmailAssistDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String fy = request.getParameter("fy");
        String atype = request.getParameter("type");
        String wtid = request.getParameter("wtid");
        ArrayList selectgroup=new ArrayList();
        
        if(atype.equalsIgnoreCase("Reviewer"))
          selectgroup = edb.getReviewerRecipGroup("80");
        else if(atype.equalsIgnoreCase("ReviewerAssign"))
            selectgroup = edb.getReviewerAssignGroup(80, Integer.parseInt(fy));
        else{
          selectgroup = edb.getRecipientGroup(Integer.parseInt(fy), 80, atype);//get PM
          selectgroup = edb.getRecipientGroupRMO(Integer.parseInt(fy), 80, atype, selectgroup);//get RMO
          Collections.sort(selectgroup, GrantBean.InstitutionComparator);
        }        
        
        RevAssignCollectionBean rc = new RevAssignCollectionBean();
        rc.setAllPotentialGrants(selectgroup);
        rc.setWorkingTemplateId(Integer.parseInt(wtid));
        rc.setFycode(Integer.parseInt(fy));
        rc.setApprovalType(atype);
        request.setAttribute("AssignCollectionBean", rc);
                
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("recipients");
    }
    
    
    /**
     * for cn admin - get matching recipient group and display to admin. allows them to
     * add the recipient group to email template
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchrecipients(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {    
      EmailAssistDBbean edb = new EmailAssistDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String fy = request.getParameter("fy");
        String atype = request.getParameter("type");
        String wtid = request.getParameter("wtid");
         
        //get recip group - project managers 
        HashMap selectgroup = edb.getRecipientGroupConstruction(Integer.parseInt(fy), atype); 
        //get recip group - public library system directors of each award/deny institution
        selectgroup = edb.getRecipientGroupConstructionPLS(Integer.parseInt(fy), atype, selectgroup);
        
        //convert the final hashmap into an arraylist
        ArrayList<GrantBean> valuelist = new ArrayList<GrantBean>(selectgroup.values());
          
        Collections.sort(valuelist, GrantBean.SystemNameComparator);
              
        RevAssignCollectionBean rc = new RevAssignCollectionBean();
        rc.setAllPotentialGrants(valuelist);
        rc.setWorkingTemplateId(Integer.parseInt(wtid));
        rc.setFycode(Integer.parseInt(fy));
        rc.setApprovalType(atype);
        request.setAttribute("AssignCollectionBean", rc);
                              
      }catch(Exception e){System.out.println("error SedemsAction "+e.getMessage().toString());}
      return mapping.findForward("recipients");
    }
    
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT SedemsAction");
        timeout = true;
      }
      
      return timeout;
    }
    
    
    
}