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
            $(".datepicker").datepicker();

            $('#list').hide();
            $('#find').click(function () {
                $('#list').show();
            })
        })

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户信息管理<b class="tip"></b>历史订单<b class="tip"></b>订单明细</div>
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

			 <tr>
                <td colspan="4">
                  
                   
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/></td>
            </tr>
            <tr>
                <td width="91">订单编号</td>
                <td width="107">${cst_indent_t.indent_id }</td>
                <td width="85">日期</td>
                <td width="749">${cst_indent_t.indent_date }</td>
                
            </tr>
            
             <tr>
                <td width="91">送货地址</td>
                <td width="107">${cst_indent_t.indent_destination }</td>
                <td width="85">总金额（元）</td>
                <td width="749">${cst_indent_t.indent_sum }</td>
                
            </tr>
            
             <tr>
                <td width="91">状态</td>
                <td width="107">${cst_indent_t.indent_state }</td>
                <td width="85">&nbsp;</td>
                <td width="749">&nbsp;</td>
                
            </tr>
            
        </tbody>
        <tfoot>
           
            
        </tfoot>
    </table>
    <table width="80%"  class="table table-striped table-bordered table-condensed list">
           <tr>
           	<td width="60">商品</td>
            <td width="60">数量</td>
            <td width="60">单位</td>
            <td width="60">单价（元）</td>
            
            <td width="60">金额(元)</td>
         
			</tr>
            <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>   
            <td>&nbsp;</td>
           <td>
            
        </tbody>
    </table>
</body>
</html>
