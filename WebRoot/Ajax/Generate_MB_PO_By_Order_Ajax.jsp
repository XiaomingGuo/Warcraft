<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String appOrderName = (String)request.getParameter("Order_Name").replace(" ", "");
	String appVendor = (String)request.getParameter("vendor").replace(" ", "");
	String appDelivDate = (String)request.getParameter("Delivery_Date").replace(" ", "");
	if (appOrderName != null && appDelivDate.length() == 8)
	{
		String sql = "select * from mb_material_po where po_name='" + appOrderName + "' and vendor='" + appVendor + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
		{
			List<String> recordList = hDBHandle.GetAllStringValue("id");
			if (recordList != null)
			{
				for (int iRow = 0; iRow < recordList.size(); iRow++)
				{
					if (appDelivDate != null&&!appDelivDate.isEmpty()&&appDelivDate.length() == 8)
					{
						sql = "UPDATE mb_material_po SET date_of_delivery='" + appDelivDate + "' WHERE id='" + recordList.get(iRow) + "'";
						hDBHandle.execUpate(sql);
					}
					else
					{
						rtnRst += "error:交货日期填写有误!$";
						break;
					}
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
			rtnRst += "error:po单不存在!";
		}
	}
	else
	{
		rtnRst += "error:po单需要填写交货日期!";
	}
	out.write(rtnRst);
%>
