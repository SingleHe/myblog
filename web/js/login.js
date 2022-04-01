$(function () {
  // 如果cookie有数据则点进去就是主页home.html
  iflogin()
  function iflogin() {
    if (getCookie('phone')) {
      window.location.href = './home.html'
    }
  }
  // 点击登录出现弹窗
  $('.log-btn').click(function () {
    $('.shadow_css,.login-alert').css('display', 'block')
  })
  // 关闭登录弹窗
  $('.close').click(function () {
    $('.shadow_css,.login-alert').css('display', 'none')
  })
  // 关闭提示
  $('.r-close').click(function () {
    $('.layer,.layer2').css('display', 'none')
  })

  // 在主页直接点击登录
  $('.btn1').click(function () {
    console.log(getCookie('phone'))
    console.log(getCookie('password'))

    let $phone = $(this).parents('.pwd-form').find('#loginname').val()
    let $password = $(this).parents('.pwd-form').find('#pwd').val()
    // 如果没有输入则提示
    if (!$phone) {
      // 提示请输入登录名
      $(this).parents('.login-inner').next('.layer').css({ display: 'block', top: '110px' })
      $(this).parents('.login-inner').next('.layer').find('.r-txt').text('请输入登录名')
      $(this).parents('.login-inner').next('.layer2').css({ display: 'block', top: '54px' })
      $(this).parents('.login-inner').next('.layer2').find('.r-txt').text('请输入登录名')
    } else if (!$password) {
      // 提示请输入密码
      $(this).parents('.login-inner').next('.layer').css({ display: 'block', top: '155px' })
      $(this).parents('.login-inner').next('.layer').find('.r-txt').text('请输入密码')
      $(this).parents('.login-inner').next('.layer2').css({ display: 'block', top: '99px' })
      $(this).parents('.login-inner').next('.layer2').find('.r-txt').text('请输入密码')
    } else {
      // 账号密码都输入了就开始验证
      // 匹配cookie数据
      if ($phone == getCookie('phone') && $password == getCookie('password')) {
        // 满足条件跳转页面
        window.location.href = './home.html'
      } else {
        // 匹配错误提示用户名或密码错误
        $(this).parents('.login-inner').next('.layer').css({ display: 'block', top: '110px' })
        $(this).parents('.login-inner').next('.layer').find('.r-txt').text('用户名或密码错误')
        $(this).parents('.login-inner').next('.layer2').css({ display: 'block', top: '54px' })
        $(this).parents('.login-inner').next('.layer2').find('.r-txt').text('用户名或密码错误')
      }
    }
  })
})
