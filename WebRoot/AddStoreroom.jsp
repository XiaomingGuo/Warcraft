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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加库房</title>
    
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
    <jsp:include page="Menu/InputInfoOfTableMenu.jsp"/>
    <br><br>
    <table width="55%" align="center">
    	<tr>
	    	<td>
				<table align="center" border="1">
			    	<tr>
			   			<th align="center">添加库房</th>
			   		</tr>
			   		<tr>
			   			<td align="right">
				    		<label>请输入库房名:</label>
				    		<input type="text" name="storename" id="storename" style='width:140px'>
				    		<input type="button" value="Add" onclick="changeAddStore(this)" style='width:50px'>
			    		</td>
			    	</tr>
		    	</table>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		function changeAddStore(obj)
		{
			if ($('#storename').val() == "")
			{
				alert("库房名不能为空!");
				return;
			}
			$.post("Ajax/AddStoreNameAjax.jsp", {"storeroom":$('#storename').val()}, function(data, textStatus)
			{
				if (!(textStatus == "success" && data.indexOf("库名") < 0))
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