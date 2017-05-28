$(function(){

	var session = JSON.parse(localStorage.getItem('SESSION'));
	newspeedList(session.EMAIL);
	// 모바일 백 버튼이 눌릴시 발생하는 이벤트
	document.addEventListener("backbutton", onBackKeyDown, false);


/***************************************************************************************************************/
});  // 즉시실행함수 끝

var onBackKeyDown = function(){
	location.replace('../main/main.html');
}

function newspeedList(email){
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/notice",
	    	data: {email:email},
		dataType:"json",
		success : function(res){
			console.log(res);
			$("#notice_list").empty();
			if(res.length==0){
				$("#notice_list").append(
					"<li><img src='../../img/notice/NOTHING.png'>"
					+"<p>수신받은 뉴스피드 소식이 없습니다.</p>"
					+"<br><p class='ui-li-aside'>&nbsp;</p></li>"
				);
			}else{
				res.filter(function(item,index,all){
					console.log("item : " + item);
					console.log("index : " + index);
					console.log("all : " + all);
					var ImageAndReqeust = getImageMessage(item);
					console.log("ImageAndReqeust : " + ImageAndReqeust);
					console.log("ImageAndReqeust.request : " + ImageAndReqeust.request );
					$("#notice_list").append(
						"<li><img src='../../img/notice/"+ImageAndReqeust.image+"\'/>"
					          +"<div>"+ item.message+"</div>"+ImageAndReqeust.request
					          +"<br><p class='ui-li-aside'>"+item.log_date+"</p></li>"
					);
				});
			}
			$( '#notice_list' ).listview( "refresh" );
		},error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

/*
	< ajax통신으로 받아오는 값들 >
	1.	object.recv_name			받는 사람 이름
	2.	object.send_name			보낸 사람 이름
	4.	object.send_email   		보낸 사람
	5.	object.recv_email  			받는 사람
 	6.	object.state        		메시지의 형태
	7.	object.log_date     		날짜
	8.  object.serial_no			시리얼 번호
	9.  object.idx					로그 번호 		< 0 : FALSE / 1: TRUE >
	0.	object.read_flag				새로운 메시지 여부 	< 1 : 읽음	/ 0: 읽지 않음 >
*/

function bt_click_yes(idx, send_email, recv_email, serial_no){
	
	var dataForm = {
		idx : idx,
		send_email : send_email,
		recv_email : recv_email,
		serial_no : serial_no,
		answer : "ACCEPT"
	};


	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/response",
		data : dataForm,
		dataType : "json",
		success : function(response){
			if(response.AJAX_RESULT="AJAX_SUCCESS"){
				newspeedList(recv_email);			
			}
			alert(response.RESULT_MSG);
		},error:function(request,status,error){
			alert("시스템 장애 입니다. 로그를 확인해 주세요");
			console.log(request);
			console.log(status);
			console.log(error);	
		}
	});
}


function bt_click_no(idx,send_email,recv_email,serial_no){

	var dataForm={
		idx : idx,
		send_email : send_email,
		recv_email : recv_email,
		serial_no : serial_no,
		answer : "REFUSE"	
	}
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/response",
		data : dataForm,
		dataType : "json",
		success : function(response){
			if(response.AJAX_RESULT="AJAX_SUCCESS"){
				newspeedList(recv_email);			
			}
			alert(response.RESULT_MSG);
		},error:function(request,status,error){
			alert("시스템 장애 입니다. 로그를 확인해 주세요");
			console.log(request);
			console.log(status);
			console.log(error);
		}
	});
}



