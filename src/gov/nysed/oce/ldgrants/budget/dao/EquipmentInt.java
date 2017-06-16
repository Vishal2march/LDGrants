package gov.nysed.oce.ldgrants.budget.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.domain.Equipment;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;
import gov.nysed.oce.ldgrants.user.domain.User;

public interface EquipmentInt extends DatabaseConnectionInt {

	public long insertEquipment(Equipment equipment, User user);

	public boolean deleteEquipment(Long equipmentId);

	public long updateEquipment(Equipment equipment, User user);
	
	public long updateEquipmentWithAwardFields(Equipment equipment, User user);
	
	public List<Equipment> searchEquipmentByGrantIdFy(Long grantId, Long fyCode);
	
	public Equipment selectEquipment(Long id);
	
	public Long getNextEquipmentId();


}
