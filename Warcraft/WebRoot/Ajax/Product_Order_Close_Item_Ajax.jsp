<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%!
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "交货时间", "数量", "完成数量", "报废数量", "未完成数", "检验合格数", "材料库存", "客户PO单名", "生产单名", "创建时间", "状态", "操作"};
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	String status = request.getParameter("status");
	
	if (order_name.length() > 11)
	{
		DBTableParent HPORHandle = new DatabaseStore("Product_Order_Record");
		if (status == null)
		{
			HPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(order_name));
		}
		else
		{
			HPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name", "status"), Arrays.asList(status));
		}
		if (HPORHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"id", "Bar_Code", "delivery_date", "QTY", "completeQTY", "OQC_QTY", "po_name", "Order_Name", "create_date", "status"};
			List<List<String>> recordList = new ArrayList<List<String>>();
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				recordList.add(HPORHandle.getDBRecordList(sqlKeyList[idx]));
			}
			int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += displayList[i] + "$";
			}
			DBTableParent hPIHandle = new DatabaseStore("Product_Info");
			DBTableParent hDMRHandle = new DatabaseStore("Discard_Material_Record");
			DBTableParent hMSHandle = new DatabaseStore("Material_Storage");
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				int iPro_storage = 0, iMat_storage = 0, iOrderQTY = 0, iCompleteQTY = 0;
				String strBarcode = recordList.get(1).get(iRow);
				hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					if("ID" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
					else if("产品类型" == displayList[iCol])
					{
						rtnRst += hPIHandle.getDBRecordList("Bar_Code").get(0) + "$";
					}
					else if("产品名称" == displayList[iCol])
					{
						rtnRst += hPIHandle.getDBRecordList("name").get(0) + "$";
					}
					else if("八码" == displayList[iCol])
					{
						rtnRst += strBarcode + "$";
					}
					else if("交货时间" == displayList[iCol])
					{
						rtnRst += recordList.get(2).get(iRow) + "$";
					}
					else if("数量" == displayList[iCol])
					{
						iOrderQTY = Integer.parseInt(recordList.get(3).get(iRow));
						rtnRst += recordList.get(3).get(iRow) + "$";
					}
					else if("完成数量" == displayList[iCol])
					{
						iCompleteQTY = Integer.parseInt(recordList.get(4).get(iRow));
						rtnRst += recordList.get(4).get(iRow) + "$";
					}
					else if("报废数量" == displayList[iCol])
					{
						rtnRst += hDMRHandle.GetIntSumOfValue("QTY", Arrays.asList("Bar_Code", "Order_Name"), Arrays.asList(hDMRHandle.GetUsedBarcode(strBarcode, "discard_material_record"))) + "$";
					}
					else if("未完成数" == displayList[iCol])
					{
						rtnRst += Integer.toString(iOrderQTY - iCompleteQTY) + "$";
					}
					else if("检验合格数" == displayList[iCol])
					{
						rtnRst += recordList.get(5).get(iRow) + "$";
					}
					else if("材料库存" == displayList[iCol])
					{
						rtnRst += hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hMSHandle.GetUsedBarcode(strBarcode, "material_storage"))) + "$";
					}
					else if("客户PO单名" == displayList[iCol])
					{
						rtnRst += recordList.get(6).get(iRow) + "$";
					}
					else if("生产单名" == displayList[iCol])
					{
						rtnRst += recordList.get(7).get(iRow) + "$";
					}
					else if("创建时间" == displayList[iCol])
					{
						rtnRst += recordList.get(8).get(iRow)  + "$";
					}
					else if("状态" == displayList[iCol])
					{
						rtnRst += recordList.get(9).get(iRow) + "$";
					}
					else if("操作" == displayList[iCol])
					{
						rtnRst += recordList.get(9).get(iRow) + "$";
					}
				}
			}
		}
	}
	out.write(rtnRst);
%>
