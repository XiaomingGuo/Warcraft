/**
 * 
 */
$(function()
{
	var $store_name_addproduct = $('#store_name_addproduct');
	var $product_type = $('#product_type');
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	
	$store_name_addproduct.change(function()
	{
		ClearSelectContent("supplier_name");
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#productname").val("");
		$("#barcode").val("");
		$("#QTY").val("");
		$("#WeightUnit").val("");
		$("#PriceUnit").val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name_addproduct")}, function(data, textStatus)
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
		});
	});
	
	$product_type.change(function()
	{
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#productname").val("");
		$("#barcode").val("");
		$("#QTY").val("");
		$("#WeightUnit").val("");
		$("#WeightUnit").removeAttr("readonly");
		$("#PriceUnit").val("");
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
		$bar_code.empty();
		$("#productname").val("");
		$("#barcode").val("");
		$("#QTY").val("");
		$("#WeightUnit").val("");
		$("#WeightUnit").removeAttr("readonly");
		$("#Description").removeAttr("readonly");
		$("#PriceUnit").val("");
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("product_name"),"product_type":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				if (code_list.length == 5)
				{
					AddNewSelectItem("bar_code", code_list[1]);
					$("#productname").val(GetSelectedContent("product_name"));
					$("#barcode").val(code_list[1]);
					$("#WeightUnit").val(code_list[2]);
					$("#WeightUnit").attr("readonly", "readonly");
					$("#Description").val(code_list[3]);
					$("#Description").attr("readonly", "readonly");
				}
				else
				{
					$bar_code.append('<option value="请选择">--请选择--</option>');
					$("#barcode").val("");
					$("#WeightUnit").val(code_list[2]);
				}
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

function checkBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		alert("八码的内容和位数不符合要求");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Check_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			if (parseInt(data.split('$')[1]) > 0)
			{
				$("#barcode").val("");
			}
		}
	});
}

function changeProductName(obj)
{
	$("#barcode").val("");
}

function InputBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) > 60000000 && parseInt(checkedBarcode) < 70000000)
	{
		checkedBarcode = parseInt(checkedBarcode)-10000000;
	}
	else if (parseInt(checkedBarcode) > 70000000 && parseInt(checkedBarcode) < 80000000)
	{
		checkedBarcode = parseInt(checkedBarcode)-20000000;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				$("#store_name_addproduct").val("原材料库");
			}
			else
			{
				$("#store_name_addproduct").val(proInfoList[1]);
			}
			$("#store_name_addproduct").change();
			$("#product_type").empty();
			var keyWord = proInfoList[2];
			if(parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 80000000)
			{
				if (keyWord.indexOf("原锭") < 0)
				{
					keyWord += "原锭";
				}
				else if (keyWord.indexOf("半成品"))
				{
					keyWord.replace("半成品", "原锭");
				}
			}
			AddNewSelectItem("product_type", keyWord);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}
