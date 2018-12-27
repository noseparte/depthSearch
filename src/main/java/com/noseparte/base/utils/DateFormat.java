package com.noseparte.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Copyright © 2018 noseparte © BeiJing BoLuo Network Technology Co. Ltd.
 *
 * @Author Noseparte
 * @Compile --
 * @Version 1.0
 * @Description
 */
public class DateFormat {

    public static Date ParseDate(String dateString) throws Exception{
        if ("".equals(dateString)) {
            return null;
        }

        //判断数字类型
        Pattern pattern=Pattern.compile("[0-9]*");
        if (pattern.matcher(dateString).matches()) {
            //从1900年1月1日开始推算
            Integer intValue=Integer.valueOf(dateString);
            Calendar calendar=Calendar.getInstance();
            calendar.set(1900, 0, -1);
            calendar.add(Calendar.DAY_OF_MONTH, intValue.intValue());
            return calendar.getTime();
        }

        //字符类型
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        String formatString=dateString.replaceAll("[\u4e00-\u9fa5|/]", "-");
        if (formatString.length()-1==formatString.lastIndexOf("-")) {
            formatString=formatString.substring(0, formatString.length()-1);
        }
        Date date;
        try {
            date = sf.parse(formatString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return date;
    }

    public static void main(String[] args) {
        //excel导入时遇到的常用日期格式
        String[] timeStrings=new String[] {"2017年8月9日", "2017/8/9", "2017-08-09", "2017-8-9", "2017年8月9日9:54:38", "2017/8/9 9:55","43052"};

        for (int i = 0; i < timeStrings.length; i++) {
            String dateString=timeStrings[i];
            try {
                Date date = DateFormat.ParseDate(dateString);
                System.out.println(date);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
