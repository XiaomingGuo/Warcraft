<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
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
				String sql = "select * from product_info where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "product_info") + "'";
				if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					//product_type Database query
					sql = "INSERT INTO product_info (name, Bar_Code, product_type, weight, description) VALUES ('" + appProductname + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "product_info") + "', '" + appProduct_type.replace("原锭", "") + "', '" + appWeightUnit + "', '" + appDescription + "')";
					hDBHandle.execUpate(sql);
					sql = "INSERT INTO product_info (name, Bar_Code, product_type, weight, description) VALUES ('" + appProductname + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "material_info") + "', '" + appProduct_type + "', '" + appWeightUnit + "', '" + appDescription + "')";
					hDBHandle.execUpate(sql);
				}
				else
				{
					hDBHandle.CloseDatabase();
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

				String sql = "select * from product_info where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "other_info") + "'";
				if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					//product_type Database query
					sql = "INSERT INTO product_info (name, Bar_Code, product_type, weight, description) VALUES ('" + appProductname + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "other_info") + "', '" + appProduct_type + "', '" + appWeightUnit + "', '" + appDescription + "')";
					hDBHandle.execUpate(sql);
				}
				else
				{
					hDBHandle.CloseDatabase();
				}
			}
			String appTotalPrice = String.format("%.2f", Float.parseFloat(appPriceUnit)*Float.parseFloat(appProductQTY));
			Calendar mData = Calendar.getInstance();
			String batch_lot_Head = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			int loopNum = 1;
			do
			{
				String batch_lot = batch_lot_Head + "-" + String.format("%02d", loopNum);
 				String sql = "select * from "+storageName+" where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, storageName) + "' and Batch_Lot='" + batch_lot + "' UNION select * from exhausted_" + storageName.split("_")[0] + " where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, storageName) + "' and Batch_Lot='" + batch_lot + "'";
				if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					//product_type Database query
					sql = "INSERT INTO "+storageName+" (Bar_Code, Batch_Lot, IN_QTY, Price_Per_Unit, Total_Price, vendor_name, in_store_date) VALUES ('" + hDBHandle.GetUsedBarcode(appBarcode, storageName) + "', '" + batch_lot + "', '" + appProductQTY+ "', '" + appPriceUnit+ "', '" + appTotalPrice + "', '" + appSupplier_name + "', '" + appInStoreDate + "')";
					hDBHandle.execUpate(sql);
					break;
				}
				else
				{
					hDBHandle.CloseDatabase();
				}
				loopNum ++;
			}
			while(true);
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
