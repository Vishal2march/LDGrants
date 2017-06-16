<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">

<link href="css/dataTables.css" rel="stylesheet" media="all"
	rel="stylesheet" />
<link href="css/literacy/literacyLandingPage.css" rel="stylesheet"
	media="all" rel="stylesheet" />
<%-- <script
	src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"
	crossorigin="anonymous"></script>   --%>
<script src="jscripts/dataTable/jquery.dataTables.min.js"></script>
<script src="jscripts/dataTable/dataTable.js"></script>
</head>
<body>


	<div class="col-md-8">
		<s:if test="hasActionMessages()">
			<div class="welcome alert alert-info" role="alert">
				<strong><s:actionmessage/></strong>
			</div>

		</s:if>
		<s:else>
			<div class="newButton">
			<input  type="button" value="Create Application" class="btn btn-success btn-lg"
				onclick="javascript:location.href='aliteracyApplication.do?item=createapp&m=a';" />
		
		<%--SH: button click doesn't work; changed to input/button above
				<button class="btn btn-success btn-lg" role="button"
					href="aliteracyApplication.do?item=createapp&m=a">
					Create a new application 
				</button>  --%>
				
<%-- 					<s:date name="appDate.availableDate" format="yy" />
					-
					<s:date name="appDate.dueDate" format="yy" /> --%>

				
			</div>
		</s:else>

		<%-- <c:choose>
			<c:when test="${appDates.canApply=='true'}">
				<div class="newButton">
					<button class="btn btn-success btn-lg" role="button"
						href="aliteracyApplication.do?item=createapp&m=a">
						Create a new application for FY
						<fmt:formatNumber minIntegerDigits="2"
							value="${appDates.fycode-1}" />
						-
						<fmt:formatNumber minIntegerDigits="2" value="${appDates.fycode}" />
					</button>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info" role="alert">
					<font color="red">***</font> You can only create 1 new Adult
					Literacy application per 3-year grant cycle during the new
					application period.<br />

					<c:if test="${appDates.plsInstitution=='false'}">
						<div class="alert alert-info" role="alert">
							<font color="red">***</font>Only Public Library Systems may apply
							for the Adult Literacy program.
					</c:if>
				</div>
			</c:otherwise>
		</c:choose> --%>

	</div>
	<div class="col-md-8">
		<div class="panel panel-primary application-links">
			<div class="panel-heading">
				<i class="fa fa-sitemap"></i>My Adult Literacy Projects
			</div>
			<div class="panel-body">
				<div class="row">

					<div class="col-md-12">

						<table id="projecttable" class="table ">
							<thead>
								<tr>
									<th>Project Number</th>
									<th>Title</th>
									<th>Institution</th>
									<th>Program Cycle</th>
								</tr>
							</thead>
							<tbody>
								</tr>
								<s:iterator var="grant" value="grantLandingPage.grantList">

									<s:if test="%{#grant.fyCode>16}">
										<%--SH Note: for 2016-19 apps; new bootstrap layout --%>
										<s:url var="currURL" action="applicationchecklist.action">
											<s:param name="fc" value="#grant.fcCode" />
											<s:param name="fy" value="#grant.fyCode" />
											<s:param name="id" value="#grant.id" />
										</s:url>
									</s:if>
									<s:else>
										<%--SH Note: for all old apps; old struts1 format --%>
										<s:url var="currURL"
											value="liInitialForms.do?item=checklist&p=%{p}">
											<s:param name="id" value="#grant.id" />
										</s:url>
									</s:else>


									<tr>
										<td align="center"><s:a href="%{currURL}"> 
											03<s:property value="#grant.fcCode" /> -<fmt:formatNumber
													value="${grant.fyCode}" minIntegerDigits="2" /> -<fmt:formatNumber
													value="${grant.projSeqNum}" minIntegerDigits="4"
													pattern="####" />
											</s:a></td>
										<td align="center"><s:property value="#grant.name" /></td>
										<td align="center"><s:property
												value="%{GrantLandingPage.institution.popularName}" /></td>
										<td align="center"><s:if test="#grant.fyCode==14">
             									 2013-2016
             									 </s:if> <s:elseif test="#grant.fyCode==17">
          									    2016-2019
           										 </s:elseif> <s:else>
												<s:property value="#grant.fiscalYear" />
											</s:else></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="panel panel-primary application-links">
			<div class="panel-heading">
				<i class="fa fa-link"></i>Quick Links
			</div>
			<div class="panel-body">
				<ul>
					<li><a target="_blank"
						href="http://www.nysl.nysed.gov/libdev/literacy/index.html">Adult
							Literacy Website</a></li>
					<li><a href="welcomePage.jsp">Online Application System
							Home</a></li>
				</ul>
			</div>
		</div>

	</div>
	<br />

</body>
</html>
