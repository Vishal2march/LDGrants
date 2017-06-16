package gov.nysed.oce.ldgrants.budget.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.ExpenditureType;

public class BudgetAmendmentPageBuilder {

	private Long grantId;
	private Long fundCode;
	
	private List<Fs10Record> fs10RecordList; // Fs10A Records related to Grant
	private List<FiscalYear> fiscalYearList; // LIST of Budget Years Fiscal Years
	private List<ExpenditureType> expenditureTypeList; //Expenditure Type List based on FUNDCODE
    private List<Approval> approvalList; //Should hold Approval status for each budget year
    private List<GrantSubmission> grantSubmissionList; // stores submission status for each budget year related to grant
    private List<Fs10Record> budgetYearOneList; // All Fs10A Records Budget Year 1
    private List<Fs10Record> budgetYearTwoList; // All Fs10A Records Budget Year 2
    private List<Fs10Record> budgetYearThreeList; // All Fs10A Records Budget Year 3
    
	public BudgetAmendmentPageBuilder() {
		this.fs10RecordList = new ArrayList<Fs10Record>();
	}

	public Long getGrantId() {
		return grantId;
	}

	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}

	public List<Fs10Record> getFs10RecordList() {
		return fs10RecordList;
	}

	public void setFs10RecordList(List<Fs10Record> fs10RecordList) {
		this.fs10RecordList = fs10RecordList;
	}

	public List<FiscalYear> getFiscalYearList() {
		return fiscalYearList;
	}

	public void setFiscalYearList(List<FiscalYear> fiscalYearList) {
		this.fiscalYearList = fiscalYearList;
	}

	public Long getFundCode() {
		return fundCode;
	}

	public void setFundCode(Long fundCode) {
		this.fundCode = fundCode;
	}

	public List<ExpenditureType> getExpenditureTypeList() {
		return expenditureTypeList;
	}

	public void setExpenditureTypeList(List<ExpenditureType> expenditureTypeList) {
		this.expenditureTypeList = expenditureTypeList;
	}

	public List<Approval> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<Approval> approvalList) {
		this.approvalList = approvalList;
	}

	public List<GrantSubmission> getGrantSubmissionList() {
		return grantSubmissionList;
	}

	public void setGrantSubmissionList(List<GrantSubmission> grantSubmissionList) {
		this.grantSubmissionList = grantSubmissionList;
	}

	public List<Fs10Record> getBudgetYearOneList() {
		return budgetYearOneList;
	}

	public void setBudgetYearOneList(List<Fs10Record> budgetYearOneList) {
		this.budgetYearOneList = budgetYearOneList;
	}

	public List<Fs10Record> getBudgetYearTwoList() {
		return budgetYearTwoList;
	}

	public void setBudgetYearTwoList(List<Fs10Record> budgetYearTwoList) {
		this.budgetYearTwoList = budgetYearTwoList;
	}

	public List<Fs10Record> getBudgetYearThreeList() {
		return budgetYearThreeList;
	}

	public void setBudgetYearThreeList(List<Fs10Record> budgetYearThreeList) {
		this.budgetYearThreeList = budgetYearThreeList;
	}
	
	

}
