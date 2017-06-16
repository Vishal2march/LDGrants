package ldgext;
import java.util.Vector;
import mypackage.DbName;
import sedems.AuthenticateBean;
import sedems.EmailLogSend;
import sedems.SEDEMSEZClass;

public class RegistrationMailBean 
{
  String testFlag = "";
  DbName db = new DbName();
  AuthenticateBean ab=new AuthenticateBean();    
  String ccs = "stefanie.husak@nysed.gov";
  String told = "LibDevGrants@nysed.gov";
  String toarch = "archgrants@nysed.gov";
  
  String registerSubject="Request registration Online Grant System";
  String testMessage = "\r----This is a test message of the OCE Online Grant System.----\r";
  

  public RegistrationMailBean()
  {
    if(db.production==true)
      testFlag = "";
    else
      testFlag = "TEST ";
  
    ab.setApplicationId(61);//TREF/PREF
    ab.setName("LDGRANTS");
    ab.setPassword("LDGRANTSPWD");
    
    if(db.production==true)
        ab.setProviderURL("http://eservices.nysed.gov/sedems/EmailWebService?wsdl");
    else
        ab.setProviderURL("http://eservicest.nysed.gov/sedems/EmailWebService?wsdl");
  }
  
  
  
    
  public boolean sendRegistrationMail(RegisterBean rb)
  {      
    try{                      
      String subject = testFlag + registerSubject;
      String from = rb.getEmail();
      boolean islgrmif = false;
      if(rb.getGrantprogram()!=null && rb.getGrantprogram().equals("lgrmif"))
        islgrmif =true;
      
     //create the string that will make up the body of the email
      StringBuffer msg = new StringBuffer();
      if(db.production==false)
        msg.append(testMessage + "\n\n");  
      
      if(islgrmif)
        msg.append("Request for NYS LGRMIF Online Grant System user account:\n\n");
      else
        msg.append("Request for NYS Division of Library Development Online Grant System user account:\n\n");
      
      msg.append("First, Last name:  "+ rb.getFname() + "  " + rb.getLname());
      msg.append("\nTitle:  "+rb.getTitle());
      msg.append("\nPhone:  "+ rb.getPhone());
      msg.append("\nEmail:  "+ rb.getEmail());
      msg.append("\nInstitution:  " + rb.getInstName());
      msg.append("\nUnit Name:  " +rb.getLibName());
      msg.append("\nAddress:  "+ rb.getAddr1());
      msg.append("\nAddress2:  "+ rb.getAddr2());
      msg.append("\nCity State:  "+ rb.getCity() +"  "+ rb.getState());
      msg.append("\nZipcode:  "+rb.getZipcd());  
      msg.append("\nCounty:  "+rb.getCounty());
      msg.append("\nSchool District:  "+rb.getSchdistrict());
      msg.append("\nFederal ID:  "+ rb.getFedid());
      msg.append("\n\nACCESS LEVEL");
      msg.append("\nReadAccess:  "+rb.isReadaccess());
      msg.append("\nEditAccess:  "+rb.isEditaccess());
      msg.append("\nSubmitAccess:  "+rb.isSubmitaccess());
      msg.append("\n\nGRANT PROGRAM");
      msg.append("\nCP Discretionary:  "+rb.isCpdiscretionary());
      msg.append("\nAdult Literacy: "+rb.isAlliteracy());
      msg.append("\nFamily Literacy: "+rb.isFmliteracy());
      msg.append("\nConstruction:  "+rb.isPlconstruction());
      msg.append("\nLGRMIF: "+islgrmif);
      msg.append("\nNew LGRMIF RMO: "+rb.isNewRmo());
           
      String username = rb.getFname().trim()+"."+rb.getLname().trim();
      SEDEMSEZClass ez=new SEDEMSEZClass(username, ab);
        
      int wt=ez.draftTemplateAndVars(from,"",subject,msg.toString(),"");
      Vector v = new Vector();
      //need to send "TO" ld vs archives
      String to="";
      if(islgrmif)
        to=toarch;
      else
        to=told;
        
      EmailLogSend els=ez.createEmailLog(to, "",ccs,username,wt);
      v.add(els);
      ez.updateEmailRecipients(wt,v);   
      ez.sendDraft(wt);     
             
      
    } catch(Exception e){
      System.err.println("error sendRegistrationMail() " + e.getMessage().toString());
      return false;
    }        
    return true;
  }
    
  /*public boolean sendLitRegistrationMail(RegisterBean rb)
  {      
    try{                      
      String subject = testFlag + litRegisterSubject;
      String from = rb.getEmail();
      
     //create the string that will make up the body of the email
      StringBuffer msg = new StringBuffer();
      if(db.production==false)
        msg.append(testMessage + "\n\n");  
      
      msg.append("The following Literacy grant user account has been requested:\n\n");
      msg.append("First, Last name:  "+ rb.getFname() + "  " + rb.getLname());
      msg.append("\nTitle:  "+rb.getTitle());
      msg.append("\nPhone:  "+ rb.getPhone());
      msg.append("\nEmail:  "+ rb.getEmail());
      msg.append("\nInstitution:  " + rb.getInstName());
      msg.append("\nLibrary Name:  " +rb.getLibName());
      msg.append("\nAddress:  "+ rb.getAddr1());
      msg.append("\nAddress2:  "+ rb.getAddr2());
      msg.append("\nCity State:  "+ rb.getCity() +"  "+ rb.getState());
      msg.append("\nZipcode:  "+rb.getZipcd());  
      msg.append("\nCounty:  "+rb.getCounty());
      msg.append("\nSchool District:  "+rb.getSchdistrict());
      msg.append("\nFederal ID:  "+ rb.getFedid());
      msg.append("\nAdult Literacy: "+rb.isAlliteracy());
      msg.append("\nFamily Literacy: "+rb.isFmliteracy());
      msg.append("\nReadAccess:  "+rb.isReadaccess());
      msg.append("\nEditAccess:  "+rb.isEditaccess());
      msg.append("\nSubmitAccess:  "+rb.isSubmitaccess());
           
      Mailer(to, ccs, from, subject, msg.toString());        
    } catch(Exception e){
      System.err.println("error sendLitRegistrationMail() " + e.getMessage().toString());
      return false;
    }        
    return true;
  }*/  

  /*
  private boolean Mailer(String to, String copy, String from, String subj, String body) 
  {    
     EMailMessage notify =new EMailMessage("listserv.nysed.gov", to, copy, null, from, subj, body, false);
     
     System.out.println("acct req: "+from);
     MailScheduler.getInstance().enqueue(notify);  // Adds to the mail scheduler queue.
     return true;
  }*/  
  
}
