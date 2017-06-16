package gov.nysed.oce.ldgrants.test.action;

import java.util.Date;
import gov.nysed.oce.ldgrants.grants.grant.domain.FiscalYear;
import gov.nysed.oce.ldgrants.grants.grant.service.FiscalYearService;

public class Ld148 extends TestAction {

	private Long code;
	private Long description;
	private Date dateCreated;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	
	FiscalYear fiscal = new FiscalYear();
	FiscalYearService service = new FiscalYearService();
	@Override
	protected boolean search() {
		
		return false;
	}
	@Override
	public boolean select() {
		
		FiscalYear fiscal = service.select(10L);
		
		System.out.println("Action Name: " + fiscal.getCreatedBy());
		System.out.println("Action Name: " + fiscal.getEndDate());
		System.out.println("Action Name: " + fiscal.getDescription());
		System.out.println("Action Name: " + fiscal.getStartDate());
		
		if (fiscal.getCreatedBy().equals("shusak")) {
			System.out.println("Getting results----");
			return true;
		} else {
			System.out.println("no result-----");
			return false;
		}
	}
	@Override
	public boolean insert() {
		
		return false;
	}
	@Override
	public boolean update() {
		
		return false;
	}
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Long getDescription() {
		return description;
	}
	public void setDescription(Long description) {
		this.description = description;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
