package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface InstitutionInt extends DatabaseConnectionInt {

	public Institution selectInstitution(Long instId);
}
