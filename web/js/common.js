$(function () {
  // 左边导航条点击效果
  $('.nav_item').click(function () {
    $(this).addClass('current')
    var $other = $(this).parent().siblings().children()
    if ($other.hasClass('current')) {
      $other.removeClass('current')
    }
  })

  // 右边登录框的固定效果
  $(window).scroll(function () {
    // console.log($(this).scrollTop())
    if ($(this).scrollTop() >= 1400) {
      $('.login-box').css({ position: 'fixed', top: '50px' })
    } else {
      $('.login-box').css({ position: '', top: '' })
    }
  })

  // 所有登录框点击切换橙色的小条条和登录方式
  $('.tab').click(function () {
    $(this).addClass('cur')
    $(this).siblings('.tab').removeClass('cur')
    // console.log($(this).text())
    if ($(this).text() == '帐号登录') {
      // console.log(1)
      $(this).parent().siblings().addClass('hid')
      $(this).parent().siblings('.pwd-form').removeClass('hid')
    } else {
      // console.log(2)
      $(this).parent().siblings().addClass('hid')
      $(this).parent().siblings('.ewm-form').removeClass('hid')
    }
  })
  // 点击手机短信显示手机短信登录框
  $('.by-ewm').click(function () {
    $(this).parent().find('.tab').removeClass('cur')
    $(this).parent().siblings().addClass('hid')
    $(this).parent().siblings('.msg-form').removeClass('hid')
  })

  //直达顶部按钮和刷新按钮固定效果代码
  $(window).scroll(function () {
    // $(document).height()=2384px，底部高度=320px，totop本身高度为40px
    // 当滚动条快到底部的时候给totop设置绝对定位，绝对定位后元素top一直固定等于h2；flush的top就等于h1。
    var h2 = $(document).height() - 320 - 40 - 20
    var h1 = h2 - 50
    //a是页面快滑到底部时的scrollTop()值
    var a = $(document).height() - $(window).height() - 320
    // 还没滚动的时候totop不显示
    if ($(this).scrollTop() == 0) {
      $('#totop').css('visibility', 'hidden')
    }
    // 滚动条快到底部的时候换成absolute绝对定位，离文档顶部的值固定就可以
    else if ($(this).scrollTop() > a) {
      $('#flush').css({ position: 'absolute', top: h1 + 'px', bottom: 'auto' })
      $('#totop').css({ visibility: 'visible', position: 'absolute', top: h2 + 'px', bottom: 'auto' })
    } // 滚动条还没到底部之前一直用fixed定位，在页面上固定
    else {
      $('#flush').css({ position: 'fixed', bottom: '90px', top: 'auto' })
      $('#totop').css({ visibility: 'visible', position: 'fixed', bottom: '40px', top: 'auto' })
    }
  })

  //点击直达顶部效果
  $('#totop').click(function () {
    $(window).scrollTop(0)
  })

  // 点击刷新页面效果
  $('#flush').click(function () {
    window.location.reload()
  })
})
