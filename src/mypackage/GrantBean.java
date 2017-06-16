/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  GrantBean.java
 * Creation/Modification History  :
 *
 * SH   2/1/07      Created
 *
 * Description
 * This class will store all information related to grants such as the ID and project numbers,
 * the institution information, and director or preservation officer information.
 *****************************************************************************/
package mypackage;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;

public class GrantBean implements Comparable
{
  public long grantid;
  public String fiscalyear;
  public long instID;
  public String sedcode;
  public String instName;
  public int fycode;
  public int fccode;
  public int fiscalYear;
  public long projseqnum;
  public String buildingName;
  public String addr1;
  public String addr2;
  public String city;
  public String state;
  public String zipcd1;
  public String zipcd2;
  public String county;
  public String title;
  public String needApproval;
  public String status;
  public String program;
  public String contractNum;
  public Date startdate;
  public Date enddate;
  public int totamtappr;
  public double score;
  public String scoreStr;
  public boolean educationapp;
  public String federalid;
  public double rating;
  public int totamtreq;
  public TotalsBean[] totalsBeans;
  public RatingBean[] ratingBeans;
  public CommentBean[] commentBeans;
  public OfficerBean projectManager;
  public SubmitBean submissionBean;
  public AppStatusBean grantstatusBean;
  public int countycode;
  public String projcategory;
  public int projcategoryId;
  public boolean dorisflag;
  public String dorisname;
  public long panelgrantId;
  public long panelId;
  public boolean assignpanel;
  public boolean assigndiffpanel;
  public boolean sendmail;
  public boolean inventory;
  public boolean emailmgmt;
  public boolean cooperative;
  public boolean electronicrecords;
  public String email;
  public String name;
  public int countapp;
  public String cooperatives;
  public String recommend;
  public String userid;
  public long staffid;
  public String region;
  public String ceoName;
  public String pmName;
  public String rmoName;
  public String summaryDescr;
  public boolean displayAmts;
  public int systemAmtApproved;
  public int amountReq;
  public long systemInstId;
  public String systemName;
  public long totalRequest;
  public long totalRecommend;
  public long totalRecommend3Year;
  public long totalApproved;
  public double recommendPercent;
  public String recommendPercentStr;
  public String approvedPercentStr;
  public int fyOne;
  public int fyTwo;
  public int fyThree;
  public long fyOneRequest;
  public long fyTwoRequest;
  public long fyThreeRequest;
  public boolean dasnySubmit;//for cn; is app submitted to dasny
  public boolean dasnyApprove;//for cn; is app approved by dasny
  public boolean bondCouncilApprove;//for cn; is app appr by dasny bond council
  public boolean reducedMatch;//for cn; is this a reduced match grant
  public String matchJustification;//for cn; justification narrative
  public int buildingTypeCode;//for cn
  public boolean finalApprove;//for cn report; is final approved
  public String plsName;
  public String plsEmail;
  public String dateSubmitStr;
  public long reviewerId;
  public int fundAmount;
  public int newconstruct;//construction project categories
  public int buildingexpand;
  public int acquisition;
  public int renovation;
  public int energyconserve;
  public int accessibility;
  public int safety;
  public int luncheligible;//construction reduce match criteria
  public int povertyrate;
  public int unemployment;
  public int education;
  public int englishlang;
  public int housing;
  public int other;
  public long ldacAppropAmt;
  public String ldacAppropAmtStr;
  public int bonusScore;
  //these are construction shpo fields from coversheet
  public int over50;
  public int historicDistrict;
  public int historicLandmark;
  public int groundDisturb;
  
  
  

  public GrantBean()
  {
  }

  public long getGrantid()
  {
    return grantid;
  }

  public void setGrantid(long grantid)
  {
    this.grantid = grantid;
  }

  public String getFiscalyear()
  {
    return fiscalyear;
  }

  public void setFiscalyear(String fiscalyear)
  {
    this.fiscalyear = fiscalyear;
  }

  public long getInstID()
  {
    return instID;
  }

  public void setInstID(long instID)
  {
    this.instID = instID;
  }

  public String getInstName()
  {
    return instName;
  }

  public void setInstName(String instName)
  {
    this.instName = instName;
  }

  public int getFycode()
  {
    return fycode;
  }

  public void setFycode(int fycode)
  {
    this.fycode = fycode;
  }

