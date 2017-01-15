<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String pro_type=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	
	Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
	List<List<String>> proInfo = hPageHandle.GetProductInfoByProType(pro_type, Arrays.asList("name", "Bar_Code"));
	
	if (proInfo != null&&proInfo.get(0).size()>0)
	{
		for(int i = 0; i < proInfo.size(); i++)
		{
			for(int j = 0; j < proInfo.get(i).size(); j++)
				rtnRst += proInfo.get(i).get(j) + '$';
		}
	}
	out.write(rtnRst);
%>
