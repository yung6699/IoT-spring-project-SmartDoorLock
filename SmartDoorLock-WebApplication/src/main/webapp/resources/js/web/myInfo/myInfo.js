function updateDelete(){
	var dataForm = {
			member_id: $("#submitForm input[name=member_member_id]").val(),
			email : $("#submitForm input[name=member_email]").val(),
			password:$("#submitForm input[name=member_password]").val(),
			password_re:$("#submitForm input[name=member_password_re]").val(),
			name:$("#submitForm input[name=member_name]").val(),
			phone_num:$("#submitForm input[name=member_phone_num]").val()
	};
	
	if(confirm("계정을 정말 삭제하시겠습니까?")){
		alert("submit!");
		$.ajax({
			type : "POST",
			url : "/"+dataForm.email+"/myInfo/update/delete.do",
			cache : false,
			async : false,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : dataForm,
			dataType : "json",
			success : function(response) {
				console.log(response);
				if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
					location.replace('/');
				}else{
					alert(response.RESULT_MSG);
				}
			},
			error : function(request, status, errorThrown) {
				console.log(request);
				console.log(status);
				console.log(errorThrown);
			}
		});
	}else{
		alert("회원탈퇴를 취소 하였습니다. 하지마라영 ㅠ 유유 ");
	}
}
function updateSubmit(){
	var dataForm = {
			member_id : $("#submitForm input[name=member_member_id]").val(),
			email : $("#submitForm input[name=member_email]").val(),
			password:$("#submitForm input[name=member_password]").val(),
			password_re:$("#submitForm input[name=member_password_re]").val(),
			name:$("#submitForm input[name=member_name]").val(),
			phone_num:$("#submitForm input[name=member_phone_num]").val()
	};
	if(dataForm.password === dataForm.password_re && dataForm.password !=""){
		$.ajax({
			type : "POST",
			url : "/"+dataForm.email+"/myInfo/update/submit.do",
			cache : false,
			async : false,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : dataForm,
			dataType : "json",
			success : function(response) {
				console.log(response);
				alert(response.RESULT_MSG);
				if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
					location.replace('/'+dataForm.email);
				}
			},
			error : function(request, status, errorThrown) {
				console.log(request);
				console.log(status);
				console.log(errorThrown);
			}
		});
	}else{
		alert("비밀번호를 확인해주세요.");
	}
}