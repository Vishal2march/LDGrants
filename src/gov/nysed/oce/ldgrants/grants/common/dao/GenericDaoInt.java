package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;


import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface GenericDaoInt<T, PK> extends DatabaseConnectionInt{
	
		/**
		 * 
		 * @param obj
		 * @param user
		 * @return
		 */
		Long insert(T obj,User user);
		
		/**
		 * 
		 * @param obj
		 * @param user
		 * @return
		 */
		Long update(T obj,User user);
		
		/**
		 * delete method removes
		 * @param id - represents the 
		 * @return boolean value, confirming whether or not the DAO indicated the delete operation was successful
		 */
		boolean delete(PK id);
		
		/**
		 * Select
		 * Selects a single object based on passing the PK of that object. Typically this is the "id", but sometimes may have been some "code" as a String
		 * @param id
		 * @return A single object is returned
		 */
		T select(PK id);
		
		/**
		 * selectAll
		 * @return a sorted list of all objects for the entity. This is not practical if over hundreds of elements
		 */
		List<T> selectAll();
}
