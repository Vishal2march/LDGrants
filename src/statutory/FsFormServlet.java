package statutory;

import construction.ConstructionDBbean;

import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Calendar;

import mypackage.AppDatesDBbean;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.OfficerBean;
import mypackage.OfficerDBbean;
import mypackage.SedrefInstBean;
import mypackage.SessionTimeoutException;
import mypackage.TotalsBean;

public class FsFormServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession s = request.getSession();
    DBHandler dbh = new DBHandler();
    BudgetDBHandler bdh = new BudgetDBHandler(); 
    OfficerDBbean odb = new OfficerDBbean();
    String fundsource = "";
    
    try{        
        boolean userID = (s.getAttribute("lduser") != null); //attr is created during login
        if (!userID && s.isNew())
          throw new SessionTimeoutException();
          
       String grantnum = (String)s.getAttribute("grantid");
       long grantid = Long.parseLong(grantnum);   
                
       GrantBean gb = dbh.getRecordBean(grantid);
       s.setAttribute("thisGrant", gb);    
       
       AppStatusBean asb = dbh.getApplicationStatus(grantid);
       
       if(gb.getFccode()==5)
        fundsource="Conservation/Preservation Discretionary Grants";
       else if(gb.getFccode()==80)
        fundsource="Local Government Records Management Improvement Fund";
       else if(gb.getFccode()==7)
        fundsource="Conservation/Preservation Big11 Coordinated Projects";
       else if(gb.getFccode()==6)
        fundsource="Conservation/Preservation Big11 Statutory Grants";
       else if(gb.getFccode()==40)
        fundsource="Adult Literacy Library Services Program";
       else if(gb.getFccode()==42)
        fundsource="Family Literacy Library Services Program";
       else if(gb.getFccode()==86){
        	//per mary ann waltz 8/15/16 - starting FY2017 has new fundsource
        	if(gb.getFycode() < 17)
        		fundsource="$14 Million Public Library Construction Grant Program";///added for construction
        	else
        		fundsource="$19 Million State Aid for Library Construction";
        }
       else if(gb.getFccode()==20)
          fundsource = "State Aid - "+ gb.getInstName();
          
       String todo = request.getParameter("i");
              
          
       if(todo.equals("fs20"))//FOR SA and LG; CN ADDED 12/10; lit added 10/11/12
       {            
          String finalTarget="/fsform/fs20HTML.jsp";
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);  
            
          //get total amt requested
          int totalAmtReq = bdh.calcTotalAmtRequested( grantid, 0);
          request.setAttribute("totalAmtRequested",new Integer(totalAmtReq));
          request.setAttribute("fundsource", fundsource);
          
          if(gb.getFccode()==80||gb.getFccode()==86)
          {
           //get totals by category
           TotalsBean tb = new TotalsBean();
           tb = bdh.calcTotalAmtReqCategorized(grantid, tb, 0, 0);
           tb = bdh.calcAmtExpSuppEquip(grantid, tb, 0, false);
           tb = bdh.calcAmtExpProfSupport(grantid, tb, 0, false);
           tb = bdh.calcAmtExpPurchBoces(grantid, tb, 0, false);  
           request.setAttribute("totalsBean", tb);
           
            OfficerBean pmBean = odb.getProjectManager(grantid);    
            request.setAttribute("presOfficerBean", pmBean);             
          }
          else 
          {
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", presOfficerBean);           
          }
          
          if(gb.getFccode()==6){//for statutory
              //get mapping of library sedcode to main institution's sedcode
              SedrefInstBean sb = new SedrefInstBean();
              String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
              gb.setSedcode(mainsedcode);
                            
              //5/5/14 new starting FY14-15; use total fund amount instead of amt req
              AppDatesDBbean adb = new AppDatesDBbean();
              int fundtotal = adb.getTotalFund(gb.getFycode(), 6);
              gb.setFundAmount(fundtotal);
              s.setAttribute("thisGrant", gb);
            
              //finalTarget="/fsform/fs20StateaidHtml.jsp";
              //starting 7/9/14 cp statutory uses fs10 front/back pages only per BL.
              finalTarget="/fsform/fs10FrontBackHtml.jsp";
          }
          
           if(gb.getFccode()==40 || gb.getFccode()==42){//literacy
                String fy = request.getParameter("fy");
                int fycode = Integer.parseInt(fy);
            
                String fyperiod="";
                if(fycode==0)
                  fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
                else
                  fyperiod=dbh.determineFiscalPeriodString(fycode);
                request.setAttribute("fundsource", fundsource + " " + fyperiod);
                
               if(fycode==0)//this will set startdate/enddate on fs form
                 gb = dbh.findFyDates(gb.getFycode(), gb);
               else
                 gb = dbh.findFyDates(fycode, gb);
               s.setAttribute("thisGrant", gb);
               
               finalTarget="/fsform/fs20LiteracyHtml.jsp"; 
           }
          
          RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
          rd.forward(request, response);  
         return;//12/12/12 added b/c illegalstateexception
       }
