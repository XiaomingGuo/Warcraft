<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SaveMBPoItemAjax" %>
<%@ page import="com.jspsmart.upload.*" %>
<%
	String rtnRst = "remove$";
	request.setCharacterEncoding("UTF-8");
	String appPOName = (String)request.getParameter("POName");
	String vendor = (String)request.getParameter("vendor");
	String delivery_Date = (String)request.getParameter("delivery_Date");
	String filePath = "D:\\MB_File_Share\\", fileName = "MBond_Po_Template.xls";
	if (appPOName != null && appPOName.length() > 5)
	{
		SaveMBPoItemAjax hPageSupport = new SaveMBPoItemAjax(filePath, fileName);
		hPageSupport.SaveToExcelByPoName(appPOName, vendor, delivery_Date);
	}
	else
	{
		rtnRst += "error:po单不完整!";
	}
	
	/*response.setCharacterEncoding("utf-8");
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
	out=pageContext.pushBody();Generate_PO.jsp?PO_Name=1180022&vendor=新航铸造&Delivery_Date=20151130*/
	out.println("<script>alert('下载成功！');window.location.href = \"../Generate_PO.jsp?PO_Name="+appPOName+"&vendor="+vendor+"&Delivery_Date="+delivery_Date+"\";</script>");	
%>
