package gov.nysed.oce.ldgrants.grants.common.enumeration;

public enum ApprovalType {

	APPROVE,
	DENIED;
	
	
	
	public static ApprovalType searchByString(String version){
		ApprovalType approvalType=null;
		
		switch(version){
			case "Approve":
				approvalType = APPROVE;
				break;
			case "Denied":
				approvalType = DENIED;
				break;			
		}
		return approvalType;
	}
	
}
