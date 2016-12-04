/**
 * 
 */
$(function()
{
	$('#store_name').change(function()
	{
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		$('#bar_code').val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var data_list = data.split("#");
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("product_type", pro_list[i]);
				}
			}
		});
	});
	
	$('#product_type').change(function()
	{
		ClearSelectContent("product_name");
		$('#bar_code').val("");
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
		});
	});
	
	$('#product_name').change(function()
	{
		$('#bar_code').empty();
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"),"product_type":GetSelectedContent("product_type"), "storage":"other_storage"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var code_list = data.split("$");
				$('#bar_code').val(code_list[1]);
				$('#Total_QTY').attr("value", code_list[code_list.length-1]);
			}
		});
	});				
});

function DisplayStorageList()
{
	var storage_name = $.trim($("#store_name").val());
	var product_type = $.trim($("#product_type").val());
	var product_name = $.trim($("#product_name").val());
	var bar_code = $.trim($("#bar_code").val());
	if (storage_name.indexOf("请选择") >= 0&&product_type.indexOf("请选择") >= 0&&product_name.indexOf("请选择") >= 0&&bar_code == "")
	{
		alert("啥都不输入难道你要我给你查空气?");
		return;
	}
	$.post("Ajax/OtherStoreMenu/Query_Storage_Item_Ajax.jsp", {"storage_name":storage_name, "product_type":product_type, "product_name":product_name, "bar_code":bar_code}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$("#display_storage").empty();
			var data_list = data.split("$");
			var iColCount = data_list[1], iRowCount = data_list[2];
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 2] + "</th>");
					tr.append(th);
				}
				$("#display_storage").append(tr);
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						td.append(data_list[iRow*iColCount + iCol + 2]);
						tr.append(td);
					}
					$("#display_storage").append(tr);
				}
			}
		}
	});
}

function InputBarcode()
{
	if(!CheckBarcode())
	{
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":$("#bar_code").val()}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#store_name").val(proInfoList[1]);
			$("#product_type").empty();
			AddNewSelectItem("product_type", proInfoList[2]);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}

function CheckBarcode()
{
	var Barcode = $("#bar_code").val();
	if(Barcode == null||Barcode.length != 8)
	{
		$("#barcode").val("");
		return false;
	}
	return true;
}

