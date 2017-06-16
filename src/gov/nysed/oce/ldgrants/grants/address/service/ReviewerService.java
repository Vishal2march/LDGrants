package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.dao.ReviewerDao;
import gov.nysed.oce.ldgrants.grants.address.domain.Reviewer;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ReviewerService implements GenericDaoService<Reviewer, Long> {

	ReviewerDao reviewerDao = new ReviewerDao();
		
	
	public ArrayList<Reviewer> searchByLname(String lname) {
		
		return reviewerDao.searchByLname(lname);
		
	}
	
	public ArrayList<Reviewer> searchByTitle(String title){
		
		return reviewerDao.searchByTitle(title);
		
	}
	
	@Override
	public Long insert(Reviewer t, User user) {
		
		return reviewerDao.insert(t, user);
		
	}

	@Override
	public Long update(Reviewer t, User user) {
		return reviewerDao.update(t, user);
	
	}

	@Override
	public boolean delete(Long id) {
		
		
		return reviewerDao.delete(id);
	}

	@Override
	public Reviewer select(Long id) {
		
		return reviewerDao.select(id);
	}

	@Override
	public List<Reviewer> selectAll() {
		
		return null;
	}

}
