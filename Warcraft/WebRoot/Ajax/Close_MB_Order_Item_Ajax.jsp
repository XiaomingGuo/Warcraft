<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PO_Shipment_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name").replace(" ", "");
	String status = request.getParameter("status");
	String po_status = null;
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "客户PO单名", "交货时间", "客户PO数量", "已投产数量", "交付数量", "操作"};
	
	PO_Shipment_Item_Ajax hPageHandle = new PO_Shipment_Item_Ajax();
	
	List<List<String>> recordList = hPageHandle.GetCustomerPoRecordList(po_name);
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
			int iCPOQTY = 0, iPro_storage = 0;
			String strBarcode = recordList.get(0).get(iRow);
			List<String> proInfoList = hPageHandle.GetProductInfoList(strBarcode);
			for(int iCol = 0; iCol < iColCount; iCol++)
			{
				if("ID" == displayList[iCol])
				{
					rtnRst += Integer.toString(iRow+1) + "$";
				}
				else if("产品类型" == displayList[iCol])
				{
					rtnRst += proInfoList.get(1) + "$";
				}
				else if("产品名称" == displayList[iCol])
				{
					rtnRst += proInfoList.get(2) + "$";
				}
				else if("八码" == displayList[iCol])
				{
					rtnRst += proInfoList.get(0) + "$";
				}
				//{"Bar_Code", "po_name", "delivery_date", "QTY", "OUT_QTY", "percent"};
				else if("客户PO单名" == displayList[iCol])
				{
					rtnRst += recordList.get(1).get(iRow) + "$";
				}
				else if("交货时间" == displayList[iCol])
				{
					rtnRst += recordList.get(2).get(iRow) + "$";
				}
				else if("客户PO数量" == displayList[iCol])
				{
					iCPOQTY = hPageHandle.CalcOrderQty(recordList.get(3).get(iRow), recordList.get(5).get(iRow));
					rtnRst += Integer.toString(iCPOQTY) + "$";
				}
				else if("已投产数量" == displayList[iCol])
				{
					iPro_storage = hPageHandle.GetInProcessRepertory(proInfoList.get(0), recordList.get(1).get(iRow));
					rtnRst += Integer.toString(iPro_storage)  + "$";
				}
				else if ("交付数量" == displayList[iCol])
				{
					rtnRst += Integer.toString(hPageHandle.GetReleaseRepertory(proInfoList.get(0), recordList.get(1).get(iRow))) + "$";
				}
				else if("操作" == displayList[iCol])
				{
					rtnRst += "1$";
				}
			}
		}
	}
	out.write(rtnRst);
%>
