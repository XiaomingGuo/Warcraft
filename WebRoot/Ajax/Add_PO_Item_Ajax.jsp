<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po" %>
<%@ page import="com.DB.operation.Customer_Po_Record"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String barcode = (String)request.getParameter("bar_code").replace(" ", "");
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
			if (barcode != null&&deliv_date != null&&cpo_qty != null&&vendorname != null)
			{
				Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Product_Storage"), poname));
				int recordCount = hCPRHandle.RecordDBCount();
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Semi_Pro_Storage"), poname));
				recordCount += hCPRHandle.RecordDBCount();
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Material_Storage"), poname));
				recordCount += hCPRHandle.RecordDBCount();
				if (recordCount <= 0)
				{
					hCPRHandle.AddARecord(barcode, poname, deliv_date, cpo_qty, vendorname, percent);
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
