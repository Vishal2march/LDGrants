/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  ReviewAssignMaxBean.java
 * Creation/Modification History  :
 *
 * SH   7/30/07      Created
 *
 * Description
 * This class has get/set accessors to store and retrieve info from the 
 * grant_assign_max table for the max number of grants per reviewer for a year.
 *****************************************************************************/
package mypackage;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class ReviewAssignMaxBean extends ActionForm
{
  public long id;
  public int numaccepted;
  public String grantprogram;
  public long revid;
  public int fycode;
  public String fiscalyear;
  public String userid;
  public String descrip;
  public String task;
  public String module;
  public String response;

  public ReviewAssignMaxBean()
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

  public int getNumaccepted()
  {
    return numaccepted;
  }

  public void setNumaccepted(int numaccepted)
  {
    this.numaccepted = numaccepted;
  }

  public String getGrantprogram()
  {
    return grantprogram;
  }

  public void setGrantprogram(String grantprogram)
  {
    this.grantprogram = grantprogram;
  }

  public long getRevid()
  {
    return revid;
  }

  public void setRevid(long revid)
  {
    this.revid = revid;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getFiscalyear()
  {
    return fiscalyear;
  }

  public void setFiscalyear(String fiscalyear)
  {
    this.fiscalyear = fiscalyear;
  }
  
  
  
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public boolean isMissingInt(int value) 
  {
    return(value ==0 );
  }
  
  
  private boolean isInt(String potentialInt) 
  {
    boolean isInt = true;
    try 
    {
      int x = Integer.parseInt(potentialInt);
    } 
    catch(NumberFormatException nfe) 
    {
      isInt = false;
    }
    return(isInt);
  }


  
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    
    /*if (isMissingInt(getNumaccepted())) 
    {
      errors.add("numMissing", new ActionError("value.required", "Number of Proposals"));
    } 
    if (! isInt(getNumaccepted())) 
    {
      errors.add("numFormat", new ActionError("value.int", "Number of Proposals"));
    } */
    
    if (isMissingInt(getFycode())) 
    {
      errors.add("fyMissing", new ActionError("value.required", "Fiscal Year"));
    } 
    if (isMissing(getGrantprogram())) 
    {
      errors.add("graprogramMissing", new ActionError("value.required", "Grant Program"));
    }       
    
    return(errors);
  }

  public String getUserid()
  {
    return userid;
  }

  public void setUserid(String userid)
  {
    this.userid = userid;
  }

  public String getDescrip()
  {
    return descrip;
  }

  public void setDescrip(String descrip)
  {
    this.descrip = descrip;
  }



  public String getTask()
  {
    return task;
  }

  public void setTask(String task)
  {
    this.task = task;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

  public String getResponse()
  {
    return response;
  }

  public void setResponse(String response)
  {
    this.response = response;
  }
}