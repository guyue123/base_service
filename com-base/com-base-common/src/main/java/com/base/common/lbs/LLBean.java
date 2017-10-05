/**
 * 
 */
package com.base.common.lbs;

/**
 * 经纬度对象
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年05月08日
 */
public class LLBean {

    /**
     * 经度
     */
    private double lng;

    /**
     * 维度
     */
    private double lat;

    LLBean(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    /**
     * @return the lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * @param lng
     *            the lng to set
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat
     *            the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * 经纬度是否合法
     * 
     * @return
     */
    public boolean isValid() {
        if (Math.abs(this.lat) > 90) {
            return false;
        }

        if (Math.abs(this.lng) > 180) {
            return false;
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LLBean [lng=" + lng + ", lat=" + lat + "]";
    }
}
