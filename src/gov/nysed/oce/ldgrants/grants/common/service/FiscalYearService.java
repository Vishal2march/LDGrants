package gov.nysed.oce.ldgrants.grants.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import gov.nysed.oce.ldgrants.grants.common.dao.FiscalYearDao;
import gov.nysed.oce.ldgrants.grants.common.domain.FiscalYear;
import gov.nysed.oce.ldgrants.user.domain.User;

public class FiscalYearService implements GenericDaoService<FiscalYear, Long> {

	FiscalYearDao dao = new FiscalYearDao();

	@Override
	public Long insert(FiscalYear t, User user) {
		return dao.insert(t, user);
	}

	@Override
	public Long update(FiscalYear t, User user) {
		return dao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		return dao.delete(id);
	}

	@Override
	public FiscalYear select(Long id) {

		FiscalYear tempFiscalYear = null;

		tempFiscalYear = dao.select(id);
		//Set display value
		tempFiscalYear.setDisplayValue((Integer
				.parseInt(tempFiscalYear.getDescription()) - 1)
				+ " - " + tempFiscalYear.getDescription());

		return tempFiscalYear;
	}

	@Override
	public List<FiscalYear> selectAll() {
		return dao.selectAll();
	}

	public FiscalYear searchByDate() {
		// Get Today's date
		Date date = new Date();
		// pass current date and return FiscalYear object with
		return dao.searchByDate(date);
	}

}
