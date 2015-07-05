<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String proposerName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appStorename = request.getParameter("store_name_addproduct");
		String appSupplier = request.getParameter("suppliername");
		String appFaxinfo = request.getParameter("faxinfo");
		String appTelinfo = request.getParameter("telinfo");
		String appMailaddress = request.getParameter("mailaddress");
		String appAddress = request.getParameter("address");
		String description = request.getParameter("description");
		
		//product_type Database query
		if (!appStorename.isEmpty() && !appSupplier.isEmpty() && !appFaxinfo.isEmpty() && !appTelinfo.isEmpty() && !appMailaddress.isEmpty() && !appAddress.isEmpty() && !description.isEmpty())
		{
			String sql = "select * from vendor_info where vendor_name='" + appSupplier +"' and storeroom='" + appStorename + "'";
			if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO vendor_info (vendor_name, storeroom, vendor_fax, vendor_tel, vendor_e_mail, vendor_address, description) VALUES ('" + appSupplier + "', '" + appStorename + "', '" + appFaxinfo + "', '" + appTelinfo + "', '" + appMailaddress +"', '" + appAddress +"', '" + description + "')";
				hDBHandle.execUpate(sql);
				response.sendRedirect("../AddSupplier.jsp");
			}
			else
			{
				hDBHandle.CloseDatabase();
				session.setAttribute("error", "供应商已存在或数据库查询出错!");
				response.sendRedirect("../tishi.jsp");
			}
		}
		else
		{
			session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
			response.sendRedirect("../tishi.jsp");
		}
}
%>
