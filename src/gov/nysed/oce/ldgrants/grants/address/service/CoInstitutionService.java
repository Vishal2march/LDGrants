package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.List;
import gov.nysed.oce.ldgrants.grants.address.dao.CoInstitutionDao;
import gov.nysed.oce.ldgrants.grants.address.domain.CoInstitution;
import gov.nysed.oce.ldgrants.user.domain.User;

public class CoInstitutionService implements GenericDaoService<CoInstitution, Long>
{
	CoInstitutionDao dao = new CoInstitutionDao();
	

	@Override
	public Long insert(CoInstitution t, User user) {
		
		return dao.insert(t, user);
	}

	@Override
	public Long update(CoInstitution t, User user) {
		
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		return dao.delete(id);
	}

	@Override
	public CoInstitution select(Long id) {
		
		return dao.select(id);
	}

	@Override
	public List<CoInstitution> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
