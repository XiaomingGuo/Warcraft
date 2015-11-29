<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SaveMBPoItemAjax" %>
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="com.office.core.ExcelManagment" %>
<%@ page import="com.office.operation.ExcelRead" %>
<%
	String rtnRst = "remove$";
	String appPOName = (String)request.getParameter("poName");
	String filePath = "", fileName = "";
	if (appPOName != null && appPOName.length() > 5)
	{
		ExcelManagment excelUtil = new ExcelManagment(new ExcelRead(filePath, fileName));
		SaveMBPoItemAjax hPageSupport = new SaveMBPoItemAjax();
		hPageSupport.SaveToExcel(appPOName);
	}
	else
	{
		rtnRst += "error:po单不完整!";
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
