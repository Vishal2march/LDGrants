package gov.nysed.oce.ldgrants.grants.address.domain;

public enum StaffType {

	RMO(1),
	RAO(2),
	LITERACY_CONTACT(3),
	LIBRARY_DIRECTOR(4),
	ADDITIONAL_CONTACT(5);
	
	
	private final long staffTypeId;
	
	StaffType(long staffTypeId){
		this.staffTypeId = staffTypeId;
	}
	
	public long getStaffTypeId(){
		return staffTypeId;
	}
	
	
	
	
	public static StaffType searchByStaffTypeId(int id){
		StaffType staffType=null;
		
		switch(id){
			case 1:
				staffType = RMO;
				break;
			case 2:
				staffType = RAO;
				break;
			case 3:
				staffType = LITERACY_CONTACT;
				break;
			case 4:
				staffType = LIBRARY_DIRECTOR;
				break;			
			case 5:
				staffType = ADDITIONAL_CONTACT;
				break;
		}
		return staffType;
	}
	
}
