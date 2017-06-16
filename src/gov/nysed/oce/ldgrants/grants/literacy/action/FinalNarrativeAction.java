package gov.nysed.oce.ldgrants.grants.literacy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.grants.common.service.NarrativeService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;

public class FinalNarrativeAction extends LDGrantSessionManager{
	
	
	private Grant grant = new Grant();
	private List<NarrativeType> narrativeTypeList;
	private ProjectNarrative selectedNarrative = new ProjectNarrative();
	private Long narrativeTypeId=0L;
	private GrantStatus grantStatus = new GrantStatus();
	private static final String ADULT_LITERACY = "adultlit";
	private static final String FAMILY_LITERACY = "familylit";
	private String userPermission;
	
	
	
	/**
	 * This action will generate the AL/FL "final narrative yr1" page.  This page has 
	 * list of narrative types for this FY/Fund.
	 * @return
	 */
	public String literacyYr1FinalNarrative() {
		
		try{
			//create/update navigation links
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
			breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Final Narrative");
			
			
			//get grant id
			String grantId =ldSession.getGrantId();
			//get user
			User user = ldSession.getUser();
			
			//get basic grant info
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get all narrative_types (narrative title/instruction) for this FY/Fund
			NarrativeService narrativeService = new NarrativeService();
			narrativeTypeList = narrativeService.getFinalYr1NarrativeTypesForFyFund(grant.getFyCode(), grant.getFcCode());
			
			
			//get fund for this fcCode - navigate to page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)		{
				userPermission = user.getAdultLiteracyAccess();			
	  			return ADULT_LITERACY;			
			}
			else if (fund== FundProgram.FAMILY_LITERACY){ 				  			
				userPermission = user.getFamilyLiteracyAccess();
				return FAMILY_LITERACY;
			}
			else
				return ERROR;
			
		}catch(Exception e){
			System.err.println("FinalNarrativeAction.literacyYr1FinalNarrative "+ e.getMessage());
		}
		
