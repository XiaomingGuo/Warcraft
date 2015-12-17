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
		ClearSelectContent("supplier_name");
		ClearSelectContent("product_type");
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#productname").val("");
		$("#barcode").val("");
		$("#QTY").val("");
		$("#WeightUnit").val("");
		$("#PriceUnit").val("");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name")}, function(data, textStatus)
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
				$("#store_name").val("原材料库");
			}
			else
			{
				$("#store_name").val(proInfoList[1]);
			}
			$("#store_name").change();
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

function CheckSubmitInfo()
{
	if(GetSelectedContent("store_name").indexOf("请选择")>0||GetSelectedContent("product_type").indexOf("请选择")>0||GetSelectedContent("supplier_name").indexOf("请选择")>0||
			$("#productname").val() == ""||$("#barcode").val() == ""||$("#QTY").val() == ""||$("#PriceUnit").val() == ""||$("#Description").val() == "")
	{
		return false;
	}
	if(parseInt($("#QTY").val()) <= 0||parseInt($("#PriceUnit").val()) <= 0)
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
	var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "单重", "单价", "备注", "供应商"];
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
    		val = GetSelectedContent("store_name");
		}
    	else if("类别" == tab.rows[0].cells[iCol].innerText)
		{
    		val = GetSelectedContent("product_type");
		}
    	else if("产品名称" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#productname").val();
		}
    	else if("八码" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#barcode").val();
		}
    	else if("入库数量" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#QTY").val();
		}
    	else if("单重" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#WeightUnit").val();
		}
    	else if("单价" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#PriceUnit").val();
		}
    	else if("备注" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#Description").val();
		}
    	else if("供应商" == tab.rows[0].cells[iCol].innerText)
		{
    		val = GetSelectedContent("supplier_name");
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

function AddMaterialFun()
{
	var tab = document.getElementById('display_add');
	if(tab.rows.length < 2)
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		$.post("Ajax/Submit_Material_Ajax.jsp", {"store_name":tab.rows[iRow].cells[1].innerText,
			"product_type":tab.rows[iRow].cells[2].innerText, "productname":tab.rows[iRow].cells[3].innerText,
			"barcode":tab.rows[iRow].cells[4].innerText, "QTY":tab.rows[iRow].cells[5].innerText,
			"WeightUnit":tab.rows[iRow].cells[6].innerText, "PriceUnit":tab.rows[iRow].cells[7].innerText,
			"Description":tab.rows[iRow].cells[8].innerText, "supplier_name":tab.rows[iRow].cells[9].innerText,
			"SubmitDate":addDate},
			function(data, textStatus)
		{
			if (!CheckAjaxResult(textStatus, data))
			{
				alert(data);
				return;
			}
		});
	}
	while(tab.rows.length > 0)
	{
		tab.deleteRow(0);
	}
}
