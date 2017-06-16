package gov.nysed.oce.ldgrants.budget.dao;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.Equipment;
import gov.nysed.oce.ldgrants.user.domain.User;

public class EquipmentDao implements EquipmentInt {

	private static long equipmentBudgetCode = 2;

	public boolean deleteEquipment(Long equipmentId) {
		int rows = 0;
		String update = "DELETE FROM SUPP_MAT_EQUIPS WHERE id = ? ";
		try {
			rows = jt.update(update, new Object[] { equipmentId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error EquipmentDao.deleteEquipment() " + ex.toString());
		} finally {
		}
		return (rows == 1);
	}
	

	public Long getNextEquipmentId() {
		return jt.queryForObject("SELECT PROJ_BUDG_SEQ.nextval FROM dual", Long.class);
	}

	
	public long insertEquipment(Equipment equipment, User user) {
		int rows = 0;
		String update = " /* Formatted on 9/19/2016 2:12:40 PM (QP5 v5.185.11230.41888) */ "+
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
					"             ?)";
		try {
			rows = jt.update(update,
					new Object[] { equipment.getId(),
							equipment.getGrantId(), 
							equipment.getFyCode(), 
							equipmentBudgetCode, 
							user.getCreatedBy(),
							equipment.getQuantity(), 
							equipment.getDescription(), 
							equipment.getUnitPrice(),
							equipment.getProjectTotal(), 
							equipment.getInstitutionContribution(),
							equipment.getGrantRequest(), 
							equipment.getExpenseSubmitted(), 
							equipment.getAmendAmount(),
							equipment.getAmountApproved(),
							equipment.getExpenseApproved(),
							equipment.getVendor() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error  EquipmentDao.insertEquipment() " + ex.toString());
		} finally {
		}

		return equipment.getId();
	}
	

	public long updateEquipment(Equipment equipment, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 2:02:43 PM (QP5 v5.185.11230.41888) */ "+
					"UPDATE SUPP_MAT_EQUIPS "+
					"   SET QUANTITY = ?, "+
					"       DESCRIPTION = ?, "+
					"       UNIT_PRICE = ?, "+
					"       VENDOR = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ? "+
					" WHERE ID = ? ";
		try {
			rows = jt.update(update,
					new Object[] { equipment.getQuantity(), 
							equipment.getDescription(), 
							equipment.getUnitPrice(),
							equipment.getVendor(), 
							equipment.getProjectTotal(), 
							equipment.getInstitutionContribution(),
							equipment.getGrantRequest(), 
							equipment.getExpenseSubmitted(), 
							user.getModifiedBy(),
							equipment.getAmendAmount(), 
							equipment.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error  EquipmentDao.updateEquipment() " + ex.toString());
		} finally {
		}
		return rows;
	}
	
	
	
	public long updateEquipmentWithAwardFields(Equipment equipment, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 2:02:43 PM (QP5 v5.185.11230.41888) */ "+
					"UPDATE SUPP_MAT_EQUIPS "+
					"   SET QUANTITY = ?, "+
					"       DESCRIPTION = ?, "+
					"       UNIT_PRICE = ?, "+
					"       VENDOR = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ?, "+
					"       AMOUNT_APPROVED = ?, "+
					"       EXP_APROVED = ? "+
					" WHERE ID = ? ";
		try {
			rows = jt.update(update,
					new Object[] { equipment.getQuantity(), 
							equipment.getDescription(), 
							equipment.getUnitPrice(),
							equipment.getVendor(), 
							equipment.getProjectTotal(), 
							equipment.getInstitutionContribution(),
							equipment.getGrantRequest(), 
							equipment.getExpenseSubmitted(), 
							user.getModifiedBy(),
							equipment.getAmendAmount(), 
							equipment.getAmountApproved(),
							equipment.getExpenseApproved(),
							equipment.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error  EquipmentDao.updateEquipmentWithAwardFields() " + ex.toString());
		} finally {
		}
		return rows;
	}

	
	public List<Equipment> searchEquipmentByGrantIdFy(Long grantId, Long fyCode) {

		try {
			List<Equipment> Equipment = jt.query(" /* Formatted on 9/19/2016 11:06:58 AM (QP5 v5.185.11230.41888) */  "+
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
 		new Object[] { grantId, fyCode, equipmentBudgetCode }, new BeanPropertyRowMapper(Equipment.class));
		
			return Equipment;
			
		} catch (Exception e) {
			System.err.println("error  EquipmentDao.searchEquipmentByGrantIdFy "+e.getMessage());
			return null;
		}
	}
	

	public Equipment selectEquipment(Long id) {

		try {
			Equipment Equipment = jt.queryForObject(" /* Formatted on 9/19/2016 11:06:58 AM (QP5 v5.185.11230.41888) */  "+
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
					new Object[] { id }, new BeanPropertyRowMapper<Equipment>(Equipment.class));
			
			return Equipment; 
			
		} catch (Exception e) {
			System.err.println("error  EquipmentDao.selectEquipment "+e.getMessage());
			return null;
		}
	}

}
