/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  OtherExpBean.java
 * Creation/Modification History  :
 * SH   2/1/07      Created
 *
 * Description
 * This class has get/set accessors to store and retrieve all info from the 
 * Other_Expense table.  
 *****************************************************************************/
package mypackage;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class OtherExpBean extends ActionForm
{
  public String description;
  public int grantrequest;
  public int amountapproved;
  public int expsubmitted;
  public int expapproved;
  public int amtamended;
  public long id;
  public String amountapprovedStr;
  public String expapprovedStr;
  public String expsubmittedStr;
  public String grantrequestStr;
  public String amtamendedStr;
  public int projtotal;
  public int instcont;
  public long grantid;
  public int fycode;
  public String fiscalyear;
  public String instcontStr;
  public String projtotStr;
  public boolean adminwarning;
  public String warningmsg;
  public boolean categoryTotal;
  public String encumbranceDateStr;
  public Date encumbranceDate;
  public String journalEntry;
  public String costsummary;
  public String providerUsed;

  public OtherExpBean()
  {
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getGrantrequest()
  {
    return grantrequest;
  }

  public void setGrantrequest(int grantrequest)
  {
    this.grantrequest = grantrequest;
  }

  public int getAmountapproved()
  {
    return amountapproved;
  }

  public void setAmountapproved(int amountapproved)
  {
    this.amountapproved = amountapproved;
  }

  public int getExpsubmitted()
  {
    return expsubmitted;
  }

  public void setExpsubmitted(int expsubmitted)
  {
    this.expsubmitted = expsubmitted;
  }

  public int getExpapproved()
  {
    return expapproved;
  }

  public void setExpapproved(int expapproved)
  {
    this.expapproved = expapproved;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getAmountapprovedStr()
  {
    return amountapprovedStr;
  }

  public void setAmountapprovedStr(String amountapprovedStr)
  {
    this.amountapprovedStr = amountapprovedStr;
  }

  public String getExpapprovedStr()
  {
    return expapprovedStr;
  }

  public void setExpapprovedStr(String expapprovedStr)
  {
    this.expapprovedStr = expapprovedStr;
  }

  public String getExpsubmittedStr()
  {
    return expsubmittedStr;
  }

  public void setExpsubmittedStr(String expsubmittedStr)
  {
    this.expsubmittedStr = expsubmittedStr;
  }

  public String getGrantrequestStr()
  {
    return grantrequestStr;
  }

  public void setGrantrequestStr(String grantrequestStr)
  {
    this.grantrequestStr = grantrequestStr;
  }

  public int getProjtotal()
  {
    return projtotal;
  }

  public void setProjtotal(int projtotal)
  {
    this.projtotal = projtotal;
  }

  public int getInstcont()
  {
    return instcont;
  }

  public void setInstcont(int instcont)
  {
    this.instcont = instcont;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getFiscalyear()
  {
    return fiscalyear;
  }

  public void setFiscalyear(String fiscalyear)
  {
    this.fiscalyear = fiscalyear;
  }
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getDescription())) 
    {
      errors.add("descMissing", new ActionError("value.bgtrequired", "Description"));
    } 
    
    if (isMissing(getGrantrequestStr())) 
    {
      errors.add("amtreqMissing", new ActionError("value.bgtrequired", "Amount Requested"));
    }
        
    return(errors);
  }

  public String getInstcontStr()
  {
    return instcontStr;
  }

  public void setInstcontStr(String instcontStr)
  {
    this.instcontStr = instcontStr;
  }

  public String getProjtotStr()
  {
    return projtotStr;
  }

  public void setProjtotStr(String projtotStr)
  {
    this.projtotStr = projtotStr;
  }


    public void setAdminwarning(boolean adminwarning) {
        this.adminwarning = adminwarning;
    }

    public boolean isAdminwarning() {
        return adminwarning;
    }

    public void setWarningmsg(String warningmsg) {
        this.warningmsg = warningmsg;
    }

    public String getWarningmsg() {
        this.warningmsg="Warning:  The amount approved for record "+ this.description
        +" is more than the amount requested.";
        return warningmsg;
    }

    public void setCategoryTotal(boolean categoryTotal) {
        this.categoryTotal = categoryTotal;
    }

    public boolean isCategoryTotal() {
        return categoryTotal;
    }

    public void setAmtamended(int amtamended) {
        this.amtamended = amtamended;
    }

    public int getAmtamended() {
        return amtamended;
    }

    public void setAmtamendedStr(String amtamendedStr) {
        this.amtamendedStr = amtamendedStr;
    }

    public String getAmtamendedStr() {
        return amtamendedStr;
    }

    public void setEncumbranceDateStr(String encumbranceDateStr) {
        this.encumbranceDateStr = encumbranceDateStr;
    }

    public String getEncumbranceDateStr() {
        return encumbranceDateStr;
    }

    public void setEncumbranceDate(Date encumbranceDate) {
        this.encumbranceDate = encumbranceDate;
    }

    public Date getEncumbranceDate() {
        return encumbranceDate;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setCostsummary(String costsummary) {
        this.costsummary = costsummary;
    }

    public String getCostsummary() {
        return costsummary;
    }

    public void setProviderUsed(String providerUsed) {
        this.providerUsed = providerUsed;
    }

    public String getProviderUsed() {
        return providerUsed;
    }
}
