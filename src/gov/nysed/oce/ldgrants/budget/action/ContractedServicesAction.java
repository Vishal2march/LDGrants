package gov.nysed.oce.ldgrants.budget.action;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import construction.AllocationYearBean;
import gov.nysed.oce.ldgrants.budget.domain.ContractedServices;
import gov.nysed.oce.ldgrants.budget.service.ContractedServicesService;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import literacy.BudgetSummaryDbBean;
import mypackage.BudgetDBHandler;

public class ContractedServicesAction extends LDGrantSessionManager {

	private ContractedServices contractedServices = new ContractedServices();
	private List<ContractedServices> allContractedServices;
	HashMap<Object, Object> JSONROOT;// uses with jtable/json output
	private AllocationYearBean allocationYearBean = new AllocationYearBean();// finalapprop
	private int initialCategoryTotal;
	private GrantStatus grantStatus = new GrantStatus();
	private static final String ADULT_LITERACY = "adultlit";
	private static final String FAMILY_LITERACY = "familylit";
	
	
	///////// Getters and setters/////////////////////////////////////////

	public HashMap<Object, Object> getJSONROOT() {
		return JSONROOT;
	}

	public void setJSONROOT(HashMap<Object, Object> jSONROOT) {
		JSONROOT = jSONROOT;
	}

	public ContractedServices getContractedServices() {
		return contractedServices;
	}

	public void setContractedServices(ContractedServices contractedServices) {
		this.contractedServices = contractedServices;
	}

	public List<ContractedServices> getAllContractedServices() {
		return allContractedServices;
	}

	public void setAllContractedServices(List<ContractedServices> allContractedServices) {
		this.allContractedServices = allContractedServices;
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
	 * used with JTable "list" function
	 * 
	 * @return
	 */
	public String literacyYr1ContractedServicesList() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {

			// get grantid selected
			String grantid = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantid));
			
			// get all contracted budget records for grantId and fy
			ContractedServicesService contractedServicesService = new ContractedServicesService();
			allContractedServices = contractedServicesService.searchContractedServicesByGrantIdFy(grant);
			
						
			// format required by jTable plugin to display table of all
			// contracted_services records
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Records", allContractedServices);
			

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "ContractedServicesAction.literacyYr1ContractedServicesList:  " + e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * action for add/edit contracted services record. Used with jtable ajax
	 * call. must return json object containing row that was edited; along with
	 * ok/error
	 * FOR APPLICANT ONLY; ADMIN UPDATE ACTION IS BELOW
	 * @return
	 */
	public String addUpdateLiteracyContractedServicesRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			ContractedServicesService contractedServicesService = new ContractedServicesService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			contractedServices = new ContractedServices();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			contractedServices.setServiceType(request.getParameter("serviceType"));
			contractedServices.setRecipient(request.getParameter("recipient"));
			contractedServices.setServiceDescription(request.getParameter("serviceDescription"));
			contractedServices.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			contractedServices.setFyCode(grant.getFyCode());// FOR
																		// YEAR
																		// 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				contractedServices.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (contractedServices.getId() == null || contractedServices.getId() == 0) {

