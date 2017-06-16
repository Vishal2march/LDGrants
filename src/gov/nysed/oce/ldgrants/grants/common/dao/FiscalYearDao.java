package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.user.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class FiscalYearDao extends GenericDao<FiscalYear, Long> {

	public FiscalYearDao() {
	}

	@Override
	public Long insert(FiscalYear fiscalyear, User user) {
		//@formatter:off
		String sql ="/* Formatted on 1/3/2017 1:16:46 PM (QP5 v5.252.13127.32847) */ " +
				"INSERT INTO Fiscal_Years (CODE, " +
				"                        DESCRIPTION, " +
				"                        START_DATE,"+
				"                        END_DATE," +
				"                        DATE_CREATED, " +
				"                        CREATED_BY) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?," +
				"             ?, " +
				"             SYSDATE, " +
				"             ?) "; 

			
		try {
			//TODO: Missing sequence value
			Long pk = getNextId("");
			long rows = (long) jt.update(sql, new Object[]{pk, fiscalyear.getDescription(), 
					fiscalyear.getStartDate(), fiscalyear.getEndDate(), user.getLanId()});
			
			if (rows == 1) 
				return pk;
			
		} catch (Exception ex) {
		
			System.err.println("CLASS:FiscalYearDao METHOD:insert()" + ex.toString());
		} 
		return null;
	}
	
	
	@Override
	public Long  update(FiscalYear fiscalyear,User user) {
		//@formatter:off
		String sql = "/* Formatted on 2/2/2017 11:58:54 AM (QP5 v5.252.13127.32847) */ " +
				"UPDATE FISCAL_YEARS " +
				"   SET DESCRIPTION = ?, " +
				"       START_DATE = ?, " +
				"       END_DATE = ?, " +
				"       MODIFIED_BY = ?, " +
				"       DATE_MODIFIED = SYSDATE " +
				" WHERE CODE = ? "; 
 

		try {
			long rows = (long) jt.update(sql, new Object[]{fiscalyear.getDescription(),user.getLanId()}, fiscalyear.getCode());
			return rows;
		} catch (Exception ex) {
		System.err.println("CLASS:FiscalYearDao METHOD:update() " + ex.toString());
		} 
		return null;
	}

	
	@Override
	public boolean delete(Long code) {
		//@formatter:off
		String sql = "DELETE FROM FISCAL_YEARS WHERE CODE=?";
		try {
			int rows = jt.update(sql, new Object[] {code });
			return (rows == 1);
		} catch (Exception ex) {
			System.err.println("CLASS:FiscalYearDao METHOD:delete() " + ex.toString());
		} 
		return false;
	}

	@Override
	public FiscalYear select(Long code) {
		//@formatter:off	
		String sql = "/* Formatted on 2/2/2017 11:53:48 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT code, " +
				"       description, " +
				"       start_date AS startdate, " +
				"       end_date AS enddate " +
				"  FROM Fiscal_Years " +
				" WHERE code = ? "; 

 
		try {
			FiscalYear fiscalyear =  jt.queryForObject(sql, new Object[] { code }, 
					new BeanPropertyRowMapper<FiscalYear>(FiscalYear.class));

			return fiscalyear;
		} catch (Exception e) {
			System.out.println("CLASS:FiscalYearDao METHOD:select()" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<FiscalYear> selectAll() {
		//@formatter:off
		String sql ="/* Formatted on 2/2/2017 11:53:48 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE, " +
				"       DESCRIPTION, " +
				"       START_DATE AS startdate, " +
				"       END_DATE AS enddate " +
				"  FROM Fiscal_Years "+
				"  order by description ";
		try {

			List<FiscalYear> fiscalYearList = jt.query(sql, new BeanPropertyRowMapper<FiscalYear>(FiscalYear.class));

			if (fiscalYearList.size() > 0)
				return (ArrayList<FiscalYear>) fiscalYearList;

		} catch (Exception e) {
			System.out.println("CLASS:FiscalYearDao METHOD:selectAll()" + e.getMessage());
		}
		return null;
	}

	public ArrayList<FiscalYear> searchByDescription(String Description) {
		//@formatter:off
		String sql ="/* Formatted on 1/3/2017 1:02:56 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE AS Code, " +
				"		DESCRIPTION AS Description, " +
				"		START_DATE AS startdate, " +
				"		END_DATE AS enddate " +
				"  FROM FISCAL_YEARS " +
				" WHERE DESCRIPTION LIKE UPPER (?) "; 

		try {
			List<FiscalYear> fiscalYearList = jt.query(sql, new Object[] { Description + '%' },
					new BeanPropertyRowMapper<FiscalYear>(FiscalYear.class));		
    				
			if(fiscalYearList.size() > 0)
			return (ArrayList<FiscalYear>)fiscalYearList;     
			
		} catch (Exception e) {
			System.out.println("CLASS:FiscalYearDao METHOD:searchByDescription()" + e.getMessage());		
		}
		return null;
	}

	public FiscalYear searchByDate(Date currentDate){
		//@formatter:off
		String sql ="/* Formatted on 2/2/2017 12:43:32 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE AS Code, " +
				"       DESCRIPTION AS Description, " +
				"       START_DATE AS startdate, " +
				"       END_DATE AS enddate " +
				"  FROM FISCAL_YEARS " +
				" WHERE (START_DATE <= ? AND END_DATE >= ?) "; 


		try {
			//Retrieve record set with date between Start and End date
			FiscalYear fiscalYear = jt.queryForObject(sql, new Object[] {currentDate, currentDate },
					new BeanPropertyRowMapper<FiscalYear>(FiscalYear.class));		
			
			return fiscalYear;     	
		} catch (Exception e) {
			System.out.println("CLASS:FiscalYearDao METHOD:searchByDate()" + e.getMessage());		
		}
		return null;
		
	}


}
