/**
 * 
 */
var displayHead = ["ID", "姓名", "工号", "部门", "假期类型", "日期", "请假时间(H)", "操作"];

function OnloadDisplay()
{
    PersonMenuOnloadDisplay("Ajax/PersonalMenu/Query_AddHoliday_Ajax.jsp");
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
        }
    });
    $(function()
    {
        $('#WorkGroup').change(function()
        {
            var SetValue = "8";
            if($("#WorkGroup").val() == "上午假"||$("#WorkGroup").val() == "下午假")
            {
                $("#HolidayTime").removeAttr("disabled");
                SetValue = "--请选择--";
            }
            else
            {
                $("#HolidayTime").attr("disabled", "disabled");
            }
            var index = 0;
            $("#HolidayTime option").each(function()
            {
                if($(this).text()==SetValue)
                {
                    HolidayTime.options[index].selected = true;
                }
                index++;
            }); 
        });
    });
}

function EnsureCheckInData(obj)
{
    var department = GetSelectedContent("department");
    var userName = GetSelectedContent("UserName");
    var workGroup = GetSelectedContent("WorkGroup");
    var holidayDate = dojo.widget.byId("AddDate").inputNode.value;
    var holidayTime = GetSelectedContent("HolidayTime");
    if(workGroup.indexOf("请选择") >= 0)
    {
        alert("请选择假期类型!");
        return;
    }
    var tab = document.getElementById('check_in_list');
    if(1 > tab.rows.length)
        tab.appendChild(GenDisplayHeadRowContent(displayHead));
    
    var index = tab.rows.length;
    if(userName.indexOf("请选择") >= 0||department.indexOf("请选择") >= 0)
    {
        $.post("Ajax/PersonalMenu/Query_AllUserInfo_Ajax.jsp", {"UserName":userName, "Department":department, "HolidayTime":holidayTime}, function(data, textStatus)
        {
            if (CheckAjaxResult(textStatus, data))
            {
                var data_list = data.split("$");
                var iColCount = parseInt(data_list[1]), iRowCount = parseInt(data_list[2]);
                for(var iRow=1; iRow < iRowCount+1; iRow++)
                {
                    var rowContent = [index+iRow-1, data_list[iColCount*iRow+4], data_list[iColCount*iRow+5], data_list[iColCount*iRow+6], workGroup, holidayDate];
                    tab.appendChild(GenDisplayRowContent(rowContent));
                }
            }
        });
    }
    else
    {
        if(holidayTime.indexOf("请选择") < 0)
        {
            var rowContent = [index, userName, $('#UserID').val(), department, workGroup, holidayDate, holidayTime];
            tab.appendChild(GenDisplayRowContent(rowContent));
        }
        else
            alert("请选择请假时间!");
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
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Add_Holidays.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "HolidayType":tab.rows[iRow].cells[4].innerText,
                                            "AddDate":tab.rows[iRow].cells[5].innerText, "HolidayTime":tab.rows[iRow].cells[6].innerText}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
            {
                return;
            }
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}
