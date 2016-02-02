/**
 * 
 */
$(function()
{
	var $bar_code = $('#bar_code');
	
	$('#product_order').change(function()
	{
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#lable_barcode").val("");
		$("#QTY").val("");
		$.post("Ajax/Get_ProductNameType_By_Order_Ajax.jsp", {"ProductOrder":GetSelectedContent("product_order"), "Storage_Name":"Semi_Pro_Storage"}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var splitList = data.split("$");
				for (var i = 1; i < (splitList.length - 1)/2; i+=2)
				{
					AddNewSelectItem("product_name", splitList[i]);
					AddNewSelectItem("product_type", splitList[i+1]);
				}
			}
		});
	});
	
	$('#product_name').change(function()
	{
		$bar_code.empty();
		$("#lable_barcode").val("");
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

function InputBarcode(obj)
{
	var checkedBarcode = $("#inputBarcode").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#inputBarcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) > 80000000)
	{
		alert("你输入的不是生产物料, 五金和工具不能报废!");
		return;
	}
	if (parseInt(checkedBarcode) >= 50000000 && parseInt(checkedBarcode) < 60000000)
	{
		checkedBarcode = parseInt(checkedBarcode) + 10000000;
	}
	else if (parseInt(checkedBarcode) >= 70000000 && parseInt(checkedBarcode) < 80000000)
	{
		checkedBarcode = parseInt(checkedBarcode) - 10000000;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#bar_code").empty();
			AddNewSelectItem("bar_code", checkedBarcode);
			$("#product_type").empty();
			var keyWord = proInfoList[2];
			AddNewSelectItem("product_type", keyWord);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
}