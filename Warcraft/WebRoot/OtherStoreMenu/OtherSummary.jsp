<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("../tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&32;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			List<String> store_name = hPageHandle.GetStoreName("TOOLS");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>五金库库存</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
	<script language="javascript" src="JS/jquery-1.12.4.min.js"></script>
	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
	<script language="javascript" src="Page_JS/OtherStoreMenuJS/OtherSummaryJS.js"></script>
  <body>
    <jsp:include page="../Menu/MainMenu.jsp"/>
    <br>
    <form action="ReportPage/SaveOtherSummary.jsp" method="post">
    <table align="center" border="1">
		<caption>其他库存筛选</caption>
		<tr>
			<td align="right">
				<label>库名:</label>
				<select name="store_name" id="store_name" style="width:120px">
					<option value = "--请选择--">--请选择--</option>
<%
					for(int i = 0; i < store_name.size(); i++)
					{
%>
					<option value = <%=store_name.get(i) %>><%=store_name.get(i)%></option>
<%
					}
%>
				</select>
			</td>
			<td align="right">
				<label>类别:</label>
				<select name="product_type" id="product_type" style="width:120px">
					<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:150px">
					<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
			<td align="right">
				<label id=label_barcode>Bar Code:</label>
				<input type="text" name="bar_code" id="bar_code" style='width:100px' onblur="InputBarcode()">
			</td>
			<td align="center">
				<input align="middle" type="button" value="查询" onclick="DisplayStorageList()">
			</td>
		</tr>
	</table>
	<br>
	<table id="display_storage" border='1' align="center"></table>
	<br>
	<table align="center"><tr><td><input align="middle" type="submit" value="下载" style='width:80px'/></td></tr></table>
	</form>
	<br><br>
  </body>
</html>
<%
		}
	}
%>