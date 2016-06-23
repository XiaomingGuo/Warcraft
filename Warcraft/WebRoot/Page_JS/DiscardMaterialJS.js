/**
 * 
 */
$(function()
{
	var $bar_code = $('#bar_code');
	
	$('#product_order').change(function()
	{
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#inputBarcode").val("");
		$("#QTY").val("");
		$.post("Ajax/Get_ProductNameType_By_Order_Ajax.jsp", {"ProductOrder":GetSelectedContent("product_order"), "Storage_Name":"Semi_Pro_Storage"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var splitList = data.split("$");
				for (var i = 1; i < splitList.length/2; i++)
				{
					AddNewSelectItem("product_type", splitList[2*i]);
				}
			}
		});
	});
	
	$('#product_type').change(function()
	{
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#inputBarcode").val("");
		$("#QTY").val("");
		$.post("Ajax/Get_ProductNameType_By_Order_Ajax.jsp", {"ProductOrder":GetSelectedContent("product_order"), "Storage_Name":"Semi_Pro_Storage"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var splitList = data.split("$");
				var proType = GetSelectedContent("product_type");
				for (var i = 1; i < splitList.length/2; i++)
				{
					if(splitList[2*i] == proType)
						AddNewSelectItem("product_name", splitList[2*i-1]);
				}
			}
		});
	});
	
	$('#product_name').change(function()
	{
		$bar_code.empty();
		$("#inputBarcode").val("");
		$("#QTY").val("");
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"),"product_type":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				if (code_list.length == 6)
				{
					AddNewSelectItem("bar_code", code_list[1]);
					$("#productname").val(GetSelectedContent("product_name"));
					$("#inputBarcode").val(code_list[1]);
				}
				else
				{
					$bar_code.append('<option value="请选择">--请选择--</option>');
					$("#inputBarcode").val("");
				}
			}
		});
	});	
});

