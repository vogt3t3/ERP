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
           
            $('#list').show();
            $('#find').click(function () {
            	var iname=$('#iname').val();
            	var cname=$('#cname').val();
            	window.location.href='indent/search_Indent?iname='+iname+'&cname='+cname;
                
            });
        })

    </script>
  </head>
  
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>订单管理<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <td colspan="6" class="auto-style2">&nbsp;请填写查询条件 </td>
            </tr>
        </thead>
        <tbody>

          
                 <tr>
                <td>订单名称</td>
                <td class="detail">
                   <input id="iname" type="text" /> </td>
                <td>客户名称</td>
                <td class="td_detail">
                    <input id="cname" type="text" /></td>
              
                </tr>
           
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="查询" />
                    <a target="Conframe" href="indent/open_addIndent"><input class="btn btn-inverse" type="button" value="新建" /></a>
                    <input name="button" type="button" class="btn btn-inverse" value="帮助" /></td>
            </tr>
        </tbody>
    </table>
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td>订单编号</td>
                <td>订单名称</td>
                <td>客户名称</td>
                <td>日期</td>
                <td>订单金额</td>
                <td>送货地址</td>
                <td>订单状态</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
        	
        	<c:forEach items="${indentList }" var="list">
            <tr>
                <td>${list.indent_id}</td>
                <td>${list.indent_name}</td>

                <td>${list.cst_customer_t.cst_name}</td>
                <td>${list.indent_date}</td>
                <td>${list.indent_sum}</td>
                <td>${list.indent_destination}</td>
                <td>${list.indent_state}</td>
                <td><button  onclick="window.location.href='indent/detail_Indent?id=${list.indent_id}'">编辑</button ></td>
            </tr>
           </c:forEach>
          
        </tbody>
</table>
</body>
</html>
