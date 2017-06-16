/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 1.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  EmailMassNotifyBean.java
 * Creation/Modification History  :
 * SH   1/5/08      Created
 *
 * Description
 * This bean will handle creating email logs through sedems for the cp/lit mass 
 * emails (app approved/denied).  Most methods have a vector param, and create
 * email logs for each grantbean in the vector.  
 *****************************************************************************/
package mypackage;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
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
import sedems.WorkingTemplateQuery;


public class EmailMassNotifyBean 
{  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;   
  //String fromBL=null;
  EmailNotificationBean eb = new EmailNotificationBean();
  
  public EmailMassNotifyBean()
  {
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
  
  
  public Vector getEmailGrants(int fy, int fc, String version, String approvalType)
  {
    Vector results = new Vector();
    
    try{
      conn = initializeConn();                   

      ps = conn.prepareStatement("select grants.id, name, proj_seq_num, fy_code, fc_code, initcap(popular_name) as popular_name "+
      " from grants,  approvals, co_institutions left join sed_institutions on "+
      " sed_institutions.inst_id= co_institutions.inst_id	where grants.id = approvals.gra_id and "+
      " grants.id = co_institutions.gra_id and is_lead='Y' and	fy_code=? and fc_code=? and version=? "+
      " and approval_type=? order by popular_name");          
      ps.setInt(1, fy);
      ps.setInt(2, fc);
      ps.setString(3, version);
      ps.setString(4, approvalType);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();
        gb.setGrantid(rs.getLong("ID"));
        gb.setTitle(rs.getString("NAME"));
        gb.setProjseqnum(rs.getLong("PROJ_SEQ_NUM"));
        gb.setFycode(rs.getInt("FY_CODE"));
        gb.setFccode(rs.getInt("FC_CODE"));     
        gb.setInstName(rs.getString("POPULAR_NAME"));
             
        results.add(gb);
      }           
           
    } catch(Exception e){
      System.err.println("error getEmailGrants() " + e.getMessage().toString());
    }
    finally{
      Close(rs);
      Close(conn);
      Close(ps);
    }    
    return results;
  }  
  
  /*public boolean processInitialApproval(Vector allGrants, UserBean userb)
  {
    boolean msgSent = false; 
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
    try{    
      
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      int wt=ez.draftStandardEmail(404, fromBL, "");
      //int wt=ez.draftStandardEmail(404);
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
      
      //update template to include "from" email
     // wt = ez.updateEmail(fromBL, "", wtq.getMessage(), wtq.getSubject(), wt);
                       
      for(int j=0; j<allGrants.size(); j++)
      {
          GrantBean gb = (GrantBean) allGrants.get(j);  
          //get the project number for the grant
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                     
          //stat and coor emails go to PO, disc emails go to PM
          OfficerBean mypo =new OfficerBean();
          if(gb.getFccode()==7 || gb.getFccode()==6)
            mypo=eb.findPresOfficer(gb.getGrantid());   
          else if(gb.getFccode()==5)
            mypo=eb.findProjectManager(gb.getGrantid());
            
          if(mypo.getEmail()==null || mypo.getEmail().equals(""))
          {
            noemail.append(" "+proposalnum);
          }
          else{                  
            //get the total amount approved for this grant
            BudgetDBHandler bdh = new BudgetDBHandler();
            int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);    
                           
            //format the amtapproved with commas
            NumberFormat numF = new DecimalFormat("###,###");
            String appr= String.valueOf(amtappr);
            long appr_amt = Long.parseLong(appr);
            appr = numF.format(appr_amt);          
                                 
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", ccb ,"", userb.getUserid(), wt);
            
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
                 evs.setValue(gb.getFiscalyear());
               }
               else if(mvqs[i].getName().equals("$$projectNum")){ 
                 evs.setValue(proposalnum);
               }
               else if(mvqs[i].getName().equals("$$grantId")){ 
                 evs.setValue(String.valueOf(gb.getGrantid()));
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
                
            //add to vector of all email logs
            myEmailLogs.add(els);      
          }
      }
      
      //finally, update all email logs - then send wt
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);
      
      if(noemail.length()>0)
      {
        eb.sedemsAddressMissing(userb, noemail.toString());
      }
                  
    } catch(Exception e){
      System.err.println("error processInitialApproval() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }*/
  
