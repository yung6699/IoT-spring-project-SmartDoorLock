
$(function() {
	$.fn.extend({
		animateCss : function(animationName, completeFunc) {
			var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
			$(this).addClass('animated ' + animationName).one(animationEnd,function() {
				$(this).removeClass('animated ' + animationName).promise().done(function() {
					//콜백함수를 검증하는 곳. 콜백함수가 없어도 사용가능할 수 있도록 구현함.
					if (completeFunc) {
						completeFunc($(this));
					} 
				});
			});
		}
	});

});



//모달로된 로그인 페이지를 나타내어라.
//input1 : modalLoginEmail
//input2 : modalLoginPassword
//button : accessModalLoginPageLogin()
// join : accessModalJoinPage();
// findAccount : accessModalFindAccountPage();
function accessModalLoginPage() {
	accessModalPageInit();
	$("#accessModalTitle").html("로그인");
	$('#accessModalContent').html("<div class='form-group'> <label for='modalLoginEmail'>Email </label> <input type='email' id='modalLoginEmail' class='form-control' placeholder='Input your E-mail address'/> </div> <div class='form-group'> <label for='modalLoginPassword'>Password </label> <input type='password' id='modalLoginPassword' class='form-control' placeholder='Input your E-mail password'/> </div> <div class='form-group'> <button id='loginButton' class='btn btn-primary form-control' onclick='accessModalLoginPageLogin()'>로그인</button> </div> <div> <center> <br/> <a href='javascript:accessModalJoinPage();'> <h5> 회원가입 </h5> </a> <a href='javascript:accessModalFindAccountPage();'> <h5> 비밀번호 찾기 </h5> </a> </center> </div>");
	$("#accessModal").modal("show");
	$("#modalLoginEmail").on("keyup",keyUpEvent);
	$("#modalLoginPassword").on("keyup",keyUpEvent);
}

function keyUpEvent(event){
	if(event.keyCode==13)accessModalLoginPageLogin();
}


//로그인 시도 하려고 할 때
function accessModalLoginPageLogin(){
	var dataForm={
			email:$("#modalLoginEmail").val(),
			password:$("#modalLoginPassword").val()
	}
	console.log("accessModalLoginPaperLogin");
	console.log(dataForm);
	$.ajax({
		url : "/login.do",
		type : "POST",
		data : dataForm,
		cache : false,
		async : false,
		success : function(res) {
			console.log(res);
			if(res.AJAX_RESULT=='AJAX_SUCCESS'){
				location.replace("/"+res.LOGIN_MEMBER.EMAIL);
			}else{
				alert(res.RESULT_MSG);
			}
		},
		error : function(req,txt,err) {
		}
	});
}


//로그인 페이지의 내용을 지우고 새로운 화면을 제시합니다 
//회원가입
//input : accessModalJoinAccept
//button : accessModalJoinEmailPage();
function accessModalJoinPage(){
	console.log("accessModalJoinPage");
	$('#accessModalPanel').fadeOut(function(){
		$("#accessModalTitle").html("회원가입");
		$('#accessModalContent').html("<div style=\"height:25em; overflow-y: scroll; text-align: left;\"> <pre>" +
				"본 계약은 영어(미국)로 작성되었습니다. 본 계약\n" +
				"의 변역본과 영어본이 상충될 경우에는 영어본을\n" +
				"우선으로 합니다. 섹션 16에 있는 미국 외 지역\n" +
				"사용자들에 적용되는 일반 약관에 변경 내용이\n" +
				"포함되어 있음을 알려드립니다.\n" +
				"\n" +
				"Smart Lock 권리 및 책임에 관한 정책\n" +
				"본 권리 및 책임에 관한 정책 ( 이하 )\n" +
				"알아서 동의해라본 계약은 영어(미국)로 작성되었습니다. 본 계약\n" +
				"의 변역본과 영어본이 상충될 경우에는 영어본을\n" +
				"우선으로 합니다. 섹션 16에 있는 미국 외 지역\n" +
				"사용자들에 적용되는 일반 약관에 변경 내용이\n" +
				"포함되어 있음을 알려드립니다.\n" +
				"\n" +
				"Smart Lock 권리 및 책임에 관한 정책\n" +
				"\n" +
				"본 권리 및 책임에 관한 정책 ( 이하 )\n" +
				"알아서 동의해라본 계약은 영어(미국)로 작성되었습니다.\n" +
				"본 계약 의 변역본과 영어본이 상충될 경우에는 영어본을\n" +
				"우선으로 합니다. 섹션 16에 있는 미국 외 지역\n" +
				"사용자들에 적용되는 일반 약관에 변경 내용이\n" +
				"포함되어 있음을 알려드립니다.\n" +
				"\n" +
				"Smart Lock 권리 및 책임에 관한 정책\n" +
				"\n" +
				"본 권리 및 책임에 관한 정책 ( 이하 )\n" +
				"알아서 동의해라\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"</pre> </div> <hr/> <div class='form-group'> <label for='accessModalJoinAccept'>약관에 동의하십니까?</label> <input type='checkbox' id='accessModalJoinAccept' />	</div> <br/> <div class='form-group'> <button class='btn btn-primary form-control' onclick='accessModalJoinEmailPage();'>다음</button> </div>"	
			);
		$('#accessModalPanel').fadeIn();
	});
}


