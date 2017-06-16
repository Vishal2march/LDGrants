package gov.nysed.oce.ldgrants.grants.common.enumeration;

public enum ExpenditureTypeEnum {

	PROFESSIONAL_SALARIES(15), 
	SUPPORT_STAFF_SALARIES(16), 
	EQUIPMENT(20), 
	MINOR_REMODELING(30), 
	PURCHASED_SERVICES(40), 
	SUPPLIES_AND_MATERIALS(45), 
	TRAVEL_EXPENSES(46), 
	EMPLOYEE_BENIFITS(80), 
	OTHER_EXPENSES(0);

	private final long expenditureTypeId;

	ExpenditureTypeEnum(long expenditureTypeId) {
		this.expenditureTypeId = expenditureTypeId;
	}

	public long getExpenditureTypeId() {
		return expenditureTypeId;
	}

	// return expenditureId based on expense code
	public static ExpenditureTypeEnum getExpenditureType(int expenseCode) {
		ExpenditureTypeEnum expenditureTypeEnum = null;
		switch (expenseCode) {
		case 1:
			expenditureTypeEnum = PROFESSIONAL_SALARIES;
			break;
		case 2:
			expenditureTypeEnum = EMPLOYEE_BENIFITS;
			break;
		case 3:
			expenditureTypeEnum = PURCHASED_SERVICES;
			break;
		case 4:
			expenditureTypeEnum = SUPPLIES_AND_MATERIALS;
			break;
		case 5:
			expenditureTypeEnum = OTHER_EXPENSES; // "OTHER EXPENSES" IS NO
													// LONGER USED
			break;
		case 6:
			expenditureTypeEnum = TRAVEL_EXPENSES;
			break;
		case 7:
			expenditureTypeEnum = SUPPORT_STAFF_SALARIES;
			break;
		case 8:
			expenditureTypeEnum = EQUIPMENT;
			break;
		case 9:
			expenditureTypeEnum = MINOR_REMODELING;
			break;
		}
		return expenditureTypeEnum;
	}
}
