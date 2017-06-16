<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%-- checklist specific css and javascript, per LD-88 --%>
<link href="css/checklist.css?v=2" rel="stylesheet" media="screen" type="text/css" /> 
<script src="jscripts/checklist/checklist.js?v=1" type="text/javascript"></script>   
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Adult Literacy Admin - Amendment Checklist</title>
</head>
<body>

<div class="row">
		<div class="col-xs-offset-2 col-xs-8">

	<div class="row">
				<div class="col-lg-1">
					<span class="label label-default">FS-10-A Budget Amendment Forms (Optional)</span>
				</div>
			</div>
			
			<div class="row">
				<div class="col-lg-12">
					<table class="table table-bordered" id="checklisttable">
						<thead>
							<tr>
							   <th class="text-center">Forms</th>
								<th class="text-center">Status</th>
								
							</tr>
						</thead>
						<tr>
										<td>
												<input  type="button" value="Amendment Summary" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='budgetamendmentsummary.action';" /></td>
					
					<td class="col-lg-2"><div  id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="Form Completed">
										<span class="glyphicon glyphicon-saved"></span> Saved</div></td>
						</tr>
						
								<tr>
															<td>
						<input  type="button" value="Amendment Summary Signoff" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='literacyAmendmentSignoff.action';" /></td>
					
					<td class="col-lg-2"><div  id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="Form Completed">
										<span class="glyphicon glyphicon-saved"></span> Saved</div></td>
						</tr>
						
					
						</table>
		</div>
	</div>

</body>
</html>