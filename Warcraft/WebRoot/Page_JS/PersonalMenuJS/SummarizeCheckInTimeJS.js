/**
 * 
 */
function changeUserName()
{
    var $displayOrder = $("#display_po");
    var $hiddenTable = $("#hidden_table");
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    $.post("Ajax/PersonalMenu/Query_Summarize_Check_In_Data_Ajax.jsp", {"User_ID":$.trim($("#UserID").val()), "User_Name":GetSelectedContent("UserName"), "queryDate": addDate}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayOrder.empty();
            $("#detail_display").empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                $displayOrder.append(GenPersonMenuInputTableHeadTitle(data_list));
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = $("<td></td>");
                        if(4 == iCol)
                            td.append("<a name='" + data_list[iRow*iColCount + 3] + "' href='javascript:void(0);' onclick='DisplayMissCheckIn(this)'>"+data_list[iRow*iColCount + iCol + 2]+"</a>");//
                        else if(5 == iCol)
                            td.append("<a name='" + data_list[iRow*iColCount + 3] + "' href='javascript:void(0);' onclick='DisplayBeLateAndLeaveEarly(this)'>"+data_list[iRow*iColCount + iCol + 2]+"</a>");//
                        else
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                            //td.append("<input type='text' name='" + iRow.toString() + iCol.toString() + "' value='"+data_list[iRow*iColCount + iCol + 2]+"' style='width:100'>");
                        tr.append(td);
                    }
                    $displayOrder.append(tr);
                    
                    var hidtr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = $("<td></td>");
                        td.append("<input type='hidden' name='" + iRow.toString() + iCol.toString() + "' value='"+data_list[iRow*iColCount + iCol + 2]+"'>");
                        hidtr.append(td);
                    }
                    $hiddenTable.append(hidtr);
                }
            }
        }
    });
}

function DisplayQueryCheckInData(iRowId, ajaxName)
{
    var tab = document.getElementById('display_po');
    var $displayDetail = $("#detail_display");
    var addDate = dojo.widget.byId("SubmitDate").inputNode.value;
    $.post("Ajax/PersonalMenu/" + ajaxName + ".jsp", {"User_ID":tab.rows[iRowId].cells[2].innerText,
                                    "User_Name":tab.rows[iRowId].cells[1].innerText, "queryDate": addDate}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayDetail.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                $displayDetail.append(GenPersonMenuInputTableHeadTitle(data_list));
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                        tr.append($("<td></td>").append(data_list[iRow*iColCount + iCol + 2]));
                    $displayDetail.append(tr);
                }
            }
        }
    });
}

function DisplayBeLateAndLeaveEarly(obj)
{
    DisplayQueryCheckInData(parseInt(obj.name), "Query_BeLate_LeaveEarly_Data_Ajax");
}

function DisplayMissCheckIn(obj)
{
    DisplayQueryCheckInData(parseInt(obj.name), "Query_Miss_Check_In_Data_Ajax");
}

function outExcel()
{
    var table = document.all.display_po;
    alert(table.rows.item(0).cells.length);
    alert(table.rows.length);
    excelApp.visible = true;
    var objBook=excelApp.Workbooks.Add();
    var objSheet = objBook.ActiveSheet;
    objSheet.Cells(1,1).value=table.rows.item(0).cells.item(0).innerText;
    objBook.SaveAs("C:/TempExcel.xls");
    alert(table.rows.item(0).cells.item(0).innerText);
}
