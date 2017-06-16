package gov.nysed.oce.ldgrants.grants.address.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ProjectManagerDao extends GenericDao<ProjectManager, Long> {
	
	public ProjectManagerDao(){}
	
	@Override
	public ProjectManager select(Long id) {
		
	
		String sql = "SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       START_DATE AS startDate, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy " +
				"       FROM PROJECT_MANAGERS " +
				"       WHERE ID = ? " ;

		try{
			ProjectManager manager = (ProjectManager) jt.queryForObject(sql, new Object[] { id },
			        new BeanPropertyRowMapper<ProjectManager>(ProjectManager.class));
					
					return manager;
				}catch (Exception e){
					System.out.println("ProjectManagerDao.select()" + e.getMessage());
				}
		
		return null;
	}

	
	
	@Override
	public Long insert(ProjectManager manager, User user) {
		
		String sql = "INSERT INTO PROJECT_MANAGERS (ID, " +
				"                              SALUTATION, " +
				"                              FNAME, " +
				"                              MNAME, " +
				"                              LNAME, " +
				"                              TITLE, " +
				"                              START_DATE, " +
				"                              ACTIVE, " +
				"                              DATE_CREATED, " +
				"                              CREATED_BY) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             ?, " +
				"             SYSDATE, " +
				"             ?) " ;
		try{
			
			Long pk  = getNextId("PM_REV_SEQ");
			
	int rowAdd = jt.update(sql, new Object[] { pk,manager.getSalutation(),manager.getFname(),manager.getMname(),
	manager.getLname(),manager.getTitle(),manager.getActive(),user.getCreatedBy()});
			 
			 
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
	public Long update(ProjectManager manager, User user) {

		
		int rows = 0;
		String updateSql = "UPDATE PROJECT_MANAGERS " +
				"       SET SALUTATION = ?, " +
				"       FNAME = ?, " +
				"       MNAME = ?, " +
				"       LNAME = ?, " +
				"       TITLE = ?, " +
				"       ACTIVE = ?, " +
				"       DATE_MODIFIED = SYSDATE, " +
				"       MODIFIED_BY = ? " +
				"       WHERE ID = ? " ;
			

		try{
	    	
	    	rows = jt.update(updateSql, new Object[] { manager.getSalutation(),manager.getFname(),manager.getMname(),
	    			manager.getLname(), manager.getTitle(),manager.getActive(),user.getModifiedBy(),manager.getId()});

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
		
		 String sql = "DELETE FROM PROJECT_MANAGERS WHERE id=?";		
			try {
				
				int rows = jt.update(sql, new Object[] { id });
				
				return (rows == 1);
	      
			}
			catch (Exception e) 
			{
				System.out.println("ReviewerDao.delete()" + e.getMessage());
			}
		return false;
	}

public ArrayList<ProjectManager> searchByLname (String lname){
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       START_DATE AS startDate, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy " +
				"       FROM PROJECT_MANAGERS " +
				"       WHERE UPPER (LNAME) LIKE UPPER (?) " ;
		
try{		
			List<ProjectManager> managers = jt.query(sql, new Object[] { '%' + lname + '%' },
					new BeanPropertyRowMapper<ProjectManager>(ProjectManager.class));
			
			if (managers.size() > 0) {
				return (ArrayList<ProjectManager>) managers;
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
	
	public ArrayList<ProjectManager> searchByTitle (String title){
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       MNAME AS mname, " +
				"       LNAME AS lname, " +
				"       TITLE AS title, " +
				"       START_DATE AS startDate, " +
				"       ACTIVE AS active, " +
				"       DATE_CREATED AS dateCreated, " +
				"       CREATED_BY AS createdBy " +
				"       FROM PROJECT_MANAGERS " +
				"       WHERE UPPER (TITLE) LIKE UPPER (?) "; 
		
try{		
			List<ProjectManager> managers = jt.query(sql, new Object[] { '%' + title + '%' },
					new BeanPropertyRowMapper<ProjectManager>(ProjectManager.class));
	
			if (managers.size() > 0) {
				return (ArrayList<ProjectManager>) managers;
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
	public List<ProjectManager> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
