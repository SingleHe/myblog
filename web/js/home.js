window.addEventListener('resize', function () {
  //1、实现瀑布流布局
  waterFall('main', 'box')
})
window.addEventListener('scroll', function () {
  //1、实现瀑布流布局
  waterFall('main', 'box')
})

/*
  实现瀑布流布局，传递参数为string类型
*/
function waterFall(parent, child) {
  //1、根据图片的列数来确定父盒子的宽度，父盒子居中
  //1.1 获取标签，父盒子和所有子盒子
  var father = document.getElementById(parent)
  var allBox = document.getElementsByClassName(child)
  //1.2 获取其中一个的宽度
  var boxWidth = allBox[0].offsetWidth
  //1.3 获取文档的宽度(兼容)
  var screen = document.documentElement.clientWidth || document.body.clientWidth
  console.log(screen)
  //1.4 求出当前图片的列数，是变化的
  var cols = parseInt(screen / boxWidth)
  //1.5 父盒子居中，给父盒子设置宽度
  father.style.width = cols * boxWidth + 'px'
  father.style.margin = '0 auto'

  //2、子盒子定位（从第二行开始）
  //2.1 定义变量
  var heightArr = [],
    boxHeight = 0,
    minBoxHeight = 0,
    minIndex = 0
  //2.2 遍历所有的盒子
  for (let i = 0; i < allBox.length; i++) {
    boxHeight = allBox[i].offsetHeight
    //2.3 判断是否是第一行
    if (i < cols) {
      heightArr.push(boxHeight)
    } else {
      //剩余行做定位
      //2.4 取出数组中最矮盒子的高度
      console.log(heightArr)
      minBoxHeight = heightArr[minBox(heightArr)]
      //2.5 取出最矮盒子再数组中的索引
      minIndex = minBox(heightArr)
      //2.6 剩余子盒子的定位
      allBox[i].style.position = 'absolute'
      allBox[i].style.left = minIndex * boxWidth + 'px'
      allBox[i].style.top = minBoxHeight + 'px'
      //2.7 更新高度
      heightArr[minIndex] += boxHeight
    }
  }
}

function minBox(box) {
  var j = 0
  for (i in box) {
    if (box[j] > box[i]) {
      j = i
    }
  }
  return j
}
