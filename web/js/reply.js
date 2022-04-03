$(function () {
  $('.repo-list').on('click', '.c-btn2', function () {
    console.log(1)
    // 获取文本框里输入的内容
    let replytxt = $(this).prev().find('.rtxt-input').val()

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
      '<div class="reuser-info clearfix"><div class="ri-img"><img src="./img/lv4.jpg"></div><div class="ri-content "><div class="ri-text"><a class="userid" href="">三分月色</a><span>：回复 <i class="userid">@三分月色</i> : ' +
        replytxt +
        '</span></div><div class="ri-data clearfix"><div class="repo-date">' +
        replydate +
        '</div><div class="repo-fun clearfix"><span class="appr"><a href="javascript:void(0)"><i class="iconfont icon-dianzan"> </i><i>赞</i></a></a></span><span class="reply"><a href="javascript:void(0)">回复</a></span><span class="delete"><a href="javascript:void(0)">删除</a></span></div></div><div class="reply-wrap hid clearfix"><span class="arrow2"></span><div class="publish"><div id="input"><textarea type="text" placeholder="回复 @三分月色" maxlength="150"  class="rtxt-input" ></textarea></div></div><div class="c-btn2">评论</div></div></div></div>'
    )

    // 插入评论区
    $('.repo-list').append($replyComment)
    // 发布成功后输入框清空
    $(this).prev().find('.rtxt-input').val('')
    // 重置按钮
    $(this).css({
      backgroundColor: '#ffc09f',
      borderColor: '#ffc09f'
    })
  })

  //根据有无内容决定发布按钮的颜色
  $('.repo-list').on('keydown', '.rtxt-input', function () {
    if ($(this).val().length == 0) {
      $(this).parents('.publish').next().css({
        backgroundColor: '#ffc09f',
        borderColor: '#ffc09f'
      })
    } else {
      $(this).parents('.publish').next().css({
        backgroundColor: '#f7671d',
        borderColor: '#f7671d'
      })
    }
  })
})
