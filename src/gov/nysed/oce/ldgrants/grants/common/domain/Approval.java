package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class Approval {

	private Long id;
	private Long grantId;
	private Long formTypeId;
	private Long amountDeclined;
	private Long fyCode; 
	
	private String version;
	private String approvalType;
	private String admin;
	private Date dateCreated;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public Long getFormTypeId() {
		return formTypeId;
	}
	public void setFormTypeId(Long formTypeId) {
		this.formTypeId = formTypeId;
	}
	public Long getAmountDeclined() {
		return amountDeclined;
	}
	public void setAmountDeclined(Long amountDeclined) {
		this.amountDeclined = amountDeclined;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Long getFyCode() {
		return fyCode;
	}
	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}
	
}
