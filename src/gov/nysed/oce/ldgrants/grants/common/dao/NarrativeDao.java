package gov.nysed.oce.ldgrants.grants.common.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectStatistic;
import gov.nysed.oce.ldgrants.grants.common.domain.StatisticType;
import gov.nysed.oce.ldgrants.user.domain.User;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.ProjectNarrative;

public class NarrativeDao implements NarrativeDaoInt {
	
	public Long getNextProjectNarrativeId() {
		return jt.queryForObject("SELECT proj_narrative_seq.nextval FROM dual", Long.class);
	}
   
	/**
	 * Retrieves all Narrative Types
	 * @param 
	 * @return narrativeType List
	 */
	
	public List<NarrativeType> selectAllNarrativeType(){
		
		String sql ="/* Formatted on 11/8/2016 12:19:35 PM (QP5 v5.252.13127.32847) */ " +
				"  SELECT id, " +
				"         narrative_name AS narrativeName, " +
				"         date_created AS dateCreated, " +
				"         created_by AS createdBy, " +
				"         date_modified AS dateModified, " +
				"         modified_by AS modifiedBy, " +
				"         display_name AS displayName, " +		
				"         display_instruction AS displayInstructions, " +
				"         cat_id AS categoryTypeId, " +
				"         sort_order AS sortOrder " +
				"    FROM Narrative_Types " +
				"ORDER BY sort_order ASC "; 

	try {
		List<NarrativeType> narrativeTypeList = jt.query(sql,
				new BeanPropertyRowMapper(NarrativeType.class));
		
		return narrativeTypeList;
	} catch (Exception ex) {
		System.err.println("FAILED: selectAllNarrativeType() " + ex.toString());
	}
	return null;	
	}
	
	
	/**
	 * Retrieves all PROJECT_NARRATIVES records related to specific Grant
	 * @param grantid
	 * @return projectNarrativeList List of project_narratives related to grant
	 */
	public List<ProjectNarrative> getAllProjectNarratives(Long grantId) {

		String sql = "/* Formatted on 8/3/2016 10:40:35 AM (QP5 v5.252.13127.32847) */ " 
					+ "SELECT id as projNarrId, "
						+ "       narrative_descr as narrativeDescr, " 
						+ "       nat_id as narrativeTypeId, "
						+ "       gra_id as grantId" 
						+ "  FROM Project_Narratives " 
						+ " WHERE gra_id = ? ";

		try {

			List<ProjectNarrative> projectNarrativeList = jt.query(sql, new Object[] { grantId },
					new BeanPropertyRowMapper(ProjectNarrative.class));

			return projectNarrativeList;
		} catch (Exception ex) {
			System.err.println("FAILED: getAllProjectNarratives() " + ex.toString());
		}
		return null;
	}

	/**
	 * Retrieves Narrative_Types record related to Project_Narrative
	 * @param narrTypeId
	 * @return NarrativeType Narrative Type related to Project Narrative
	 */

	public NarrativeType getNarrativeType(Long narrTypeId) {

		String sql = "/* Formatted on 8/3/2016 11:22:10 AM (QP5 v5.252.13127.32847) */ " +
				"SELECT id AS narrativeTypeId, narrative_name AS narrativeName " +
				"  FROM Narrative_Types " +
				" WHERE id = ? ";

		try {

			List<NarrativeType> NarrativeType = jt.query(sql, new Object[] {narrTypeId},
					new BeanPropertyRowMapper(NarrativeType.class));

			// Only one distinct record is fetched and returned
			return NarrativeType.get(0);
		} catch (Exception ex) {
			System.err.println("FAILED: getNarrativeType() " + ex.toString());
		}
		return null;
	}

