<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<html>
<body>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	String sql=(String)request.getParameter("q");
	proInfo = hDBHandle.GetProductInfo(sql);
%>
 	  	<select name="product_info">
		  	<option value = 0>--请选择--</option>
<%
if (null != proInfo)
{
	for(int i = 0; i < proInfo.size(); i++)
	{
%>
		  	<option value = <%= i + 1 %>><%=proInfo.get(i)%></option>
<%
	}
}
%>
	  	</select>
		<br><br>
</body>
</html>