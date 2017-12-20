package com.aiiju.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author  维斯
 * @Description 时间处理
 * 2016年6月20日
 */
public class DateUtil {
	private static final String dayFormat="yyyy-MM-dd";
	private static final String monthFormat="yyyy-MM";
	private static final String dateTimeFormat="yyyy-MM-dd HH:mm:ss";
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;

    private static final String ONE_SECOND_AGO = "刚刚";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "昨天";

	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}
	
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author fh
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }
    
    /**
     * 转换日期 str<==>date
     * @param obj
     * @param pattern 转换格式   不传，默认是 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Object transFormDate(Object obj,String pattern) {
    	if(pattern == null || "".equals(pattern)) {
    		pattern = "yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat sf = new SimpleDateFormat(pattern);
    	if(obj instanceof Date) {
    		Date date = (Date)obj;
    		return sf.format(date);
    	}else if(obj instanceof String) {
    		String dateStr = (String)obj;
    		try {
    			return sf.parse(dateStr);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
    	return null;
    }
    
    /**e
     * 格林时间转换
     * @author 维斯
     * 2016年12月2日   上午11:44:21
     */
    public static String GLFormDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);  
		try {
			Date d = sdf.parse(pattern);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
			return sdf1.format(d); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return pattern;
		
	}
    /**
     * 获取毫秒值
     * @param datetime
     * @param pattern 不传，默认是 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeMillis(String datetime,String pattern) {
    	if(pattern == null || "".equals(pattern)) {
    		pattern = "yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat sf = new SimpleDateFormat(pattern);
    	try {
			Date date = sf.parse(datetime);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return 0;
    }


	/**
	 * 获取当天日期
	 * @return
	 */
	public static String getCurDay(){
		Date d=new Date();
		SimpleDateFormat sf = new SimpleDateFormat(dayFormat);
		return sf.format(d);
	}
	
	/**
	 * 获取当前月份  格式默认为YYYY-MM
	 * @return
	 */
	public static String getCurMonth(String pattern){
		Date d=new Date();
		SimpleDateFormat sf;
		if(StringUtils.isEmpty(pattern)){
			sf = new SimpleDateFormat(monthFormat);
		}else{
			sf = new SimpleDateFormat(pattern);
		}
		return sf.format(d);
	}
	/**
	 * 获取当前日期   格式默认为YYYY-MM
	 * @return
	 */
	public static String getCurDate(String pattern){
		Date d=new Date();
		SimpleDateFormat sf;
		if(StringUtils.isEmpty(pattern)){
			sf = new SimpleDateFormat(dayFormat);
		}else{
			sf = new SimpleDateFormat(pattern);
		}
		return sf.format(d);
	}
	
    /**
     * 考勤时间提醒判断
     * @param signAlertTime 提前多少秒判断
     * @param onlyTime 考勤时间 09:00:00 或09:00格式
     * @return 若返回1提醒
     * @throws ParseException 
     */
    public static int compareTimeByCondition(int signAlertTime,String onlyTime) throws ParseException{
    	SimpleDateFormat sf = new SimpleDateFormat(dateTimeFormat);
    	int result=Constant.NUM_ZERO;
    	if(onlyTime.length()<8){
    		onlyTime+=":00";
    	}
    	String signTimeStr=getCurDay()+" "+onlyTime;
    	int signTimel=((int)sf.parse(signTimeStr).getTime()/1000-signAlertTime)/60;
    	int curTime=(int)System.currentTimeMillis()/1000/60;
    	
        if(signTimel==curTime){
        	result=Constant.NUM_ONE;
        }
    	return result;	
    }
    /**
     * 
     * @Title: getWeek 
     * @Description: 获取日期是星期几 数字形式表示 0 1 2 3 4 5 6 星期天开始
     * @param date
     * @return 
     * @throws
     */
    public  static int getWeekToNum(Date date) {
    	int weekNum=0;
        String week = getWeek(date);
        switch (week) {
		case "星期日":
			weekNum=0;
			break;
		case "星期一":
			weekNum=1;
			break;
		case "星期二":
			weekNum=2;
			break;
		case "星期三":
			weekNum=3;
			break;
		case "星期四":
			weekNum=4;
			break;
		case "星期五":
			weekNum=5;
			break;
		case "星期六":
			weekNum=6;
			break;
		default:
			weekNum=0;
			break;
		}
        return weekNum;
    }
    
    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }
    
    /**
     * 
     * @Title: isTheDay 
     * @Description: 是否是指定日期 
     * @param date
     * @param day
     * @return 
     * @throws
     */
    private static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= DateUtil.dayBegin(day).getTime() 
                && date.getTime() <= DateUtil.dayEnd(day).getTime();
    }
    
    /**
     * 
     * @Title: dayBegin 
     * @Description: 获取指定日 00:00:00的时间
     * @param date
     * @return 
     * @throws
     */
    private static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    /**
     * 
     * @Title: dayEnd 
     * @Description: 取指定日 23:59:59的时间
     * @param date
     * @return 
     * @throws
     */
    private static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
    
    /**
     * 
     * @Title: isToday 
     * @Description: 是否是今天
     * @param date
     * @return 
     * @throws
     */
    public static boolean isToday(final Date date) {
        return DateUtil.isTheDay(date, new Date());
    }
    
    /**
     * 
     * @Title: isThisWeek 
     * @Description: 判断日期是否为本周
     * @param time
     * @return 
     * @throws
     */
    private static boolean isThisWeek(long time)  
    {  
        Calendar calendar = Calendar.getInstance();  
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);  
        calendar.setTime(new Date(time));  
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);  
        if(paramWeek==currentWeek){  
           return true;  
        }  
        return false;  
    }
    
    /**
	 * 转换日期为字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date ,String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		String result = null;
		try {
			result = sf.format(date);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
     * 
     * @Title: getWeek 
     * @Description: 获取日期是星期几
     * @param date
     * @return 
     * @throws
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }
    /**
     * 
     * @Title: getWeek 
     * @Description: 获取日期是星期几
     * @param date
     * @return 
     * @throws
     */
    public static String getNewWeek(Date date, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE",locale);
        String week = sdf.format(date);
        return week;
    }
    
    /**
     * 获取两个日期相差的天数
     * @param start  开始日期
     * @param end    结束日期
     * @return
     */
	public static int getDateCount(String start, String end) {
		long time1 = ((Date)transFormDate(start,"yyyy-MM-dd")).getTime();
		long time2 = ((Date)transFormDate(end,"yyyy-MM-dd")).getTime();
		long time3 = time2 - time1;
		int result = new Long(time3 / (1000*3600*24)).intValue();
		return result;
	}
	
	/**
	 * 获取任意的日期
	 * @param date 原点日期
	 * @param day  天数，正数表示当前日期的后n天，负数表示当前日期的前n天
	 * @param pattern
	 * @return
	 */
	public static String getAnyDate(String date,int day,String pattern) {
		if(day == 0) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date)transFormDate(date, pattern));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+day);
		return formatDate(calendar.getTime() ,pattern);
	}
    
    /**
	 * 
	 * @Title: relativeDateFormat 
	 * @Description: 根据前端时间显示规则写的时间操作类
	 * @param date
	 * @param type	公告需要用到的时间转换方式--1    工作汇报用到的时间转换方式--2  暂定
	 * @return 
	 * @throws
	 */
	public static String relativeDateFormat(String dateStr,int type) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long delta = new Date().getTime() - date.getTime();
        if (delta < 0) {
            return "您穿越时空了呢！";
        }
        if (delta < 1L * ONE_MINUTE) {
            return ONE_SECOND_AGO;
        }
        if (delta < 59L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        
        if(type == 1){
        	if (delta < 24L * ONE_HOUR && isToday(date)) {
                long hours = toHours(delta);
                return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
            }
        	if (delta < 48L * ONE_HOUR) {
        		return ONE_DAY_AGO;
        	}
        	if (delta > (48L * ONE_HOUR) && isThisWeek(date.getTime())) {
        		String time = formatDate(date, " HH:mm");
        		return getWeek(date) + time;
        	}else {
        		return formatDate(date, "yyyy-MM-dd HH:mm");
        	}
        }else{
        	if (delta < 24L * ONE_HOUR && isToday(date)) {
                long hours = toHours(delta);
                return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
            }else if(isToday(date)){
            	return formatDate(date, "HH:mm");
            }else{
            	return formatDate(date, "MM月dd日 HH:mm");
            }
        }
    }
	 /**
	  * 获取两个时间相差的分钟数
	  * @param time1  较大的时间 格式如18:25
	  * @param time2  较小的时间 格式如18:20
	  * @return
	  */
	public static long getDifferMinute(String time1, String time2) {
		long minute = 0;
		try {
		   SimpleDateFormat dfs = new SimpleDateFormat("HH:mm");
		   Date d1 = dfs.parse(time1);
		   Date d2 = dfs.parse(time2);
		   long between = (d1.getTime()-d2.getTime())/1000;
		   minute = between/60;
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return minute;
	}
	/**
	 * 获取下一天的日期
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, +1); //今天的时间加一天  
        date = calendar.getTime();  
        return date;          
    }
	
	/**
	 * 获取下一天的日期
	 * @param date
	 * @return
	 */
	public static int getToDay() {  
        Calendar calendar = Calendar.getInstance();  
        int toDay = calendar.get(Calendar.DATE);  
        return toDay;          
    }
	
	/**
	 * 自定义方法：获取某个日期是周几
	 * 返回数字：1-7
	 * @return
	 */
	public static int getDayofweek(String date){  
		Calendar cal = Calendar.getInstance();  
		if(StringUtils.isNotBlank(date)){
			Date fomatDate = fomatDate(date);
			//获取某天
			cal.setTime(fomatDate);  
		}else{
			//获取当天
			cal.setTime(new Date(System.currentTimeMillis()));  
		}
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week>1){
			return week-1; 
		}else{
			return 7;  
		}
	}  
	public static void main(String[] args) {
		int dayofweek = getDayofweek("2017-06-11");
		System.out.println(dayofweek);
	}

}
