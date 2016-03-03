/**
 * 
 */
$(function()
{
	$('#from_store_name').change(function()
	{
		ClearSelectContent("from_product_type");
		ClearSelectContent("from_product_name");
		ClearSelectContent("from_bar_code");
		$("#from_productname").val("");
		$("#from_barcode").val("");
		$("#from_QTY").val("");
		$("#store_QTY").val("");
		$("#from_WeightUnit").val("");
		$("#from_ProductWeight").val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("from_store_name")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var data_list = data.split("#");
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("from_product_type", pro_list[i]);
				}
			}
		});
	});
	
	$('#from_product_type').change(function()
	{
		ClearSelectContent("from_product_name");
		ClearSelectContent("from_bar_code");
		$("#from_productname").val("");
		$("#from_barcode").val("");
		$("#from_QTY").val("");
		$("#store_QTY").val("");
		$("#from_WeightUnit").val("");
		$("#from_WeightUnit").removeAttr("readonly");
		$("#from_ProductWeight").val("");
		$("#from_ProductWeight").removeAttr("readonly");
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":GetSelectedContent("from_product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("from_product_name", pro_list[i]);
				}
			}
		});
	});
	
	$('#from_product_name').change(function()
	{
		$('#from_bar_code').empty();
		$("#from_productname").val("");
		$("#from_barcode").val("");
		$("#from_QTY").val("");
		$("#store_QTY").val("");
		$("#from_WeightUnit").val("");
		$("#from_WeightUnit").removeAttr("readonly");
		$("#from_ProductWeight").val("");
		$("#from_ProductWeight").removeAttr("readonly");
		$("#from_Description").removeAttr("readonly");
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("from_product_name"),"product_type":GetSelectedContent("from_product_type")}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				if (code_list.length == 6)
				{
					AddNewSelectItem("from_bar_code", code_list[1]);
					$("#from_productname").val(GetSelectedContent("from_product_name"));
					$("#from_barcode").val(code_list[1]);
					$("#from_WeightUnit").val(code_list[2]);
					$("#from_WeightUnit").attr("readonly", "readonly");
					$("#from_Description").val(code_list[3]);
					$("#from_Description").attr("readonly", "readonly");
					$("#from_ProductWeight").val(code_list[4]);
					$("#from_ProductWeight").attr("readonly", "readonly");
					$("#store_QTY").val(code_list[code_list.length-1]);
					$("#store_QTY").attr("readonly", "readonly");
				}
				else
				{
					$bar_code.append('<option value="请选择">--请选择--</option>');
					$("#from_barcode").val("");
					$("#from_WeightUnit").val(code_list[2]);
					$("#store_QTY").val(0);
					$("#store_QTY").attr("readonly", "readonly");
				}
			}
		});
	});	
	
	$('#from_bar_code').change(function()
	{
		$("#from_barcode").val("");
		var selectedBarcode = GetSelectedContent("from_bar_code");
		if (selectedBarcode.indexOf("请选择") >= 0)
		{
			$("#from_barcode").val("");
		}
		else
		{
			$("#from_barcode").val(selectedBarcode);
		}
	});
	
	$('#to_store_name').change(function()
	{
		ClearSelectContent("to_product_type");
		ClearSelectContent("to_product_name");
		ClearSelectContent("to_bar_code");
		$("#to_productname").val("");
		$("#to_barcode").val("");
		$("#to_QTY").val("");
		$("#to_WeightUnit").val("");
		$("#to_ProductWeight").val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("to_store_name")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var data_list = data.split("#");
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("to_product_type", pro_list[i]);
				}
			}
		});
	});
	
	$('#to_product_type').change(function()
	{
		ClearSelectContent("to_product_name");
		ClearSelectContent("to_bar_code");
		$("#to_productname").val("");
		$("#to_barcode").val("");
		$("#to_QTY").val("");
		$("#to_WeightUnit").val("");
		$("#to_WeightUnit").removeAttr("readonly");
		$("#to_ProductWeight").val("");
		$("#to_ProductWeight").removeAttr("readonly");
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":GetSelectedContent("to_product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					AddNewSelectItem("to_product_name", pro_list[i]);
				}
			}
		});
	});
	
	$('#to_product_name').change(function()
	{
		$('#to_bar_code').empty();
		$("#to_productname").val("");
		$("#to_barcode").val("");
		$("#to_QTY").val("");
		$("#to_WeightUnit").val("");
		$("#to_WeightUnit").removeAttr("readonly");
		$("#to_ProductWeight").val("");
		$("#to_ProductWeight").removeAttr("readonly");
		$("#to_Description").removeAttr("readonly");
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":GetSelectedContent("to_product_name"),"product_type":GetSelectedContent("to_product_type")}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				if (code_list.length == 6)
				{
					AddNewSelectItem("to_bar_code", code_list[1]);
					$("#to_productname").val(GetSelectedContent("to_product_name"));
					$("#to_barcode").val(code_list[1]);
					$("#to_WeightUnit").val(code_list[2]);
					$("#to_WeightUnit").attr("readonly", "readonly");
					$("#to_Description").val(code_list[3]);
					$("#to_Description").attr("readonly", "readonly");
					$("#to_ProductWeight").val(code_list[4]);
					$("#to_ProductWeight").attr("readonly", "readonly");
				}
				else
				{
					$bar_code.append('<option value="请选择">--请选择--</option>');
					$("#to_barcode").val("");
					$("#to_WeightUnit").val(code_list[2]);
				}
			}
		});
	});	
	
	$('#to_bar_code').change(function()
	{
		$("#to_barcode").val("");
		var selectedBarcode = GetSelectedContent("to_bar_code");
		if (selectedBarcode.indexOf("请选择") >= 0)
		{
			$("#to_barcode").val("");
		}
		else
		{
			$("#to_barcode").val(selectedBarcode);
		}
	});
});