//=================================================================================
       else if(todo.equals("fs20pdf"))//FOR SA and LG and (cn until fy13-14)
       {       
          String finalTarget="fs20Form.pdf";
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);  
                    
          //get total amt requested
          int totalAmtReq = bdh.calcTotalAmtRequested( grantid, 0);
          s.setAttribute("totalAmtRequested",new Integer(totalAmtReq));
          s.setAttribute("fundsource", fundsource);
          
          if(gb.getFccode()==80 || gb.getFccode()==86)//lgrmif or construction
          {
           //get totals by category
            TotalsBean tb = new TotalsBean();
            tb = bdh.calcTotalAmtReqCategorized(grantid, tb, 0, 0);
            tb = bdh.calcAmtExpSuppEquip(grantid, tb, 0, false);
            tb = bdh.calcAmtExpProfSupport(grantid, tb, 0, false);
            tb = bdh.calcAmtExpPurchBoces(grantid, tb, 0, false);  
            s.setAttribute("totalsBean", tb);
            
            OfficerBean pmBean = odb.getProjectManager(grantid);    
            s.setAttribute("presOfficerBean", pmBean);
          }
          else//statutory or literacy
          {
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            s.setAttribute("presOfficerBean", presOfficerBean);
          }
          
           if(gb.getFccode()==6){//for statutory
               //get mapping of library sedcode to main institution's sedcode
               SedrefInstBean sb = new SedrefInstBean();
               String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
               gb.setSedcode(mainsedcode);
                           
               //5/5/14 new starting FY14-15; use total fund amount instead of amt req
               AppDatesDBbean adb = new AppDatesDBbean();
               int fundtotal = adb.getTotalFund(gb.getFycode(), 6);
               gb.setFundAmount(fundtotal);
               s.setAttribute("thisGrant", gb);
               
               //finalTarget="fs20FormStateaid.pdf";
               //starting 7/9/14 use fs10 front/back pages only per BL
               finalTarget="fs10FrontBack.pdf";
           }
           
           if(gb.getFccode()==40 || gb.getFccode()==42){//for literacy
                String fy = request.getParameter("fy");
                int fycode = Integer.parseInt(fy);
            
                String fyperiod="";
                if(fycode==0)
                  fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
                else
                  fyperiod=dbh.determineFiscalPeriodString(fycode);
                s.setAttribute("fundsource", fundsource + " " + fyperiod);
                
               if(fycode==0)//this will set startdate/enddate on fs form
                 gb = dbh.findFyDates(gb.getFycode(), gb);
               else
                 gb = dbh.findFyDates(fycode, gb);
               s.setAttribute("thisGrant", gb);
               
               finalTarget="fs20FormLiteracy.pdf"; 
           }
           
          //send to fs20 form 
          response.sendRedirect(finalTarget); 
         return;//12/12/12 added b/c illegalstateexception
       }
