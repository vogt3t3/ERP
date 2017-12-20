<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>CRM客户信息管理系统</title>
    <link rel="stylesheet" type="text/css" href="Styles/admin-all.css" />
    <link rel="stylesheet" type="text/css" href="Styles/base.css" />
    <link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
    <script type="text/javascript" src="Scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="Scripts/jquery-ui-1.8.22.custom.min.js"></script>
    <script type="text/javascript" src="Scripts/index.js"></script>
</head>
<body style="background: url(img/back.jpg) no-repeat;">
    <div class="warp">
        i<!--头部开始-->
        <div class="top_c">
            <div class="top-menu">
                <ul class="top-menu-nav">
                    <li><a href="#">首页</a></li>
                    <li><a href="#">营销管理<i class="tip-up"></i></a>
                        <ul class="kidc">
                            <li><a target="Conframe" href="chance/list_Chance">销售机会管理</a></li>
                            <li><a target="Conframe" href="chance/list_Plan">客户开发计划</a></li>
                          
                        </ul>
                    </li>
                    <li><a href="#">客户管理<i class="tip-up"></i></a>
                        <ul class="kidc">
                            <li><b class="tip"></b><a target="Conframe" href="client/list_Client">客户信息管理</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="client/list_Lst">客户流失管理</a></li>
                        </ul>
                    </li>
                    <li><a href="#">服务管理<i class="tip-up"></i></a>
                        <ul class="kidc">
                            <li><b class="tip"></b><a target="Conframe" href="service/open_CreateService">服务创建</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_AssignService">服务分配</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_ResolveService">服务处理</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_BackeService">服务反馈</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_StoreService">服务归档</a></li>
                        </ul>
                    </li>
                    <li><a href="#">统计报表<i class="tip-up"></i></a>
                        <ul class="kidc">
                       	<li><b class="tip"></b><a target="Conframe" href="stat/offer_AnalysisList">客户贡献分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/form_AnalysisList">客户构成分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/service_AnalysisList">客户服务分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/lst_Analysis">客户流失分析</a></li>
                        
                    </ul>
                    </li>
                    <li><a href="#">基础数据<i class="tip-up"></i></a>
                        <ul class="kidc">
                        <li><b class="tip"></b><a target="Conframe" href="dict/open_CreateDict">数据字典管理</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="Template/basicdata/productinformation.html">查询产品信息</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="Template/basicdata/querystorage.html">查询库存</a></li>
                       
                    </ul>
                    </li>
                    <li><a href="#">订单管理<i class="tip-up"></i></a>
                        <ul class="kidc">
                        <li><b class="tip"></b><a target="Conframe" href="indent/open_CreateIndent">订单信息管理</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="top-nav">欢迎您，${session.user.user_name}，身份：${session.role}  &nbsp;&nbsp;<a href="#">修改密码</a> | <a href="loginout">退出</a></div>
        </div>
        <!--头部结束-->
        <!--左边菜单开始-->
        <div class="left_c left">
            <h1>客户关系管理系统</h1>
               <div class="acc">
                <div>
                    <a class="one">营销管理</a>
               
                    <ul class="kid">
                            <li><b class="tip"></b><a target="Conframe" href="chance/list_Chance">销售机会管理</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="chance/list_Plan">客户开发计划</a></li>
                    
                    </ul>
                </div>
                <div>
                    <a class="one">客户管理</a>
                    <ul class="kid">
                            <li><b class="tip"></b><a target="Conframe" href="client/list_Client">客户信息管理</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="client/list_Lst">客户流失管理</a></li>
                    </ul>
                </div>
                <div>
                    <a class="one">服务管理</a>
                    <ul class="kid">
                        <li><b class="tip"></b><a target="Conframe" href="service/open_CreateService">服务创建</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_AssignService">服务分配</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_ResolveService">服务处理</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_BackeService">服务反馈</a></li>
                            <li><b class="tip"></b><a target="Conframe" href="service/open_StoreService">服务归档</a></li
                    ></ul>
                </div>
                <div>
                    <a class="one">统计报表</a>
                    <ul class="kid">
                        <li><b class="tip"></b><a target="Conframe" href="stat/offer_AnalysisList">客户贡献分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/form_AnalysisList">客户构成分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/service_AnalysisList">客户服务分析</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="stat/lst_Analysis">客户流失分析</a></li>
                    </ul>
                </div>
                <div>
                    <a class="one">基础数据</a>
                    <ul class="kid">
                        <li><b class="tip"></b><a target="Conframe" href="dict/open_CreateDict">数据字典管理</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="dict/open_Product">查询产品信息</a></li>
                        <li><b class="tip"></b><a target="Conframe" href="dict/open_Stock">查询库存</a></li>
                    </ul>
                </div>
                <div>
                    <a class="one">订单管理</a>
                    <ul class="kid">
                        <li><b class="tip"></b><a target="Conframe" href="indent/open_CreateIndent">订单信息管理</a></li>
 
                   </ul>
                </div>
                <div>
                    <a class="one">权限管理</a>
                    <ul class="kid">
 						<li><b class="tip"></b><a target="Conframe" href="right/open_Right">系统权限</a></li>
 						<li><b class="tip"></b><a target="Conframe" href="right/open_Role">角色管理</a></li>
 						<li><b class="tip"></b><a target="Conframe" href="right/open_User">用户角色</a></li>
                   </ul>
                </div>
            </div>

        </div>
        <!--左边菜单结束-->
        <!--右边框架开始-->
        <div class="right_c">
            <div class="nav-tip" onClick="javascript:void(0)">&nbsp;</div>

        </div>
        <div class="Conframe">
            <iframe name="Conframe" id="Conframe"></iframe>
        </div>
        <!--右边框架结束-->

        <!--底部开始-->
        <div class="bottom_c">Copyright &copy;2014 肇庆学院 版权所有</div>
        <!--底部结束-->
    </div>
</body>
</html>
