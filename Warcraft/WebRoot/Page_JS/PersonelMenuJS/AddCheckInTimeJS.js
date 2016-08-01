/**
 * 
 */
var displayHead = ["ID", "姓名", "工号", "部门", "选择班次", "补卡日期", "补卡时间", "操作"];
function OnloadDisplay()
{
    PersonMenuOnloadDisplay("Ajax/PersonalMenu/Query_Arrange_Check_In_Time_Ajax.jsp");
}

function changeUserName(obj)
{
    $.post("Ajax/PersonalMenu/Query_User_Name_Ajax.jsp", {"UserName":obj}, function(data, textStatus)
    {
        if (CheckAjaxResult(textStatus, data))
        {
            var data_list = data.split("$");
            var iColCount = parseInt(data_list[1]);
            $('#UserID').val(data_list[iColCount+5]);
            
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
    var department = GetSelectedContent("department");
    var userName = GetSelectedContent("UserName");
    var workGroup = GetSelectedContent("WorkGroup");
    var checkInDate = dojo.widget.byId("AddDate").inputNode.value;
    var checkInTime = $('#AddTime').val();
    if(workGroup.indexOf("请选择") >= 0)
    {
        alert("请选择排班班次!");
        return;
    }
    var tab = document.getElementById('check_in_list');
    if(1 > tab.rows.length)
        tab.appendChild(GenDisplayHeadRowContent(displayHead));
    
    var index = tab.rows.length;
    if(userName.indexOf("请选择") >= 0||department.indexOf("请选择") >= 0)
    {
        $.post("Ajax/PersonalMenu/Query_AllUserInfo_Ajax.jsp", {"UserName":userName, "Department":department}, function(data, textStatus)
        {
            if (CheckAjaxResult(textStatus, data))
            {
                var data_list = data.split("$");
                var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
                for(var iRow=1; iRow < iRowCount+1; iRow++)
                {
                    var rowContent = [index+iRow-1, data_list[iColCount*iRow+4], data_list[iColCount*iRow+5], data_list[iColCount*iRow+6], workGroup, checkInDate, checkInTime];
                    tab.appendChild(GenDisplayRowContent(rowContent));
                }
            }
        });
    }
    else
    {
        var rowContent = [index, userName, $('#UserID').val(), department, workGroup, checkInDate, checkInTime];
        tab.appendChild(GenDisplayRowContent(rowContent));
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
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Add_Check_In_Data_Ajax.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "WorkGroup":tab.rows[iRow].cells[4].innerText,
                                                                "AddDate":tab.rows[iRow].cells[5].innerText+"#"+tab.rows[iRow].cells[6].innerText}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
            {
                alert(data);
            }
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}
