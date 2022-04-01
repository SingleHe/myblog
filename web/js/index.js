$(function () {
  //  防抖&节流
  let throunce = function (fun, time) {
    let timer = null
    let pre = new Date().getTime()

    return () => {
      let now = new Date().getTime()

      if (now - pre <= time) {
        if (timer) {
          clearTimeout(timer)
        }

        timer = setTimeout(fun, time)
      } else {
        fun()
        pre = now
      }
    }
  }

  // 插入博客函数
  let a = 0
  function insblogs(len) {
    for (let i = a; i < a + len; i++) {
      // console.log('i=', i)
      if (i >= news.length) {
        return
      }
      //博客数据
      let $blog =
        '<div class="blog-item"><div class="blog-pic"><img src=' +
        news[i].image +
        ' alt="" /></div><div class="blog-des"><h3 class="blog-title"><a href="blog.html" target="_blank" class="b-title">' +
        news[i].title +
        '</a></h3><div class="blog-sub clearfix"><a href="javascript:void(0)"><img src="./img/freedom.jpg" alt="" /></a><a href="javascript:void(0)"><span>文旅天下</span></a><span class="time">' +
        news[i].date +
        '</span><span class="fr"><i class="bico iconfont icon-dianzan"></i><i>' +
        news[i].apprnum +
        '</i></span><span class="rgt-line fr"></span><span class="fr"><i class="bico iconfont icon-pinglun"></i><i>' +
        news[i].recordnum +
        '</i></span><span class="rgt-line fr"></span><span class="fr"><i class="bico iconfont icon-zhuanfa"></i><i>' +
        news[i].trannum +
        '</i></span></div></div></div>'
      $('.blog-content').append($blog)
    }
    a += 4
  }
  insblogs(4)
  console.log()
  let j = 4
  let flag = 1
  function loadblog() {
    let wHeight = $(window).height()
    let scrTop = $(window).scrollTop()
    let dHeight = $(document).height()
    let fHeight = $('footer').height()
    if (j < news.length && wHeight + scrTop >= dHeight - fHeight) {
      j += 4
      let $wait = '<div class="wait">正在加载中请稍等...</div>'
      $('.blog-content').append($wait)
      time = setTimeout(() => {
        insblogs(4)
        $('.wait').remove()
      }, 1000)
      console.log('j=', j)
    } else if (j >= news.length && flag) {
      flag = 0
      clearTimeout(time)
      $('.wait').remove()
      let $wait2 = '<div class="wait">不好意思已经加载到底了~~</div>'
      $('.blog-content').append($wait2)
    }
  }
  $(window).on('scroll', throunce(loadblog, 1000))
})
