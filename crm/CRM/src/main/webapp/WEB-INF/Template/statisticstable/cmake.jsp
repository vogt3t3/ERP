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
            $(".datepicker").datepicker();

            $('#list').hide();
            $('#find').click(function () {
				var value=$("#cc option:selected").val();
        		
        		$("#chart").attr("src","stat/form_Analysis");	 //将返回的结果显示到ajaxDiv中
            });
        });

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>统计报表<b class="tip"></b>客户构成分析</div>
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

            <tr>
                
                <td width="12%"><div align="center">报表方式</div></td>
                <td width="88%">
                    <select id="cc">
                        <option>按等级</option>
                    
						<option>高级</option>
                    </select>                </td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="查询" />
                    <input class="btn btn-inverse" type="button" value="帮助" /></td>
            </tr>
        </tbody>
    </table>
    <div style="margin:10px 10;text-align: center;">
			<img id="chart" alt="" src="" style="margin: auto;">
	</div>
    
</body>
</html>
