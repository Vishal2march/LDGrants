package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoryType {

	private Long categoryTypeId;
	private String categoryType;
	private String displayName;
	private String sortOrder;
	
	private NarrativeType narrativeType;
	private List<NarrativeType> narrativeTypes = new ArrayList<NarrativeType>();//category_type can have multiple narrative_types
	private List<StatisticType> statisticTypes = new ArrayList<StatisticType>();//same as narrTypes above; just different name
	
	
	public Long getCategoryTypeId() {
		return categoryTypeId;
	}
	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public NarrativeType getNarrativeType() {
		return narrativeType;
	}
	public void setNarrativeType(NarrativeType narrativeType) {
		this.narrativeType = narrativeType;
	}
	public List<NarrativeType> getNarrativeTypes() {
		return narrativeTypes;
	}
	public void setNarrativeTypes(List<NarrativeType> narrativeTypes) {
		this.narrativeTypes = narrativeTypes;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public List<StatisticType> getStatisticTypes() {
		return statisticTypes;
	}
	public void setStatisticTypes(List<StatisticType> statisticTypes) {
		this.statisticTypes = statisticTypes;
	}
	
	
	
}
