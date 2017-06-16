$(document).ready(function() {

	var grantId = $('#grantIdOne').val();

	$.ajax({
		type : "POST",
		url : "isbudgetamendmentstatus.action",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		cache : false,
		success : function(data) {
		 /**If list is empty hide element related to id**/
			if (jQuery.isEmptyObject(data.isAmendmentSubmitted) == false) {
				//if not empty loop through and unhide 
				//elements with id key and 'true' value
				$.each(data.isAmendmentSubmitted, function(key, value) {
					if (value == true) {

						$("#" + key + "Submitted").show();
					}  else {

						$("#" + key + "Submitted").hide();
					}
				});
			}
			/**Do the same for Approvals**/
			if (jQuery.isEmptyObject(data.isAmendmentApproved) == false) { //Checks if list is empty
				$.each(data.isAmendmentApproved, function(key, value) {
					if (value == true) {

						$("#" + key + "Approved").show();
					} else {
						$("#" + key + "Approved").hide();
					}
				});
			} 
		},

		error : function() {
			alert("Status retrieval failed");

		}
	});
	
	//is Locked
	//disable budget year One, Two and Three save and delete buttons
	var isLocked = $('#fs10ALocked').val();
	
	//if Locked disable every input field in budget year panel
	if(isLocked == 'true') {
		//Disable and remove click function for Add and delete buttons
		$("a#add").attr('disabled','disabled');
		$("a#add.form-control").click(function(){ return false });
		$("a#delete-btn").attr('disabled','disabled');
		$("a#delete-btn.btn.btn-md.btn-danger.form-control").click(function(){ return false });
		//disable save button
		$("button#savefs10arecords_add.btn.btn-md.btn-primary").prop('disabled',true);

	}
});