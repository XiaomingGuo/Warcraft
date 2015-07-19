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
	if (appPOName != null && appDelivDate.length() == 8)
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
					String strMaterialBarcode = Integer.toString(Integer.parseInt(strBarcode)-10000000);
					String strVendor = recordList.get(1).get(iRow);
					int iPOCount = Integer.parseInt(recordList.get(2).get(iRow));
					int ipercent = Integer.parseInt(recordList.get(3).get(iRow));
					int iRepertory = hDBHandle.GetRepertoryByBarCode(strMaterialBarcode, "material_storage") + hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
					int manufacture_QTY = iPOCount*(100+ipercent)/100;
					if (iRepertory < manufacture_QTY)
					{
						sql = "select * from mb_material_po where Bar_Code='" + strMaterialBarcode + "' and vendor='" + strVendor + "' and po_name='" + appPOName + "'";
						if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
						{
							hDBHandle.CloseDatabase();
							if (appDelivDate != null&&!appDelivDate.isEmpty()&&appDelivDate.length() == 8)
							{
								sql = "INSERT INTO mb_material_po (Bar_Code, vendor, po_name, PO_QTY, date_of_delivery) VALUES ('" + strMaterialBarcode + "','" + strVendor + "','" + appPOName + "','" + Integer.toString(manufacture_QTY-iRepertory) + "','" + appDelivDate + "')";
								hDBHandle.execUpate(sql);
							}
							else
							{
								rtnRst += "error:交货日期填写有误!$";
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
			rtnRst += "error:po单不存在!";
		}
	}
	else
	{
		rtnRst += "error:po单需要填写交货日期!";
	}
	out.write(rtnRst);
%>
