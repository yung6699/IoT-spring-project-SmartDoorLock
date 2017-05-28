
var session;

$(function(){

	$('#mydoorLockList_page').on("pagebeforeshow",function(){

		if(localStorage.getItem('DOORLOCK_INFO')!=null ){
			localStorage.removeItem('DOORLOCK_INFO');
		}

		session = JSON.parse(window.localStorage.getItem('SESSION'));
		
		var data = {
			member_id : session.MEMBER_ID,
			email : session.EMAIL
		}

		var master = '<img src="../../img/mydoorLock/master_black.svg" id="master"/>',
		manager = '<img src="../../img/mydoorLock/manager_black.svg" id="manager"/>',	
		img;

		$.ajax({
			type : "post",
			url : GLOBAL_HOST+"/app/mydoorlock/list",
			dataType : "json",
			cache : false,
			async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : data,
			success : function(res){
				console.log(res);
				$("#mydoorLockList").empty();
				$.each(res, function(key, object){
					if(object.grade_name=='마스터'){
						img=master;
					}else{
						img=manager;
					}

					$("#mydoorLockList").append(
						"<li style='padding-top:0.25em;padding-right:0.25em;padding-left:0.25em;' >"
						+"<a href='#'>"+img
						+"<h3 class='doorLock_name'>"+object.doorlock_name+"</h3>"
						+"<div class='serial_no'>"+object.serial_no+"</div>"
						+"<div class='key_name'>"+object.key_name+"</div>"
						+"<div class='installed_place'>"+object.installed_place+"</div>"
						+"<div style='display:none' class='doorlock_data'  data-doorlock=\'"+JSON.stringify(object)+"\'' ></div>"
						+"</a></li>"
					);
				});
				$("#mydoorLockList").listview("refresh");

			},error : function(request, status, error) {
				console.log(JSON.stringify(request));
				console.log(JSON.stringify(status));
				console.log(JSON.stringify(error));
			}
		});
	});

	// 리스트의 요소 클릭시 특정 도어락 정보를 추출하여 로컬에 저장한다.
	$('#mydoorLockList').delegate("li","click",function() { 
		console.log($(this).find('.doorlock_data').data('doorlock'));
		window.localStorage.setItem('DOORLOCK_INFO',JSON.stringify($(this).find('.doorlock_data').data('doorlock')));
		location.href='doorLockDetail.html#doorLockDetail_page';
	});
	
	// 모바일 백 버튼이 눌릴시 발생하는 이벤트
	document.addEventListener("backbutton", onBackKeyDown, false);

/***************************************************************************************************************/
});  // 즉시실행함수 끝

var onBackKeyDown = function(){
	location.replace('../main/main.html');
}