<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jspsmart.upload.*"  %>
<%@ page import="com.office.core.ExcelManagment"  %>
<%@ page import="com.office.operation.ExcelCreate"  %>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
		response.sendRedirect("tishi.jsp");
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
			String[] displayKeyList = {"ID", "姓名", "工号", "漏打卡次数", "迟到早退(分)", "2小时加班(小时)", "4小时加班(小时)", "周末加班(天)", "总加班(小时)", "年假(天)", "事假(天)", "查询时间范围"};
			
			List<List<String>> recordList = new ArrayList<List<String>>();
			List<String> headList = new ArrayList<String>();
			for (int iHead = 0; iHead < displayKeyList.length; iHead++)
				headList.add(displayKeyList[iHead]);
			recordList.add(headList);
			
			for(int iRow = 1; ;iRow++)
			{
				if(null == request.getParameter(Integer.toString(iRow)+"-1"))
					break;
				List<String> tempList = new ArrayList<String>();
				for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					tempList.add(request.getParameter(Integer.toString(iRow)+"-"+Integer.toString(iCol)));
				recordList.add(tempList);
			}
			ExcelManagment excelUtil = new ExcelManagment(new ExcelCreate("d:\\tempFolder", "CheckInSummary.xls"));
			excelUtil.execWriteExcelBlock(recordList, 10);
			String fileFullPath = "d:\\tempFolder\\CheckInSummary.xls";
			fileFullPath = new String(fileFullPath.getBytes("iso-8859-1"));
			SmartUpload su = new SmartUpload(); // 新建一个smartupload对象 	
			su.initialize(pageContext); 		// 初始化准备操作  
			
			su.setContentDisposition(null);
			su.downloadFile(fileFullPath); // 下载文件
			out.clear();
			out=pageContext.pushBody();
		}
		out.println("<script>alert('下载成功！');window.location.href = '../PersonMenu/SummarizeCheckInTime.jsp';</script>");	
	}
%>