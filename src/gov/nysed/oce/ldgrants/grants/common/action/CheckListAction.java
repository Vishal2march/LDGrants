package gov.nysed.oce.ldgrants.grants.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import gov.nysed.oce.ldgrants.grants.common.domain.FormType;
import gov.nysed.oce.ldgrants.grants.common.domain.FormVersionBuilder;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.VersionBean;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.FormVersionService;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.grants.common.service.InstitutionService;
import gov.nysed.oce.ldgrants.grants.common.service.StatusService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.shared.service.SessionManagerService;
import mypackage.AppStatusBean;
import mypackage.DBHandler;
import mypackage.GrantBean;
import mypackage.OfficerDBbean;
import mypackage.SubmissionDBbean;
import mypackage.UserBean;

public class CheckListAction extends LDGrantSessionManager {
	
	private Map userSession;
	  @Override 
	  public void setSession(Map session) {
	     userSession = session;
	  }

	private static final String ADULT_LITERACY = "adultlit";
	private static final String FAMILY_LITERACY = "familylit";
	     
	private Long fc;
	private Long fy;
	private Long id;

	private String ft;
	private String formType;

	private Grant grant;

	private Map<String, Boolean> isFormSubmitted;
	private Map<String, Boolean> isFormApproved;
	private Map<String, Boolean> isFormDenied;
	private Map<String, Boolean> isFormDeclined;
	private Map<String, String> statusMap;
	
	private FormType formTypeObject;
	private FormVersionBuilder formVersion;
	private List<FormType> formTypeList;
	private List<Grant> allGrants;
	private List<NarrativeType> narrativeTypeList;
	private List<NarrativeType> narrativeFormButtonList = new ArrayList<NarrativeType>();
	
	SessionManagerService sessionService = new SessionManagerService();
	
	
	

	public CheckListAction() {
	}

	public String generateApplicationChecklist() {
		FormVersionService formService = new FormVersionService();

		try {			
			//create/update navigation links
  			BreadcrumbManager breadcrumbs = new BreadcrumbManager();  			
  			breadcrumbs.createBreadcrumb(BreadcrumbPage.CHECKLIST, "Checklist");
  			
  			//clear session variables
			sessionService.resetSessionForProjectChecklist();
			
			allGrants = ldSession.getAllGrants();
			
			
			//SH NOTE: need to put grantId in session or all old struts1 links break
			ldSession.setGrantId(String.valueOf(id));			
			
			
			for (Grant grant : allGrants) {
								
				//if (grant.getFcCode() == fc && grant.getFyCode() == fy) {
				if (grant.getFcCode().longValue()==fc.longValue() && grant.getFyCode().longValue()==fy.longValue()) {
					ldSession.setGrant(grant);
				}
			}

			grant = ldSession.getGrant();
			
			formVersion = formService.buildFromVersion( grant.getFcCode(), grant.getFyCode());
			
			formTypeList = formVersion.getFormTypeList();
			
			
			// add form version builder to session
			ldSession.setFormVersion(formVersion);
			
			
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)				
	  			return ADULT_LITERACY;			
			else if (fund== FundProgram.FAMILY_LITERACY) 				  			
				return FAMILY_LITERACY;
			else 
				return ERROR;
			
			
			
		} catch (Exception ex) {
			System.err.println("FAILED: METHOD: generateApplicationChecklist: " + ex.toString());
		}
				
