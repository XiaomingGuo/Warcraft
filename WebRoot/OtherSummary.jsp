<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&32;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
			hSNHandle.GetAllRecord();
			List<String> store_name = hSNHandle.getDBRecordList("name");
			
			store_name.remove("成品库");
			store_name.remove("原材料库");
			store_name.remove("半成品库");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>其他库存</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
  	<script language="javascript" src="Page_JS/OtherSummaryJS.js"></script>
  <body>
    <jsp:include page="Menu/QueryMenu.jsp"/>
    <table align="center" border="1">
		<caption>其他库存筛选</caption>
	  	<tr>
	  		<td align="right">
		  		<label>库名:</label>
			  	<select name="store_name" id="store_name" style="width:180px">
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
			  	<select name="product_type" id="product_type" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
			  	</select>
		  	</td>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
			<td align="right">
	   			<label id=lable_barcode>Bar Code:</label>
				<input type="text" name="bar_code" id="bar_code" style='width:180px' onblur="InputBarcode()">
			</td>
	  	</tr>
   	</table>
   	<br>
   	<table id="display_storage" border='1' align="center"></table>
   	<br><br><br>
  </body>
</html>
<%
		}
	}
%>