package gov.nysed.oce.ldgrants.sedref.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.dao.GenericReadOnlyDao;
import gov.nysed.oce.ldgrants.sedref.domain.SedAdminPosition;
import gov.nysed.oce.ldgrants.sedref.domain.SedContactInfo;

public class SedContactInfoDao extends GenericReadOnlyDao<SedContactInfo, Long>{

	@Override
	public SedContactInfo select(Long id) {
		
		String sql =
				"       SELECT CONTACT_ID, " +
				"       CONTACT_VALUE, " +
				"       CONTACT_TYPE_CODE, " +
				"       ADMIN_POS_ID, " +
				"       INST_ID " +
				"       FROM SED_CONTACT_INFO " +
				"       WHERE CONTACT_ID = ? ";

		try{
			SedContactInfo sedContactInfo = (SedContactInfo) jt.queryForObject(sql, new Object[] { id },
		        new BeanPropertyRowMapper<SedContactInfo>(SedContactInfo.class));
				
				return sedContactInfo;
				
			}catch (Exception e){
				System.out.println("SedContactInfoDao.select()" + e.getMessage());
			}
		return null;
	}
	
		

	@Override
	public List<SedContactInfo> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
		
	
	
public ArrayList<SedContactInfo> searchByAdminPositionId(Long id) {
		
		String sql =
				"       SELECT CONTACT_ID, " +
				"       CONTACT_VALUE, " +
				"       CONTACT_TYPE_CODE, " +
				"       ADMIN_POS_ID, " +
				"       INST_ID " +
				"       FROM SED_CONTACT_INFO " +
				"       WHERE ADMIN_POS_ID = ? and " +
				"       INACTIVE_DATE IS NULL ";

		try{
			List<SedContactInfo> sedContactInfo = jt.query(sql, new Object[] { id },
		        new BeanPropertyRowMapper<SedContactInfo>(SedContactInfo.class));
				
			if (sedContactInfo.size() > 0) 
				return (ArrayList<SedContactInfo>) sedContactInfo;
			else{
				System.out.println("no results");
				return null;
			}	
				
			}catch (Exception e){
				System.out.println("SedContactInfoDao.select()" + e.getMessage());
			}
		return null;
	}

}
