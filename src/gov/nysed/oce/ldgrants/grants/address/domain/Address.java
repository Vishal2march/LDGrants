package gov.nysed.oce.ldgrants.grants.address.domain;

import java.util.Date;

public class Address {

	Long id;
	String addrLine1;
	Long addrTypeCode;
	Long bldgId;
	String city;
	String county;
	String createdBy;
	String modifiedBy;
	String state;
	String zipcode;
	Date dateCreated;
	Date dateModified;
	Long revId;
	Long prmId;
	Long venId;
	String isActive;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddrLine1() {
		return addrLine1;
	}
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}
	public Long getAddrTypeCode() {
		return addrTypeCode;
	}
	public void setAddrTypeCode(Long addrTypeCode) {
		this.addrTypeCode = addrTypeCode;
	}
	public Long getBldgId() {
		return bldgId;
	}
	public void setBldgId(Long bldgId) {
		this.bldgId = bldgId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public Long getRevId() {
		return revId;
	}
	public void setRevId(Long revId) {
		this.revId = revId;
	}
	public Long getPrmId() {
		return prmId;
	}
	public void setPrmId(Long prmId) {
		this.prmId = prmId;
	}
	public Long getVenId() {
		return venId;
	}
	public void setVenId(Long venId) {
		this.venId = venId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
}
