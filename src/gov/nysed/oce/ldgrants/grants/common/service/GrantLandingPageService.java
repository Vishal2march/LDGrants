package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.ApplicationDate;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantLandingPage;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;

public class GrantLandingPageService {
	GrantService grantService = new GrantService();
	InstitutionService institutionService = new InstitutionService();

	public GrantLandingPageService() {
		// TODO Auto-generated constructor stub
	}

    /**
    * buildGrantLandingPage
    * BUILD Grant Programs Landing Page Object
    * @param instid, fundcode
    * @return A GrantLandingPage object is returned
    */
	public GrantLandingPage buildGrantLandingPage(Long instId, Long fundCode) {
		GrantLandingPage grantLandingPage = new GrantLandingPage();
		try {
			//get institution info from DB and add to LandingPage constructor 
			grantLandingPage.setInstitution(institutionService.selectInstitution(instId));
			//Add all grants related to instittution and add to a temp Grant object List 
			List<Grant> tempGrantList = grantService.searchByInstId(instId);
             
			// loop through temp list and add all grants with correct funcode to LandingPage constructor
			for (Grant grant : tempGrantList) {
				if (grant.getFcCode().equals(fundCode)) {
					grantLandingPage.getGrantList().add(grant);
					
				}
			}
			//return landing page
			return grantLandingPage;
		} catch (Exception e) {
			System.err.println("GrantLandingPageService.buildGrantLandingPage: " + e.getMessage());
			return null;
		}
	}
	
	
	
	
	
	
public List<String> institutionCanCreateNewApp(Long fundcode, Institution inst, ApplicationDate appDate, List<Grant> grantList){
	
	List<String> errorMsgs = new ArrayList<String>();
	try{	
		
		//determine fund;  "new app" criteria different for each fund program
		FundProgram fund = FundProgram.searchByFundCode(fundcode.intValue());
		
		// Check if Public Library System is only for Literacy Grants
		if (fund==FundProgram.ADULT_LITERACY ||  fund==FundProgram.FAMILY_LITERACY){
						
			if(inst.isLibrarySystem() == false){
				errorMsgs.add("Only Public Library Systems may apply for the Literacy program.");
			}
		}
		
		//check if today between application available/due dates
		if(appDate.isInitialDatesAcceptible()== false){
			errorMsgs.add("You can only create 1 new Literacy application per 3-year grant cycle during the new application period.");
		}
		else{
			//today's date is ok (between available/due date)
			//check if institution already has literacy app started (Literacy can only create 1 app per FY)
			//NOTE: LITERACY HAS LIMIT OF 1 APP PER YEAR (construction has 2; lgrmif can be unlimited, etc)
			
			for(Grant grant: grantList){
				//if(grant.getFyCode() == appDate.getFyCode()){
				if(grant.getFyCode().longValue()==appDate.getFyCode().longValue()){
					//a grant application already exists for this FY; cannot create another one
					errorMsgs.add("You can only create 1 new Literacy application per 3-year grant cycle during the new application period.");
				}
			}			
		}
	
	}catch(Exception e){
		System.err.println("GrantLandingPageService.institutionCanCreateNewApp() "+e.getMessage());
	}		
	return errorMsgs;
 
}


}
