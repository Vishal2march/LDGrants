package gov.nysed.oce.ldgrants.grants.common.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ApprovalDao extends GenericDao<Approval, Long> {

	@Override
	public Long insert(Approval obj, User user) {
		//@formatter:off
		String sql = "/* Formatted on 4/12/2017 11:31:40 AM (QP5 v5.252.13127.32847) */ " +
				"INSERT INTO APPROVALS (ID, " +
				"                       VERSION, " +
				"                       APPROVAL_TYPE, " +
				"                       GRA_ID, " +
				"                       AMOUNT_DECLINE, " +
				"                       FMT_ID, " +
				"                       FY_FY_CODE, " +
				"                       ADMIN, " +
				"                       CREATED_BY, " +
				"                       DATE_CREATED, " +
				"                       DATE_ACCEPTED) " +
				"     VALUES (?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             ?, " +
				"             SYSDATE, " +
				"             SYSDATE) "; 


		//@formatter:on
		try {

			Long pk = getNextId("APPROVAL_SEQ");

			long rows = (long) jt.update(sql, new Object[] { pk, obj.getVersion(), obj.getApprovalType(),
					obj.getGrantId(), obj.getAmountDeclined(), obj.getFormTypeId(), obj.getFyCode(), 
					user.getCreatedBy(), user.getCreatedBy() });

			if (rows == 1)
				return pk;// return primary key

		} catch (Exception ex) {

			System.err.println("CLASS:ApprovalDao:::METHOD:insert()::: " + ex.toString());
		}
		return null;
	}

	@Override
	public Long update(Approval obj, User user) {
		//@formatter:off
		String sql = "/* Formatted on 3/21/2017 10:45:33 AM (QP5 v5.252.13127.32847) */ " +
				"UPDATE APPROVALS " +
				"   SET VERSION = ?, " +
				"       APPROVAL_TYPE = ?, " +
				"       GRA_ID = ?, " +
				"       AMOUNT_DECLINE = ?, " +
				"       FMT_ID = ?, " +
				"       ADMIN = ? " +
				"       FY_FY_CODE = ? " +
				"       MODIFIED_BY = ?, " +
				"       DATE_MODIFIED = ? " +
				"       WHERE ID = ? "; 

		//@formatter:on
		try {
			long rows = (long) jt.update(sql, new Object[] { obj.getVersion(), obj.getApprovalType(), obj.getGrantId(),
					obj.getAmountDeclined(), obj.getFormTypeId(), obj.getAdmin(), obj.getFyCode(), user.getCreatedBy(), obj.getId() });

			return rows;

		} catch (Exception ex) {

			System.err.println("CLASS:ApprovalDao:::METHOD:update()::: " + ex.toString());
		}
		return null;
	}

	@Override
	public boolean delete(Long id) {
		//@formatter:off
		String sql = "DELETE FROM APPROVALS WHERE ID=?";
		//@formatter:on
		try {
			int rows = jt.update(sql, new Object[] { id });
			return (rows == 1);

		} catch (Exception ex) {
			System.err.println("CLASS:ApprovalDao:::METHOD:selectAll()::: " + ex.toString());
		}
		return false;
	}

	@Override
	public Approval select(Long id) {
		//@formatter:off
		String sql = "/* Formatted on 3/21/2017 10:56:03 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT ID, " +
				"       GRA_ID AS GRANTID, " +
				"       FMT_ID AS FORMTYPEID, " +
				"       AMOUNT_DECLINE AS AMOUNTDECLINED, " +
				"       VERSION, " +
				"       APPROVAL_TYPE AS APPROVALTYPE, " +
				"       ADMIN " +
				"       FY_FY_CODE AS FYCODE " +
				"  FROM APPROVALS " +
				" WHERE ID = ? "; 
		//@formatter:on
		try {

			Approval approval = jt.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<Approval>(Approval.class));

			return approval;

		} catch (Exception ex) {
			System.err.println("CLASS:ApprovalDao:::METHOD:select()::: " + ex.toString());
		}
		return null;

	}

	@Override
	public List<Approval> selectAll() {
		//@formatter:off
		String sql ="/* Formatted on 3/21/2017 10:55:22 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT ID, " +
				"       GRA_ID AS GRANTID, " +
				"       FMT_ID AS FORMTYPEID, " +
				"       AMOUNT_DECLINE AS AMOUNTDECLINED, " +
				"       VERSION, " +
				"       APPROVAL_TYPE AS APPROVALTYPE, " +
				"       ADMIN " +
				"       FY_FY_CODE AS FYCODE " +
				"  FROM APPROVALS "; 
		//@formatter:on

		try {
			List<Approval> approvalList = jt.query(sql, new BeanPropertyRowMapper<Approval>(Approval.class));

			return approvalList;

		} catch (Exception ex) {
			System.err.println("CLASS:ApprovalDao:::METHOD:selectAll()::: " + ex.toString());
		}
		return null;
	}

	public List<Approval> searchApprovalsByGrant(Long grantId) {
		//@formatter:off
		String sql = "/* Formatted on 3/21/2017 10:55:22 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT ID, " +
				"       GRA_ID AS GRANTID, " +
				"       FMT_ID AS FORMTYPEID, " +
				"       AMOUNT_DECLINE AS AMOUNTDECLINED, " +
				"       VERSION, " +
				"       APPROVAL_TYPE AS APPROVALTYPE, " +
				"       ADMIN, " +
				"       FY_FY_CODE AS FYCODE " +
				"  FROM APPROVALS " +
				" WHERE GRA_ID = ? ";
		//@formatter:on
		try {
			List<Approval> approvalList = jt.query(sql, new Object[] { grantId },
					new BeanPropertyRowMapper<Approval>(Approval.class));

			return approvalList;

		} catch (Exception ex) {
			System.err.println("ApprovalDao.searchApprovalsByGrant " + ex.toString());
		}
		return null;

	}

