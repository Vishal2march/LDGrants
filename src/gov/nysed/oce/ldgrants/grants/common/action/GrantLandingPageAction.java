package gov.nysed.oce.ldgrants.grants.common.action;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.ApplicationDate;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantLandingPage;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.ApplicationDateService;
import gov.nysed.oce.ldgrants.grants.common.service.FiscalYearService;
import gov.nysed.oce.ldgrants.grants.common.service.GrantLandingPageService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.shared.service.SessionManagerService;


public class GrantLandingPageAction extends LDGrantSessionManager {

	private Long fc; //fundcode
	private String p; //program param to pass to struts 1 action
	
	private GrantLandingPage grantLandingPage;

	 
     GrantLandingPageService landingPageService = new GrantLandingPageService();
     ApplicationDateService appDateService = new ApplicationDateService();
     FiscalYearService fiscalYearService = new FiscalYearService();
     SessionManagerService sessionService = new SessionManagerService();
     
     ApplicationDate appDate = new ApplicationDate();
     
     private static final String ADULT_LITERACY = "adultlit";
     private static final String FAMILY_LITERACY = "familylit";
     
	public String LandingPage(){
		
		try {
			sessionService.resetSessionForLandingPage();//this clears "allGrants" and "institution" from session
			
			//Build Grant landing page Adult Lit; get user from session
			grantLandingPage = landingPageService.buildGrantLandingPage(ldSession.getLdUser().getInstid(), fc);
			
			//Add all grants to session
			ldSession.setAllGrants(grantLandingPage.getGrantList());
			//Defined INSTITUTION object in LDSESSION Manager and Added object to session to get instname for applicationchecklist
			ldSession.setInstitution(grantLandingPage.getInstitution());
			
			
			
			//search APPLICATION_DATES table for current date between start/due dates
			appDate = appDateService.searchByTodayDate(fc);
			////////////////////////////////////
			
			
			//check if user is able to create a new app for this fiscal year
			//get back a list of error/warning messages regarding "create app" criteria that failed
			List<String> actionMsgs = landingPageService.institutionCanCreateNewApp(fc, ldSession.getInstituiton(), appDate, grantLandingPage.getGrantList());
			if(actionMsgs !=null &&  actionMsgs.size()>0){
				for(String s: actionMsgs)
					addActionMessage(s);
			}
				
			
			
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(fc.intValue());
						
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
  			
			if(fund== FundProgram.ADULT_LITERACY){
				// call to generate landing page breadcrumb
				breadcrumbs.createBreadcrumb(BreadcrumbPage.PROGRAM_HOME, "Adult Literacy Home");	  				  			
	  			return ADULT_LITERACY;
	  			
			}
			else if (fund== FundProgram.FAMILY_LITERACY)  {
				//call to generate landing page breadcrumb
				breadcrumbs.createBreadcrumb(BreadcrumbPage.PROGRAM_HOME, "Family Literacy Home");	  			
				return FAMILY_LITERACY;
				
			} else {
				return ERROR;
			}
			
			
			} catch (Exception ex) {
			System.err.println("CLASS:GrantLandingPageAction METHOD:LandingPage()" + ex.toString());
		} 
		return ERROR;
				
	}
	

	
	public Long getFc() {
		return fc;
	}

	public void setFc(Long fc) {
		this.fc = fc;
	}


	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}


	public GrantLandingPage getGrantLandingPage() {
		return grantLandingPage;
	}

	public void setGrantLandingPage(GrantLandingPage grantLandingPage) {
		this.grantLandingPage = grantLandingPage;
	}
	
	

	public ApplicationDate getAppDate() {
		return appDate;
	}

	public void setAppDate(ApplicationDate appDate) {
		this.appDate = appDate;
	}

}
