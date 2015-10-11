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
		return false;
	}
}