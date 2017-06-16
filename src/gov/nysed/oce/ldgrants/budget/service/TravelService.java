package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.TravelDao;
import gov.nysed.oce.ldgrants.budget.domain.Travel;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.user.domain.User;

public class TravelService {

	
private TravelDao travelDao = new TravelDao();
	
	
	public Long getNextTravelId(){
		return travelDao.getNextTravelId();
	}
	
	
	public long updateTravel(User user, Travel travel) {
		return travelDao.updateTravel(travel, user);
	}
	
	
	public long updateTravelWithAwardFields(User user, Travel travel) {
		return travelDao.updateTravelWithAwardFields(travel, user);
	}
	
	
	public boolean deleteTravel(Travel travel){
		return travelDao.deleteTravel(travel.getId());
	}
	
	
	public List<Travel> searchTravelByGrantIdFy(Grant grant){
		return travelDao.searchTravelByGrantIdFy(grant.getId(), grant.getFyCode());
	}
	
	public Travel selectTravel(Travel travel){
		return travelDao.selectTravel(travel.getId());
	}
	
	
		
	public void insertTravel(User user, Grant grant, Travel travel){
		
		travel.setGrantId(grant.getId());
		travelDao.insertTravel(travel, user);
	}
	

	
}
