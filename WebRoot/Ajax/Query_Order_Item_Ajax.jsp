<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayList = {"ID", "产品类型", "产品名称", "八码", "交货时间", "数量", "成品库存", "物料库存", "采购量", "进货余量", "订单名", "创建时间", "操作"};
	String[] sqlKeyList = {"id", "product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "percent", "Order_Name", "create_date", "status"};
	List<List<String>> recordList = null;
%>
<%
	String rtnRst = "remove$";
	String order_name = (String)request.getParameter("order_name");
	
	if (order_name != null)
	{
		String sql = "select * from product_order_record where Order_Name='" + order_name + "'";
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
				for(int iCol = 0; iCol < iColCount; iCol++)
				{	
					if("成品库存" == displayList[iCol])
					{
						iPro_storage = hDBHandle.GetRepertoryByBarCode(recordList.get(3).get(iRow), "product_storage");
						rtnRst += Integer.toString(iPro_storage)  + "$";
					}
					else if ("物料库存" == displayList[iCol])
					{
						iMat_storage = hDBHandle.GetRepertoryByBarCode(recordList.get(3).get(iRow), "material_storage");
						rtnRst += Integer.toString(iMat_storage) + "$";
					}
					else if ("采购量" == displayList[iCol])
					{
						int orderCount = Integer.parseInt(recordList.get(5).get(iRow));
						int iRepertory = iPro_storage + iMat_storage;
						if (iRepertory >= orderCount)
						{
							rtnRst += "0$";
						}
						else
						{
							int POCount = orderCount - iRepertory;
							float ratio = 1 + (float)Integer.parseInt(recordList.get(6).get(iRow))/100;
							rtnRst += Integer.toString((int)(POCount*ratio)) + "$";
						}
						
					}
					else if (iCol > 5)
					{
						rtnRst += recordList.get(iCol-3).get(iRow) + "$";
					}
					else
					{
						rtnRst += recordList.get(iCol).get(iRow) + "$";
					}
				}
			}
		}
	}
	out.write(rtnRst);
%>
