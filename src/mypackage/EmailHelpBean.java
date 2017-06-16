package mypackage;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import sedems.EmailLogQuery;
import sedems.MessageVarQuery;

public class EmailHelpBean extends ActionForm
{
  public String cc;
  public String from;
  public int fccode;
  public int fycode;
  public String program;
  public String approvalType;
  public String subject;
  public String message;
  public boolean managerName;
  public boolean projectNum;
  public boolean instName;
  public boolean buildingName;
  public boolean projectTitle;
  public int wtid;
  public int managerNameId;
  public int projectNumId;
  public boolean programName;
  public int programId;
  public boolean amtapproved;
  public int amtapprovedId;
  public boolean fiscalYear;
  public int fiscalYearId;
  public boolean grantNum;
  public long grantId;
  public EmailLogQuery[] emailLogs;
  public MessageVarQuery[] messageVars;

  public EmailHelpBean()
  {
  }

  public String getCc()
  {
    return cc;
  }

  public void setCc(String cc)
  {
    this.cc = cc;
  }

  public String getFrom()
  {
    return from;
  }

  public void setFrom(String from)
  {
    this.from = from;
  }

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public String getProgram()
  {
    return program;
  }

  public void setProgram(String program)
  {
    this.program = program;
  }

  public String getApprovalType()
  {
    return approvalType;
  }

  public void setApprovalType(String approvalType)
  {
    this.approvalType = approvalType;
  }

  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public boolean isManagerName()
  {
    return managerName;
  }

  public void setManagerName(boolean managerName)
  {
    this.managerName = managerName;
  }

  public boolean isProjectNum()
  {
    return projectNum;
  }

  public void setProjectNum(boolean projectNum)
  {
    this.projectNum = projectNum;
  }
  
  public int getWtid()
  {
    return wtid;
  }

  public void setWtid(int wtid)
  {
    this.wtid = wtid;
  }

  public int getManagerNameId()
  {
    return managerNameId;
  }

  public void setManagerNameId(int managerNameId)
  {
    this.managerNameId = managerNameId;
  }

  public int getProjectNumId()
  {
    return projectNumId;
  }

  public void setProjectNumId(int projectNumId)
  {
    this.projectNumId = projectNumId;
  }


  public EmailLogQuery[] getEmailLogs()
  {
    return emailLogs;
  }
  
  public void setEmailLogs(EmailLogQuery[] emailLogs)
  {
    this.emailLogs = emailLogs;
  }
  
  public void setMessageVars(MessageVarQuery[] messageVars)
  {
    this.messageVars = messageVars;
  }


  public MessageVarQuery[] getMessageVars()
  {
    return messageVars;
  }


   public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    ActionErrors errors = new ActionErrors();
    if (isMissing(getFrom())) 
      errors.add("fromMissing", new ActionError("value.required", "From"));
        
    if (isMissing(getSubject())) 
      errors.add("subMissing", new ActionError("value.required", "Subject"));
       
    if (isMissing(getMessage())) 
      errors.add("messageMissing", new ActionError("value.required", "Message"));
     
    return(errors);
  }


    public void setGrantNum(boolean grantNum) {
        this.grantNum = grantNum;
    }

    public boolean isGrantNum() {
        return grantNum;
    }

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getProgramId() {
        return programId;
    }

   

    public void setAmtapprovedId(int amtapprovedId) {
        this.amtapprovedId = amtapprovedId;
    }

    public int getAmtapprovedId() {
        return amtapprovedId;
    }

    public boolean isAmtapproved() {
        return amtapproved;
    }

    public void setAmtapproved(boolean amtapproved) {
        this.amtapproved = amtapproved;
    }

    public void setProgramName(boolean programName) {
        this.programName = programName;
    }

    public boolean isProgramName() {
        return programName;
    }

    public void setFiscalYear(boolean fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public boolean isFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYearId(int fiscalYearId) {
        this.fiscalYearId = fiscalYearId;
    }

    public int getFiscalYearId() {
        return fiscalYearId;
    }

    public void setInstName(boolean instName) {
        this.instName = instName;
    }

    public boolean isInstName() {
        return instName;
    }

    public void setBuildingName(boolean buildingName) {
        this.buildingName = buildingName;
    }

    public boolean isBuildingName() {
        return buildingName;
    }

    public void setProjectTitle(boolean projectTitle) {
        this.projectTitle = projectTitle;
    }

    public boolean isProjectTitle() {
        return projectTitle;
    }
}
