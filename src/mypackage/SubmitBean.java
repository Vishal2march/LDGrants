/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  SubmitBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This bean will store/retrieve all info from the grant_submissions table regarding
 * each time the grant was submitted, date submitted, version, and user that
 * submitted the grant. 
 *****************************************************************************/
package mypackage;
import java.util.Date;

public class SubmitBean 
{
  public long grantid;
  public Date dateSubmitted;
  public String userSubmitted;
  public String versionSubmitted;
  public long id;

  public SubmitBean()
  {
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public Date getDateSubmitted()
  {
    return dateSubmitted;
  }

  public void setDateSubmitted(Date dateSubmitted)
  {
    this.dateSubmitted = dateSubmitted;
  }

  public String getUserSubmitted()
  {
    return userSubmitted;
  }

  public void setUserSubmitted(String userSubmitted)
  {
    this.userSubmitted = userSubmitted;
  }

  public String getVersionSubmitted()
  {
    return versionSubmitted;
  }

  public void setVersionSubmitted(String versionSubmitted)
  {
    this.versionSubmitted = versionSubmitted;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }


  
  
  
  
  
  
  
  
  
  
  
}