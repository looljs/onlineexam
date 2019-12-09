<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2019, looli.club, Ltd."/>
    <title>前台首页</title>
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
<style>
    h2 {
        font-size: 14px;
        margin: 20px 0 10px 0;
    }

    .tm_param_list a {
        color: #000
    }

    .tm_param_list a:hover {
        color: #f00
    }

    .tm_blocker {
        float: left;
        width: 50%;
        min-width: 450px
    }

    .tm_blocker2 {
        float: left;
        width: 800px;
    }
</style>
<body>
<div class="tm_main" style="min-width:1000px">


    <div class="tm_container">
        <div class="tm_navtitle">
            <h1>欢迎使用</h1>
            <span>欢迎使用【looli.club】在线考试系统</span>
        </div>
    </div>


    <script type="text/javascript">
        var tm = {
            startExam : function(e){
                if(!tmCheckBrowserSupport()){
                    alert("抱歉，您的浏览器不被支持，如需继续使用，请更换为：Chrome、Firefox、360极速浏览器。");
                    return false;
                }
                var tr = $("#"+e);
                var eid = tr.attr("data-key");
                var html = [];
                html.push('<div style="margin:20px">');
                html.push('	<p style="line-height:20px">确定进入试卷并开始考试吗？</p>');

                html.push('	<table style="margin:0 auto; width:350px" border="0" cellpadding="0" cellspacing="0">');
                html.push('	<tr>');
                html.push('		<td width="80"><img src="/static/home/images/paper_pencil.png" width="60" /></td>');
                html.push('		<td><p><b>考试名称</b>：'+tr.find("td").eq(0).text()+'<p>');
                html.push('			<p><b>考试时长</b>：'+tr.find("td").eq(2).text()+'<p>');
                html.push('			<p><b>卷面总分</b>：'+tr.find("td").eq(4).text()+'<p>');
                html.push('			<p><b>及格分数</b>：'+tr.find("td").eq(5).text()+'<p>');
                html.push('		</td>');
                html.push('	</tr>');
                html.push('</table>');

                html.push('<p style="text-align:center; margin-top:30px">');
                html.push('<button class="confir-exam tm_btn tm_btn_primary" type="button" onclick="tm.joinExam(\''+eid+'\')">确定</button>');
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
            joinExam : function(eid){
                $(".confir-exam").text('请稍等...');
                $(".confir-exam").attr("disabled", true);
                $.ajax({
                    type: "POST",
                    url: "/home/exam/statr_exam",
                    dataType: "json",
                    data: {"examId":eid},
                    success: function(data){
                        if(data.type == 'success'){
                            top.window.location="/home/exam/examing?examId="+eid;
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

    <div class="tm_container">
        <div class="tm_blocker2">
            <h2>进行中的考试</h2>
            <table width="100%" cellpadding="10" border="0" class="tm_table_list">
                <thead>
                <tr>
                    <th>考试名称</th>
                    <th>时间设定</th>
                    <th>考试时长</th>
                    <th>考试科目</th>
                    <th>卷面总分</th>
                    <th>及格分数</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
<#--                <#if (examList?size > 0)>-->
<#--                    <tr>-->
<#--                        <td colspan="7">你还没有考过试!</td>-->
<#--                    </tr>-->
<#--                </#if>-->
                <#list examList as exam>
                    <tr id="tr-${exam.id }" data-key="${exam.id }">
                        <td>${exam.name }</td>
                        <td>
                            ${exam.startTime?datetime}
                            ---${exam.endTime?datetime}</td>
<#--                            ${exam.startTime?datetime('yyyy-MM-dd hh:mm:ss')}-->
<#--                            ---${exam.endTime?datetime('yyyy-MM-dd hh:mm:ss')}</td>-->
                        <td>${exam.avaliableTime }分钟</td>
                        <td>${subjectName}</td>
                        <td>${exam.totalScore }</td>
                        <td>${exam.passScore }</td>
                        <td>
                            <button class="tm_btn tm_btn_primary" onclick="tm.startExam('tr-${exam.id }');">开始考试
                            </button>
                        </td>
                    </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>

    <div class="tm_container">
        <div class="tm_blocker2">
            <h2>参加过的考试</h2>
            <table width="100%" cellpadding="10" border="0" class="tm_table_list">
                <thead>
                <tr>
                    <th>考试名称</th>
                    <th>考试时长</th>
                    <th>考试耗时</th>
                    <th>考试时间</th>
                    <th>试卷得分</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list historyList as exam>
                    <tr>
                        <td>${exam.name }</td>
                        <td>${exam.avaliableTime}</td>
                        <td>${exam.useTime }分钟</td>
                        <#if (exam.startExamTime)??>
                            <td>${exam.startExamTime}</td>
                        <#else >
                            <td></td>
<#--                            <td>-->
<#--                                <button class="tm_btn tm_btn_primary" onclick="tm.startExam('tr-${exam.examId }');">开始考试-->
<#--                                </button>-->
<#--                            </td>-->
                        </#if>


<#--                        <td>${exam.startExamTime}</td>-->
                        <td>${exam.score }</td>
                        <td>
                            <a href="/home/user/review_exam?examId=${exam.examId}&examPaperId=${exam.examPaperId}" target="_blank" class="tm_btn tm_btn_primary" style="text-decoration:none;color:white;" >回顾试卷</a>
                        </td>
                    </tr>
                </#list>
<#--                <#if (historyList??)>-->
<#--                    <tr>-->
<#--                        <td colspan="7">你还没有考过试!</td>-->
<#--                    </tr>-->
<#--                </#if>-->
                </tbody>
            </table>
        </div>
    </div>
    <div class="tm_container"></div>
</div>
<p>&nbsp;</p>
</body>
</html>