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
        		
        		$("#chart").attr("src","stat/offer_Analysis?year="+value);	 //将返回的结果显示到ajaxDiv中
            })
        })

    </script>
</head>
<body>
    <div class="alert alert-info">当前位置<b class="tip"></b>统计报表<b class="tip"></b>客户贡献分析</div>
    <table class="table table-striped table-bordered table-condensed">
        
        <tbody>

            <tr>
                <td width="12%"><div align="center">客户名称</div></td>
                <td width="38%" class="detail">
              <input type="text" />                </td>
               
                <td width="12%"><div align="center">年份</div></td>
                <td width="38%">
                    <select id="cc">
                        <option value="all">全部</option>
                        <option value="2014">2014年</option>
                        <option value="2013">2013年</option>
                        <option>2012年</option>
                        <option>2011年</option>
                        <option>2010年</option>
                        
              </select>                </td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <input class="btn btn-inverse" id="find" type="button" value="展示表格" />
                    <input class="btn btn-inverse" type="button" value="帮助" /></td>
            </tr>
        </tbody>
    </table>
    <div style="margin:10px 10;text-align: center;">
			<img id="chart" alt="" src="" style="margin: auto;">
	</div>
    <table class="table table-striped table-bordered table-condensed" id="list">
        <thead>
            <tr class="tr_detail">
                <td width="21%"><div align="center">编号 </div></td>
                <td width="43%"><div align="center">客户名称</div></td>
                <td width="36%"><div align="center">订单金额（元）</div></td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
           
            <tr class="tr_pagenumber">
                <td colspan="100">当前第5页/共55页&nbsp;&nbsp;共650条记录&nbsp;&nbsp;<a class="badge badge-inverse">首页</a>&nbsp;<a class="badge badge-inverse">下一页</a>&nbsp;
                    <a class="badge badge-inverse">1</a>&nbsp;<a class="badge badge-inverse">2</a>&nbsp;<a class="badge badge-inverse">3</a>&nbsp;<a class="badge badge-inverse">4</a>&nbsp;
                    <a class="badge badge-warning">5</a>&nbsp;<a class="badge badge-inverse">...</a>&nbsp;<a class="badge badge-inverse">55</a>&nbsp;<a class="badge badge-inverse">上一页</a>&nbsp;<a class="badge badge-inverse">尾页</a></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
