package gov.nysed.oce.ldgrants.budget.dao;

import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface BudgetSummaryInt extends DatabaseConnectionInt{
	
	public int calcTotalBudgetSummaryForFyExpenseType(Long grantId, Long fyCode, int expenseTypeId);

}
