// This testing class mainly for Authorization Table
package gov.nysed.oce.ldgrants.test.action;

import java.util.ArrayList;

import gov.nysed.oce.ldgrants.grants.grant.domain.Authorization;
import gov.nysed.oce.ldgrants.grants.grant.service.AuthorizationService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Ld150 extends TestAction {

	Authorization author = new Authorization();
	User user = new User();
	AuthorizationService service = new AuthorizationService();
	
	@Override
	protected boolean insert() {
		
		try{
			user.setCreatedBy("Vishal");
			author.setGraId(1L);
			author.setAutUser("Agg");
			author.setName("VA");
			author.setTitle("Dev-op");
			author.setId(service.insert(author, user));
			
			System.out.println("insert successful-------------");
			if(author.getId()>0){
				return true;
			}
		} catch (Exception e) {

			System.err.println("insert() " + e.getMessage());
		}
		return false;
	}
	
	@Override
	protected boolean select() {
		
		try{
		Authorization authorTemp = new Authorization();
		
		authorTemp = service.select(author.getId());
		
		if (author.getName().equals(authorTemp.getName()))
			return true;

	} catch (Exception e) {
		System.err.println("select() " + e.getMessage());
	}
		return false;
	}
	
	@Override
	protected boolean update() {
		Authorization temp = new Authorization();
		
		try{
			temp.setId(author.getId());
			temp.setGraId(21L);
			temp.setName("vagg");
			temp.setTitle("developer");
			
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
	protected boolean search() {
		
		try{
			ArrayList<Authorization> list = service.searchByTitle("developer");
			
			for(Authorization a: list){
				if(a.getGraId().equals(21L))
					
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
			boolean isDeleted = service.delete(author.getId());
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
