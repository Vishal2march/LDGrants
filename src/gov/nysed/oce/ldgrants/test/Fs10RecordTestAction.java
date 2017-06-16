package gov.nysed.oce.ldgrants.test;

import java.util.List;

import gov.nysed.oce.ldgrants.budget.domain.Fs10Record;
import gov.nysed.oce.ldgrants.budget.service.Fs10RecordService;
import gov.nysed.oce.ldgrants.user.domain.User;

public class Fs10RecordTestAction extends TestAction {
	
	public Fs10RecordTestAction(){
		user = new User();
		user.setCreatedBy("test");
		user.setModifiedBy("test");
	}
	
	

	Fs10RecordService service = new Fs10RecordService();
	User user = new User();
	Fs10Record fs = new Fs10Record();
	public Long pk;
	
	
	@Override
	protected boolean insert() {
		
		fs = new Fs10Record();
		fs.setGraId(2750L);
		fs.setFyCode(17L);
		fs.setDescription("test");
		fs.setAmountDecr(250L);
		fs.setExpCode(3L);
		
		pk = service.insert(fs, user);
		System.out.println("PK="+pk);
		
		if(pk>0)
			return true;	
		
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

	@Override
	protected boolean select() {
		
		fs = service.select(pk);
		
		if(fs.getDescription().equalsIgnoreCase("test"))
			return true;		
		
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	@Override
	protected boolean update() {
		
		fs.setDescription("New Test");
		fs.setAmountIncr(500L);
		fs.setAmountDecr(475L);
		fs.setExpCode(2L);
		fs.setId(pk);
		
		
		long rows = service.update(fs, user);
		
		if(rows >0)
			return true;
		
		
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

	@Override
	protected boolean search() {
		//Note: testing selectAll() here.  No search() for Fs10Records
		
		List<Fs10Record> list = service.selectAll();
		
		if(list.size()>0)
			return true;
		
		
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

	@Override
	protected boolean delete() {
		
		boolean rows = service.delete(147L);
		
		if(rows == true)
			return true;
		
		
		// TODO Auto-generated method stub
		return false;
	}

}
