<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>menu</title>
	<link rel="stylesheet" type="text/css" href="/static/home/css/style.css" />

	<script type="text/javascript" src="/static/home/js/jquery.js"></script>
	<script type="text/javascript">
		function tm_toggle_menu(obj){
			$(obj).parent().siblings().toggle();
		}
		function tm_mark_current_menuitem(){

		}

		$(document).ready(function() {
			$(".tm_menu_item ul li a").click(function(){
				$(".tm_menu_item ul li").removeClass("itemon");
				$(this).parent().addClass("itemon");
			});

		});

		document.oncontextmenu= function(){return false;}

	</script>

	<style>
		.tmc_menu_learn{background:url('/static/home/images/menu_learn.png') no-repeat 8px 8px;}
		.tmc_menu_test{background:url('/static/home/images/menu_test.png') no-repeat 8px 8px;}
		.tmc_menu_self{background:url('/static/home/images/menu_self.png') no-repeat 8px 8px;}
		.tmc_menu_account{background:url('/static/home/images/menu_account.png') no-repeat 8px 8px;}
	</style>
</head>
<body oncontextmenu="return false">

<div class="tm_menu">





	<div class="tm_menu_item clearfix">
		<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_test">我的考试</a></h2>
		<ul>
			<li><a href="/home/user/exam_list" target="main">我的考试</a></li>
			<li><a href="/home/user/history_list" target="main">历史考试</a></li>
		</ul>
	</div>



	<div class="tm_menu_item clearfix">
		<h2><a href="javascript:void(0);" onclick="tm_toggle_menu(this)" class="tmc_menu_account">个人信息</a></h2>
		<ul>
			<li><a href="/home/user/password" target="main">修改密码</a></li>
			<li><a href="/home/user/profile" target="main">帐户信息</a></li>
		</ul>
	</div>


</div>


</body>
</html>