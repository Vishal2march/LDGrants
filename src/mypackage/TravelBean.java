package mypackage;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class TravelBean extends ActionForm
{
  public long id;
  public String description;
  public String purpose;
  public long grantid;
  public int fycode;
  public int grantrequest;
  public String grantrequestStr;
  public int amountapproved;
  public String amountapprovedStr;
  public int expsubmitted;
  public String expsubmittedStr;
  public String expapprovedStr;
  public int expapproved;
  public int projtotal;
  public String projtotStr;
  public String instcontStr;
  public int instcont;
  public int amtamended;
  public String amtamendedStr;
  public String costsummary;
  public boolean adminwarning;
  public String warningmsg;
  public boolean categoryTotal;
  public String travelPeriod;
  public String journalEntry;
  public String travelerName;

  public TravelBean()
  {
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getPurpose()
  {
    return purpose;
  }

  public void setPurpose(String purpose)
  {
    this.purpose = purpose;
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

  public int getGrantrequest()
  {
    return grantrequest;
  }

  public void setGrantrequest(int grantrequest)
  {
    this.grantrequest = grantrequest;
  }

  public String getGrantrequestStr()
  {
    return grantrequestStr;
  }

  public void setGrantrequestStr(String grantrequestStr)
  {
    this.grantrequestStr = grantrequestStr;
  }

  public int getAmountapproved()
  {
    return amountapproved;
  }

  public void setAmountapproved(int amountapproved)
  {
    this.amountapproved = amountapproved;
  }

  public String getAmountapprovedStr()
  {
    return amountapprovedStr;
  }

  public void setAmountapprovedStr(String amountapprovedStr)
  {
    this.amountapprovedStr = amountapprovedStr;
  }

  public int getExpsubmitted()
  {
    return expsubmitted;
  }

  public void setExpsubmitted(int expsubmitted)
  {
    this.expsubmitted = expsubmitted;
  }

  public String getExpsubmittedStr()
  {
    return expsubmittedStr;
  }

  public void setExpsubmittedStr(String expsubmittedStr)
  {
    this.expsubmittedStr = expsubmittedStr;
  }

  public String getExpapprovedStr()
  {
    return expapprovedStr;
  }

  public void setExpapprovedStr(String expapprovedStr)
  {
    this.expapprovedStr = expapprovedStr;
  }

  public int getExpapproved()
  {
    return expapproved;
  }

  public void setExpapproved(int expapproved)
  {
    this.expapproved = expapproved;
  }

  public int getProjtotal()
  {
    return projtotal;
  }

  public void setProjtotal(int projtotal)
  {
    this.projtotal = projtotal;
  }

  public String getProjtotStr()
  {
    return projtotStr;
  }

  public void setProjtotStr(String projtotStr)
  {
    this.projtotStr = projtotStr;
  }

  public String getInstcontStr()
  {
    return instcontStr;
  }

  public void setInstcontStr(String instcontStr)
  {
    this.instcontStr = instcontStr;
  }

  public int getInstcont()
  {
    return instcont;
  }

  public void setInstcont(int instcont)
  {
    this.instcont = instcont;
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

  public String getCostsummary()
  {
    return costsummary;
  }

  public void setCostsummary(String costsummary)
  {
    this.costsummary = costsummary;
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
        this.warningmsg="Warning:  The amount approved for record "+this.purpose
        + " is more than the amount requested.";
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

    public void setTravelPeriod(String travelPeriod) {
        this.travelPeriod = travelPeriod;
    }

    public String getTravelPeriod() {
        return travelPeriod;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getTravelerName() {
        return travelerName;
    }
}
