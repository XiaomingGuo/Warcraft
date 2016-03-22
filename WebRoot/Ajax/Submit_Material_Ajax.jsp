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
		String appStore_name = request.getParameter("store_name_addproduct").replace(" ", "");
		String appProductname = request.getParameter("productname").replace(" ", "");
		String appBarcode = request.getParameter("barcode").replace(" ", "");
		String appProductQTY = request.getParameter("QTY").replace(" ", "");
		String appWeightUnit = request.getParameter("WeightUnit").replace(" ", "");
		String appPriceUnit = request.getParameter("PriceUnit").replace(" ", "");
		String appDescription = request.getParameter("Description").replace(" ", "");
		String appSupplier_name = request.getParameter("supplier_name").replace(" ", "");
		String appInStoreDate = request.getParameter("SubmitDate").replace("-", "");
		Submit_Material_Ajax hPageHandle = new Submit_Material_Ajax();
		
		if (appSupplier_name.indexOf("请选择") < 0 && !appStore_name.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty() && !appProductQTY.isEmpty() && !appPriceUnit.isEmpty() && !appWeightUnit.isEmpty())
		{
			if(hPageHandle.CheckBarcodeStatus(appBarcode))
			{
				if(appStore_name.indexOf("原材料库") >= 0)
				{
					if(!hPageHandle.IsMaterialBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?原材料八码必须介于[50000000 ~ 60000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
				}
				else if(appStore_name.indexOf("半成品库") >= 0)
				{
					if(!hPageHandle.IsSemiProBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?半成品八码必须介于[70000000 ~ 80000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
				}
				else if(appStore_name.indexOf("成品库") >= 0)
				{
					if(!hPageHandle.IsProductBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?半成品八码必须介于[60000000 ~ 70000000)之间, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
				}
				else
				{
					if(!hPageHandle.IsOtherBarcode(appBarcode))
					{
						rtnRst += "error:弄啥呢?介于[50000000 ~ 69999999]之间的八码已经被原材料库和成品库占用了, 你不知道吗?";
						out.write(rtnRst);
						return;
					}
				}
				String appTotalPrice = String.format("%.4f", Float.parseFloat(appPriceUnit)*Float.parseFloat(appProductQTY));
				String batch_lot = hPageHandle.GenBatchLot(appBarcode);
				hPageHandle.AddARecordToStorage(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, appSupplier_name, appInStoreDate);
			}
			else
			{
				rtnRst += "error:你输入八码已经存在了,还一个吧好吗?";
			}
		}
		else
		{
			rtnRst += "error:你输入的是什么啊,赶紧重新输入!";
		}
		out.write(rtnRst);
	}
%>
