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
			OrderName = hDBHandle.GenOrderName("MB_" + createDate + "_" + appPOName);

			String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
			recordList = hDBHandle.GetCustomerPORecord(appPOName, sqlKeyList);
			if (recordList != null)
			{
				int iUpdatePOStatusFlag = 0;
				for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
				{
					String iOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY");
					if (iOrderQTY == null)
					{
						continue;
					}
					else if(Integer.parseInt(iOrderQTY) > 0)
					{
						String strBarcode = recordList.get(0).get(iRow);
						String strDeliDate = recordList.get(2).get(iRow);
						sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + strBarcode + "','" + strDeliDate + "','" + iOrderQTY + "','" + appPOName + "','" + OrderName + "')";
						hDBHandle.execUpate(sql);
						sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
						hDBHandle.execUpate(sql);
						int iAllOrderQTY = Integer.parseInt(recordList.get(1).get(iRow)) * (100 + Integer.parseInt(recordList.get(3).get(iRow)))/100;
						iUpdatePOStatusFlag += iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName);
					}
					else
					{
						session.setAttribute("error", "物料不足或生产单已生成!");
						response.sendRedirect("../tishi.jsp");
						return;
					}
				}
				if (iUpdatePOStatusFlag == 0)
				{
					sql = "UPDATE customer_po SET status = 1 WHERE po_name='" + appPOName + "'";
					hDBHandle.execUpate(sql);
				}
			}
			else
			{
				;
			}
		}
		response.sendRedirect("../Query_Order.jsp");
	}
%>
