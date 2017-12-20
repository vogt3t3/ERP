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
            
            
            $('#find').click(function(){
            	$('#list').show();
            });
            
            
            $('#save').click(function(){
            	$('#clientinfo').submit();
            });
            
            
            $('.btn1').click(function(){
            	var parent_id=$('#parentId'+this.id+' option:selected').val();
            	var id=this.id;
            	var right_type=$('#type'+this.id).val();
            	var right_text=$('#text'+this.id).val();
            	var rigth_url=$('#url'+this.id).val();
            	var right_tip=$('#tip'+this.id).val();
            	window.location.href='right/mod_Right?right_id='+id+'&parent_id='+parent_id+'&right_type='+right_type+'&right_text='+right_text+'&rigth_url='+rigth_url+'&right_tip='+right_tip;
                
            });
        });
        
        
       
        
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户管理<b class="tip"></b></div>
    <table class="table table-striped table-bordered table-condensed">
    
        <tbody>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/>
                   
            </tr>
        </tbody>
    </table>
    
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td>父亲模块</td>
                <td>权限类型</td>
                <td>权限描述</td>
                <td>权限对应的url</td>
                <td>权限简称</td>
                <td>操作</td>
            </tr>
            
           <c:forEach items="${rights }" var="right">
           	<tr>
           		
           		<td>
           			<select id="parentId${right.right_id }" name="sys_right_t.sys_right_t.right_id"> 
           				<option value=''></option>
           				<c:forEach items="${parentRight }" var="parent">
           					<option value="${parent.right_id }" ${right.sys_right_t.right_id==parent.right_id?'selected':'' }>${parent.right_tip }</option>
           				</c:forEach>
           			</select>
           		</td>
           		<td><input id="type${right.right_id }" name="sys_right_t.right_type" type="text" value="${right.right_type }"/> </td>
              	<td><input id="text${right.right_id }" name="sys_right_t.right_text" type="text" value="${right.right_text }"/> </td>
                <td><input id="url${right.right_id }"  name="sys_right_t.rigth_url" type="text" value="${right.rigth_url }"/> </td>
                
             	<td><input id="tip${right.right_id }"  name="sys_right_t.right_tip" type="text" value="${right.right_tip }"/> </td>
             	<td><input id="${right.right_id }" type="button" value="修改" class="btn btn-inverse btn1"/> </td>
           	</tr>
            
           </c:forEach>
            
         
        </thead>
        <tbody>
        	
        
        </tbody>
	</table>
	
</body>
</html>
