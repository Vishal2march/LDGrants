package gov.nysed.oce.ldgrants.grants.common.dao;

import gov.nysed.oce.ldgrants.grants.common.domain.FundCode;
import gov.nysed.oce.ldgrants.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class FundCodeDao extends GenericDao<FundCode, Long>{

    public FundCodeDao(){}
	
	@Override
	public Long insert(FundCode fundcode,User user) {
		//@formatter:off
		String sql ="/* Formatted on 1/3/2017 1:16:46 PM (QP5 v5.252.13127.32847) */ " +
				"INSERT INTO FUND_CODES (CODE, " +
				"                        DESCRIPTION, " +
				"                        DATE_CREATED, " +
				"                        CREATED_BY) " +
				"     VALUES (?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             ?) "; 

			
		try {
			//TODO: Missing sequence value
			Long pk = getNextId("");
			long rows = (long) jt.update(sql, new Object[]{pk, fundcode.getDescription(),user.getLanId()});
			
			if (rows == 1) 
				return pk;
			
		} catch (Exception ex) {
		
			System.err.println("CLASS:FundCodeDao METHOD:insert()" + ex.toString());
		} 
		return null;
	}
	
	
	@Override
	public Long  update(FundCode fundcode,User user) {
		//@formatter:off
		String sql = "/* Formatted on 1/3/2017 1:25:11 PM (QP5 v5.252.13127.32847) */ " +
				"UPDATE FUND_CODES " +
				"   SET DESCRIPTION = ?, MODIFIED_BY = ?, DATE_MODIFIED = SYSDATE " +
				" WHERE CODE = ? "; 

		try {
			long rows = (long) jt.update(sql, new Object[]{fundcode.getDescription(),user.getLanId()}, fundcode.getCode());
			return rows;
		} catch (Exception ex) {
		System.err.println("CLASS:FundCodeDao METHOD:update() " + ex.toString());
		} 
		return null;
	}

	
	@Override
	public boolean delete(Long code) {
		//@formatter:off
		String sql = "DELETE FROM FUND_CODES WHERE CODE=?";
		try {
			int rows = jt.update(sql, new Object[] {code });
			return (rows == 1);
		} catch (Exception ex) {
			System.err.println("CLASS:FundCodeDao METHOD:delete() " + ex.toString());
		} 
		return false;
	}

	@Override
	public FundCode select(Long code) {
		//@formatter:off	
		String sql = "/* Formatted on 1/3/2017 1:08:32 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE AS Code, DESCRIPTION AS description " +
				"  FROM FUND_CODES " +
				" WHERE CODE = ? "; 
		try {
			FundCode fundcode =  jt.queryForObject(sql, new Object[] { code }, 
					new BeanPropertyRowMapper<FundCode>(FundCode.class));

			return fundcode;
		} catch (Exception e) {
			System.out.println("CLASS:FundCodeDao METHOD:select()" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<FundCode> selectAll() {
		//@formatter:off
		String sql ="/* Formatted on 1/3/2017 1:11:31 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE AS code, " +
				"       DESCRIPTION AS description, " +
				"       CREATED_BY AS createdBy, " +
				"       DATE_CREATED AS dateCreated, " +
				"       MODIFIED_BY AS modifiedby, " +
				"       DATE_MODIFIED AS dateModified " +
				"  FROM FUND_CODES ";
		try {

			List<FundCode> fundList = jt.query(sql, new BeanPropertyRowMapper<FundCode>(FundCode.class));

			if (fundList.size() > 0)
				return (ArrayList<FundCode>) fundList;

		} catch (Exception e) {
			System.out.println("CLASS:FundCodeDao METHOD:selectAll()" + e.getMessage());
		}
		return null;
	}

	public ArrayList<FundCode> searchByDescription(String Description) {
		//@formatter:off
		String sql ="/* Formatted on 1/3/2017 1:02:56 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT CODE AS Code, DESCRIPTION AS Description, " +
				"  FROM FUND_CODES " +
				" WHERE DESCRIPTION LIKE UPPER (?) "; 

		try {
			List<FundCode> fundList = jt.query(sql, new Object[] { Description + '%' },
					new BeanPropertyRowMapper<FundCode>(FundCode.class));		
    				
			if(fundList.size() > 0)
			return (ArrayList<FundCode>)fundList;     
			
		} catch (Exception e) {
			System.out.println("CLASS:FundCodeDao METHOD:searchByDescription()" + e.getMessage());		
		}
		return null;
	}

}
