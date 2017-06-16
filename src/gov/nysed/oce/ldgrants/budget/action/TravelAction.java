package gov.nysed.oce.ldgrants.budget.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import construction.AllocationYearBean;
import gov.nysed.oce.ldgrants.budget.domain.Travel;
import gov.nysed.oce.ldgrants.budget.service.TravelService;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import literacy.BudgetSummaryDbBean;
import mypackage.BudgetDBHandler;

public class TravelAction extends LDGrantSessionManager {

	HashMap<Object, Object> JSONROOT;// uses with jtable/json output
	private Travel travel = new Travel();
	private List<Travel> allTravel;
	private AllocationYearBean allocationYearBean = new AllocationYearBean();// finalapprop
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

	public Travel getTravel() {
		return travel;
	}

	public void setTravel(Travel travel) {
		this.travel = travel;
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

	public List<Travel> getAllTravel() {
		return allTravel;
	}

	public void setAllTravel(List<Travel> allTravel) {
		this.allTravel = allTravel;
	}

	/**
	 * initial action to load literacy travel budget page. "list" action
	 * populates jtable with travel records
	 * 
	 * @return
	 */
	public String literacyYr1Travel() {
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
					bsb.determineExpenditureType("Travel"));
	
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
			System.err.println("TravelAction.literacyYr1Travel "+ e.getMessage());
		}
		
		return SUCCESS;
	}
	

	/**
	 * used with JTable "list" function. queries for all travel records to
	 * display in jtable.
	 * 
	 * @return
	 */
	public String literacyYr1TravelList() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get all travel budget records for grantId and fy
			TravelService travelService = new TravelService();
			allTravel = travelService.searchTravelByGrantIdFy(grant);

			// format required by jTable plugin to display table of all travel
			// records
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Records", allTravel);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "TravelAction.literacyYr1TravelList:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action to delete travel budget row. Used with jtable confirm dialog. must
	 * return json object containing ok/error.
	 * 
	 * @return
	 */
	public String deleteLiteracyTravelRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			// create new domain object
			travel = new Travel();

			// get id from jtable post form
			if (request.getParameter("id") != null)
				travel.setId(Long.parseLong(request.getParameter("id")));
			else
				travel.setId(0L);// id not always sent?, set id=0 to prevent
									// errors below

			// delete this travel budget ID
			TravelService travelService = new TravelService();
			travelService.deleteTravel(travel);

			// jtable requires ok/error result to refresh page
			JSONROOT.put("Result", "OK");

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "TravelAction.deleteLiteracyTravelRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * action for add/edit travel record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * FOR APPLICANT ONLY - ADMIN ADDUPDATE ACTION BELOW
	 * 
	 * @return
	 */
	public String addUpdateLiteracyTravelRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			TravelService travelService = new TravelService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			travel = new Travel();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			travel.setDescription(request.getParameter("description"));
			travel.setPurpose(request.getParameter("purpose"));
			travel.setCostSummary(request.getParameter("costSummary"));
			travel.setTravelPeriod(request.getParameter("travelPeriod"));
			travel.setTravelerName(request.getParameter("travelerName"));
			travel.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			travel.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				travel.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (travel.getId() == null || travel.getId() == 0) {

				// get next id to insert
				travel.setId(travelService.getNextTravelId());
				// insert
				travelService.insertTravel(user, grant, travel);
			} else
				travelService.updateTravel(user, travel);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Travel newTravel = new Travel();
			newTravel = travelService.selectTravel(travel);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newTravel);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "TravelAction.addUpdateLiteracyTravelRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	
	
	/**
	 * action for add/edit travel record. Used with jtable ajax call. must
	 * return json object containing row that was edited; along with ok/error
	 * ADMIN ADDUPDATE ACTION - MODIFIES AMT_APPROVE/EXP_APPROVE FIELDS
	 * 
	 * @return
	 */
	public String addUpdateLiteracyTravelRowAdminFields() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			TravelService travelService = new TravelService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			travel = new Travel();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			travel.setDescription(request.getParameter("description"));
			travel.setPurpose(request.getParameter("purpose"));
			travel.setCostSummary(request.getParameter("costSummary"));
			travel.setTravelPeriod(request.getParameter("travelPeriod"));
			travel.setTravelerName(request.getParameter("travelerName"));
			travel.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			travel.setExpenseApproved(Integer.parseInt(request.getParameter("expenseApproved")));
			travel.setFyCode(grant.getFyCode());// FOR YEAR 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				travel.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (travel.getId() == null || travel.getId() == 0) {

				// get next id to insert
				travel.setId(travelService.getNextTravelId());
				// insert
				travelService.insertTravel(user, grant, travel);
			} else
				travelService.updateTravelWithAwardFields(user, travel);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			Travel newTravel = new Travel();
			newTravel = travelService.selectTravel(travel);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newTravel);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "TravelAction.addUpdateLiteracyTravelRowAdminFields:  " + e.getMessage());
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
