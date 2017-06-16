package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.dao.ContactDao;
import gov.nysed.oce.ldgrants.grants.address.domain.Contact;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ContactService implements GenericDaoService<Contact, Long>{
	
	ContactDao dao = new ContactDao();

	@Override
	public Long insert(Contact t, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long update(Contact t, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contact select(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<Contact> searchByProjectManagerId(Long id) {
		return dao.searchByProjectManagerId(id);
		
	}

	
	
	public List<Contact> searchByGrantStaffId(Long id) {
		return dao.searchByGrantStaffId(id);
		
	}
	
	
	
}
