<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po_Record" %>
<%@ page import="com.DB.operation.Mb_Material_Po"%>
<%@ page import="com.DB.operation.Product_Storage"%>
<%@ page import="com.DB.operation.Material_Storage"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String appPOName = (String)request.getParameter("PO_Name").replace(" ", "");
	String appVendor = (String)request.getParameter("vendor").replace(" ", "");
	String appDelivDate = (String)request.getParameter("Delivery_Date").replace(" ", "");
	if (appPOName != null && appDelivDate.length() == 8)
	{
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "vendor"), Arrays.asList(appPOName, appVendor));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] colNames = {"Bar_Code", "vendor", "QTY", "percent"};
			List<List<String>> recordList = new ArrayList<List<String>>();
			for(int idx=0; idx < colNames.length; idx++)
			{
				recordList.add(hCPRHandle.getDBRecordList(colNames[idx]));
			}
			if (recordList.size() <= 0)
			{
				Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
				Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
				Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
				for (int iRow = 0; iRow < recordList.get(0).size(); iRow++)
				{
					String strBarcode = recordList.get(0).get(iRow);
					String strMaterialBarcode = hCPRHandle.GetUsedBarcode(strBarcode, "mb_material_po");
					String strVendor = recordList.get(1).get(iRow);
					int iPOCount = Integer.parseInt(recordList.get(2).get(iRow));
					int ipercent = Integer.parseInt(recordList.get(3).get(iRow));
					int iRepertory = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strMaterialBarcode)) + 
							hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
					int manufacture_QTY = iPOCount*(100+ipercent)/100;
					if (iRepertory < manufacture_QTY)
					{
						hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "vendor", "po_name"), Arrays.asList(strMaterialBarcode, strVendor, appPOName));
						if (hMMPHandle.RecordDBCount() <= 0)
						{
							if (appDelivDate != null&&!appDelivDate.isEmpty()&&appDelivDate.length() == 8)
							{
								hMMPHandle.AddARecord(strMaterialBarcode, strVendor, appPOName, manufacture_QTY-iRepertory, appDelivDate);
							}
							else
							{
								rtnRst += "error:交货日期填写有误!$";
								break;
							}
						}
					}
				}
			}
		}
		else
		{
			rtnRst += "error:po单不存在!";
		}
	}
	else
	{
		rtnRst += "error:po单需要填写交货日期!";
	}
	out.write(rtnRst);
%>
