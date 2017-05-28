var key_info;
var session;
$(function(){
		// 키 상세정보 페이지 헤더에 제목 표시 및 해당 도어락의 시리얼 넘버를 가져옴
		$('#keyDetail_page').on("pagebeforeshow", function(){

			key_info = JSON.parse(window.localStorage.getItem('KEY_INFO'));
			$('#key_name_D').html(key_info.key_name);
			$('#grade_name_D').html(key_info.grade_name);
			$('#serial_no_D').html(key_info.serial_no);
			$('#crt_dt_D').html(key_info.crt_dt);
			$('#crt_email_D').html(key_info.crt_email);
			var temp = key_info.start_date + '  ~  ' + key_info.expire_date;
			$('#key_date').html(temp);

			// 삭제 버튼 보이는 여부
			if(key_info.grade_name != '마스터' ){
				$('#delete').show();	
			} else{
				$('#delete').hide();	
			}
			
		});

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 키 이름을 사용자가 정의 하고 싶은대로  변경 한다.
	$('#key_name_D').bind('click', function(){
		
		var definedName = prompt("키 이름을 지어주세요.", $("#key_name_D").text());

			// 빈칸이면 기존 키 이름 유지한다.
			if(definedName == "" || definedName == null ){
				$("#key_name_D").html(key_info.key_name);
			} else {
				key_info.key_name = definedName;
				window.localStorage.setItem('KEY_INFO', JSON.stringify(key_info)); //이름 바꾸면 세션 갱신
				$("#key_name_D").html(key_info.key_name);

				var session = JSON.parse(window.localStorage.getItem('SESSION'));
				// 어느 도어락의 어떤 맴버의 키의 이름이 바뀌었는지 질의의 조건에 들어갈 파라미터를 정의
				var data = {
					key_id : key_info.key_id,
					serial_no : key_info.serial_no,
					member_id :session.MEMBER_ID,
					email : session.EMAIL,
					key_name : key_info.key_name
				};

				$.ajax({
					type : "post",
					url : GLOBAL_HOST+"/app/mykey/name/update",
					cache : false,
					async : true,
					dataType : "json",
					data : data,
					success : function(res){
						if(res.AJAX_RESULT == "AJAX_SUCCESS")
							alert("이름이 변경되었습니다.");
						else{
							alert("이름 변경에 실패하였습니다.");
							$("#key_name_D").html(key_info.key_name);
						}
					}
				});
			}	

		});

		// 본인키 삭제 함수
	$('#delete').on('click', function(){
			session = JSON.parse(window.localStorage.getItem('SESSION'));
			if (confirm("키를  삭제하시겠습니까??") == true){  
				var data ={
						crt_email : key_info.crt_email, // 삭제 했다는 것을 알려줄 이메일
						email : session.EMAIL,
						serial_no: key_info.serial_no,
						key_id : key_info.key_id,
						member_id : session.MEMBER_ID
					};

					$.ajax({
						type : "post",
						url : GLOBAL_HOST+"/app/mykey/delete",
						cache : false,
						async : true,
						dataType : "json",
						data : data,
						success : function(res){
							if(res.AJAX_RESULT == "AJAX_SUCCESS"){
								alert("삭제 되었습니다.");	
								history.back(-1);
								localStorage.removeItem('KEY_INFO');
							}
						}
				});
			}
		});

});