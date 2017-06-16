/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  UpdateCpExpenses.java
 * Creation/Modification History  :
 *
 * SH   8/9/07      Created  12/23/08 modified for sa/co/di
 * 3/30/09 modified for fl/al, 4/27/09 modified for lg
 * Description
 * This servlet will save all expense records on given tab page
 * for SA/CO/di/fl/al/lg/cn. 
 *****************************************************************************/
package coordinated;

import java.util.HashMap;
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

public class UpdateCpExpenses extends HttpServlet 
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
    String bgtpage = "";//lg/al/fl/cn use different tabs
      
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
      String module = request.getParameter("m");
      
      //make sure user has permissions to update
      UserBean userb = (UserBean)sess.getAttribute("lduser");
      String permiss = "read";
      
      if(module.equals("lg")){
        permiss = userb.getPrglg();
        bgtpage = request.getParameter("i");
        finalTarget = "LgBudget"+ bgtpage +".do";
      }
      else if(module.equals("sa")){
        permiss = userb.getPrgsa();
        finalTarget = "ApcntBudgetTab" + tab + ".do";  
      }
      else if(module.equals("co")){
        permiss = userb.getPrgco();
        finalTarget = "ApcntCoPBViewTab" +tab+ ".do";   
      }
      else if(module.equals("di")){
        permiss = userb.getPrgdi();
        finalTarget = "DiBudgetTab"+tab+".do";      
      }
      else if(module.equals("cn")){
          permiss = userb.getPrgconstruction();
          bgtpage = request.getParameter("i");
          finalTarget = "CnBudgetTab"+bgtpage+".do";
      }
      else if(module.equals("lit")){
        bgtpage = request.getParameter("i");
        String prog = request.getParameter("p");
        
        if(prog.equals("fl")){
          permiss = userb.getPrgfl();
          finalTarget = "FlExpense"+bgtpage+".do";
        }
        else if(prog.equals("al")){
          permiss = userb.getPrgal();
          finalTarget = "AlExpense"+bgtpage+".do";
        }
        request.setAttribute("p", prog);
      }
      else if(module.equals("staid")){
          permiss = userb.getPrgNycStateaid();
          finalTarget = "StateAidTab" + tab + ".do";  
      }
            
                     
      if(! permiss.equals("read"))
      {
        ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
        //cast the action form to a BudgetCollectionBean 
        BudgetCollectionBean bc = (BudgetCollectionBean) myform;
        int outcome=0;
        
        if(module.equals("sa") || module.equals("co"))
            outcome = bdh.updateExpensesWithAmend(bc, userb, ctab);
        else if(module.equals("lit"))
        {
            //outcome = bdh.updateLitCategoryExpenses(bc, userb, ctab);
            //SH 6/30/14 no longer using lit expenses for category; now do lit exp per record.  per LAreford for 2013-14
            outcome = bdh.updateExpenses(bc, userb, ctab);
        }
        else//for di and lg and cn and stateaid
            outcome = bdh.updateExpenses(bc, userb, ctab);
      }
        
        //refresh budget vars
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
        
        GrantBean gb = dbh.getRecordBean(grantid);
        request.setAttribute("thisGrant", gb); 
        
        if(asb.getFccode()==80)//lgrmif
        {
          //get project budget records for lg tab
          BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, Integer.parseInt(bgtpage), asb);
          request.setAttribute("BudgetCollectionBean", bc);    
          
           //get lg budget narrative(s) for LG page (not same as all other programs )
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLgBudgetNarrative(request, bgtpage, grantid);
        }
        else if(asb.getFccode()==40 || asb.getFccode()==42){    
          //for lit, get budget records by split category
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(bgtpage), true);
          request.setAttribute("BudgetCollectionBean", bc);   
          
          HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
          request.setAttribute("totalsMap", tb);
            
          //get corresponding budget narrative for this budget tab
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLitBudgetNarrative(request, bgtpage, grantid, asb.getFccode(), asb.getFycode());
        }
        else if(asb.getFccode()==86){
            //for cosntruction, split categories
             BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(bgtpage), true);
             request.setAttribute("BudgetCollectionBean", bc);                
        }
        else{
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, ctab, false);
            request.setAttribute("BudgetCollectionBean", bc);   
        }
    //-----------------------------------------------------------------------

        if(!module.equals("lit")){//budget totals for all programs except al/fl
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);    
        }     
              
        RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
        rd.forward(request, response);  
      return;//12/12/12 added b/c illegalstateexception
    
      
    } catch(Exception e) { 
      System.out.println("error UpdateCpExpenses " + e.getMessage().toString());
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