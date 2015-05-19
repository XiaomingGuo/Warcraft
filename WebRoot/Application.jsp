<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_type = null, product_info = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null){
		response.sendRedirect("tishi.jsp");
	}
	else{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		
		//product_type Database query
		String sql = "select * from product_type";
		if (hDBHandle.QueryDataBase(sql))
		{
			product_type = hDBHandle.GetAllStringValue("name");
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<script src="JS/GetComboxContent.js"></script>
    <title>申领</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="JS/selectcustomer.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#product_type").change(function(){
				$("#product_type option").each(function(i,o){
					if($(this).prop("selected"))
					{
						$(".sub").hide();
						$(".sub").eq(i).show();
					}
				});
			});
			$("#product_type").change();
		});
	</script>
  <body>
    <jsp:include page="MainPage.jsp"/>
  	<form name="AppContent" action = "SubmitApplication.jsp" method = "post">
  	<center>
  	<br><br><br>
	  	<select id="product_type" onchange="searchProduct_info(document.AppContent.product_type.options[document.AppContent.product_type.selectedIndex].text)">
		  	<option value = 0>--请选择类别--</option>
<%
for(int i = 0; i < product_type.size(); i++)
{
%>
		  	<option value = <%= i + 1 %>><%=product_type.get(i)%></option>
<%
}
%>
	  	</select>
	<br><br>
		<select class="sub">
		  	<option value = 0>--请选择--</option>
		</select>
<%
for(int i = 0; i < product_type.size(); i++)
{
%>
<script type="text/javascript">
		alert("");
</script>
<%
		product_info = hDBHandle.GetProductInfo("name");
%>
		<select class="sub">
		  	<option value = 0>--请选择--</option>
		</select>
<%
}
%>
		<div id="pro_info"></div>
		<input name="commit" type=submit value="提交">
  	</center>
  	</form>
  </body>
</html>
<%
	}
%>