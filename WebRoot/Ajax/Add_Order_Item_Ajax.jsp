<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
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
		String sql = "select * from product_order where Order_Name='" + order_name + "'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
		{
			hDBHandle.CloseDatabase();
			if (vendor != null&&vendor != ""&&bar_code != null&&deliv_date != null&&pro_qty != null&&percent != null)
			{
				int iOrderQTY = Integer.parseInt(pro_qty)*(100 + Integer.parseInt(percent))/100;
				sql = "select * from product_order_record where Order_Name='" + order_name + "' and Bar_Code='" + hDBHandle.GetUsedBarcode(bar_code, "product_order_record") + "'";
				if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					sql = "INSERT INTO product_order_record (Bar_Code, delivery_date, QTY, po_name, Order_Name) VALUES ('" + hDBHandle.GetUsedBarcode(bar_code, "product_order_record") + "','" + deliv_date + "','" + Integer.toString(iOrderQTY) + "','Internal_po','" + order_name + "')";
					hDBHandle.execUpate(sql);
				}
				else
				{
					int tempQTY = hDBHandle.GetSingleInt("QTY");
					sql = "UPDATE product_order_record SET QTY='" + Integer.toString(tempQTY + iOrderQTY) + "' WHERE Order_Name='" + order_name + "' and Bar_Code='" + hDBHandle.GetUsedBarcode(bar_code, "product_order_record") + "'";
					hDBHandle.execUpate(sql);
				}
				
				String material_barcode = hDBHandle.GetUsedBarcode(bar_code, "mb_material_po");
				int po_qty = iOrderQTY - hDBHandle.GetRepertoryByBarCode(material_barcode, "material_storage");
				if (po_qty > 0)
				{
					sql = "select * from mb_material_po where po_name='" + order_name + "' and Bar_Code='" + material_barcode + "'";
					if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
					{
						hDBHandle.CloseDatabase();
						sql = "INSERT INTO mb_material_po (Bar_Code, vendor, po_name, PO_QTY, date_of_delivery) VALUES ('" + material_barcode + "','" + vendor + "','" + order_name + "','" + Integer.toString(po_qty) + "', 'null')";
						hDBHandle.execUpate(sql);
					}
					else
					{
						int tempQTY = hDBHandle.GetSingleInt("PO_QTY");
						sql = "UPDATE mb_material_po SET PO_QTY='" + Integer.toString(tempQTY + po_qty) + "' WHERE po_name='" + order_name + "' and Bar_Code='" + material_barcode + "'";
						hDBHandle.execUpate(sql);
					}
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
			rtnRst += "error:大哥这生产单已经有了,换个生产单名吧!";
		}
		
	}
	out.write(rtnRst);
%>
