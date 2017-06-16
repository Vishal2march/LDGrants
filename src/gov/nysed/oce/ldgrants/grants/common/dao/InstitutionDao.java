package gov.nysed.oce.ldgrants.grants.common.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.AdminPosition;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;

public class InstitutionDao implements InstitutionInt {

	public Institution selectInstitution(Long id) {

		try {

			Institution institution = jt.queryForObject(
					"select initcap(i.POPULAR_NAME) as POPULAR_NAME, initcap(a.ADDR_LINE1) as ADDR_LINE1, "
							+ " initcap(a.ADDR_LINE2) as ADDR_LINE2, initcap(a.CITY) as CITY, a.STATE_CODE, a.ZIPCD5, a.ZIPADD4, i.sed_code, "
							+ " initcap(sed_counties.description) as countyname, sed_counties.county_code "
							+ " from SED_ADDRESSES a, SED_INSTITUTIONS i "
							+ " left join sed_counties on sed_counties.COUNTY_CODE =i.county_code "
							+ " where i.INST_ID =? and " + " i.INST_ID = A.INST_ID and a.ADDR_TYPE_CODE = 1 ",
					new Object[] { id }, new BeanPropertyRowMapper<Institution>(Institution.class));
            
			institution.setLibrarySystem(isLibrarySystem(id));
			return institution;

		} catch (Exception e) {
			System.out.println("InstitutionDao.selectInstitution " + e.getMessage());
			return null;
		}
	}


	/**
	 * 
	 * query sedref admin_positions for the sed_admin_pos_id that is active in
	 * GRANT_ADMINS table
	 */
	public AdminPosition getAdminPositionAssigned(Long grantId, String officer_type) {

		try {

			List<AdminPosition> AdminPosition = jt.query(
					"select ADMIN_POS_ID, initcap(TITLE) as TITLE, initcap(FNAME) as FNAME, initcap(MI) as MI, "
							+ " initcap(LNAME) as LNAME, initcap(SED_SALUTATIONS.DESCRIPTION) as SALUT "
							+ " from SED_ADMIN_POSITIONS, SED_SALUTATIONS "
							+ " where SED_ADMIN_POSITIONS.SAL_CODE = SED_SALUTATIONS.SAL_CODE and "
							+ " SED_ADMIN_POSITIONS.ADMIN_POS_ID in "
							+ " (select ADMIN_POS_ID from GRANT_ADMINS where GRA_ID=? "
							+ " and TITLE=? and END_DATE is null)",
					new Object[] { grantId, officer_type }, new BeanPropertyRowMapper(AdminPosition.class));

			return AdminPosition.get(0);

		} catch (Exception e) {
			System.out.println("InstitutionDao.getAdminPositionAssigned " + e.getMessage());
			return null;
		}
	}

	public AdminPosition getAdminPositionContacts(AdminPosition adminPosition) {

		try {

			List queryList = jt.queryForList("select * from SED_CONTACT_INFO where ADMIN_POS_ID=?",
					new Object[] { adminPosition.getAdminPosId() });

			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);

				if (queryMap.get("CONTACT_TYPE_CODE").equals("1"))
					adminPosition.setPhone((String) queryMap.get("contact_value"));
				else if (queryMap.get("CONTACT_TYPE_CODE").equals("3"))
					adminPosition.setEmail((String) queryMap.get("contact_value"));
			}

		} catch (Exception e) {
			System.out.println("InstitutionDao.getAdminPositionContacts " + e.getMessage());
			return null;
		}
		return adminPosition;
	}
	
	private boolean isLibrarySystem(long instId) {
		//@formatter:off
		String sql = "/* Formatted on 1/31/2017 11:55:01 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT * " +
				"  FROM LDSTATEAID.LIBRARY_SYSTEM_MAPPINGS " +
				" WHERE INST_ID_HAS = ? "; 

		try {

			List queryList = jt.queryForList(sql,
					new Object[] {instId});

			for (int i = 0; i < queryList.size(); i++) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("InstitutionDao.isLibrarySystem " + e.getMessage());
			return false;
		}
		return false;
		
	}

}
