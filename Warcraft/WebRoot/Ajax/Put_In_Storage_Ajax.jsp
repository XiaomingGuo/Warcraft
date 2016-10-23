<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String rtnRst = "remove$";
	String pro_id = request.getParameter("product_id").replace(" ", "");
	String QTYOfStore = request.getParameter("PutInQTY").replace(" ", "");
	
	if (pro_id != null && QTYOfStore != null)
	{
		int used_count = Integer.parseInt(QTYOfStore);
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		if (hPORHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] orderRecordKey = {"Bar_Code", "Order_Name", "QTY", "completeQTY"};
			List<List<String>> orderInfo = new ArrayList<List<String>>();
			for(int idx=0; idx < orderRecordKey.length; idx++)
			{
				orderInfo.add(hPORHandle.getDBRecordList(orderRecordKey[idx]));
			}
			String barcode = orderInfo.get(0).get(0);
			String ordername = orderInfo.get(1).get(0);
			int order_QTY = Integer.parseInt(orderInfo.get(2).get(0));
			int pro_record_comp_QTY = Integer.parseInt(orderInfo.get(3).get(0));
			hPORHandle.UpdateRecordByKeyList("completeQTY", Integer.toString(pro_record_comp_QTY + used_count), Arrays.asList("id"), Arrays.asList(pro_id));
			if (order_QTY == (pro_record_comp_QTY+used_count))
			{
				hPORHandle.UpdateRecordByKeyList("status", "3", Arrays.asList("id"), Arrays.asList(pro_id));
			}
			else
			{
				hPORHandle.UpdateRecordByKeyList("status", "2", Arrays.asList("id"), Arrays.asList(pro_id));
			}
		}
	}
	
	out.write(rtnRst);
%>
