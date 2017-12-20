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
            $('#find').click(function () {
                $('#list').show();
            })
        })

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户信息管理<b class="tip"></b>历史订单</div>
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

			 <tr>
                <td colspan="4">
                  
                   
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/></td>
            </tr>
            <tr>
                <td width="91">客户编号</td>
                <td width="107">${cst_customer_t.cst_id }</td>
                <td width="85">客户名称<font color="FF0000">*</font></td>
                <td width="749">${cst_customer_t.cst_name }</td>
            </tr>
            
        </tbody>
        <tfoot>
           
            
        </tfoot>
    </table>
    <table width="80%"  class="table table-striped table-bordered table-condensed list">
           <tr>
           <td width="60">订单编号</td>
            <td width="60">日期</td>
            <td width="60">送货地址</td>
            <td width="60">状态</td>
            <td width="90">操作</td>
			</tr>
			<c:forEach items="${cst_customer_t.cst_indent_t }" var="indent">
            <tr>
            <td>${indent.indent_id }</td>
            <td>${indent.indent_date }</td>
            <td>${indent.indent_destination }</td>   
            <td>${indent.indent_state }</td>
            <td><button  onclick="window.location.href='client/get_IndentDetail?identId=${indent.indent_id }'" >明细</button><button onclick="window.location.href='client/delete_IndentDetail?identId=${indent.indent_id }'">删除</button></td>
            </tr>
         	</c:forEach>
         
           
            <tr class="tr_pagenumber">
                <td colspan="100">当前第5页/共55页&nbsp;&nbsp;共650条记录&nbsp;&nbsp;<a class="badge badge-inverse">首页</a>&nbsp;<a class="badge badge-inverse">下一页</a>&nbsp;
                    <a class="badge badge-inverse">1</a>&nbsp;<a class="badge badge-inverse">2</a>&nbsp;<a class="badge badge-inverse">3</a>&nbsp;<a class="badge badge-inverse">4</a>&nbsp;
                    <a class="badge badge-warning">5</a>&nbsp;<a class="badge badge-inverse">...</a>&nbsp;<a class="badge badge-inverse">55</a>&nbsp;<a class="badge badge-inverse">上一页</a>&nbsp;<a class="badge badge-inverse">尾页</a></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
