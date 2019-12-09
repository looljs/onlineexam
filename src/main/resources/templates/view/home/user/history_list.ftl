<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>历史考试</title>
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
            <h1>历史考试</h1>
            <span>历史考试，请选择以下列表中我参加过的考试进行详情查看。</span>
        </div>
    </div>

    <div class="tm_container">
        <form action="/home/user/history_list" method="get">
            <div class="tm_searchbox">
                考试名称 :
                <input type="text" name="name" class="tm_txts" size="10" maxlength="20"  /> &nbsp;
                <button class="tm_btns" type="submit">搜索</button>
            </div>

            <table width="100%" cellpadding="10" border="0" class="tm_table_list">
                <thead>
                <tr>
                    <th>考试名称</th>
                    <th>考试状态</th>
                    <th>考试时长</th>
                    <th>考试耗时</th>
                    <th>考试时间</th>
                    <th>考试科目</th>
                    <th>试卷得分</th>
                    <th>及格分数/卷面总分</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list historyList as history>
                <tr>
                    <td>${history.name}</td>
                    <td>已批阅</td>
                    <td><span class="tm_label">${history.avaliableTime}</span> 分钟</td>
                    <td><span class="tm_label">${history.useTime}</span> 分钟</td>
                    <td>
                        ${history.startExamTime }---
                        ${history.endExamTime }
                    </td>
                    <td>${byId}</td>
                    <td>
                        <span class="tm_label">${history.score}</span>
                        <#if  history.score &lt; history.passScore >
                            <font color="red"><b>不及格</b></font>
                        </#if>
                    </td>
                    <td>${history.passScore }/${history.totalScore }</td>
                    <td>
                        <a  href="/home/user/review_exam?examId=${history.examId}&examPaperId=${history.examPaperId}" target="_blank" class="tm_btn tm_btn_primary"
                           style="text-decoration:none;color:white;" >回顾试卷</a>
                    </td>
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
                        <a href="/home/user/history_list?page=${page - 1}" class="tm_btns" style="color:white;text-decoration:none;">上一页</a>&nbsp;&nbsp;&nbsp;<font size="5" color="red"><b>${page}</b></font>&nbsp;&nbsp;&nbsp;<a href="history_list?page=${page + 1}" class="tm_btns" style="color:white;text-decoration:none;">下一页</a>&nbsp;
                    </div>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>


</div>
<script type="text/javascript">
    window.onload = function(){
        $(".tm_table_list tbody tr").mouseover(function(){
            $(this).attr("style","background:#f5f5f5");
        });
        $(".tm_table_list tbody tr").mouseout(function(){
            $(this).attr("style","background:#ffffff");
        });
    }
</script>
</body>
</html>