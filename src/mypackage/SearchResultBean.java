package mypackage;

public class SearchResultBean {
    public SearchResultBean() {
    }
    
    
    public long revid;
    public String fname;
    public String lname;
    public String affiliation;
    public long panelid;
    public long grantid;
    public String projcategory;
    public String projectNum;
    public String description;
    public String year;
    public int fccode;
    public int fycode;
    public String institution;
    public boolean acceptreview;
    public boolean assignpanel;
    public ReviewerBean[] reviewerBeans;
    public int fullfund;
    public int modifyfund;
    public int nofund;
    public int instTypeCode;
    public int instSubTypeCode;
    public String active;

    public void setRevid(long revid) {
        this.revid = revid;
    }

    public long getRevid() {
        return revid;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLname() {
        return lname;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAffiliation() {
        return affiliation;
    }


    public void setPanelid(long panelid) {
        this.panelid = panelid;
    }

    public long getPanelid() {
        return panelid;
    }

    public void setGrantid(long grantid) {
        this.grantid = grantid;
    }

    public long getGrantid() {
        return grantid;
    }

    public void setProjcategory(String projcategory) {
        this.projcategory = projcategory;
    }

    public String getProjcategory() {
        return projcategory;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setAcceptreview(boolean acceptreview) {
        this.acceptreview = acceptreview;
    }

    public boolean isAcceptreview() {
        return acceptreview;
    }

    public void setAssignpanel(boolean assignpanel) {
        this.assignpanel = assignpanel;
    }

    public boolean isAssignpanel() {
        return assignpanel;
    }


  public void setReviewerBeans(ReviewerBean[] reviewerBeans)
  {
    this.reviewerBeans = reviewerBeans;
  }


  public ReviewerBean[] getReviewerBeans()
  {
    return reviewerBeans;
  }


  public void setFccode(int fccode)
  {
    this.fccode = fccode;
  }


  public int getFccode()
  {
    return fccode;
  }


  public void setInstitution(String institution)
  {
    this.institution = institution;
  }


  public String getInstitution()
  {
    return institution;
  }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setFullfund(int fullfund) {
        this.fullfund = fullfund;
    }

    public int getFullfund() {
        return fullfund;
    }

    public void setModifyfund(int modifyfund) {
        this.modifyfund = modifyfund;
    }

    public int getModifyfund() {
        return modifyfund;
    }

    public void setNofund(int nofund) {
        this.nofund = nofund;
    }

    public int getNofund() {
        return nofund;
    }

    public void setInstTypeCode(int instTypeCode) {
        this.instTypeCode = instTypeCode;
    }

    public int getInstTypeCode() {
        return instTypeCode;
    }

    public void setInstSubTypeCode(int instSubTypeCode) {
        this.instSubTypeCode = instSubTypeCode;
    }

    public int getInstSubTypeCode() {
        return instSubTypeCode;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }
}
