package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.List;

import gov.nysed.oce.ldgrants.user.domain.User;

public interface GenericDaoReadOnlyService<T, PK> {
	public T select(PK id);
	public List<T> selectAll();
}
