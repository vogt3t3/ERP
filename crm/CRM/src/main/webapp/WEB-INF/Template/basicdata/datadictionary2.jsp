﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="Styles/admin-all.css" />
    <script type="text/javascript" src="Scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="Scripts/jquery-ui-1.8.22.custom.min.js"></script>
    <link rel="stylesheet" type="text/css" href="Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
    <script type="text/javascript">
        $(function () {
            $('#save').click(function(){
            	$('#dictAlter').submit();
            });
           
        });
    </script>
    <style type="text/css">
<!--
.STYLE2 {font-size: small}
-->
    </style>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>服务管理<b class="tip"></b>服务创建</div>
	<form id="dictAlter" action="dict/add_Dict" method="post">
    <table class="table table-striped table-bordered table-condensed" id="list">
        
        <tbody>
            <tr>
                <td width="15%"><div align="center">
                  <p>&nbsp;</p>
                  <p>编号</p>
                </div></td>
                <td width="500">
                <input id="id" type="text" name="bas_dict_t.dict_id" value="${dict.dict_id}"readonly="readonly"/></td>
                <td width="500"><div align="center">
                  <p>&nbsp;</p>
                  <p>类别<font color="FF0000">*</font></p>
                </div></td>
              <td width="500"><p>
                <input id="type" type="text"name="bas_dict_t.dict_type" value="${dict.dict_type}" />
              </p>
                <p><span class="STYLE2">（需要使用Ajax实现自动补全功能）</span></p></td>
            </tr>
            <tr>
                <td><div align="center">条目<font color="FF0000">*</font></div></td>
              <td>
                    <input id="item" type="text"  name="bas_dict_t.dict_item" value="${dict.dict_item}" /></td>
                <td><div align="center">值<font color="FF0000"></font><font color="FF0000">*</font></div></td>
              <td>&nbsp;
                <input id="value" type="text" name="bas_dict_t.dict_value" value="${dict.dict_value}"/> </td>
            </tr>
            <tr>
                <td><div align="center">是否可编辑<font color="FF0000"></font></div></td>
                <td><form id="form1" name="form1" method="post" action="">
                  <label>
                    <input id="edit" type="checkbox" name="bas_dict_t.dict_is_editable" value="是"checked onclick="javascript:if(this.checked){this.value='Y';}else{this.value='N';} alert(this.value);"${dict.dict_is_editable=="是"?'checked':'' }/>
                  </label>
                </form>
                </td>
    
     
                <td colspan="2">&nbsp;</td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
				    <input class="btn btn-inverse"  type="button" value="返回" onclick="window.location.href='dict/search_Dict'"/>
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />&nbsp;</td>
            </tr>
        </tfoot>
    </table>
    </form>
</body>
</html>
