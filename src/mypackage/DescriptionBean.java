/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  DescriptionBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class has all the get/set accessors to store and retrieve information for the 
 * project description narratives 
 *****************************************************************************/
package mypackage;
import java.sql.Clob;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class DescriptionBean extends ActionForm
{
  public DescriptionBean()
  {
  }  
  
  public long grantid;
  public String narrative;
  public long id;
  public int narrTypeID;
  public String narrativeTitle;
  public String narrativeDescr;
  public String module;
  public String program;
  public boolean lockNarrative;


  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public String getNarrative()
  {
    return narrative;
  }

  public void setNarrative(String narrative)
  {
    this.narrative = narrative;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public int getNarrTypeID()
  {
    return narrTypeID;
  }

  public void setNarrTypeID(int narrTypeID)
  {
    this.narrTypeID = narrTypeID;
  }

  public String getNarrativeTitle()
  {
    return narrativeTitle;
  }

  public void setNarrativeTitle(String narrativeTitle)
  {
    this.narrativeTitle = narrativeTitle;
  }

  public String getNarrativeDescr()
  {
    return narrativeDescr;
  }

  public void setNarrativeDescr(String narrativeDescr)
  {
    this.narrativeDescr = narrativeDescr;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

  public String getProgram()
  {
    return program;
  }

  public void setProgram(String program)
  {
    this.program = program;
  }

  public boolean isLockNarrative()
  {
    return lockNarrative;
  }

  public void setLockNarrative(boolean lockNarrative)
  {
    this.lockNarrative = lockNarrative;
  }
  
  
    public ActionErrors validateSummary(ActionMapping mapping,  HttpServletRequest request, int maxChars) 
    {  
      ActionErrors errors = new ActionErrors();
      
        //if summary desc contains data, make sure maxlength is <max chars
        if(getNarrative() !=null && !getNarrative().equals("") ) {
           //System.out.println(getNarrative().trim().length());
            if(getNarrative().trim().length() >maxChars)    
              errors.add("summarysize", new ActionError("errors.summaryDescr"));
        }      
      
      return (errors);
    }
    
  
}