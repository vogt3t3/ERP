<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
            	var name=$('#name').val();
            	var linkman=$('#linkman').val();
            	var title=$('#title').val();
 
            	window.location.href='chance/search_Chance?title='+title+'&name='+name+'&linkman='+linkman;
                
            })
        })

    </script>
  </head>
  
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>营销机会管理<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <td colspan="6" class="auto-style2">&nbsp;请填写查询条件 </td>
            </tr>
        </thead>
        <tbody>

            <tr>
                <td>客户名字</td>
                <td class="detail">
                    <input type="text" id="name"/></td>
                <td>概要</td>
                <td class="td_detail">
                    <input type="text" id="title" /></td>
                <td>联系人</td>
                <td><input type="text" id="linkman"/></td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="查询" />
                    <a target="Conframe" href="chance/open_CreateChance"><input class="btn btn-inverse" type="button" value="新建" /></a>
                    <input name="button" type="button" class="btn btn-inverse" value="帮助" /></td>
            </tr>
        </tbody>
    </table>
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td>编号</td>
                <td>客户名称</td>
                <td>概要</td>
                <td>联系人</td>
                <td>联系人电话</td>
                <td>创建时间</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
        	
        	<c:forEach items="${chanceList }" var="list">
            <tr>
                <td>${list.chc_id}</td>
                <td>${list.chc_cust_name}</td>
                <td>${list.chc_title}</td>
                <td>${list.chc_linkman}</td>
                <td>${list.chc_tel}</td>
                <td><fmt:formatDate value="${list.chc_create_date}" type="both" /></td>
                <td>
                <button  onclick="window.location.href='chance/detail_Chance?cid=${list.chc_id}'">编辑</button >
                
                </td>
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
