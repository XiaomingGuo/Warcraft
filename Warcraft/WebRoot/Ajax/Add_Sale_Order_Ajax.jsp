<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@	page import="com.DB.operation.Shipping_No"%>
<%
	String rtnRst = "remove$";
	String po_name = (String)request.getParameter("POName").replace(" ", "");
	if (po_name != null)
	{
		Calendar mData = Calendar.getInstance();
		String currentDate = String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
		DBTableParent hSRHandle = new DatabaseStore("Shipping_Record");
		hSRHandle.QueryRecordByFilterKeyList(Arrays.asList("customer_po", "shipping_no"), Arrays.asList(po_name, "0"));
		if (hSRHandle.getTableInstance().RecordDBCount() > 0)
		{
			List<String> idList = hSRHandle.getDBRecordList("id");
			DBTableParent hSNHandle = new DatabaseStore("Shipping_No");
			hSNHandle.QueryRecordMoreThanKeyValue("shipping", currentDate + "0000");
			String ship_no = String.format("%s%04d", currentDate, hSNHandle.getTableInstance().RecordDBCount() + 1);
			((Shipping_No)hSNHandle.getTableInstance()).AddARecord(po_name, ship_no);
			for (int index = 0; index < idList.size(); index++)
			{
				hSRHandle.UpdateRecordByKeyList("shipping_no", ship_no, Arrays.asList("id"), Arrays.asList(idList.get(index)));
			}
		}
	}
	out.write(rtnRst);
%>
