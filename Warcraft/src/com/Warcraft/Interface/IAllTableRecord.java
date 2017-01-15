package com.Warcraft.Interface;

import java.util.List;

public interface IAllTableRecord
{
    List<List<String>> User_Info_All(String[] getKeyArray);
    List<List<String>> User_Info_AllWithoutRoot(String[] getKeyArray);
    List<List<String>> Check_In_Raw_Data_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord);
    List<List<String>> Holiday_Mark_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord);
    List<List<String>> Over_Time_Record_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord);
    List<List<String>> Work_Group_Info_All(String[] getKeyArray);
}
