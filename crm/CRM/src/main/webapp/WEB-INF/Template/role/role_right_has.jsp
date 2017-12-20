<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
            
            
            $('#save').click(function(){
            	$('#rightInfo').submit();
            });
            
           
        });
        
        
       
        
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>角色【${sys_role_t.role_name}】拥有的授权<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
    
        <tbody>
            <tr>
                <td colspan="6" align="right">
                 
                 <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/>
                   
            </tr>
        </tbody>
    </table>
    
    <form id="rightInfo" action="right/give_Role" method="post">
    <input type="hidden" name="role_id" value="${sys_role_t.role_id }"/>
    <table class="table table-striped table-bordered table-condensed" id="list1">
        
        <thead>
           
            <tr class="tr_detail"><td>顶级模块</td><td>二级模块</td><td>启用</td></tr>
           	
        </thead>
        <tbody>
        	<c:forEach items="${sys_role_t.sys_right_t }" var="right">
        				<c:if test='${right.rigth_url=="" }'>
        				<tr>
        				<td>${right.right_tip }</td>
        				<td>&nbsp;</td>
        				<td><input type="checkbox" value="${right.right_id }" name="rightsId"/></td>
        				</tr>
        				
        				<c:forEach items="${right.rights }" var="r">
        				<tr>
        				<td>&nbsp;</td>
        				<td>${r.right_tip }</td>
        				<td><input type="checkbox" value="${r.right_id }" name="rightsId"  
        					
        				/></td>
        				</tr>
        				
        				</c:forEach>
        				</c:if>
        	</c:forEach>
        </tbody>
	</table>
	</form>
</body>
</html>
