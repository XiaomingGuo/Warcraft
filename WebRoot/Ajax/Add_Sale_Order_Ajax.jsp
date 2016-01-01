<%@page import="com.DB.operation.Shipping_No"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Shipping_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String po_name = (String)request.getParameter("POName").replace(" ", "");
	if (po_name != null)
	{
		Calendar mData = Calendar.getInstance();
		String currentDate = String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
		Shipping_Record hSRHandle = new Shipping_Record(new EarthquakeManagement());
		hSRHandle.QueryRecordByFilterKeyList(Arrays.asList("customer_po", "shipping_no"), Arrays.asList(po_name, "0"));
		if (hSRHandle.RecordDBCount() > 0)
		{
			List<String> idList = hSRHandle.getDBRecordList("id");
			String ship_no = null;
			Shipping_No hSNHandle = new Shipping_No(new EarthquakeManagement());
			hSNHandle.QueryRecordMoreThanShipNo(currentDate + "0000");
			ship_no = String.format("%s%04d", currentDate, hSNHandle.RecordDBCount() + 1);
			hSNHandle.AddARecord(po_name, ship_no);
			for (int index = 0; index < idList.size(); index++)
			{
				hSRHandle.UpdateRecordByKeyList("shipping_no", ship_no, Arrays.asList("id"), Arrays.asList(idList.get(index)));
			}
		}
	}
	out.write(rtnRst);
%>
