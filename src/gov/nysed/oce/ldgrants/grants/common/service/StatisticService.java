package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gov.nysed.oce.ldgrants.grants.common.dao.CategoryDao;
import gov.nysed.oce.ldgrants.grants.common.dao.NarrativeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.CategoryType;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectStatistic;
import gov.nysed.oce.ldgrants.grants.common.domain.StatisticType;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FormTypeEnum;
import gov.nysed.oce.ldgrants.user.domain.User;

public class StatisticService {
	
	private NarrativeDao narrativeDao = new NarrativeDao();
	private CategoryDao categoryDao = new CategoryDao();
	
	
	
public List<CategoryType> getFinalYr1StatisticTypesSubTypesForFyFund(Long fyCode, Long fcCode){
		
		//query all category types (will filter for categories based on version(fy/fund/formtype))
		List<CategoryType> categoryTypes = categoryDao.searchCategoriesForVersion(fyCode, fcCode, FormTypeEnum.Statistics.getFormTypeId());
		
				
		//this queries narrative_types table for all nt's for given version(fy/fund/formtype)
		List<NarrativeType> statisticTypes = narrativeDao.searchNarrativeTypesByVersion(fyCode, fcCode, FormTypeEnum.Statistics.getFormTypeId());
		
			
		//class that converts narrative_type to statistic_type
		DomainConverterService converterService = new DomainConverterService();
		
		//loop on categories
		for(CategoryType category: categoryTypes){
			//search for narrative types that use this category; add to list			
			//goal: list of ct's...with all nt's under it
			
			
			//loop on statistics.  if cat_id is the same; this statistic belongs to this category
			for(NarrativeType narrativeType: statisticTypes){
				
				
				//if(narrativeType.getCategoryTypeId()== category.getCategoryTypeId()){	
				if(narrativeType.getCategoryTypeId().longValue() == category.getCategoryTypeId().longValue()){	
					
					//convert narrative_type to statistic_type
					StatisticType statistic = converterService.convert(narrativeType);
					//add to category
					category.getStatisticTypes().add(statistic);
									
				}
			}
		}	
		return categoryTypes;
	}
	


	public List<ProjectStatistic> getFinalYr1StatisticAnswersForFyFund(Long grantId, Long fyCode, 
																	Long fcCode) {
				
		//get all entries in the PROJECT_NARRATIVES table (answers) for the given version (fy/fc/ft_id)
		return narrativeDao.searchStatisticsByVersionGrant(grantId, fyCode, fcCode, FormTypeEnum.Statistics.getFormTypeId());
				
	}
	
	
	
	/**
	 * This combines 2 separate service calls into 1:
	 * getFinalYr1StatisticTypesSubTypesForFyFund() which queries for statistic questions
	 * getFinalYr1StatisticAnswersForFyFund() which queries for statistic answers
	 * Then combine the 2 lists into single list to generate data entry form on jsp
	 * @param grantId
	 * @param fyCode
	 * @param fcCode
	 * @return
	 */
	public List<CategoryType> getFinalYr1StatisticQuestionAnswerList(Long grantId, Long fyCode, 
			Long fcCode) {
		
		List<CategoryType> questionAnswerList = null;
		
		//get all narrative_types (statistic_types stored in narrative_types table) for this FY/Fund
		//these are the statistic "questions" that generate the form
		List<CategoryType> categoryList = this.getFinalYr1StatisticTypesSubTypesForFyFund(
								fyCode, fcCode);
		
		
		//get all project_narratives("statistic answers").  
		//these are the user answers (if any) statistic questions
		List<ProjectStatistic> answerList = this.getFinalYr1StatisticAnswersForFyFund(
												grantId, fyCode, fcCode);
		////////////////////////////////////////////////////////////
		
		
		
		//combine questions/answers
		//loop on all categories (high level questions)
		for(CategoryType category: categoryList){
			
			
			//get all associated statistic_types (actual questions)
			List<StatisticType> statisticTypes = category.getStatisticTypes();
			
			
			//loop on all questions for this category
			for(StatisticType statisticType: statisticTypes){
				
				//search statistic answers for this nat_id
				for(ProjectStatistic answer: answerList){
									
					//is answer nat_id = category nat_id??
					//if(statisticType.getStatisticTypeId().equals(answer.getStatisticType().getStatisticTypeId())){
					if(statisticType.getStatisticTypeId().longValue()==answer.getStatisticType().getStatisticTypeId().longValue()){
							
						
						//set this answer to narrative type
						statisticType.setAnswer(answer);
					}
				}
				
			}
		}
		questionAnswerList = categoryList;
		//System.out.println("StatisticService3 questionAnswerList.size="+questionAnswerList.size());
				
		
	/*	DEBUG PRINTING TO CONSOLE
	 * for(CategoryType category: categoryList){
			
			if(category.getCategoryTypeId()==3L){
				//get all associated narrative_types (actual questions)
				List<NarrativeType> narrativeTypes = category.getNarrativeTypes();
				System.out.println("num nts "+ narrativeTypes.size());
				
				ProjectNarrative ans = narrativeTypes.get(0).getAnswer();
				if(ans==null)
					System.out.println("NULL");
				System.out.println(ans.getStatisticDescription());
				
			}
			
		}*/
		return questionAnswerList;
					
	}
	
	
	
	
	
	public void saveStatistic(User user, Long grantId, List<CategoryType> categoryList){
		
		try{
			
			for(CategoryType category: categoryList){
				
				//get all associated statistic_types (actual questions)
				List<StatisticType> statisticTypes = category.getStatisticTypes();
				
				//loop on statistic types
				for(StatisticType statisticType: statisticTypes){
					
					//get answer 
					ProjectStatistic answer = statisticType.getAnswer();
					answer.setStatisticTypeId(statisticType.getStatisticTypeId());
											
					if(answer.getId()==null){//if no narrative id; do insert statistic
						this.insertStatistic(user, grantId, answer);
																
					}else
						this.updateStatistic(user, grantId, answer);
					}
					
				}
			
		}catch(Exception e){
			System.err.println("error StatisticService.saveStatistic "+e.getMessage());
		}
	}



	public void insertStatistic(User user, Long grantId, ProjectStatistic statistic){
		
		statistic.setId(narrativeDao.getNextProjectNarrativeId());
		statistic.setGrantId(grantId);		
		
		narrativeDao.insertStatistic(statistic, user);
	}
	
	
	
	
	public void updateStatistic(User user, Long grantId, ProjectStatistic statistic){
		
			statistic.setGrantId(grantId);
			
			narrativeDao.updateStatistic(statistic, user);			
	}
	
	

}
