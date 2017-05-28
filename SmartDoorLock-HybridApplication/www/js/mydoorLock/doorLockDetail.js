var doorLock_info;
var session;

$(function(){

	if(localStorage.getItem('MEMBER_INFO')!=null ){
		localStorage.removeItem('MEMBER_INFO');
	}

	// 키 상세정보 페이지 헤더에 제목 표시 및 해당 도어락의 시리얼 넘버를 가져옴
	$('#doorLockDetail_page').on("pagebeforeshow",function(){
		
		doorLock_info = JSON.parse(window.localStorage.getItem('DOORLOCK_INFO'));
		$("#doorlock_name_D").html(doorLock_info.doorlock_name);
		$("#master_name_D").html(doorLock_info.master_name);
		$("#master_email_D").html(doorLock_info.master_email);
		$("#crt_dt_D").html(doorLock_info.crt_dt);
		$("#installed_place_D").html(doorLock_info.installed_place);
		$("#serial_no_D").html(doorLock_info.serial_no);

		session = JSON.parse(window.localStorage.getItem('SESSION'));
				// 삭제 버튼 보이는 여부
		if(doorLock_info.master_email == session.EMAIL){
			$('#delete').show();	
		} else{
			$('#delete').hide();	
		}

		var data= { 
				serial_no : doorLock_info.serial_no ,
				email:session.EMAIL,
				member_id:session.MEMBER_ID
		};

		$.ajax({
			type : "post",
			url : GLOBAL_HOST+"/app/mydoorlock/key",
			cache : false,
			async : true,
			dataType : "json",
			data : data,
			success : function(res){
				console.log(res);
				$("#register_key_info_list").empty();
				var str ="<li data-role=\"list-divider\">등록된 열쇠<a id=\"key_register\" style=\"float: right;\">+  추가하기</a></li>";
				$.each(res, function(key, object){
					str+="<li class=\"key\">";
						str+="<span class=\"member_name\">"+object.member_name+"</span>"
						str+="<span class=\"grade_name\">"+object.grade_name+"</span>"
						str+="<div class=\"member_email\" style=\"display:none\">"+object.member_email+"</div>"
						str+="<div class=\"state\" style=\"display:none\">"+object.state+"</div>"
						str+="<div class=\"state_name\" style=\"display:none\">"+object.state_name+"</div>"
						str+="<div class=\"key_crt_dt\" style=\"display:none\">"+object.crt_dt+"</div>"
						str+="<div class=\"key_start_date\" style=\"display:none\">"+object.start_date+"</div>"
						str+="<div class=\"key_expire_date\" style=\"display:none\">"+object.expire_date+"</div>"
						str+="<div class=\"key_id\" style=\"display:none\">"+object.key_id+"</div>"
					str+="</li>"		
				});

				$("#register_key_info_list").append(str);
				$("#register_key_info_list").listview("refresh");		
			}
		});
	});


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///
	// 도어락 이름을 마스터가 정의 하고 싶은대로  변경 한다.
	$('#doorlock_name_D').bind('click', function(){
		var master_email = $('#master_email_D').text();
		console.log(' : ' + master_email);

		session = JSON.parse(window.localStorage.getItem('SESSION'));
		// 도어락 설치한 이메일과 로그인한 이메일이 같을때 즉 마스터일때이다.
		if( master_email == session.EMAIL){
			var definedName = prompt("변경하고 싶은 도어락 이름은?", $("#doorlock_name_D").text());
			// 빈칸이면 기존 도어락 이름으로 등록된다.
			if(definedName == "" || definedName == null ){
				$("#doorlock_name_D").html(doorLock_info.doorlock_name);
			} else {
				doorLock_info.doorlock_name = definedName;
				window.localStorage.setItem('DOORLOCK_INFO', JSON.stringify(doorLock_info)); //
				$("#doorlock_name_D").html(doorLock_info.doorlock_name);
					// 어느 도어락의 어떤 맴버의 키의 이름이 바뀌었는지 질의의 조건에 들어갈 파라미터를 정의
					var data = {
						serial_no : doorLock_info.serial_no,
						member_id :session.MEMBER_ID,
						doorlock_name : doorLock_info.doorlock_name
					} ;

					mydoorLockName_update(data);
			}	
		}
	});
	// 도어락 장소를 마스터가 정의 하고 싶은대로  변경 한다.
	$('#installed_place_D').bind('click', function(){
		var master_email = $('#master_email_D').text();
		session = JSON.parse(window.localStorage.getItem('SESSION'));
		// 도어락 설치한 이메일과 로그인한 이메일이 같을때 즉 마스터일때이다.
		if( master_email == session.EMAIL){
			var definedPlace = prompt("도어락 위치를 변경하세요",$("#installed_place_D").text());
			
			if(definedPlace == "" || definedPlace == null ){
				$("#installed_place_D").html(doorLock_info.installed_place);
			} else {
				doorLock_info.installed_place = definedPlace;
				window.localStorage.setItem('DOORLOCK_INFO', JSON.stringify(doorLock_info)); //
				$("#installed_place_D").html(doorLock_info.installed_place);

					// 어느 도어락의 어떤 맴버의 키의 이름이 바뀌었는지 질의의 조건에 들어갈 파라미터를 정의
					var data = {
						serial_no : doorLock_info.serial_no,
						member_id :session.MEMBER_ID,
						installed_place : doorLock_info.installed_place
					} ;

					doorLockPlace_update(data);
			}	
		}
	});


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 유저 이름 클릭시 유저 상세 정보 페이지로 넘어간다.
	//  이때 유저 이름 요소에 있는 값들을 추출하여 로컬 저장소로 저장해 넘긴다.
	$('#register_key_info_list').delegate('li.key', 'click', function(){

		//  키 상세보기는 html파일이 따로 있다.
		var member_info = {
			member_name : $(this).find(".member_name").text(),
			grade_name : $(this).find('.grade_name').text(),
			member_email :  $(this).find('.member_email').text(),
			key_crt_dt :  $(this).find(".key_crt_dt").text(),
			key_start_date : $(this).find(".key_start_date").text(),
			key_expire_date : $(this).find(".key_expire_date").text(),
			state : $(this).find(".state").text(),
			state_name : $(this).find(".state_name").text(),
			key_id : $(this).find(".key_id").text()
			
		}
		window.localStorage.setItem('MEMBER_INFO',JSON.stringify(member_info));		
		location.href="reg_key.html#reg_key_page";
	});


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	// 키 추가 버튼 클릭시 키를 추가하는 페이지로 넘어간다.
	$("#register_key_info_list").delegate("#key_register","click", function() {

		// 키를 추가시 사용자 정의도 처음에는 도어락 이름으로 등록된다.
		// 그후 사용자가 알아서 바꾸는 로직으로 구성되어 있다.
		$('#key_register').css("color", "#008CBA");
		location.href='key_Register.html#key_register_page';
	});



	$("#register_key_info_list").delegate("li.key","taphold",function(){
		
		session = JSON.parse(window.localStorage.getItem('SESSION'));
		var member_info = {
			key_id : $(this).find(".key_id").text(),
			email:session.EMAIL,
			member_name : $(this).find(".member_name").text(),
			grade_name : $(this).find('.grade_name').text(),
			member_email :  $(this).find('.member_email').text(),
			state : $(this).find(".state").text(),
			state_name : $(this).find(".state_name").text(),
			serial_no : doorLock_info.serial_no
		};
		console.log(member_info);
		console.log(session);
		if(member_info.grade_name!="마스터"){
			if(member_info.state!="GRANTING"){
				if( member_info.member_email == session.EMAIL ){
					alert("자기 자신의 열쇠는 열쇠 리스트에서 삭제해주세요.");
				} else {
					if (confirm("해당열쇠는 권한이 부여중인 열쇠입니다. 삭제를 지속하시겠습니까?") == true){  
						console.log(member_info);
						reg_key_delete(member_info);
					}
				}	
			}else{
				if (confirm("해당열쇠는 권한이 부여중인 열쇠입니다. 삭제를 지속하시겠습니까?") == true){  
					console.log(member_info);
					reg_key_delete(member_info);
				}
			}
		} 
		else{
			alert("마스터 키는 도어락 제거시 삭제가 가능합니다.");
		}
	});

	//  도어락 삭제하기 버튼 클릭시 실행된다.
	$('#delete').bind('click',function(){
		if (confirm("도어락을  삭제하시겠습니까??") == true){  
				var data = { 
					serial_no : doorLock_info.serial_no,
					email :  session.EMAIL,
					member_id:session.MEMBER_ID
				};
				console.log(data);
				$.ajax({
					type : "post",
					url : GLOBAL_HOST+"/app/mydoorlock/delete/doorlock",
					cache : false,
					async : true,
					dataType : "json",
					data : data,
					success : function(res){
						if(res.AJAX_RESULT == "AJAX_SUCCESS"){
							alert("도어락이 삭제 되었습니다.");
							history.back(-1);
							localStorage.removeItem('DOORLOCK_INFO');
						} else if(res. AJAX_RESULT == 'AJAX_FAIL'){
							alert(res.RESULT_MSG);
						} 
					}
				});
		}
	});		
});


