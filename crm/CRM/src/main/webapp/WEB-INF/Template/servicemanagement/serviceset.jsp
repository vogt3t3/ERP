<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

            $('#save').click(function(){
            	$('#serviceInfo').submit();
            });
            
        })
    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>服务管理<b class="tip"></b>服务创建</div>
    <form id="serviceInfo" action="service/add_Service" method="post">
    <table class="table table-striped table-bordered table-condensed list">
        
        <tbody>
            <tr>
                <td width="15%"><div align="center">编号</div></td>
                <td width="500">
                    <input name="cst_service_t.svc_id" value="${cst_service_t.svc_id }" type="text" />                </td>
                 <td width="500"><div align="center">服务类型<font color="FF0000">*</font></div></td>
                  <td><select name="cst_service_t.svc_type">
                  <option value="建议" ${cst_service_t.svc_type=="建议"?'selected':'' }>建议</option>
                  <option value="咨询" ${cst_service_t.svc_type=="咨询"?'selected':'' }>咨询</option>
                  <option value="投诉" ${cst_service_t.svc_type=="投诉"?'selected':'' }>投诉</option>
                
                </select></td> 
               
                    
            </tr>
            <tr>
                <td><div align="center">概要<font color="FF0000">*</font></div></td>
              <td >
              <input name="cst_service_t.svc_title" value="${cst_service_t.svc_title }" type="text"  /></td>
            </tr>
            <tr>
                <td><div align="center">客户<font color="FF0000">*</font></div></td>
              <td>
                    <input name="cst_service_t.svc_cst_name" value="${cst_service_t.svc_cst_name}" type="text"   /></td>
                <td><div align="center">状态<font color="FF0000"></font></div></td>
              <td>新创建<input type="hidden" value="新创建" name="cst_service_t.svc_status"/></td>
            </tr>
            <tr>
              <td><div align="center">
                <p>&nbsp;</p>
                <p>服务请求<font color="FF0000">*</font></p>
              </div></td>
              <td width="500" colspan="3" height="">
                    <textarea name="cst_service_t.svc_request" style="width: 95%" rows="4" cols="5">${cst_service_t.svc_request}</textarea></td>
            </tr>
            <tr>
                <td><div align="center">创建人<font color="FF0000">*</font></div></td>
                <td><input name="cst_service_t.svc_create_by" value="${cst_service_t.svc_create_by}" type="text"></td>
                <td><div align="center">创建时间<font color="FF0000">*</font></div></td>
                <td><input name="cst_service_t.svc_create_date" value="${cst_service_t.svc_create_date}"  id="today"/></td>
                
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />
                    <input class="btn btn-inverse" type="button" value="帮助" /></td>
            </tr>
        </tfoot>
    </table>
    </form>
</body>
</html>
