var key_id = null, 
      bluetooth_id = null,
app = {
     initialize: function(data, bluetooth) {
            key_id = data;
            bluetooth_id = bluetooth;
            this.bindEvents();
      },

     bindEvents: function() {
           bluetoothSerial.enable(app.connect(),
                    function() {
                        alert("블루투스를 켜세요");
                    }
            );
     },

     connect: function() {
            var onConnect = function() {
                    app.sendData();
            };
            
            bluetoothSerial.connect(bluetooth_id, onConnect, function(){
                alert('열쇠를 다시 확인하세요');
            });
    },

           
     sendData: function() { 
                var success = function() {
                         bluetoothSerial.read(function (data) {
                          console.log(data);
                        }, failure);
                        bluetoothSerial.disconnect(function(){
                            console.log("key_id 전송 완료!!!");
                        }, app.onError); 
                },

                failure = function() {
                    bluetoothSerial.disconnect(function(){
                            console.log("key_id 전송 실패!!!");
                    }, app.onError);
                    alert("전송에 실패하였습니다.");

                };

                console.log("key_id : " + key_id);
                bluetoothSerial.write(key_id, success, failure)
     },

     onError: function(reason) {
        alert("도어락에 블루투스 연결이 되지 않았습니다.");
    }
};




