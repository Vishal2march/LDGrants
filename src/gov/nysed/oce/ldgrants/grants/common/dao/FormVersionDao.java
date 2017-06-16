package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.FormType;
import gov.nysed.oce.ldgrants.grants.common.domain.VersionBean;

public class FormVersionDao implements FormVersionDaoInt {

	/**
	 * Retrieves all Versions based on fund type and fiscal year
	 * @param fundcode, fyStartDate, fyEndDate
	 * @return versionBeanList list of all Versions needed to populate checklist etc
	 */
	public List<VersionBean> selectVersionByFundAndFiscalYear(Long fundCode, Long fyBeginDate, Long fyEndDate) {

		String sql = "/* Formatted on 11/7/2016 10:09:51 AM (QP5 v5.252.13127.32847) */ " +
				"  SELECT id, " +
				"         fc_code AS fundCode, " +
				"         nat_id AS narrativeId, " +
				"         fy_code AS fyBeginDate, " +
				"         fy_code_end_fy AS fyEndDate, " +
				"         fmt_fmt_id AS formTypeId " +
				"    FROM Versions " +
				"   WHERE     fc_code = ? " +
				"         AND FY_CODE <= ? " +
				"         AND (FY_CODE_END_FY <= ? OR fy_code_end_fy IS NULL) " +
				"ORDER BY fmt_fmt_id ";
		
		try {			
			List<VersionBean> versionBeanList = jt.query(sql, 
					new Object[] {fundCode, fyBeginDate, fyEndDate}, 
					new BeanPropertyRowMapper(VersionBean.class)); 
			
			
			return versionBeanList;
		} catch(Exception ex){
			System.err.println("FAILED: METHOD: selectVersionByFundAndFiscalYear: " + ex.toString());
		}

		return null;
	}
	
	
	/**
	 * Select All Form Types
	 * @param 
	 * @return List of All form types
	 */
	public List<FormType> selectAllFormTypes() {

		String sql ="/* Formatted on 4/3/2017 3:10:54 PM (QP5 v5.252.13127.32847) */ " +
				"SELECT id, " +
				"       form_type AS formType, " +
				"       created_by AS createdBy, " +
				"       date_created AS dateCreated, " +
				"       Date_modified AS dateModified, " +
				"       modified_by AS modifiedBy, " +
				"       display_name AS displayName, " +
				"       sort_order AS sort " +
				"  FROM form_Types " +
				"  ORDER BY sort ASC "; 


		try {			
			
			List<FormType>  formTypeList = jt.query(sql, 
					new BeanPropertyRowMapper(FormType.class)); 
			
			return formTypeList;
		} catch(Exception ex){
			System.err.println("FAILED: METHOD: selectAllFormTypes: " + ex.toString());
		}

		return null;
	}

}
