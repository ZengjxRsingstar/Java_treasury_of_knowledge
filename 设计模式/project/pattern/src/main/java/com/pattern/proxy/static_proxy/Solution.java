package com.pattern.proxy.static_proxy;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class Solution {
    public int daysBetweenDates(String date1, String date2) {

        if(date1==null || date2==null){
            return -1;
        }
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int  day= (int)(dateFormat.parse(date1).getTime()-dateFormat.parse(date2).getTime())/(1000*60*60*24);

            System.out.println(dateFormat.parse(date1).getTime());
            System.out.println(dateFormat.parse(date2).getTime());
            System.out.println(Math.abs((dateFormat.parse(date1).getTime()-dateFormat.parse(date2).getTime())/(1000*60*60*24)));
            return day;
        }catch(Exception e){
            e.printStackTrace();
        }
        return  -1;
    }
    @Test
    public   void   test (){
        Solution  solution =new Solution();
        int dates = daysBetweenDates("2019-06-29", "2019-7-30");
        System.out.println(dates);

    }
}