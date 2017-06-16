package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.ContractedServicesDao;
import gov.nysed.oce.ldgrants.budget.domain.ContractedServices;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ContractedServicesService {

	
	private ContractedServicesDao contractedServicesDao = new ContractedServicesDao();
	
	
	public Long getNextContractedServicesId(){
		return contractedServicesDao.getNextContractedServicesId();
	}
	
	public long insertContractedServices(User user, Grant grant, ContractedServices contractedServices){
		
		contractedServices.setGrantId(grant.getId());
		return contractedServicesDao.insertContractedServices( contractedServices, user);
	}

	public long updateContractedServices(User user, ContractedServices contractedServices) {
		return contractedServicesDao.updateContractedServices(contractedServices, user);
	}
	
	public long updateContractedServicesWithAwardFields(User user, ContractedServices contractedServices) {
		return contractedServicesDao.updateContractedServicesWithAwardFields(contractedServices, user);
	}
	
	
	public boolean deleteContractedServices(ContractedServices contractedServices){
		return contractedServicesDao.deleteContractedServices(contractedServices.getId());
	}
	
	
	public List<ContractedServices> searchContractedServicesByGrantIdFy(Grant grant){
		return contractedServicesDao.searchContractedServicesByGrantIdFy(grant.getId(), grant.getFyCode());
	}
	
	public ContractedServices selectContractedServices(ContractedServices contractedServices){
		return contractedServicesDao.selectContractedServices(contractedServices.getId());
	}
	
		
	
}
