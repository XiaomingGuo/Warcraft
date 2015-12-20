/**
 * 
 */
$(function()
{
	$('#store_name').change(function()
	{
		ClearSelectContent("supplier_name");
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var data_list = data.split("#");
				var vendor_list = data_list[0].split("$");
				for (var i = 1; i < vendor_list.length - 1; i++)
				{
					AddNewSelectItem("supplier_name", vendor_list[i]);
				}
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("product_type", pro_list[i]);
				}
			}
			DisplayAddList();
		});
	});
	
	$('#product_type').change(function()
	{
		ClearSelectContent("product_name");
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("product_name", pro_list[i]);
				}
			}
			DisplayAddList();
		});
	});
	
	$('#product_name').change(function()
	{
		DisplayAddList();
	});
	
	$('#supplier_name').change(function()
	{
		DisplayAddList();
	});
});

function SubmitDateChange()
{
	DisplayAddList();
}

function DisplayAddList()
{
	var storage_name = $.trim($("#store_name").val());
	var product_type = $.trim($("#product_type").val());
	var product_name = $.trim($("#product_name").val());
	var supplier_name = $.trim($("#supplier_name").val());
	var beginDate = dojo.widget.byId("BeginDate").inputNode.value;
	var endDate = dojo.widget.byId("EndDate").inputNode.value;
	var submitDate = dojo.widget.byId("SubmitDate").inputNode.value;
	$.post("Ajax/Query_Storage_Report_Ajax.jsp", {"storage_name":storage_name, "product_type":product_type, "product_name":product_name,
		"supplier_name":supplier_name, "beginDate":beginDate, "endDate":endDate, "submitDate":submitDate}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$("#display_add").empty();
			$("#hidden_table").empty();
			var data_list = data.split("$");
			var iColCount = data_list[1], iRowCount = data_list[2];
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th><label name="+iHead+">" + data_list[iHead + 2] + "</label></th>");
					tr.append(th);
				}
				$("#display_add").append(tr);
				//<input name="<%=iRow*12+iCol %>" value=<%= iRow + 1 %> style='width:30px' readonly>
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						var iColNum = iRow*iColCount+iCol;//
						td.append("<input style='width:80px' value='"+data_list[iRow*iColCount + iCol + 2]+"' name='"+iColNum+"'/>");
						tr.append(td);
					}
					$("#display_add").append(tr);
				}
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						var iColNum = iRow*iColCount+iCol;
						td.append("<input type='hidden' value='"+data_list[iRow*iColCount + iCol + 2]+"' name='"+iColNum+"'/>");
						tr.append(td);
					}
					$("#hidden_table").append(tr);
				}
			}
		}
	});
}

