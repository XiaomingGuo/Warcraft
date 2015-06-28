<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<List<String>> recordList = null;
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String appPOName = request.getParameter("POName");
		if (!appPOName.isEmpty())
		{
			String sql = "select * from customer_po where po_name='" + appPOName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				sql = "INSERT INTO customer_po (po_name, status) VALUES ('" + appPOName + "', '0')";
				hDBHandle.execUpate(sql);
			}
			else
			{
				hDBHandle.CloseDatabase();
				response.sendRedirect("../Customer_PO.jsp");
			}
			sql = "select * from customer_po_record where po_name='" + appPOName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				String[] colNames = {"Bar_Code", "vendor", "QTY", "percent"};
				List<List<String>> recordList = hDBHandle.GetAllDBColumnsByList(colNames);
				if (recordList != null)
				{
					for (int iRow = 0; iRow < recordList.get(0).size(); iRow++)
					{
						String strBarcode = recordList.get(0).get(iRow);
						String strVendor = recordList.get(1).get(iRow);
						int iPOCount = Integer.parseInt(recordList.get(2).get(iRow));
						int ipercent = Integer.parseInt(recordList.get(3).get(iRow));
						int iRepertory = hDBHandle.GetRepertoryByBarCode(Integer.toString(Integer.parseInt(strBarcode)-10000000), "material_storage") + hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
						int manufacture_QTY = iPOCount*(100+ipercent)/100;
						if (iRepertory < manufacture_QTY)
						{
							sql = "INSERT INTO mb_material_po_record (Bar_Code, vendor, po_name, PO_QTY) VALUES ('" + strBarcode + "','" + strVendor + "','" + appPOName + "', '" + Integer.toString(manufacture_QTY-iRepertory) + "')";
							hDBHandle.execUpate(sql);
						}
					}
				}
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
	}
	response.sendRedirect("../Customer_PO.jsp");
%>
