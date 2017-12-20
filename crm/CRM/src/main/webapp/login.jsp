<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<LINK href="images/public.css" type=text/css rel=stylesheet>
<LINK href="images/login.css" type=text/css rel=stylesheet>
<STYLE type=text/css>
</STYLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
</HEAD>
<BODY>
	
	<DIV id=div1>
		 <TABLE id=login height="690" cellSpacing=0 cellPadding=0 width=800
align=center>
    <TBODY>
      <TR id=main>
        <TD>
          <form action="login" method="post">
          <TABLE align=center height="50%" cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
              <TR height=30>
                <TD width=272>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD rowSpan=4>&nbsp;</TD>
                <TD>用户名：</TD>
                <TD>
                  <INPUT class=textbox id=txtUserName name="sys_user_t.user_name">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>密　码：</TD>
                <TD>
                  <INPUT class=textbox id=txtUserPassword type=password 
            name="sys_user_t.user_password">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
             
              <TR height=40>
                <TD></TD>
                <TD>
                  <INPUT id=btnLogin type=submit value=" 登 录 " name=btnLogin>
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=110>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
          </form>
        </TD>
      </TR>
      <TR id=root height=104>
        <TD>&nbsp;</TD>
		
      </TR>
    </TBODY>
  </TABLE>

	</DIV>
	<DIV id=div2 style="DISPLAY: none"></DIV>
	</CONTENTTEMPLATE>

</BODY>
</html>
