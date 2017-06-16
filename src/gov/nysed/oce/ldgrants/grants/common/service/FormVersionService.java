package gov.nysed.oce.ldgrants.grants.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gov.nysed.oce.ldgrants.grants.common.dao.ApprovalDao;
import gov.nysed.oce.ldgrants.grants.common.dao.FormVersionDao;
import gov.nysed.oce.ldgrants.grants.common.dao.GrantSubmissionDao;
import gov.nysed.oce.ldgrants.grants.common.dao.NarrativeDao;
import gov.nysed.oce.ldgrants.grants.common.domain.Approval;
import gov.nysed.oce.ldgrants.grants.common.domain.FormType;
import gov.nysed.oce.ldgrants.grants.common.domain.FormVersionBuilder;
import gov.nysed.oce.ldgrants.grants.common.domain.GrantSubmission;
import gov.nysed.oce.ldgrants.grants.common.domain.NarrativeType;
import gov.nysed.oce.ldgrants.grants.common.domain.VersionBean;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FormTypeEnum;
import gov.nysed.oce.ldgrants.grants.common.enumeration.FundProgram;

public class FormVersionService {

	public final static int NARRATIVE_FORM_BUTTON_CATEGORY_TYPE_ID = 10;
	
	FormVersionDao versionFormDao = new FormVersionDao();
	GrantSubmissionDao submissionDao = new GrantSubmissionDao();
	ApprovalDao approvalDao = new ApprovalDao();
	NarrativeDao natDao = new NarrativeDao();
	NarrativeService narService = new NarrativeService();

	public List<VersionBean> selectVersionList(Long fundCode, Long fyCode) {
		// query and return VersionBeanList
		return versionFormDao.selectVersionByFundAndFiscalYear(fundCode, fyCode, fyCode);
	}

	public List<FormType> selectAllFormTypes() {
		return versionFormDao.selectAllFormTypes();
	}

	public FormVersionBuilder buildFromVersion(Long fundCode, Long fyCode) {

		
		// instantiate new FormVersionBuilder
		FormVersionBuilder formBuilder = new FormVersionBuilder();

		List<FormType> tempFormTypeList = new ArrayList<FormType>();
		List<NarrativeType> tempNarrativeList = new ArrayList<NarrativeType>();

		// set versionBean list form builder
		formBuilder.setVersionList(selectVersionList(fundCode, fyCode));
		
		// select all form types and set to formtype list variable
		List<FormType> formTypeList = selectAllFormTypes();
		// loop through form type list and version list from
		// form builder to get all related form type values and add to builder's
		// form type list
		
		
		//get fund for this fcCode - for Literacy, remove "Statistics" from application checklist
		FundProgram fund = FundProgram.searchByFundCode(fundCode.intValue());					
		if(fund== FundProgram.ADULT_LITERACY || fund==FundProgram.FAMILY_LITERACY){		
			
			Iterator<FormType> i = formTypeList.iterator();
  			while(i.hasNext()){
  				FormType f = i.next();
  				if(f.getId()==FormTypeEnum.Statistics.getFormTypeId())
  					i.remove();
  			}			
		}
				

		for (FormType fBean : formTypeList) {
			// loop version list
			for (VersionBean vBean : formBuilder.getVersionList()) {
				// save version's form type id in temp variable
				Long tempVersionFormTypeId = vBean.getFormTypeId();

				// if values equal add fBean to builder formType list
				if (tempVersionFormTypeId.longValue()==fBean.getId().longValue()) {
					tempFormTypeList.add(fBean);
					break;
				}

			}

		}

		List<NarrativeType> narrativeTypeList = narService.selectAllNarratives();
		// loop through narrative type type list and version list from
		// form builder to get all related narrative type values and add to
		// builder's
		// narrative type list

		for (NarrativeType nBean : narrativeTypeList) {

			// loop version list
			for (VersionBean vBean : formBuilder.getVersionList()) {
				// save version's form type id in temp variable
				Long tempVersionNarrativeId = vBean.getNarrativeId();

				// if values equal add nBean to builder narrativeType list
				if (tempVersionNarrativeId.longValue() == nBean.getId().longValue()) {

					//if narrative is a form button set formbutton to true
					if (nBean.getCategoryTypeId() != null && nBean.getCategoryTypeId().intValue() == NARRATIVE_FORM_BUTTON_CATEGORY_TYPE_ID){
						
						nBean.setFormButton(true);
					}
                    
					tempNarrativeList.add(nBean);
					break;
				}

			}

		}

					
		// set formTypeList
		formBuilder.setFormTypeList(tempFormTypeList);

		// set narrative list
		formBuilder.setNarrativeTypeList(tempNarrativeList);
		
		
		return formBuilder;

	}

	public boolean isFormSubmitted(Long formTypeId, Long grantId) {

		List<GrantSubmission> grantSubmissionList = submissionDao.searchGrantSubmissionByGrantId(grantId);

		for (GrantSubmission submissionBean : grantSubmissionList) {
			if (submissionBean.getFormTypeId().longValue() == formTypeId.longValue()) {
				return true;
			}
		}
		return false;

	}

	public List<String> getApprovalTypes(Long formTypeId, Long grantId) {

		List<Approval> approvalList = approvalDao.searchApprovalsByGrant(grantId);
		List<String> approvalTypeList = new ArrayList<String>();

		for (Approval approval : approvalList) {
			if (approval.getFormTypeId().longValue() == formTypeId.longValue()) {

				approvalTypeList.add(approval.getApprovalType());
			}
		}
		return approvalTypeList;

	}





}
