package mypackage;
import java.util.Date;

public class Eligibility 
{
  public long instelId;
  String eligible;
  public Date eligdate;

  public Eligibility()
  {
  }

  public long getInstelId()
  {
    return instelId;
  }

  public void setInstelId(long instelId)
  {
    this.instelId = instelId;
  }

  public String getEligible()
  {
    return eligible;
  }

  public void setEligible(String eligible)
  {
    this.eligible = eligible;
  }

  public Date getEligdate()
  {
    return eligdate;
  }

  public void setEligdate(Date eligdate)
  {
    this.eligdate = eligdate;
  }
}