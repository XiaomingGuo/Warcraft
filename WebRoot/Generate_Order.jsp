<%@page import="org.apache.struts2.components.Else"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
 	List<String> po_list = null, product_type = null;
%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&512;
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
			String sql = "select * from customer_po where status = 0";
			if (hDBHandle.QueryDataBase(sql))
			{
				po_list = hDBHandle.GetAllStringValue("po_name");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
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

  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  <body>
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br>
    <table align="center">
    	<tr>
    		<td>
		  		<table align="center">
		  			<tr>
		  				<td>
			  				<h1>
						  		<label>客户PO单号:</label>
							  	<select name="po_select" id="po_select" style="width:300px">
								  	<option value = "--请选择--">--请选择--</option>
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
				  		</td>
			  		</tr>
		  		</table>
	 		   	<div id="display_page" align="center"></div>
			</td>
		</tr>
   	</table>
  	<script type="text/javascript">
		$(function()
		{
			var $po_select = $('#po_select');
			$po_select.change(function()
			{
				var $display_page = $("#display_page");
				var po_name = $po_select.find("option:selected").text();
				alert(po_name);
				if (po_name.indexOf("请选择") >= 0)
				{
					$display_page.load("Generate_Order_Manual.jsp");
				}
				else
				{
					$display_page.load("Generate_Order_By_PO.jsp");
				}
			});
		});
	</script>
  </body>
</html>
<%
		}
	}
%>