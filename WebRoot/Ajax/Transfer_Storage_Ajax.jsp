<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Mb_Material_Po"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Transfer_Storage_Ajax" %>
<%
	String rtnRst = "remove$";
	String POName = (String)request.getParameter("PO_Name").replace(" ", "");
	if (POName != null)
	{
		Transfer_Storage_Ajax hPageHandle = new Transfer_Storage_Ajax();
		List<List<String>> recordList = hPageHandle.GetCustomerPoRecordList(POName);
		//{"Bar_Code", "QTY", "percent"};
		if (recordList.size() > 0)
		{
			for (int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				String strBarcode = recordList.get(0).get(iRow);
				int manufacture_QTY = hPageHandle.CalcOrderQty(recordList.get(1).get(iRow), recordList.get(2).get(iRow));
				hPageHandle.UpdateStoragePoName(strBarcode, POName, manufacture_QTY);
				hPageHandle.EnsureCustomerPoRecordInput(POName);
			}
		}
		else
			rtnRst += "error:po单不存在!";
	}
	else
		rtnRst += "error:po单需要填写交货日期!";
	out.write(rtnRst);
%>
