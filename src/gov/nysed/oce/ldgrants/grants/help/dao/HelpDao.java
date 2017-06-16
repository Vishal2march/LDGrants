package gov.nysed.oce.ldgrants.grants.help.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.address.domain.Address;
import gov.nysed.oce.ldgrants.grants.address.domain.GrantStaff;
import gov.nysed.oce.ldgrants.grants.help.domain.Help;
import gov.nysed.oce.ldgrants.grants.help.domain.LocationDescription;
import gov.nysed.oce.ldgrants.user.domain.User;

public class HelpDao extends GenericDao<Help,Long> {

	@Override
	public Long insert(Help help, User user) {
		
		//@formatter:off
		String sql = "INSERT INTO HELPS (ID, " +
				"                   FC_CODE, " +
				"                   FY_CODE, " +
				"                   DOC_TYPE, " +
				"                   NAME, " +
				"                   CREATED_BY, " +
				"                   DATE_CREATED, " +
				"                   DATE_MODIFIED, " +
				"                   START_DATE, " +
				"                   END_DATE, " +
				"                   BLOB_CONTENT, " +
				"                   DESCRIPTION, " +
				"                   DISPLAY_CONTENT, " +
				"                   DISPLAY_LINKS, " +
				"                   LOCATION_ID) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             SYSDATE, " +
				"             SYSDATE, " +
				"             SYSDATE, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?) "; 

		
		try{
			
			Long pk  = getNextId("");
			
			 int rowAdd = jt.update(sql, new Object[] { pk,help.getFcCode(),help.getDocType(),
					 help.getName(),user.getCreatedBy(),help.getBlobContent(),help.getDescription(),
					 help.getDisplayContent(),help.getDisplayLinks(),help.getLocationId()});
			 
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
	public Long update(Help help, User user) {
		
		//@formatter:off
		String sql = "  UPDATE HELPS " +
				"       SET FC_CODE = ?, FY_CODE = ?, " +
				"       DOC_TYPE = ?, " +
				"       NAME = ?, " +
				"       DATE_MODIFIED = SYSDATE, " +
				"       MODIFIED_BY = ?, " +
				"       START_DATE = SYSDATE, " +
				"       END_DATE = SYSDATE, " +
				"       BLOB_CONTENT = ?, " +
				"       DESCRIPTION = ?, " +
				"       DISPLAY_CONTENT = ? " +
				"       WHERE ID = ? "; 
		
		try{
	    	
	    	long rows = (long) jt.update(sql, new Object[] {help.getFcCode(), help.getFyCode(), help.getDocType(),
	    			help.getName(),user.getModifiedBy(),help.getBlobContent(),help.getDescription(),
	    			help.getDisplayContent(),help.getId()
	    			});
	    	
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
		
		String sql = "DELETE FROM HELPS WHERE id=?";		
		try {
			
			int rows = jt.update(sql, new Object[] { id });
			
			return (rows == 1);
      
		}
		catch (Exception e) 
		{
			System.out.println("HelpDao.delete()" + e.getMessage());
		}
		return false;
	}

	@Override
	public Help select(Long id) {
		
		//@formatter:off
		String sql = "  SELECT ID AS id, " +
				"       FC_CODE AS fcCode, FY_CODE AS fyCode, " +
				"       DOC_TYPE AS docType, " +
				"       NAME AS name, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS date_created, " +
				"       DATE_MODIFIED AS dateModified, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       START_DATE AS startDate, " +
				"       END_DATE AS endDate, " +
				"       BLOB_CONTENT AS blobContent, " +
				"       DESCRIPTION AS description, " +
				"       DISPLAY_CONTENT AS displayContent, " +
				"       DISPLAY_LINKS AS displayLinks, LOCATION_ID AS locationId " +
				"       FROM HELPS " +
				"       WHERE ID = ? "; 

		try{
			Help addRecord = (Help) jt.queryForObject(sql, new Object[] { id },
			        new BeanPropertyRowMapper<Help>(Help.class));
					
					return addRecord;
				}catch (Exception e){
					System.out.println("HelpDao.select()" + e.getMessage());
				}
		return null;
	}

	@Override
	public List<Help> selectAll() {
		
		//@formatter:off
		String sql = "  SELECT ID AS id, " +
				"       FC_CODE AS fcCode, " +
				"       FY_CODE AS fyCode, " +
				"       DOC_TYPE AS docType, " +
				"       NAME AS name, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS date_created, " +
				"       DATE_MODIFIED AS dateModified, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       START_DATE AS startDate, " +
				"       END_DATE AS endDate, " +
				"       BLOB_CONTENT AS blobContent, " +
				"       DESCRIPTION AS description, " +
				"       DISPLAY_CONTENT AS displayContent, " +
				"       DISPLAY_LINKS AS displayLinks, " +
				"       LOCATION_ID AS locationId " +
				"       FROM HELPS " ;
		
		try {

			ArrayList<Help> records = (ArrayList<Help>) jt.query(sql,
					new BeanPropertyRowMapper<Help>(Help.class));

			return records;
			
		} catch (Exception e) {
			System.err.println("selectAll()" + e.getMessage());
		}

		return null;
	}
	
	public ArrayList<Help> searchByFcCode (Long fcCode){
		
		//@formatter:off
		String sql = "  SELECT ID AS id, " +
				"       FC_CODE AS fcCode, FY_CODE AS fyCode, " +
				"       DOC_TYPE AS docType, " +
				"       NAME AS name, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS date_created, " +
				"       DATE_MODIFIED AS dateModified, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       START_DATE AS startDate, " +
				"       END_DATE AS endDate, " +
				"       BLOB_CONTENT AS blobContent, " +
				"       DESCRIPTION AS description, " +
				"       DISPLAY_CONTENT AS displayContent, " +
				"       DISPLAY_LINKS AS displayLinks, LOCATION_ID AS locationId " +
				"       FROM HELPS " +
				"       WHERE UPPER (FC_CODE) LIKE  (?) ";
		
		try{
			
			List<Help> list = jt.query(sql, new Object[] { '%' + fcCode + '%' },
					new BeanPropertyRowMapper<Help>(Help.class));

			
			if (list.size() > 0) {
				return (ArrayList<Help>) list;
			} else
			{
				System.out.println("Getting no results");
				return null;
			}	
			
		}catch(Exception e){
			System.err.println("error searchByFcCode() " + e.toString());
		}
		return null;

}
	public Help searchByFcCodeFyCode (Long fcCode, Long fyCode ){
		
		System.out.println("fcCode" + fcCode);
		System.out.println("fyCode" + fyCode);
		
		//@formatter:off
		String sql = "  SELECT ID AS id, " +
				"       FC_CODE AS fc_code, FY_CODE AS fyCode, " +
				"       DOC_TYPE AS docType, " +
				"       NAME AS name, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS date_created, " +
				"       DATE_MODIFIED AS dateModified, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       START_DATE AS startDate, " +
				"       END_DATE AS endDate, " +
				"       BLOB_CONTENT AS blobContent, " +
				"       DESCRIPTION AS description, " +
				"       DISPLAY_CONTENT AS displayContent, " +
				"       DISPLAY_LINKS AS displayLinks, LOCATION_ID AS locationId " +
				"       FROM HELPS " +
				"       WHERE FC_CODE = ? AND FY_CODE = ? "; 
		
		
		try{
			Help help = (Help) jt.queryForObject(sql, new Object[] {fcCode, fyCode },
			        new BeanPropertyRowMapper<Help>(Help.class));
			
			if(help.getFcCode() !=null && help.getFyCode() >0){
				return help;
				
			}else{
				System.out.println("Getting no results");
				return null;
			}
		}catch (Exception e){
			System.out.println("HelpDao.searchByFcCodeFyCodet()" + e.getMessage());
		}
		return null;
		
	}
	
	public Help searchByLocationLocationId (String Description, Long locationId){
		
		//@formatter:off
		String sql = "  SELECT ID AS id, " +
				"       FC_CODE AS fc_code, " +
				"       FY_CODE AS fyCode, " +
				"       DOC_TYPE AS docType, " +
				"       NAME AS name, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS date_created, " +
				"       DATE_MODIFIED AS dateModified, " +
				"       MODIFIED_BY AS modifiedBy, " +
				"       START_DATE AS startDate, " +
				"       END_DATE AS endDate, " +
				"       BLOB_CONTENT AS blobContent, " +
				"       DESCRIPTION AS description, " +
				"       DISPLAY_CONTENT AS displayContent, " +
				"       DISPLAY_LINKS AS displayLinks, " +
				"       LOCATION_ID AS locationId " +
				"       FROM HELPS " +
				"       WHERE DESCRIPTION = ? AND LOCATION_ID = ? "; 
		
		try{
			Help help = (Help) jt.queryForObject(sql, new Object[] {Description, locationId },
			        new BeanPropertyRowMapper<Help>(Help.class));
			
			// if locationId exists, get LocationDescription enum..
			if(help.getLocationId() != null && help.getLocationId() > 0){
				
				// search for LocationDescription enum
   LocationDescription description = LocationDescription.searchByLocationId(help.getLocationId().intValue());
			 
             //set enum to help object
            help.setLocationDescription(description);
                
			}
			return help;

		}catch (Exception e){
			System.out.println("HelpDao.searchByLocationLocationId()" + e.getMessage());
		}
		return null;
		
	}

}
