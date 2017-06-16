package gov.nysed.oce.ldgrants;

import gov.nysed.oce.ldgrants.grants.common.service.NarrativeService;

public class HelloWorld extends com.opensymphony.xwork2.ActionSupport {
	
	public HelloWorld(){
	}
	
	public String test(){
		NarrativeService narrativeService = new NarrativeService();
		narrativeService.deleteNarrative(1L, 1L);
		return SUCCESS;
	}

}
