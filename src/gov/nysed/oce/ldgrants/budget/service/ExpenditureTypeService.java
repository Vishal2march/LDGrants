package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.ExpenditureTypeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.ExpenditureType;
import gov.nysed.oce.ldgrants.grants.common.service.GenericDaoReadOnlyService;

public class ExpenditureTypeService implements GenericDaoReadOnlyService<ExpenditureType, Long> {

	ExpenditureTypeDao dao = new ExpenditureTypeDao();

	@Override
	public ExpenditureType select(Long id) {
		return dao.select(id);
	}

	@Override
	public List<ExpenditureType> selectAll() {
		return dao.selectAll();
	}
	
	
	public List<ExpenditureType> searchByFundCode(Long fundCode) {
		return dao.searchByFundCode(fundCode);
	}

}
