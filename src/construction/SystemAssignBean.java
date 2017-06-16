package construction;

import javax.servlet.http.HttpServletRequest;

import mypackage.DBHandler;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SystemAssignBean extends ActionForm{
    public SystemAssignBean() {
    }
    
    
    public long grantId;
    public long instId;
    public long systemInstId;
    public long assignmentId;
    public boolean ratingComplete;
    public long recommendAmt;
    public String recommendAmtStr;
    public long libSysMappingId;
    public boolean assignmentExists;
    public boolean recommendation;
    public String instName;
    public String systemName;
    public int fycode;
    public int fccode;
    public long projectSeqNum;
    public String projName;
    public int fiscalYear;
    public long amtrequested;
    public double maxRequestCost;
    public boolean systemLockOut;
    public boolean reduceMatchExists;
    public String matchJustification;
    public boolean lunchEligible;
    public boolean povertyRate;
    public boolean unemployment;
    public boolean education;
    public boolean englishLang;
    public boolean housing;
    public boolean otherRate;
    public String otherDescr;
     

    public void setGrantId(long grantId) {
        this.grantId = grantId;
    }

    public long getGrantId() {
        return grantId;
    }

    public void setInstId(long instId) {
        this.instId = instId;
    }

    public long getInstId() {
        return instId;
    }

    public void setSystemInstId(long systemInstId) {
        this.systemInstId = systemInstId;
    }

    public long getSystemInstId() {
        return systemInstId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setRatingComplete(boolean ratingComplete) {
        this.ratingComplete = ratingComplete;
    }

    public boolean isRatingComplete() {
        return ratingComplete;
    }

    public void setRecommendAmt(long recommendAmt) {
        this.recommendAmt = recommendAmt;
    }

    public long getRecommendAmt() {
        return recommendAmt;
    }

    public void setLibSysMappingId(long libSysMappingId) {
        this.libSysMappingId = libSysMappingId;
    }

    public long getLibSysMappingId() {
        return libSysMappingId;
    }

    public void setAssignmentExists(boolean assignmentExists) {
        this.assignmentExists = assignmentExists;
    }

    public boolean isAssignmentExists() {
        return assignmentExists;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstName() {
        return instName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setRecommendation(boolean recommendation) {
        this.recommendation = recommendation;
    }

    public boolean isRecommendation() {
        return recommendation;
    }

    public void setRecommendAmtStr(String recommendAmtStr) {
        this.recommendAmtStr = recommendAmtStr;
    }

    public String getRecommendAmtStr() {
        return recommendAmtStr;
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setProjectSeqNum(long projectSeqNum) {
        this.projectSeqNum = projectSeqNum;
    }

    public long getProjectSeqNum() {
        return projectSeqNum;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjName() {
        return projName;
    }

   public void setFccode(int fccode) {
        this.fccode = fccode;
    }

    public int getFccode() {
        return fccode;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setAmtrequested(long amtrequested) {
        this.amtrequested = amtrequested;
    }

    public long getAmtrequested() {
        return amtrequested;
    }

    public void setMaxRequestCost(double maxRequestCost) {
        this.maxRequestCost = maxRequestCost;
    }

    public double getMaxRequestCost() {
        return maxRequestCost;
    }
    
    public boolean isMissing(String value) 
    {
     return((value == null) || (value.trim().equals("")));
    }
    
    public boolean isMissing(int value) 
    {
     return((value == 0) );
    }
    
    public ActionErrors validate(ActionMapping mapping,  HttpServletRequest request) 
    {  
      ActionErrors errors = new ActionErrors();
      
      //verify that amt recommended is not more than 50% max
      if (!isMissing(getRecommendAmtStr())) 
      {
          //parse out an $ or decimals in amt_recommended
          DBHandler dbh = new DBHandler();
          long amtrec_num = 0;
          if(getRecommendAmtStr()!= null && !getRecommendAmtStr().equals(""))
            amtrec_num = dbh.parseLongAmtNoDecimal(getRecommendAmtStr());
            
          if(amtrec_num > getMaxRequestCost())
            errors.add("overMaxAward", new ActionError("errors.overMaxCnAward", getInstName()));
      }
      return errors;
    }

    public void setSystemLockOut(boolean systemLockOut) {
        this.systemLockOut = systemLockOut;
    }

    public boolean isSystemLockOut() {
        return systemLockOut;
    }

    public void setReduceMatchExists(boolean reduceMatchExists) {
        this.reduceMatchExists = reduceMatchExists;
    }

    public boolean isReduceMatchExists() {
        return reduceMatchExists;
    }

    public void setMatchJustification(String matchJustification) {
        this.matchJustification = matchJustification;
    }

    public String getMatchJustification() {
        return matchJustification;
    }

    public void setLunchEligible(boolean lunchEligible) {
        this.lunchEligible = lunchEligible;
    }

    public boolean isLunchEligible() {
        return lunchEligible;
    }

    public void setPovertyRate(boolean povertyRate) {
        this.povertyRate = povertyRate;
    }

    public boolean isPovertyRate() {
        return povertyRate;
    }

    public void setUnemployment(boolean unemployment) {
        this.unemployment = unemployment;
    }

    public boolean isUnemployment() {
        return unemployment;
    }

    public void setEducation(boolean education) {
        this.education = education;
    }

    public boolean isEducation() {
        return education;
    }

    public void setEnglishLang(boolean englishLang) {
        this.englishLang = englishLang;
    }

    public boolean isEnglishLang() {
        return englishLang;
    }

    public void setHousing(boolean housing) {
        this.housing = housing;
    }

    public boolean isHousing() {
        return housing;
    }

    public void setOtherRate(boolean otherRate) {
        this.otherRate = otherRate;
    }

    public boolean isOtherRate() {
        return otherRate;
    }

    public void setOtherDescr(String otherDescr) {
        this.otherDescr = otherDescr;
    }

    public String getOtherDescr() {
        return otherDescr;
    }
}
