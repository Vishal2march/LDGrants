package gov.nysed.oce.ldgrants.grants.common.enumeration;

public enum SubmitApproveVersion {
	
	Initial(1),
	Final(2),
	FinalYear2(3),
	FinalYear3(4),
	Amendment(5);
	
	private final long formTypeId;	
	
	
	//constructor for SubmitApproveVersion
	SubmitApproveVersion(long formTypeId){
		this.formTypeId = formTypeId;
	}
	
	
	
	public long getFormTypeId() {
		return formTypeId;
	}
	
	public static SubmitApproveVersion searchByVersionString(String version){
		SubmitApproveVersion versionType=null;
		
		switch(version){
			case "Initial":
				versionType = Initial;
				break;
			case "Final":
				versionType = Final;
				break;
			case "Final Year2":
				versionType = FinalYear2;
				break;
			case "Final Year3":
				versionType = FinalYear3;
				break;
			case "Amendment":
				versionType = Amendment;
				break;			
		}
		return versionType;
	}
	
	
	
	
	public static SubmitApproveVersion searchByFormTypeId(Long formTypeId){
		SubmitApproveVersion versionType=null;
		
		switch(formTypeId.intValue()){
			case 1:
				versionType = Initial;
				break;
			case 2:
				versionType = Final;
				break;
			case 3:
				versionType = FinalYear2;
				break;
			case 4:
				versionType = FinalYear3;
				break;
			case 5:
				versionType = Amendment;
				break;			
		}
		return versionType;
	}
	

}
