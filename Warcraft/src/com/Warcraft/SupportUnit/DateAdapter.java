package com.Warcraft.SupportUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateAdapter
{
    public static int getDayCountOfAMonth(String yearMonth)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        Calendar handle = Calendar.getInstance();
        try
        {
            Date myDate = myFormatter.parse(yearMonth.length()>6?yearMonth:yearMonth+"01");
            handle.setTime(myDate);
            handle.roll(Calendar.DATE, -1);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return handle.get(Calendar.DATE);
    }
    
    public static String getTomorrowDateString(String yearMonth)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        Calendar handle = Calendar.getInstance();
        try
        {
            Date myDate = myFormatter.parse(yearMonth);
            handle.setTime(myDate);
            handle.roll(Calendar.DATE, 1);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return String.format("%d%02d%02d", handle.get(Calendar.YEAR), handle.get(Calendar.MONTH)+1, handle.get(Calendar.DATE));
    }
    
    public static String getYesterdayDateString(String yearMonth)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        Calendar handle = Calendar.getInstance();
        try
        {
            Date myDate = myFormatter.parse(yearMonth);
            handle.setTime(myDate);
            handle.roll(Calendar.DATE, -1);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return String.format("%d%02d%02d", handle.get(Calendar.YEAR), handle.get(Calendar.MONTH)+1, handle.get(Calendar.DATE));
    }
    
    public static String getPrecedingMonthString(String yearMonth)
    {
        String strYear = yearMonth.substring(0, 4);
        String strMonth = yearMonth.substring(4, 6);
        Calendar handle = Calendar.getInstance();
        handle.set(Calendar.YEAR, Integer.parseInt(strYear));
        handle.set(Calendar.MONTH, Integer.parseInt(strMonth) - 2);
        return String.format("%d%02d%s", handle.get(Calendar.YEAR), handle.get(Calendar.MONTH)+1, yearMonth.subSequence(6, 8));
    }
    
    public static List<String> GetWeekDayOfAMonth(String yearMonthDay, int getWeedday)
    {
        List<String> rtnRst = new ArrayList<String>();
        for(int iDayIdx=0; iDayIdx < DateAdapter.getDayCountOfAMonth(yearMonthDay); iDayIdx++)
        {
            String curDateOfYear = String.format("%s%02d", yearMonthDay.substring(0, 6), iDayIdx + 1);
            if(DateAdapter.getDayOfAWeek(curDateOfYear) == getWeedday)
                rtnRst.add(curDateOfYear);
        }
        return rtnRst;
    }
    
    public static List<String> getAllDayStringOfAMonth(String yearMonthDay)
    {
        List<String> rtnRst = new ArrayList<String>();
        int beginDate = Integer.parseInt(yearMonthDay.length()>6?yearMonthDay.substring(0,6):yearMonthDay + "01");
        int maxDays = DateAdapter.getDayCountOfAMonth(yearMonthDay);
        
        for(int dateOffset = 0; dateOffset < maxDays; dateOffset++ )
        {
            int curDate = beginDate + dateOffset;
            rtnRst.add(Integer.toString(curDate));
        }
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
    
    public static long CalculateTimeSpan(String beginTime, String endTime)
    {
        long rtnRst = 0;
        if(DateAdapter.TimeSpan(beginTime, endTime) <= 0)
            rtnRst = DateAdapter.TimeSpan(endTime, beginTime);
        else
            rtnRst = DateAdapter.TimeSpan("24:00:00", beginTime) + DateAdapter.TimeSpan(endTime, "00:00:00") ;
        return rtnRst;
    }
    
    public static String GetMiddleTimeBetweenTimes(String checkInTime, String checkOutTime)
    {
        long timeSpan = CalculateTimeSpan(checkInTime, checkOutTime)/2;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); 
        Calendar cal = Calendar.getInstance();
        try
        {
            Date beginTime = format.parse(checkInTime);
            cal.setTime(beginTime);
            cal.add(Calendar.HOUR, (int)timeSpan/60);
            cal.add(Calendar.MINUTE, (int)timeSpan%60);
        }
        catch(Exception e)
        {
            System.out.println("错误!");
        }
        return String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
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
    
    public static boolean isTimeBetweenTimespan(String strTime, String BeginTime, String EndTime)
    {
        SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss"); 
        try
        {
            Date dateAfter=df.parse(EndTime);
            Date dateBefore=df.parse(BeginTime);
            Date time=df.parse(strTime);
            if(time.before(dateAfter) && time.after(dateBefore))
                return true;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
