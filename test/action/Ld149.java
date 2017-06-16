// This testing class mainly for ProjectNarrative Table

package gov.nysed.oce.ldgrants.test.action;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;

import gov.nysed.oce.ldgrants.grants.grant.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.grant.service.ProjectNarrativeService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Ld149 extends TestAction {

	private String createdBy;
	private Date dateCreated;
	private Date dateModified;
	private Long graId;
	private Long id;
	private String modifiedBy;
	private Clob narrativeDescr;
	private Long natId;
	private String statisticDescr;

	ProjectNarrative project = new ProjectNarrative();
	User user = new User();
	ProjectNarrativeService service = new ProjectNarrativeService();

	@Override
	protected boolean insert() {

		try {
			user.setCreatedBy("Vishal");
			project.setGraId(2L);
			project.setNatId(83L);
			project.setId(service.insert(project, user));

			System.out.println("insert successful-------------");
			if (project.getId() > 0) {
				return true;
			}
		} catch (Exception e) {

			System.err.println("insert() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean select() {

		ProjectNarrative projectTemp = new ProjectNarrative();

		try {
			projectTemp = service.select(project.getId());

			if (project.getNatId().equals(projectTemp.getNatId()))
				return true;

		} catch (Exception e) {
			System.err.println("select() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean update() {

		ProjectNarrative pnarr = new ProjectNarrative();

		try {
			pnarr.setId(project.getId());
			pnarr.setGraId(2750L);
			pnarr.setNatId(19L);
			user.setModifiedBy("vishal");

			ProjectNarrativeService updateService = new ProjectNarrativeService();
			Long rows = updateService.update(pnarr, user);

			System.out.println("UPDATED ROWS == " + rows);

			if (rows >= 0) {
				return true;
			}

		} catch (Exception e) {
			System.err.println("update() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean search() {

		try {
			ArrayList<ProjectNarrative> list = service.searchByGraId(2750L);

			for (ProjectNarrative p : list) {
				if (p.getNatId().equals(19L))
					return true;
			}
		} catch (Exception e) {
			System.err.println("search() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean delete() {

		try {
			boolean isDeleted = service.delete(project.getId());
			System.out.println("delete execute successful");
			System.out.println(isDeleted);

			if (isDeleted)
				return true;

		} catch (Exception e) {
			System.err.println("delete() " + e.getMessage());
		}

		return false;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Long getGraId() {
		return graId;
	}

	public void setGraId(Long graId) {
		this.graId = graId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Clob getNarrativeDescr() {
		return narrativeDescr;
	}

	public void setNarrativeDescr(Clob narrativeDescr) {
		this.narrativeDescr = narrativeDescr;
	}

	public Long getNatId() {
		return natId;
	}

	public void setNatId(Long natId) {
		this.natId = natId;
	}

	public String getStatisticDescr() {
		return statisticDescr;
	}

	public void setStatisticDescr(String statisticDescr) {
		this.statisticDescr = statisticDescr;
	}

	public ProjectNarrative getProject() {
		return project;
	}

	public void setProject(ProjectNarrative project) {
		this.project = project;
	}

	public ProjectNarrativeService getService() {
		return service;
	}

	public void setService(ProjectNarrativeService service) {
		this.service = service;
	}

}
