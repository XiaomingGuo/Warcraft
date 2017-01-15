/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "原材料单重", "成品单重", "备注"];
$(function()
{
	var $store_name_addproduct = $('#store_name_addproduct');
	var $product_type = $('#product_type');
	
	$store_name_addproduct.change(function()
	{
		ClearSelectContent("product_type");
		$("#productname").val("");
		$("#barcode").val("");
		$("#WeightUnit").val("0");
		$("#ProductWeight").val("0");
		$.post("Ajax/App_Pro_Type_Ajax.jsp", {"FilterKey1":GetSelectedContent("store_name_addproduct")}, function(data, textStatus)
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
	
	$product_type.change(function()
	{
		ClearSelectContent("product_name");
		ClearSelectContent("bar_code");
		$("#productname").val("");
		$("#barcode").val("");
		$("#QTY").val("");
		$("#WeightUnit").val("0");
		$("#WeightUnit").removeAttr("readonly");
		$("#ProductWeight").val("0");
		$("#ProductWeight").removeAttr("readonly");
		$("#PriceUnit").val("");
		$.post("Ajax/App_Pro_Name_Ajax.jsp", {"FilterKey1":GetSelectedContent("product_type")}, function(data, textStatus)
		{
			if (CheckAjaxResult(textStatus, data))
			{
				var pro_list = data.split("$");
				for (var i = 1; i < pro_list.length/2; i++)
				{
					AddNewSelectItem("product_name", pro_list[i]);
				}
			}
		});
	});
});

function checkProductName()
{
	CheckInputValue("Product_Info", "name", "productname", "confirm_button");
}

function checkBarcode(obj)
{
	CheckInputValue("Product_Info", "Bar_Code", "barcode", "confirm_button");
}

function changeProductName(obj)
{
	$("#barcode").val("");
}

function CheckSubmitInfo()
{
	if(GetSelectedContent("store_name_addproduct").indexOf("请选择") > 0||GetSelectedContent("product_type").indexOf("请选择") > 0||
			$("#productname").val() == ""||$("#barcode").val() == ""||$("#WeightUnit").val() == ""||$("#ProductWeight").val() == ""||$("#Description").val() == "")
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
    		val = GetSelectedContent("store_name_addproduct");
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
    	else if("原材料单重" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#WeightUnit").val();
		}
    	else if("成品单重" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#ProductWeight").val();
		}
    	else if("单重" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#WeightUnit").val();
		}
    	else if("备注" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#Description").val();
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
		alert("我没发现有新物料信息要添加啊!");
		return;
	}

	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		$.post("Ajax/Submit_New_Material_Info_Ajax.jsp", {"store_name":tab.rows[iRow].cells[1].innerText,
			"product_type":tab.rows[iRow].cells[2].innerText, "productname":tab.rows[iRow].cells[3].innerText,
			"barcode":tab.rows[iRow].cells[4].innerText, "WeightUnit":tab.rows[iRow].cells[5].innerText,
			"ProductWeight":tab.rows[iRow].cells[6].innerText, "Description":tab.rows[iRow].cells[7].innerText},
			function(data, textStatus)
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

