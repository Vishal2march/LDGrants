package gov.nysed.oce.ldgrants.grants.address.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.grants.address.domain.StaffType;
import gov.nysed.oce.ldgrants.user.domain.User;

public class GrantStaffDao extends GenericDao<GrantStaff, Long>{

	public GrantStaffDao(){}
	
	@Override
	public GrantStaff select(Long id) {
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       GRA_ID AS graId, " +
				"       ST1_ID AS st1Id " +
				"       FROM GRANT_STAFFS " +
				"       WHERE ID = ? " ;

		try{
			GrantStaff staff = (GrantStaff) jt.queryForObject(sql, new Object[] { id },
			        new BeanPropertyRowMapper<GrantStaff>(GrantStaff.class));
					
					return staff;
					
				}catch (Exception e){
					System.out.println("GrantStaffDao.select()" + e.getMessage());
				}
		
		return null;
	}
	
	
	@Override
	public Long insert(GrantStaff staff, User user) {
		
		String sql = "INSERT INTO GRANT_STAFFS (ID, " +
				"                          FNAME, " +
				"                          LNAME, " +
				"                          TITLE, " +
				"                          DATE_CREATED, " +
				"                          CREATED_BY, " +
				"                          GRA_ID, " +
				"                          ST1_ID) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             ?, " +
				"             ?, " +
				"             ?) "; 	
		try{	
			
			Long pk  = getNextId("GS_SEQ");
			
			 int rowAdd = jt.update(sql, new Object[] { pk,staff.getFname(),
			staff.getLname(),staff.getTitle(),user.getCreatedBy(),staff.getGraId(),staff.getSt1Id()});
			 
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
	public Long update(GrantStaff staff, User user) {
		
		String updateSql =  " UPDATE GRANT_STAFFS " +
				"       SET FNAME = ?, " +
				"       MNAME = ?, " +
				"       LNAME = ?, " +
				"       TITLE = ?, " +
				"       ACTIVE = ?,       " +
				"       DATE_MODIFIED = SYSDATE, " +
				"       MODIFIED_BY = ? " +
				"       WHERE ID = ? " ;
		 try{
		    	
		long rows = (long) jt.update(updateSql, new Object[] {staff.getFname(),staff.getMname(),
		   staff.getLname(),staff.getTitle(),staff.getActive(),user.getModifiedBy(),staff.getId()});		    			    	
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
		 String sql = "DELETE FROM GRANT_STAFFS WHERE id=?";		
			try {
				
				int rows = jt.update(sql, new Object[] { id });
				
				return (rows == 1);
	      
			}
			catch (Exception e) 
			{
				System.out.println("GrantStaffDao.delete()" + e.getMessage());
			}
		return false;
	}

public ArrayList<GrantStaff> searchByLname (String lname){
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       GRA_ID AS graId, " +
				"       ST1_ID AS st1Id " +
				"       FROM GRANT_STAFFS " +
				"       WHERE UPPER (LNAME) LIKE UPPER (?) " ;
		
		
try{
			
			List<GrantStaff> staffs = jt.query(sql, new Object[] { '%' + lname + '%' },
					new BeanPropertyRowMapper<GrantStaff>(GrantStaff.class));

			
			if (staffs.size() > 0) {
				return (ArrayList<GrantStaff>) staffs;
			} else
			{
				System.out.println("Getting no results");
				return null;
			}	
			
		}catch(Exception e){
			System.err.println("error searchByLname() " + e.toString());
		}
		
		
		return null;
		
	}
	
	public ArrayList<GrantStaff> searchByTitle (String title){
	
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       GRA_ID AS graId, " +
				"       ST1_ID AS st1Id " +
				"       FROM GRANT_STAFFS " +
				"       WHERE UPPER (TITLE) LIKE UPPER (?) "; 

try{
			
			List<GrantStaff> staffs = jt.query(sql, new Object[] { '%' + title + '%' },
					new BeanPropertyRowMapper<GrantStaff>(GrantStaff.class));

			
			if (staffs.size() > 0) {
				return (ArrayList<GrantStaff>) staffs;
			} else
			{
				System.out.println("Getting no results");
				return null;
			}	
			
		}catch(Exception e){
			System.err.println("error searchByTitle() " + e.toString());
		}
		return null;
		
	}

	@Override
	public List<GrantStaff> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	public GrantStaff searchByGrantIdStaffTypeId(Long grantId, long staffTypeId) {
		System.out.println("st_id "+staffTypeId);
		System.out.println("gra_id "+grantId);
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       GRA_ID AS graId, " +
				"       ST1_ID AS st1Id " +
				"       FROM GRANT_STAFFS " +
				"       WHERE GRA_ID = ?  and "+
				"       ST1_ID = ?  and "+
				"       ACTIVE='Y' ";

		try{
			GrantStaff staff = (GrantStaff) jt.queryForObject(sql, new Object[] {grantId, staffTypeId },
			        new BeanPropertyRowMapper<GrantStaff>(GrantStaff.class));
			
					//if staffTypeId exists; get StaffType enum
					if(staff.getSt1Id() != null  &&  staff.getSt1Id()>0){
						
						//search for StaffType enum
						StaffType st = StaffType.searchByStaffTypeId(staff.getSt1Id().intValue());
						//set enum to staff object
						staff.setStaffType(st);
					}
					
					return staff;
					
				}catch (Exception e){
					System.out.println("GrantStaffDao.searchByGrantIdStaffTypeId()" + e.getMessage());
				}
		
		return null;
	}

}
