/**
 * 
 */
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