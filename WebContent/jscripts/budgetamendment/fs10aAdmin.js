$(document).ready(function() {

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
					} else {

						$("." + key + "Submitted").hide();
					}
				});
			} 

			/**Do the same for Approvals**/
			if (jQuery.isEmptyObject(data.isAmendmentApproved) == false) { //Checks if list is empty
				$.each(data.isAmendmentApproved, function(key, value) {
					if (value == true) {

						$("#" + key + "Approved").show();
					} else {
						$("." + key + "Approved").hide();
					}
				});
			} 
		},

		error : function() {
			alert("Status retrieval failed");

		}
	});
	

	

});