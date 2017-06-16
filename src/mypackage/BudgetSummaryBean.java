package mypackage;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BudgetSummaryBean  extends ActionForm{
    public BudgetSummaryBean() {
        super();
    }
    
    long id;
    int fycode;
    String fiscalYear;
    int expensecode;
    String expenseName;
    long grantId;
    String amountStr;
    int amount;
    String serviceType;
    String vendor;
    String description;

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setExpensecode(int expensecode) {
        this.expensecode = expensecode;
    }

    public int getExpensecode() {
        return expensecode;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    
    
    
    
    
    
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
     
    if (isMissing(getAmountStr())) 
    {
      errors.add("amtMissing", new ActionError("value.bgtrequired", "Anticipated Amount"));
    }
        
    return(errors);
  }
}
