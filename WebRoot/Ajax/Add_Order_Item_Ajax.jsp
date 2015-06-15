<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String pro_type = (String)request.getParameter("product_type");
	String pro_name = (String)request.getParameter("product_name");
	String bar_code = (String)request.getParameter("bar_code");
	String deliv_date = (String)request.getParameter("delivery_date");
	String pro_qty = (String)request.getParameter("order_QTY");
	String precent = (String)request.getParameter("present");
	String order_name = (String)request.getParameter("order_name");
	
	if (pro_type != null&&pro_name != null&&bar_code != null&&deliv_date != null&&pro_qty != null&&precent != null)
	{
		String sql = "INSERT INTO product_order (product_type, product_name, Bar_Code, delivery_date, QTY, precent, Order_Name) VALUES ('" + pro_type + "','" + pro_name + "','" + bar_code + "','" + deliv_date +"','" + pro_qty + "','" + precent + "','" + order_name + "')";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
