/**
 * 
 */
$(function()
{
	var $store_name = $('#store_name');
	var $product_type = $('#product_type');
	var $product_name = $('#product_name');
	var $bar_code = $('#bar_code');
	var $Total_QTY = $('#Total_QTY');
	
	$store_name.change(function()
	{
		$product_type.empty();
		$product_name.empty();
		$bar_code.val("");
		$product_type.append('<option value="请选择">--请选择--</option>');
		$product_name.append('<option value="请选择">--请选择--</option>');
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":$("#store_name").find("option:selected").text()}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var data_list = data.split("#");
				var pro_list = data_list[1].split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					var newOption = $("<option>" + pro_list[i] + "</option>");
					$(newOption).val(pro_list[i]);
					$product_type.append(newOption);
				}
			}
		});
	});
	
	$product_type.change(function()
	{
		$product_name.empty();
		$bar_code.val("");
		$product_name.append('<option value="请选择">--请选择--</option>');
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":$("#product_type").find("option:selected").text()}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length - 1; i++)
				{
					var newOption = $("<option>" + pro_list[i] + "</option>");
					$(newOption).val(pro_list[i]);
					$product_name.append(newOption);
				}
			}
		});
	});
	
	$product_name.change(function()
	{
		$bar_code.empty();
		$.post("Ajax/App_Pro_QTY_Ajax.jsp", {"product_name":$("#product_name").find("option:selected").text(),"product_type":$("#product_type").find("option:selected").text(), "storage":"other_storage"}, function(data, textStatus)
		{
			if (textStatus == "success")
			{
				var code_list = data.split("$");
				$bar_code.val(code_list[1]);
				$Total_QTY.attr("value", code_list[4]);
			}
		});
	});				
});

function InputBarcode(obj)
{
	var checkedBarcode = $("#bar_code").val();
	if(checkedBarcode == null||checkedBarcode == "" || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) > 50000000 && parseInt(checkedBarcode) < 70000000)
	{
		alert("注意不能申请生产物料!");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (textStatus == "success" && data.indexOf("error") < 0)
		{
			var proInfoList = data.split("$");
			$("#store_name").val(proInfoList[1]);
			$("#product_type").empty();
			var newOption = $("<option>" + proInfoList[2] + "</option>");
			$(newOption).val(proInfoList[2]);
			$("#product_type").append(newOption);
			$("#product_name").empty();
			var newOption = $("<option>" + proInfoList[3] + "</option>");
			$(newOption).val(proInfoList[3]);
			$("#product_name").append(newOption);
			$("#product_name").change();
		}
		else
		{
			alert(data.split("$")[1]);
		}
	});
}