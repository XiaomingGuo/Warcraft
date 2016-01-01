<%@page import="com.DB.operation.Mb_Material_Po"%>
<%@page import="com.DB.operation.Material_Storage"%>
<%@page import="com.DB.operation.Product_Order_Record"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String vendor = (String)request.getParameter("vendor_name").replace(" ", "");
	String bar_code = (String)request.getParameter("bar_code").replace(" ", "");
	String deliv_date = (String)request.getParameter("delivery_date").replace(" ", "");
	String pro_qty = (String)request.getParameter("corder_QTY").replace(" ", "");
	String percent = (String)request.getParameter("percent").replace(" ", "");
	String order_name = (String)request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null&&order_name != "")
	{
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		hPOHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(order_name));
		if (hPOHandle.RecordDBCount() <= 0)
		{
			if (vendor != null&&vendor != ""&&bar_code != null&&deliv_date != null&&pro_qty != null&&percent != null)
			{
				int iOrderQTY = Integer.parseInt(pro_qty)*(100 + Integer.parseInt(percent))/100;
				Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
				String wBarcode = hPORHandle.GetUsedBarcode(bar_code, "product_order_record");
				hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Order_Name"), Arrays.asList(wBarcode, order_name));
				if (hPORHandle.RecordDBCount() <= 0)
				{
					hPORHandle.AddARecord(wBarcode, deliv_date, iOrderQTY, "Internal_po", order_name);
				}
				else
				{
					int tempQTY = hPORHandle.GetIntSumOfValue("QTY", Arrays.asList("Bar_Code", "Order_Name"), Arrays.asList(wBarcode, order_name));
					hPORHandle.UpdateRecordByKeyList("QTY", Integer.toString(tempQTY + iOrderQTY), Arrays.asList("Order_Name", "Bar_Code"), Arrays.asList(order_name, wBarcode));
				}
				
				Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
				String material_barcode = hMSHandle.GetUsedBarcode(bar_code, "mb_material_po");
				int po_qty = iOrderQTY - hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(material_barcode));
				if (po_qty > 0)
				{
					Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
					hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(material_barcode, order_name));
					if (hMMPHandle.RecordDBCount() <= 0)
					{
						hMMPHandle.AddARecord(material_barcode, vendor, order_name, po_qty, "null");
					}
					else
					{
						int tempQTY = hMMPHandle.GetIntSumOfValue("PO_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(material_barcode, order_name));
						hMMPHandle.UpdateRecordByKeyList("PO_QTY", Integer.toString(tempQTY + po_qty), Arrays.asList("Bar_Code", "po_name"), Arrays.asList(material_barcode, order_name));
					}
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
