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
            $(".datepicker").datepicker();
 			$('#list').show();
 
            $('#find').click(function () {
            	var type=$('#type').val();
            	var name=$('#name').val();
            	var value=$('#value').val();
            	window.location.href='dict/search_Dict?type='+type+'&name='+name+'&value='+value;
               
            });
        });

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>基础数据<b class="tip"></b>数据字典管理</div>
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

            <tr>
                <td width="7%"><div align="center">类别</div></td>
                <td width="26%" class="detail">
                    <input id="type" type="text" />                </td>
                <td width="7%"><div align="center">条目</div></td>
                <td width="26%" class="td_detail">
                    <input id="name" type="text" /></td>
                <td width="8%"><div align="center">值</div></td>
                <td width="26%"><span class="td_detail">
                  <input id="value" name="text" type="text" />
              </span></td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="查询" />&nbsp;
                    <input class="btn btn-inverse" type="button" value="新建" onclick="window.location.href='dict/open_InfoDict'"/>&nbsp;</td>
            </tr>
        </tbody>
    </table>
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
              <td width="12%"><div align="center">编号</div></td>
                <td width="29%"><div align="center">类别</div></td>
                <td width="17%"><div align="center">条目</div></td>
              <td width="11%"><div align="center">值</div></td>
              <td width="11%"><div align="center">是否可编辑</div></td>
              <td width="9%"><div align="center">操作</div></td>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${dictList }" var="dict">
        	
            <tr>
                <td><div align="center">${dict.dict_id }</td>
                <td><div align="center">${dict.dict_type}</td>
                <td><div align="center">${dict.dict_item }</td>
                <td><div align="center">${dict.dict_value }</td>
                <td><div align="center">${dict.dict_is_editable }</td>
             	<c:if test='${dict.dict_is_editable=="是"}'>
                <td><div align="center"><input type="button" class="btn btn-inverse" id="edit" value="编辑" onclick="window.location.href='dict/open_Alter?id=${dict.dict_id}' "/></td>
           		</c:if>
           		<c:if test='${dict.dict_is_editable=="否"}'>
           			<Td><div align="center">不可编辑</Td>
           		</c:if>
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