  public String getProgramName(EmailHelpBean ehb)
  {
    String msg="";
    try{
      if(ehb.getFccode()==40)
        msg="Adult Literacy Library Services";
      else if(ehb.getFccode()==42)
        msg="Family Literacy Library Services";
    }
    catch(Exception e){System.out.println("error EmailMassNotify "+e.getMessage().toString());}
    return msg;
  }
  
  public int processLitInitialApproval(Vector allGrants, UserBean userb, EmailHelpBean ehb)
  {
    int wt=0;
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
    try{    
      String program = getProgramName(ehb);
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      wt=ez.draftStandardEmail(482, ehb.getFrom(), "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
      DbName dbn = new DbName();
      
                       
      for(int j=0; j<allGrants.size(); j++)
      {
          GrantBean gb = (GrantBean) allGrants.get(j);  
          //get the project number for the grant
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                     
          //emails go to PM
          OfficerBean mypo =eb.findProjectManager(gb.getGrantid());            
          if(mypo.getEmail()==null || mypo.getEmail().equals(""))
          {
            noemail.append(" "+proposalnum);
          }
          else{                  
            //get the total amount approved for this grant
            BudgetDBHandler bdh = new BudgetDBHandler();
            //int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);//total both years
            int year1 = bdh.calcTotalAmtApproved(gb.getGrantid(), gb.getFycode());
            int year2 = bdh.calcTotalAmtApproved(gb.getGrantid(), gb.getFycode()+1);
            int amtappr = year1 + year2;
            //format the amtapproved with commas     
            NumberFormat numF = NumberFormat.getIntegerInstance(Locale.US);               
                                                         
            //get an emaillog including "to" address
            String to="";
            if(dbn.production==true)
                to = mypo.getEmail();
            else
                to = "stefanie.husak@nysed.gov";
            EmailLogSend els=ez.createEmailLog(to, ehb.getCc() ,"", userb.getUserid(), wt);
              
            
            //create values for vars
            MessageVarQuery[] mvqs=wtq.getMessagevariables();
            EmailVarSend evs=null;
            //loop thru each message var
            Vector vVars=new Vector();
            for(int i=0;i<mvqs.length;i++)
            {
               evs=new EmailVarSend();
               evs.setMessageVarId(mvqs[i].getId());
               if(mvqs[i].getName().equals("$$program")){
                 evs.setValue(program);
               }
               else if(mvqs[i].getName().equals("$$projectNum")){ 
                 evs.setValue(proposalnum);
               }
               else if(mvqs[i].getName().equals("$$grantId")){ 
                 evs.setValue(String.valueOf(gb.getGrantid()));
               }               
               else if(mvqs[i].getName().equals("$$managerName")){ 
                 evs.setValue(mypo.getFname() +" "+ mypo.getLname());
               }
               else if(mvqs[i].getName().equals("$$amtApproved")){ 
                 evs.setValue("$"+ numF.format(amtappr));
               }
               else if(mvqs[i].getName().equals("$$year1")){ 
                 evs.setValue("$"+ numF.format(year1));
               }
               else if(mvqs[i].getName().equals("$$year2")){ 
                 evs.setValue("$"+ numF.format(year2));
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                
            //add to vector of all email logs
            myEmailLogs.add(els);      
          }
      }
      
      //finally, update all email logs 
      ez.updateEmailRecipients(wt,myEmailLogs);
      //ez.sendDraft(wt);DO NOT SEND, ALLOW FOR UPDATE FIRST 5/27/09
      
      if(noemail.length()>0)
      {
        eb.sedemsAddressMissing(userb, noemail.toString());
      }
                  
    } catch(Exception e){
      System.err.println("error processLitInitialApproval() " + e.getMessage().toString());
      return wt;
    }        
    return wt;
  }
    
 /*public boolean processFinalApproval(Vector allGrants, UserBean userb)
 {
    boolean msgSent = false; 
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
   try{
   
      SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());

      //create wt instance of st
      int wt=ez.draftStandardEmail(405, fromBL, "");
      WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
      
             
      for(int j=0; j<allGrants.size(); j++)
      {
          GrantBean gb = (GrantBean) allGrants.get(j);  
          //get the project number for the grant
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                      
           //stat and coor emails go to PO, disc emails go to PM
          OfficerBean mypo =new OfficerBean();
          if(gb.getFccode()==7 || gb.getFccode()==6)
            mypo=eb.findPresOfficer(gb.getGrantid());   
          else if(gb.getFccode()==5)
            mypo=eb.findProjectManager(gb.getGrantid()); 
                        
          if(mypo.getEmail()==null || mypo.getEmail().equals(""))
          {
            noemail.append(" "+proposalnum);
          }
          else{
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", ccb ,"", userb.getUserid(), wt);
            
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
                 evs.setValue(gb.getFiscalyear());
               }
               else if(mvqs[i].getName().equals("$$projectNum")){ 
                 evs.setValue(proposalnum);
               }
               else if(mvqs[i].getName().equals("$$grantId")){ 
                 evs.setValue(String.valueOf(gb.getGrantid()));
               }
               else if(mvqs[i].getName().equals("$$managerName")){ 
                 evs.setValue(mypo.getFname() +" "+ mypo.getLname());
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                      
            myEmailLogs.add(els);
          }
      }
      
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);
      
      if(noemail.length()>0)
      {
        eb.sedemsAddressMissing(userb, noemail.toString());
      }
      
    } catch(Exception e) {
      System.err.println("error processFinalCoApproval() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
 }*/
 
 
  /*public boolean processAppDenied(Vector allGrants, UserBean userb)
  {
    boolean msgSent = false; 
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
        
    try{    
       SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        int wt=ez.draftStandardEmail(406, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
        
               
      for(int j=0; j<allGrants.size(); j++)
      {
          GrantBean gb = (GrantBean) allGrants.get(j);      
          //get the project number for the grant
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
          
                
          //stat and coor emails go to PO
          OfficerBean mypo =new OfficerBean();
          if(gb.getFccode()==7 || gb.getFccode()==6)
            mypo=eb.findPresOfficer(gb.getGrantid());   
          else if(gb.getFccode()==5)
            mypo=eb.findProjectManager(gb.getGrantid());   
           
           if(mypo.getEmail()==null || mypo.getEmail().equals(""))
           {
             noemail.append(" "+proposalnum);
           }
           else{                       
              //get an emaillog including "to" address
              EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", ccb ,"", userb.getUserid(), wt);
              
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
                   evs.setValue(gb.getFiscalyear());
                 }
                 else if(mvqs[i].getName().equals("$$projectNum")){ 
                   evs.setValue(proposalnum);
                 }
                 else if(mvqs[i].getName().equals("$$grantId")){ 
                   evs.setValue(String.valueOf(gb.getGrantid()));
                 }
                 else if(mvqs[i].getName().equals("$$managerName")){ 
                   evs.setValue(mypo.getFname() +" "+ mypo.getLname());
                 }
                 vVars.add(evs);
              }
              EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
              els.setEmailVariables(vararray);//add var values to the email log
                              
              myEmailLogs.add(els);
           }
        }        
        
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);
        
        if(noemail.length()>0)
        {
          eb.sedemsAddressMissing(userb, noemail.toString());
        }
      
    } catch(Exception e){
      System.err.println("error processAppDenied() " + e.getMessage().toString());
      return msgSent;
    }        
    return true;
  }  */
  
  
  public int processLitAppDenied(Vector allGrants, UserBean userb, EmailHelpBean ehb)
  {
    int wt=0;
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
        
    try{    
       String program = getProgramName(ehb);
       SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
       DbName dbn = new DbName();
        
        //create wt instance of st
        wt=ez.draftStandardEmail(481, ehb.getFrom(), "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
        
              
      for(int j=0; j<allGrants.size(); j++)
      {
          GrantBean gb = (GrantBean) allGrants.get(j);      
          //get the project number for the grant
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                          
          OfficerBean mypo =eb.findProjectManager(gb.getGrantid());   
           
           if(mypo.getEmail()==null || mypo.getEmail().equals(""))
           {
             noemail.append(" "+proposalnum);
           }
           else{                       
              //get an emaillog including "to" address
              String to="";
              if(dbn.production==true)
                  to = mypo.getEmail();
              else
                  to = "stefanie.husak@nysed.gov";
              EmailLogSend els=ez.createEmailLog(to, ehb.getCc() ,"", userb.getUserid(), wt);
              
              //create values for vars
              MessageVarQuery[] mvqs=wtq.getMessagevariables();
              EmailVarSend evs=null;
              //loop thru each message var
              Vector vVars=new Vector();
              for(int i=0;i<mvqs.length;i++)
              {
                 evs=new EmailVarSend();
                 evs.setMessageVarId(mvqs[i].getId());
                 if(mvqs[i].getName().equals("$$program")){
                   evs.setValue(program);
                 }
                 else if(mvqs[i].getName().equals("$$projectNum")){ 
                   evs.setValue(proposalnum);
                 }
                 else if(mvqs[i].getName().equals("$$grantId")){ 
                   evs.setValue(String.valueOf(gb.getGrantid()));
                 }
                 else if(mvqs[i].getName().equals("$$managerName")){ 
                   evs.setValue(mypo.getFname() +" "+ mypo.getLname());
                 }
                 vVars.add(evs);
              }
              EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
              els.setEmailVariables(vararray);//add var values to the email log
                              
              myEmailLogs.add(els);
           }
        }        
        
        ez.updateEmailRecipients(wt,myEmailLogs);
        //ez.sendDraft(wt);DO NOT SEND, ALLOW FOR EDITING 5/27/09
        
        if(noemail.length()>0)
        {
          eb.sedemsAddressMissing(userb, noemail.toString());
        }
      
    } catch(Exception e){
      System.err.println("error processLitAppDenied() " + e.getMessage().toString());
      return wt;
    }        
    return wt;
  }  
  
  
 /* public boolean processCoIntentAward(Vector allGrants, UserBean userb)
  {
    boolean msgSent = false; 
    Vector myEmailLogs=new Vector();
    StringBuffer noemail = new StringBuffer();
   
   try {
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());    
        //create wt instance of st
        int wt=ez.draftStandardEmail(407, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                            
        for(int j=0; j<allGrants.size(); j++)
        {
          GrantBean gb = (GrantBean) allGrants.get(j);  
          //get the project number
          String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                      
          //get po name and email -send all emails to po
          OfficerBean mypo =eb.findPresOfficer(gb.getGrantid());              
               
          if(mypo.getEmail()==null || mypo.getEmail().equals(""))
          {
            noemail.append(" "+proposalnum);
          }
          else{
            //get the total amount approved for this grant
            BudgetDBHandler bdh = new BudgetDBHandler();
            int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);           
          
            //format the amtapproved with commas and dollar sign
            NumberFormat numF = new DecimalFormat("###,###");
            String appr= String.valueOf(amtappr);
            long appr_amt = Long.parseLong(appr);
            appr = numF.format(appr_amt);
                                     
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", ccb ,"", userb.getUserid(), wt);
            
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
                 evs.setValue(String.valueOf(gb.getGrantid()));
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
                
            myEmailLogs.add(els);
          }
      }
      
      ez.updateEmailRecipients(wt,myEmailLogs);
      ez.sendDraft(wt);         
      
      if(noemail.length()>0)
      {
        eb.sedemsAddressMissing(userb, noemail.toString());
      }
            
    } catch(Exception e){
      System.err.println("error processCoIntentAward() " + e.getMessage().toString());
      return msgSent;
    }    
    return true;
  } */
  
    
    /*public boolean processDiProvisionalAward(Vector allGrants, UserBean userb)
    {
      boolean msgSent = false; 
      Vector myEmailLogs=new Vector();
      StringBuffer noemail = new StringBuffer();
     
     try {
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());    
          //create wt instance of st
          int wt=ez.draftStandardEmail(701, fromBL, "");
          WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                               
          for(int j=0; j<allGrants.size(); j++)
          {
            GrantBean gb = (GrantBean) allGrants.get(j);  
            //get the project number for the grant
            String proposalnum = eb.formatProjectNumber(gb.getFccode(), gb.getFycode(), gb.getProjseqnum());      
                        
            //get the pm name/email
            OfficerBean mypo =eb.findProjectManager(gb.getGrantid());              
                 
            if(mypo.getEmail()==null || mypo.getEmail().equals(""))
            {
              noemail.append(" "+proposalnum);
            }
            else{
              //get the total amount approved for this grant
              BudgetDBHandler bdh = new BudgetDBHandler();
              int amtappr = bdh.calcTotalAmtApproved(gb.getGrantid(),0);           
            
              //format the amtapproved with commas and dollar sign
              NumberFormat numF = new DecimalFormat("###,###");
              String appr= String.valueOf(amtappr);
              long appr_amt = Long.parseLong(appr);
              appr = numF.format(appr_amt);
                                       
              //get an emaillog including "to" address
              EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", ccb ,"", userb.getUserid(), wt);
              
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
                   evs.setValue(String.valueOf(gb.getGrantid()));
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
                  
              myEmailLogs.add(els);
            }
        }
        
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);         
        
        if(noemail.length()>0)
        {
          eb.sedemsAddressMissing(userb, noemail.toString());
        }
              
      } catch(Exception e){
        System.err.println("error processDiProvisionalAward() " + e.getMessage().toString());
        return msgSent;
      }    
      return true;
    }*/
   
}