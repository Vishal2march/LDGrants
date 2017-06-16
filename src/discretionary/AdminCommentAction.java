/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminCommentAction.java
 * Creation/Modification History  :
 *
 * SH   2/5/08      Created
 *
 * Description
 * This dispatchaction class will route admin to get or add comments for given grant
 * Modified to use for Co/sa/di/fl/al
 *****************************************************************************/
package discretionary;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.CommentBean;
import mypackage.CommentDBbean;
import mypackage.EmailNotificationBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminCommentAction extends DispatchAction
{
  
  
  public ActionForward comments(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = request.getParameter("p");
      try{
        String grantid = (String) sess.getAttribute("grantid");
        CommentDBbean cdb = new CommentDBbean();
        Vector allComments= cdb.getAdminComments(Long.parseLong(grantid));
        request.setAttribute("allComments", allComments);
      
      }catch(Exception e){System.out.println("error AdminComment "+e.getMessage().toString());}
              
      return mapping.findForward(module +"comments");
    }
    
    
     public ActionForward deletec(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = request.getParameter("p");
      
      try{                      
           CommentDBbean cdb = new CommentDBbean();
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           UserBean userb = (UserBean) sess.getAttribute("lduser");
            
          String comid = request.getParameter("comid");
          int result = cdb.deleteAdminComment(Integer.parseInt(comid));       
            
          if(result <=0)
            return mapping.findForward("error");
              
            //get all comments and return to view comment page           
            Vector allComments= cdb.getAdminComments(grantid);
            request.setAttribute("allComments", allComments);   
              
      }catch(Exception e) {
        System.out.println("error AdminCommentAction "+e.getMessage().toString());
      }                        
     return mapping.findForward(module +"comments");
    }
    
    
    public ActionForward updatec(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = request.getParameter("p");
      
      try{                      
           CommentDBbean cdb = new CommentDBbean();
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
            
           cdb.updateAdminComment(request);//NEED to change to collection               
                       
            //get all comments and return to view comment page           
            Vector allComments= cdb.getAdminComments(grantid);
            request.setAttribute("allComments", allComments);   
              
      }catch(Exception e) {
        System.out.println("error AdminCommentAction "+e.getMessage().toString());
      }                        
     return mapping.findForward(module +"comments");
    }
    
    
    public ActionForward sendmail(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = request.getParameter("p");
      
      try{                              
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
            
            EmailNotificationBean enb = new EmailNotificationBean();
            CommentBean cb = new CommentBean();
            
            String com = request.getParameter("id");
            cb.setId(Long.parseLong(com));
            String from = request.getParameter("from");
            cb.setFrom(from);
            String cc = request.getParameter("cc");
            cb.setCc(cc);
            String subject = request.getParameter("subject");
            cb.setSubject(subject);
            cb.setGrantid(grantid);
            enb.sedemsCommentMail(cb, (UserBean) sess.getAttribute("lduser"));      
                       
            //get all comments and return to view comment page       
            CommentDBbean cdb = new CommentDBbean();
            Vector allComments= cdb.getAdminComments(grantid);
            request.setAttribute("allComments", allComments);   
              
      }catch(Exception e) {
        System.out.println("error AdminCommentAction "+e.getMessage().toString());
      }                        
     return mapping.findForward(module +"comments");
    }
        
    
    public ActionForward add(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      String module = request.getParameter("p");
      
      try{
          String comment = request.getParameter("appCorrection");
          //if no comment in text field then return to add page
          if(comment==null || comment.equals(""))
            return mapping.findForward(module +"add");
            
           CommentDBbean cdb = new CommentDBbean();
           String grantnum = (String) sess.getAttribute("grantid");
           long grantid = Long.parseLong(grantnum);
           UserBean userb = (UserBean) sess.getAttribute("lduser");
            
            // save the correction comment to the database
            int result = cdb.saveAdminComment(grantid, userb, comment);
            if(result <=0)
              return mapping.findForward("error");
              
            //get all comments and return to view comment page           
            Vector allComments= cdb.getAdminComments(grantid);
            request.setAttribute("allComments", allComments);   
              
      }catch(Exception e) {
        System.out.println("error AdminCommentAction "+e.getMessage().toString());
      }
                        
     return mapping.findForward(module +"comments");
    }
    
    
       
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT AdminCommentAction");
        timeout = true;
      }
      
      return timeout;
    }
}