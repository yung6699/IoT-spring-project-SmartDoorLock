$(function(){
	console.log("main_newspeed!");
	var email = $("#login_email").text();
	console.log(email);
	newspeedList(email);

});

function newspeedList(email){
	$("#news_list").html("");
	$.ajax({
		url:"/"+email+"/newspeed",
		dataType:"json",
		method:"POST",
		success:function(response){
			console.log(response);
			$("#news_list").empty();
			
			if(response.length==0){
				$("#news_list").append(
					"<li><img src='../../img/notice/NOTHING.png'>"
					+"<p>수신받은 뉴스피드 소식이 없습니다.</p>"
					+"<br><p class='ui-li-aside'>&nbsp;</p></li>"
				);
			}else{
				response.filter(function(item,index,all){
					var ImageAndReqeust = getImageMessage(email,item);
					$("#news_list").append(
						"<li class='row well'>"+
							"<div class='col-xs-9 col-sm-10 col-md-12 col-lg-10'>"+
								"<div>"+
									"<span class='attr'>날 짜 : </span> <span class='value'>"+item.log_date+"</span>"+
								"</div>"+
								"<div>"+
									"<span class='attr'>내 용 : </span> <span class='value'>"+item.message+"</span>"+
								"</div>"+
								ImageAndReqeust.request+
							"</div>"+
							"<div class='col-xs-3 col-sm-2 col-lg-2 image_right'>" +
								"<img width='50' src='/resources/images/pages/main/newspeed/"+ImageAndReqeust.image+"\'/>"+			     
							"</div>"+
							
						"</li>"
								
					);
				});
			}
		},
		error:function(){
			
		},
		complete:function(){
			
		}
	});
}
function bt_click_yes(email, idx, send_email, recv_email, serial_no){
	
	var dataForm = {
		idx : idx,
		send_email : send_email,
		recv_email : recv_email,
		serial_no : serial_no,
		answer : "ACCEPT",
		email : email
	};

	$.ajax({
		type : "POST",
		url : email+"/newspeed/key/response",
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

function bt_click_no(email,idx,send_email,recv_email,serial_no){

	var dataForm={
		idx : idx,
		send_email : send_email,
		recv_email : recv_email,
		serial_no : serial_no,
		answer : "REFUSE",
		email:email
	}
	$.ajax({
		type : "POST",
		url : email+"/newspeed/key/response",
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



function getImageMessage(email,item){
	var image = "",
	      request ="";
	      
	switch(item.state){
		case "WEB_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			 request ="<div><button class='btn btn-primary' onclick=\'bt_click_yes(\""+email+"\",\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>YES</button>";
			 request+="<button class='btn btn-danger' onclick=\'bt_click_no(\""+email+"\",\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>NO</button></div>";
			break;
		case "APP_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			request ="<div><button class='btn btn-primary' onclick=\'bt_click_yes(\""+email+"\",\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>YES</button>";
			request+="<button class='btn btn-danger' onclick=\'bt_click_no(\""+email+"\",\""+item.idx+"\"\,\""+item.send_email+"\"\,\""+item.recv_email+"\"\,\""+item.serial_no+"\");\'>NO</button></div>";
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


