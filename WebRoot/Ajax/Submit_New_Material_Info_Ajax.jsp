<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.jsp.support.Submit_Material_Ajax" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
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
		String appStore_name = request.getParameter("store_name_addproduct").replace(" ", "");
		String appProduct_type = request.getParameter("product_type").replace(" ", "");
		String appProductname = request.getParameter("productname").replace(" ", "");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appWeightUnit = request.getParameter("WeightUnit").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		String storageName="other_storage";
		Submit_Material_Ajax hPageHandle = new Submit_Material_Ajax();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		
		if (!appStore_name.isEmpty() && !appProduct_type.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty() && !appWeightUnit.isEmpty())
		{
			if(appStore_name.indexOf("原材料库") >= 0)
			{
				String productWeight = request.getParameter("ProductWeight").replace(" ", "");
				if(Integer.parseInt(appBarcode) < 50000000||Integer.parseInt(appBarcode) >= 60000000)
				{
					rtnRst += "error:弄啥呢?原材料八码必须介于[50000000 ~ 60000000)之间, 你不知道吗?";
					out.write(rtnRst);
					return;
				}
				storageName = "material_storage";
				appProduct_type = appProduct_type.contains("原锭")?appProduct_type:appProduct_type+"原锭";
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					//product_type Database query
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("原锭", ""),
							productWeight, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type,
							appWeightUnit, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type.replace("原锭", "半成品"),
							"0", appDescription);
				}
			}
			else if(appStore_name.indexOf("半成品库") >= 0)
			{
				String productWeight = request.getParameter("ProductWeight").replace(" ", "");
				if(Integer.parseInt(appBarcode) < 70000000||Integer.parseInt(appBarcode) >= 80000000)
				{
					rtnRst += "error:弄啥呢?半成品八码必须介于[70000000 ~ 80000000)之间, 你不知道吗?";
					out.write(rtnRst);
					return;
				}
				storageName = "semi_pro_storage";
				appProduct_type = appProduct_type.contains("半成品")?appProduct_type:appProduct_type+"半成品";
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "semi_pro_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					//product_type Database query
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("半成品", ""),
							productWeight, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type.replace("半成品", "原锭"),
							appWeightUnit, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type,
							"0", appDescription);
				}
			}
			else if(appStore_name.indexOf("成品库") >= 0)
			{
				String productWeight = request.getParameter("ProductWeight").replace(" ", "");
				if(Integer.parseInt(appBarcode) < 60000000||Integer.parseInt(appBarcode) >= 70000000)
				{
					rtnRst += "error:弄啥呢?半成品八码必须介于[60000000 ~ 70000000)之间, 你不知道吗?";
					out.write(rtnRst);
					return;
				}
				storageName = "product_storage";
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					//product_type Database query
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type,
							productWeight, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type + "原锭",
							appWeightUnit, appDescription);
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type + "半成品",
							"0", appDescription);
				}
			}
			else
			{
				if(Integer.parseInt(appBarcode) >= 50000000&&Integer.parseInt(appBarcode) < 70000000)
				{
					rtnRst += "error:弄啥呢?介于[50000000 ~ 69999999]之间的八码已经被原材料库和成品库占用了, 你不知道吗?";
					out.write(rtnRst);
					return;
				}
				hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(appBarcode, "other_storage"));
				if (hPIHandle.RecordDBCount() <= 0)
				{
					hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "other_storage"), appProductname, appProduct_type, appWeightUnit, appDescription);
				}
			}
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
