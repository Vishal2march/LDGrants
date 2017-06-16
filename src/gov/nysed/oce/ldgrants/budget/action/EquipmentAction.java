package gov.nysed.oce.ldgrants.budget.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import construction.AllocationYearBean;
import gov.nysed.oce.ldgrants.budget.domain.Equipment;
import gov.nysed.oce.ldgrants.budget.service.EquipmentService;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import literacy.BudgetSummaryDbBean;
import mypackage.BudgetDBHandler;

public class EquipmentAction extends LDGrantSessionManager {

	HashMap<Object, Object> JSONROOT;// uses with jtable/json output
	private Equipment equipment = new Equipment();
	private List<Equipment> allEquipment;// all equipment records
	private AllocationYearBean allocationYearBean = new AllocationYearBean();// final
																				// approp
	private int initialCategoryTotal;
	private GrantStatus grantStatus = new GrantStatus();
	private static final String ADULT_LITERACY = "adultlit";
	private static final String FAMILY_LITERACY = "familylit";
	
	

	public HashMap<Object, Object> getJSONROOT() {
		return JSONROOT;
	}

	public void setJSONROOT(HashMap<Object, Object> jSONROOT) {
		JSONROOT = jSONROOT;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public List<Equipment> getAllEquipment() {
		return allEquipment;
	}

	public void setAllEquipment(List<Equipment> allEquipment) {
		this.allEquipment = allEquipment;
	}

	public AllocationYearBean getAllocationYearBean() {
		return allocationYearBean;
	}

	public void setAllocationYearBean(AllocationYearBean allocationYearBean) {
		this.allocationYearBean = allocationYearBean;
	}

	public int getInitialCategoryTotal() {
		return initialCategoryTotal;
	}

	public void setInitialCategoryTotal(int initialCategoryTotal) {
		this.initialCategoryTotal = initialCategoryTotal;
	}

	/**
	 * initial action to load literacy equipment/materials budget page. The
	 * jtable containing equipment budget records gets populated by "list"
	 * action; not this action
	 * 
	 * @return
	 */
	public String literacyYr1Equipment() {

		try{
			// get grantid selected
			String grantId = ldSession.getGrantId();
	
			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get grant status (submit/approve/lock/etc)
			grantStatus = grantService.getGrantStatus(grant.getId());
	
			// get the final appropriation for this inst/FY
			BudgetDBHandler bdh = new BudgetDBHandler();
			allocationYearBean = bdh.getAllocationForInstFy(grant.getFyCode().intValue(),
									grant.getFcCode().intValue(), grant.getInstId());
	
			// get category total from initial application
			BudgetSummaryDbBean bsb = new BudgetSummaryDbBean();
			initialCategoryTotal = bsb.calcBudgetSummaryTotalForFyExp(grant.getId(), grant.getFyCode().intValue(),
					bsb.determineExpenditureType("Equipment"));
			
			//get fund for this fcCode - navigate to page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)		{		
	  			return ADULT_LITERACY;			
			}
			else if (fund== FundProgram.FAMILY_LITERACY){ 			
				return FAMILY_LITERACY;
			}
			else
				return ERROR;
		
		}catch(Exception e){
			System.err.println("EquipmentAction.literacyYr1Equipment "+ e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * used with JTable "list" function. queries for all supply/material records
	 * to display in jtable.
	 * 
	 * @return
	 */
	public String literacyYr1EquipmentList() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get all supp/material budget records for grantId and fy
			EquipmentService equipmentService = new EquipmentService();
			allEquipment = equipmentService.searchEquipmentByGrantIdFy(grant);

			// format required by jTable plugin to display table of all
			// supp/material records
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Records", allEquipment);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "EquipmentAction.literacyYr1EquipmentList:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action to delete equipment budget row. Used with jtable confirm dialog.
	 * must return json object containing ok/error.
	 * 
	 * @return
	 */
	public String deleteLiteracyEquipmentRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			// create new domain object
			equipment = new Equipment();

			// get id from jtable post form
			if (request.getParameter("id") != null)
				equipment.setId(Long.parseLong(request.getParameter("id")));
			else
				equipment.setId(0L);// id not always sent?, set id=0 to prevent
									// errors below

			// delete this equipment budget ID
			EquipmentService equipmentService = new EquipmentService();
			equipmentService.deleteEquipment(equipment);

			// jtable requires ok/error result to refresh page
			JSONROOT.put("Result", "OK");

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "EquipmentAction.deleteLiteracyEquipmentRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action for add/edit equipment record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * APPLICANT ONLY; ADMIN UPDATE ACTION IS BELOW
	 * @return
	 */
	public String addUpdateLiteracyEquipmentRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			EquipmentService equipmentService = new EquipmentService();

			// get grantid selected
			String grantId = ldSession.getGrantId();
			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			equipment = new Equipment();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			equipment.setDescription(request.getParameter("description"));
			equipment.setVendor(request.getParameter("vendor"));
			equipment.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			equipment.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
			equipment.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			equipment.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				equipment.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (equipment.getId() == null || equipment.getId() == 0) {

				// get next id to insert
				equipment.setId(equipmentService.getNextEquipmentId());
				// insert
				equipmentService.insertEquipment(user, grant, equipment);
			} else
				equipmentService.updateEquipment(user, equipment);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Equipment newEquipment = new Equipment();
			newEquipment = equipmentService.selectEquipment(equipment);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newEquipment);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "EquipmentAction.addUpdateLiteracyEquipmentRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	
	
	/**
	 * action for add/edit equipment record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * ADMIN ONLY; UPDATES AMT/EXP APPROVED FIELDS
	 * @return
	 */
	public String addUpdateLiteracyEquipmentRowAdminFields() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			EquipmentService equipmentService = new EquipmentService();

			// get grantid selected
			String grantId = ldSession.getGrantId();
			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			equipment = new Equipment();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			equipment.setDescription(request.getParameter("description"));
			equipment.setVendor(request.getParameter("vendor"));
			equipment.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			equipment.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
			equipment.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			equipment.setExpenseApproved(Integer.parseInt(request.getParameter("expenseApproved")));
			equipment.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				equipment.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (equipment.getId() == null || equipment.getId() == 0) {

				// get next id to insert
				equipment.setId(equipmentService.getNextEquipmentId());
				// insert
				equipmentService.insertEquipment(user, grant, equipment);
			} else
				equipmentService.updateEquipmentWithAwardFields(user, equipment);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Equipment newEquipment = new Equipment();
			newEquipment = equipmentService.selectEquipment(equipment);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newEquipment);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "EquipmentAction.addUpdateLiteracyEquipmentRowAdminFields:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	

	public GrantStatus getGrantStatus() {
		return grantStatus;
	}

	public void setGrantStatus(GrantStatus grantStatus) {
		this.grantStatus = grantStatus;
	}

}
