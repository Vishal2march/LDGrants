/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerEmailBean.java
 * Creation/Modification History  :
 *
 * SH   8/6/07      Created
 *
 * Description
 * This class will handle the sending of all reviewer email messages for the system.  It has 
 * methods to send rating submitted email and warning emails.  
 * 
 * $Id: ReviewerEmailBean.java 4849 2009-06-23 14:42:53Z shusak $
 *****************************************************************************/
package mypackage;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
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

public class ReviewerEmailBean 
{  
  Connection conn;
  PreparedStatement prestmt;
  ResultSet rs; 
      
  String fromBL = "barbara.lilley@nysed.gov";
  String fromLD = "LibDevGrants@nysed.gov";
  String fromGAU = "archgrants@nysed.gov";
  EmailNotificationBean eb = new EmailNotificationBean();
    
  
  public ReviewerEmailBean()
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
  
  
  
  public boolean sedemsRatingSubmitEmail(ReviewerBean r, UserBean userb)
  {    
    //if po email not found - send notice to LD staff instead
      if(r.getEmail()==null || r.getEmail().equals(""))
      {
        System.err.println("error: No email address for "+r.getFname() +" "+ r.getLname());
        return false;
      } 
    
    try{        
       //get the project number for the grant
       String proposalnum = eb.formatProjectNumber(r.reviewerAssigns[0].getFccode(), r.reviewerAssigns[0].getFycode(), r.reviewerAssigns[0].getProjseqnum());      
                   
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        int wt=ez.draftStandardEmail(421, fromLD, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
         
        //get an emaillog including "to" address
        EmailLogSend els=ez.createEmailLog(r.getEmail(), "" ,"", userb.getUserid(), wt);
        
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
             evs.setValue(String.valueOf(r.reviewerAssigns[0].getGraid()));
           }
           else if(mvqs[i].getName().equals("$$reviewerName")){ 
             evs.setValue(r.getFname() +" "+ r.getLname());
           }
           else if(mvqs[i].getName().equals("$$revId")){ 
             evs.setValue(String.valueOf(r.getRevid()));
           }
           vVars.add(evs);
        }
        EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
        els.setEmailVariables(vararray);//add var values to the email log
            
        Vector myEmailLogs=new Vector();
        myEmailLogs.add(els);
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);       
           
    } catch(Exception e)
    {
      System.err.println("error sedemsRatingSubmitEMail() " + e.getMessage().toString());
      return false;
    }        
    return true;
  }
      
      
      
    public boolean sedemsLgRatingSubmitEmail(ReviewerBean r, UserBean userb)
    {    
      //if po email not found - send notice to Archives staff instead
        if(r.getEmail()==null || r.getEmail().equals(""))
        {
          System.err.println("error: No email address for "+r.getFname() +" "+ r.getLname());
          return false;
        } 
      
      try{        
         //get the project number for the grant
         String proposalnum = eb.formatProjectNumber(r.reviewerAssigns[0].getFccode(), r.reviewerAssigns[0].getFycode(), r.reviewerAssigns[0].getProjseqnum());      
                     
          SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getArchivesAuthBean());
    
          //create wt instance of st
          int wt=ez.draftStandardEmail(844, fromGAU, "");
          WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
           
          //get an emaillog including "to" address
          EmailLogSend els=ez.createEmailLog(r.getEmail(), "" ,"", userb.getUserid(), wt);
          
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
               evs.setValue(String.valueOf(r.reviewerAssigns[0].getGraid()));
             }
             else if(mvqs[i].getName().equals("$$reviewerName")){ 
               evs.setValue(r.getFname() +" "+ r.getLname());
             }
             else if(mvqs[i].getName().equals("$$revId")){ 
               evs.setValue(String.valueOf(r.getRevid()));
             }
             vVars.add(evs);
          }
          EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
          els.setEmailVariables(vararray);//add var values to the email log
              
          Vector myEmailLogs=new Vector();
          myEmailLogs.add(els);
          ez.updateEmailRecipients(wt,myEmailLogs);
          ez.sendDraft(wt);       
             
      } catch(Exception e)
      {
        System.err.println("error sedemsLgRatingSubmitEMail() " + e.getMessage().toString());
        return false;
      }        
      return true;
    }
        
  /* 3/27/13 No longer using - see EmailAssistDBbean
  public boolean sedemsParticipationMail(Vector allReviewers, int fccode, UserBean userb)
  {
    Vector myEmailLogs=new Vector();
    try {     
        String program="";
        if(fccode==5)
          program="Discretionary";
        else if(fccode==7)
          program="Coordinated";
          
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        int wt=ez.draftStandardEmail(443, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
               
        //for each reviewer create and send to queue a mail message
        for(int j=0; j<allReviewers.size();j++)
        {
          ReviewerBean rb = (ReviewerBean) allReviewers.get(j);
         
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", "" ,"", userb.getUserid(), wt);
            
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
               else if(mvqs[i].getName().equals("$$reviewerName")){ 
                 evs.setValue(rb.getFname() +" "+ rb.getLname());
               }
               else if(mvqs[i].getName().equals("$$username")){ 
                 evs.setValue(rb.getUsername());
               }
               else if(mvqs[i].getName().equals("$$revId")){ 
                 evs.setValue(String.valueOf(rb.getRevid()));
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                            
            myEmailLogs.add(els);           
        }
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);  
           
    }catch(Exception e) {
      System.err.println("error sedemsParticipationMail() "+ e.getMessage().toString());
      return false;
    }
    return true;
  }
 */
    
  
  /*
   * This method will send email to reviewers reminding them to fill out participation
   * and update contact info in the ldgrants system
   * @return 
   
  * REMOVED 3/27/13 - see EmailAssistDBbean
  public boolean sedemsParticipateRemind(Vector allReviewers, int fccode, UserBean userb)
  {
    Vector myEmailLogs=new Vector();
    try {     
        String program="";
        if(fccode==5)
          program="Discretionary";
        else if(fccode==7)
          program="Coordinated";
          
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        int wt=ez.draftStandardEmail(444, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
               
        //for each reviewer create and send to queue a mail message
        for(int j=0; j<allReviewers.size();j++)
        {
            ReviewerBean rb = (ReviewerBean) allReviewers.get(j);            
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", "" ,"", userb.getUserid(), wt);
            
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
               else if(mvqs[i].getName().equals("$$reviewerName")){ 
                 evs.setValue(rb.getFname() +" "+ rb.getLname());
               }
               else if(mvqs[i].getName().equals("$$username")){ 
                 evs.setValue(rb.getUsername());
               }
               else if(mvqs[i].getName().equals("$$revId")){ 
                 evs.setValue(String.valueOf(rb.getRevid()));
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                            
            myEmailLogs.add(els);  
          }
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);       
      
    }catch(Exception e){
      System.err.println("error sedemsParticipateRemind() "+ e.getMessage().toString());
      return false;
    }
    return true;
  }
  */
  
  /* No longer used as of 3/27/13 
   * 
   * gets all active reviewers for given fundcode. 
   * @return 
   * @param grantprogram
   
  public Vector getActiveReviewers(int grantprogram)
  {
    Vector results = new Vector();
    
    try{
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select id, salutation, fname, lname, user_id, active from REVIEWERS where "+
      " active='Y' and id in (select rev_id from reviewer_programs where fc_code=?)");
      prestmt.setInt(1, grantprogram);
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        ReviewerBean rb = new  ReviewerBean();
        rb.setSalutation(rs.getString("SALUTATION"));
        rb.setFname(rs.getString("FNAME"));
        rb.setLname(rs.getString("LNAME"));
        rb.setUsername(rs.getString("USER_ID"));
        rb.setRevid(rs.getLong("id"));
        results.add(rb);   
      }    
      
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select contact_value from contacts where rev_id=? and CONTACT_TYPE_CODE=3");
      
      for(int i=0; i<results.size(); i++)
      {
        ReviewerBean r = (ReviewerBean) results.get(i);
        prestmt.setLong(1, r.getRevid());
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
          r.setEmail(rs.getString("contact_value"));
        }
      }
      
      
    }catch(Exception e){
      System.err.println("error getActiveReviewers() "+ e.getMessage().toString());
    }
    finally
    {
      Close(conn);
      Close(prestmt);
      Close(rs);
    }
    return results;
  }
*/
  
  
  
  /**
     * new 3/21/13 gets active reviewers for given fundcode, used on updated cpAdminSelectRecipients page.
     * @param fundcode
     * @return
     */
  public ArrayList getReviewerRecipGroup(int fundcode)
  {
    ArrayList results = new ArrayList();
    
    try{
      conn = initializeConn();
      
      prestmt = conn.prepareStatement("select id, salutation, fname, lname, user_id, active from REVIEWERS where "+
      " active='Y' and id in (select rev_id from reviewer_programs where fc_code=?)");
      prestmt.setInt(1, fundcode);
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        GrantBean gb = new GrantBean();                    
            OfficerBean pm = new OfficerBean();
            pm.setFname(rs.getString("fname"));
            pm.setLname(rs.getString("lname"));        
        gb.setProjectManager(pm);
        gb.setName(pm.getFname() + " "+ pm.getLname());
          
        gb.setUserid(rs.getString("USER_ID"));
        gb.setReviewerId(rs.getLong("id"));   
          
        if(fundcode==5)
           gb.setProgram("Discretionary");
        else
           gb.setProgram("Coordinated");
          
        results.add(gb);       
      }    
      
      prestmt.clearParameters();
      prestmt = conn.prepareStatement("select contact_value from contacts where rev_id=? and CONTACT_TYPE_CODE=3");
      
      for(int i=0; i<results.size(); i++)
      {
        GrantBean g = (GrantBean) results.get(i);
        OfficerBean ob = g.getProjectManager();
        prestmt.setLong(1, g.getReviewerId());
        rs = prestmt.executeQuery();
        
        while(rs.next())
        {
          g.setEmail(rs.getString("contact_value"));
          ob.setEmail(g.getEmail());                 
        }
      }      
      
    }catch(Exception e){
      System.err.println("error getReviewerRecipGroup() "+ e.getMessage().toString());
    }
    finally
    {
      Close(conn);
      Close(prestmt);
      Close(rs);
    }
    return results;
  }
  
  
  /**
     *new 3/21/13, method gets all reviewers assigned to projects for given fy and fundprogram. used on the
     * updated cpAdminSelectRecipient page to send email to these reviewers
     * @param fycode
     * @param fccode
     * @return
     */
  public ArrayList getReviewerAssignRecipGroup(int fycode, int fccode)
  {
    ArrayList results = new ArrayList();    
    try{             
      conn = initializeConn();

      prestmt = conn.prepareStatement("select reviewers.id, salutation, fname, lname, user_id, contact_value from "+
      " reviewers, contacts where reviewers.id = contacts.rev_id and contact_type_code=3 and rev_id in "+
      " (select rev_id from grant_assigns, grants where grant_assigns.GRA_ID=grants.id and fy_code=? and fc_code=?)");
           
      prestmt.setInt(1, fycode);
      prestmt.setInt(2, fccode);
      rs = prestmt.executeQuery();
      while(rs.next())
      {        
          GrantBean gb = new GrantBean();
              OfficerBean pm = new OfficerBean();
              pm.setFname(rs.getString("fname"));
              pm.setLname(rs.getString("lname"));   
              pm.setEmail(rs.getString("contact_value"));
          gb.setProjectManager(pm);
          gb.setName(pm.getFname() + " "+ pm.getLname());
          gb.setEmail(pm.getEmail());
          gb.setReviewerId(rs.getLong("id"));
          gb.setUserid(rs.getString("user_id"));
          
          if(fccode==5)
              gb.setProgram("Discretionary");
          else
              gb.setProgram("Coordinated");
          results.add(gb);        
      }    
              
    }catch(Exception e) {
      System.err.println("error getReviewerAssignRecipGroup() " + e.getMessage().toString());
    }
    finally{
      Close(prestmt);
      Close(conn);
      Close(rs);
    }    
    return results;
  }
  

  /* REMOVED 3/27/13 - see EmailAssistDBbean  
  public boolean sedemsCpAssignEmail(Vector reviewers, int fy, String duedate, int fccode, UserBean userb)
  {  
    Vector myEmailLogs=new Vector();
    
    try{ 
        String program="";
        if(fccode==5)
          program="Discretionary";
        else if(fccode==7)
          program="Coordinated";
          
        SEDEMSEZClass ez=new SEDEMSEZClass(userb.getUserid(), userb.getAuthBean());
  
        //create wt instance of st
        int wt=ez.draftStandardEmail(442, fromBL, "");
        WorkingTemplateQuery wtq=ez.viewUnsentMailMessage(wt);
                     
        //for each reviewer, get the grants they  have been assigned and compose msg
        for(int k=0; k<reviewers.size(); k++)
        {
            //ReviewerAssignBean rb = (ReviewerAssignBean) reviewers.get(k);
            ReviewerBean rb = (ReviewerBean) reviewers.get(k);
            ArrayList grantnums = getAssignedGrantInfo(rb.getRevid(), fy, fccode);//get each project number assigned to this reviewer
                                                      
            StringBuffer msg = new StringBuffer();              
            //print out all grant project nums they have been assigned
            for(int j=0; j<grantnums.size(); j++)
              msg.append((String) grantnums.get(j) + "\n");
                        
            //get an emaillog including "to" address
            EmailLogSend els=ez.createEmailLog("stefanie.husak@nysed.gov", "" ,"", userb.getUserid(), wt);
            
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
                 evs.setValue(msg.toString());
               }
               else if(mvqs[i].getName().equals("$$program")){ 
                 evs.setValue(program);
               }
               else if(mvqs[i].getName().equals("$$reviewerName")){ 
                 evs.setValue(rb.getFname() +" "+ rb.getLname());
               }
               else if(mvqs[i].getName().equals("$$username")){ 
                 evs.setValue(rb.getUsername());
               }
               else if(mvqs[i].getName().equals("$$dueDate")){ 
                 evs.setValue(duedate);
               }
               else if(mvqs[i].getName().equals("$$revId")){ 
                 evs.setValue(String.valueOf(rb.getRevid()));
               }
               vVars.add(evs);
            }
            EmailVarSend[] vararray = (EmailVarSend[]) vVars.toArray(new EmailVarSend[0]);
            els.setEmailVariables(vararray);//add var values to the email log
                            
            myEmailLogs.add(els);                          
        }
        ez.updateEmailRecipients(wt,myEmailLogs);
        ez.sendDraft(wt);      
 
    }catch(Exception e){
      System.err.println("error sedemsCpAssignEmail() "+ e.getMessage().toString());
      return false;
    }   
    return true;
  }
 */

  public ArrayList getAssignedGrantInfo(long revid, int fy, int fccode)
  {
    ArrayList allgrants = new ArrayList();
    int fc=0;;
    long projseq=0;
    int year=0;
    
    try{
      conn = initializeConn();
      prestmt = conn.prepareStatement("select fy_code, fc_code, proj_seq_num from grants, "+
        " grant_assigns where grants.id = grant_assigns.gra_id and rev_id=? and fy_code=? and fc_code=? ");
      prestmt.setLong(1, revid);
      prestmt.setInt(2, fy);
      prestmt.setInt(3, fccode);
      rs = prestmt.executeQuery();
      
      while(rs.next())
      {
        year = rs.getInt("FY_CODE");
        fc = rs.getInt("FC_CODE");
        projseq = rs.getLong("PROJ_SEQ_NUM");

       //get the project number for the grant
       String proposalnum = eb.formatProjectNumber(fc, year, projseq);                  
       allgrants.add(proposalnum);
      }      
    }catch(Exception e){
      System.err.println("error getAssignedGrantInfo() "+ e.getMessage().toString());
    }     
    finally{
      Close(conn);
      Close(prestmt);
      Close(rs);
    }
    return allgrants;
  }   
}