// This testing class mainly for GrantSubmission Table

package gov.nysed.oce.ldgrants.test.action;

import gov.nysed.oce.ldgrants.grants.grant.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.grant.service.GrantSubmissionService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Ld151 extends TestAction {

	
	GrantSubmission grant = new GrantSubmission();
	User user = new User();
	GrantSubmissionService service = new GrantSubmissionService();
	
	@Override
	protected boolean insert() {
	
		try{
			user.setCreatedBy("Vishal");
			grant.setGraId(21L);;
			grant.setGrasUser("VISHAL");
			grant.setFmtId(1L);
			grant.setId(service.insert(grant, user));
			

			System.out.println("insert successful-------------");
			if(grant.getId()>0){
				return true;
			}
		} catch (Exception e) {

			System.err.println("insert() " + e.getMessage());
		}
		return false;
	}

	@Override
	protected boolean search() {
	
		return false;
	}

	@Override
	protected boolean select() {
		
		try{
		GrantSubmission grantTemp = new GrantSubmission();
		grantTemp = service.select(grant.getId());
		
		if(grant.getGrasUser().equals(grantTemp.getGrasUser())){
			
			return true;
		}
		} catch (Exception e) {
			System.err.println("select() " + e.getMessage());
		}
			return false;
	}

	
	@Override
	protected boolean update() {
		
		try{
		GrantSubmission temp = new GrantSubmission();
		
		temp.setId(grant.getGraId());
		temp.setGraId(490L);
		
		Long rows = service.update(temp, user);

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
	protected boolean delete() {
	
		try {
			boolean isDeleted = service.delete(grant.getId());
			System.out.println("delete execute successful");
			System.out.println(isDeleted);

			if (isDeleted)
				return true;

		} catch (Exception e) {
			System.err.println("delete() " + e.getMessage());
		}
		
		return false;
	}

}
