/**
 * 
 */
function CheckInputValue(tableName, keyWord, inputVal, buttonName)
{
	var KeyVal = $("#"+inputVal).val();
	if (KeyVal != null&&KeyVal == "")
	{
		alert(keyWord+"不能为空!");
		$("#"+inputVal).val("");
		return;
	}
	$.post("Ajax/Check_Input_Ajax.jsp", {"TableName":tableName, "KeyWord":keyWord, "KeyVal":KeyVal}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			if (parseInt(data.split('$')[1]) > 0)
			{
				alert(keyWord+"已经存在或查询数据库异常!");
				$("#"+inputVal).val("");
				DisableButton(buttonName);
				return;
			}
			EnableButton(buttonName);
		}
	});
}

function CheckAjaxResult(textStatus, data)
{
	if (textStatus == "success" && data.indexOf("error") < 0)
	{
		return true;
	}
	else
	{
		alert(data.split("$")[1]);
		return false;
	}
}

function IsProductionMaterial(Barcode)
{
	if (parseInt(Barcode) >= 50000000 && parseInt(Barcode) < 80000000)
	{
		return true;
	}
	return false;
}

function ReplaceInputWithProductBarcode(Barcode)
{
	if (parseInt(Barcode) >= 50000000 && parseInt(Barcode) < 60000000)
		return parseInt(Barcode) + 10000000;
	else if(parseInt(Barcode) >= 70000000 && parseInt(Barcode) < 80000000)
		return parseInt(Barcode) - 10000000;
	return parseInt(Barcode);
}

function ReplaceInputWithMaterialBarcode(Barcode)
{
	if (parseInt(Barcode) >= 60000000 && parseInt(Barcode) < 70000000)
		return parseInt(Barcode) - 10000000;
	else if(parseInt(Barcode) >= 70000000 && parseInt(Barcode) < 80000000)
		return parseInt(Barcode) - 20000000;
	return parseInt(Barcode);
}

function ReplaceInputWithProcessBarcode(Barcode)
{
	if (parseInt(Barcode) >= 50000000 && parseInt(Barcode) < 60000000)
		return parseInt(Barcode) + 20000000;
	else if(parseInt(Barcode) >= 60000000 && parseInt(Barcode) < 70000000)
		return parseInt(Barcode) + 10000000;
	return parseInt(Barcode);
}

function GetSelectedContent(keyWord)
{
	return $("#"+keyWord).find("option:selected").text();
}

function AddNewSelectItem(keyWord, value)
{
	var newOption = $("<option>" + value + "</option>");
	$(newOption).val(value);
	$("#"+keyWord).append(newOption);
}

function ClearSelectContent(keyWord)
{
	$("#"+keyWord).empty();
	$("#"+keyWord).append('<option value="请选择">--请选择--</option>');
}

function CreateTabCellContext(keyWord, value)
{
    var myCurrentCell = document.createElement(keyWord);
    myCurrentCell.innerHTML = value;
    return myCurrentCell;
}

function DisableButton(keyWord)
{
	$("#"+keyWord).attr("disabled", "disabled");
}

function EnableButton(keyWord)
{
	$("#"+keyWord).removeAttr("disabled");
}