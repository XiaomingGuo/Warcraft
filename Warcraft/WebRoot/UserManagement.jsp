<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.UserManagement" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    UserManagement hPageHandle = new UserManagement();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("tishi.jsp");
    }
    else
    {
        int temp = mylogon.getUserRight()&2;
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
            int PageRecordCount = 20;
            int BeginPage = Integer.parseInt(request.getParameter("BeginPage"));
            
            String[] keyList = hPageHandle.GetDisplayArray();
            String[] codeKeyList = {"ID", "check_in_id", "isFixWorkGroup", "name", "create_date", "department", "password", "permission", "submit"};
            int recordCount = hPageHandle.GetUserCount();
            List<List<String>> recordList = hPageHandle.GetUserInfo(PageRecordCount, BeginPage);
            List<String> titleList = hPageHandle.GetTitleName();
            List<String> workGroupList = hPageHandle.GetAllWorkGroupName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
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
    <script language="javascript" src="Page_JS/UserManagementJS.js"></script>
  <body onload="DisplayUserTable(<%=BeginPage%>)">
    <jsp:include page="Menu/MainMenu.jsp"/>
    <table border="1" align="center">
        <tr>
<%
            for(int iCol = 1; iCol <= keyList.length; iCol++)
            {
                if(keyList[iCol-1]=="ID"||keyList[iCol-1]=="创建时间")
                    continue;
%>
            <th><%= keyList[iCol-1]%></th>
<%
            }
%>
        </tr>

<%
            if (!recordList.isEmpty())
            {
                int iRow = 0;
%>
        <tr>
<%
                for(int iCol = 1; iCol <= keyList.length; iCol++)
                {
                    if(keyList[iCol-1]=="ID"||keyList[iCol-1]=="创建时间")
                        continue;
                    if(keyList[iCol-1] == "用户权限")
                    {
%>
        <td align="center">
<%
                        for(int titleIdx=1; titleIdx <= titleList.size(); titleIdx++)
                        {
                            String titleName = titleList.get(titleIdx-1);
%>
            <input type="checkbox" name="AddPermission" value="<%=titleName %>"><%=titleName %>
<%
                            if((titleIdx)%5 == 0)
                            {
%>
            <br>
<%
                            }
                        }
%>
        </td>
<%
                    }
                    else if(keyList[iCol-1] == "操作")
                    {
%>
        <td>
            <center><input type="button" value="确认" onclick="AddUser(this)"></center>
        </td>
<%
                    }
                    else if(keyList[iCol-1] == "考勤类型")
                    {
%>
        <td>
            <select name="workGroup" id="workGroup" style="width:120px">
                <option value = "--请选择--">--请选择--</option>
<%
                        for(int i = 0; i < workGroupList.size(); i++)
                        {
%>
                <option value = <%=workGroupList.get(i) %>><%=workGroupList.get(i)%></option>
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
        <td>
            <center><input type="text" name="<%=codeKeyList[iCol-1] %>" id="<%=codeKeyList[iCol-1] %>" style="width:100px"></center>
        </td>
<%
                    }
                }
%>
        </tr>
<%
            }
%>
    </table>
    <br>
    <table id="display_user" border='1' align="center"></table>
    <br><br>
    <jsp:include page="PageNum.jsp">
        <jsp:param value="<%=recordCount %>" name="recordCount"/>
        <jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
        <jsp:param value="<%=BeginPage %>" name="BeginPage"/>
        <jsp:param value="UserManagement.jsp" name="PageName"/>
    </jsp:include>
  </body>
</html>
<%
        }
    }
%>