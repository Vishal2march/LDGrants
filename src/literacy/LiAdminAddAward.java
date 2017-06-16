package literacy;


import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDBHandler;

import mypackage.BudgetDeleteBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.NarrativeDBbean;
import mypackage.UserBean;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LiAdminAddAward extends DispatchAction {
    
    BudgetDBHandler bdh = new BudgetDBHandler();
    DBHandler dbh = new DBHandler();
    
    
    
    
    
    public ActionForward adminrec(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception
      {        
        String cprogram="", ctab="", littab="";
        HttpSession sess = request.getSession();          
            
        try{              
          boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
          if (!userID && sess.isNew())         
            mapping.findForward("timeout");
                        
          UserBean userb = (UserBean) sess.getAttribute("lduser");               
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
         
          String fyoffset = request.getParameter("fy");
          cprogram = request.getParameter("p");//al or fl
          request.setAttribute("p", cprogram);
          
          ctab = request.getParameter("tab");//budget table
          int tab = Integer.parseInt(ctab);
          
          littab= request.getParameter("t");//lit budget page number
          int smetcode=0;
          if(littab!=null &&  (!littab.equals(""))) {
              smetcode=bdh.determineLitBudgetCode(Integer.parseInt(littab));
          }
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);  
          
          int fy = Integer.parseInt(fyoffset);
          fy = fy+ asb.getFycode();
                      
          //add blank record
          int outcome = bdh.addAdminBudgetItem(userb, grantid, tab, fy, smetcode);
               
          //redisplay admin budget page
           BudgetCollectionBean bb= dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(littab));
           request.setAttribute("BudgetCollectionBean", bb);        
                  
           GrantBean gb = dbh.getRecordBean(grantid);
           HashMap hm = dbh.getProjectBudgetTotalsByFy(grantid, true, asb, gb.getInstID());
           request.setAttribute("totalsMap", hm);
           
           //check amt appr cannot go over 200k per fiscal year
           Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
           request.setAttribute("fyTotals", totals);  
           
           //starting FY2013-14; check that award is not over allocation for given grant
           if(asb.getFycode()>13){
               Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
               request.setAttribute("fyWarnings", warns);
           }
           
           NarrativeDBbean ndb = new NarrativeDBbean();
           ndb.getLitBudgetNarrative(request, littab, grantid, asb.getFccode(), asb.getFycode());
                                    
       }catch(Exception e){
         System.out.println("error LiAdminAddAward "+e.getMessage().toString());
       }       
       return mapping.findForward(cprogram +"tab"+ littab);         
    }                    
    
    
    
    
    
    
    
  public ActionForward blankrec(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception
    {        
      String cprogram="", ctab="", littab="";
      HttpSession sess = request.getSession();          
          
      try{              
        boolean userID = (sess.getAttribute("lduser") != null); //attr is created during login
        if (!userID && sess.isNew())         
          mapping.findForward("timeout");
                      
        UserBean userb = (UserBean) sess.getAttribute("lduser");               
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
       
        String fyoffset = request.getParameter("fy");
        cprogram = request.getParameter("p");//al or fl
        request.setAttribute("p", cprogram);
        
        ctab = request.getParameter("tab");//budget table
        int tab = Integer.parseInt(ctab);
        
        littab= request.getParameter("t");//lit budget page number
        int smetcode=0;
        if(littab!=null &&  (!littab.equals(""))) {
            smetcode=bdh.determineLitBudgetCode(Integer.parseInt(littab));
        }
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);  
        
                            
        //add blank record   ONLY DIFFERENCE BETWEEN 2 ACTIONS IS BLANK VS ADMIN RECORD
        int outcome = bdh.addBudgetFy(userb, grantid, tab, Integer.parseInt(fyoffset), smetcode);
             
        //redisplay admin budget page
         BudgetCollectionBean bb= dbh.getBudgetBeanRecords(grantid, 0, true, asb, true, Integer.parseInt(littab));
         request.setAttribute("BudgetCollectionBean", bb);        
                
         GrantBean gb = dbh.getRecordBean(grantid);
         HashMap hm = dbh.getProjectBudgetTotalsByFy(grantid, true, asb, gb.getInstID());
         request.setAttribute("totalsMap", hm);
         
         //check amt appr cannot go over 200k per fiscal year
         Vector totals = bdh.getTotalApprForFyCoLit(asb.getFycode(), asb.getFccode());
         request.setAttribute("fyTotals", totals);  
         
         //starting FY2013-14; check that award is not over allocation for given grant
         if(asb.getFycode()>13){
             Vector warns = bdh.getTotApproveLitAllocation(grantid, asb.getFycode(), asb.getFccode(), gb.getInstID());
             request.setAttribute("fyWarnings", warns);
         }
         
         NarrativeDBbean ndb = new NarrativeDBbean();
         ndb.getLitBudgetNarrative(request, littab, grantid, asb.getFccode(), asb.getFycode());
                                  
     }catch(Exception e){
       System.out.println("error LiAdminAddAward "+e.getMessage().toString());
     }       
     return mapping.findForward(cprogram +"tab"+ littab);         
  }                    
  
  
  
  
  
  
  
  public ActionForward confirmbdgtdelete(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response) throws Exception 
  {             
    String module = "";
    HttpSession sess = request.getSession(); 
       
    try{
        String grantnum = (String) sess.getAttribute("grantid");
        long grantid = Long.parseLong(grantnum);
        
        AppStatusBean asb = dbh.getApplicationStatus(grantid);
        request.setAttribute("appStatus", asb);
        
        if(asb.getFccode()==40){
          module ="aladmin";
        }
        else{
            module ="fladmin";
        }
        BudgetDeleteBean bb = new BudgetDeleteBean();
        bb.setGrantid(grantid);
        bb.setId(Long.parseLong(request.getParameter("id").trim()));
        bb.setTab(Integer.parseInt(request.getParameter("tab").trim()));
        bb.setModule(module + request.getParameter("p"));
        //get descr of record
        bb.setDescription(bdh.getBudgetRecordDesc(bb.getTab(), bb.getId()) );
        
        request.setAttribute("deleteBean", bb);
    }
    catch(Exception e) {
      log.error("LiAdminAddAward "+e.getMessage().toString());
    }        
    return mapping.findForward(module+"confirmbdgtdelete");      
  }
  
  
  
  
}
