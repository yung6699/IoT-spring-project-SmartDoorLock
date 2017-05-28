var regID;
var dataForm;
var pushNotification;

var autoLogin = function() {
	var memberSession = JSON.parse(window.localStorage.getItem('SESSION'));

	$("#email").val(memberSession.EMAIL);
	$("#password").val(memberSession.PASSWORD);
	$("#autologin").prop("checked", true).checkboxradio("refresh");

	var dataForm = {
		email : memberSession.EMAIL,
		password : memberSession.PASSWORD,
		autologin : $("#autologin")[0].checked ? 1 : 0
	};

	
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST + "/app/access/login",
		cache : false,
		async : false, //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
		data : dataForm,
		dataType : "json",
		success : function(response) {
			console.log(response);
			if (response.AJAX_RESULT == 'AJAX_SUCCESS') {
				response.LOGIN_MEMBER.PASSWORD = dataForm.password;
				window.localStorage.setItem('JUST_ONE_AUTO_LOGIN', 'true');
				window.localStorage.setItem('SESSION', JSON.stringify(response.LOGIN_MEMBER));
				location.href = '../main/main.html#mainPage';
			}
		},error : function(request,error,status){
			console.log(GLOBAL_HOST+"/app/access/login");
			console.log(request.responseText);
			console.log(error);
			console.log(status);
		}
	});
}


function onNotificationGCM(e) {

	regID = e.regid;
	dataForm = {
		email : $("#email").val(),
		password : $("#password").val(),
		autologin : 1,
		regId : regID
	};
	ajaxCall();
}

function ajaxCall() {
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST + "/app/access/login",
		cache : false,
		async : false,
		data : dataForm,
		dataType : "json",
		success : function(response) {
			console.log(response);
			if (response.AJAX_RESULT == 'AJAX_SUCCESS') {
				if (dataForm.autologin == 1) {
					window.localStorage.setItem('JUST_ONE_AUTO_LOGIN','true');
				} else {
					window.localStorage.setItem('JUST_ONE_AUTO_LOGIN','false');
				}
				response.LOGIN_MEMBER.PASSWORD = dataForm.password;
				window.localStorage.setItem('SESSION', JSON.stringify(response.LOGIN_MEMBER));
				location.href = '../main/main.html#mainPage';
			} else {
				alert("아이디, 비밀번호를 다시 확인 해 주세요");
			}
		},error : function(request,error,status){
			console.log(request);
			console.log(error);
			console.log(status);
		}
	});
}



function loginSubmit() {
	var networkState = navigator.connection.type;
	if (networkState !== Connection.NONE) {
		if ($("#autologin")[0].checked) {
			pushNotification = window.plugins.pushNotification;
			pushNotification.register(successHandler, errorHandler, {
				"senderID" : "988917980557",
				"ecb" : "onNotificationGCM"
			});
		} else {
			dataForm = {
				email : $("#email").val(),
				password : $("#password").val(),
				autologin : 0
			};
			ajaxCall();
		}
	} else {
		alert("인터넷을 연결해주세요!!!");
	}
};

function successHandler(result) {}
function errorHandler(error) {alert( 'error = ' + error); }
