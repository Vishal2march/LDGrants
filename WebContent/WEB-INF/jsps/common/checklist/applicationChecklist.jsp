<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Literacy Check List</title>

<%-- checklist specific css and javascript, per LD-88 --%>
<link href="css/checklist.css?v=2" rel="stylesheet" media="screen" type="text/css" /> 
<script src="jscripts/checklist/checklist.js?v=1" type="text/javascript"></script>   
</head>

<body>
	<div class="row">

		<div class="col-xs-offset-2 col-xs-8">

			<div class="row">
				<div class="col-lg-1">
					<span class="label label-default">Application Checklist</span>
				</div>
			</div>
			
			<div class="row">
				<div class="col-lg-12">
				<s:form action="formchecklist" method="GET">
					<table class="table table-bordered table-hover" id="checklisttable">
						<thead>
							<tr>
							<th class="text-center">Forms</th>
							<th class="text-center">Status</th>
								
							</tr>
						</thead>
						<s:iterator value="formTypeList" var="form">
			           
							<tr>
								<td>
									<!-- If display name contains the word Optional fill blue in related button-->
									<s:if test="#form.displayName.contains('Optional')">
										<s:submit id="%{formType}" type="submit"
										name="ft"
											value="%{displayName}"
											cssClass="btn btn-md btn-info btn-block"
											/>
									</s:if> <s:else>
								
										<s:submit id="%{formType}"  
										type="submit"
										name="ft"
											value="%{displayName}"
											cssClass="btn btn-md btn-active btn-block"
											/>
									</s:else>
								</td>
									<td class="col-lg-2">
									<div  style="display: none;"  id="<s:property value="formType"/>Submitted"
										class="btn btn-sm btn-block btn-warning disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Application Submitted">
										<span class="glyphicon glyphicon-check"></span>Submitted
										</button>
									</div>
									<div  style="display: none;" id="<s:property value="formType"/>Approved"
										class="btn btn-sm btn-success btn-block disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Application Approved">
										<span class="glyphicon glyphicon-ok-circle"></span>Approved</div>
									<div  style="display: none;" id="<s:property value="formType"/>Denied"
										class="btn btn-sm btn-danger btn-block disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Application Denied">
										<span class="glyphicon glyphicon-ban-circle"></span>Denied</div>
									<div  style="display: none;" id="<s:property value="formType"/>Declined"
										class="btn btn-sm btn-danger btn-block disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Application Declined by Library">
										Declined</div>
								</td>
							

						
							</tr>
							</div>


							<%-- 										<div id="<s:property value="formType"/>Declined" --%>
							<!-- 											class="col-lg-1"> -->
							<%-- 											<span class="glyphicon glyphicon-remove-sign text-danger"></span> --%>
							<!-- 										</div> -->
							<%-- 								<div id="<s:property value="formType"/>Declined" class="col-lg-1"> --%>
							<%--    						            <span  class="glyphicon glyphicon-remove-sign text-danger"></span> --%>
							<!--    						            </div>  -->


							</div>

						</s:iterator>

					</table>
                   </s:form>
				</div>
			</div>








</body>
</html>