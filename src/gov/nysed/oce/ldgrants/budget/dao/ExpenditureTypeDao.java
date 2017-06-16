package gov.nysed.oce.ldgrants.budget.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.dao.GenericReadOnlyDao;
import gov.nysed.oce.ldgrants.grants.common.domain.ExpenditureType;


public class ExpenditureTypeDao extends GenericReadOnlyDao<ExpenditureType, Long> {

	public ExpenditureTypeDao() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public ExpenditureType select(Long id) {
		//@formatter:off	
		String sql = "/* Formatted on 3/6/2017 8:41:52 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT id, " +
				"       expenditure_name, " +
				"       created_by, " +
				"       modified_by, " +
				"       date_created, " +
				"       date_modified " +
				"  FROM Expenditure_Types " +
				" WHERE id = ? "; 

 
		try {
			ExpenditureType expenditureType =  jt.queryForObject(sql, new Object[] { id }, 
					new BeanPropertyRowMapper<ExpenditureType>(ExpenditureType.class));

			return expenditureType;
		} catch (Exception e) {
			System.out.println("CLASS:ExpenditureTypeDao METHOD:select()" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<ExpenditureType> selectAll() {
		//@formatter:off
		String sql ="/* Formatted on 3/6/2017 8:41:20 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT id, " +
				"       expenditure_name as expenditureType, " +
				"       created_by as createdBy," +
				"       modified_by as modifiedBy, " +
				"       date_created as dateCreated, " +
				"       date_modified as dateCreated " +
				"  FROM Expenditure_Types ";
		try {

			List<ExpenditureType> expenditureType = jt.query(sql, new BeanPropertyRowMapper<ExpenditureType>(ExpenditureType.class));

			if (expenditureType.size() > 0)
				return (ArrayList<ExpenditureType>) expenditureType;

		} catch (Exception e) {
			System.out.println("CLASS:ExpenditureTypeDao METHOD:selectAll()" + e.getMessage());
		}
		return null;
	}
	
	public List<ExpenditureType> searchByFundCode(Long fundCode){
		
		String sql ="/* Formatted on 3/6/2017 8:44:22 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT EXP.id as id, " +
				"       expenditure_name as expenditureName, " +
				"       EXP.created_by as createdBy, " +
				"       EXP.modified_by as modifiedBy, " +
				"       EXP.date_created as dateCreated, " +
				"       EXP.date_modified as dateModified" +
				"  FROM Expenditure_Types EXP, Fund_Exp_Types fet " +
				" WHERE fet.et_id = EXP.id AND fet.fc_code = ? "; 

		try {

			List<ExpenditureType> expenditureType = jt.query(sql,new Object[] { fundCode }, new BeanPropertyRowMapper<ExpenditureType>(ExpenditureType.class));

			if (expenditureType.size() > 0)
				return (ArrayList<ExpenditureType>) expenditureType;

		} catch (Exception e) {
			System.out.println("CLASS:ExpenditureTypeDao METHOD:searchByFundCode()" + e.getMessage());
		}
		return null;
	}
		
	}


