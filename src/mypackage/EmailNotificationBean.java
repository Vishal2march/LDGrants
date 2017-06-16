/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  EmailNotificationBean.java
 * Creation/Modification History  :
 *
 * SH   5/8/07      Created
 * SH   8/14/07     Modified methods to call MailScheduler instead of sending the
 *                  email message 
 *
 * Description
 * This class will handle the sending of all email messages for the system.  It has 
 * methods to send correction emails, application submitted emails, and application
 * approved emails.  It has methods to determine the PO officer that the mail should
 * be sent to, and has vars that store the general messages. 
 * 
 * $Id: EmailNotificationBean.java 4849 2009-06-23 14:42:53Z shusak $
 *****************************************************************************/
package mypackage;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import sedems.AuthenticateBean;
import sedems.EmailLogSend;
import sedems.EmailVarSend;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.StandardTemplate;
import sedems.WorkingTemplateQuery;
import sedems.WorkingTemplateSend;

public class EmailNotificationBean 
{
  String urlApp = "\rAccess the Division of Library Development Online Grant System  https://eservices.nysed.gov/ldgrants ";
  String urlAppTest = "\rAccess the TEST Division of Library Development Online Grant System  https://eservicest.nysed.gov/ldgrants ";
  String testMessage = "\r----This is a test message of the Division of Library Development Online Grant System.----\r";
      
  String ccb = "";
  String ccGAU = "";
  String ccLindaKim = "";
  String toLynneLinda = "";
  String fromBL = "barbara.lilley@nysed.gov";
  String fromLD = "LibDevGrants@nysed.gov";
  String fromGAU = "archgrants@nysed.gov";
  String testFlag = "";
  DbName db = new DbName();
  DBHandler dbh = new DBHandler();
  CommentDBbean cdb = new CommentDBbean();
  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;

  public EmailNotificationBean()
  {
    if(db.production==true)
    {
      testFlag = "";
      ccLindaKim = "MaryAnne.Waltz@nysed.gov,kimberly.anderson@nysed.gov";
      toLynneLinda = "MaryAnne.Waltz@nysed.gov,marisa.boomhower@nysed.gov";
      ccb = "barbara.lilley@nysed.gov";
      ccGAU = "archgrants@nysed.gov";
    }
    else
    {
      testFlag = "TEST ";
      ccLindaKim = "stefanie.husak@nysed.gov";
      toLynneLinda = "stefanie.husak@nysed.gov";
      ccb = "stefanie.husak@nysed.gov";
      ccGAU = "stefanie.husak@nysed.gov";
    }
  }
  
