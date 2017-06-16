package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.Date;

public class Authorization {

	private Long id;
	private Long grantId;
	private String name;
	private String title;
	private String version;
	private String authorizingUser;
	private Date authorizationDate;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthorizingUser() {
		return authorizingUser;
	}
	public void setAuthorizingUser(String authorizingUser) {
		this.authorizingUser = authorizingUser;
	}
	public Date getAuthorizationDate() {
		return authorizationDate;
	}
	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}
	
	
}
