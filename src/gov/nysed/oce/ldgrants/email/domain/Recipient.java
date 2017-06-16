package gov.nysed.oce.ldgrants.email.domain;

import java.util.List;

public class Recipient {

	private RecipientType recipientType;
	private long workingTemplateId;
	private long grantId;
	private String name;
	private String title;
	private String email;
	private List<MessageVariableValue> variableValueList;
	
	
	public RecipientType getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(RecipientType recipientType) {
		this.recipientType = recipientType;
	}
	public long getWorkingTemplateId() {
		return workingTemplateId;
	}
	public void setWorkingTemplateId(long workingTemplateId) {
		this.workingTemplateId = workingTemplateId;
	}
	public long getGrantId() {
		return grantId;
	}
	public void setGrantId(long grantId) {
		this.grantId = grantId;
	}
	public List<MessageVariableValue> getVariableValueList() {
		return variableValueList;
	}
	public void setVariableValueList(List<MessageVariableValue> variableValueList) {
		this.variableValueList = variableValueList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
