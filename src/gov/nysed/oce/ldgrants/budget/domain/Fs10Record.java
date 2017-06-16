package gov.nysed.oce.ldgrants.budget.domain;

public class Fs10Record {

	private Long id;
	private String description;
	private Long amountIncr;
	private Long amountDecr;
	private Long expCode;
	private String expName;
	private Long graId;
	private Boolean adminApproval;
	private Long fyCode;
	private Long expenditureTypeId;
	
	private String adjustment;
	
	
	
	
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
	public Long getAmountIncr() {
		return amountIncr;
	}
	public void setAmountIncr(Long amountIncr) {
		this.amountIncr = amountIncr;
	}
	public Long getAmountDecr() {
		return amountDecr;
	}
	public void setAmountDecr(Long amountDecr) {
		this.amountDecr = amountDecr;
	}
	public Long getExpCode() {
		return expCode;
	}
	public void setExpCode(Long expCode) {
		this.expCode = expCode;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public Long getGraId() {
		return graId;
	}
	public void setGraId(Long graId) {
		this.graId = graId;
	}
	public Boolean getAdminApproval() {
		return adminApproval;
	}
	public void setAdminApproval(Boolean adminApproval) {
		this.adminApproval = adminApproval;
	}
	public Long getFyCode() {
		return fyCode;
	}
	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}
	public Long getExpenditureTypeId() {
		return expenditureTypeId;
	}
	public void setExpenditureTypeId(Long expenditureTypeId) {
		this.expenditureTypeId = expenditureTypeId;
	}
	public String getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(String adjustment) {
		this.adjustment = adjustment;
	}

}
