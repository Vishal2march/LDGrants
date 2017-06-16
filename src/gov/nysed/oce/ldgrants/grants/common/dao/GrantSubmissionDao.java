package gov.nysed.oce.ldgrants.grants.common.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.domain.Institution;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.user.domain.User;

public class GrantSubmissionDao extends GenericDao<GrantSubmission, Long> {

	public Long getNextGrantSubmissionId() {
		return jt.queryForObject("SELECT GRA_SUB_SEQ.nextval FROM dual", Long.class);
	}

	
	public long insertGrantSubmission(GrantSubmission submission, User user) {
		int rows = 0;
		String update = "insert into GRANT_SUBMISSIONS "
				+ " (ID, GRA_ID, DATE_SUBMITTED, GRAS_USER, VERSION, DATE_CREATED, CREATED_BY, FMT_ID) "
				+ " values (?, ?, SYSDATE, ?, ?, SYSDATE, ?, ?) ";
		try {
			rows = jt.update(update, new Object[] { submission.getId(), submission.getGrantId(),
					user.getCreatedBy(),
					submission.getVersion(),
					user.getCreatedBy(), submission.getFormTypeId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error GrantSubmissionDao.insertGrantSubmission() " + ex.toString());
		} finally {
		}
		return submission.getId();
	}
	
	
	
	
	
	public List<GrantSubmission> searchSubmissionByGrant(Long grantId) {

		List<GrantSubmission> submissionList = new ArrayList<GrantSubmission>();
		try {

			List queryList = jt.queryForList(
					"select * from GRANT_SUBMISSIONS where GRA_ID=?",
					new Object[] { grantId });
			
			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);
				GrantSubmission submission = new GrantSubmission();
				
				submission.setId(((BigDecimal) queryMap.get("ID")).longValue());
				submission.setGrantId(((BigDecimal) queryMap.get("gra_id")).longValue());
				submission.setFormTypeId(((BigDecimal) queryMap.get("fmt_id")).longValue());
				submission.setGrasUser((String) queryMap.get("gras_user"));
				submission.setVersion((String) queryMap.get("version"));
				
				submissionList.add(submission);				
			}

		} catch (Exception e) {
			System.out.println("GrantSubmissionDao.searchSubmissionByGrant: " + e.getMessage());
			return null;
		}
		return submissionList;
	}
	
	

@Override
public Long insert(GrantSubmission obj, User user) {
	//@formatter:off
	String sql = " /* Formatted on 3/24/2017 12:32:13 PM (QP5 v5.252.13127.32847) */ " +
			"INSERT INTO GRANT_SUBMISSIONS (ID, " +
			"                               GRA_ID, " +
			"                               FMT_ID, " +
			"                               FY_FY_CODE, " +
			"                               GRAS_USER, " +
			"                               VERSION, " +
			"                               DATE_SUBMITTED, " +
			"                               CREATED_BY, " +
			"                               DATE_CREATED) " +
			"     VALUES (?, " +
			"             ?, " +
			"             ?, " +
			"             ?, " +
			"             ?, " +
			"             ?, " +
			"             SYSDATE, " +
			"             ?, " +
			"             SYSDATE) "; 


	//@formatter:on
	try {

		Long pk = getNextId("GRA_SUB_SEQ");

		long rows = (long) jt.update(sql, new Object[] { pk, obj.getGrantId(), obj.getFormTypeId(), obj.getFyCode(),
				user.getCreatedBy(), obj.getVersion(), user.getCreatedBy()});

		if (rows == 1)
			return pk;// return primary key

	} catch (Exception ex) {

		System.err.println("CLASS:GrantSubmissionDao:::METHOD:insert()::: " + ex.toString());
	}
	return null;
}





public List<Grant> searchByFundCodeFyCode(Long fcCode, Long fyCode) {

	List<Grant> submissionList = new ArrayList<Grant>();
	try {

		List queryList = jt.queryForList(
				"/* Formatted on 3/9/2017 10:25:39 AM (QP5 v5.185.11230.41888) */  "+
				"  SELECT GRANT_SUBMISSIONS.GRA_ID, "+
				"                  VERSION, "+
				"                  DATE_SUBMITTED, "+
				"                  GRAS_USER, "+				
				"                  FMT_ID, "+
				"                  FC_CODE, "+
				"                  GRANTS.FY_CODE, "+
				"                  PROJ_SEQ_NUM, "+
				"                  NAME, "+
				"                  CO_INSTITUTIONS.INST_ID, "+
				"                  INITCAP (POPULAR_NAME) AS popular_name "+
				"    FROM grant_submissions, "+
				"         grants, "+
				"            co_institutions "+
				"         LEFT JOIN "+
				"            SED_INSTITUTIONS "+
				"         ON sed_institutions.inst_id = co_institutions.inst_id "+
				"   WHERE     GRANTS.ID = GRANT_SUBMISSIONS.GRA_ID "+
				"         AND GRANTS.ID = CO_INSTITUTIONS.GRA_ID "+
				"         AND FC_CODE = ? "+
				"         AND IS_LEAD = 'Y' "+
				"         AND GRANTS.FY_CODE = ? "+
				" ORDER BY popular_name, GRANT_SUBMISSIONS.GRA_ID ",
				new Object[] { fcCode, fyCode });
		
		for (int i = 0; i < queryList.size(); i++) {

			Map queryMap = (Map) queryList.get(i);
			
			Grant grant = new Grant();
			grant.setId(((BigDecimal) queryMap.get("GRA_ID")).longValue());
			grant.setFyCode(((BigDecimal) queryMap.get("FY_CODE")).longValue());
			grant.setFcCode(((BigDecimal) queryMap.get("FC_CODE")).longValue());
			grant.setProjSeqNum(((BigDecimal) queryMap.get("PROJ_SEQ_NUM")).longValue());
			grant.setName((String) queryMap.get("name"));
						
				GrantSubmission submission = new GrantSubmission();			
				submission.setGrantId(((BigDecimal) queryMap.get("gra_id")).longValue());
				submission.setFormTypeId(((BigDecimal) queryMap.get("fmt_id")).longValue());
				submission.setUserName((String) queryMap.get("gras_user"));
				submission.setVersion((String) queryMap.get("version"));
				submission.setDateSubmitted((Date) queryMap.get("DATE_SUBMITTED"));				
			grant.setGrantSubmission(submission);
			
				Institution inst = new Institution();
				inst.setInstId(((BigDecimal) queryMap.get("INST_ID")).longValue());
				inst.setPopularName((String) queryMap.get("popular_name"));
			grant.setInstitution(inst);
			
			submissionList.add(grant);				
		}

	} catch (Exception e) {
		System.out.println("GrantSubmissionDao.searchByFundCodeFyCode: " + e.getMessage());
		return null;
	}
	return submissionList;
}





@Override
public Long update(GrantSubmission obj, User user) {
	//@formatter:off
	String sql = "/* Formatted on 3/24/2017 12:23:07 PM (QP5 v5.252.13127.32847) */ " +
			"UPDATE GRANT_SUBMISSIONS " +
			"   SET GRA_ID = ?, " +
			"       FMT_ID = ?, " +
			"       FY_FY_CODE = ?, " +
			"       GRAS_USER = ?, " +
			"       VERSION = ?, " +
			"       DATE_SUBMITTED = ?, " +
			"       MODIFIED_BY = ?, " +
			"       DATE_MODIFIED = SYSDATE " +
			" WHERE ID = ? "; 


	//@formatter:on
	try {
		long rows = (long) jt.update(sql, new Object[] { obj.getGrantId(), obj.getFormTypeId(), obj.getFyCode(),
				obj.getGrasUser(), obj.getVersion(), obj.getDateSubmitted(), user.getCreatedBy()});

		return rows;

	} catch (Exception ex) {

		System.err.println("CLASS:GrantSubmissionDao:::METHOD:update()::: " + ex.toString());
	}
	return null;
}



@Override
public boolean delete(Long id) {
	//@formatter:off
	String sql = "DELETE FROM GRANT_SUBMISSIONS WHERE ID=?";
	//@formatter:on
	try {
		int rows = jt.update(sql, new Object[] { id });
		return (rows == 1);

	} catch (Exception ex) {
		System.err.println("CLASS:GrantSubmissionDao:::METHOD:selectAll()::: " + ex.toString());
	}
	return false;
}

@Override
public GrantSubmission select(Long id) {
	//@formatter:off
	String sql = "/* Formatted on 3/24/2017 12:16:42 PM (QP5 v5.252.13127.32847) */ " +
			"SELECT ID, " +
			"       GRA_ID AS GRANTID, " +
			"       FMT_ID AS FORMTYPEID, " +
			"       FY_FY_CODE AS fyCode, " +
			"       GRAS_USER AS grasUSER, " +
			"       VERSION, " +
			"       DATE_SUBMITTED, " +
			"       DATE_CREATED, " +
			"       CREATED_BY, " +
			"       MODIFIED_BY, " +
			"       DATE_MODIFIED " +
			"  FROM GRANT_SUBMISSIONS "+
			" WHERE ID = ? "; 
	//@formatter:on
	try {

		GrantSubmission grantSubmission = jt.queryForObject(sql, new Object[] { id },
				new BeanPropertyRowMapper<GrantSubmission>(GrantSubmission.class));

		return grantSubmission;

	} catch (Exception ex) {
		System.err.println("CLASS:GrantSubmissionDao:::METHOD:select()::: " + ex.toString());
	}
	return null;

}

@Override
public List<GrantSubmission> selectAll() {
	//@formatter:off
	String sql ="/* Formatted on 3/24/2017 12:16:42 PM (QP5 v5.252.13127.32847) */ " +
			"SELECT ID, " +
			"       GRA_ID AS GRANTID, " +
			"       FMT_ID AS FORMTYPEID, " +
			"       FY_FY_CODE AS fyCode, " +
			"       GRAS_USER AS grasUSER, " +
			"       VERSION, " +
			"       DATE_SUBMITTED, " +
			"       DATE_CREATED, " +
			"       CREATED_BY, " +
			"       MODIFIED_BY, " +
			"       DATE_MODIFIED " +
			"  FROM GRANT_SUBMISSIONS "; 
 
	//@formatter:on

	try {
		List<GrantSubmission> grantSubmissionList = jt.query(sql, new BeanPropertyRowMapper<GrantSubmission>(GrantSubmission.class));

		return grantSubmissionList;

	} catch (Exception ex) {
		System.err.println("CLASS:GrantSubmissionDao:::METHOD:selectAll()::: " + ex.toString());
	}
	return null;
}

public List<GrantSubmission> searchGrantSubmissionByGrantId(Long grantId) {
	//@formatter:off
	String sql = "/* Formatted on 3/24/2017 12:16:42 PM (QP5 v5.252.13127.32847) */ " +
			"SELECT ID, " +
			"       GRA_ID AS GRANTID, " +
			"       FMT_ID AS FORMTYPEID, " +
			"       FY_FY_CODE AS fyCode, " +
			"       GRAS_USER AS grasUSER, " +
			"       VERSION, " +
			"       DATE_SUBMITTED, " +
			"       DATE_CREATED, " +
			"       CREATED_BY, " +
			"       MODIFIED_BY, " +
			"       DATE_MODIFIED " +
			"  FROM GRANT_SUBMISSIONS " +
			" WHERE GRA_ID = ? ";
	//@formatter:on
	try {
		List<GrantSubmission> grantSubmissionList = jt.query(sql, new Object[] { grantId },
				new BeanPropertyRowMapper<GrantSubmission>(GrantSubmission.class));

		return grantSubmissionList;

	} catch (Exception ex) {
		System.err.println("GrantSubmissionDao.searchGrantSubmissionsByGrant " + ex.toString());
	}
	return null;

}
}