//==============================================================================
       else if(todo.equals("fs10"))
       {
          String fy = request.getParameter("fy");
          int fycode = Integer.parseInt(fy);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);         
          
          if(gb.getFccode()==6 || gb.getFccode()==7){            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", presOfficerBean);
          }
          else{
            OfficerBean pm = odb.getProjectManager(grantid);
            request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          }
          
          if(fycode==0)
            gb = dbh.findFyDates(gb.getFycode(), gb);
          else
            gb = dbh.findFyDates(fycode, gb);
          gb.setFederalid(dbh.determineFederalId(gb.getInstID())); 
          request.setAttribute("fundsource", fundsource);
          
          if(gb.getFccode()==40 || gb.getFccode()==42){//literacy
               String fyperiod="";
               if(fycode==0)
                 fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
               else
                 fyperiod=dbh.determineFiscalPeriodString(fycode);
               request.setAttribute("fundsource", fundsource + " " + fyperiod);
          }
          else if(gb.getFccode()==7){//for coordinated
               //get mapping of library sedcode to main institution's sedcode
               SedrefInstBean sb = new SedrefInstBean();
               String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
               gb.setSedcode(mainsedcode);
               gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
           }
          else if(gb.getFccode()==20){//for stateaid nyhs/cjh
              
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
              
              //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
              //use presofficer or projmanager
              request.setAttribute("presOfficerBean", libdirectorBean);            
          }
           s.setAttribute("thisGrant", gb);          
          
          //get total amt requested
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
          request.setAttribute("budgetBean", bb);
          
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalAmtReqCategorized(grantid, tb, fycode, 0);
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false);  
          request.setAttribute("totalsBean", tb);
          
  ////////////////////////////////////////////////////////////////////////////////
          if(gb.getFccode()==86){//construction
              //4/7/15 LWebb wants approve amts printed on fs10
              //get total amt APPROVED
              bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
              request.setAttribute("budgetBean", bb);
              
              tb = new TotalsBean();
              tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
              tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
              tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
              request.setAttribute("totalsBean", tb);            
          }
          
           
          String finalTarget="/fsform/fs10HtmlLong.jsp";
          if(gb.getFccode()==86)//construction
             finalTarget="/fsform/cnFs10Html.jsp";
          else if(gb.getFccode()==7 || gb.getFccode()==40 || gb.getFccode()==42)//for coordinated/adult lit/family lit
          {         //7/7/15 updated for lit per KBALSEN
             if(fycode >0)//7/20/13 - set year 1/2/3 fycode for printing on FS10form, per LD
                 gb.setFycode(fycode);
             s.setAttribute("thisGrant", gb);
          }
     
          RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
          rd.forward(request, response);
         return;//12/12/12 added b/c illegalstateexception
       }
//=================================================================================
       else if(todo.equals("fs10pdf"))
       {
          String fy = request.getParameter("fy");
          int fycode = Integer.parseInt(fy);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);  
          
          if(gb.getFccode()==6 || gb.getFccode()==7)
          {            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            s.setAttribute("presOfficerBean", presOfficerBean);
          }
          else{
            OfficerBean pm = odb.getProjectManager(grantid);
            s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          }
          
           if(fycode==0)
             gb = dbh.findFyDates(gb.getFycode(), gb);
           else
             gb = dbh.findFyDates(fycode, gb);
          gb.setFederalid(dbh.determineFederalId(gb.getInstID())); 
          s.setAttribute("fundsource", fundsource);
          
           if(gb.getFccode()==40 || gb.getFccode()==42){//literacy
                String fyperiod="";
                if(fycode==0)
                  fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
                else
                  fyperiod=dbh.determineFiscalPeriodString(fycode);
                s.setAttribute("fundsource", fundsource + " " + fyperiod);
           }
           else if(gb.getFccode()==7){//for coordinated
               //get mapping of library sedcode to main institution's sedcode
               SedrefInstBean sb = new SedrefInstBean();
               String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
               gb.setSedcode(mainsedcode);
               gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
           }
           else if(gb.getFccode()==20){//for stateaid nyhs/cjh
               
        	 //per BL; need 4 digit proj num to be same every year for stateaid
           	 gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
               
               //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
               //use presofficer or projmanager
               s.setAttribute("presOfficerBean", libdirectorBean);            
           }
           else if(gb.getFccode()==86){//for construction
               //4/7/15 LWebb wants amt recommended printed on fs10              
               ConstructionDBbean cdb = new ConstructionDBbean();
               long amtappr = cdb.getAmtRecommendedForGrant(grantid);
               s.setAttribute("totAmtAppr", amtappr);                 
           }
           s.setAttribute("thisGrant", gb); 
                  
          //get total amt requested
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
          s.setAttribute("budgetBean", bb);
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalAmtReqCategorized(grantid, tb, fycode, 0);
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false);  
          s.setAttribute("totalsBean", tb);        
          
     ////////////////////////////////////////////////////////////////////////////////
         if(gb.getFccode()==86){//construction
             //4/7/15 LWebb wants approve amts printed on fs10
             //get total amt APPROVED
             bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
             s.setAttribute("budgetBean", bb);
             
             tb = new TotalsBean();
             tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
             tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
             tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
             s.setAttribute("totalsBean", tb);            
         }
          
          String finalTarget="fs20LongForm.pdf";
          if(gb.getFccode()==86)//construction
                finalTarget="fs10Construction.pdf";
          else if(gb.getFccode()==7 || gb.getFccode()==40 || gb.getFccode()==42){//for coordinated/adult and family lit
               if(fycode >0)//7/20/13 - set year 1/2/3 fycode on form, updated 7/7/15 for literacy per KBALSEN
                   gb.setFycode(fycode);
               s.setAttribute("thisGrant", gb);
          }
          
          response.sendRedirect(finalTarget);
          return;//12/12/12 added b/c illegalstateexception
       }
