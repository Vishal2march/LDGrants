package gov.nysed.oce.ldgrants.grants.address.domain;


public enum ContactType {

	HOME_PHONE(1),
	WORK_PHONE(2),
	EMAIL(3),
	WORK_EXTENSION(8);
	
	
private final long contactTypeId;
	
	
	ContactType(long contactTypeId){
		this.contactTypeId = contactTypeId;
	}
	
	public long getContactTypeId(){
		return contactTypeId;
	}
	
	
	
	
	public static ContactType searchByContactTypeId(int id){
		ContactType contactType=null;
		
		switch(id){
			case 1:
				contactType = HOME_PHONE;
				break;
			case 2:
				contactType = WORK_PHONE;
				break;
			case 3:
				contactType = EMAIL;
				break;
			case 8:
				contactType = WORK_EXTENSION;
				break;			
		}
		return contactType;
	}
}
