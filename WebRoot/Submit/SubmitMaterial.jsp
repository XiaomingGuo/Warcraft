<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<%@ page import="com.jsp.support.SubmitMaterial" %>
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
		request.setCharacterEncoding("UTF-8");
		String appInStoreDate = request.getParameter("SubmitDate").replace("-", "");
		String appStore_name = request.getParameter("store_name_addproduct").replace(" ", "");
		String appSupplier_name = request.getParameter("supplier_name").replace(" ", "");
		String appProduct_type = request.getParameter("product_type").replace(" ", "");
		String appProductname = request.getParameter("productname").replace(" ", "");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appPriceUnit = request.getParameter("PriceUnit").replace(" ", "");
		String appWeightUnit = request.getParameter("WeightUnit").replace(" ", "");
		String appProductQTY = request.getParameter("QTY").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		String storageName="other_storage";
		SubmitMaterial hPageHandle = new SubmitMaterial();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		if (appSupplier_name.indexOf("请选择") < 0 && !appStore_name.isEmpty() && !appProduct_type.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty() && !appProductQTY.isEmpty() && !appPriceUnit.isEmpty() && !appWeightUnit.isEmpty())
		{
			if(appStore_name.indexOf("原材料库") >= 0)
			{
				if(Integer.parseInt(appBarcode) < 50000000||Integer.parseInt(appBarcode) > 60000000)
				{
					session.setAttribute("error", "弄啥呢?原材料八码必须介于[50000000 ~ 60000000)之间, 你不知道吗?");
					response.sendRedirect("../tishi.jsp");
					return;
				}
				storageName = "material_storage";
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					//product_type Database query
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("原锭", ""),
							appWeightUnit, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type,
							appWeightUnit, appDescription);
				}
			}
			else
			{
				if(Integer.parseInt(appBarcode) >= 50000000&&Integer.parseInt(appBarcode) < 70000000)
				{
					session.setAttribute("error", "弄啥呢?介于[50000000 ~ 69999999]之间的八码已经被原材料库和成品库占用了, 你不知道吗?");
					response.sendRedirect("../tishi.jsp");
					return;
				}
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "other_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "other_storage"), appProductname, appProduct_type, appWeightUnit, appDescription);
				}
			}
			String appTotalPrice = String.format("%.2f", Float.parseFloat(appPriceUnit)*Float.parseFloat(appProductQTY));
			String batch_lot = hPageHandle.GenBatchLot(storageName, appBarcode);
			hPageHandle.AddARecordToStorage(storageName, appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, appSupplier_name, appInStoreDate);
		}
		else
		{
			session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
			response.sendRedirect("../tishi.jsp");
			return;
		}
		response.sendRedirect("../AddMaterial.jsp");
	}
%>
