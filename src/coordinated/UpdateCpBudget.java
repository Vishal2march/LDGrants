/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UpdateCpBudget.java
 * Creation/Modification History  :
 *
 * SH   8/9/07      Created
 *
 * Description
 * This servlet will save budget records on given tab for 
 * SA/CO/DI/fl/al/lg/cn applicant and cn reviewer. 
 *****************************************************************************/
package coordinated;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.ActionForm;

public class UpdateCpBudget extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    String finalTarget = "error.jsp?errormsg=Could not save project budget record";      
    BudgetDBHandler bdh = new BudgetDBHandler();
    DBHandler dbh = new DBHandler();    
      
    try {    
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew()){
        response.sendRedirect("error.jsp?sessionTime=true");
        return;//12/12/12 added b/c illegalstateexception
        }
                                 
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      
      String tab = request.getParameter("currtab");
      int ctab = Integer.parseInt(tab);
      String module = request.getParameter("m");//passed through 'forward' in actionmaping
      String bgtpage ="";//al/fl/lg/cn use split category tabs
            
      //make sure user has permissions to update
      UserBean userb = (UserBean) sess.getAttribute("lduser");
      String permiss = "read";
      
      if(module.equals("sa")){
        permiss = userb.getPrgsa();
        finalTarget = "ApcntBudgetTab" + tab + ".do"; 
      }
      else if(module.equals("co")){
        permiss = userb.getPrgco();
        finalTarget = "ApcntCoPBTab"+tab+".do"; 
      }
      else if(module.equals("di")){
        permiss = userb.getPrgdi();
        finalTarget = "DiBudgetTab"+tab+".do"; 
      }
      else if(module.equals("lg")) {
        permiss = userb.getPrglg();
        bgtpage = request.getParameter("i");
        finalTarget = "LgBudget"+bgtpage+".do";
      }
      else if(module.equals("fl")){
        permiss = userb.getPrgfl();
        bgtpage = request.getParameter("i");
        finalTarget = "FlBudget"+bgtpage+".do";
      }
      else if(module.equals("al")){
        permiss = userb.getPrgal();
        bgtpage = request.getParameter("i");
        finalTarget = "AlBudget"+bgtpage+".do";
      }
      else if(module.equals("cn")){
          permiss = userb.getPrgconstruction();
          bgtpage = request.getParameter("i");
          finalTarget = "CnBudgetTab"+bgtpage+".do";
      }
      else if(module.equals("cnrev")){
          if(userb.isReviewconstruction())
                permiss = "edit";
          bgtpage = request.getParameter("i");
          finalTarget = "CnBudgetReview"+bgtpage+".do";
      }
      else if(module.equals("staid")){
        permiss = userb.getPrgNycStateaid();
        finalTarget = "StateAidTab" + tab + ".do"; 
      }
      
             
     if(! permiss.equals("read")){
        ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
        //cast the action form to a BudgetCollectionBean 
        BudgetCollectionBean bc = (BudgetCollectionBean) myform;
            
        int outcome = bdh.updateCpLiBudget(bc, userb, ctab);
     }
     
     
     AppStatusBean asb = dbh.getApplicationStatus(grantid);
     request.setAttribute("appStatus", asb);
      
      if(asb.getFccode()==80){ //lgrmif
        //get project budget records for selected lg tab
        BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, Integer.parseInt(bgtpage), asb);
        request.setAttribute("BudgetCollectionBean", bc);          
      }
      else if(asb.getFccode()==40 || asb.getFccode()==42 || asb.getFccode()==86){//al/fl/cn
        //refresh budget vars  - split budget categories
        BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(bgtpage), true);
        request.setAttribute("BudgetCollectionBean", bc);
      }
      else{
          //refresh budget vars  
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, ctab, false);
          request.setAttribute("BudgetCollectionBean", bc);
      }
      
          
      if(module.equals("fl") || module.equals("al"))
      {
          GrantBean gb = dbh.getRecordBean(grantid);
          HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
          request.setAttribute("totalsMap", tb);
          
          if(asb.getFycode()>13){
              
              Vector warns = bdh.getTotAmtReqLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
              request.setAttribute("fyWarnings", warns);
          }
          else{
              Vector warns = bdh.getTotAmtReqLiFYPeriod(grantid, asb.getFycode(), asb.getFccode());
              request.setAttribute("fyWarnings", warns);
          }
           
          //get corresponding budget narrative for this budget tab
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLitBudgetNarrative(request, bgtpage, grantid, asb.getFccode(), asb.getFycode());
      }
      else
      {
        TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
        request.setAttribute("totalsBean", tb);     
        
        if(module.equals("lg")){
            //get lg budget narrative(s) for LG page (not same as all other programs tab#)
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLgBudgetNarrative(request, bgtpage, grantid);
            
            GrantBean gb = dbh.getRecordBean(grantid);
            request.setAttribute("thisGrant", gb); 
        }
      }
                        
      RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
      rd.forward(request, response);       
      return;//12/12/12 added b/c illegalstateexception
      
    } catch(Exception e){ 
      System.out.println("error UpdateCpBudget "+e.getMessage().toString());
      
      //this needed to send exception name/description to error page -otherwise blank page
      request.setAttribute("javax.servlet.jsp.jspException",  e);
      
      RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
      rd.forward(request, response); 
    }
                   
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}