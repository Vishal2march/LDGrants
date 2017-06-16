package gov.nysed.oce.ldgrants.grants.help.domain;

import java.sql.Blob;
import java.util.Date;

public class Help {

	private Long id;
	private Long fcCode;
	private Long fyCode;
	private String docType;
	private String name;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	private String modifiedBy;
	private Blob blobContent;
	private String Description;
	private String displayContent;
	private String displayLinks;
	private Date dateCreated;
	private Date dateModified;
	private Long locationId;
	
	// enum declaration..
	private LocationDescription locationDescription;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFcCode() {
		return fcCode;
	}
	public void setFcCode(Long fcCode) {
		this.fcCode = fcCode;
	}
	public Long getFyCode() {
		return fyCode;
	}
	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Blob getBlobContent() {
		return blobContent;
	}
	public void setBlobContent(Blob blobContent) {
		this.blobContent = blobContent;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getDisplayContent() {
		return displayContent;
	}
	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}
	public String getDisplayLinks() {
		return displayLinks;
	}
	public void setDisplayLinks(String displayLinks) {
		this.displayLinks = displayLinks;
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
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public LocationDescription getLocationDescription() {
		return locationDescription;
	}
	public void setLocationDescription(LocationDescription locationDescription) {
		this.locationDescription = locationDescription;
	}
	
}
