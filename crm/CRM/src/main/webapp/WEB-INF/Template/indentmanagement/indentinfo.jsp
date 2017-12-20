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
            	$('#indentinfo').submit();
            });
            
            
            $('#clientName').change(function(){
            	$('#clientId option[value='+this.value+']').attr('selected',true);
          
            })
        });
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>订单管理<b class="tip"></b>新建订单</div>
 <s:form id="indentinfo" action="indent/add_Indent" method="post">
    <table class="table table-striped table-bordered table-condensed list">
        <thead>
            <tr>
                <td colspan="4">订单信息</td>
            </tr>
        </thead>
        <tbody>
            
            <tr>
                <td width="15%">订单编号<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_indent_t.indent_id" value="${cst_indent_t.indent_id}" type="text" readonly="readonly"/>                </td>
                <td width="500">订单名称<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_indent_t.indent_name" value="${cst_indent_t.indent_name}" type="text" /></td>
            </tr>
            <tr>
                <td>客户编号<font color="FF0000">*</font></td>
                <td>
                	<select id="clientId" name="cst_indent_t.cst_customer_t.cst_id" >
                		<option value=""></option>
                		<c:forEach  items="${client }" var="client">
                		<option value="${client.cst_id }" >${client.cst_id }</option>
                		</c:forEach>
                	</select>
                    </td>
                <td>客户名称<font color="FF0000">*</font></td>
                <td>
                	<select id="clientName" name="cst_indent_t.cst_customer_t.cst_name">
                		<option value="" ></option>
                		<c:forEach  items="${client }" var="client">
                		<option value="${client.cst_id }" ${cst_indent_t.cst_customer_t.cst_name==client.cst_name?'selected':'' }>${client.cst_name }</option>
                		</c:forEach>
                	</select>
                    </td>
            </tr>
			
			
            <tr>
                <td><span class="STYLE1">订单金额</span><font color="FF0000">*</font></td>
                <td>
                    <input name="cst_indent_t.indent_sum" value="${cst_indent_t.indent_sum}" type="text" />                </td>
                <td>送货地址<font color="FF0000">*</font></td>
                <td>
                    <input name="cst_indent_t.indent_destination" value="${cst_indent_t.indent_destination}" type="text" /></td>
            </tr>
           
            <tr>
                 <td width="15%">订单状态</td>
                
                 <td><select name="cst_indent_t.indent_state">
                  <option value="未回款" ${cst_indent_t.indent_state=="未回款"?'selected':'' }>未回款</option>
                  <option value="已汇款" ${cst_indent_t.indent_state=="已汇款"?'selected':'' }>已汇款</option>
                  
                
                  </select></td>             
            </tr>
			<tr>
                <td width="15%">创建人<font color="FF0000">*</font></td>
                <td>
                    <input name="cst_indent_t.ind_create_by" value="<c:if test="${cst_indent_t}!=''">${cst_indent_t.ind_create_by}</c:if><c:if test="${cst_indent_t}==''">${session.user.user_name}</c:if>" readonly="readonly" type="text" />                </td>
                <td width="500">创建时间</td>
                <td width="500">
                    <input name="cst_indent_t.indent_date" value="${cst_indent_t.indent_date }" type="text" <c:if test="${cst_indent_t==null }">id="today"</c:if>/>                </td>
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
