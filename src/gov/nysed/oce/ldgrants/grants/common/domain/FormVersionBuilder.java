package gov.nysed.oce.ldgrants.grants.common.domain;

import java.util.List;

public class FormVersionBuilder {

	private List<VersionBean> versionList;
	private List<FormType> formTypeList;
	private List<NarrativeType> narrativeTypeList;
	

	public FormVersionBuilder() {
		
		// TODO Auto-generated constructor stub
	}

	public List<VersionBean> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<VersionBean> versionList) {
		this.versionList = versionList;
	}



	public List<FormType> getFormTypeList() {
		return formTypeList;
	}

	public void setFormTypeList(List<FormType> formTypeList) {
		this.formTypeList = formTypeList;
	}

	public List<NarrativeType> getNarrativeTypeList() {
		return narrativeTypeList;
	}

	public void setNarrativeTypeList(List<NarrativeType> narrativeTypeList) {
		this.narrativeTypeList = narrativeTypeList;
	}

}
