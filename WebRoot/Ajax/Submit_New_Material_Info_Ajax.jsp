<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
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
		String appStore_name = request.getParameter("store_name_addproduct").replace(" ", "");
		String appProduct_type = request.getParameter("product_type").replace(" ", "");
		String appProductname = request.getParameter("productname").replace(" ", "");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appWeightUnit = request.getParameter("WeightUnit").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		if (!appStore_name.isEmpty() && !appProduct_type.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty() && !appWeightUnit.isEmpty())
		{
			if (hPageHandle.CheckBarcodeStatus(appBarcode))
			{
				String productWeight = request.getParameter("ProductWeight").replace(" ", "");
				if(appStore_name.indexOf("原材料库") >= 0)
				{
					if(!hPageHandle.IsMaterialBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?原材料八码必须介于[50000000 ~ 60000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
					appProduct_type = appProduct_type.contains("原锭")?appProduct_type:appProduct_type+"原锭";
					hPageHandle.AddNewProductInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
				}
				else if(appStore_name.indexOf("半成品库") >= 0)
				{
					if(!hPageHandle.IsSemiProBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?半成品八码必须介于[70000000 ~ 80000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
					appProduct_type = appProduct_type.contains("半成品")?appProduct_type.replace("半成品", "原锭"):appProduct_type+"原锭";
					hPageHandle.AddNewProductInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
				}
				else if(appStore_name.indexOf("成品库") >= 0)
				{
					if(!hPageHandle.IsProductBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?成品八码必须介于[60000000 ~ 70000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
					appProduct_type = appProduct_type.contains("原锭")?appProduct_type:appProduct_type+"原锭";
					hPageHandle.AddNewProductInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
				}
				else
				{
					if(!hPageHandle.IsOtherBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?其他库不能使用[50000000 ~ 79999999]之间的八码, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
					hPageHandle.AddNewOtherInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
				}
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
