/**
 * 
 */
var inputHead = ["库名", "类别", "产品名称", "八码", "入库数量", "原材料单重", "成品单重", "单价", "备注", "供应商"];

function InputBarcode(obj)
{
	var checkedBarcode = $("#barcode").val();
	if(checkedBarcode == null || checkedBarcode.length != 8)
	{
		$("#barcode").val("");
		return;
	}
	if (parseInt(checkedBarcode) < 50000000 || parseInt(checkedBarcode) >= 80000000)
	{
		alert("只能输入八码(大于等于50000000或小于80000000)!");
		$("#barcode").val("");
		return;
	}
	$.post("Ajax/Get_ProName_By_Barcode_Ajax.jsp", {"Bar_Code":checkedBarcode}, function(data, textStatus)
	{
		if (CheckAjaxResult(textStatus, data))
		{
			var proInfoList = data.split("$");
			$("#store_name_addproduct").val(proInfoList[1]);
			$("#store_name_addproduct").change();
			$("#product_type").empty();
			AddNewSelectItem("product_type", proInfoList[2]);
			$("#product_name").empty();
			AddNewSelectItem("product_name", proInfoList[3]);
			$("#product_name").change();
		}
	});
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
		$.post("Ajax/Submit_Material_Ajax.jsp", {"store_name_addproduct":tab.rows[iRow].cells[1].innerText,
			"product_type":tab.rows[iRow].cells[2].innerText, "productname":tab.rows[iRow].cells[3].innerText,
			"barcode":tab.rows[iRow].cells[4].innerText, "QTY":tab.rows[iRow].cells[5].innerText,
			"WeightUnit":tab.rows[iRow].cells[6].innerText, "ProductWeight":tab.rows[iRow].cells[7].innerText, 
			"PriceUnit":tab.rows[iRow].cells[8].innerText, "Description":tab.rows[iRow].cells[9].innerText,
			"supplier_name":tab.rows[iRow].cells[10].innerText,"SubmitDate":addDate},
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
