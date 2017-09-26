package com.taotaohai.util;

import android.util.Log;


import com.taotaohai.ConstantValue;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(String dateString) {
        if (dateString != null && !"null".equals(dateString)) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    public static String getStringDateShort2(String dateString) {
        if (dateString != null && !"null".equals(dateString)) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    /*
    * 计算时间差
    * */
    public static long getTime(String data) {
        long t = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("df", "1");
        String format = df.format(new Date());
        Log.e("df", "2");
        try {
            Log.e("df", "3");
            Date d1 = df.parse(format);
            Log.e("df", "4");
            Date d2 = df.parse(data);
            Log.e("df", "5");
            long diff = d1.getTime() - d2.getTime();
            Log.e("df", "6");
            long days = diff / (1000 * 60 * 60 * 24);
            Log.e("df", "7");
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            Log.e("df", "8");
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Log.e("df", "9");
            t = 30 - minutes;
            Log.e("df", "10");
            if (t <= 0) {
                t = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    /*
    * 计算时间（首页有空点击 判断半个小时内）
    * */
    public static Boolean getTimeDifference(String data) {
        boolean b = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        try {
            Date d1 = df.parse(format);
            Date d2 = df.parse(data);
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Log.e("minutes", "" + minutes);
            if (minutes > 30) {
                b = true;
            } else {
                b = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateCommon2(String dateString) {
        if (dateString == null) {
            return "";
        }
        int time = (int) (System.currentTimeMillis() / 1000) - Integer.parseInt(dateString);
        if (time <= 60) {
            return "刚刚";
        } else if (time <= 3600) {
            return time / 60 + "分钟前";
        } else if (time <= 86400) {
            return time / 3600 + "小时前";
        } else if (time <= 259200) {
            return time / 86400 + "天前";
        }
        if (dateString != null) {
            SimpleDateFormat sdr = new SimpleDateFormat("yy-MM-dd");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateCommon(String dateString) {
        if (dateString == null) {
            return "";
        }
//        int time = (int) (System.currentTimeMillis() / 1000) - Integer.parseInt(dateString);
//        if (time <= 60) {
//            return "刚刚";
//        } else if (time <= 3600) {
//            return time / 60 + "分钟前";
//        } else if (time <= 86400) {
//            return time / 3600 + "小时" + ((time % 3600) / 60) + "分钟前";
//        } else if (time <= 259200) {
//            return time / 86400 + "天前";
//        }
        if (dateString != null) {
            if (dateString.length() > 10) {
                dateString = dateString.substring(0, 10);
            }
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    public static String getStringDateCommon3(String dateString) {
        if (dateString == null) {
            return "";
        }
        int time = (int) (System.currentTimeMillis() / 1000) - Integer.parseInt(dateString);
//        if (time <= 60) {
//            return "刚刚";
//        } else if (time <= 3600) {
//            return time / 60 + "分钟前";
//        } else if (time <= 86400) {
//            return time / 3600 + "小时前";
//        } else if (time <= 259200) {
//            return time / 86400 + "天前";
//        }
        if (dateString != null) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    public static String DateShortCommon(String dateString) {
        int time = (int) (System.currentTimeMillis() / 1000) - Integer.parseInt(dateString);
        if (dateString != null) {
            SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    public static String DateShortCommon2(String dateString) {
        int time = (int) (System.currentTimeMillis() / 1000) - Integer.parseInt(dateString);
        if (dateString != null) {
            dateString = dateString.substring(0, 9);
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDate(String dateString) {
        if (dateString != null) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(dateString);
            int i = Integer.parseInt(dateString);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        }
        return "";

    }

    public static String getShalou(int dateString) {
        String shi = String.valueOf(dateString / 3600);
        int s = dateString % 3600;
        String fen = String.valueOf(s / 60);
        if (shi.length() < 2) {
            shi = "0" + shi;
        }
        if (fen.length() < 2) {
            fen = "0" + fen;
        }
        return shi + ":" + fen;

    }

    private static String getCurrentDate() {
        String dateStr = "2013-10-01 12:12:00";
        SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        if (dFormat != null && date != null) {
            dateStr = dFormat.format(date);
        }
        return dateStr;
    }


    public static String hasHttp(String url) {
        if (!StringUtils.isEmpty(url)) {
            if (url.length() > 4) {
                if ("http".equals(url.substring(0, 4))) {
                    return url;
                }
            }
        }
        return ConstantValue.IMGURL + url;
    }

    public static String hasURLHttp(String url) {
        if (!StringUtils.isEmpty(url)) {
            if ("http".equals(url.substring(0, 4))) {
                return url;
            } else {
                return ConstantValue.URL + url;
            }
        }
        return null;

    }

    public static String getOneMonth(int last) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.add(Calendar.DATE, -last); //月份减1
        Date lastMonth = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(lastMonth);
    }

    public static String setIndustry(String industry) {
        if ("insurance".equals(industry)) {
            return "保险";
        } else if ("property".equals(industry)) {
            return "房产";
        } else if ("finance".equals(industry)) {
            return "金融";
        } else if ("other".equals(industry)) {
            return "车行";
        } else if ("other".equals(industry)) {
            return "其他";
        }
        return "";
    }

    //每条聊天记录间隔时间，判断显示隐藏
    public static String setTimeVisit(String time1, String time2) {
        /*int time = (int)(Integer.parseInt(time1) - Integer.parseInt(time2));
        if(time<300){
            if("".equals(time1)){
                return chatTime(time2);
            }else{
                return "";
            }
        }else{
            return chatTime(time2);
        }*/

        if ("".equals(time1)) {
            return chatTime(time2);
        } else {
            int time = (int) (Integer.parseInt(time2) - Integer.parseInt(time1));
            Log.e("time[]", "" + time);
            if (time < 300) {
                return "";
            } else {
                return chatTime(time2);
            }
        }
    }

    //获取显示聊天时间
    public static String chatTime(String dateString) {
        if (dateString == null) {
            return "";
        }

        String times = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat sdr2 = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressWarnings("unused")
            int i2 = Integer.parseInt(dateString);
            String dateString2 = sdr2.format(new Date(i2 * 1000L));

            Date date = sdf.parse(dateString2);
            Date now = new Date();
            now = sdf.parse(sdf.format(now));
            long sl = date.getTime();
            long el = now.getTime();
            long ei = sl - el;
            int value = (int) (ei / (1000 * 60 * 60 * 24));
            if (value == 0) {
                SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
                @SuppressWarnings("unused")
                int i = Integer.parseInt(dateString);
                times = sdr.format(new Date(i * 1000L));
                return "今天 " + times;
                //System.out.println("日期是今天");
            } else if (value == -1) {
                // System.out.println("日期是昨天");
                SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
                @SuppressWarnings("unused")
                int i = Integer.parseInt(dateString);
                times = sdr.format(new Date(i * 1000L));
                return "昨天 " + times;
            } else if (value == -2) {
                // System.out.println("日期是昨天");
                SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
                @SuppressWarnings("unused")
                int i = Integer.parseInt(dateString);
                times = sdr.format(new Date(i * 1000L));
                return "前天 " + times;
            } else {
                // System.out.println("日期不是昨天,今天，明天");
                SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm");
                @SuppressWarnings("unused")
                int i = Integer.parseInt(dateString);
                times = sdr.format(new Date(i * 1000L));
                return times;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static long gettime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static void main(String[] st) {
        String st1 = getStringDateCommon("1495093289");
        System.out.print(st1);
    }

}
