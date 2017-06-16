package gov.nysed.oce.ldgrants.grants.address.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.address.domain.Reviewer;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ReviewerDao extends GenericDao<Reviewer, Long> {

	@Override
	public Reviewer select(Long id) {
		
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       LNAME AS lname, " +
				"       MNAME AS mname, " +
				"       TITLE AS title, " +
				"       AFFILIATION AS affiliation, " +
				"       INTEREST AS interest, " +
				"       ACTIVE AS active, " +
				"       USER_ID AS userId, " +
				"       SSN AS ssn, " +
				"       RAO_FLAG AS raoFlag, " +
				"       DATE_CREATED AS dateCreated, " +
				"       GOVT_EMP_FLAG AS govtEmpFlag, " +
				"       VENDOR_NUM AS vendorNum " +
				"       FROM REVIEWERS " +
				"       WHERE ID = ? " ;


		try{
		Reviewer review = (Reviewer) jt.queryForObject(sql, new Object[] { id },
		        new BeanPropertyRowMapper<Reviewer>(Reviewer.class));
				
				return review;
			}catch (Exception e){
				System.out.println("ReviewerDao.select()" + e.getMessage());
			}
		return null;
	}
		
	@Override
	public Long insert(Reviewer reviewer, User user) {
		
	String sql =	"INSERT INTO REVIEWERS (ID, " +
			"                       SALUTATION, " +
			"                       DATE_CREATED, " +
			"                       FNAME, " +
			"                       LNAME, " +
			"                       MNAME, " +
			"                       TITLE, " +
			"                       AFFILIATION, " +
			"                       INTEREST, " +
			"                       CREATED_BY) " +
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
		
		Long pk  = getNextId("PM_REV_SEQ");
		
		 int rowAdd = jt.update(sql, new Object[] { pk,	reviewer.getSalutation(),reviewer.getFname(),reviewer.getLname(),reviewer.getMname(),reviewer.getTitle()
				 ,reviewer.getAffiliation(),reviewer.getInterest(),user.getCreatedBy()
		 });
	
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
	public Long update(Reviewer reviewer, User user) {
	
		
		String updateSql = "UPDATE REVIEWERS SET " +
				"       FNAME = ?, " +
				"       MNAME = ?, " +
				"       LNAME = ?, " +
				"       TITLE = ?, " +
				"       DATE_MODIFIED = SYSDATE, " +
				"       MODIFIED_BY = ? " +
				"       WHERE ID = ? " ;

		 try{
		    	
		    long rows = (long) jt.update(updateSql, new Object[] {reviewer.getFname(),
		        reviewer.getMname(),reviewer.getLname(), reviewer.getTitle(),
		        user.getModifiedBy(),reviewer.getId()});
		    	    	
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
		
		 String sql = "DELETE FROM REVIEWERS WHERE id=?";		
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

public ArrayList<Reviewer> searchByLname (String lname){
		
		String sql ="   SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       LNAME AS lname, " +
				"       MNAME AS mname, " +
				"       TITLE AS title, " +
				"       AFFILIATION AS affiliation, " +
				"       INTEREST AS interest, " +
				"       ACTIVE AS active, " +
				"       USER_ID AS userId, " +
				"       SSN AS ssn, " +
				"       RAO_FLAG AS raoFlag, " +
				"       DATE_CREATED AS dateCreated, " +
				"       GOVT_EMP_FLAG AS govtEmpFlag, " +
				"       VENDOR_NUM AS vendorNum " +
				"       FROM REVIEWERS " +
				"       WHERE UPPER (LNAME) LIKE UPPER (?) " ;

		try{
			
			List<Reviewer> reviewers = jt.query(sql, new Object[] { '%' + lname + '%' },
					new BeanPropertyRowMapper<Reviewer>(Reviewer.class));

			
			if (reviewers.size() > 0) {
				return (ArrayList<Reviewer>) reviewers;
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
	
public ArrayList<Reviewer> searchByTitle (String title){
		
		String sql = "  SELECT ID AS id, " +
				"       SALUTATION AS salutation, " +
				"       FNAME AS fname, " +
				"       LNAME AS lname, " +
				"       MNAME AS mname, " +
				"       TITLE AS title, " +
				"       AFFILIATION AS affiliation, " +
				"       INTEREST AS interest, " +
				"       ACTIVE AS active, " +
				"       USER_ID AS userId, " +
				"       SSN AS ssn, " +
				"       RAO_FLAG AS raoFlag, " +
				"       DATE_CREATED AS dateCreated, " +
				"       GOVT_EMP_FLAG AS govtEmpFlag, " +
				"       VENDOR_NUM AS vendorNum " +
				"       FROM REVIEWERS " +
				"       WHERE UPPER (TITLE) LIKE UPPER (?) " ;
		

		try{
			
			List<Reviewer> reviewers = jt.query(sql, new Object[] { '%' + title + '%' },
					new BeanPropertyRowMapper<Reviewer>(Reviewer.class));

			
			if (reviewers.size() > 0) {
				return (ArrayList<Reviewer>) reviewers;
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
	public List<Reviewer> selectAll() {
		
		return null;
	}

}
