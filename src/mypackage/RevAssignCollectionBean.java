package mypackage;

import construction.SystemAssignBean;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

public class RevAssignCollectionBean extends ActionForm
{
  public RevAssignCollectionBean()
  {
  }
  
  public List allAssignRecords;//for literacy
  public List allPotentialGrants;//for literacy
  public List allVendorRecords;//for lgrmif
  public String reviewerName;
  public int workingTemplateId;
  public String emailAddress;
  public int fycode;
  public String approvalType;
  public int fccode;
  public String duedate;
  
  //for cnReviewer - submit to ld list
  public List allConstructionAssigns;
  public long totalAmountRecommend;
  public long totalAmountRequest;
  public long initialAlloc;
  
  //for stateaid
  public List allStateaidApps;


  public void setAllAssignRecords(List allAssignRecords)
  {
    this.allAssignRecords = allAssignRecords;
  }


  public List getAllAssignRecords()
  {
    return allAssignRecords;
  }
  
  public ReviewerAssignBean getAssignItem(int index)
  {        // make sure that orderList is not null
      if(this.allAssignRecords == null)
      {
          this.allAssignRecords = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allAssignRecords.size())
      {
          this.allAssignRecords.add(new ReviewerAssignBean());
      }

      // return the requested item
      return (ReviewerAssignBean) allAssignRecords.get(index);
  }

  public String getReviewerName()
  {
    return reviewerName;
  }

  public void setReviewerName(String reviewerName)
  {
    this.reviewerName = reviewerName;
  }

    public void setAllPotentialGrants(List allPotentialGrants) {
        this.allPotentialGrants = allPotentialGrants;
    }

    public List getAllPotentialGrants() {
        return allPotentialGrants;
    }
    
    
    public GrantBean getGrantItem(int index)
    {        // make sure that orderList is not null
        if(this.allPotentialGrants == null)
        {
            this.allPotentialGrants = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allPotentialGrants.size())
        {
            this.allPotentialGrants.add(new GrantBean());
        }

        // return the requested item
        return (GrantBean) allPotentialGrants.get(index);
    }

    public void setWorkingTemplateId(int workingTemplateId) {
        this.workingTemplateId = workingTemplateId;
    }

    public int getWorkingTemplateId() {
        return workingTemplateId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setAllVendorRecords(List allVendorRecords) {
        this.allVendorRecords = allVendorRecords;
    }

    public List getAllVendorRecords() {
        return allVendorRecords;
    }
    
    public VendorBean getVendorItem(int index)
    {        // make sure that orderList is not null
        if(this.allVendorRecords == null)
        {
            this.allVendorRecords = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allVendorRecords.size())
        {
            this.allVendorRecords.add(new VendorBean());
        }

        // return the requested item
        return (VendorBean) allVendorRecords.get(index);
    }

    public void setFycode(int fycode) {
        this.fycode = fycode;
    }

    public int getFycode() {
        return fycode;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getApprovalType() {
        return approvalType;
    }



//for construction pls reviewer module
    public void setAllConstructionAssigns(List allConstructionAssigns) {
        this.allConstructionAssigns = allConstructionAssigns;
    }

    public List getAllConstructionAssigns() {
        return allConstructionAssigns;
    }
    
    
    public SystemAssignBean getSystemAssignItem(int index)
    {        
        // make sure that orderList is not null
        if(this.allConstructionAssigns == null)
        {
            this.allConstructionAssigns = new ArrayList();
        }

        // indexes do not come in order, populate empty spots
        while(index >= this.allConstructionAssigns.size())
        {
            this.allConstructionAssigns.add(new SystemAssignBean());
        }

        // return the requested item
        return (SystemAssignBean) allConstructionAssigns.get(index);
    }

    public void setTotalAmountRecommend(long totalAmountRecommend) {
        this.totalAmountRecommend = totalAmountRecommend;
    }

    public long getTotalAmountRecommend() {
        return totalAmountRecommend;
    }

    public void setTotalAmountRequest(long totalAmountRequest) {
        this.totalAmountRequest = totalAmountRequest;
    }

    public long getTotalAmountRequest() {
        return totalAmountRequest;
    }

    public void setInitialAlloc(long initialAlloc) {
        this.initialAlloc = initialAlloc;
    }

    public long getInitialAlloc() {
        return initialAlloc;
    }

    public void setFccode(int fccode) {
        this.fccode = fccode;
    }

    public int getFccode() {
        return fccode;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setAllStateaidApps(List allStateaidApps) {
        this.allStateaidApps = allStateaidApps;
    }

    public List getAllStateaidApps() {
        return allStateaidApps;
    }
    
  public GrantBean getStateaidItem(int index)
  {        
      // make sure that orderList is not null
      if(this.allStateaidApps == null)
      {
          this.allStateaidApps = new ArrayList();
      }

      // indexes do not come in order, populate empty spots
      while(index >= this.allStateaidApps.size())
      {
          this.allStateaidApps.add(new GrantBean());
      }

      // return the requested item
      return (GrantBean) allStateaidApps.get(index);
  }
}
