/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  RegisterBean.java
 * Creation/Modification History  :
 *
 * SH  3/24/08     Created
 *
 * Description
 * This class has get/set accessors to store and retrieve info from Di,Lit, Con
 * registration for account form. 
 *****************************************************************************/
package ldgext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RegisterBean extends ActionForm
{
  public String fname;
  public String lname;
  public String email;
  public String phone;
  public String title;
  public String instName;
  public String addr1;
  public String addr2;
  public String city;
  public String state;
  public String zipcd;
  public String county;
  public String schdistrict;
  public boolean readaccess;
  public boolean editaccess;
  public boolean submitaccess;
  public String fedid;
  public String libName;
  public String grantprogram;
  public boolean cpdiscretionary;
  public boolean fmliteracy;
  public boolean alliteracy;
  public boolean plconstruction;
  public boolean newRmo;

  public RegisterBean()
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

  public String getLname()
  {
    return lname;
  }

  public void setLname(String lname)
  {
    this.lname = lname;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getInstName()
  {
    return instName;
  }

  public void setInstName(String instName)
  {
    this.instName = instName;
  }

  public String getAddr1()
  {
    return addr1;
  }

  public void setAddr1(String addr1)
  {
    this.addr1 = addr1;
  }

  public String getAddr2()
  {
    return addr2;
  }

  public void setAddr2(String addr2)
  {
    this.addr2 = addr2;
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

  public String getZipcd()
  {
    return zipcd;
  }

  public void setZipcd(String zipcd)
  {
    this.zipcd = zipcd;
  }
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }  
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();    
        
    if (isMissing(getFname())) 
    {
      errors.add("fnameMissing", new ActionError("value.required", "First Name"));
    }   
    if (isMissing(getLname())) 
    {
      errors.add("nameMissing", new ActionError("value.required", "Last Name"));
    } 
    if (isMissing(getTitle())) 
    {
      errors.add("titleMissing", new ActionError("value.required", "Title"));
    }   
    if(isMissing(getEmail()))
    {
      errors.add("emailMissing", new ActionError("value.required", "Email"));
    }
    if (isMissing(getPhone())) 
    {
      errors.add("phoneMissing", new ActionError("value.required", "Phone"));
    }       
    if (isMissing(getInstName())) 
    {
      errors.add("instMissing", new ActionError("value.required", "Institution Name"));
    } 
    if (isMissing(getAddr1())) 
    {
      errors.add("addrMissing", new ActionError("value.required", "Address"));
    } 
    if (isMissing(getCity())) 
    {
      errors.add("cityMissing", new ActionError("value.required", "City"));
    } 
    if (isMissing(getState())) 
    {
      errors.add("stateMissing", new ActionError("value.required", "State"));
    } 
    if (isMissing(getZipcd())) 
    {
      errors.add("zipMissing", new ActionError("value.required", "Zip code"));
    } 
    
        
    return(errors);
  }

  public String getCounty()
  {
    return county;
  }

  public void setCounty(String county)
  {
    this.county = county;
  }

  public String getSchdistrict()
  {
    return schdistrict;
  }

  public void setSchdistrict(String schdistrict)
  {
    this.schdistrict = schdistrict;
  }

  public boolean isReadaccess()
  {
    return readaccess;
  }

  public void setReadaccess(boolean readaccess)
  {
    this.readaccess = readaccess;
  }

  public boolean isEditaccess()
  {
    return editaccess;
  }

  public void setEditaccess(boolean editaccess)
  {
    this.editaccess = editaccess;
  }

  public boolean isSubmitaccess()
  {
    return submitaccess;
  }

  public void setSubmitaccess(boolean submitaccess)
  {
    this.submitaccess = submitaccess;
  }

  public String getFedid()
  {
    return fedid;
  }

  public void setFedid(String fedid)
  {
    this.fedid = fedid;
  }

  public String getLibName()
  {
    return libName;
  }

  public void setLibName(String libName)
  {
    this.libName = libName;
  }

  public String getGrantprogram()
  {
    return grantprogram;
  }

  public void setGrantprogram(String grantprogram)
  {
    this.grantprogram = grantprogram;
  }

  public boolean isFmliteracy()
  {
    return fmliteracy;
  }

  public void setFmliteracy(boolean fmliteracy)
  {
    this.fmliteracy = fmliteracy;
  }

  public boolean isAlliteracy()
  {
    return alliteracy;
  }

  public void setAlliteracy(boolean alliteracy)
  {
    this.alliteracy = alliteracy;
  }

    public void setCpdiscretionary(boolean cpdiscretionary) {
        this.cpdiscretionary = cpdiscretionary;
    }

    public boolean isCpdiscretionary() {
        return cpdiscretionary;
    }

    public void setPlconstruction(boolean plconstruction) {
        this.plconstruction = plconstruction;
    }

    public boolean isPlconstruction() {
        return plconstruction;
    }

    public void setNewRmo(boolean newRmo) {
        this.newRmo = newRmo;
    }

    public boolean isNewRmo() {
        return newRmo;
    }
}
