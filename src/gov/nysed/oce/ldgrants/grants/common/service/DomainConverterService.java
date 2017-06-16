package gov.nysed.oce.ldgrants.grants.common.service;

import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.domain.StatisticType;
import gov.nysed.oce.ldgrants.user.domain.User;
import mypackage.CoversheetBean;
import mypackage.DescriptionBean;
import mypackage.UserBean;

public class DomainConverterService {
	private User user;
	private ProjectNarrative projectNarrative;
	private StatisticType statisticType;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User convert(UserBean userBean) {
		user.setUserId(userBean.getUserid());
		return user;
	}

	public ProjectNarrative convert(CoversheetBean cb) {
		projectNarrative.setNarrativeDescr(cb.getSummaryDesc());
		projectNarrative.setNarrativeTypeId((long) cb.getNarrTypeId());
		return projectNarrative;
	}
	
	public ProjectNarrative convert(DescriptionBean pdb){
		projectNarrative.setNarrativeDescr(pdb.getNarrative());
		projectNarrative.setNarrativeTypeId((long) pdb.getNarrTypeID());
		return projectNarrative;
	}
	
	/**
	 * NarrativeType and StatisticType both use same NARRATIVE_TYPES table.  
	 * @param nt
	 * @return
	 */
	public StatisticType convert(NarrativeType nt){
		
		statisticType = new StatisticType();
		statisticType.setCategoryTypeId(nt.getCategoryTypeId());
		statisticType.setDisplayInstruction(nt.getDisplayInstruction());
		statisticType.setDisplayName(nt.getDisplayName());
		statisticType.setSortOrder(nt.getSortOrder());
		statisticType.setStatisticName(nt.getNarrativeName());
		statisticType.setStatisticTypeId(nt.getNarrativeTypeId());
		return statisticType;
	}

}
