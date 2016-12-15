<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Submit_Material_Ajax" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String rtnRst = "remove$";
	if(session.getAttribute("logonuser")==null)
	{
		rtnRst += "error:未登陆";
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appProductQTY = request.getParameter("QTY").replace(" ", "");
		String appPriceUnit = request.getParameter("PriceUnit").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		String appSupplier_name = request.getParameter("supplier_name").replace(" ", "");
		String appInStoreDate = request.getParameter("SubmitDate").replace("-", "");
		Submit_Material_Ajax hPageHandle = new Submit_Material_Ajax();
		
		if (appSupplier_name.indexOf("请选择") < 0 && !appBarcode.isEmpty() && !appProductQTY.isEmpty() && !appPriceUnit.isEmpty())
		{
			if(!hPageHandle.CheckBarcodeStatus(appBarcode))
			{
				String appTotalPrice = String.format("%.4f", Float.parseFloat(appPriceUnit)*Float.parseFloat(appProductQTY));
				String batch_lot = hPageHandle.GenBatchLot(appBarcode);
				hPageHandle.AddARecordToStorage(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, appSupplier_name, appInStoreDate, appDescription);
			}
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
