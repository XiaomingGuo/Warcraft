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

function changePOName(obj)
{
	var $displayOrder = $("#display_po");
	var $confirmOrder = $("#confirm_po");
	var po_name = $.trim($("#POName").val());
	if (po_name.length < 6)
	{
		alert("你就不能取个长点儿的PO单号吗?");
		return;
	}
	$.post("Ajax/Query_PO_Item_Ajax.jsp", {"po_name":po_name, "status":"0"}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$displayOrder.empty();
			$confirmOrder.empty();
			var Count = 0;
			var data_list = data.split("$");
			var status = data_list[1], iColCount = data_list[2], iRowCount = data_list[3];
			if (status != "null")
			{
				$("#confirm_button").attr("disabled", "disabled");
			}
			else
			{
				$("#confirm_button").removeAttr("disabled");
			}
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 3] + "</th>");
					tr.append(th);
				}
				$displayOrder.append(tr);
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						if (0 == iColCount - iCol)
						{
							if(status == "null")
							{
								td.append("<input type='button' value='删除' name=" + data_list[iRow*iColCount + iCol + 3] + " onclick=deleteRecord(this)>");
							}
							else
							{
								td.append("<label>已录入</label>");
							}
						}
						else
						{
							td.append(data_list[iRow*iColCount + iCol + 3]);
						}
						if(3 == iColCount - iCol)
						{
							Count += parseInt(data_list[iRow*iColCount + iCol + 3]);
						}
						tr.append(td);
					}
					$displayOrder.append(tr);
				}
				
				var cmdtr = $("<tr></tr>");
				if (status == "null")
				{
					//cmdtr.append("<td><input align='middle' type='submit' value='录入订单'></td>");
				}
				if (Count > 0)
				{
					cmdtr.append("<td><input align='middle' type='button' onclick=CreatePO(this) value='生成采购单'></td>");
				}
				$confirmOrder.append(cmdtr);
			}
		}
	});
}

function addpoitem(obj)
{
	var po_name = $("#POName").val();
	if(po_name==""||$("#barcode").val() == null||$("#barcode").val() == ""||$("#delivery_date").val().length != 8||parseInt($("#order_QTY").val()) <= 0||$("#vendor_name").find("option:selected").text().indexOf("请选择") >= 0)
	{
		alert("能输入点儿正常值不?");
		return;
	}
	$.post("Ajax/Add_PO_Item_Ajax.jsp", {"bar_code":$("#barcode").val(), "delivery_date":$("#delivery_date").val(), "cpo_QTY":$("#cpo_QTY").val(), "percent":$("#percent").val(), "vendor_name":$("#vendor_name").find("option:selected").text(), "po_name":po_name}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changePOName();
		}
	});
}

function deleteRecord(obj)
{
	var delID = obj.name;
	$.post("Ajax/Del_PO_Item_Ajax.jsp", {"product_id":delID}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			changePOName();
		}
	});
}

function Qty_Calc(obj)
{
	var poCount = parseInt($("#cpo_QTY").val());
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
		$("#cpo_QTY").val("");
	}
}

function CreatePO(obj)
{
	var po_name = $("#POName").val();
	location.href ="List_Purchase.jsp?PO_Name="+po_name;
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

