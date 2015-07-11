<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "客户PO单名", "交货时间", "客户PO数量", "已交付数量", "加工总量", "已加工总量", "成品库存", "交付数量", "操作"};
	String[] sqlKeyList = {"Bar_Code", "po_name", "delivery_date", "QTY", "OUT_QTY", "percent"};
	List<List<String>> recordList = null;
%>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name");
	String status = request.getParameter("status");
	String po_status = null;
	String sql = "select * from customer_po_record where po_name='" + po_name + "' order by id asc";
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
			int iPro_storage = 0, iMat_storage = 0, iCPOQTY = 0, iDelivQTY = 0, iOrderQTY = 0, inProcess = 0;
			String strBarcode = recordList.get(0).get(iRow);
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
					inProcess = hDBHandle.GetInProcessQty(strBarcode, po_name);
					rtnRst += Integer.toString(inProcess) + "$";
				}
				else if("成品库存" == displayList[iCol])
				{
					iPro_storage = hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
					rtnRst += Integer.toString(iPro_storage)  + "$";
				}
				else if ("交付数量" == displayList[iCol])
				{
					int po_count = iCPOQTY-iDelivQTY;
					if (po_count <= 0)
					{
						rtnRst += "0$";
					}
					else if(po_count <= iPro_storage)
					{
						rtnRst += Integer.toString(po_count) + "$";
					}
					else if(po_count > (iPro_storage))
					{
						rtnRst += Integer.toString(iPro_storage) + "$";
					}
				}
				else if("操作" == displayList[iCol])
				{
					if (iCPOQTY > iDelivQTY)
					{
						rtnRst += "1$";
					}
					else
					{
						rtnRst += "0$";
					}
				}
			}
		}
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	out.write(rtnRst);
%>