		return SUCCESS;
	}
	
	
	
	public String literacySelectFinalNarrative() {
		
		try{
				//get grant id
				String grantId =ldSession.getGrantId();
				//get user
				User user = ldSession.getUser();
				
				//get narrative type user selected
				HttpServletRequest request = ServletActionContext.getRequest();
				String narrativeType = request.getParameter("narrativeTypeId");
				if(narrativeType!=null)
					narrativeTypeId = Long.parseLong(narrativeType);
							
				
				//get basic grant info
				GrantService grantService = new GrantService();
				grant = grantService.getGrant(Long.parseLong(grantId));
				
				//get all narrative_types (narrative title/instruction) for this FY/Fund
				NarrativeService narrativeService = new NarrativeService();
				narrativeTypeList = narrativeService.getFinalYr1NarrativeTypesForFyFund(grant.getFyCode(), grant.getFcCode());
						
				//get narrative for selected nt_id
				selectedNarrative = narrativeService.getNarrativeAndInstruction(grant.getId(), narrativeTypeId);
				
				//get grant status (submit/approve/lock/etc)
				grantStatus = grantService.getGrantStatus(grant.getId());
				
				//get fund for this fcCode - navigate to page
				FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
							
				if(fund== FundProgram.ADULT_LITERACY)		{
					userPermission = user.getAdultLiteracyAccess();			
		  			return ADULT_LITERACY;			
				}
				else if (fund== FundProgram.FAMILY_LITERACY){ 				  			
					userPermission = user.getFamilyLiteracyAccess();
					return FAMILY_LITERACY;
				}
				else
					return ERROR;
		
		}catch(Exception e){
			System.err.println("FinalNarrativeAction.literacySelectFinalNarrative "+ e.getMessage());
		}
				
		return SUCCESS;
	}


	
	
	public String literacySaveFinalNarrative() {
		
		try{
			//get grant id
			String grantId =ldSession.getGrantId();
			//get user
			User user = ldSession.getUser();
						
			//insert or update narrative
			NarrativeService narrativeService = new NarrativeService();
			narrativeService.saveNarrative(user, Long.parseLong(grantId), selectedNarrative);
			
										
			/////////////refresh variables and return to page
			//get basic grant info
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get all narrative_types (narrative title/instruction) for this FY/Fund		
			narrativeTypeList = narrativeService.getFinalYr1NarrativeTypesForFyFund(grant.getFyCode(), grant.getFcCode());
					
			//get narrative for selected nt_id
			selectedNarrative = narrativeService.getNarrativeAndInstruction(grant.getId(), selectedNarrative.getNarrativeTypeId());
			
			//get grant status (submit/approve/lock/etc)
			grantStatus = grantService.getGrantStatus(grant.getId());
			
			//get fund for this fcCode - navigate to page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)		{
				userPermission = user.getAdultLiteracyAccess();			
	  			return ADULT_LITERACY;			
			}
			else if (fund== FundProgram.FAMILY_LITERACY){ 				  			
				userPermission = user.getFamilyLiteracyAccess();
				return FAMILY_LITERACY;
			}
			else
				return ERROR;
		
		}catch(Exception e){
			System.err.println("FinalNarrativeAction.literacySaveFinalNarrative "+ e.getMessage());
		}
				
		return SUCCESS;
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * This action will generate the AL/FL "final summary" page.  This page has 
	 * a single textarea/narrative box
	 * @return
	 */
	public String literacyFinalSummary() {
		try{
		
			//create/update navigation links
			BreadcrumbManager breadcrumbs = new BreadcrumbManager();
			breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Final Summary");
						
			//get grant id
			String grantId =ldSession.getGrantId();
			//get user
			User user = ldSession.getUser();
						
			//get basic grant info
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get all narrative_types (narrative title/instruction) for this FY/Fund
			NarrativeService narrativeService = new NarrativeService();
			narrativeTypeList = narrativeService.getFinalSummaryNarrativeTypesForFyFund(grant.getFyCode(), grant.getFcCode());
						
			//assuming final summary only has 1 narrative type id (only 1 narrative on page per KBalsen)		
			for(NarrativeType n: narrativeTypeList){
			//get narrative for selected nt_id
			selectedNarrative = narrativeService.getNarrativeAndInstruction(grant.getId(), n.getNarrativeTypeId());
			}
					
					
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)		{
				userPermission = user.getAdultLiteracyAccess();			
	  			return ADULT_LITERACY;			
			}
			else if (fund== FundProgram.FAMILY_LITERACY){ 				  			
				userPermission = user.getFamilyLiteracyAccess();
				return FAMILY_LITERACY;
			}
			else 
				return ERROR;
			
		} catch (Exception ex) {
			System.err.println("FinalNarrativeAction.literacySaveFinalSummary() " + ex.toString());
		}
		return null;
	}
	

	
	
	
	
	/**
	 * This action will save the narrative on AL/FL "final summary" page.  
	 * @return
	 */
	public String literacySaveFinalSummary() {
		
		try{		
			//get grant id
			String grantId =ldSession.getGrantId();
			//get user
			User user = ldSession.getUser();
						
			//insert or update narrative
			NarrativeService narrativeService = new NarrativeService();
			narrativeService.saveNarrative(user, Long.parseLong(grantId), selectedNarrative);
			
										
			/////////////refresh variables and return to page
			//get basic grant info
			GrantService grantService = new GrantService();
			grant = grantService.getGrant(Long.parseLong(grantId));
			
			//get all narrative_types (narrative title/instruction) for this FY/Fund
			narrativeTypeList = narrativeService.getFinalSummaryNarrativeTypesForFyFund(grant.getFyCode(), grant.getFcCode());
			
			//assuming final summary only has 1 narrative type id (only 1 narrative on page per KBalsen)		
			for(NarrativeType n: narrativeTypeList){
			//get narrative for selected nt_id
			selectedNarrative = narrativeService.getNarrativeAndInstruction(grant.getId(), n.getNarrativeTypeId());
			}
					
			//get fund for this fcCode - navigate to home page
			FundProgram fund = FundProgram.searchByFundCode(grant.getFcCode().intValue());
						
			if(fund== FundProgram.ADULT_LITERACY)		{
				userPermission = user.getAdultLiteracyAccess();			
	  			return ADULT_LITERACY;			
			}
			else if (fund== FundProgram.FAMILY_LITERACY){ 				  			
				userPermission = user.getFamilyLiteracyAccess();
				return FAMILY_LITERACY;
			}
			else
				return ERROR;
		
		} catch (Exception ex) {
			System.err.println("FinalNarrativeAction.literacySaveFinalSummary() " + ex.toString());
		}
		return null;
		
	}

	
	

	public Grant getGrant() {
		return grant;
	}



	public void setGrant(Grant grant) {
		this.grant = grant;
	}


	public ProjectNarrative getSelectedNarrative() {
		return selectedNarrative;
	}



	public void setSelectedNarrative(ProjectNarrative selectedNarrative) {
		this.selectedNarrative = selectedNarrative;
	}



	public Long getNarrativeTypeId() {
		return narrativeTypeId;
	}



	public void setNarrativeTypeId(Long narrativeTypeId) {
		this.narrativeTypeId = narrativeTypeId;
	}



	public GrantStatus getGrantStatus() {
		return grantStatus;
	}



	public void setGrantStatus(GrantStatus grantStatus) {
		this.grantStatus = grantStatus;
	}



	public List<NarrativeType> getNarrativeTypeList() {
		return narrativeTypeList;
	}



	public void setNarrativeTypeList(List<NarrativeType> narrativeTypeList) {
		this.narrativeTypeList = narrativeTypeList;
	}



	public String getUserPermission() {
		return userPermission;
	}



	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

}
