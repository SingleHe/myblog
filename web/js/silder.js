// 用swiper插件实现
var mySwiper = new Swiper('.swiper-container', {
  direction: 'horizontal', // 垂直切换选项
  loop: true, // 循环模式选项
  autoplay: {
    delay: 2000, //1秒切换一次
    disableOnInteraction: false
  },
  // 分页器
  pagination: {
    el: '.swiper-pagination',
    type: 'bullets',
    clickable: true
  }
})
