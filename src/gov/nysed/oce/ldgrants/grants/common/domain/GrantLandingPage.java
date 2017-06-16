package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.ArrayList;
import java.util.List;

public class GrantLandingPage {

	private Institution institution;
	private List<Grant> grantList = new ArrayList<Grant>();
	
	
	public GrantLandingPage() {
		// TODO Auto-generated constructor stub
	}



	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}



	public List<Grant> getGrantList() {
		return grantList;
	}



	public void setGrantList(List<Grant> grantList) {
		this.grantList = grantList;
	}







}
