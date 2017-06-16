/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewerBean.java
 * Creation/Modification History  :
 *
 * SH   7/25/07      Created
 *
 * Description
 * This bean stores all info about the REVIEWER including contact info from the
 * contacts and addresses tables
 *****************************************************************************/
package mypackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class ReviewerBean extends ActionForm 
{
  public String fname;
  public String mname;
  public String lname;
  public String title;
  public String salutation;
  public String affiliation;
  public String interest;
  public String phone;
  public String email;
  public long revid;
  public String address;
  public String city;
  public String state;
  public String zipcode;
  public String active;
  public String username;//the username from LDAP
  public boolean reviewerFound;
  public String grantprogram;
  public boolean discretionary;
  public boolean coordinated;
  public boolean fliteracy;
  public boolean aliteracy;
  public ReviewerAssignBean[] reviewerAssigns;
  public String phoneext;
  public long phoneextId;
  public boolean lgrmif;
  public String comment;
  public String ssn;
  public long panelreviewerId;
  public boolean rao;
  public boolean govtemp;
  public int available;
  public String vendornum;

  public ReviewerBean()
  {
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

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getSalutation()
  {
    return salutation;
  }

  public void setSalutation(String salutation)
  {
    this.salutation = salutation;
  }

  public String getAffiliation()
  {
    return affiliation;
  }

  public void setAffiliation(String affiliation)
  {
    this.affiliation = affiliation;
  }

  public String getInterest()
  {
    return interest;
  }

  public void setInterest(String interest)
  {
    this.interest = interest;
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

  public long getRevid()
  {
    return revid;
  }

  public void setRevid(long revid)
  {
    this.revid = revid;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getState()
  {
    return state;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getZipcode()
  {
    return zipcode;
  }

  public void setZipcode(String zipcode)
  {
    this.zipcode = zipcode;
  }


  
  public String getActive()
  {
    return active;
  }

  public void setActive(String active)
  {
    this.active = active;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public boolean isReviewerFound()
  {
    return reviewerFound;
  }

  public void setReviewerFound(boolean reviewerFound)
  {
    this.reviewerFound = reviewerFound;
  }

  public String getGrantprogram()
  {
    return grantprogram;
  }

  public void setGrantprogram(String grantprogram)
  {
    this.grantprogram = grantprogram;
  }

  public boolean isDiscretionary()
  {
    return discretionary;
  }

  public void setDiscretionary(boolean discretionary)
  {
    this.discretionary = discretionary;
  }

  public boolean isCoordinated()
  {
    return coordinated;
  }

  public void setCoordinated(boolean coordinated)
  {
    this.coordinated = coordinated;
  }

  public boolean isFliteracy()
  {
    return fliteracy;
  }

  public void setFliteracy(boolean fliteracy)
  {
    this.fliteracy = fliteracy;
  }

  public boolean isAliteracy()
  {
    return aliteracy;
  }

  public void setAliteracy(boolean aliteracy)
  {
    this.aliteracy = aliteracy;
  }

  public void setReviewerAssigns(ReviewerAssignBean[] reviewerAssigns)
  {
    this.reviewerAssigns = reviewerAssigns;
  }

  public ReviewerAssignBean[] getReviewerAssigns()
  {
    return reviewerAssigns;
  } 
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public boolean isValidPhone(String phoneNumber)
  {    
    boolean valResult = false;
    String numPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
    valResult = phoneNumber.matches(numPattern);

    return valResult;
  }
  
  public boolean isValidSsn(String ssnNumber)
  {    
    boolean valResult = false;
    String numPattern = "\\d{3}-\\d{2}-\\d{4}$";
    valResult = ssnNumber.matches(numPattern);

    return valResult;
  }
    
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    if (isMissing(getUsername())) 
    {
      errors.add("useridMissing", new ActionError("value.required", "UserName"));
    } 
    if (isMissing(getLname())) 
    {
      errors.add("nameMissing", new ActionError("value.required", "Last Name"));
    } 
    if (isMissing(getFname())) 
    {
      errors.add("fnameMissing", new ActionError("value.required", "First Name"));
    }    
    if(isMissing(getEmail()))
    {
      errors.add("emailMissing", new ActionError("value.required", "Email"));
    }    
    if(!isMissing(getPhone()))
    {
      if(!isValidPhone(getPhone()))
        errors.add("phoneinvalid", new ActionError("errors.phone"));
    }
    if(!isMissing(getSsn()))
    {
      if(!isValidSsn( getSsn() ))
        errors.add("ssninvalid", new ActionError("errors.ssn"));
    }
    return(errors);
  }

  public String getPhoneext()
  {
    return phoneext;
  }

  public void setPhoneext(String phoneext)
  {
    this.phoneext = phoneext;
  }

  public long getPhoneextId()
  {
    return phoneextId;
  }

  public void setPhoneextId(long phoneextId)
  {
    this.phoneextId = phoneextId;
  }

  public boolean isLgrmif()
  {
    return lgrmif;
  }

  public void setLgrmif(boolean lgrmif)
  {
    this.lgrmif = lgrmif;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getSsn()
  {
    return ssn;
  }

  public void setSsn(String ssn)
  {
    this.ssn = ssn;
  }

    public void setPanelreviewerId(long panelreviewerId) {
        this.panelreviewerId = panelreviewerId;
    }

    public long getPanelreviewerId() {
        return panelreviewerId;
    }


    public void setRao(boolean rao) {
        this.rao = rao;
    }

    public boolean isRao() {
        return rao;
    }

    public void setGovtemp(boolean govtemp) {
        this.govtemp = govtemp;
    }

    public boolean isGovtemp() {
        return govtemp;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    public void setVendornum(String vendornum) {
        this.vendornum = vendornum;
    }

    public String getVendornum() {
        return vendornum;
    }
}
