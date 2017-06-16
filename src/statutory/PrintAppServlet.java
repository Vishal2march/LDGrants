/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  PrintAppServlet.java
 * Creation/Modification History  :
 *
 * SH   3/17/09      Created
 *
 * Description
 * This servlet is used to print html/pdf versions of coversheet,narratives,budget,
 * and app for sa/co/di/lit/lg/cn admin/applicants.  
 *****************************************************************************/
package statutory;
import clobpackage.ClobBean;

import construction.AllocationYearBean;
import construction.CnApplicationBean;

import construction.CnRatingFormBean;
import construction.ConstructAllocationDbBean;
import construction.ConstructionDBbean;

import construction.SystemAssignBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

import literacy.BudgetSummaryDbBean;

import mypackage.AppStatusBean;
import mypackage.ApprovalsDBbean;
import mypackage.AuthorizationBean;
import mypackage.AuthorizationsDBbean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.CommentDBbean;
import mypackage.CoversheetBean;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.DistrictBean;
import mypackage.EligibilityDbBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.NarrInstructionBean;
import mypackage.NarrativeDBbean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.PartCollectionBean;
import mypackage.PartInstDBbean;
import mypackage.SessionTimeoutException;
import mypackage.StatisticBean;
import mypackage.StatisticDBbean;
import mypackage.SubmissionDBbean;
import mypackage.TotalsBean;
import mypackage.VendorDBbean;

public class PrintAppServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  private DBHandler dbh = new DBHandler(); 
  private OfficerDBbean odb = new OfficerDBbean();
  
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession s = request.getSession();
    BudgetDBHandler bdh = new BudgetDBHandler();
    String finalPage="";
    
    try{
      boolean userID = (s.getAttribute("lduser") != null); //attr is created during login
      if (!userID && s.isNew())
        throw new SessionTimeoutException();
          
      String grantnum = (String)s.getAttribute("grantid");
      long grantid = Long.parseLong(grantnum);   
      
      //get all grant info into grant bean 
      GrantBean gb = dbh.getRecordBean(grantid); 
      s.setAttribute("thisGrant", gb);    
      
      
      String todo = request.getParameter("i");
      int pdffound = todo.indexOf("pdf");
      
      if(todo.equals("budget") || todo.equals("budgetpdf"))
      {
        String isadmin = request.getParameter("a");        
        getBudgetInfo(grantid, isadmin, s);
        finalPage = getFinalTargetUrl(todo, gb.getFccode());
      }
//=====================================================================
      else if(todo.equals("cover") || todo.equals("coverpdf"))
      {
        getCoverStatusItems(grantid, gb.getInstID(), s, gb.getFccode());  
        getPersonInfo(grantid, s);
        finalPage = getFinalTargetUrl(todo, gb.getFccode());
        
         if(gb.getFccode()==80)
         {
            ClobBean clobb = new ClobBean();
            clobb.setGrantid(grantid);
            clobb.openOracleConnection();      
            clobb.getClobNarrative(1); 
            clobb.closeOracleConnection();     
            
            CoversheetBean csb = new CoversheetBean();
            csb.setSummaryDesc(clobb.getData());           
            csb.setGrantid(grantid);                 
            csb= odb.getProjectManager(csb);
            csb= odb.getAdditionalContact(csb, 1);
            csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
            
            EligibilityDbBean edb = new EligibilityDbBean();
            csb = edb.getArchivesGovtInfo(csb);
            csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());            
            s.setAttribute("coversheetBean", csb);     
         }
         else if(gb.getFccode()==86)
         {//construction
             //get the shpo exemption narrative
             ClobBean clobb = new ClobBean();
             clobb.setGrantid(grantid);
             clobb.openOracleConnection();      
             clobb.getClobNarrative(117); 
             clobb.closeOracleConnection();    
         
            AppStatusBean appbean = dbh.getGrantStatusYn(grantid);
           
             ConstructionDBbean cdb = new ConstructionDBbean();
             CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
             //set shpo exempt narr fields
             ab.setShpoExemption(clobb.getData());
             ab.setShpoExemptionId(clobb.getNarrID());
             ab.setShpoExemptionNarrTypeId(117);
             ab.setGroundDisturb(appbean.isGroundDisturb());
           
              //set all other fields
              ab.setProjectTitle(gb.getTitle());
              ab.setInstId(gb.getInstID());
              ab.setFycode(gb.getFycode());
              ab = cdb.determineCostAmts(ab, grantid);
              ab= odb.getCnProjectManager(ab);
              ab= odb.getCnDirector(ab);  
              ab= cdb.getBuildingProjectTypes(ab);//building_projects table
              s.setAttribute("applicationFormBean", ab);               
         }
         else if(gb.getFccode()==5)
          {          
              //get summary description
              ClobBean clobb = new ClobBean();
              clobb.setGrantid(grantid);
              clobb.openOracleConnection();      
              clobb.getClobNarrative(1); 
              clobb.closeOracleConnection();             
              CoversheetBean csb = new CoversheetBean();
              csb.setSummaryDesc(clobb.getData());            
              //get project manager info and title   
              csb.setGrantid(grantid);        
              csb =dbh.getProjectNameReligAffil(csb);        
              csb= odb.getProjectManager(csb);       
              
              csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
              
              EligibilityDbBean edb = new EligibilityDbBean();
              csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());        
              s.setAttribute("coversheetBean", csb);   
          }
          else if(gb.getFccode()==7)
          {
              //get total amount requested by fiscal year
              Vector allsums =bdh.getTotAmtReqCoFYPeriod(grantid, gb.getFycode());
              s.setAttribute("allsums", allsums);
              
              OfficerBean ob = odb.getProjectManager(grantid);
              s.setAttribute("projManagerBean", ob);
          }
          else if(gb.getFccode()==40 || gb.getFccode()==42)
          {
              //get all coverpage info
              CoversheetBean csb = new CoversheetBean();
              csb.setGrantid(grantid);        
              csb =dbh.getProjectNameReligAffil(csb);        
              csb= odb.getProjectManager(csb); 
              csb= odb.getAdditionalContact(csb, 3);
              s.setAttribute("coversheetBean", csb);    
              
              //8/13/15 per KBALSEN; get yearly allocation and print on cover
              Vector allocs = bdh.getYearlyAllocationForInst(gb.getFycode(), gb.getFccode(), gb.getInstID());
              s.setAttribute("allAlloc", allocs);
              
              if(gb.getFycode()>13){
                  //new method starting fy 2013-14
                   DescriptionBean pdb = new DescriptionBean();
                   //get the narrative from the db and set to descrBean in session
                    ClobBean cb = new ClobBean();
                    cb.setGrantid(grantid);
                    cb.openOracleConnection();      
                    cb.getClobNarrative(107); 
                    cb.closeOracleConnection();
                    
                    pdb.setNarrative(cb.getData());
                    pdb.setId(cb.getNarrID());
                    s.setAttribute("projNarrative", pdb);  
              }
          }         
      }
