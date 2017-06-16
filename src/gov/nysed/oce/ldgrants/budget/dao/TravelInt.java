package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.domain.Travel;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface TravelInt extends DatabaseConnectionInt {
	
	
	public long insertTravel(Travel travel, User user);

	public boolean deleteTravel(Long travelId);

	public long updateTravel(Travel travel, User user);
	
	public long updateTravelWithAwardFields(Travel travel, User user);
	
	public List<Travel> searchTravelByGrantIdFy(Long grantId, Long fyCode);
	
	public Travel selectTravel(Long id);
	
	public Long getNextTravelId();
	

}
