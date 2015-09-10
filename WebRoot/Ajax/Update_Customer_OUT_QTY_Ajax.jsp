<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String appBarcode = (String)request.getParameter("Bar_Code");
	String appPONum = (String)request.getParameter("po_name");
	String out_QTY = request.getParameter("OUT_QTY");
	int used_count = Integer.parseInt(out_QTY);
	//product_type Database query
	if (appBarcode != null && appPONum != null && out_QTY != null && used_count > 0 && appBarcode.length()==8 && appPONum!="")
	{
		int repertory_count = hDBHandle.GetRepertoryByBarCode(appBarcode, "product_storage");
		if (repertory_count >= used_count)
		{
			String sql = "select * from product_storage where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "product_storage") +"'";
			if (hDBHandle.QueryDataBase(sql))
			{
				String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name"};
				List<List<String>> material_info_List = hDBHandle.GetAllDBColumnsByList(keyArray);
				for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
				{
					String batchLot =  material_info_List.get(0).get(iCol);
					int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
					int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
					String ordername = material_info_List.get(3).get(iCol);
					int recordCount = sql_in_count - sql_out_count;
					if (recordCount >= used_count)
					{
						sql= "UPDATE product_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "product_storage") +"' and Batch_Lot='" + batchLot +"'";
						hDBHandle.execUpate(sql);
						if (recordCount == used_count)
						{
							hDBHandle.MoveToExhaustedTable(appBarcode, batchLot, "product_storage", "exhausted_product");
						}
						sql = "INSERT INTO shipping_record (customer_po, Bar_Code, Batch_Lot, Order_Name, ship_QTY) VALUES ('" + appPONum + "','" + hDBHandle.GetUsedBarcode(appBarcode, "shipping_record") + "','" + batchLot + "','" + ordername +"','" + used_count +"')";
						hDBHandle.execUpate(sql);
						break;
					}
					else
					{
						sql= "UPDATE product_storage SET OUT_QTY='" + Integer.toString(sql_in_count) + "' WHERE Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "product_storage") +"' and Batch_Lot='" + batchLot +"'";
						hDBHandle.execUpate(sql);
						hDBHandle.MoveToExhaustedTable(appBarcode, batchLot, "product_storage", "exhausted_product");
						sql = "INSERT INTO shipping_record (customer_po, Bar_Code, Batch_Lot, Order_Name, ship_QTY) VALUES ('" + appPONum + "','" + hDBHandle.GetUsedBarcode(appBarcode, "shipping_record") + "','" + batchLot + "','" + ordername +"','" + Integer.toString(recordCount) +"')";
						hDBHandle.execUpate(sql);
						used_count -= recordCount;
					}
				}
				String writeQTY = Integer.toString(Integer.parseInt(hDBHandle.GetPOInfo(appBarcode, appPONum, "OUT_QTY")) + Integer.parseInt(out_QTY));
				sql= "UPDATE customer_po_record SET OUT_QTY='" + writeQTY + "'WHERE Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "shipping_record") +"' and po_name='" + appPONum +"'";
				hDBHandle.execUpate(sql);
				response.sendRedirect("../Product_Shipment.jsp");
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		else
		{
			session.setAttribute("error", "("+ appBarcode + "): 库存数量不足,不够出货数量,加油生产吧兄弟!");
			response.sendRedirect("../tishi.jsp");
		}
	}
	else
	{
		session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
		response.sendRedirect("../tishi.jsp");
	}

	out.write(rtnRst);
%>