// 도어락 이름 변경 함수
var mydoorLockName_update = function(data){
	data.member_id=session.MEMBER_ID;
	data.email=session.EMAIL;
	data.key_id=doorLock_info.key_id
	console.log(data);

	$.ajax({
		type : "post",
		url : GLOBAL_HOST+"/app/mydoorlock/name/update",
		cache : false,
		async : true,
		dataType : "json",
		data : data,
		success : function(res){
			if(res.AJAX_RESULT == "AJAX_SUCCESS")
				alert("이름이 변경되었습니다.");
			else{
				alert("이름 변경에 실패하였습니다.");
				$("#doorlock_name_D").html(doorLock_info.doorlock_name);
			}
		}
	});
}


// 도어락 장소 변경 함수
var doorLockPlace_update = function(data){
	data.member_id=session.MEMBER_ID;
	data.email=session.EMAIL;
	data.key_id=doorLock_info.key_id
	console.log(data);
	$.ajax({
		type : "post",
		url : GLOBAL_HOST+"/app/mydoorlock/location/update",
		cache : false,
		async : true,
		dataType : "json",
		data : data,
		success : function(res){
			if(res.AJAX_RESULT == "AJAX_SUCCESS")
				alert("장소가 변경되었습니다.");
			else{
				alert("장소 변경에 실패하였습니다.");
				$("#installed_place_D").html(doorLock_info.installed_place);
			}
		}
	});
}


// 키 삭제 함수
var reg_key_delete = function(data){
	console.log(data);
	
	$.ajax({
		type : "post",
		url : GLOBAL_HOST+"/app/mydoorlock/key/delete",
		cache : false,
		async : true,
		dataType : "json",
		data : data,
		success : function(res){
			if(res.AJAX_RESULT == "AJAX_SUCCESS"){
				alert("삭제 되었습니다.");	
				location.reload(true);
			}
		}
	});
	
}

