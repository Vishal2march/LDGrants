package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.Authorization;
import gov.nysed.oce.ldgrants.user.domain.User;

public class AuthorizationDao implements AuthorizationInt{
	
	
	public Long getNextAuthorizationId() {
		return jt.queryForObject("SELECT authorization_seq.nextval FROM dual", Long.class);
	}

	
	public long insertAuthorization(Authorization authorization, User user) {
		int rows = 0;
		String update = "insert into AUTHORIZATIONS (ID, GRA_ID, NAME, TITLE, AUTH_DATE, "+
        " AUT_USER, VERSION, DATE_CREATED, CREATED_BY) "+
		" values (?, ?,?,?,sysdate, ?,?,sysdate,?) ";
		try {
			rows = jt.update(update,
					new Object[] { 
							authorization.getId(), 
							authorization.getGrantId(), 
							authorization.getName(),
							authorization.getTitle(), 
							authorization.getAuthorizingUser(), 
							authorization.getVersion(), 
							user.getCreatedBy()});
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error AuthorizationDao.insertAuthorization() " + ex.toString());
		} finally {
		}

		return authorization.getId();//new; insert should return new ID
	}

}
