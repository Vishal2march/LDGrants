<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Family Literacy Admin Statistics</title>
</head>
<body>

<div class="container">

<div class="panel panel-default">
	<div class="panel-heading">
	    <h2>Family Literacy Admin - Statistics Year 1</h2>
	</div>
	<div class="panel-body">
	
		<%-- user with no admin access --%>
		<s:if test="%{#session.user.familyLiteracyAdminAccess==null}">
			<s:set var="disableForm">true</s:set><%--set form disabled --%>
			
			<div class="row" id="errorM">			
					<div class="alert alert-danger">
						<strong> <span class="glyphicon glyphicon-alert" 
						role="alert"></span>  Error:
						</strong> User does not have "Edit" permissions.
					</div>			
			</div>
		</s:if>			
			
		<s:else><%--form is enabled --%>
			<s:set var="disableForm">false</s:set>
		</s:else>


	

	<br/>
	<s:form action="familyLiteracySaveStatisticsAdmin" cssClass="form-horizontal">
	
	<%-- loop on all statistic categories --%>
	<s:iterator value="categoryList" status="categoryRow">
	
		<%-- this is category: high-level group/heading (this groups checkbox options together under
		single ct_id.  for all others, it is just a heading for text/radio questions underneath) --%>
		<h4>  <s:property value="displayName" escapeHtml="false"/><br/></h4>
	
		<s:set var="currentInstruction" value="0"/><%-- used for looping on textbox grouping
		TODO: create table/FK for this instead.  Need 3 tier grouping; not 2, for new SD/D questions --%>
						
		
		<%--  loop on all statistic types associated with this category --%>
		<s:iterator value="%{statisticTypes}" status="statisticRow">
		
			
			<%-- switch on the input form type (text/checkbox/radio/textgroup) --%>
			<s:if test="statisticName=='text'">
				
				<%-- print NarrativeType.display_name --%>
				<s:property value="displayName" escapeHtml="false" />						
										
				<div class="form-group">
					<s:textfield name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].answer.statisticDescription" cssClass="form-control" disabled="#disableForm" title="statistic description"/>
				</div>
				
			</s:if>
			<s:elseif test="statisticName=='textgroup'">
				
				<s:if test="sortOrder==1"><%--IF this is 1st textbox in the list/group; print question instruction --%>
					<%-- print NarrativeType.display_instruction --%>
					<s:property value="displayInstruction" />	<br/>	
					
					<%--set current variable to current instruction --%>
					<s:set var="currentInstruction" value="displayInstruction"/>	
				</s:if>
												
				<s:if test="#currentInstruction != displayInstruction">
					<%-- display instruction changed: this is new textbox grouping so print new instruction
					TODO: add a FK for this instead of looping/comparing strings --%>
					<%-- print NarrativeType.display_instruction --%>
					<s:property value="displayInstruction" />	<br/>	
				</s:if>
				
				<div class="form-group">
					<%-- print NarrativeType.display_name --%>
					<s:property value="displayName" escapeHtml="false" />																		
					
					<div class="col-xs-3">					
					<s:textfield name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].answer.statisticDescription" cssClass="form-control" disabled="#disableForm" title="statistic description"/>
					</div>
				</div>
				
				<%--set current variable to current instruction; then loop --%>
				<s:set var="currentInstruction" value="displayInstruction"/>
								
			</s:elseif>
			<s:elseif test="statisticName=='checkbox'">
				
				<s:if test="sortOrder==1"><%--IF this is 1st checkbox in the list; print question instruction --%>
					<%-- print NarrativeType.display_instruction --%>
					<s:property value="displayInstruction" />				
				</s:if>
			
				<div class="checkbox">
				<label>
					<s:checkbox name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].answer.statisticDescription" disabled="#disableForm" /> <s:property value="displayName"/> <br/>
				</label>
				</div>
			</s:elseif>
			<s:elseif test="statisticName=='radio'">
			
				<%-- print NarrativeType.display_name --%>
				<s:property value="displayName" />		
			
				<div class="form-group">
				<div class="radio">
    				<div class="form-inline">
        					<fieldset>
							<s:radio name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].answer.statisticDescription" list="#{true:'Yes', false:'No'}" disabled="#disableForm" /> <br/>
							</fieldset>
					</div>
				</div>
				</div>
			</s:elseif>
			
			<s:hidden name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].statisticTypeId" />
			<s:hidden name="categoryList[%{#categoryRow.index}].statisticTypes[%{#statisticRow.index}].answer.id" />
			
		</s:iterator>
		
		<br/><br/>
			
	</s:iterator>
	
	<s:submit value="Save" id="submitBtn" name="submitBtn" cssClass="btn btn-primary" disabled="#disableForm" />
	
	</s:form>
	<br/><br/>
	
	
	</div>
		
	
</div><%--end panel --%>

</div><%--end container --%>

</body>
</html>