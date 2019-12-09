<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改账户信息</title>
	<link rel="stylesheet" type="text/css" href="/static/home/css/style.css" />
	<link rel="stylesheet" type="text/css" href="/static/home/css/other.css" />
	<script src="/static/home/js/jquery.js" type="text/javascript"></script>
	<script src="/static/home/js/baseutil.js" type="text/javascript"></script>
	<script src="/static/home/js/layer.js" type="text/javascript"></script>
	<link rel="stylesheet" href="/static/home/css/validationEngine.jquery.css" />
	<script src="/static/home/js/jquery.validationEngine.js"></script>
	<script src="/static/home/js/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			tm_bindPasswordLevelChecker("new_password");
		});
		var tmProfile = {
			doUpdate : function(){
				var formcheck = $("#form_user_form").validationEngine('validate');
				if(formcheck){
					var wcm = window.confirm('确认修改？');
					if(!wcm){
						return;
					}
					$.ajax({
						type: "POST",
						url: "update_password",
						dataType:'json',
						data:{password:$("#new_password").val()},
						success: function(data){
							if(data.type == 'success'){
								window.location.reload();
							}else{
								alert(data.msg);
							}
						},
						error : function(){
							//top.location.href = "home/login";
							alert('网络错误');
						}
					});

				}else{
					return false;
				}
			}
		};
	</script>

  </head>
  
<body>
	<div class="tm_main">
        <div class="tm_container">
        	<div class="tm_navtitle">
				<h1>修改密码</h1>
                <span>在下面的表单中修改您的登录密码</span>
            </div>
        </div>
        <br/>
        <div class="tm_container">
			<form method="post" id="form_user_form">
        	<table width="100%" cellpadding="5" border="0" class="tm_table_form">
            	<tbody>
                    <tr>
                        <th width="120">用户名 : </th>
                        <td>${name}</td>
                    </tr>
					<tr>
                        <th>旧密码 : </th>
                        <td>
							<input type="password" name="old_password" class="validate[required] tm_txt" size="50" maxlength="30" />
							<span class="tm_required">*</span>
						</td>
                    </tr>
					<tr>
                        <th>新密码 : </th>
                        <td>
							<input type="password" id="new_password" name="new_password" class="validate[required] tm_txt" size="50" maxlength="30" />
							<span class="tm_required">*</span>

							<div id="tm_level" class="pw-strength">
								<div class="pw-bar"></div>
								<div class="pw-bar-on"></div>
								<div class="pw-txt">
									<span>弱</span>
									<span>中</span>
									<span>强</span>
								</div>
							</div>

						</td>
                    </tr>
					<tr>
						<th>确认新密码 : </th>
						<td>
							<input type="password" class="validate[required,equals[new_password]] tm_txt" name="new_password2" size="50" maxlength="30" value="" />
							<span class="tm_required">*</span>
						</td>
					</tr>
                   
                </tbody>
                
                <tfoot>
                	<tr>
                    	<th></th>
                        <td>
                        	<button class="tm_btn tm_btn_primary" type="button" onclick="tmProfile.doUpdate();">提交</button>
                        </td>
                    </tr>
                </tfoot>
            </table>
			</form>
        </div>
    </div>
</body>
</html>
