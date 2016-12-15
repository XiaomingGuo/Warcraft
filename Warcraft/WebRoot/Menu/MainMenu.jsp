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
        message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        List<String> productList = hPageHandle.GetAllProductNameList();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="css/responsive-menu.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    
    <script src="JS/modernizr.min.js" type="text/javascript"></script>
    <script src="JS/modernizr-custom.js" type="text/javascript"></script>
    <script src="JS/jquery-1.12.4.min.js"></script>
    <script src="JS/responsive-menu.js" type="text/javascript"></script>
    <script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
    <script language="javascript" src="Page_JS/MenuJS.js"></script>
    <script>
        jQuery(function ($) {
            var menu = $('.rm-nav').rMenu({
                minWidth: '900px',
            });
        });
		$(function(){
			/*登录*/
			$(".list a").click(function(){
				$(".member").slideToggle(500);
			});
		});
	</script>
  </head>
  <body>
		<div class="header">
		<div class="headerinner">
		<div class="brand">
		    <p><img src="IMAGE/A-5.png" align="middle"></p>
		</div>
		<div class="rm-container">
			<nav class="rm-nav rm-nojs rm-lighten">
				<ul class="headernav">
					<li><a href="MainPage.jsp">Home</a></li>
					<li><a href="OtherStoreMenu/OtherSummary_New.jsp">五金库管理</a>
						<ul>
							<li><a href="OtherStoreMenu/Application.jsp">申请</a></li>
							<li><a href="OtherStoreMenu/QueryApplication.jsp">查询</a></li>
							<li><a href="OtherStoreMenu/ApproveApplication.jsp">批准</a></li>
							<li><a href="AddMFGToolsMaterial.jsp">五金录入</a></li>
							<li><a href="QueryOther.jsp">五金录入状态</a></li>
							<li><a href="javascript:void(0)">五金库报表</a>
								<ul>
									<li><a href="MFGToolsMonthReport.jsp">五金消耗报表</a></li>
									<li><a href="OtherStoreMenu/OtherSummary.jsp">五金库库存</a></li>
									<li><a href="OtherStoreMenu/OtherStorageReport.jsp">库房报表</a></li>
								</ul></li>
						</ul></li>
					<li><a href="MainPage.jsp">生产过程控制</a>
						<ul>
							<li><a href="Customer_PO.jsp">客户PO生成</a></li>
							<li><a href="Generate_Order.jsp">生产单生成</a></li>
							<li><a href="Planned_Production.jsp">生产计划</a></li>
							<li><a href="Transfer_To_SemiProStorage.jsp">原材料转半成品</a></li>
							<li><a href="Transfer_To_ProductStorage.jsp">半成品转成品</a></li>
							<li><a href="Discard_Material.jsp">材料报废</a></li>
							<li><a href="Close_MB_Order.jsp">关闭生产单</a></li>
							<li><a href="Product_Shipment.jsp">成品出货</a></li>
						</ul></li>
					<li><a href="MainPage.jsp">生产物料录入</a>
						<ul>
							<li><a href="TransferMFGBarcode.jsp">生产物料八码转换</a></li>
							<li><a href="AddMFGMaterial.jsp">生产物料入库</a></li>
							<li><a href="AddMFGMaterial_ReferTo_PO.jsp">PO物料入库</a></li>
							<li><a href="QueryMaterial.jsp">原材料状态</a></li>
						</ul></li>
					<li><a href="MainPage.jsp">生产报表</a>
						<ul>
							<li><a href="ManufactorySummary.jsp">生产库存</a></li>
							<li><a href="ShippingSummary.jsp">出货报表</a></li>
							<li><a href="ManufactorySummary.jsp">半成品库存</a></li>
						</ul></li>
					<li><a href="MainPage.jsp">物料信息录入</a>
						<ul>
							<li><a href="AddStoreroom.jsp">添加库房</a></li>
							<li><a href="AddProductType.jsp">添加类型</a></li>
							<li><a href="NewMaterialInfo.jsp">添加新物料</a></li>
							<li><a href="AddSupplier.jsp">添加供应商</a></li>
						</ul></li>
					<li><a href="PersonMenu/SummarizeCheckInTime.jsp">人事信息统计</a>
						<ul>
							<li><a href="PersonMenu/PersonalInfo.jsp">人员考勤查询</a></li>
							<li><a href="PersonMenu/SummarizeCheckInTime.jsp">考勤汇总</a></li>
							<li><a href="PersonMenu/AddCheckInTime.jsp">补刷考勤</a></li>
							<li><a href="PersonMenu/AddBatchHoliday.jsp">批量补录假期</a></li>
							<li><a href="PersonMenu/AddHolidays.jsp">节假日及转班</a></li>
							<li><a href="PersonMenu/SummaryHoliday.jsp">节假日查询</a></li>
							<li><a href="PersonMenu/ArrangeCheckInTime.jsp">人员排班</a></li>
						</ul></li>
					<li><a href="Quit.jsp">退出</a></li>
				</ul>
			</nav>
		</div>
		<div class="member">
			<ul>
				<li>
					<img src="IMAGE/huiyuan1.png" alt="">
					<a href="ChangePassword.jsp">登录</a>
				</li>
				<li>
					<img src="IMAGE/huiyuan1.png" alt="">
					<a href="ChangePassword.jsp">新会员注册</a>
				</li>
			</ul>
		</div>
		</div>
		</div>
        <!-- <hr width=100% noshade="noshade" size="5"> -->
    <table width="80%" align="center">
        <tr>
            <td width="80%">
                <table width="100%">
                    <tr>
                        <td width="40%" align="right">您好！<jsp:getProperty property="username" name="mylogon"/>！欢迎登录！
<%
                        int temp = mylogon.getUserRight()&2;
                        if(temp == 0)
                          {
%>
                        <a href = 'ChangePassword.jsp'>修改密码</a>
<%
                          }
                          else
                          {
%>
                        <a href = 'ChangePassword.jsp'>修改密码</a>
                        <a href = 'UserManagement.jsp?BeginPage=1'>用户管理</a>
                
<%
                          }
%>
                        </td>
                    </tr>
                </table>
                <table align="center">
                    <tr>
                        <td>
                            <label>八码查询:&nbsp;</label>
                            <input type="text" name="search_name" id="search_name" list="productName" style="width:280px" onblur="findBarcode(this)"/>
                            <datalist name="productName" id="productName" style="width:100px">
                                <option value = "--请选择--">--请选择--</option>
<%
                                for(int i = 0; i < productList.size(); i++)
                                {
%>
                                <option value = <%=productList.get(i) %>><%=productList.get(i)%></option>
<%
                                }
%>
                            </datalist>
                            <input type="text" name="disBarcode" id="disBarcode" style="width:180px" readonly>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
  </body>
</html>
<%
    }
%>