<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<style>
	body{background:#ECF5FF}
	</style>
  </head>
  
  <body>
<!-- -->
<br>
	<table width="80%" align="center">
		<tr>
			<td width="80%">
				<table width="100%">
					<tr>
						<td width="60%" align="left"><img src="IMAGE/Logo.png" align="middle"><font size="5"><b>常州市茂邦机械有限公司内部网络</b></font></td>
						<td width="40%" align="right">您好！<jsp:getProperty property="username" name="mylogon"/>！欢迎登录！
<%
						int temp = mylogon.getUserRight()&2;
						if(temp == 0)
					  	{
%>
						
						<a href = 'ChangePassword.jsp'>修改密码</a>
<%
					  	}
					  	else
					  	{
%>
						<a href = 'ChangePassword.jsp'>修改密码</a>&nbsp;&nbsp;
						<a href = 'UserManagement.jsp?BeginPage=1'>用户管理</a>
				
<%
				  		}
%>
						</td>
					</tr>
				</table>
				<br>
	  			<h2 align="center">
			    	<ul>
			    		<li><a href="MainPage.jsp">首页</a></li>
			    		<li><a href="AddStoreroom.jsp">添加库房</a></li>
			    		<li><a href="AddProductType.jsp">添加类型</a></li>
			    		<li><a href="AddMaterial.jsp">添加物料</a></li>
			    		<li><a href="AddSupplier.jsp">添加供应商</a></li>
			    		<li><a href="QueryMaterial.jsp">物料录入状态</a></li>
			    		<li><a href="Quit.jsp">退出</a></li>
			    	</ul>
			    </h2>
	    		<hr width=95% noshade="noshade" size="5">
	    	</td>
    	</tr>
    </table>
  </body>
</html>
<%
	}
%>