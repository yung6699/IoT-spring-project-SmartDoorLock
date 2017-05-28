$(document).ready(function() {
	$('body').css('overflow-x', 'hidden');
	$("#cat_list_type_name").css("display","none");
	initPage();
	$.fn.extend({
	    animateCss: function (animationName,callBack) {
	        var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
	        $(this).addClass('animated ' + animationName).one(animationEnd, function() {
	            $(this).removeClass('animated ' + animationName);
	            callBack();
	        });
	    }
	});
	
	
});

function initPage(){
	var pageData={
		email : $("#page_data input[name=email]").val(),
		cat_id : $("#page_data input[name=cat_id]").val()
	};
	console.log(pageData);
	loadPageInfo(pageData);	
}

var interval="";
var toggle=true;

function loadPageInfo(pageData){
	$.ajax({
		url:"/"+pageData.email+"/category/"+pageData.cat_id+"/modify.json",
		type:"post",
		beforeSend:function(){
			$("#key_list").html("");
			$("#category_list").html("");
			$("#cat_list_type_img").attr("src","");
			$("#cat_list_type_text").html("");
			$("#cat_list_name").html("");
			$("#cat_list_type_name").html("");
			
		},
		success:function(response){
			
			console.log(response);
			$("#cat_list_name").html("카테고리 이름:    "+response.CAT_LIST.cat_name+"");
			$("#cat_list_type_name").html("카테고리 타입:  "+response.CAT_LIST.type_name);
			$("#cat_list_type_img").attr("src","/resources/images/pages/category/"+keyIcon(response.CAT_LIST.type));
			$("#cat_list_type_text").html(response.CAT_LIST.type_name);
			var keys = response.KEY_MST;
			for(var j in response.CAT_KEY){
				for(var i in response.KEY_MST){
					if(response.CAT_KEY[j].key_id == response.KEY_MST[i].key_id){
						$("#category_list").append(
							 '<li class="row" data-key-id="'+response.KEY_MST[i].key_id+'">'
							+'	<div class="col-xs-12">'
							+'		<span>' 
							+'			<span class="attr">열쇠 이름 : </span> <span class="value" >'+response.KEY_MST[i].key_name+'</span>'
							+'		</span>'
							+'		<br/>'
							+'		<span> '
							+'			<span class="attr">도어락 주인 : </span> <span class="value">'+response.KEY_MST[i].owner_name+'</span>'
							+'		</span> '
							+'		<br /> '
							+'		<span> '
							+'			<span class="attr">도어락 위치 : </span> <span class="value">'+response.KEY_MST[i].installed_place+'</span>'
							+'		</span>'
							+'	</div>'
							+'	<div style="padding: 10px 10px 0px 0px;" class="image_right">'
							+'		<img src="/resources/images/pages/category/'+keyIcon(response.KEY_MST[i].grade)+'" width="46" height="46"/>'
							+'	</div>'
							+'</li>');
						keys.splice(keys.indexOf(response.KEY_MST[i]), 1);
					}
				}
			}
			for(var i in keys){
				$("#key_list").append(
					 '<li class="row" data-key-id="'+keys[i].key_id+'">'
					+'	<div class="col-xs-12">'
					+'		<span>' 
					+'			<span class="attr">열쇠 이름 : </span> <span class="value" >'+keys[i].key_name+'</span>'
					+'		</span>'
					+'		<br/>'
					+'		<span> '
					+'			<span class="attr">도어락 주인 : </span> <span class="value">'+keys[i].owner_name+'</span>'
					+'		</span> '
					+'		<br /> '
					+'		<span> '
					+'			<span class="attr">도어락 위치 : </span> <span class="value">'+keys[i].installed_place+'</span>'
					+'		</span>'
					+'	</div>'
					+'	<div style="padding: 10px 10px 0px 0px;" class="image_right">'
					+'		<img src="/resources/images/pages/category/'+keyIcon(keys[i].grade)+'" width="46" height="46"/>'
					+'	</div>'
					+'</li>'
				);
			}
			for(var j in keys){
				delete keys[j].crt_dt;
				delete keys[j].crt_email;
				delete keys[j].updt_dt;
				delete keys[j].updt_email;
				delete keys[j].expire_date;
				delete keys[j].start_date;
				delete keys[j].email;
				delete keys[j].sort;
				delete keys[j].member_id;
				delete keys[j].key_id;
			}
			$("#searchKey").unbind('keyup');
			$("#searchKey").bind('keyup',function(event){
				if(this.value ==''){
					$("#key_list").html();
					for(var i in keys){
						$("#key_list").append(
							 '<li class="row" data-key-id="'+keys[i].key_id+'">'
							+'	<div class="col-xs-12">'
							+'		<span>' 
							+'			<span class="attr">열쇠 이름 : </span> <span class="value" >'+keys[i].key_name+'</span>'
							+'		</span>'
							+'		<br/>'
							+'		<span> '
							+'			<span class="attr">도어락 주인 : </span> <span class="value">'+keys[i].owner_name+'</span>'
							+'		</span> '
							+'		<br /> '
							+'		<span> '
							+'			<span class="attr">도어락 위치 : </span> <span class="value">'+keys[i].installed_place+'</span>'
							+'		</span>'
							+'	</div>'
							+'	<div style="padding: 10px 10px 0px 0px;" class="image_right">'
							+'		<img src="/resources/images/pages/category/'+keyIcon(keys[i].grade)+'" width="46" height="46"/>'
							+'	</div>'
							+'</li>'
						);
					}
				}else{
					var ary=[];
					for(var k in keys){
						for(var l in keys[k]){
							if(keys[k][l].match(this.value)){
								ary.push(keys[k]);
								break;
							}
						}
					}
					$("#key_list").html(" ");
					for(var i in ary){
						$("#key_list").append(
							 '<li class="row" data-key-id="'+ary[i].key_id+'">'
							+'	<div class="col-xs-12">'
							+'		<span>' 
							+'			<span class="attr">열쇠 이름 : </span> <span class="value" >'+ary[i].key_name+'</span>'
							+'		</span>'
							+'		<br/>'
							+'		<span> '
							+'			<span class="attr">도어락 주인 : </span> <span class="value">'+ary[i].owner_name+'</span>'
							+'		</span> '
							+'		<br /> '
							+'		<span> '
							+'			<span class="attr">도어락 위치 : </span> <span class="value">'+ary[i].installed_place+'</span>'
							+'		</span>'
							+'	</div>'
							+'	<div style="padding: 10px 10px 0px 0px;" class="image_right">'
							+'		<img src="/resources/images/pages/category/'+keyIcon(ary[i].grade)+'" width="46" height="46"/>'
							+'	</div>'
							+'</li>'
						);
					}
					console.log(ary);
				}
			});

			
		},
		error : function(request, status, errorThrown) {
			console.log(request);
			console.log(status);
			console.log(errorThrown);
		},
		complete:function(){

			draggable();
			clearInterval(interval);
			interval = setInterval(function(){
				if(toggle){
					toggle = false;
					$("#cat_list_type_img").animateCss("bounceOutLeft",function(){
						$("#cat_list_type_img").css("display","none");
						$("#cat_list_type_text").css("display","block");
						$("#cat_list_type_text").animateCss("bounceInRight",function(){});	
					});
				}else{
					toggle = true;
					$("#cat_list_type_text").animateCss("bounceOutLeft",function(){
						$("#cat_list_type_img").css("display","block");
						$("#cat_list_type_text").css("display","none");
						$("#cat_list_type_img").animateCss("bounceInRight",function(){});	
					});
				}
			}, 3000);
			
		}
	});
}

