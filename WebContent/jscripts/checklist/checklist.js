$(document).ready(function() {

	isForm();

});

function isForm() {
	var grantId = $('#grantid').val();
    
	$.ajax({
		type : "POST",
		url : "isform.action?id=" + grantId,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			$.each(data.isFormSubmitted, function(key, value) {

				if (value == true) {
					$("#" + key + "Submitted").show();

				} else if (value == false) {
					$("#" + key + "Submitted").hide();

				}
			});
			$.each(data.isFormApproved, function(key, value) {
				if (value == true) {
					$("#" + key + "Approved").show();

				} else if (value == false) {
					$("#" + key + "Approved").hide();

				}

			});
			$.each(data.isFormDenied, function(key, value) {
				if (value == true) {
					$("#" + key + "Denied").show();

				} else if (value == false) {
					$("#" + key + "Denied").hide();

				}

			});
			$.each(data.isFormDeclined, function(key, value) {
				if (value == true) {
					$("#" + key + "Declined").show();

				} else if (value == false) {
					$("#" + key + "Declined").hide();

				}
			});
		},
		error : function() {
			alert("Failed");

		}
	});

}

