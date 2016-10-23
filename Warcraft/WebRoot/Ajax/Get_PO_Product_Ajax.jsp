<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	List<String> recordList = null;
	String po_name = request.getParameter("po_name").replace(" ", "");
	if(po_name.length() > 6)
	{
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
		if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
		{
			recordList = hCPRHandle.getDBRecordList("Bar_Code");
			for(int iRow = 0; iRow < recordList.size(); iRow++)
			{
				String strBarcode = recordList.get(iRow);
				rtnRst += strBarcode + "$";
			}
		}
	}
	else
	{
		rtnRst += "error$产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
