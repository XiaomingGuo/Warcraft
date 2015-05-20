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
	<style type="text/css">
		.sub{display:none;}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>

  <body>
    <jsp:include page="MainPage.jsp"/>
  	<form name="AppContent" action = "SubmitApplication.jsp" method = "post">
  	<center>
  	<br><br><br>
	  	<select name="product_type" id="product_type">
	  	<!-- <select id="product_type" onchange="searchProduct_info(document.AppContent.product_type.options[document.AppContent.product_type.selectedIndex].text)"> -->
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
		<select name="product_info" id="product_info">
		  	<option value = "">--请选择--</option>
		</select>
		<script type="text/javascript">
			$(function(){
				var $product_type = $('#product_type');
				var $product_info = $('#product_info');
				
				$product_type.change(function(){
					$product_info.empty();
					var $pro_info_content = <%=hDBHandle.GetProductInfo(product_type)%>;
					alert($("#product_type").find("option:selected").text());
					$product_info.append('<option value="22">--请选择--</option>');
				});
			});
			//alert($("#product_type").find("option:selected").text());
		</script>
	<br><br>
		<input name="commit" type=submit value="提交">
  	</center>
  	</form>
  </body>
</html>
<%
	}
%>