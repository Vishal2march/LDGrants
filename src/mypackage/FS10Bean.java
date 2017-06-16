/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  FS10Bean.java
 * Creation/Modification History  :
 *
 * SH   5/1/07      Created
 * Ghudson 03/17/2017 Modified
 *
 * Description
 * This class will store/retrieve all info from the FS10_records table, including
 * the budget category, the reason for amendment and the amount increasing or decreasing
 * of the approved budget.
 *****************************************************************************/
package mypackage;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.action.ActionForm;

public class FS10Bean extends ActionForm
{
  public long id;
  public String description;
  public int amountincr;
  public int amountdecr;
  public String amountincrStr;
  public String amountdecrStr;
  public int expcode;
  public String expname;
  public ArrayList allExpenseCodes;
  public Date datecreated;
  public String createdby;
  public String modifiedby;
  public Date datemodified;
  public int fycode;
  public Long expendTypeId;
  public String year;
  

  public FS10Bean()
  {
  }

  public Long getExpendTypeId() {
	return expendTypeId;
}

public void setExpendTypeId(Long expendTypeId) {
	this.expendTypeId = expendTypeId;
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

  public int getAmountincr()
  {
    return amountincr;
  }

  public void setAmountincr(int amountincr)
  {
    this.amountincr = amountincr;
  }

  public int getAmountdecr()
  {
    return amountdecr;
  }

  public void setAmountdecr(int amountdecr)
  {
    this.amountdecr = amountdecr;
  }

  public int getExpcode()
  {
    return expcode;
  }

  public void setExpcode(int expcode)
  {
    this.expcode = expcode;
  }

  public String getExpname()
  {
    return expname;
  }

  public void setExpname(String expname)
  {
    this.expname = expname;
  }

  public Date getDatecreated()
  {
    return datecreated;
  }

  public void setDatecreated(Date datecreated)
  {
    this.datecreated = datecreated;
  }

  public String getCreatedby()
  {
    return createdby;
  }

  public void setCreatedby(String createdby)
  {
    this.createdby = createdby;
  }

  public String getModifiedby()
  {
    return modifiedby;
  }

  public void setModifiedby(String modifiedby)
  {
    this.modifiedby = modifiedby;
  }

  public Date getDatemodified()
  {
    return datemodified;
  }

  public void setDatemodified(Date datemodified)
  {
    this.datemodified = datemodified;
  }


    public void setAllExpenseCodes(ArrayList allExpenseCodes) {
        this.allExpenseCodes = allExpenseCodes;
    }

    public ArrayList getAllExpenseCodes() {
        return allExpenseCodes;
    }

    public void setAmountincrStr(String amountincrStr) {
        this.amountincrStr = amountincrStr;
    }

    public String getAmountincrStr() {
        return amountincrStr;
    }

    public void setAmountdecrStr(String amountdecrStr) {
        this.amountdecrStr = amountdecrStr;
    }

    public String getAmountdecrStr() {
        return amountdecrStr;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }
}
