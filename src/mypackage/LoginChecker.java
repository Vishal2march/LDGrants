 /******************************************************************************
  * @author  : Stefanie Husak
  * @Version : 2.0
  *
  * Development Environment        :  JDeveloper 10g
  * Name of the file               :  LoginChecker.java
  * Creation/Modification History  :
  *
  * SH   2/1/07      Created
  *
  * Description
  * This class will determine the user's role and access privileges based on thier
  * ldap http headers (if running on the server) or based on the parameters sent
  * in the request (if running locally).  The ldap header check is performed using
  * the SSOProcessor Bean.  The user info is saved in a UserBean in the session.
  * 
  * $Id: LoginChecker.java 4849 2009-06-23 14:42:53Z shusak $
  *****************************************************************************/
 package mypackage;
 import gov.nysed.oce.ldgrants.user.domain.User;
import gov.nysed.osa.datacollection.beans.SSOprocessorBean;
 import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
 import javax.servlet.http.*;
 import java.io.PrintWriter;
 import java.io.IOException;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import sedems.AuthenticateBean;

 public class LoginChecker extends HttpServlet 
 {
   private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
   private Log log = LogFactory.getLog(this.getClass());
   String sedemsTest = "http://eservicest.nysed.gov/sedems/EmailWebService?wsdl";
   String sedemsProd = "http://eservices.nysed.gov/sedems/EmailWebService?wsdl";
   
   
   
       
   public void init(ServletConfig config) throws ServletException
   {
     super.init(config);
   }
   
   /**
    * SH version 3/9/07
    * This servlet will determine whether the app is running locally or on the server.
    * If locally - then get temporary user attributes from the request.
    * If server - then get the user's attributes from the ldap http headers.
    * The userbean will determine the roles/permissions based on the user's attributes.
    * The userbean is then set in session, to be accessed by all other jsp pages.
    * @throws java.io.IOException
    * @throws javax.servlet.ServletException
    * @param response
    * @param request
    */
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {       
     //Use these to store user headers we get from LDAP
     String userid = "";
     String instid = "";
     String prgsa_tmp = "";
     String prgco_tmp = "";
     String prgdi_tmp = "";  
     String prgfl_tmp ="";
     String prgal_tmp ="";
     String admin1_tmp = "";
     String admin2_tmp = "";  
     String reviewer_tmp ="";
     String lgadmin="", lgrev="", lgsubmit="", lgedit="", lgread="";
     String lgconstruction="";   
     
     try{
     UserBean lduser = new UserBean();      
     HttpSession sess = request.getSession(true);
   
     //Get the hostname we are logged on as. Comes from the java.net.InetAddress packages
     InetAddress addr = InetAddress.getLocalHost();
     String hostname = addr.getHostName();
         
    // System.out.println("hostname is: "+hostname);
    // if(hostname.indexOf("akeller8300")!=0) //we're on the server


    //Removed the hardcoded value & now checking for windows 7
    // if not windows 7, must be running on app server, else running locally         
    String OS = System.getProperty("os.name").toLowerCase();
    if(!OS.equalsIgnoreCase("windows 7"))

     {      
       System.out.println("create SSO bean...");
       SSOprocessorBean loginBean = new SSOprocessorBean(request);      
       
       //Get the values from the bean and store them locally
       userid = loginBean.getAttribute("SM_USER"); 
       instid = loginBean.getAttribute("instid");
       admin1_tmp = loginBean.getAttribute("admin1");
       admin2_tmp = loginBean.getAttribute("admin2");      
       prgsa_tmp = loginBean.getAttribute("prgsa");
       prgco_tmp = loginBean.getAttribute("prgco");
       prgdi_tmp = loginBean.getAttribute("prgdi");        
       reviewer_tmp = loginBean.getAttribute("review");
       prgfl_tmp = loginBean.getAttribute("prgfl");   
       prgal_tmp = loginBean.getAttribute("prgal");   
       lgconstruction = loginBean.getAttribute("prgcn");
        //the following attributes are for lgrmif only
       lgadmin = loginBean.getAttribute("saadmin");
       lgrev = loginBean.getAttribute("sareview");
       lgsubmit = loginBean.getAttribute("prgsubmit"); 
       lgedit = loginBean.getAttribute("prgedit");
       lgread = loginBean.getAttribute("prgread");
       
     } else{ //OS is Windows 7, must be running on the local pc
         
       //Get the values from request variables         
       userid = request.getParameter("SM_USER");   
       instid = request.getParameter("INSTID");
       admin1_tmp = request.getParameter("ADMIN1");
       admin2_tmp = request.getParameter("ADMIN2");      
       prgsa_tmp = request.getParameter("PRGSA");
       prgco_tmp = request.getParameter("PRGCO");
       prgdi_tmp = request.getParameter("PRGDI");    
       prgfl_tmp = request.getParameter("PRGFL");
       prgal_tmp = request.getParameter("PRGAL");   
       reviewer_tmp = request.getParameter("REVIEW");   
       lgconstruction = request.getParameter("PRGCN");
       //the following attributes are for lgrmif only
       lgadmin = request.getParameter("SAADMIN");
       lgrev = request.getParameter("SAREVIEW");
       lgsubmit = request.getParameter("PRGSUBMIT"); 
       lgedit = request.getParameter("PRGEDIT");
       lgread = request.getParameter("PRGREAD");      
     }       
     
    
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();
    String strDate = sdfDate.format(now);
     System.out.println(strDate +":  Attempting to login user: "+userid);
     
     
     //set the values to user bean
     lduser.setUserid(userid);
     if(instid==null || instid.equals(""))
       lduser.setInstid(0);
     else
       lduser.setInstid(Long.parseLong(instid));  ///set institution ID 
     
     //evaluate info passed in ldap headers and assign user's privilege level to userbean
     lduser.checkAdminRoles(admin1_tmp, admin2_tmp, reviewer_tmp); 
     
     ///assigns read,edit,submit permission in user bean from various grant programs
     lduser.checkApplicantRoles(prgsa_tmp, prgco_tmp, prgdi_tmp,lgconstruction, instid);
     lduser.checkLgrmifRoles(lgrev, lgadmin, lgsubmit, lgedit, lgread);
     lduser.checkLiteracyRoles(prgfl_tmp, prgal_tmp);
         
      ///////////////check database///////////////////////////////
      DbName db = new DbName();
      if (!db.isProduction()) 
           sess.setAttribute("isProd", "No");
      else 
           sess.setAttribute("isProd", "Yes");
      
         
         
     //*************************************************************************
     //sedems connection saved to UserBean in session
     AuthenticateBean ab=new AuthenticateBean();
     ab.setApplicationId(61);//TREF/PREF
     ab.setName("LDGRANTS");
     ab.setPassword("LDGRANTSPWD");
     if(!db.isProduction())
         ab.setProviderURL(sedemsTest);
     else
         ab.setProviderURL(sedemsProd);
     lduser.setAuthBean(ab);
         
     AuthenticateBean ab1=new AuthenticateBean();
     ab1.setApplicationId(121);//TREF/PREF
     ab1.setName("SAGRANTS");
     ab1.setPassword("SAGRANTSPWD");
     if(!db.isProduction())
        ab1.setProviderURL(sedemsTest);
     else
         ab1.setProviderURL(sedemsProd);
     lduser.setArchivesAuthBean(ab1);
         
     AuthenticateBean ab2 =new AuthenticateBean();
     ab2.setApplicationId(142);
     ab2.setName("CONSTRUCTION");
     ab2.setPassword("CONSTRUCTIONPWD");
     if(!db.isProduction())
        ab2.setProviderURL(sedemsTest);
     else
         ab2.setProviderURL(sedemsProd);
     lduser.setConstructionAuthBean(ab2);
     //*************************************************************************          
     
      sess.setAttribute("lduser", lduser); 
      
      //System.out.println("NEW USER OBJECT");
      
      ///*******SH 9/22/16 adding new User object to session. will use this new object for struts2 calls; 
      //continue to use old UserBean for struts1 calls. 
      User user = new User();
      user.setUserId(lduser.getUserid());
      user.setCreatedBy(lduser.getUserid());
      user.setModifiedBy(lduser.getUserid());
      user.setAdmin(lduser.isTypeadmin());
      //set literacy permission from old UserBean to new User object
      if(lduser.getPrgal()!=null && !lduser.equals("")){
			      if(lduser.getPrgal().equalsIgnoreCase(User.getReadAccess()))
			    	  user.setAdultLiteracyAccess(User.getReadAccess());
			      else if(lduser.getPrgal().equalsIgnoreCase(User.getEditAccess()))
			    	  user.setAdultLiteracyAccess(User.getEditAccess());
			      else if(lduser.getPrgal().equalsIgnoreCase(User.getSubmitAccess()))
			    	  user.setAdultLiteracyAccess(User.getSubmitAccess());
      }
      
      if(lduser.getPrgfl()!=null && !lduser.getPrgfl().equals("")){
			      if(lduser.getPrgfl().equalsIgnoreCase(User.getReadAccess()))
			    	  user.setFamilyLiteracyAccess(User.getReadAccess());
			      else if(lduser.getPrgfl().equalsIgnoreCase(User.getEditAccess()))
			    	  user.setFamilyLiteracyAccess(User.getEditAccess());
			      else if(lduser.getPrgfl().equalsIgnoreCase(User.getSubmitAccess()))
			    	  user.setFamilyLiteracyAccess(User.getSubmitAccess());
      }
      
      //set literacy ADMIN permissions from old UserBean to new User object
      if(lduser.getAdminal()!=null && !lduser.getAdminal().equals("")){
    	  	if(lduser.getAdminal().equalsIgnoreCase("approve"))
    	  		user.setAdultLiteracyAdminAccess(User.getSubmitAccess());//approvers same as applicant/submit
    	  	else if(lduser.getAdminal().equalsIgnoreCase("review"))
    	  		user.setAdultLiteracyAdminAccess(User.getEditAccess());//reviewers same as applicant/edit
      }
      
      if(lduser.getAdminfl()!=null && !lduser.getAdminfl().equals("")){
  	  	if(lduser.getAdminfl().equalsIgnoreCase("approve"))
  	  		user.setFamilyLiteracyAdminAccess(User.getSubmitAccess());//approvers same as applicant/submit
  	  	else if(lduser.getAdminfl().equalsIgnoreCase("review"))
  	  		user.setFamilyLiteracyAdminAccess(User.getEditAccess());//reviewers same as applicant/edit
      }
      
      
      
      sess.setAttribute("user", user);
      ////////////////////////////////////////////////***********************************
      
      //go directly to welcomePage, code to read from file in jsp
       if(lduser.isTypeadmin() || lduser.isTypeapcnt() || lduser.isTyperev())
       {
         response.sendRedirect("welcomePage.jsp");   
         return;//12/12/12 added b/c illegalstateexception
       }
       else
       {
         //error occured - usertype was null
         System.out.println("error Login: usertype is null");  
         log.error("User type was null");
         response.sendRedirect("error.jsp?errormsg=Your user type is null. You are not designated as admin or user");
         return;//12/12/12 added b/c illegalstateexception
       }
     
     }catch(Exception e){
       System.out.println("error Login: "+e.getMessage().toString());
       log.error(e.getMessage().toString());
       response.sendRedirect("error.jsp?errormsg=error in LoginAction");
       return;//12/12/12 added b/c illegalstateexception
     }
    
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     doGet(request, response);
   }
 }
