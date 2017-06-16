package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.domain.ContractedServices;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface ContractedServicesInt extends DatabaseConnectionInt {

	public long insertContractedServices(ContractedServices contractedServices, User user);

	public boolean deleteContractedServices(Long contractedServicesId);

	public long updateContractedServices(ContractedServices contractedServices, User user);
	
	public long updateContractedServicesWithAwardFields(ContractedServices contractedServices, User user);
	
	public List<ContractedServices> searchContractedServicesByGrantIdFy(Long grantId, Long fyCode);
	
	public ContractedServices selectContractedServices(Long id);
	
	public Long getNextContractedServicesId();
	
	
}
