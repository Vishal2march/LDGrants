package gov.nysed.oce.ldgrants.budget.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BudgetSummaryDao implements BudgetSummaryInt{
	


	public int calcTotalBudgetSummaryForFyExpenseType(Long grantId, Long fyCode, int expenseTypeId) {

		int total=0;
		try {

			List queryList = jt.queryForList("/* Formatted on 10/24/2016 1:18:01 PM (QP5 v5.185.11230.41888) */ "+
											" SELECT SUM (amount) AS budgetTotal "+
											"  FROM BUDGET_SUMMARY "+
											"  WHERE et_id = ? AND gra_id = ? AND fy_id = ?",
							new Object[] { expenseTypeId, grantId, fyCode });

			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);

				if (queryMap.get("budgetTotal") != null)
					total = ((BigDecimal) queryMap.get("budgetTotal")).intValue();				
			}

		} catch (Exception e) {
			System.err.println("error BudgetDao.calcTotalBudgetSummaryForFyExpenseType "+e.getMessage());
			return 0;
		}
		return total;
	}
	
	
	

}
