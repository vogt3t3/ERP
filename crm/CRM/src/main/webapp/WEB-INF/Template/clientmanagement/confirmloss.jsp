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
            $(".datepicker").datepicker();

            $('#list').hide();
            
            $('#save').click(function () {
                $('#lstInfo').submit();
            })
        })

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户流失管理<b class="tip"></b>确认流失</div>
    <form id="lstInfo" action="client/sure_Lst" method="post">
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

			
             <tr>
                <td colspan="4">
                 <input class="btn btn-inverse" id="lxr" type="button" value="暂缓流失" onclick="window.location.href='client/open_Lst?lstId=${lst.lst_id }'"  />
                    <input class="btn btn-inverse" id="save" type="button" value="保存" />
                   
                    <input class="btn btn-inverse" type="button" value="返回"  onclick="history.back()" /></td>
             </tr>
            
            <tr>
          	<td width="40">编号*</td>
			<td width="40">${lst.lst_id }<input type="hidden" name="cst_lst_t.lst_id" value="${lst.lst_id }"/></td>
            <td width="40">客户*</td>
            <td width="40">${lst.lst_cst_name }<input type="hidden" name="cst_lst_t.lst_cst_name" value="${lst.lst_cst_name }"/></td>
            </tr>
            <tr>
           	<td width="40">客户经理*</td>
             <td width="40">${lst.lst_cst_manager_name }<input type="hidden" name="cst_lst_t.lst_cst_manager_name" value="${lst.lst_cst_manager_name }"/></td>
            <td width="40">上次下单时间</td>
             <td width="40">${lst.lst_last_order_date }<input type="hidden" name="cst_lst_t.lst_last_order_date" value="${lst.lst_last_order_date }"/></td>
            </tr>
            
             <tr>
           	<td colspan="2" width="40">暂缓流失</td>
             	
                 <td width="500" colspan="3" height="">${lst.lst_delay }<input type="hidden" name="cst_lst_t.lst_delay" value="${lst.lst_delay }"/></td>
            </tr>
            <tr>
           	<td colspan="2" width="40"><p>流失原因</p>
       	   <p>&nbsp;</p></td>
             	
                 <td width="500" colspan="3" height="">
                    <textarea name="cst_lst_t.lst_reason" style="width: 95%" rows="4" cols="5">${lst.lst_reason }</textarea>                </td>
            </tr>
        </tbody>
    </table>
    </form>
</body>
</html>
