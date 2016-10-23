<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.jsp.support.Generate_Order_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name").replace(" ", "");
	String status = request.getParameter("status");
	Generate_Order_Item_Ajax hPageHandle = new Generate_Order_Item_Ajax();
	String po_status = null;
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "客户PO单名", "交货时间", "客户PO数量", "已交付数量", "加工总量", "已加工总量", "成品库存", "物料库存", "缺料数量", "操作"};
	DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
	hPORHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("po_name"), Arrays.asList(po_name), Arrays.asList("id"));
	if (hPORHandle.getTableInstance().RecordDBCount() > 0)
	{
		String[] sqlKeyList = {"Bar_Code", "po_name", "delivery_date", "QTY", "OUT_QTY", "percent"};
		List<List<String>> recordList = new ArrayList<List<String>>();
		for(int idx=0; idx < sqlKeyList.length; idx++)
		{
			recordList.add(hPORHandle.getDBRecordList(sqlKeyList[idx]));
		}
		int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
		rtnRst += Integer.toString(iColCount) + "$";
		rtnRst += Integer.toString(iRowCount) + "$";
		for(int i = 0; i < iColCount; i++)
		{
			rtnRst += displayList[i] + "$";
		}
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		DBTableParent hPSHandle = new DatabaseStore("Product_Storage");
		DBTableParent hMSHandle = new DatabaseStore("Material_Storage");
		for(int iRow = 0; iRow < iRowCount; iRow++)
		{
			int iPro_storage = 0, iMat_storage = 0, iCPOQTY = 0, iDelivQTY = 0, iOrderQTY = 0, inProcess = 0;
			String strBarcode = recordList.get(0).get(iRow);
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
			for(int iCol = 0; iCol < iColCount; iCol++)
			{
				if("ID" == displayList[iCol])
				{
					rtnRst += Integer.toString(iRow+1) + "$";
				}
				else if("产品类型" == displayList[iCol])
				{
					rtnRst += hPIHandle.getDBRecordList("product_type").get(0) + "$";
				}
				else if("产品名称" == displayList[iCol])
				{
					rtnRst += hPIHandle.getDBRecordList("name").get(0) + "$";
				}
				else if("八码" == displayList[iCol])
				{
					rtnRst += strBarcode + "$";
				}
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
					iCPOQTY = Integer.parseInt(recordList.get(3).get(iRow));
					rtnRst += recordList.get(3).get(iRow) + "$";
				}
				else if("已交付数量" == displayList[iCol])
				{
					iDelivQTY = Integer.parseInt(recordList.get(4).get(iRow));
					rtnRst += recordList.get(4).get(iRow) + "$";
				}
				else if("加工总量" == displayList[iCol])
				{
					iOrderQTY = iCPOQTY*(100+Integer.parseInt(recordList.get(5).get(iRow)))/100;
					rtnRst += Integer.toString(iOrderQTY) + "$";
				}
				else if("已加工总量" == displayList[iCol])
				{
					inProcess = hPORHandle.GetIntSumOfValue("QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hPORHandle.GetUsedBarcode(strBarcode, "product_order_record"), po_name));
					rtnRst += Integer.toString(inProcess) + "$";
				}
				else if("成品库存" == displayList[iCol])
				{
					iPro_storage = hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPSHandle.GetUsedBarcode(strBarcode, "product_storage")));
					rtnRst += Integer.toString(iPro_storage)  + "$";
				}
				else if ("物料库存" == displayList[iCol])
				{
					int tempQTY = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hMSHandle.GetUsedBarcode(strBarcode, "material_storage")))-
							hPageHandle.GetUncompleteOrderRecord(hPORHandle.GetUsedBarcode(strBarcode, "product_order_record"));
					iMat_storage = (tempQTY > 0)?tempQTY:0;
					rtnRst += Integer.toString(iMat_storage) + "$";
				}
				else if ("缺料数量" == displayList[iCol])
				{
					if ((iOrderQTY-inProcess) > (iPro_storage+iMat_storage))
					{
						rtnRst += Integer.toString((iOrderQTY-inProcess)-(iPro_storage+iMat_storage)) + "$";
					}
					else
					{
						rtnRst += "0$";
					}
				}
				else if ("操作" == displayList[iCol])
				{
					int operationQTY = iOrderQTY - iPro_storage;
					if(operationQTY > 0)
					{
						if(operationQTY > iMat_storage)
						{
							if (iMat_storage > 0)
							{
								rtnRst += Integer.toString(iMat_storage) + "$";
							}
							else
							{
								rtnRst += "-1$";
							}
						}
						else
						{
							rtnRst += Integer.toString(operationQTY) + "$";;
						}
					}
					else
					{
						rtnRst += "0$";
					}
				}
			}
		}
	}
	out.write(rtnRst);
%>
