<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%@ page import="com.jsp.support.PageParentClass" %>
<%@ page import="com.jspsmart.upload.*"  %>
<%@ page import="com.office.core.ExcelManagment"  %>
<%@ page import="com.office.operation.ExcelCreate"  %>
<%@ page import="com.jsp.support.MonthReport" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("../tishi.jsp");
	}
	else
	{
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		request.setCharacterEncoding("UTF-8");
		String splitFlag=(String)request.getParameter("OrderItemSelect").replace(" ", "");
		String storage_name=(String)request.getParameter("store_name").replace(" ", "");
		String product_type=(String)request.getParameter("product_type").replace(" ", "");
		String product_name=(String)request.getParameter("product_name").replace(" ", "");
		String user_name=(String)request.getParameter("user_name").replace(" ", "");
		String beginDate=(String)request.getParameter("BeginDate").replace(" ", "");
		String endDate=(String)request.getParameter("EndDate").replace(" ", "");
		
		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			MonthReport hPageHandle = new MonthReport();
			String rtnRst = hPageHandle.GenerateResponseString(storage_name, product_type, product_name, user_name, beginDate.replace("-", ""), endDate.replace("-", ""));
			
			String[] tempArray = rtnRst.split("\\$");
			int iColCount = Integer.parseInt(tempArray[0]), iRowCount = Integer.parseInt(tempArray[1]) - 1;
			
			List<List<String>> writeList = new ArrayList<List<String>>();
			for(int iRow = 0; iRow < iRowCount + 1; iRow++)
			{
				List<String> tempList = new ArrayList<String>();
				for(int iCol = 2; iCol < iColCount + 2; iCol++)
				{
					tempList.add(tempArray[iRow*iColCount + iCol]);
				}
				writeList.add(tempList);
			}
			ExcelManagment excelUtil = new ExcelManagment(new ExcelCreate("d:\\tempFolder", "MonthReport.xls"));
			excelUtil.execWriteExcelBlock(writeList, Integer.parseInt(splitFlag));
			String fileFullPath = "d:\\tempFolder\\MonthReport.xls";
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
		out.println("<script>alert('下载成功！');window.location.href = \"../OtherStoreMenu/MFGToolsMonthReport.jsp?BeginDate="+beginDate+"&EndDate="+endDate+"\";</script>");	
	}
%>
