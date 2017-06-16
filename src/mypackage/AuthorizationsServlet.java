/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AuthorizationsServlet.java
 * Creation/Modification History  :
 *
 * SH   6/26/07      Created
 *
 * Description
 * This servlet will handle form actions to save the institutional authorization and
 * final signoff and coop agreement for the grant applications. Saves authorization 
 * records to db and redirects back to page.  Used for both SA and CO
 *****************************************************************************/
package mypackage;

import java.io.InputStream;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

public class AuthorizationsServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    AuthorizationsDBbean ab = new AuthorizationsDBbean();
    String curr_page = request.getParameter("currpage");
    String finalTarget = "error.jsp?errormsg=Could not complete authorization";
    int outcome =0;
    
    
    
    try{ 
    //check user permissions
    String progname = request.getParameter("p");
    UserBean userb = (UserBean) sess.getAttribute("lduser");
    
    if(progname.equals("sa"))
    {
      String prgsa = userb.getPrgsa();
      if(prgsa.equals("read")){
        response.sendRedirect("error.jsp?errormsg=User does not have permissions for authorization");
        return;//12/12/12 added b/c illegalstateexception
      }
    }
    else if(progname.equals("co"))
    {
      String prgco = userb.getPrgco();
      if(prgco.equals("read")){
        response.sendRedirect("error.jsp?errormsg=User does not have permissions for authorization");
        return;//12/12/12 added b/c illegalstateexception
      }
    }
    
    
     
    String grantnum = (String) sess.getAttribute("grantid");
    long grantid= Long.parseLong(grantnum);
    
    
    if(curr_page.equals("saInstAuth"))
    {
        if(request.getParameter("PresOfficer") != null)
          outcome = ab.insertPOAuthorization(grantid, userb);//preservation officer
                
        if(request.getParameter("LibDir") !=null)          
          outcome = ab.insertLDAuthorization(grantid, userb);//library director      
                
        if(request.getParameter("ResFnd") !=null)
          outcome = ab.insertRFAuthorization(grantid, userb);//suny research foundation
        
     
        Vector grantAuth = ab.getGrantAuthorizations(grantid);
        request.setAttribute("grantAuth", grantAuth);
        
        //check if user represents suny (they have extra authorizations)
        UserBean ub = (UserBean) sess.getAttribute("lduser");
        DbName db = new DbName();        
        InputStream is;
        if(db.production==false)
          is = getServletContext().getResourceAsStream("docs/sunyinstidTest.txt");
        else 
          is = getServletContext().getResourceAsStream("docs/sunyinstid.txt");
          
        boolean isSuny = ab.isSunyInstitution(ub.getInstid(), is);
        request.setAttribute("isSuny", Boolean.valueOf(isSuny));
        
        finalTarget="ApcntAuthorization.do";      
      
    }
//----------------------------------------------------------------------------------
    else if(curr_page.equals("saFinalRptSignoff"))
    {
      if(request.getParameter("LibDir") !=null)
        outcome = ab.updateFinalSignoff(grantid, userb);
           
      Vector grantAuth = ab.getGrantAuthorizations(grantid);
      request.setAttribute("grantAuth", grantAuth);
      finalTarget="ApcntFinalSignoff.do";            
    }
//----------------------------------------------------------------------------
    else if(curr_page.equals("coInstAuth"))
    {
        if(request.getParameter("PresOfficer") != null)
          outcome = ab.insertPOAuthorization(grantid, userb);//preservation officer
                
        if(request.getParameter("LibDir") !=null)          
          outcome = ab.insertLDAuthorization(grantid, userb);//library director      
                
        if(request.getParameter("ResFnd") !=null)
          outcome = ab.insertRFAuthorization(grantid, userb);//suny research foundation
      
        Vector grantAuth = ab.getGrantAuthorizations(grantid);
        request.setAttribute("grantAuth", grantAuth);
        
        //check if user represents suny (they have extra authorizations)
        UserBean ub = (UserBean) sess.getAttribute("lduser");
        DbName db = new DbName();
        InputStream is;
        if(db.production==false)
          is = getServletContext().getResourceAsStream("docs/sunyinstidTest.txt");
        else 
          is = getServletContext().getResourceAsStream("docs/sunyinstid.txt");
        boolean isSuny = ab.isSunyInstitution(ub.getInstid(), is);
        request.setAttribute("isSuny", Boolean.valueOf(isSuny));
        
        finalTarget="CoInstAuth.do";
            
    }
//---------------------------------------------------------------------------
    else if(curr_page.equals("coFinalSignoff"))
    {
      if(request.getParameter("LibDir") !=null)
        outcome = ab.updateFinalSignoff(grantid, userb);
            
      Vector grantAuth = ab.getGrantAuthorizations(grantid);
      request.setAttribute("grantAuth", grantAuth);
      finalTarget="CoFinalSignoff.do";      
    }
//----------------------------------------------------------------------------
    else if(curr_page.equals("coCoopAgree"))
    {      
      long instid = userb.getInstid();

      if(request.getParameter("PresOfficer") != null)
        outcome = ab.insertPOcoopAgree(grantid, userb, instid);//preservation officer
           
      if(request.getParameter("LibDir") !=null)
        outcome = ab.insertLDcoopAgree(grantid, userb, instid);//library director      
     
        
      if(outcome <=0)
        finalTarget="error.jsp?errormsg=Unable to save cooperative agreement record.";
      else
      {
        Vector grantAuth = ab.getInstCoopAgreements(grantid, instid);
        request.setAttribute("grantAuth", grantAuth);
        finalTarget="CoCoopAgree.do";
      }
    }
    
  }catch(Exception e){System.out.println("error AuthorizationServlet "+ e.getMessage());}     
    
    RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
    rd.forward(request, response);  
    
    
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}