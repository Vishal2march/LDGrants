package construction;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import gov.nysed.oce.ldgrants.grants.common.service.EmailValidator;

public class CnApplicationBean extends ActionForm{
    public CnApplicationBean() {
    }
    
   
    public String buildingName;
    public int buildingTypeCode;
    public String buildingType;
    public String buildingStreet;
    public String buildingStreet2;
    public String buildingCity;
    public String buildingState;
    public String buildingZip;
    public boolean physicalAccess;
    public boolean progAccess;
    public int libOwned;
    public int siteOwned;
    public Date dateConstructed;
    public String dateConstructedStr;
    public boolean historicDist;
    public boolean historicLandmark;
    public boolean over50;
    public boolean schoolOwned;
    public String schoolDistrict; 
    public boolean overCost;
    public long managerId;
    public String managerFirstName;
    public String managerLastName;
    public String managerPhone;
    public long managerPhoneId;
    public String managerPhoneExt;
    public long managerPhoneExtId;
    public String managerEmail;
    public long managerEmailId;
    public boolean newConstruction;
    public boolean expansion;
    public boolean acquisition;
    public boolean renovation;
    public boolean conservation;
    public boolean access;
    public boolean otherProject;
    public boolean broadband;
    public int totalCost;
    public String totalCostStr;
    public long totalCostFundId;
    public int requestCost;
    public String requestCostStr;
    public long requestCostFundId;
    public double requestedAmt;
    public double maxRequestCost;
    public boolean bondPaid;
    public String expectStartDate;
    public String projStartedDate;
    public String projCompleteDate;
    public boolean applicantAuth;
    public boolean legal;
    public boolean tenYear;
    public String projectTitle;
    public long grantBuildingId;
    public long grantId;
    public long buildingId;
    public int numberBuildingFloors;
    public String numberBuildingFloorsStr;
    public long buildingSquareFootage;
    public String buildingSquareFootageStr;
    public long instId;
    public long addressId;
    public String module;
    public int fycode;
    public String shpoExemption;
    public long shpoExemptionId;
    public int shpoExemptionNarrTypeId;
    public boolean shpoExemptionFlag;
    public boolean groundDisturb;
    public boolean certOccupancy;
    public long directorId;//the following fields are new for 2016-17 per lwebb 2/23/16
    public String directorFirstName;
    public String directorLastName;
    public String directorPhone;
    public long directorPhoneId;
    public String directorPhoneExt;
    public long directorPhoneExtId;
    public String directorEmail;
    public long directorEmailId;
    
    EmailValidator eValidator = new EmailValidator();
    

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingStreet(String buildingStreet) {
        this.buildingStreet = buildingStreet;
    }

    public String getBuildingStreet() {
        return buildingStreet;
    }

    public void setBuildingStreet2(String buildingStreet2) {
        this.buildingStreet2 = buildingStreet2;
    }

    public String getBuildingStreet2() {
        return buildingStreet2;
    }

    public void setBuildingCity(String buildingCity) {
        this.buildingCity = buildingCity;
    }

    public String getBuildingCity() {
        return buildingCity;
    }

    public void setBuildingState(String buildingState) {
        this.buildingState = buildingState;
    }

    public String getBuildingState() {
        return buildingState;
    }

    public void setBuildingZip(String buildingZip) {
        this.buildingZip = buildingZip;
    }

    public String getBuildingZip() {
        return buildingZip;
    }

    public void setPhysicalAccess(boolean physicalAccess) {
        this.physicalAccess = physicalAccess;
    }

    public boolean isPhysicalAccess() {
        return physicalAccess;
    }

    public void setProgAccess(boolean progAccess) {
        this.progAccess = progAccess;
    }

    public boolean isProgAccess() {
        return progAccess;
    }

    public void setLibOwned(int libOwned) {
        this.libOwned = libOwned;
    }

    public int getLibOwned() {
        return libOwned;
    }

