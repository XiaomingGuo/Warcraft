<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String bar_code = (String)request.getParameter("bar_code").replace(" ", "");
	String deliv_date = (String)request.getParameter("delivery_date").replace(" ", "");
	String cpo_qty = (String)request.getParameter("cpo_QTY").replace(" ", "");
	String vendorname = (String)request.getParameter("vendor_name").replace(" ", "");
	String poname = (String)request.getParameter("po_name").replace(" ", "");
	String percent = (String)request.getParameter("percent").replace(" ", "");
	
	if (poname != null&&!poname.isEmpty())
	{
		String sql = "select * from customer_po where po_name='" + poname + "' and status > 0";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
		{
			hDBHandle.CloseDatabase();
			if (bar_code != null&&deliv_date != null&&cpo_qty != null&&vendorname != null)
			{
				sql = "select * from customer_po_record where Bar_Code='" + hDBHandle.GetUsedBarcode(bar_code, "customer_po_record") + "' and po_name='" + poname + "'";
				if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
				{
					sql = "INSERT INTO customer_po_record (Bar_Code, po_name, delivery_date, QTY, vendor, percent) VALUES ('" + hDBHandle.GetUsedBarcode(bar_code, "customer_po_record") + "','" + poname + "','" + deliv_date +"','" + cpo_qty + "','" + vendorname +"','" + percent + "')";
					hDBHandle.execUpate(sql);
				}
				else
				{
					hDBHandle.CloseDatabase();
					rtnRst += "error:大哥这产品已经有了,要不删掉重新输入!";
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
			rtnRst += "error:大哥这生产单已经有了,换个生产单名吧!";
		}
	}
	out.write(rtnRst);
%>
