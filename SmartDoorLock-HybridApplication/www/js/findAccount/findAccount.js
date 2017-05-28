$(document ).on("pagecreate", function() {
  footer_btn_able("btn_page_complete","page_complete");
  //회원가입 완료 페이지
});

$(document).ready(function(){

  $('.footer_btn_active').on('vclick',function(){

    var href=$(this).data('href');
    if(typeof(href) != "undefined"){
      switch(href){
        case 'page_complete':
          var phone_reg = /[0-9]{11}/;
          var email_reg = /[\w-_.]+@[\w-_].[\w-_.]/g;

          var phone_num = $("#phone_num").val();
          var name = $("#name").val();
          var email = $("#email").val();
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
                  url : GLOBAL_HOST+"/app/access/find/account",
                  cache : false,
                  async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
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

          break;
        default :
          console.log(href);
          location.href='#'+href;
          break;
      }
    }
  });

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
  $("#"+id).removeData("href");
}


function footer_btn_able(id,url){

  $("#"+id).removeClass("footer_btn_disactive");
  $("#"+id).addClass('footer_btn_active');
  $("#"+id).data("href",url);
}
