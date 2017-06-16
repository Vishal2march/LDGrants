<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Family Literacy Admin - Final Signoff</title>
</head>
<body>

<div class="container">


<div class="panel panel-default">
	<div class="panel-heading">
	    <h2>Yearly Final Report Sign-off -<small> required for submission of Family Literacy Final Reporting Year 1</small></h2>
	</div>
	<div class="panel-body">
  


	Project Number:  03<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number" value="${grant.fcCode}" />
	            -<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" />
	            -<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
	<br/><br/>
	
	Public Library System: <s:property value="%{institution.popularName}" /> 
	
	<br/><br/>      
	  
	<p>I hereby certify that all expenditures reported in the attached budget report are directly 
	attributable to this project, and that the attached narrative is an accurate and complete 
	account of the project. </p>
	
	<%--show admin error msgs/validation that a user might receive (note: this doesn't check for user=submit) --%>
					
		<%--due date expired --%>
		<s:if test="%{grantStatus.applicationDate.finalDatesAcceptible==false}">
			   <s:set var="disableForm">true</s:set>
	           <div class="row" id="errorM">
					<div class="alert alert-danger">
						<strong> <span class="glyphicon glyphicon-alert" role="alert"></span>  Error:
						</strong> Due date has passed.
					</div>
				</div> 
	    </s:if>
	    	    
		<%-- already submitted/locked --%>
		<s:elseif test="%{grantStatus.allowSubmitFinal==false}">
			<s:set var="disableForm">true</s:set>
	        <div class="row" id="errorM">
					<div class="alert alert-warning">
						<strong> <span class="glyphicon glyphicon-alert" role="alert"></span>  Warning:
						</strong> Application has been submitted and is locked
					</div>			
			</div>      			
	    </s:elseif>
	    
	    <%-- FAILED submit validation --%>
		<s:elseif test="%{validationFailed==true}">
			<s:set var="disableForm">true</s:set>
	        <div class="row" id="errorM">
					<div class="alert alert-danger">
						<strong> <span class="glyphicon glyphicon-alert" role="alert"></span>  Warning:
						The application cannot be submitted until the following items are corrected.</strong> <br/><br/>
						<ul>						
							<s:iterator value="missingNarrativeTypes">
								<li>Narrative "<s:property value='displayName'/>" is required.</li>
							</s:iterator>
												
							<s:if test="%{validExpenses==false}">
								<li>Total budget expenses must be equal to appropriation.</li></s:if>	
								
							<s:iterator value="outOfRangeCategories" >
								<li>Total Expenses for <s:property/> must be within 10% of the total 
								from the initial budget application.</li>
							</s:iterator>	
						</ul>
					</div>			
					
			</div>      			
	    </s:elseif>
	    
	    <%--allow submit --%>
	    <s:else>
	    	<s:set var="disableForm">false</s:set>
	    </s:else>
	



	<s:form action="submitFamilyLiteracyReportingYr1"  cssClass="form-vertical" method="POST">
			
		<div class="form-group">
			<div class="checkbox">
				<label>
					<s:checkbox name="directorSignoff" required disabled="#disableForm"/>  System Director: 
					<s:property value="%{adminPosition.salut}" /> <s:property value="%{adminPosition.fname}" />
		            <s:property value="%{adminPosition.mi}" /> <s:property value="%{adminPosition.lname}" />
		        </label>
			</div>            
		</div>				
		<s:submit value="Submit Final Year 1" cssClass="btn btn-primary" disabled="#disableForm" />
		                                      
	</s:form>
        
</div>


 <div class="panel-footer  alert-info">
	<strong> <span class="glyphicon glyphicon-info-sign"></span>  NOTE: </strong>
	Checking off this form will submit your Year 1 Reporting. 
	Once the Year 1 Reporting is submitted, the System Director must email Carol A. Desch 
	and Barbara Massago at DLDLP@nysed.gov .
</div>

</div>


</div>


</body>
</html>