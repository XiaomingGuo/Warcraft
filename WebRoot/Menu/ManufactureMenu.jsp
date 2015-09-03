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
  	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
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
				<table align="center">
					<tr>
						<td>
							<label>八码查询:&nbsp;</label>
							<input type="text" name="search_name" id="search_name" style="width:100px" onblur="findBarcode(this)">
							<input type="text" name="disBarcode" id="disBarcode" style="width:100px" readonly>
						</td>
					</tr>
				</table>
	  			<h2 align="center">
			    	<ul>
			    		<li><a href="MainPage.jsp">首页</a></li>
			    		<li><a href="Customer_PO.jsp">客户PO生成</a></li>
			    		<li><a href="Generate_Order.jsp">生产单生成</a></li>
			    		<li><a href="Query_Order.jsp">生产单查询</a></li>
			    		<li><a href="Approve_Order.jsp">生产单审核</a></li>
			    		<li><a href="Put_In_Pro_Storage.jsp">入待检库</a></li>
			    		<li><a href="Out_Quality_Control.jsp">入库检验</a></li>
			    		<li><a href="Discard_Material.jsp">材料报废</a></li>
			    		<li><a href="Product_Shipment.jsp">成品出货</a></li>
			    		<li><a href="Quit.jsp">退出</a></li>
			    	</ul>
			    </h2>
	    		<hr width=95% noshade="noshade" size="5">
	    	</td>
    	</tr>
    </table>
   	<script type="text/javascript">
		function findBarcode(obj)
		{
			var proName = $("#search_name").val();
			if(proName == null||proName == "")
			{
				alert("产品类型不能为空!");
				$("#search_name").val("");
				return;
			}
			$.post("Ajax/Get_Barcode_By_ProName_Ajax.jsp", {"search_name":proName, "flag":"Pro"}, function(data, textStatus)
			{
				if (textStatus == "success" && data.indexOf("error") < 0)
				{
					var proInfoList = data.split("$");
					$("#disBarcode").val(proInfoList[1]);
				}
				else
				{
					alert(data.split("$")[1]);
				}
			});
		}
	</script>
  </body>
</html>
<%
	}
%>