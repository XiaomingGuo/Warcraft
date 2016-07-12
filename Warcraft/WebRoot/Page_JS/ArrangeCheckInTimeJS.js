/**
 * 
 */
function OnloadDisplay()
{
    var $displayOrder = $("#display_info");
    $.post("Ajax/Query_Arrange_Check_In_Time_Ajax.jsp", {}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayOrder.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]), iWorkGroup = parseInt(data_list[3]);
            if (iColCount > 0&&iRowCount > 0&&iWorkGroup > 0)
            {
                var tr = $("<tr></tr>");
                for (var iHead = 1; iHead <= iColCount; iHead++)
                {
                    var th = $("<th>" + data_list[iHead + 3] + "</th>");
                    tr.append(th);
                }
                $displayOrder.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = $("<td></td>");
                        if(iColCount - iCol == 0)
                            td.append("<input type='button' value='确认' name='" + data_list[iRow*iColCount + iCol + 3] +
                                    "' onclick=SubmitCheckInData(this)>");
                        else if(iColCount - iCol == 1)
                        {
                            var selectItem = data_list[iRow*iColCount + iCol + 3].split("#");
                            var appendString = "<select name='WorkGroup" + data_list[iRow*iColCount + iCol + 4] + "' id='WorkGroup" + data_list[iRow*iColCount + iCol + 4] + "' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 4)
                        {
                            var selectItem = data_list[iRow*iColCount + iCol + 3].split("#");
                            var appendString = "<select name='WorkGroup" + data_list[iRow*iColCount + iCol + 4] + "' id='WorkGroup" + data_list[iRow*iColCount + iCol + 4] + "' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else
                            td.append(data_list[iRow*iColCount + iCol + 3]);
                        tr.append(td);
                    }
                    $displayOrder.append(tr);
                }
            }
        }
    });
}

function SubmitCheckInData(obj)
{
    var workGroup = GetSelectedContent("WorkGroup"+obj.name);
    if(workGroup.indexOf("请选择") >= 0)
    {
        alert("请选择班次信息!");
        return;
    }
    $.post("Ajax/Submit_Arrange_Check_In_Data_Ajax.jsp", {"userId":obj.name, "WorkGroup":workGroup}, function(data, textStatus)
    {
        if (!CheckAjaxResult(textStatus, data))
        {
            alert(data);
        }
        else
        {
            alert("完成排班");
        }
    });
}
