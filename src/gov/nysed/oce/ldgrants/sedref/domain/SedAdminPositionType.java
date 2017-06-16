package gov.nysed.oce.ldgrants.sedref.domain;

import gov.nysed.oce.ldgrants.grants.address.domain.ContactType;

public enum SedAdminPositionType {
	
	CEO(1),
	ADDITIONAL_CONTACT(4),
	PRESERVATION_PROGRAM_OFFICER(16);
	
	
	private final long adminTypeId;
	
	
	SedAdminPositionType(long adminTypeId){
		this.adminTypeId = adminTypeId;
	}
	
	public long getAdminTypeId(){
		return adminTypeId;
	}
	
	
	
	
	public static SedAdminPositionType searchByAdminTypeId(int id){
		SedAdminPositionType adminType=null;
		
		switch(id){
			case 1:
				adminType = CEO;
				break;
			case 4:
				adminType = ADDITIONAL_CONTACT;
				break;
			case 16:
				adminType = PRESERVATION_PROGRAM_OFFICER;
				break;
		}
		return adminType;
	}
	
}
