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
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		String appPOName = request.getParameter("po_select");
		String OrderName = null, sql = null;
		
		if (!appPOName.isEmpty())
		{
			int iCount = 1;
			do
			{
				OrderName = "MB_" + createDate + "_" + appPOName + "_" + Integer.toString(iCount);
				sql = "select * from product_order where Order_Name='" + OrderName + "'";
				if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					break;
				}
				else
				{
					hDBHandle.CloseDatabase();
					iCount += 1;
					continue;
				}
			}while(true);

			sql = "select * from customer_po_record where po_name='" + appPOName + "' order by id asc";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				String[] sqlKeyList = {"Bar_Code", "delivery_date"};
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
				for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
				{
					String iOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY");
					if (Integer.parseInt(iOrderQTY) > 0)
					{
						String strBarcode = recordList.get(0).get(iRow);
						String strDeliDate = recordList.get(1).get(iRow);
						sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + strBarcode + "','" + strDeliDate + "','" + iOrderQTY + "','" + appPOName + "','" + OrderName + "')";
						hDBHandle.execUpate(sql);
						sql = "select * from product_order where Order_Name='" + OrderName + "'";
						if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
						{
							hDBHandle.CloseDatabase();
							sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
							hDBHandle.execUpate(sql);
						}
						else
						{
							hDBHandle.CloseDatabase();
						}
					}
				}
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		response.sendRedirect("../Query_Order.jsp");
	}
%>
