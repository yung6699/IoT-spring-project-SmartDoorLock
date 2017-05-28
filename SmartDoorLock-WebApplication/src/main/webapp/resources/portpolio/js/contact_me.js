function sendMessage() {
	var name = $("#name").val();
	var email = $("#email").val();
	var tel = $("#tel").val();
	var message = $.trim($("#message").val());

	var tel_reg = /[0-9]{11}/;
	var email_reg = /[\w-_.]+@[\w-_].[\w-_.]/g;

	if (email_reg.test(email)) {
		if (tel_reg.test(tel)) {
			if (name.length >= 2) {
				if(message.length >= 10){
					var dataForm = {
						name : name,
						tel : tel,
						email : email,
						message : message
					};

					console.log(dataForm);
					$.ajax({
						url : "/message",
						type : "POST",
						data : dataForm,
						cache : false,
						async : false,
						success : function() {
							// Success message
							$('#success').html("<div class='alert alert-success'>");
							$('#success > .alert-success')
									.html(
											"<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
									.append("</button>");
							$('#success > .alert-success').append(
									"<strong>Your message has been sent. </strong>");
							$('#success > .alert-success').append('</div>');

							//clear all fields
							$("a[data-toggle=\"tab\"]").click(function(e) {
								e.preventDefault();
								$(this).tab("show");
							});

							/*When clicking on Full hide fail/success boxes */
							$('#name').focus(function() {
								$('#success').html('');
							});
						},
						error : function() {
							// Fail message
							$('#success').html("<div class='alert alert-danger'>");
							$('#success > .alert-danger')
									.html(
											"<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
									.append("</button>");
							$('#success > .alert-danger')
									.append(
											"<strong>Sorry "
													+ name
													+ ", it seems that my mail server is not responding. Please try again later!");
							$('#success > .alert-danger').append('</div>');
							//clear all fields
						}
					});

				}else{
					alert("[문의메세지] 내용은 10글자 이상 입력 해주세요");
				}
			} else {
				alert("[문의메세지] 이름은 3글자 이상 입력 해주세요");
			}
		} else {
			alert("[문의메세지] 전화번호는 11글자 이상 입력 해주세요");
		}
	} else {
		alert("[문의메세지] 이메잏ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ5글자 이상 입력 해주세요");
	}
}
