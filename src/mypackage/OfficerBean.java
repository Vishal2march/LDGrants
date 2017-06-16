/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  OfficerBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This bean will store/retrieve all info about the PO and LD that is stored in the
 * SEDREF db, including the officer's name, and contact info. 
 *****************************************************************************/
package mypackage;

public class OfficerBean 
{
  public long staffID;
  public String fname;
  public String mname;
  public String lname;
  public long instID;
  public long grantid;
  public String userID;
  public String salutation;
  public String phone;
  public String email;
  public String fax;
  public String title;
  public String sedrefadminid;
  public String contactitem;
  public int phonenum;
  public String phoneext;
 

  public OfficerBean()
  {
  }

  public long getStaffID()
  {
    return staffID;
  }

  public void setStaffID(long staffID)
  {
    this.staffID = staffID;
  }

  public String getFname()
  {
    return fname;
  }

  public void setFname(String fname)
  {
    this.fname = fname;
  }

  public String getMname()
  {
    return mname;
  }

  public void setMname(String mname)
  {
    this.mname = mname;
  }

  public String getLname()
  {
    return lname;
  }

  public void setLname(String lname)
  {
    this.lname = lname;
  }

  public long getInstID()
  {
    return instID;
  }

  public void setInstID(long instID)
  {
    this.instID = instID;
  }

 

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getUserID()
  {
    return userID;
  }

  public void setUserID(String userID)
  {
    this.userID = userID;
  }

  public String getSalutation()
  {
    return salutation;
  }

  public void setSalutation(String salutation)
  {
    this.salutation = salutation;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getFax()
  {
    return fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getSedrefadminid()
  {
    return sedrefadminid;
  }

  public void setSedrefadminid(String sedrefadminid)
  {
    this.sedrefadminid = sedrefadminid;
  }

  public String getContactitem()
  {
    return contactitem;
  }

  public void setContactitem(String contactitem)
  {
    this.contactitem = contactitem;
  }

  public int getPhonenum()
  {
    return phonenum;
  }

  public void setPhonenum(int phonenum)
  {
    this.phonenum = phonenum;
  }

  public String getPhoneext()
  {
    return phoneext;
  }

  public void setPhoneext(String phoneext)
  {
    this.phoneext = phoneext;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}