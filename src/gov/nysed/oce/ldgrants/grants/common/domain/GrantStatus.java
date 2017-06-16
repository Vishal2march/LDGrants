package gov.nysed.oce.ldgrants.grants.common.domain;

public class GrantStatus {
	
	
	public GrantStatus(){
		this.awaitingAppr = false;
		this.lockApp = false;
		this.initialSubmitted = false;
		this.finalSubmitted = false;
		this.amendmentSubmitted = false;
		this.dasnySubmitted = false;
		this.mwbeSubmitted = false;
		this.finalYr2Submitted = false;
		this.finalYr3Submitted = false;
		this.initialApproved = false;
		this.finalApproved = false;
		this.denied = false;
		this.declined = false;
		this.finalYr2Approved = false;
		this.finalYr3Approved = false;
		this.allowSubmitInitial = false;
		this.allowSubmitFinal = false;
		this.allowSubmitFinalYr2 = false;
		this.allowSubmitFinalYr3 = false;
		this.pendingReview = false;
		this.pendingFinalReview = false;
	}

	private Boolean awaitingAppr;
	private Boolean lockApp;
	private Boolean initialSubmitted;
	private Boolean finalSubmitted;
	private Boolean amendmentSubmitted;
	private Boolean dasnySubmitted;
	private Boolean mwbeSubmitted;
	private Boolean finalYr2Submitted;
	private Boolean finalYr3Submitted;
	private Boolean initialApproved;
	private Boolean finalApproved;
	private Boolean denied;
	private Boolean declined;
	private Boolean finalYr2Approved;
	private Boolean finalYr3Approved;
	private Boolean allowSubmitInitial;
	private Boolean allowSubmitFinal;
	private Boolean allowSubmitFinalYr2;
	private Boolean allowSubmitFinalYr3;
	private Boolean pendingReview;
	private Boolean pendingFinalReview;
	private Long fyCode;
	private Long fcCode;
	private ApplicationDate applicationDate;
	
	
	
	public Boolean convertNullToFalse(Boolean databaseValue){
		return (databaseValue==null ? false : databaseValue);
	}
	
	
	
	public Boolean getAwaitingAppr() {
		return awaitingAppr;
	}
	public void setAwaitingAppr(Boolean awaitingAppr) {
		this.awaitingAppr = convertNullToFalse(awaitingAppr);
	}
	public Boolean getLockApp() {
		return lockApp;
	}
	public void setLockApp(Boolean lockApp) {
		
		this.lockApp = convertNullToFalse(lockApp);
	}
	public Boolean getInitialSubmitted() {
		return initialSubmitted;
	}
	public void setInitialSubmitted(Boolean initialSubmitted) {
		this.initialSubmitted = convertNullToFalse(initialSubmitted);
	}
	public Boolean getFinalSubmitted() {
		return finalSubmitted;
	}
	public void setFinalSubmitted(Boolean finalSubmitted) {
		this.finalSubmitted = convertNullToFalse(finalSubmitted);
	}
	public Boolean getAmendmentSubmitted() {
		return amendmentSubmitted;
	}
	public void setAmendmentSubmitted(Boolean amendmentSubmitted) {
		this.amendmentSubmitted = convertNullToFalse(amendmentSubmitted);
	}
	public Boolean getDasnySubmitted() {
		return dasnySubmitted;
	}
	public void setDasnySubmitted(Boolean dasnySubmitted) {
		this.dasnySubmitted = convertNullToFalse(dasnySubmitted);
	}
	public Boolean getMwbeSubmitted() {
		return mwbeSubmitted;
	}
	public void setMwbeSubmitted(Boolean mwbeSubmitted) {
		this.mwbeSubmitted = convertNullToFalse(mwbeSubmitted);
	}
	public Boolean getFinalYr2Submitted() {
		return finalYr2Submitted;
	}
	public void setFinalYr2Submitted(Boolean finalYr2Submitted) {
		this.finalYr2Submitted = convertNullToFalse(finalYr2Submitted);
	}
	public Boolean getFinalYr3Submitted() {
		return finalYr3Submitted;
	}
	public void setFinalYr3Submitted(Boolean finalYr3Submitted) {
		this.finalYr3Submitted = convertNullToFalse(finalYr3Submitted);
	}
	public Boolean getInitialApproved() {
		return initialApproved;
	}
	public void setInitialApproved(Boolean initialApproved) {
		this.initialApproved = convertNullToFalse(initialApproved);
	}
	public Boolean getFinalApproved() {
		return finalApproved;
	}
	public void setFinalApproved(Boolean finalApproved) {
		this.finalApproved = convertNullToFalse(finalApproved);
	}
	public Boolean getDenied() {
		return denied;
	}
	public void setDenied(Boolean denied) {
		this.denied = convertNullToFalse(denied);
	}
	public Boolean getDeclined() {
		return declined;
	}
	public void setDeclined(Boolean declined) {
		this.declined = convertNullToFalse(declined);
	}
	public Boolean getFinalYr2Approved() {
		return finalYr2Approved;
	}
	public void setFinalYr2Approved(Boolean finalYr2Approved) {
		this.finalYr2Approved = convertNullToFalse(finalYr2Approved);
	}
	public Boolean getFinalYr3Approved() {
		return finalYr3Approved;
	}
	public void setFinalYr3Approved(Boolean finalYr3Approved) {
		this.finalYr3Approved = convertNullToFalse(finalYr3Approved);
	}
	public Boolean getAllowSubmitInitial() {
		return allowSubmitInitial;
	}
	public void setAllowSubmitInitial(Boolean allowSubmitInitial) {
		this.allowSubmitInitial = convertNullToFalse(allowSubmitInitial);
	}
	public Boolean getAllowSubmitFinal() {
		return allowSubmitFinal;
	}
	public void setAllowSubmitFinal(Boolean allowSubmitFinal) {
		this.allowSubmitFinal = convertNullToFalse(allowSubmitFinal);
	}
	public Boolean getAllowSubmitFinalYr2() {
		return allowSubmitFinalYr2;
	}
	public void setAllowSubmitFinalYr2(Boolean allowSubmitFinalYr2) {
		this.allowSubmitFinalYr2 = convertNullToFalse(allowSubmitFinalYr2);
	}
	public Boolean getAllowSubmitFinalYr3() {
		return allowSubmitFinalYr3;
	}
	public void setAllowSubmitFinalYr3(Boolean allowSubmitFinalYr3) {
		this.allowSubmitFinalYr3 = convertNullToFalse(allowSubmitFinalYr3);
	}
	public Boolean getPendingReview() {
		return pendingReview;
	}
	public void setPendingReview(Boolean pendingReview) {
		this.pendingReview = convertNullToFalse(pendingReview);
	}
	public Boolean getPendingFinalReview() {
		return pendingFinalReview;
	}
	public void setPendingFinalReview(Boolean pendingFinalReview) {
		this.pendingFinalReview = convertNullToFalse(pendingFinalReview);
	}
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
	public ApplicationDate getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(ApplicationDate applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	
}
