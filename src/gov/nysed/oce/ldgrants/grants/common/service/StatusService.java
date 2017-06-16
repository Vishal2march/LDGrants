package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nysed.oce.ldgrants.grants.common.dao.StatusDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.StatusBean;
import gov.nysed.oce.ldgrants.user.domain.User;

public class StatusService {


	StatusDao statusDao = new StatusDao();


	public List<StatusBean> selectStatusesByGrantId(Long grantId) {

		return statusDao.selectStatusesByGrantId(grantId);
	}


	// narrative Status

	public Map<String, String> getNarrativeStatus(List<NarrativeType> narrativeList, Long grantId) {

		List<StatusBean> statusList = selectStatusesByGrantId(grantId);
		Map<String, String> statusMap = new HashMap<String, String>();
		for (NarrativeType nBean : narrativeList) {
			for (StatusBean sBean : statusList) {
				if (nBean.getId().equals(sBean.getNarrativeId())) {
					statusMap.put(nBean.getNarrativeName(), sBean.getStatus());
				}
			}
		}

		for (Map.Entry<String, String> entry : statusMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			// ...

			// System.out.println("NARRATIVE NAME: " + key +" Status: " +
			// value);
		}
		return statusMap;

	}
}
