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

function CheckSubmitInfo()
{
	if(GetSelectedContent("store_name").indexOf("请选择")>0||GetSelectedContent("product_type").indexOf("请选择")>0||GetSelectedContent("product_name").indexOf("请选择")>0||
			$("#user_name").val() == null||$("#QTY").val() == null||$("#Total_QTY").val() == null)
	{
		return false;
	}
	if(parseInt($("#QTY").val()) <= 0||parseInt($("#QTY").val()) > parseInt($("#Total_QTY").val()))
	{
		return false;
	}
	return true;
}

function addappitem(obj)
{
	if(!CheckSubmitInfo())
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	var tab = document.getElementById('display_app');
	var inputTab = document.getElementById('inputTab');
	var sampleCount = inputTab.rows[0].cells.length;
    if(1 > tab.rows.length)
	{
        var myHeadRow = document.createElement("tr");
        myHeadRow.setAttribute("align", "center");
    	myHeadRow.appendChild(CreateTabCellContext("th", "ID"));
        for(var iCol=0; iCol < sampleCount-2; iCol++)
    	{
        	myHeadRow.appendChild(CreateTabCellContext("th", inputTab.rows[0].cells[iCol].innerText));
    	}
        myHeadRow.appendChild(CreateTabCellContext("th", inputTab.rows[0].cells[sampleCount-1].innerText));
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
    	else if("名称" == tab.rows[0].cells[iCol].innerText)
		{
    		val = GetSelectedContent("product_name");
		}
    	else if("八码" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#bar_code").val();
		}
    	else if("使用者" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#user_name").val();
		}
    	else if("数量" == tab.rows[0].cells[iCol].innerText)
		{
    		val = $("#QTY").val();
		}
    	myCurrentRow.appendChild(CreateTabCellContext("td", val));
	}
    myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ index +"' value='删除' onclick='delappitem(this)'>"));
    tab.appendChild(myCurrentRow);
}

function delappitem(obj)
{
	var tab = document.getElementById('display_app');
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
		tab.rows[i].cells[tab.rows[i].cells.length-1].innerHTML="<input align='middle' type='button' name='"+ i +"' value='删除' onclick='delappitem(this)'>";  
	}
}

function submitOtherApp()
{
	if(!CheckSubmitInfo())
	{
		alert("申领数量超出库存数量或申领信息填写不完整!");
		return;
	}
	var tab = document.getElementById('display_app');
	for(var iRow=1; iRow < tab.rows.length; iRow++)
	{
		$.post("Ajax/Submit_Application_Ajax.jsp", {"product_type":tab.rows[iRow].cells[2].innerText, "product_name":tab.rows[iRow].cells[3].innerText,
			"bar_code":tab.rows[iRow].cells[4].innerText, "user_name":tab.rows[iRow].cells[5].innerText, "QTY":tab.rows[iRow].cells[6].innerText},
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

