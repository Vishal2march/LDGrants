<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<%-- checklist specific css and javascript, per LD-88 --%>
<link href="css/checklist.css?v=2" rel="stylesheet" media="screen" type="text/css" /> 
<script src="jscripts/checklist/checklist.js?v=1" type="text/javascript"></script>   
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Family Literacy Final Year 1 Reporting</title>
</head>
<body>


<div class="row">
		<div class="col-xs-offset-2 col-xs-8">

	<div class="row">
				<div class="col-lg-1">
					<span class="label label-default">Final Year 1 Reporting Forms</span>
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
						<input  type="button" value="Final Narrative" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='literacyYr1FinalNarrative.action';" /></td>
					
					<td class="col-lg-2"><div  id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="Form Completed">
										<span class="glyphicon glyphicon-saved"></span> Saved</div></td>
						</tr>
						
								<tr>
														<td>
						<input  type="button" value="Project Budget" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='literacyYr1ContractedServices.action';" /></td>
					
					<td class="col-lg-2"><div  id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="Form Completed">
										<span class="glyphicon glyphicon-saved"></span> Saved</div></td>
						</tr>
						
						
						<tr>						
										<td>
						<input  type="button" value="Final Report Statistics" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='familyLiteracyYr1Statistics.action';" /></td>
					
					<td class="col-lg-2"><div  id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="Form Completed">
										<span class="glyphicon glyphicon-saved"></span> Saved</div></td>
						</tr>
						
						
						<tr>
										<td>
						<input  type="button" value="Yearly Final Report Signoff" class="btn btn-md btn-active btn-block"
										onclick="javascript:location.href='literacyYr1FinalSignoff.action';" /></td>
					
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