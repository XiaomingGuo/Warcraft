<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Query_Add_Material_PO_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name").replace(" ", "");
	String[] displayList = {"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "采购量", "已交数量", "未交数量", "创建时间", "状态", "操作"};
	if(po_name.length() > 6)
	{
		Query_Add_Material_PO_Item_Ajax hPageHandle = new Query_Add_Material_PO_Item_Ajax();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		List<List<String>> recordList = hPageHandle.GetAllMbMaterialPo(po_name);
		if (recordList.size() > 0)
		{
			int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += displayList[i] + "$";
			}
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				int iPurchaseCount = 0, iFinishCount = 0;
				String strBarcode = recordList.get(1).get(iRow);
				hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					if("ID" == displayList[iCol])
					{
						rtnRst += Integer.toString(iRow+1) + "$";
					}
					else if("产品类型" == displayList[iCol])
					{
						String protype = hPIHandle.getDBRecordList("product_type").get(0);
						rtnRst += protype + "$";
					}
					else if("产品名称" == displayList[iCol])
					{
						String proname = hPIHandle.getDBRecordList("name").get(0);
						rtnRst += proname + "$";
					}
					else if("供应商" == displayList[iCol])
					{
						rtnRst += recordList.get(4).get(iRow) + "$";
					}
					else if("八码" == displayList[iCol])
					{
						rtnRst += strBarcode + "$";
					}
					else if("PO单名" == displayList[iCol])
					{
						rtnRst += recordList.get(2).get(iRow) + "$";
					}
					else if("交货时间" == displayList[iCol])
					{
						rtnRst += recordList.get(3).get(iRow) + "$";
					}
					//{"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "采购量", "已交数量", "未交数量", "创建时间", "操作"};
					//{"id", "Bar_Code", "po_name", "date_of_delivery", "vendor",  "PO_QTY", "create_date"};
					else if("采购量" == displayList[iCol])
					{
						iPurchaseCount = Integer.parseInt(recordList.get(5).get(iRow));
						rtnRst += Integer.toString(iPurchaseCount) + "$";
					}
					else if ("已交数量" == displayList[iCol])
					{
						iFinishCount = hPageHandle.GetHasFinishPurchaseNum(strBarcode, recordList.get(2).get(iRow));
						rtnRst += Integer.toString(iFinishCount) + "$";
					}
					else if ("未交数量" == displayList[iCol])
					{
						rtnRst += Integer.toString(iPurchaseCount-iFinishCount) + "$";
					}
					else if ("创建时间" == displayList[iCol])
					{
						rtnRst += recordList.get(6).get(iRow) + "$";
					}
					else if ("状态" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
					else if ("操作" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
				}
			}
		}
	}
	else
	{
		rtnRst += "error:产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
