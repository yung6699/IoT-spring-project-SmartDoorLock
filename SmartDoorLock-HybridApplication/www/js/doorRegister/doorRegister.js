  // 모바일 백 버튼이 눌릴시 발생하는 이벤트


$(document ).on("pagecreate", function() {

  $("#doorlock_name").on( "keydown", function( event ) {
    if(event.which==13){
      $("#installed_place").focus();
    }
  });
  $("#installed_place").on( "keydown", function( event ) {
    if(event.which==13){
      insertDoorLock();
    }
  });

  $.fn.extend({
    animateCss: function (animationName, completeFunc) {
      var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
      $(this).addClass('animated ' + animationName).one(animationEnd, function() {
        $(this).removeClass('animated ' + animationName);
        completeFunc();
      });
    }
  })

  
document.addEventListener("backbutton", function(){
  location.replace('../main/main.html');
}, false);

});


function checkSerial(){

  dataForm = {
    serial_no:$('#serial').val()
  }
  console.log(JSON.stringify(dataForm));
  $.ajax({
    type : "POST",
    url : GLOBAL_HOST+"/app/doorlock/register/check",
    cache : false,
    async : false,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
    data : dataForm,
    dataType : "json",
    success : function(response) {
      if(response.AJAX_RESULT == 'AJAX_SUCCESS'){
        $("#complete_btn").html("등록하기");
        $("#complete_btn").attr("onclick","callSubmitDiv()");
        $("#submitDiv").animateCss('bounceOutLeft',function(){
          $("#submitDiv").css("display","none").promise().done(function(){
            $("#serial").attr("disabled",true);
            $("#submitDiv").html('<center><br/>제품 번호가 유효합니다.<br/><img id="test" src="../../img/doorRegister/doorlock_ok.svg" style="margin-top:3em; width:12em;height:auto"/></center><br/><br/>&nbsp;').promise().done(function(){
              
              $("#submitDiv").css("display","block").promise().done(function(){
                $("#submitDiv").animateCss('bounceInRight',function(){});
              });
            });
          });
        });
      }else{
        alert(response.RESULT_MSG);
      }
    },error : function(request, status, errorThrown) {
      console.log(JSON.stringify(request));
      console.log(JSON.stringify(status));
      console.log(JSON.stringify(errorThrown));
    }
  });
}
function callSubmitDiv(){
   $("#complete_btn").html("등록완료");
   $("#complete_btn").attr("onclick","insertDoorLock()");
  $("#submitDiv").animateCss('bounceOut',function(){
    $("#submitDiv").css("display","none").promise().done(function(){
      $("#submitDiv").html('<form id="submitForm">'+
                        '<p style="margin-bottom:0.75em; margin-left:0.5em;">도어락 이름</p>'+
                        '<input type="text" placeholder="도어락의 이름을 정해주세요" id="doorlock_name" style="width:100%;font-size:0.75em;padding-top:1.25em;padding-bottom:1.25em;padding-left:0.75em;box-sizing:border-box;border:solid 0.125em #ddd; margin-bottom:0.5em;"/>'+
                        '<p style="margin-bottom:0.75em; margin-left:0.5em;">설치 장소</p>'+
                        '<input type="text" placeholder="설치장소를 입력해주세요" id="installed_place" style="width:100%;font-size:0.75em;padding-top:1.25em;padding-bottom:1.25em;padding-left:0.75em;box-sizing:border-box;border:solid 0.125em #ddd; margin-bottom:0.5em;"/>'+
                        '</form>'+
                        '<br/><br/><br/><br/>&nbsp;'
    ).promise().done(function(){
      $("#submitDiv").css("display","block").promise().done(function(){
        $("#submitForm").trigger('create'); // form 태그 새로 그리기
        $("#complete_btn").html("등록완료");
        $("#complete_btn").attr("onclick","insertDoorLock()");
        $("#submitDiv").animateCss('bounceIn',function(){});
      });
    });
  });
});
}
function home(){
  location.replace('../main/main.html');
}

function insertDoorLock(){
  var session = JSON.parse(window.localStorage.getItem('SESSION'));
  //session 을 사용하려거든 JSON.parse 를 이용해서 사용하시길 바랍니다.

  var dataForm = {
    serial_no : $("#serial").val(),
    member_id : session.MEMBER_ID,
    doorlock_name : $("#doorlock_name").val(),
    installed_place : $("#installed_place").val(),
    email : session.EMAIL
  };
  console.log(JSON.stringify(dataForm));
  $.ajax({
    type : "POST",
    url : GLOBAL_HOST+"/app/doorlock/register/insert",
    cache : false,
    async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
    data : dataForm,
    dataType : "json",
    success : function(res) {
      if(res.AJAX_RESULT == 'AJAX_SUCCESS'){
        $("#content").empty();
        $("#hd_left").empty();
        $("#content").append(
          '<ul data-role="listview">'+
            '<li class="doorlock_info">스마트락 기본 정보</li>'+
            '<li><span>제품 번호</span><span></span></li>'+
            '<li><span>제품 타입</span><span></span></li>'+
            '<li><span>도어락 이름</span><span></span></li>'+
            '<li><span>설치 장소</span><span></span></li>'+
            '<li><span>마스터</span><span></span></li>'+
            '<li><span>도어락 상태</span><span></span></li>'+
            '<li style="margin-bottom:1em;"><span>등록일</span><span></span></li>'+
          '</ul>'
        );

        $("ul li:nth-child(2) span:nth-child(2)").html(res.serial_no);
        $("ul li:nth-child(3) span:nth-child(2)").html(res.type);
        $("ul li:nth-child(4) span:nth-child(2)").html(res.doorlock_name);
        $("ul li:nth-child(5) span:nth-child(2)").html(res.installed_place);
        $("ul li:nth-child(6) span:nth-child(2)").html(res.email);
        $("ul li:nth-child(7) span:nth-child(2)").html(res.state_name);
        $("ul li:nth-child(8) span:nth-child(2)").html(res.crt_dt);
        $("#complete_btn").html("메인 화면으로");
        $("#complete_btn").attr("onclick","home()");


      }else{
        alert(res.RESULT_MSG);
  
      }
    },
    error : function(request, status, errorThrown) {
      console.log(JSON.stringify(request));
      console.log(JSON.stringify(status));
      console.log(JSON.stringify(errorThrown));
    }
  });
}
