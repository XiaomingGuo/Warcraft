<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOther" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
    String message="";
    QueryOther hPageHandle = new QueryOther();
    if(session.getAttribute("logonuser")==null)
    {
        response.sendRedirect("tishi.jsp");
    }
    else
    {
        message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String[] displayKeyList = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注", "操作"};
        String tempBP = request.getParameter("BeginPage");
        int PageRecordCount = 20;
        int BeginPage = tempBP!=null?Integer.parseInt(tempBP):1;
        int recordCount = hPageHandle.GetAllRecordCount();
        Thread.sleep(500);
        List<List<String>> recordList = hPageHandle.GetOtherStorageRecordDisplay(BeginPage, PageRecordCount);
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
      <script language="javascript" src="Page_JS/QueryOtherJS.js"></script>
  <body>
    <jsp:include page="Menu/MainMenu.jsp"/>
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
			//"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存", "单价", "总进货价", "供应商", "备注", "操作"
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
    <table id="display_info" align="center" border="1">
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
                String Barcode = recordList.get(0).get(iRow-1);
                for(int iCol = 1; iCol <= displayKeyList.length; iCol++)
                {
                    if (displayKeyList[iCol-1] == "ID")
                    {
%>
            <td><%=PageRecordCount*(BeginPage-1)+iRow %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "产品名称")
                    {
%>
            <td><%= hPageHandle.GetProductInfoByBarcode(Barcode, "name") %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "八码")
                    {
%>
            <td><%= Barcode %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "批号")
                    {
%>
            <td><%= recordList.get(1).get(iRow-1) %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "总进货量")
                    {
%>
            <td><%= recordList.get(2).get(iRow-1)%></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "已消耗")
                    {
%>
            <td><%= recordList.get(3).get(iRow-1) %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "库存")
                    {
%>
            <td><%= (Integer.parseInt(recordList.get(2).get(iRow-1)) - Integer.parseInt(recordList.get(3).get(iRow-1)))%></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "单价")
                    {
%>
            <td><%= recordList.get(4).get(iRow-1)%></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "总进货价")
                    {
%>
            <td><%= recordList.get(5).get(iRow-1)%></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "供应商")
                    {
%>
            <td><%= recordList.get(6).get(iRow-1)%></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "备注")
                    {
%>
            <td><%= hPageHandle.GetProductInfoByBarcode(Barcode, "description") %></td>
<%
                    }
                    else if (displayKeyList[iCol-1] == "操作")
                    {
                        if(recordList.get(8).get(iRow-1).equals("0"))
                        {
%>
            <td>
                    <input type='button' value='确认' id='<%=recordList.get(7).get(iRow-1) %>Sure' name='<%=recordList.get(1).get(iRow-1) %>$<%=Barcode %>' onclick='SubmitQty(this)'>
                    &nbsp;
                    <input type='button' value='删除' id='<%=recordList.get(7).get(iRow-1) %>Rej' name='<%=recordList.get(1).get(iRow-1) %>$<%=Barcode %>' onclick='RejectQty(this)'>
            </td>
<%
                        }
                        else
                        {
                            %>
            <td><input type='button' value='修改' id='<%=recordList.get(7).get(iRow-1) %>Modify' name='<%=recordList.get(7).get(iRow-1) %>#<%=iRow %>' onclick='ModifyRecord(this)'></td>
                            <%
                        }
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
           <jsp:param value="QueryOther.jsp" name="PageName"/>
       </jsp:include>
  </body>
</html>
<%
    }
%>