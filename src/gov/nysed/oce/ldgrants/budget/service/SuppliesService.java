package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.SuppliesDao;
import gov.nysed.oce.ldgrants.budget.domain.Supplies;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.user.domain.User;

public class SuppliesService {

	
private SuppliesDao suppliesDao = new SuppliesDao();
	
	
	public Long getNextSuppliesId(){
		return suppliesDao.getNextSuppliesId();
	}
	
	
	public long updateSupplies(User user, Supplies supplies) {
		return suppliesDao.updateSupplies(supplies, user);
	}
	
	public long updateSuppliesWithAwardFields(User user, Supplies supplies) {
		return suppliesDao.updateSuppliesWithAwardFields(supplies, user);
	}
	
	
	public boolean deleteSupplies(Supplies supplies){
		return suppliesDao.deleteSupplies(supplies.getId());
	}
	
	
	public List<Supplies> searchSuppliesByGrantIdFy(Grant grant){
		return suppliesDao.searchSuppliesByGrantIdFy(grant.getId(), grant.getFyCode());
	}
	
	public Supplies selectSupplies(Supplies supplies){
		return suppliesDao.selectSupplies(supplies.getId());
	}
	
	
		
	public void insertSupplies(User user, Grant grant, Supplies supplies){
		
		supplies.setGrantId(grant.getId());
		suppliesDao.insertSupplies(supplies, user);
	}
	
	
	
	
	
}
