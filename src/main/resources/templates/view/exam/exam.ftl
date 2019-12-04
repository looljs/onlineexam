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
    <title>考试管理</title>
</head>
<body class="easyui-layout">
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <#list exam as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
        <div class="wu-toolbar-search">
            <label>考试名称:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>所属科目:</label>
            <input id="search-subject" class="easyui-combobox" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/subject/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
            <label>考试开始时间:</label><input id="search-startTime" class="wu-text easyui-datetimebox" style="width:150px">
            <label>考试结束时间:</label><input id="search-endTime" class="wu-text easyui-datetimebox" style="width:150px">
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td align="right">考试名称:</td>
                <td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试名称'" ></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                    <input class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:268px;" data-options="
                            url:'/admin/subject/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择学科'
                            ">
                </td>
            </tr>
            <tr>
                <td align="right">考试开始时间:</td>
                <td><input type="text" id="add-startTime" name="startTime" class="wu-text easyui-datetimebox easyui-validatebox" editable="false"></td>
            </tr>
            <tr>
                <td align="right">考试结束时间:</td>
                <td><input type="text" id="add-endTime" name="endTime" class="wu-text easyui-datetimebox easyui-validatebox" editable="false"></td>
            </tr>
            <tr>
                <td align="right">考试限制时间:</td>
                <td><input type="text" id="add-avaliableTime" name="avaliableTime" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试限制时间'" ></td>
            </tr>
            <tr>
                <td align="right">总分数:</td>
                <td><input type="text" id="add-totalScore" name="totalScore" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试总分'" ></td>
            </tr>
            <tr>
                <td align="right">及格分数线:</td>
                <td><input type="text" id="add-passScore" name="passScore" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试及格分数线'" ></td>
            </tr>
            <tr>
                <td align="right">单选题数量:</td>
                <td><input type="text"  id="add-singleQuestionNum" name="singleQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试单选题数量'" ></td>
            </tr>
            <tr>
                <td align="right">多选题数量:</td>
                <td><input type="text"  id="add-muiltQuestionNum" name="muiltQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试多选题数量'" ></td>
            </tr>
            <tr>
                <td align="right">判断题数量:</td>
                <td><input type="text"  id="add-chargeQuestionNum" name="chargeQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试判断题数量'" ></td>
            </tr>
        </table>
    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="edit-form" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table>
            <tr>
                <td align="right">考试名称:</td>
                <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试名称'" ></td>
            </tr>
            <tr>
                <td align="right">所属科目:</td>
                <td>
                    <input id="edit-subjectId" class="easyui-combobox" name="subjectId" panelHeight="auto" style="width:268px;" data-options="
                            url:'/admin/subject/list2',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            required:true,
                            missingMessage:'请选择学科'
                            ">
<#--                    <select id="edit-subjectId" name="subjectId" class="easyui-combobox easyui-validatebox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择考试科目'">-->
<#--                        <c:forEach items="${subjectList}" var="subject">-->
<#--                            <option value="${subject.id}">${subject.name}</option>-->
<#--                        </c:forEach>-->
<#--                    </select>-->
                </td>
            </tr>
            <tr>
                <td align="right">考试开始时间:</td>
                <td><input type="text" id="edit-startTime" name="startTime" class="wu-text easyui-datetimebox easyui-validatebox" editable="false"></td>
            </tr>
            <tr>
                <td align="right">考试结束时间:</td>
                <td><input type="text" id="edit-endTime" name="endTime" class="wu-text easyui-datetimebox easyui-validatebox" editable="false"></td>
            </tr>
            <tr>
                <td align="right">考试限制时间:</td>
                <td><input type="text" id="edit-avaliableTime" name="avaliableTime" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试限制时间'" ></td>
            </tr>
            <tr>
                <td align="right">及格分数线:</td>
                <td><input type="text" id="edit-passScore" name="passScore" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试及格分数线'" ></td>
            </tr>
            <tr>
                <td align="right">单选题数量:</td>
                <td><input type="text"  id="edit-singleQuestionNum" name="singleQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试单选题数量'" ></td>
            </tr>
            <tr>
                <td align="right">多选题数量:</td>
                <td><input type="text"  id="edit-muiltQuestionNum" name="muiltQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试多选题数量'" ></td>
            </tr>
            <tr>
                <td align="right">判断题数量:</td>
                <td><input type="text" id="edit-chargeQuestionNum" name="chargeQuestionNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考试判断题数量'" ></td>
            </tr>
        </table>
    </form>
</div>
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/static/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>

<!-- End of easyui-dialog -->
<script type="text/javascript">



    /**
     *  添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','添加成功！','info');
                    $("#add-name").val('');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    function edit(){
        var validate = $("#edit-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#edit-form").serialize();
        console.log(data);
        $.ajax({
            url:'edit',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $("#edit-name").val('');
                    $("#edit-remark").val('');
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
            title: "编辑考试信息",
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
                $("#edit-name").val(item.name);
                $("#edit-startTime").datetimebox('setValue',format(item.startTime));
                $("#edit-endTime").datetimebox('setValue',format(item.endTime));
                $("#edit-passScore").val(item.passScore);
                $("#edit-singleQuestionNum").val(item.singleQuestionNum);
                $("#edit-muiltQuestionNum").val(item.muiltQuestionNum);
                $("#edit-chargeQuestionNum").val(item.chargeQuestionNum);
                $("#edit-singleQuestionNum").val(item.singleQuestionNum);
                $("#edit-avaliableTime").val(item.avaliableTime);
                $("#edit-subjectId").combobox('setValue',item.subjectId);
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        //$('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加考试信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                //$("#add-form input").val('');
            }
        });
    }

    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {name:$("#search-name").val()};
        var subjectId = $("#search-subject").combobox('getValue');
        if(subjectId != -1){
            option.subjectId = subjectId;
        }
        var startTime = $("#search-startTime").datetimebox('getValue');
        var endTime = $("#search-endTime").datetimebox('getValue');
        if(startTime != null && startTime != ''){
            option.startTime = startTime;
        }
        if(endTime != null && startTime != ''){
            option.endTime = endTime;
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
            { field:'name',title:'考试名称',width:256,sortable:true},
            { field:'subjectId',title:'所属学科',width:160,formatter:function(value,index,row){
                    var subjectList = $("#search-subject").combobox("getData");
                    for(var i=0;i<subjectList.length;i++){
                        if(subjectList[i].id == value)return subjectList[i].name;
                    }
                    return value;
                }},
            { field:'startTime',title:'考试开始日期',width:200,formatter:function(value,index,row){
                    return format(value);
                }},
            { field:'endTime',title:'考试结束日期',width:200,formatter:function(value,index,row){
                    return format(value);
                }},
            { field:'avaliableTime',title:'限制时间',width:110,formatter:function(value,index,row){
                    return value + '分钟';
                }},
            { field:'questionNum',title:'试题总数',width:110},
            { field:'totalScore',title:'总分',width:60},
            { field:'passScore',title:'及格线',width:80},
            { field:'singleQuestionNum',title:'单选题数量',width:150},
            { field:'muiltQuestionNum',title:'多选题数量',width:150},
            { field:'chargeQuestionNum',title:'判断题数量',width:150},
            { field:'paperNum',title:'生成试卷数量',width:190},
            { field:'examedNum',title:'已考人数',width:110},
            { field:'passNum',title:'及格人数',width:110},
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