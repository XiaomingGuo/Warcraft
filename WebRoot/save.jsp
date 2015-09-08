<%@ page language="java" import="java.util.* ,java.io.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="com.office.core.ExcelManagment" %>
<%@ page import="com.office.operation.ExcelRead" %>
<%@ page import="com.DB.DatabaseConn" %>
<%!DatabaseConn hDBHandle = new DatabaseConn();%>
<%
	request.setCharacterEncoding("UTF-8");
	String filePath = request.getParameter("filePath");
	String fileName = request.getParameter("fileName");
	ExcelManagment excelUtil = new ExcelManagment(new ExcelRead(filePath, fileName));
	int[] startCell = {2,1}, endCell = {261,6};
	List<List<String>> res = excelUtil.execReadExcelBlock("Sheet1", startCell, endCell);
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
			String sql = "select * from product_info where Bar_Code='" + Barcode +"'";
			if(hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
			{
				hDBHandle.CloseDatabase();
				sql = "UPDATE product_info SET name='" + product_name + "', product_type='" + product_type + "', weight='" + weight + "', description='" + description + "' where Bar_Code='" + Barcode +"'";
				hDBHandle.execUpate(sql);
			}
			else
			{
				sql = "select * from storeroom_name where name='" + storename +"'";
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
	}
	response.setCharacterEncoding("utf-8");
	String fileFullPath = filePath + fileName;
	fileFullPath = new String(fileFullPath.getBytes("iso-8859-1"));
	SmartUpload su = new SmartUpload(); // 新建一个smartupload对象 	
	su.initialize(pageContext); 		// 初始化准备操作  

	// 设定contentdisposition为null以禁止浏览器自动打开文件， 
	//保证点击链接后是下载文件。若不设定，则下载的文件扩展名为 
	//doc时，浏览器将自动用word打开它。扩展名为pdf时， 
	//浏览器将用acrobat打开。 
	su.setContentDisposition(null);
	 
	su.downloadFile(fileFullPath); // 下载文件
	out.clear();
	out=pageContext.pushBody();
	out.println("<script>alert('创建成功！');window.location.href = 'index.jsp';</script>");
%>