//==================================================================
        else if(todo.equals("fs10ApprAmt"))
        {
    //used for Coor FS10Long - prints amtAppr instead of amtReq; also sedref info for Main inst
           String fy = request.getParameter("fy");
           int fycode = Integer.parseInt(fy);
           
           OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
           request.setAttribute("libDirectorBean", libdirectorBean);         
                        
           OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
           request.setAttribute("presOfficerBean", presOfficerBean);
                      
           if(fycode==0)
             gb = dbh.findFyDates(gb.getFycode(), gb);
           else
             gb = dbh.findFyDates(fycode, gb);
           //s.setAttribute("thisGrant", gb);   //set at bottom after getting sedcode      
           request.setAttribute("fundsource", fundsource);
           
           //get total amt APPROVED - diff from apcnt version of FS10Long
           BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
           request.setAttribute("budgetBean", bb);
           
           TotalsBean tb = new TotalsBean();
           tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
           tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
           tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
           request.setAttribute("totalsBean", tb);
              
           //get mapping of library sedcode to main institution's sedcode
           SedrefInstBean sb = new SedrefInstBean();
           String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
           gb.setSedcode(mainsedcode);
           gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
           s.setAttribute("thisGrant", gb);
                      
           RequestDispatcher rd = request.getRequestDispatcher("/fsform/fs10ApproveAmts.jsp");
           rd.forward(request, response);
          return;//12/12/12 added b/c illegalstateexception
        }
//==================================================================
        else if(todo.equals("fs10ApprAmtPdf"))
        {
    //used for Coor FS10Long - prints amtAppr instead of amtReq; also sedref info for Main inst
           String fy = request.getParameter("fy");
           int fycode = Integer.parseInt(fy);
           
           OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
           s.setAttribute("libDirectorBean", libdirectorBean);         
                        
           OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
           s.setAttribute("presOfficerBean", presOfficerBean);
                      
           if(fycode==0)
             gb = dbh.findFyDates(gb.getFycode(), gb);
           else
             gb = dbh.findFyDates(fycode, gb);     
           s.setAttribute("fundsource", fundsource);
           
           //get total amt APPROVED - diff from apcnt version of FS10Long
           BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
           s.setAttribute("budgetBean", bb);
           
           TotalsBean tb = new TotalsBean();
           tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
           tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
           tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
           s.setAttribute("totalsBean", tb);
              
           //get mapping of library sedcode to main institution's sedcode
           SedrefInstBean sb = new SedrefInstBean();
           String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
           gb.setSedcode(mainsedcode);
           gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
           s.setAttribute("thisGrant", gb);
                      
           response.sendRedirect("Fs10ApprAmt.pdf");
          return;//12/12/12 added b/c illegalstateexception
        }
