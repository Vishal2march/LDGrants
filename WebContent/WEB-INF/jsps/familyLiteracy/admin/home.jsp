<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type"	content="text/html; charset=windows-1252">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link href="css/dataTables.css" rel="stylesheet" media="all" rel="stylesheet" />
<link href="css/literacy/literacyLandingPage.css" rel="stylesheet"	media="all" rel="stylesheet" />
<script	src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script	src="jscripts/dataTable/dataTable.js"></script>
</head>
<body>


<div class="panel panel-primary">
    <div class="panel-heading">Family Literacy Admin</div>
    <div class="panel-body">




 <s:form action="familyLiteracyAdminLandingSearch.action" class="form-inline">
 <div class="form-group">
  <label>Program Cycle</label>
              <select name="fyCode" class="form-control">
                <option value="17">2016-2019</option>
                <option value="14">2013-2016</option>
              </select>
       <s:hidden name="fcCode" />
       <s:submit value="View" class="btn btn-primary"/>
  </div>
  </s:form>
  
  </div>
</div>


<div id="accordion">

<h3>Submitted Applications</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">					
			<table id="projecttable" class="table ">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Submission Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.submittedList">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -
								<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" /> -
								<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.grantSubmission.dateSubmitted" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>			
		</div>
	</div>
</div>



<h3>First Year Reporting Submitted</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable" class="table ">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Submission Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year1Submitted">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -
								<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" /> -
								<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.grantSubmission.dateSubmitted" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>			
		</div>
	</div>
</div>




<h3>First Year Reporting Approved</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable3" class="table">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Approve Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year1Approved">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -<fmt:formatNumber
										value="${grant.fyCode}" minIntegerDigits="2" /> -<fmt:formatNumber
										value="${grant.projSeqNum}" minIntegerDigits="4"
										pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.approval.dateCreated" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>				
		</div>
	</div>
</div>




<h3>Second Year Reporting Submitted</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable" class="table ">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Submission Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year2Submitted">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -
								<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" /> -
								<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.grantSubmission.dateSubmitted" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>						
		</div>
	</div>
</div>




<h3>Second Year Reporting Approved</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable3" class="table">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Approve Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year2Approved">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -<fmt:formatNumber
										value="${grant.fyCode}" minIntegerDigits="2" /> -<fmt:formatNumber
										value="${grant.projSeqNum}" minIntegerDigits="4"
										pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.approval.dateCreated" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>					
		</div>
	</div>
</div>




<h3>Third Year Reporting Submitted</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable" class="table ">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Submission Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year3Submitted">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -
								<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" /> -
								<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4" pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.grantSubmission.dateSubmitted" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>				
		</div>
	</div>
</div>



<h3>Third Year Reporting Approved</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable3" class="table">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Approve Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.year3Approved">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -<fmt:formatNumber
										value="${grant.fyCode}" minIntegerDigits="2" /> -<fmt:formatNumber
										value="${grant.projSeqNum}" minIntegerDigits="4"
										pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.approval.dateCreated" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>				
		</div>
	</div>
</div>




<h3>Approved Applications</h3>
<div class="col-md-30">
	<div class="panel panel-primary application-links">
		<div class="panel-body">
			<table id="projecttable3" class="table">
				<thead>
					<tr>
						<th>Project Number</th>
						<th>Title</th>
						<th>Institution</th>
						<th>Approve Date</th>
					</tr>
				</thead>
				<tbody>							
					<s:iterator var="grant" value="adminLandingPage.approvedList">
						<s:url var="appURL" value="adminChecklist.action">
							<s:param name="id" value="#grant.id" />
						</s:url>
						<tr>
							<td align="center"><s:a href="%{appURL}"> 
								03<s:property value="#grant.fcCode" /> -<fmt:formatNumber
										value="${grant.fyCode}" minIntegerDigits="2" /> -<fmt:formatNumber
										value="${grant.projSeqNum}" minIntegerDigits="4"
										pattern="####" />
								</s:a></td>
							<td align="center"><s:property value="#grant.name" /></td>
							<td align="center"><s:property value="#grant.institution.popularName" /></td>
							<td align="center"><s:date name="#grant.approval.dateCreated" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>					
		</div>
	</div>
</div>


</div> <%-- end accordion; switch on h3 tags --%>


</body>
</html>