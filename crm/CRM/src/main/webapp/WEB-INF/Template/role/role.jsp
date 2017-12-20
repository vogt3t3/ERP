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
            
            $('#list').hide();
            $('#find').click(function(){
            	$('#list').show();
            });
            
           
        });
    </script>
  </head>
  
  <body>
  <div class="alert alert-info">当前位置<b class="tip"></b>权限角色<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">

        <tbody>

            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="添加角色" />
                   </td>
            </tr>
        </tbody>
    </table>
    
     <table class="table table-striped table-bordered table-condensed" >
        <thead>
            <tr class="tr_detail">
                <td>编号</td>
                <td>角色名称</td>
                <td>角色描述</td>
                <td>角色状态</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
        	
        	<c:forEach items="${roleList }" var="list">
            <tr>
                <td>${list.role_id}</td>
                <td>${list.role_name}</td>
                <td>${list.role_desc}</td>
                <td>${list.role_flag}</td>
                <td><button  onclick="window.location.href='right/open_giveRole?role_id=${list.role_id}'">权限授予</button >
                	<button  onclick="window.location.href='right/open_hasRole?role_id=${list.role_id}'">查看拥有的权限</button >
                
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
    
    <form action="right/add_Role" method="post">
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
           
            <tr class="tr_detail"><td>角色名称</td><td><input name="sys_role_t.role_name" type="text"/> </td></tr>
            <tr class="tr_detail"><td>角色描述</td><td><input name="sys_role_t.role_desc" type="text"/> </td></tr>
            <tr class="tr_detail"><td>角色状态</td><td>
            <select name="sys_role_t.role_flag">
            	<option value="注销">注销</option>
            	<option value="使用中">使用中</option>
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