  public int getFccode()
  {
    return fccode;
  }

  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }

  public long getProjseqnum()
  {
    return projseqnum;
  }

  public void setProjseqnum(long projseqnum)
  {
    this.projseqnum = projseqnum;
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

  public String getZipcd1()
  {
    return zipcd1;
  }

  public void setZipcd1(String zipcd1)
  {
    this.zipcd1 = zipcd1;
  }

  public String getZipcd2()
  {
    return zipcd2;
  }

  public void setZipcd2(String zipcd2)
  {
    this.zipcd2 = zipcd2;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getNeedApproval()
  {
    return needApproval;
  }

  public void setNeedApproval(String needApproval)
  {
    this.needApproval = needApproval;
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getProgram()
  {
    return program;
  }

  public void setProgram(String program)
  {
    this.program = program;
  }

  public String getContractNum()
  {
    return contractNum;
  }

  public void setContractNum(String contractNum)
  {
    this.contractNum = contractNum;
  }

  public Date getStartdate()
  {
    return startdate;
  }

  public void setStartdate(Date startdate)
  {
    this.startdate = startdate;
  }

  public Date getEnddate()
  {
    return enddate;
  }

  public void setEnddate(Date enddate)
  {
    this.enddate = enddate;
  }

  public int getTotamtappr()
  {
    return totamtappr;
  }

  public void setTotamtappr(int totamtappr)
  {
    this.totamtappr = totamtappr;
  }

  public double getScore()
  {
    return score;
  }

  public void setScore(double score)
  {
    this.score = score;
  }  

  public String getFederalid()
  {
    return federalid;
  }

  public void setFederalid(String federalid)
  {
    this.federalid = federalid;
  }

  public String getCounty()
  {
    return county;
  }

  public void setCounty(String county)
  {
    this.county = county;
  }

  public int getTotamtreq()
  {
    return totamtreq;
  }

  public void setTotamtreq(int totamtreq)
  {
    this.totamtreq = totamtreq;
  }

  public int compareTo(Object o) 
  {
      GrantBean gb2 = (GrantBean) o; 
      double gbscore = getScore();
      double gb2score = gb2.getScore();      
           
      if (gbscore > gb2score)
        return -1;
      else if (gbscore < gb2score)
        return 1;
      return 0; //they are equal
  }
 
 
 public static Comparator InstitutionComparator = new Comparator() {
    public int compare(Object gb1, Object gb2) {
      String instname1 = ((GrantBean) gb1).getInstName().toUpperCase();
      String instname2 = ((GrantBean) gb2).getInstName().toUpperCase();

      return instname1.compareTo(instname2);     
    }
  };
  
 public static Comparator SystemNameComparator = new Comparator() {
       public int compare(Object gb1, Object gb2) {
         String sysname1 = ((GrantBean) gb1).getSystemName().toUpperCase();
         String sysname2 = ((GrantBean) gb2).getSystemName().toUpperCase();
         
         int i = sysname1.compareTo(sysname2);
         if(i!=0){
             return i;
         }
         
         //if same sysname; then compare institution name
         String instname1 = ((GrantBean) gb1).getInstName().toUpperCase();
         String instname2 = ((GrantBean) gb2).getInstName().toUpperCase();
         return instname1.compareTo(instname2);    
       }
     };
  
  public static Comparator FiscalYearComparator = new Comparator() {
    public int compare(Object gb1, Object gb2) {
      String fy1 = ((GrantBean) gb1).getFiscalyear();
      String fy2 = ((GrantBean) gb2).getFiscalyear();

      return fy1.compareTo(fy2);     
    }
  };
   
  public static Comparator FiscalYearCodeComparator = new Comparator() {
    public int compare(Object gb1, Object gb2) {
      int fy1 = ((GrantBean) gb1).getFycode();
      int fy2 = ((GrantBean) gb2).getFycode();

      return new Integer(fy1).compareTo(new Integer(fy2));     
    }
  };
     
  public static Comparator CategoryScoreComparator = new Comparator() {
     public int compare(Object gb1, Object gb2) {
       String cat1 = ((GrantBean) gb1).getProjcategory().toUpperCase();
       String cat2 = ((GrantBean) gb2).getProjcategory().toUpperCase();
       
       int i = cat1.compareTo(cat2);
       if(i!=0){
           return i;
       }
       
       //if same project category; then compare score
       String score1 = ((GrantBean) gb1).getScoreStr().toUpperCase();
       String score2 = ((GrantBean) gb2).getScoreStr().toUpperCase();
       return score2.compareTo(score1);    
     }
   };
  
  
  public void setTotalsBeans(TotalsBean[] totalsBeans)
  {
    this.totalsBeans = totalsBeans;
  }


  public TotalsBean[] getTotalsBeans()
  {
    return totalsBeans;
  }

  public double getRating()
  {
    return rating;
  }

  public void setRating(double rating)
  {
    this.rating = rating;
  }


  public void setRatingBeans(RatingBean[] ratingBeans)
  {
    this.ratingBeans = ratingBeans;
  }


  public RatingBean[] getRatingBeans()
  {
    return ratingBeans;
  }


  public void setProjectManager(OfficerBean projectManager)
  {
    this.projectManager = projectManager;
  }


  public OfficerBean getProjectManager()
  {
    return projectManager;
  }


  public void setSubmissionBean(SubmitBean submissionBean)
  {
    this.submissionBean = submissionBean;
  }


  public SubmitBean getSubmissionBean()
  {
    return submissionBean;
  }

  public int getCountycode()
  {
    return countycode;
  }

  public void setCountycode(int countycode)
  {
    this.countycode = countycode;
  }

  public String getProjcategory()
  {
    return projcategory;
  }

  public void setProjcategory(String projcategory)
  {
    this.projcategory = projcategory;
  }

  public boolean isDorisflag()
  {
    return dorisflag;
  }

  public void setDorisflag(boolean dorisflag)
  {
    this.dorisflag = dorisflag;
  }

  public String getDorisname()
  {
    return dorisname;
  }

  public void setDorisname(String dorisname)
  {
    this.dorisname = dorisname;
  }


    public void setPanelgrantId(long panelgrantId) {
        this.panelgrantId = panelgrantId;
    }

    public long getPanelgrantId() {
        return panelgrantId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public long getPanelId() {
        return panelId;
    }

    public void setAssignpanel(boolean assignpanel) {
        this.assignpanel = assignpanel;
    }

    public boolean isAssignpanel() {
        return assignpanel;
    }

    public void setEducationapp(boolean educationapp) {
        this.educationapp = educationapp;
    }

    public boolean isEducationapp() {
        return educationapp;
    }

    public void setAssigndiffpanel(boolean assigndiffpanel) {
        this.assigndiffpanel = assigndiffpanel;
    }

    public boolean isAssigndiffpanel() {
        return assigndiffpanel;
    }

    public void setGrantstatusBean(AppStatusBean grantstatusBean) {
        this.grantstatusBean = grantstatusBean;
    }

    public AppStatusBean getGrantstatusBean() {
        return grantstatusBean;
    }

    public void setCommentBeans(CommentBean[] commentBeans) {
        this.commentBeans = commentBeans;
    }

    public CommentBean[] getCommentBeans() {
        return commentBeans;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


  public void setProjcategoryId(int projcategoryId)
  {
    this.projcategoryId = projcategoryId;
  }


  public int getProjcategoryId()
  {
    return projcategoryId;
  }

    public void setCountapp(int countapp) {
        this.countapp = countapp;
    }

    public int getCountapp() {
        return countapp;
    }

    public void setCooperatives(String cooperatives) {
        this.cooperatives = cooperatives;
    }

    public String getCooperatives() {
        return cooperatives;
    }

    public void setSendmail(boolean sendmail) {
        this.sendmail = sendmail;
    }

    public boolean isSendmail() {
        return sendmail;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setStaffid(long staffid) {
        this.staffid = staffid;
    }

    public long getStaffid() {
        return staffid;
    }

    public void setSedcode(String sedcode) {
        this.sedcode = sedcode;
    }

    public String getSedcode() {
        return sedcode;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setEmailmgmt(boolean emailmgmt) {
        this.emailmgmt = emailmgmt;
    }

    public boolean isEmailmgmt() {
        return emailmgmt;
    }

    public void setCooperative(boolean cooperative) {
        this.cooperative = cooperative;
    }

    public boolean isCooperative() {
        return cooperative;
    }

    public void setElectronicrecords(boolean electronicrecords) {
        this.electronicrecords = electronicrecords;
    }

    public boolean isElectronicrecords() {
        return electronicrecords;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPmName() {
        return pmName;
    }

    public void setRmoName(String rmoName) {
        this.rmoName = rmoName;
    }

    public String getRmoName() {
        return rmoName;
    }

    public void setSummaryDescr(String summaryDescr) {
        this.summaryDescr = summaryDescr;
    }

    public String getSummaryDescr() {
        return summaryDescr;
    }

    public void setDisplayAmts(boolean displayAmts) {
        this.displayAmts = displayAmts;
    }

    public boolean isDisplayAmts() {
        return displayAmts;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

   public void setAmountReq(int amountReq) {
        this.amountReq = amountReq;
    }

    public int getAmountReq() {
        return amountReq;
    }

    public void setSystemAmtApproved(int systemAmtApproved) {
        this.systemAmtApproved = systemAmtApproved;
    }

    public int getSystemAmtApproved() {
        return systemAmtApproved;
    }


    public void setSystemInstId(long systemInstId) {
        this.systemInstId = systemInstId;
    }

    public long getSystemInstId() {
        return systemInstId;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setTotalRequest(long totalRequest) {
        this.totalRequest = totalRequest;
    }

    public long getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRecommend(long totalRecommend) {
        this.totalRecommend = totalRecommend;
    }

    public long getTotalRecommend() {
        return totalRecommend;
    }

    public void setRecommendPercent(double recommendPercent) {
        this.recommendPercent = recommendPercent;
    }

    public double getRecommendPercent() {
        return recommendPercent;
    }

    public void setRecommendPercentStr(String recommendPercentStr) {
        this.recommendPercentStr = recommendPercentStr;
    }

    public String getRecommendPercentStr() {
        return recommendPercentStr;
    }

    public void setTotalApproved(long totalApproved) {
        this.totalApproved = totalApproved;
    }

    public long getTotalApproved() {
        return totalApproved;
    }

    public void setApprovedPercentStr(String approvedPercentStr) {
        this.approvedPercentStr = approvedPercentStr;
    }

    public String getApprovedPercentStr() {
        return approvedPercentStr;
    }

    public void setFyOne(int fyOne) {
        this.fyOne = fyOne;
    }

    public int getFyOne() {
        return fyOne;
    }

    public void setFyTwo(int fyTwo) {
        this.fyTwo = fyTwo;
    }

    public int getFyTwo() {
        return fyTwo;
    }

    public void setFyThree(int fyThree) {
        this.fyThree = fyThree;
    }

    public int getFyThree() {
        return fyThree;
    }

    public void setFyOneRequest(long fyOneRequest) {
        this.fyOneRequest = fyOneRequest;
    }

    public long getFyOneRequest() {
        return fyOneRequest;
    }

    public void setFyTwoRequest(long fyTwoRequest) {
        this.fyTwoRequest = fyTwoRequest;
    }

    public long getFyTwoRequest() {
        return fyTwoRequest;
    }

    public void setFyThreeRequest(long fyThreeRequest) {
        this.fyThreeRequest = fyThreeRequest;
    }

    public long getFyThreeRequest() {
        return fyThreeRequest;
    }

    public void setDasnySubmit(boolean dasnySubmit) {
        this.dasnySubmit = dasnySubmit;
    }

    public boolean isDasnySubmit() {
        return dasnySubmit;
    }

    public void setDasnyApprove(boolean dasnyApprove) {
        this.dasnyApprove = dasnyApprove;
    }

    public boolean isDasnyApprove() {
        return dasnyApprove;
    }

    public void setBondCouncilApprove(boolean bondCouncilApprove) {
        this.bondCouncilApprove = bondCouncilApprove;
    }

    public boolean isBondCouncilApprove() {
        return bondCouncilApprove;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setReducedMatch(boolean reducedMatch) {
        this.reducedMatch = reducedMatch;
    }

    public boolean isReducedMatch() {
        return reducedMatch;
    }

    public void setMatchJustification(String matchJustification) {
        this.matchJustification = matchJustification;
    }

    public String getMatchJustification() {
        return matchJustification;
    }

    public void setPlsName(String plsName) {
        this.plsName = plsName;
    }

    public String getPlsName() {
        return plsName;
    }

    public void setPlsEmail(String plsEmail) {
        this.plsEmail = plsEmail;
    }

    public String getPlsEmail() {
        return plsEmail;
    }

    public void setDateSubmitStr(String dateSubmitStr) {
        this.dateSubmitStr = dateSubmitStr;
    }

    public String getDateSubmitStr() {
        return dateSubmitStr;
    }

    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public long getReviewerId() {
        return reviewerId;
    }

    public void setBuildingTypeCode(int buildingTypeCode) {
        this.buildingTypeCode = buildingTypeCode;
    }

    public int getBuildingTypeCode() {
        return buildingTypeCode;
    }

    public void setFinalApprove(boolean finalApprove) {
        this.finalApprove = finalApprove;
    }

    public boolean isFinalApprove() {
        return finalApprove;
    }

    public void setFundAmount(int fundAmount) {
        this.fundAmount = fundAmount;
    }

    public int getFundAmount() {
        return fundAmount;
    }

    public void setNewconstruct(int newconstruct) {
        this.newconstruct = newconstruct;
    }

    public int getNewconstruct() {
        return newconstruct;
    }

    public void setBuildingexpand(int buildingexpand) {
        this.buildingexpand = buildingexpand;
    }

    public int getBuildingexpand() {
        return buildingexpand;
    }

    public void setAcquisition(int acquisition) {
        this.acquisition = acquisition;
    }

    public int getAcquisition() {
        return acquisition;
    }

    public void setRenovation(int renovation) {
        this.renovation = renovation;
    }

    public int getRenovation() {
        return renovation;
    }

    public void setEnergyconserve(int energyconserve) {
        this.energyconserve = energyconserve;
    }

    public int getEnergyconserve() {
        return energyconserve;
    }

    public void setAccessibility(int accessibility) {
        this.accessibility = accessibility;
    }

    public int getAccessibility() {
        return accessibility;
    }

    public void setSafety(int safety) {
        this.safety = safety;
    }

    public int getSafety() {
        return safety;
    }

    public void setLuncheligible(int luncheligible) {
        this.luncheligible = luncheligible;
    }

    public int getLuncheligible() {
        return luncheligible;
    }

    public void setPovertyrate(int povertyrate) {
        this.povertyrate = povertyrate;
    }

    public int getPovertyrate() {
        return povertyrate;
    }

    public void setUnemployment(int unemployment) {
        this.unemployment = unemployment;
    }

    public int getUnemployment() {
        return unemployment;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getEducation() {
        return education;
    }

    public void setEnglishlang(int englishlang) {
        this.englishlang = englishlang;
    }

    public int getEnglishlang() {
        return englishlang;
    }

    public void setHousing(int housing) {
        this.housing = housing;
    }

    public int getHousing() {
        return housing;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getOther() {
        return other;
    }

    public void setScoreStr(String scoreStr) {
        this.scoreStr = scoreStr;
    }

    public String getScoreStr() {
        return scoreStr;
    }

    public void setLdacAppropAmt(long ldacAppropAmt) {
        this.ldacAppropAmt = ldacAppropAmt;
    }

    public long getLdacAppropAmt() {
        return ldacAppropAmt;
    }

    public void setLdacAppropAmtStr(String ldacAppropAmtStr) {
        this.ldacAppropAmtStr = ldacAppropAmtStr;
    }

    public String getLdacAppropAmtStr() {
        return ldacAppropAmtStr;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public int getBonusScore() {
        return bonusScore;
    }

    public void setTotalRecommend3Year(long totalRecommend3Year) {
        this.totalRecommend3Year = totalRecommend3Year;
    }

    public long getTotalRecommend3Year() {
        return totalRecommend3Year;
    }

    public void setOver50(int over50) {
        this.over50 = over50;
    }

    public int getOver50() {
        return over50;
    }

    public void setHistoricDistrict(int historicDistrict) {
        this.historicDistrict = historicDistrict;
    }

    public int getHistoricDistrict() {
        return historicDistrict;
    }

    public void setHistoricLandmark(int historicLandmark) {
        this.historicLandmark = historicLandmark;
    }

    public int getHistoricLandmark() {
        return historicLandmark;
    }

    public void setGroundDisturb(int groundDisturb) {
        this.groundDisturb = groundDisturb;
    }

    public int getGroundDisturb() {
        return groundDisturb;
    }
}
