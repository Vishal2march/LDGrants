/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  PersonalBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class has get/set accessors to store and retrieve info from the 
 * Personal_Service expense table.
 *****************************************************************************/
package mypackage;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PersonalBean extends ActionForm
{
  public String name;
  public String title;
  public String salaryrate;
  public float fte;
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
  public String projtotalStr;
  public String instcontStr;
  public String fteStr;
  public String cost;
  public int salary;
  public float salaryf;
  public String type;
  public int typeCode;
  public String module;
  public boolean adminwarning;
  public String warningmsg;
  public boolean categoryTotal;
  public Date beginDate;
  public Date endDate;
  public String beginDateStr;
  public String endDateStr;
  

  public PersonalBean()
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

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getSalaryrate()
  {
    return salaryrate;
  }

  public void setSalaryrate(String salaryrate)
  {
    this.salaryrate = salaryrate;
  }

  public float getFte()
  {
    return fte;
  }

  public void setFte(float fte)
  {
    this.fte = fte;
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

  public String getProjtotalStr()
  {
    return projtotalStr;
  }

  public void setProjtotalStr(String projtotalStr)
  {
    this.projtotalStr = projtotalStr;
  }

  public String getInstcontStr()
  {
    return instcontStr;
  }

  public void setInstcontStr(String instcontStr)
  {
    this.instcontStr = instcontStr;
  }
   
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getName())) 
      errors.add("nameMissing", new ActionError("value.bgtrequired", "Name"));
        
    if (isMissing(getTitle())) 
      errors.add("titleMissing", new ActionError("value.bgtrequired", "Title"));
        
    if (isMissing(getSalaryrate())) 
      errors.add("salMissing", new ActionError("value.bgtrequired", "Salary/Rate"));
        
    if (isMissing(getGrantrequestStr())) 
      errors.add("amtreqMissing", new ActionError("value.bgtrequired", "Amount Requested"));
            
    return(errors);
  }

  public String getFteStr()
  {
    return fteStr;
  }

  public void setFteStr(String fteStr)
  {
    this.fteStr = fteStr;
  }

  public String getCost()
  {
    return cost;
  }

  public void setCost(String cost)
  {
    this.cost = cost;
  }

  public int getSalary()
  {
    return salary;
  }

  public void setSalary(int salary)
  {
    this.salary = salary;
  }

  public float getSalaryf()
  {
    return salaryf;
  }

  public void setSalaryf(float salaryf)
  {
    this.salaryf = salaryf;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public int getTypeCode()
  {
    return typeCode;
  }

  public void setTypeCode(int typeCode)
  {
    this.typeCode = typeCode;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
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
        this.warningmsg = "Warning:  The amount approved for record "+ this.name 
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
}
