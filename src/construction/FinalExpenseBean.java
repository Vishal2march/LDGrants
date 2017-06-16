package construction;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FinalExpenseBean extends ActionForm{
    public FinalExpenseBean() {
    }
    
    public long id;
    public long grantId;
    public long expenseId;
    public String expenseName;
    public String journalEntry;
    public String provider;
    public String description;
    public Date beginDate;
    public Date endDate;
    public String beginDateStr;
    public String endDateStr;
    public int expAmount;
    public String expAmountStr;
    public boolean grantFundYn;
    public int fycode;
    //following fields for completion form entries
    public String grantAmountStr;
    public long grantAmount;
    public long grantAmountFundId;
    public String projectCostStr;
    public long projectCost;
    public long projectCostFundId;
    public long buildingGrantId;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setExpAmount(int expAmount) {
        this.expAmount = expAmount;
    }

    public int getExpAmount() {
        return expAmount;
    }

    public void setExpAmountStr(String expAmountStr) {
        this.expAmountStr = expAmountStr;
    }

    public String getExpAmountStr() {
        return expAmountStr;
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
        Date thedate = sdf.parse(value);
        badd=false;
      }catch(Exception e)
      {badd = true; }
      
      return badd;
    }    
    
    
    public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      Format formatter = new SimpleDateFormat("MM/dd/yyyy");  
      
     if(isMissing(getProvider())) 
       errors.add("provMissing", new ActionError("value.required", "Vendor/Provider"));
    
     if(isMissing(getJournalEntry())) 
        errors.add("journMissing", new ActionError("value.required", "Journal/Check"));
        
     if(isMissing(getExpAmountStr())) 
         errors.add("expMissing", new ActionError("value.required", "Amount Expended"));
                
       if (isMissing(getBeginDateStr())){
            errors.add("startdateMissing", new ActionError("value.required", "Encumbrance Date"));
       }
       else{
          if(isWrongDateFormat(getBeginDateStr())){
            errors.add("startdateformat", new ActionError("errors.date", "Encumbrance Date"));
          }
          else{
            //we have a date, in correct format, now check date range, 7/17/12 per MLT
            //System.out.println("fycode is "+getFycode());
                        
            try{            
                //convert str date to formatted date                
                Date userdate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(getBeginDateStr());
                //System.out.println("userdate"+userdate);
                              
                Calendar beginFiscalDate = Calendar.getInstance();
                beginFiscalDate.clear();                
                beginFiscalDate.set((getFycode()-1), 0, 1, 0, 0, 0);// 1/1/20??
                //System.out.println("begindate"+beginFiscalDate.getTime());
                
                Calendar grantStartDate = Calendar.getInstance();
                grantStartDate.clear();                
                grantStartDate.set((getFycode()-1), 6, 1, 0, 0, 0);// 7/1/20??
               // System.out.println("startdate"+grantStartDate.getTime());
                
                Calendar endFiscalDate = Calendar.getInstance();
                endFiscalDate.clear();                
                endFiscalDate.set((getFycode()+2), 6, 30, 0, 0, 0);//  7/30/20??
               // System.out.println("enddate"+endFiscalDate.getTime());
                
               
                //check if date is within the 3 year range
                if(userdate.before(beginFiscalDate.getTime())){
                  //  System.out.println("error - before start date");
                    errors.add("beforefy", new ActionError("errors.cnDatePeriod", 
                        String.valueOf(getFycode()-1), String.valueOf(getFycode()+2)));
                }
                else if(userdate.before(grantStartDate.getTime())){
                    //if date before 6/30 -> must be a match_fund; NOT a grant_fund
                    if(isGrantFundYn()){
                      //  System.out.println("error - before 6/30 must be match_fund");
                        errors.add("matchdateonly", new ActionError("errors.cnMatchDateOnly", 
                            String.valueOf(getFycode()-1)));
                    }
                   // else
                   //     System.out.println("date before 6/30; but match date");
                    
                }
                else if(userdate.after(endFiscalDate.getTime())){//needs to be w/in 3yr range
                   // System.out.println("error - after end date");
                    errors.add("afterfy", new ActionError("errors.cnDatePeriod", 
                        String.valueOf(getFycode()-1), String.valueOf(getFycode()+2)));
                }
                
            }
            catch(Exception e){
                System.out.println("Exception: FinalExpenseBean.validate() "+e.getMessage());;
            }

          }
       }
       
             
       return(errors);
    }
    
    
    public ActionErrors validateCompletionForm(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
                       
       if (isMissing(getGrantAmountStr())){
            errors.add("amtMissing", new ActionError("value.required", "Final Grant Amount"));
       }
       
       if (isMissing(getProjectCostStr())){
            errors.add("costMissing", new ActionError("value.required", "Final Cost of Project"));
       }
             
       return(errors);
    }
    
    

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

   
    public void setGrantAmount(long grantAmount) {
        this.grantAmount = grantAmount;
    }

    public long getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmountFundId(long grantAmountFundId) {
        this.grantAmountFundId = grantAmountFundId;
    }

    public long getGrantAmountFundId() {
        return grantAmountFundId;
    }

    public void setProjectCostStr(String projectCostStr) {
        this.projectCostStr = projectCostStr;
    }

    public String getProjectCostStr() {
        return projectCostStr;
    }

    public void setProjectCost(long projectCost) {
        this.projectCost = projectCost;
    }

    public long getProjectCost() {
        return projectCost;
    }

    public void setProjectCostFundId(long projectCostFundId) {
        this.projectCostFundId = projectCostFundId;
    }

    public long getProjectCostFundId() {
        return projectCostFundId;
    }

    public void setBuildingGrantId(long buildingGrantId) {
        this.buildingGrantId = buildingGrantId;
    }

    public long getBuildingGrantId() {
        return buildingGrantId;
    }

    public void setGrantAmountStr(String grantAmountStr) {
        this.grantAmountStr = grantAmountStr;
    }

    public String getGrantAmountStr() {
        return grantAmountStr;
    }

    public void setGrantFundYn(boolean grantFundYn) {
        this.grantFundYn = grantFundYn;
    }

    public boolean isGrantFundYn() {
        return grantFundYn;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }
}
