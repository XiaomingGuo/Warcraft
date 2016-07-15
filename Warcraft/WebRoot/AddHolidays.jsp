<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PageParentClass" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    PageParentClass hPageHandle = new PageParentClass();
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
            message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            String currentDate = hPageHandle.GenYearMonthString("-");
            String endDayOfMonth = currentDate+hPageHandle.GetEndDayOfMonth(currentDate.replaceAll("-", ""));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>节假日及转班</title>
    
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
    <script language="javascript" src="Page_JS/ArrangeCheckInTimeJS.js"></script>
    <script language="javascript" src="dojojs/dojo.js"></script>
  <body onload="OnloadDisplay()">
    <script type="text/javascript">
        dojo.require("dojo.widget.*");
    </script>
    <jsp:include page="Menu/PersonnelMenu.jsp"/>
    <br><br>
    <table align="center" width="40%">
        <tr>
            <td align="right" width="40%">
                <h2><label>日期:</label></h2>
            </td>
            <td align="left" width="60%">
                <div dojoType="dropdowndatepicker" name="AddDate" id="AddDate" displayFormat="yyyyMMdd" value="<%=currentDate+"01"%>"></div>
            </td>
        </tr>
    </table>
    <table id="display_info" border='1' align="center"></table>
    <br>
    <table id="check_in_list" border='1' align="center"></table>
    <br>
    <table align="center">
        <tr>
            <td><input name="commit" type="button" value="提交" style="width:100" onclick="SubmitAddHolidaysDate()"></td>
        </tr>
    </table>
  </body>
</html>
<%
        }
    }
%>