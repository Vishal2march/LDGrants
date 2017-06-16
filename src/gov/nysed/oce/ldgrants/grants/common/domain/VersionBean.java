package gov.nysed.oce.ldgrants.grants.common.domain;


public class VersionBean {

private Long id;
private Long narrativeId;
private Long fyBeginDate;
private Long fyEndDate;
private Long formTypeId;
private Long fundCode;

private Long sortOrder;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}



public Long getNarrativeId() {
	return narrativeId;
}

public void setNarrativeId(Long narrativeId) {
	this.narrativeId = narrativeId;
}

public Long getFyBeginDate() {
	return fyBeginDate;
}

public void setFyBeginDate(Long fyBeginDate) {
	this.fyBeginDate = fyBeginDate;
}

public Long getFyEndDate() {
	return fyEndDate;
}

public void setFyEndDate(Long fyEndDate) {
	this.fyEndDate = fyEndDate;
}

public Long getFormTypeId() {
	return formTypeId;
}

public void setFormTypeId(Long formTypeId) {
	this.formTypeId = formTypeId;
}

public Long getFundCode() {
	return fundCode;
}

public void setFundCode(Long fundCode) {
	this.fundCode = fundCode;
}

public Long getSortOrder() {
	return sortOrder;
}

public void setSortOrder(Long sortOrder) {
	this.sortOrder = sortOrder;
}


}
