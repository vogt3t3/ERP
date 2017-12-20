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
            
           
            $('#find').click(function(){
            	$('#list').show();
            });
            
           
        });
    </script>
  </head>
  
  <body>
  <div class="alert alert-info">当前位置<b class="tip"></b>用户信息修改<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">

        <tbody>

            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/>
                   </td>
            </tr>
        </tbody>
    </table>
    
    
    <form action="right/add_User" method="post">
    <input type="hidden" value="${user.user_id }" name="sys_user_t.user_id"> 
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            
            <tr class="tr_detail"><td>用户名称</td><td><input name="sys_user_t.user_name" type="text" value="${user.user_name }"/> </td></tr>
            <tr class="tr_detail"><td>角用户密码</td><td><input name="sys_user_t.user_password" type="text" value="${user.user_password }"/> </td></tr>
            <tr class="tr_detail"><td>用户状态</td><td>
            <select name="sys_user_t.user_flag">
            	<option value="注销" ${user.user_flag=="注销"?'selected':'' }>注销</option>
            	<option value="使用中" ${user.user_flag=="使用中"?'selected':'' }>使用中</option>
            </select>
            </td></tr>
           	<tr><td  colspan="6"><input id="save" type="submit" class="btn btn-inverse" value="保存" /></td></tr>
        </thead>
        <tbody>
        	
        
        </tbody>
	</table>
	</form>
	

	
  </body>
</html>
