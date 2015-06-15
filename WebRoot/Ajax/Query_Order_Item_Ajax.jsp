<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] sqlKeyList = {"product_type", "product_name", "Bar_Code", "delivery_date", "QTY", "precent", "Order_Name", "status", "create_date"};
	List<List<String>> recordList = null;
%>
<%
	String rtnRst = "remove$";
	String order_name = (String)request.getParameter("order_name");
	
	if (order_name != null)
	{
		String sql = "select * from product_order where Order_Name='" + order_name + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
		{
			recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			int iRowCount = recordList.get(0).size(), iColCount = sqlKeyList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += sqlKeyList[i] + "$";
			}
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					rtnRst += recordList.get(iCol).get(iRow) + "$";
				}
			}
		}
	}
	out.write(rtnRst);
%>
