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
            <#list examinee as m>
                <a href="#" class="easyui-linkbutton" iconCls="${m.icon}" onclick="${m.url}" plain="true">${m.name}</a>
            </#list>
        </div>
        <div class="wu-toolbar-search">
            <label>考生名称:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>所属学科:</label>
            <input id="search-subject" class="easyui-combobox" panelHeight="auto" style="width:120px;" data-options="
                            url:'/admin/subject/list1',
                            method:'post',
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            labelPosition: 'top',
                            ">
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
                <td align="right">考生账号:</td>
                <td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生账号'" ></td>
            </tr>
            <tr>
                <td align="right">所属学科:</td>
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
                <td align="right">考生密码:</td>
                <td><input type="password" id="add-password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生密码'" ></td>
            </tr>
            <tr>
                <td align="right">考生姓名:</td>
                <td><input type="text" id="add-truename" name="trueName" class="wu-text" ></td>
            </tr>
            <tr>
                <td align="right">考生手机:</td>
                <td><input type="text" id="add-tel" name="tel" class="wu-text" ></td>
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
                <td align="right">考生账号:</td>
                <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生账号'" ></td>
            </tr>
            <tr>
                <td align="right">所属学科:</td>
                <td>
                    <select id="edit-subjectId" name="subjectId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择学科'">
                        <#--                        <c:forEach items="${subjectList }" var="subject">-->
                        <#--                            <option value="${subject.id }">${subject.name }</option>-->
                        <#--                        </c:forEach>-->
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">考生密码:</td>
                <td><input type="password" id="edit-password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写考生密码'" ></td>
            </tr>
            <tr>
                <td align="right">考生姓名:</td>
                <td><input type="text" id="edit-truename" name="trueName" class="wu-text" ></td>
            </tr>
            <tr>
                <td align="right">考生手机:</td>
                <td><input type="text" id="edit-tel" name="tel" class="wu-text" ></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<!--加载动画-->
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#F9F9F9;text-align :center;padding-top:20%;">
    <img src="/static/easyui/images/load-page.gif" width="50%">
    <h1><font color="#15428B">加载中....</font></h1>
</div>
</body>
</html>
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
                    $("#add-password").val('');
                    $("#add-truename").val('');
                    $("#add-tel").val('');
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
            title: "编辑考生信息",
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
                $("#edit-password").val(item.password);
                $("#edit-truename").val(item.trueName);
                $("#edit-tel").val(item.tel);
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
            title: "添加考生信息",
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
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'name',title:'登录名',width:100,sortable:true},
            { field:'subjectId',title:'所属学科',width:100,formatter:function(value,index,row){
                    var subjectList = $("#search-subject").combobox('getData');
                    console.log(subjectList);
                    console.log(value);
                    for(var i=0;i<subjectList.length;i++){
                        if(subjectList[i].id == value)return subjectList[i].name;
                    }
                    return (value);
                }},
            { field:'password',title:'登录密码',width:200},
            { field:'trueName',title:'姓名',width:200},
            { field:'tel',title:'手机号码',width:200},
            { field:'createTime',title:'注册时间',width:200,formatter:function(value,index,row){
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