var dataForm;

$(function(){
	dataForm ={
			email :	$("#login_email").text()
	}
	//alert(dataForm.email);
	if(dataForm.email != 'web'){
		$.ajax({
			type:"post",
			url:"/"+$("#login_email").text()+"/newspeed",
			dataType:"json",
			data:dataForm,
			success:function(res){
				//$("#newspeedList").empty();
				$.each(res,function(key,val){
					

					if(val.state == "STEAL_KEY_MEMBER"){
						message = "의 열쇠를 삭제했습니다.";
					}else if(val.state == "GRANTED_KEY_MEMBER"){
						message = "에게 열쇠를 부여했습니다.";
					}else if(val.state == "GET_KEY_MEMBER"){
						message = "이  부여한 열쇠를 수락했습니다.";
					}
					else{
						message = "null";
					}

					$("#newspeedList").append(
							'<li class=\"row\">'+
								//'<div class=\"col-xs-9 col-sm-10 col-md-9 col-lg-10\">'+
									'<div>'+
									'<img src=\"../../resources/images/chess_knight_gray.svg\"/>	'+
									'</div>'+
									'<div style=\"width:100%;\" id=\"row_div\">'+
										'<span style=\"float:right;\">'+val.log_date+'</span>'+
										'</span><br><br>'+
										'<span>'+val.send_email+' 님이 </span>'+
										'</span>'+
										'<span>'+val.recv_email+' 님</span>'+
										'</span>'+
										'<span>'+message+'</span>'+
										'</span><br>'+
										'<span>('+val.state+')</span>'+
										'</span><br>'+
										'<span>('+val.serial_no+')</span>'+
										'</span><br>'+
									'</div>'+
								//'</div>'+
							'</li>'
					);
					if(val.state == "GRANT_KEY_MEMBER"){
						$("#row_div").append(
								"<div>"+
									"<input type='button' value= '수락' onclick='bt_click_yes(this.parentNode"+","+val.log_no+",\""+val.send_email+"\",\""+val.recv_email+"\",\""+val.serial_no+"\",\"MEMBER\");/>"+
									"<input type='button' value= '거절' onclick='bt_click_no(this.parentNode"+","+val.log_no+",\""+val.send_email+"\",\""+val.recv_email+"\",\""+val.serial_no+"\",\"MEMBER\");/>"+
								"</div>"
						);
					}else if(val.state == "GRANT_KEY_MANAGER"){
						$("#row_div").append(
								"<div>"+
									"<input type='button' value= '수락' onclick='bt_click_yes(this.parentNode"+","+val.log_no+",\""+val.send_email+"\",\""+val.recv_email+"\",\""+val.serial_no+"\",\"MANAGER\");/>"+
									"<input type='button' value= '거절' onclick='bt_click_no(this.parentNode"+","+val.log_no+",\""+val.send_email+"\",\""+val.recv_email+"\",\""+val.serial_no+"\",\"MANAGER\");/>"+
								"</div>"
						);
					}
				});
			},
			error:function(req,stat,err){
				$("#newspeedList").append(
						'<p>'+req+'</p><br>'+
						'<p>'+stat+'</p><br>'+
						'<p>'+err+'</p><br>'
				);
			}
		});
	}

});

function bt_click_yes(elmnt,logNo,send,recv,serial_no,grade){
	elmnt.remove();
	var yes='1';
	var datas={
		log_no : logNo,
		answer : yes,
		send_email : recv,
		recv_email : send,
		grade : grade,
		serial_no : serial_no
	}
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/response",
		data : datas,
		dataType : "json",
		success : function(obj){
			$("#notice_list").listview("refresh");
		},error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});

}
function bt_click_no(elmnt,logNo,send,recv,serial_no,grade){
	elmnt.remove();
	var no='2';
	var datas={
		log_no : logNo,
		answer : no,
		send_email : recv,
		recv_email : send,
		grade : grade,
		serial_no : serial_no
	}
	$.ajax({
		type : "POST",
		url : GLOBAL_HOST+"/app/notice/response",
		data : datas,
		dataType : "json",
		success : function(obj){
			$("#notice_list").listview("refresh");
		},error:function(request,status,error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

//function newspeed_list(){
//	var dataForm ={
//			email :	$("#login_email").text()
//	}
//	if(dataForm.email != 'web'){
//		$.ajax({
//			type:"post",
//			url:"/"+$("#login_email").text()+"/newspeed_list",
//			dataType:"json",
//			data:dataForm,
//			success:function(res){
//				$.each(res,function(key,val){
//					$("#news_list").append(
//							'<li class=\"row\">'+
//								'<div class=\"col-xs-9 col-sm-10 col-md-9 col-lg-10\">'+
//									'<span class=\"value\">'+res.state+'</span>'+
//									'</span><br />'+
//								'</div>'+
//							'</li>'
//					);
//				});
//			},
//			error:function(req,stat,err){
//				//alert(req+","+stat+","+err);
//				$("#news_list").append(
//						'<p>'+req+'</p><br>'+
//						'<p>'+stat+'</p><br>'+
//						'<p>'+err+'</p><br>'
//						);
//			}
//		});
//	}
//}