function deleteCategory(){
	var pageData={
			email : $("#page_data input[name=email]").val(),
			cat_id : $("#page_data input[name=cat_id]").val()
		};
	if(window.confirm("해당 카테고리를 삭제합니다. \n!!두 번 다시 복구 할 수 없게 됩니다.")){
		$.ajax({
			url:"/"+pageData.email+"/category/"+pageData.cat_id+"/modify/delete.do",
			type:"post",
			success:function(response){
				if(response.AJAX_RESULT=='AJAX_SUCCESS'){
					location.replace("/"+pageData.email+"/category");
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
		alert("카테고리 삭제를 취소하였습니다.");;
	}
}








function draggable(data){
	var adjustment;
	$("#key_list").sortable({

		group : 'list-unstyled',
		pullPlaceholder : true,
		placeholder: '<div class="sortable_cursor"><i class="fa fa-paper-plane"/></div>',
		// animation on drop
		onDrop : function($item, container, _super) {
			var $clonedItem = $('<li/>').css({
				height : 3
			});
			$item.before($clonedItem);
			$clonedItem.animate({
				'height' : $item.height()
			});

			$item.animate($clonedItem.position(), function() {
				$clonedItem.detach();
				_super($item, container);
			});
		},

		// set $item relative to cursor position
		onDragStart : function($item, container, _super) {
			var offset = $item.offset(), pointer = container.rootGroup.pointer;
			adjustment = {
				left : pointer.left - offset.left,
				top : pointer.top - offset.top
			};
			_super($item, container);
		},
		onDrag : function($item, position) {
			$item.css({
				left : position.left - adjustment.left,
				top : position.top - adjustment.top
			});
		}
	});
	var adjustment2;
	$("#category_list").sortable({

		group : 'list-unstyled',
		pullPlaceholder : true,
		placeholder: '<div class="sortable_cursor"><i class="fa fa-paper-plane"/></div>',
		// animation on drop
		onDrop : function($item, container, _super) {
			var $clonedItem = $('<li/>').css({
				height : 3
			});
			$item.before($clonedItem);
			$clonedItem.animate({
				'height' : $item.height()
			});

			$item.animate($clonedItem.position(), function() {
				$clonedItem.detach();
				_super($item, container);
			});
		},

		// set $item relative to cursor position
		onDragStart : function($item, container, _super) {
			var offset = $item.offset(), pointer = container.rootGroup.pointer;
			adjustment2 = {
				left : pointer.left - offset.left,
				top : pointer.top - offset.top
			};
			_super($item, container);
		},
		onDrag : function($item, position) {
			$item.css({
				left : position.left - adjustment2.left,
				top : position.top - adjustment2.top
			});
		},
	});
}


function submitCategory(){
	var pageData={
			email : $("#page_data input[name=email]").val(),
			cat_id : $("#page_data input[name=cat_id]").val()
		};
	var dataForm = {
		category : categoryData = $("#category_list").sortable("serialize").get()[0]
	};
	console.log(dataForm);
	
	$.ajax({
		url:"/"+pageData.email+"/category/"+pageData.cat_id+"/modify/submit.do",
		type:"post",
		data:{json:JSON.stringify(dataForm)},
		success:function(response){
			if(response.AJAX_RESULT='AJAX_SUCCESS'){
				alert("성공적으로 반영 되었습니다.");
				location.href="/"+pageData.email+"/category/"+pageData.cat_id;
			}else{
				alert("카테고리 저장에 실패하였습니다.");
				location.replace("/"+pageData.email+"/category/"+pageData.cat_id+"/modify");
			}
		},
		error : function(request, status, errorThrown) {
			console.log(request);
			console.log(status);
			console.log(errorThrown);
		}
	});
}




//STATE 및 TYPE 을 입력하면 이미지를 반환함
function keyIcon(code){
	switch(code){
		case 'MEMBER':
			return 'member.svg';
			break;
		case 'MANAGER':
			return 'manager.svg';
			break;
		case 'MASTER':
			return 'master.svg';
			break;
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