function from_InputBarcode(obj)
{
	var checkedBarcode = $("#from_barcode").val();
	if(checkedBarcode == null || checkedBarcode.length != 8)
	{
		$("#from_barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) >= 80000000)
	{
		alert("只能输入八码(大于等于50000000或小于80000000)!");
		$("#from_barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#from_store_name").val(proInfoList[1]);
			$("#from_store_name").change();
			$("#from_product_type").empty();
			AddNewSelectItem("from_product_type", proInfoList[2]);
			$("#from_product_name").empty();
			AddNewSelectItem("from_product_name", proInfoList[3]);
			$("#from_product_name").change();
		}
	});
}

function to_InputBarcode(obj)
{
	var checkedBarcode = $("#to_barcode").val();
	if(checkedBarcode == null || checkedBarcode.length != 8)
	{
		$("#to_barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) >= 80000000)
	{
		alert("只能输入八码(大于等于50000000或小于80000000)!");
		$("#to_barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#to_store_name").val(proInfoList[1]);
			$("#to_store_name").change();
			$("#to_product_type").empty();
			AddNewSelectItem("to_product_type", proInfoList[2]);
			$("#to_product_name").empty();
			AddNewSelectItem("to_product_name", proInfoList[3]);
			$("#to_product_name").change();
		}
	});
}

function DoTranferBarcode()
{
	var from_store_name = GetSelectedContent("from_store_name");
	var to_store_name = GetSelectedContent("to_store_name");
	if(from_store_name != to_store_name)
	{
		alert("必须在同一库中进行物料转换!");
		return;
	}
	var from_barcode = GetSelectedContent("from_bar_code");
	var to_barcode = GetSelectedContent("to_bar_code");
	if(from_barcode == to_barcode)
	{
		alert("同一种物料无需转换!");
		return;
	}
	
	$.post("Ajax/TransferMFGMaterialBarcode_Ajax.jsp", {"store_name":from_store_name,
		"from_bar_code":from_barcode, "to_bar_code":to_barcode,
		"from_QTY":$("#from_QTY").val(), "to_QTY":$("#to_QTY").val()}, function(data, textStatus)
	{
		if (!CheckAjaxResult(textStatus, data))
		{
			alert(data);
			return;
		}
	});
}
