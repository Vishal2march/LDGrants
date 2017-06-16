package gov.nysed.oce.ldgrants.email.domain;

public class MessageVariableValue {

	private String value;
	private MessageVariableType messageVarType;
		
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public MessageVariableType getMessageVarType() {
		return messageVarType;
	}
	public void setMessageVarType(MessageVariableType messageVarType) {
		this.messageVarType = messageVarType;
	}
	
}
