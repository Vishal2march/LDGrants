package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.Travel;
import gov.nysed.oce.ldgrants.user.domain.User;

public class TravelDao implements TravelInt {

	public boolean deleteTravel(Long travelId) {
		int rows = 0;
		String update = "DELETE FROM TRAVEL_EXPENSES WHERE id = ? ";
		try {
			rows = jt.update(update, new Object[] { travelId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error TravelDao.deleteTravel() " + ex.toString());
		} finally {
		}
		return (rows == 1);
	}
	

	public Long getNextTravelId() {
		return jt.queryForObject("SELECT PROJ_BUDG_SEQ.nextval FROM dual", Long.class);
	}
	

	public long insertTravel(Travel travel, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 1:38:28 PM (QP5 v5.185.11230.41888) */ "+
				"INSERT INTO TRAVEL_EXPENSES (ID, "+
				"                             GRA_ID, "+
				"                             FY_CODE, "+
				"                             DATE_CREATED, "+
				"                             CREATED_BY, "+
				"                             DESCRIPTION, "+
				"                             PURPOSE, "+
				"                             PROJ_TOTAL, "+
				"                             INST_CONT, "+
				"                             COSTSUMMARY, "+
				"                             GRANT_REQUEST, "+
				"                             EXP_SUBMITTED, "+
				"                             AMEND_AMOUNT, "+
				"                             TRAVEL_PERIOD, "+
				"                             JOURNAL_ENTRY, "+
				"                             TRAVELER_NAME,  "+
				"							  AMOUNT_APPROVED, "+
				"							  EXP_APPROVED )  "+
				"     VALUES (?, "+
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
				"             ?, "+
				"             ?, "+
				"             ?) ";
		try {
			rows = jt.update(update,
					new Object[] { travel.getId(), 
							travel.getGrantId(), 
							travel.getFyCode(), 
							user.getCreatedBy(), 
							travel.getDescription(),
							travel.getPurpose(), 
							travel.getProjectTotal(), 
							travel.getInstitutionContribution(),
							travel.getCostSummary(), 
							travel.getGrantRequest(), 
							travel.getExpenseSubmitted(),
							travel.getAmendAmount(), 
							travel.getTravelPeriod(), 
							travel.getJournalEntry(),
							travel.getTravelerName(),
							travel.getAmountApproved(),
							travel.getExpenseApproved() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error TravelDao.insertTravel() " + ex.toString());
		} finally {
		}

		return travel.getId();
	}

	
	public long updateTravel(Travel travel, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 1:33:32 PM (QP5 v5.185.11230.41888) */ "+
					"UPDATE TRAVEL_EXPENSES "+
					"   SET DESCRIPTION = ?, "+
					"       PURPOSE = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       COSTSUMMARY = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ?, "+
					"       TRAVEL_PERIOD = ?, "+
					"       JOURNAL_ENTRY = ?, "+
					"       TRAVELER_NAME = ? "+
					" WHERE ID = ? ";
		try {
			rows = jt.update(update,
					new Object[] { travel.getDescription(), 
							travel.getPurpose(), 
							travel.getProjectTotal(),
							travel.getInstitutionContribution(), 
							travel.getCostSummary(), 
							travel.getGrantRequest(),
							travel.getExpenseSubmitted(), 
							user.getModifiedBy(), 
							travel.getAmendAmount(), 
							travel.getTravelPeriod(),
							travel.getJournalEntry(), 
							travel.getTravelerName(), 
							travel.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error TravelDao.updateTravel() " + ex.toString());
		} finally {
		}
		return rows;
	}
	
	
	
	
	public long updateTravelWithAwardFields(Travel travel, User user) {
		long rows = 0;
		String update = " /* Formatted on 9/19/2016 1:33:32 PM (QP5 v5.185.11230.41888) */ "+
					"UPDATE TRAVEL_EXPENSES "+
					"   SET DESCRIPTION = ?, "+
					"       PURPOSE = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       COSTSUMMARY = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ?, "+
					"       TRAVEL_PERIOD = ?, "+
					"       JOURNAL_ENTRY = ?, "+
					"       TRAVELER_NAME = ?, "+
					"       AMOUNT_APPROVED = ?, "+
					"       EXP_APPROVED = ? "+
					" WHERE ID = ? ";
		try {
			rows = jt.update(update,
					new Object[] { travel.getDescription(), 
							travel.getPurpose(), 
							travel.getProjectTotal(),
							travel.getInstitutionContribution(), 
							travel.getCostSummary(), 
							travel.getGrantRequest(),
							travel.getExpenseSubmitted(), 
							user.getModifiedBy(), 
							travel.getAmendAmount(), 
							travel.getTravelPeriod(),
							travel.getJournalEntry(), 
							travel.getTravelerName(), 
							travel.getAmountApproved(),
							travel.getExpenseApproved(),
							travel.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error TravelDao.updateTravelWithAwardFields() " + ex.toString());
		} finally {
		}
		return rows;
	}
	
	
	
	public List<Travel> searchTravelByGrantIdFy(Long grantId, Long fyCode) {

		try {

			List<Travel> Travel = jt.query("/* Formatted on 9/15/2016 4:31:13 PM (QP5 v5.185.11230.41888) */  "+
									" SELECT id,  "+
									"       description, "+
									"       purpose, "+
									"       costsummary, "+
									"       proj_total AS projectTotal, "+
									"       inst_cont AS institution_contribution, "+
									"       grant_request, "+
									"       amount_approved, "+
									"       exp_submitted AS expenseSubmitted, "+
									"       exp_approved AS expenseApproved, "+
									"       gra_id AS grantId, "+
									"       fy_code, "+
									"       amend_amount, "+
									"       travel_period, "+
									"       journal_entry, "+
									"       traveler_name "+
									"  FROM travel_expenses "+
									"  WHERE gra_id = ? AND fy_code = ?",
					new Object[] { grantId, fyCode }, new BeanPropertyRowMapper(Travel.class));

			
			return Travel;
	
		} catch (Exception e) {
			System.err.println("error TravelDao.searchTravelByGrantIdFy "+e.getMessage());
			return null;
		}
	}
	
	
	
	
	public Travel selectTravel(Long id) {

		try {

			Travel Travel = jt.queryForObject("/* Formatted on 9/15/2016 4:31:13 PM (QP5 v5.185.11230.41888) */  "+
					" SELECT id,  "+
					"       description, "+
					"       purpose, "+
					"       costsummary, "+
					"       proj_total AS projectTotal, "+
					"       inst_cont AS institution_contribution, "+
					"       grant_request, "+
					"       amount_approved, "+
					"       exp_submitted AS expenseSubmitted, "+
					"       exp_approved AS expenseApproved, "+
					"       gra_id AS grantId, "+
					"       fy_code, "+
					"       amend_amount, "+
					"       travel_period, "+
					"       journal_entry, "+
					"       traveler_name "+
					"  FROM travel_expenses "+
					"  WHERE id = ? ",
					new Object[] { id }, new BeanPropertyRowMapper<Travel>(Travel.class));
			
			return Travel;

			
		} catch (Exception e) {
			System.err.println("error TravelDao.selectTravel "+ e.getMessage());
			return null;
		}
	}

}
