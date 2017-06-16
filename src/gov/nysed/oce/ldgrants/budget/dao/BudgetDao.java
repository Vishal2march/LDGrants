package gov.nysed.oce.ldgrants.budget.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.budget.domain.BudgetTotal;


public class BudgetDao implements BudgetInt{
	
	
	
	
	public int calcTotalExpenseSubmittedForFy(Long grantId, Long fyCode) {

		int total=0;
		try {

			List queryList = jt.queryForList("/* Formatted on 9/19/2016 1:27:28 PM (QP5 v5.185.11230.41888) */  "+
							"SELECT SUM (totexpsub) AS totalSubmitted "+
							"  FROM ldgrants.multifyproj_budgettotals_view "+
							" WHERE gra_id = ? AND fy_code = ?",
							new Object[] { grantId, fyCode });

			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);

				if (queryMap.get("totalSubmitted") != null)
					total = ((BigDecimal) queryMap.get("totalSubmitted")).intValue();				
			}

		} catch (Exception e) {
			System.err.println("error BudgetDao.calcTotalExpenseSubmittedForFy "+e.getMessage());
			return 0;
		}
		return total;
	}
	
	
	
	/**
	 * Calculate the total initial/expense requested/approved/etc for TRAVEL, PURCHASED_SERVICES, BENEFITS
	 * tables.  Cannot be used for SUPPLIES/EQUIPMENT - those tables have additional id foreign key filter.
	 */
	public BudgetTotal calcTotalsForBudgetTableFiscalYear(String budgetTable, Long grantId, Long fyCode) {

		BudgetTotal budgetTotal = new BudgetTotal();
		try {
						
			String query = "/* Formatted on 10/24/2016 11:08:35 AM (QP5 v5.185.11230.41888) */ "+
						  " SELECT gra_id, "+
						  "       fy_code, "+
						  "       SUM (grant_request) AS totalAmountRequested, "+
						  "       SUM (amount_approved) AS totalAmountApproved, "+
						  "       SUM (exp_submitted) AS totalExpensesSubmitted, "+
						  "       SUM (exp_approved) AS totalExpensesApproved, "+
						  "       SUM (amend_amount) AS totalAmendment "+
						  "  FROM  "+ budgetTable +
						  " WHERE gra_id = ? AND fy_code = ? "+
						" GROUP BY gra_id, fy_code";

			List<BudgetTotal> budgetTotalList = jt.query(query,
							new Object[] { grantId, fyCode}, new BeanPropertyRowMapper(BudgetTotal.class));

			//if query returned results
			if(!budgetTotalList.isEmpty()){			
				budgetTotal = budgetTotalList.get(0);
				budgetTotal.setTableName(budgetTable);	
			}

		} catch (Exception e) {
			System.err.println("error BudgetDao.calcTotalsForBudgetTableFiscalYear "+e.getMessage());
			return budgetTotal;
		}
		return budgetTotal;
	}
	
	
	
	
	public BudgetTotal calcTotalsForSupplyEquipTableFiscalYear(String budgetTable, 
													int supplyEquipCode, Long grantId, Long fyCode) {

		BudgetTotal budgetTotal = new BudgetTotal();
		try {			
						
			String query = "/* Formatted on 10/24/2016 11:08:35 AM (QP5 v5.185.11230.41888) */ "+
						  " SELECT gra_id, "+
						  "       fy_code, "+
						  "       SUM (grant_request) AS totalAmountRequested, "+
						  "       SUM (amount_approved) AS totalAmountApproved, "+
						  "       SUM (exp_submitted) AS totalExpensesSubmitted, "+
						  "       SUM (exp_aproved) AS totalExpensesApproved, "+
						  "       SUM (amend_amount) AS totalAmendment "+
						  "  FROM  supp_mat_equips "+
						  " WHERE gra_id = ? AND fy_code = ? AND smet_code = ? "+
						" GROUP BY gra_id, fy_code";

			List<BudgetTotal> budgetTotalList = jt.query(query,
							new Object[] { grantId, fyCode, supplyEquipCode}, new BeanPropertyRowMapper(BudgetTotal.class));

			//if query returned results
			if(!budgetTotalList.isEmpty()){			
				budgetTotal = budgetTotalList.get(0);
				budgetTotal.setTableName(budgetTable);	
			}		

		} catch (Exception e) {
			System.err.println("error BudgetDao.calcTotalsForSupplyEquipTableFiscalYear "+e.getMessage());
			return budgetTotal;
		}
		return budgetTotal;
	}

}
