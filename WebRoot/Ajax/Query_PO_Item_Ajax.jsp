<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Material_Storage"%>
<%@ page import="com.DB.operation.Product_Storage"%>
<%@ page import="com.DB.operation.Semi_Product_Storage"%>
<%@ page import="com.DB.operation.Product_Info"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Query_PO_Item_Ajax" %>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name").replace(" ", "");
	String status = request.getParameter("status");
	String po_status = null;
	String[] displayList = {"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "数量", "成品库存", "半成品库存", "物料库存", "采购余量", "进货余量", "创建时间", "操作"};
	if(po_name.length() > 6)
	{
		Query_PO_Item_Ajax hPageHandle = new Query_PO_Item_Ajax();
		if (status != null)
		{
			po_status = hPageHandle.GetCustomerPoStatus(po_name);
			if (po_status != null)
			{
				if (Integer.parseInt(po_status) > Integer.parseInt(status))
				{
					rtnRst += "error:该PO单已经存在!";
					out.write(rtnRst);
				}
			}
		}
		rtnRst += po_status + "$";
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		
		List<List<String>> recordList = hPageHandle.GetCustomerPoRecordList(po_name);
		if (recordList.size() > 0)
		{
			int iRowCount = recordList.get(0).size(), iColCount = displayList.length;
			rtnRst += Integer.toString(iColCount) + "$";
			rtnRst += Integer.toString(iRowCount) + "$";
			for(int i = 0; i < iColCount; i++)
			{
				rtnRst += displayList[i] + "$";
			}
			for(int iRow = 0; iRow < iRowCount; iRow++)
			{
				int iPro_storage = 0, iSemiPro_storage = 0, iMat_storage = 0;
				String strBarcode = recordList.get(2).get(iRow);
				hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				for(int iCol = 0; iCol < iColCount; iCol++)
				{
					if("ID" == displayList[iCol])
					{
						rtnRst += Integer.toString(iRow+1) + "$";
					}
					else if("产品类型" == displayList[iCol])
					{
						String protype = hPIHandle.getDBRecordList("product_type").get(0);
						rtnRst += protype + "$";
					}
					else if("产品名称" == displayList[iCol])
					{
						String proname = hPIHandle.getDBRecordList("name").get(0);
						rtnRst += proname + "$";
					}
					else if("成品库存" == displayList[iCol])
					{
						iPro_storage = hPageHandle.GetProductRepertory(strBarcode, po_name);
						rtnRst += Integer.toString(iPro_storage)  + "$";
					}
					else if("半成品库存" == displayList[iCol])
					{
						String curBarcode = hSPSHandle.GetUsedBarcode(strBarcode, "semi_storage");
						iSemiPro_storage = hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(curBarcode, "Material_Supply", "1")) + 
								hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(curBarcode, po_name, "1"));
						rtnRst += Integer.toString(iSemiPro_storage)  + "$";
					}
					else if ("物料库存" == displayList[iCol])
					{
						String curBarcode = hMSHandle.GetUsedBarcode(strBarcode, "material_storage");
						iMat_storage = hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(curBarcode, "Material_Supply", "1")) + 
								hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(curBarcode, po_name, "1"));
						rtnRst += Integer.toString(iMat_storage) + "$";
					}
					else if ("采购余量" == displayList[iCol])
					{
						int poCount = hPageHandle.GetSurplusPurchaseQty(strBarcode, po_name);
						if (poCount <= 0)
						{
							int iAllPoCount = hPageHandle.CalcOrderQty(recordList.get(5).get(iRow), recordList.get(6).get(iRow));
							rtnRst += Math.abs(iPro_storage+iSemiPro_storage+iMat_storage-iAllPoCount) + "$";
						}
						else
						{
							rtnRst += poCount + "$";
						}
					}
					else if ("进货余量" == displayList[iCol])
					{
						rtnRst += recordList.get(6).get(iRow) + "$";
					}
					else if ("操作" == displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "#" + recordList.get(7).get(iRow) + "$";
					}
					else if ("创建时间" == displayList[iCol])
					{
						rtnRst += recordList.get(8).get(iRow) + "$";
					}
					else
					{
						rtnRst += recordList.get(iCol-2).get(iRow) + "$";
					}
				}
			}
		}
	}
	else
	{
		rtnRst += "error:产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
