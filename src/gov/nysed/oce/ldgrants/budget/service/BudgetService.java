package gov.nysed.oce.ldgrants.budget.service;

import gov.nysed.oce.ldgrants.budget.dao.BudgetDao;
import gov.nysed.oce.ldgrants.budget.domain.BudgetTotal;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;

public class BudgetService {

	private BudgetDao budgetDao = new BudgetDao();
	private static String purchasedServicesTableName = "CONTRACTED_SERVICES";
	private static String suppliesTableName = "SUPP_MAT_EQUIPS";
	private static String equipmentTableName = "SUPP_MAT_EQUIPS";
	private static String travelTableName = "TRAVEL_EXPENSES";
	private static int suppliesSmetCode = 1;
	private static int equipmentSmetCode = 2;
	
	
	
	
	
	public int calcTotalExpensesSubmittedForFy(Grant grant){
		return budgetDao.calcTotalExpenseSubmittedForFy(grant.getId(), grant.getFyCode());
	}
		
	/**
	 * @deprecated - if possible use method that passes new Grant object, calcTotalExpensesSubmittedForFy(Grant)
	 * @param grantId
	 * @param fyCode
	 * @return
	 */
	public int calcTotalExpensesSubmittedForFy(Long grantId, Long fyCode){
		return budgetDao.calcTotalExpenseSubmittedForFy(grantId, fyCode);
	}
	
	
	
	public BudgetTotal calcTotalPurchasedServicesForFiscalYear(Long grantId, Long fyCode){
		return budgetDao.calcTotalsForBudgetTableFiscalYear(purchasedServicesTableName, grantId, fyCode);
	}
	
	public BudgetTotal calcTotalTravelForFiscalYear(Long grantId, Long fyCode){
		return budgetDao.calcTotalsForBudgetTableFiscalYear(travelTableName, grantId, fyCode);
	}
	
	public BudgetTotal calcTotalEquipmentForFiscalYear(Long grantId, Long fyCode){
		return budgetDao.calcTotalsForSupplyEquipTableFiscalYear(equipmentTableName, equipmentSmetCode,
																grantId, fyCode);
	}
	
	public BudgetTotal calcTotalSuppliesForFiscalYear(Long grantId, Long fyCode){
		return budgetDao.calcTotalsForSupplyEquipTableFiscalYear(suppliesTableName, suppliesSmetCode, 
																grantId, fyCode);
	}
	
	
	
	
	
}
