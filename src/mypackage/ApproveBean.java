/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ApproveBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will store all  information from the Approvals table, including the
 * admin that approved, the grant version, and date of approval.
 *****************************************************************************/
package mypackage;
import java.util.Date;

public class ApproveBean 
{
  public long id;
  public long grantid;
  public String adminuser;
  public String version;
  public Date dateapproved;
  public String approvalType;
  public int amountappr;
  public int fycode;
  public int fccode;

  public ApproveBean()
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

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getAdminuser()
  {
    return adminuser;
  }

  public void setAdminuser(String adminuser)
  {
    this.adminuser = adminuser;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public Date getDateapproved()
  {
    return dateapproved;
  }

  public void setDateapproved(Date dateapproved)
  {
    this.dateapproved = dateapproved;
  }

  public String getApprovalType()
  {
    return approvalType;
  }

  public void setApprovalType(String approvalType)
  {
    this.approvalType = approvalType;
  }

  public int getAmountappr()
  {
    return amountappr;
  }

  public void setAmountappr(int amountappr)
  {
    this.amountappr = amountappr;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }



}