package gov.nysed.oce.ldgrants.shared.service;

import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class SessionManagerService extends LDGrantSessionManager{
	
	
	/**
	 * This method clears session objects for GrantLandingPageAction - for applicants only, used to 
	 * clear out any old grants in response to user click to load Literacy Landing page.
	 */
	public void resetSessionForLandingPage(){
		
		try{
			
			ldSession.clearAllGrants();
			ldSession.clearInstitution();
						
		}catch(Exception e){
			System.out.println("SessionManagerService.resetSessionForLandingPage() "
					+ e.getMessage());
		}
	}
	
	
	
	/**
	 * This method clears session objects for the ChecklistAction - used when user clicks on a 
	 * grant project number, and ChecklistAction is called to generate Checklist. This clears any 
	 * old grant selected, in preparation for adding the newly selected grant into session.
	 */
	public void resetSessionForProjectChecklist(){
		
		try{
			
			ldSession.clearGrantBean();
			ldSession.clearGrantId();
			ldSession.clearGrant();
			ldSession.clearFormVersion();
			
		}catch(Exception e){
			System.out.println("SessionManagerService.resetSessionForProjectChecklist() "
					+ e.getMessage());
		}
	}
	
	
	
	
	
	
	/**
	 * This method clears session objects for the LogoutAction. This clears the new and old user, grant 
	 * and grant id objects from the session. User will need to login again.
	 */
	public void resetSessionForLogout(){
		
		try{			
			//clear user session
			ldSession.clearUser();
			ldSession.clearLdUser();
			ldSession.clearGrantId();
			ldSession.clearGrant();
			ldSession.clearGrantBean();		
			
		}catch(Exception e){
			System.out.println("SessionManagerService.resetSessionForLogout() "
					+ e.getMessage());
		}
	}

}
