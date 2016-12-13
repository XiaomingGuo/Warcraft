<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.ApprovePage" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    ApprovePage hPageHandle = new ApprovePage();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("tishi.jsp");
    }
    else
    {
        int temp = mylogon.getUserRight()&2048;
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
            String[] displayKeyList = {"ID", "物料名称", "八码", "批号", "申请人", "数量", "使用人", "申请时间", "领取"};
            String tempBP = request.getParameter("BeginPage");
            
            int recordCount = hPageHandle.GetOtherRecordCount();
            
            int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
            List<List<String>> recordList = hPageHandle.GetDisplayRecordList(BeginPage, PageRecordCount);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>批准</title>
    
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
  <body>
    <jsp:include page="Menu/MainMenu.jsp"/>
        <table align="center" border="1">
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
                    String Barcode = recordList.get(1).get(iRow-1);
                    for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
                    {
                        if(displayKeyList[iCol-1] == "领取")
                        {
                            if(!recordList.get(iCol-2).get(iRow-1).equalsIgnoreCase("1"))
                            {
%>
                <td>
                        <center><input type="button" value="领取" name=<%=recordList.get(0).get(iRow-1)+"$"+Barcode+"$"+recordList.get(4).get(iRow-1)%> id=<%=recordList.get(0).get(iRow-1)%> onclick="change(this)"></center>
                </td>
<%
                            }
                        }
                        else if(displayKeyList[iCol-1] == "物料名称")
                        {
%>
                <td><%= hPageHandle.GetNameByBarCode(Barcode) %></td>
<%
                        }
                        else if (displayKeyList[iCol-1] == "ID")
                        {
%>
                    <td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
                        }
                        else
                        {
%>
                <td><%= recordList.get(iCol-2).get(iRow-1)%></td>
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
           <jsp:include page="PageNum.jsp">
               <jsp:param value="<%=recordCount %>" name="recordCount"/>
               <jsp:param value="<%=PageRecordCount %>" name="PageRecordCount"/>
               <jsp:param value="<%=BeginPage %>" name="BeginPage"/>
               <jsp:param value="Approve.jsp" name="PageName"/>
           </jsp:include>
        <script type="text/javascript">
            function change(obj)
            {
                var tempList = obj.name.split("$");
                DisableButton(tempList[0]);
                $.post("Ajax/ApproveAjax.jsp", {"material_id":tempList[0], "Barcode":tempList[1], "OUT_QTY":tempList[2]}, function(data, textStatus)
                {
                    if (!(textStatus == "success" && data.indexOf(tempList[1]) < 0))
                    {
                        alert(data);
                    }
                    location.reload();
                });
            }
        </script>
  </body>
</html>
<%
        }
    }
%>