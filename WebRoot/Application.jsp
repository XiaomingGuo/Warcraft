<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_type = null, product_info = null;
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
    <title>申领</title>
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
    <jsp:include page="MainPage.jsp"/>
  	<form name="AppContent" action = "SubmitApplication.jsp" method = "post" >
  	<center>
  	<br><br><br>
  		<label>类别:</label>
	  	<select name="product_type" id="product_type">
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
<!-- <br><br>
		<label>类型:</label>
		<select name="product_info" id="product_info">
		  	<option value = "">--请选择--</option>
		</select>
-->
	<br><br>
		<label>名称:</label>
		<select name="product_name" id="product_name">
		  	<option value = "">--请选择--</option>
		</select>
	<br><br>
		<label>数量:</label>
		<select name="QTY" id="QTY">
<%
for(int i = 1; i <= 10; i++)
{
%>
		  	<option value = <%=i%>><%=i%></option>
<%
}
%>		
		</select>
		<br><br>
		<label>库存数量:</label>
		<input name="Total_QTY" id="Total_QTY">
			<script type="text/javascript">
			$(function()
			{
				var $product_type = $('#product_type');
				var $product_info = $('#product_info');
				var $product_name = $('#product_name');
				var $Total_QTY = $('#Total_QTY');
				
				$product_type.change(function()
				{
					$product_info.empty();
					$product_name.empty();
					$product_info.append('<option value="请选择">--请选择--</option>');
					$product_name.append('<option value="请选择">--请选择--</option>');
					$.post("AppAjax.jsp", {"FilterKey1":$("#product_type").find("option:selected").text()}, function(data, textStatus)
					{
						if (textStatus = "success")
						{
							var pro_list = data.split("$");
							for (var i = 0; i < pro_list.length - 1; i++)
							{
								var newOption = $("<option >" + pro_list[i] + "</option>");
								$(newOption).val(pro_list[i]);
								$("#product_name").append(newOption);
							}
						}
					});
				});
				
				$product_name.change(function()
				{
					$.post("Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text()}, function(data, textStatus)
					{
						if (textStatus = "success")
						{
							$Total_QTY.attr("value", data);
						}
					});
				});				
			});
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