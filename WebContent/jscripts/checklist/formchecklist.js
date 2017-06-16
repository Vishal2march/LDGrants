$(document).ready(function() {

	narrativeformstatus();

});

function narrativeformstatus() {

	$.ajax({
		type : "POST",
		url : "narrativeformstatus.action",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
				$.each(data.statusMap, function(key, value) {
					var n = value.localeCompare("Saved");
				if (value != null && n == 0) {
					$("#" + key + "saved").show();
					$("#" + key + "incomplete").hide();
				} else {
					
					$("#" + key + "incomplete").show();
					$("#" + key + "saved").hide();
				}
			});
		},
		error : function() {
			alert("Failed");

		}
	});
	
}