//=============================================================================
      else if(todo.equals("narr") || todo.equals("narrpdf"))
      {
          getNarrativeBeans(gb.getFccode(), grantid, s, false, 0);
          
          //if literacy 16-19 FY; need to get budget summary
          if(gb.getFccode()==40 || gb.getFccode()==42){
              if(gb.getFycode()>16){
                //refresh budget summary records
                BudgetSummaryDbBean bsdb = new BudgetSummaryDbBean();
                ArrayList purchased = bsdb.getBudgetSummariesForExp(grantid, 40);
                s.setAttribute("purchasedSummary", purchased);
                
                ArrayList travel = bsdb.getBudgetSummariesForExp(grantid, 46);
                s.setAttribute("travelSummary", travel);
                
                ArrayList supply = bsdb.getBudgetSummariesForExp(grantid, 45);
                s.setAttribute("supplySummary", supply);
                
                ArrayList equip = bsdb.getBudgetSummariesForExp(grantid, 20);
                s.setAttribute("equipmentSummary", equip);
              }
          }
          
          finalPage = getFinalTargetUrl(todo, gb.getFccode());
      }
//=============================================================================
      else if(todo.equals("app") || todo.equals("apppdf"))
      {
          finalPage = getFinalTargetUrl(todo, gb.getFccode());
          //get budget info
          String isadmin = request.getParameter("a");        
          getBudgetInfo(grantid, isadmin, s);
          
          //get narrative info
          getNarrativeBeans(gb.getFccode(), grantid, s, false, 0);
          
          //get coversheet info
          getCoverStatusItems(grantid, gb.getInstID(), s, gb.getFccode());  
          getPersonInfo(grantid, s);
          
          if(gb.getFccode()==80)
          {//lgrmif
             ClobBean clobb = new ClobBean();
             clobb.setGrantid(grantid);
             clobb.openOracleConnection();      
             clobb.getClobNarrative(1); 
             clobb.closeOracleConnection();     
             
             CoversheetBean csb = new CoversheetBean();
             csb.setSummaryDesc(clobb.getData());           
             csb.setGrantid(grantid);                 
             csb= odb.getProjectManager(csb);
             csb= odb.getAdditionalContact(csb, 1);
             csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
             
             EligibilityDbBean edb = new EligibilityDbBean();
             csb = edb.getArchivesGovtInfo(csb);
             csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());            
             s.setAttribute("coversheetBean", csb);     
             
             VendorDBbean vdb = new VendorDBbean();
             ArrayList allquotes = vdb.getVendorQuotesForGrant(grantid);
             s.setAttribute("vendorRecords", allquotes);
             
             StatisticDBbean sdb = new StatisticDBbean();
             StatisticBean sb = sdb.getStatisticsForGrant(grantid);
             s.setAttribute("StatBean",sb);
          }
          else if(gb.getFccode()==86)
          {//construction
                            
              AppStatusBean appbean = dbh.getGrantStatusYn(grantid);
          
              //get the shpo exemption narrative
              ClobBean clobb = new ClobBean();
              clobb.setGrantid(grantid);
              clobb.openOracleConnection();      
              clobb.getClobNarrative(117); 
              clobb.closeOracleConnection();    
              
              ConstructionDBbean cdb = new ConstructionDBbean();
              CnApplicationBean ab = cdb.getBuildingGrantInfo(gb.getGrantid());
              //set shpo exempt narr fields
              ab.setShpoExemption(clobb.getData());
              ab.setShpoExemptionId(clobb.getNarrID());
              ab.setShpoExemptionNarrTypeId(117);
              ab.setGroundDisturb(appbean.isGroundDisturb());
                                 
               ab.setProjectTitle(gb.getTitle());
               ab.setInstId(gb.getInstID());
               ab.setFycode(gb.getFycode());
               ab = cdb.determineCostAmts(ab, grantid);
               ab= odb.getCnProjectManager(ab);
               ab= cdb.getBuildingProjectTypes(ab);//building_projects table
               s.setAttribute("applicationFormBean", ab);      
               
              //for construction apps - additional sources of funding
               ArrayList allfunds = cdb.getFundsForPrintview(grantid);
               s.setAttribute("allFunds", allfunds);   
          }
          else if(gb.getFccode()==5)
          {          
              //get summary description
              ClobBean clobb = new ClobBean();
              clobb.setGrantid(grantid);
              clobb.openOracleConnection();      
              clobb.getClobNarrative(1); 
              clobb.closeOracleConnection();             
              CoversheetBean csb = new CoversheetBean();
              csb.setSummaryDesc(clobb.getData());            
              //get project manager info and title   
              csb.setGrantid(grantid);        
              csb =dbh.getProjectNameReligAffil(csb);        
              csb= odb.getProjectManager(csb);       
              csb.setAmtrequested(bdh.calcTotalAmtRequested(grantid, 0));
              
              EligibilityDbBean edb = new EligibilityDbBean();
              csb = edb.getInstEligibilityForCoversheet(csb, gb.getFccode());        
              s.setAttribute("coversheetBean", csb);   
          }
          else if(gb.getFccode()==7)
          {
              OfficerBean ob = odb.getProjectManager(grantid);
              s.setAttribute("projManagerBean", ob);
              
              //get total amount requested by fiscal year
              Vector allsums =bdh.getTotAmtReqCoFYPeriod(grantid, gb.getFycode());
              s.setAttribute("allsums", allsums);
              
              //BL does not want items on print app view for Contracts Unit 8/18/08
              s.setAttribute("allApprovals", null);           
              s.setAttribute("allComments", null);
          }
          else if(gb.getFccode()==6)
          {
              //get any FS10A amendment records
              FS10DBbean fs = new FS10DBbean();
              ArrayList allFSRecords = fs.getFSARecords(grantid);
              s.setAttribute("allFSRecords", allFSRecords);
          }
          else if(gb.getFccode()==20){
            AuthorizationsDBbean adb = new AuthorizationsDBbean();
            AuthorizationBean ab = adb.getGrantAssurance(grantid);
            s.setAttribute("authorizationBean", ab);
          }
      }
