$(function () {
  var flag1 = 0,
    flag2 = 0,
    flag3 = 0
  // 获得焦点会提示输入的内容
  $('input').focus(function () {
    if (!$(this).val()) {
      $(this).next().css('display', 'block')
      $(this).next().find('.rf-icon').css('background-position', '0 -200px')
      $(this).next('.tip1').find('.rf-txt').removeClass('level1').text('请输入手机号')
      $(this).next('.tip2').find('.rf-txt').removeClass('level1').text('请输入密码')
      $(this).next('.tip3').find('.rf-txt').removeClass('level1').text('请输入密码')
      $(this).next('.tip4').find('.rf-txt').removeClass('level1').text('请输入验证码')
    }
  })

  $('input').blur(function () {
    // 失去焦点如果输入框内容为空会提示不能为空
    if (!$(this).val()) {
      $(this).next().find('.rf-icon').css('background-position', '0 -250px')
      $(this).next('.tip1').find('.rf-txt').addClass('level1').text('手机号不能为空')
      $(this).next('.tip2').find('.rf-txt').addClass('level1').text('密码不能为空')
      $(this).next('.tip3').find('.rf-txt').addClass('level1').text('确认密码不能为空')
      $(this).next('.tip4').find('.rf-txt').addClass('level1').text('验证码不能为空')
    }
  })

  // 验证手机号码
  $('#phone').blur(function () {
    if ($(this).val().length > 0) {
      // 电话号码正则匹配，手机号长度11位，以13/14/15/16/17/18/19开头
      var off = /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test($(this).val())
      // 电话号码正确
      if (off) {
        // // 判断此账号是否存在用户数据库中，如果手机号已经存在就提示可以直接登录
        // for (let i = 0; i < users.length; i++) {
        //   if ($(this).val() == users[i]['phone']) {
        //     $(this).next('.tip1').find('.rf-icon').css('background-position', '0 -250px')
        //     $(this).next('.tip1').find('.rf-txt').addClass('level1').text('手机号已经注册，可以直接登录')
        //     flag1 = 0
        //     break
        //   } else {
        //     // 不存在就提示该电话号码正确
        //     $(this).next('.tip1').find('.rf-icon').css('background-position', '0 0')
        //     $(this).next('.tip1').find('.rf-txt').text('')
        //     flag1 = 1
        //   }
        // }
        $(this).next('.tip1').find('.rf-icon').css('background-position', '0 0')
        $(this).next('.tip1').find('.rf-txt').text('')
        flag1 = 1
      } else {
        // 电话号码错误出现提示
        $(this).next('.tip1').find('.rf-icon').css('background-position', '0 -250px')
        $(this).next('.tip1').find('.rf-txt').text('手机号长度11位，以13/14/15/16/17/18/19开头直接登录')
        flag1 = 0
      }
    }
  })

  // 判断密码强弱
  function checkStrong(val) {
    let modes = 0
    if (val.length > 0 && val.length < 6) return 0
    if (/\d/.test(val)) modes++
    if (/[a-z]/.test(val)) modes++
    if (/[A-Z]/.test(val)) modes++
    if (/\W/.test(val)) modes++
    return modes
  }
  // 验证密码，键盘监听
  $('#password').keyup(function () {
    // 开始输入密码之后进行判断
    if ($('#password').val().length > 0) {
      // 密码长度在6-16之间时进行强弱判断并输出
      if ($('#password').val().length > 5 && $('#password').val().length < 17) {
        var num = checkStrong($('#password').val())
        // console.log(num)
        switch (num) {
          case 1:
            $(this).next('.tip2').find('.rf-icon').css('width', '0')
            $(this)
              .next('.tip2')
              .find('.rf-txt')
              .removeClass('level2')
              .removeClass('level3')
              .addClass('level1')
              .text('弱：您输入的密码强度过弱，请重新输入，试试字母、数字、常用符号的组合')
            break
          case 2:
            $(this).next('.tip2').find('.rf-icon').css('width', '0')
            $(this)
              .next('.tip2')
              .find('.rf-txt')
              .removeClass('level1')
              .removeClass('level3')
              .addClass('level2')
              .text('中：您的密码还可以更复杂')
            break
          case 3:
            $(this).next('.tip2').find('.rf-icon').css('width', '0')
            $(this)
              .next('.tip2')
              .find('.rf-txt')
              .removeClass('level1')
              .removeClass('level2')
              .addClass('level3')
              .text('强：您的密码很安全')
            break
        }
      } else {
        // 密码长度不超过6位数或者超过16位时提示
        $(this).next('.tip2').find('.rf-icon').css({ width: '16px', 'background-position': '0 -250px' })
        $(this)
          .next('.tip2')
          .find('.rf-txt')
          .removeClass('level2')
          .removeClass('level3')
          .addClass('level1')
          .text('请输入6-16位数字、字母或常用符号，字母区分大小写')
      }
    } else {
      // 密码删除完的时候提示密码不能为空
      $(this).next('.tip2').find('.rf-icon').css({ width: '16px', 'background-position': '0 -250px' })
      $(this).next('.tip2').find('.rf-txt').addClass('level1').text('密码不能为空')
    }
  })

  // 验证确认密码
  $('#passrepeat').on('focus keyup', function () {
    if ($(this).val().length > 0) {
      // 当密码为空的时候提示先输入密码
      if (!$('#password').val()) {
        $(this).next('.tip3').find('.rf-icon').css('background-position', '0 -250px')
        $(this).next('.tip3').find('.rf-txt').addClass('level1').text('请先输入密码')
      } else {
        // 密码不为空的时候验证是否和输入的密码一样
        if ($(this).val() === $('#password').val()) {
          $(this).next('.tip3').find('.rf-icon').css('background-position', '0 0')
          $(this).next('.tip3').find('.rf-txt').text('')
          flag2 = 1
        } else {
          $(this).next('.tip3').find('.rf-icon').css('background-position', '0 -250px')
          $(this).next('.tip3').find('.rf-txt').addClass('level1').text('两次密码不一致')
        }
      }
    }
  })

  // 随机生成验证码函数
  function rand(min, max) {
    return Math.floor(Math.random() * (max - min)) + min
  }
  // 点击随机生成验证码
  $('.messbtn').click(function () {
    var randnum = rand(1000, 9999)
    $('.yanzhengma').css('display', 'block').text(randnum)
    // 点击之后隐藏提示
    $(this).siblings('.tip4').css('display', 'none')
    // 生成验证码后检查验证码是否正确
    $('#mess').on('focus blur keyup', function () {
      $(this).siblings('.tip4').css('display', 'block')
      if ($(this).val() > 0) {
        // 验证码正确
        if ($(this).val() == randnum) {
          $(this).next('.tip4').find('.rf-icon').css('background-position', '0 0')
          $(this).next('.tip4').find('.rf-txt').text('')
          flag3 = 1
        } else {
          // 验证码错误提示
          $(this).next('.tip4').find('.rf-icon').css('background-position', '0 -250px')
          $(this).next('.tip4').find('.rf-txt').addClass('level1').text('验证码错误')
        }
      }
    })
  })
  // 生成验证码前检查验证码
  $('#mess').keyup(function () {
    if (!$('.yanzhengma').text()) {
      $(this).next('.tip4').find('.rf-icon').css('background-position', '0 -250px')
      $(this).next('.tip4').find('.rf-txt').addClass('level1').text('请先获取验证码')
    }
  })

  $('#reg_form').submit(function () {
    var flag = true; 
    // 如果哪个文本框没有输入内容则提示
    $('input').each(function () {
      if (!$(this).val()) {
        $(this).next().css('display', 'block')
        $(this).next().find('.rf-icon').css('background-position', '0 -250px')
        $(this).next('.tip1').find('.rf-txt').addClass('level1').text('手机号不能为空')
        $(this).next('.tip2').find('.rf-txt').addClass('level1').text('密码不能为空')
        $(this).next('.tip3').find('.rf-txt').addClass('level1').text('确认密码不能为空')
        $(this).next('.tip4').find('.rf-txt').addClass('level1').text('验证码不能为空')
        flag = false;
      }
    })
    return flag;
  })
  // 点击注册跳转页面
  /*$('#fastreg').click(function () {
    flag = flag1 + flag2 + flag3
    // 如果输入的东西都正确则注册成功
    if (flag == 3) {
      // console.log('注册')
      let $username = $('#phone').val()
      let $password = $('#password').val()
      $('.register-center-form').css('display', 'none')
      $('.reg-success').css('display', 'block')
      var s = 2
      // 倒计时3秒跳转到登录页面
      sobj = setInterval(function () {
        $('.second').text(s--)
        setCookie('phone', $username)
        setCookie('password', $password)
        if (s == 0) {
          window.location.href = './index.html'
        }
      }, 1000)
    } else {
      // 如果哪个文本框没有输入内容则提示
      $('input').each(function () {
        if (!$(this).val()) {
          $(this).next().css('display', 'block')
          $(this).next().find('.rf-icon').css('background-position', '0 -250px')
          $(this).next('.tip1').find('.rf-txt').addClass('level1').text('手机号不能为空')
          $(this).next('.tip2').find('.rf-txt').addClass('level1').text('密码不能为空')
          $(this).next('.tip3').find('.rf-txt').addClass('level1').text('确认密码不能为空')
          $(this).next('.tip4').find('.rf-txt').addClass('level1').text('验证码不能为空')

        }
      })
    }
  })*/
})
