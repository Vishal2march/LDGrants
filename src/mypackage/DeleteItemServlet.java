/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  DeleteItemServlet.java
 * Creation/Modification History  :
 *
 * SH   7/10/07      Created
 *
 * Description
 * This servlet will handle deletion of budget records. 
 * Delete method same for SA, CO, DI, FL, AL, LG, CN.
 * Updated to allow for construction applicant, reviewer, admin deleteItem 
 * modified 8/30/13 for lit admin
 *****************************************************************************/
package mypackage;
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

public class DeleteItemServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession sess = request.getSession();
    DBHandler dbh = new DBHandler();
    UserBean userb = (UserBean) sess.getAttribute("lduser");
    String finalTarget = "error.jsp?errormsg=Not a valid item";
    
    try{
      String grantnum = (String) sess.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);
      String progname = request.getParameter("p");
      String permiss = "";
      
      //check permissions for grant program
      if(progname.equals("sa"))
        permiss = userb.getPrgsa();
      else if(progname.equals("adminsa"))
          permiss = "edit";
      else if(progname.equals("co"))
        permiss = userb.getPrgco();
      else if(progname.indexOf("cnrev") > -1){//construction review
        if(userb.isReviewconstruction())
            permiss="edit";
      }
      else if(progname.indexOf("admncn") > -1){//construction admin
           if(userb.getAdmconstruction()!=null && userb.getAdmconstruction().equals("approve"))
              permiss="edit";
      }
      else if(progname.indexOf("cn") > -1)//construction apcnt
          permiss = userb.getPrgconstruction();      
      else if(progname.equals("di"))
        permiss = userb.getPrgdi();
      else if(progname.equals("admindi"))
          permiss = "edit";
      else if(progname.indexOf("fladmin") >-1){          
         permiss = "edit";
      }
      else if(progname.indexOf("fl") >-1)
        permiss = userb.getPrgfl();
      else if(progname.indexOf("aladmin") >-1){
        permiss = "edit";
      }
      else if(progname.indexOf("al") >-1)
        permiss = userb.getPrgal();
      else if(progname.indexOf("lga") >-1){//lg admin
        boolean lgadmin = userb.isLgadmin();
        if(lgadmin)
            permiss = "edit";
        else
            permiss = "read";
      }else if(progname.indexOf("lg")>-1 )//lg applicant
        permiss = userb.getPrglg();
      else if(progname.equals("staid"))//state aid
        permiss = userb.getPrgNycStateaid();
        
        
        
      if(permiss.equals("read")){
        //cannot delete if permissions are read only
        finalTarget = "error.jsp?errormsg=User does not have access to delete record";
        response.sendRedirect(finalTarget);
        return;//12/12/12 added b/c illegalstateexception
      }     