//=============================================================================
      else if(todo.equals("revapp") || todo.equals("revapppdf"))
      {
            finalPage = getFinalTargetUrl(todo, gb.getFccode());
            //get budget info
            getBudgetInfo(grantid, "false", s);
                                             
            if(gb.getFccode()==7){
                //get total amount requested by fiscal year
                Vector allsums =bdh.getTotAmtReqCoFYPeriod(grantid, gb.getFycode());
                s.setAttribute("allsums", allsums);
              
                //get coversheet info
                getCoverStatusItems(grantid, gb.getInstID(), s, gb.getFccode());  
                getPersonInfo(grantid, s);
            
                OfficerBean ob = odb.getProjectManager(grantid);
                s.setAttribute("projManagerBean", ob);             
            }
      }
//=============================================================================
      else if(todo.equals("vq") || todo.equals("vqpdf"))
      {
            finalPage = getFinalTargetUrl(todo, gb.getFccode());
            VendorDBbean vdb = new VendorDBbean();
            ArrayList allquotes = vdb.getVendorQuotesForGrant(grantid);
            s.setAttribute("vendorRecords", allquotes);
      }
//=============================================================================
      else if(todo.equals("im") || todo.equals("impdf"))
      {
            finalPage = getFinalTargetUrl(todo, gb.getFccode());
            
            //get all statistics for IM form data
           StatisticDBbean sdb = new StatisticDBbean();
           StatisticBean sb = sdb.getStatisticsForGrant(grantid);
           s.setAttribute("StatBean",sb);
      }
