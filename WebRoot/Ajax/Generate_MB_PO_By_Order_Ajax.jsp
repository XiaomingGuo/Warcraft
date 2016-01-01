<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Mb_Material_Po" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String appOrderName = (String)request.getParameter("Order_Name").replace(" ", "");
	String appVendor = (String)request.getParameter("vendor").replace(" ", "");
	String appDelivDate = (String)request.getParameter("Delivery_Date").replace(" ", "");
	if (appOrderName != null && appDelivDate.length() == 8)
	{
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "vendor"), Arrays.asList(appOrderName, appVendor));
		if (hMMPHandle.RecordDBCount() > 0)
		{
			List<String> recordList = hMMPHandle.getDBRecordList("id");
			if (recordList != null)
			{
				for (int iRow = 0; iRow < recordList.size(); iRow++)
				{
					if (appDelivDate != null&&!appDelivDate.isEmpty()&&appDelivDate.length() == 8)
					{
						hMMPHandle.UpdateRecordByKeyList("date_of_delivery", appDelivDate, Arrays.asList("id"), Arrays.asList(recordList.get(iRow)));
					}
					else
					{
						rtnRst += "error:交货日期填写有误!$";
						break;
					}
				}
			}
		}
		else
		{
			rtnRst += "error:po单不存在!";
		}
	}
	else
	{
		rtnRst += "error:po单需要填写交货日期!";
	}
	out.write(rtnRst);
%>
