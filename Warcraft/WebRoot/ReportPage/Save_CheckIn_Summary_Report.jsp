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
			String[] displayKeyList = {"ID", "产品名称", "八码", "产品类型", "进货数量", "出库数量", "库存", "总价值"};
			String storage_name = request.getParameter("11");
			String product_type = request.getParameter("12");
			String product_name = request.getParameter("13");
			String bar_code = request.getParameter("bar_code");
			QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
			List<String> bar_code_List = new ArrayList<String>();
			if (storage_name.indexOf("请选择") < 0 && product_type.indexOf("请选择") >= 0 &&product_name.indexOf("请选择") >= 0 )
			{
				List<String> pro_Type_List = hPageHandle.QueryProTypeStorage(storage_name);
				for(int idx = 0; idx < pro_Type_List.size(); idx++)
				{
					List<String> tempList = hPageHandle.QueryProNameByProType(pro_Type_List.get(idx));
					for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
						bar_code_List.add(hPageHandle.QueryBarCodeByProName(tempList.get(iRecordIdx)));
				}
			}
			else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") >= 0)
			{
				List<String> tempList = hPageHandle.QueryProNameByProType(product_type);
				for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
					bar_code_List.add(hPageHandle.QueryBarCodeByProName(tempList.get(iRecordIdx)));
			}
			else if(storage_name.indexOf("请选择") < 0&&product_type.indexOf("请选择") < 0&&product_name.indexOf("请选择") < 0)
				bar_code_List.add(bar_code);
			List<String> storageRecord = hPageHandle.GetAllRecordByBarCodeList(bar_code_List);
			
			List<List<String>> recordList = new ArrayList<List<String>>();
			List<String> headList = new ArrayList<String>();
			for (int iHead = 0; iHead < displayKeyList.length; iHead++)
				headList.add(displayKeyList[iHead]);
			recordList.add(headList);
			for(int iRow = 0; iRow < storageRecord.size()/displayKeyList.length;iRow++)
			{
				List<String> tempList = new ArrayList<String>();
				for(int iCol = 0; iCol < displayKeyList.length; iCol++)
					tempList.add(storageRecord.get(iRow*displayKeyList.length+iCol));
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
		out.println("<script>alert('下载成功！');window.location.href = '../OtherSummary.jsp';</script>");	
	}
%>