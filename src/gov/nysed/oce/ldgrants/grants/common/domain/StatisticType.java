package gov.nysed.oce.ldgrants.grants.common.domain;

public class StatisticType {

	private Long statisticTypeId;
	private String statisticName;
	private String displayName;
	private String displayInstruction;
	private Long sortOrder;
	private Long categoryTypeId;
	
	private ProjectStatistic answer;

	public Long getStatisticTypeId() {
		return statisticTypeId;
	}

	public void setStatisticTypeId(Long statisticTypeId) {
		this.statisticTypeId = statisticTypeId;
	}

	public String getStatisticName() {
		return statisticName;
	}

	public void setStatisticName(String statisticName) {
		this.statisticName = statisticName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayInstruction() {
		return displayInstruction;
	}

	public void setDisplayInstruction(String displayInstruction) {
		this.displayInstruction = displayInstruction;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public ProjectStatistic getAnswer() {
		return answer;
	}

	public void setAnswer(ProjectStatistic answer) {
		this.answer = answer;
	}
	
	
}
