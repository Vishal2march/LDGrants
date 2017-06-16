package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class ExpenditureType {
	
	private Long id;
	
	private String ExpenditureName;
	private String createdBy;
	private String modifiedBy;
	
	private Date dateCreated;
	private Date dateModified;
	
	public ExpenditureType() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenditureName() {
		return ExpenditureName;
	}

	public void setExpenditureName(String expenditureName) {
		ExpenditureName = expenditureName;
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
