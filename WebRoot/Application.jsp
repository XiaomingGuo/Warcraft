<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_type = null, product_info = null, store_name=null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		//storeroom name Database query
		String sql = "select * from storeroom_name";
		if (hDBHandle.QueryDataBase(sql))
		{
			store_name = hDBHandle.GetAllStringValue("name");
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
		store_name.remove("成品库");
		store_name.remove("原材料库");
		store_name.remove("半成品库");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>申请</title>
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
  	<script language="javascript" src="Page_JS/ApplicationJS.js"></script>
  <body>
    <jsp:include page="Menu/MainMenu.jsp"/>
  	<br><br><br>
   	<form name="AppContent" action = "Submit/SubmitApplication.jsp" method = "post">
  	<table align="center" border="1">
	  	<tr>
	  		<th align="center">申领物品</th>
	  	</tr>
		<tr>
	  		<td align="right">
		  		<label>库名:</label>
			  	<select name="store_name" id="store_name" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
<%
					for(int i = 0; i < store_name.size(); i++)
					{
%>
				  	<option value = <%= store_name.get(i) %>><%=store_name.get(i)%></option>
<%
					}
%>
			  	</select>
		  	</td>
	  	</tr>
	  	<tr>
	  		<td align="right">
		  		<label>类别:</label>
			  	<select name="product_type" id="product_type" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
			  	</select>
		  	</td>
	  	</tr>
		<tr>
			<td align="right">
				<label>名称:</label>
				<select name="product_name" id="product_name" style="width:180px">
				  	<option value = "--请选择--">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>Bar Code:</label>
				<input name="bar_code" id="bar_code" onblur="InputBarcode()" style="width:180px">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>使用者:</label>
				<input name="user_name" id="user_name" style="width:180px">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>数量:</label>
				<input name="QTY" id="QTY" style="width:180px">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label>库存数量:</label>
				<input name="Total_QTY" id="Total_QTY" style="width:180px" readonly>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input name="commit" type=submit value="提交" style="width:100">
			</td>
		</tr>
	</table>
  	</form>
  </body>
</html>
<%
	}
%>