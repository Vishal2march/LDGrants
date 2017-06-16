package gov.nysed.oce.ldgrants.email.action;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.email.domain.RecipientGroup;
import gov.nysed.oce.ldgrants.email.service.SedemsRecipientService;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.enumeration.ApprovalType;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.FiscalYearService;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;

public class SedemsRecipientAction extends LDGrantSessionManager{
	
	public SedemsRecipientService sedemsService = new SedemsRecipientService();
	public FiscalYearService fyService = new FiscalYearService();
	
	
	private ApprovalType approvalType;
	private List<String> approvalTypes = new ArrayList();
	private List<FiscalYear> fiscalYears = new ArrayList();
	private Long fyCode;
	private Long fcCode;
	private List<Grant> grants = new ArrayList();
	private RecipientGroup recipientGroup;
	
	
	public String loadRecipientPage() throws Exception 
	  {       	     
	      try{                   
	                   
	        System.out.println("loadRecipient()");
	       	  
	        //get all FY's for dropdown search
	        fiscalYears = fyService.selectAll();
	        System.out.println("fy size "+fiscalYears.size());
	        System.out.println("fc "+fcCode);
	                          
	      }catch(Exception e){
	    	  System.err.println("error SedemsRecipientAction.loadRecipientPage() "+e.getMessage().toString());
	      }
	    return SUCCESS;        
	  }

	
	
	
	public String searchRecipients() throws Exception 
	{
		try{
			System.out.println("searchRecipient()");
			
			System.out.println("fy "+fyCode);
	        System.out.println("fc "+fcCode);
	        System.out.println("approval "+approvalType);
	        
			
	        //service will query for all matching grants
		    grants = sedemsService.generateGrantList(fyCode, fcCode, approvalType);
		    
		    //for list of grants; query for selected recipient group
		    //TODO: this gets single recipient group; should change to query multiple groups
		    grants = sedemsService.generatePotentialRecipientList(grants, recipientGroup);
		       
		    
		    //get all FY's for dropdown search
	        fiscalYears = fyService.selectAll();
			
		}catch(Exception e){
			System.err.println("error SedemsRecipientAction.searchRecipients() "+e.getMessage());
		}
		return SUCCESS;
		
	}
	
	
	
	
	
	
	
	
	

	public ApprovalType getApprovalType() {
		return approvalType;
	}


	public void setApprovalType(ApprovalType approvalType) {
		this.approvalType = approvalType;
	}


	public List<String> getApprovalTypes() {
		approvalTypes = new ArrayList();
		approvalTypes.add("APPROVE");
		approvalTypes.add("DENIED");
		return approvalTypes;
	}


	public void setApprovalTypes(List<String> approvalTypes) {
		this.approvalTypes = approvalTypes;
	}














	public List<FiscalYear> getFiscalYears() {
		return fiscalYears;
	}














	public void setFiscalYears(List<FiscalYear> fiscalYears) {
		this.fiscalYears = fiscalYears;
	}














	public Long getFyCode() {
		return fyCode;
	}














	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}




	public Long getFcCode() {
		return fcCode;
	}




	public void setFcCode(Long fcCode) {
		this.fcCode = fcCode;
	}




	public List<Grant> getGrants() {
		return grants;
	}




	public void setGrants(List<Grant> grants) {
		this.grants = grants;
	}




	public RecipientGroup getRecipientGroup() {
		return recipientGroup;
	}




	public void setRecipientGroup(RecipientGroup recipientGroup) {
		this.recipientGroup = recipientGroup;
	}      
			    
	
	

}
