package gov.nysed.oce.ldgrants.user.domain;

public class User {
	
	//3 levels of applicant permissions: read/edit/submit.  
	private static String readAccess = "READ";
	private static String editAccess = "EDIT";
	private static String submitAccess = "SUBMIT";
	
	private Long id;
	private String firstName;
	private String lastName;
	private String lanId;
	private String email;
	private String userId;
	private String adultLiteracyAccess;
	private String familyLiteracyAccess;
	private String adultLiteracyAdminAccess;
	private String familyLiteracyAdminAccess;
	private String createdBy;
	private String modifiedBy;
	
			
    private String role;
    
	private boolean authenticated;
	private boolean admin;
	
	public User(){
		//Default to unauthenticated, and no admin role 
		this.authenticated = false;
		this.admin = false;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanid) {
		this.lanId = lanid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public String getAdultLiteracyAccess() {
		return adultLiteracyAccess;
	}
	public String getRole() {
		return role;
	}

	public void setAdultLiteracyAccess(String adultLiteracyAccess) {
		this.adultLiteracyAccess = adultLiteracyAccess;
	}

	public String getFamilyLiteracyAccess() {
		return familyLiteracyAccess;
	}

	public void setFamilyLiteracyAccess(String familyLiteracyAccess) {
		this.familyLiteracyAccess = familyLiteracyAccess;
	}

	public static String getReadAccess() {
		return readAccess;
	}

	public static void setReadAccess(String readAccess) {
		User.readAccess = readAccess;
	}

	public static String getEditAccess() {
		return editAccess;
	}

	public static void setEditAccess(String editAccess) {
		User.editAccess = editAccess;
	}

	public static String getSubmitAccess() {
		return submitAccess;
	}

	public static void setSubmitAccess(String submitAccess) {
		User.submitAccess = submitAccess;
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

	public String getAdultLiteracyAdminAccess() {
		return adultLiteracyAdminAccess;
	}

	public void setAdultLiteracyAdminAccess(String adultLiteracyAdminAccess) {
		this.adultLiteracyAdminAccess = adultLiteracyAdminAccess;
	}

	public String getFamilyLiteracyAdminAccess() {
		return familyLiteracyAdminAccess;
	}

	public void setFamilyLiteracyAdminAccess(String familyLiteracyAdminAccess) {
		this.familyLiteracyAdminAccess = familyLiteracyAdminAccess;
	}

	

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}



}
