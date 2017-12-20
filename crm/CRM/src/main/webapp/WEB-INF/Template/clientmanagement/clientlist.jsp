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

            
            $('#find').click(function () {
            	var area=$('#area').val();
            	var manager=$('#manager').val();
            	var credit=$('#credit').val();
 
            	window.location.href='client/search_Client?manager='+manager+'&area='+area+'&credit='+credit;
                
            });
        });

    </script>
  </head>
  
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户管理<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <td colspan="6" class="auto-style2">&nbsp;请填写查询条件 </td>
            </tr>
        </thead>
        <tbody>

            <tr>
                <td>地区</td>
                <td class="detail">
                    <select id="area">
                    <option value="" >全部</option>
                    <option value="北京" >北京</option>
                    <option value="上海">上海</option>
                    <option value="广州" >广州</option>
                    <option value="深圳">深圳</option>
                </select>         </td>
                <td>客户经理</td>
                <td class="td_detail">
                    <input id="manager" type="text" /></td>
                <td>客户信誉度</td>
                <td><select id="credit" name="select">
                <option value="" >全部</option>
                  <option value="0">☆</option>
                  
                  <option value="1">☆☆</option>
                  <option value="2">☆☆☆</option>
                  <option value="3">☆☆☆☆</option>
                  <option value="4">☆☆☆☆☆</option>
                  <option value="5">☆☆☆☆☆☆</option>
                </select></td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="查询" />
                    <a target="Conframe" href="client/open_CreateClient"><input class="btn btn-inverse" type="button" value="新建" /></a>
                    <input name="button" type="button" class="btn btn-inverse" value="帮助" /></td>
            </tr>
        </tbody>
    </table>
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td>编号</td>
                <td>客户名称</td>
                <td>客户级别名称</td>
                <td>客户经理</td>
                <td>客户法人</td>
                <td>客户状态</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
        	
        	<c:forEach items="${clientList }" var="list">
            <tr>
                <td>${list.cst_id}</td>
                <td>${list.cst_name}</td>
                <td>${list.cst_level_label}</td>
                <td>${list.cust_manager_name}</td>
                <td>${list.cst_chieftain}</td>
                <td><c:if test="${list.cst_status==0 }">正常</c:if><c:if test="${list.cst_status==1 }">流失</c:if></td>
                <td><button  onclick="window.location.href='client/detail_Client?id=${list.cst_id}'">编辑</button ></td>
            </tr>
           </c:forEach>
            <tr class="tr_pagenumber">
                <td colspan="100">当前第 页/共 页&nbsp;&nbsp;共 条记录&nbsp;&nbsp;<a class="badge badge-inverse">首页</a>&nbsp;<a class="badge badge-inverse">下一页</a>&nbsp;
                    <a class="badge badge-inverse">1</a>&nbsp;<a class="badge badge-inverse">2</a>&nbsp;<a class="badge badge-inverse">3</a>&nbsp;<a class="badge badge-inverse">4</a>&nbsp;
                    <a class="badge badge-warning">5</a>&nbsp;<a class="badge badge-inverse">...</a>&nbsp;<a class="badge badge-inverse">55</a>&nbsp;<a class="badge badge-inverse">上一页</a>&nbsp;<a class="badge badge-inverse">尾页</a></td>
            </tr>
        </tbody>
</table>
</body>
</html>
