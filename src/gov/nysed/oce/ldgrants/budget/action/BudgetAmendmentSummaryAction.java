package gov.nysed.oce.ldgrants.budget.action;

import java.util.List;
import java.util.Map;

import gov.nysed.oce.ldgrants.budget.domain.BudgetAmendmentPageBuilder;
import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.budget.service.BudgetAmendmentPageBuilderService;
import gov.nysed.oce.ldgrants.budget.service.Fs10RecordService;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.ExpenditureType;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class BudgetAmendmentSummaryAction extends LDGrantSessionManager {

	private Long fsId;
	private Long fmtId;

	private List<Fs10Record> fs10RecordList;
	private List<Fs10Record> budgetYearOneList;
	private List<Fs10Record> budgetYearTwoList;
	private List<Fs10Record> budgetYearThreeList;

	private List<ExpenditureType> expenditureTypeList;

	private List<Approval> amendmentApprovalStatusList;
	private List<GrantSubmission> grantSubmissionList;
	private List<FiscalYear> fiscalYearList;

	private Map<String, Boolean> isAmendmentSubmitted;
	private Map<String, Boolean> isAmendmentApproved;

	private Boolean ad; // IS ADMIN
	private Boolean fs10ALocked;

	private BudgetAmendmentPageBuilder budgetAmendmentPageBuilder;

	private static final String ADMIN_PAGE = "adminpage";

	BudgetAmendmentPageBuilderService budgetAmendService = new BudgetAmendmentPageBuilderService();
	Fs10RecordService fsRecordService = new Fs10RecordService();

	// Method gets a list of all FS10 Records related to current Grant and all
	// Expenditure Types related to current fund program
	public String buildBudgetAmendmentPage() {

		try {
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();

			if (ad != null && ad == true) { // Request coming from admin page do
											// this
				ldSession.clearFormTypeID();
				ldSession.setFormTypeID(fmtId);
				breadcrumbs.createBreadcrumb(BreadcrumbPage.BUDGET_AMENDMENT_SUMMARY,
						"Budget Amendment Summary (ADMIN)");

			} else {
				// Set Bread crumbs
				breadcrumbs.createBreadcrumb(BreadcrumbPage.BUDGET_AMENDMENT_SUMMARY, "Budget Amendment Summary");

			}
			
			// get grant id
			String grantId = ldSession.getGrantId();

			// get basic grant info
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));
             
			ldSession.setGrant(grant);
			
			// get grant id from session and set grant id variable for page
			// builder object, get FundCode from Session
			budgetAmendmentPageBuilder.setGrantId(ldSession.getGrant().getId());
			budgetAmendmentPageBuilder.setFundCode(ldSession.getGrant().getFcCode());

			// generate page builder objects
			budgetAmendmentPageBuilder = budgetAmendService.generatePageBuilderObjects(budgetAmendmentPageBuilder);
			// ALL FSRecords get stored here that doesn't have an associated
			// FyCode
			fs10RecordList = budgetAmendmentPageBuilder.getFs10RecordList();
			// Separate each Budget year list by Fiscal code
			budgetYearOneList = budgetAmendmentPageBuilder.getBudgetYearOneList();
			budgetYearTwoList = budgetAmendmentPageBuilder.getBudgetYearTwoList();
			budgetYearThreeList = budgetAmendmentPageBuilder.getBudgetYearThreeList();

			// generate Expenditure List based on Fundcode
			expenditureTypeList = budgetAmendmentPageBuilder.getExpenditureTypeList();
			/**
			 * MOVED TO AJAX // GET APPROVAL STATUS OF BUDGET AMENDMENT
			 * amendmentApprovalStatusList =
			 * budgetAmendmentPageBuilder.getApprovalList();
			 * 
			 * // GET SUBMISSION STATUS OF BUDGET AMENDMENT grantSubmissionList
			 * = budgetAmendmentPageBuilder.getGrantSubmissionList();
			 **/
			// Get Fiscal Year List
			fiscalYearList = budgetAmendmentPageBuilder.getFiscalYearList();
			// Get Application Lock value from session
			fs10ALocked = ldSession.getGrant().getFs10aLocked();

		} catch (Exception ex) {
			System.err.println(
					"FAILED: CLASS:BudgetAmendmentSummaryAction METHOD:buildBudgetAmendmentPage() : " + ex.toString());
		}
		if (ad != null && ad == true) {
			return ADMIN_PAGE;
		} else {
			return SUCCESS;
		}

	}

	public String saveFs10aRecords() {

		try {
			if (fs10RecordList.isEmpty() != true) {
				budgetAmendService.saveAllRecords(fs10RecordList, ldSession.getUser());
			}
		} catch (Exception ex) {
			System.err.println("FAILED: CLASS:BudgetAmendmentSummaryAction saveFs10aRecords() : " + ex.toString());
		}
		return SUCCESS;
	}

	// Method adds new record to FS10Record table and returns new Fs10recordList
	// with newly created record

	public String addNewRecord() {

		try {
			// pass current grantID and User to service
			budgetAmendService.addNewRcord(ldSession.getGrant().getId(), ldSession.getUser());

		} catch (Exception ex) {
			System.err.println("FAILED: CLASS:BudgetAmendmentSummaryAction METHOD:addNewRecord() : " + ex.toString());
		}
		return SUCCESS;
	}

	// Method deletes All
	public String deleteRecord() {

		try {

			fsRecordService.delete(fsId);

		} catch (Exception ex) {
			System.err.println("FAILED: CLASS:BudgetAmendmentSummaryAction METHOD:deleteRecord() : " + ex.toString());
		}
		return SUCCESS;
	}

	public String isBudgetAmendmentStatus() {

		try {
			long grantId = ldSession.getGrant().getId();
			long fmtId = ldSession.getFormTypeId(); // Get Form Type ID from
													// session
			// populate hashmap list and return to Ajax
			isAmendmentSubmitted = budgetAmendService.isSubmitted(grantId, fmtId);
			isAmendmentApproved = budgetAmendService.isApproved(grantId, fmtId);

		} catch (Exception ex) {
			System.err.println(
					"FAILED: CLASS:BudgetAmendmentSummaryAction METHOD:isBudgetAmendmentStatus() : " + ex.toString());
		}
		return SUCCESS;
	}

	public String lockAmendment() {

		Fs10RecordService fsRecordService = new Fs10RecordService();
		try {

			// LOCK Grant Amendment Summary form
			fsRecordService.updateFS10ALocked(ldSession.getGrant().getId(), ldSession.getUser());

		} catch (Exception ex) {

		}
		return SUCCESS;
	}

	public String unlockAmendment() {

		Fs10RecordService fsRecordService = new Fs10RecordService();

		try {
			//Unlock Grant Amendment Summary Page
			fsRecordService.updateFS10AUnlocked(ldSession.getGrant().getId(), ldSession.getUser());
		} catch (Exception ex) {

		}
		return SUCCESS;
	}

	/** constructor **/
	public BudgetAmendmentSummaryAction() {
		this.budgetAmendmentPageBuilder = new BudgetAmendmentPageBuilder();
	}

	/* GENERATE ACCESSORS */

	public Long getFsId() {
		return fsId;
	}

	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}

	public BudgetAmendmentPageBuilder getBudgetAmendmentPageBuilder() {
		return budgetAmendmentPageBuilder;
	}

	public void setBudgetAmendmentPageBuilder(BudgetAmendmentPageBuilder budgetAmendmentPageBuilder) {
		this.budgetAmendmentPageBuilder = budgetAmendmentPageBuilder;
	}

	public List<ExpenditureType> getExpenditureTypeList() {
		return expenditureTypeList;
	}

	public void setExpenditureTypeList(List<ExpenditureType> expenditureTypeList) {
		this.expenditureTypeList = expenditureTypeList;
	}

	public List<Fs10Record> getFs10RecordList() {
		return fs10RecordList;
	}

	public void setFs10RecordList(List<Fs10Record> fs10RecordList) {
		this.fs10RecordList = fs10RecordList;
	}

	public List<Approval> getAmendmentApprovalStatusList() {
		return amendmentApprovalStatusList;
	}

	public void setAmendmentApprovalStatusList(List<Approval> amendmentApprovalStatusList) {
		this.amendmentApprovalStatusList = amendmentApprovalStatusList;
	}

	public List<GrantSubmission> getGrantSubmissionList() {
		return grantSubmissionList;
	}

	public void setGrantSubmissionList(List<GrantSubmission> grantSubmissionList) {
		this.grantSubmissionList = grantSubmissionList;
	}

	public List<FiscalYear> getFiscalYearList() {
		return fiscalYearList;
	}

	public void setFiscalYearList(List<FiscalYear> fiscalYearList) {
		this.fiscalYearList = fiscalYearList;
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

	public Map<String, Boolean> getIsAmendmentSubmitted() {
		return isAmendmentSubmitted;
	}

	public void setIsAmendmentSubmitted(Map<String, Boolean> isAmendmentSubmitted) {
		this.isAmendmentSubmitted = isAmendmentSubmitted;
	}

	public Map<String, Boolean> getIsAmendmentApproved() {
		return isAmendmentApproved;
	}

	public void setIsAmendmentApproved(Map<String, Boolean> isAmendmentApproved) {
		this.isAmendmentApproved = isAmendmentApproved;
	}

	public Boolean getFs10ALocked() {
		return fs10ALocked;
	}

	public void setFs10ALocked(Boolean fs10aLocked) {
		fs10ALocked = fs10aLocked;
	}

	public Long getFmtId() {
		return fmtId;
	}

	public void setFmtId(Long fmtId) {
		this.fmtId = fmtId;
	}

	public Boolean getAd() {
		return ad;
	}

	public void setAd(Boolean ad) {
		this.ad = ad;
	}

}
