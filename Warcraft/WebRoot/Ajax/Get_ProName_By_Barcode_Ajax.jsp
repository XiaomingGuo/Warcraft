<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code").replace(" ", "");
	Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
	if(barcode.length() == 8)
	{
		List<String> recordList = hPageHandle.GetProNameAndTypeAndStorageName(barcode);
		if(recordList.size() > 0)
		{
			for(int idx=0; idx < recordList.size(); idx++)
				rtnRst += recordList.get(idx) + "$";
		}
		else
			rtnRst += "error:这个八码不存在?";
	}
	else
		rtnRst += "error:八码有八位你不知道吗?";
	out.write(rtnRst);
%>
