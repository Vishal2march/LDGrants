package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.dao.GrantStaffDao;
import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.user.domain.User;

public class GrantStaffService implements GenericDaoService<GrantStaff, Long> {

	
	GrantStaffDao dao = new GrantStaffDao();
	
	public ArrayList<GrantStaff> searchByLname (String lname){
		return dao.searchByLname(lname);
		
	}
	
	public ArrayList<GrantStaff> searchByTitle (String title){
		return dao.searchByTitle(title);
	}
	@Override
	public Long insert(GrantStaff t, User user) {
		
		
		return dao.insert(t, user);
	}

	@Override
	public Long update(GrantStaff t, User user) {
	
		
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		
		return dao.delete(id);
	}

	@Override
	public GrantStaff select(Long id) {
		
		return dao.select(id);
	}

	@Override
	public List<GrantStaff> selectAll() {
		
		return null;
	}
	
	
	
	
	public GrantStaff searchByGrantIdStaffTypeId(Long grantId, long staffTypeId){
		return dao.searchByGrantIdStaffTypeId(grantId, staffTypeId);
		
	}

}
