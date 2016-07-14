/**
 * 
 */
function OnloadDisplay()
{
    var $displayInfo = $("#display_info");
    $.post("Ajax/Query_Arrange_Check_In_Time_Ajax.jsp", {}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $displayInfo.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                var tr = $("<tr></tr>");
                for (var iHead = 1; iHead <= iColCount; iHead++)
                {
                    var th = $("<th>" + data_list[iHead + 2] + "</th>");
                    tr.append(th);
                }
                $displayInfo.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = null;
                        if(iColCount - iCol == 0)
                        {
                            td = $("<td></td>");
                            td.append("<input type='button' value='确认' name='" + data_list[iRow*iColCount + iCol + 2] +
                                    "' onclick=SubmitCheckInData(this)>");
                        }
                        else if(iColCount - iCol == 1)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='WorkGroup' id='WorkGroup' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 2)
                        {
                            td = $("<td name='department' id='department'></td>");
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        }
                        else if(iColCount - iCol == 3)
                        {
                            td = $("<td name='workNum' id='workNum'></td>");
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        }
                        else if(iColCount - iCol == 4)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='UserName' id='UserName' style='width:100px' onchange='ChangeUserName(this.options[this.options.selectedIndex].value)'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        tr.append(td);
                    }
                    $displayInfo.append(tr);
                }
            }
        }
    });
}

function ChangeUserName(obj)
{
    alert(obj);
    var $check_in_list = $("#check_in_list");
    $.post("Ajax/Query_User_Name_Ajax.jsp", {"UserName":obj}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            $check_in_list.empty();
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
            if (iColCount > 0&&iRowCount > 0)
            {
                var tr = $("<tr></tr>");
                for (var iHead = 1; iHead <= iColCount; iHead++)
                {
                    var th = $("<th>" + data_list[iHead + 2] + "</th>");
                    tr.append(th);
                }
                $check_in_list.append(tr);
                for(var iRow = 1; iRow <= iRowCount; iRow++)
                {
                    var tr = $("<tr></tr>");
                    for (var iCol = 1; iCol <= iColCount; iCol++)
                    {
                        var td = null;
                        if(iColCount - iCol == 0)
                        {
                            td = $("<td></td>");
                            td.append("<input type='button' value='确认' name='" + data_list[iRow*iColCount + iCol + 2] +
                                    "' onclick=SubmitCheckInData(this)>");
                        }
                        else if(iColCount - iCol == 1)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='WorkGroup' id='WorkGroup' style='width:100px'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        else if(iColCount - iCol == 2)
                        {
                            td = $("<td name='department' id='department'></td>");
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        }
                        else if(iColCount - iCol == 3)
                        {
                            td = $("<td name='workNum' id='workNum'></td>");
                            td.append(data_list[iRow*iColCount + iCol + 2]);
                        }
                        else if(iColCount - iCol == 4)
                        {
                            td = $("<td></td>");
                            var selectItem = data_list[iRow*iColCount + iCol + 2].split("#");
                            var appendString = "<select name='UserName' id='UserName' style='width:100px' onchange='ChangeUserName(this.options[this.options.selectedIndex].value)'><option value = '--请选择--'>--请选择--</option>";
                            for(var idx = 0; idx < selectItem.length; idx++)
                                appendString += "<option value = " + selectItem[idx] +">" + selectItem[idx] + "</option>";
                            appendString += "</select>";
                            td.append(appendString);
                        }
                        tr.append(td);
                    }
                    $check_in_list.append(tr);
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