//==================================================================================
       else if(todo.equals("fs10f"))//FINAL HTML Long form
       {
          String fy = request.getParameter("fyf");
          int fycode= Integer.parseInt(fy);
          String finalTarget="/fsform/fs10fLongHtml.jsp";
           
          //get all positions (director, proj manager, presservation officer, rmo, etc)
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);  
          
          if(gb.getFccode()==6 || gb.getFccode()==7)
          {            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", presOfficerBean);
            
              if(gb.getFccode()==7){//for coordinated
                   //get mapping of library sedcode to main institution's sedcode
                   SedrefInstBean sb = new SedrefInstBean();
                   String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
                   gb.setSedcode(mainsedcode);
                   gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
                   request.setAttribute("thisGrant", gb);
               }
          }
          else if(gb.getFccode()==20){//for stateaid nyhs/cjh
            
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
            request.setAttribute("thisGrant", gb);
            
            //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
            //use presofficer or projmanager
            request.setAttribute("presOfficerBean", libdirectorBean);            
          }
          else{
            OfficerBean pm = odb.getProjectManager(grantid);
            request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          }
               
          
          
          //get all final expense budget numbers (construction method is completely different)           
          if(gb.getFccode()==86){//construction
                    
            finalTarget="/fsform/cnFs10fLong.jsp";//construction has a seperate fs10f form
            
             //get all final expense records
             ConstructionDBbean cdb = new ConstructionDBbean();
             ArrayList allexp = cdb.getFinalExpensesForCode(grantid, -1);
             request.setAttribute("expenseBeans", allexp);
             
             //get total approved for this year; exp_submitted should = amt_appr
             long amtappr = cdb.getAmtRecommendedForGrant(grantid);
             request.setAttribute("totAmtAppr", amtappr);     
          }   
          else{
            //ALL OTHER programs
            BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
            request.setAttribute("budgetBean", bb);
             
            //get total exp submitted
            TotalsBean tb = new TotalsBean();
            tb = bdh.calcTotalExpSubCategorized(grantid, tb, fycode);        
            tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
            tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
            tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false);  
            request.setAttribute("totalsBean", tb); 
             
            //get total approved for this year; exp_submitted should = amt_appr
            int amtappr = bdh.calcTotalAmtApproved(grantid, fycode);
            request.setAttribute("totAmtAppr", amtappr);         
          }
          
                   
           String fyperiod="";
           if(fycode==0)
             fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
           else
             fyperiod=dbh.determineFiscalPeriodString(fycode);
           request.setAttribute("fyPeriod", fyperiod);
           
          request.setAttribute("fundsource", fundsource);
          
          //this will set start/end dates
           if(fycode==0)
             gb = dbh.findFyDates(gb.getFycode(), gb);
           else
             gb = dbh.findFyDates(fycode, gb);
           
           //7/7/15 adjust literacy fycode in proj number
           if(gb.getFccode()==40 || gb.getFccode()==42){
              gb.setFycode(fycode);
           }
           request.setAttribute("thisGrant", gb);
                       
          RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
          rd.forward(request, response);    
         return;//12/12/12 added b/c illegalstateexception
       }
