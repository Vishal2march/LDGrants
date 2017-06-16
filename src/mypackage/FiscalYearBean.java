package mypackage;
import java.util.Date;

public class FiscalYearBean 
{
  public int fycode;
  public String year;
  public Date startdate;
  public Date enddate;

  public FiscalYearBean()
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

  public String getYear()
  {
    return year;
  }

  public void setYear(String year)
  {
    this.year = year;
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
  
  
  
  
}