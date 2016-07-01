<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PersonnelInfo" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    PersonnelInfo hPageHandle = new PersonnelInfo();
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
            String curUserName = request.getParameter("userName");
            if(null == curUserName)
                curUserName = "";
            List<String> UserList = hPageHandle.GetAllUserName();
            UserList.remove("root");
            String currentDate = hPageHandle.GenYearMonthDayString("-");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>人员考勤查询</title>
    
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
    <script language="javascript" src="Page_JS/PersonnelInfoJS.js"></script>
    <script language="javascript" src="dojojs/dojo.js"></script>
  <body onload="changePOName()">
    <script type="text/javascript">
        dojo.require("dojo.widget.*");
    </script>
    <jsp:include page="Menu/PersonnelMenu.jsp"/>
    <br>
    <table align="center">
        <tr>
            <td>
                <table align="center">
                    <tr>
                        <td align="right">
                            <h1>
                                <label>姓名:</label>
                                <select name="UserName" id="UserName" style="width:200px">
                                    <option value = "--请选择--">--请选择--</option>
<%
                                    for(int i = 0; i < UserList.size(); i++)
                                    {
                                        if(curUserName.contains(UserList.get(i)))
                                        {
%>
                                    <option value = <%=UserList.get(i) %> selected><%=UserList.get(i)%></option>
<%
                                        }
                                        else
                                        {
%>
                                    <option value = <%=UserList.get(i) %>><%=UserList.get(i)%></option>
<%                                            
                                        }
                                    }
%>
                                </select>
                            </h1>
                        </td>
                        <td align="center">
                            <h1>
                            <label>考勤月:</label>
                            <div dojoType="dropdowndatepicker" name="SubmitDate" id="SubmitDate" displayFormat="yyyyMM" value="<%=currentDate %>"></div>
                            </h1>
                        </td>
                        <td align="right"><h1><input name="query" type="button" value="查询" style="width:100" onclick="changeUserName()"></h1></td>
                    </tr>
                    <tr>
                    </tr>
                </table>
                <table id="display_po" border='1' align="center"></table>
            </td>
        </tr>
    </table>
  </body>
</html>
<%
        }
    }
%>