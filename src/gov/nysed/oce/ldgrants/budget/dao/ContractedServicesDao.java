package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.ContractedServices;
import gov.nysed.oce.ldgrants.user.domain.User;


public class ContractedServicesDao implements ContractedServicesInt {

	private static long purchasedServicesBudgetCode = 5;
	
	

	public boolean deleteContractedServices(Long contractedServicesId) {
		int rows = 0;
		String update = "DELETE FROM Contracted_Services WHERE id = ? ";
		try {
			rows = jt.update(update, new Object[] { contractedServicesId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error ContractedServicesDao.deleteContractedServices() " + ex.toString());
		} finally {
		}
		return (rows == 1);
	}
	
	

	public Long getNextContractedServicesId() {
		return jt.queryForObject("SELECT PROJ_BUDG_SEQ.nextval FROM dual", Long.class);
	}
	
	

	public long insertContractedServices(ContractedServices contractedServices, User user) {
		int rows = 0;
		String update = " /* Formatted on 9/19/2016 1:18:21 PM (QP5 v5.185.11230.41888) */ "+
				"INSERT INTO CONTRACTED_SERVICES (ID, "+
				"                                 GRA_ID, "+
				"                                 FY_CODE, "+
				"                                 CODE, "+
				"                                 DATE_CREATED, "+
				"                                 CREATED_BY, "+
				"                                 SERVICE_TYPE, "+
				"                                 RECIPIENT, "+
				"                                 PROJ_TOTAL, "+
				"                                 INST_CONT, "+
				"                                 SERVICE_DESCR, "+
				"                                 GRANT_REQUEST, "+
				"                                 EXP_SUBMITTED, "+
				"                                 AMOUNT_APPROVED, "+
				"                                 EXP_APPROVED, "+
				"                                 AMEND_AMOUNT) "+
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
				"             ?) ";
		try {
			rows = jt.update(update,
					new Object[] { contractedServices.getId(), 
							contractedServices.getGrantId(), 
							contractedServices.getFyCode(),
							purchasedServicesBudgetCode, 
							user.getCreatedBy(), 
							contractedServices.getServiceType(),
							contractedServices.getRecipient(), 
							contractedServices.getProjectTotal(),
							contractedServices.getInstitutionContribution(), 
							contractedServices.getServiceDescription(),
							contractedServices.getGrantRequest(), 
							contractedServices.getExpenseSubmitted(),
							contractedServices.getAmountApproved(),
							contractedServices.getExpenseApproved(),
							contractedServices.getAmendAmount() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error ContractedServicesDao.insertContractedServices() " + ex.toString());
		} finally {
		}

		return rows;
	}
	

	public long updateContractedServices(ContractedServices contractedServices, User user) {
		long rows = 0;
		String update = "/* Formatted on 9/19/2016 1:25:49 PM (QP5 v5.185.11230.41888) */  "+
					"UPDATE CONTRACTED_SERVICES "+
					"   SET SERVICE_TYPE = ?, "+
					"       RECIPIENT = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       SERVICE_DESCR = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ? "+
					" WHERE ID = ?";
		try {
			rows = jt.update(update,
					new Object[] { contractedServices.getServiceType(), 
							contractedServices.getRecipient(),
							contractedServices.getProjectTotal(), 
							contractedServices.getInstitutionContribution(),
							contractedServices.getServiceDescription(), 
							contractedServices.getGrantRequest(),
							contractedServices.getExpenseSubmitted(), 
							user.getModifiedBy(),
							contractedServices.getAmendAmount(),
							contractedServices.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error ContractedServicesDao.updateContractedServices() " + ex.toString());
		} finally {
		}
		return rows;
	}
	

	public long updateContractedServicesWithAwardFields(ContractedServices contractedServices, User user) {
		long rows = 0;
		String update = "/* Formatted on 9/19/2016 1:15:55 PM (QP5 v5.185.11230.41888) */  "+
					" UPDATE CONTRACTED_SERVICES "+
					"   SET SERVICE_TYPE = ?, "+
					"       RECIPIENT = ?, "+
					"       PROJ_TOTAL = ?, "+
					"       INST_CONT = ?, "+
					"       SERVICE_DESCR = ?, "+
					"       GRANT_REQUEST = ?, "+
					"       EXP_SUBMITTED = ?, "+
					"       DATE_MODIFIED = SYSDATE, "+
					"       MODIFIED_BY = ?, "+
					"       AMEND_AMOUNT = ?, "+
					"       AMOUNT_APPROVED = ?, "+
					"       EXP_APPROVED = ? "+
					" WHERE ID = ?";
		try {
			rows = jt.update(update,
					new Object[] { contractedServices.getServiceType(), 
							contractedServices.getRecipient(),
							contractedServices.getProjectTotal(), 
							contractedServices.getInstitutionContribution(),
							contractedServices.getServiceDescription(), 
							contractedServices.getGrantRequest(),
							contractedServices.getExpenseSubmitted(), 
							user.getModifiedBy(), 
							contractedServices.getAmendAmount(),
							contractedServices.getAmountApproved(), 
							contractedServices.getExpenseApproved(),
							contractedServices.getId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error ContractedServicesDao.updateContractedServicesWithAwardFields() " + ex.toString());
		} finally {
		}
		return rows;
	}

	
	public List<ContractedServices> searchContractedServicesByGrantIdFy(Long grantId, Long fyCode) {

		try {
			
			List<ContractedServices> ContractedServices = jt.query(" /* Formatted on 9/19/2016 12:12:36 PM (QP5 v5.185.11230.41888) */ "+
				"SELECT id, "+
				"       service_type, "+
				"       recipient, "+
				"       service_descr AS serviceDescription, "+
				"       proj_total AS projectTotal, "+
				"       inst_cont AS institution_contribution, "+
				"       grant_request, "+
				"       amount_approved, "+
				"       exp_submitted AS expenseSubmitted, "+
				"       exp_approved AS expenseApproved, "+
				"       gra_id AS grantId, "+
				"       fy_code, "+
				"       code AS budgetTypeCode, "+
				"       amend_amount, "+
				"       encumbrance_date, "+
				"       journal_entry, "+
				"       provider_used "+
				"  FROM contracted_services "+
				" WHERE gra_id = ? AND fy_code = ? ", 
 		new Object[] { grantId, fyCode}, new BeanPropertyRowMapper(ContractedServices.class));
		
			
			return ContractedServices;

		} catch (Exception e) {
			System.err.println("error ContractedServicesDao.searchContractedServicesByGrantIdFy() " + e.toString());
			return null;
		}
	}
	
	

	public ContractedServices selectContractedServices(Long id) {

		ContractedServices contractedServices = new ContractedServices();

		try {

			ContractedServices ContractedServices = jt.queryForObject(" /* Formatted on 9/19/2016 12:12:36 PM (QP5 v5.185.11230.41888) */ "+
					"SELECT id, "+
					"       service_type, "+
					"       recipient, "+
					"       service_descr AS serviceDescription, "+
					"       proj_total AS projectTotal, "+
					"       inst_cont AS institution_contribution, "+
					"       grant_request, "+
					"       amount_approved, "+
					"       exp_submitted AS expenseSubmitted, "+
					"       exp_approved AS expenseApproved, "+
					"       gra_id AS grantId, "+
					"       fy_code, "+
					"       code AS budgetTypeCode, "+
					"       amend_amount, "+
					"       encumbrance_date, "+
					"       journal_entry, "+
					"       provider_used "+
					"  FROM contracted_services "+
					" WHERE id=? ", 
	 		new Object[] { id}, new BeanPropertyRowMapper<ContractedServices>(ContractedServices.class));
			
				
				return ContractedServices;

		} catch (Exception e) {
			System.err.println("error ContractedServicesDao.selectContractedServices() " + e.toString());
			return null;
		}
	}

}
