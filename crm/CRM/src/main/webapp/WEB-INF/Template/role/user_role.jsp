<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	 <link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="Styles/admin-all.css" />
    <script type="text/javascript" src="Scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="Scripts/jquery-ui-1.8.22.custom.min.js"></script>
    <link rel="stylesheet" type="text/css" href="Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
    <script type="text/javascript">
        $(function () {
            var _this = $('.list').find('thead');
            //折叠
            _this.click(function () {
                var i = $(this).find('i');
                if (i.attr('class') == 'tip-down') { i.attr('class', 'tip-up') } else { i.attr('class', 'tip-down') }
                $(this).parent().find('tbody').toggle();
            })
            //添加
            var _html = '<tr>' + _this.parent().find('.demo').html() + '</tr>';
            $('.add').click(function () {
                $(this).parents('.list').find('tbody').append(_html);

            })
            //删除
            $('.del').live('click',function () {
                var _tr = $(this).parents('tr');
                // alert(_tr.attr('class'))
                if (_tr.attr('class') != "demo") {
                    if (confirm("你确定要干掉这条数据吗？")) { _tr.remove(); }
                }
            });
            
            $('#list').hide();
            $('#find').click(function(){
            	$('#list').show();
            });
            
           
        });
    </script>
  </head>
  
  <body>
   <form action="right/give_User" method="post">
   <input type="hidden" value="${user.user_id}" name="user_id"> 
   <table class="table table-striped table-bordered table-condensed" >
        <thead>
            <tr class="tr_detail">
                <td>编号</td>
                <td>用户名称</td>
                <td>角用户密码</td>
                <td>用户状态</td>
            </tr>
        </thead>
        <tbody>
        	
       
            <tr>
                <td>${user.user_id}</td>
                <td>${user.user_name}</td>
                <td>${user.user_password}</td>
                <td>${user.user_flag}</td>
               
            </tr>
     
           
        </tbody>
	</table>
    
    
    <table class="table table-striped table-bordered table-condensed" >
        <thead>
            <tr class="tr_detail">
                <td>角色名称</td>
                <td>启用</td>
            </tr>
        </thead>
        <tbody>
        	
        	<c:forEach items="${roles }" var="role">
            <tr>
                <td>${role.role_name}</td>
                <td><input type="checkbox" value="${role.role_id}" name="role_id"/></td>
            </tr>
           </c:forEach>
           
        </tbody>
	</table>
	
	 <input class="btn btn-inverse" id="find" type="submit" value="保存" />
	 <input class="btn btn-inverse" id="find" type="button" value="返回" onclick="javascript:history.back()"/>
    </form>
  </body>
</html>
