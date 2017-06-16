package construction;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.BudgetDeleteBean;
import mypackage.DBHandler;

import mypackage.FS10DBbean;
import mypackage.PersonalBean;
import mypackage.TotalsBean;
import mypackage.UserBean;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ExpenseNavAction extends DispatchAction {
    private DBHandler dbh = new DBHandler();
    private ConstructionDBbean cdb = new ConstructionDBbean();
    
    
    public ActionForward selectexpense(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           String finalTarget = "";
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               String grantnum = (String) sess.getAttribute("grantid");
               long grantid = Long.parseLong(grantnum);
                
               AppStatusBean asb = dbh.getApplicationStatus(grantid);
               request.setAttribute("appStatus", asb);
                
               String fscode = request.getParameter("c");
                
               //get all expense records for this fs code
               ArrayList allexp = cdb.getFinalExpensesForCode(grantid, Integer.parseInt(fscode));
                
               BudgetCollectionBean bc = new BudgetCollectionBean();
               bc.setAllFinalExpenses(allexp);
               request.setAttribute("BudgetCollectionBean", bc);
               
               //get expense totals for bottom of page
               TotalsBean tb = cdb.getFinalExpenseTotalsForCode(grantid, Integer.parseInt(fscode));
               request.setAttribute("expenseTotals", tb);
               
               //navigate to selected fs code page
               switch(Integer.parseInt(fscode)){
                    case 45:  finalTarget="supplyexp";
                              break;
                    case 20:  finalTarget="equipexp";
                              break;
                    case 40:  finalTarget="purchaseexp";
                              break;              
               }
                      
           } catch (Exception e) {
              System.out.println("error ExpenseNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward(finalTarget);
     }
     
     
    public ActionForward addexp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
    {         
           String finalTarget = "";
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               UserBean ub = (UserBean) sess.getAttribute("lduser");
               String grantnum = (String) sess.getAttribute("grantid");
               long grantid = Long.parseLong(grantnum);             
               String fscode = request.getParameter("c");
                
               //insert new record
               cdb.insertFinalExpenseRecord(ub, grantid, Integer.parseInt(fscode));
                
               //get all expense records for this fs code
               ArrayList allexp = cdb.getFinalExpensesForCode(grantid, Integer.parseInt(fscode));
               
               BudgetCollectionBean bc = new BudgetCollectionBean();
               bc.setAllFinalExpenses(allexp);
               request.setAttribute("BudgetCollectionBean", bc);
               
               //get expense totals for bottom of page
               TotalsBean tb = cdb.getFinalExpenseTotalsForCode(grantid, Integer.parseInt(fscode));
               request.setAttribute("expenseTotals", tb);
                                           
               AppStatusBean asb = dbh.getApplicationStatus(grantid);
               request.setAttribute("appStatus", asb);
               
                //navigate to selected fs code page
                switch(Integer.parseInt(fscode)){
                    case 45:  finalTarget="supplyexp";
                              break;
                    case 20:  finalTarget="equipexp";
                              break;
                    case 40:  finalTarget="purchaseexp";
                              break;              
                }
                      
           } catch (Exception e) {
              System.out.println("error ExpenseNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward(finalTarget);
     }
     
     
     
   public ActionForward saveexp(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
   {         
           String finalTarget = "";
           HttpSession sess = request.getSession();
           if(checkSessionTimeout(sess))
              return mapping.findForward("timeout");
          
           try {    
               UserBean ub = (UserBean) sess.getAttribute("lduser");
               String grantnum = (String) sess.getAttribute("grantid");
               long grantid = Long.parseLong(grantnum);             
               String expensecode= (String) request.getParameter("eid"); 
               int fscode= Integer.parseInt(expensecode);
               
               //navigation back to fs code page
               switch(fscode){
                    case 45:  finalTarget="supplyexp";
                              break;
                    case 20:  finalTarget="equipexp";
                              break;
                    case 40:  finalTarget="purchaseexp";
                              break;              
               }
               
               //need the FYCODE to do date validation on expense records
               int fyear = dbh.getYearForGrant(grantid);
               
               ActionForm myform = (ActionForm) request.getAttribute("BudgetCollectionBean");
               //cast the action form to a BudgetCollectionBean 
               BudgetCollectionBean b = (BudgetCollectionBean) myform;
                
               //get the list of expenses from the form
               List expList = b.getAllFinalExpenses();
                
               if(expList !=null){
                 ActionErrors ae = null;//validate each expense record     
                 for(int i=0; i<expList.size(); i++)
                 {
                     FinalExpenseBean fb = (FinalExpenseBean)expList.get(i);   
                     fb.setFycode(fyear);//need FY for date validation
                     ae = fb.validate(mapping, request);//validate() returns ActionErrors
                                          
                     if(ae !=null && (ae.size() > 0)){
                       request.setAttribute(Globals.ERROR_KEY, ae);//return if errors                       
                                              
                       return (mapping.findForward(finalTarget));                       
                     }                       
                 }
               }
                   
               //if we get here; all validation has passed; save all records
               int outcome = cdb.updateExpenseRecords(b.getAllFinalExpenses(), ub.getUserid());
                                  
               //refresh all variables/data and redisplay page
               ArrayList allexp = cdb.getFinalExpensesForCode(grantid, fscode);
               
               BudgetCollectionBean bc = new BudgetCollectionBean();
               bc.setAllFinalExpenses(allexp);
               request.setAttribute("BudgetCollectionBean", bc);
               
               //get expense totals for bottom of page
               TotalsBean tb = cdb.getFinalExpenseTotalsForCode(grantid, fscode);
               request.setAttribute("expenseTotals", tb);
               
               AppStatusBean asb = dbh.getApplicationStatus(grantid);
               request.setAttribute("appStatus", asb);
                                                            
           } catch (Exception e) {
              System.out.println("error ExpenseNavAction "+e.getMessage());
              log.error(e.getMessage().toString());
           }   
           return mapping.findForward(finalTarget);
   }
     
     
    public ActionForward confirmdelete(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
           
          BudgetDeleteBean bb = new BudgetDeleteBean();
          bb.setGrantid(grantid);
          bb.setId(Long.parseLong(request.getParameter("id").trim()));
          bb.setModule(request.getParameter("p"));
          
          String fscode =request.getParameter("fs").trim();
          bb.setTab(Integer.parseInt(fscode));
         
          //get descr of final expense record
          bb.setDescription(cdb.getExpenseDescription(bb.getId()));          
          request.setAttribute("deleteBean", bb);
      }
      catch(Exception e) {
        log.error("ExpenseNavAction "+e.getMessage().toString());
      }        
      return mapping.findForward("confirmdelete");      
    }
    
    
    public ActionForward deleteexp(ActionMapping mapping, ActionForm form,
         HttpServletRequest request, HttpServletResponse response) throws Exception 
    {             
      String finalTarget="";
      HttpSession sess = request.getSession(); 
      if(checkSessionTimeout(sess))
        return mapping.findForward("timeout");
     
      try{
          String grantnum = (String) sess.getAttribute("grantid");
          long grantid = Long.parseLong(grantnum);
          
          String id = request.getParameter("id");
          String fs=request.getParameter("tab");
          int fscode=Integer.parseInt(fs);
          
          //delete record
          int outcome = cdb.deleteExpenseRecord(Long.parseLong(id));
          
          //refresh all variables/data and redisplay page
          ArrayList allexp = cdb.getFinalExpensesForCode(grantid, fscode);
          
          BudgetCollectionBean bc = new BudgetCollectionBean();
          bc.setAllFinalExpenses(allexp);
          request.setAttribute("BudgetCollectionBean", bc);
          
          //get expense totals for bottom of page
          TotalsBean tb = cdb.getFinalExpenseTotalsForCode(grantid, fscode);
          request.setAttribute("expenseTotals", tb);
                    
          //navigation back to fs code page
          switch(fscode){
               case 45:  finalTarget="supplyexp";
                         break;
               case 20:  finalTarget="equipexp";
                         break;
               case 40:  finalTarget="purchaseexp";
                         break;              
          }
          
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
      }
      catch(Exception e) {
        log.error("ExpenseNavAction "+e.getMessage().toString());
      }        
      return mapping.findForward(finalTarget);      
    }
    
     
    public boolean checkSessionTimeout(HttpSession sess)
    {
        boolean timeout = false;
        //check for session timeout
        boolean userID = (sess.getAttribute("lduser") != null);
        if (!userID && sess.isNew()){      
          timeout = true;
        }      
        return timeout;
    }
}//class
