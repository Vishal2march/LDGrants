package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class StatusBean {
	
	private Long id;
	private Long gantId;
	private Long narrativeId;
	private Long narrativeLock;
	
	private String status;
	private String createdBy;
	private String modifiedBy;
	
	private Date dateCreated;
	private Date dateModified;
	
	public StatusBean() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGantId() {
		return gantId;
	}
	public void setGantId(Long gantId) {
		this.gantId = gantId;
	}
	public Long getNarrativeId() {
		return narrativeId;
	}
	public void setNarrativeId(Long narrativeId) {
		this.narrativeId = narrativeId;
	}
	public Long getNarrativeLock() {
		return narrativeLock;
	}
	public void setNarrativeLock(Long narrativeLock) {
		this.narrativeLock = narrativeLock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
