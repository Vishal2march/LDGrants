package gov.nysed.oce.ldgrants.budget.domain;

import java.util.Date;

public class Supplies {

	
	private Long id;	
	private Integer quantity;
	private String description;
	private float unitPrice;
	private String vendor;	
		  		  
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
	private Long budgetTypeCode;
	private String isTotal;
	  
	private Integer amendAmount;
	private Date encumbranceDate;
	private String journalEntry;
	
	
	
	public float convertNullToZero(String databaseValue){
		return (databaseValue==null ? 0 : Float.parseFloat(databaseValue));
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = convertNullToZero(unitPrice);
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public Integer getGrantRequest() {
		if(grantRequest==null)
			grantRequest=0;//supp_mat_equips table; field grant_request cannot be null(why?)
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
}
