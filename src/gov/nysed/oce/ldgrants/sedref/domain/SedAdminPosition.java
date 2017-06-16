package gov.nysed.oce.ldgrants.sedref.domain;

import java.util.ArrayList;
import java.util.List;

public class SedAdminPosition {

	private Long adminPosId;
	private String title;
	private String fname;
	private String mi;
	private String lname;
	private Long addrId;
	private Long adminPostTypeCode;
	private List<SedContactInfo> contactInfos = new ArrayList<SedContactInfo>();
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public Long getAddrId() {
		return addrId;
	}
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	public Long getAdminPostTypeCode() {
		return adminPostTypeCode;
	}
	public void setAdminPostTypeCode(Long adminPostTypeCode) {
		this.adminPostTypeCode = adminPostTypeCode;
	}
	public Long getAdminPosId() {
		return adminPosId;
	}
	public void setAdminPosId(Long adminPosId) {
		this.adminPosId = adminPosId;
	}
	public List<SedContactInfo> getContactInfos() {
		return contactInfos;
	}
	public void setContactInfos(List<SedContactInfo> contactInfos) {
		this.contactInfos = contactInfos;
	}
	
}
