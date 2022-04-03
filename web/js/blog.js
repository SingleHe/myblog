$(function () {
  // 实现点击关注
  $('.opt').click(function () {
    $(this).children().toggleClass('hid')
  })
  //  实现收藏变颜色
  $('#hascollect').click(function () {
    $('.hid').toggleClass('show')
  })

  //实现点赞博客变颜色，数字+1
  $('.row li')
    .eq(2)
    .click(function () {
      $(this).toggleClass('like')
      // 判断是否已经点赞过
      if ($(this).hasClass('like')) {
        $(this).children().children().eq(1).html('323')
      } else {
        $(this).children().children().eq(1).html('322')
      }
    })

  // 留言板下面没人评论则显示empty
  let value = $('.repo-list').children().length
  if (value == 0) {
    $('.empty').removeClass('hid')
  } else {
    $('.empty').addClass('hid')
  }
})
