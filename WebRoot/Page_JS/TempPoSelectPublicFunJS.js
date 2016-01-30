/**
 * 
 */
$(function()
{
	$('#POName').change(function()
	{
		changePOName();
	});
});

function AddToMaterialStorage(obj)
{
	var tempList = obj.name.split('$');
	var storeQTY = $("#"+tempList[1]).val();

	if(parseInt(storeQTY) <= 0)
	{
		alert("物料数量怎么能小于等于零呢？");
		return;
	}
	var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
	//$("#"+valueList[1]+"_Button").attr("disabled", "disabled");
	$.post("Ajax/AddMFGMaterial_ReferTo_PO_Ajax.jsp", {"mb_material_po_id":tempList[0], "PutInQTY":storeQTY, "AddDate":addDate}, function(data, textStatus)
	{
		if (!(textStatus == "success"))
		{
			alert(data);
		}
		location.href ="AddMFGMaterial_ReferTo_PO.jsp?POName="+data.split('$')[1];
	});
}

function CheckQTY(obj)
{
	var tempList = obj.name.split('$');
	if (parseInt(obj.value)+parseInt(tempList[1]) > parseInt(tempList[0]))
	{
		alert("入库数量不能大于生产单量!");
		obj.value = 0;
	}
}
