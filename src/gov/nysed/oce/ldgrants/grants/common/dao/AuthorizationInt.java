package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.Authorization;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface AuthorizationInt extends DatabaseConnectionInt{

	
	public Long getNextAuthorizationId();
	
	public long insertAuthorization(Authorization authorization, User user);
}