function getImageMessage(item){
	console.log(item);
	var image = "",
	      request ="";
	      
	switch(item.state){
		case "WEB_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			 request ="<div><button onclick=\'bt_click_yes(\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>YES</button>";
			 request+="<button onclick=\'bt_click_no(\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>NO</button></div>";
			break;
		case "APP_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			request ="<div><button onclick=\'bt_click_yes(\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>YES</button>";
			request+="<button onclick=\'bt_click_no(\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>NO</button></div>";
			break;
			break;
		case "WEB_KEY_DELETE":
			image = "KEY_DELETE.png";
			break;
		case "WEB_DOORLOCK_CREATE_MASTER":
			image = "DOORLOCK_CREATE_MASTER.png";
			break;
		case "WEB_DOORLOCK_CREATE":
			image = "DOORLOCK_CREATE.png";
			break;
		case "WEB_DOORLOCK_DELETE":
			image = "DOORLOCK_DELETE.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE":
			image = "DOORLOCK_KEY_CREATE.png";
			break;
		case "WEB_DOORLOCK_KEY_DELETE":
			image = "DOORLOCK_KEY_DELETE.png";
			break;
		case "WEB_DOORLOCK_KEY_UPDATE":
			image = "DOORLOCK_KEY_UPDATE.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_ACCEPT":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_REFUSE":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE.png";
			break;
		case "WEB_ACCOUNT_DELETE":
			image = "ACCOUNT_DELETE.png";
			break;
		case "APP_KEY_DELETE":
			image = "KEY_DELETE.png";
			break;
		case "APP_DOORLOCK_CREATE":
			image = "DOORLOCK_CREATE.png";
			break;
		case "APP_DOORLOCK_CREATE_MASTER":
			image = "DOORLOCK_CREATE_MASTER.png";
			break;
		case "APP_DOORLOCK_DELETE":
			image = "DOORLOCK_DELETE.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE":
			image = "DOORLOCK_KEY_CREATE.png";
			break;
		case "APP_DOORLOCK_KEY_DELETE":
			image = "DOORLOCK_KEY_DELETE.png";
			break;
		case "APP_DOORLOCK_KEY_UPDATE":
			image = "DOORLOCK_KEY_UPDATE.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_ACCEPT":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_REFUSE":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE.png";
			break;
		case "SUB_KEY_EXPIRE":
			image = "SUB_KEY_EXPIRE.png";
			break;
		case "HW_KEY_OPEN":
			image = "HW_KEY_OPEN.png";
			break;

			
//FAIL------------------------------------------------------------->
		case "WEB_KEY_DELETE_FAIL":
			image = "KEY_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_CREATE_MASTER_FAIL":
			image = "DOORLOCK_CREATE_MASTER_FAIL.png";
			break;
		case "WEB_DOORLOCK_DELETE_FAIL":
			image = "DOORLOCK_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE_FAIL":
			image = "DOORLOCK_KEY_CREATE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE_REQUEST_FAIL":
			image = "DOORLOCK_KEY_CREATE_REQUEST_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_DELETE_FAIL":
			image = "DOORLOCK_KEY_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_UPDATE_FAIL":
			image = "DOORLOCK_KEY_UPDATE_FAIL.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE_FAIL.png";
			break;
		case "WEB_ACCOUNT_DELETE_FAIL":
			image = "ACCOUNT_DELETE_FAIL.png";
			break;
		case "APP_KEY_DELETE_FAIL":
			image = "KEY_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_CREATE_FAIL":
			image = "DOORLOCK_CREATE_FAIL.png";
			break;
		case "APP_DOORLOCK_CREATE_MASTER_FAIL":
			image = "DOORLOCK_CREATE_MASTER_FAIL.png";
			break;
		case "APP_DOORLOCK_DELETE_FAIL":
			image = "DOORLOCK_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE_FAIL":
			image = "DOORLOCK_KEY_CREATE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE_REQUEST_FAIL":
			image = "DOORLOCK_KEY_CREATE_REQUEST_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_DELETE_FAIL":
			image = "DOORLOCK_KEY_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_UPDATE_FAIL":
			image = "DOORLOCK_KEY_UPDATE_FAIL.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE_FAIL.png";
			break;
		case "SUB_KEY_EXPIRE_FAIL":
			image = "SUB_KEY_EXPIRE_FAIL.png";
			break;
		case "HW_KEY_OPEN_FAIL":
			image = "HW_KEY_OPEN_FAIL.png";
			break;
		default:
			image = "default.png";
			break;
	}
		var result = {image:image,request:request};
		console.log(result);
	return result;
}

	// WEB_KEY_DELETE	열쇠 삭제
	// WEB_DOORLOCK_CREATE_MASTER	도어락 생성 & 마스터 권한
	// WEB_DOORLOCK_DELETE	도어락 삭제
	// WEB_DOORLOCK_KEY_CREATE	도어락 타 사용자 열쇠 생성
	// WEB_DOORLOCK_KEY_CREATE_REQUEST	도어락 타 사용자 열쇠 부여
	// WEB_DOORLOCK_KEY_DELETE	도어락 타 사용자 열쇠 삭제
	// WEB_DOORLOCK_KEY_UPDATE	도어락 타 사용자 열쇠 수정
	// WEB_NEWSPEED_KEY_RESPONSE_ACCEPT	부여 받은 열쇠 수락
	// WEB_NEWSPEED_KEY_RESPONSE_REFUSE	부여 받은 열쇠 거절
	// WEB_ACCOUNT_DELETE	회원 탈퇴
	// APP_KEY_DELETE	열쇠 삭제
	// APP_DOORLOCK_CREATE	도어락 생성
	// APP_DOORLOCK_CREATE_MASTER	도어락 생성 & 마스터 권한
	// APP_DOORLOCK_DELETE	도어락 삭제
	// APP_DOORLOCK_KEY_CREATE	도어락 타 사용자 열쇠 생성
	// APP_DOORLOCK_KEY_CREATE_REQUEST	도어락 타 사용자 열쇠 부여
	// APP_DOORLOCK_KEY_DELETE	도어락 타 사용자 열쇠 삭제
	// APP_DOORLOCK_KEY_UPDATE	도어락 타 사용자 열쇠 수정
	// APP_NEWSPEED_KEY_RESPONSE_ACCEPT	부여 받은 열쇠 수락
	// APP_NEWSPEED_KEY_RESPONSE_REFUSE	부여 받은 열쇠 거절
	// WEB_KEY_DELETE_FAIL	열쇠 삭제 실패
	// WEB_DOORLOCK_CREATE_MASTER_FAIL	도어락 생성 & 마스터 권한 실패
	// WEB_DOORLOCK_DELETE_FAIL	도어락 삭제 실패
	// WEB_DOORLOCK_KEY_CREATE_FAIL	도어락 타 사용자 열쇠 생성 실패
	// WEB_DOORLOCK_KEY_CREATE_REQUEST_FAIL	도어락 타 사용자 열쇠 부여 실패
	// WEB_DOORLOCK_KEY_DELETE_FAIL	도어락 타 사용자 열쇠 삭제 실패
	// WEB_DOORLOCK_KEY_UPDATE_FAIL	도어락 타 사용자 열쇠 수정 실패
	// WEB_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL	부여 받은 열쇠 수락 실패
	// WEB_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL	부여 받은 열쇠 거절 실패
	// WEB_ACCOUNT_DELETE_FAIL	회원 탈퇴 실패
	// APP_KEY_DELETE_FAIL	열쇠 삭제 실패
	// APP_DOORLOCK_CREATE_FAIL	도어락 생성 실패
	// APP_DOORLOCK_CREATE_MASTER_FAIL	도어락 생성 & 마스터 권한 실패
	// APP_DOORLOCK_DELETE_FAIL	도어락 삭제 실패
	// APP_DOORLOCK_KEY_CREATE_FAIL	도어락 타 사용자 열쇠 생성 실패
	// APP_DOORLOCK_KEY_CREATE_REQUEST_FAIL	도어락 타 사용자 열쇠 부여 실패
	// APP_DOORLOCK_KEY_DELETE_FAIL	도어락 타 사용자 열쇠 삭제 실패
	// APP_DOORLOCK_KEY_UPDATE_FAIL	도어락 타 사용자 열쇠 수정 실패
	// APP_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL	부여 받은 열쇠 수락 실패
	// APP_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL	부여 받은 열쇠 거절 실패
	// HW_KEY_OPEN
	// SUB_KEY_EXPIRE
	// HW_KEY_OPEN_FAIL
	// SUB_KEY_EXPIRE_FAIL

