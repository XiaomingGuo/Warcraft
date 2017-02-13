/**
 * 
 */
var displayHead = ["ID", "姓名", "工号", "部门", "选择班次", "开始日期", "结束日期", "操作"];
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
    var beginDate = dojo.widget.byId("BeginDate").inputNode.value;
    var endDate = dojo.widget.byId("EndDate").inputNode.value;
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
                    var rowContent = [index+iRow-1, data_list[iColCount*iRow+4], data_list[iColCount*iRow+5], data_list[iColCount*iRow+6], workGroup, beginDate, endDate];
                    tab.appendChild(GenDisplayRowContent(rowContent));
                }
            }
        });
    }
    else
    {
        var rowContent = [index, userName, $('#UserID').val(), department, workGroup, beginDate, endDate];
        tab.appendChild(GenDisplayRowContent(rowContent));
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
    for(var iRow=1; iRow < tab.rows.length; iRow++)
    {
        $.post("Submit/Submit_Arrange_Check_In_Data_Ajax.jsp", {"userId":tab.rows[iRow].cells[2].innerText, "WorkGroup":tab.rows[iRow].cells[4].innerText,
                                                                "BeginDate":tab.rows[iRow].cells[5].innerText, "EndDate":tab.rows[iRow].cells[6].innerText}, function(data, textStatus)
        {
            if (!CheckAjaxResult(textStatus, data))
                alert(data);
        });
    }
    while(tab.rows.length > 0)
    {
        tab.deleteRow(0);
    }
}

function BrowseFolder()
{
	try
	{
		var Message = "请选择文件夹";//选择框提示信息
		var Shell = new ActiveXObject( "Shell.Application" );
		var Folder = Shell.BrowseForFolder(0,Message,0x0040,0x11);//起始目录为：我的电脑
		//var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
		if(Folder != null)
		{
			Folder = Folder.items();  // 返回 FolderItems 对象
			Folder = Folder.item();  // 返回 Folderitem 对象
			Folder = Folder.Path;   // 返回路径
			if(Folder.charAt(Folder.length-1) != "//")
			{
				Folder = Folder + "//";
			}
			document.getElementById("savePath").value=Folder;//savePath是指输出的控件id。
			return Folder;
		}
	}
	catch(e)
	{ 
		alert(e.message);
	}
}