//=============================================================================
      else if(todo.equals("ifnarr") || todo.equals("ifnarrpdf")){
          getNarrativeBeans(gb.getFccode(), grantid, s, true, gb.getFycode());
          finalPage = getFinalTargetUrl(todo, gb.getFccode());
      }
//-----------------------------------------------------------------------------
      else if(todo.equals("evalform") || todo.equals("evalformpdf")){
          ConstructionDBbean cdb = new ConstructionDBbean();
          ConstructAllocationDbBean cadb = new ConstructAllocationDbBean();
          
          //get all info about system assignment
          SystemAssignBean sab = cdb.getSystemAssignRecord(grantid);
          if(gb.getFycode()>14)//starting fy14-15; new reduce match criteria/fields
              sab = cdb.getReduceMatchTypes(sab);
          s.setAttribute("assignBean", sab);
          
          //get amtreq, 50%, total pls allocation, amtrecommend so far, etc
          AllocationYearBean ab = cadb.calcRequestApprAmounts(grantid, gb.getFycode());//amtreq & 50% amt
          AllocationYearBean ab2 = cadb.calcAllocAndAwardForPlsFy(sab.getSystemInstId(), gb.getFycode());
          
          //get all eval form comments and answers
          CnRatingFormBean rb = cdb.getCnRatingComments(sab.getAssignmentId());
          rb.setGrantid(grantid);
          rb.setAssignmentid(sab.getAssignmentId());
          rb.setFycode(gb.getFycode());
          rb.setAmtrecommended(sab.getRecommendAmt());
          rb.setAmtrecommendedStr(sab.getRecommendAmtStr());
          rb.setProjectrecommend(sab.isRecommendation());
          rb.setAmountRequested(ab.getAmountRequested());//from alloc bean
          rb.setRequestCost(ab.getRequestCost());//from alloc bean
          rb.setMaxRecommendAmt(ab.getMaxRequestCost());//from alloc bean
          rb.setInitialAlloc(ab2.getInitialAlloc());//from alloc bean
          rb.setTallyAmountRecommend(ab2.getTallyAmountRecommend());//from alloc bean
          rb = cdb.getCnRatingAnswers(rb, sab.getAssignmentId());
          s.setAttribute("reviewFormBean", rb);   
          
          //get the senate, congress districts, for reduce match part of form
          DistrictBean db = dbh.getDistrictInfo(gb.getInstID());
          s.setAttribute("distBean", db);
          
          //get total cost, amt recommend, %
          gb = cdb.getAmtRecommendCostForGrant(gb, grantid);
          s.setAttribute("thisGrant", gb);  
          
          //get the narrative from the db and set to descrBean
          ClobBean cb = new ClobBean();
          cb.setGrantid(grantid);
          cb.openOracleConnection();      
          cb.getClobNarrative(95); 
          cb.closeOracleConnection();
            
          DescriptionBean pdb = new DescriptionBean();
          pdb.setNarrative(cb.getData());
          s.setAttribute("projNarrative", pdb);  
          
          finalPage = getFinalTargetUrl(todo, gb.getFccode());
      }
