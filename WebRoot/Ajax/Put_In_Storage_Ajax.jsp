<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String barcode = null, ordername = null;
%>
<%
	String rtnRst = "remove$";
	String pro_id = request.getParameter("product_id");
	String QTYOfStore = request.getParameter("PutInQTY");
	
	if (pro_id != null && QTYOfStore != null)
	{
		int used_count = Integer.parseInt(QTYOfStore);
		String sql = "select * from product_order_record where id='" + pro_id + "'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			String[] orderRecordKey = {"Bar_Code", "Order_Name", "QTY", "completeQTY"};
			List<List<String>> orderInfo = hDBHandle.GetAllDBColumnsByList(orderRecordKey);
			barcode = orderInfo.get(0).get(0);
			ordername = orderInfo.get(1).get(0);
			int order_QTY = Integer.parseInt(orderInfo.get(2).get(0));
			int pro_record_comp_QTY = Integer.parseInt(orderInfo.get(3).get(0));
			if (order_QTY == (pro_record_comp_QTY+used_count))
			{
				sql= "UPDATE product_order_record SET completeQTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='3' WHERE id='" + pro_id + "'";
			}
			else
			{
				sql= "UPDATE product_order_record SET completeQTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='1' WHERE id='" + pro_id + "'";
			}
			hDBHandle.execUpate(sql);
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	
	out.write(rtnRst);
%>
