//initialize join document
var email_check=0;
$(document ).on("pagecreate", function() {
  console.log("초기화가 되어야 합니다.");

  //약관동의 초기화
  $("#page_agree_input").checkboxradio();
  $("#page_agree_input").prop("checked",false).checkboxradio('refresh');
  footer_btn_disable('btn_page_email');

  //이메일 인증 초기화
  $("#page_email_input").val("");
  $("#page_email_message").html("&nbsp;");
  footer_btn_disable('btn_page_password');
  //부가 정보 등록

  $("#page_password_message").html("&nbsp;");
  $("#page_phone_message").html("&nbsp;");
  $("#page_name_message").html('&nbsp;');

  $("#page_password_input").val("");
  $("#page_password_input_re").val("");
  $("#page_phone_input").val("");
  $("#page_name_input").val("");
  footer_btn_able("btn_page_complete","page_complete");
  //회원가입 완료 페이지
  $("#page_complete_list").html("&nbsp;");
 $("body").removeAttr('style');
   document.addEventListener("backbutton", function(){
           location.replace('../login/login.html');
   }, false);
});


//약관동의
function agreeInput(){
  var state= $("#page_agree_input")[0].checked;
  console.log(state);
  if(state){
    footer_btn_able('btn_page_email','step1');
  }else{
    footer_btn_disable('btn_page_email');
  }
};


//이메일 인증
$("#page_email_input").keyup(function(){
  var reg = /[\w-_.]+@[\w-_].[\w-_.]/g;
  var input_email =$("#page_email_input").val();
  alert("123");
  if(reg.test(input_email)){
    page_message("page_email_message",'유효한 메일 주소입니다.');
    footer_btn_able('btn_page_password','page_password');
  }else{
    page_message("page_email_message",'이메일 형식이 올바르지 않습니다.',"err");
    footer_btn_disable('btn_page_password');
  }
});



//비밀번호 대조.
$("#page_password_input_re").keyup(function(){
  var first_password = $("#page_password_input").val();
  var second_password = $("#page_password_input_re").val();
  if(first_password == second_password){
    page_message('page_password_message','유효한 비밀번호 입니다.');
  }else{
    page_message('page_password_message','재확인 비밀번호와 일치하지 않습니다.','err');
  }
});



//핸드폰 인증 페이지
$("#page_phone_input").keyup(function(){
  var reg = /[0-9]{11}/;
  var input_phone = $("#page_phone_input").val();

  if(reg.test(input_phone)){
    page_message('page_phone_message','유효한 전화번호 입니다.');
  }else{
    page_message('page_phone_message','유효하지 않은 전화번호 입니다','err');
  }
});



//이름 작성
$("#page_name_input").keyup(function(){
  if($(this).val().length>=2){
    page_message('page_name_message',"유효한 이름입니다.");
  }else{
    page_message('page_name_message',"유효하지 않는 이름입니다.",'err');
  }
});


function page_message(id, message, state){
  var element = $('#'+id);
  switch(state){
    case "err":
    element.css("color","#fc1414");
    element.html(message);
    break;
    case "good":
    element.css("color","#0FBA15");
    element.html(message);
    break;
    default :
    element.css("color","#000");
    element.html(message);
    break;

  }
}


function footer_btn_disable(id){
  $("#"+id).removeClass("footer_btn_active");
  $("#"+id).addClass("footer_btn_disactive");
  $("#"+id).attr('onclick', '');
}


function footer_btn_able(id,url){
  $("#"+id).removeClass("footer_btn_disactive");
  $("#"+id).addClass('footer_btn_active');  
  $("#"+id).attr('onclick', 'next(\''+url+'\');');
}


function login_page(){
  location.replace('../login/login.html');
}


function join_page(){
  location.replace('../join/join.html');
}

var bit;
var check1=0;
var check2=0;
var check3=0;
var check4=0;

