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
            
            
            $('#save').click(function(){
            	$('#clientinfo').submit();
            });
        });
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>权限管理<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
    
        <tbody>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="添加" />
                    <input type="button" class="btn btn-inverse" value="查看权限" onClick="window.location.href='right/get_Right'"/></td>
            </tr>
        </tbody>
    </table>
    
    <form action="right/add_Right" method="post">
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td>父权限</td>
                <td>
                <select name="sys_right_t.sys_right_t.right_id">
                <option value=''>无</option>
                <c:forEach items="${parentRight}" var="right">
                	<option value='${right.right_id }'>${right.right_tip }</option>
                </c:forEach>
                </select>
                
                </td>
            </tr>
            <tr class="tr_detail">	  <td >权限类型</td><td><input name="sys_right_t.right_type" type="text"/> </td></tr>
            <tr class="tr_detail">    <td>权限描述</td><td><input name="sys_right_t.right_text" type="text"/> </td></tr>
            <tr class="tr_detail">    <td>权限对应的url</td><td><input name="sys_right_t.rigth_url" type="text"/> </td></tr>
            <tr class="tr_detail">    <td>权限简称</td><td><input name="sys_right_t.right_tip" type="text"/> </td></tr>
           	<tr><td  colspan="6"><input id="save" type="submit" class="btn btn-inverse" value="保存" /></td></tr>
        </thead>
        <tbody>
        	
        
        </tbody>
	</table>
	</form>
</body>
</html>
