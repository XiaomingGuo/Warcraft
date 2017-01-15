<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOther" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    QueryOther hPageHandle = new QueryOther();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("../tishi.jsp");
    }
    else
    {
        message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String[] displayKeyList = hPageHandle.GetDisplayArray();
		String submitDate = request.getParameter("SubmitDate");
        String curDate = submitDate==null?hPageHandle.GenYearMonthDayString("-"):submitDate;
        List<String> vendorList = hPageHandle.GetAllVendorName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>五金录入状态</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
      <script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
      <script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
      <script language="javascript" src="Page_JS/OtherStoreMenuJS/QueryOtherJS.js"></script>
	<script language="javascript" src="dojojs/dojo.js"></script>
  <body onload="OnloadDisplay('<%=curDate%>')">
	<script type="text/javascript">
		dojo.require("dojo.widget.*");
	</script>
    <jsp:include page="../Menu/MainMenu.jsp"/>
    <br>
	<table align="center">
		<tr>
			<td align="center">
				<label>交货时间:</label>
    			<div dojoType="dropdowndatepicker" name="SubmitDate" id="SubmitDate" displayFormat="yyyy-MM-dd" value="<%=curDate %>"></div>
			</td>
			<td align="center">
				<input type="button" value="查询" style='width:100px' onclick="QueryOtherStoreRecord()">
			</td>
		</tr>
    </table>
    <br>
    <table id="modify_info" border="1" align="center">
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
        <tr>
<%
            for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
            {
                if("操作" == displayKeyList[iCol-1])
                {
%>
        <td>
            <center><input type="button" value="确认" onclick="ExecModify()"></center>
        </td>
<%
                }
                else if("八码" == displayKeyList[iCol-1])
                {
%>
        <td><input name="barcode" id="barcode" type="text" style="width:100" onblur="InputBarcode()"></td>
<%
                }
                else if("供应商" == displayKeyList[iCol-1])
                {
%>
        <td>
            <select name="Vendor" id="Vendor" style="width:120px">
                <option value = "--请选择--">--请选择--</option>
<%
                    for(int i = 0; i < vendorList.size(); i++)
                    {
%>
                <option value = <%=vendorList.get(i) %>><%=vendorList.get(i)%></option>
<%
                    }
%>
            </select>
        </td>
<%
                }
                else
                {
%>
        <td>...</td>
<%
                }
            }
%>
        </tr>
    </table>
    <br>
    <table id="display_info" align="center" border="1"></table>
    <br><br>
  </body>
</html>
<%
    }
%>