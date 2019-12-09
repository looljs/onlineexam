<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2019, looli.club, Ltd." />
    <link rel="stylesheet" type="text/css" href="/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/icon.css" />
    <script type="text/javascript" src="/static/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <title>成绩统计管理</title>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-search">
            <label>考试列表:</label>
            <input id="search-exam" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/exam/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>

    <div class="easyui-accordion" style="width:950px;height:660px;">
        <div title="成绩统计图表展示" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
            <div id="main" style="width: 880px;height:560px;"></div>
        </div>
    </div>


</div>
<!--加载动画-->
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/static/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>
<!-- End of easyui-dialog -->
<script src="/static/easyui/js/echarts.min.js"></script>
<script type="text/javascript">

    //基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据

    $("#search-btn").click(function(){
        var examId = $("#search-exam").combobox('getValue');
        if(examId == -1){
            $.messager.alert('信息提示','请选择要统计的考试！','info');
            return;
        }
        $.ajax({
            type: "POST",
            url: "get_stats",
            dataType: "json",
            data: {"examId":examId},
            success: function(data){
                if(data.type == 'success'){
                    var option = {
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        xAxis: {
                            type: 'category',
                            data: data.examList
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            data: data.examScore,
                            type: 'line'
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }else{
                    alert(data.msg);
                }
            },
            error: function(){
                alert('系统忙，请稍后再试');
                window.location.reload();
            }
        });
    });

</script>
<script>
    var pc;
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    };
    function closes() {
        $('#loading').fadeOut('normal', function () {
            $(this).remove();
        });
    }
</script>