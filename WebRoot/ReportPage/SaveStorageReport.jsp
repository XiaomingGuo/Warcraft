<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%@ page import="com.jspsmart.upload.*"  %>
<%@ page import="com.office.core.ExcelManagment"  %>
<%@ page import="com.office.operation.ExcelCreate"  %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!DatabaseConn hDBHandle = new DatabaseConn();%>
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
			String[] displayKeyList = {"ID", "八码", "名称", "规格", "批号", "进货数量", "消耗数量", "单价", "总价", "供应商"};
		
			List<List<String>> recordList = new ArrayList<List<String>>();
			List<String> headList = new ArrayList<String>();
			for (int iHead = 0; iHead < displayKeyList.length; iHead++)
			{
				headList.add(displayKeyList[iHead]);
			}
			recordList.add(headList);
			for(int iRow = 0; ;iRow++)
			{
				List<String> tempList = new ArrayList<String>();
				String breakFlag = request.getParameter(Integer.toString(iRow*10)).replace(" ", "");
				if (breakFlag == null)
				{
					break;
				}
				for(int iCol = 0; iCol < displayKeyList.length;iCol++)
				{
					String tempValue = request.getParameter(Integer.toString(iRow*10+iCol)).replace(" ", "");
					tempList.add(tempValue);
				}
				recordList.add(tempList);
			}
			ExcelManagment excelUtil = new ExcelManagment(new ExcelCreate("d:\\tempFolder", "tempExcel.xls"));
			excelUtil.execWriteExcelBlock(recordList, 9);
			String fileFullPath = "d:\\tempFolder\\tempExcel.xls";
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
		}
		out.println("<script>alert('下载成功！');window.location.href = '../Storeroom_Summary.jsp';</script>");	

		//response.sendRedirect("../Storeroom_Summary.jsp");
	}
%>