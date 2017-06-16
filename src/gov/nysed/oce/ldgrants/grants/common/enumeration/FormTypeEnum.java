package gov.nysed.oce.ldgrants.grants.common.enumeration;

public enum FormTypeEnum {

	Initial(1),
	FinalYear1(2),
	FinalYear2(3),
	FinalYear3(4),
	Amendment(5),
	Statistics(10),
	FinalSummary(11);
	
	private final long formTypeId;
	
	
	FormTypeEnum(long formTypeId){
		this.formTypeId = formTypeId;
	}
	
	public long getFormTypeId(){
		return formTypeId;
	}
	
	
	
	
}
