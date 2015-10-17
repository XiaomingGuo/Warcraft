<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.page.support.Customer_Po" %>
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
		int temp = mylogon.getUserRight()&1024;
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
			Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
			hCPHandle.GetRecordByStatus(0);
			List<String> po_list = hCPHandle.getDBRecordList("po_name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单生成</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
  	<script language="javascript" src="Page_JS/Generate_OrderJS.js"></script>
  	<script type="text/javascript">
  		function winload()
  		{
			$("#display_page_order").load("Generate_Order_Manual.jsp");
  		}
  	</script>
  </head>
  <body onload="winload()">
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td align="center">
			<form name="Create_Order" action = "Submit/SubmitCreateOrder.jsp" method = "post">
  				<h1>
	  				<label>客户PO单号:</label>
				  	<select name="po_select" id="po_select" style="width:300px">
					  	<option value = "--请选择--">--请选择--</option>
<%
			if (po_list != null)
			{
				for(int i = 0; i < po_list.size(); i++)
				{
%>
					  	<option value = <%= po_list.get(i) %>><%=po_list.get(i)%></option>
<%
				}
			}
%>
				  	</select>
		  		</h1>
	 		   	<div id="display_page_po" align="center">
		 		   	<table id="display_order_po"></table>
		 		   	<table id="confirm_order_po"></table>
				</div>
			</form>
			</td>
		</tr>
		<tr>
			<td>
			   	<div id="display_page_order" align="center"></div>
			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>