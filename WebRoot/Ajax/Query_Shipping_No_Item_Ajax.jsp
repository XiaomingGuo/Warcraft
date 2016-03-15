<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Material_Storage"%>
<%@ page import="com.DB.operation.Product_Storage"%>
<%@ page import="com.DB.operation.Semi_Product_Storage"%>
<%@ page import="com.DB.operation.Product_Info"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Query_Shipping_No_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String shipNo = request.getParameter("Shipping_No").replace(" ", "");
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "批号", "PO单号", "数量", "出货时间"};
	if(shipNo.length() > 6)
	{
		Query_Shipping_No_Item_Ajax hPageHandle = new Query_Shipping_No_Item_Ajax();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		List<List<String>> recordList = hPageHandle.GetShippingRecordByShippingNo(shipNo);
		if (recordList.size() > 0)
		{
			//{"id", "customer_po", "Bar_Code", "Batch_Lot", "Order_Name", "ship_QTY"};
			int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += displayList[i] + "$";
			}
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				String strBarcode = recordList.get(2).get(iRow);
				String strBatchLot = recordList.get(3).get(iRow);
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
					else if("八码" == displayList[iCol])
					{
						rtnRst += strBarcode + "$";
					}
					else if("批号" == displayList[iCol])
					{
						rtnRst += strBatchLot + "$";
					}
					else if ("PO单号" == displayList[iCol])
					{
						rtnRst += recordList.get(1).get(iRow) + "$";
					}
					else if ("数量" == displayList[iCol])
					{
						rtnRst += recordList.get(5).get(iRow) + "$";
					}
					else if ("出货时间" == displayList[iCol])
					{
						rtnRst += hPageHandle.GetShippingDate(shipNo) + "$";
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
