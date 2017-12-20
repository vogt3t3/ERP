<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- public end -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
</head>
<body>
<div class="main">
<H2>
你没有该权限，<a href="<%=request.getContextPath() %>/login.jsp"  >请联系管理员</a>
<p>Sorry ! Not Found 404 ! </p>
</H2>
<div class="logo">
<DIV class=img404>　
</DIV>

</div>

<div class="clear"></div>

<div class="Menu">
<ul>

</ul>
</div>

<div class="clear"></div>

</p>

</div>

</div>

</body>

</html>