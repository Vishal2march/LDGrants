package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class ApplicationDate {

	public ApplicationDate(){
		this.setInitialDatesAcceptible(false);
		this.setFinalDatesAcceptible(false);
		this.setAmendmentDateAcceptible(false);
	}

	private Long id;
	private Date currentDate;
	private Date availableDate;
	private Date dueDate;
	private Date finalAvailableDate;
	private Date finalDueDate;
	private Date amendmentDueDate;
	private Boolean initialDatesAcceptible;
	private Boolean finalDatesAcceptible;
	private Boolean amendmentDateAcceptible;
	private Long fyCode;
	private Long fcCode;
	
	
	public Long getFyCode() {
		return fyCode;
	}


	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}


	public Long getFcCode() {
		return fcCode;
	}


	public void setFcCode(Long fcCode) {
		this.fcCode = fcCode;
	}


	public Boolean convertNullToFalse(Boolean databaseValue){
		return (databaseValue==null ? false : databaseValue);
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getFinalAvailableDate() {
		return finalAvailableDate;
	}
	public void setFinalAvailableDate(Date finalAvailableDate) {
		this.finalAvailableDate = finalAvailableDate;
	}
	public Date getFinalDueDate() {
		return finalDueDate;
	}
	public void setFinalDueDate(Date finalDueDate) {
		this.finalDueDate = finalDueDate;
	}
	public Date getAmendmentDueDate() {
		return amendmentDueDate;
	}
	public void setAmendmentDueDate(Date amendmentDueDate) {
		this.amendmentDueDate = amendmentDueDate;
	}
	public Boolean isInitialDatesAcceptible() {
		return initialDatesAcceptible;
	}
	public void setInitialDatesAcceptible(Boolean initialDatesAcceptible) {
		this.initialDatesAcceptible = convertNullToFalse(initialDatesAcceptible);
	}
	public Boolean isFinalDatesAcceptible() {
		return finalDatesAcceptible;
	}
	public void setFinalDatesAcceptible(Boolean finalDatesAcceptible) {
		this.finalDatesAcceptible = convertNullToFalse(finalDatesAcceptible);
	}
	public Boolean isAmendmentDateAcceptible() {
		return amendmentDateAcceptible;
	}
	public void setAmendmentDateAcceptible(Boolean amendmentDateAcceptible) {
		this.amendmentDateAcceptible = convertNullToFalse(amendmentDateAcceptible);
	}
	
	
	
	
}
