<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Material_PO_Item_Ajax" %>
<%
    String rtnRst = "remove$";
    String po_name = request.getParameter("po_name").replace(" ", "");
    String[] displayList = {"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "数量", "状态", "操作"};
    if(po_name.length() > 6)
    {
        Query_Material_PO_Item_Ajax hPageHandle = new Query_Material_PO_Item_Ajax();
        
        List<List<String>> recordList = hPageHandle.GetAllStorageRecord(po_name);
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
                int iPurchaseCount = 0, iFinishCount = 0;
                //{"Bar_Code", "po_name", "date_of_delivery", "vendor_name",  "IN_QTY"};
                String strBarcode = recordList.get(0).get(iRow);
                for(int iCol = 0; iCol < iColCount; iCol++)
                {
                    if("ID" == displayList[iCol])
                    {
                        rtnRst += Integer.toString(iRow+1) + "$";
                    }
                    else if("产品类型" == displayList[iCol])
                    {
                        String protype = hPageHandle.GetProductInfoByBarcode(strBarcode, "product_type");
                        rtnRst += protype + "$";
                    }
                    else if("产品名称" == displayList[iCol])
                    {
                        String proname = hPageHandle.GetProductInfoByBarcode(strBarcode, "name");
                        rtnRst += proname + "$";
                    }
                    else if("供应商" == displayList[iCol])
                    {
                        rtnRst += recordList.get(3).get(iRow) + "$";
                    }
                    else if("八码" == displayList[iCol])
                    {
                        rtnRst += strBarcode + "$";
                    }
                    else if("PO单名" == displayList[iCol])
                    {
                        rtnRst += recordList.get(1).get(iRow) + "$";
                    }
                    else if("交货时间" == displayList[iCol])
                    {
                        rtnRst += recordList.get(2).get(iRow) + "$";
                    }
                    else if ("数量" == displayList[iCol])
                    {
                        rtnRst += recordList.get(4).get(iRow) + "$";
                    }
                    else if ("状态" == displayList[iCol])
                    {
                        rtnRst += recordList.get(4).get(iRow) + "$";
                    }
                    else if ("操作" == displayList[iCol])
                    {
                        rtnRst += recordList.get(4).get(iRow) + "$";
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
