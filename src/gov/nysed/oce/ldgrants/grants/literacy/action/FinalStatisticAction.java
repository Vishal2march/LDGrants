package gov.nysed.oce.ldgrants.grants.literacy.action;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.CategoryType;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectStatistic;
import gov.nysed.oce.ldgrants.grants.common.domain.StatisticType;
import gov.nysed.oce.ldgrants.grants.common.service.GrantService;
import gov.nysed.oce.ldgrants.grants.common.service.StatisticService;
import gov.nysed.oce.ldgrants.shared.BreadcrumbManager;
import gov.nysed.oce.ldgrants.shared.BreadcrumbPage;
import gov.nysed.oce.ldgrants.shared.LDGrantSessionManager;
import gov.nysed.oce.ldgrants.user.domain.User;

public class FinalStatisticAction extends LDGrantSessionManager{
	
	 
	private Grant grant = new Grant();
	private List<CategoryType> categoryList;
	private GrantStatus grantStatus = new GrantStatus();
	
	
	
	/**
	 * This action will generate the FL "statistics yr1" page.  Query for all statistic_types(question_types)
	 *  for given fund/FY.  Statistic_types reuses the NARRATIVE_TYPES table.
	 *  Also query PROJECT_NARRATIVES(answers) table for user answers/values to the statistc_type questions.
	 * @return
	 */
	public String literacyYr1Statistics() {
		
		//create/update navigation links
		BreadcrumbManager breadcrumbs = new BreadcrumbManager();
		breadcrumbs.createBreadcrumb(BreadcrumbPage.FORM, "Statistics");
		
		
		//get grant id
		String grantId =ldSession.getGrantId();
				
		//get basic grant info
		GrantService grantService = new GrantService();
		grant = grantService.getGrant(Long.parseLong(grantId));
		
		//get grant status (submit/approve/lock/etc)
		grantStatus = grantService.getGrantStatus(grant.getId());
		
		//get all narrative_types (statistic_types stored in narrative_types table) for this FY/Fund
		//these are the statistic "questions" that generate the form				
		//get all project_narratives("statistic answers").  
		//these are the user answers (if any) statistic questions				
		//combine questions/answers
		StatisticService statisticService = new StatisticService();
		categoryList = statisticService.getFinalYr1StatisticQuestionAnswerList(grant.getId(), grant.getFyCode(), grant.getFcCode());
				
		return SUCCESS;
	}
	
	
	
	
	public String literacySaveStatistics() {
		
		//get grant id
		String grantId =ldSession.getGrantId();
		//get user
		User user = ldSession.getUser();
								
		
		//DEBUG PRINTING TO CONSOLE
	/*	System.out.println(categoryList.size());
		for(CategoryType ct: categoryList){
						
				//get all associated questions)
				List<StatisticType> narrativeTypes = ct.getStatisticTypes();
				StatisticType nt = narrativeTypes.get(0);
				System.out.println("nt_id "+ nt.getStatisticTypeId());
				
				ProjectStatistic ans = narrativeTypes.get(0).getAnswer();				
				System.out.println(ans.getStatisticDescription());						
		}    */				
		
		
		//insert or update each statistic 
		StatisticService statisticService = new StatisticService();
		statisticService.saveStatistic(user, Long.parseLong(grantId), categoryList);
		
											
		/////////////refresh variables and return to page
		//get basic grant info
		GrantService grantService = new GrantService();
		grant = grantService.getGrant(Long.parseLong(grantId));
				
		//get grant status (submit/approve/lock/etc)
		grantStatus = grantService.getGrantStatus(grant.getId());
		
				
		//get questions/answers for statistics form
		categoryList = statisticService.getFinalYr1StatisticQuestionAnswerList(grant.getId(), grant.getFyCode(), grant.getFcCode());
								
		return SUCCESS;
	}

	
	
	
	public Grant getGrant() {
		return grant;
	}
	public void setGrant(Grant grant) {
		this.grant = grant;
	}

	public GrantStatus getGrantStatus() {
		return grantStatus;
	}
	public void setGrantStatus(GrantStatus grantStatus) {
		this.grantStatus = grantStatus;
	}

	public List<CategoryType> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryType> categoryList) {
		this.categoryList = categoryList;
	}
	
   
}
