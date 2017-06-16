package mypackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import sedems.AuthenticateBean;
import sedems.EmailLogSend;
import sedems.EmailVarSend;
import sedems.MessageVarQuery;
import sedems.SEDEMSEZClass;
import sedems.WorkingTemplateQuery;

public class LitEmailNotifyBean 
{
    
  DbName db = new DbName();
  String ccLitAdmin = "";
  
  public LitEmailNotifyBean()
  {
    if(db.production==true)
        ccLitAdmin = "DLDLP@nysed.gov,barbara.massago@nysed.gov,carol.desch@nysed.gov";         
    else
        ccLitAdmin = "LibDevGrants@nysed.gov";
  }
  
    
  String fromLD = "LibDevGrants@nysed.gov";
  //String ccLit = "LibDevGrants@nysed.gov";
  DBHandler dbh = new DBHandler();
  
  
  public boolean sendLiSubmit(UserBean userb, long grantid, boolean ccAdmin)
  {
    boolean msgSent = false; 
    EmailNotificationBean enb = new EmailNotificationBean();
    
    try{   
      GrantBean theGrant = dbh.getRecordBean(grantid);
      //get the project number for grant
       String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
      
      //get pm name and email 
      OfficerBean mypo =enb.findProjectManager(grantid);
      
      //if po email not found - send notice to LD staff instead
      if(mypo.getEmail()==null || mypo.getEmail().equals(""))
      {
        enb.sedemsAddressMissing(userb, proposalnum);
        return false;
      }
          
       DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL); 
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());          
             
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
      
      //create wt instance of st
      int wt=ez.draftStandardEmail(403, fromLD, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
      
      //get an emaillog including "to" address
      String ccString="";
      if(ccAdmin)
        ccString=ccLitAdmin;
      EmailLogSend els=ez.createEmailLog(mypo.getEmail(),ccString,"", userb.getUserid(), wt);
      
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
      
      
    } catch(Exception e){
      System.err.println("error sendLiSubmit() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }
  
  
  
    public boolean sendLiAmendmentSubmit(UserBean userb, long grantid)
    {
       boolean msgSent = false; 
       EmailNotificationBean enb = new EmailNotificationBean();
      
      try{  
        GrantBean theGrant = dbh.getRecordBean(grantid);
        String proposalnum = formatProjectNumber(theGrant.getFccode(), theGrant.getFycode(), theGrant.getProjseqnum());      
          
        //get pm name and email 
        OfficerBean mypo =enb.findProjectManager(grantid);            
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
        int wt=ez.draftStandardEmail(1045, fromLD, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
             
        //get an emaillog including "to" address
        EmailLogSend els=ez.createEmailLog(mypo.getEmail(), ccLitAdmin,"", userb.getUserid(), wt);
        
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
        System.err.println("error sendLiAmendmentSubmit() " + e.getMessage().toString());
        return msgSent;
      }        
      return true;
    }
    
    
  public String formatProjectNumber(int fccode, int fycode, long projseq)
  {
    String projNum="";
    try{    
       //get the project number for the grant      
       projNum = "03" + fccode + "-" + fycode + "-" + projseq;     
      
    }catch(Exception e){
      System.out.println("error formatProjectNumber "+e.getMessage().toString());
    }
    return projNum;
  }
     
}