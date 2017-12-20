<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    			var date=new Date();
    			var day=date.getDay();
    			var week;
    			
    			var today=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    			$("#today").val(today);
  
        	
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
            	$('#chanceinfo').submit();
            });
        });
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>销售机会管理<b class="tip"></b>销售指派</div>
 <s:form id="chanceinfo" action="chance/add_selectChance" method="post">
    <table class="table table-striped table-bordered table-condensed list">
        <thead>
            <tr>
                <td colspan="4">销售机会</td>
            </tr>
        </thead>
        <tbody>
            
            <tr>
                <td width="15%">编号<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="sal_chance_t.chc_id" value="${sal_chance_t.chc_id }" type="text" readonly="readonly" />                </td>
                <td width="500">机会来源<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="sal_chance_t.chc_source" value="${sal_chance_t.chc_source }" type="text" readonly="readonly" /></td>
            </tr>
            <tr>
                <td>客户名称<font color="FF0000">*</font></td>
                <td>
                    <input name="sal_chance_t.chc_cust_name" value="${sal_chance_t.chc_cust_name }" type="text" readonly="readonly" /></td>
                <td>成功几率<font color="FF0000">*</font></td>
                <td>
                    <input name="sal_chance_t.chc_rate" value="${sal_chance_t.chc_rate }" type="text" readonly="readonly" /></td>
            </tr>
			<tr>
                <td>概要</td>
                <td colspan="3"><input type="text" readonly="readonly" style="width:500px;" name="sal_chance_t.chc_title" value="${sal_chance_t.chc_title }"/></td>
            </tr>
			
            <tr>
                <td><span class="STYLE1">联系人</span><font color="FF0000">*</font></td>
                <td>
                    <input name="sal_chance_t.chc_linkman" value="${sal_chance_t.chc_linkman }" type="text" readonly="readonly" />                </td>
                <td>联系人电话<font color="FF0000">*</font></td>
                <td>
                    <input name="sal_chance_t.chc_tel" value="${sal_chance_t.chc_tel }" type="text" readonly="readonly" /></td>
            </tr>
           
            <tr>
                <td width="15%">机会描述</td>
                <td width="500" colspan="3" height="">
                    <textarea name="sal_chance_t.chc_desc" style="width: 95%" rows="4" cols="5" readonly="readonly">${sal_chance_t.chc_desc }</textarea>                </td>
            </tr>
			<tr>
                <td width="15%">创建人<font color="FF0000">*</font></td>
                <td>
                    <input name="sal_chance_t.chc_create_by" value="${sal_chance_t.chc_create_by }" readonly="readonly" type="text" readonly="readonly" />                </td>
                <td width="500">创建时间</td>
                <td width="500">
                    <input name="sal_chance_t.chc_create_date" value="${sal_chance_t.chc_create_date }" type="text" readonly="readonly" />                </td>
            </tr>
			<tr>
                <td width="15%">指派给谁<font color="FF0000">*</font></td>
                <td>
                   <select name="sal_chance_t.chc_due_to" >
                   <option value="">指派给谁</option>
                   <c:forEach items="${users }" var="user">
                   
                   <option value="${user.id }" ${sal_chance_t.chc_due_to==user.id?'selected':'' }>${user.name }</option>
                   
                   </c:forEach>
                   </select>
                                </td>
                <td width="500">分配时间</td>
                <td width="500">
                    <input name="sal_chance_t.chc_due_date" value="${sal_chance_t.chc_due_date}" type="text" readonly="readonly" id="today" />                </td>
            </tr>
			
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()" />
                    </td>
            </tr>
        </tfoot>
    </table>
    </s:form>
</body>
</html>
