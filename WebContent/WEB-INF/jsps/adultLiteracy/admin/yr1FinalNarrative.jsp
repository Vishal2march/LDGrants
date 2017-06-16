<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<!-- tinymce text editor -->
<script type="text/javascript" src="jscripts/tinymce/tinymce.js"></script>
    <script type="text/javascript">
    tinymce.init({
         selector: "textarea",
         menubar: false,
         toolbar: "bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent",
         statusbar: false,
         nowrap: false,
         width: 600
     });
  </script>


<script type="text/javascript">
    $(document).ready(function () {
        
    	//call struts2 action when user clicks on bootstrap pill
    	$('body').on('click', "[data-toggle='pill']", function(e) {
    		var myhref = this.getAttribute("href");
	    	location.href = this.getAttribute("href");
	    	
	    	//set this button/pill as the active one
	    	//$("li.menu3").addClass("active");
    	});
    	    	    	
    });
</script>
</head>
<body>


<div class="container">

<div class="panel panel-default">
	<div class="panel-heading">
	    <h2>Adult Literacy Admin Final Narratives</h2>
	</div>
	<div class="panel-body">
	
		<!-- use bootstrap "pills" vertical buttons for narrative menu list -->
		<ul class="nav nav-pills nav-stacked">
		
		<!-- loop on all final narrative types -->
		<s:iterator value="narrativeTypeList" status="row">
			
			<s:url var="narrativeUrl" action="literacySelectFinalNarrativeAdmin">
				<s:param name="narrativeTypeId" value="narrativeTypeId"/>
			</s:url>
		
			<li class="menu<s:property value='#row.count'/>">
				<a data-toggle="pill" href='<s:property value="narrativeUrl"/>'>
					<s:property value="displayName"/> 
				</a>
			</li>
		
		</s:iterator>
		</ul>	
	</div>
</div>





<div class="panel panel-default">
	<div class="panel-body">
	
	
	<%--only show narrative update form if user selected a narrativeTypeId --%>
	<s:if test="selectedNarrative.narrativeTypeId>0">
		
		<h4><s:property value="selectedNarrative.narrativeType.displayName" /></h4>
		<s:property value="selectedNarrative.narrativeType.displayInstruction"/>
		
		<br/><br/><br/>		
		
		
		
		<s:form action="literacySaveFinalNarrativeAdmin" cssClass="form-vertical">
				
			<s:hidden name="selectedNarrative.id"/>
			<s:hidden name="selectedNarrative.narrativeTypeId"/>
				
			<%-- user with no admin access --%>
			<s:if test="%{#session.user.adultLiteracyAdminAccess==null}">
				<div class="row" id="errorM">			
						<div class="alert alert-danger">
							<strong> <span class="glyphicon glyphicon-alert" 
							role="alert"></span>  Error:
							</strong> User does not have "Edit" permissions.
						</div>			
				</div>
						
				<fieldset disabled>
					<label for="selectedNarrative.narrative" value="Narrative"/>
					<s:textarea name="selectedNarrative.narrative" id="selectedNarrative.narrative" cssClass="form-control" disabled="true" />
				
					<s:submit value="Save" id="submitBtn" name="submitBtn" cssClass="btn btn-primary" disabled="true"/>
				</fieldset>
			</s:if>
			
					    
		    <%-- allow update of narrative --%>
			<s:else>
				<div class="form-group required">
					<label for="selectedNarrative.narrative" value="Narrative"/>
					<s:textarea name="selectedNarrative.narrative" id="selectedNarrative.narrative" cssClass="form-control"/>
				</div>
				<s:submit value="Save" id="submitBtn" name="submitBtn" cssClass="btn btn-primary"/>
			</s:else>
		</s:form>
		
	</s:if>
	
	</div>
</div>


</body>
</html>