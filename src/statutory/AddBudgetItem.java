/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AddBudgetItem.java
 * Creation/Modification History  :
 * SH   9/25/07      Created
 *
 * Description
 * This servlet will add either a budget record for given tab.
 * Adding a Personal record automatically adds an associated Benefits 
 * record. (10/2/07).  Used for sa/co/di/fl/al/lg/cn/lgadmin/cnrev/cnadmin/diadmin/saadmin
 * $Id: AddBudgetItem.java 4689 2009-06-11 14:30:57Z shusak $
 *****************************************************************************/
package statutory;
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

import mypackage.*;


public class AddBudgetItem extends HttpServlet 
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
     HttpSession sess = request.getSession();
     String finalTarget = "error.jsp?errormsg=Error adding a budget record.";             
     int outcome=0;
       
      try{            
        //check for session timeout 
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew())
         throw new SessionTimeoutException();       
       
       
       String cprogram = request.getParameter("p");
       String ctab = request.getParameter("tab");
       int tab = Integer.parseInt(ctab);
      
       String grantnum = (String) sess.getAttribute("grantid");
       long grantid = Long.parseLong(grantnum);
       UserBean userb = (UserBean) sess.getAttribute("lduser");     
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
        

        if(cprogram.equals("cn"))
        {  
            String cntab = request.getParameter("t");//cn category tab
            int smetcode=0;
            //cn and lit use the same category tabs, so figure out the smetcode
            if(cntab!=null &&  (!cntab.equals(""))) 
                smetcode=bdh.determineLitBudgetCode(Integer.parseInt(cntab));
                            
          if(!userb.getPrgconstruction().equals("read"))
            outcome = bdh.addBudgetItem(userb, grantid, tab, smetcode);
            
          //refresh budget vars
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(cntab), true);
          request.setAttribute("BudgetCollectionBean", bc);  
              
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
          request.setAttribute("totalsBean", tb);  
                              
          finalTarget = "CnBudgetTab"+cntab+".do";//go back to cn category tab           
        }
//-------------------------------------------------------------------------
        else if(cprogram.equals("cnrev")){//cn reviewers can add budget recs
        
           String cntab = request.getParameter("t");//cn category tab
           int smetcode=0;
           //cn and lit use the same category tabs, so figure out the smetcode
           if(cntab!=null &&  (!cntab.equals(""))) 
               smetcode=bdh.determineLitBudgetCode(Integer.parseInt(cntab));
                 
            if(userb.isReviewconstruction())
                outcome = bdh.addBudgetItem(userb, grantid, tab, smetcode);
                
            //refresh budget vars
            BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(cntab), true);
            request.setAttribute("BudgetCollectionBean", bc);  
                
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
            request.setAttribute("totalsBean", tb);  
                                
            finalTarget = "CnBudgetReview"+cntab+".do";                   
        }
//-------------------------------------------------------------------------
      else if(cprogram.equals("admncn")){//cn admin can add budget recs
      
         String cntab = request.getParameter("t");//cn category tab
         int smetcode=0;
         //cn and lit use the same category tabs, so figure out the smetcode
         if(cntab!=null &&  (!cntab.equals(""))) 
             smetcode=bdh.determineLitBudgetCode(Integer.parseInt(cntab));
               
          if(userb.getAdmconstruction()!=null && userb.getAdmconstruction().equals("approve"))
              outcome = bdh.addBudgetItem(userb, grantid, tab, smetcode);
              
          //refresh budget vars
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(cntab));
          request.setAttribute("BudgetCollectionBean", bb);
          
          ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
          ConstructionDBbean cdb = new ConstructionDBbean();
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);//all category totals
          //project cost, max cost
          tb = cadb.calcMaxAmtSysRecommend(grantid, tb, asb.getFycode());
          //system amt_recommended
          SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
          tb.setSysAmtRecommended(sab.getRecommendAmt());
          request.setAttribute("totalsBean", tb);
                                        
          finalTarget = "CnAdminBudget"+cntab+".do";                   
      }
