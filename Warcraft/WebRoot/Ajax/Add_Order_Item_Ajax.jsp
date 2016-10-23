<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.DB.operation.Customer_Po_Record"%>
<%
	String rtnRst = "remove$";
	String vendor = (String)request.getParameter("vendor_name").replace(" ", "");
	String bar_code = (String)request.getParameter("bar_code").replace(" ", "");
	String deliv_date = (String)request.getParameter("delivery_date").replace(" ", "");
	String pro_qty = (String)request.getParameter("corder_QTY").replace(" ", "");
	String percent = (String)request.getParameter("percent").replace(" ", "");
	String order_name = (String)request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null&&!order_name.isEmpty())
	{
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		hCPHandle.QueryRecordByFilterKeyListAndMoreThanKeyValue(Arrays.asList("po_name"), Arrays.asList(order_name), "status", "0");
		if (hCPHandle.getTableInstance().RecordDBCount() <= 0)
		{
			if (bar_code != null&&deliv_date != null&&pro_qty != null&&vendor != null)
			{
				DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(bar_code, "Product_Storage"), order_name));
				int recordCount = hCPRHandle.getTableInstance().RecordDBCount();
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(bar_code, "Semi_Pro_Storage"), order_name));
				recordCount += hCPRHandle.getTableInstance().RecordDBCount();
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(bar_code, "Material_Storage"), order_name));
				recordCount += hCPRHandle.getTableInstance().RecordDBCount();
				if (recordCount <= 0)
				{
					((Customer_Po_Record)hCPRHandle.getTableInstance()).AddARecord(bar_code, order_name, deliv_date, pro_qty, vendor, percent);
				}
				else
				{
					rtnRst += "error:大哥这产品已经有了,要不删掉重新输入!";
				}
			}
		}
		else
		{
			rtnRst += "error:大哥这生产单已经有了,换个生产单名吧!";
		}
		
	}
	out.write(rtnRst);
%>
