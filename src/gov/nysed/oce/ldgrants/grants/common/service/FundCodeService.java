package gov.nysed.oce.ldgrants.grants.common.service;


import java.util.List;
import gov.nysed.oce.ldgrants.grants.common.dao.FundCodeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.FundCode;
import gov.nysed.oce.ldgrants.user.domain.User;


public class FundCodeService implements GenericDaoService<FundCode, Long> {

	FundCodeDao dao = new FundCodeDao();

	@Override
	public Long insert(FundCode t, User user) {
		return dao.insert(t, user);
	}

	@Override
	public Long update(FundCode t, User user) {
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		return dao.delete(id);
	}

	@Override
	public FundCode select(Long id) {
		return dao.select(id);
	}

	@Override
	public List<FundCode> selectAll() {
		return dao.selectAll();
	}

}