//====================================================================================
       else if(todo.equals("fs10fpdf"))//FINAL PDF Long form
       {
          String fy = request.getParameter("fyf");
          int fycode = Integer.parseInt(fy);
          String finalTarget="Fs10FLong.pdf";
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);  
             
          if(gb.getFccode()==6 || gb.getFccode()==7)
          {            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            s.setAttribute("presOfficerBean", presOfficerBean);
            
              if(gb.getFccode()==7){//for coordinated
                   //get mapping of library sedcode to main institution's sedcode
                   SedrefInstBean sb = new SedrefInstBean();
                   String mainsedcode = sb.getMainInstSedcode(gb.getSedcode());
                   gb.setSedcode(mainsedcode);
                   gb.setFederalid(dbh.determineFederalId(gb.getInstID()));
                   s.setAttribute("thisGrant", gb);
               }
          }
          else if(gb.getFccode()==20){//for stateaid nyhs/cjh
            
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	  gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
              request.setAttribute("thisGrant", gb);
            
            //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
            //use presofficer or projmanager
            request.setAttribute("presOfficerBean", libdirectorBean);            
          }
          else{
            OfficerBean pm = odb.getProjectManager(grantid);
            s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          }
               
                         
         if(gb.getFccode()==86){//construction uses diff db tables & form
             finalTarget="Fs10FLongConstruction.pdf";
             
             //get all final expense records
             ConstructionDBbean cdb = new ConstructionDBbean();
             ArrayList allexp = cdb.getFinalExpensesForCode(grantid, -1);
             s.setAttribute("expenseBeans", allexp);       
             
             //get total approved for this year; exp_submitted should = amt_appr
             long amtappr = cdb.getAmtRecommendedForGrant(grantid);
             s.setAttribute("totAmtAppr", amtappr);    
         }else{
                          
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
          s.setAttribute("budgetBean", bb);
          
          //get total exp submitted
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalExpSubCategorized(grantid, tb, fycode);        
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false); //commented out 1/28/10-needs to be put back once pref cont_serv.code is updated
          s.setAttribute("totalsBean", tb);    
          
           //get total approved for this year; exp_submitted should = amt_appr
           int amtappr = bdh.calcTotalAmtApproved(grantid, fycode);
           s.setAttribute("totAmtAppr", amtappr);           
         }
          
          
          String fyperiod="";
          if(fycode==0)
            fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
          else
            fyperiod=dbh.determineFiscalPeriodString(fycode);
          s.setAttribute("fyPeriod", fyperiod);          
          s.setAttribute("fundsource", fundsource);             
          
         //this will set start/end dates
          if(fycode==0)
            gb = dbh.findFyDates(gb.getFycode(), gb);
          else
            gb = dbh.findFyDates(fycode, gb);
          
          //7/7/15 adjust literacy fycode in proj number
          if(gb.getFccode()==40 || gb.getFccode()==42){
             gb.setFycode(fycode);
          }
          s.setAttribute("thisGrant", gb);
                    
          response.sendRedirect(finalTarget);
         return;//12/12/12 added b/c illegalstateexception
       }
//=================================================================================
      else if(todo.equals("shortfs10f"))//FINAL short form HTML
       {        
          String fy = request.getParameter("fyf");
          int fycode = 0;
          if(fy!=null && !fy.equals(""))
            fycode =Integer.parseInt(fy);
           
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);  
                   
          OfficerBean pm = odb.getProjectManager(grantid);
          request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
                                 
          //BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
          //request.setAttribute("budgetBean", bb);
          
          //get total exp submitted
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalExpSubCategorized(grantid, tb, fycode);  
          //break up exp_submitted into categories (supplies vs equip, etc)
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false);  
          request.setAttribute("totalsBean", tb); 
          
          //get total approved for this year; exp_submitted should = amt_appr
          int amtappr = bdh.calcTotalAmtApproved(grantid, fycode);
          request.setAttribute("totAmtAppr", amtappr);
         
          request.setAttribute("fundsource", fundsource);
          RequestDispatcher rd = request.getRequestDispatcher("/fsform/fs10fHTML.jsp");
          rd.forward(request, response);     
         return;//12/12/12 added b/c illegalstateexception
       }
//====================================================================================
       else if(todo.equals("shortfs10fpdf"))//FINAL short form PDF
       {          
           String fy = request.getParameter("fyf");
           int fycode = 0;
           if(fy!=null && !fy.equals(""))
             fycode =Integer.parseInt(fy);
             
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);  
                 
          OfficerBean pm = odb.getProjectManager(grantid);
          s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
                                      
          //BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, false, asb, false, 0);
          //s.setAttribute("budgetBean", bb);
          
          //get total exp submitted
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalExpSubCategorized(grantid, tb, fycode);        
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, false);
          tb = bdh.calcAmtExpPurchBoces(grantid, tb, fycode, false);  
          s.setAttribute("totalsBean", tb);        
          
           //get total approved for this year; exp_submitted should = amt_appr
           int amtappr = bdh.calcTotalAmtApproved(grantid, fycode);
           s.setAttribute("totAmtAppr", amtappr);
          
          s.setAttribute("fundsource", fundsource);                              
          response.sendRedirect("FS10Fform.pdf");   
          return;//12/12/12 added b/c illegalstateexception
       }
