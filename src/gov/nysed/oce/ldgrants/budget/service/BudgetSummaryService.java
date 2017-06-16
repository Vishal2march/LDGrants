package gov.nysed.oce.ldgrants.budget.service;

import gov.nysed.oce.ldgrants.budget.dao.BudgetSummaryDao;
import gov.nysed.oce.ldgrants.budget.domain.ExpenditureType;

public class BudgetSummaryService {
	
	private BudgetSummaryDao budgetSummaryDao = new BudgetSummaryDao();
	
	
	

	public int calcTotalPurchasedServicesSummaryForFy(long grantId, long fyCode){
		return budgetSummaryDao.calcTotalBudgetSummaryForFyExpenseType(
				grantId, fyCode, ExpenditureType.getPurchasedServicesExpenseCode());
	}
	
	
	public int calcTotalTravelSummaryForFy(long grantId, long fyCode){
		return budgetSummaryDao.calcTotalBudgetSummaryForFyExpenseType(
				grantId, fyCode, ExpenditureType.getTravelExpenseCode());
	}
	
	
	public int calcTotalSupplySummaryForFy(long grantId, long fyCode){
		return budgetSummaryDao.calcTotalBudgetSummaryForFyExpenseType(
				grantId, fyCode, ExpenditureType.getSupplyExpenseCode());
	}
	
	
	public int calcTotalEquipmentSummaryForFy(long grantId, long fyCode){
		return budgetSummaryDao.calcTotalBudgetSummaryForFyExpenseType(
				grantId, fyCode, ExpenditureType.getEquipmentExpenseCode());
	}
	
}