  public boolean sedemsCommentMail(CommentBean cb, UserBean userb)
  {
    boolean msgSent = false; 
    String program="";
          
    try{
      GrantBean theGrant =dbh.getRecordBean(cb.getGrantid());   
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
       
      int fccode = theGrant.getFccode();      
      OfficerBean mypo = new OfficerBean();
      if(fccode==6 || fccode==7)
      {
        mypo = findPresOfficer(cb.getGrantid());
        program=" Conservation/Preservation ";
      }
      else if(fccode==5)
      {      
        mypo = findProjectManager(cb.getGrantid());
        program=" Conservation/Preservation ";
      }
      else if(fccode==40 || fccode==42)
      {
        mypo = findProjectManager(cb.getGrantid());
        program=" Literacy Services ";
      }
        
      //if po email not found 
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return false;
      }
      
       //get the comment text for chosen id
       CommentBean comm = cdb.getComment(cb.getId());                
                 
      //create the string that will make up the body of the email
      StringBuffer msg = new StringBuffer();
      if(db.production==false)
        msg.append(testMessage + "\n\n");     
      msg.append("The following message is in regards to"+ program +"grant application project number "+ proposalnum +" \n");
      msg.append( comm.getComment() + "\n\n");
      if(db.production==false)
        msg.append(urlAppTest);
      else
        msg.append(urlApp);
                
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
        
        int wt=ez.draftTemplateAndVars(cb.getFrom(),"",cb.getSubject(),msg.toString(),"$$grantId");

        Vector v=new Vector();
        
        String too="";
        if(db.production==true)
            too = mypo.getEmail();
        else
            too = "stefanie.husak@nysed.gov";
        EmailLogSend els=ez.createEmailLog(too, cb.getCc(),"",userb.getUserid(),wt);
        
        //now add var
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        
        //loop thru each var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++){
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$grantId")){
             evs.setValue(String.valueOf(theGrant.getGrantid()));
           }           
           vVars.add(evs);
         }
         EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
         els.setEmailVariables(vararray);
         v.add(els);
         ez.updateEmailRecipients(wt,v);   
         ez.sendDraft(wt);     
      
    }
    catch(Exception e)
    {
      System.err.println("error sedemsCommentMail() " + e.getMessage().toString());
      return msgSent;
    }   
    return true;
  }

   
  public boolean sedemsLgAppSubmitted(UserBean userb, long grantid)
  {  
    boolean msgSent = false; 
        
    try{
      GrantBean theGrant = dbh.getRecordBean(grantid);   
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
      
      //notifications go to PM and RMO
      OfficerBean mypo = findProjectManager(grantid);
      OfficerBean myrmo = findRMOForLgrmif(grantid);
       
      //if pm email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return false;
      }
            
       //get current date and time
       DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());             
    
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getArchivesAuthBean());

      //create wt instance of st 
      int wt = ez.draftStandardEmail(561, fromGAU, "");  
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                  
      //get an emaillog including "to" address
      String emailto= mypo.getEmail() +"," + myrmo.getEmail();
      EmailLogSend els=ez.createEmailLog(emailto,"","", userb.getUserid(), wt);
      
      //create values for vars      
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$fiscalYear")){
           evs.setValue(theGrant.fiscalyear);
         }
         else if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$date")){ 
           evs.setValue(dateFormat.format(cal.getTime()));
         }
         else if(mvqs[i].getName().equals("$$user")){ 
           evs.setValue(userb.getUserid());
         }
          else if(mvqs[i].getName().equals("$$grantId")) {
              evs.setValue(String.valueOf(grantid));
          }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);
         
    } 
    catch(Exception e){
      System.err.println("error sedemsLgAppSubmitted() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
  
  
  
    public boolean sedemsLgFinalSubmit(UserBean userb, long grantid)
    {  
      boolean msgSent = false; 
          
      try{
        GrantBean theGrant = dbh.getRecordBean(grantid);   
        //get the project number for the grant
        String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
        
        //notifications go to PM and RMO
        OfficerBean mypo = findProjectManager(grantid);
        OfficerBean myrmo = findRMOForLgrmif(grantid);
         
        //if pm email not found - send notice to LD staff instead
        if(mypo.getEmail()==null || mypo.getEmail().equals(""))
        {
          sedemsAddressMissing(userb, proposalnum);
          return false;
        }
              
         //get current date and time
         DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());             
      
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getArchivesAuthBean());

        //create wt instance of st 
        int wt = ez.draftStandardEmail(741, fromGAU, "");  
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                    
        //get an emaillog including "to" address
        String emailto= mypo.getEmail() +"," + myrmo.getEmail();
        EmailLogSend els=ez.createEmailLog(emailto,"","", userb.getUserid(), wt);
        
        //create values for vars      
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        //loop thru each message var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++)
        {
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$fiscalYear")){
             evs.setValue(theGrant.fiscalyear);
           }
           else if(mvqs[i].getName().equals("$$projectNum")){ 
             evs.setValue(proposalnum);
           }
           else if(mvqs[i].getName().equals("$$date")){ 
             evs.setValue(dateFormat.format(cal.getTime()));
           }
           else if(mvqs[i].getName().equals("$$user")){ 
             evs.setValue(userb.getUserid());
           }
           else if(mvqs[i].getName().equals("$$grantId")) {
               evs.setValue(String.valueOf(grantid));
           }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);
           
      } 
      catch(Exception e){
        System.err.println("error sedemsLgFinalSubmit() " + e.getMessage().toString());
        return msgSent;
      }        
      return true;
    }
    
  
  /**
   * This method is used to send an app submitted email for sa/co/di applicants.
   * @return 
   * @param grantid
   * @param userb
   * @param fccode
   */
  public boolean sedemsCpAppSubmitted(UserBean userb, long grantid, int fccode)
  {  
    boolean msgSent = false; 
        
    try{
      GrantBean theGrant = dbh.getRecordBean(grantid);   
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
      
      OfficerBean mypo = new OfficerBean();
      if(fccode==6 || fccode==7)
        mypo = findPresOfficer(grantid);
      else if(fccode==5)
        mypo = findProjectManager(grantid);
       
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return false;
      }
            
       //get current date and time
       DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());             
    
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      int wt = ez.draftStandardEmail(401, fromLD, "");    
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                 
      //get an emaillog including "to" address
      EmailLogSend els=ez.createEmailLog(mypo.getEmail(),"","", userb.getUserid(), wt);
      
      //create values for vars      
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$fiscalYear")){
           evs.setValue(theGrant.fiscalyear);
         }
         else if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$date")){ 
           evs.setValue(dateFormat.format(cal.getTime()));
         }
         else if(mvqs[i].getName().equals("$$user")){ 
           evs.setValue(userb.getUserid());
         }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);         
    } 
    catch(Exception e){
      System.err.println("error sedemsCpAppSubmitted() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
  
  
    /**
     * This method is used to send an app submitted email for cn applicants.
     * @return 
     * @param grantid
     * @param userb
     * @param fccode
     */
    public boolean sedemsConstructAppSubmitted(UserBean userb, long grantid, int fccode)
    {  
      boolean msgSent = false; 
          
      try{
        GrantBean theGrant = dbh.getRecordBean(grantid);   
        //get the project number for the grant
        String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
        
        OfficerBean mypo = findProjectManager(grantid);         
        //if po email not found - send notice to LD staff instead
        if(mypo.getEmail()==null || mypo.getEmail().equals("")){
          sedemsAddressMissing(userb, proposalnum);
          return false;
        }
              
         //get current date and time
         DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());             
      
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

        //create wt instance of st
        int wt = ez.draftStandardEmail(1065, fromLD, "");    
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                   
        //get an emaillog including "to" address
        EmailLogSend els=ez.createEmailLog(mypo.getEmail(),"","", userb.getUserid(), wt);
        
        //create values for vars      
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        //loop thru each message var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++)
        {
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$fiscalYear")){
             evs.setValue(theGrant.fiscalyear);
           }
           else if(mvqs[i].getName().equals("$$projectNum")){ 
             evs.setValue(proposalnum);
           }
           else if(mvqs[i].getName().equals("$$date")){ 
             evs.setValue(dateFormat.format(cal.getTime()));
           }
           else if(mvqs[i].getName().equals("$$user")){ 
             evs.setValue(userb.getUserid());
           }
            else if(mvqs[i].getName().equals("$$grantId")) {
                evs.setValue(String.valueOf(grantid));
            }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);           
      } 
      catch(Exception e){
        System.err.println("error sedemsConstructAppSubmitted() " + e.getMessage().toString());
        return msgSent;
      }          
      return true;
    }
    
    
    public boolean sedemsConstructFinalSubmit(UserBean userb, long grantid)
    {
       boolean msgSent = false; 
      
      try{  
        GrantBean theGrant = dbh.getRecordBean(grantid);
        //get the project number for the grant
        String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
          
        OfficerBean mypo = new OfficerBean();
        mypo = findProjectManager(grantid);
        
        //if po email not found - send notice to LD staff instead
        if(mypo.getEmail()==null || mypo.getEmail().equals(""))
        {
          sedemsAddressMissing(userb, proposalnum);
          return false;
        }
                     
         DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());               
              
         SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());

        //create wt instance of st
        int wt=ez.draftStandardEmail(1145, fromLD, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
             
        //get an emaillog including "to" address
        EmailLogSend els=ez.createEmailLog(mypo.getEmail(),ccLindaKim,"", userb.getUserid(), wt);
        
        //create values for vars
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        //loop thru each message var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++)
        {
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$fiscalYear")){
             evs.setValue(theGrant.fiscalyear);
           }
           else if(mvqs[i].getName().equals("$$projectNum")){ 
             evs.setValue(proposalnum);
           }
           else if(mvqs[i].getName().equals("$$date")){ 
             evs.setValue(dateFormat.format(cal.getTime()));
           }
           else if(mvqs[i].getName().equals("$$user")){ 
             evs.setValue(userb.getUserid());
           }
           else if(mvqs[i].getName().equals("$$institution")){ 
              evs.setValue(theGrant.getInstName());
           }
            else if(mvqs[i].getName().equals("$$grantId")){ 
               evs.setValue(String.valueOf(grantid));
            }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);           
      } 
      catch(Exception e)
      {
        System.err.println("error sedemsConstructFinalSubmit() " + e.getMessage().toString());
        return msgSent;
      }        
      return true;
    }
    
    
    
    
    
  public boolean sedemsConstructMwbeSubmit(UserBean userb, long grantid)
  {
     boolean msgSent = false; 
    
    try{  
      GrantBean theGrant = dbh.getRecordBean(grantid);
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
           
                        
       DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());               
            
       SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getConstructionAuthBean());

      //create wt instance of st
      int wt=ez.draftStandardEmail(1485, fromLD, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
           
      //get an emaillog including "to" address
      EmailLogSend els=ez.createEmailLog(toLynneLinda,"","", userb.getUserid(), wt);
      
      //create values for vars
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$date")){ 
           evs.setValue(dateFormat.format(cal.getTime()));
         }
         else if(mvqs[i].getName().equals("$$user")){ 
           evs.setValue(userb.getUserid());
         }
         else if(mvqs[i].getName().equals("$$institution")){ 
            evs.setValue(theGrant.getInstName());
         }
          else if(mvqs[i].getName().equals("$$grantId")){ 
             evs.setValue(String.valueOf(grantid));
          }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);           
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsConstructMwbeSubmit() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
    
    
  
  public boolean sedemsCpFinalAppSubmit(UserBean userb, long grantid, int fccode)
  {
     boolean msgSent = false; 
    
    try{  
      GrantBean theGrant = dbh.getRecordBean(grantid);
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
        
      OfficerBean mypo = new OfficerBean();
      if(fccode==6 || fccode==7)
        mypo = findPresOfficer(grantid);
      else if(fccode==5)
        mypo = findProjectManager(grantid);
      
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return false;
      }
                   
       DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());               
            
       SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      int wt=ez.draftStandardEmail(402, fromLD, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
           
      //get an emaillog including "to" address
      EmailLogSend els=ez.createEmailLog(mypo.getEmail(),"","", userb.getUserid(), wt);
      
      //create values for vars
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$fiscalYear")){
           evs.setValue(theGrant.fiscalyear);
         }
         else if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$date")){ 
           evs.setValue(dateFormat.format(cal.getTime()));
         }
         else if(mvqs[i].getName().equals("$$user")){ 
           evs.setValue(userb.getUserid());
         }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);   
      
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsCpFinalAppSubmit() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
  
  /**
     * send lgrmif amendment submitted email to RMO, PM, and FC. added 1/26/11.
     * there is a separate method for cp amendment submitted.
     * @param ub
     * @param grantid
     * @return
     */
  public boolean sedemsLgAmendmentSubmit(UserBean ub, long grantid){
      boolean msgSent = false; 
      
      try{
       GrantBean theGrant = dbh.getRecordBean(grantid);
       //get the project number for the grant
       String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
         
       OfficerBean mypo = findProjectManager(grantid);
       OfficerBean myrmo = findRMOForLgrmif(grantid);
                           
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());                     
        SEDEMSEZClass ez=new SEDEMSEZClass(ub.getUserid(), ub.getArchivesAuthBean());

       //create wt instance of st
       int wt=ez.draftStandardEmail(1044, fromGAU, "");
       WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
            
       //get an emaillog including "to" address
       String toemail = mypo.getEmail() + "," + myrmo.getEmail();
       EmailLogSend els=ez.createEmailLog(toemail, ccGAU, "", ub.getUserid(), wt);
       
       //create values for vars
       MessageVarQuery[] mvqs=wtq.getMessagevariables();
       EmailVarSend evs=null;
       //loop thru each message var
       Vector vVars=new Vector();
       for(int i=0;i<mvqs.length;i++)
       {
          evs=new EmailVarSend();
          evs.setMessageVarId(mvqs[i].getId());
          if(mvqs[i].getName().equals("$$grantId")){
              evs.setValue(String.valueOf(grantid));
          }
         else if(mvqs[i].getName().equals("$$fiscalYear")){
            evs.setValue(theGrant.fiscalyear);
          }
          else if(mvqs[i].getName().equals("$$projectNum")){ 
            evs.setValue(proposalnum);
          }
          else if(mvqs[i].getName().equals("$$date")){ 
            evs.setValue(dateFormat.format(cal.getTime()));
          }
          else if(mvqs[i].getName().equals("$$user")){ 
            evs.setValue(ub.getUserid());
          }
          vVars.add(evs);
       }
       EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
       els.setEmailVariables(vararray);//add var values to the email log
           
       Vector myEmailLogs=new Vector();
       myEmailLogs.add(els);
       ez.updateEmailRecipients(wt,myEmailLogs);
       ez.sendDraft(wt);   
       
      }catch(Exception e){
       System.err.println("error sedemsLgAmendmentSubmit() " + e.getMessage().toString());
       return msgSent;
      }
      return true;
  }
  
  
   /*
    * New method for SA/CO/DI applicants, send email to apcnt and admin on submit of fs10a 6/17/10
    */
    public boolean sedemsAmendmentSubmit(UserBean userb, long grantid)
    {
       boolean msgSent = false; 
      
      try{  
        GrantBean theGrant = dbh.getRecordBean(grantid);
        //get the project number for the grant
        String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
          
        OfficerBean mypo = new OfficerBean();
        if(theGrant.getFccode() ==6 || theGrant.getFccode()==7)
          mypo = findPresOfficer(grantid);
        else if(theGrant.getFccode()==5)
          mypo = findProjectManager(grantid);
        //if po email not found - send notice to LD staff instead
       /* if(mypo.getEmail()==null || mypo.getEmail().equals("")){
          sedemsAddressMissing(userb, proposalnum);
          return false;
        }*/
                     
         DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());                     
         SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

        //create wt instance of st
        int wt=ez.draftStandardEmail(904, fromLD, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
             
        //get an emaillog including "to" address
        EmailLogSend els=ez.createEmailLog(mypo.getEmail(),ccb,"", userb.getUserid(), wt);
        
        //create values for vars
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        //loop thru each message var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++)
        {
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$grantId")){
               evs.setValue(String.valueOf(grantid));
           }
          else if(mvqs[i].getName().equals("$$fiscalYear")){
             evs.setValue(theGrant.fiscalyear);
           }
           else if(mvqs[i].getName().equals("$$projectNum")){ 
             evs.setValue(proposalnum);
           }
           else if(mvqs[i].getName().equals("$$date")){ 
             evs.setValue(dateFormat.format(cal.getTime()));
           }
           else if(mvqs[i].getName().equals("$$user")){ 
             evs.setValue(userb.getUserid());
           }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);   
        
      }catch(Exception e){
        System.err.println("error sedemsAmendmentSubmit() " + e.getMessage().toString());
        return msgSent;
      }        
      return true;
    }
 

  public int sedemsCpInitialApproval(long grantid, UserBean userb, boolean sendNow)
  {    
    int wt=0;
    
    try {
      GrantBean theGrant = dbh.getRecordBean(grantid);   
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
       
      //stat and coor emails go to PO, disc emails go to PM
      OfficerBean mypo =new OfficerBean();
      if(theGrant.getFccode()==7 || theGrant.getFccode()==6)
        mypo=findPresOfficer(grantid);   
      else if(theGrant.getFccode()==5)
        mypo=findProjectManager(grantid);
      
      //if po email not found - send notice to LD staff instead
      /*if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return wt;
      }*/
    
      //get the total amount approved for this grant
      BudgetDBHandler bdh = new BudgetDBHandler();
      int amtappr = bdh.calcTotalAmtApproved(grantid,0);       
            
      //format the amtapproved with commas
      NumberFormat numF = new DecimalFormat("###,###");
      String appr= String.valueOf(amtappr);
      long appr_amt = Long.parseLong(appr);
      appr = numF.format(appr_amt);
            
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      wt=ez.draftStandardEmail(404, fromBL, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
            
      //get an emaillog including "to" address
      String too="";
      if(db.production==true)
          too = mypo.getEmail();
      else
          too = "stefanie.husak@nysed.gov";
      EmailLogSend els=ez.createEmailLog(too, ccb ,"", userb.getUserid(), wt);
      
      //create values for vars
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$fiscalYear")){
           evs.setValue(theGrant.getFiscalyear());
         }
         else if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$grantId")){ 
           evs.setValue(String.valueOf(theGrant.getGrantid()));
         }
         else if(mvqs[i].getName().equals("$$amtApproved")){ 
           evs.setValue("$"+appr);
         }
         else if(mvqs[i].getName().equals("$$managerName")){ 
           evs.setValue(mypo.getFname() +" "+ mypo.getLname());
         }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      
      if(sendNow){
        ez.sendDraft(wt);
      }
            
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsCpInitialApproval() " + e.getMessage().toString());
      return wt;
    }        
    return wt;
  }
  
  
  public void sendFinalEmail(SEDEMSEZClass ez, int wtid)
  {
    try{    
      ez.sendDraft(wtid);
      
    }catch(Exception e){
      System.err.println("error sendFinalEmail() "+e.getMessage().toString());
    }
  }
  
    
  public int sedemsCpFinalApproval(long grantid, UserBean userb, boolean sendNow)
  {  
    int wt=0;      
    
    try{ 
      GrantBean theGrant = dbh.getRecordBean(grantid); 
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
       
       //stat and coor emails go to PO, disc emails go to PM
      OfficerBean mypo =new OfficerBean();
      if(theGrant.getFccode()==7 || theGrant.getFccode()==6)
        mypo=findPresOfficer(grantid);   
      else if(theGrant.getFccode()==5)
        mypo=findProjectManager(grantid); 
      
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return wt;
      }           
               
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      wt=ez.draftStandardEmail(405, fromBL, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
            
      //get an emaillog including "to" address
      String too="";
      if(db.production==true)
          too = mypo.getEmail();
      else
          too = "stefanie.husak@nysed.gov";
      EmailLogSend els=ez.createEmailLog(too, ccb ,"", userb.getUserid(), wt);
      
      //create values for vars
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$fiscalYear")){
           evs.setValue(theGrant.getFiscalyear());
         }
         else if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$grantId")){ 
           evs.setValue(String.valueOf(theGrant.getGrantid()));
         }
         else if(mvqs[i].getName().equals("$$managerName")){ 
           evs.setValue(mypo.getFname() +" "+ mypo.getLname());
         }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      
      if(sendNow){
        ez.sendDraft(wt);
      }
      
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsCpFinalApproval() " + e.getMessage().toString());
      return wt;
    }        
    return wt;  
  }

 
  /**
   * This creates connection to oracle ldgrants db
   * @throws java.lang.Exception
   * @return 
   */
  private static Connection initializeConn() throws Exception
  {
      Context namingContext = new InitialContext();
      DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      return ds.getConnection();   
  }
  
  public void Close(Connection conn)
  {
    try
    {
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
  
  public void Close(PreparedStatement stmt)
  {
    try
    {
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

   public void Close(ResultSet rs)
  {
    try
    {
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }


  public OfficerBean findPresOfficer(long grantid)
  {  
     OfficerBean ob = new OfficerBean();
     try {
        conn = initializeConn();
    
        ps = conn.prepareStatement("select initcap(fname) as fname, initcap(lname) as lname, "+
        " initcap(SED_SALUTATIONS.DESCRIPTION) as sal from SED_ADMIN_POSITIONS, SED_SALUTATIONS where "+
        " SED_ADMIN_POSITIONS.SAL_CODE = SED_SALUTATIONS.SAL_CODE and SED_ADMIN_POSITIONS.ADMIN_POS_ID in "+
        " (select ADMIN_POS_ID from GRANT_ADMINS where GRA_ID=? and END_DATE is null and TITLE='Preservation Officer')");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          //ob.setSalutation(rs.getString("DESCRIPTION"));
          ob.setSalutation(rs.getString("sal"));
          ob.setFname(rs.getString("FNAME"));
          ob.setLname(rs.getString("LNAME"));
        }
        
        ps.clearParameters();        
        //get po email
        ps = conn.prepareStatement("select * from SED_CONTACT_INFO where CONTACT_TYPE_CODE='3' "+
          " and ADMIN_POS_ID in (select ADMIN_POS_ID from GRANT_ADMINS where GRA_ID=? "+
          " and END_DATE is null and TITLE='Preservation Officer') ");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next())
        {
          ob.setEmail(rs.getString("CONTACT_VALUE"));
        }
       
     }
      catch(Exception e){
        System.err.println("error findPresOfficer() " + e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(conn);
        Close(rs);
      }       
      return ob;
  }
  
  public StandardTemplate findStandardTemplate(UserBean userb, int stId)
  {
    StandardTemplate searchST = new StandardTemplate();
    try{
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
      StandardTemplate st[] =ez.listStandardTemplates();
      
      if(st.length>0)
      {
        for(int i=0; i<st.length; i++)
        {
          if(stId== st[i].getId())
            searchST = st[i];       
        }
      }
          
      }catch(Exception e){
        System.err.println("error findStandardTemplate() " + e.getMessage().toString());
      }
      return searchST;
  }
  
  public OfficerBean findProjectManager(long grantid)
  {
    OfficerBean ob = new OfficerBean();    
    try{    
      conn = initializeConn();
      ps = conn.prepareStatement("select * from PROJECT_MANAGERS where ID in "+
        " (select PRM_ID from GRANTS where ID=?)");
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next()){
        ob.setStaffID(rs.getLong("ID"));
        ob.setFname(rs.getString("FNAME"));
        ob.setLname(rs.getString("LNAME"));
      }
      
      Close(rs);
      Close(ps);
      
      ps = conn.prepareStatement("select * from CONTACTS where PRM_ID=? and CONTACT_TYPE_CODE=3");
      ps.setLong(1, ob.getStaffID());
      rs = ps.executeQuery();      
      while(rs.next()){
        ob.setEmail(rs.getString("CONTACT_VALUE").trim());
      }      
      
    }catch(Exception e){
      System.out.println("error email findProjectManager "+e.getMessage().toString());
    }
    finally{
      Close(ps);
      Close(rs);
      Close(conn);
    }
    return ob;    
  }
  
  
    public OfficerBean findRMOForLgrmif(long grantid)
    {
      OfficerBean ob = new OfficerBean();      
      try{    
        conn = initializeConn();
        ps = conn.prepareStatement("select * from GRANT_STAFFS where gra_id=? and st1_id=1");
        ps.setLong(1, grantid);
        rs = ps.executeQuery();
        
        while(rs.next()){
          ob.setStaffID(rs.getLong("ID"));
          ob.setFname(rs.getString("FNAME"));
          ob.setLname(rs.getString("LNAME"));
        }
        
        Close(rs);
        Close(ps);
        
        ps = conn.prepareStatement("select contact_value from CONTACTS where GS_ID=? and CONTACT_TYPE_CODE=3");
        ps.setLong(1, ob.getStaffID());
        rs = ps.executeQuery();      
        while(rs.next()){
          ob.setEmail(rs.getString("CONTACT_VALUE").trim());
        }      
        
      }catch(Exception e){
        System.out.println("error email findRMOForLgrmif "+e.getMessage().toString());
      }
      finally{
        Close(ps);
        Close(rs);
        Close(conn);
      }
      return ob;    
    }
  
  public String formatProjectNumber(int fccode, int fycode, long projseq)
  {
    String projNum="";
    try{    
      if(fccode==5 || fccode==6 || fccode==7)
      {
        //get the project number for the grant      
        projNum = "030" + fccode + "-" + fycode + "-" + projseq;  
      }
      else if(fccode==80)
        projNum = "05" + fccode + "-" + fycode + "-" + projseq; 
      else
        projNum = "03" + fccode + "-" + fycode + "-" + projseq;  
      
    }catch(Exception e){
      System.out.println("error formatProjectNumber "+e.getMessage().toString());
    }
    return projNum;
  }


   
  public int sedemsCpAppDenied(long grantid, UserBean userb, boolean sendNow)
  {
    int wt=0;      
      
    try{       
      GrantBean theGrant =dbh.getRecordBean(grantid); 
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
            
      //stat and coor emails go to PO, disc emails go to PM
      OfficerBean mypo =new OfficerBean();
      if(theGrant.getFccode()==7 || theGrant.getFccode()==6)
        mypo=findPresOfficer(grantid);   
      else if(theGrant.getFccode()==5)
        mypo=findProjectManager(grantid);   
      
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return wt;
      }      
                        
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        wt=ez.draftStandardEmail(406, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
               
        //get an emaillog including "to" address
        String too="";
        if(db.production==true)
            too = mypo.getEmail();
        else
            too = "stefanie.husak@nysed.gov";
        EmailLogSend els=ez.createEmailLog(too, ccb ,"", userb.getUserid(), wt);
        
        //create values for vars
        MessageVarQuery[] mvqs=wtq.getMessagevariables();
        EmailVarSend evs=null;
        //loop thru each message var
        Vector vVars=new Vector();
        for(int i=0;i<mvqs.length;i++)
        {
           evs=new EmailVarSend();
           evs.setMessageVarId(mvqs[i].getId());
           if(mvqs[i].getName().equals("$$fiscalYear")){
             evs.setValue(theGrant.getFiscalyear());
           }
           else if(mvqs[i].getName().equals("$$projectNum")){ 
             evs.setValue(proposalnum);
           }
           else if(mvqs[i].getName().equals("$$grantId")){ 
             evs.setValue(String.valueOf(theGrant.getGrantid()));
           }
           else if(mvqs[i].getName().equals("$$managerName")){ 
             evs.setValue(mypo.getFname() +" "+ mypo.getLname());
           }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        if(sendNow){
          ez.sendDraft(wt);
        }
        
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsCpAppDenied() " + e.getMessage().toString());
      return wt;
    }        
    return wt;
  }

  
  public int sedemsCoIntentAward(long grantid, UserBean userb, boolean sendNow)
  {
    int wt=0;
        
    try{
      GrantBean theGrant =dbh.getRecordBean(grantid);   
      //get the project number for the grant
      String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
      
      //get the po name and email -send all emails to po
      OfficerBean mypo =findPresOfficer(grantid);    
      
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        sedemsAddressMissing(userb, proposalnum);
        return wt;
      }
      
      //get the total amount approved for this grant
      BudgetDBHandler bdh = new BudgetDBHandler();
      int amtappr = bdh.calcTotalAmtApproved(grantid,0);           
    
      //format the amtapproved with commas and dollar sign
      NumberFormat numF = new DecimalFormat("###,###");
      String appr= String.valueOf(amtappr);
      long appr_amt = Long.parseLong(appr);
      appr = numF.format(appr_amt);
           
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      wt=ez.draftStandardEmail(407, fromBL, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
            
      //get an emaillog including "to" address
      String too="";
      if(db.production==true)
          too = mypo.getEmail();
      else
          too = "stefanie.husak@nysed.gov";
      EmailLogSend els=ez.createEmailLog(too, ccb ,"", userb.getUserid(), wt);
      
      //create values for vars
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$projectNum")){ 
           evs.setValue(proposalnum);
         }
         else if(mvqs[i].getName().equals("$$grantId")){ 
           evs.setValue(String.valueOf(theGrant.getGrantid()));
         }
         else if(mvqs[i].getName().equals("$$amtApproved")){ 
           evs.setValue("$"+appr);
         }
         else if(mvqs[i].getName().equals("$$managerName")){ 
           evs.setValue(mypo.getFname() +" "+ mypo.getLname());
         }
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      if(sendNow){
        ez.sendDraft(wt);
      }               
      
    } catch(Exception e){
      System.err.println("error sedemsCoIntentAward() " + e.getMessage().toString());
      return wt;
    }        
    return wt;
  }
  
  
  public boolean sedemsAddressMissing(UserBean userb, String proposalnum)
  {  
    boolean msgSent = false; 
        
    try{                 
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      //int wt = ez.draftStandardEmail(461, fromLD, "");    
      int wt = ez.draftStandardEmail(461);
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
      
      //update template to include "from" email
      wt = ez.updateEmail(fromLD, "", wtq.getMessage(), wtq.getSubject(), wt);
           
      //get an emaillog including "to" address
      EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov","","", userb.getUserid(), wt);
      
      //create values for vars      
      MessageVarQuery[] mvqs=wtq.getMessagevariables();
      EmailVarSend evs=null;
      //loop thru each message var
      Vector vVars=new Vector();
      for(int i=0;i<mvqs.length;i++)
      {
         evs=new EmailVarSend();
         evs.setMessageVarId(mvqs[i].getId());
         if(mvqs[i].getName().equals("$$projectNum")){
           evs.setValue(proposalnum);
         }         
         vVars.add(evs);
      }
      EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
      els.setEmailVariables(vararray);//add var values to the email log
          
      Vector myEmailLogs=new Vector();
      myEmailLogs.add(els);
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);
         
    } 
    catch(Exception e)
    {
      System.err.println("error sedemsAddressMissing() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
      
}