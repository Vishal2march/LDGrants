package gov.nysed.oce.ldgrants.budget.domain;

public class ExpenditureType {

	private static int professionalStaffExpenseCode = 15;
	private static int supportStaffExpenseCode = 16;
	private static int purchasedServicesExpenseCode = 40;
	private static int supplyExpenseCode = 45;
	private static int travelExpenseCode = 46;
	private static int benefitsExpenseCode = 80;
	private static int remodelingExpenseCode = 30;
	private static int equipmentExpenseCode = 20;
	
	
	
	public static int getProfessionalStaffExpenseCode() {
		return professionalStaffExpenseCode;
	}
	public static void setProfessionalStaffExpenseCode(int professionalStaffExpenseCode) {
		ExpenditureType.professionalStaffExpenseCode = professionalStaffExpenseCode;
	}
	public static int getSupportStaffExpenseCode() {
		return supportStaffExpenseCode;
	}
	public static void setSupportStaffExpenseCode(int supportStaffExpenseCode) {
		ExpenditureType.supportStaffExpenseCode = supportStaffExpenseCode;
	}
	public static int getPurchasedServicesExpenseCode() {
		return purchasedServicesExpenseCode;
	}
	public static void setPurchasedServicesExpenseCode(int purchasedServicesExpenseCode) {
		ExpenditureType.purchasedServicesExpenseCode = purchasedServicesExpenseCode;
	}
	public static int getSupplyExpenseCode() {
		return supplyExpenseCode;
	}
	public static void setSupplyExpenseCode(int supplyExpenseCode) {
		ExpenditureType.supplyExpenseCode = supplyExpenseCode;
	}
	public static int getTravelExpenseCode() {
		return travelExpenseCode;
	}
	public static void setTravelExpenseCode(int travelExpenseCode) {
		ExpenditureType.travelExpenseCode = travelExpenseCode;
	}
	public static int getBenefitsExpenseCode() {
		return benefitsExpenseCode;
	}
	public static void setBenefitsExpenseCode(int benefitsExpenseCode) {
		ExpenditureType.benefitsExpenseCode = benefitsExpenseCode;
	}
	public static int getRemodelingExpenseCode() {
		return remodelingExpenseCode;
	}
	public static void setRemodelingExpenseCode(int remodelingExpenseCode) {
		ExpenditureType.remodelingExpenseCode = remodelingExpenseCode;
	}
	public static int getEquipmentExpenseCode() {
		return equipmentExpenseCode;
	}
	public static void setEquipmentExpenseCode(int equipmentExpenseCode) {
		ExpenditureType.equipmentExpenseCode = equipmentExpenseCode;
	}
	
}
