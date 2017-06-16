package gov.nysed.oce.ldgrants.grants.address.domain;

public class Contact {
	
	private Long id;
	private String contactValue;
	private Boolean isActive;
	private Long prmId;
	private Long revId;
	private Long gsId;
	private Long contactTypeCode;
	private ContactType contactType;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContactValue() {
		return contactValue;
	}
	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getPrmId() {
		return prmId;
	}
	public void setPrmId(Long prmId) {
		this.prmId = prmId;
	}
	public Long getRevId() {
		return revId;
	}
	public void setRevId(Long revId) {
		this.revId = revId;
	}
	public Long getGsId() {
		return gsId;
	}
	public void setGsId(Long gsId) {
		this.gsId = gsId;
	}
	public Long getContactTypeCode() {
		return contactTypeCode;
	}
	public void setContactTypeCode(Long contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	
	

}
