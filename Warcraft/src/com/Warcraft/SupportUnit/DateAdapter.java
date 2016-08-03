package com.Warcraft.SupportUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAdapter
{
    public static int getMaxDaysByYearMonth(String yearMonth)
    {
        String strYear = yearMonth.substring(0, 4);
        String strMonth = yearMonth.substring(4);
        Calendar handle = Calendar.getInstance();
        handle.set(Calendar.YEAR, Integer.parseInt(strYear));
        handle.set(Calendar.MONTH, Integer.parseInt(strMonth) - 1);
        handle.set(Calendar.DATE, 1);
        handle.roll(Calendar.DATE, -1);
        return handle.get(Calendar.DATE);
    }
    
    public static String getPrecedingMonth(String yearMonth)
    {
        String rtnRst = null;
        int Year = Integer.parseInt(yearMonth.substring(0, 4));
        int Month = Integer.parseInt(yearMonth.substring(4));
        if(Month > 1)
            rtnRst = Integer.toString(Year) + String.format("%02d", Month);
        else
            rtnRst = Integer.toString(Year-1) + "12";
        return rtnRst;
    }
    
    public static int getDayOfAWeek(String yearMonthDay)
    {
        int rtnRst = -1;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        try
        {  
            Date myDate = myFormatter.parse(yearMonthDay);
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            rtnRst = cal.get(Calendar.DAY_OF_WEEK);
        }
        catch (Exception e)
        {  
            System.out.println("错误!");
        }
        return rtnRst;
    }
    
    public static String getMiddleTimeBetweenTimeSpan(String checkInTime, String checkOutTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); 
        Calendar cal = Calendar.getInstance();
        try
        {
            Date beginTime = format.parse(checkInTime);
            //Date endTime = format.parse(checkOutTime);
            cal.setTime(beginTime);
            cal.add(Calendar.HOUR, 4);
        }
        catch(Exception e)
        {
            System.out.println("错误!");
        }
        return Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(cal.get(Calendar.MINUTE)) + ":" + Integer.toString(cal.get(Calendar.SECOND));
    }
    
    public static long TimeSpan(String beginTime, String endTime)
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
            e.printStackTrace();
        }
        return 0;
    }
    
    public static Date parseToTime(String convertTime)
    {
        Date rtnRst = null;
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        try
        {
            rtnRst = df.parse(convertTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return rtnRst;
    }
}
