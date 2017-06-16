package gov.nysed.oce.ldgrants.grants.common.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.Travel;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.user.domain.User;

public class GrantDao implements GrantInt {

	public Grant selectGrant(Long id) {

		try {

			Grant Grant = jt.queryForObject(
					"SELECT grants.id as id, inst_id, NAME, fc_code, fy_code,  "
							+ " proj_seq_num, contract_num, fiscal_years.description as fiscalYear, "
							+ " amount_req as ldacAppropriationAmount, grants.FS10A_YN as fs10aLocked"
							+ " FROM grants, co_institutions, fiscal_years, fund_codes "
							+ " WHERE grants.ID = ? AND is_lead = 'Y' AND grants.ID = co_institutions.gra_id "
							+ " AND grants.fy_code = fiscal_years.code AND grants.fc_code = fund_codes.code",
					new Object[] { id }, new BeanPropertyRowMapper<Grant>(Grant.class));

			return Grant;

		} catch (Exception e) {
			System.err.println("GrantDao.selectGrant() " + e.getMessage());
			return null;
		}
	}
	
	
	
	
	
	
	/**
	 * Update GRANT table upon final report submission to lock fields. Might not be needed 
	 * after VERSIONS table implementation.
	 * @param user
	 * @param grantId
	 * @return
	 */
	public long updateGrantForFinalSubmit(Long grantId, User user) {
		long rows = 0;
		String update = "update GRANTS set FINAL_NARR_COMP =1, FINAL_BUDGET_COMP =1, "+
              " SIGNOFF_COMP=1, LOCK_APP=1, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?";
		try {
			rows = jt.update(update,
					new Object[] { user.getModifiedBy(), grantId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error GrantDaoupdateGrantForFinalSubmit() " + ex.toString());
			return 0;
		} finally {
		}
		return rows;
	}
	
	
	
	
	
	/**
	 * Update GRANT table upon amendment submission to lock fields. Might not be needed 
	 * after VERSIONS/STATUSES table implementation.
	 * @param user
	 * @param grantId
	 * @return
	 */
	public long updateGrantForAmendmentSubmit(Long grantId, User user) {
		long rows = 0;
		String update = "update GRANTS set FS10A_YN=1, DATE_MODIFIED=sysdate, MODIFIED_BY=?  where ID=?";
		try {
			rows = jt.update(update,
					new Object[] { user.getModifiedBy(), grantId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error GrantDao.updateGrantForAmendmentSubmit() " + ex.toString());
			return 0;
		} finally {
		}
		return rows;
	}
	
	
	
	
	/**
	 * Update GRANT table upon initial app submission to lock fields. Might not be needed 
	 * after VERSIONS table implementation.
	 * @param user
	 * @param grantId
	 * @return
	 */
	public long updateGrantForInitialSubmit(Long grantId, User user) {
		long rows = 0;
		String update = "update GRANTS set  COVERSHEET_COMP =1, DESCRIPTION_COMP =1, "+
            " BUDGET_COMP=1, AUTH_COMP=1, FS20_COMP=1, LOCK_APP=1, DATE_MODIFIED=sysdate, "+
				" MODIFIED_BY=?  where ID=?";
		try {
			rows = jt.update(update,
					new Object[] { user.getModifiedBy(),
									grantId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error GrantDao.updateGrantForInitialSubmit() " + ex.toString());
			return 0;
		} finally {
		}
		return rows;
	}
	
	
	
	
	
	
	public GrantStatus selectGrantStatusItems(Long grantId) {

		try {

			GrantStatus GrantStatus = jt.queryForObject(
					"select * from GRANTS where ID=?",
					new Object[] { grantId }, new BeanPropertyRowMapper<GrantStatus>(GrantStatus.class));

			return GrantStatus;

		} catch (Exception e) {
			System.err.println("GrantDao.selectGrantStatusItems: " + e.getMessage());
			return null;
		}
	}


	@Override
	public List<Grant> searchByInstId(Long id) {
		//@formatter:off
		String sql = "/* Formatted on 1/25/2017 12:33:54 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT grants.id AS id, " +
				"       inst_id as instId, " +
				"       NAME as Name, " +
				"       fc_code as fcCode, " +
				"       fy_code as fyCode, " +
				"       proj_seq_num as projSeqNum, " +
				"       contract_num as contratNum, " +
				"       fiscal_years.description AS fiscalYear, " +
				"       amount_req AS ldacAppropriationAmount, " +
				"        fs10a_yn as fs10aLocked" +
				"  FROM grants, " +
				"       co_institutions, " +
				"       fiscal_years, " +
				"       fund_codes " +
				" WHERE co_institutions.inst_id = ? " +
				"       AND is_lead = 'Y' " +
				"       AND grants.ID = co_institutions.gra_id " +
				"       AND grants.fy_code = fiscal_years.code " +
				"       AND grants.fc_code = fund_codes.code "; 

		try {

			List<Grant> grantList = jt.query(sql,
					new Object[] { id }, new BeanPropertyRowMapper(Grant.class));

			return grantList;

		} catch (Exception e) {
			System.err.println("GrantDao.searchByInstId() " + e.getMessage());
			return null;
		}
	}






	
	
	
	

}
