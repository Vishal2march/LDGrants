package gov.nysed.oce.ldgrants.budget.service;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.dao.Fs10RecordDao;
import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.grants.common.service.GenericDaoService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Fs10RecordService implements GenericDaoService<Fs10Record, Long> {

	Fs10RecordDao dao = new Fs10RecordDao();

	@Override
	public Long insert(Fs10Record t, User user) {
		
		return dao.insert(t, user);
	}
	
	@Override
	public Long update(Fs10Record t, User user) {
		
		return dao.update(t, user);
	}
	
	@Override
	public boolean delete(Long id) {
		
		return dao.delete(id);
	}
	
	@Override
	public Fs10Record select(Long id) {
		
		return dao.select(id);
	}
	
	@Override
	public List<Fs10Record> selectAll() {
		
		return dao.selectAll();
	}
	
	public void updateFS10ALocked(Long grantId, User user){
		dao.updateFS10ALocked(grantId, user);
	}
	
	public void updateFS10AUnlocked(Long grantId, User user){
		dao.updateFS10AUnlocked(grantId, user);
	}
	
	
}
