package gov.nysed.oce.ldgrants.grants.literacy.action;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.AdminPosition;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FormTypeEnum;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.AuthorizationService;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.grants.common.service.GrantSubmissionService;
import gov.nysed.oce.ldgrants.grants.common.service.InstitutionService;
import gov.nysed.oce.ldgrants.grants.literacy.utils.SubmitValidationService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;
import mypackage.LitEmailNotifyBean;
import mypackage.UserBean;

public class FinalSignoffAction extends LDGrantSessionManager{

	private Grant grant = new Grant();
	private Institution institution = new Institution();
	private AdminPosition adminPosition = new AdminPosition();
	private Boolean directorSignoff;
	private GrantStatus grantStatus = new GrantStatus();
	private Boolean validationFailed;
	private boolean validExpenses;
	private List<NarrativeType> missingNarrativeTypes;
	List<String> outOfRangeCategories;
	private static final String ADULT_LITERACY = "adultlit";
	private static final String FAMILY_LITERACY = "familylit";
	
	
	
	/**
	 * This action will generate the AL/FL "final signoff" page.  This page has the "submit" 
	 * button for year1 final report.  This will also check/display validation messages and prevent
	 * submit if validation does not pass.
	 * @return
	 */
	public String literacyYr1FinalSignoff() {
		try{
		
			//create/update navigation links
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
			breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Final Report Signoff");
			
			
			//get grant id
			String grantId =ldSession.getGrantId();
						
			
			//get basic grant info
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get grant status (submit/approve/lock/etc)
			grantStatus = grantService.getGrantStatus(grant.getId());
			
			//get inst info
			InstitutionService instService = new InstitutionService();
			institution = instService.selectInstitution(grant.getInstId());		
			
			//get library director info
			adminPosition = instService.getAdminPositionDetail(grant.getId(), "Library Director");
			
			//-------------------validate required items
			
			SubmitValidationService validateService = new SubmitValidationService();
			
			//validate all narratives entered
			missingNarrativeTypes = validateService.validateRequiredNarratives(
					grant.getFyCode(), grant.getFcCode(), FormTypeEnum.FinalYear1.getFormTypeId(), grant.getId());		
					
			if(missingNarrativeTypes!=null)
				validationFailed=true;
			
			
			//validate expenses = approp for FY
			validExpenses = validateService.validateExpensesEqualAppropriation(grant);
			if(validExpenses==false)
				validationFailed=true;
			
					
			//validate each budget_summary category total <10% different from final_budget category total
			outOfRangeCategories = validateService.validateExpensesWithinRangeOfInitialBudget(
														grant.getFyCode(), grant);
			
			if(outOfRangeCategories.size()>0)
				validationFailed=true;
	        
			
			
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
			System.err.println("FinalSignoffAction.literacyYr1FinalSignoff "+ e.getMessage());
		}
		
		return SUCCESS;
	}


	
	public String submitFinalReport() {
		
		try{
			//get grant id
			String grantId =ldSession.getGrantId();
			//get user
			User user = ldSession.getUser();
			
			//insert the "checkbox checked" into AUTHORIZATIONS table
			AuthorizationService authService = new AuthorizationService();
			authService.insertFinalSignoffAuthorization(user, Long.parseLong(grantId));
			
			//insert into SUBMISSIONS table; lock final report items
			GrantSubmissionService submissionService = new GrantSubmissionService();
			submissionService.submitFinalYear1(user, Long.parseLong(grantId));
			
			//TODO: send confirm submit email - change the "email" code to use spring jdbc/service/etc.
			UserBean oldUserBean = ldSession.getLdUser();
			LitEmailNotifyBean eb = new LitEmailNotifyBean();
	        eb.sendLiSubmit(oldUserBean, Long.parseLong(grantId), true); //old/original code to send literacy email
        
		}catch(Exception e){
			System.err.println("FinalSignoffAction.submitFinalReport "+ e.getMessage());
		}
                
		//go to checklist				
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Grant getGrant() {
		return grant;
	}

	public void setGrant(Grant grant) {
		this.grant = grant;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}


	public AdminPosition getAdminPosition() {
		return adminPosition;
	}


	public void setAdminPosition(AdminPosition adminPosition) {
		this.adminPosition = adminPosition;
	}


	public Boolean getDirectorSignoff() {
		return directorSignoff;
	}



	public void setDirectorSignoff(Boolean directorSignoff) {
		this.directorSignoff = directorSignoff;
	}



	public GrantStatus getGrantStatus() {
		return grantStatus;
	}



	public void setGrantStatus(GrantStatus grantStatus) {
		this.grantStatus = grantStatus;
	}



	public Boolean getValidationFailed() {
		return validationFailed;
	}



	public void setValidationFailed(Boolean validationFailed) {
		this.validationFailed = validationFailed;
	}



	public boolean isValidExpenses() {
		return validExpenses;
	}



	public void setValidExpenses(boolean validExpenses) {
		this.validExpenses = validExpenses;
	}



	public List<NarrativeType> getMissingNarrativeTypes() {
		return missingNarrativeTypes;
	}



	public void setMissingNarrativeTypes(List<NarrativeType> missingNarrativeTypes) {
		this.missingNarrativeTypes = missingNarrativeTypes;
	}



	public List<String> getOutOfRangeCategories() {
		return outOfRangeCategories;
	}



	public void setOutOfRangeCategories(List<String> outOfRangeCategories) {
		this.outOfRangeCategories = outOfRangeCategories;
	}


}
