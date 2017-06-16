package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;


import gov.nysed.oce.ldgrants.grants.common.domain.StatusBean;

public class StatusDao implements StatusDaoInt{

	public StatusDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<StatusBean> selectStatusesByGrantId(Long grantId) {
		String sql ="/* Formatted on 11/9/2016 10:05:02 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT id, " +
				"       gra_id AS grantId, " +
				"       nat_id AS narrativeId, " +
				"       sta_lock AS nrrativeLock, " +
				"       status, " +
				"       created_by AS createdBy, " +
				"       modified_by AS modifiedBy, " +
				"       date_created AS dateCreated, " +
				"       date_modified AS dateModified " +
				"  FROM STATUSES " +
				" WHERE gra_id = ?"; 

	try {

		List<StatusBean>  statusList = jt.query(sql, new Object[] { grantId }, 
				new BeanPropertyRowMapper(StatusBean.class));

		return statusList;
	} catch (Exception ex) {
		System.err.println("FAILED: selectStatusesByGrantId() " + ex.toString());
	}
	return null;	
	}
}
