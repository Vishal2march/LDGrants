package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.ArrayList;
import java.util.List;


public class AdminLandingPage {

	
	 private List<Grant> submittedList = new ArrayList<Grant>();
	 private List<Grant> year1Submitted = new ArrayList<Grant>();
	 private List<Grant> year1Approved = new ArrayList<Grant>();
	 private List<Grant> year2Submitted = new ArrayList<Grant>();
	 private List<Grant> year2Approved = new ArrayList<Grant>();
	 private List<Grant> year3Submitted = new ArrayList<Grant>();
	 private List<Grant> year3Approved = new ArrayList<Grant>();	
	 private List<Grant> approvedList = new ArrayList<Grant>();
	 
	 
	 
	public List<Grant> getSubmittedList() {
		return submittedList;
	}
	public void setSubmittedList(List<Grant> submittedList) {
		this.submittedList = submittedList;
	}
	public List<Grant> getYear1Submitted() {
		return year1Submitted;
	}
	public void setYear1Submitted(List<Grant> year1Submitted) {
		this.year1Submitted = year1Submitted;
	}
	public List<Grant> getYear1Approved() {
		return year1Approved;
	}
	public void setYear1Approved(List<Grant> year1Approved) {
		this.year1Approved = year1Approved;
	}
	public List<Grant> getYear2Submitted() {
		return year2Submitted;
	}
	public void setYear2Submitted(List<Grant> year2Submitted) {
		this.year2Submitted = year2Submitted;
	}
	public List<Grant> getYear2Approved() {
		return year2Approved;
	}
	public void setYear2Approved(List<Grant> year2Approved) {
		this.year2Approved = year2Approved;
	}
	public List<Grant> getYear3Submitted() {
		return year3Submitted;
	}
	public void setYear3Submitted(List<Grant> year3Submitted) {
		this.year3Submitted = year3Submitted;
	}
	public List<Grant> getYear3Approved() {
		return year3Approved;
	}
	public void setYear3Approved(List<Grant> year3Approved) {
		this.year3Approved = year3Approved;
	}
	public List<Grant> getApprovedList() {
		return approvedList;
	}
	public void setApprovedList(List<Grant> approvedList) {
		this.approvedList = approvedList;
	}
}
