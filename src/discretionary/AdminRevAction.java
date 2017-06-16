/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminRevAction.java
 * Creation/Modification History  :
 *
 * SH   2/13/08      Created
 *
 * Description
 * This dispatchaction class will route Di/Co admin calls to view/add/delete reviewer 
 * assignments. Also used to view/delete reviewer records for DI/CO/FL/AL/LG
 *****************************************************************************/
package discretionary;
import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.CommentBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.RevAssignCollectionBean;
import mypackage.ReviewerAssignDBbean;
import mypackage.ReviewerBean;
import mypackage.ReviewerDBbean;
import mypackage.UserBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AdminRevAction extends DispatchAction
{
  ReviewerAssignDBbean rab = new ReviewerAssignDBbean();
  ReviewerDBbean rdb = new ReviewerDBbean();
  private Log log = LogFactory.getLog(this.getClass());
 
 public ActionForward assignments(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        String module = request.getParameter("m");
               
        //get all rev assigned to this grant and all possible rev that can be assigned        
        Vector allAssigned = rab.getAllAssignForGrant(grantid);  
        Vector allRev = getAcceptedAssignedRevs(module, grantid);
        
        request.setAttribute("allAssigned", allAssigned);
        request.setAttribute("allRev", allRev);
                
      }catch(Exception e) { log.error(e.getMessage().toString()); }    
      return mapping.findForward("assignments");
    }
    
 
  /**
   * Action used to delete a reviewer assignment
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
 public ActionForward delete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        //delete this assignment for this reviewer
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        String assignid = request.getParameter("id");
        String module = request.getParameter("m");
        
        int outcome = rab.deleteRevAssign(Long.parseLong(assignid));
        
        //get all reviewers assigned to this grant and go back to same page       
        Vector allAssigned = rab.getAllAssignForGrant(grantid);    
        Vector allRev = getAcceptedAssignedRevs(module, grantid);
         
        request.setAttribute("allAssigned", allAssigned);
        request.setAttribute("allRev", allRev);
        
      }catch(Exception e) { log.error(e.getMessage().toString()); }           
      return mapping.findForward("assignments");
    }
    
    
  /**
   * Action used to assign a DI reviewer to Di grant. 
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
     public ActionForward assign(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        String revid=request.getParameter("revid");
        String module = request.getParameter("m");
        
        //assign the chosen reviewer to the current grant
        int outcome = rab.assignReviewer(grantid, Integer.parseInt(revid) , userb);
        
        //get all reviewers assigned to this grant and go back to same page       
        Vector allAssigned = rab.getAllAssignForGrant(grantid);     
        Vector allRev = getAcceptedAssignedRevs(module, grantid);
        
        request.setAttribute("allAssigned", allAssigned);
        request.setAttribute("allRev", allRev);
 
      }catch(Exception e) { log.error(e.getMessage().toString()); }
      return mapping.findForward("assignments");        
    }
    
    
    public ActionForward ratings(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String finalTarget="ratings";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{  
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        String module = request.getParameter("m");
        
        DBHandler dbh = new DBHandler();
        GrantBean gb=dbh.getRecordBean(grantid); 
        request.setAttribute("thisGrant", gb);  
        
        //get all rev assigned to this grant AND the scores they gave
        Vector allAssigned = new Vector();
        if(module.equals("di"))
          allAssigned = rab.getScoresForDiWeighted(grantid);  
        else if(module.equals("co"))
        {
          int fy = dbh.getFiscalYearForGrant(grantid);
          allAssigned = rab.getScoresForCoGrant(grantid, fy);
        }
        else if(module.equals("fl") || module.equals("al"))
        {
          allAssigned = rab.getScoresForLit(grantid);
          finalTarget+=module;
        }
          
        Vector allComments = rab.getCommentsForAdmin(grantid);
        
        request.setAttribute("allAssigned", allAssigned);
        request.setAttribute("allComments", allComments);
        request.setAttribute("module", module);//jsp and action used for co and di
       
      }catch(Exception e) { log.error(e.getMessage().toString());}        
      return mapping.findForward(finalTarget);        
    }
    
    
    
    
  public ActionForward unlock(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {       
    String finalTarget="unlock";
    HttpSession sess = request.getSession(); 
    if(checkSessionTimeout(sess))
      return mapping.findForward("timeout");
      
    try{  
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      UserBean userb = (UserBean) sess.getAttribute("lduser");
      String module = request.getParameter("m");
      String assignId = request.getParameter("id");
        
      //update the grant assignment to 0=unlocked
      rab.unsubmitRatingForm(userb, Long.parseLong(assignId), false, module);
       
      /////////////get all variables and redisplay page//////////////
      DBHandler dbh = new DBHandler();
      GrantBean gb=dbh.getRecordBean(grantid); 
      request.setAttribute("thisGrant", gb);  
      
      //get all rev assigned to this grant AND the scores they gave
      Vector allAssigned = new Vector();
      if(module.equals("di"))
        allAssigned = rab.getScoresForDiWeighted(grantid);  
      else if(module.equals("co"))
      {
        int fy = dbh.getFiscalYearForGrant(grantid);
        allAssigned = rab.getScoresForCoGrant(grantid, fy);
      }      
        
      Vector allComments = rab.getCommentsForAdmin(grantid);
      
      request.setAttribute("allAssigned", allAssigned);
      request.setAttribute("allComments", allComments);
      request.setAttribute("module", module);//jsp and action used for co and di
     
    }catch(Exception e) { log.error(e.getMessage().toString());}        
    return mapping.findForward(finalTarget);        
  }
    
    
     public ActionForward comment(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{  
        String id = request.getParameter("id");
        String module = request.getParameter("m");
        //get detailed reviewer comment info
        CommentBean cb = rab.getRevComment(Long.parseLong(id));   
        cb.setModule(module);//action used for di and co
        request.setAttribute("comment", cb);       
      
      }catch(Exception e) { log.error(e.getMessage().toString()); }        
      return mapping.findForward("comment");        
    }
    
    
    
  /**
   * Get info for all reviewers of certain grant program, display list on admin page.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
    public ActionForward reviewers(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
        String module=request.getParameter("m");
        //get all di reviewers 
        Vector allRev = getReviewers(module);                    
        request.setAttribute("allRev", allRev);
        
      }catch(Exception e) { log.error(e.getMessage().toString()); }        
      return mapping.findForward("reviewers");
    }
    
    
    public ActionForward record(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String program="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{
          String id = request.getParameter("id");
          program = request.getParameter("m");
          //boolean retrievessn = program.equals("lg");//lg needs rev ssn
          
          //get info about a certain reviewer to update          
          ReviewerBean rb = rdb.getReviewerInfo(Integer.parseInt(id), true);          
          rb.setGrantprogram(program);
          request.setAttribute("ReviewerBean", rb);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("record"+program);
    }
        
    public ActionForward newrev(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String program="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{                   
          ReviewerBean rb = new ReviewerBean();
          program = request.getParameter("m");
          rb.setGrantprogram(program);
          request.setAttribute("ReviewerBean", rb);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("record"+program);
    }
        
    
    public ActionForward deleterev(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String module="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
          int outcome=0;
          String id = request.getParameter("id");
          module = request.getParameter("m");
          
          //check if reviewer has any participation/assignments records
          Vector participateRec = rdb.getGrantAssignMax(Integer.parseInt(id));
                   
          //only delete reviewer if not in other tables
          if(participateRec.size() == 0)     
            outcome = rdb.deleteReviewer(Integer.parseInt(id));
                
          //get the new list of reviewers
          Vector allRev = getReviewers(module);
          request.setAttribute("allRev", allRev);
          request.setAttribute("saveStatus", new Integer(outcome));
        
      }catch(Exception e){log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }
      return mapping.findForward("reviewers"+module);  
    }
    
    
    public ActionForward loademail(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      String program="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{                             
          program = request.getParameter("m");
          DBHandler dbh = new DBHandler();
          ArrayList allyears = dbh.dropDownFiscalYears();
          sess.setAttribute("dropDownList", allyears);
      }
      catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }      
      return mapping.findForward("loademail"+program);
    }
    
    
  /**
   * Method used for fl/al admin assign reviewer.  Displays a list of all fy/program
   * projects and allows admin to check/uncheck which project reviewer is assigned.
   * @throws java.lang.Exception
   * @return 
   * @param response
   * @param request
   * @param form
   * @param mapping
   */
  public ActionForward assignlist(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {       
      ReviewerDBbean rdb = new ReviewerDBbean();
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
        
      try{   
          String revid = request.getParameter("revid");
          String fy = request.getParameter("fy");
          String fc = request.getParameter("fc");
          
          RevAssignCollectionBean rc= new RevAssignCollectionBean();        
          //get all possible assignments, and projects already assigned to reviewer
          ArrayList results = rab.handlePotentialAssignments(Integer.parseInt(fy), Integer.parseInt(fc), 
                  Integer.parseInt(revid));          
          rc.setAllAssignRecords(results);
          //get reviewer name and info
          ReviewerBean r = rdb.getReviewerInfo(Integer.parseInt(revid), false);
          rc.setReviewerName(r.getFname() +" " + r.getLname());
          request.setAttribute("AssignCollectionBean", rc);
        
      }catch(Exception e){ log.error(e.getMessage().toString());
        return mapping.findForward("error");
      }
      return mapping.findForward("assignlist");  
    }
    
    
    public ActionForward loadassign(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response) throws Exception 
      {       
        ReviewerDBbean rdb = new ReviewerDBbean();
        HttpSession sess = request.getSession(); 
        if(checkSessionTimeout(sess))
          return mapping.findForward("timeout");
          
        try{   
            DBHandler dbh = new DBHandler();
            ArrayList allyears = dbh.dropDownFiscalYears();
            sess.setAttribute("dropDownList", allyears);
            
            Vector allrevs = getReviewers("li");
            sess.setAttribute("allReviewers", allrevs);
            
        }catch(Exception e){ log.error(e.getMessage().toString());
          return mapping.findForward("error");
        }
        return mapping.findForward("assignlist");  
      }
      
    
  public Vector getReviewers(String module)
  {
    Vector allrecords =new Vector();
    String fundcodes =null;
    
    try{
       if(module.equals("lg"))
          fundcodes="80";
       else if(module.equals("di"))
          fundcodes="5";
        else if(module.equals("co"))
          fundcodes="7";
        else if(module.equals("li"))
          fundcodes="40,42";
        
        allrecords = rdb.getAllReviewers(fundcodes);        
        
    }catch(Exception e) {log.error(e.getMessage().toString());}  
    return allrecords;
  }
  
  public Vector getAcceptedAssignedRevs(String module, long grantid)
  {
    Vector allrecords =new Vector();
    String program =null;
    
    try{
       if(module.indexOf("di")>-1)
          program="discretionary";
        else if(module.indexOf("co")>-1)
          program="coordinated";
        
        allrecords = rab.getAcceptedAssignedByYear(grantid, program);   
        
    }catch(Exception e) {log.error(e.getMessage().toString()); }  
    return allrecords;
  }
  
  
    
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
      {      
        System.out.println("SESSION TIMED OUT DiAdminRevAction");
        timeout = true;
      }
      
      return timeout;
    }
 
}
