<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
.breadcrumb>.active {
    color: #494949;
}
</style>
</head>

<body>
	<div class="row">
		<input type="hidden" value="<s:property value="id"/>" id="grantid" />
		<div class="col-lg-offset-1 col-lg-10">

			<div class="panel panel-primary">
				<div class="panel-heading ">
				<h4 lead>	Project:&nbsp; 03<s:property value="#session.grant.fcCode" /> -<s:property
								value="#session.grant.fyCode" /> -<s:property
								value="#session.grant.projSeqNum" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:property	value="#session.institution.popularName" />	
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:property value="#session.grant.name" />		</h4>						
				</div>
				
				<ol class="breadcrumb">
				<s:if test="#session.breadcrumbs!=null">
					<s:iterator value="#session.breadcrumbs" status="rowstatus">					
						<s:if test="#rowstatus.last == true"><%--last item in list does not have link; just title --%>
							<li class="active"><s:property value="displayName"/></li>
						</s:if>
						<s:else>
							<li><a href='<s:property value="url"/>'><s:property value="displayName"/></a></li>
						</s:else>
					</s:iterator>
				</s:if>								
				</ol>
			</div>
			
			
		</div>
	</div>
</body>
</html>