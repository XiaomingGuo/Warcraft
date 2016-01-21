<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po" %>
<%@ page import="com.DB.operation.Customer_Po_Record"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
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
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByPoNameAndMoreThanStatus(poname, "0");
		if (hCPHandle.RecordDBCount() <= 0)
		{
			if (bar_code != null&&deliv_date != null&&cpo_qty != null&&vendorname != null)
			{
				Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(bar_code, "customer_po_record"), poname));
				if (hCPRHandle.RecordDBCount() <= 0)
				{
					hCPRHandle.AddARecord(hCPRHandle.GetUsedBarcode(bar_code, "customer_po_record"), poname, deliv_date, cpo_qty, vendorname, percent);
				}
				else
				{
					rtnRst += "error:大哥这产品已经有了,要不删掉重新输入!";
				}
			}
		}
		else
		{
			rtnRst += "error:大哥这生产单已经有了,换个生产单名吧!";
		}
	}
	out.write(rtnRst);
%>
