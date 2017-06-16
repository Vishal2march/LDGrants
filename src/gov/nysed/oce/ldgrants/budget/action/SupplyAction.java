package gov.nysed.oce.ldgrants.budget.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import construction.AllocationYearBean;
import gov.nysed.oce.ldgrants.budget.domain.Supplies;
import gov.nysed.oce.ldgrants.budget.service.SuppliesService;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import literacy.BudgetSummaryDbBean;
import mypackage.BudgetDBHandler;

public class SupplyAction extends LDGrantSessionManager {

	HashMap<Object, Object> JSONROOT;// uses with jtable/json output
	private Supplies supplies = new Supplies();
	private List<Supplies> allSupplies;
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

	public Supplies getSupplies() {
		return supplies;
	}

	public void setSupplies(Supplies supplies) {
		this.supplies = supplies;
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

	public List<Supplies> getAllSupplies() {
		return allSupplies;
	}

	public void setAllSupplies(List<Supplies> allSupplies) {
		this.allSupplies = allSupplies;
	}

	/**
	 * initial action to load literacy supplies/materials budget page. "list"
	 * action populates the jtable of budget records
	 * 
	 * @return
	 */
	public String literacyYr1Supplies() {

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
					bsb.determineExpenditureType("Supplies"));
	
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
			System.err.println("SupplyAction.literacyYr1Supplies "+ e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * used with JTable "list" function. queries for all supply/material records
	 * to display in jtable.
	 * 
	 * @return
	 */
	public String literacyYr1SuppliesList() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get all supp/material budget records for grantId and fy
			SuppliesService suppliesService = new SuppliesService();
			allSupplies = suppliesService.searchSuppliesByGrantIdFy(grant);

			// format required by jTable plugin to display table of all
			// supp/material records
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Records", allSupplies);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "SupplyAction.literacyYr1SuppliesList:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action to delete supplies budget row. Used with jtable confirm dialog.
	 * must return json object containing ok/error.
	 * 
	 * @return
	 */
	public String deleteLiteracySuppliesRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			// create new domain object
			supplies = new Supplies();

			// get id from jtable post form
			if (request.getParameter("id") != null)
				supplies.setId(Long.parseLong(request.getParameter("id")));
			else
				supplies.setId(0L);// id not always sent?, set id=0 to prevent
									// errors below

			// delete this supplies budget ID
			SuppliesService suppliesService = new SuppliesService();
			suppliesService.deleteSupplies(supplies);

			// jtable requires ok/error result to refresh page
			JSONROOT.put("Result", "OK");

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "SuppliesAction.deleteLiteracySuppliesRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action for add/edit supplies record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * APPLICANT ONLY; ADMIN EDIT ACTION IS BELOW
	 * @return
	 */
	public String addUpdateLiteracySuppliesRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			SuppliesService suppliesService = new SuppliesService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			supplies = new Supplies();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			supplies.setDescription(request.getParameter("description"));
			supplies.setVendor(request.getParameter("vendor"));
			supplies.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			//supplies.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
			supplies.setUnitPrice(request.getParameter("unitPrice"));
			supplies.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			supplies.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				supplies.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (supplies.getId() == null || supplies.getId() == 0) {

				// get next id to insert
				supplies.setId(suppliesService.getNextSuppliesId());
				// insert
				suppliesService.insertSupplies(user, grant, supplies);
			} else
				suppliesService.updateSupplies(user,supplies);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Supplies newSupplies = new Supplies();
			newSupplies = suppliesService.selectSupplies(supplies);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newSupplies);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "SupplyAction.addUpdateLiteracySuppliesRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	/**
	 * action for add/edit supplies record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * ADMIN ACTION FOR EDIT APPROVAL FIELDS
	 * @return
	 */
	public String addUpdateLiteracySuppliesRowAdminFields() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			SuppliesService suppliesService = new SuppliesService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			supplies = new Supplies();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			supplies.setDescription(request.getParameter("description"));
			supplies.setVendor(request.getParameter("vendor"));
			supplies.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			//supplies.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
			supplies.setUnitPrice(request.getParameter("unitPrice"));
			supplies.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			supplies.setExpenseApproved(Integer.parseInt(request.getParameter("expenseApproved")));
			supplies.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				supplies.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (supplies.getId() == null || supplies.getId() == 0) {

				// get next id to insert
				supplies.setId(suppliesService.getNextSuppliesId());
				// insert
				suppliesService.insertSupplies(user, grant, supplies);
			} else
				suppliesService.updateSuppliesWithAwardFields(user,supplies);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Supplies newSupplies = new Supplies();
			newSupplies = suppliesService.selectSupplies(supplies);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newSupplies);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "SupplyAction.addUpdateLiteracySuppliesRowAdminFields:  " + e.getMessage());
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