//============================================================================
       else if(todo.equals("fs10a"))
       {
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);  
  
          if(gb.getFccode()==6 || gb.getFccode()==7)//statutory or coordinated
          {            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            request.setAttribute("presOfficerBean", presOfficerBean);
          }
          else if(gb.getFccode()==20){//for stateaid nyhs/cjh
             
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	  gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
              request.setAttribute("thisGrant", gb);
             
             //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
             //use presofficer or projmanager
             request.setAttribute("presOfficerBean", libdirectorBean);            
          }
          else{
            OfficerBean pm = odb.getProjectManager(grantid);
            request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          }
          
          
         String fy = request.getParameter("fya");
         int fycode = 0;
         if(fy!=null && !fy.equals(""))//lit now uses fs10a by FY
           fycode=Integer.parseInt(fy);
         
         
          FS10DBbean fs = new FS10DBbean();
          if(fycode==0){//for cp and lgrmif
              ArrayList allFSRecords = fs.getFSARecords(grantid);        
              request.setAttribute("allFSRecords", allFSRecords);
          }
          else{//for literacy split by year
              ArrayList allFSRecords = fs.getFSARecordsForFy(grantid, fycode);        
              request.setAttribute("allFSRecords", allFSRecords);            
          }
          
          //get total approved for bottom of form           
          int totamtAppr = bdh.calcTotalAmtApproved(grantid, fycode);                
          request.setAttribute("totAmtAppr",new Integer(totamtAppr));
          request.setAttribute("fundsource", fundsource);
          
           String fyperiod="";
           if(fycode==0)
             fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
           else
             fyperiod=dbh.determineFiscalPeriodString(fycode);
           request.setAttribute("fyPeriod", fyperiod);
           
           //7/7/15 adjust literacy fycode in proj number
           if(gb.getFccode()==40 || gb.getFccode()==42){
              gb.setFycode(fycode);
              request.setAttribute("thisGrant", gb);
           }
           
          RequestDispatcher rd = request.getRequestDispatcher("statutory/fs10aHTML.jsp");
          rd.forward(request, response);  
         return;//12/12/12 added b/c illegalstateexception
       }
//==============================================================================
       else if(todo.equals("fs10apdf"))
       {
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);   
       
          if(gb.getFccode()==6 || gb.getFccode()==7)
          {            
            OfficerBean presOfficerBean = odb.getOfficerAssigned(grantid,"Preservation Officer");    
            s.setAttribute("presOfficerBean", presOfficerBean);
          }
          else if(gb.getFccode()==20){//for stateaid nyhs/cjh
              
        	//per BL; need 4 digit proj num to be same every year for stateaid
          	gb.setProjseqnum(dbh.handleStateAidProjNumbers(gb.getInstID()));       
              s.setAttribute("thisGrant", gb);
              
              //set libdirector to 'presofficer' bean; used to populate 'report prepared by' field since stateaid does not
              //use presofficer or projmanager
              s.setAttribute("presOfficerBean", libdirectorBean);            
          }
          else{
        	  
            OfficerBean pm = odb.getProjectManager(grantid);
            s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
            
          }
          
         String fy = request.getParameter("fya");
         int fycode = 0;
         if(fy!=null && !fy.equals(""))//lit now uses fs10a by FY
           fycode=Integer.parseInt(fy);
         
                     
          FS10DBbean fs = new FS10DBbean();
           if(fycode==0){//for cp and lgrmif
               ArrayList allFSRecords = fs.getFSARecords(grantid);        
               s.setAttribute("allFSRecords", allFSRecords);
           }
           else{//for literacy split by year
               ArrayList allFSRecords = fs.getFSARecordsForFy(grantid, fycode);        
               s.setAttribute("allFSRecords", allFSRecords);            
           }
                             
          
          //get total approved for bottom of form
          int totamtAppr = bdh.calcTotalAmtApproved(grantid, fycode);        
          s.setAttribute("totAmtAppr",new Integer(totamtAppr));
          s.setAttribute("fundsource", fundsource);
          
          String fyperiod="";
          if(fycode==0)
            fyperiod=dbh.determineFiscalPeriodString(gb.getFycode());
          else
            fyperiod=dbh.determineFiscalPeriodString(fycode);
          s.setAttribute("fyPeriod", fyperiod);
          
           //7/7/15 adjust literacy fycode in proj number
           if(gb.getFccode()==40 || gb.getFccode()==42){
              gb.setFycode(fycode);
              s.setAttribute("thisGrant", gb);
           }
          
          response.sendRedirect("fs10aForm.pdf");
          return;//12/12/12 added b/c illegalstateexception
       }
