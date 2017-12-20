package com.aiiju.util.common;

public class Distance {
	//地球平均半径  
    private static final double EARTH_RADIUS = 6378137;  
    //把经纬度转为
    private static double rad(double d){  
       return d * Math.PI / 180.0;  
    }  
      
    /**  
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米  
     * @param lng1  
     * @param lat1  
     * @param lng2  
     * @param lat2  
     * @return  
     */  
    public static double getDistance(double lng1, double lat1, double lng2, double lat2){  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lng1) - rad(lng2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 10000) / 10000;  
       return s;  
    }  
    
    public static void main(String[] args){
    	double distance1 = getDistance(120.060358,30.287471,120.045788,30.283212);
    	double distance2 = getDistance(120.060296,30.287478,120.060336,30.287652);
    	System.out.println("景麒的偏差距离："+distance1+"m");
    	System.out.println("小辉的偏差距离："+distance2+"m");
    }
}
