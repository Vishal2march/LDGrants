package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.dao.AddressDao;
import gov.nysed.oce.ldgrants.grants.address.domain.Address;
import gov.nysed.oce.ldgrants.user.domain.User;

public class AddressService implements GenericDaoService<Address, Long>{
	
	AddressDao addressDao = new AddressDao();
	
	public ArrayList<Address> searchByAddrLine1(String addrLine1) {
		
		return addressDao.searchByAddrLine1(addrLine1);
	}

	@Override
	public Long insert(Address t, User user) {
		
		return addressDao.insert(t, user);
	}

	@Override
	public Long update(Address t, User user) {
		
		return addressDao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		return addressDao.delete(id);
	}

	@Override
	public Address select(Long id) {
		
		return addressDao.select(id);
	}

	@Override
	public List<Address> selectAll() {
	
		return null;
	}

}
