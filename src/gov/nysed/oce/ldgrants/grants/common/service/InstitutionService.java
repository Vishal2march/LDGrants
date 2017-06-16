package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.Vector;

import gov.nysed.oce.ldgrants.grants.common.dao.InstitutionDao;
import gov.nysed.oce.ldgrants.grants.common.domain.AdminPosition;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;

public class InstitutionService {

	private InstitutionDao instDao = new InstitutionDao();

	public Institution selectInstitution(Long instId) {
		return instDao.selectInstitution(instId);
	}

	public AdminPosition getAdminPositionDetail(Long grantId, String officer_type) {

		// query sedref sed_admin_positions
		AdminPosition adminPosition = instDao.getAdminPositionAssigned(grantId, officer_type);

		// query sedref sed_contact_infos
		adminPosition = instDao.getAdminPositionContacts(adminPosition);
		adminPosition.setPhone(parsePhone(adminPosition.getPhone()));

		return adminPosition;
	}

	public String parsePhone(String phone) {
		String tmpString = "";
		try {
			
			if(phone!=null  && !phone.equals("")){

				char[] phString = phone.toCharArray();// convert string to array of
														// char
				Vector newphoneString = new Vector();
	
				// loop on all items in the old string array
				for (int i = 0; i < phString.length; i++) {
					switch (i) {
					case 0:
						newphoneString.add("(");
						break;
					case 3:
						newphoneString.add(")");
						break;
					case 6:
						newphoneString.add("-");
						break;
					}
					newphoneString.add(new Character(phString[i]));// cannot add
																	// char to
																	// vector - must
																	// wrap in a
																	// character
																	// object
				}
	
				// now convert all the chars in the vector back to a string
				for (int i = 0; i < newphoneString.size(); i++) {
					tmpString += newphoneString.get(i);
				}
			}

		} catch (Exception e) {
			System.out.println("InstitutionService.parsePhone: " + e.getMessage());
			return null;
		}

		return tmpString;
	}
}
