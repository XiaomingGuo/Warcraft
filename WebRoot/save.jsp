<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.office.util.ExcelOperationUtil"  %>    
<%	
	request.setCharacterEncoding("UTF-8");
	String filePath = request.getParameter("filePath");
	String fileName = request.getParameter("fileName");
	ExcelOperationUtil excelUtil = new ExcelOperationUtil();  
	boolean res = excelUtil.CreateExcelFile(filePath,fileName);
	if(res)
		out.println("<script>alert('创建成功！');window.location.href = 'index.jsp';</script>");	
 %>