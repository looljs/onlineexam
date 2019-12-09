<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>head</title>
	<link rel="stylesheet" type="text/css" href="/static/home/css/style.css" />
	<script type="text/javascript" src="/static/home/js/jquery.js"></script>
	<script type="text/javascript">
		var tm = {
			logout : function(){
				if(window.confirm('确定要退出登录吗？')){
					top.location.href = "/home/user/logout";
				}
			}
		};
		var tm_activemenu = function(obj){
			$(obj).parent().parent().children("li").attr("class","");
			$(obj).parent().attr("class","active");
		}
		//异步获取登录帐号信息
		$.ajax({
			type: "POST",
			url: "/home/user/get_current",
			dataType:'json',
			success: function(data){
				if(data.type === 'success'){
					$("#user-name").text('当前用户' + '【' + data.truename + '】');
				}else{
					alert("登录信息失效，请重新登录");
					top.location.href = "/home/login";
				}
			},
			error : function(){
				top.location.href = "/home/login";
			}
		});
	</script>
</head>
<body>
<div class="tm_head">
	<div class="tm_head_logo"><a href=""><img src="/static/home/images/logo.png" /></a></div>
	<div class="tm_head_switch"><a href="javascript:void(0);" ><img src="/static/home/images/ico_lines.png" /></a></div>
	<div class="tm_head_menu">
		<ul>
		</ul>
	</div>
	<div class="tm_head_tools">
				<span style="cursor:pointer" id="user-name">
					正在获取账户信息...
				</span> |
		<a href="/home/user/profile" target="main"><img src="/static/home/images/ico_profile.png" align="absmiddle"/>帐户</a> |
		<a href="/admin/system/login" target="_blank"><img src="/home/images/ico_profile.png" align="absmiddle"/>登录后台</a> |
		<a href="javascript:void(0);" onclick="return tm.logout();"><img src="/home/images/ico_exit.png" align="absmiddle"/>退出</a>
	</div>
</div>
<script type="text/javascript">
</script>
</body>
</html>