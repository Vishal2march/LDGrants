package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class NarrativeType {

	private Long narrativeTypeId;
	private String narrativeName;
	private String displayName;
	private String displayInstruction;
	private Long sortOrder;
	private Long categoryTypeId;
	
	private ProjectNarrative answer;
	
	
	
	
	private Long id;
	
	private String createdBy;
	private String modifiedBy;
	private String status;

	private Date dateCreated;
	private Date dateModified;


	private Boolean FormButton;
	
	
	public Long getNarrativeTypeId() {
		return narrativeTypeId;
	}
	public void setNarrativeTypeId(Long narrativeTypeId) {
		this.narrativeTypeId = narrativeTypeId;
	}
	public String getNarrativeName() {
		return narrativeName;
	}
	public void setNarrativeName(String narrativeName) {
		this.narrativeName = narrativeName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayInstruction() {
		return displayInstruction;
	}
	public void setDisplayInstruction(String displayInstruction) {
		this.displayInstruction = displayInstruction;
	}
	public Long getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Long getCategoryTypeId() {
		return categoryTypeId;
	}
	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	public ProjectNarrative getAnswer() {
		return answer;
	}
	public void setAnswer(ProjectNarrative answer) {
		this.answer = answer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Boolean getFormButton() {
		return FormButton;
	}
	public void setFormButton(Boolean formButton) {
		FormButton = formButton;
	}
}
