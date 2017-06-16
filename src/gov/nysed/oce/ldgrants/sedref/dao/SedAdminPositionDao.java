package gov.nysed.oce.ldgrants.sedref.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.address.domain.Address;
import gov.nysed.oce.ldgrants.grants.common.dao.GenericReadOnlyDao;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;

public class SedAdminPositionDao extends GenericReadOnlyDao<SedAdminPosition, Long>{

	@Override
	public SedAdminPosition select(Long id) {
		
		String sql =
				"       SELECT ADMIN_POS_ID, " +
				"       TITLE, " +
				"       FNAME, " +
				"       MI, " +
				"       LNAME, " +
				"       INST_ID, " +
				"       ADDR_ID, " +
				"       ADMIN_POS_TYPE_CODE " +
				"       FROM SED_ADMIN_POSITIONS " +
				"       WHERE ADMIN_POS_ID = ? " ;

		try{
			SedAdminPosition sedAdminPosition = (SedAdminPosition) jt.queryForObject(sql, new Object[] { id },
		        new BeanPropertyRowMapper<SedAdminPosition>(SedAdminPosition.class));
				
				return sedAdminPosition;
				
			}catch (Exception e){
				System.out.println("SedAdminPositionDao.select()" + e.getMessage());
			}
		return null;
	}
	
	
	

	@Override
	public List<SedAdminPosition> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
public ArrayList<SedAdminPosition> searchByInstId(Long id) {
		
		String sql =
				"       SELECT ADMIN_POS_ID, " +
				"       TITLE, " +
				"       FNAME, " +
				"       MI, " +
				"       LNAME, " +
				"       INST_ID, " +
				"       ADDR_ID, " +
				"       ADMIN_POS_TYPE_CODE " +
				"       FROM SED_ADMIN_POSITIONS " +
				"       WHERE INST_ID = ? and" +
				"       INACTIVE_DATE IS NULL ";

		try{
			List<SedAdminPosition> sedAdminPosition =  jt.query(sql, new Object[] { id },
		        new BeanPropertyRowMapper<SedAdminPosition>(SedAdminPosition.class));
				
			if (sedAdminPosition.size() > 0) {
				return (ArrayList<SedAdminPosition>) sedAdminPosition;
			} else
			{
				System.out.println("no results");
				return null;
			}	
				
			}catch (Exception e){
				System.out.println("SedAdminPositionDao.searchByInstId()" + e.getMessage());
			}
		return null;
	}

}
