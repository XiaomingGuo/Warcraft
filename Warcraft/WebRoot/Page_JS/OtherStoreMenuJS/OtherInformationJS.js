/**
 * 
 */
function DisplayStorageList()
{
	QueryAllRepertory($.trim($("#store_name").val()), $.trim($("#product_type").val()), $.trim($("#product_name").val()), $.trim($("#bar_code").val()));
}

function DisplayStorageList_New(obj)
{
	var valueList = obj.name.split('#');
	QueryAllRepertory($.trim(valueList[0]), $.trim(valueList[1]), "--请选择--", "10011001");
}

function QueryAllRepertory(storage_name, product_type, product_name, bar_code)
{
	if (storage_name.indexOf("请选择") >= 0&&product_type.indexOf("请选择") >= 0&&product_name.indexOf("请选择") >= 0&&bar_code == "")
	{
		alert("啥都不输入难道你要我给你查空气?");
		return;
	}
	$.post("Ajax/OtherStoreMenu/Query_Storage_Item_Ajax.jsp", {"storage_name":storage_name, "product_type":product_type, "product_name":product_name, "bar_code":bar_code}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$("#display_storage").empty();
			var data_list = data.split("$");
			var iColCount = data_list[1], iRowCount = data_list[2];
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 2] + "</th>");
					tr.append(th);
				}
				$("#display_storage").append(tr);
				for(var iRow = 1; iRow <= iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						td.append(data_list[iRow*iColCount + iCol + 2]);
						tr.append(td);
					}
					$("#display_storage").append(tr);
				}
			}
		}
	});
}

function DisplayInformation(obj)
{
	var valueList = obj.name.split('#');
	var storage_name = $.trim(valueList[0]);
	var product_type = $.trim(valueList[1]);
	$.post("Ajax/OtherStoreMenu/Query_Storage_Info_Ajax.jsp", {"storage_name":storage_name, "product_type":product_type}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			$("#display_storage").empty();
			var data_list = data.split("$");
			var iColCount = data_list[1], iRowCount = data_list[2];
			if (iColCount > 0&&iRowCount > 0)
			{
				var tr = $("<tr></tr>");
				for (var iHead = 1; iHead <= iColCount; iHead++)
				{
					var th = $("<th>" + data_list[iHead + 2] + "</th>");
					tr.append(th);
				}
				tr.append("<th>操作</th>");
				$("#display_storage").append(tr);
				for(var iRow = 1; iRow < iRowCount; iRow++)
				{
					var tr = $("<tr></tr>");
					for (var iCol = 1; iCol <= iColCount; iCol++)
					{
						var td = $("<td></td>");
						td.append(data_list[iRow*iColCount + iCol + 2]);
						tr.append(td);
					}
					tr.append("<input type='button' value='修改' name="+data_list[iRow*iColCount + 3]+" id="+iRow+"'_App' onclick='ModifyRecord(this)'>");
					$("#display_storage").append(tr);
				}
			}
		}
	});
}

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
	return true;
}

function ModifyRecord(obj)
{
    var iRow = parseInt(obj.name);
    var modifytab = document.getElementById('modify_info');
    var displaytab = document.getElementById('display_storage');
    modifytab.rows[1].cells[0].innerText=iRow;
    $("#proName").val(displaytab.rows[iRow].cells[1].innerText);
    $("#barcode").val(displaytab.rows[iRow].cells[2].innerText);
    
    modifytab.rows[1].cells[3].innerText=displaytab.rows[iRow].cells[3].innerText;
    modifytab.rows[1].cells[4].innerText=displaytab.rows[iRow].cells[4].innerText;
    modifytab.rows[1].cells[5].innerText=displaytab.rows[iRow].cells[5].innerText;
    modifytab.rows[1].cells[6].innerText=displaytab.rows[iRow].cells[6].innerText;
    var index = 0;
    $("#VendorName").val("--请选择--");
    $("#VendorName option").each(function()
    {
        if($(this).text()==displaytab.rows[iRow].cells[7].innerText)
        {
            VendorName.options[index].selected = true;
        }
        index++;
    });
    modifytab.rows[1].cells[8].innerText=displaytab.rows[iRow].cells[8].innerText;
    $("#Description").val(displaytab.rows[iRow].cells[9].innerText);
    alert(displaytab.rows[iRow].cells[10].innerText);
}

function ExecModify()
{
    $.post("Ajax/PersonalMenu/Update_OtherInformation_Ajax.jsp", {"Pro_Name":$("#proName").val(), "Bar_Code":$("#barcode").val(), "Description": $("#Description").val()}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert("更新记录出错!");
        }
        location.reload();
    });
}
