<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的考试</title>
    <link rel="stylesheet" type="text/css" href="/static/home/css/style.css">
    <link rel="stylesheet" type="text/css" href="/static/home/css/layer.css">
    <link rel="stylesheet" type="text/css" href="/static/home/css/other.css">
    <link rel="stylesheet" type="text/css" href="/static/home/css/validationEngine.jquery.css">
    <script src="/static/home/js/jquery.js" type="text/javascript"></script>
    <script src="/static/home/js/baseutil.js" type="text/javascript"></script>
    <script src="/static/home/js/aes.js" type="text/javascript"></script>
    <script src="/static/home/js/pad-zeropadding-min.js" type="text/javascript"></script>
    <script src="/static/home/js/layer.js" type="text/javascript"></script>
    <script src="/static/home/js/jquery.validationEngine.js" type="text/javascript"></script>
    <script src="/static/home/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
</head>
<body>
<div class="tm_main">
    <div class="tm_container">
        <div class="tm_navtitle">
            <h1>我的考试</h1>
            <span>我的考试，请选择以下列表中我需要参加的考试。</span>
        </div>
    </div>

    <div class="tm_container">
        <form action="/home/user/exam_list" method="get">
            <div class="tm_searchbox">
                考试名称 :
                <input type="text" name="name" class="tm_txts" size="10" maxlength="20"/> &nbsp;
                <button class="tm_btns" type="submit">搜索</button>
            </div>
            <!-- 当前日期 -->
            <table width="100%" cellpadding="10" border="0" class="tm_table_list">
                <thead>
                <tr>
                    <th>考试名称</th>
                    <th>时间设定</th>
                    <th>考试时长</th>
                    <th>试卷类型</th>
                    <th>卷面总分</th>
                    <th>及格分数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list examList as exam>
                    <tr>
                    <tr id="tr-${exam.id }" data-key="${exam.id }">
                        <td>${exam.name }</td>
                        <td>
                            ${exam.startTime?datetime}
                            ---${exam.endTime?datetime}
                        </td>
                        <td>${exam.avaliableTime }分钟</td>
                        <td>${byId }</td>
                        <td>${exam.totalScore }</td>
                        <td>${exam.passScore }</td>
                        <td>
                            <button type="button" class="tm_btn tm_btn_primary"
                                    onclick="tm.startExam('tr-${exam.id}');">开始考试
                            </button>
                        </td>
                    </tr>
                    </tr>
                </#list>
                </tbody>
            </table>
        </form>
        <table width="100%" cellpadding="10" border="0" class="tm_table_list">
            <tfoot>
            <tr>
                <td>
                    <div class="tm_pager_foot">
                        <a href="/home/user/exam_list?page=${page - 1}" class="tm_btns"
                           style="color:white;text-decoration:none;">上一页
                        </a>&nbsp;&nbsp;&nbsp;<font size="5" color="red"><b>${page}</b></font>&nbsp;&nbsp;&nbsp;
                        <a href="/home/user/exam_list?page=${page + 1}" class="tm_btns"
                                style="color:white;text-decoration:none;">下一页</a>&nbsp;
                    </div>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>


</div>
<script type="text/javascript">
    window.onload = function () {
        $(".tm_table_list tbody tr").mouseover(function () {
            $(this).attr("style", "background:#f5f5f5");
        });
        $(".tm_table_list tbody tr").mouseout(function () {
            $(this).attr("style", "background:#ffffff");
        });
    }

    var tm = {
        startExam: function (e) {
            if (!tmCheckBrowserSupport()) {
                alert("抱歉，您的浏览器不被支持，如需继续使用，请更换为：Chrome、Firefox、360极速浏览器。");
                return false;
            }
            var tr = $("#" + e);
            var eid = tr.attr("data-key");
            var html = [];
            html.push('<div style="margin:20px">');
            html.push('	<p style="line-height:20px">确定进入试卷并开始考试吗？</p>');

            html.push('	<table style="margin:0 auto; width:350px" border="0" cellpadding="0" cellspacing="0">');
            html.push('	<tr>');
            html.push('		<td width="80"><img src="/static/home/images/paper_pencil.png" width="60" /></td>');
            html.push('		<td><p><b>考试名称</b>：' + tr.find("td").eq(0).text() + '<p>');
            html.push('			<p><b>考试时长</b>：' + tr.find("td").eq(2).text() + '<p>');
            html.push('			<p><b>卷面总分</b>：' + tr.find("td").eq(4).text() + '<p>');
            html.push('			<p><b>及格分数</b>：' + tr.find("td").eq(5).text() + '<p>');
            html.push('		</td>');
            html.push('	</tr>');
            html.push('</table>');
            html.push('<p style="text-align:center; margin-top:30px">');
            html.push('<button class="confir-exam tm_btn tm_btn_primary" type="button" onclick="tm.joinExam(\'' + eid + '\')">确定</button>');
            html.push('</p>');
            html.push('</div>');

            layer.open({
                type: 1,
                title: '开始考试',
                shadeClose: true,
                shade: 0.8,
                area: ['450px', '310px'],
                content: html.join("")
            });

            return false;
        },
        joinExam: function (eid) {
            $(".confir-exam").text('请稍等...');
            $(".confir-exam").attr("disabled", true);
            $.ajax({
                type: "POST",
                url: "../exam/statr_exam",
                dataType: "json",
                data: {"examId": eid},
                success: function (data) {
                    if (data.type == 'success') {
                        top.window.location = "../exam/examing?examId=" + eid;
                    } else {
                        alert(data.msg);
                        window.location.reload();
                    }
                },
                error: function () {
                    alert('系统忙，请稍后再试');
                    window.location.reload();
                }
            });
        }
    };
</script>
</body>
</html>