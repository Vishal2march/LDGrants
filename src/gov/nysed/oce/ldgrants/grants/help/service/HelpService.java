package gov.nysed.oce.ldgrants.grants.help.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.service.GenericDaoService;
import gov.nysed.oce.ldgrants.grants.help.dao.HelpDao;
import gov.nysed.oce.ldgrants.grants.help.domain.Help;
import gov.nysed.oce.ldgrants.user.domain.User;

public class HelpService implements GenericDaoService<Help,Long> {

	HelpDao dao = new HelpDao();
	
	@Override
	public Long insert(Help t, User user) {
		
		return dao.insert(t, user);
	}

	@Override
	public Long update(Help t, User user) {
		
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		return dao.delete(id);
	}

	@Override
	public Help select(Long id) {
	
		return dao.select(id);
	}

	@Override
	public List<Help> selectAll() {
		
		return dao.selectAll();
	}
	
	public ArrayList<Help> searchByFcCode (Long fcCode){
		return dao.searchByFcCode(fcCode);
		
	}
	
	public Help searchByFcCodeFyCode (Long fcCode, Long fyCode ){
		return dao.searchByFcCodeFyCode(fcCode, fyCode);
		
	}
	
	public Help searchByLocationLocationId (String Description, Long locationId){
		return dao.searchByLocationLocationId(Description, locationId);
		
	}
	
	

}
