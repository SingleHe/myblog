$(function () {
  // 搜索框点击出现下拉热搜榜单
  $('#input').focus(function () {
    $(this).siblings('.dropdown').show()
    // ajax定时请求数据
    setTimeajax()
  })
  $('#input').blur(function () {
    $(this).siblings('.dropdown').hide()
  })
  //利用闭包对请求的结果进行缓存，十秒刷新一次
  let setTimeajax = (function () {
    let cache = null
    setInterval(function () {
      cache = null
    }, 10 * 1000)
    return function () {
      if (cache) {
        console.log('暂未刷新')
      } else {
        $.ajax({
          url: 'data/search.php',
          type: 'get',
          dataType: 'json',
          success: function (data) {
            cache = data
            console.log(cache)
            for (let i in data) {
              $('.s-tit')
                .eq(i - 1)
                .text(data[i])
            }
          }
        })
      }
    }
  })()
})