    public void setSiteOwned(int siteOwned) {
        this.siteOwned = siteOwned;
    }

    public int getSiteOwned() {
        return siteOwned;
    }

    public void setHistoricDist(boolean historicDist) {
        this.historicDist = historicDist;
    }

    public boolean isHistoricDist() {
        return historicDist;
    }

    public void setOver50(boolean over50) {
        this.over50 = over50;
    }

    public boolean isOver50() {
        return over50;
    }

    public void setSchoolOwned(boolean schoolOwned) {
        this.schoolOwned = schoolOwned;
    }

    public boolean isSchoolOwned() {
        return schoolOwned;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setManagerFirstName(String managerFirstName) {
        this.managerFirstName = managerFirstName;
    }

    public String getManagerFirstName() {
        return managerFirstName;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setNewConstruction(boolean newConstruction) {
        this.newConstruction = newConstruction;
    }

    public boolean isNewConstruction() {
        return newConstruction;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setAcquisition(boolean acquisition) {
        this.acquisition = acquisition;
    }

    public boolean isAcquisition() {
        return acquisition;
    }

    public void setRenovation(boolean renovation) {
        this.renovation = renovation;
    }

    public boolean isRenovation() {
        return renovation;
    }

    public void setConservation(boolean conservation) {
        this.conservation = conservation;
    }

    public boolean isConservation() {
        return conservation;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public boolean isAccess() {
        return access;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setRequestCost(int requestCost) {
        this.requestCost = requestCost;
    }

    public int getRequestCost() {
        return requestCost;
    }

    public void setRequestedAmt(double requestedAmt) {
        this.requestedAmt = requestedAmt;
    }

    public double getRequestedAmt() {
        return requestedAmt;
    }

    public void setBondPaid(boolean bondPaid) {
        this.bondPaid = bondPaid;
    }

    public boolean isBondPaid() {
        return bondPaid;
    }

    public void setApplicantAuth(boolean applicantAuth) {
        this.applicantAuth = applicantAuth;
    }

    public boolean isApplicantAuth() {
        return applicantAuth;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    public boolean isLegal() {
        return legal;
    }

    public void setTenYear(boolean tenYear) {
        this.tenYear = tenYear;
    }

    public boolean isTenYear() {
        return tenYear;
    }

    public void setGrantBuildingId(long grantBuildingId) {
        this.grantBuildingId = grantBuildingId;
    }

    public long getGrantBuildingId() {
        return grantBuildingId;
    }

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerPhoneExt(String managerPhoneExt) {
        this.managerPhoneExt = managerPhoneExt;
    }

    public String getManagerPhoneExt() {
        return managerPhoneExt;
    }

    public void setManagerPhoneId(long managerPhoneId) {
        this.managerPhoneId = managerPhoneId;
    }

    public long getManagerPhoneId() {
        return managerPhoneId;
    }

    public void setManagerPhoneExtId(long managerPhoneExtId) {
        this.managerPhoneExtId = managerPhoneExtId;
    }

    public long getManagerPhoneExtId() {
        return managerPhoneExtId;
    }

    public void setManagerEmailId(long managerEmailId) {
        this.managerEmailId = managerEmailId;
    }

    public long getManagerEmailId() {
        return managerEmailId;
    }

    public void setExpectStartDate(String expectStartDate) {
        this.expectStartDate = expectStartDate;
    }

    public String getExpectStartDate() {
        return expectStartDate;
    }

    public void setProjStartedDate(String projStartedDate) {
        this.projStartedDate = projStartedDate;
    }

    public String getProjStartedDate() {
        return projStartedDate;
    }

    public void setProjCompleteDate(String projCompleteDate) {
        this.projCompleteDate = projCompleteDate;
    }

    public String getProjCompleteDate() {
        return projCompleteDate;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setOtherProject(boolean otherProject) {
        this.otherProject = otherProject;
    }

    public boolean isOtherProject() {
        return otherProject;
    }

    public void setOverCost(boolean overCost) {
        this.overCost = overCost;
    }

    public boolean isOverCost() {
        return overCost;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setMaxRequestCost(double maxRequestCost) {
        this.maxRequestCost = maxRequestCost;
    }

    public double getMaxRequestCost() {
        return maxRequestCost;
    }

    public void setTotalCostFundId(long totalCostFundId) {
        this.totalCostFundId = totalCostFundId;
    }

    public long getTotalCostFundId() {
        return totalCostFundId;
    }

    public void setRequestCostFundId(long requestCostFundId) {
        this.requestCostFundId = requestCostFundId;
    }

    public long getRequestCostFundId() {
        return requestCostFundId;
    }

    public void setTotalCostStr(String totalCostStr) {
        this.totalCostStr = totalCostStr;
    }

    public String getTotalCostStr() {
        return totalCostStr;
    }

    public void setRequestCostStr(String requestCostStr) {
        this.requestCostStr = requestCostStr;
    }

    public String getRequestCostStr() {
        return requestCostStr;
    }

    public void setBuildingTypeCode(int buildingTypeCode) {
        this.buildingTypeCode = buildingTypeCode;
    }

    public int getBuildingTypeCode() {
        return buildingTypeCode;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setHistoricLandmark(boolean historicLandmark) {
        this.historicLandmark = historicLandmark;
    }

    public boolean isHistoricLandmark() {
        return historicLandmark;
    }

    public void setNumberBuildingFloors(int numberBuildingFloors) {
        this.numberBuildingFloors = numberBuildingFloors;
    }

    public int getNumberBuildingFloors() {
        return numberBuildingFloors;
    }

    public void setBuildingSquareFootage(long buildingSquareFootage) {
        this.buildingSquareFootage = buildingSquareFootage;
    }

    public long getBuildingSquareFootage() {
        return buildingSquareFootage;
    }

    public void setDateConstructed(Date dateConstructed) {
        this.dateConstructed = dateConstructed;
    }

    public Date getDateConstructed() {
        return dateConstructed;
    }

    public void setDateConstructedStr(String dateConstructedStr) {
        this.dateConstructedStr = dateConstructedStr;
    }

    public String getDateConstructedStr() {
        return dateConstructedStr;
    }

    public void setInstId(long instId) {
        this.instId = instId;
    }

    public long getInstId() {
        return instId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModule() {
        return module;
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
          if(yearint <1600 || yearint >2020)
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
      ActionErrors errors = new ActionErrors();
      
    //////////////////validate building addr info///////////////////
    if (isMissing(getBuildingName())) 
       errors.add("bnameMissing", new ActionError("value.required", "Building Name"));
    
    if (isMissing(getBuildingStreet())) 
       errors.add("bstreetMissing", new ActionError("value.required", "Street Address"));
    
    if (isMissing(getBuildingCity())) 
       errors.add("bcityMissing", new ActionError("value.required", "City"));
    
    if (isMissing(getBuildingState())) 
       errors.add("bstateMissing", new ActionError("value.required", "State"));
    
    if (isMissing(getBuildingZip())) 
       errors.add("bzipMissing", new ActionError("value.required", "Zip Code"));
                
    //////////////////validate building data//////////////////////
     if(getBuildingTypeCode()==0)
         errors.add("btypecode", new ActionError("value.required", "Building Type"));        
     
     if(getLibOwned()==0)
         errors.add("libown", new ActionError("value.required", "Library Building Own/Lease"));        
     
     if(getSiteOwned()==0)
         errors.add("siteown", new ActionError("value.required", "Library Site Own/Lease"));        
    
     if(isMissing(getDateConstructedStr()))
         errors.add("constdate", new ActionError("value.required", "Year of Building Construction"));
     else{
         if(isWrongYearFormat(getDateConstructedStr()))
            errors.add("constdate", new ActionError("errors.year", "Year of Building Construction"));            
     }
     
     if (isMissing(getBuildingSquareFootageStr())) 
       errors.add("sqftMissing", new ActionError("value.required", "Square Footage of Building"));
     
     if (isMissing(getNumberBuildingFloorsStr())) 
       errors.add("numflrMissing", new ActionError("value.required", "Number of Floors"));
      
      
    ////////////////////validate school owned//////////////////////
    if(isSchoolOwned()){
        //if school_owned = true; need name of school
        if(isMissing(getSchoolDistrict()))
            errors.add("sdistMissing", new ActionError("errors.schDistrict"));
    }
      
    ////////////////////validate project manager info  /////////////////////      
     if (isMissing(getProjectTitle())) 
       errors.add("ptitleMissing", new ActionError("value.required", "Project Title"));
     
      if (isMissing(getManagerFirstName())) 
        errors.add("fnameMissing", new ActionError("value.required", "Project Manager First Name"));
      
      if (isMissing(getManagerLastName())) 
        errors.add("lnameMissing", new ActionError("value.required", "Project Manager Last Name"));
          
      if (isMissing(getManagerPhone())) 
        errors.add("phoneMissing", new ActionError("value.required", "Project Manager Phone"));
      else
      {
        if(!isValidPhone(getManagerPhone()))
          errors.add("phoneinvalid", new ActionError("errors.phone"));
      }
      
      if (isMissing(getManagerEmail())){ 
        errors.add("emailMissing", new ActionError("value.required", "Project Manager Email"));
	   } else if(!eValidator.isEmailAddressValid(getManagerEmail())) {
	    	errors.add("invalidEmail", new ActionError("errors.email", getManagerEmail()));	
	   }
      
      
      
      ////////////////////validate library director; new 2/24/16  /////////////////////      
        if (isMissing(getDirectorFirstName())) 
          errors.add("dfnameMissing", new ActionError("value.required", "Director First Name"));
        
        if (isMissing(getDirectorLastName())) 
          errors.add("dlnameMissing", new ActionError("value.required", "Director Last Name"));
            
        if (isMissing(getDirectorPhone())) 
          errors.add("dphoneMissing", new ActionError("value.required", "Director Phone"));
        else
        {
          if(!isValidPhone(getDirectorPhone()))
            errors.add("dphoneinvalid", new ActionError("errors.phone"));
        }
        
        if (isMissing(getDirectorEmail())) 
          errors.add("demailMissing", new ActionError("value.required", "Director Email"));
        else if(!eValidator.isEmailAddressValid(getDirectorEmail())) 
        	errors.add("invalidEmail", new ActionError("errors.email", getDirectorEmail()));	
    
               
   ////////////////////////////validate costs ///////////////////    
   
    if (isMissing(getTotalCostStr())) 
      errors.add("totcostMissing", new ActionError("value.required", "Total Project Cost (a)"));
                        
    if (isMissing(getRequestCostStr())) 
      errors.add("reqcostMissing", new ActionError("value.required", "Amount Requested for this Project (c)"));
                     
    //////////////////validate project dates////////////////////////////
      
      /* per LWEBB - remove this question starting 2016-17.  2/23/16
       if (isMissing(getExpectStartDate())){
           errors.add("expectdateMissing", new ActionError("value.required", "Expected Start Date"));
       }
       else{
         if(isWrongDateFormat(getExpectStartDate()))
           errors.add("dateformat", new ActionError("errors.date", "Expected Start Date"));
       }
       */
       
       
       if (isMissing(getProjStartedDate())){
            errors.add("startdateMissing", new ActionError("value.required", "Project Started Date"));
       }
       else{
          if(isWrongDateFormat(getProjStartedDate()))
            errors.add("startdateformat", new ActionError("errors.date", "Project Started Date"));
       }
        
       if (isMissing(getProjCompleteDate())){
             errors.add("compdateMissing", new ActionError("value.required", "Project Complete Date"));
       }
       else{
           if(isWrongDateFormat(getProjCompleteDate()))
             errors.add("compdateformat", new ActionError("errors.date", "Project Complete Date"));
       }
       
        return(errors);
    }

    public void setNumberBuildingFloorsStr(String numberBuildingFloorsStr) {
        this.numberBuildingFloorsStr = numberBuildingFloorsStr;
    }

    public String getNumberBuildingFloorsStr() {
        return numberBuildingFloorsStr;
    }

    public void setBuildingSquareFootageStr(String buildingSquareFootageStr) {
        this.buildingSquareFootageStr = buildingSquareFootageStr;
    }

    public String getBuildingSquareFootageStr() {
        return buildingSquareFootageStr;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setShpoExemption(String shpoExemption) {
        this.shpoExemption = shpoExemption;
    }

    public String getShpoExemption() {
        return shpoExemption;
    }

    public void setShpoExemptionId(long shpoExemptionId) {
        this.shpoExemptionId = shpoExemptionId;
    }

    public long getShpoExemptionId() {
        return shpoExemptionId;
    }

    public void setShpoExemptionNarrTypeId(int shpoExemptionNarrTypeId) {
        this.shpoExemptionNarrTypeId = shpoExemptionNarrTypeId;
    }

    public int getShpoExemptionNarrTypeId() {
        return shpoExemptionNarrTypeId;
    }

    public void setCertOccupancy(boolean certOccupancy) {
        this.certOccupancy = certOccupancy;
    }

    public boolean isCertOccupancy() {
        return certOccupancy;
    }

    public void setShpoExemptionFlag(boolean shpoExemptionFlag) {
        this.shpoExemptionFlag = shpoExemptionFlag;
    }

    public boolean isShpoExemptionFlag() {
        return shpoExemptionFlag;
    }

    public void setGroundDisturb(boolean groundDisturb) {
        this.groundDisturb = groundDisturb;
    }

    public boolean isGroundDisturb() {
        return groundDisturb;
    }

    public void setBroadband(boolean broadband) {
        this.broadband = broadband;
    }

    public boolean isBroadband() {
        return broadband;
    }

    public void setDirectorId(long directorId) {
        this.directorId = directorId;
    }

    public long getDirectorId() {
        return directorId;
    }

    public void setDirectorFirstName(String directorFirstName) {
        this.directorFirstName = directorFirstName;
    }

    public String getDirectorFirstName() {
        return directorFirstName;
    }

    public void setDirectorLastName(String directorLastName) {
        this.directorLastName = directorLastName;
    }

    public String getDirectorLastName() {
        return directorLastName;
    }

    public void setDirectorPhone(String directorPhone) {
        this.directorPhone = directorPhone;
    }

    public String getDirectorPhone() {
        return directorPhone;
    }

    public void setDirectorPhoneId(long directorPhoneId) {
        this.directorPhoneId = directorPhoneId;
    }

    public long getDirectorPhoneId() {
        return directorPhoneId;
    }

    public void setDirectorPhoneExt(String directorPhoneExt) {
        this.directorPhoneExt = directorPhoneExt;
    }

    public String getDirectorPhoneExt() {
        return directorPhoneExt;
    }

    public void setDirectorPhoneExtId(long directorPhoneExtId) {
        this.directorPhoneExtId = directorPhoneExtId;
    }

    public long getDirectorPhoneExtId() {
        return directorPhoneExtId;
    }

    public void setDirectorEmail(String directorEmail) {
        this.directorEmail = directorEmail;
    }

    public String getDirectorEmail() {
        return directorEmail;
    }

    public void setDirectorEmailId(long directorEmailId) {
        this.directorEmailId = directorEmailId;
    }

    public long getDirectorEmailId() {
        return directorEmailId;
    }
}
