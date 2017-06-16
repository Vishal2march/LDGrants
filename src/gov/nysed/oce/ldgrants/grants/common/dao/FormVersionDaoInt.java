package gov.nysed.oce.ldgrants.grants.common.dao;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.domain.FormType;
import gov.nysed.oce.ldgrants.grants.common.domain.VersionBean;
import gov.nysed.oce.ldgrants.shared.dao.common.DatabaseConnectionInt;

public interface FormVersionDaoInt extends DatabaseConnectionInt{
	
	public List<VersionBean> selectVersionByFundAndFiscalYear(Long fundCode, Long fyBeginDate, Long fyEndDate);
	public List<FormType> selectAllFormTypes();
}
