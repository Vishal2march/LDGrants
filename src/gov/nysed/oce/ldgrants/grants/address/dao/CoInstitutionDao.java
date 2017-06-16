package gov.nysed.oce.ldgrants.grants.address.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import gov.nysed.oce.ldgrants.grants.address.domain.CoInstitution;
import gov.nysed.oce.ldgrants.user.domain.User;

public class CoInstitutionDao extends GenericDao<CoInstitution, Long> {

	public CoInstitutionDao(){}
	
	@Override
	public CoInstitution select(Long id) {
		
		String sql = " SELECT ID AS id, " +
				"       INST_ID AS instId, " +
				"       GRA_ID AS graId, " +
				"       IS_LEAD AS isLead, " +
				"       CREATED_BY AS createdBy, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       DATE_CREATED AS dateCreated, " +
				"       DATE_MODIFIED AS dateModified " +
				"       FROM CO_INSTITUTIONS " +
				"       WHERE id = ? ";
		
		try{
			CoInstitution record = (CoInstitution) jt.queryForObject(sql, new Object[] { id },
			        new BeanPropertyRowMapper<CoInstitution>(CoInstitution.class));
					
					return record;
				}catch (Exception e){
					System.out.println("CoInstitutionDao.select()" + e.getMessage());
				}
			return null;
	}
	
	@Override
	public Long insert(CoInstitution coInstitution, User user) {
		
		String sql = "INSERT INTO CO_INSTITUTIONS (ID, " +
				"                             INST_ID, " +
				"                             GRA_ID, " +
				"                             IS_LEAD, " +
				"                             CREATED_BY, " +
				"                             MODIFIED_BY, " +
				"                             DATE_CREATED, " +
				"                             DATE_MODIFIED) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             SYSDATE) " ;

				try{
					
					Long pk  = getNextId("CO_INST_SEQ");
					
	int rowAdd = jt.update(sql, new Object[] { pk,coInstitution.getInstId(),coInstitution.getGraId(),
			coInstitution.getIsLead(),user.getCreatedBy(),user.getModifiedBy()});
	if(rowAdd == 1 ){
		 System.out.println("1 row added");
		 return pk;
	}
	 else{				
		return null;
	 }	
				}catch(Exception e){
					System.err.println("error insert() " + e.toString());
				}
						return null;
					}

	@Override
	public Long update(CoInstitution coInstitution, User user) {
		
		String updateSql = "UPDATE CO_INSTITUTIONS " +
				"   SET IS_LEAD = ?, MODIFIED_BY = ?, DATE_MODIFIED = SYSDATE " +
				"   WHERE ID = ? " ;
		
		try{
		long rows = (long) jt.update(updateSql, new Object[] {coInstitution.getIsLead(),user.getModifiedBy(),
				coInstitution.getId()});
		
		if(rows == 1){
  		  System.out.println("1 row updated");
  			 return (long) rows;
  	  }
  }
	catch(Exception e){
  	System.err.println("error update() " + e.toString());
   }
		return null;
	}

	@Override
	public boolean delete(Long id) {
		
		 String sql = "DELETE FROM CO_INSTITUTIONS WHERE id=?";		
			try {
				
				int rows = jt.update(sql, new Object[] { id });
				
				return (rows == 1);
	      
			}
			catch (Exception e) 
			{
				System.out.println("CoInstitutionDao.delete()" + e.getMessage());
			}
		return false;
	}

	

	@Override
	public List<CoInstitution> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
