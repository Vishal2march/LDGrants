package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.CategoryType;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface CategoryInt extends DatabaseConnectionInt {
	
	//return all categories with foreign key in NARRATIVE_TYPES, where nt_id is in VERSIONS 
	//for selected fy/fund/fmt_id
	public List<CategoryType> searchCategoriesForVersion(Long fyCode, Long fcCode, Long formTypeId);
	

}
