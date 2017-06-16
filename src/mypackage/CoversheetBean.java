package mypackage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import gov.nysed.oce.ldgrants.grants.common.service.EmailValidator;

import java.util.Date;
import java.util.HashMap;

public class CoversheetBean extends ActionForm
{
  public long pmId;
  public String fname;
  public String lname;
  public String title;
  public String mname;
  public String email;
  public String phone;
  public long emailId;
  public long phoneId;
  public String projectTitle;
  public String summaryDesc;
  public long narrId;
  public int narrTypeId;
  public long grantid;
  public boolean chartered;
  public boolean accepted;
  public boolean charity;
  public boolean notprofit;
  public boolean other;
  public String charterdate;
  public String acceptdate;
  public String charitydate;
  public String notprofitdate;
  public String module;
  public String archiveRegion;
  public String phoneext;
  public long phoneextId;
  public int amtrequested;
  public int rmoAppointedint;
  public int scheduleAdoptedint;
  public String rmoDate;
  public String scheduleDate;
  public boolean dorisFlag;
  public String dorisName;
  public String rmofname;
  public String rmolname;
  public String rmotitle;
  public String rmophone;
  public String rmoemail;
  public long rmoId;
  public String rmophoneext;
  public long rmophoneextId;
  public int projcategoryId;
  public int govtTypeId;
  public int govtRegionId;
  public int ftemployees;
  public int ptemployees;
  public String ftemployeesStr;
  public String ptemployeesStr;
  public String dept;
  public long annualbudget;
  public String annualbudgetStr;
  public long population;
  public String populationStr;
  public int applicationType;
  public int demoType;
  public boolean cooperative;
  public boolean sharedserv;
  public boolean emailmgmt;
  public boolean inventory;
  public boolean recordsmgmt;
  public long govtId;
  public DropDownListBean[] regionsList;
  public DropDownListBean[] govttypesList;
  public DropDownListBean[] categoriesList;
  public String projcategoryName;
  public String govtRegionName;
  public String govtTypeName;
  public long sedrefinstid;
  public boolean religious;
  public int score;
  public boolean planningDemo;
  public boolean implementDemo;
  public int fycode;

  EmailValidator eValidator = new EmailValidator();
  
  public CoversheetBean()
  {

  }

  public long getPmId()
  {
    return pmId;
  }

