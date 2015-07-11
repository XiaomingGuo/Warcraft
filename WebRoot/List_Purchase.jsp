<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
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
			String sql = "select * from customer_po_record where po_name='" + POName + "' group by vendor";
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
					String[] kewordList = {"Bar_Code", "QTY", "percent"};
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
					int po_qty = Integer.parseInt(recordList.get(1).get(recordIndex))*(100 + Integer.parseInt(recordList.get(2).get(recordIndex)))/100;
					int repertory = hDBHandle.GetRepertoryByBarCode(barcode, "material_storage") + hDBHandle.GetRepertoryByBarCode(Integer.toString(Integer.parseInt(barcode) - 10000000), "material_storage");
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
			Calendar mData = Calendar.getInstance();
			String DeliveryDate = String.format("%04d", mData.get(Calendar.YEAR));
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
    <jsp:include page="Menu/ManufactureMenu.jsp"/>
    <br><br><br>
    <table align="center">
<%
			for (int iRow = 0; iRow < vendorList.size(); iRow++)
			{
%>
			<tr>
				<td align="center" width="25%"><h1><a onclick="func(this)" name="<%=POName %>$<%=vendorList.get(iRow) %>$<%=iRow %>" href="javascript:void(0)"><%=vendorList.get(iRow) %></a></h1></td>
				<td align="right"><b>交货日期: </b></td>
				<td><input type="text" id="date_of_delivery_<%=iRow %>" name="date_of_delivery_<%=iRow %>" value="<%=DeliveryDate %>"></td>
			</tr>
<%
			}
%>
   	</table>
  </body>
   	<script type="text/javascript">
		function func(obj)
		{
			var paramList = obj.name.split("$");
			var vPOName = paramList[0];
			var vendorName = paramList[1];
			var deliveryDate = $("#date_of_delivery_"+paramList[2]).val();
			if(vPOName==""||vPOName == null||vendorName == ""||vendorName == null)
			{
				alert("我说大姐,你这PO号和供应商名称我没见过啊?");
				return;
			}
			$.post("Ajax/Generate_MB_PO_Ajax.jsp", {"PO_Name":vPOName, "vendor":vendorName, "Delivery_Date":deliveryDate}, function(data, textStatus)
			{
				if (!(textStatus == "success")||data.indexOf("error") >= 0)
				{
					alert(data.split('$')[1]);
					return;
				}
				location.href = "Generate_PO.jsp?PO_Name=" + vPOName + "&vendor=" + vendorName + "&Delivery_Date=" + deliveryDate;
			});
		}
	</script>
</html>
<%
		}
	}
%>