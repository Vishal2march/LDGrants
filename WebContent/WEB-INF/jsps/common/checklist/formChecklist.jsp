<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%-- checklist specific css and javascript, per LD-88 --%>
<link href="css/checklist.css?v=2" rel="stylesheet" media="screen" type="text/css" /> 
<script src="jscripts/checklist/formchecklist.js?v=1" type="text/javascript"></script>   
</head>

<body>
	<div class="row">
		<div class="col-xs-offset-2 col-xs-8">

	<div class="row">
				<div class="col-lg-1">
					<span class="label label-default"><s:property value="formType"/> Forms</span>
				</div>
			</div>
			
			<div class="row">
				<div class="col-lg-12">
					<s:form action="formchecklist" method="POST" >
					<s:hidden name="ft" id="ft" value="ft"/>
					<table class="table table-bordered table-hover" id="checklisttable">
						<thead>
							<tr>
							<th class="col-lg-1 ">Completed</th>
							<th class="text-center">Form Status</th>
								<th class="text-center"> Form Types</th>
								
							</tr>
						</thead>
						<s:iterator value="narrativeFormButtonList" var="form">
							<tr>
							<td class="text-right"><input  type="checkbox"   style="cursor: default;" checked disabled/></td>
								<td>
						
									<!-- If display name contains the word Optional fill blue in related button-->
									<s:if test="#form.displayName.contains('Optional')">
										<input id="<s:property value="narrativeName"/>" type="submit"
										 name="ft"
											value="<s:property value="displayName"/> Form"
											class="btn btn-md btn-info btn-block"
											 />
									</s:if> <s:else>
									
										<input id="<s:property value="narrativeName"/>" type="submit"
										   name="ft"
											value="<s:property value="displayName"/> Form"
											class="btn btn-md btn-active btn-block"
											/>
									</s:else>
								</td>
									<td class="col-lg-2">


									<div  style="display: none;"  id="<s:property value="narrativeName"/>saved"
										class="btn btn-sm btn-block btn-success disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Form Saved">
										<span class="glyphicon glyphicon-save"></span> Saved
										</div>
									</div>
									<div  style="display: none;" id="<s:property value="narrativeName"/>incomplete"
										class="btn btn-sm btn-warning btn-block disabled"
										style="cursor: default;"
										title="<s:property value="displayName"/> Form Incomplete">
										<span class="glyphicon glyphicon-warning-sign"></span> incomplete </div>
									
								</td>
							

						
							</tr>
							</div>


							<%-- 										<div id="<s:property value="narrativeName"/>Declined" --%>
							<!-- 											class="col-lg-1"> -->
							<%-- 											<span class="glyphicon glyphicon-remove-sign text-danger"></span> --%>
							<!-- 										</div> -->
							<%-- 								<div id="<s:property value="narrativeName"/>Declined" class="col-lg-1"> --%>
							<%--    						            <span  class="glyphicon glyphicon-remove-sign text-danger"></span> --%>
							<!--    						            </div>  -->


							</div>

						</s:iterator>

					</table>
                   </s:form>
		</div>
	</div>
	</div>
	</div>
</body>
</html>