  public void setPmId(long pmId)
  {
    this.pmId = pmId;
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

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getMname()
  {
    return mname;
  }

  public void setMname(String mname)
  {
    this.mname = mname;
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

  public long getEmailId()
  {
    return emailId;
  }

  public void setEmailId(long emailId)
  {
    this.emailId = emailId;
  }

  public long getPhoneId()
  {
    return phoneId;
  }

  public void setPhoneId(long phoneId)
  {
    this.phoneId = phoneId;
  }

  public String getProjectTitle()
  {
    return projectTitle;
  }

  public void setProjectTitle(String projectTitle)
  {
    this.projectTitle = projectTitle;
  }

  public String getSummaryDesc()
  {
    return summaryDesc;
  }

  public void setSummaryDesc(String summaryDesc)
  {
    this.summaryDesc = summaryDesc;
  }
  
  public boolean isMissing(String value) 
  {
    return((value == null) || (value.trim().equals("")));
  }
  
  public boolean isWrongDateFormat(String value) 
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    boolean badd = false;
    
    try{
      Date thedate = sdf.parse(value);
      badd=false;
    }catch(Exception e)
    {badd = true; }
    
    return badd;
  }
  
  
    public boolean isWrongYearFormat(String value) 
    {
      boolean badd = false;
      
      try{
        int yearint = Integer.parseInt(value);//fails if string or m/d/y input
        if(yearint <1800 || yearint >2020)
            badd=true;
      }catch(Exception e)
      {badd = true; }
      
      return badd;
    }
  
  public boolean isValidPhone(String phoneNumber)
  {    
    boolean valResult = false;
    String numPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
    valResult = phoneNumber.matches(numPattern);

    return valResult;
  }
  
  public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
  {  
    String mod = getModule();
    ActionErrors errors = new ActionErrors();
    String pmtitle = "PM";//allow same action error for PM versus PD (FC 8/3/09)

    if(mod!=null && mod.equals("lg"))
        pmtitle = "PD";
        
        
    if (isMissing(getFname())) 
      errors.add("fnameMissing", new ActionError("value.required", pmtitle+" First Name"));
    
    if (isMissing(getLname())) 
      errors.add("lnameMissing", new ActionError("value.required", pmtitle+" Last Name"));
        
    if (isMissing(getPhone())) 
      errors.add("phoneMissing", new ActionError("value.required", pmtitle+" Phone"));
    else
    {
      if(!isValidPhone(getPhone()))
        errors.add("phoneinvalid", new ActionError("errors.phone"));
    }
    
    if (isMissing(getEmail())) {
      errors.add("emailMissing", new ActionError("value.required", pmtitle+" Email"));
    
    } else if(!eValidator.isEmailAddressValid(getEmail())) {
    	errors.add("invalidEmail", new ActionError("errors.email", getEmail()));	
    }
    
   	
   
    if(mod!=null && (!mod.equals("lg")) )//all programs EXCEPT lg
    {
      if (isMissing(getProjectTitle()))
        errors.add("ptitleMissing", new ActionError("value.required", "Project Title"));      
    }
    
    //the following only required for discretionary
    if(mod!=null && mod.equals("di"))
    {                  
        if(!isMissing(getCharterdate()))
        {
          if(isWrongDateFormat(getCharterdate()))
            errors.add("dateformat", new ActionError("errors.date", "Chartered Date"));
        }
    }        
    
    if(mod!=null &&  (mod.equals("fl")  || mod.equals("al")  )){
       //for lit;  other contact info is required
       if(isMissing(getRmofname()) )
           errors.add("rmofname", new ActionError("value.required", "Additional Contact First Name"));
            
       if(isMissing(getRmolname()))
           errors.add("rmolname", new ActionError("value.required", "Additional Contact Last Name"));
        
        if(isMissing(getRmotitle()))
          errors.add("rmotitle", new ActionError("value.required", "Additional Contact Title"));
          
        if(isMissing(getRmoemail()))
          errors.add("rmoemail", new ActionError("value.required", "Additional Contact Email"));
        else if(!eValidator.isEmailAddressValid(getRmoemail())) 
        	errors.add("invalidEmail", new ActionError("errors.email", getRmoemail()));	
        
        if(isMissing(getRmophone()))
          errors.add("rmophone", new ActionError("value.required", "Additional Contact Phone"));
        else{
          if(!isValidPhone(getRmophone()))
            errors.add("phoneinvalid", new ActionError("errors.phone"));
        }
              
    }
    
    //the following only required for lgrmif
    if(mod!=null && mod.equals("lg"))
    {
      if(isMissing(getRmofname()))
        errors.add("rmofname", new ActionError("value.required", "RMO First Name"));
        
      if(isMissing(getRmolname()))
        errors.add("rmolname", new ActionError("value.required", "RMO Last Name"));
      
      if(isMissing(getRmotitle()))
        errors.add("rmotitle", new ActionError("value.required", "RMO Title"));
        
      if(isMissing(getRmoemail()))
        errors.add("rmoemail", new ActionError("value.required", "RMO Email"));
      else if(!eValidator.isEmailAddressValid(getRmoemail())) 
      	errors.add("invalidEmail", new ActionError("errors.email", getRmoemail()));	
      
      if(isMissing(getRmophone()))
        errors.add("rmophone", new ActionError("value.required", "RMO Phone"));
      else
      {
        if(!isValidPhone(getRmophone()))
          errors.add("phoneinvalid", new ActionError("errors.phone"));
      }
      
      if(isMissing(getAnnualbudgetStr()))
          errors.add("budgetmiss", new ActionError("value.required", "Annual Budget"));
          
      if(isMissing(getPopulationStr()))
          errors.add("popmiss", new ActionError("value.required", "Population Served"));
          
      if(isMissing(getFtemployeesStr()))
          errors.add("ftemiss", new ActionError("value.required", "Full Time Employees"));
          
      if(isMissing(getPtemployeesStr()))
            errors.add("ptemiss", new ActionError("value.required", "Part Time Employees"));
                                              
      if(isDorisFlag() && getSedrefinstid()!=800000047654L)//only doris can choose the doris flag
        errors.add("dorisinst", new ActionError("errors.dorisinst"));
        
      if(isDorisFlag() && isMissing(getDorisName()))
        errors.add("dorisname", new ActionError("errors.dorisreq"));//if doris=y, then need agency name
        
      if(getProjcategoryId()==11 && getSedrefinstid()!=800000047654L)//only doris can pick admin category
        errors.add("admincat", new ActionError("errors.dorisadmin"));
       
      if(getRmoAppointedint()==0){ // 0=Yes, and Yes needs an eligible date
        if(isMissing(getRmoDate()))
            errors.add("rmodate", new ActionError("value.required", "RMO Year"));
        else{
            if(isWrongYearFormat(getRmoDate()))
               errors.add("rmodate", new ActionError("errors.year", "RMO Year"));            
        }
      }
      
      if(getProjcategoryId()==8){//GIS category cannot be 'individual' app_type
        if(getApplicationType()==1){//type 1 is individual
            errors.add("categoryapp", new ActionError("errors.gisIndividual"));
        }
      }
      else if(getProjcategoryId()==12 || getProjcategoryId()==13){
          //10/3/13 'preservation' and 'rma' categories can only be 'demo' app_type
          if(getApplicationType()!=2)
              errors.add("demoapp", new ActionError("errors.demoAppType"));        
      }
            
     
      /* SH 8/8/14  NOTE:  these validations on demo were only needed for FY14-15 b/c demo was an apptype and
       * a subcategory.  starting FY15-16 demo is a category; so this validation no longer applies
      if(getApplicationType()==2){//demo app_type, must have category of 12 or 13; new 10/3/13
          if(getProjcategoryId()!=12  &&  getProjcategoryId()!=13)
              errors.add("democategory", new ActionError("errors.demoCategoryType"));
      }
            
      //10/3/13 if app_type is 'demo', a demoType must be selected
      if(getApplicationType()==2){
          if(getDemoType()==0)
            errors.add("demoTypeReq", new ActionError("errors.demoTypeRequired"));
      }
      
      //10/3/13 if a demoType is selected, the app_type must be 'demo'
      if(getDemoType()!=0){
          if(getApplicationType()!=2)
             errors.add("demoAppReq", new ActionError("errors.demoAppRequired"));
      }
     */
      
      //8/12/14 SH; new validation starting FY15-16 for new category/apptype when apptype=demo      
      if(getApplicationType()==2){//demo app_type, must have category of 14 or 15; 
          if(getProjcategoryId()!=14  &&  getProjcategoryId()!=15)
              errors.add("democategory", new ActionError("errors.demoCategoryType"));
      }
      
      if(getProjcategoryId()==14 || getProjcategoryId()==15){
            //8/12/14 'plann' and 'implement' categories can only be 'demo' app_type
            if(getApplicationType()!=2)
                errors.add("demoapp", new ActionError("errors.demoAppType"));        
        }
      
      
      
      
      
      if(getScheduleAdoptedint()==0){//0=Y, and Yes needs eligible date
        if(isMissing(getScheduleDate()))
            errors.add("schdate", new ActionError("value.required", "Schedule Year"));
        else {
            if(isWrongYearFormat(getScheduleDate()))
               errors.add("schdate", new ActionError("errors.year", "Schedule Year"));            
        }
      }
      
      if(getGovtRegionId()==0)
          errors.add("regfield", new ActionError("value.required", "Region"));
          
      if(getGovtTypeId()==0)
          errors.add("typefield", new ActionError("value.required", "Government Type"));
      
      if(getProjcategoryId()==0)
          errors.add("catfield", new ActionError("value.required", "Project Category"));
        
      //if summary desc contains data, make sure maxlength is <2500 chars
      if(getSummaryDesc() !=null && !getSummaryDesc().equals("") ) {
         //System.out.println(getSummaryDesc().trim().length());
          if(getSummaryDesc().trim().length() >2500)    
            errors.add("summarysize", new ActionError("errors.summarySize"));
      }      
    }
    return(errors);
  }
  
  
    public ActionErrors validateProjManager(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
                          
      if (isMissing(getFname())) 
        errors.add("fnameMissing", new ActionError("value.required", "PM First Name"));
      
      if (isMissing(getLname())) 
        errors.add("lnameMissing", new ActionError("value.required", "PM Last Name"));
          
      if (isMissing(getPhone())) 
        errors.add("phoneMissing", new ActionError("value.required", "PM Phone"));
      else
      {
        if(!isValidPhone(getPhone()))
          errors.add("phoneinvalid", new ActionError("errors.phone"));
      }
      
      if (isMissing(getEmail())) 
        errors.add("emailMissing", new ActionError("value.required", "PM Email"));
      else if(!eValidator.isEmailAddressValid(getEmail())) 
      	errors.add("invalidEmail", new ActionError("errors.email", getEmail()));	         
    return(errors);
  }
  
  
    public ActionErrors validateContact(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
    
        //for lit;  other contact info is required
        if(isMissing(getRmofname()) )
            errors.add("rmofname", new ActionError("value.required", "Additional Contact First Name"));
             
        if(isMissing(getRmolname()))
            errors.add("rmolname", new ActionError("value.required", "Additional Contact Last Name"));
         
         if(isMissing(getRmotitle()))
           errors.add("rmotitle", new ActionError("value.required", "Additional Contact Title"));
           
         if(isMissing(getRmoemail()))
           errors.add("rmoemail", new ActionError("value.required", "Additional Contact Email"));
         else if(!eValidator.isEmailAddressValid(getRmoemail())) 
           	errors.add("invalidEmail", new ActionError("errors.email", getRmoemail()));	
         
         if(isMissing(getRmophone()))
           errors.add("rmophone", new ActionError("value.required", "Additional Contact Phone"));
         else{
           if(!isValidPhone(getRmophone()))
             errors.add("phoneinvalid", new ActionError("errors.phone"));
         }
        return errors;
    }

  public long getNarrId()
  {
    return narrId;
  }

  public void setNarrId(long narrId)
  {
    this.narrId = narrId;
  }

  public int getNarrTypeId()
  {
    return narrTypeId;
  }

  public void setNarrTypeId(int narrTypeId)
  {
    this.narrTypeId = narrTypeId;
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }
  
  public boolean isChartered()
  {
    return chartered;
  }

  public void setChartered(boolean chartered)
  {
    this.chartered = chartered;
  }

  public boolean isAccepted()
  {
    return accepted;
  }

  public void setAccepted(boolean accepted)
  {
    this.accepted = accepted;
  }

  public boolean isCharity()
  {
    return charity;
  }

  public void setCharity(boolean charity)
  {
    this.charity = charity;
  }

  public boolean isNotprofit()
  {
    return notprofit;
  }

  public void setNotprofit(boolean notprofit)
  {
    this.notprofit = notprofit;
  }

  public boolean isOther()
  {
    return other;
  }

  public void setOther(boolean other)
  {
    this.other = other;
  }

  public String getCharterdate()
  {
    return charterdate;
  }

  public void setCharterdate(String charterdate)
  {
    this.charterdate = charterdate;
  }

  public String getAcceptdate()
  {
    return acceptdate;
  }

  public void setAcceptdate(String acceptdate)
  {
    this.acceptdate = acceptdate;
  }

  public String getCharitydate()
  {
    return charitydate;
  }

  public void setCharitydate(String charitydate)
  {
    this.charitydate = charitydate;
  }

  public String getNotprofitdate()
  {
    return notprofitdate;
  }

  public void setNotprofitdate(String notprofitdate)
  {
    this.notprofitdate = notprofitdate;
  }

  public String getModule()
  {
    return module;
  }

  public void setModule(String module)
  {
    this.module = module;
  }

  public String getArchiveRegion()
  {
    return archiveRegion;
  }

  public void setArchiveRegion(String archiveRegion)
  {
    this.archiveRegion = archiveRegion;
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

  public int getAmtrequested()
  {
    return amtrequested;
  }

  public void setAmtrequested(int amtrequested)
  {
    this.amtrequested = amtrequested;
  }

  public String getRmoDate()
  {
    return rmoDate;
  }

  public void setRmoDate(String rmoDate)
  {
    this.rmoDate = rmoDate;
  }

  public String getScheduleDate()
  {
    return scheduleDate;
  }

  public void setScheduleDate(String scheduleDate)
  {
    this.scheduleDate = scheduleDate;
  }

  public boolean isDorisFlag()
  {
    return dorisFlag;
  }

  public void setDorisFlag(boolean dorisFlag)
  {
    this.dorisFlag = dorisFlag;
  }

  public String getDorisName()
  {
    return dorisName;
  }

  public void setDorisName(String dorisName)
  {
    this.dorisName = dorisName;
  }

  public String getRmofname()
  {
    return rmofname;
  }

  public void setRmofname(String rmofname)
  {
    this.rmofname = rmofname;
  }

  public String getRmolname()
  {
    return rmolname;
  }

  public void setRmolname(String rmolname)
  {
    this.rmolname = rmolname;
  }

  public String getRmotitle()
  {
    return rmotitle;
  }

  public void setRmotitle(String rmotitle)
  {
    this.rmotitle = rmotitle;
  }

  public String getRmophone()
  {
    return rmophone;
  }

  public void setRmophone(String rmophone)
  {
    this.rmophone = rmophone;
  }

  public String getRmoemail()
  {
    return rmoemail;
  }

  public void setRmoemail(String rmoemail)
  {
    this.rmoemail = rmoemail;
  }

  public long getRmoId()
  {
    return rmoId;
  }

  public void setRmoId(long rmoId)
  {
    this.rmoId = rmoId;
  }

  public String getRmophoneext()
  {
    return rmophoneext;
  }

  public void setRmophoneext(String rmophoneext)
  {
    this.rmophoneext = rmophoneext;
  }

  public long getRmophoneextId()
  {
    return rmophoneextId;
  }

  public void setRmophoneextId(long rmophoneextId)
  {
    this.rmophoneextId = rmophoneextId;
  }

  public int getProjcategoryId()
  {
    return projcategoryId;
  }

  public void setProjcategoryId(int projcategoryId)
  {
    this.projcategoryId = projcategoryId;
  }

  public int getGovtTypeId()
  {
    return govtTypeId;
  }

  public void setGovtTypeId(int govtTypeId)
  {
    this.govtTypeId = govtTypeId;
  }

  public int getGovtRegionId()
  {
    return govtRegionId;
  }

  public void setGovtRegionId(int govtRegionId)
  {
    this.govtRegionId = govtRegionId;
  }

  public int getFtemployees()
  {
    return ftemployees;
  }

  public void setFtemployees(int ftemployees)
  {
    this.ftemployees = ftemployees;
  }

  public int getPtemployees()
  {
    return ptemployees;
  }

  public void setPtemployees(int ptemployees)
  {
    this.ptemployees = ptemployees;
  }

  public String getDept()
  {
    return dept;
  }

  public void setDept(String dept)
  {
    this.dept = dept;
  }

  public long getAnnualbudget()
  {
    return annualbudget;
  }

  public void setAnnualbudget(long annualbudget)
  {
    this.annualbudget = annualbudget;
  }

  public long getPopulation()
  {
    return population;
  }

  public void setPopulation(long population)
  {
    this.population = population;
  }

  public boolean isCooperative()
  {
    return cooperative;
  }

  public void setCooperative(boolean cooperative)
  {
    this.cooperative = cooperative;
  }

  public boolean isEmailmgmt()
  {
    return emailmgmt;
  }

  public void setEmailmgmt(boolean emailmgmt)
  {
    this.emailmgmt = emailmgmt;
  }

  public boolean isInventory()
  {
    return inventory;
  }

  public void setInventory(boolean inventory)
  {
    this.inventory = inventory;
  }

  public boolean isRecordsmgmt()
  {
    return recordsmgmt;
  }

  public void setRecordsmgmt(boolean recordsmgmt)
  {
    this.recordsmgmt = recordsmgmt;
  }

  public long getGovtId()
  {
    return govtId;
  }

  public void setGovtId(long govtId)
  {
    this.govtId = govtId;
  }

  public DropDownListBean[] getRegionsList()
  {
    return regionsList;
  }

  public void setRegionsList(DropDownListBean[] regionsList)
  {
    this.regionsList = regionsList;
  }

  public DropDownListBean[] getGovttypesList()
  {
    return govttypesList;
  }

  public void setGovttypesList(DropDownListBean[] govttypesList)
  {
    this.govttypesList = govttypesList;
  }

  public DropDownListBean[] getCategoriesList()
  {
    return categoriesList;
  }

  public void setCategoriesList(DropDownListBean[] categoriesList)
  {
    this.categoriesList = categoriesList;
  }

  public String getProjcategoryName()
  {
    return projcategoryName;
  }

  public void setProjcategoryName(String projcategoryName)
  {
    this.projcategoryName = projcategoryName;
  }

  public String getGovtRegionName()
  {
    return govtRegionName;
  }

  public void setGovtRegionName(String govtRegionName)
  {
    this.govtRegionName = govtRegionName;
  }

  public String getGovtTypeName()
  {
    return govtTypeName;
  }

  public void setGovtTypeName(String govtTypeName)
  {
    this.govtTypeName = govtTypeName;
  }


    public void setSedrefinstid(long sedrefinstid) {
        this.sedrefinstid = sedrefinstid;
    }

    public long getSedrefinstid() {
        return sedrefinstid;
    }

    public void setReligious(boolean religious) {
        this.religious = religious;
    }

    public boolean isReligious() {
        return religious;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setAnnualbudgetStr(String annualbudgetStr) {
        this.annualbudgetStr = annualbudgetStr;
    }

    public String getAnnualbudgetStr() {
        return annualbudgetStr;
    }

    public void setPopulationStr(String populationStr) {
        this.populationStr = populationStr;
    }

    public String getPopulationStr() {
        return populationStr;
    }

    public void setRmoAppointedint(int rmoAppointedint) {
        this.rmoAppointedint = rmoAppointedint;
    }

    public int getRmoAppointedint() {
        return rmoAppointedint;
    }

    public void setScheduleAdoptedint(int scheduleAdoptedint) {
        this.scheduleAdoptedint = scheduleAdoptedint;
    }

    public int getScheduleAdoptedint() {
        return scheduleAdoptedint;
    }

    public void setFtemployeesStr(String ftemployeesStr) {
        this.ftemployeesStr = ftemployeesStr;
    }

    public String getFtemployeesStr() {
        return ftemployeesStr;
    }

    public void setPtemployeesStr(String ptemployeesStr) {
        this.ptemployeesStr = ptemployeesStr;
    }

    public String getPtemployeesStr() {
        return ptemployeesStr;
    }

    public void setSharedserv(boolean sharedserv) {
        this.sharedserv = sharedserv;
    }

    public boolean isSharedserv() {
        return sharedserv;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setDemoType(int demoType) {
        this.demoType = demoType;
    }

    public int getDemoType() {
        return demoType;
    }

    public void setPlanningDemo(boolean planningDemo) {
        this.planningDemo = planningDemo;
    }

    public boolean isPlanningDemo() {
        return planningDemo;
    }

    public void setImplementDemo(boolean implementDemo) {
        this.implementDemo = implementDemo;
    }

    public boolean isImplementDemo() {
        return implementDemo;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }
}
