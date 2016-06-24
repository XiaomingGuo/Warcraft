package com.jsp.support;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.DB.operation.*;

public class SummarizeCheckInTime extends PageParentClass
{
    String[] m_displayList = {"ID", "姓名", "工号", "日期", "迟到早退总时间", "本日班次"};
    public List<String> GetAllUserName()
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.QueryAllRecord();
        return hUIHandle.getDBRecordList("name");
    }
    
    private String GetCheckInIdFromUserInfo(String user_name)
    {
        User_Info hUIHandle = new User_Info(new EarthquakeManagement());
        hUIHandle.GetRecordByName(user_name);
        return hUIHandle.getDBRecordList("check_in_id").get(0);
    }
    
    private List<String> GetAllCheckInDate(String user_name, String queryDate)
    {
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        
        hCIRDHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("check_in_id"), Arrays.asList(GetCheckInIdFromUserInfo(user_name)), Arrays.asList("check_in_date"));
        if (hCIRDHandle.RecordDBCount() > 0)
        {
            return hCIRDHandle.getDBRecordList("check_in_date");
        }
        return null;
    }
    
    private String PrepareHeader(List<List<String>> recordList)
    {
        String rtnRst = "remove$";
        if (recordList.size() > 0)
        {
            rtnRst += Integer.toString(m_displayList.length) + "$";
            rtnRst += Integer.toString(recordList.get(0).size()) + "$";
            for(int i = 0; i < m_displayList.length; i++)
            {
                rtnRst += m_displayList[i] + "$";
            }
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllDisplayData(String user_name, String queryDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        List<String> dateRecord = GetAllCheckInDate(user_name, queryDate);
        Check_In_Raw_Data hCIRDHandle = new Check_In_Raw_Data(new EarthquakeManagement());
        for(int idx = 0; idx < dateRecord.size(); idx++)
        {
            hCIRDHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("check_in_id", "check_in_date"), 
                    Arrays.asList(GetCheckInIdFromUserInfo(user_name), dateRecord.get(idx)), Arrays.asList("check_in_time"));
            List<List<String>> tempList = new ArrayList<List<String>>();
            String[] sqlKeyList = {"check_in_id", "check_in_date", "check_in_time", "work_group"};
            for(int recordIdx=0; recordIdx < sqlKeyList.length; recordIdx++)
            {
                tempList.add(hCIRDHandle.getDBRecordList(sqlKeyList[recordIdx]));
            }
            List<String> oneDayRecord = GetOneDayDateRecord(tempList);
            for(int writeIdx = 0; writeIdx < oneDayRecord.size(); writeIdx++)
            {
            	List<String> tempList1;
                if(rtnRst.size() < oneDayRecord.size())
                {
                	tempList1 = new ArrayList<String>();
                	tempList1.add(oneDayRecord.get(writeIdx));
                    rtnRst.add(tempList1);
                }
                else
                    rtnRst.get(writeIdx).add(oneDayRecord.get(writeIdx));
            }
        }
        return rtnRst;
    }
    
    private List<String> GetOneDayDateRecord(List<List<String>> recordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        if(recordList.size() > 0)
        {
            rtnRst.add(recordList.get(0).get(0));
            rtnRst.add(recordList.get(1).get(0));
            String checkInTime = recordList.get(2).get(0);
            String checkOutTime = recordList.get(2).get(recordList.get(0).size()-1);
            String groupInTime = GetGroupTime(recordList.get(3).get(0), "check_in_time");
            String groupOutTime = GetGroupTime(recordList.get(3).get(recordList.get(0).size()-1), "check_out_time");
            int timeSpan = 0;
            if(TimeSpan(checkInTime, groupInTime) > 0)
                timeSpan += TimeSpan(checkInTime, groupInTime);
            if(TimeSpan(groupOutTime, checkOutTime) > 0)
                timeSpan += TimeSpan(groupOutTime, checkOutTime);
            rtnRst.add(Integer.toString(timeSpan));
            rtnRst.add(recordList.get(3).get(0));
        }
        return rtnRst;
    }
    
    private long TimeSpan(String beginTime, String endTime)
    {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d1, d2;
        try
        {
            d1 = df.parse(beginTime);
            d2 = df.parse(endTime);
            long diff = d1.getTime() - d2.getTime();
            return diff/(1000*60);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1000;
    }
    
    private String GetGroupTime(String id, String keyWord)
    {
        Work_Group_Info hUIHandle = new Work_Group_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(id));
        return hUIHandle.getDBRecordList(keyWord).get(0);
    }

    public String GenerateReturnString(String user_name, String queryDate)
    {
        List<List<String>> recordList = GetAllDisplayData(user_name, queryDate);
        String rtnRst = PrepareHeader(recordList);
        
        if (recordList.size() > 0)
        {
            //"check_in_id", "check_in_time", "work_group"
            //"ID", "姓名", "工号", "日期", "迟到早退总时间", "本日班次"
            for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
            {
                for(int iCol = 0; iCol < m_displayList.length; iCol++)
                {
                    if("ID" == m_displayList[iCol])
                    {
                        rtnRst += Integer.toString(iRow+1) + "$";
                    }
                    else if("姓名" == m_displayList[iCol])
                    {
                        rtnRst += user_name + "$";
                    }
                    else if("工号" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(0).get(iRow) + "$";
                    }
                    else if("日期" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(1).get(iRow) + "$";
                    }
                    else if("迟到早退总时间" == m_displayList[iCol])
                    {
                        rtnRst += recordList.get(2).get(iRow) + "分$";
                    }
                    else if("本日班次" == m_displayList[iCol])
                    {
                        if(recordList.get(3).get(iRow).indexOf("0") == 0)
                            rtnRst += "未排班$";
                        else
                            rtnRst += GetWorkGroupName(recordList.get(3).get(iRow)) + "$";
                    }
                }
            }
        }
        return rtnRst;
    }
    
    private String GetWorkGroupName(String id)
    {
        Work_Group_Info hUIHandle = new Work_Group_Info(new EarthquakeManagement());
        hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(id));
        return hUIHandle.getDBRecordList("group_name").get(0);
    }
}
