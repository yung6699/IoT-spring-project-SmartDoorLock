$(function(){

	var session = JSON.parse(window.localStorage.getItem('SESSION'));
	var data = {member_id:session.MEMBER_ID,};
	
	swipe(data);
	noticeCount(data);


/*****************************************************************************************************************/

    	$("ul.swipe-wrap").delegate("li > img.key","touchstart",function(event) {
	    	$(this).attr('src','../../img/main/Lock_open.svg');
	    });	

	    $("ul.swipe-wrap").delegate("li > img.key","touchend",function(event) {
	    	var key_id = $(this).next().text(),
	    	      bluetooth_id = $(this).next().next().text();

                         if (confirm("문을 열겠습니까?") == true){    //확인
                   	     app.initialize(key_id, bluetooth_id);
                   	      $(this).attr('src','../../img/main/Lock_close.svg');
                          }else{   //취소
                         	     $(this).attr('src','../../img/main/Lock_close.svg');
                    	     return;
                          }
	    });	


/**************************************************************************************************************/
	// 모바일 백 버튼이 눌릴시 발생하는 이벤트
	document.addEventListener("backbutton", onBackKeyDown, false);

/***************************************************************************************************************/
});  // 즉시실행함수 끝

var onBackKeyDown = function(){
	var temp =  confirm('종료하시겠습니까?');
	if(temp){
		localStorage.removeItem('my_email');
		navigator.app.exitApp();
	};
}

var swipe = function(data){
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/main",
		dataType : "json",
		data: data,
		success:function(res){
			var count;
			var html="";

			if(res.length < 5){
				count = res.length
			} else {
				count = 5
			}

			for(var i=0; i<count; i++){
				html+=	"<li class=\"swiper-container\">"
				html+="<h1>"+res[i].key_name+"</h1>"
				html+="<img src=\"../../img/main/Lock_close.svg\" class=\"key\" style='width:14em;height:auto'/>"
				html+="<div style='display:none' id='key_id'>"+res[i].key_id+"</div>"
				html+="<div style='display:none' class='bluetooth_id'>"+res[i].bluetooth_id+"</div><br><h1 style='margin-bottom: -1px;'>"
				
				var grade =res[i].grade;
				if(grade === 'MASTER'){
					 html+="마스터</h1>"
				} else if(grade==="MANAGER"){
					 html+="매니저</h1>"
				} else{
					 html+="사용자</h1>"
				}

				html+="<div class='text'>"+res[i].doorlock_name+"</div><div class='text'>"+res[i].installed_place+"</div>"
			}

			$('ul').html(html);
			swipeFun();
		}  
	});
}


var noticeCount = function(data){

	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/count",
		dataType : "json",
		data : data,
		success:function(res){
			if(res.notice_count!=0){
				$("#notice_page").append(
					"<p class='count'>"+res.notice_count+"</p>"
				);
			}
		}
	});
}


var swipeFun = function(){
	window.mySwipe = $("#mySwipe").Swipe({
		startSlide:0,
		continuous:false,
		disableScroll:true,
		callback:function(index,element){},
		transitionEnd:function(index,element){}
	}).data('Swipe');
}


