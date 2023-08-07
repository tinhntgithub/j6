$(document).ready(function() {
	$(".otp-form *:input[type!=hidden]:first").focus();
	let otp_fields = $(".otp-form .otp-field"),
		otp_value_field = $(".otp-form .otp-value");
	otp_fields
		.on("input", function(e) {
			$(this).val(
				$(this)
					.val()
					.replace(/[^0-9]/g, "")
			);
			let opt_value = "";
			otp_fields.each(function() {
				let field_value = $(this).val();
				if (field_value != "") opt_value += field_value;
			});
			otp_value_field.val(opt_value);
		})
		.on("keyup", function(e) {
			let key = e.keyCode || e.charCode;
			if (key == 8 || key == 46 || key == 37 || key == 40) {
				// Backspace or Delete or Left Arrow or Down Arrow
				$(this).prev().focus();
			} else if (key == 38 || key == 39 || $(this).val() != "") {
				// Right Arrow or Top Arrow or Value not empty
				$(this).next().focus();
			}
		})
		.on("paste", function(e) {
			let paste_data = e.originalEvent.clipboardData.getData("text");
			let paste_data_splitted = paste_data.split("");
			$.each(paste_data_splitted, function(index, value) {
				otp_fields.eq(index).val(value);
			});
		});

	var timeleft = 60;
	var otpTimer = setInterval(function() {
		if (timeleft <= 0) {
			clearInterval(otpTimer);
		}
		$("#otpTimer").text(timeleft)
		timeleft -= 1;
	}, 1000);
});


