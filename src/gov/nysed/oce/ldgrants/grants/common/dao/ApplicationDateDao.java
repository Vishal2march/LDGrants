package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.ApplicationDate;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;

public class ApplicationDateDao implements ApplicationDateInt{
	
	
	public ApplicationDate searchApplicationDateByFyFund(Long fyCode, Long fcCode) {

		try {

			ApplicationDate ApplicationDate = jt.queryForObject(
					"/* Formatted on 9/13/2016 1:55:03 PM (QP5 v5.185.11230.41888) */ "+
					"SELECT id, "+
					"       start_date AS availableDate, "+
					"       due_date, "+
					"       final_rpt_date AS finalDueDate, "+
					"       interim_rpt_date AS finalAvailableDate, "+
					"       interim_rpt_date AS amendmentDueDate, "+
					"       SYSDATE as currentDate, "+
					"       fy_code, "+
					"       fc_code "+
					"  FROM APP_DATES "+
					" WHERE FY_CODE = ? AND FC_CODE = ? ",
					new Object[] { fyCode, fcCode }, new BeanPropertyRowMapper<ApplicationDate>(ApplicationDate.class));

			return ApplicationDate;

		} catch (Exception e) {
			System.out.println("ApplicationDateDao.searchApplicationDateByFyFund " + e.getMessage());
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ApplicationDate searchApplicationDateByTodayDate(Date currentDate, Long fcCode){
		
		String sql =" SELECT id, "+
					"       start_date AS availableDate, "+
					"       due_date, "+
					"       final_rpt_date AS finalDueDate, "+
					"       interim_rpt_date AS finalAvailableDate, "+
					"       interim_rpt_date AS amendmentDueDate, "+
					"       SYSDATE as currentDate, "+
					"       fy_code, "+
					"       fc_code "+
					"  FROM APP_DATES  " +
				" WHERE (START_DATE <= ? AND DUE_DATE >= ?) "+
				" AND FC_CODE = ?";


		try {
			
			ApplicationDate applicationDate = jt.queryForObject(sql, new Object[] {currentDate, currentDate, fcCode },
					new BeanPropertyRowMapper<ApplicationDate>(ApplicationDate.class));		
								
			return applicationDate;  
			
		} catch (Exception e) {
			System.out.println("ApplicationDateDao.searchApplicationDateByTodayDate " + e.getMessage());
			return null;
		}
		
	}
	
	

}
