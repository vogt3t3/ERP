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
    
    <title>My JSP 'index.jsp' starting page</title>
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
            $('.modal').show();
            
            $('#save').click(function(){
            	$('#serviceinfo').submit();
            });
        })
    </script>
    <style type="text/css">
<!--
.STYLE1 {color: #000000}
-->
    </style>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>服务管理<b class="tip"></b>服务处理</div>
    <s:form id="serviceinfo" action="service/addresolve_Service" method="post">
    <table class="table table-striped table-bordered table-condensed list"> 
        <thead>
            <tr>
                <td colspan="4">服务处理</td>
            </tr>
        </thead>
        <tbody>
            
            <tr>
                <td width="15%">编号<font color="FF0000"></font></td>
                <td width="500">
                    <input name="cst_service_t.svc_id" value="${cst_service_t.svc_id}" type="text" readonly="readonly"/>                </td>
                <td width="500">服务类型<font color="FF0000"></font></td>
                <td width="500">
                    <input name="cst_service_t.svc_type" value="${cst_service_t.svc_type}" type="text" readonly="readonly"/>                </td>
            </tr>
	    <tr>
                <td>概要</td>
                <td>
                    <input name="cst_service_t.svc_title" value="${cst_service_t.svc_title}" type="text" readonly="readonly"/>                </td>
            </tr>
			
            <tr>
                <td><span class="STYLE1">客户</span><font color="FF0000"></font></td>
                <td>
                    <input name="cst_service_t.svc_cst_name" value="${cst_service_t.svc_cst_name}" type="text" readonly="readonly"/>                </td>
                <td>状态<font color="FF0000"></font></td>
                <td>
                    <input name="cst_service_t.svc_status" value="${cst_service_t.svc_status}" type="text" readonly="readonly"/></td>
            </tr>
            <tr>
                <td>服务请求<font color="FF0000"></font></td><td>
                    <input name="cst_service_t.svc_request" value="${cst_service_t.svc_request}" type="text" readonly="readonly"/>                </td>
            </tr>

           
	    <tr>
                <td width="15%">创建人<font color="FF0000"></font></td>
                <td>
                    <input name="cst_service_t.svc_create_by" value="${cst_service_t.svc_create_by}"  type="text" readonly="readonly"/>                </td>
                <td width="500">创建时间</td>
                <td width="500">
                    <input name="cst_service_t.svc_create_date" value="${cst_service_t.svc_create_date}"  type="text" readonly="readonly"/>               </td>
            </tr>
            <tr>
                <td width="15%">分配给<font color="FF0000"></font></td>
                <td>
                    <input name="cst_service_t.svc_due_to" value="${cst_service_t.svc_due_to}"  type="text" readonly="readonly"/>                </td>
                <td width="500">分配时间</td>
                <td width="500">
                    <input name="cst_service_t.svc_due_date" value="${cst_service_t.svc_due_date}"  type="text" readonly="readonly"/>               </td>
            </tr>
            
            <tr>
                <td width="15%">服务处理<font color="FF0000"></font></td>
                <td width="500" colspan="3" height="">
                    <textarea name="cst_service_t.svc_deal" style="width: 95%" rows="4" cols="5">${cst_service_t.svc_deal}</textarea>                </td>
       
               
            </tr>
	    <tr>
                <td width="15%">处理人<font color="FF0000"></font></td>
                <td>
                    <input name="cst_service_t.svc_deal_by" value="${cst_service_t.svc_deal_by}" type="text" />                </td>
                <td width="500">处理时间</td>
                <td width="500">
                    <!--   <input name="cst_service_t.svc_deal_date" value="${cst_service_t.svc_deal_date}" type="text" />--> 
                   <input name="cst_service_t.svc_deal_date" value="${cst_service_t.svc_deal_date}" id="today" />               </td>
            </tr>
			
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/></td>
            </tr>
        </tfoot>
    </table>
    </s:form>
  </body>
</html>
