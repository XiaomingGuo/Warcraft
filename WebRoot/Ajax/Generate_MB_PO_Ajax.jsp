<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String appPOName = (String)request.getParameter("PO_Name");
	String appVendor = (String)request.getParameter("vendor");
	String appDelivDate = (String)request.getParameter("Delivery_Date");
	if (appPOName != null)
	{
		String sql = "select * from customer_po_record where po_name='" + appPOName + "' and vendor='" + appVendor + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
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
						sql = "select * from mb_material_po where Bar_Code='" + strBarcode + "' and vendor='" + strVendor + "' and po_name='" + appPOName + "'";
						if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
						{
							hDBHandle.CloseDatabase();
							if (appDelivDate != null&&!appDelivDate.isEmpty())
							{
								sql = "INSERT INTO mb_material_po (Bar_Code, vendor, po_name, PO_QTY, date_of_delivery) VALUES ('" + strBarcode + "','" + strVendor + "','" + appPOName + "','" + Integer.toString(manufacture_QTY-iRepertory) + "','" + appDelivDate + "')";
								hDBHandle.execUpate(sql);
							}
							else
							{
								rtnRst += "error:交货日期不能为空!$";
								break;
							}
						}
						else
						{
							hDBHandle.CloseDatabase();
						}
					}
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	out.write(rtnRst);
%>
