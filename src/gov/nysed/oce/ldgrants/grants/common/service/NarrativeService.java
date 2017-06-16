package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.NarrativeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FormTypeEnum;
import gov.nysed.oce.ldgrants.user.domain.User;


public class NarrativeService {

	private NarrativeDao narrativeDao = new NarrativeDao();
			
	
	
	public List<NarrativeType> getFinalYr1NarrativeTypesForFyFund(Long fyCode, Long fcCode){
		return narrativeDao.searchNarrativeTypesByVersion(fyCode, fcCode, FormTypeEnum.FinalYear1.getFormTypeId());
	}
	
	public List<NarrativeType> getFinalSummaryNarrativeTypesForFyFund(Long fyCode, Long fcCode){
		return narrativeDao.searchNarrativeTypesByVersion(fyCode, fcCode, FormTypeEnum.FinalSummary.getFormTypeId());
	}
	
	
	public List<NarrativeType> selectAllNarratives() {
		return narrativeDao.selectAllNarrativeType();
	}

	/**
	 * Retrieves list of project Narratives and its narrative type
	 * 
	 * @param grantid
	 * @return List<ProjectNarrative> returns a list of project narratives
	 */
	public List<ProjectNarrative> getProjectNarratives(Long grantId) {

		List<ProjectNarrative> projectNarrativeList = narrativeDao.getAllProjectNarratives(grantId);

		int i = 0;
		for (ProjectNarrative pNarrBean : projectNarrativeList) {
			projectNarrativeList.get(i).setNarrativeType(narrativeDao.getNarrativeType(pNarrBean.getNarrativeTypeId()));
			i++;
		}

		return projectNarrativeList;

	}


	
		
	
	public ProjectNarrative getNarrativeAndInstruction(Long grantId, Long narrativeTypeId){
		
		//get user narrative
		ProjectNarrative narrative = narrativeDao.searchNarrativeByGrantType(grantId, narrativeTypeId);
		
		//get narrative instructions/title
		NarrativeType instruction = narrativeDao.selectNarrativeType(narrativeTypeId);
		
		//set all fields to narrative object
		narrative.setNarrativeType(instruction);
		
		return narrative;
	}

	
	
	public void saveNarrative(User user, Long grantId, ProjectNarrative narrative){
		
		narrative.setGrantId(grantId);
		
		try{
			if(narrative.getId()==null){//if no narrative id; do insert emptyClob()
				
				narrative.setId(narrativeDao.getNextProjectNarrativeId());
				
				narrativeDao.insertNarrative(narrative, user);
				//is this needed?  can clob be inserted in 1 step?
				
				//then update with user narrative
				narrativeDao.updateNarrative(narrative, user);
								
			}else//why isn't this updating based on id?
				narrativeDao.updateNarrative(narrative, user);
			
						
		}catch(Exception e){
			System.err.println("error NarrativeService.saveNarrative "+e.getMessage());
		}
		
	}
	
	

	public void deleteNarrative(Grant grant, ProjectNarrative projectNarrative) {
		narrativeDao.deleteNarrative(grant.getId(), projectNarrative.getNarrativeTypeId());
	}
	
	
	
	

}
