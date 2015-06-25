<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"名称", "模具号", "型号", "数量", "单位", "交货日期", "备注"};
	String[] sqlKeyList = {"product_type", "Bar_Code", "product_name", "QTY", "delivery_date"};
	List<List<String>> recordList = null;
%>
<%
	String message="";
	List<String> vendorList = null;
	String POName = request.getParameter("PO_Name");
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
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			String sql = "select * from customer_po_record group by vendor";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				vendorList = hDBHandle.GetAllStringValue("vendor");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			for (int index = 0; index < vendorList.size(); index++)
			{
				List<List<String>> recordList = null;
				sql = "select * from customer_po_record where vendor='" + vendorList.get(index) + "'";
				if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
				{
					String[] kewordList = {"Bar_Code", "QTY", "OUT_QTY"};
					recordList = hDBHandle.GetAllDBColumnsByList(kewordList);
				}
				else
				{
					hDBHandle.CloseDatabase();
					continue;
				}
				boolean isRemoveVendor = true;
				for (int recordIndex = 0; recordIndex < recordList.get(0).size(); recordIndex++)
				{
					String barcode = recordList.get(0).get(recordIndex);
					int po_qty = Integer.parseInt(recordList.get(1).get(recordIndex)) - Integer.parseInt(recordList.get(2).get(recordIndex));
					int repertory = hDBHandle.GetRepertoryByBarCode(barcode, "material_storage");
					if (repertory < po_qty)
					{
						isRemoveVendor = false;
						break;
					}
				}
				if (isRemoveVendor)
				{
					vendorList.remove(index);
					continue;
				}
			}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产单查询</title>
    
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
    <jsp:include page="MainMenu.jsp"/>
    <table width="61.8%" height="80%" align="center">
    	<tr>
    		<td height="3%"></td>
    		<td height="3%" bgcolor="grey"></td>
    		<td height="3%" align="center"><b><font size="5"><label id="TitleName">生产单号</label></font></b></td>
    	</tr>
		<tr>
			<td valign="top" align="center" width="19%">
				<table align="center" border="1" width="100%">
					<tr><th>生产单号:</th></tr>
				</table>
				<h5>
					<ul>
<%
					if (orderName != null)
					{
						for(int iRow = 0; iRow < orderName.size(); iRow++)
						{
							displayName = orderName.get(iRow);
%>
						<li><%=displayName %></li>
<%
						}
					}
%>
	   				</ul>
	   			</h5>
   			</td>
			<td width="0.5%" height="98%" bgcolor="grey"></td>
   			<td width="80.5%" valign="top">
   				<table width="100%" border="1">
   					<tr><th>生产单内容：</th></tr>
	   			</table>
	   			<table id="OrderBlock" border="1"></table>
   			</td>
		</tr>
   	</table>
  </body>
</html>
<%
		}
	}
%>