		return ERROR;
	}

	
	
	
	
	
	
	
	
	
	
	
	public String generateFormChecklist() {
	
		try {
			//create/update navigation links
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
			breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM_CHECKLIST, "Forms");
									
			grant = ldSession.getGrant();
					
			Long tempFormTypeID = null;
			List<Long> narrativeIdList = new ArrayList<Long>();

			String[] parts = ft.split(" Forms");
			formType = parts[0];
			
			
			// grab form version builder from session
			formVersion = ldSession.getFormVersion();
		
			this.formTypeList = formVersion.getFormTypeList();
			this.narrativeTypeList = formVersion.getNarrativeTypeList();

			// get form type Id related to form type display name
			for (FormType fBean : this.formTypeList) {
				if (fBean.getDisplayName().equals(formType)) {
					//set current formTypeID In session
					ldSession.clearFormTypeID();
                    ldSession.setFormTypeID(fBean.getId());
                  
					tempFormTypeID = fBean.getId();
					formTypeObject = fBean;
				}
			}
			
			// loop through and get all narratives related to form type
			for (VersionBean vBean : formVersion.getVersionList()) {
				if (vBean.getFormTypeId().equals(tempFormTypeID)) {

					narrativeIdList.add(vBean.getNarrativeId());
				}
			}
					
			// loop through narrative bean and compare to ids to narrative id
			// list
			for (NarrativeType nBean : this.narrativeTypeList) {
				for (Long id : narrativeIdList) {
					if (id.equals(nBean.getId()) && nBean.getFormButton().equals(true)) {

						narrativeFormButtonList.add(nBean);
					}

				}
			}
					
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
			
			
			if(fund== FundProgram.ADULT_LITERACY)				
	  			return (ADULT_LITERACY  + formTypeObject.getFormType());
			
			else if (fund== FundProgram.FAMILY_LITERACY)  					  			
				return (FAMILY_LITERACY + formTypeObject.getFormType());
			else 
				return ERROR;
			
															

		} catch (Exception ex) {
			System.err.println("FAILED: METHOD: generateFormCheckList: " + ex.toString());
			
			//SH NOTE: adding page forward here - exception thrown btw sop5/6 above??
			//otherwise never forwards to next page?
			
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
			System.out.println("form checklist "+fund);
			
			if(fund== FundProgram.ADULT_LITERACY)				
	  			return (ADULT_LITERACY  + formTypeObject.getFormType());
			
			else if (fund== FundProgram.FAMILY_LITERACY)  					  			
				return (FAMILY_LITERACY + formTypeObject.getFormType());
			else 
				return ERROR;
			
		}		
	}
	
		
	
	
	public String generateAdminChecklist() {
		GrantBean grantBean = new GrantBean();
		try {
			//create/update navigation links
  			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
  			breadcrumbs.createBreadcrumb(BreadcrumbPage.PROGRAM_HOME, "Literacy Home");
  			
			//create/update navigation links
			breadcrumbs.createBreadcrumb(BreadcrumbPage.CHECKLIST, "Checklist");
			
			sessionService.resetSessionForProjectChecklist();
			
			//need to put grantId in session or all old struts1 links break
			ldSession.setGrantId(String.valueOf(id));
						
			//NOTE: the following calls basically do the same thing(query GRANTS table)
			//and set variables needed for subsequent struts1/2 calls/pages.
			///////////////////////////////////////////////////////
			
			// sets the bean 'thisGrant' to the session       
			DBHandler dbh = new DBHandler();
	        grantBean = dbh.getRecordBean(id);
	        userSession.put("thisGrant", grantBean);
	        //for new struts2 admin pages - need grantBean/old grant/new variables in session manager
	        ldSession.setGrantBean(grantBean);
	        
	       //get basic grant info - new struts2/spring jdbc version
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(id);
			ldSession.setGrant(grant);
			InstitutionService institutionService = new InstitutionService();
			Institution inst = institutionService.selectInstitution(grant.getInstId());
			ldSession.setInstitution(inst);
			/////////////////////////////////////////////////////////////////////////
			
			
			AppStatusBean asb= dbh.getApplicationStatus(id);
			HttpServletRequest request = ServletActionContext.getRequest();
	        request.setAttribute("appStatus", asb);
	        
	        SubmissionDBbean sb = new SubmissionDBbean();
	        Vector allSubmissions = sb.getSubmissions(id); 
	        request.setAttribute("allSubmissions", allSubmissions);
	          
	        //added 3/18/13 to check for and add new sedref director fk to grant_admins table
	        UserBean userb = ldSession.getLdUser();  
	        OfficerDBbean odb = new OfficerDBbean();
	        odb.verifyCurrentDirector(id, grantBean.getInstID(), userb);
			
	      	
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
			
			
			if(fund== FundProgram.ADULT_LITERACY){
				
	  			return ADULT_LITERACY;
			}
			else if (fund== FundProgram.FAMILY_LITERACY)  {
					  			
				return FAMILY_LITERACY;
			} else {
				return ERROR;
			}
			
			
					
		} catch (Exception ex) {
			System.err.println("error CheckListAction.generateAdminChecklist() " + ex.toString());
		}
		
		return ERROR;
	}
	
	
	
	
	
	
	
	
	
	
	

	public String narrativeFormStatus() {

		StatusService statusService = new StatusService();
	    statusMap = new HashMap<>();
		
		try {
			this.grant = ldSession.getGrant();
			this.formVersion = ldSession.getFormVersion();
			statusMap = statusService.getNarrativeStatus(formVersion.getNarrativeTypeList(), grant.getId());
			
		} catch (Exception ex) {
			System.err.println("FAILED: METHOD: narrativeFormStatus(): " + ex.toString());
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	

	public String isForm() {

		FormVersionService formService = new FormVersionService();

		isFormSubmitted = new HashMap<String, Boolean>();
		isFormApproved = new HashMap<String, Boolean>();
		isFormDenied = new HashMap<String, Boolean>();
		isFormDeclined = new HashMap<String, Boolean>();

		try {
			grant = ldSession.getGrant();
			this.formVersion = ldSession.getFormVersion();

			for (FormType fBean : this.formVersion.getFormTypeList()) {

				Boolean value = formService.isFormSubmitted(fBean.getId(), grant.getId());
				isFormSubmitted.put(fBean.getFormType(), value);

				List<String> approvalTypeList = formService.getApprovalTypes(fBean.getId(), grant.getId());

				if (approvalTypeList.size() > 0) {

					for (int i = 0; i < approvalTypeList.size(); i++) {

						if (approvalTypeList.get(i).equalsIgnoreCase("Approve")) {
							isFormApproved.put(fBean.getFormType(), true);
							isFormDenied.put(fBean.getFormType(), false);
							isFormDeclined.put(fBean.getFormType(), false);
							isFormSubmitted.put(fBean.getFormType(), false);
						} else {
							isFormApproved.put(fBean.getFormType(), false);
						}

						if (approvalTypeList.get(i).equalsIgnoreCase("Denied")) {
							isFormDenied.put(fBean.getFormType(), true);
							isFormApproved.put(fBean.getFormType(), false);
							isFormSubmitted.put(fBean.getFormType(), false);
							isFormDeclined.put(fBean.getFormType(), false);
						} else {
							isFormDenied.put(fBean.getFormType(), false);
						}

						if (approvalTypeList.get(i).equalsIgnoreCase("Declined")) {
							isFormDeclined.put(fBean.getFormType(), true);
							isFormApproved.put(fBean.getFormType(), false);
							isFormDenied.put(fBean.getFormType(), false);
							isFormSubmitted.put(fBean.getFormType(), false);
						} else {
							isFormDeclined.put(fBean.getFormType(), false);
						}
					}

				} else {
					isFormApproved.put(fBean.getFormType(), false);
					isFormDenied.put(fBean.getFormType(), false);
					isFormDeclined.put(fBean.getFormType(), false);
				}
			}

		} catch (Exception ex) {
			System.err.println("FAILED: METHOD: isForm: " + ex.toString());
		}
		return SUCCESS;
	}

	public Long getFc() {
		return fc;
	}

	public void setFc(Long fc) {
		this.fc = fc;
	}

	public Long getFy() {
		return fy;
	}

	public void setFy(Long fy) {
		this.fy = fy;
	}

	public String getFt() {
		return ft;
	}

	public void setFt(String ft) {
		this.ft = ft;
	}

	public List<FormType> getFormTypeList() {
		return formTypeList;
	}

	public void setFormTypeList(List<FormType> formTypeList) {
		this.formTypeList = formTypeList;
	}

	public Map<String, Boolean> getIsFormSubmitted() {
		return isFormSubmitted;
	}

	public void setIsFormSubmitted(Map<String, Boolean> isFormSubmitted) {
		this.isFormSubmitted = isFormSubmitted;
	}

	public Map<String, Boolean> getIsFormApproved() {
		return isFormApproved;
	}

	public void setIsFormApproved(Map<String, Boolean> isFormApproved) {
		this.isFormApproved = isFormApproved;
	}

	public Map<String, Boolean> getIsFormDenied() {
		return isFormDenied;
	}

	public void setIsFormDenied(Map<String, Boolean> isFormDenied) {
		this.isFormDenied = isFormDenied;
	}

	public Map<String, Boolean> getIsFormDeclined() {
		return isFormDeclined;
	}

	public void setIsFormDeclined(Map<String, Boolean> isFormDeclined) {
		this.isFormDeclined = isFormDeclined;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Grant getGrant() {
		return grant;
	}

	public void setGrant(Grant grant) {
		this.grant = grant;
	}

	public List<NarrativeType> getNarrativeTypeList() {
		return narrativeTypeList;
	}

	public void setNarrativeTypeList(List<NarrativeType> narrativeTypeList) {
		this.narrativeTypeList = narrativeTypeList;
	}

	public List<NarrativeType> getNarrativeFormButtonList() {
		return narrativeFormButtonList;
	}

	public void setNarrativeFormButtonList(List<NarrativeType> narrativeFormButtonList) {
		this.narrativeFormButtonList = narrativeFormButtonList;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	

	public FormType getFormTypeObject() {
		return formTypeObject;
	}

	public void setFormTypeObject(FormType formTypeObject) {
		this.formTypeObject = formTypeObject;
	}

}
