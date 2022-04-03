<?php
    
    $span1 = '网信办要求取消所有涉明星艺人榜单';
    $span2 = '喀布尔机场发生两起爆炸';
    $span3 = '古剑奇谭 下架';
    $span4 = '赵薇怎么了';
    $span5 = '林心如影视工作室注销';
    $span6 = '阿里';
    $span7 = '万惠';
    $span8 = 'ISIS宣布对喀布尔爆炸负责';
    $span9 = '女子谎报强奸被拘留';
    $span10 = '西藏将于今年9月1日起开征契税';
    
    header('Content-Type:application/json; charset=utf-8');

    $arr = array('1'=>$span1,
    '2'=>$span2,
    '3'=>$span3,
    '4'=>$span4,
    '5'=>$span5,
    '6'=>$span6,
    '7'=>$span7,
    '8'=>$span8,
    '9'=>$span9,
    '10'=>$span10);

    exit(json_encode($arr));
?>