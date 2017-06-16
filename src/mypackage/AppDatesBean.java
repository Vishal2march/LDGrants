/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AppDatesBean.java
 * Creation/Modification History  :
 *
 * SH   10/1/07      Created
 *
 * Description
 * This action class has get/set accessors for all fields in the app_dates table
 *****************************************************************************/
package mypackage;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class AppDatesBean extends ActionForm
{
  public int fycode;
  public String fiscalyear;
  public int fccode;
  public String fundcode;
  public long id;
  public Date startdate;
  public Date enddate;
  public Date duedate;
  public Date finalrptdate;
  public Date interimrptdate;
  public Date reviewdate;
  public Date extensiondate;
  public String startdateStr;
  public String enddateStr;
  public String duedateStr;
  public String finalrptdateStr;
  public String interimrptdateStr;
  public String reviewdateStr;
  public String extensiondateStr;
  public boolean dateAcceptable;
  public boolean canApply;
  public int totalfund;
  public String totalfundStr;
  public String module;
  public boolean plsInstitution;

  public AppDatesBean()
  {
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

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }

  public String getFundcode()
  {
    return fundcode;
  }

  public void setFundcode(String fundcode)
  {
    this.fundcode = fundcode;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public Date getStartdate()
  {
    return startdate;
  }

  public void setStartdate(Date startdate)
  {
    this.startdate = startdate;
  }

  public Date getEnddate()
  {
    return enddate;
  }

  public void setEnddate(Date enddate)
  {
    this.enddate = enddate;
  }

  public Date getDuedate()
  {
    return duedate;
  }

  public void setDuedate(Date duedate)
  {
    this.duedate = duedate;
  }

  public String getStartdateStr()
  {
    return startdateStr;
  }

  public void setStartdateStr(String startdateStr)
  {
    this.startdateStr = startdateStr;
  }

  public String getEnddateStr()
  {
    return enddateStr;
  }

  public void setEnddateStr(String enddateStr)
  {
    this.enddateStr = enddateStr;
  }

  public String getDuedateStr()
  {
    return duedateStr;
  }

  public void setDuedateStr(String duedateStr)
  {
    this.duedateStr = duedateStr;
  }
  
  
  
   public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
   public boolean isMissing(int value) 
  {
    return((value == 0) );
  }
  
   public boolean isMissing(Date value) 
  {
    return((value == null) );
  }
  
  
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    if (isMissing(getFccode())) 
    {
      errors.add("fccodeMissing", new ActionError("value.required", "Grant Program"));
    } 
    if (isMissing(getFycode())) 
    {
      errors.add("fycodeMissing", new ActionError("value.required", "Fiscal Year"));
    } 
    if (isMissing(getStartdateStr())) 
    {
      errors.add("sdateMissing", new ActionError("value.required", "Available Date"));
    }    
    if(isMissing(getDuedateStr()))
    {
      errors.add("ddateMissing", new ActionError("value.required", "Due Date"));
    }
    
    return(errors);
  }

  public boolean isDateAcceptable()
  {
    return dateAcceptable;
  }

  public void setDateAcceptable(boolean dateAcceptable)
  {
    this.dateAcceptable = dateAcceptable;
  }

  public boolean isCanApply()
  {
    return canApply;
  }

  public void setCanApply(boolean canApply)
  {
    this.canApply = canApply;
  }

  public int getTotalfund()
  {
    return totalfund;
  }

  public void setTotalfund(int totalfund)
  {
    this.totalfund = totalfund;
  }

  public String getTotalfundStr()
  {
    return totalfundStr;
  }

  public void setTotalfundStr(String totalfundStr)
  {
    this.totalfundStr = totalfundStr;
  }


    public void setFinalrptdate(Date finalrptdate) {
        this.finalrptdate = finalrptdate;
    }

    public Date getFinalrptdate() {
        return finalrptdate;
    }

    public void setInterimrptdate(Date interimrptdate) {
        this.interimrptdate = interimrptdate;
    }

    public Date getInterimrptdate() {
        return interimrptdate;
    }

    public void setFinalrptdateStr(String finalrptdateStr) {
        this.finalrptdateStr = finalrptdateStr;
    }

    public String getFinalrptdateStr() {
        return finalrptdateStr;
    }

    public void setInterimrptdateStr(String interimrptdateStr) {
        this.interimrptdateStr = interimrptdateStr;
    }

    public String getInterimrptdateStr() {
        return interimrptdateStr;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
    }

    public Date getReviewdate() {
        return reviewdate;
    }

    public void setReviewdateStr(String reviewdateStr) {
        this.reviewdateStr = reviewdateStr;
    }

    public String getReviewdateStr() {
        return reviewdateStr;
    }

    public void setPlsInstitution(boolean plsInstitution) {
        this.plsInstitution = plsInstitution;
    }

    public boolean isPlsInstitution() {
        return plsInstitution;
    }

	public Date getExtensiondate() {
		return extensiondate;
	}

	public void setExtensiondate(Date extensiondate) {
		this.extensiondate = extensiondate;
	}

	public String getExtensiondateStr() {
		return extensiondateStr;
	}

	public void setExtensiondateStr(String extensiondateStr) {
		this.extensiondateStr = extensiondateStr;
	}
}
