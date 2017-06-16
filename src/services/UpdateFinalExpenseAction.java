package services;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import mypackage.AppDatesBean;
import mypackage.AppDatesDBbean;
import mypackage.AppStatusBean;
import mypackage.BudgetCollectionBean;
import mypackage.ContractedBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.OtherExpBean;
import mypackage.PersonalBean;

import mypackage.SupplyBean;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UpdateFinalExpenseAction extends Action{
  public ActionForward execute(
   ActionMapping mapping,
   ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws Exception
   {    
      
       ActionErrors ae = null;
       ae = new ActionErrors();     
       BudgetCollectionBean allItems = (BudgetCollectionBean) form;
    
       //get the tab user was working on
       String tab = request.getParameter("currtab");
    
      try{
         int ctab = Integer.parseInt(tab);
          
         //get grant id from session
         HttpSession sess = request.getSession();
         String grantnum = (String) sess.getAttribute("grantid");
         long grantid = Long.parseLong(grantnum);
          
          //get appstatus; needed on fail; need to redisplay final budget fields (not initial budget)
          DBHandler dbh = new DBHandler(); 
          AppStatusBean asb = dbh.getApplicationStatus(grantid);
          request.setAttribute("appStatus", asb);
                              
          //get grantbean - budget pages use it to display projnum, branch depending on fy
          GrantBean gb = dbh.getRecordBean(grantid);
          request.setAttribute("thisGrant", gb);
          
          
          //new 10/25/16 for lgrmif encumbrance date validation
          //get appdates bean; used when validating encumbrance_date in case of extension beyond 7/1-6/30
          AppDatesDBbean datesDB = new AppDatesDBbean();
          AppDatesBean appDateBean = datesDB.searchDateByFundFiscalYear(gb.getFycode(), gb.getFccode());
          
          
          //get fy in 20xx format; the fycode only has last 2 digits of year
          String fy = "20"+asb.getFycode();
          int fycode=Integer.parseInt(fy);
          
         //collection of expense records from form
         List expList = null;
                  
         //System.out.println("tab is "+ctab);
          switch(ctab){
          case 1://personal services
                expList = allItems.getAllPersRecords();
                if(expList !=null)
                {                        
                    for(int i=0; i<expList.size(); i++)
                    {
                      PersonalBean pb = (PersonalBean)expList.get(i);   
                        
                       if(!pb.isCategoryTotal()){//don't validate admin award records
                                                    
                           if(!isMissing(pb.getBeginDateStr())){//if begin date exists; check format
                               if(isWrongDateFormat(pb.getBeginDateStr()))
                                 ae.add("startdateformat", new ActionError("errors.date", "Begin Date"));
                           }
                           
                           if(!isMissing(pb.getEndDateStr())){//if end date exists; check format
                               if(isWrongDateFormat(pb.getEndDateStr()))
                                 ae.add("startdateformat", new ActionError("errors.date", "End Date"));
                           }
                       }                                               
                      
                        if(ae !=null && (ae.size() > 0))
                        {
                          System.out.println("personal failed");
                          request.setAttribute(Globals.ERROR_KEY, ae);  
                          if(pb.getModule()!=null && 
                              (pb.getModule().equals("lg") || pb.getModule().equals("al") || pb.getModule().equals("fl") ))
                            return (mapping.findForward("fail"+pb.getTypeCode()));//special mapping for lg/lit
                          else
                            return (mapping.findForward("fail"));
                        }                       
                    }                                        
                }
                else {
                  return (mapping.findForward("fail"));//no personal records so nothing to save              
                }
                break;
          
          
          case 2://benefits
              break;
          
          case 3://contracted
              expList = allItems.getAllContractRecords();
              if(expList !=null)
              {                    
                  //for literacy only - get al vs fl parameter
                  String litmodule="";
                  if(request.getParameter("p")!=null)
                      litmodule = request.getParameter("p");
                  
                  for(int i=0; i<expList.size(); i++)
                  {
                    ContractedBean pb = (ContractedBean)expList.get(i);   
                      
                     if(!pb.isCategoryTotal()){//don't validate admin award records
                          
                         if(!isMissing(pb.getEncumbranceDateStr())){//if date exists; check format
                             if(isWrongDateFormat(pb.getEncumbranceDateStr()))
                               ae.add("startdateformat", new ActionError("errors.date", "Encumbrance Date"));
                             else 
                             {
                                 //date format is ok; now validate date btwn grant start/end period (except for lit)
                                 if(litmodule==null || litmodule.equals("")){
                                     
                                     if(isDateOutsideGrantPeriod(pb.getEncumbranceDateStr(), fycode, 
                                    		 					appDateBean.getExtensiondateStr()))
                                         ae.add("dateoutsiderange", new ActionError("errors.grantDatePeriod", 
                                                                             String.valueOf(fycode-1), fy));
                                 }
                             }
                         }                        
                     }                                               
                    
                      if(ae !=null && (ae.size() > 0))
                      {
                        request.setAttribute(Globals.ERROR_KEY, ae); 
                        if(pb.getModule()!=null && pb.getModule().equals("lg"))
                          return (mapping.findForward("fail"+pb.getTypeCode()));//for lgrmif
                        else if(!litmodule.equals("") && litmodule.equals("al"))                    
                            return (mapping.findForward("failal"));//for adult lit
                        else if(!litmodule.equals("") && litmodule.equals("fl"))
                            return (mapping.findForward("failfl"));//for family lit
                        else
                          return (mapping.findForward("fail"));
                      }                       
                  }                                          
              }
              else {
                return (mapping.findForward("fail"));//no contracted records so nothing to save              
              }              
              break;          
          
          
          case 4://supplies/equip
              expList = allItems.getAllSupplyRecords();
              if(expList !=null)
              {                 
              
                  //for literacy only - get al vs fl parameter
                  String litmodule="";
                  if(request.getParameter("p")!=null)
                      litmodule = request.getParameter("p");
                
                  for(int i=0; i<expList.size(); i++)
                  {
                    SupplyBean pb = (SupplyBean)expList.get(i);   
                      
                     if(!pb.isCategoryTotal()){//don't validate admin award records
                          
                         if(!isMissing(pb.getEncumbranceDateStr())){//if date exists; check format
                             if(isWrongDateFormat(pb.getEncumbranceDateStr()))
                               ae.add("startdateformat", new ActionError("errors.date", "Encumbrance Date"));
                             else 
                             {
                               //date format is ok; now validate date btwn grant start/end period (except for lit)
                               if(litmodule==null || litmodule.equals("")){
                                   
                                   if(isDateOutsideGrantPeriod(pb.getEncumbranceDateStr(), fycode, appDateBean.getExtensiondateStr()))
                                       ae.add("dateoutsiderange", new ActionError("errors.grantDatePeriod", 
                                                                                 String.valueOf(fycode-1), fy));
                               }
                             }
                         }                        
                     }//end for loop                                               
                    
                      if(ae !=null && (ae.size() > 0))
                      {
                        request.setAttribute(Globals.ERROR_KEY, ae);  
                                                
                        if(pb.getModule()!=null && pb.getModule().equals("lg"))                           
                          return (mapping.findForward("fail"+pb.getSuppmatCode()));
                        else if(litmodule!=null && !litmodule.equals("")){
                            if(litmodule.equals("fl") || litmodule.equals("al"))
                                return (mapping.findForward("fail"+pb.getSuppmatCode()));
                        }
                        else
                          return (mapping.findForward("fail"));
                      }                       
                  }                                                
              }
              else {
                return (mapping.findForward("fail"));//no supply records so nothing to save              
              }              
              break;      
          
          
          
          case 5://other expenses
              expList = allItems.getAllExpRecords();
              if(expList !=null)
              {                        
                  for(int i=0; i<expList.size(); i++)
                  {
                    OtherExpBean pb = (OtherExpBean)expList.get(i);   
                      
                     if(!pb.isCategoryTotal()){//don't validate admin award records
                          
                         if(!isMissing(pb.getEncumbranceDateStr())){//if date exists; check format
                             if(isWrongDateFormat(pb.getEncumbranceDateStr()))
                               ae.add("startdateformat", new ActionError("errors.date", "Encumbrance Date"));
                             else 
                             {
                               //date format is ok; now validate date btwn grant start/end period
                               if(isDateOutsideGrantPeriod(pb.getEncumbranceDateStr(), fycode, appDateBean.getExtensiondateStr()))
                                   ae.add("dateoutsiderange", new ActionError("errors.grantDatePeriod", 
                                                                             String.valueOf(fycode-1), fy));
                             }
                         }                        
                     }                                               
                    
                      if(ae !=null && (ae.size() > 0))
                      {
                        request.setAttribute(Globals.ERROR_KEY, ae);                          
                        return (mapping.findForward("fail"));
                      }                       
                  }                       
              }
              else {
                return (mapping.findForward("fail"));//no other records so nothing to save              
              }              
              break;   
          
          
          case 6:
              break;              
          }
                            
           //if we get here; validation passed
           return (mapping.findForward("success"));                
                       
       }catch(Exception e)
       {
         System.out.println("error UpdateFinalExpenseAction "+e.getMessage().toString());
         return (mapping.findForward("fail"));
       }    
  }
  
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public boolean isWrongDateFormat(String value) 
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    boolean badd = false;
    
    try{
      sdf.setLenient(false);
      Date thedate = sdf.parse(value);
      badd=false;
    }catch(Exception e)
    {badd = true; 
     System.out.println("exception");}
    
    return badd;
  }    
  
  
  /**
   * Modified 10/25/16 to check extension_date for lgrmif first; else use grant period
   * @param encumbranceDate
   * @param fycode
   * @param extensionDate
   * @return
   */
  public boolean isDateOutsideGrantPeriod(String encumbranceDate, int fycode, String extensionDate){
      boolean datebad = false;
      try{
          //convert str date to formatted date                
          Date encumbranceDateFmt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(encumbranceDate);
          //System.out.println("encumbranceDateFmt  "+encumbranceDateFmt);
                   
          Calendar grantStartDate = Calendar.getInstance();
          grantStartDate.clear();                
          grantStartDate.set((fycode-1), 6, 1, 0, 0, 0);// 7/1/20??
          //System.out.println("startdate"+grantStartDate.getTime());
          
          Calendar endFiscalDate = Calendar.getInstance();
          endFiscalDate.clear();                
          endFiscalDate.set(fycode, 5, 30, 0, 0, 0);//  6/30/20??
          //System.out.println("enddate"+endFiscalDate.getTime());
          
          
          
          //check if date is within the grant period start date
          if(encumbranceDateFmt.before(grantStartDate.getTime())){
              //System.out.println("error - before start date");
              datebad = true;              
          }
          
          
          //compare EITHER the extension date(if exists) OR grant period end date
          if(extensionDate!=null && !extensionDate.equals("")){//if extension date exists; use this
        	         	         	  
        	  Date extensionDateFmt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(extensionDate);
        	  
        	  //use extension date instead of end grant period date
        	  if(encumbranceDateFmt.after(extensionDateFmt)){
        		  //System.out.println("error - after extension date");
        		  datebad = true;
        	  }
        	  
          }//else, no extension date, so use grant period end date for comparison
          else{                    
        	  //check if date is within grant period end date
	          if(encumbranceDateFmt.after(endFiscalDate.getTime())){
	              //System.out.println("error - after end date");
	              datebad = true;
	          }          
          }
    
      }catch(Exception e){System.out.println("isDateOutsideGrantPeriod "+e.getMessage().toString());}
      
      return datebad;
  }
}
