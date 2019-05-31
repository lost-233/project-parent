package com.zhaoming.base.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 根据gps坐标计算距离
 * @author zm
 */
@UtilityClass
public class LocationUtils {  
    private static double EARTH_RADIUS = 6378.137;  
  
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }  
  
    /** 
     * 通过经纬度获取距离(单位：千米 四舍五入到两位小数)
     * @param lat1 经度1
     * @param lng1 纬度1
     * @param lat2 经度2
     * @param lng2 纬度2
     * @return 距离 单位千米
     */  
    public static double getDistance(double lat1, double lng1, double lat2,  
                                     double lng2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
        double a = radLat1 - radLat2;  
        double b = rad(lng1) - rad(lng2);  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
                + Math.cos(radLat1) * Math.cos(radLat2)  
                * Math.pow(Math.sin(b / 2), 2)));  
        s = s * EARTH_RADIUS;  
        s = Math.round(s * 10000d) / 10000d;  
        //s = s*1000;
        BigDecimal bg = new BigDecimal(s).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    /**
     * 通过经纬度获取距离(单位：千米 四舍五入到两位小数)
     * @param gps1 位置1 39.82665501488607,116.28850216343146
     * @param gps2 位置2 39.90589845325239,116.49804042519719
     * @return 距离 单位千米
     */
    public static Double getDistanceByString(String gps1, String gps2) {
        if (StringUtils.isBlank(gps1)||StringUtils.isBlank(gps2)){
            return null;
        }
        String[] locations1 = gps1.split(",");
        String[] locations2 = gps2.split(",");
        double lat1 = new BigDecimal(locations1[0]).doubleValue();
        double lng1 = new BigDecimal(locations1[1]).doubleValue();
        double lat2 = new BigDecimal(locations2[0]).doubleValue();
        double lng2 = new BigDecimal(locations2[1]).doubleValue();
        return getDistance(lat1,lng1,lat2,lng2);
    }
}