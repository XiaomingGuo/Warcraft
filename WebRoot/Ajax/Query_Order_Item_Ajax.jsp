<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "交货时间", "数量", "完成数量", "报废数量", "未完成数", "检验合格数", "材料库存", "采购量", "客户PO单名", "生产单名", "创建时间", "操作"};
	String[] sqlKeyList = {"id", "Bar_Code", "delivery_date", "QTY", "completeQTY", "OQC_QTY", "po_name", "Order_Name", "create_date", "status"};
	List<List<String>> recordList = null;
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	String status = request.getParameter("status");
	
	String order_status = null;
	if (order_name.length() > 12)
	{
		String sql = null;
		if (status != null)
		{
			sql = "select * from product_order where Order_Name='" + order_name + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				order_status = hDBHandle.GetSingleString("status");
				if (Integer.parseInt(order_status) > Integer.parseInt(status))
				{
					rtnRst += "error:该PO单已经存在!";
					out.write(rtnRst);
				}
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		rtnRst += order_status + "$";
		sql = "select * from product_order_record where Order_Name='" + order_name + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
		{
			recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += displayList[i] + "$";
			}
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				int iPro_storage = 0, iMat_storage = 0, iOrderQTY = 0, iCompleteQTY = 0;
				String strBarcode = recordList.get(1).get(iRow);
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					if("ID" == displayList[iCol])
					{
						rtnRst += Integer.toString(iRow+1) + "$";
					}
					else if("产品类型" == displayList[iCol])
					{
						rtnRst += hDBHandle.GetTypeByBarcode(strBarcode) + "$";
					}
					else if("产品名称" == displayList[iCol])
					{
						rtnRst += hDBHandle.GetNameByBarcode(strBarcode) + "$";
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
						rtnRst += hDBHandle.GetDiscardMaterialQTY(strBarcode, order_name) + "$";
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
						int iMaterialQTY = hDBHandle.GetRepertoryByBarCode(strBarcode, "material_storage") - hDBHandle.GetUncompleteOrderRecord(strBarcode);
						if (iMaterialQTY < 0)
						{
							rtnRst += "0$";
						}
						else
						{
							rtnRst += iMaterialQTY + "$";
						}
					}
					else if("采购量" == displayList[iCol])
					{
						int orderCount = Integer.parseInt(recordList.get(3).get(iRow)) * (100 + 8)/100;
						int iRepertory = iMat_storage;
						if (iRepertory >= orderCount)
						{
							rtnRst += "0$";
						}
						else
						{
							rtnRst += Integer.toString(orderCount - iRepertory) + "$";
						}
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
					else if("操作" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "#" + recordList.get(1).get(iRow) + "$";
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
