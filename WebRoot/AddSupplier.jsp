<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> store_name = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&2048;
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
			//product_type Database query
			String sql = "select * from storeroom_name";
			if (hDBHandle.QueryDataBase(sql))
			{
				store_name = hDBHandle.GetAllStringValue("name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			for (int index = 0; index < store_name.size(); index++)
			{
				if (store_name.get(index).indexOf("成品库") == 0)
				{
					store_name.remove(index);
				}
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加供应商</title>
    
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
  <body>
    <jsp:include page="Menu/DataEnterMenu.jsp"/>
    <br><br>
    <table width="55%" align="center">
    	<tr>
    		<td>
		  		<form name="AddSupplier" action = "Submit/SubmitAddSupplier.jsp" method = "post">
		    	<table align="center" border="1">
			    	<tr>
			   			<th>添加供应商信息</th>
			   		</tr>
					<tr>
				  		<td align="right">
					  		<label>库名:</label>
						  	<select name="store_name_addproduct" id="store_name_addproduct" style="width:180px">
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
				  	</tr>
					<tr>
				  		<td align="right">
					  		<label>供应商名:</label>
							<input type="text" name="suppliername" id="suppliername" style='width:180px'>
					  	</td>
				  	</tr>
				  	<tr>
				  		<td align="right">
					  		<label>传真:</label>
							<input type="text" name="faxinfo" id="faxinfo" style='width:180px'>
					  	</td>
				  	</tr>
					<tr>
						<td align="right">
							<label>电话:</label>
							<input type="text" name="telinfo" id="telinfo" style='width:180px'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label>邮箱:</label>
							<input type="text" name="mailaddress" id="mailaddress" style='width:180px'>
						</td>
					</tr>
			    	<tr>
			   			<td align="right">
				   			<label>厂家地址:</label>
							<input type="text" name="address" id="address" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="right">
				   			<label>备注:</label>
							<input type="text" name="description" id="description" style='width:180px'>
						</td>
					</tr>
					<tr>
			   			<td align="center">
							<input type=submit value="提交" style='width:100px'>
						</td>
					</tr>
		    	</table>
				</form>
			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>