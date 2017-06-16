package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.user.domain.User;

public class ApprovalService implements GenericDaoService<Approval, Long> {
	ApprovalDao approvalDao = new ApprovalDao();

	public ApprovalService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long insert(Approval t, User user) {

		return approvalDao.insert(t, user);
	}

	@Override
	public Long update(Approval t, User user) {
      return approvalDao.update(t, user);
	}

	@Override
	public boolean delete(Long id) {
		
		return approvalDao.delete(id);
	}

	@Override
	public Approval select(Long id) {
		
		return approvalDao.select(id);
	}

	@Override
	public List<Approval> selectAll() {
		
		return approvalDao.selectAll();
	}
	public List<Approval> searchApprovalsByGrant(Long grantId) {
		return approvalDao.searchApprovalsByGrant(grantId);
	}
}
