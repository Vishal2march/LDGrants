<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container">

	<div class="row">
	
	<s:form action="searchRecipients" theme="simple">
	
		<div>Project Type:<br/>
		<div class="form-group">
		<div class="radio">
  				<div class="form-inline">
      				<fieldset>
      				
						<s:radio name="approvalType" list="approvalTypes" /> <br/>
					
					</fieldset>
				</div>
		</div>
		</div>		
		</div>
		
		<div>Fiscal Year:<br/>
		<s:select name="fyCode" list="fiscalYears"
				listKey="code" listValue="description" />
		<s:hidden name="fcCode" />
		</div>
		
		<div><s:submit value="Search"/></div>
	</s:form>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	<div class="panel panel-primary application-links">
		<div class="panel-body">					
			<table id="projecttable" class="table ">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Institution</th>
						<th>ProjectManager</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="grants">
						
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -
								<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" /> -
								<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:property value="#grant.projectManager.email.contactValue" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>			
		</div>
	</div>
	
</div>
</body>
</html>