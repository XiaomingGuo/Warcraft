<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] displayKeyList = {"名称", "模具号", "型号", "数量", "单位", "交货日期", "备注"};
	List<List<String>> recordList = null, vendorInfo = null;
%>
<%
	String message="";
	String POName = request.getParameter("PO_Name").replace(" ", "");
	String vendor = request.getParameter("vendor").replace(" ", "");
	String delivery_Date = request.getParameter("Delivery_Date").replace(" ", "");
	
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&1024;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			String sql = "select * from mb_material_po where po_name='" + POName + "' and vendor='" + vendor + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				String[] sqlKeyList = {"Bar_Code", "PO_QTY", "date_of_delivery"};
				recordList = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			sql = "select * from vendor_info where vendor_name='" + vendor +  "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
			{
				String[] sqlKeyList = {"vendor_fax", "vendor_tel", "vendor_e_mail", "vendor_address"};
				vendorInfo = hDBHandle.GetAllDBColumnsByList(sqlKeyList);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
			Calendar mData = Calendar.getInstance();
			String currentDate = String.format("%04d%02d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1, mData.get(Calendar.DAY_OF_MONTH));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <center>
       	<br><br>
       	<table>
    		<tr>
				<td width="60%" align="center"><img src="IMAGE/Logo.png" align="middle"><font size="5"><b>常州市茂邦机械有限公司</b></font></td>
			</tr>
    		<tr>
				<td width="60%" align="center"><font size="5"><b>采购单</b></font></td>
			</tr>
    	</table>
    	<br><br>
       	<table width="80%">
    		<tr>
				<td width="50%" align="left"><b>供货商/SHIP TO: </b></td>
				<td width="50%" align="left"><b>购买者/VENDOR: </b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>姓名/ATTN NAME: <%=vendor %></b></td>
				<td width="50%" align="left"><b>姓名/NAME:  茂邦机械</b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>传真/FAX: <%=vendorInfo.get(0).get(0) %></b></td>
				<td width="50%" align="left"><b>传真/FAX: 85850265</b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>电话/TEL: <%=vendorInfo.get(1).get(0) %></b></td>
				<td width="50%" align="left"><b>电话/TEL: 85850265</b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>邮箱/MAIL: <%=vendorInfo.get(2).get(0) %></b></td>
				<td width="50%" align="left"><b>邮箱/MAIL: </b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>地址/ADD: <%=vendorInfo.get(3).get(0) %></b></td>
				<td width="50%" align="left"><b>地址/ADD: 常州市武进区</b></td>
			</tr>
    		<tr>
				<td width="50%" align="left"><b>日期/DATE: <%=currentDate %></b></td>
			</tr>
    	</table>
    	<br><br><br><br>
    	<table width="80%" border="1">
    		<tr>
<%
			for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
			{
%>
	   			<th><%= displayKeyList[iCol-1]%></th>
<%
			}
%>
    		</tr>
 
<%
			if (!recordList.isEmpty())
			{ 
				for(int iRow = 1; iRow <= recordList.get(0).size(); iRow++)
				{
%>
  			<tr>
<%
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						String strBarcode = recordList.get(0).get(iRow-1);
						
				    	if (displayKeyList[iCol-1] == "模具号" || displayKeyList[iCol-1] == "备注" )
				    	{
%>
    			<td width="20%"><input type="text"></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "交货日期")
				    	{
%>
    			<td width="2%"><input type="text" value="<%=recordList.get(2).get(iRow-1) %>" readonly></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "单位")
				    	{
%>
    			<td width="2%"><input type="text" value="件" readonly></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "名称")
				    	{
%>
    			<td width="2%"><input type="text" value="<%=hDBHandle.GetTypeByBarcode(strBarcode)%>" readonly></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "型号")
				    	{
%>
    			<td width="2%"><input type="text" value="<%=hDBHandle.GetNameByBarcode(strBarcode)%>" readonly></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "数量")
				    	{
%>
    			<td width="15%"><input type="text" value=<%=recordList.get(1).get(iRow-1) %> readonly></td>
<%
				    	}
				    	else
				    	{
%>
    			<td width="8%"><input type="text" value=<%=recordList.get(iCol-1).get(iRow-1) %> readonly></td>
<%
						}
    				}
%>
			</tr>
<%
				}
			}
%>
    	</table>
    	<br><br>
    	<h3>另:请按交货期日期之前送至我公司，逾期将按比例扣款,请知悉!!!<br>以前的订单将全部取消,请以此份订单为准!</h3>
    	<table width="80%">
	    	<tr>
	    		<td>签字确认并回传:</td>
	    		<td>采购: </td>
	    		<td>审批:</td>
	    	</tr>
	    	<tr>
	    		<td>日期:</td>
	    		<td>日期: </td>
	    		<td>日期:</td>
	    	</tr>
    	</table>
    	<br><br>
    	<h3>注:接到订单之后，务必认真核对确认,再以电话或者传真方式回复.所有供货件将需符合铸件（灰铸铁）国标GB9439-2010.务必在交货日期之前把货全部供清。<br></h3>
    </center>
  </body>
</html>
<%
		}
	}
%>