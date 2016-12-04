<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jspsmart.upload.*"  %>
<%@ page import="com.office.core.ExcelManagment"  %>
<%@ page import="com.office.operation.ExcelCreate"  %>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			request.setCharacterEncoding("UTF-8");
			String storage_name = request.getParameter("store_name");
			String product_type = request.getParameter("product_type");
			String product_name = request.getParameter("product_name");
			String bar_code = request.getParameter("bar_code");
			QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
			String tempString = hPageHandle.GenerateResponseString(storage_name, product_type, product_name, bar_code);
			String[] splitList = tempString.split("\\$");
			
			List<List<String>> recordList = new ArrayList<List<String>>();
			for(int iRow = 0; iRow < Integer.parseInt(splitList[1]) + 1; iRow++)
			{
				List<String> tempList = new ArrayList<String>();
				for(int iCol = 2; iCol < Integer.parseInt(splitList[0]) + 2; iCol++)
					tempList.add(splitList[iRow*Integer.parseInt(splitList[0])+iCol]);
				recordList.add(tempList);
			}
			ExcelManagment excelUtil = new ExcelManagment(new ExcelCreate("d:\\tempFolder", "tempExcel.xls"));
			excelUtil.execWriteExcelBlock(recordList, 3);
			String fileFullPath = "d:\\tempFolder\\tempExcel.xls";
			fileFullPath = new String(fileFullPath.getBytes("iso-8859-1"));
			SmartUpload su = new SmartUpload(); // 新建一个smartupload对象 	
			su.initialize(pageContext); 		// 初始化准备操作  
			
			su.setContentDisposition(null);
			su.downloadFile(fileFullPath); // 下载文件
			out.clear();
			out=pageContext.pushBody();
		}
		out.println("<script>alert('下载成功！');window.location.href = '../OtherStoreMenu/OtherSummary.jsp';</script>");	
	}
%>