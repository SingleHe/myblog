$(function () {
  // 实现图片懒加载

  // 初始化首页页面的图片
  lazyload()

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

  function lazyload() {
    // 可视区域高度$window.height，滚动条离顶部的距离$window.scrollTop()，元素离顶部的距离$i.offset().top
    let windowheight = $(window).height()
    let scrolltop = $(window).scrollTop()
    // console.log(scrolltop + windowheight)
    $('img')
      .not('.isLoaded')
      .each(function () {
        // 如果未加载的图片距离顶部的高度<=窗口高度+滚动条滚动距离，那么加载图片
        if ($(this).offset().top <= windowheight + scrolltop) {
          $(this).attr('src', $(this).attr('data-src'))
          $(this).addClass('isLoaded')
        }
      })
  }

  $(window).on('scroll', throunce(lazyload, 1000))
})
