package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectStatistic;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface NarrativeDaoInt extends DatabaseConnectionInt {
	
	public Long getNextProjectNarrativeId();

	public long insertNarrative(ProjectNarrative narrative, User user);

	public boolean deleteNarrative(Long grantid, Long narrTypeId);

	public long updateNarrative(ProjectNarrative narrative, User user);
	
	public ProjectNarrative searchNarrativeByGrantType(Long grantId, Long narrativeTypeId);
	
	public List<ProjectNarrative> getAllProjectNarratives(Long grantId);
	
	public NarrativeType getNarrativeType(Long narrTypeId);
    
	public List<NarrativeType> selectNarrativeByCategoryId(Long categoryId);
	
	public List<NarrativeType> selectAllNarrativeType();
	public List<NarrativeType> searchNarrativeTypesByVersion(Long fyCode, Long fcCode, Long formTypeId);
	
	public NarrativeType selectNarrativeType(Long narrativeTypeId);
	
	
	
	public long insertStatistic(ProjectStatistic statistic, User user);
	
	public long updateStatistic(ProjectStatistic statistic, User user);
		
	public List<ProjectStatistic> searchStatisticsByVersionGrant(Long grantId, Long fyCode, Long fcCode, Long formTypeId);
}
