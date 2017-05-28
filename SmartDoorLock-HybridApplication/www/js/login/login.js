$(function() {
	$('div.ui-loader ').remove();

	document.addEventListener("online", function() {
		if (window.localStorage.getItem('JUST_ONE_AUTO_LOGIN') == 'true') {
			autoLogin();
		}
	}, false);

	document.addEventListener("offline", function() {
		alert("인터넷을 연결하세요!!!!!");
		navigator.app.exitApp();
	}, false);

	document.addEventListener("backbutton", function() {
		navigator.app.exitApp();
	}, false);

	$('#loginBtn').bind('touchstart', function(e) {
		$('#loginBtn').css("background-color", "#A19F9F");	
	});

	$('#loginBtn').bind('touchend', function(e) {
		$('#loginBtn').css("background-color", "#2A3F54");	
		loginSubmit();
	});

	$('#joinBtn').bind('click', function() {
		location.href = '../join/join.html'
	});

	$('#findAccount').bind('click', function() {
		location.href = "../findAccount/findAccount.html#findAccount_page"
	});

});
