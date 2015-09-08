<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%@ page import="com.Warcraft.SupportUnit.SubmitDiscardMaterial" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	SubmitDiscardMaterial hProcessor = new SubmitDiscardMaterial();
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
		String appOrderName = request.getParameter("product_order");
		String appBarcode = request.getParameter("bar_code");
		String appProduct_QTY = request.getParameter("QTY");
		String appreason = request.getParameter("discard_reason");
		int used_count = Integer.parseInt(appProduct_QTY);
		//product_type Database query
		if (used_count > 0&&appBarcode.length() == 8&&appOrderName.indexOf("请选择") < 0&&appreason != ""&&appreason != null)
		{
			if (hProcessor.CheckPOStatus(appBarcode, appOrderName) < used_count)
			{
				session.setAttribute("error", "("+ appBarcode + "): 你这报废的也太狠了吧, 加上报废数量都比客户生产单数量大了!");
				response.sendRedirect("../tishi.jsp");
			}
			int repertory_count = hDBHandle.GetRepertoryByBarCode(appBarcode, "material_storage");
			if (repertory_count >= used_count)
			{
				String sql = "select * from material_storage where Bar_Code='" + hDBHandle.GetUsedBarcode(appBarcode, "material_storage") +"'";
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
							sql= "INSERT INTO discard_material_record (Order_Name, Bar_Code, BatchLot, QTY, reason) VALUE ('" + appOrderName + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "discard_material_record") + "', '" + batchLot + "', '" + appProduct_QTY + "', '" + appreason +"')";
							hDBHandle.execUpate(sql);
							response.sendRedirect("../Discard_Material.jsp");
							break;
						}
						else
						{
							sql= "INSERT INTO discard_material_record (Order_Name, Bar_Code, BatchLot, QTY, reason) VALUE ('" + appOrderName + "', '" + hDBHandle.GetUsedBarcode(appBarcode, "discard_material_record") + "', '" + batchLot + "', '"  + recordCount + "', '" + appreason +"')";
							hDBHandle.execUpate(sql);
						}
					}
				}
				else
				{
					hDBHandle.CloseDatabase();
				}
			}
			else
			{
				session.setAttribute("error", "("+ appBarcode + "): 库存数量不足,都不够你报废的!");
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
