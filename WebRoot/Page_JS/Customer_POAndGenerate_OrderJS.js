/**
 * 
 */
$(function()
{
	var $store_name = $('#store_name');
	var $product_type = $('#product_type');
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	
	$store_name.change(function()
	{
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		ClearSelectContent("vendor_name");
		$("#barcode").val("");
		$("#product_QTY").val("0");
		$("#material_QTY").val("0");
		$("#Need_QTY").val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var data_list = data.split("#");
				var vendor_list = data_list[0].split("$");
				for (var i = 1; i < vendor_list.length - 1; i++)
				{
					AddNewSelectItem("vendor_name", vendor_list[i]);
				}
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("product_type", pro_list[i]);
				}
			}
		});
	});

	$product_type.change(function()
	{
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#barcode").val("");
		$("#product_QTY").val("0");
		$("#material_QTY").val("0");
		$("#Need_QTY").val("");
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
	
	$product_name.change(function()
	{
		$("#bar_code").empty();
		$.post("Ajax/App_Order_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"), "product_type":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var code_list = data.split("$");
				AddNewSelectItem("bar_code", code_list[1]);
				$("#barcode").val(code_list[1]);
				$("#product_QTY").val(code_list[2]);
				$("#semi_pro_QTY").val(code_list[3]);
				$("#material_QTY").val(code_list[4]);
				Qty_Calc();
			}
		});
	});
	
	$bar_code.change(function()
	{
		$("#barcode").val("");
		var selectedBarcode = GetSelectedContent("bar_code");
		if (selectedBarcode.indexOf("请选择") >= 0)
		{
			$("#barcode").val("");
		}
		else
		{
			$("#barcode").val(selectedBarcode);
		}
	});
});

function Qty_Calc(obj)
{
	var poCount = parseInt($("#Input_QTY").val());
	var proCount = parseInt($("#product_QTY").val());
	var semiProCount = parseInt($("#semi_pro_QTY").val());
	var matCount = parseInt($("#material_QTY").val());
	var tempQTY = (proCount + semiProCount + matCount) - poCount;
	if (poCount > 0&&proCount >= 0&&matCount >= 0&&semiProCount >= 0)
	{
		if (tempQTY >= 0)
		{
			$("#Need_QTY").val(0);
		}
		else
		{
			$("#Need_QTY").val(-tempQTY);
		}
	}
	else
	{
		$("#Input_QTY").val("");
	}
}

function InputBarcode(obj)
{
	var barcode = $("#barcode").val();
	if(barcode == null||barcode.length != 8)
	{
		alert("输入八码不对吧!");
		$("#bar_code").val("");
		return;
	}
	if (parseInt(barcode) < 50000000 || parseInt(barcode) >= 80000000)
	{
		alert("只能输入八码(大于等于50000000或小于80000000)!");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":barcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#store_name").val(proInfoList[1]);
			$("#store_name").change();
			$("#product_type").empty();
			AddNewSelectItem("product_type", proInfoList[2]);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}

