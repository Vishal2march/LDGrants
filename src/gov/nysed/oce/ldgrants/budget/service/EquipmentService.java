package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.EquipmentDao;
import gov.nysed.oce.ldgrants.budget.domain.Equipment;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.user.domain.User;

public class EquipmentService {

	
private EquipmentDao equipmentDao = new EquipmentDao();
	
	
	public Long getNextEquipmentId(){
		return equipmentDao.getNextEquipmentId();
	}
	
	public long insertEquipment(User user, Grant grant, Equipment equipment){
		
		equipment.setGrantId(grant.getId());
		return equipmentDao.insertEquipment(equipment, user);
	}

	public long updateEquipment(User user, Equipment equipment) {
		return equipmentDao.updateEquipment(equipment, user);
	}
	
	
	public long updateEquipmentWithAwardFields(User user, Equipment equipment) {
		return equipmentDao.updateEquipmentWithAwardFields(equipment, user);
	}
	
	
	public boolean deleteEquipment(Equipment equipment){
		return equipmentDao.deleteEquipment(equipment.getId());
	}
	
	
	public List<Equipment> searchEquipmentByGrantIdFy(Grant grant){
		return equipmentDao.searchEquipmentByGrantIdFy(grant.getId(), grant.getFyCode());
	}
	
	public Equipment selectEquipment(Equipment equipment){
		return equipmentDao.selectEquipment(equipment.getId());
	}
	
	
	
	
}
