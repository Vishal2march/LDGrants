package gov.nysed.oce.ldgrants.grants.help.domain;

public enum LocationDescription {

	WELCOME_PAGE(1),
	ADULT_LITERACY_PAGE(2),
	FAMILY_LITERACY_PAGE(3)
	;
	private final long locationId;
	
	LocationDescription(long locationId){
		this.locationId = locationId;
	}
	
	public long getLocationId(){
		return locationId;
	}
	
	
	public static LocationDescription searchByLocationId (int id){
		
		LocationDescription locationDescription = null;
		
		switch(id){
		case 1:
			locationDescription = WELCOME_PAGE;
		break;
		case 2:
			locationDescription = ADULT_LITERACY_PAGE;
		break;
		case 3:
			locationDescription = FAMILY_LITERACY_PAGE;
		break;
		}
		
		
		return locationDescription;
		
	}
}
