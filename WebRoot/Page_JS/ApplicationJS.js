/**
 * 
 */
$(function()
{
	var $bar_code = $('#bar_code');
	
	$('#store_name').change(function()
	{
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		$bar_code.val("");
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
		$bar_code.val("");
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
		$bar_code.empty();
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"),"product_type":GetSelectedContent("product_type"), "storage":"other_storage"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var code_list = data.split("$");
				$bar_code.val(code_list[1]);
				$('#Total_QTY').attr("value", code_list[4]);
			}
		});
	});				
});

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
	if (IsProductionMaterial(Barcode))
	{
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		alert("注意不能申请生产物料!");
		return false;
	}
	return true;
}




