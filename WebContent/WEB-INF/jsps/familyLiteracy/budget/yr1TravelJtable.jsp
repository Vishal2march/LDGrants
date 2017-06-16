<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Literacy Year 1 Travel</title>
<script src="jscripts/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="jscripts/jquery-ui-1.10.0.min.js" type="text/javascript"></script>
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css">

<!-- Include one of jTable styles. -->
<link href="jscripts/jtable.2.4.0/themes/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
 
<!-- Include jTable script file. -->
<script src="jscripts/jtable.2.4.0/jquery.jtable.js" type="text/javascript"></script>

<!-- Include validation css/js for use with jtable -->
<link href="jscripts/jtable.2.4.0/validationEngine.jquery.css" rel="stylesheet" type="text/css">
<script src="jscripts/jtable.2.4.0/jquery.validationEngine.js" type="text/javascript"></script>
<script src="jscripts/jtable.2.4.0/jquery.validationEngine-en.js" type="text/javascript"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#TravelTableContainer').jtable({
            title: 'Family Literacy - Travel Year 1',
            footer: true,
            actions: {
                listAction: 'literacyYr1TravelList.action',
                createAction: 'addUpdateLiteracyTravelRow.action',
                //updateAction is supposed to only return "result", but reusing addupdate action seems to work ok
                updateAction: 'addUpdateLiteracyTravelRow.action',
                deleteAction: 'deleteLiteracyTravelRow.action'
            },
            fields: {            
                id: {
                    key: true,
                    list: false
                },
                description: {
                    title: 'Description',
                    inputClass: 'validate[required,maxSize[500]]'
                },
                purpose: {
                    title: 'Purpose',
                    inputClass: 'validate[required,maxSize[500]]'
                },                
                costSummary: {
                    title: 'CalculationOfCost',
                    inputClass: 'validate[required,maxSize[100]]'
                }, 
                travelPeriod: {
                    title: 'DatesOfTravel',
                    inputClass: 'validate[required,maxSize[30]]'
                }, 
                travelerName: {
                    title: 'NameOfTraveler',
                    inputClass: 'validate[required,maxSize[100]]'
                },     
                expenseSubmitted: {
                    title: 'ActualExpense',
                    inputClass: 'validate[required,custom[onlyNumberSp]]',
                    footer: function( data ){
		                var total = 0;
		                $.each(data.Records, function(index, record){
		                    total += Number(record.expenseSubmitted);
		                });
		               if (isNaN(total)){
		                	total =0;
		                }
		                return( '$'+total.toLocaleString("en"));
            		}        
                },             
                expenseApproved: {
                    title: 'ExpenseApproved',
                    create: false,
                    edit: false,
                    footer: function( data ){
		                var total = 0;
		                $.each(data.Records, function(index, record){
		                    total += Number(record.expenseApproved);
		                });
		                if (isNaN(total)){
		                	total =0;
		                }
		                return( '$'+total.toLocaleString("en"));
            		}        
                }                               
            },
            //Initialize validation logic when a form is created
            formCreated: function (event, data) {
                data.form.validationEngine();
            },
            //Validate form when it is being submitted
            formSubmitting: function (event, data) {
                return data.form.validationEngine('validate');
            },
            //Dispose validation logic when form is closed
            formClosed: function (event, data) {
                data.form.validationEngine('hide');
                data.form.validationEngine('detach');
            },
            //to disable add/edit/delete button when page should be locked
            recordsLoaded: function(event, data) {			  
			  var myDisable = $('#disableTheForm').attr("value");
			  if (myDisable=='true'){
			     $('#TravelTableContainer').find('.jtable-command-button.jtable-edit-command-button').remove();
			  	 $('#TravelTableContainer').find('.jtable-command-button.jtable-delete-command-button').remove();
			     $('#TravelTableContainer').find('.jtable-toolbar-item.jtable-toolbar-item-add-record').remove();
			  }
			}
        });
                
        $('#TravelTableContainer').jtable('load');  
        
        //set the active bootstrap pill (the budget tab button for travel)
    	$("li.menu4").addClass("active");
    	
    	//call struts2 action when user clicks on bootstrap pill
    	$('body').on('click', "[data-toggle='pill']", function(e) {
	    	var myhref = this.getAttribute("href");
	    	location.href = this.getAttribute("href");
    	});      
    });
</script>
</head>
<body>


<div class="container">

  
<div class="panel panel-default">
  <div class="panel-body">
  <b>Travel Expenses (Code 46)</b><br/>
  List specific project expenses that relate to Travel.  
  All expenses listed in this section must be fully described in the Application Narrative.  
 
  <br/><br/>
  <b>Travel Initial Application Total: $<s:property value="%{getText('{0,number,integer}', {initialCategoryTotal})}"/></b>
  (If the amount spent for this budget category differs by 10% or $1,000 (whichever is less) from the application's budgeted amount, listed here, an FS-10a should be submitted to DLD by mid-May.)
 
  <br/><br/>
  <b>Total Appropriation for Year 1: $<s:property value="%{getText('{0,number,integer}', {allocationYearBean.finalRecommend})}"/></b>
 
 	<br/><br/>  
 	<%-- user with readonly access --%>
	<s:if test="%{#session.user.familyLiteracyAccess=='READ'}">
		<s:set var="disableForm">true</s:set><%--set form disabled --%>
		
		<div class="row" id="errorM">			
				<div class="alert alert-danger">
					<strong> <span class="glyphicon glyphicon-alert" 
					role="alert"></span>  Error:
					</strong> User does not have "Edit" permissions.
				</div>			
		</div>
	</s:if>					
	<%-- page is locked --%>
	<s:elseif test="%{grantStatus.allowSubmitFinal==false}">
		<s:set var="disableForm">true</s:set><%--set form disabled --%>
	
        <div class="row" id="errorM">			
				<div class="alert alert-warning">
					<strong> <span class="glyphicon glyphicon-alert" 
					role="alert"></span>  Warning:
					</strong> Application has been submitted and is locked.
				</div>			
		</div>      
	</s:elseif>
	<s:else><%--form is enabled --%>
		<s:set var="disableForm">false</s:set>
	</s:else>
	
	<div id="disableTheForm" value='<s:property value="disableForm"/>'/>	
 </div>
</div>
 
 
 
  <div id="TravelTableContainer"></div>



</div>
 

</body>
</html>