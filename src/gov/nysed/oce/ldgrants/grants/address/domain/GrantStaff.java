package gov.nysed.oce.ldgrants.grants.address.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrantStaff {

	
	String active;
	String createdBy;
	String fname;
	String lname;
	String mname;
	String modifiedBy;
	String salutation;
	String title;
	Date dateCreated;
	Date dateModified;
	Long graId;
	Long id;
	Long st1Id;
	
	private StaffType staffType;
	private List<Contact> contacts = new ArrayList<Contact>();
	private Contact email;
	
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Long getGraId() {
		return graId;
	}
	public void setGraId(Long graId) {
		this.graId = graId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSt1Id() {
		return st1Id;
	}
	public void setSt1Id(Long st1Id) {
		this.st1Id = st1Id;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public Contact getEmail() {
		return email;
	}
	public void setEmail(Contact email) {
		this.email = email;
	}
	public StaffType getStaffType() {
		return staffType;
	}
	public void setStaffType(StaffType staffType) {
		this.staffType = staffType;
	}
	
	
}
