<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Get_ProductNameType_By_Order_Ajax" %>
<%
	String rtnRst = "remove$";
	String proOrder = request.getParameter("ProductOrder").replace(" ", "");
	String storageName = request.getParameter("Storage_Name").replace(" ", "");
	if(proOrder.length() > 0&&storageName.length() > 0)
	{
		Get_ProductNameType_By_Order_Ajax hPageHandle = new Get_ProductNameType_By_Order_Ajax();
		List<List<String>> proInfoList = hPageHandle.GetProNameByPoName(proOrder, storageName);
		if(proInfoList.size() > 0)
		{
			for(int idx=0; idx < proInfoList.get(0).size(); idx++)
			{
				rtnRst += proInfoList.get(0).get(idx) + "$";
				rtnRst += proInfoList.get(1).get(idx) + "$";
			}
		}
		else
		{
			rtnRst += "error:这个订单不存在不存在?";
		}
	}
	else
	{
		rtnRst += "error:订单名和查询库名不正确?";
	}
	out.write(rtnRst);
%>
