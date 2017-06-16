package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.domain.Supplies;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface SuppliesInt extends DatabaseConnectionInt {

	public long insertSupplies(Supplies supplies, User user);

	public boolean deleteSupplies(Long suppliesId);

	public long updateSupplies(Supplies supplies, User user);
	
	public long updateSuppliesWithAwardFields(Supplies supplies, User user);
	
	public List<Supplies> searchSuppliesByGrantIdFy(Long grantId, Long fyCode);
	
	public Supplies selectSupplies(Long id);
	
	public Long getNextSuppliesId();

}
