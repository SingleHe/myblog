//设置cookie
function setCookie(name, value, day) {
  var date = new Date()
  date.setTime(date.getTime() + day * 24 * 60 * 60 * 1000)
  document.cookie = name + '=' + escape(value) + ';expires=' + date.toGMTString()
}
//获取cookie
function getCookie(name) {
  var arr,
    reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)')
  if ((arr = document.cookie.match(reg))) return unescape(arr[2])
  else return null
}
