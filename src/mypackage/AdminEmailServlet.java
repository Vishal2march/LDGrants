/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminEmailServlet.java
 * Creation/Modification History  :
 *
 * SH   6/14/07      Created
 *
 * Description
 * This servlet will route the links on admin page for sending approval/denial
 * emails.  It checks to see if the grant actually has an initial approval, final
 * approval, etc., before sending out the corresponding email to PO (or PM).
 * Used for both SA and CO and DI admin emails.   NOT BEING USED 11/15/11
 *****************************************************************************/
package mypackage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

public class AdminEmailServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    ApprovalsDBbean adb = new ApprovalsDBbean();
    EmailNotificationBean eb = new EmailNotificationBean();
    
    //check for session timeout 
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew())
    {      
      response.sendRedirect("error.jsp?sessionTime=true");
      return;//12/12/12 added b/c illegalstateexception
    }
          
    int newwt =0;
    String finalTarget = "";
    
    try{
      String etype = request.getParameter("etype");
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      String currpage = request.getParameter("currpage");
      UserBean userb=(UserBean) sess.getAttribute("lduser");
       
      if(currpage.equals("sa"))
        finalTarget = "/AdminEmail.do";
      else if(currpage.equals("co"))
        finalTarget = "/AdminCoEmail.do";
      else if(currpage.equals("di"))
        finalTarget = "/DiAdminEmail.do";
        
      
      //switch on the type of email to sent
      if(etype !=null)
      {
        if(etype.equals("initial"))
        {
          //boolean hasInitial = adb.isInitialAppApproved(grantid);
          boolean hasInitial = true;
          if(hasInitial)
          {
            //send an email notifying of initial approved amount
            newwt=eb.sedemsCpInitialApproval(grantid, userb, true);
          }                   
        }
//--------------------------------------------------------------------------------
        else if(etype.equals("final"))
        {
          //boolean hasFinal = adb.isFinalAppApproved(grantid);
          boolean hasFinal = true;
          if(hasFinal)
          {
            //send an email notifying of final approval
            newwt = eb.sedemsCpFinalApproval(grantid, userb, true);            
          }          
        }
//---------------------------------------------------------------------------------
        else if(etype.equals("deny"))
        {
          //boolean hasDeny = adb.isAppDenied(grantid);
          boolean hasDeny = true;
          if(hasDeny)
          {
            //send an email notifying of denial           
            newwt = eb.sedemsCpAppDenied(grantid, userb, true);         
          }          
        }
//----------------------------------------------------------------------------
        else if(etype.equals("osc"))
        {
          //boolean hasAppr = adb.isInitialAppApproved(grantid);
          boolean hasAppr = true;
          if(hasAppr)
          {
            //send an email notifying of pending osc approval - FOR CO ONLY
            newwt = eb.sedemsCoIntentAward(grantid, userb, true);
          }          
        }        
      }     
      
    } catch(Exception e){
      System.out.println("error AdminEmailServlet " + e.getMessage().toString());
    }
    
    //route the user back to the email page - display errmsg if needed
    request.setAttribute("emailStatus", String.valueOf(newwt));
    RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
    rd.forward(request, response);  
    
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}