/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AdminBudgetSave.java
 * Creation/Modification History  :
 * SH   9/25/07      Created
 * 8/20/15 modified per BLILLEY; CP Discretionary admin can now update all budget fields.
 *
 * Description
 * This action will save admin budget for sa/co/di/fl/al/lg/cn/staid. Handles budget save, 
 * copy amt requested, and copy exp submitted buttons. 
 *****************************************************************************/
package statutory;
import clobpackage.ClobBean;

import construction.ConstructAllocationDbBean;
import construction.ConstructionDBbean;
import construction.SystemAssignBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;
import mypackage.DBHandler;
import mypackage.DescriptionBean;
import mypackage.FS10DBbean;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.TotalsBean;
import mypackage.UserBean;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdminBudgetSave extends Action
{
  private DBHandler dbh = new DBHandler();
  
  
    public ActionForward execute(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {        
      String finalTarget="";
      HttpSession sess = request.getSession(); 
      BudgetDBHandler bdh = new BudgetDBHandler(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
      
      try{           
        String mod = request.getParameter("p");
        String tab = request.getParameter("currtab"); 
        int ctab = Integer.parseInt(tab);
        
        //check user permissions
        UserBean userb = (UserBean) sess.getAttribute("lduser");
        String permiss = "", lgtab="";
        int lgtabint =0;
        if(mod.equals("lg"))
        {
          lgtab = request.getParameter("lgtab");
          sess.setAttribute("pagemodule", "lg");
          finalTarget=mod+"tab"+lgtab;
          if(userb.isLgadmin())
            permiss = "approve";
        }
        else if(mod.equals("lgr")) {
            lgtab = request.getParameter("lgtab");
            sess.setAttribute("pagemodule", "lgr");
            finalTarget=mod+"tab"+lgtab;
            if(userb.isLgreviewer())
              permiss = "approve";
        }
        else if(mod.equals("cn"))
        {
            permiss=userb.getAdmconstruction();
            //note:'tab' variable from above is for saving records by table (3=purchased,4=suppequip)
            lgtab= request.getParameter("constructtab");//lgtab (4,5,6) used to set finaltarget page and refresh recs on page
            finalTarget=mod+"tab"+lgtab;//cn admin ->format cntab4 or cntab5 or cntab6
        }
        else if(mod.equals("di"))
        {
          permiss=userb.getAdmindisc();
          finalTarget=mod+"tab"+tab;
        }
        else if(mod.equals("fl"))
        {
          lgtab = request.getParameter("littab");//lit budget page number
          lgtabint = Integer.parseInt(lgtab);
          request.setAttribute("p", mod);
          permiss=userb.getAdminfl();
          finalTarget=mod+"tab"+lgtab;
        }
        else if(mod.equals("al"))
        {
          lgtab = request.getParameter("littab");
          lgtabint = Integer.parseInt(lgtab);
          request.setAttribute("p", mod);
          permiss=userb.getAdminal();
          finalTarget=mod+"tab"+lgtab;
        }
        else
        {
          permiss=userb.getAdminstat();//stat or coor or stateaid
          finalTarget=mod+"tab"+tab;//ex satab1  or satab2
        }
        if( !permiss.equals("approve"))
          return mapping.findForward("authorize");        
          
                         
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);        
        String task = request.getParameter("btn");
                        
        //cast the action form to a BudgetCollectionBean
        BudgetCollectionBean bc = (BudgetCollectionBean) form;
        
          //System.out.println("mod "+mod);
          //System.out.println("lgtab "+lgtabint);
        if(task.equals("Save")){
            if(mod.equals("staid"))//tateaid admin can save amt_amend
               bdh.updateAdminBudgetAmendment(bc, userb, ctab);
            else if(mod.equals("lg") || mod.equals("cn") || mod.equals("di") || mod.equals("sa"))//lg & cn & di & statutory admin can save all apcnt budget info
                bdh.updateAdminApcntBudget(bc, userb, ctab);
            else if((mod.equals("fl")|| mod.equals("al")) &&  (lgtabint==4 || lgtabint==5 || lgtabint==6 || lgtabint==7 )  )
                bdh.updateAdminApcntBudget(bc, userb, ctab);//lit starting 13-14 can save all info on 4 tabs
            else
               bdh.updateAdminBudget(bc, userb, ctab); //as of 11/23/15 no longer used for statutory.
        }
        else if(task.equals("Copy Amt Requested"))
          dbh.autoSaveApprAmts(userb, ctab, grantid, "AmtReq");
        else if(task.equals("Copy Exp Submitted"))
          dbh.autoSaveApprAmts(userb, ctab, grantid, "ExpSub");
        
        
        
        
        
    //-----------------------------------------------------------------------
        //refresh budget session vars        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);  
        
        GrantBean gb=dbh.getRecordBean(grantid); 
        request.setAttribute("thisGrant", gb);  
        
        if(asb.getFccode()==80 || asb.getFccode()==86 || asb.getFccode()==40 || asb.getFccode()==42)
        { //AL, FL, LGRMIF, CONSTRUCTION - split type codes
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(lgtab));
          request.setAttribute("BudgetCollectionBean", bb);
        }else{
          BudgetCollectionBean bb = dbh.getBudgetBeanRecords(grantid, 0, true, asb, false, ctab);
          request.setAttribute("BudgetCollectionBean", bb);
        }      
          
        if(mod.equals("lg") || mod.equals("lgr"))
        {
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
          request.setAttribute("totalsBean", tb);
          
          TotalsBean tb1 = bdh.calcLgAmtApprForPanel( grantid, asb.getFycode(), tb.getTotAmtAppr());
          request.setAttribute("fyTotal", tb1);
          
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLgBudgetNarrative(request, lgtab, grantid);
        }
        else if(mod.equals("cn")) //construction
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
        }
        else if(mod.equals("di"))
        {
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
          request.setAttribute("totalsBean", tb);  
        
          TotalsBean tb1 = bdh.calcDiFyTotalAmtAppr(asb.getFycode());
          request.setAttribute("fyTotal", tb1);  
        }
        else if(mod.equals("co"))
        {
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
          request.setAttribute("totalsBean", tb);  
        
          //added 2/17/11-totals for each year of project
          HashMap tb1 = bdh.calcFyBudgetTotals(grantid, asb.getFycode(), true, asb);
          request.setAttribute("totalsMap", tb1);
            
          //the amt appr cannot go over 350k per fiscal year
          Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
          request.setAttribute("fyTotals", totals);   
        }
        else if(mod.equals("fl") || mod.equals("al"))
        {
         //check amt appr cannot go over 200k per fiscal year
          Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
          request.setAttribute("fyTotals", totals);  
          
          //starting FY2013-14; check that award is not over allocation for given grant
          if(asb.getFycode()>13){
              Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
              request.setAttribute("fyWarnings", warns);
          }
            
          HashMap hm = dbh.getProjectBudgetTotalsByFy(grantid, true, asb, gb.getInstID());
          request.setAttribute("totalsMap", hm);
          
          NarrativeDBbean ndb = new NarrativeDBbean();
          ndb.getLitBudgetNarrative(request, lgtab, grantid, asb.getFccode(), asb.getFycode()); 
        }
        else if(mod.equals("staid"))
        {//stateaid
            TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
            request.setAttribute("totalsBean", tb);
        }
        else //mod is statutory
        {
          TotalsBean tb = dbh.getProjectBudgetTotals(grantid, true, asb);
          request.setAttribute("totalsBean", tb);
        
          FS10DBbean fs = new FS10DBbean();
          ArrayList allFSRecords = fs.getFSARecords(grantid);        
          request.setAttribute("allFSRecords", allFSRecords);
          request.setAttribute("fsaSize", new Integer(allFSRecords.size()) );     
        }
                                     
      }catch(Exception e) {
        System.out.println("error AdminBudgetSave "+e.getMessage().toString());
      }        
      return mapping.findForward(finalTarget);       
    }
    
        
    public boolean checkSessionTimeout(HttpSession sess)
    {
      boolean timeout = false;
      //check for session timeout
      boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
      if (!userID && sess.isNew())
        timeout = true;
        
      return timeout;
    }
    
}