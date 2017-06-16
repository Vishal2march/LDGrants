package gov.nysed.oce.ldgrants.grants.literacy.utils;

import java.util.ArrayList;
import java.util.List;

import construction.AllocationYearBean;
import gov.nysed.oce.ldgrants.budget.domain.BudgetTotal;
import gov.nysed.oce.ldgrants.budget.service.BudgetService;
import gov.nysed.oce.ldgrants.budget.service.BudgetSummaryService;
import gov.nysed.oce.ldgrants.grants.common.dao.NarrativeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import mypackage.BudgetDBHandler;

public class SubmitValidationService {
	
	public static double literacyBudgetRange = 0.10;//literacy: expenses must be within 10% initial budget
	
	
	/**
	 * Determine all required narrative_types.  Check if this grant has a record for each required 
	 * narrative_type.  Return list of all narrative_types missing.
	 * @param fiscalYearId
	 * @param fundCode
	 * @param formTypeId
	 * @param grantId
	 * @return
	 */
	public List<NarrativeType> validateRequiredNarratives(long fiscalYearId, long fundCode, long formTypeId, long grantId){
		
		List<NarrativeType> missingNarrativeTypes = new ArrayList<NarrativeType>();
		try{			
			//get all required narrative types for this fy/fund/version
			NarrativeDao narrativeDao = new NarrativeDao();
			List<NarrativeType> requiredNarrativeTypes = 
								narrativeDao.searchNarrativeTypesByVersion(fiscalYearId, fundCode, formTypeId);
			
			
			//for each required narrative type; check if it exists in db
			//note: faster to just query for all narrative types at once? select...in{}
			for(NarrativeType narrativeType: requiredNarrativeTypes){
				
				//does grant have this narrative type?
				ProjectNarrative narrative = narrativeDao.searchNarrativeByGrantType(
													grantId, narrativeType.getNarrativeTypeId());
				
				
				if(narrative.getNarrative()==null || narrative.getNarrative().equals("")){
					//this narrative not completed; add to missing list
					missingNarrativeTypes.add(narrativeType);					
				}					
			}
			
		}catch(Exception e){
			System.err.println("SubmitValidationService.validateRequiredNarratives(): "+e.getMessage());
		}	
		if(missingNarrativeTypes.size()==0)
			return null;
		else
			return missingNarrativeTypes;				
	}
	
	
	
	
public Boolean validateExpensesEqualAppropriation(Grant grant){
		
		boolean expensesValid=false;
		try{		
			
			// get the total final appropriation for this inst/FY/fund
			BudgetDBHandler bdh = new BudgetDBHandler();
			AllocationYearBean allocationYearBean = bdh.getAllocationForInstFy(grant.getFyCode().intValue(),
								grant.getFcCode().intValue(), grant.getInstId());
			
			//get sum final expenses from all budget tables
			BudgetService budgetService = new BudgetService();
			int totalExpenses = budgetService.calcTotalExpensesSubmittedForFy(grant);
			
			//compare: final approp must equal final expenses
			if(allocationYearBean.getFinalRecommend()== totalExpenses)
				expensesValid = true;
			
		}catch(Exception e){
			System.err.println("SubmitValidationService.validateExpensesEqualAppropriation(): "+e.getMessage());
		}	
		return expensesValid;		
	}






/**
 * for each budget category, the final expenses for a FY cannot be >10% different from the total entered 
 * on the initial "budget summary" for that category/FY.  (per KBalsen)
 * Expenses must +/- 10% summary
 * @param grant
 * @return
 */
public List<String> validateExpensesWithinRangeOfInitialBudget(long fyCode, Grant grant){
	
	List<String> outOfRangeCategory = new ArrayList<String>();//budget categories that are out of range
	try{		
		BudgetService budgetService = new BudgetService();
		BudgetSummaryService budgetSummaryService = new BudgetSummaryService();
		
		
		
		//get total expenses for each literacy budget category for given FY
		BudgetTotal purchasedExpenseTotal = budgetService.calcTotalPurchasedServicesForFiscalYear(
											grant.getId(), fyCode);
		
		BudgetTotal travelExpenseTotal = budgetService.calcTotalTravelForFiscalYear(
											grant.getId(), fyCode);
		
		BudgetTotal supplyExpenseTotal = budgetService.calcTotalSuppliesForFiscalYear(
											grant.getId(), fyCode);
		
		BudgetTotal equipmentExpenseTotal = budgetService.calcTotalEquipmentForFiscalYear(
											grant.getId(), fyCode);
				
		
		
		//get the total "initial budget summary" for each category/FY
		int purchasedServicesSummary = budgetSummaryService.calcTotalPurchasedServicesSummaryForFy(
										grant.getId(), fyCode);
		
		int travelSummary = budgetSummaryService.calcTotalTravelSummaryForFy(
										grant.getId(), fyCode);
		
		int supplySummary = budgetSummaryService.calcTotalSupplySummaryForFy(
										grant.getId(), fyCode);
		
		int equipmentSummary = budgetSummaryService.calcTotalEquipmentSummaryForFy(
										grant.getId(), fyCode);
			
		
		
		//compare each expense to budget summary; expense cannot be more than 10% different from summary
		double dollarRange = purchasedServicesSummary * literacyBudgetRange;
		double maxAmount = purchasedServicesSummary + dollarRange;//summary plus 10% is max amount of expenses
		double minAmount = purchasedServicesSummary - dollarRange;//summary minus 10% is min amount of expenses
		//expenses must be within range
		if(purchasedExpenseTotal.getTotalExpensesSubmitted()!=null){
			if(purchasedExpenseTotal.getTotalExpensesSubmitted() >  maxAmount 
					|| 
			   purchasedExpenseTotal.getTotalExpensesSubmitted() < minAmount)			
					outOfRangeCategory.add("Purchased Services");
		}
		
		
		dollarRange = travelSummary * literacyBudgetRange;
		maxAmount = travelSummary + dollarRange;//summary plus 10% is max amount of expenses
		minAmount = travelSummary - dollarRange;//summary minus 10% is min amount of expenses
		//expenses must be within range
		if(travelExpenseTotal.getTotalExpensesSubmitted()!=null){
			if(travelExpenseTotal.getTotalExpensesSubmitted() >  maxAmount 
					|| 
					travelExpenseTotal.getTotalExpensesSubmitted() < minAmount)			
					outOfRangeCategory.add("Travel");
		}
		
		
		dollarRange = supplySummary * literacyBudgetRange;
		maxAmount = supplySummary + dollarRange;//summary plus 10% is max amount of expenses
		minAmount = supplySummary - dollarRange;//summary minus 10% is min amount of expenses
		//expenses must be within range
		if(supplyExpenseTotal.getTotalExpensesSubmitted()!=null){
			if(supplyExpenseTotal.getTotalExpensesSubmitted() >  maxAmount 
					|| 
					supplyExpenseTotal.getTotalExpensesSubmitted() < minAmount)			
					outOfRangeCategory.add("Supplies and Materials");
		}
		
		
		dollarRange = equipmentSummary * literacyBudgetRange;
		maxAmount = equipmentSummary + dollarRange;//summary plus 10% is max amount of expenses
		minAmount = equipmentSummary - dollarRange;//summary minus 10% is min amount of expenses
		//expenses must be within range
		if(equipmentExpenseTotal.getTotalExpensesSubmitted()!=null){
			if(equipmentExpenseTotal.getTotalExpensesSubmitted() >  maxAmount 
					|| 
					equipmentExpenseTotal.getTotalExpensesSubmitted() < minAmount)			
					outOfRangeCategory.add("Equipment");
		}
		
					
	}catch(Exception e){
		System.err.println("SubmitValidationService.validateExpensesWithinRangeOfInitialBudget(): "+e.getMessage());
	}	
	return outOfRangeCategory;		
}

}
