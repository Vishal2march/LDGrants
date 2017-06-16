package gov.nysed.oce.ldgrants.sedref.domain;

public class SedContactInfo {

	private Long contactId;
	private String contactValue;
	private Long contactTypeCode;
	private Long adminPosId;
	private Long instId;
	
	
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getContactValue() {
		return contactValue;
	}
	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}
	public Long getContactTypeCode() {
		return contactTypeCode;
	}
	public void setContactTypeCode(Long contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}
	public Long getAdminPosId() {
		return adminPosId;
	}
	public void setAdminPosId(Long adminPosId) {
		this.adminPosId = adminPosId;
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	
}
