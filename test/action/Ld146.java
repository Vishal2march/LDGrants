package gov.nysed.oce.ldgrants.test.action;

import java.sql.Date;
import java.util.ArrayList;

import gov.nysed.oce.ldgrants.grants.grant.domain.Grant;
import gov.nysed.oce.ldgrants.grants.grant.service.GrantService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Ld146 extends TestAction {

	private String createdBy;
	private String modifiedBy;
	private Date dateCreated;
	private Long fcCode;
	private Long fyCode;
	private Long id;
	private Long prmId;
	private String lockApp;
	private String name;

	Grant grants = new Grant();
	User user = new User();
	GrantService service = new GrantService();

	@Override
	protected boolean insert() {

		// make domain class object

		try {
			// Hard coded values to be inserted inside GRANTS table
			grants.setName("David");
			grants.setFyCode(5L);
			grants.setFcCode(5L);
			grants.setLockApp("1");
			user.setCreatedBy("Vishal");
			user.setModifiedBy("Anton");

			// make service class object
			// GrantService grantService = new GrantService();

			// set primary Id returned by method.
			grants.setId(service.insert(grants, user));

			System.out.println("insert successful-------------");

			if (grants.getId() > 0) {
				return true;
			}
		} catch (Exception e) {

			System.err.println("insert() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean select() {

		// create a temporary instance for Grant bean class
		Grant grantsTemp = new Grant();

		try {
			// create a new service class object
			// GrantService service = new GrantService();

			grantsTemp = service.select(grants.getId());

			if (grants.getName().equals(grantsTemp.getName()))
				return true;

		} catch (Exception e) {
			System.err.println("select() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean update() {
		// boolean result = false;
		Grant ggrants = new Grant();

		try {
			ggrants.setId(grants.getId());
			ggrants.setName("Emily");
			user.setCreatedBy("John");
			user.setModifiedBy("John");

			GrantService updateService = new GrantService();

			Long rows = updateService.update(ggrants, user);

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

		int count = 0;
		try {
			ArrayList<Grant> grantList = service.searchByName("Emily");

			for (Grant list : grantList) {

				count++;
				System.out.println(count++);
				if (grantList.size() == 4) {
					System.out.println("records returned");
					return true;
				}

			}
		} catch (Exception e) {
			System.err.println("search() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean delete() {

		try {

			boolean isDeleted = service.delete(grants.getId());

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

	public Long getFcCode() {
		return fcCode;
	}

	public void setFcCode(Long fcCode) {
		this.fcCode = fcCode;
	}

	public Long getFyCode() {
		return fyCode;
	}

	public void setFyCode(Long fyCode) {
		this.fyCode = fyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrmId() {
		return prmId;
	}

	public void setPrmId(Long prmId) {
		this.prmId = prmId;
	}

	public String getLockApp() {
		return lockApp;
	}

	public void setLockApp(String lockApp) {
		this.lockApp = lockApp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
