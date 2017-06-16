package gov.nysed.oce.ldgrants.budget.dao;


import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.Supplies;
import gov.nysed.oce.ldgrants.user.domain.User;

public class SuppliesDao implements SuppliesInt {

	private static long suppliesBudgetCode = 1;

	public boolean deleteSupplies(Long suppliesId) {
		int rows = 0;
		String update = "DELETE FROM SUPP_MAT_EQUIPS WHERE id = ? ";
		try {
			rows = jt.update(update, new Object[] { suppliesId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error SuppliesDao.deleteSupplies() " + ex.toString());
		} finally {
		}
		return (rows == 1);
	}
	

	public Long getNextSuppliesId() {
		return jt.queryForObject("SELECT PROJ_BUDG_SEQ.nextval FROM dual", Long.class);
	}
	

	public long insertSupplies(Supplies supplies, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 1:52:17 PM (QP5 v5.185.11230.41888) */  "+
					"INSERT INTO SUPP_MAT_EQUIPS (ID, "+
					"                             GRA_ID, "+
					"                             FY_CODE, "+
					"                             SMET_CODE, "+
					"                             DATE_CREATED, "+
					"                             CREATED_BY, "+
					"                             QUANTITY, "+
					"                             DESCRIPTION, "+
					"                             UNIT_PRICE, "+
					"                             PROJ_TOTAL, "+
					"                             INST_CONT, "+
					"                             GRANT_REQUEST, "+
					"                             EXP_SUBMITTED, "+
					"                             AMEND_AMOUNT, "+
					"                             AMOUNT_APPROVED, "+
					"                             EXP_APROVED, "+
					"                             VENDOR) "+
					"     VALUES (?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             SYSDATE, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?, "+
					"             ?) ";
		try {
			rows = jt.update(update,
					new Object[] { supplies.getId(), 
							supplies.getGrantId(), 
							supplies.getFyCode(), 
							suppliesBudgetCode, 
							user.getCreatedBy(),
							supplies.getQuantity(), 
							supplies.getDescription(), 
							supplies.getUnitPrice(),
							supplies.getProjectTotal(), 
							supplies.getInstitutionContribution(),
							supplies.getGrantRequest(), 
							supplies.getExpenseSubmitted(), 
							supplies.getAmendAmount(),
							supplies.getAmountApproved(),
							supplies.getExpenseApproved(),
							supplies.getVendor() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error SuppliesDao.insertSupplies() " + ex.toString());
		} finally {
		}

		return supplies.getId();
	}
	

	public long updateSupplies(Supplies supplies, User user) {
		long rows = 0;
		String update = "/* Formatted on 9/19/2016 1:50:09 PM (QP5 v5.185.11230.41888) */  "+
				"UPDATE SUPP_MAT_EQUIPS "+
				"   SET QUANTITY = ?, "+
				"       DESCRIPTION = ?, "+
				"       UNIT_PRICE = ?, "+
				"       VENDOR = ?, "+
				"       PROJ_TOTAL = ?, "+
				"       INST_CONT = ?, "+
				"       EXP_SUBMITTED = ?, "+
				"       DATE_MODIFIED = SYSDATE, "+
				"       MODIFIED_BY = ?, "+
				"       AMEND_AMOUNT = ? "+
				" WHERE ID = ?";
		try {
			rows = jt.update(update,
					new Object[] { supplies.getQuantity(), 
							supplies.getDescription(), 
							supplies.getUnitPrice(),
							supplies.getVendor(), 
							supplies.getProjectTotal(), 
							supplies.getInstitutionContribution(),
							supplies.getExpenseSubmitted(), 
							user.getModifiedBy(),
							supplies.getAmendAmount(), 
							supplies.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error SuppliesDao.updateSupplies() " + ex.toString());
		} finally {
		}
		return rows;
	}
	
	
	
	public long updateSuppliesWithAwardFields(Supplies supplies, User user) {
		long rows = 0;
		String update = "/* Formatted on 9/19/2016 1:50:09 PM (QP5 v5.185.11230.41888) */  "+
				"UPDATE SUPP_MAT_EQUIPS "+
				"   SET QUANTITY = ?, "+
				"       DESCRIPTION = ?, "+
				"       UNIT_PRICE = ?, "+
				"       VENDOR = ?, "+
				"       PROJ_TOTAL = ?, "+
				"       INST_CONT = ?, "+
				"       EXP_SUBMITTED = ?, "+
				"       DATE_MODIFIED = SYSDATE, "+
				"       MODIFIED_BY = ?, "+
				"       AMEND_AMOUNT = ?, "+
				"       AMOUNT_APPROVED = ?, "+
				"       EXP_APROVED = ? "+
				" WHERE ID = ?";
		try {
			rows = jt.update(update,
					new Object[] { supplies.getQuantity(), 
							supplies.getDescription(), 
							supplies.getUnitPrice(),
							supplies.getVendor(), 
							supplies.getProjectTotal(), 
							supplies.getInstitutionContribution(),
							supplies.getExpenseSubmitted(), 
							user.getModifiedBy(),
							supplies.getAmendAmount(), 
							supplies.getAmountApproved(),
							supplies.getExpenseApproved(),
							supplies.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error SuppliesDao.updateSuppliesWithAwardFields() " + ex.toString());
		} finally {
		}
		return rows;
	}

	

	
	public List<Supplies> searchSuppliesByGrantIdFy(Long grantId, Long fyCode) {
		
		try {
			
			List<Supplies> Supplies = jt.query(" /* Formatted on 9/19/2016 11:06:58 AM (QP5 v5.185.11230.41888) */  "+
					"SELECT id, "+
					"       quantity, "+
					"       description, "+
					"       unit_price, "+
					"       vendor, "+
					"       proj_total AS projectTotal, "+
					"       inst_cont AS institution_contribution, "+
					"       grant_request, "+
					"       amount_approved, "+
					"       exp_submitted AS expenseSubmitted, "+
					"       exp_aproved AS expenseApproved, "+
					"       gra_id AS grantId, "+
					"       fy_code, "+
					"       amend_amount, "+
					"       encumbrance_date, "+
					"       journal_entry "+
					"  FROM supp_mat_equips "+
					" WHERE gra_id = ? AND fy_code = ? AND smet_code=? ", 
 		new Object[] { grantId, fyCode, suppliesBudgetCode }, new BeanPropertyRowMapper(Supplies.class));
		
			
			return Supplies;
			
		} catch (Exception e) {
			System.err.println("error SuppliesDao.searchSuppliesByGrantIdFy "+e.getMessage());
			return null;
		}
	}

	public Supplies selectSupplies(Long id) {

		try {

			Supplies Supplies = jt.queryForObject(" /* Formatted on 9/19/2016 11:06:58 AM (QP5 v5.185.11230.41888) */  "+
					"SELECT id, "+
					"       quantity, "+
					"       description, "+
					"       unit_price, "+
					"       vendor, "+
					"       proj_total AS projectTotal, "+
					"       inst_cont AS institution_contribution, "+
					"       grant_request, "+
					"       amount_approved, "+
					"       exp_submitted AS expenseSubmitted, "+
					"       exp_aproved AS expenseApproved, "+
					"       gra_id AS grantId, "+
					"       fy_code, "+
					"       amend_amount, "+
					"       encumbrance_date, "+
					"       journal_entry "+
					"  FROM supp_mat_equips "+
					" WHERE id=? ", 
					new Object[] { id}, new BeanPropertyRowMapper<Supplies>(Supplies.class));
		
			
			return Supplies;

		} catch (Exception e) {
			System.err.println("error SuppliesDao.selectSupplies "+e.getMessage());
			return null;
		}
	}

}
