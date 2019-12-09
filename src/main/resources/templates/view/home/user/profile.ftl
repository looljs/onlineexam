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
	</script>
  </head>
<body>
<div class="tm_main">
    <div class="tm_container">
        <div class="tm_navtitle">
            <h1>修改账户信息</h1>
            <span>在下列表单中修改您的账户信息</span>
        </div>
    </div>
    <br/>
    <div class="tm_container">
        <form method="post" id="form_user_load">
            <table width="100%" cellpadding="5" border="0" class="tm_table_form">
                <tbody>
                <tr>
                    <th width="120">用户名 : </th>
                    <td>${info.name}</td>
                </tr>
                <tr>
                    <th width="120">所属学科: </th>
                    <td>${subjectName}</td>
                </tr>
                <tr>
                    <th>真实姓名 : </th>
                    <td>
                        <input type="text" id="truename" name="truename" class="validate[required] tm_txt" size="50" maxlength="30" value="${info.trueName}" />
                        <span class="tm_required">*</span>
                        <span class="tm_tip">填写用户的真实姓名</span>
                    </td>
                </tr>

                <tr>
                    <th>联系电话 : </th>
                    <td><input type="text" id="tel" name="tel" class="tm_txt" size="50" maxlength="30" value="${info.tel}" /></td>
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
<script type="text/javascript">
    $(document).ready(function() {

    });

    var tmProfile = {
        doUpdate : function(){
            var formcheck = $("#form_user_load").validationEngine('validate');
            if(formcheck){
                var wcm = window.confirm('确认修改？');
                if(!wcm){
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: "update_info",
                    dataType:'json',
                    data:{trueName:$("#truename").val(),tel:$("#tel").val()},
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
</body>
</html>