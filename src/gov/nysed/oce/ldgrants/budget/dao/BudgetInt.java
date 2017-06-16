package gov.nysed.oce.ldgrants.budget.dao;


import gov.nysed.oce.ldgrants.budget.domain.BudgetTotal;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface BudgetInt extends DatabaseConnectionInt{

	
		public int calcTotalExpenseSubmittedForFy(Long grantId, Long fyCode);
		
		public BudgetTotal calcTotalsForBudgetTableFiscalYear(String budgetTable, Long grantId, Long fyCode);
}
