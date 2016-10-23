<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	String POName = request.getParameter("PO_Name").replace(" ", "");
	String ship_no = request.getParameter("shipping_no").replace(" ", "");
	String[] displayKeyList = {"行号", "品名规格", "单位", "数量", "单重", "总重", "备注"};
	List<List<String>> vendorInfo = null;
	
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
			response.sendRedirect("tishi.jsp");
		}
		else
		{
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			DBTableParent hSRHandle = new DatabaseStore("Shipping_Record");
			DBTableParent hPIHandle = new DatabaseStore("Product_Info");
			hSRHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("customer_po", "shipping_no"), Arrays.asList(POName, ship_no), Arrays.asList("Bar_Code"));
			List<List<String>> recordList = new ArrayList<List<String>>();
			if (hSRHandle.getTableInstance().RecordDBCount() > 0)
			{
				String[] sqlKeyList = {"Bar_Code", "ship_QTY"};
				for(int idx = 0; idx < sqlKeyList.length; idx++)
				{
					recordList.add(hSRHandle.getDBRecordList(sqlKeyList[idx]));
				}
			}
			Calendar mData = Calendar.getInstance();
			String currentDate = String.format("%04d-%02d-%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1, mData.get(Calendar.DAY_OF_MONTH));
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
       	<table width="80%">
    		<tr>
				<td align="center"><font size="5"><b>常州市茂邦机械有限公司</b></font></td>
			</tr>
    		<tr>
				<td align="left"><font size="3">地址:武进高新技术开发区龙潜路97号</font></td>
			</tr>
    		<tr>
				<td align="left"><font size="3">联系方式:FAX:0519-85850265 TEL:0519-85850255</font></td>
			</tr>
    		<tr>
				<td align="center"><font size="5"><b>销售单</b></font></td>
			</tr>
		</table>
		<table width="80%">
    		<tr>
				<td width="50%" align="left"><font size="3">客户: 国茂减速机集团有限公司                     日期:<%=currentDate %></font></td>
				<td width="50%" align="right"><font size="3">NO.		MOB<%=ship_no %>		</font></td>
			</tr>
    	</table>
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
					String strBarcode = recordList.get(0).get(iRow-1);
					hSRHandle.QueryRecordByFilterKeyList(Arrays.asList("customer_po", "Bar_Code", "shipping_no"), Arrays.asList(POName, strBarcode, ship_no));
					hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
					double dblPerPrice = Double.parseDouble(hPIHandle.getDBRecordList("weight").get(0));
					double dblTotalPrice = dblPerPrice * Double.parseDouble(recordList.get(1).get(iRow-1));
					for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
					{
						//{"行号", "品名规格", "单位", "数量", "单重", "总重", "备注"};
				    	if (displayKeyList[iCol-1] == "行号")
				    	{
%>
    			<td align="center" width="5%"><%=iRow %></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "品名规格")
				    	{
%>
    			<td><%=hPIHandle.getDBRecordList("name").get(0) %></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "单位")
				    	{
%>
    			<td align="center" width="5%">根</td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "数量")
				    	{
%>
    			<td align="right" width="10%"><%=hSRHandle.GetIntSumOfValue("ship_QTY", Arrays.asList("customer_po", "Bar_Code", "shipping_no"), Arrays.asList(POName, strBarcode, ship_no)) %></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "单重")
				    	{
%>
    			<td align="right" width="10%"><%=dblPerPrice %></td>
<%
				    	}
				    	else if(displayKeyList[iCol-1] == "总重")
				    	{
%>
    			<td align="right" width="10%"><%=dblTotalPrice %></td>
<%
				    	}
				    	else
				    	{
%>
    			<td width="10%"><input width="100%" type="text"></td>
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
		<table width="80%">
    		<tr>
				<td width="100%" align="left"><font size="3">说明: 白色联: 财务    红色联: 客户</font></td>
			</tr>
    	</table>
    	<br>
    	<table width="80%">
	    	<tr>
	    		<td>制单人: <%=mylogon.getUsername() %></td>
	    		<td>送货人:<input type="text"></td>
	    		<td>签收人:</td>
	    	</tr>
    	</table>
    </center>
  </body>
</html>
<%
		}
	}
%>