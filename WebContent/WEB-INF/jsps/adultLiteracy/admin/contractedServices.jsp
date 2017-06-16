<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
    
        $('#ContractedServicesTableContainer').jtable({
            title: 'Adult Literacy Admin - Purchased Services Year 1',
            footer: true,
            actions: {
                listAction: 'literacyYr1ContractedServicesList.action',
                createAction: 'addUpdateLiteracyContractedServicesRowAdmin.action',
                //updateAction is supposed to only return "result", but reusing addupdate action seems to work ok
                updateAction: 'addUpdateLiteracyContractedServicesRowAdmin.action',
                deleteAction: 'deleteLiteracyContractedServicesRow.action'
            },
            fields: {            
                id: {
                    key: true,
                    list: false
                },
                serviceType: {
                    title: 'ServiceType',
                    inputClass: 'validate[required,maxSize[50]]'
                },
                recipient: {
                    title: 'Consultant/Vendor',
                    inputClass: 'validate[required,maxSize[50]]'
                },                
                serviceDescription: {
                    title: 'ServiceDescription',
                    inputClass: 'validate[required,maxSize[100]]'
                },     
                expenseSubmitted: {
                    title: 'ActualExpense',
                    inputClass: 'validate[required,custom[onlyNumberSp]]',
                    display: function (data) {
                            return '$'+ data.record.expenseSubmitted;
                    },
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
            }
        });
        
        //load the jtable        
        $('#ContractedServicesTableContainer').jtable('load');  
                  	
    	//set the active bootstrap pill (the budget tab button for contracted services)
    	$("li.menu1").addClass("active");
    	
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
  
	  <b>Purchased Services (Code 40)</b><br/>
	  List all services to be purchased for the project by service type (ie. consultants, 
	  rentals, tuition, printing, communications, and other contractual services).  Attach per 
	  diem rate for consultants, cost estimates, bids, or other supporting data.    
	  <br/><br/>
	  Consultant Services include professional and technical advice that will be provided by 
	  individuals or groups of individuals.  Consultants are normally retained for a short period
	  to provide advice about specific aspects of the project.  Consultants are normally expected
	  to provide a report of their activities, usually at a time agreed upon before the 
	  consultancy begins.  Provide the number of days the consultant is being hired for and their
	  daily rate.<br/><br/>
	
	  Contracted Services include professional or technical activities that will be performed by 
	  commercial vendors or qualified individuals.  Contractual services are normally used for 
	  project activities that cannot be carried out by the institution, or for those activities 
	  that can be more economically performed by firms or individuals specializing in a particular
	  service.<br/><br/>
	  <b>NOTE:</b> You must explain any changes to the project budget in the "Budget Changes Year 1"
	  section of the Final Narrative.
	  	  
	  <br/><br/>
	  <b>Purchased Services Initial Application Total: $<s:property value="%{getText('{0,number,integer}', {initialCategoryTotal})}"/></b>
	  (If the amount spent for this budget category differs by 10% or $1,000 (whichever is less) from the application's budgeted amount, listed here, an FS-10a should be submitted to DLD by mid-May.)

	  <br/><br/>
	  <b>Total Appropriation for Year 1: $<s:property value="%{getText('{0,number,integer}', {allocationYearBean.finalRecommend})}"/></b>


	<br/><br/>  
 	<%-- user with no admin access --%>
	<s:if test="%{#session.user.familyLiteracyAdminAccess==null}">
		<s:set var="disableForm">true</s:set><%--set form disabled --%>
		
		<div class="row" id="errorM">			
				<div class="alert alert-danger">
					<strong> <span class="glyphicon glyphicon-alert" 
					role="alert"></span>  Error:
					</strong> User does not have "Edit" permissions.
				</div>			
		</div>
	</s:if>					
	
	<s:else><%--form is enabled --%>
		<s:set var="disableForm">false</s:set>
	</s:else>
	
	</div>
</div>





<div id="ContractedServicesTableContainer"></div>





</div>

</body>
</html>