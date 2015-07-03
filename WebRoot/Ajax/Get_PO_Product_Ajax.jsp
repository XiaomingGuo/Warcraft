<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	List<String> recordList = null;
	String po_name = request.getParameter("po_name");
	if(po_name.length() > 6)
	{
		String sql = "select * from customer_po_record where po_name='" + po_name + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
		{
			recordList = hDBHandle.GetAllStringValue("Bar_Code");
			for(int iRow = 0; iRow < recordList.size(); iRow++)
			{
				String strBarcode = recordList.get(iRow);
				rtnRst += strBarcode + "$";
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	else
	{
		rtnRst += "error$产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
