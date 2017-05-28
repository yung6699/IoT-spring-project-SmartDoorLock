var session;

$(function(){

	$('#mykeyList_page').on("pagebeforeshow",function(){

		if(localStorage.getItem('NFCkey')!=null){
			localStorage.removeItem('NFCkey');
		}

		if(localStorage.getItem('KEY_INFO')!=null){
			localStorage.removeItem('KEY_INFO');
		}

		session = JSON.parse(window.localStorage.getItem('SESSION'));
		var data = { member_id : session.MEMBER_ID };

		// 내가 등록된 키 정보를 가져온다.
		var master = '<img src="../../img/mykey/master_black.svg" id="master"/>',
		manager = '<img src="../../img/mykey/manager_black.svg" id="manager"/>',
		user = '<img src="../../img/mykey/user_black.svg" id="user"/>',
		img;

		$.ajax({
			type : "post",
			url : GLOBAL_HOST+"/app/mykey/list",
			dataType : "json",
			cache : false,
			async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : data,
			success : function(res){
				$("#mykeyList").empty();
				$.each(res, function(key, object){

					if(object.grade_name=='마스터'){
						img=master;
					}else if(object.grade_name=='매니저'){
						img=manager;
					}else{
						img=user;
					}

					$("#mykeyList").append(
						"<li><a href='#'>"
						+img
						+"<h3 id='key_name'>"+object.key_name+"</h3>" /*열쇠 이름*/
						+"<div style='display:none' id='key_id'>"+object.key_id+"</div>" /*열쇠 난수 */
						+"<div style='display:none' class='bluetooth_id'>"+object.bluetooth_id+"</div>" /*열쇠 난수 */
						+"<div style='display:none' id='grade_name'>"+object.grade_name+"</div>" /*열쇠 등급*/
						+"<div id='serial_no'>"+object.serial_no+"</div>" /* 열쇠가 등록된 도어락 시리얼*/
						+"<div style='display:none' id='crt_dt'>"+object.crt_dt+"</div>" /*열쇠 부여 날짜*/
						+"<div style='display:none' id='crt_email'>"+object.crt_email+"</div>" /*열쇠를 부여한 사람 이메일*/
						+"<div style='display:none' id='start_date'>"+object.start_date+"</div>"
						+"<div style='display:none' id='expire_date'>"+object.expire_date+"</div>"
						+"</a></li>"
					);
					
					$("#mykeyList").listview("refresh");
				});

			} , error:function(){
				alert("error");
			}
		});
	});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 리스트의 요소 롱클릭시 특정 목록의 키와 도어락 정보를 추출하여 로컬에 저장한다.
	$('#mykeyList').delegate("li","click",function() { 
		var temp = {
			key_name :  $(this).find("#key_name").text(),
			key_id :  $(this).find("#key_id").text(),
			grade_name: $(this).find("#grade_name").text(),
			serial_no : $(this).find("#serial_no").text(),
			crt_dt : $(this).find("#crt_dt").text(),
			crt_email :  $(this).find("#crt_email").text(),
			start_date:$(this).find("#start_date").text(),
			expire_date:$(this).find("#expire_date").text()
		};

		window.localStorage.setItem('KEY_INFO', JSON.stringify(temp));
		location.href='keyDetail.html#keyDetail_page';
	});


	//리스트의 요소 클릭시 NFC 페이지로 넘어가서 열수있다.
	$('#mykeyList').delegate("li","taphold",function() { 
		var key_id =  $(this).find("#key_id").text(),
		      bluetooth_id = $(this).find(".bluetooth_id").text();	

	             console.log("bluetooth_id : " + bluetooth_id);	
                         if (confirm("문을 열겠습니까?") == true){    //확인
                   	     app.initialize(key_id, bluetooth_id);
                          }else{   //취소
                    	     return;
                         }
	});

	// 모바일 백 버튼이 눌릴시 발생하는 이벤트
	document.addEventListener("backbutton", onBackKeyDown, false);

/***************************************************************************************************************/
});  // 즉시실행함수 끝

var onBackKeyDown = function(){
	location.replace('../main/main.html');
}