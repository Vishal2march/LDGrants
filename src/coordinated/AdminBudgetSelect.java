/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminBudgetSelect.java
 * Creation/Modification History  :
 * SH   7/25/07      Created
 *
 * Description
 * This servlet will route sa,co,di,fl,al,lg admin to the budget tabs. 
 *****************************************************************************/
package coordinated;
import clobpackage.ClobBean;

import construction.ConstructAllocationDbBean;
import construction.ConstructionDBbean;
import construction.SystemAssignBean;

import java.util.HashMap;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;

import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;

public class AdminBudgetSelect extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    DBHandler dbh = new DBHandler();
    BudgetDBHandler bdh = new BudgetDBHandler();
    FS10DBbean fs = new FS10DBbean();
    HttpSession sess = request.getSession();
    String finalTarget = "";
    
    //check for session timeout 
    boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
    if (!userID && sess.isNew())
    {      
      System.out.println("SESSION TIMED OUT AdminBudgetSelect");
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
          
          //project budget
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);        
          
          boolean splittypecodes=false;
          //splitting fl/al by budget category per CA 5/26/10
          //modified 9/30/11 to use for construction admin/dasny
          if(asb.getFccode()==80 || asb.getFccode()==86 || asb.getFccode()==40 || asb.getFccode()==42) 
            splittypecodes=true;
          BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, splittypecodes, Integer.parseInt(tab));
          request.setAttribute("BudgetCollectionBean", bc);
                    
                    
          if(module.equals("lg") || module.equals("lgr"))//lgrmif admin/reviewer
          {
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
            
              TotalsBean tb1 = bdh.calcLgAmtApprForPanel( grantid, asb.getFycode(), tb.getTotAmtAppr());
              request.setAttribute("fyTotal", tb1);
            
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLgBudgetNarrative(request, tab, grantid);            
            
            if(module.equals("lg")){
              finalTarget = "LgAdminBudget"+tab+".do";//lgrmif admin
              sess.setAttribute("pagemodule", "lg");
            }
            else{
              finalTarget = "LgRevBudget"+tab+".do";//lgrmif reviewer
              sess.setAttribute("pagemodule", "lgr");
            }
          }
          else if(module.equals("cn"))
          {          
               ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
               ConstructionDBbean cdb = new ConstructionDBbean();
               TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
               //project cost, max cost
               tb = cadb.calcMaxAmtSysRecommend(grantid, tb, gb.getFycode());
               //system amt_recommended
               SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
               tb.setSysAmtRecommended(sab.getRecommendAmt());
               request.setAttribute("totalsBean", tb);
          
              finalTarget = "CnAdminBudget" + tab +".do";//construction admin
          }
          else if(module.equals("dasny"))
          {          
                ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
                ConstructionDBbean cdb = new ConstructionDBbean();
                TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
                //project cost, max cost
                tb = cadb.calcMaxAmtSysRecommend(grantid, tb, gb.getFycode());
                //system amt_recommended
                SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                tb.setSysAmtRecommended(sab.getRecommendAmt());
                request.setAttribute("totalsBean", tb);
           
               finalTarget = "CnDasnyBudget" + tab +".do";//construction dasny
          }
          else if(module.equals("sa"))
          {
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
          
            ArrayList allFSRecords = fs.getFSARecords(grantid);        
            request.setAttribute("allFSRecords", allFSRecords);
            request.setAttribute("fsaSize", new Integer(allFSRecords.size()) );
            
            finalTarget = "AdminBudgetTab" + tab +".do";//SA ADMIN 
          }
          else if(module.equals("di"))
          {
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
          
             TotalsBean tb1 = bdh.calcDiFyTotalAmtAppr(asb.getFycode());
             request.setAttribute("fyTotal", tb1);//amt appr cannot go over 500k per fy
             
             finalTarget= "DiAdminBudget" +tab +".do";//DI ADMIN
          }
          else if(module.equals("staid"))
          {
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
                
            finalTarget = "StateAidAdminTab" + tab +".do";//State aid ADMIN 
          }
          else if(module.equals("co"))
          {
              TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
              request.setAttribute("totalsBean", tb);
              
              //added 2/17/11-totals for each year of project
              HashMap tb1 = bdh.calcFyBudgetTotals(grantid, asb.getFycode(), true, asb);
              request.setAttribute("totalsMap", tb1);
          
             //the amt appr cannot go over 350k per fiscal year
              Vector totals = bdh.getTotalApprForFyCoLit(gb.getFycode(), gb.getFccode());
              request.setAttribute("fyTotals", totals);  
              
              finalTarget = "AdminCoPBTab" +tab +".do";//CO ADMIN
          }
          else if(module.equals("fl") || module.equals("al"))
          {
            request.setAttribute("p", module);//set al/fl program for admin budget pages
            HashMap hm = dbh.getProjectBudgetTotalsByFy(grantid, true, asb, gb.getInstID());
            request.setAttribute("totalsMap", hm);
            
            //check amt appr cannot go over 200k per fiscal year
            Vector totals = bdh.getTotalApprForFyCoLit(gb.getFycode(), gb.getFccode());
            request.setAttribute("fyTotals", totals);  
            
            //starting FY2013-14; check that award is not over allocation for given grant
            if(asb.getFycode()>13){
              Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
              request.setAttribute("fyWarnings", warns);
            }
              
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLitBudgetNarrative(request, tab, grantid, asb.getFccode(), asb.getFycode());
            if(module.equals("fl"))
                finalTarget ="FlAdminBudget"+tab+".do";
            else
                finalTarget ="AlAdminBudget"+tab+".do";
          }
          
                                     
    }catch(Exception e){
      System.out.println("error AdminBudgetSelect "+ e.getMessage());
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