public List<Grant> searchByFundCodeFyCode(Long fcCode, Long fyCode){
	
	List<Grant> approveList = new ArrayList<Grant>();
	
	String sql = "/* Formatted on 3/13/2017 10:59:40 AM (QP5 v5.185.11230.41888) */  "+
				 " SELECT APPROVALS.gra_id AS gra_id, "+
				 "        FC_CODE, "+
				 "        GRANTS.FY_CODE, "+
				 "        PROJ_SEQ_NUM, "+
				 "        NAME, "+
				 "        PRM_ID, "+		
				 "        CO_INSTITUTIONS.INST_ID as inst_id, "+
				 "        INITCAP (POPULAR_NAME) AS popular_name, "+
				 "        admin, "+
				 "        version, "+
				 "        approval_type AS approvaltype, "+
				 "        fmt_id,  "+
				 "        APPROVALS.date_created AS dateCreated "+	
				 "   FROM Approvals, "+
				 "        grants, "+
				 "           co_institutions "+
				 "        LEFT JOIN "+
				 "           SED_INSTITUTIONS "+
				 "        ON sed_institutions.inst_id = co_institutions.inst_id "+
				 "  WHERE     GRANTS.ID = APPROVALS.GRA_ID "+
				 "        AND GRANTS.ID = CO_INSTITUTIONS.GRA_ID "+
				 "        AND FC_CODE = ? "+
				 "        AND IS_LEAD = 'Y' "+
				 "        AND GRANTS.FY_CODE = ? "+
				 "  ORDER BY popular_name";
	try {
		
		List queryList = jt.queryForList(sql,new Object[] { fcCode, fyCode });
		
		for (int i = 0; i < queryList.size(); i++) {

			Map queryMap = (Map) queryList.get(i);
				
			Grant grant = new Grant();
			grant.setId(((BigDecimal) queryMap.get("GRA_ID")).longValue());
			grant.setFyCode(((BigDecimal) queryMap.get("FY_CODE")).longValue());
			grant.setFcCode(((BigDecimal) queryMap.get("FC_CODE")).longValue());
			grant.setProjSeqNum(((BigDecimal) queryMap.get("PROJ_SEQ_NUM")).longValue());
			grant.setName((String) queryMap.get("name"));
			BigDecimal prmId = (BigDecimal) queryMap.get("prm_id");
			if(prmId!=null)
				grant.setPrmId(prmId.longValue());	
			
				Approval approval = new Approval();
				approval.setGrantId( grant.getId());
				BigDecimal fmtId = (BigDecimal) queryMap.get("fmt_id");
				if(fmtId!=null)
					approval.setFormTypeId(fmtId.longValue());	
				approval.setAdmin((String) queryMap.get("admin"));
				approval.setVersion((String) queryMap.get("version"));
				approval.setApprovalType((String) queryMap.get("approvalType"));
				approval.setDateCreated((Date) queryMap.get("dateCreated"));
			grant.setApproval(approval);
			
				Institution inst = new Institution();
				inst.setInstId(((BigDecimal) queryMap.get("INST_ID")).longValue());
				inst.setPopularName((String) queryMap.get("popular_name"));
			grant.setInstitution(inst);
			
			approveList.add(grant);				
		}
		
	} catch (Exception ex) {
		System.err.println("ApprovalDao.searchByFundCodeFyCode " + ex.toString());
	}
	return approveList;

}

}
