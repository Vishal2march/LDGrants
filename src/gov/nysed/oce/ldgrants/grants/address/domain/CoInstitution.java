package gov.nysed.oce.ldgrants.grants.address.domain;

import java.util.Date;

public class CoInstitution {

	Long id;
	Long instId;
	Long graId;
	String createdBy;
	String isLead;
	String modifiedBy;
    Date dateCreated;
    Date dateModified;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	public Long getGraId() {
		return graId;
	}
	public void setGraId(Long graId) {
		this.graId = graId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getIsLead() {
		return isLead;
	}
	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
    
    
}