//--------------------------------------------------------------------------       
        else if(cprogram.equals("di"))
        {
          //add di budget record
          if(!userb.getPrgdi().equals("read"))
            outcome = bdh.addBudgetItem(userb, grantid, tab, 0);
          
           //refresh budget vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, false);
           request.setAttribute("BudgetCollectionBean", bc);  
            
           TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
           request.setAttribute("totalsBean", tb);  
                      
           finalTarget = "DiBudgetTab"+ctab+".do";
        }
//--------------------------------------------------------------------------       
        else if(cprogram.equals("admindi"))//new 8/19/15 admin can add/edit/delete all for cp dg per BLILLEY
        {
            //add di budget record
            outcome = bdh.addBudgetItem(userb, grantid, tab, 0);
                     
            BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, false, tab);
            request.setAttribute("BudgetCollectionBean", bc);
          
                        
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
          
           TotalsBean tb1 = bdh.calcDiFyTotalAmtAppr(asb.getFycode());
           request.setAttribute("fyTotal", tb1);//amt appr cannot go over 500k per fy
                      
           finalTarget= "DiAdminBudget" +tab +".do";//DI ADMIN
        }
//-----------------------------------------------------------------------------
        else if(cprogram.equals("lg"))//lg apcnt 
        {
          String lgpage = request.getParameter("lgtab");
          int lgtab = Integer.parseInt(lgpage);
          int typecode = bdh.determineLgBudgetCode(lgtab);
            
          if(!userb.getPrglg().equals("read"))
            outcome = bdh.addBudgetItem(userb, grantid, tab, typecode);
        
            GrantBean gb = dbh.getRecordBean(grantid);
            request.setAttribute("thisGrant", gb); 
            
           //refresh budget vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionTab(grantid, lgtab, asb);
           request.setAttribute("BudgetCollectionBean", bc);  
            
           TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
           request.setAttribute("totalsBean", tb);  
                      
           finalTarget = "LgBudget"+lgpage+".do";  
           
           //get budget narratives - based on LG page#, not tab#
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLgBudgetNarrative(request, lgpage, grantid);        
        }
//------------------------------------------------------------------------------
        else if(cprogram.equals("lga")){//lgrmif admin can add/delete/update budget
        
         String lgpage = request.getParameter("lgtab");
         int lgtab = Integer.parseInt(lgpage);
         int typecode = bdh.determineLgBudgetCode(lgtab);
           
         outcome = bdh.addBudgetItem(userb, grantid, tab, typecode);
         
           GrantBean gb = dbh.getRecordBean(grantid);
           request.setAttribute("thisGrant", gb); 
           
          //refresh budget vars
          BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, lgtab);
          request.setAttribute("BudgetCollectionBean", bc);  
           
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
          request.setAttribute("totalsBean", tb);  
                     
          finalTarget = "LgAdminBudget"+lgpage+".do";  
          
          //get budget narratives - based on LG page#, not tab#
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLgBudgetNarrative(request, lgpage, grantid);        
        }
//------------------------------------------------------------------------------
        else if(cprogram.equals("sa"))
        {          
          //Add sa budget record
          if(!userb.getPrgsa().equals("read"))
            outcome = bdh.addBudgetItem(userb, grantid, tab, 0);                                    
          
          //refresh budget vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, false);
           request.setAttribute("BudgetCollectionBean", bc);
            
           TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
           request.setAttribute("totalsBean", tb);  
                      
          finalTarget = "ApcntBudgetTab" + ctab + ".do";             
        }
//--------------------------------------------------------------------------       
        else if(cprogram.equals("adminsa"))//new 11/23/15 admin can add/edit/delete all for cp statutory per BLILLEY
        {
            //add budget record
            outcome = bdh.addBudgetItem(userb, grantid, tab, 0);
                     
            BudgetCollectionBean bc = dbh.getBudgetBeanRecords(grantid, 0, true, asb, false, tab);
            request.setAttribute("BudgetCollectionBean", bc);
                                  
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
          
            FS10DBbean fs = new FS10DBbean();
            ArrayList allFSRecords = fs.getFSARecords(grantid);        
            sess.setAttribute("allFSRecords", allFSRecords);
            sess.setAttribute("fsaSize", new Integer(allFSRecords.size()) );  
                      
           finalTarget= "AdminBudgetTab" +tab +".do";//CP STATUTORY ADMIN
        }
