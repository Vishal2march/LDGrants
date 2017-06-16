package gov.nysed.oce.ldgrants.grants.address.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import gov.nysed.oce.ldgrants.grants.address.domain.Address;
import gov.nysed.oce.ldgrants.user.domain.User;

public class AddressDao extends GenericDao<Address, Long> {

	
	public AddressDao()
	{}

	@Override
	public Address select(Long id) {
		
		String sql =
				"       SELECT ID AS id, " +
				"       ADDR_LINE1 AS addrLine1, " +
				"       ADDR_TYPE_CODE AS addrTypeCode, " +
				"       BLDG_ID AS bldgId, " +
				"       CITY AS city, " +
				"       COUNTY AS county, " +
				"       CREATED_BY AS createdBy, " +
				"       IS_ACTIVE AS isActive, " +
				"       REV_ID AS revId, " +
				"       STATE AS state, " +
				"       ZIPCODE AS zipcode, " +
				"       DATE_CREATED AS date_created, " +
				"       VEN_ID AS venId, " +
				"       PRM_ID AS prmId " +
				"       FROM ADDRESSES " +
				"       WHERE ID = ? " ;

		try{
		Address addRecord = (Address) jt.queryForObject(sql, new Object[] { id },
		        new BeanPropertyRowMapper<Address>(Address.class));
				
				return addRecord;
			}catch (Exception e){
				System.out.println("AddressDao.select()" + e.getMessage());
			}
		return null;
	}
	
	@Override
	public Long insert(Address address, User user) {

String sql = "INSERT INTO ADDRESSES (ID, " +
		"                       ADDR_LINE1, " +
		"                       DATE_CREATED, " +
		"                       CREATED_BY, " +
		"                       ADDR_TYPE_CODE, " +
		"                       PRM_ID, " +
		"                       REV_ID, " +
		"                       BLDG_ID, " +
		"                       VEN_ID, " +
		"                       ZIPCODE) " +
		"     VALUES (?, " +
		"             ?, " +
		"             SYSDATE, " +
		"             ?, " +
		"             ?, " +
		"             ?, " +
		"             ?, " +
		"             ?, " +
		"             ?, " +
		"             ?) " ;
try{
	
	Long pk  = getNextId("ADDRESS_SEQ");
	
	 int rowAdd = jt.update(sql, new Object[] { pk,address.getAddrLine1(),user.getCreatedBy(),address.getAddrTypeCode(),
			 address.getPrmId(),address.getRevId(),address.getBldgId(),address.getVenId(),address.getZipcode()});
	 
	 //address.getBldgId(),address.getCity(),address.getState(),address.getCounty(),address.getIsActive(),address.getRevId()
	 
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
	public Long update(Address address, User user) {
		
    String updateSql = "UPDATE ADDRESSES " +
    		"   SET ADDR_LINE1 = ?, MODIFIED_BY = ?, DATE_MODIFIED = SYSDATE " +
    		" WHERE ID = ? " ;
    try{
    	
    	long rows = (long) jt.update(updateSql, new Object[] {address.getAddrLine1(), 
    			user.getModifiedBy(),address.getId() });
    	
    	 if(rows == 1){
    		  System.out.println("1 row updated");
    			 return (long) rows;
    	  }
    }catch(Exception e){
    	System.err.println("error update() " + e.toString());
    }
		return null;
	}

	@Override
	public boolean delete(Long id) {
		
		 String sql = "DELETE FROM ADDRESSES WHERE id=?";		
			try {
				
				int rows = jt.update(sql, new Object[] { id });
				
				return (rows == 1);
	      
			}
			catch (Exception e) 
			{
				System.out.println("AddressDao.delete()" + e.getMessage());
			}
		return false;
	}

	
	
	public ArrayList<Address> searchByAddrLine1 (String addrLine1){
		
		
		String sql = "  SELECT ID AS id, " +
				"       ADDR_LINE1 AS addrLine1, " +
				"       ADDR_TYPE_CODE AS addrTypeCode, " +
				"       BLDG_ID AS bldgId, " +
				"       CITY AS city, " +
				"       COUNTY AS county, " +
				"       CREATED_BY AS createdBy, " +
				"       IS_ACTIVE AS isActive, " +
				"       REV_ID AS revId, " +
				"       STATE AS state, " +
				"       ZIPCODE AS zipcode, " +
				"       DATE_CREATED AS date_created, " +
				"       VEN_ID AS venId, " +
				"       PRM_ID AS prmId " +
				"       FROM ADDRESSES " +
				"       WHERE UPPER (ADDR_LINE1) LIKE UPPER (?) " ;
		
		try{
	
		List<Address> addresses = jt.query(sql, new Object[] { '%' + addrLine1 + '%' },
				new BeanPropertyRowMapper<Address>(Address.class));

		
		if (addresses.size() > 0) {
			return (ArrayList<Address>) addresses;
		} else
		{
			System.out.println("Getting no results");
			return null;
		}	
		
	}catch(Exception e){
		System.err.println("error searchAddressByAddrLine1() " + e.toString());
	}
	
	
	return null;
	
}

	@Override
	public List<Address> selectAll() {
		
		return null;
	}

	//@Override
	//public Address select(Long id, String city) {
		
	//	return null;
	//}

	
	
	
}
