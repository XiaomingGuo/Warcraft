/**
 * 
 */
$(function()
{
	$("li").click(function()
	{
		var shipNo=$(this).html();
		$("#TitleName").html(shipNo);
		$.post("Ajax/Query_Shipping_No_Item_Ajax.jsp", {"Shipping_No":shipNo}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				$("#OrderBlock").empty();
				var data_list = data.split("$");
				var iColCount = data_list[1], iRowCount = data_list[2];
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 2] + "</th>");
					tr.append(th);
				}
				$("#OrderBlock").append(tr);
				//{"ID", "产品类型", "产品名称", "八码", "批号", "PO单号", "数量", "出货时间"};
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						td.append(data_list[iRow*iColCount + iCol + 2]);
						tr.append(td);
					}
					$("#OrderBlock").append(tr);
				}
			}
		});
	});
});

function PutInStorage(obj)
{
	var tempList = obj.name.split('$');
	var storeQTY = $("#"+tempList[1]).val();
	$.post("Ajax/Out_Quality_Control_Ajax.jsp", {"product_id":tempList[0], "PutInQTY":storeQTY}, function(data, textStatus)
	{
		if (!(textStatus == "success") || data.indexOf("error") >= 0)
		{
			alert(data.split("$")[1]);
		}
		location.reload();
	});
}

function commitShippingDate()
{
	var beginDate = dojo.widget.byId("BeginDate").inputNode.value;
	var endDate = dojo.widget.byId("EndDate").inputNode.value;
	alert(beginDate);
	alert(endDate);
	location.href ="ShippingSummary.jsp?beginDate="+beginDate + "&endDate=" + endDate;
}
