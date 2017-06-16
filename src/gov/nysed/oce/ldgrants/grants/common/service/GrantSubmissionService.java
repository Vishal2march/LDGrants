package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.GrantDao;
import gov.nysed.oce.ldgrants.grants.common.dao.GrantSubmissionDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Grant;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FormTypeEnum;
import gov.nysed.oce.ldgrants.user.domain.User;

public class GrantSubmissionService implements GenericDaoService<GrantSubmission, Long> {

	public GrantSubmissionDao grantSubmissionDao = new GrantSubmissionDao();
	
	public int submitFinalYear1(User user, Long grantId) {

		int result = 0;
		try {
			// get primary key
			long id = grantSubmissionDao.getNextGrantSubmissionId();

			// create Submission object
			GrantSubmission submission = new GrantSubmission();
			submission.setId(id);
			submission.setVersion("Final");
			submission.setFormTypeId(FormTypeEnum.FinalYear1.getFormTypeId());
			submission.setGrantId(grantId);

			// insert into GRANT_SUBMISSIONS table
			result = (int) grantSubmissionDao.insertGrantSubmission(submission, user);

			// lock app; GRANTS table
			GrantDao grantDao = new GrantDao();
			result = (int) grantDao.updateGrantForFinalSubmit(grantId, user);

		} catch (Exception e) {
			System.err.println("GrantSubmissionService.submitFinalYear1 " + e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
	
	public int submitInitial(User user, Long grantId) {

		int result = 0;
		try {
			// get primary key
			long id = grantSubmissionDao.getNextGrantSubmissionId();

			// create Submission object
			GrantSubmission submission = new GrantSubmission();
			submission.setId(id);
			submission.setVersion("Initial");
			submission.setFormTypeId(FormTypeEnum.Initial.getFormTypeId());
			submission.setGrantId(grantId);

			// insert into GRANT_SUBMISSIONS table
			result = (int) grantSubmissionDao.insertGrantSubmission(submission, user);

			// lock app; GRANTS table
			GrantDao grantDao = new GrantDao();
			result = (int) grantDao.updateGrantForInitialSubmit(grantId, user);

		} catch (Exception e) {
			System.err.println("GrantSubmissionService.submitInitial " + e.getMessage());
		}
		return result;
	}


	
	
	public long submitAmendment(User user, Grant grant) {

		long result = 0;
		try {
			// get primary key
			long id = grantSubmissionDao.getNextGrantSubmissionId();

			// create Submission object
			GrantSubmission submission = new GrantSubmission();
			submission.setId(id);
			submission.setVersion("Amendment");
			submission.setFormTypeId(FormTypeEnum.Amendment.getFormTypeId());
			submission.setGrantId(grant.getId());
			submission.setFyCode(grant.getFyCode());

			// insert into GRANT_SUBMISSIONS table
			result = grantSubmissionDao.insert(submission, user);

			// lock amendments; currently using GRANTS table
			GrantDao grantDao = new GrantDao();
			result = (int) grantDao.updateGrantForAmendmentSubmit(grant.getId(), user);

		} catch (Exception e) {
			System.err.println("GrantSubmissionService.submitAmendment() " + e.getMessage());
		}
		return result;
	}





	@Override
	public Long insert(GrantSubmission t, User user) {

		return grantSubmissionDao.insert(t, user);
	}

	@Override
	public Long update(GrantSubmission t, User user) {
      return grantSubmissionDao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		return grantSubmissionDao.delete(id);
	}

	@Override
	public GrantSubmission select(Long id) {
		
		return grantSubmissionDao.select(id);
	}

	@Override
	public List<GrantSubmission> selectAll() {
		
		return grantSubmissionDao.selectAll();
	}
	public List<GrantSubmission> searchGrantSubmissionsByGrantId(Long grantId) {
		return grantSubmissionDao.searchGrantSubmissionByGrantId(grantId);
	}

}