function next(){
  footer_btn_disable('btn_page_email');
  console.log("next");
  bit=arguments[0];
  console.log(bit);
  ajaxEvent(bit);
  
  if(arguments[0]=='step1'){
     $("body").removeAttr('style');
    $("#content").empty();
    $("#content").append(
    '<h3 style="margin-top: 30px;">이메일 주소를 입력해주세요.</h3>'+
    '<h5 id="page_email_message" style="color:#fc1414">&nbsp;</h5>'+
    '<input type="email" placeholder="Email" id="page_email_input" class="email_input"/>'

    );
   
  }else if(arguments[0]=='step2'&&email_check==1){

    $("#content").empty();
    var url = "step1";

    $(".footer_btn_disactive").css('position','fixed');
    $(".footer_btn_disactive").css('position','fixed');
    $("#btn_page_email").html('완료');
    $("#content").append(
      '<h5 class="info_title" >비밀번호 입력</h5>'+
      '<input type="password" placeholder="Password" id="page_password_input" class="input_box"/>'+
      '<input type="password" placeholder="Password" id="page_password_input_re" class="input_box"/>'+
      '<h5 class="info_title">핸드폰 번호 입력</h5>'+
      '<input type="number" placeholder=" - 을 제거한 번호를 입력해 주세요." id="page_phone_input" class="input_box"/>'+
      '<h5 class="info_title">이름 입력</h5>'+
      '<input type="text" placeholder="사용할 이름을 입력해주세요(공백 없이 입력해주세요.)" id="page_name_input" class="input_box"/>'
      );  
  }
  
    //이메일 인증
  $("#page_email_input").keyup(function(){
    var reg = /[\w-_.]+@[\w-_].[\w-_.]/g;
    var input_email =$("#page_email_input").val();
    if(reg.test(input_email)){
      console.log('email ok');
      check1=1;
      page_message("page_email_message",'유효한 메일 주소입니다.');
      footer_btn_able('btn_page_email','step2');

    }else{
      check1=0;
      page_message("page_email_message",'이메일 형식이 올바르지 않습니다.',"err");
      footer_btn_disable('btn_page_email');
    }
  });

    //비밀번호 대조.
  $("#page_password_input_re").keyup(function(){
    var first_password = $("#page_password_input").val();
    var second_password = $("#page_password_input_re").val();
    if(first_password == second_password){
      check2=1;
      console.log('password ok');

      page_message('page_password_message','유효한 비밀번호 입니다.');
    }else{
      check2=0;
      page_message('page_password_message','재확인 비밀번호와 일치하지 않습니다.','err');
    }
  });

  //핸드폰 인증 페이지
  $("#page_phone_input").keyup(function(){
    var reg = /[0-9]{11}/;
    var input_phone = $("#page_phone_input").val();

    if(reg.test(input_phone)){
      check3=1;
      console.log('phone ok');
      page_message('page_phone_message','유효한 전화번호 입니다.');
    }else{
      check3=0;
      page_message('page_phone_message','유효하지 않은 전화번호 입니다','err');
    }
  });

  //이름 작성
  $("#page_name_input").keyup(function(){

    if($(this).val().length>=2){
      console.log('name ok');
      page_message('page_name_message',"유효한 이름입니다.");
      if(check1==1&& check2==1&&check3==1){
       footer_btn_able('btn_page_email','step3');
     }
     
   }else{

    page_message('page_name_message',"유효하지 않는 이름입니다.",'err');
  }
});

}
function ajaxEvent(bit){
    if(typeof(bit) != "undefined"){

      switch(bit){
        case 'step2':
          var dataForm = {email:$('#page_email_input').val()}
          $.ajax({
            type : "POST",
            url : GLOBAL_HOST+"/app/access/join/email",
            cache : false,
            async : false,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
            data : dataForm,
            dataType : "json",
            success : function(response) {
                  if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
                    GLOBAL_EMAIL = dataForm.email;
                    console.log('ajax success');
                    alert(response.RESULT_MSG);
                    email_check=1;
                  }else{
                    alert(response.RESULT_MSG);
                    email_check=0;
                  }
            },
            error : function(request, status, errorThrown) {
                  console.log(request);
                  console.log(status);
                  console.log(errorThrown);
              }
          });
          break;


        case 'step3':
          var first_password = $("#page_password_input").val();
          var second_password = $("#page_password_input_re").val();
          var reg = /[0-9]{11}/;
          var phone = $("#page_phone_input").val();
          var name = $("#page_name_input").val();

          if(first_password == second_password){
            if(reg.test(phone)){
              if(name.length>=2){
                    var dataForm = {
                      email : GLOBAL_EMAIL,
                      password : second_password,
                      name : name,
                      phone_num : phone
                    };
                    console.log(dataForm);
                    $.ajax({
                      type : "POST",
                      url : GLOBAL_HOST+"/app/access/join/complete",
                      cache : false,
                      async : false,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
                      data : dataForm,
                      dataType : "json",
                      success : function(response) {
                            console.log(response);
                            if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
                              alert(response.RESULT_MSG);
                              location.replace('../login/login.html');
                            }else{
                              alert(response.RESULT_MSG);
                            }
                        },
                        error : function(request, status, errorThrown) {
                          console.log(request);
                          console.log(status);
                          console.log(errorThrown);
                        }
                });
                }else{
                  alert("이름을 확인해 주세요");
                }
            }else{
              alert("전화번호를 확인해 주세요");
            }
          }else{
            alert("비밀번호를 확인해주세요.");
          }
          break;

          default :
          break;
      }
    }
}