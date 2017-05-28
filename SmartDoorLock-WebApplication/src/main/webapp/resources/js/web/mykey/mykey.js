// 검색 데이터가 저장될 전역 변수
var searchData
$(function() {
	
	$("#key_detail").hide();
	
	searchData = load_keyList();
//	console.log(JSON.stringify(searchData));

	/*키 클릭시 상세정보 데이터 뿌리는 ajax*/
	$('#key_list').delegate("li", "click", function() {

		var data = {};
		data.key_id = $(this).find('.key_id').text();
		data.serial_no = $(this).find('.serial_no').text();
		console.log
		$.ajax({
			type : "post",
			url : "/" + $("#login_email").val() + "/mykey/detail",
			dataType : "json",
			cache : false,
			async : true, //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : data,
			success : function(res) {
				console.log(res);
				$("#key_name").val(res.key_name);
				$("#key_name").removeAttr("disabled");
				$("#email").val(res.email);
				$("#grade_name").val(res.grade_name);
				$("#serial_no").val(res.serial_no);
				$("#crt_dt").val(res.crt_dt);

				var temp = res.start_date + " ~ " + res.expire_date;
				$("#date").attr("value", temp);
				
				$("#key_detail").show();
			}
		});

	});

	/* 사용자 정의 관련 ajax */
	$("#key_name").keydown(function(key){

		if (key.keyCode == 13) {// 키가 13이면 실행 (엔터는 13)
			var data = {}
			data.key_name = $('#key_name').val();
			data.serial_no = $('#serial_no').val();
			data.email = $('#email').val();

			$.ajax({
				type : "post",
				url : "/" + $("#login_email").val() + "/mykey/keyName/update",
				dataType : "json",
				cache : false,
				async : true, // true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
				data : data,
				success : function(res) {
					if (res.AJAX_RESULT == "AJAX_SUCCESS") {
						//console.log("바꾼 이름 : " + data.key_name);
						alert("이름 수정함")
						$("#key_name").val(data.key_name);
						
						//바뀐 데이터 다시 json 저장
						searchData = load_keyList("update");
						
						// 검색한 상태에서 이름 바꿀시 검색 리스트상태 유지를 위해
						// 검색창에 입력 한 값이나 바꾼 키값으로 다시 검색한다.
						// 등급이나 빈칸인 상태에서 이름 바끌때
						if($('#search_value').val()=="마스터"||$('#search_value').val()=="매니저"
									||$('#search_value').val()=="사용자" || $('#search_value').val()==""){
							update_val = $('#search_value').val();
						} else{
							// 이름으로 검색한 후 키 이름 바꿨을때 리스트 상태 유지
							var update_val = $("#key_name").val();
						}
						
						search_fun(update_val);
					
					} else {
						alert("이름 변경에 실패하였습니다.");
					}
				}
			});

		}
	});

	
	
	// 검색 관련 ajax // 버튼클릭시
	$('#search_btn').click(function() {
		var search_value = $('#search_value').val();
		search_fun(search_value);
	});
	
	
	// 엔터키로 검색
	$("#search_value").keydown(function(key){
		if (key.keyCode == 13) {// 키가 13이면 실행 (엔터는 13)
			var search_value = $('#search_value').val();
			search_fun(search_value);
		}
	});
	
	

		
	$('#deleteMyKey').click(function() {

		var check = confirm("정말 키를 삭제 하시겠습니까?");

		if (check) {
			var data = {
				grade_name : $("#grade_name").val(),
				serial_no : $("#serial_no").val(),
				email : $("#login_email").val()
			};

			if (data.grade_name != "마스터") {
				$.ajax({
					type : "post",
					url : "/" + $("#login_email").val() + "/mykey/delete",
					dataType : "json",
					data : data,
					success : function(object) {
						alert('키 삭제 성공');
						location.reload(true);
					},
					error : function(req, stat, err) {
						alert("req:" + req + "\nstat:" + stat + "\nerr" + err);
					}
				});

			} else {
				alert("마스터 키는 도어락관리에서 삭제해 주세요");
			}

		}

	});

});



// 페이지가 로딩되면 검색을 위한 데이터를 모두 가져와 json으로 저장 및 리스트에 뿌린다.
var load_keyList = function(str) {
	var temp;
	$.ajax({
		type :"POST",
		url : "/" + $("#login_email").val() + "/mykey",
		dataType : "json",
		cache : false,
		async : false, //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
		success : function(res) {
			temp = res;
			$("#key_list").empty();
			
			if(str != "update"){
				$.each(res, function(key, object){             
	
					$("#key_list").append(
						"<li class='row'>"
						+"<div class='col-xs-9 col-sm-10 col-md-9 col-lg-10'>"	
						+"<br /> <span> <span class='attr'\>열쇠 이름 : </span>"
						+"<span class='value'>"+object.key_name+"</span><br /></span>"
						+"<span> <span class='attr'>기간 : </span>" 
						+"<span class='value'>"+ object.start_date +" ~ " + object.expire_date +"</span><br /></span>"
						+"<span><span class='attr'>상태 : </span>" 
						+"<span class='value'>"+object.state_name+"</span> <br /></span>"
						+"<span><span class='attr'>등급 : </span>" 
						+"<span class='value'>"+object.grade_name+"</span> <br /><br /></div>"
						+"<div class='key_id' style='display:none'>"+object.key_id+"</div>"
						+"<div class='serial_no' style='display:none'>"+object.serial_no+"</div>"
						+"<div style='padding: 20px 20px 0px 0px;' class='image_right'>" 
						+"<i class='fa fa-4x fa-key'></i></div></li>"
					);		
				});
			}
		}
	});
	return temp;
}

var search_fun = function(search_value){
	
	$("#key_list").empty();
	$('#search_value').val(search_value);
	
	// 검색어가 없을때 전체 리스트 출력
	if(search_value == ""){
		searchData = load_keyList();
	} else{
		
		// 검색어가 있는지 json을 뒤져 있는거만 리스트에 뿌린다. 이름 , 등급으로 검색 가능
		for(var i in searchData){
			var object = searchData[i];
			if(object.key_name == search_value || object.grade_name == search_value){
				$("#key_list").append(
					"<li class='row'>"
					+"<div class='col-xs-9 col-sm-10 col-md-9 col-lg-10'>"	
					+"<br /> <span> <span class='attr'\>열쇠 이름 : </span>"
					+"<span class='value'>"+object.key_name+"</span><br /></span>"
					+"<span> <span class='attr'>기간 : </span>" 
					+"<span class='value'>"+ object.start_date +" ~ " + object.expire_date +"</span><br /></span>"
					+"<span><span class='attr'>등급 : </span>" 
					+"<span class='value'>"+object.grade_name+"</span> <br /><br /></div>"
					+"<div id='key_id' style='display:none'>"+object.key_id+"</div>"
					+"<div style='padding: 20px 20px 0px 0px;' class='image_right'>" 
					+"<i class='fa fa-4x fa-key'></i></div></li>"
				);
			}
		};
		
	}

}