//-------------------------------------------------------------------------
         else if(cprogram.equals("fl"))
        {          
          //Add family literacy budget record (per fy and category)
          String fy = request.getParameter("fy");
          String littab = request.getParameter("t");//lit category tab
          int smetcode=0;
          if(littab!=null &&  (!littab.equals(""))) 
              smetcode=bdh.determineLitBudgetCode(Integer.parseInt(littab));
                                
          if(!userb.getPrgfl().equals("read"))
            outcome = bdh.addBudgetFy(userb, grantid, tab, Integer.parseInt(fy), smetcode);                                   
                    
          //refresh budget and narrative vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(littab), true);
           request.setAttribute("BudgetCollectionBean", bc);   
            
           GrantBean gb = dbh.getRecordBean(grantid);
           HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
           request.setAttribute("totalsMap", tb);
           
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLitBudgetNarrative(request, littab, grantid, asb.getFccode(), asb.getFycode());
                      
          finalTarget = "FlBudget" + littab + ".do";             
        }
//-------------------------------------------------------------------------
         else if(cprogram.equals("al"))
        {          
          //Add adult literacy budget record (per fy and category)
          String fy = request.getParameter("fy");
          String littab = request.getParameter("t");//lit category tab
          int smetcode=0;
          if(littab!=null &&  (!littab.equals(""))) 
              smetcode=bdh.determineLitBudgetCode(Integer.parseInt(littab));
                                
          if(!userb.getPrgal().equals("read"))
            outcome = bdh.addBudgetFy(userb, grantid, tab,Integer.parseInt(fy), smetcode);                                   
          
          //refresh budget and narrative vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, Integer.parseInt(littab), true);
           request.setAttribute("BudgetCollectionBean", bc); 
            
           GrantBean gb = dbh.getRecordBean(grantid);
           HashMap tb = dbh.getProjectBudgetTotalsByFy(grantid, false, asb, gb.getInstID());
           request.setAttribute("totalsMap", tb);
           
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLitBudgetNarrative(request, littab, grantid, asb.getFccode(), asb.getFycode());
           
          finalTarget = "AlBudget" + littab + ".do";             
        }
//------------------------------------------------------------------------------
        else if(cprogram.equals("co"))
        {
          String fy = request.getParameter("fy");
          
          //add a record for coordinated budget
          if(!userb.getPrgco().equals("read"))
            outcome = bdh.addBudgetFy(userb, grantid, tab, Integer.parseInt(fy), 0);
              
          //refresh budget vars
          BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, false);
          request.setAttribute("BudgetCollectionBean", bc);  
           
         TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
         request.setAttribute("totalsBean", tb);  
          
          finalTarget ="ApcntCoPBTab"+ ctab+ ".do";        
        }
//--------------------------------------------------------------------------       
        else if(cprogram.equals("staid"))
        {
          //add stateaid budget record
          if(!userb.getPrgNycStateaid().equals("read"))
            outcome = bdh.addBudgetItem(userb, grantid, tab, 0);
          
           //refresh budget vars
           BudgetCollectionBean bc = dbh.getApcntBudgetActionLD(grantid, asb, tab, false);
           request.setAttribute("BudgetCollectionBean", bc);  
            
           TotalsBean tb = dbh.getProjectBudgetTotals(grantid, false, asb);
           request.setAttribute("totalsBean", tb);  
                      
           finalTarget = "StateAidTab"+ctab+".do";
        }
                
          
      }catch(Exception e){
        System.out.println("error AddBudgetItem "+e.getMessage().toString());
      }
            
     RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
     rd.forward(request, response);   
  }
  
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}