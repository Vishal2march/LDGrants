package gov.nysed.oce.ldgrants.grants.address.service;

import java.util.ArrayList;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.address.dao.ProjectManagerDao;
import gov.nysed.oce.ldgrants.grants.address.domain.ProjectManager;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ProjectManagerService implements GenericDaoService<ProjectManager, Long>{

	
	ProjectManagerDao dao = new ProjectManagerDao();
	
	public ArrayList<ProjectManager> searchByLname(String lname){
		
		return dao.searchByLname(lname);
		
	}
	
	public ArrayList<ProjectManager> searchByTitle(String title){
		
		return dao.searchByTitle(title);
		
	}
	@Override
	public Long insert(ProjectManager t, User user) {
		
		return dao.insert(t, user);
	}

	@Override
	public Long update(ProjectManager t, User user) {
		
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
	
		return dao.delete(id);
	}

	@Override
	public ProjectManager select(Long id) {
		
		return dao.select(id);
	}

	@Override
	public List<ProjectManager> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
