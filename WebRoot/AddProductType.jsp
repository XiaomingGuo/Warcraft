<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> product_type = null, store_name = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&16;
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
    
    <title>物料录入</title>
    
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
		       	<table align="center" border="1">
			    	<tr>
			   			<th align="center">添加产品类型</th>
			   		</tr>
					<tr>
				  		<td align="right">
					  		<label>库名:</label>
						  	<select name="store_name_addtype" id="store_name_addtype" style="width:193px">
							  	<option value = "--请选择--">--请选择--</option>
<%
								for(int i = 0; i < store_name.size(); i++)
								{
%>
							  	<option value = <%= i + 1 %>><%=store_name.get(i)%></option>
<%
								}
%>
						  	</select>
					  	</td>
				  	</tr>
			   		<tr>
			   			<td align="right">
				    		<label>请输入产品类型:</label>
				    		<input type="text" name="producttype" id="producttype" style='width:140px'>
				    		<input type="button" value="Add" onclick="changeAddType(this)" style='width:50px'>
			    		</td>
			    	</tr>
			   	</table>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		function changeAddType(obj)
		{
			if ($("#store_name_addtype").find("option:selected").text().indexOf("请选择") >= 0 || $('#producttype').val() == "")
			{
				alert("未选择库房名或产品类型为空!");
				return;
			}
			$.post("Ajax/AddProTypeAjax.jsp", {"storeroom":$("#store_name_addtype").find("option:selected").text(), "pro_type":$('#producttype').val()}, function(data, textStatus)
			{
				if (!(textStatus == "success" && data.indexOf("产品类型") < 0))
				{
					alert(data);
				}
				location.reload();
			});
		}
	</script>
  </body>
</html>
<%
		}
	}
%>