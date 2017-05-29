# 프로젝트 Git Repositories
 - [시스템(전체)](https://github.com/yung6699/SmartDoorLock) : (시스템 소개를 위해 존재하는 깃 repo) 
 - [웹, 서비스](../SmartDoorLock-WebApplication) : 관리자 페이지 이며 앱보다 더 넓은 관리기능 제공
 - [앱](../SmartDoorLock-HybridApplication) : 실제 도어락을 여는 주체이며 웹보다 적은 관리기능을 제공한다.
 - [하드웨어](../SmartDoorLock-Arduino) : 블루투스 모듈과 Wi-Fi모듈을 이용한 하드웨어 도어락
 - [배치 프로세스](../SmartDoorLock-BatchProcess) : 서비스에 발생된 로그를 분석하여 서비스 이용자에게 그래프를 제공한다.

# 아두이노 스마트 도어락 소개

- 아두이노, 도어락를 연결함으로써 아두이노가 도어락을 제어
- 아두이노에 블루투스 , WiFi 제어 기능 부여
- 앱과 아두이노가 블루투스 통신을 통해 데이터를 주고 받음
- 아두이노가 WiFi에 접속하여 서버와 HTTP 통신이 가능

   
# 하드웨어 이미지 

![하드웨어 이미지](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-Arduino/docs/Hardware.png)

# 주요 코드

 1. 도어락에 맞는 열쇠인지 판별하는 함수

  ``` c
	  	//SmartDoorLockHW.

		void keyExistHttpRequest(String key_id) {

 		 DATA = "";
   		 client.stop();
   		 String url = "POST /hw/key/check?serial_no="+SERIAL_NO+"&key_id="+key_id;

  		 if (client.connect(server1, PORT)) {

   			Serial.println(F("\nconnecting..."));
		    client.println(url);
		    client.println("HTTP/1.1");
		    client.println("Connection: close");
		    client.println();

		  }else {

		    Serial.println(F("connection failed")); 

		  }
		}
	

  ```

 2. 아두이노 SD카드에 등록된 열쇠 기록

  ``` c
		//SmartDoorLockHW.ino
		
		void writeFile(){
		   // 새로 갱신하기 위해 전에 있던 파일은 삭제한다.
		   if(SD.exists("key_list.txt")){
		       SD.remove("key_list.txt");  
		    }
		    
		   // 파일을 만들고 새로 받아온 JSON을 기록한다.
		    myFile = SD.open("key_list.txt", FILE_WRITE);
		    if (myFile) {
		      myFile.println(DATA);
		      myFile.close();
		      Serial.println(F("\nwrite success."));
		    } else {
		      Serial.println(F("error opening key_list.txt"));
		    }
		    DATA = "";
		}

  ```

# 기술 스택

 - 아두이노 스케치 프로그래밍
  - [Arduino IDE 활용](https://www.arduino.cc/en/Main/Software) 
  - 개발언어 : C

