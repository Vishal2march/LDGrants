package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantStatus;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface GrantInt extends DatabaseConnectionInt {

	public Grant selectGrant(Long id);
	
	public long updateGrantForFinalSubmit(Long grantId, User user);

	public GrantStatus selectGrantStatusItems(Long grantId);
	
	public List<Grant> searchByInstId(Long id); 
}
