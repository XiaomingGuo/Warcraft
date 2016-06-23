/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "原材料单重", "成品单重", "备注"];

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
	var from_barcode = GetSelectedContent("from_bar_code");
	var to_barcode = GetSelectedContent("to_bar_code");
	var tab = document.getElementById('display_add');
	if(tab.rows.length < 2)
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	if(from_store_name.indexOf("请选择") > 0||to_store_name.indexOf("请选择") > 0||
			$("#from_QTY").val() == ""||$("#to_QTY").val() == ""||
			from_barcode.indexOf("请选择") > 0||to_barcode.indexOf("请选择") > 0)
	{
		alert("信息输入不完整!");
		return;
	}
	if(parseInt($("#from_QTY").val()) > parseInt($("#store_QTY").val()))
	{
		alert("物料库存不足!");
		return;
	}
	if(from_store_name != to_store_name)
	{
		alert("必须在同一库中进行物料转换!");
		return;
	}
	if(from_barcode == to_barcode)
	{
		alert("同一种物料无需转换!");
		return;
	}
	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		$.post("Ajax/TransferMFGMaterialBarcode_Ajax.jsp", {"store_name":from_store_name,
			"from_bar_code":from_barcode, "to_bar_code":tab.rows[iRow].cells[4].innerText,
			"from_QTY":$("#from_QTY").val(), "to_QTY":tab.rows[iRow].cells[5].innerText, "saveFromFlag":iRow-1}, function(data, textStatus)
		{
			if (!CheckAjaxResult(textStatus, data))
			{
				return;
			}
		});
	}
	while(tab.rows.length > 0)
	{
		tab.deleteRow(0);
	}
}

function CheckSubmitInfo()
{
	if(GetSelectedContent("to_store_name").indexOf("请选择")>0||GetSelectedContent("to_product_type").indexOf("请选择")>0||$("#to_productname").val() == ""||
			$("#to_barcode").val() == ""||$("#to_QTY").val() == ""||$("#to_WeightUnit").val() == ""||$("#to_Description").val() == "")
	{
		return false;
	}
	if(parseInt($("#to_QTY").val()) <= 0)
	{
		return false;
	}
	return true;
}

function additem(obj)
{
	if(!CheckSubmitInfo())
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	var tab = document.getElementById('display_add');
	var sampleCount = inputHead.length;
	if(1 > tab.rows.length)
	{
		var myHeadRow = document.createElement("tr");
		myHeadRow.setAttribute("align", "center");
		myHeadRow.appendChild(CreateTabCellContext("th", "ID"));
		for(var iCol=0; iCol < sampleCount; iCol++)
		{
			myHeadRow.appendChild(CreateTabCellContext("th", inputHead[iCol]));
		}
		myHeadRow.appendChild(CreateTabCellContext("th", "操作"));
		tab.appendChild(myHeadRow);
	}

	var myCurrentRow = document.createElement("tr");
	var index = tab.rows.length;
	myCurrentRow.appendChild(CreateTabCellContext("td", index));
	for(var iCol=1; iCol < tab.rows[0].cells.length-1; iCol++)
	{
		var val = "";
		if("库名" == tab.rows[0].cells[iCol].innerText)
		{
			val = GetSelectedContent("to_store_name");
		}
		else if("类别" == tab.rows[0].cells[iCol].innerText)
		{
			val = GetSelectedContent("to_product_type");
		}
		else if("产品名称" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_productname").val();
		}
		else if("八码" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_barcode").val();
		}
		else if("入库数量" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_QTY").val();
		}
		else if("原材料单重" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_WeightUnit").val();
		}
		else if("成品单重" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_ProductWeight").val();
		}
		else if("备注" == tab.rows[0].cells[iCol].innerText)
		{
			val = $("#to_Description").val();
		}
		myCurrentRow.appendChild(CreateTabCellContext("td", val));
	}
	myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ index +"' value='删除' onclick='deladditem(this)'>"));
	tab.appendChild(myCurrentRow);
}

function deladditem(obj)
{
	var tab = document.getElementById('display_add');
	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		if(tab.rows[iRow].cells[0].innerText == obj.name)
		{
			tab.deleteRow(iRow);
			if(tab.rows.length == 1)
				tab.deleteRow(0);
			break;
		}
	}
	initRows(tab);
}

function initRows(tab)
{
	var tabRows = tab.rows.length;  
	for(var i = 1; i<tabRows; i++)
	{
		tab.rows[i].cells[0].innerText=i;
		tab.rows[i].cells[tab.rows[i].cells.length-1].innerHTML="<input align='middle' type='button' name='"+ i +"' value='删除' onclick='deladditem(this)'>";  
	}
}

