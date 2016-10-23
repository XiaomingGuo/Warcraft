<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(order_name));
		if (hPORHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] keyList = {"id", "Bar_Code", "QTY"};
			List<List<String>> recordList = new ArrayList<List<String>>();
			for(int idx=0; idx < keyList.length; idx++)
			{
				recordList.add(hPORHandle.getDBRecordList(keyList[idx]));
			}
			boolean isApprove = true;
			if (recordList != null)
			{
				List<String> sqlList = new ArrayList<String>();
				DBTableParent hMSHandle = new DatabaseStore("Material_Storage");
				for (int index = 0; index < recordList.get(0).size(); index++)
				{
					if (hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(recordList.get(1).get(index))) >= Integer.parseInt(recordList.get(2).get(index)))
					{
						sqlList.add(recordList.get(0).get(index));
					}
					else
					{
						rtnRst += "error:原材料库存不足不能生产!$";
						isApprove = false;
						break;
					}
				}
				if (isApprove)
				{
					for(int idx = 0; idx < sqlList.size(); idx++)
					{
						hPORHandle.UpdateRecordByKeyList("status", "1", Arrays.asList("id"), Arrays.asList(sqlList.get(idx)));
					}
					DBTableParent hPOHandle = new DatabaseStore("Product_Order");
					hPOHandle.UpdateRecordByKeyList("status", "1", Arrays.asList("Order_Name"), Arrays.asList(order_name));
				}
			}
		}
	}
	out.write(rtnRst);
%>
