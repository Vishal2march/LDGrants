/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  BudgetSelect.java
 * Creation/Modification History  :
 * SH   7/25/07      Created
 *
 * Description
 * This servlet will route sa,co,di,fl,al,lg,cn apcnts and cnreviewer
 * to the budget and expense tabs. 
 *****************************************************************************/
package coordinated;
import clobpackage.ClobBean;

import construction.ConstructionDBbean;
import construction.SystemAssignBean;

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
import mypackage.DescriptionBean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;

public class BudgetSelect extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    DBHandler dbh = new DBHandler();
    HttpSession sess = request.getSession();
    String finalTarget = "";
    
    //check for session timeout 
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew()){
      response.sendRedirect("error.jsp?sessionTime=true");
      return;//12/12/12 added b/c illegalstateexception
    }
   
              
    try {
          String tab = request.getParameter("tab");
          String module = request.getParameter("p");
 
          //get the fiscal year of grant
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          GrantBean gb = dbh.getRecordBean(grantid);
          sess.setAttribute("thisGrant", gb); 
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
                       
          if(module.equals("lg"))
          {
            //get project budget records for lgrmif tab numbering
            BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, Integer.parseInt(tab), asb);
            request.setAttribute("BudgetCollectionBean", bc);            
          }
          else{          
            boolean splitcat = false;
            if(asb.getFccode()==40 || asb.getFccode()==42 || asb.getFccode()==86)
                splitcat=true;//for lit and construction
            
            //project budget records for cp/lit tab numbering
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(tab), splitcat);
            request.setAttribute("BudgetCollectionBean", bc);
          }
                                       
          if( (module.indexOf("fl")>-1 ) || (module.indexOf("al")>-1) )
          {                             
            if(asb.getFccode()==40)
                request.setAttribute("p", "al");
            else
                request.setAttribute("p", "fl");
            //determine narrId for budget tab, then get narrative from db
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLitBudgetNarrative(request, tab, grantid, asb.getFccode(), asb.getFycode());
            
            BudgetDBHandler bdh = new BudgetDBHandler();
            
            if(asb.getFycode()>13){
                  Vector warns = bdh.getTotAmtReqLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
                  request.setAttribute("fyWarnings", warns);
            }
            else{
                  Vector warns = bdh.getTotAmtReqLiFYPeriod(grantid, asb.getFycode(), asb.getFccode());
                  request.setAttribute("fyWarnings", warns);
            }
             
            //totals for fl/al are by fy total
            HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
            request.setAttribute("totalsMap", tb);
          }
          else if(  module.indexOf("lg")>-1 )
          {
            //totals are by category and by grand total
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
            request.setAttribute("totalsBean", tb);     
            
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLgBudgetNarrative(request, tab, grantid);
          }
          else//for sa/co/di,cn
          {
            //totals are by category and by grand total
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
            request.setAttribute("totalsBean", tb);
          }

           if (module.equals("lg"))
                finalTarget = "LgBudget" + tab + ".do"; //lgrmif initial                    
            else if (module.equals("co"))
                finalTarget = "ApcntCoPBTab" + tab + ".do"; //initial    CO
            else if (module.equals("coe"))
                finalTarget = "ApcntCoPBViewTab" + tab + ".do"; //final  CO
            else if (module.equals("sa"))
                finalTarget = "ApcntBudgetTab" + tab + ".do"; //initial and final  SA
            else if (module.equals("di"))
                finalTarget = "DiBudgetTab" + tab + ".do"; //initial and final  DI
            else if(module.equals("staid"))
                finalTarget = "StateAidTab" + tab + ".do";//initial and final stateaid
            else if (module.equals("fl"))
                finalTarget = "FlBudget" + tab + ".do"; //initial  FL
            else if (module.equals("fle"))
                finalTarget = "FlExpense" + tab + ".do"; //final  FL
            else if (module.equals("al"))
                finalTarget = "AlBudget" + tab + ".do"; //initial  AL
            else if (module.equals("ale"))
                finalTarget = "AlExpense" + tab + ".do"; //final  AL
            else if (module.equals("cn"))
                finalTarget = "CnBudgetTab" + tab + ".do"; //initial and final  CN
            else if(module.equals("cnrev")){
                //get all info about system assignment
                ConstructionDBbean cdb = new ConstructionDBbean();
                SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                request.setAttribute("assignBean", sab);
                
                finalTarget = "CnBudgetReview" + tab + ".do";//cn reviewer
            }
            
                  
    }catch(Exception e){
      System.out.println("error BudgetSelect "+ e.getMessage());
    }      
      
    //send user to appropriate page
    RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
    rd.forward(request, response);      
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }  
}