package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.StatusBean;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface StatusDaoInt extends DatabaseConnectionInt  {

	public List<StatusBean> selectStatusesByGrantId(Long grantId);
}
