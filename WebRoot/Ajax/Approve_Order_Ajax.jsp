<%@page import="org.apache.struts2.components.Else"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		String sql= "select * from product_order_record where Order_Name='"+order_name+"'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			String[] keyList = {"id", "Bar_Code", "QTY"};
			List<List<String>> recordList = hDBHandle.GetAllDBColumnsByList(keyList);
			boolean isApprove = true;
			if (recordList != null)
			{
				List<String> sqlList = new ArrayList<String>();
				for (int index = 0; index < recordList.get(0).size(); index++)
				{
					if (hDBHandle.GetRepertoryByBarCode(recordList.get(1).get(index), "material_storage") >= Integer.parseInt(recordList.get(2).get(index)))
					{
						sqlList.add("UPDATE product_order_record SET status=1 where id='" + recordList.get(0).get(index) + "'");
					}
					else
					{
						rtnRst += "error:原材料库存不足不能生产!$";
						isApprove = false;
						break;
					}
				}
				if (isApprove)
				{
					for(int idx = 0; idx < sqlList.size(); idx++)
					{
						hDBHandle.execUpate(sqlList.get(idx));
					}
					sql = "UPDATE product_order SET status=1 where Order_Name='" + order_name + "'";
					hDBHandle.execUpate(sql);
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	out.write(rtnRst);
%>