//==============================================================================
       else if(todo.equals("fs10DiAppr"))
       {
          String fy = request.getParameter("fy");
          int fycode = Integer.parseInt(fy);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          request.setAttribute("libDirectorBean", libdirectorBean);         
                    
          OfficerBean pm = odb.getProjectManager(grantid);
          request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          
          if(fycode==0)
            gb = dbh.findFyDates(gb.getFycode(), gb);
          else
            gb = dbh.findFyDates(fycode, gb);
          gb.setFederalid(dbh.determineFederalId(gb.getInstID())); 
          request.setAttribute("fundsource", fundsource);
                    
           s.setAttribute("thisGrant", gb);          
                              
           //get total amt APPROVED - diff from apcnt version of FS10Long
           BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
           request.setAttribute("budgetBean", bb);
           
           TotalsBean tb = new TotalsBean();
           tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
           tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
           tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
           request.setAttribute("totalsBean", tb);
         
                     
          String finalTarget="/fsform/fs10HtmlApprove.jsp";
                    
          RequestDispatcher rd = request.getRequestDispatcher(finalTarget);
          rd.forward(request, response);
         return;//12/12/12 added b/c illegalstateexception
       }
//=================================================================================
       else if(todo.equals("fs10DiApprpdf"))
       {
          String fy = request.getParameter("fy");
          int fycode = Integer.parseInt(fy);
          
          OfficerBean libdirectorBean = odb.getOfficerAssigned(grantid, "Library Director");
          s.setAttribute("libDirectorBean", libdirectorBean);  
                    
          OfficerBean pm = odb.getProjectManager(grantid);
          s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
                    
           if(fycode==0)
             gb = dbh.findFyDates(gb.getFycode(), gb);
           else
             gb = dbh.findFyDates(fycode, gb);
          gb.setFederalid(dbh.determineFederalId(gb.getInstID())); 
          s.setAttribute("fundsource", fundsource);          
         
           s.setAttribute("thisGrant", gb); 
                  
          //get total amt APPROVED - diff from apcnt version of FS10Long
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, fycode, true, asb, false, 0);
          s.setAttribute("budgetBean", bb);
          
          TotalsBean tb = new TotalsBean();
          tb = bdh.calcTotalAmtApprCategorized(grantid, tb, fycode);
          tb = bdh.calcAmtExpSuppEquip(grantid, tb, fycode, true);
          tb = bdh.calcAmtExpProfSupport(grantid, tb, fycode, true);
          s.setAttribute("totalsBean", tb);
          
          String finalTarget="fs10CpApprove.pdf";
                   
          response.sendRedirect(finalTarget);
          return;//12/12/12 added b/c illegalstateexception
       }
//==============================================================================       
       else if(todo.equals("fs25"))
       {
         OfficerBean pm = odb.getProjectManager(grantid);
         request.setAttribute("presOfficerBean", pm);//use po name even though data is pm
         
         //need to get current month/year
         Calendar cal = Calendar.getInstance();
         int month = cal.get(Calendar.MONTH) +1;
         request.setAttribute("monthCurr", month);
         
         int year = cal.get(Calendar.YEAR);
         request.setAttribute("yearCurr", year);
          
          RequestDispatcher rd = request.getRequestDispatcher("fsform/fs25html.jsp");
          rd.forward(request, response);  
         return;//12/12/12 added b/c illegalstateexception
       }
//==============================================================================
       else if(todo.equals("fs25pdf"))
       {
         OfficerBean pm = odb.getProjectManager(grantid);
         s.setAttribute("presOfficerBean", pm);//use po name even though data is pm
          
         //need to get current month/year
         Calendar cal = Calendar.getInstance();
         int month = cal.get(Calendar.MONTH) +1;
         s.setAttribute("monthCurr", month);
         
         int year = cal.get(Calendar.YEAR);
         s.setAttribute("yearCurr", year);
         
          response.sendRedirect("fs25Form.pdf");
          return;//12/12/12 added b/c illegalstateexception
       }
          
          
      
    }catch(Exception e)
    {
      System.out.println("error FsFormServlet "+ e.getMessage().toString());
      
      //needed to send exception name/description to error page -otherwise blank page
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