	public boolean deleteNarrative(Long grantid, Long narrTypeId) {
		int rows = 0;
		String update = "DELETE FROM Project_Narratives WHERE gra_id =? AND nat_id = ? ";
		try {
			rows = jt.update(update, new Object[] { grantid, narrTypeId });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error NarrativeDao.deleteNarrative() " + ex.toString());
		} finally {
		}
		return (rows == 1);
	}

	
	public long updateNarrative(ProjectNarrative narrative, User user) {
		long rows = 0;
		String update = " update PROJECT_NARRATIVES set NARRATIVE_DESCR = ?, "
				+ " MODIFIED_BY=?, DATE_MODIFIED=SYSDATE where GRA_ID = ? and NAT_ID = ?";
		try {
			rows = jt.update(update, new Object[] { narrative.getNarrative(), user.getModifiedBy(), 
					narrative.getGrantId(), narrative.getNarrativeTypeId() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error NarrativeDao.updateNarrative() " + ex.toString());
			System.err.println("ERROR: updateNarrative() " + ex.toString());
		} finally {
		}
		return rows;
	}

	
	public long insertNarrative(ProjectNarrative narrative, User user) {
		int rows = 0;
		String update = "insert into project_narratives(ID, GRA_ID, NARRATIVE_DESCR, NAT_ID, "
				+ " DATE_CREATED, CREATED_BY) VALUES (?, ?, "
				+ " EMPTY_CLOB(), ?, SYSDATE, ?) ";
		try {
			rows = jt.update(update, new Object[] { narrative.getId(), narrative.getGrantId(), 
					narrative.getNarrativeTypeId(), user.getCreatedBy() });
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error NarrativeDao.insertNarrative() " + ex.toString());
		} finally {
		}

		return narrative.getId();
	}
	
		
	public ProjectNarrative searchNarrativeByGrantType(Long grantId, Long narrativeTypeId) {
		
		ProjectNarrative narrative = new ProjectNarrative();
		narrative.setNarrativeTypeId(narrativeTypeId);
		
		String query = "select * from project_narratives where gra_id=? and nat_id =?";
		try {
			
			List queryList = jt.queryForList(query, new Object[] { grantId, narrativeTypeId});
			
			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);
				
				narrative.setId(((BigDecimal)queryMap.get("id")).longValue());
				//the narrative Clob doesn't print correctly on jsp(?)  using narrative string instead
				//narrative.setNarrativeClob(((String) queryMap.get("narrative_descr")).getBytes());
				narrative.setNarrative((String) queryMap.get("narrative_descr"));										
			}
			
			
		} catch (Exception ex) {
			System.err.println("error NarrativeDao.searchNarrativeByGrantType() " + ex.toString());
		} finally {
		}
		return narrative;
	}
	
	
	
	
	
	public List<NarrativeType> searchNarrativeTypesByVersion(Long fyCode, Long fcCode, Long formTypeId) {

		try {

			List<NarrativeType> NarrativeType = jt.query(
					"/* Formatted on 9/19/2016 4:09:13 PM (QP5 v5.185.11230.41888) */  "+
					  "SELECT id AS narrativeTypeId, "+
					  "       narrative_name, "+
					  "       sort_order, "+
					  "       display_name, "+
					  "       display_instruction, "+
					  "       cat_id as categoryTypeId "+
					  "  FROM narrative_types "+
					  " WHERE id IN (SELECT nat_id "+
					  "                FROM versions "+
					  "               WHERE fy_code = ? AND fc_code = ? AND fmt_fmt_id = ?) "+
					" ORDER BY sort_order",
				new Object[] { fyCode, fcCode, formTypeId}, new BeanPropertyRowMapper(NarrativeType.class));

			return NarrativeType;

		} catch (Exception e) {
			System.err.println("NarrativeDao.searchNarrativeTypesByVersion " + e.getMessage());
			return null;
		}
	}
	
	
	
