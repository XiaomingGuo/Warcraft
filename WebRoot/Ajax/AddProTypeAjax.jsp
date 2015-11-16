<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom").replace(" ", "");
	String pro_type = (String)request.getParameter("pro_type").replace(" ", "");
	Product_Type hSNHandle = new Product_Type(new EarthquakeManagement());
	hSNHandle.GetRecordByName(pro_type);
	if (!storeroom.isEmpty()&&!pro_type.isEmpty())
	{
		if(hSNHandle.RecordDBCount() > 0)
		{
			rtnRst = "产品类型已经存在!";
		}
		else
		{
			if(storeroom.indexOf("原材料库") == 0)
			{
				hSNHandle.AddARecord(pro_type, "成品库");
				hSNHandle.AddARecord(pro_type+ "原锭", storeroom);
			}
			else
				hSNHandle.AddARecord(pro_type, storeroom);
		}
	}
	else
	{
		rtnRst = "产品类型或库名为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
