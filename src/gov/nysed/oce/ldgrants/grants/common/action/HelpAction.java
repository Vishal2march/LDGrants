package gov.nysed.oce.ldgrants.grants.common.action;

import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class HelpAction extends LDGrantSessionManager{

	
	public String help() {		
		String program = null;
		try{			
			
			//determine which module user is in (literacy/construction/lgrmif/etc)
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
  			program = breadcrumbs.determineProgramFromHomeCrumb();
  			
			
		}catch(Exception e){
			System.err.println("HelpAction.help "+ e.getMessage().toString());
		}
				
		return program;
	}

}
