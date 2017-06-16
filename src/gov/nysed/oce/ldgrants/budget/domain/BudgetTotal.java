package gov.nysed.oce.ldgrants.budget.domain;

/**
 * This object not based on database table.  It is used for storing budget/category totals
 * calculated in a service.
 * @author shusak
 *
 */
public class BudgetTotal {
	
	private long fyCode;
	private String tableName;
	private Integer totalAmountRequested;
	private Integer totalAmountApproved;
	private Integer totalExpensesSubmitted;
	private Integer totalExpensesApproved;
	private Integer totalAmendment;
	
	
	public long getFyCode() {
		return fyCode;
	}
	public void setFyCode(long fyCode) {
		this.fyCode = fyCode;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getTotalAmountRequested() {
		return totalAmountRequested;
	}
	public void setTotalAmountRequested(Integer totalAmountRequested) {
		this.totalAmountRequested = totalAmountRequested;
	}
	public Integer getTotalAmountApproved() {
		return totalAmountApproved;
	}
	public void setTotalAmountApproved(Integer totalAmountApproved) {
		this.totalAmountApproved = totalAmountApproved;
	}
	public Integer getTotalExpensesSubmitted() {
		return totalExpensesSubmitted;
	}
	public void setTotalExpensesSubmitted(Integer totalExpensesSubmitted) {
		this.totalExpensesSubmitted = totalExpensesSubmitted;
	}
	public Integer getTotalExpensesApproved() {
		return totalExpensesApproved;
	}
	public void setTotalExpensesApproved(Integer totalExpensesApproved) {
		this.totalExpensesApproved = totalExpensesApproved;
	}
	public Integer getTotalAmendment() {
		return totalAmendment;
	}
	public void setTotalAmendment(Integer totalAmendment) {
		this.totalAmendment = totalAmendment;
	}

}
