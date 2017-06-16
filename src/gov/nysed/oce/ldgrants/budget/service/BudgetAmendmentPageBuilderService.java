package gov.nysed.oce.ldgrants.budget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nysed.oce.ldgrants.budget.domain.BudgetAmendmentPageBuilder;
import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.enumeration.ExpenditureTypeEnum;
import gov.nysed.oce.ldgrants.grants.common.service.ApprovalService;
import gov.nysed.oce.ldgrants.grants.common.service.FiscalYearService;
import gov.nysed.oce.ldgrants.grants.common.service.GrantSubmissionService;
import gov.nysed.oce.ldgrants.user.domain.User;

//@formatter:off
/******************************************************************************
 * @author  : Gabredhan Hudson
 * @Version : 1.0
 *
 * Development Environment        :  JDeveloper 12c
 * Name of the file               :  BudgetAmendmentPageBuilderService.java
 * Creation/Modification History  :
 *
 * GH   03/06/17     Created
 *
 * Description
 * Create necessary methods within service to build objects
 *          and list to be accessed on Budget Amendment jsps
 *****************************************************************************/

//@formatter:on
public class BudgetAmendmentPageBuilderService {

	Fs10RecordService fs10Service = new Fs10RecordService();

	private static final String INCREASE = "INCREASE";
	private static final String DECREASE = "DECREASE";
	private static final long BUDGET_YEAR_ONE_FISCAL_CODE = 17;

	/**
	 * get all fs10record related to grant, get Fiscal Year of all budget years,
	 * generate expenditureType List by Fund Code
	 * 
	 * @param BudgetAmendmentPageBuilder
	 * @return BudgetAmendmentPageBuilder
	 */
	public BudgetAmendmentPageBuilder generatePageBuilderObjects(BudgetAmendmentPageBuilder pageBuilder) {

		ExpenditureTypeService expTypeService = new ExpenditureTypeService();

		List<Fs10Record> tempFs10RecordList = new ArrayList<Fs10Record>();
		List<Fs10Record> budgetYearOneList = new ArrayList<Fs10Record>();
		List<Fs10Record> budgetYearTwoList = new ArrayList<Fs10Record>();
		List<Fs10Record> budgetYearThreeList = new ArrayList<Fs10Record>();

		try {
			// Select All records from FS10_Record table, iterate through list
			// and save all fs10 records related to grantId

			for (Fs10Record fs10Record : fs10Service.selectAll()) {

				// if grant ids are equal add record to temp list
				if (fs10Record.getGraId().longValue() == pageBuilder.getGrantId()) {

					// if expense id null pass to checkExpenditureId
					if (fs10Record.getExpenditureTypeId() == null && fs10Record.getExpCode() != null) {

						// get expenditure type based on expense code
						ExpenditureTypeEnum expenditureType = ExpenditureTypeEnum
								.getExpenditureType(fs10Record.getExpCode().intValue());

						// set expenditure type id for FS10 record
						fs10Record.setExpenditureTypeId(expenditureType.getExpenditureTypeId());

					}

					// check if amount is increase or decrease
					if (fs10Record.getAmountIncr() != null && fs10Record.getAmountIncr().longValue() != 0) {
						fs10Record.setAdjustment(INCREASE);
					} else {
						fs10Record.setAdjustment(DECREASE);
					}

					// Populate Budget year
					if (fs10Record.getFyCode() != null) {

						if (fs10Record.getFyCode().longValue() == BUDGET_YEAR_ONE_FISCAL_CODE) {
							budgetYearOneList.add(fs10Record);
						} else if (fs10Record.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 1)) {
							budgetYearTwoList.add(fs10Record);
						} else if (fs10Record.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 2)) {
							budgetYearThreeList.add(fs10Record);
						} else {

						}

					} else {
						// add fs10 record to templist
						tempFs10RecordList.add(fs10Record);
					}

				}
			}

			// add Fs10ARecordList
			pageBuilder.setFs10RecordList((tempFs10RecordList));

			// add Budget year Lists
			pageBuilder.setBudgetYearOneList(budgetYearOneList);

			pageBuilder.setBudgetYearTwoList(budgetYearTwoList);

			pageBuilder.setBudgetYearThreeList(budgetYearThreeList);

			// generate Expenditure List based on Fundcode
			pageBuilder.setExpenditureTypeList(expTypeService.searchByFundCode(pageBuilder.getFundCode()));

