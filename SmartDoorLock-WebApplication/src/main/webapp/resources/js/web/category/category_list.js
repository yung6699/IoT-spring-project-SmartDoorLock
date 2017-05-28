$(document).ready(function() {
	loadCategoryList()
});

function loadCategoryList(){
	var pageData={
			email : $("#page_data input[name=email]").val()
		};
	$.ajax({
		url:"/"+pageData.email+"/category/list.json",
		type:"POST",
		success:function(response){
			console.log(response);
			$("#category_list").html("");
			for(var i in response){
				$("#category_list").append(
					'<div class="'+keyColor(response[i].type)+' col-lg-3 col-md-4 col-sm-6 col-xs-12" onclick="categoryPage('+response[i].cat_id+');">'
					+'	<div class="well category">'
					+'		<center>'
					+'			<h2>'+response[i].cat_name+'</h2>'
					+'			<div class="image_right">'
					+'					<img src="/resources/images/pages/category/'+keyIcon(response[i].type)+'" width="46" height="46"/>'
					+'				</div>'
					+'		</center>'
					+'	</div>'
					+'</div>'
				);
			}			
		},
		error : function(request, status, errorThrown) {
			console.log(request);
			console.log(status);
			console.log(errorThrown);
		},
		complete : function(){
			$("#category_list").append(
			'<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12" onclick="categoryModalShow()">'
			+'	<div class="well category_add" style="text-align: center;">'
			+'		<h2 style="font-weight:bold">생성</h2>'
			+'		<i class="fa fa-plus-circle fa-3x"></i>'
			+'	</div>'
			+'</div>'
			);
		}
	});
}

function categoryPage(cat_id){
	location.href="/"+$("#page_data input[name=email]").val()+"/category/"+cat_id;
}

// 모달 관련 
function categoryModalShow(){
	categoryModalInit();
	$("#categoryModalCreate").modal("show");
}

function categoryCreate(){
	$("#categoryModalCreate").modal("hide");
	var dataForm = {
		cat_name:$("#category_name").val(),
		type:$("#category_type").val()
	};
	console.log(dataForm);
	if($.trim(dataForm.cat_name)!=""){
		if($.trim(dataForm.type) != "" ){
			$.ajax({
				url:"/"+$("#page_data input[name=email]").val()+"/category/create.do",
				type:"POST",
				data:dataForm,
				success:function(response){
					//AJAX_RESULT 와 / RESULT_MSG / CAT_ID 
					console.log(response);
					if(response.AJAX_RESULT=="AJAX_SUCCESS"){
						location.href="/"+$("#page_data input[name=email]").val()+"/category/"+response.CAT_LIST.cat_id+"/modify";
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
			alert("타입을 선택해 주세요");
		}
	}else{
		alert("카테고리 명을 입력해주세요.");
	}
}
function categoryModalInit(){
	$("#category_name").val("");
}
















//

function keyIcon(code){
	switch(code){
		case 'BASIC':
			return 'basic.png';
			break;
		case 'STORE_SECURITY':
			return 'store.png';
			break;
		case 'COMPANY_SECURITY':
			return 'company.png';
			break;
		case 'ACCOMMODATION':
			return 'accomo.png';
			break;
		default:
			return 'error.svg';
			break;
	}
}

function keyColor(code){
	switch(code){
		case 'BASIC':
			return 'cat_basic';
			break;
		case 'STORE_SECURITY':
			return 'cat_store';
			break;
		case 'COMPANY_SECURITY':
			return 'cat_company';
			break;
		case 'ACCOMMODATION':
			return 'cat_accomo';
			break;
		default:
			return 'cat_error';
			break;
	}
}