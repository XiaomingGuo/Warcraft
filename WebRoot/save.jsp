<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.office.util.ExcelOperationUtil"  %>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%	
	request.setCharacterEncoding("UTF-8");
	String filePath = request.getParameter("filePath");
	String fileName = request.getParameter("fileName");
	ExcelOperationUtil excelUtil = new ExcelOperationUtil();
	int[] startCell = {2,1}, endCell = {260,6};
	List<List<String>> res = excelUtil.ReadExcel(filePath, fileName, "Sheet1", startCell, endCell);
	if(res.size() > 0)
	{
		for(int iRow = 0; iRow < res.size(); iRow++)
		{
			String storename = res.get(iRow).get(0);
			String product_type = res.get(iRow).get(1);
			String product_name = res.get(iRow).get(2);
			String Barcode = res.get(iRow).get(3);
			String weight = res.get(iRow).get(4);
			String description = res.get(iRow).get(5);
			String sql = "select * from storeroom_name where name='" + storename +"'";
			if(hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO storeroom_name (name) VALUES ('" + storename + "')";
				hDBHandle.execUpate(sql);
			}
			
			sql = "select * from product_type where name='" + product_type +"' and storeroom='" + storename + "'";
			if(hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO product_type (name, storeroom) VALUES ('" + product_type + "', '"+ storename + "')";
				hDBHandle.execUpate(sql);
			}
			
			sql = "select * from product_info where Bar_Code='" + Barcode +"' and name='" + product_name + "' and product_type='" + product_type + "'";
			if(hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO product_info (Bar_Code, name, product_type, weight, description) VALUES ('" + Barcode + "', '"+ product_name + "', '"+ product_type + "', '"+ weight + "', '"+ description + "')";
				hDBHandle.execUpate(sql);
			}
			
		}
	}
	out.println("<script>alert('创建成功！');window.location.href = 'index.jsp';</script>");	
 %>