//---------------------------------------------------------------------------
      else{
          BudgetDBHandler bdh = new BudgetDBHandler();               
            
         //Delete from appropriate table in the database
         String tab = request.getParameter("tab").trim();
         String rowID = request.getParameter("id").trim();
         
         int dresult = bdh.deletePbItem(Integer.parseInt(tab), Long.parseLong(rowID));
                   
         //refresh all fields on original budget page
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);          
          
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb); 
          
        if(asb.getFccode()==80){//lgrmif
          
           //get project budget records for selected tab only
           String lgpage="";
           if(progname.indexOf("lga") >-1)
                lgpage = progname.substring(3);//for lga
           else
                lgpage = progname.substring(2);//for lg           
           
           //get budget narrative(s)- based on lg page# not tab#
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLgBudgetNarrative(request, lgpage, grantid);  
                        
            if(progname.indexOf("lga") >-1){//admin
                BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(lgpage));
                request.setAttribute("BudgetCollectionBean", bc);
                
                finalTarget = "LgAdminBudget"+ lgpage+".do";
                
                TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
                request.setAttribute("totalsBean", tb);           
            }
            else if(progname.indexOf("lg") >-1){//applicant            
                BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, Integer.parseInt(lgpage), asb);
                request.setAttribute("BudgetCollectionBean", bc);      
             
                finalTarget = "LgBudget"+ lgpage+".do"; 
                
                TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
                request.setAttribute("totalsBean", tb);           
            }
          }
    //------------------------------------------------------------------------
          else if(asb.getFccode()==40 || asb.getFccode()==42)//literacy
          {
            //get project budget records for selected tab only
            String litpage = "";
            if(progname.indexOf("fladmin")>-1  ||  progname.indexOf("aladmin")>-1)
              litpage = progname.substring(7);
            else                
              litpage = progname.substring(2);
            
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(litpage), true);
            request.setAttribute("BudgetCollectionBean", bc);
            
            if(asb.getFccode()==40)
                finalTarget = "AlBudget"+ litpage+ ".do";//lit bdg tabs
            else
                finalTarget = "FlBudget"+ litpage+ ".do";
              
            NarrativeDBbean ndb = new NarrativeDBbean();
            ndb.getLitBudgetNarrative(request, litpage, grantid, asb.getFccode(), asb.getFycode());
              
            HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
            request.setAttribute("totalsMap", tb);   
            
            if(progname.indexOf("fladmin")>-1  ||  progname.indexOf("aladmin")>-1){
                //FOR LIT ADMIN ONLY                
                
                //check amt appr cannot go over 200k per fiscal year
                Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
                request.setAttribute("fyTotals", totals);  
                
                //starting FY2013-14; check that award is not over allocation for given grant
                if(asb.getFycode()>13){
                    Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
                    request.setAttribute("fyWarnings", warns);
                }
                
                if(asb.getFccode()==40){
                  finalTarget = "AlAdminBudget"+ litpage+ ".do";//lit bdg tabs
                  request.setAttribute("p", "al");
                }
                else{
                  finalTarget = "FlAdminBudget"+ litpage+ ".do";
                  request.setAttribute("p", "fl");
                  }
              
            }
              
          }
    //----------------------------------------------------------------------
          else if(asb.getFccode()==86){
              //construction uses split category tabs
               String cnpage = "";
               
               if(progname.indexOf("cnrev") > -1){
                   cnpage = progname.substring(5);
                   finalTarget = "CnBudgetReview" +cnpage+ ".do";//cn reviewer tabs 
                   
                   BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(cnpage), true);
                   request.setAttribute("BudgetCollectionBean", bc); 
                   TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
                   request.setAttribute("totalsBean", tb);       
               }
               else if(progname.indexOf("admncn") > -1){
               
                   cnpage = progname.substring(6);
                   finalTarget = "CnAdminBudget" +cnpage+ ".do";//cn admin tabs 
                   
                   BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(cnpage));
                   request.setAttribute("BudgetCollectionBean", bb);
                    
                   ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
                   ConstructionDBbean cdb = new ConstructionDBbean();
                   TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
                   //project cost, max cost
                   tb = cadb.calcMaxAmtSysRecommend(grantid, tb, gb.getFycode());
                   //system amt_recommended
                   SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
                   tb.setSysAmtRecommended(sab.getRecommendAmt());
                   request.setAttribute("totalsBean", tb);
               }
               else{
                   cnpage = progname.substring(2);
                   finalTarget = "CnBudgetTab" +cnpage+ ".do";//cn budget tabs   
                   
                   BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(cnpage), true);
                   request.setAttribute("BudgetCollectionBean", bc); 
                   TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
                   request.setAttribute("totalsBean", tb);       
               }           
                                 
          }
  //-----------------------------------------------------------------------------
        else if(asb.getFccode()==5){//cp discretionary
        
            if(progname.equalsIgnoreCase("admindi")){
              
              BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, false, Integer.parseInt(tab));
              request.setAttribute("BudgetCollectionBean", bc);
              
              TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
              request.setAttribute("totalsBean", tb);
              
               TotalsBean tb1 = bdh.calcDiFyTotalAmtAppr(asb.getFycode());
               request.setAttribute("fyTotal", tb1);//amt appr cannot go over 500k per fy              
            }
            else {//applicant cpdg
              
                //refresh budget variables  
                BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(tab), false);
                request.setAttribute("BudgetCollectionBean", bc);
                
                TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
                request.setAttribute("totalsBean", tb);                   
            }
                      
        }
        //----------------------------------------------------------------------
        else if(asb.getFccode()==6){//cp statutory
        
            if(progname.equalsIgnoreCase("adminsa")){
              
              BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, false, Integer.parseInt(tab));
              request.setAttribute("BudgetCollectionBean", bc);
              
              TotalsBean tb1 = dbh.getProjectBudgetTotals(grantid, true, asb);
              request.setAttribute("totalsBean", tb1);       
              
              FS10DBbean fs = new FS10DBbean();
              ArrayList allFSRecords = fs.getFSARecords(grantid);        
              sess.setAttribute("allFSRecords", allFSRecords);
              sess.setAttribute("fsaSize", new Integer(allFSRecords.size()) );  
            }
            else {//applicant cpsa
              
                //refresh budget variables  
                BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(tab), false);
                request.setAttribute("BudgetCollectionBean", bc);
                
                TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
                request.setAttribute("totalsBean", tb);                   
            }
                      
        }
        //----------------------------------------------------------------------
          else{
            //refresh budget variables  
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(tab), false);
            request.setAttribute("BudgetCollectionBean", bc);
          }
          
                    
          //all programs except lit use totals; lgrmif & cn & cpdg totals handled above
          if(asb.getFccode()!=40 && asb.getFccode()!=42 && asb.getFccode()!=80 && asb.getFccode()!=86 && asb.getFccode()!=5 && asb.getFccode()!=6){
              TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
              request.setAttribute("totalsBean", tb);              
          }           
                        
         if(progname.equals("sa"))      
            finalTarget = "ApcntBudgetTab" + tab + ".do";//stat bdg tabs 
          else if(progname.equals("co"))
            finalTarget = "ApcntCoPBTab" +tab+ ".do";//coord bdg tabs
          else if(progname.equals("di"))
            finalTarget = "DiBudgetTab" +tab+ ".do";//di bdg tabs
          else if(progname.equals("staid"))
            finalTarget = "StateAidTab" +tab+ ".do";//stateaid nyhs/cjh
          else if(progname.equals("admindi"))
            finalTarget= "DiAdminBudget" +tab +".do";//DI ADMIN
          else if(progname.equals("adminsa"))
            finalTarget= "AdminBudgetTab" +tab +".do";//cp statutory ADMIN
            
                        
          RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
          rd.forward(request, response);        
        return;//12/12/12 added b/c illegalstateexception
      }
      
    } catch(Exception e){
      System.out.println("error DeleteItemServlet " + e.getMessage().toString());
      
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