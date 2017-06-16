package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;

public class Grant {
	
	private Long id;
	private Long grantid;
	private String name;
	private Long instId;
	private String sedcode;
	private Long fyCode;
	private Long fcCode;
	private Long projSeqNum;
	private String fiscalYear;
	private String programName;
	private String contractNum;
	private Long ldacAppropriationAmount;
	private Boolean fs10aLocked;
	private Long prmId;
	private GrantSubmission grantSubmission;
	private Institution institution;
	private Approval approval;
	private ProjectManager projectManager;
	private List<GrantStaff> grantStaffs = new ArrayList<GrantStaff>();
	private List<SedAdminPosition> adminPositions = new ArrayList<SedAdminPosition>();
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	public String getSedcode() {
		return sedcode;
	}
	public void setSedcode(String sedcode) {
		this.sedcode = sedcode;
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
	public Long getProjSeqNum() {
		return projSeqNum;
	}
	public void setProjSeqNum(Long projSeqNum) {
		this.projSeqNum = projSeqNum;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public Long getLdacAppropriationAmount() {
		return ldacAppropriationAmount;
	}
	public void setLdacAppropriationAmount(Long ldacAppropriationAmount) {
		this.ldacAppropriationAmount = ldacAppropriationAmount;
	}
	public GrantSubmission getGrantSubmission() {
		return grantSubmission;
	}
	public void setGrantSubmission(GrantSubmission grantSubmission) {
		this.grantSubmission = grantSubmission;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public Approval getApproval() {
		return approval;
	}
	public void setApproval(Approval approval) {
		this.approval = approval;
	}
	public Boolean getFs10aLocked() {
		return fs10aLocked;
	}
	public void setFs10aLocked(Boolean fs10aLocked) {
		this.fs10aLocked = fs10aLocked;
	}
	public Long getGrantid() {
		return grantid;
	}
	public void setGrantid(Long grantid) {
		this.grantid = grantid;
	}
	public Long getPrmId() {
		return prmId;
	}
	public void setPrmId(Long prmId) {
		this.prmId = prmId;
	}
	public ProjectManager getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	public List<GrantStaff> getGrantStaffs() {
		return grantStaffs;
	}
	public void setGrantStaffs(List<GrantStaff> grantStaffs) {
		this.grantStaffs = grantStaffs;
	}
	public List<SedAdminPosition> getAdminPositions() {
		return adminPositions;
	}
	public void setAdminPositions(List<SedAdminPosition> adminPositions) {
		this.adminPositions = adminPositions;
	}

	

}
