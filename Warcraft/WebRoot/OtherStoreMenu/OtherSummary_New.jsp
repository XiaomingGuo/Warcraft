<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("../tishi.jsp");
	}
	else
	{
		int temp = mylogon.getUserRight()&32;
		if(temp == 0)
		{
			session.setAttribute("error", "管理员未赋予您进入权限,请联系管理员开通权限后重新登录!");
			response.sendRedirect("../tishi.jsp");
		}
		else
		{
			message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			List<String> store_name = hPageHandle.GetStoreName("TOOLS");
			List<List<String>> productType = hPageHandle.GetProductTypeList(store_name);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>五金库库存</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/zzsc-demo.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-accordion-menu.css"/>
	<style type="text/css">
		.content{width:260px;margin:50px auto;}
		.filterinput{
			background-color:rgba(249, 244, 244, 0);
			border-radius:15px;
			width:90%;
			height:30px;
			border:thin solid #FFF;
			text-indent:0.5em;
			font-weight:bold;
			color:#FFF;
		}
		#demo-list a{
			overflow:hidden;
			text-overflow:ellipsis;
			-o-text-overflow:ellipsis;
			white-space:nowrap;
			width:100%;
		}
		.main-sidebar{
			position: absolute;
			top: 80px;
			left: 0;
			height: 90%;
			min-height: 90%;
			width: 300px;
			z-index: 810;
			background-color: #CCDDFF;
		}
	</style>
  </head>
	<script language="javascript" src="JS/jquery-2.1.3.min.js"></script>
	<script language="javascript" src="Page_JS/PagePublicFunJS.js"></script>
	<script language="javascript" src="Page_JS/OtherStoreMenuJS/OtherSummaryJS.js"></script>
	<body>
    <jsp:include page="../Menu/MainMenu.jsp"/>
		<aside class="main-sidebar">
		<div class="zzsc-container">
			<div class="content">
				<div id="jquery-accordion-menu" class="jquery-accordion-menu black">
					<div class="jquery-accordion-menu-header" id="form"></div>
					<ul id="demo-list">
						<li class="active"><a href="OtherStoreMenu/OtherSummary_New.jsp"><i class="fa fa-home"></i>Home </a></li>
<%
					for(int iStore = 0; iStore < store_name.size(); iStore++)
					{
%>
						<li><a href="javascript:void(0)"><i class="fa fa-cog"></i><%=store_name.get(iStore)%></a>
						<ul class="submenu">
<%
						for(int iType = 0; iType < productType.get(iStore).size(); iType++)
						{
							String passVal = store_name.get(iStore)+"#"+productType.get(iStore).get(iType);
%>
								<li><a href="javascript:void(0)" name=<%=passVal %> onclick="DisplayStorageList_New(this)"><%=productType.get(iStore).get(iType) %></a></li>
<%
						}
%>
						</ul>
						</li>
<%
					}
%>
					</ul>
					<div class="jquery-accordion-menu-footer">
						Footer
					</div>
				</div>
			</div>
		</div>
		</aside>
		<br><br>
		<table id="display_storage" border='1' align="center"></table>
	<script src="JS/jquery-accordion-menu.js" type="text/javascript"></script>
	<script type="text/javascript">
		jQuery(document).ready(function () {
			jQuery("#jquery-accordion-menu").jqueryAccordionMenu();
		});
		
		$(function(){	
			//列表项背景颜色切换
			$("#demo-list li").click(function(){
				$("#demo-list li.active").removeClass("active")
				$(this).addClass("active");
			});
		})
		(function($) {
			$.expr[":"].Contains = function(a, i, m) {
				return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
			};
			function filterList(header, list) {
				//@header 头部元素
				//@list 无序列表
				//创建一个搜素表单
				var form = $("<form>").attr({
					"class":"filterform",
					action:"#"
				}), input = $("<input>").attr({
					"class":"filterinput",
					type:"text"
				});
				$(form).append(input).appendTo(header);
				$(input).change(function() {
					var filter = $(this).val();
					if (filter) {
						$matches = $(list).find("a:Contains(" + filter + ")").parent();
						$("li", list).not($matches).slideUp();
						$matches.slideDown();
					} else {
						$(list).find("li").slideDown();
					}
					return false;
				}).keyup(function() {
					$(this).change();
				});
			}
			$(function() {
				filterList($("#form"), $("#demo-list"));
			});
			})(jQuery);
	</script>
  </body>
</html>
<%
		}
	}
%>