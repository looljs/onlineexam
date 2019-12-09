<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>请登录 - 考试系统</title>
    <link rel="stylesheet" type="text/css" href="/static/home/css/style.css">
    <style>
        .tm_login_body {
            background: url('/static/home/images/001.jpg');
            background-size: cover;
            -moz-background-size: cover;
            background-repeat: no-repeat;
        }

        .tm_login_container {
            width: 500px;
            margin: 200px auto;
            clear: both
        }

        .tm_login_title {
            height: 80px;
            margin: 10px 0 15px 0;
            background: #fff;
            text-align: center;
            border-bottom: solid 1px #eee;
        }

        .tm_login_title img {
            height: 50px;
        }

        .tm_login_title span {
            font-size: 22px;
            line-height: 80px;
            font-family: 'Microsoft Yahei', Tahoma, Geneva, 'Simsun';
        }

        .tm_login_form {
            width: 100%;
            height: 320px;
            clear: both;
            -moz-border-radius: 8px;
            -webkit-border-radius: 8px;
            border-radius: 8px;
            padding: 1px;
            background: #fff;
        }

        .tm_login_table {
            width: 400px;
            margin: 20px auto;
        }

        .tm_login_table tr th {
            width: 100px;
        }

        .tm_login_table tr td {
            width: 300px;
            text-align: left
        }

        .tm_login_title_table {
            width: 400px;
            margin: 0px auto ;
        }

        .tm_login_title_table tr th {
            width: 100px;
        }

        .tm_login_title_table tr td {
            width: 300px;
            text-align: left
        }

        .tm_login_foot {
            width: 100%;
            line-height: 20px;
            text-align: center;
            clear: both;
            margin: 20px 0
        }


        html {
            overflow: hidden;
        }

        body {
            overflow: hidden;
        }

        .layui-layer-btn {
            text-align: center !important;
        }
    </style>
    <link rel="stylesheet" href="/static/home/css/layer.css" id="layui_layer_skinlayercss">
	<script src="/static/home/js/jquery.js" type="text/javascript"></script>
	<script src="/static/home/js/baseutil.js" type="text/javascript"></script>
	<script src="/static/home/js/aes.js" type="text/javascript"></script>
	<script src="/static/home/js/pad-zeropadding-min.js" type="text/javascript"></script>
	<script src="/static/home/js/layer.js" type="text/javascript"></script>
</head>
<body class="tm_login_body">
<div class="tm_login_container">
    <div class="tm_login_form">
        <div class="tm_login_title">
            <table border="0" cellpadding="0" cellspacing="0" class="tm_login_title_table">
                <tbody>
                <tr>
                    <th><img src="/static/home/images/logo_min.png" align="absmiddle"></th>
                    <td><span>LOOLI考试系统</span></td>
                </tr>
                </tbody>
            </table>
        </div>
        <table border="0" cellpadding="5" cellspacing="0" class="tm_login_table">
            <tbody>
            <tr>
                <th>用户名</th>
                <td><input type="text" class="tm_txt" name="name" maxlength="20" value="" style="width:255px"></td>
            </tr>
            <tr>
                <th>密 &nbsp; 码</th>
                <td><input type="password" class="tm_txt" name="password" maxlength="20" value="" style="width:255px">
                </td>
            </tr>

            <tr>
                <th></th>
                <td>
                    <div style="margin-top:10px">
                        <button type="button" class="tm_btn tm_btn_primary" style="width:50%" onclick="tm.doLogin();">
                            登录
                        </button>
                        <button type="button" class="tm_btn" onclick="tm.goRegister();" style="width:40%">注册</button>
                    </div>
                </td>
            </tr>
            <tr>
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="tm_login_foot">
        <div>Copyright © 【looli.club】 All Right Reserved 2019</div>
    </div>
</div>
<script type="text/javascript">

    function getBrowserInfo() {
        try {
            var Sys = {};
            var ua = navigator.userAgent.toLowerCase();
            console.log(ua);
            var re = /(msie|trident|firefox|chrome|opera|version).*?([\d.]+)/;
            var m = ua.match(re);
            Sys.browser = m[1].replace(/version/, "'safari");
            Sys.ver = m[2];
            return Sys;
        } catch (e) {
        }
    }

    function checkBrowser() {
        var tmBrowser = getBrowserInfo();
        var isSupportedBrowser = false;
        if (tmBrowser) {
            if (tmBrowser.browser == "firefox" || tmBrowser.browser == "chrome") {
                isSupportedBrowser = true;
            }
        }
        if (!isSupportedBrowser) {
            layer.open({
                title: "浏览器提示",
                content: "为达到最佳使用效果，请使用Chrome、FireFox、或360极速浏览器访问系统。",
                btnAlign: "c"
            });
        }
    }


    var tm = {
        doReset: function () {
            $("input[name='name']").val('');
            $("input[name='password']").val('');
        },
        goRegister: function () {
            window.location = "register";
        },
        doLogin: function () {
            var name = $("input[name='name']").val();
            var password = $("input[name='password']").val();
            if (baseutil.isEmpty(name)) {
                alert('没有填写用户名');
                return;
            }
            if (baseutil.isEmpty(password)) {
                alert('没有填写登录密码');
                return;
            }
            $(".tm_btn_primary").text('登录...');

            $.ajax({
                type: "POST",
                url: "login",
                dataType: "json",
                data: {
                    "name": name,
                    "password": password,
                },
                success: function(data){
                    if(data.type == 'success'){
                        window.location="/home/user/index";
                    }else{
                        alert(data.msg);
                        window.location.reload();
                    }
                },
                error: function(){
                    alert('系统忙，请稍后再试');
                    window.location.reload();
                }
            });

        }
    };
</script>
</body>
</html>