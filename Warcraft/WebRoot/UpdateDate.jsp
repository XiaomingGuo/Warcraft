<%@ page language="java" import="java.util.* ,java.io.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent" %>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%
    request.setCharacterEncoding("UTF-8");
    DatabaseStore hORHandle = new DatabaseStore("Other_Record");
    hORHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("apply_date"), Arrays.asList("00000000"), Arrays.asList("id"));
    List<String> tempID = hORHandle.getDBRecordList("id");
    List<String> createDate = hORHandle.getDBRecordList("create_date");
    for(int iRow = 0; iRow < tempID.size(); iRow++)
    {
        String curCreateDate = createDate.get(iRow);
        String applyDate = curCreateDate.split(" ")[0].replace("-", "");
        hORHandle.UpdateRecordByKeyList("apply_date", applyDate, Arrays.asList("id"), Arrays.asList(tempID.get(iRow)));
        hORHandle.UpdateRecordByKeyList("create_date", curCreateDate, Arrays.asList("id"), Arrays.asList(tempID.get(iRow)));
    }
    out.println("<script>alert('创建成功！');window.location.href = 'index.jsp';</script>");
%>