<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Mb_Material_Po"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Generate_MB_PO_Ajax" %>
<%
	String rtnRst = "remove$";
	String POName = (String)request.getParameter("PO_Name").replace(" ", "");
	String appVendor = (String)request.getParameter("vendor").replace(" ", "");
	String appDelivDate = (String)request.getParameter("Delivery_Date").replace(" ", "");
	if (POName != null && appDelivDate.length() == 8)
	{
		Generate_MB_PO_Ajax hPageHandle = new Generate_MB_PO_Ajax();
		List<List<String>> recordList = hPageHandle.GetCustomerPoRecordList(POName, appVendor);
		if (recordList.size() > 0)
		{
			Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
			for (int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				String strBarcode = recordList.get(0).get(iRow);
				String strVendor = recordList.get(1).get(iRow);
				int iRepertory = 0;
				if(!POName.contains("MB_"))
					iRepertory = hPageHandle.GetHasFinishPurchaseNum(strBarcode, POName);
				int manufacture_QTY = hPageHandle.CalcOrderQty(recordList.get(2).get(iRow), recordList.get(3).get(iRow));
				
				if (iRepertory < manufacture_QTY)
				{
					hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "vendor", "po_name"), Arrays.asList(strBarcode, strVendor, POName));
					if (hMMPHandle.RecordDBCount() <= 0)
					{
						if (appDelivDate != null&&!appDelivDate.isEmpty()&&appDelivDate.length() == 8)
						{
							hMMPHandle.AddARecord(strBarcode, strVendor, POName, manufacture_QTY-iRepertory, appDelivDate);
						}
						else
						{
							rtnRst += "error:交货日期填写有误!$";
							break;
						}
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