			// generate Fiscal Year List
			List<FiscalYear> tempFYList = getFiscalYear();
			pageBuilder.setFiscalYearList(tempFYList);
			/**
			 * Switch to jquery // get Approvals List List
			 * <Approval> tempApprovalList =
			 * getBudgetAmendmentApprovalStatus(pageBuilder.getGrantId());
			 * pageBuilder.setApprovalList(tempApprovalList);
			 * 
			 * // Get Grant Submissions List List
			 * <GrantSubmission> tempGrantSubmissionList =
			 * getBudgetAmendmentSubmissionStatus(pageBuilder.getGrantId());
			 * pageBuilder.setGrantSubmissionList(tempGrantSubmissionList);
			 **/
		} catch (Exception ex) {
			System.err.println("FAILED: CLASS:BudgetAmendmentSummaryService METHOD:generatePageBuilderObjects() : "
					+ ex.toString());
		}
		// return pageBuilder
		return pageBuilder;
	}

	/**
	 * Pass Budget Year 1 Fiscal code and return list of FiscalYear objects with
	 * fyCodes from Budget Year 1 to Budget Year 3 FY code
	 * 
	 * @param
	 * @return List<FiscalYear>
	 */

	public List<FiscalYear> getFiscalYear() {

		List<FiscalYear> fiscalYearList = new ArrayList<FiscalYear>();
		FiscalYearService fiscalYearService = new FiscalYearService();
		// Get Fiscal Year List for Budget Year 1, 2, and 3
		for (long i = BUDGET_YEAR_ONE_FISCAL_CODE; i <= (BUDGET_YEAR_ONE_FISCAL_CODE + 2); i++) {
			fiscalYearList.add(fiscalYearService.select(i));
		}
		return fiscalYearList;
	}

	/**
	 * Retrieve Approval List related to grant
	 * 
	 * @param
	 * @return List<FiscalYear>
	 */
	public List<Approval> getBudgetAmendmentApprovalStatus(Long grantId) {
		ApprovalService approvalService = new ApprovalService();
		List<Approval> grantApprovalList = approvalService.searchApprovalsByGrant(grantId);

		return grantApprovalList;
	}

	/**
	 * Retrieve Submissions List related to grant
	 * 
	 * @param
	 * @return List<GrantSubmission>
	 */
	public List<GrantSubmission> getBudgetAmendmentSubmissionStatus(Long grantId) {
		GrantSubmissionService grantSubmissionService = new GrantSubmissionService();
		List<GrantSubmission> grantSubmissionList = grantSubmissionService.searchGrantSubmissionsByGrantId(grantId);

		return grantSubmissionList;
	}

	/**
	 * Retrieve Approval map list
	 * 
	 * @param grantId
	 * @return Map<String, Boolean> fyCode, boolean
	 */
	public Map<String, Boolean> isApproved(Long grantId, Long fmtId) {

		List<Approval> approvalList = new ArrayList<Approval>();
		Map<String, Boolean> tempApprovalMap = new HashMap<String, Boolean>();

		approvalList = getBudgetAmendmentApprovalStatus(grantId);
		// Check if FY is in list, if so set map to true
		for (Approval approval : approvalList) {

			if (approval.getFyCode() != null && approval.getApprovalType().equalsIgnoreCase("Approve")
					&& approval.getFormTypeId().longValue() == fmtId) {
				if (approval.getFyCode().longValue() == BUDGET_YEAR_ONE_FISCAL_CODE) {
					tempApprovalMap.put("BudgetYearOne", true);
				} else if (approval.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 1)) {
					tempApprovalMap.put("BudgetYearTwo", true);
				} else if (approval.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 2)) {
					tempApprovalMap.put("BudgetYearThree", true);
				}
			}
		}
		return tempApprovalMap;
	}

	/**
	 * Retrieve submission map list
	 * 
	 * @param grantId
	 * @return Map<String, Boolean> fyCode, boolean
	 */
	public Map<String, Boolean> isSubmitted(Long grantId, Long fmtId) {

		List<GrantSubmission> submissionList = new ArrayList<GrantSubmission>();
		Map<String, Boolean> tempSubmissionMap = new HashMap<String, Boolean>();

		submissionList = getBudgetAmendmentSubmissionStatus(grantId);
		// Check if FY is in list, if so set map to true
		for (GrantSubmission submission : submissionList) {

			if (submission.getFyCode() != null && submission.getFormTypeId().longValue() == fmtId) {	
				if (submission.getFyCode().longValue() == BUDGET_YEAR_ONE_FISCAL_CODE) {
					tempSubmissionMap.put("BudgetYearOne", true);
				} else if (submission.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 1)) {
					tempSubmissionMap.put("BudgetYearTwo", true);
				} else if (submission.getFyCode().longValue() == (BUDGET_YEAR_ONE_FISCAL_CODE + 2)) {
					tempSubmissionMap.put("BudgetYearThree", true);

				}
			}
		}
		return tempSubmissionMap;
	}

	/**
	 * Save all records in List to FS10Record table
	 * 
	 * @param List<Fs10Record>,
	 *            User
	 * @return
	 */
	public void saveAllRecords(List<Fs10Record> fs10RecordList, User user) {

		for (Fs10Record fs10Record : fs10RecordList) {

			// need to check if amount is an increase or decrease
			//
			if (fs10Record.getAdjustment().equals(DECREASE)) {
				fs10Record.setAmountDecr(fs10Record.getAmountIncr());
				fs10Record.setAmountIncr(null);
			}
			fs10Service.update(fs10Record, user);
		}
	}

	/**
	 * Adds new record to FS10Record table
	 * 
	 * @param grantid
	 *            and current User
	 * 
	 * @return
	 */
	public void addNewRcord(Long grantId, User user) {

		Fs10Record fs10Record = new Fs10Record();

		// Add grant ID to Fs10Record Object
		fs10Record.setGraId(grantId);

		// Create new Fs10Record
		fs10Service.insert(fs10Record, user);

	}

}
