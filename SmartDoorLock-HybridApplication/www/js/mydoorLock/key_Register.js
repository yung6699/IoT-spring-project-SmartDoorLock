var grade_name;

$(function(){

	// 사용자 등록 페이지 이벤트
	$(".select-native-2").change(function(){
		grade_name = $(this).val();
	});

	var session = JSON.parse(window.localStorage.getItem('SESSION')),
	      doorLockInfo =  JSON.parse(window.localStorage.getItem('DOORLOCK_INFO'));

		//사용자 검색 이벤트
	$('.search_button').bind('click',function(){
		$(this).css('background-color','#A19F9F');

		var member_email = $('.email_input').val();
		member_email = $.trim(member_email);
		if(member_email == ""){
		     alert("검색할 이메일을 입력한 후 검색하세요");
		     $(this).css('background-color','#172d44');
		} else {
			var temp = { member_email : member_email };
			//ajax로 검색할 이메일을 보내 검색한다.
			$.ajax({
				type:"post",
				url : GLOBAL_HOST+"/app/mydoorlock/key/search/member",
				cache : false,
    				async : true,
				data : temp,
				success:function(res){
					if(res.AJAX_RESULT == 'AJAX_FAIL'){
						alert(res.RESULT_MSG);
					}  else{
						$('.email_input').val("");
						$(".email_address").val(res.member_email);
						$(".email_userName").val(res.member_name);
						$(".serial_no").val(doorLockInfo.serial_no);
						$(".member_id").html(res.member_id);
						$('.search_button').css('background-color','#172d44');
					}
				} 
			});
		}
	});


	// 키 부여 시 이벤트
	$("#keyadd_submit_button").bind('touchend',function() {
		$('.footer').css('background-color','#A19F9F');
		if(grade_name=="사용자"){
		      
		         	 var data = {},
		         	start_date = $(".start_date").val(),
			expire_date = $(".expire_date").val();

			if(start_date=="" | expire_date==""){
				alert("날짜를 설정하세요");
				$('.footer').css('background-color','#172d44');
			}
			 else{
				if(start_date > expire_date){
					alert("날짜를 다시 설정하세요");
					$('.footer').css('background-color','#172d44');						
				} else{
					data.start_date = start_date.toString();
					data.expire_date = expire_date.toString();
			
					//ajax 실행
					if( $(".member_id").text() !=""){
						// 멤버아이디 타입은 Integer다.
						data.member_id =Number($(".member_id").text());
						data.serial_no = doorLockInfo.serial_no;
						data.key_name = doorLockInfo.doorlock_name; // 키를 처음 설정시에는 도어락 이름으로
						data.grade = "MEMBER";
						data.crt_email =  session.EMAIL;
						data.updt_email =  session.EMAIL;
						data.member_email = $(".email_address").val();
						key_insert(data);
					} else {
						alert("등록할 유저를 설정하세요");
						$('.footer').css('background-color','#172d44');
					}

				}
			}
		}
		else if(grade_name=="매니저"){
		
		         	 var data = {},
		         	start_date = $(".start_date").val(),
			expire_date = $(".expire_date").val();

			data.start_date = start_date.toString();
			data.expire_date = expire_date.toString();

			// ajax 실행 검색을 했는지 여부 파악
			if( $(".member_id").text() != ""){
				data.member_id = Number($(".member_id").text());
				data.serial_no = doorLockInfo.serial_no;
				data.key_name = doorLockInfo.doorlock_name; /// 키를 처음 설정시에는 도어락 이름으로
				data.grade = "MANAGER";
				data.crt_email = session.EMAIL;
				data.updt_email =  session.EMAIL;
				data.member_email = $(".email_address").val();
				key_insert(data);
			} else {
				alert("등록할 유저를 설정하세요");
				$('.footer').css('background-color','#172d44');
			}

		} else {
			alert("등급을 설정해주세요");
			$('.footer').css('background-color','#172d44');
		}

	});
});

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///


var key_insert = function(data){
	$.ajax({
		type : "post",
		url : GLOBAL_HOST+"/app/mydoorlock/key/register",
		cache : false,
    		async : true,
		dataType : "json",
		data : data,
		success : function(res){
			if(res.AJAX_RESULT=="AJAX_SUCCESS"){
				alert("키를 등록했습니다.");
				history.back(-1);
				$(".key_setting_box").each(function() {  
					this.reset();  
				});  

				$(".key_date_setting")	.each(function() {  
					this.reset();  
				});  

			} else{
				alert(res.RESULT_MSG);
				$('.footer').css('background-color','#172d44');
			}
		}
	});
}


