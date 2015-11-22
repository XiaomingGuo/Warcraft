<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SubmitCreateOrder" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		List<List<String>> recordList = null;
		request.setCharacterEncoding("UTF-8");
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		String appPOName = request.getParameter("po_select").replace(" ", "");
		String OrderName = null, sql = null;
		SubmitCreateOrder hPageSupport = new SubmitCreateOrder();
		
		if (!appPOName.isEmpty())
		{
			recordList = hPageSupport.getCustomerPORecord(appPOName);
		}
		if (recordList != null)
		{
			int iUpdatePOStatusFlag = 0;
			OrderName = hPageSupport.GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				String strOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY").replace(" ", "");
				int iAllOrderQTY = hPageSupport.CalcOrderQty(recordList.get(1).get(iRow), recordList.get(3).get(iRow));
				String strBarcode = recordList.get(0).get(iRow);
				String strDeliDate = recordList.get(2).get(iRow);
				if(strOrderQTY != null&&Integer.parseInt(strOrderQTY) > 0)
				{
					hPageSupport.InsertProductOrderRecord(strBarcode, strDeliDate, Integer.parseInt(strOrderQTY), appPOName, OrderName);
				}
				if(iAllOrderQTY - hPageSupport.GetInProcessQty(strBarcode, appPOName) > 0)
				{
					iUpdatePOStatusFlag += iAllOrderQTY-hPageSupport.GetInProcessQty(strBarcode, appPOName)-hPageSupport.GetRepertoryByBarCode(strBarcode);
				}
			}
			hPageSupport.InsertProductOrder(OrderName);
			if (iUpdatePOStatusFlag == 0)
			{
				hPageSupport.UpdateCustomerPoStatus("1", appPOName);
				//sql = "UPDATE customer_po SET status = 1 WHERE po_name='" + appPOName + "'";
			}
			else
			{
				iUpdatePOStatusFlag = 0;
				OrderName = hPageSupport.GenOrderName("MB_" + createDate + "_" + appPOName);
				hPageSupport.InsertProductOrder(OrderName);
				for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
				{
					String strOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY").replace(" ", "");
					int iAllOrderQTY = hPageSupport.CalcOrderQty(recordList.get(1).get(iRow), recordList.get(3).get(iRow));
					String strBarcode = recordList.get(0).get(iRow);
					String strDeliDate = recordList.get(2).get(iRow);
					
					int tempOrderQty = iAllOrderQTY-hPageSupport.GetInProcessQty(strBarcode, appPOName)-hPageSupport.GetRepertoryByBarCode(strBarcode);
					if (tempOrderQty > 0)
					{
						if(strOrderQTY != null&&Integer.parseInt(strOrderQTY) > 0)
						{
							hPageSupport.InsertProductOrderRecord(strBarcode, strDeliDate, tempOrderQty, appPOName, OrderName);
							iUpdatePOStatusFlag += iAllOrderQTY-hPageSupport.GetInProcessQty(strBarcode, appPOName)-hPageSupport.GetRepertoryByBarCode(strBarcode);
						}
						else if(iAllOrderQTY - hPageSupport.GetInProcessQty(strBarcode, appPOName) > 0)
						{
							hPageSupport.InsertProductOrderRecord(strBarcode, strDeliDate, tempOrderQty, appPOName, OrderName);
							iUpdatePOStatusFlag += iAllOrderQTY-hPageSupport.GetInProcessQty(strBarcode, appPOName)-hPageSupport.GetRepertoryByBarCode(strBarcode);
						}
					}
				}
				if (iUpdatePOStatusFlag == 0)
				{
					hPageSupport.UpdateCustomerPoStatus("1", appPOName);
					//sql = "UPDATE customer_po SET status = 1 WHERE po_name='" + appPOName + "'";
				}
			}
		}
		
		
		/*
		//End of jsp page
		if (!appPOName.isEmpty())
		{
			String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
			recordList = hDBHandle.GetCustomerPORecord(appPOName, sqlKeyList);
			if (recordList != null)
			{
				int iUpdatePOStatusFlag = 0;
				OrderName = hDBHandle.GenOrderName("MB_" + createDate + "_" + appPOName);
				sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
				hDBHandle.execUpate(sql);
				for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
				{
					String strOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY").replace(" ", "");
					int iAllOrderQTY = Integer.parseInt(recordList.get(1).get(iRow)) * (100 + Integer.parseInt(recordList.get(3).get(iRow)))/100;
					String strBarcode = recordList.get(0).get(iRow);
					String strDeliDate = recordList.get(2).get(iRow);
					if(strOrderQTY != null&&Integer.parseInt(strOrderQTY) > 0)
					{
						sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + hDBHandle.GetUsedBarcode(strBarcode, "product_order_record") + "','" + strDeliDate + "','" + strOrderQTY + "','" + appPOName + "','" + OrderName + "')";
						hDBHandle.execUpate(sql);
						iUpdatePOStatusFlag += iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
					}
					else if(iAllOrderQTY - hDBHandle.GetInProcessQty(strBarcode, appPOName) > 0)
					{
						iUpdatePOStatusFlag += iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
					}
				}
				if (iUpdatePOStatusFlag == 0)
				{
					sql = "UPDATE customer_po SET status = 1 WHERE po_name='" + appPOName + "'";
					hDBHandle.execUpate(sql);
				}
				else
				{
					iUpdatePOStatusFlag = 0;
					OrderName = hDBHandle.GenOrderName("MB_" + createDate + "_" + appPOName);
					sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
					hDBHandle.execUpate(sql);
					for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
					{
						String strOrderQTY = request.getParameter(Integer.toString(iRow+1) + "_QTY").replace(" ", "");
						int iAllOrderQTY = Integer.parseInt(recordList.get(1).get(iRow)) * (100 + Integer.parseInt(recordList.get(3).get(iRow)))/100;
						if(strOrderQTY != null&&Integer.parseInt(strOrderQTY) > 0)
						{
							String strBarcode = recordList.get(0).get(iRow);
							String strDeliDate = recordList.get(2).get(iRow);
							int tempOrderQty = iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
							if(tempOrderQty > 0)
							{
								sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + hDBHandle.GetUsedBarcode(strBarcode, "product_order_record") + "','" + strDeliDate + "','" + Integer.toString(tempOrderQty) + "','" + appPOName + "','" + OrderName + "')";
								hDBHandle.execUpate(sql);
								iUpdatePOStatusFlag += iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
							}
						}
						else if(iAllOrderQTY - hDBHandle.GetInProcessQty(recordList.get(0).get(iRow), appPOName) > 0)
						{
							String strBarcode = recordList.get(0).get(iRow);
							String strDeliDate = recordList.get(2).get(iRow);
							int tempOrderQty = iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
							if(tempOrderQty > 0)
							{
								sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + hDBHandle.GetUsedBarcode(strBarcode, "product_order_record") + "','" + strDeliDate + "','" + Integer.toString(tempOrderQty) + "','" + appPOName + "','" + OrderName + "')";
								hDBHandle.execUpate(sql);
								iUpdatePOStatusFlag += iAllOrderQTY-hDBHandle.GetInProcessQty(strBarcode, appPOName)-hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
							}
						}
					}
					if (iUpdatePOStatusFlag == 0)
					{
						sql = "UPDATE customer_po SET status = 1 WHERE po_name='" + appPOName + "'";
						hDBHandle.execUpate(sql);
					}
				}
			}
		}
		*/
		response.sendRedirect("../Query_Order.jsp");
	}
%>
