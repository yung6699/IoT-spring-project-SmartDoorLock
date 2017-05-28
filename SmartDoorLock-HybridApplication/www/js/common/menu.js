$(document).ready(function() {
    
    var session = JSON.parse(window.localStorage.getItem('SESSION'));
    var data = {
        member_id:session.MEMBER_ID,
    };

     $('#mykey_page').click(function(event) {
        location.replace("../mykey/mykey.html#mykeyList_page");
        hideSidebar() ;
    });

    $('#mydoorLock_page').click(function(event) {
        location.replace("../mydoorLock/mydoorLock.html#mydoorLockList_page");
        hideSidebar() ;    
    });

    $('#notice_page').click(function(event) {
        location.replace("../notice/notice.html#noticeList_page");
        hideSidebar() ;
    });

    $('#doorRegister_page').click(function(event) {
        location.replace('../doorRegister/doorRegister.html');
        hideSidebar() ;    
    });

     $('#logout').bind('click', function(){
        localStorage.JUST_ONE_AUTO_LOGIN = false;
        logout(data);
        localStorage.removeItem("SESSION");
        localStorage.removeItem('my_email')
        location.replace('../login/login.html');
    });


    function showSidebar() {
        sidebar.css('margin-left', '0');
        //content.hide();
        overlay.show(0, function() {
            overlay.fadeTo('250', 0.4);
        });   
    }

    // hide sidebar and overlay
    function hideSidebar() {
        sidebar.css('margin-left', sidebar.width() * -1 + 'px');

        overlay.fadeTo('250', 0.4, function() {
            overlay.hide();
        });
    }

    // selectors
    var sidebar = $('[data-sidebar]');
    var button = $("#hd_menu");
    var overlay = $('[data-sidebar-overlay]');
    var content = $('[data-role=content]');


    overlay.parent().css('min-height', 'inherit');
    sidebar.css('margin-left', sidebar.width() * -1 + 'px');

    //사이드 메뉴 속도 조절
    sidebar.show(0, function() {
        sidebar.css('transition', 'all 0.2s ease');
    });

    //버튼 클릭시 메뉴 보여줌
    // toggle sidebar on click
    button.click(function() {
            showSidebar();

    });
    $('#hd_menu2').click(function(){
        showSidebar();
    });

    // 영역 클릭시 메뉴 접음
    // hide sidebar on overlay click
    overlay.click(function() {
        hideSidebar();
    });
});


var logout = function(data){
    $.ajax({
        type : "POST",
        url : GLOBAL_HOST+"/app/logout",
        dataType : "json",
        data : data,
        success:function(res){
            alert('autoLogin 해제.');
        }

    })
}