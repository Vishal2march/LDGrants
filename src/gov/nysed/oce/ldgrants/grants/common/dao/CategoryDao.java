package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import gov.nysed.oce.ldgrants.grants.common.domain.CategoryType;

public class CategoryDao implements CategoryInt {
	
	public List<CategoryType> searchCategoriesForVersion(Long fyCode, Long fcCode, Long formTypeId){
						
		try {

			List<CategoryType> CategoryType = jt.query(
					"/* Formatted on 10/13/2016 12:10:50 PM (QP5 v5.185.11230.41888) */ "+
					"	SELECT id AS categoryTypeId, "+
					"	       category_type, "+
					"	       display_name, "+
					"	       sort_order "+
					"	       FROM category_types "+
					"		WHERE id IN "+
				    "        (SELECT cat_id "+
				    "          FROM narrative_types "+
				    "          WHERE id IN "+
				    "                   (SELECT nat_id "+
				    "                      FROM versions "+
				    "                     WHERE     fy_code = ? "+
				    "                           AND fc_code = ? "+
				    "                           AND fmt_fmt_id = ?)) "+
				    "        ORDER BY sort_order",
				new Object[] { fyCode, fcCode, formTypeId}, new BeanPropertyRowMapper(CategoryType.class));

			return CategoryType;  
			
		} catch (Exception e) {
			System.err.println("CategoryDao.searchCategoriesForVersion " + e.getMessage());
			return null;
		}
	}

}
