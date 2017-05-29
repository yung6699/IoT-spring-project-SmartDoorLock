# 프로젝트 Git Repositories
 - [시스템(전체)](https://github.com/yung6699/SmartDoorLock) : (시스템 소개를 위해 존재하는 깃 repo) 
 - [웹, 서비스](../SmartDoorLock-WebApplication) : 관리자 페이지 이며 앱보다 더 넓은 관리기능 제공
 - [앱](../SmartDoorLock-HybridApplication) : 실제 도어락을 여는 주체이며 웹보다 적은 관리기능을 제공한다.
 - [하드웨어](../SmartDoorLock-Arduino) : 블루투스 모듈과 Wi-Fi모듈을 이용한 하드웨어 도어락
 - [배치 프로세스](../SmartDoorLock-BatchProcess) : 서비스에 발생된 로그를 분석하여 서비스 이용자에게 그래프를 제공한다.

# 하이브리드 앱 서비스 프로젝트 소개

- 블루투스 통신을 활용한 도어락 컨트롤러 역할 수행
- 열쇠 소유 및 관리 기능 제공
- 한 사람이 여러 도어락에 대한 다양한 종류의 권한을 받을 수 있음
- 한 도어락이 여러사람에게 다양한 종류의 권한을 부여할 수 있음
- 사용자:도어락 => n:m의 관계를 갖음

# 도어락에 대한 권한별 기능 이용 범위

![권한별 이용 범위](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-HybridApplication/docs/authfunction.png)

   
# 앱 UI 

![앱 UI](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-HybridApplication/docs/app.png)

# 주요 코드

 1. 내가 소유한 열쇠 가져오기

  ``` javascript 
		//mykey.js

		$.ajax({
			type : "post",
			url : GLOBAL_HOST+"/app/mykey/list",
			dataType : "json",
			cache : false,
			async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : data,
			success : function(res){
				$("#mykeyList").empty();
				$.each(res, function(key, object){

					if(object.grade_name=='마스터'){
						img=master;
					}else if(object.grade_name=='매니저'){
						img=manager;
					}else{
						img=user;
					}

					$("#mykeyList").append(
						"<li><a href='#'>"
						+img
						+"<h3 id='key_name'>"+object.key_name+"</h3>" /*열쇠 이름*/
						+"<div style='display:none' id='key_id'>"+object.key_id+"</div>" /*열쇠 난수 */
						+"<div style='display:none' class='bluetooth_id'>"+object.bluetooth_id+"</div>" /*열쇠 난수 */
						+"<div style='display:none' id='grade_name'>"+object.grade_name+"</div>" /*열쇠 등급*/
						+"<div id='serial_no'>"+object.serial_no+"</div>" /* 열쇠가 등록된 도어락 시리얼*/
						+"<div style='display:none' id='crt_dt'>"+object.crt_dt+"</div>" /*열쇠 부여 날짜*/
						+"<div style='display:none' id='crt_email'>"+object.crt_email+"</div>" /*열쇠를 부여한 사람 이메일*/
						+"<div style='display:none' id='start_date'>"+object.start_date+"</div>"
						+"<div style='display:none' id='expire_date'>"+object.expire_date+"</div>"
						+"</a></li>"
					);
					
					$("#mykeyList").listview("refresh");
				});

			} , error:function(){
				alert("error");
			}
		});
	});
	

  ```

 2. 내가 소유한 도어락 가져오기

  ``` javascript 
		//mydoorLock.js.js

    	$('#mydoorLockList_page').on("pagebeforeshow",function(){

		if(localStorage.getItem('DOORLOCK_INFO')!=null ){
			localStorage.removeItem('DOORLOCK_INFO');
		}

		session = JSON.parse(window.localStorage.getItem('SESSION'));
		
		var data = {
			member_id : session.MEMBER_ID,
			email : session.EMAIL
		}

		var master = '<img src="../../img/mydoorLock/master_black.svg" id="master"/>',
		manager = '<img src="../../img/mydoorLock/manager_black.svg" id="manager"/>',	
		img;

		$.ajax({
			type : "post",
			url : GLOBAL_HOST+"/app/mydoorlock/list",
			dataType : "json",
			cache : false,
			async : true,  //true = 비동기로 ajax 통신을 하겠다. sync 동기로 통신을 하겠다.
			data : data,
			success : function(res){
				console.log(res);
				$("#mydoorLockList").empty();
				$.each(res, function(key, object){
					if(object.grade_name=='마스터'){
						img=master;
					}else{
						img=manager;
					}

					$("#mydoorLockList").append(
						"<li style='padding-top:0.25em;padding-right:0.25em;padding-left:0.25em;' >"
						+"<a href='#'>"+img
						+"<h3 class='doorLock_name'>"+object.doorlock_name+"</h3>"
						+"<div class='serial_no'>"+object.serial_no+"</div>"
						+"<div class='key_name'>"+object.key_name+"</div>"
						+"<div class='installed_place'>"+object.installed_place+"</div>"
						+"<div style='display:none' class='doorlock_data'  data-doorlock=\'"+JSON.stringify(object)+"\'' ></div>"
						+"</a></li>"
					);
				});
				$("#mydoorLockList").listview("refresh");

			},error : function(request, status, error) {
				console.log(JSON.stringify(request));
				console.log(JSON.stringify(status));
				console.log(JSON.stringify(error));
			}
		});
	});

  ```

# 산출문서

 - [UI 정의서](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-HybridApplication/docs/Mobile-UI.pdf)

# 기술 스택

 - 모바일 하이브리드 앱
  - [Apache Cordova 활용](https://cordova.apache.org/) 
  - HTML5, CSS3, jQuery, jQuery Mobile

