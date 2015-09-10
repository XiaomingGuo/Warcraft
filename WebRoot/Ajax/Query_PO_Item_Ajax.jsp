<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayList = {"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "数量", "成品库存", "物料库存", "采购量", "进货余量", "创建时间", "操作"};
	String[] sqlKeyList = {"id", "vendor", "Bar_Code", "po_name", "delivery_date", "QTY", "percent", "create_date"};
	List<List<String>> recordList = null;
%>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name");
	String status = request.getParameter("status");
	String po_status = null;
	if(po_name.length() > 6)
	{
		String sql = null;
		if (status != null)
		{
			sql = "select * from customer_po where po_name='" + po_name + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				po_status = hDBHandle.GetSingleString("status");
				if (Integer.parseInt(po_status) > Integer.parseInt(status))
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
		rtnRst += po_status + "$";
		sql = "select * from customer_po_record where po_name='" + po_name + "'";
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
				int iPro_storage = 0, iMat_storage = 0;
				String strBarcode = recordList.get(2).get(iRow);
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					if("ID" == displayList[iCol])
					{
						rtnRst += Integer.toString(iRow+1) + "$";
					}
					else if("产品类型" == displayList[iCol])
					{
						String protype = hDBHandle.GetTypeByBarcode(strBarcode);
						rtnRst += protype + "$";
					}
					else if("产品名称" == displayList[iCol])
					{
						String proname = hDBHandle.GetNameByBarcode(strBarcode);
						rtnRst += proname + "$";
					}
					else if("成品库存" == displayList[iCol])
					{
						iPro_storage = hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
						rtnRst += Integer.toString(iPro_storage)  + "$";
					}
					else if ("物料库存" == displayList[iCol])
					{
						iMat_storage = hDBHandle.GetRepertoryByBarCode(strBarcode, "material_storage");
						rtnRst += Integer.toString(iMat_storage) + "$";
					}
					else if ("采购量" == displayList[iCol])
					{
						int poCount = Integer.parseInt(recordList.get(5).get(iRow)) * (100 + Integer.parseInt(recordList.get(6).get(iRow)))/100;
						int iRepertory = iPro_storage + iMat_storage;
						if (iRepertory >= poCount)
						{
							rtnRst += "0$";
						}
						else
						{
							rtnRst += Integer.toString(poCount - iRepertory) + "$";
						}
						
					}
					else if ("操作" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
					else if ("进货余量" == displayList[iCol])
					{
						rtnRst += recordList.get(6).get(iRow) + "$";
					}
					else if ("创建时间" == displayList[iCol])
					{
						rtnRst += recordList.get(7).get(iRow) + "$";
					}
					else
					{
						rtnRst += recordList.get(iCol-2).get(iRow) + "$";
					}
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	else
	{
		rtnRst += "error:产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