//이메일을 인증하러 가는 곳.
//input : accessModalJoinEmail
//message : accessModalJoinEmailMessage
//button : accessModalJoinAccountPage()

function accessModalJoinEmailPage(){
	console.log();
	if($('#accessModalJoinAccept:checked').length==1){
		console.log("이메일을 인증하러 갑시다.");
		$("#accessModalTitle").html("회원가입 >> 이메일 확인");
		$("#accessModalContent").fadeOut(function(){
			$("#accessModalContent").html('<div class="form-group"> <label for="accessMadalJoinEmail">Email</label> <input type="email" class="form-control" id="accessModalJoinEmail"/> </div>	<p class="message" id="accessModalJoinEmailMessage"></p> <div class="well"> <h5>안녕하세요 SmartLock 입니다.</h5> <ul> <li>이메일은 아이디로 사용됩니다.</li> <li>반드시 인증 가능한 이메일을 사용해주세요.</li> <li>좀 더 나은 서비스를 제공하겠습니다.</li> </ul> </div> <div class="form-group"> <button class="btn btn-primary form-control" onclick="accessModalJoinAccountPage()">다음</button> </div> </div> <div id="accessModalFooter" class="modal-footer"> </div>');
			$("#accessModalContent").fadeIn();
			$('#accessModalJoinEmail').keyup(function(){
				var email = $('#accessModalJoinEmail').val(); 
				var reg = /[\w-_.]+@[\w-_].[\w-_.]/g;//
				
				if(reg.test(email)){
					$("#accessModalJoinEmailMessage").css("color","black"); 
					$("#accessModalJoinEmailMessage").html("유효한 이메일 주소입니다.");
				}else{
					$("#accessModalJoinEmailMessage").css("color","red"); 
					$("#accessModalJoinEmailMessage").html("이메일 주소가 유효하지 않습니다.");
				}
			});
		});
	}else{
		alert("약관에 동의 하셔야만 SmartLock을\n 이용하실 수 있습니다.");
	}
}

//input1 : accessModalJoinEmail
//button : accessModalJoinAccountSubmit()
function accessModalJoinAccountPage(){
	var dataForm = {email:$('#accessModalJoinEmail').val()};
	//value=email;
	console.log(email); //잘 넘어온다. 
	var reg = /[\w-_.]+@[\w-_].[\w-_.]/g;
	
	if(reg.test(dataForm.email)){
		$.ajax({
			url : "/join/email.do",
			type : "POST",
			data : dataForm,
			cache : false,
			async : false,
			success : function(res) {
				console.log(res);
				if(res.AJAX_RESULT=='AJAX_SUCCESS'){
					alert(res.RESULT_MSG);
					console.log("계정을 등록하러 왔습니다.");
					$("#accessModalTitle").html("회원가입 >> 이메일 확인 >> 계정 등록");
					$("#accessModalContent").fadeOut(function(){
						$("#accessModalContent").html('<div class="form-group"> <label for="accessModalJoinAccountEmail">Eamil</label> <input type="email" id="accessModalJoinAccountEmail" class="form-control" value='+dataForm.email+' readonly/> </div> <div class="form-group"> <label for="accessModalJoinAccountPassword">Password</label> <input type="password" id="accessModalJoinAccountPassword" placeholder="8글자 이상 입력해주세요." class="form-control"/> </div> <div class="form-group"> <label for="accessModalJoinAccountRePassword">Re-Password</label> <input type="password" id="accessModalJoinAccountRePassword"  placeholder="8글자 이상 입력해주세요." class="form-control"/> </div> <div class="form-group"> <label for="accessModalJoinAccountName">Name</label> <input type="text" id="accessModalJoinAccountName"  placeholder="2글자 이상 입력해주세요." class="form-control"/> </div> <div class="form-group"> <label for="accessModalJoinAccountPhone">Phone</label> <input type="tel" id="accessModalJoinAccountPhone"  placeholder="숫자와 10~11글자로 입력해주세요." class="form-control"/> </div> <br/> <div class="form-group"> <button onclick="accessModalJoinAccountSubmit()" class="btn btn-primary form-control">완료</button> </div>');
						$("#accessModalContent").fadeIn();
					});
				}else{
					alert(res.RESULT_MSG);
				}
			},
			error : function(req,txt,err) {
			}
		});
	}else{
		alert("이메일 주소가 유효하지 않습니다.");
	}
}
//계정 정보 입력하러
//input1 : accessModalJoinAccountEmail
//input2 : accessModalJoinAccountPassword
//input3 : accessModalJoinAccountRePassword
//input4 : accessModalJoinAccountName
//input5 : accessModalJoinAccountPhone
function accessModalJoinAccountSubmit(){
	console.log("accessModalJoinAccountSubmit");
	var email = $("#accessModalJoinAccountEmail").val();
	var email_reg = /[\w-_.]+@[\w-_].[\w-_.]/g;
    var first_password = $("#accessModalJoinAccountPassword").val();
	var second_password = $("#accessModalJoinAccountRePassword").val();
	var phone_reg = /[0-9]{11}/;
	var phone = $("#accessModalJoinAccountPhone").val();
	var name = $("#accessModalJoinAccountName").val();
	if(email_reg.test(email)){
		if(first_password == second_password){
			if(phone_reg.test(phone)){
				if(name.length>=2){
					var dataForm = {
						email : email,
						password : second_password,
						name : name,
						phone_num : phone
					};
					console.log(dataForm);
					$.ajax({
						type : "POST",
						url : "/join.do",
						cache : false,
						async : false, // true = 비동기로 ajax 통신을 하겠다. sync 동기로
										// 통신을 하겠다.
						data : dataForm,
						dataType : "json",
						success : function(response) {
							console.log(response);
							if (response.AJAX_RESULT == 'AJAX_SUCCESS') {
								alert(response.RESULT_MSG);
								location.replace('/');
							} else {
								alert(response.RESULT_MSG);
							}
						},
						error : function(request, status, errorThrown) {
							console.log(request);
							console.log(status);
							console.log(errorThrown);
						}
					});
				} else {
					alert("이름을 확인해 주세요");
				}
			} else {
				alert("전화번호를 확인해 주세요");
			}
		} else {
			alert("비밀번호를 확인해주세요.");
		}
	} else {
		alert("html주작은 뺨따귀야!");
	}
}

