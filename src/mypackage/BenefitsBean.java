/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  BenefitsBean.java
 * Creation/Modification History  :
 * SH   2/1/07      Created
 *
 * Description
 * This class will store records from the EMPLOYEE_BENEFITS expense table.  It includes 
 * all get/set accessors for fields in the table.
 *****************************************************************************/
package mypackage;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BenefitsBean extends ActionForm
{
  public String name;
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
  public float benpercent;
  public String benpercentStr;
  public String instcontStr;
  public String salary;
  public String cost;
  public float fte;
  public boolean adminwarning;
  public String warningmsg;
  public boolean categoryTotal;

  public BenefitsBean()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
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

  public float getBenpercent()
  {
    return benpercent;
  }

  public void setBenpercent(float benpercent)
  {
    this.benpercent = benpercent;
  }
    
  
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getName())) 
    {
      errors.add("nameMissing", new ActionError("value.bgtrequired", "Name"));
    } 
    
    if (isMissing(getBenpercentStr())) 
    {
      errors.add("percMissing", new ActionError("value.bgtrequired", "Benefits percentage"));
    } 
    
       
    if (isMissing(getGrantrequestStr())) 
    {
      errors.add("amtreqMissing", new ActionError("value.bgtrequired", "Amount Requested"));
    }
        
    return(errors);
  }

  public String getBenpercentStr()
  {
    return benpercentStr;
  }

  public void setBenpercentStr(String benpercentStr)
  {
    this.benpercentStr = benpercentStr;
  }

  public String getInstcontStr()
  {
    return instcontStr;
  }

  public void setInstcontStr(String instcontStr)
  {
    this.instcontStr = instcontStr;
  }

  public String getSalary()
  {
    return salary;
  }

  public void setSalary(String salary)
  {
    this.salary = salary;
  }

  public String getCost()
  {
    return cost;
  }

  public void setCost(String cost)
  {
    this.cost = cost;
  }

  public float getFte()
  {
    return fte;
  }

  public void setFte(float fte)
  {
    this.fte = fte;
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
        this.warningmsg="Warning:  The amount approved for this record "+ this.name
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
}
