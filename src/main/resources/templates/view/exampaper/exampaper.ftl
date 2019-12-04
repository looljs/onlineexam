<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, looli.club, Ltd." />
    <link rel="stylesheet" type="text/css" href="/static/easyui/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="/static/easyui/css/icon.css" />
    <script type="text/javascript" src="/static/easyui/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
    <title>学科管理</title>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <#list exampaper as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
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
<#--            <select id="search-exam" class="easyui-combobox" panelHeight="auto" style="width:120px">-->
<#--                <option value="-1">全部</option>-->
<#--&lt;#&ndash;                <c:forEach items="${examList}" var="exam">&ndash;&gt;-->
<#--&lt;#&ndash;                    <option value="${exam.id}">${exam.name}</option>&ndash;&gt;-->
<#--&lt;#&ndash;                </c:forEach>&ndash;&gt;-->
<#--            </select>-->
            <label>所属考生:</label>
            <input id="search-student" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/examinee/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
<#--            <select id="search-student" class="easyui-combobox" panelHeight="auto" style="width:120px">-->
<#--                <option value="-1">全部</option>-->
<#--&lt;#&ndash;                <c:forEach items="${studentList}" var="student">&ndash;&gt;-->
<#--&lt;#&ndash;                    <option value="${student.id}">${student.name}</option>&ndash;&gt;-->
<#--&lt;#&ndash;                </c:forEach>&ndash;&gt;-->
<#--            </select>-->
            <label>试卷状态:</label>
            <select id="search-status" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <option value="0">未考</option>
                <option value="1">已考</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td align="right">所属考试:</td>
                <td>
                    <input id="edit-examId" class="easyui-combobox" name="examId" panelHeight="auto" style="width:268px;" data-options="
                            url:'/admin/exam/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择试卷科目'
                            ">
                </td>
<#--                <td>-->
<#--                    <select id="edit-examId" name="examId" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试卷科目'">-->
<#--&lt;#&ndash;                        <c:forEach items="${examList}" var="exam">&ndash;&gt;-->
<#--&lt;#&ndash;                            <option value="${exam.id}">${exam.name}</option>&ndash;&gt;-->
<#--&lt;#&ndash;                        </c:forEach>&ndash;&gt;-->
<#--                    </select>-->
<#--                </td>-->
            </tr>
            <tr>
                <td align="right">所属学生:</td>
                <td>
<#--                    <select id="edit-studentId" name="studentId" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择试卷科目'">-->
<#--&lt;#&ndash;                        <c:forEach items="${studentList}" var="student">&ndash;&gt;-->
<#--&lt;#&ndash;                            <option value="${student.id}">${student.name}</option>&ndash;&gt;-->
<#--&lt;#&ndash;                        </c:forEach>&ndash;&gt;-->
<#--                    </select>-->
                    <input id="edit-studentId" class="easyui-combobox" name="examineeId" panelHeight="auto" style="width:268px;" data-options="
                            url:'/admin/examinee/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择学生'
                            ">
                </td>
            </tr>
        </table>
    </form>
</div>
<!--加载动画-->
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/static/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>

<!-- End of easyui-dialog -->
<script type="text/javascript">




    function edit(){
        var validate = $("#edit-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#edit-form").serialize();
        $.ajax({
            url:'edit',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $('#edit-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    /**
     * 删除记录
     */
    function remove(){
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelected');
                if(item == null || item.length == 0){
                    $.messager.alert('信息提示','请选择要删除的数据！','info');
                    return;
                }
                $.ajax({
                    url:'delete',
                    dataType:'json',
                    type:'post',
                    data:{id:item.id},
                    success:function(data){
                        if(data.type == 'success'){
                            $.messager.alert('信息提示','删除成功！','info');
                            $('#data-datagrid').datagrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }

    /*
    编辑
    */
    function openEdit(){
        //$('#add-form').form('clear');
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要编辑的数据！','info');
            return;
        }
        $('#edit-dialog').dialog({
            closed: false,
            modal:true,
            title: "编辑试卷信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler:edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#edit-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                //$("#add-form input").val('');
                $("#edit-id").val(item.id);
                $("#edit-examId").combobox('setValue',item.examId);
                $("#edit-studentId").combobox('setValue',item.examineeId);
            }
        });
    }



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
        var status = $("#search-status").combobox('getValue');
        if(status != -1){
            option.status = status;
        }
        $('#data-datagrid').datagrid('reload',option);
    });

    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo){
        //shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

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
                    for(var i=0;i<examList.length;i++){
                        if(examList[i].id == value)return examList[i].name;
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
            { field:'status',title:'试卷状态',width:200,formatter:function(value,index,row){
                    if(value == 0) return '未考';
                    return '已考';
                }},
            { field:'startExamTime',title:'开始考试时间',width:200,formatter:function(value,index,row){
                    if(value == '' || value == null)return '';
                    return format(value);
                }},
            { field:'endExamTime',title:'结束考试时间',width:200,formatter:function(value,index,row){
                    if(value == '' || value == null)return '';
                    return format(value);
                }},
            { field:'useTime',title:'考试用时',width:200,formatter:function(value,index,row){
                    return value + '分钟';
                }},
            { field:'totalScore',title:'试卷总分',width:200},
            { field:'score',title:'试卷得分',width:200},
            { field:'createTime',title:'添加时间',width:200,formatter:function(value,index,row){
                    return format(value);
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