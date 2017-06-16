/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  AuthorizationBean.java
 * Creation/Modification History  :
 *
 * SH   4/1/07      Created
 *
 * Description
 * This class will store/retrieve all info from the Authorizations table.  It is used
 * to get info on the PO and LD that have provided electronic signiture on the 
 * institutional authorization and final signoff pages.  
 *****************************************************************************/
package mypackage;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AuthorizationBean extends ActionForm
{
  public String name;
  public String title;
  public Date authdate;
  public String user;
  public String version;
  public long grantid;
  public String authdateStr;
  public long id;

  public AuthorizationBean()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Date getAuthdate()
  {
    return authdate;
  }

  public void setAuthdate(Date authdate)
  {
    this.authdate = authdate;
  }

  public String getUser()
  {
    return user;
  }

  public void setUser(String user)
  {
    this.user = user;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String version)
  {
    this.version = version;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

    public void setAuthdateStr(String authdateStr) {
        this.authdateStr = authdateStr;
    }

    public String getAuthdateStr() {
        return authdateStr;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    
    
    
    
  public boolean isMissing(String value) 
  {
   return((value == null) || (value.trim().equals("")));
  }    
    
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    if (isMissing(getName())) 
    {
      errors.add("nameMissing", new ActionError("value.required", "Name"));
    } 
    if (isMissing(getTitle())) 
    {
      errors.add("titleMissing", new ActionError("value.required", "Title"));
    } 
    if (isMissing(getAuthdateStr())) 
    {
      errors.add("authdateMissing", new ActionError("value.required", "Assurance Date"));
    }     
    return(errors);
  }
  
}
