package gov.nysed.oce.ldgrants.grants.common.action;

import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.shared.service.SessionManagerService;

public class LogoutAction extends LDGrantSessionManager{
	
	private SessionManagerService sessionService = new SessionManagerService();
	
public String logout() {		
	
		try{			
			//clear user session
			sessionService.resetSessionForLogout();
						
		}catch(Exception e){
			System.err.println("LogoutAction.logout "+ e.getMessage().toString());
		}
				
		return SUCCESS;
	}

}
