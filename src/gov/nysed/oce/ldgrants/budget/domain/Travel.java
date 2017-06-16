package gov.nysed.oce.ldgrants.budget.domain;

import java.util.Date;

public class Travel {

	
	private Long id;	
	private String description;
	private String purpose;
		  		  
	private Integer grantRequest;
	private Long grantRequestLong;

	private Integer amountApproved;
	private Integer expenseSubmitted;
	private Integer expenseApproved;
	
	private Integer projectTotal;
	private Integer institutionContribution;	
	  
	private Date dateCreated;
	private String createdBy;
	private Date dateModified;
	private String modifiedBy;
	  
	private Long grantId;
	private Long fyCode;
	private String costSummary;	
	private String isTotal;
	  
	private Integer amendAmount;
	private String travelPeriod;
	private String journalEntry;
	private String travelerName;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Integer getGrantRequest() {
		return grantRequest;
	}
	public void setGrantRequest(Integer grantRequest) {
		this.grantRequest = grantRequest;
	}
	public Long getGrantRequestLong() {
		return grantRequestLong;
	}
	public void setGrantRequestLong(Long grantRequestLong) {
		this.grantRequestLong = grantRequestLong;
	}
	
	public Integer getExpenseSubmitted() {
		return expenseSubmitted;
	}
	public void setExpenseSubmitted(Integer expenseSubmitted) {
		this.expenseSubmitted = expenseSubmitted;
	}

	public Integer getProjectTotal() {
		return projectTotal;
	}
	public void setProjectTotal(Integer projectTotal) {
		this.projectTotal = projectTotal;
	}
	public Integer getInstitutionContribution() {
		return institutionContribution;
	}
	public void setInstitutionContribution(Integer institutionContribution) {
		this.institutionContribution = institutionContribution;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public Long getFyCode() {
		return fyCode;
	}
	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}
	public String getCostSummary() {
		return costSummary;
	}
	public void setCostSummary(String costSummary) {
		this.costSummary = costSummary;
	}
	public String getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}
	public Integer getAmendAmount() {
		return amendAmount;
	}
	public void setAmendAmount(Integer amendAmount) {
		this.amendAmount = amendAmount;
	}
	public String getTravelPeriod() {
		return travelPeriod;
	}
	public void setTravelPeriod(String travelPeriod) {
		this.travelPeriod = travelPeriod;
	}
	public String getJournalEntry() {
		return journalEntry;
	}
	public void setJournalEntry(String journalEntry) {
		this.journalEntry = journalEntry;
	}
	public String getTravelerName() {
		return travelerName;
	}
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	public Integer getAmountApproved() {
		return amountApproved;
	}
	public void setAmountApproved(Integer amountApproved) {
		this.amountApproved = amountApproved;
	}
	public Integer getExpenseApproved() {
		if(expenseApproved==null)
			expenseApproved=0;//so totals on JTable show up
		return expenseApproved;
	}
	public void setExpenseApproved(Integer expenseApproved) {
		this.expenseApproved = expenseApproved;
	}
	


}
