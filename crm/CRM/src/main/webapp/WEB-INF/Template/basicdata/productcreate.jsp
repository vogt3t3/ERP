<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
            	$('#proInfo').submit();
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
    <div class="alert alert-info">当前位置<b class="tip"></b>基础数据<b class="tip"></b>查询产品信息</div>
	<form id="proInfo" action="dict/add_Pro" method="post">
    <table class="table table-striped table-bordered table-condensed list">
        
        <tbody>
            
            <tr><td valign="top"width="15%">
            <div align="center">编号</td><td valign="top"width="500">
            <input type="text" name="bas_product_t.pro_id" value="" readonly="readonly"></td>
            <td valign="top"width="15%">
            <div align="center">型号<font color="#ff0000">*</font></td>
            <td valign="top"width="500">
            <input type="text" name="bas_product_t.pro_type" value=""></td></tr><tr>
            <td><div align="center">名称<font color="FF0000">*</font></div></td>
            <td><input name="bas_product_t.pro_name" value="" type="text" "  /></td>
            <td><div align="center">批次/等级<font color="FF0000">*</font></div></td>
            <td valign="top"><input type="text" name="bas_product_t.pro_rank" value=""></td>           
            </tr>
            <tr><td valign="top"><div align="center">单位<font color="#ff0000">*</font></td>
            <td valign="top"><input type="text" name="bas_product_t.pro_unit" value=""></td>
            <td valign="top"><div align="center">单价(元)<font color="#ff0000">*</font></td>
            <td valign="top"><input type="text" name="bas_product_t.pro_price" value=""></td></tr>
        </tbody>
        <tfoot>
            <tr><td valign="top"><div align="center">备注<font color="#ff0000">*</font></td>
            <td valign="top"><input type="text" name="bas_product_t.pro_remark" value=""></td>
            <td valign="top"><br></td><td valign="top"><br></td></tr><tr>
                <td colspan="3">
				    <input class="btn btn-inverse" id="back" type="button" value="返回" onclick="javascript:history.back()"/>
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />&nbsp;</td>
            </tr>
        </tfoot>
    </table>
    </form>
</body>
</html>
