package mypackage;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PanelBean extends ActionForm
{
  public long id;
  public String name;
  public String descr;
  public String status;
  public int amtavailable;
  public int amtapproved;
  public int amtdifference;
  public int fycode;
  public String year;

  public PanelBean()
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

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescr()
  {
    return descr;
  }

  public void setDescr(String descr)
  {
    this.descr = descr;
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public int getAmtavailable()
  {
    return amtavailable;
  }

  public void setAmtavailable(int amtavailable)
  {
    this.amtavailable = amtavailable;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getYear()
  {
    return year;
  }

  public void setYear(String year)
  {
    this.year = year;
  }
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    if (isMissing(getName())) 
      errors.add("nameMissing", new ActionError("value.required", "Panel Name"));
      
    return errors;
  }

    public void setAmtapproved(int amtapproved) {
        this.amtapproved = amtapproved;
    }

    public int getAmtapproved() {
        return amtapproved;
    }

    public void setAmtdifference(int amtdifference) {
        this.amtdifference = amtdifference;
    }

    public int getAmtdifference() {
        return amtdifference;
    }
}
