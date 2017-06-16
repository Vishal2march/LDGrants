package gov.nysed.oce.ldgrants.grants.common.domain;


public class ProjectNarrative {
	
	private Long id;
	private String narrative;
	private Long narrativeTypeId;
	private byte[] narrativeClob;
	private Long categoryTypeId;
	private String statisticDescription;
	private Long grantId;
	
	private NarrativeType narrativeType;
	
		
	private Long projNarrId;
	private String narrativeDescr;
	
	
	public Long getProjNarrId() {
		return projNarrId;
	}
	public void setProjNarrId(Long projNarrId) {
		this.projNarrId = projNarrId;
	}
	public String getNarrativeDescr() {
		return narrativeDescr;
	}
	public void setNarrativeDescr(String narrativeDescr) {
		this.narrativeDescr = narrativeDescr;
	}
	public Long getNarrativeTypeId() {
		return narrativeTypeId;
	}
	public void setNarrativeTypeId(Long narrativeTypeId) {
		this.narrativeTypeId = narrativeTypeId;
	}
	
	
	public byte[] getNarrativeClob() {
		return narrativeClob;
	}
	public void setNarrativeClob(byte[] narrativeClob) {
		this.narrativeClob = narrativeClob;
	}
	
	public Long getCategoryTypeId() {
		return categoryTypeId;
	}
	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	
	public String getStatisticDescription() {
		return statisticDescription;
	}
	public void setStatisticDescription(String statisticDescription) {
		this.statisticDescription = statisticDescription;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNarrative() {
		return narrative;
	}
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	public NarrativeType getNarrativeType() {
		return narrativeType;
	}
	public void setNarrativeType(NarrativeType narrativeType) {
		this.narrativeType = narrativeType;
	}
	
	
	


	

}
