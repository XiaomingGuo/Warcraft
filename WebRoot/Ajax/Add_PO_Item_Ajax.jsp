<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Add_PO_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	Add_PO_Item_Ajax hPageHandle = new Add_PO_Item_Ajax();
	
	String barcode = hPageHandle.CheckRequestPara((String)request.getParameter("bar_code"));
	String deliv_date = hPageHandle.CheckRequestPara((String)request.getParameter("delivery_date"));
	String cpo_qty = hPageHandle.CheckRequestPara((String)request.getParameter("cpo_QTY"));
	String vendorname = hPageHandle.CheckRequestPara((String)request.getParameter("vendor_name"));
	String poname = hPageHandle.CheckRequestPara((String)request.getParameter("po_name"));
	String percent = hPageHandle.CheckRequestPara((String)request.getParameter("percent"));
	
	if (hPageHandle.CheckParamValidityMoreThanLength(poname, 6))
	{
		if (!hPageHandle.IsCustomerPoClose(poname))
		{
			if(!hPageHandle.NewARecordInCustomerPoRecord(barcode, poname, deliv_date, cpo_qty, vendorname, percent))
				rtnRst += "error:大哥这输入的参数好像有问题,麻烦检查下重新输入一下吧？";
		}
		else
			rtnRst += "error:大哥这生产单已经有了,换个生产单名吧!";
	}
	else
		rtnRst += "error:PO单号忒短了点儿!";
	out.write(rtnRst);
%>
