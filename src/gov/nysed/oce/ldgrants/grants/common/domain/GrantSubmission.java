package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class GrantSubmission {

	private Long id;
	private Long grantId;
	private Long formTypeId;
	private Long fyCode;
	private String userName;
	private String grasUser;
	private String version;
	private Date dateSubmitted;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public Long getFormTypeId() {
		return formTypeId;
	}
	public void setFormTypeId(Long formTypeId) {
		this.formTypeId = formTypeId;
	}

	public String getGrasUser() {
		return grasUser;
	}
	public void setGrasUser(String grasUser) {
		this.grasUser = grasUser;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Long getFyCode() {
		return fyCode;
	}
	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDateSubmitted() {
		return dateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
	
}
