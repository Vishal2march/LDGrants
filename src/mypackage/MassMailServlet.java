/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  MassMailServlet.java
 * Creation/Modification History  :
 *
 * SH   8/14/07      Created
 *
 * Description
 * This servlet will handle mass mailings sent out by the admin to reviewers 
 * for CO/DI reviewer participation/assignment emails.
 * Modified 3/27/13 No longer used - see AdminMassEmail for sedems sending of co/di reviewer emails
 *****************************************************************************/
package mypackage;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import sedems.AuthenticateBean;

public class MassMailServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    String finalTarget="";
    ReviewerEmailBean reb = new ReviewerEmailBean();
   /* 
    try{
      String etype = request.getParameter("etype");
      String module = request.getParameter("mod");
      UserBean userb = (UserBean) sess.getAttribute("lduser");
      
      if(etype.equals("participate"))
      {        
          if(module.equals("co"))
          {
            //send email to coor reviewers to update participation and personal info
            Vector revs = reb.getActiveReviewers(7);
            if(revs.size()>0)
              reb.sedemsParticipationMail(revs, 7, userb);
            finalTarget="AdminRevEmail.do";
          }
          else
          {
            //send email to disc reviewers to update participation and personal info
            Vector revs = reb.getActiveReviewers(5);
            if(revs.size()>0)
              reb.sedemsParticipationMail(revs, 5, userb);
            finalTarget="DiAdminRevMail.do";
          }
      }
//-----------------------------------------------------------------------------
      else if(etype.equals("partremind"))
      {
          Vector revs = reb.getActiveReviewers(5);
          if(revs.size()>0)
            reb.sedemsParticipateRemind(revs, 5, userb);                     
          finalTarget="DiAdminRevMail.do";        
      }
//--------------------------------------------------------------------------------
      else if(etype.equals("getAssign"))
      {
        //get all assignemnts for chosen fy and grantprogram
        String fy = request.getParameter("fycode");
        request.setAttribute("fy", fy);        
        ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();             
        
        if(module.equals("co"))
        {
          Vector results = rdb.getAssignForFyFc(Integer.parseInt(fy), 7);                
          request.setAttribute("allAssign", results);        
          finalTarget="AdminRevEmail.do?todo=assign";
        }
        else
        {
          Vector results = rdb.getAssignForFyFc(Integer.parseInt(fy), 5);                
          request.setAttribute("allAssign", results);        
          finalTarget="DiAdminRevMail.do?todo=assign";
        }
      }
//--------------------------------------------------------------------------------
      else if(etype.equals("assignment"))
      {
        //send assignment email
        String fyc = request.getParameter("fycode");
        int fy=Integer.parseInt(fyc);
        String duedate = request.getParameter("duedate");
        ReviewerAssignDBbean rdb = new ReviewerAssignDBbean();   
        
        if(module.equals("co"))
        {
          int result = rdb.setReviewDueDate(duedate, fy, 7);
          Vector results = rdb.getRevForFyFc(fy, 7);        
          if(results.size()>0)
            reb.sedemsCpAssignEmail(results, fy, duedate, 7, userb);
          finalTarget="AdminRevEmail.do";
        }
        else if(module.equals("di"))
        {
          int result = rdb.setReviewDueDate(duedate, fy, 5);
          Vector results = rdb.getRevForFyFc(fy, 5);
          if(results.size()>0)
             reb.sedemsCpAssignEmail(results, fy, duedate, 5, userb);
          finalTarget="DiAdminRevMail.do";
        }
      }

      
    }catch(Exception e){
      System.out.println("error MassMailServlet"+ e.getMessage().toString());
    }
    */
    RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
    rd.forward(request, response); 
    
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}