	public NarrativeType selectNarrativeType(Long id) {
		
		try {			
			NarrativeType NarrativeType = jt.queryForObject(
					"/* Formatted on 9/19/2016 4:09:13 PM (QP5 v5.185.11230.41888) */  "+
					  "SELECT id AS narrativeTypeId, "+
					  "       narrative_name, "+
					  "       sort_order, "+
					  "       display_name, "+
					  "       display_instruction "+
					  "  FROM narrative_types "+
					  " WHERE id =? ",
					new Object[] { id}, new BeanPropertyRowMapper<NarrativeType>(NarrativeType.class));

			return NarrativeType;
			
			
		} catch (Exception ex) {
			System.err.println("error NarrativeDao.selectNarrativeType() " + ex.toString());
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	public long insertStatistic(ProjectStatistic statistic, User user) {
		int rows = 0;
		String update = "insert into project_narratives(ID, GRA_ID, NARRATIVE_DESCR, NAT_ID, "
				+ " DATE_CREATED, CREATED_BY, STATISTIC_DESCR) VALUES (?, ?, "
				+ " EMPTY_CLOB(), ?, SYSDATE, ?, ?) ";
		try {
			rows = jt.update(update, new Object[] {statistic.getId(), statistic.getGrantId(), 
					statistic.getStatisticTypeId(),
					user.getCreatedBy(),
					statistic.getStatisticDescription()});
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error NarrativeDao.insertStatistic() " + ex.toString());
		} 

		return rows;
	}
	
	
	public long updateStatistic(ProjectStatistic statistic, User user) {
		long rows = 0;
		String update = " update PROJECT_NARRATIVES set STATISTIC_DESCR = ?, "
				+ " MODIFIED_BY=?, DATE_MODIFIED=SYSDATE where GRA_ID = ? and NAT_ID = ?";
		 
		try {
			rows = jt.update(update, new Object[] { statistic.getStatisticDescription(), 
					user.getModifiedBy(),
					statistic.getGrantId(), 
					statistic.getStatisticTypeId() });
					  
		} catch (Exception ex) {
			rows = 0;
			System.err.println("error NarrativeDao.updateStatistic() " + ex.toString());
		} finally {
		}
		return rows;
	}
	
	
	
	public List<ProjectStatistic> searchStatisticsByVersionGrant(Long grantId, Long fyCode, Long fcCode, Long formTypeId) {
		
		List<ProjectStatistic> statisticAnswers = new ArrayList<ProjectStatistic>();
		try {

			List queryList = jt.queryForList(
					"/* Formatted on 10/4/2016 12:01:45 PM (QP5 v5.185.11230.41888) */  "+
					" SELECT pn.id, "+
					"       statistic_descr as statisticDescription, "+
					"       nat_id AS narrativeTypeId, "+
					"       narrative_name, "+
					"       display_name, "+
					"       display_instruction, "+
					"       sort_order "+
					"  FROM project_narratives pn, narrative_types nt "+
					"  WHERE     pn.nat_id = nt.id "+
					"       AND gra_id = ? "+
					"       AND nat_id IN "+
					"              (SELECT nat_id "+
					"                 FROM versions "+
					"                WHERE fy_code = ? AND fc_code = ? AND fmt_fmt_id = ?)",
										new Object[] {grantId, fyCode, fcCode, formTypeId});
						
			for (int i = 0; i < queryList.size(); i++) {

				Map queryMap = (Map) queryList.get(i);
				ProjectStatistic statistic = new ProjectStatistic();
				
				statistic.setId(((BigDecimal) queryMap.get("ID")).longValue());
				statistic.setStatisticTypeId(((BigDecimal) queryMap.get("narrativeTypeId")).longValue());
				statistic.setStatisticDescription((String) queryMap.get("statisticDescription"));
				
				StatisticType statisticType = new StatisticType();
				statisticType.setStatisticTypeId(((BigDecimal) queryMap.get("narrativeTypeId")).longValue());
				statisticType.setStatisticName((String) queryMap.get("narrative_name"));
				statisticType.setDisplayName((String) queryMap.get("display_name"));
				statisticType.setDisplayInstruction((String) queryMap.get("display_instruction"));
				statisticType.setSortOrder(((BigDecimal) queryMap.get("sort_order")).longValue());
				
				statistic.setStatisticType(statisticType);
				
				statisticAnswers.add(statistic);				
			}

		} catch (Exception e) {
			System.err.println("NarrativeDao.searchStatisticsByVersionGrant " + e.getMessage());
			return null;
		}
		return statisticAnswers;
	}

	/**
	 * Retrieves all narrative records with same cateoryTypeID
	 * @param categoryId
	 * @return List of narrativeType Objects
	 */
	public List<NarrativeType> selectNarrativeByCategoryId(Long categoryId) {
		String sql = "/* Formatted on 10/27/2016 2:50:03 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT id, " +
				"       narrative_name AS narrativeName, " +
				"       cat_id AS categoryTypeID, " +
				"       display_name AS displayName, " +
				"       sort_order AS sortorder, " +
				"       date_created AS datecreated, " +
				"       created_by AS createdby, " +
				"       modified_by AS modifiedby, " +
				"       date_modified AS datemodified " +
				"  FROM Narrative_Types " +
				" WHERE cat_id = ? " ;


	try {

		List<NarrativeType> narrativeTypeList = jt.query(sql, new Object[] { categoryId },
				new BeanPropertyRowMapper(NarrativeType.class));

		return narrativeTypeList;
	} catch (Exception ex) {
		System.err.println("FAILED: selectNarrativeByCategoryId() " + ex.toString());
	}
	return null;
	}

}
