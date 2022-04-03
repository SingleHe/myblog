$(function () {
  // 点击按钮发布评论
  $('.c-btn').click(function () {
    // 获取文本框里输入的内容
    let replytxt = $('.txt-input').val()

    // 获取现在的时间
    let date = new Date()
    let month = date.getMonth() + 1
    let day = date.getDate()
    let hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
    let minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
    let currentTime = month + '月' + day + '日  ' + hour + ':' + minute
    let replydate = currentTime

    // 写下要插入评论区模块的结构
    let $replyComment = $(
      '<div class="reuser-info clearfix"><div class="ri-img"><img src="./img/lv4.jpg"></div><div class="ri-content "><div class="ri-text"><a class="userid" href="">三分月色</a><span> : ' +
        replytxt +
        '</span></div><div class="ri-data clearfix"><div class="repo-date">' +
        replydate +
        '</div><div class="repo-fun clearfix"><span class="appr"><a href="javascript:void(0)"><i class="iconfont icon-dianzan"> </i><i>赞</i></a></a></span><span class="reply"><a href="javascript:void(0)">回复</a></span><span class="delete"><a href="javascript:void(0)">删除</a></span></div></div><div class="reply-wrap hid clearfix"><span class="arrow2"></span><div class="publish"><div id="input"><textarea type="text" placeholder="回复 @三分月色" maxlength="150"  class="rtxt-input" ></textarea></div></div><div class="c-btn2">评论</div></div></div></div>'
    )

    // 插入评论区
    $('.repo-list').append($replyComment)
    // 发布成功后输入框清空
    $('.txt-input').val('')
    // 限制字数重置
    $('.limit').html('150')
    // 重置按钮
    $('.c-btn').css({
      backgroundColor: '#ffc09f',
      borderColor: '#ffc09f'
    })

    // 留言板下面没评论则显示empty，反之隐藏empty
    let value = $('.repo-list').children().length
    if (value == 0) {
      $('.empty').removeClass('hid')
    } else {
      $('.empty').addClass('hid')
    }
  })

  // 用事件委托给所有元素（包括后面动态添加的）绑定好点击事件
  // 点击回复
  $('.repo-list').on('click', '.reply', function () {
    $(this).parents('.ri-content').children('.reply-wrap').toggleClass('hid')
  })

  // 点击删除评论
  $('.repo-list').on('click', '.delete', function () {
    if (confirm('你确定删除吗？')) {
      $(this).parents('.reuser-info').remove()

      // 留言板下面没评论则显示empty，反之隐藏empty
      let value = $('.repo-list').children().length
      if (value == 0) {
        $('.empty').removeClass('hid')
      } else {
        $('.empty').addClass('hid')
      }
    }
  })

  // 点赞评论
  $('.repo-list').on('click', '.appr', function () {
    $(this).children().toggleClass('col')
    // 判断是否已经点赞过
    if ($(this).children().hasClass('col')) {
      $(this).children().children().eq(1).html('1')
    } else {
      $(this).children().children().eq(1).html('赞')
    }
  })

  // 输入框限制字数
  $('.txt-input').keydown(function () {
    // 剩余可输入的字个数
    let limit = 150 - $(this).val().length
    // 根据可输入字的个数决定右下角文本的提示
    $('.limit').html(limit)

    // 根据有无内容决定发布按钮的颜色
    if ($('.txt-input').val().length == 0) {
      $('.c-btn').css({
        backgroundColor: '#ffc09f',
        borderColor: '#ffc09f'
      })
    } else {
      $('.c-btn').css({
        backgroundColor: '#f7671d',
        borderColor: '#f7671d'
      })
    }
  })
})
