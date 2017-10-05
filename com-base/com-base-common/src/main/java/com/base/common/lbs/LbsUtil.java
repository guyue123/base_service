/**
 * 
 */
package com.base.common.lbs;

import java.util.HashMap;
import java.util.Map;

/**
 * 地理位置公共类
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年05月08日
 */
public class LbsUtil {

    /**
     * 地球半径
     */
    public final static double EARTH_RADIUS = 6378.137;

    /**
     * 一公里对应的经纬度
     */
    public final static double ONE_KM_LL = 0.00898315;

    /**
     * 东南西北
     */
    public final static String DIRECT_SOUTH = "south";
    public final static String DIRECT_EAST = "east";
    public final static String DIRECT_WEST = "west";
    public final static String DIRECT_NORTH = "north";

    /**
     * 计算
     * 
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度直接的距离
     * 
     * @param longStart
     *            经度
     * @param latStart
     *            纬度
     * @param longEnd
     *            经度
     * @param latEnd
     *            纬度
     * 
     * @return
     */
    public static double getDistance(double longStart, double latStart, double longEnd, double latEnd) {
        double radLat1 = rad(latStart);
        double radLat2 = rad(latEnd);
        double a = radLat1 - radLat2;
        double b = rad(longStart) - rad(longEnd);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 计算两个经纬度直接的距离
     * 
     * @param start
     *            起点
     * @param end
     *            终点
     * @return
     */
    public static double getDistance(LLBean start, LLBean end) {
        return getDistance(start.getLng(), start.getLat(), end.getLng(), end.getLat());
    }

    /**
     * 通过多个坐标计算出坐标中心点
     * 
     * @return
     */
    public static LLBean getCenter(LLBean... lbs) {
        double lng = 0;
        double lat = 0;
        int i = 0;
        for (LLBean bean : lbs) {
            if (bean.isValid()) {
                lng += bean.getLng();
                lat += bean.getLat();

                i++;
            }
        }

        if (i == 0) {
            return null;
        }

        return new LLBean(lng / i, lat / i);
    }

    /**
     * 通过一个经纬度找到指定距离的东南西北四个方向的坐标
     * 
     * @param bean
     * @return
     */
    public static Map<String, LLBean> get4Direct(LLBean bean, double distance) {
        Map<String, LLBean> beans = new HashMap<String, LLBean>();

        // 南方
        beans.put(DIRECT_SOUTH, new LLBean(bean.getLng(), bean.getLat() - distance * ONE_KM_LL));
        // 北方
        beans.put(DIRECT_NORTH, new LLBean(bean.getLng(), bean.getLat() + distance * ONE_KM_LL));
        // 东方
        beans.put(DIRECT_EAST, new LLBean(bean.getLng() + distance * ONE_KM_LL, bean.getLat()));
        // 西方
        beans.put(DIRECT_WEST, new LLBean(bean.getLng() - distance * ONE_KM_LL, bean.getLat()));

        return beans;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getDistance(119.966533, 29.237357, 116.768301, 23.491435));

        System.out.println(get4Direct(new LLBean(119.651048,29.085133), 3));
    }
}
