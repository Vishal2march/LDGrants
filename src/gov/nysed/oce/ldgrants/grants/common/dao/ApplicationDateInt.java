package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.ApplicationDate;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface ApplicationDateInt extends DatabaseConnectionInt{

	public ApplicationDate searchApplicationDateByFyFund(Long fyCode, Long fcCode);

}
