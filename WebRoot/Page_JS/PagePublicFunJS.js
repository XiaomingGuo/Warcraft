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