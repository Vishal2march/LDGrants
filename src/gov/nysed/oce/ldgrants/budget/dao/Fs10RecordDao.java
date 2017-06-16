package gov.nysed.oce.ldgrants.budget.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.grants.common.dao.GenericDao;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Fs10RecordDao extends GenericDao<Fs10Record, Long> {

	@Override
	public Long insert(Fs10Record fs10Record, User user) {
		//@formatter:off
		String sql ="/* Formatted on 2/27/2017 1:21:48 PM (QP5 v5.185.11230.41888) */ "+
				"INSERT INTO FS10_RECORDS (ID, "+
				"                         DESCRIPTION, "+
				"                          AMOUNT_INCR, "+
				"                          AMOUNT_DECR, "+
				"                          EXP_CODE, "+
				"                          EXP_NAME, "+
				"                          EXT_ID, " +
				"                          GRA_ID, "+
				"                          FY_CODE, "+
				"                          DATE_CREATED, "+
				"                          CREATED_BY) "+
				"     VALUES (?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             ?, "+
				"             SYSDATE, "+
				"             ?)"; 
			
		try {
			
			Long pk = getNextId("FS10_SEQ");
			
			long rows = (long) jt.update(sql, 
					new Object[]{pk,   fs10Record.getDescription(), 
					fs10Record.getAmountIncr(), fs10Record.getAmountDecr(), 
					fs10Record.getExpCode(), fs10Record.getExpName(), fs10Record.getExpenditureTypeId(),
					fs10Record.getGraId(), fs10Record.getFyCode(),
					user.getCreatedBy()});
			
			if(rows ==1)
				return pk;//return primary key			
			
		} catch (Exception ex) {
		
			System.err.println("CLASS:Fs10RecordDao METHOD:insert()" + ex.toString());
		} 
		return null;
	}

	
	@Override
	public Long update(Fs10Record fs10Record, User user) {
		//@formatter:off
		// TODO Auto-generated method stub
		String sql ="/* Formatted on 2/27/2017 1:21:48 PM (QP5 v5.185.11230.41888) */ "+
				" UPDATE FS10_RECORDS "+
				"                   SET DESCRIPTION = ?, "+
				"                          AMOUNT_INCR = ?, "+
				"                          AMOUNT_DECR = ?, "+
				"                          EXP_CODE = ?, "+
				"                          EXP_NAME = ?, "+
				"                          GRA_ID = ?, "+
				"                          FY_CODE = ?, "+
				"                          EXT_ID = ?, " +
				"                          DATE_MODIFIED = SYSDATE, "+
				"                          MODIFIED_BY = ? "+
				"     WHERE ID = ?"; 
		
		try {
			long rows = (long) jt.update(sql, 
					new Object[]{fs10Record.getDescription(), 
					fs10Record.getAmountIncr(), fs10Record.getAmountDecr(), 
					fs10Record.getExpCode(), fs10Record.getExpName(),  
					fs10Record.getGraId(), fs10Record.getFyCode(), fs10Record.getExpenditureTypeId(),
					user.getModifiedBy(),  fs10Record.getId()});
			
			return rows;
			
		} catch (Exception ex) {
		System.err.println("CLASS:Fs10RecordDao METHOD:update() " + ex.toString());
		} 		
		return null;
	}

	
	
	
	@Override
	public boolean delete(Long id) {
		//@formatter:off
		String sql = "DELETE FROM FS10_RECORDS WHERE ID=?";
		
		try {
			int rows = jt.update(sql, new Object[] {id });
			return (rows == 1);
			
		} catch (Exception ex) {
			System.err.println("CLASS:Fs10RecordDao METHOD:delete() " + ex.toString());
		} 
		return false;
	}

	
	
	
	@Override
	public Fs10Record select(Long id) {
		//@formatter:off
		String sql = "/* Formatted on 2/27/2017 2:08:33 PM (QP5 v5.185.11230.41888) */ "+
					"	SELECT ID, "+
					"	       DESCRIPTION, "+
					"	       AMOUNT_INCR as amountIncr, "+
					"	       AMOUNT_DECR as amountDecr, "+
					"	       EXP_CODE as expCode, "+
					"	       EXP_NAME as expName, "+
					"	       GRA_ID as graId, "+
					"	       FY_CODE as fyCode, "+
					"	       ADMIN_APPROVAL as adminApproval, " +
					"          EXT_ID as expenditureTypeId "+
					"	  FROM FS10_RECORDS "+
					"	 WHERE ID = ?"; 
 
		try {
			Fs10Record fs10Record =  jt.queryForObject(sql, new Object[] { id }, 
					new BeanPropertyRowMapper<Fs10Record>(Fs10Record.class));

			return fs10Record;
			
		} catch (Exception e) {
			System.out.println("CLASS:Fs10RecordDao METHOD:select()" + e.getMessage());
		}
		return null;
	}
	
	
	

	@Override
	public List<Fs10Record> selectAll() {
		//@formatter:off
		String sql = "/* Formatted on 3/13/2017 2:31:30 PM (QP5 v5.252.13127.32847) */ " +
				"  SELECT ID, " +
				"         DESCRIPTION, " +
				"         AMOUNT_INCR AS amountIncr, " +
				"         AMOUNT_DECR AS amountDecr, " +
				"         EXP_CODE AS expCode, " +
				"         EXP_NAME AS expName, " +
				"         GRA_ID AS graId, " +
				"         FY_CODE AS fyCode, " +
				"         ADMIN_APPROVAL AS adminApproval, " +
				"         EXT_ID AS expenditureTypeId " +
				"    FROM FS10_RECORDS " +
				"ORDER BY date_created DESC "; 
 
		
		try {

			List<Fs10Record> fs10RecordList = jt.query(sql, 
					new BeanPropertyRowMapper<Fs10Record>(Fs10Record.class));

			if (fs10RecordList.size() > 0)
				return (ArrayList<Fs10Record>) fs10RecordList;

		} catch (Exception e) {
			System.out.println("CLASS:Fs10RecordDao METHOD:selectAll()" + e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * Update FS10A_YN to True When Locked
	 * 
	 * @param user
	 * @param grantId
	 * @return
	 */
	public long updateFS10ALocked(Long grantId, User user) {
		long rows = 0;
		String update = "/* Formatted on 4/7/2017 9:58:08 AM (QP5 v5.252.13127.32847) */ " +
				"UPDATE GRANTS " +
				"   SET MODIFIED_BY = ?, DATE_MODIFIED = SYSDATE, FS10A_YN = 1 " +
				" WHERE ID = ? "; 

		try {
			rows = jt.update(update,
					new Object[] { user.getModifiedBy(),
									grantId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error updateFS10ALocked() " + ex.toString());
			return 0;
		} finally {
		}
		return rows;
	}
	
	/**
	 * Update FS10A_YN to false When unlocked
	 * 
	 * @param user
	 * @param grantId
	 * @return
	 */
	public long updateFS10AUnlocked(Long grantId, User user) {
		long rows = 0;
		String update = "/* Formatted on 4/7/2017 9:58:08 AM (QP5 v5.252.13127.32847) */ " +
				"UPDATE GRANTS " +
				"   SET MODIFIED_BY = ?, DATE_MODIFIED = SYSDATE, FS10A_YN = 0 " +
				" WHERE ID = ? "; 


		try {
			rows = jt.update(update,
					new Object[] { user.getModifiedBy(),
									grantId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error updateFS10AUnlocked() " + ex.toString());
			return 0;
		} finally {
		}
		return rows;
	}
	

	
}
