<%--
 * @author  Stefanie Husak
 * @version 1.0
 * Name of File  amendmentSignoffLiteracy.jsp
 * Creation/Modification History  
 * SHusak 3/28/17 Created
 *
 * Description
 * Submission page for Adult/Family Literacy amendment summary and FS10A. Contains Literacy specific
 * instructions. Submit amendment summary will insert row in GRANT_SUBMISSIONS.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Amendment Signoff</title>
</head>
<body>


	<div class="container">


		<div class="panel panel-default">
			<div class="panel-heading">
				<h2>
					Amendment Sign-off -<small> required for submission of
						Budget Amendment</small>
				</h2>
			</div>

			<div class="panel-body">

				<div class="panel  alert-info">
					<strong>&nbsp;&nbsp; <span
						class="glyphicon glyphicon-info-sign"></span></strong>
					<ul>
						<li>Please contact Carol A. Desch (carol.desch@nysed.gov)
							before submitting any amendment information.</li>

						<li>FS-10-A must be submitted by mid-May each year, in order
							to be considered.</li>

					</ul>
				</div>

				<br />
				<br />

				<div>
					The <b>FS-10-A</b> Budget Amendment form must be submitted <b>only
						if</b> the applicant is requesting any type of amendment to the
					original approved budget, such as a change of vendor or consultant.
					Complete if more than 10% or $1,000 (whichever is less) is moved
					from one budget category to another.<br />
					<br /> Please call Carol A. Desch for approval before you submit this
					form. Ms. Desch can be reached at 518-474-7196 or
					carol.desch@nysed.gov. <br />
					<br />

					<address>
						<strong>Literacy Library Services Grant Program</strong><br>
						New York State Library<br> Division of Library Development<br>
						Cultural Education Center<br> Albany, NY 12230 <br> <abbr
							title="Attention">Attn:</abbr> Carol A. Desch, Program Director; <br>Barbara
						Massago, Budget Review <br /> <br />
					</address>
				</div>



				<br />
				<br /> Project Number: 03
				<fmt:formatNumber minIntegerDigits="2" pattern="##" type="number"
					value="${grant.fcCode}" />
				-
				<fmt:formatNumber value="${grant.fyCode}" minIntegerDigits="2" />
				-
				<fmt:formatNumber value="${grant.projSeqNum}" minIntegerDigits="4"
					pattern="####" />
				<br />
				<br /> Public Library System:
				<s:property value="%{institution.popularName}" />

				<br />
				<br />


				<%-- user does not have submit access --%>
				<s:if test="%{userPermission!='SUBMIT'}">
					<s:set var="disableForm">true</s:set>
					<div class="row" id="errorM">
						<div class="alert alert-danger">
							<strong> <span class="glyphicon glyphicon-alert"
								role="alert"></span> Error:
							</strong> User does not have "Submit" permissions.
						</div>
					</div>
				</s:if>


				<%-- already submitted/locked --%>
				<s:elseif test="%{amendmentLocked == true}">
					<s:set var="disableForm">true</s:set>
					<div class="row" id="errorM">
						<div class="alert alert-warning">
							<strong> <span class="glyphicon glyphicon-alert"
								role="alert"></span> Warning:
							</strong> The amendment summary has been submitted and is locked
						</div>
					</div>
				</s:elseif>

				<%--allow submit --%>
				<s:else>
					<s:set var="disableForm">false</s:set>
				</s:else>




				<s:form action="submitAmendmentLiteracy" cssClass="form-vertical"
					method="POST">

					<div class="form-group">
						<div class="checkbox">
							<label> <s:checkbox name="directorSignoff" required
									disabled="#disableForm" /> System Director: <s:property
									value="%{adminPosition.salut}" /> <s:property
									value="%{adminPosition.fname}" /> <s:property
									value="%{adminPosition.mi}" /> <s:property
									value="%{adminPosition.lname}" />
							</label>
						</div>
					</div>
					<s:submit value="Submit Amendment" cssClass="btn btn-primary"
						disabled="#disableForm" />

				</s:form>

			</div>


		</div>

	</div>

</body>
</html>