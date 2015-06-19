<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String userName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appBarcode = request.getParameter("bar_code");
		String appProduct_QTY = request.getParameter("QTY");
		int used_count = Integer.parseInt(appProduct_QTY);
		//product_type Database query
		if (used_count > 0 && appBarcode.length()==8)
		{
			int repertory_count = hDBHandle.GetRepertoryByBarCode(appBarcode, "product_storage");
			if (repertory_count >= used_count)
			{
				String sql = "select * from product_storage where Bar_Code='" + appBarcode +"'";
				if (hDBHandle.QueryDataBase(sql))
				{
					List<List<String>> material_info_List = hDBHandle.GetAllDBColumnsByList(keyArray);
					for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
					{
						String batchLot =  material_info_List.get(0).get(iCol);
						int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
						int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
						int recordCount = sql_in_count - sql_out_count;
						if (recordCount >= used_count)
						{
							sql= "UPDATE product_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + appBarcode +"' and Batch_Lot='" + batchLot +"'";
							hDBHandle.execUpate(sql);
							if (recordCount == used_count)
							{
								//hDBHandle.MoveToExhaustedTable(appBarcode, batchLot, "material_storage", "exhausted_material");
							}
							break;
						}
						else
						{
							sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_in_count) + "' WHERE Bar_Code='" + appBarcode +"' and Batch_Lot='" + batchLot +"'";
							hDBHandle.execUpate(sql);
							//hDBHandle.MoveToExhaustedTable(appBarcode, batchLot, "material_storage", "exhausted_material");
						}
					}
					response.sendRedirect("../Product_Shipment.jsp");
				}
				else
				{
					hDBHandle.CloseDatabase();
				}
			}
			else
			{
				session.setAttribute("error", "("+ appBarcode + "): 库存数量不足,不够出货数量,加油生产吧兄弟!");
				response.sendRedirect("../tishi.jsp");
			}			
		}
		else
		{
			session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
			response.sendRedirect("../tishi.jsp");
		}
	}
%>