//로그인 페이지의 내용을 지우고 새로운 화면을 제시합니다 
//회원 계정 및 패스워드 찾기
//input1 : accessModalFindAccountEmail
//input2 : accessModalFindAccountPhone
//input3 : accessModalFindAccountName
//button : accessModalFindAccountSubmit();
function accessModalFindAccountPage(){
	console.log("accessModalFindAccountPage");
	$('#accessModalPanel').fadeOut(function(){
		$("#accessModalTitle").html("계정 찾기");
		$('#accessModalContent').html('<div class="form-group"> <label for="accessModalFindAccountEmail">Email</label> <input type="text" class="form-control" id="accessModalFindAccountEmail" placeholder="Input your Email Account"/> </div> <div class="form-group"> <label for="accessModalFindAccountPhone">Phone</label> <input type="text" class="form-control" id="accessModalFindAccountPhone" placeholder="Input your Phone number"/> </div> <div class="form-group"> <label for="accessModalFindAccountName">Name</label> <input type="tel" class="form-control" id="accessModalFindAccountName" placeholder="Input your Name"/> </div> <br/><div class="form-group"> <button class="btn btn-primary form-control" onclick="accessModalFindAccountSubmit()">회원 찾기</button> </div>');
		$("#accessModalPanel").fadeIn();
	});
}


function accessModalFindAccountSubmit(){
	var phone_reg = /[0-9]{11}/;
    var email_reg = /[\w-_.]+@[\w-_].[\w-_.]/g;

    var phone_num = $("#accessModalFindAccountPhone").val();
    var name = $("#accessModalFindAccountName").val();
    var email = $("#accessModalFindAccountEmail").val();
    console.log(email);
    console.log(name);
    console.log(phone_num);

    if(email_reg.test(email)){
      if(phone_reg.test(phone_num)){
        if(name.length>=2){
          var dataForm = {
            email : email,
            name : name,
            phone_num : phone_num
          };
          console.log(JSON.stringify(dataForm));
          $.ajax({
            type : "POST",
            url : "/find/account.do",
            cache : false,
            async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
            data : dataForm,
            dataType : "json",
            success : function(response) {
              console.log(response);
              if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
                alert(response.RESULT_MSG);
                location.replace('/');
              }else{
                alert(response.RESULT_MSG);
              }
            },
            error : function(request, status, errorThrown) {
              console.log(JSON.stringify(request));
              console.log(JSON.stringify(status));
              console.log(JSON.stringify(errorThrown));
            }
          });
        }else{
          alert("이름을 확인해 주세요");
        }
      }else{
        alert("전화번호를 확인해 주세요");
      }
    }else{
      alert('이메일을 확인해 주세요');
    }

}


function accessModalPageInit(){
	$("#accessModalTitle").html("");
	$("#accessModalContent").html("");
	$("#accessModalFooter").html("");
	
}