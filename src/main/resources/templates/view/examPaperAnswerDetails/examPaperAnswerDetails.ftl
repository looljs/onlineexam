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
    <title>答题详情管理</title>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div class="wu-toolbar-search">
        <label>所属考试:</label>
        <input id="search-exam" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/exam/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
        <label>所属考生:</label>
        <input id="search-student" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/examinee/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
        <label>所属试题:</label>
        <input id="search-question" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:300px;" data-options="
                            url:'/admin/question/list1',
                            method:'post',
                            valueField:'id',
                            textField:'title',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
        <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!--加载动画-->
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/static/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>

<!-- End of easyui-dialog -->
<script type="text/javascript">
    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {};
        var examId = $("#search-exam").combobox('getValue');
        if(examId != -1){
            option.examId = examId;
        }
        var studentId = $("#search-student").combobox('getValue');
        if(studentId != -1){
            option.studentId = studentId;
        }
        var questionId = $("#search-question").combobox('getValue');
        if(questionId != -1){
            option.questionId = questionId;
        }
        $('#data-datagrid').datagrid('reload',option);
    });



    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({

        url:'list',
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        nowrap:false,
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'examId',title:'所属考试',width:180,formatter:function(value,index,row){
                    var examList = $("#search-exam").combobox("getData");
                    console.log(examList);
                    console.log(value);
                    for(var i=0;i<examList.length;i++){
                        if(examList[i].id == value)return examList[i].name;
                    }
                    return value;
                }},
            { field:'examPaperId',title:'试卷ID',width:200},
            { field:'questionId',title:'所属试题',width:200,formatter:function(value,index,row){
                    var questionList = $("#search-question").combobox("getData");
                    for(var i=0;i<questionList.length;i++){
                        if(questionList[i].id == value)return questionList[i].title;
                    }
                    return value;
                }},
            { field:'examineeId',title:'所属考生',width:180,formatter:function(value,index,row){
                    var studentList = $("#search-student").combobox("getData");
                    for(var i=0;i<studentList.length;i++){
                        if(studentList[i].id == value)return studentList[i].name;
                    }
                    return value;
                }},
            { field:'answer',title:'提交答案',width:200},
            { field:'isCorrect',title:'是否正确',width:200,formatter:function(value,index,row){
                    if(value == 0) return '错误';
                    return '正确';
                }},
        ]]
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