//=============================================================================
      else if(todo.equals("otherfund") || todo.equals("otherfundpdf")){
      
            //for construction apps - additional sources of funding
             ConstructionDBbean cdb = new ConstructionDBbean();
             ArrayList allfunds = cdb.getFundsForPrintview(grantid);
             s.setAttribute("allFunds", allfunds);   
             
             finalPage = getFinalTargetUrl(todo, gb.getFccode());        
      }
//=============================================================================
      else if(todo.equals("finalexp") || todo.equals("finalexppdf"))
      {
            finalPage = getFinalTargetUrl(todo, gb.getFccode());
            ConstructionDBbean cdb = new ConstructionDBbean();
            
          //get all expense records for all fs codes
          ArrayList allexp = cdb.getFinalExpensesForCode(grantid, 0);
          s.setAttribute("allExpenses", allexp);       
          
          //get all expense totals by category & grand total
          TotalsBean tb = cdb.getFinalExpenseTotalsAllCodes(grantid);
          s.setAttribute("expenseGrandTotals", tb);
      }
      
      
   // System.out.print("FINAL PAGE " + finalPage);
      
    //******************REDIRECT TO PRINT PAGE*************   
      if(pdffound <0)
      {
        RequestDispatcher rd = request.getRequestDispatcher(finalPage);
        rd.forward(request, response);   
        return;//12/12/12 added b/c illegalstateexception
      }
      else
      {
        response.sendRedirect(finalPage);
        return;//12/12/12 added b/c illegalstateexception
      }
          
          
    }catch(Exception e)
    {
      System.out.println("error PrintAppServlet "+ e.getMessage().toString());
      
      //needed to send exception name/description to error page -otherwise blank page
      //request.setAttribute("javax.servlet.jsp.jspException",  e);
      
     // RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
     // rd.forward(request, response);   
    }   
    RequestDispatcher rd = request.getRequestDispatcher(finalPage);
    rd.forward(request, response);  
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
  
  
  
  
//======================================================================================  
  public void getBudgetInfo(long grantid, String isadmin, HttpSession sess)
  {
    try{      
      BudgetCollectionBean bb = new BudgetCollectionBean();
      TotalsBean tb = new TotalsBean();      
      AppStatusBean asb = dbh.getApplicationStatus(grantid);
      
      boolean adminview = false;
      if(isadmin!=null && isadmin.equals("true"))
        adminview =true;
              
      if(asb.getFccode()==80 || asb.getFccode()==86)//lgrmif and construct
        bb = dbh.getBudgetBeanRecords(grantid, 0, adminview, asb, true, 0);
      else
        bb = dbh.getBudgetBeanRecords(grantid, 0, adminview, asb, false, 0);        
      tb = dbh.getProjectBudgetTotals(grantid, adminview, asb);
               
      sess.setAttribute("budgetBean", bb);
      sess.setAttribute("totalsBean", tb);
      
    }catch(Exception e) {
      System.out.println("Error PrintAppServlet "+e.getMessage().toString());
    }        
  }
  
  
  public void getCoverStatusItems(long grantid, long instid, HttpSession sess, int fccode)
  {
    try{      
        //get participating inst        
        PartInstDBbean pdb = new PartInstDBbean();   
        Vector allPartInst = pdb.getParticipants(grantid);
        if(fccode==80){
            PartCollectionBean pc =pdb.getEligibleForParts(allPartInst);
            sess.setAttribute("PartCollectionBean", pc);
        }
        else
            sess.setAttribute("allPartInst", allPartInst);
               
        SubmissionDBbean sb = new SubmissionDBbean();
         Vector allSubmissions = sb.getSubmissions(grantid);   
         sess.setAttribute("allSubmissions", allSubmissions);
         
         ApprovalsDBbean adb = new ApprovalsDBbean();
          Vector allApprovals = adb.getApprovals(grantid);           
          sess.setAttribute("allApprovals", allApprovals);
          
          CommentDBbean cdb = new CommentDBbean();
          Vector allComments = cdb.getAdminComments(grantid);
          sess.setAttribute("allComments", allComments);
          
         //inst auth and final signoff
         AuthorizationsDBbean ab = new AuthorizationsDBbean();
         Vector grantAuth = ab.getGrantAuthorizations(grantid);
         sess.setAttribute("grantAuth", grantAuth);          
       
        //get the senate, congress districts, etc.          
        DistrictBean db = dbh.getDistrictInfo(instid);
        sess.setAttribute("distBean", db);
      
    }catch(Exception e) {
      System.out.println("Error PrintAppServlet "+e.getMessage().toString());
    }     
  }
  
  
  public void getPersonInfo(long grantid, HttpSession sess)
  {
    try{        
      OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
      sess.setAttribute("presOfficerBean", presOfficerBean);
      
      OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
      sess.setAttribute("libDirectorBean", libdirectorBean);   
    
     }catch(Exception e) {
      System.out.println("Error PrintAppServlet "+e.getMessage().toString());
    }     
  }
  
  
  public void getNarrativeBeans(int fccode, long grantid, HttpSession sess, boolean interimfinal, int fycode)
  {
    NarrInstructionBean nib = new NarrInstructionBean();        
    try{
    
        int[] allTypes={0};
        switch(fccode)
        {
          case 5:
              allTypes= nib.getDiNarrTypes();
              break;
          case 6:
              allTypes= nib.getSaNarrTypes();
              break;
          case 7:
              allTypes= nib.getCoNarrTypes();
              break;
          case 40:
          case 42://literacy; interim/final narrs depend on FY
              if(interimfinal)
                allTypes = nib.determineLitInterimFinalTypes(fycode, fccode);
              else
                allTypes= nib.getLitNarrTypes();
              break;
          case 80:
              allTypes = nib.getLgNarrTypes();
              break;
          case 86://construction
              allTypes = nib.getConNarrTypes();
              break;
          case 20:
              allTypes= nib.getStaidNarrTypes();
              break;
        }
        
        //set up the clob bean to retrieve narratives
        ClobBean cb = new ClobBean();
        cb.setGrantid(grantid);
        cb.openOracleConnection();     
                   
        for(int i=0; i <allTypes.length; i++)//for each narrativeTypeId
        {                   
          DescriptionBean db = new DescriptionBean();
          String narrString = Integer.toString(allTypes[i]);
          cb.getClobNarrative(allTypes[i]); 
          db.setNarrative(cb.getData());              
          
          String newBean = "projNarr"+narrString;
          sess.setAttribute(newBean, db);   
        }
        
        //adding finalRpt narr to lgrmif html/pdf narr printout per FC 8/8/11
        if(fccode==80){
            DescriptionBean db = new DescriptionBean();
            cb.getClobNarrative(2); 
            db.setNarrative(cb.getData());              
            sess.setAttribute("projNarr2", db);   
        }
        
        cb.closeOracleConnection();
      
    }catch(Exception e){System.out.println("Error getNarrativeBeans "+e.getMessage().toString());}
    
  }
      
  
  public String getFinalTargetUrl(String page, int fccode)
  {
    String finalTarget="";
    
    try{
        HashMap diurls = new HashMap();
        diurls.put("budget", "/discretionary/diViewBudget.jsp");
        diurls.put("budgetpdf", "diBudget.pdf");
        diurls.put("cover", "diCoversheetHtml.jsp");
        diurls.put("coverpdf", "diCoversheet.pdf");
        diurls.put("narr", "diNarrativeHtml.jsp");
        diurls.put("narrpdf", "diNarratives.pdf");
        diurls.put("app", "DiApplication.do");
        diurls.put("revapp", "/reviewers/disc/revBudgetDiHtml.jsp");
        diurls.put("revapppdf", "diReviewerBudget.pdf");
        
        HashMap courls = new HashMap();
        courls.put("budget", "/coordinated/completeBudgetHTML.jsp");
        courls.put("budgetpdf", "coordinatedBudget.pdf");
        courls.put("cover", "/coordinated/completeCoverHTML.jsp");
        courls.put("coverpdf", "coordinatedCoversheet.pdf");
        courls.put("narr", "/coordinated/completeNarrativeHTML.jsp");
        courls.put("narrpdf", "coordinatedNarratives.pdf");
        courls.put("app", "CoApplication.do");
        courls.put("revapp", "/reviewers/coord/reviewerAppHTML.jsp");
        courls.put("revapppdf", "coordinatedProposal.pdf");
        
        HashMap liturls = new HashMap();
        liturls.put("budget", "/fmliteracy/liBudgetHtml.jsp");
        liturls.put("budgetpdf", "liBudget.pdf");
        liturls.put("cover", "/fmliteracy/liCoversheetHtml.jsp");
        liturls.put("coverpdf", "liCoversheet.pdf");
        liturls.put("narr", "/fmliteracy/liNarrativeHtml.jsp");
        liturls.put("narrpdf", "liNarrative.pdf");
        liturls.put("ifnarr", "/fmliteracy/liInterimFinalNarr.jsp");
        liturls.put("ifnarrpdf", "liFinalNarr.pdf");
        
        HashMap lgurls = new HashMap();
        lgurls.put("narr", "/lgrmif/lgNarrativesHtml.jsp");
        lgurls.put("narrpdf", "lgNarratives.pdf");
        lgurls.put("budget", "/lgrmif/lgBudgetHtml.jsp");
        lgurls.put("budgetpdf", "lgBudget.pdf");
        lgurls.put("cover", "/lgrmif/lgCoversheetHtml.jsp");
        lgurls.put("coverpdf", "lgCoversheet.pdf");
        lgurls.put("app", "LgApplicationView.do");
        lgurls.put("vq", "/lgrmif/vqHtml.jsp");
        lgurls.put("vqpdf", "lgVqForm.pdf");
        lgurls.put("im", "/lgrmif/imHtml.jsp");
        lgurls.put("impdf", "lgImForm.pdf");
              
        HashMap saurls = new HashMap();
        saurls.put("app", "statutory/saPrintViewHTML.jsp");
        saurls.put("apppdf", "grantApplication.pdf");
        
        HashMap staidurls = new HashMap();
        staidurls.put("app", "stateaid/printViewHTML.jsp");
        staidurls.put("apppdf", "stateAidApplication.pdf");
        
        HashMap constructurls = new HashMap();
        constructurls.put("narr", "/construction/cnNarrativesHtml.jsp");
        constructurls.put("narrpdf", "cnNarratives.pdf");
        constructurls.put("budget", "/construction/cnBudgetHtml.jsp");
        constructurls.put("budgetpdf", "cnBudget.pdf");       
        constructurls.put("cover", "/construction/cnApplicationHtml.jsp");
        constructurls.put("coverpdf", "cnApplicationForm.pdf");   
        constructurls.put("evalform", "/construction/evaluationFormHtml.jsp"); 
        constructurls.put("evalformpdf", "cnEvaluationForm.pdf"); 
        constructurls.put("otherfund", "/construction/otherFundsHtml.jsp"); 
        constructurls.put("otherfundpdf", "cnAdditionalFunds.pdf"); 
        constructurls.put("app", "CnCompleteApplication.do");
        constructurls.put("finalexp", "/construction/cnExpensesHtml.jsp");
        constructurls.put("finalexppdf", "cnExpenses.pdf");
       
        switch(fccode)
        {
          case 20://stateaid nyhs/cjh
             if(staidurls.containsKey(page))
               finalTarget= (String) staidurls.get(page);
             break;
          case 5://discretionary
             if(diurls.containsKey(page))
               finalTarget= (String) diurls.get(page);
             break;
          case 80://lgrmif
              if(lgurls.containsKey(page))//page is narr, budget, cover or reviewer
                finalTarget = (String) lgurls.get(page);
              break;
          case 6://statutory
            if(saurls.containsKey(page))
               finalTarget= (String) saurls.get(page);
            break;
          case 7://coordianted
            if(courls.containsKey(page))
               finalTarget= (String) courls.get(page);
            break;
          case 40:
          case 42:
            if(liturls.containsKey(page))
               finalTarget= (String) liturls.get(page);
            break;
          case 86://construction
            if(constructurls.containsKey(page))
                finalTarget = (String) constructurls.get(page);
        }
        
        
    }catch(Exception e){System.out.println("error getFinalTargetUrl "+e.getMessage().toString());}
    
    return finalTarget;   
  }
  
  
  
}