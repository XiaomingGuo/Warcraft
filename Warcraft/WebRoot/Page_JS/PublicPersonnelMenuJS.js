/**
 * 
 */
function ChangeUserName(obj)
{
    $.post("Ajax/Query_User_Name_Ajax.jsp", {"UserName":obj}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]);
            $('#workNum').html(data_list[iColCount+5]);
            
            var department = document.getElementById('department');
            var index = 0;
            $("#department option").each(function()
            {
                if($(this).text()==data_list[iColCount+6])
                {
                    department.options[index].selected = true;
                }
                index++;
            }); 
            
            var workGroup = document.getElementById('WorkGroup');
            for(var i=0;i<workGroup.options.length;i++)
            {
                if(i == parseInt(data_list[iColCount+7]))
                {
                    workGroup.options[i].selected = true;
                    break;
                }
            }
        }
    });
}

function EnsureCheckInData(obj)
{
    var workNum = $('#workNum').html();
    var department = GetSelectedContent("department");
    var userName = GetSelectedContent("UserName");
    var workGroup = GetSelectedContent("WorkGroup");
    if(workGroup.indexOf("请选择") >= 0)
    {
        alert("请选择排班班次!");
        return;
    }
    var tab = document.getElementById('check_in_list');
    var displayTab = document.getElementById('display_info');
    var sampleCount = displayTab.rows[0].cells.length;
    if(1 > tab.rows.length)
    {
        var myHeadRow = document.createElement("tr");
        myHeadRow.setAttribute("align", "center");
        for(var iCol=0; iCol < sampleCount; iCol++)
        {
            myHeadRow.appendChild(CreateTabCellContext("th", displayTab.rows[0].cells[iCol].innerText));
        }
        tab.appendChild(myHeadRow);
    }
    var index = tab.rows.length;
    if(userName.indexOf("请选择") >= 0||department.indexOf("请选择") >= 0)
    {
        $.post("Ajax/Query_AllUserInfo_Ajax.jsp", {"UserName":userName, "Department":department}, function(data, textStatus)
        {
            if (CheckAjaxResult(textStatus, data))
            {
                var data_list = data.split("$");
                var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
                for(var iRow=1; iRow < iRowCount+1; iRow++)
                {
                    var myCurrentRow = document.createElement("tr");
                    var rowNum = index+iRow-1;
                    myCurrentRow.appendChild(CreateTabCellContext("td", rowNum));
                    for(var iCol=1; iCol < iColCount-1; iCol++)
                    {
                        var val = "";
                        if("姓名" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+4];
                        }
                        else if("工号" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+5];;
                        }
                        else if("部门" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = data_list[iColCount*iRow+6];
                        }
                        else if("选择班次" == tab.rows[0].cells[iCol].innerText)
                        {
                            val = workGroup;
                        }
                        myCurrentRow.appendChild(CreateTabCellContext("td", val));
                    }
                    myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ rowNum +"' value='删除' onclick='delappitem(this)'>"));
                    tab.appendChild(myCurrentRow);
                }
            }
        });
    }
    else
    {
        var myCurrentRow = document.createElement("tr");
        myCurrentRow.appendChild(CreateTabCellContext("td", index));
        for(var iCol=1; iCol < tab.rows[0].cells.length-1; iCol++)
        {
            var val = "";
            if("姓名" == tab.rows[0].cells[iCol].innerText)
            {
                val = userName;
            }
            else if("工号" == tab.rows[0].cells[iCol].innerText)
            {
                val = workNum;
            }
            else if("部门" == tab.rows[0].cells[iCol].innerText)
            {
                val = department;
            }
            else if("选择班次" == tab.rows[0].cells[iCol].innerText)
            {
                val = workGroup;
            }
            myCurrentRow.appendChild(CreateTabCellContext("td", val));
        }
        myCurrentRow.appendChild(CreateTabCellContext("td", "<input align='middle' type='button' name='"+ index +"' value='删除' onclick='delappitem(this)'>"));
        tab.appendChild(myCurrentRow);
    }
}

function delappitem(obj)
{
    var tab = document.getElementById('check_in_list');
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        if(tab.rows[iRow].cells[0].innerText == obj.name)
        {
            tab.deleteRow(iRow);
            if(tab.rows.length == 1)
                tab.deleteRow(0);
            break;
        }
    }
    initRows(tab);
}

function initRows(tab)
{
    var tabRows = tab.rows.length;  
    for(var i = 1; i<tabRows; i++)
    {
        tab.rows[i].cells[0].innerText=i;
        tab.rows[i].cells[tab.rows[i].cells.length-1].innerHTML="<input align='middle' type='button' name='"+ i +"' value='删除' onclick='delappitem(this)'>";  
    }
}

function SubmitArrangeCheckIn()
{
    var tab = document.getElementById('check_in_list');
    if(tab.rows.length < 2)
    {
        alert("申请人信息填写不完整!");
        return;
    }
    var beginDate = dojo.widget.byId("BeginDate").inputNode.value;
    var endDate = dojo.widget.byId("EndDate").inputNode.value;
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Arrange_Check_In_Data_Ajax.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "WorkGroup":tab.rows[iRow].cells[4].innerText,
                                                                "BeginDate":beginDate, "EndDate":endDate}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
                alert(data);
            else
                alert("完成排班");
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}

function SubmitAddCheckInTime()
{
    var tab = document.getElementById('check_in_list');
    if(tab.rows.length < 2)
    {
        alert("申请人信息填写不完整!");
        return;
    }
    var addDate = dojo.widget.byId("AddDate").inputNode.value;
    var addTime = $("#AddTime").val();
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Add_Check_In_Data_Ajax.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "WorkGroup":tab.rows[iRow].cells[4].innerText,
                                                                "AddDate":addDate+"#"+addTime}, function(data, textStatus)
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
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}

function SubmitAddHolidaysDate()
{
    var tab = document.getElementById('check_in_list');
    if(tab.rows.length < 2)
    {
        alert("申请人信息填写不完整!");
        return;
    }
    var addDate = dojo.widget.byId("AddDate").inputNode.value;
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Add_Holidays.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "AddDate":addDate}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
            {
                alert(data);
            }
            else
            {
                alert("完成添加!");
            }
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}
