package gov.nysed.oce.ldgrants.grants.literacy.action;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.AdminPosition;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
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

public class AmendmentSignoffAction extends LDGrantSessionManager{
	
	
	private Grant grant = new Grant();
	private Institution institution = new Institution();
	private AdminPosition adminPosition = new AdminPosition();
	private Boolean directorSignoff;
	private GrantStatus grantStatus = new GrantStatus();
	private String userPermission;
	private Boolean amendmentLocked;
	
	 private static final String ADULT_LITERACY = "adultlit";
     private static final String FAMILY_LITERACY = "familylit";
	
	
	
	
	/**
	 * This action will generate the AL/FL "amendment submit" page.  This page has the "submit" 
	 * button for amendments.  
	 * @return
	 */
	public String amendmentSignoff() {
		
		try{		
				//create/update navigation links
				BreadcrumbManager breadcrumbs = new BreadcrumbManager();
				breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Amendment Signoff");
								
				//get grant id
				String grantId =ldSession.getGrantId();							
				
				//get basic grant info
				GrantService grantService = new GrantService();
				grant = grantService.getGrant(Long.parseLong(grantId));
				//amendment lock is in GRANTS TABLE, grant object
				amendmentLocked = grant.getFs10aLocked();
				//System.out.println("fs10a lock "+ grant.getFs10aLocked());
				ldSession.setGrant(grant);//update grant in session; might be locked now
				
				//get grant status (submit/approve/lock/etc for everything besides amendments)
				grantStatus = grantService.getGrantStatus(grant.getId());
				
				//get inst info
				InstitutionService instService = new InstitutionService();
				institution = instService.selectInstitution(grant.getInstId());		
				
				//get library director info
				adminPosition = instService.getAdminPositionDetail(grant.getId(), "Library Director");
								
				//get user
				User user = ldSession.getUser();				
				
				//get fund for this fcCode - navigate to home page
				FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
					
				if(fund== FundProgram.ADULT_LITERACY){
					userPermission = user.getAdultLiteracyAccess();
					return ADULT_LITERACY;  		
				}
				else if (fund== FundProgram.FAMILY_LITERACY) {
					userPermission = user.getFamilyLiteracyAccess();
					return FAMILY_LITERACY;		
				}
				else 
					return ERROR;
		
		}catch(Exception e){
			System.err.println("AmendmentSignoffAction.amendmentSignoff "+e.getMessage());
			return ERROR;
		}
	}

	
	
	
	
public String submitAmendment() {
		
	try{
		//get grant id
		String grantId =ldSession.getGrantId();
		//get user
		User user = ldSession.getUser();
		
		//insert the "checkbox checked" into AUTHORIZATIONS table
		AuthorizationService authService = new AuthorizationService();
		authService.insertAmendmentAuthorization(user, Long.parseLong(grantId));
		
		//get basic grant info
		GrantService grantService = new GrantService();
		grant = grantService.getGrant(Long.parseLong(grantId));
		
		//insert into SUBMISSIONS table
		GrantSubmissionService submissionService = new GrantSubmissionService();
		submissionService.submitAmendment(user, grant);
		
		//TODO: send confirm submit email - change the "email" code to use spring jdbc/service/etc.
		UserBean oldUserBean = ldSession.getLdUser();
		LitEmailNotifyBean eb = new LitEmailNotifyBean();
		eb.sendLiAmendmentSubmit(oldUserBean, Long.parseLong(grantId));//old/original code to send emails
                
		//refresh page
		
	}catch(Exception e){
		System.err.println("AmendmentSignoffAction.submitAmendment() "+e.getMessage());
	}
				
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



	public static String getAdultLiteracy() {
		return ADULT_LITERACY;
	}




	public static String getFamilyLiteracy() {
		return FAMILY_LITERACY;
	}




	public String getUserPermission() {
		return userPermission;
	}




	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}





	public Boolean isAmendmentLocked() {
		return amendmentLocked;
	}





	public void setAmendmentLocked(Boolean amendmentLocked) {
		this.amendmentLocked = amendmentLocked;
	}

	
	
	
	

}
