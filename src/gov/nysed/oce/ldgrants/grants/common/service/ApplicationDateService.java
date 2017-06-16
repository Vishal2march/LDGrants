package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.Date;

import gov.nysed.oce.ldgrants.grants.common.dao.ApplicationDateDao;
import gov.nysed.oce.ldgrants.grants.common.domain.ApplicationDate;

public class ApplicationDateService {

	private ApplicationDateDao dateDao = new ApplicationDateDao();
	
	
	
	public ApplicationDate getApplicationDate(Long fyCode, Long fcCode){
		
		return dateDao.searchApplicationDateByFyFund(fyCode, fcCode);		
	}
	
	
	
	public ApplicationDate searchByTodayDate(Long fcCode){
		// Get Today's date
		Date date = new Date();
				
		// check if current date is between any available/due date periods
		ApplicationDate appDate = dateDao.searchApplicationDateByTodayDate(date, fcCode);	
		
		
		if(appDate==null){
			
			//if appDate is null - NO available/due date period found - Cannot create/submit now
			appDate = new ApplicationDate();
			appDate.setInitialDatesAcceptible(false);
		}
		else{
			
			//if a record was returned; then today's date is within an available/due date period
			appDate.setInitialDatesAcceptible(true);
		}
		return 	appDate;
	}
	
	
	
	
	
	public ApplicationDate getAvailableDueDateStatus(Long fyCode, Long fcCode){
		
		ApplicationDate appDate = new ApplicationDate();
		
		try{
			//query database for available/due dates
			appDate = dateDao.searchApplicationDateByFyFund(fyCode, fcCode);
			
			
			//check if initial app dates ok (current date between available/due dates)  
			if(appDate.getAvailableDate()!=null && appDate.getDueDate()!=null){
		          if(appDate.getCurrentDate().compareTo(appDate.getAvailableDate()) >=0) {//currentDate is =/>  availDate
		            
		            if(appDate.getCurrentDate().compareTo(appDate.getDueDate()) <=0)//currentDate is =/< due date
		            	appDate.setInitialDatesAcceptible(true);
		          }
		    }
		    
		    		    
		    
		    //check if final app date ok (current date before final due date)
		    if(appDate.getFinalDueDate()!=null){
		          //currentDate is =/< final due date   -used to lockout lgrmif after finalduedate		    	
		          if(appDate.getCurrentDate().compareTo(appDate.getFinalDueDate()) <=0)
		        	  appDate.setFinalDatesAcceptible(true);
		    }
		    
		    		    		    
		    
		    //check if amendment due date ok
		    if(appDate.getAmendmentDueDate()!=null){
	          //currentDate is =/< amend due date     -used to lockout lgrmif amendments
	          if(appDate.getCurrentDate().compareTo(appDate.getAmendmentDueDate()) <=0)
	        	  appDate.setAmendmentDateAcceptible(true);
	        }
	        
		      		  						
		}catch(Exception e){
			System.out.println("ApplicationDateService.getAvailableDueDateStatus " + e.getMessage());
			return null;
		}
		return appDate;
	}
}
