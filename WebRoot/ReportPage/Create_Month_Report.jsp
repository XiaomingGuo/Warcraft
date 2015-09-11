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
		String splitFlag=(String)request.getParameter("OrderItemSelect");
		String beginDate=(String)request.getParameter("DateOfBegin");
		String endDate=(String)request.getParameter("DateOfEnd");

		int temp = mylogon.getUserRight()&64;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			//product_info Database query
			List<List<String>> recordList = null;
			String[] displayKeyList = {"ID", "名称", "八码", "批号", "申请人", "数量", "使用者", "价值", "申请日期"};
			String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
			String sql = "select * from other_record where create_date>'" + beginDate + "' and create_date<'" + endDate + "' and isApprove=1";
			if (hDBHandle.QueryDataBase(sql))
			{
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			List<List<String>> writeList = new ArrayList<List<String>>();
			List<String> headList = new ArrayList<String>();
			for (int iHead=0; iHead < displayKeyList.length; iHead++)
			{
				headList.add(displayKeyList[iHead]);
			}
			writeList.add(headList);
			if (null != recordList)
			{
				for(int iRow = 0; iRow < recordList.get(0).size();iRow++)
				{
					List<String> tempList = new ArrayList<String>();
					for(int iCol = 0; iCol < displayKeyList.length; iCol++)
					{
						//{"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "create_date", "isApprove"};
						//{"ID", "名称", "八码", "批号", "申请人", "数量", "使用者", "申请日期", "价值"};
						if("ID" == displayKeyList[iCol])
						{
							tempList.add(Integer.toString(iRow+1));
						}
						else if("名称" == displayKeyList[iCol])
						{
							tempList.add(hDBHandle.GetNameByBarcode(recordList.get(0).get(iRow)));
						}
						else if("八码" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(0).get(iRow));
						}
						else if("批号" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(1).get(iRow));
						}
						else if("申请人" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(2).get(iRow));
						}
						else if("数量" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(3).get(iRow));
						}
						else if("使用者" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(4).get(iRow));
						}
						else if("申请日期" == displayKeyList[iCol])
						{
							tempList.add(recordList.get(5).get(iRow));
						}
						else if("价值" == displayKeyList[iCol])
						{
							double perPrice = hDBHandle.GetPrice_Pre_Unit("other", recordList.get(0).get(iRow), recordList.get(1).get(iRow));
							double totalPrice = perPrice * Integer.parseInt(recordList.get(3).get(iRow));
							tempList.add(Double.toString(totalPrice));
						}
					}
					writeList.add(tempList);
				}
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
		out.println("<script>alert('下载成功！');window.location.href = \"../MonthReport.jsp?BeginDate="+beginDate+"&EndDate="+endDate+"\";</script>");	
	}
%>