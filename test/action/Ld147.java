package gov.nysed.oce.ldgrants.test.action;

import java.util.ArrayList;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;

import gov.nysed.oce.ldgrants.grants.grant.domain.ProjectCategory;
import gov.nysed.oce.ldgrants.grants.grant.service.ProjectCategoryService;

public class Ld147 extends TestAction {

	private String createdBy;
	private String descr;
	private String modifiedBy;
	private Date dateCreated;
	private Date dateModified;
	private Date inactiveDate;
	private Long id;

	ProjectCategory category = new ProjectCategory();
	ProjectCategoryService service = new ProjectCategoryService();

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected boolean search() {

		ArrayList<ProjectCategory> project = service.selectProjectDescr("In");
		for (ProjectCategory p : project) {
			System.out.println("Action Project Id: " + p.getId());
			System.out.println("Action Project descr: " + p.getDescr());
			System.out.println("Action Project Modified By: " + p.getModifiedBy());
			System.out.println("Action Project Created By: " + p.getCreatedBy());
		}

		boolean countItems;
		if (countItems = project.size() == 5) {
			System.out.println("records returned-----");
			return true;
		} else {
			System.out.println(" no records-----");
			return false;
		}
	}

	@Override
	protected boolean select() {

		ProjectCategory pc = service.selectProjectId(3L);

		System.out.println("Action Project Descr: " + pc.getDescr());
		System.out.println("Action Project Modified By: " + pc.getModifiedBy());
		System.out.println("Action Project Id: " + pc.getId());
		System.out.println("Action Project Created By: " + pc.getCreatedBy());

		if (pc.getDescr().equals("Inactive Records")) {
			System.out.println("Getting results");
			return true;
		} else {
			System.out.println("no result");
			return false;
		}

	}

	@Override
	protected boolean insert() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

}