				// get next id to insert
				contractedServices.setId(contractedServicesService.getNextContractedServicesId());
				// insert
				contractedServicesService.insertContractedServices(user, grant,	contractedServices);
			} else
				contractedServicesService.updateContractedServices(user, contractedServices);

			
			
			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			ContractedServices newContractedServices = new ContractedServices();
			newContractedServices = contractedServicesService.selectContractedServices(contractedServices);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newContractedServices);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message",
					"ContractedServicesAction.addUpdateLiteracyContractedServicesRow:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	
	/**
	 * action for add/edit contracted services record. Used with jtable ajax
	 * call. must return json object containing row that was edited; along with
	 * ok/error
	 * ADMIN ONLY - UPDATES AMT/EXP APPROVED FIELDS
	 * @return
	 */
	public String addUpdateLiteracyContractedServicesRowAdminFields() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			ContractedServicesService contractedServicesService = new ContractedServicesService();

			// get grantid selected
			String grantId = ldSession.getGrantId();

			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));

			// get user logged in
			User user = ldSession.getUser();

			// create new domain object
			contractedServices = new ContractedServices();

			HttpServletRequest request = ServletActionContext.getRequest();

			// get each object from ajax post form and set to object
			contractedServices.setServiceType(request.getParameter("serviceType"));
			contractedServices.setRecipient(request.getParameter("recipient"));
			contractedServices.setServiceDescription(request.getParameter("serviceDescription"));
			contractedServices.setExpenseSubmitted(Integer.parseInt(request.getParameter("expenseSubmitted")));
			contractedServices.setExpenseApproved(Integer.parseInt(request.getParameter("expenseApproved")));
			contractedServices.setFyCode(grant.getFyCode());// FOR
																		// YEAR
																		// 1

			// check if ID parameter passed in (no ID param on "add record"
			// form)
			if (request.getParameter("id") != null)
				contractedServices.setId(Long.parseLong(request.getParameter("id")));

			// if no ID - then insert. Else do update
			if (contractedServices.getId() == null || contractedServices.getId() == 0) {

				// get next id to insert
				contractedServices.setId(contractedServicesService.getNextContractedServicesId());
				// insert
				contractedServicesService.insertContractedServices(user, grant,	contractedServices);
			} else
				contractedServicesService.updateContractedServicesWithAwardFields(user, contractedServices);

			// jtable plugin requires new record to be returned. query db for
			// newly inserted record
			ContractedServices newContractedServices = new ContractedServices();
			newContractedServices = contractedServicesService.selectContractedServices(contractedServices);

			// format required by jTable plugin.
			// ADD is supposed to return ok, and record that was just inserted
			// UPDATE is supposed to return ok (but also returning record seems
			// to work ok)
			JSONROOT.put("Result", "OK");
			JSONROOT.put("Record", newContractedServices);

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message",
					"ContractedServicesAction.addUpdateLiteracyContractedServicesRowAdminFields:  " + e.getMessage());
			return SUCCESS;
		}

		return SUCCESS;
	}
	
	

	/**
	 * action to delete budget row. Used with jtable confirm dialog. must return
	 * json object containing ok/error.
	 * 
	 * @return
	 */
	public String deleteLiteracyContractedServicesRow() {

		// create map to hold data required by jtable
		JSONROOT = new HashMap<Object, Object>();

		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			// create new domain object
			contractedServices = new ContractedServices();

			// get id from jtable post form
			if (request.getParameter("id") != null)
				contractedServices.setId(Long.parseLong(request.getParameter("id")));
			else
				contractedServices.setId(0L);// id not always sent?, set id=0 to
												// prevent errors below

			// delete this contracted budget ID
			ContractedServicesService contractedServicesService = new ContractedServicesService();
			contractedServicesService.deleteContractedServices(contractedServices);

			// jtable requires ok/error result to refresh page
			JSONROOT.put("Result", "OK");

		} catch (Exception e) {
			// format to print error dialog in jtable
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "ContractedServicesAction.deleteLiteracyContractedServicesRow:  " + e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * query all data on literacy contracted budget tab. the actual Jtable with
	 * contracted budget records gets populated by "list" action which returns
	 * JSON. This action will query for all other data elements on page
	 * (appropriation, total of initial application budget category)
	 * 
	 * @return
	 */
	public String literacyYr1ContractedServices() {
				
		try{
			//create/update navigation links
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
			breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Project Budget");
			
	
			// get grantid selected
			String grantId = ldSession.getGrantId();
	
			// get grant fy
			GrantService grantService = new GrantService();
			Grant grant = grantService.getGrant(Long.parseLong(grantId));
	
			// get the final appropriation for this inst/FY
			BudgetDBHandler bdh = new BudgetDBHandler();
			allocationYearBean = bdh.getAllocationForInstFy(grant.getFyCode().intValue(),
									grant.getFcCode().intValue(), grant.getInstId());
	
			// get category total from initial application
			BudgetSummaryDbBean bsb = new BudgetSummaryDbBean();
			initialCategoryTotal = bsb.calcBudgetSummaryTotalForFyExp(grant.getId(), grant.getFyCode().intValue(),
					bsb.determineExpenditureType("Purchased Services"));
			
			//get grant status (submit/approve/lock/etc)
			grantStatus = grantService.getGrantStatus(grant.getId());
			
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
			System.err.println("ContractedServicesAction.literacyYr1ContractedServices "+ e.getMessage());
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
