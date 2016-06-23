<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Submit_New_Material_Info_Ajax" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String rtnRst = "remove$";
	Submit_New_Material_Info_Ajax hPageHandle = new Submit_New_Material_Info_Ajax();
	if(session.getAttribute("logonuser")==null)
	{
		rtnRst += "error:未登陆";
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String appStore_name = request.getParameter("store_name").replace(" ", "");
		String appProduct_type = request.getParameter("product_type").replace(" ", "");
		String appProductname = request.getParameter("productname").replace(" ", "");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appWeightUnit = request.getParameter("WeightUnit").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		
		if (!appStore_name.isEmpty() && !appProduct_type.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty() && !appWeightUnit.isEmpty())
		{
			if (hPageHandle.CheckBarcodeStatus(appBarcode))
			{
				String productWeight = request.getParameter("ProductWeight").replace(" ", "");
				rtnRst += hPageHandle.AddProductInfoRecord(appStore_name, appBarcode, appProduct_type, appProductname, appWeightUnit, productWeight, appDescription);
			}
			else
			{
				rtnRst += "error:弄啥呢?这八码不是已经有了吗,换一个吧?";
			}
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
