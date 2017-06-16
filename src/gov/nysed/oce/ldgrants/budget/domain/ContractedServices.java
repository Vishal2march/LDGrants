package gov.nysed.oce.ldgrants.budget.domain;

import java.util.Date;

public class ContractedServices {

private Long id;	
private String serviceType;
private String recipient;
private Integer projectTotal;
private Integer institutionContribution;	  
private String serviceDescription;
	  
private Integer grantRequest;
private Long grantRequestLong;


private Integer amountApproved;
private Integer expenseSubmitted;
private Integer expenseApproved;
  
private Date dateCreated;
private String createdBy;
private Date dateModified;
private String modifiedBy;
  
private Long grantId;
private Long fyCode;
private Long budgetTypeCode;
private String isTotal;
  	  
private Integer amendAmount;
private Date encumbranceDate;
private String journalEntry;
private String providerUsed;



public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getServiceType() {
	return serviceType;
}
public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
}
public String getRecipient() {
	return recipient;
}
public void setRecipient(String recipient) {
	this.recipient = recipient;
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
public String getServiceDescription() {
	return serviceDescription;
}
public void setServiceDescription(String serviceDescription) {
	this.serviceDescription = serviceDescription;
}
public Integer getGrantRequest() {
	if(grantRequest==null)
		grantRequest=0;//so totals on JTable show up
	return grantRequest;
}
public void setGrantRequest(Integer grantRequest) {
	this.grantRequest = grantRequest;
}
public Integer getAmountApproved() {
	return amountApproved;
}
public void setAmountApproved(Integer amountApproved) {
	this.amountApproved = amountApproved;
}
public Integer getExpenseSubmitted() {
	return expenseSubmitted;
}
public void setExpenseSubmitted(Integer expenseSubmitted) {
	this.expenseSubmitted = expenseSubmitted;
}
public Integer getExpenseApproved() {
	if(expenseApproved==null)
		expenseApproved=0;//so totals on JTable show up
	return expenseApproved;
}
public void setExpenseApproved(Integer expenseApproved) {
	this.expenseApproved = expenseApproved;
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
public Long getBudgetTypeCode() {
	return budgetTypeCode;
}
public void setBudgetTypeCode(Long budgetTypeCode) {
	this.budgetTypeCode = budgetTypeCode;
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
public Date getEncumbranceDate() {
	return encumbranceDate;
}
public void setEncumbranceDate(Date encumbranceDate) {
	this.encumbranceDate = encumbranceDate;
}
public String getJournalEntry() {
	return journalEntry;
}
public void setJournalEntry(String journalEntry) {
	this.journalEntry = journalEntry;
}
public String getProviderUsed() {
	return providerUsed;
}
public void setProviderUsed(String providerUsed) {
	this.providerUsed = providerUsed;
}
public Long getGrantRequestLong() {
	return grantRequestLong;
}
public void setGrantRequestLong(Long grantRequestLong) {
	this.grantRequestLong = grantRequestLong;
}	  
	  
	  
}
