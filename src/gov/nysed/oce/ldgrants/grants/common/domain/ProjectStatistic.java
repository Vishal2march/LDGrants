package gov.nysed.oce.ldgrants.grants.common.domain;

public class ProjectStatistic {
	
	private Long id;
	private String statisticDescription;
	private Long statisticTypeId;	
	private Long categoryTypeId;
	private Long grantId;
	
	private StatisticType statisticType;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatisticDescription() {
		return statisticDescription;
	}
	public void setStatisticDescription(String statisticDescription) {
		this.statisticDescription = statisticDescription;
	}
	public Long getStatisticTypeId() {
		return statisticTypeId;
	}
	public void setStatisticTypeId(Long statisticTypeId) {
		this.statisticTypeId = statisticTypeId;
	}
	public Long getCategoryTypeId() {
		return categoryTypeId;
	}
	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public StatisticType getStatisticType() {
		return statisticType;
	}
	public void setStatisticType(StatisticType statisticType) {
		this.statisticType = statisticType;
	}
	
}
