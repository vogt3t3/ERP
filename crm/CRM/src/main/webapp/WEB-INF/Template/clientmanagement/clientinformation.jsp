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
            	$('#clientinfo').submit();
            });
            
            
            $('#levelSelect').change(function(){
            	
            	var value=$('#levelSelect option:selected').text();
            	$('#levelLabel').val(value);
            })
        });
    </script>
  </head>
 <body>
    <div class="alert alert-info">当前位置<b class="tip"></b>客户信息管理<b class="tip"></b>客户信息</div>
	 <s:form id="clientinfo" action="client/add_Client" method="post">
    <table class="table table-striped table-bordered table-condensed list">
        <thead>
            <tr>
                <td colspan="4">客户信息</td>
            </tr>
        </thead>
       
        <tbody>
             <tr>
                <td colspan="4">
                	<s:if test="#request.cst_customer_t!=null">
                    <input class="btn btn-inverse" id="lxr" type="button" value="联系人" onclick="window.location.href='client/get_LinkMan?id=${cst_customer_t.cst_id}' "/>
                    <input class="btn btn-inverse" id="jwjl" type="button" value="交往记录" onclick="window.location.href='client/get_Activity?id=${cst_customer_t.cst_id}' "/> 
                    <input class="btn btn-inverse" id="lsdd" type="button" value="历史订单"  onclick="window.location.href='client/get_Indent?id=${cst_customer_t.cst_id}'"/>
                    </s:if>
                    <input class="btn btn-inverse" type="button" value="保存" id="save"/>
                    <input class="btn btn-inverse" type="button" value="返回" onclick="javascript:history.back()"/></td>
            </tr>
            <tr>
                <td width="15%">客户编号</td>
                <td width="500">
                    <input name="cst_customer_t.cst_id" value="${cst_customer_t.cst_id}" type="text" readonly="readonly"/>                </td>
                <td width="500">名称<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_customer_t.cst_name" value="${cst_customer_t.cst_name}" type="text" /></td>
            </tr>
            <tr>
                <td>地区<font color="FF0000">*</font></td>
                <td><select name="cst_customer_t.cst_region">
                  <option value="北京" ${cst_customer_t.cst_region=="北京"?'selected':'' }>北京</option>
                  <option value="上海" ${cst_customer_t.cst_region=="上海"?'selected':'' }>上海</option>
                  <option value="广州" ${cst_customer_t.cst_region=="广州"?'selected':'' }>广州</option>
                  <option value="深圳" ${cst_customer_t.cst_region=="深圳"?'selected':'' }>深圳</option>
                </select></td>
                <td>客户经理<font color="FF0000">*</font></td>
                <td><select name="cst_customer_t.cust_manager_name">
                  <c:forEach items="${userList }" var="user">
                  <option value="${user.user_name }" ${cst_customer_t.cust_manager_name==user.user_name?'selected':'' }>${user.user_name }</option>
                  </c:forEach>
                </select></td>
            </tr>
			<tr>
                <td>客户等级<font color="FF0000">*</font></td>
                <td colspan="3"><select id="levelSelect"  name="cst_customer_t.cst_level">
                <c:forEach items="${bas}" var="bas">
                  <option value="${bas.dict_value }" ${cst_customer_t.cst_level==bas.dict_value?'selected':'' }>${bas.dict_item }</option>
                  
          		</c:forEach>
          		
                </select>
                <input type="hidden" id="levelLabel" name="cst_customer_t.cst_level_label"/>
                </td>
            </tr>
			
            <tr>
                <td>客户满意度<font color="FF0000">*</font></td>
                <td><select name="cst_customer_t.cst_satisfy">
                  <option value="0" ${cst_customer_t.cst_satisfy==0?'selected':'' }>☆</option>
                  <option value="1" ${cst_customer_t.cst_satisfy==1?'selected':'' }>☆☆</option>
                  <option value="2" ${cst_customer_t.cst_satisfy==2?'selected':'' }>☆☆☆</option>
                  <option value="3" ${cst_customer_t.cst_satisfy==3?'selected':'' }>☆☆☆☆</option>
                  <option value="4" ${cst_customer_t.cst_satisfy==4?'selected':'' }>☆☆☆☆☆</option>
                </select></td>
                <td>客户信用度<font color="FF0000">*</font></td>
                <td><select name="cst_customer_t.cst_credit">
                  <option value="0" ${cst_customer_t.cst_credit==0?'selected':'' }>☆</option>
                  <option value="1" ${cst_customer_t.cst_credit==1?'selected':'' }>☆☆</option>
                  <option value="2" ${cst_customer_t.cst_credit==2?'selected':'' }>☆☆☆</option>
                  <option value="3" ${cst_customer_t.cst_credit==3?'selected':'' }>☆☆☆☆</option>
                  <option value="4" ${cst_customer_t.cst_credit==4?'selected':'' }>☆☆☆☆☆</option>
                </select></td>
            </tr>
            <tr>
                <td width="15%">地址<font color="FF0000">*</font></td>
                <td>
                     <input name="cst_customer_t.cst_addr" value="${cst_customer_t.cst_addr }" type="text" /></td>               
                <td width="500">邮政编码<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_customer_t.cst_zip" value="${cst_customer_t.cst_zip }" type="text" /></td>
            </tr>
			<tr>
                <td width="15%">电话<font color="FF0000">*</font></td>
                <td>
                    <input name="cst_customer_t.cst_tel" value="${cst_customer_t.cst_tel }"  type="text" />                </td>
                <td width="500">传真<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_customer_t.cst_fax" value="${cst_customer_t.cst_fax }" type="text" />                </td>
            </tr>
			<tr>
                <td width="15%">网址<font color="FF0000">*</font></td>
                <td>
                    <input name="cst_customer_t.cst_website" value="${cst_customer_t.cst_website }" type="text" />                </td>
                <td width="500">&nbsp;</td>
                <td width="500">
                    <input name="" value="" disabled="disabled" type="text"/>                </td>
                    
            </tr>
			<tr>
                <td width="15%">营业执照注册号</td>
                <td>
                    <input name="cst_customer_t.cst_licence_no" value="${cst_customer_t.cst_licence_no }" type="text" />                </td>
              <td width="500">法人<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_customer_t.cst_chieftain" value="${cst_customer_t.cst_chieftain }" type="text" />                </td>
            </tr>
            
            <tr>
                <td width="15%">注册资金（万元）</td>
                <td>
                    <input name="cst_customer_t.cst_bankroll" value="${cst_customer_t.cst_bankroll }" type="text" />                </td>
              <td width="500">年营业额</td>
                <td width="500">
                    <input name="cst_customer_t.cst_turnover" value="${cst_customer_t.cst_turnover }"  type="text"/>                </td>
            </tr>
            <tr>
                <td width="15%">开户银行<font color="FF0000">*</font></td>
                <td>
                    <input name="cst_customer_t.cst_bank" value="${cst_customer_t.cst_bank }" type="text" />                </td>
              <td width="500">银行账号<font color="FF0000">*</font></td>
                <td width="500">
                    <input name="cst_customer_t.cst_bank_account" value="${cst_customer_t.cst_bank_account }"  type="text"/>                </td>
            </tr><tr>
                <td width="15%">地税登记号</td>
                <td>
                    <input name="cst_customer_t.cst_local_tax_no" value="${cst_customer_t.cst_local_tax_no}" type="text" />                </td>
              <td width="500">国税等级号</td>
                <td width="500">
                    <input name="cst_customer_t.cst_national_tax_no" value="${cst_customer_t.cst_national_tax_no}"  type="text"/>                </td>
            </tr>
           
        </tbody>
       
        <tfoot>
           
        </tfoot>
    </table>
     </s:form>
</body>
</html>
