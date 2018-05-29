$(function() {

  var $fullText = $('.admin-fullText');
  $('#admin-fullscreen').on('click', function() {
    $.AMUI.fullscreen.toggle();
  });

  $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
    $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
  });


  var dataType = $('body').attr('data-type');
  for (key in pageData) {
    if (key == dataType) {
      pageData[key]();
    }
  }

  $('.tpl-switch').find('.tpl-switch-btn-view').on('click', function() {
    $(this).prev('.tpl-switch-btn').prop("checked", function() {
      if ($(this).is(':checked')) {
        return false
      } else {
        return true
      }
    })
    // console.log('123123123')

  })
})
// ==========================
// 侧边导航下拉列表
// ==========================

$('.tpl-left-nav-link-list').on('click', function() {
  $(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80)
    .end()
    .find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
})
// ==========================
// 头部导航隐藏菜单
// ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
  $('.tpl-left-nav').toggle();
  $('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
})


function logout() {
  $.ajax({
    type: "post",
    contentType: "application/json",
    url: "/logout",
    success: function(msg) {
      if (msg == "退出成功") {
        location.href = "/admin/login.html";
      } else {
        alert("error!");
      }
    }
  })
}


//获取教师名
$.ajax({
  type: "get",
  url: "/user/getUserInfo",
  dataType: "json",
  contentType: "application/json",
  success: function(result) {
    document.getElementById('name').innerHTML = result.name;
    //alert(result.name);
  }
})

//获取课程名
$.ajax({
  type: "get",
  url: "/course/getCourseList",
  dataType: "json",
  contentType: "application/json",
  success: function(result) {
    $.each(result, function(i, item) {
      document.getElementById('course_name').innerHTML = item.courseName;

    })
  }
})



function get_new_msg() {
  $.ajax({
    type: "get",
    // url: "/course/getCourseList",
    dataType: "json",
    contentType: "application/json",
    success: function(result) {
      // $.each(result, function(i, item) {
      //     document.getElementById('course_name').innerHTML = item.courseName;
      //
      // })
    }
  })
}
