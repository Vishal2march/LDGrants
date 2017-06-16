package gov.nysed.oce.ldgrants.grants.common.domain;

public class AdminPosition {

	private Long adminPosId;
	private String fname;
	private String mi;
	private String lname;
	private String title;
	private String salut;
	private String phone;
	private String email;

	public Long getAdminPosId() {
		return adminPosId;
	}

	public void setAdminPosId(Long adminPosId) {
		this.adminPosId = adminPosId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalut() {
		return salut;
	}

	public void setSalut(String salut) {
		this.salut = salut;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
