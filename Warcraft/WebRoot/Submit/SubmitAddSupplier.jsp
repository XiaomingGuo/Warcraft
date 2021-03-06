<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.DB.operation.Vendor_Info" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String proposerName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appStorename = request.getParameter("store_name_addproduct").replace(" ", "");
		String appSupplier = request.getParameter("suppliername").replace(" ", "");
		String appFaxinfo = request.getParameter("faxinfo").replace(" ", "");
		String appTelinfo = request.getParameter("telinfo").replace(" ", "");
		String appMailaddress = request.getParameter("mailaddress").replace(" ", "");
		String appAddress = request.getParameter("address").replace(" ", "");
		String description = request.getParameter("description").replace(" ", "");
		
		//product_type Database query
		if (!appStorename.isEmpty() && !appSupplier.isEmpty() && !appFaxinfo.isEmpty() && !appTelinfo.isEmpty() && !appMailaddress.isEmpty() && !appAddress.isEmpty() && !description.isEmpty())
		{
			DBTableParent hVIHandle = new DatabaseStore("Vendor_Info");
			hVIHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor_name", "storeroom"), Arrays.asList(appSupplier, appStorename));
			if (hVIHandle.getTableInstance().RecordDBCount() <= 0)
			{
				((Vendor_Info)hVIHandle.getTableInstance()).AddARecord(appSupplier, appStorename, appFaxinfo, appTelinfo, appMailaddress, appAddress, description);
				response.sendRedirect("../AddSupplier.jsp");
			}
			else
			{
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
