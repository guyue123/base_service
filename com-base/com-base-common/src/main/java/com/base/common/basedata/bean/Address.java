package com.base.common.basedata.bean;

/**
 * 国家编码：省、市，区县，街道，居委会
 * 
 * @author Administrator
 *
 */
public class Address {

    /**
     * 国家行政区划编码
     */
    private String id;

    /**
     * 国家行政区划编码：父编码
     */
    private String pid;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 街道、办事处
     */
    private String street;

    /**
     * 居委会
     */
    private String committee;

    public Address() {
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district
     *            the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the committee
     */
    public String getCommittee() {
        return committee;
    }

    /**
     * @param committee
     *            the committee to set
     */
    public void setCommittee(String committee) {
        this.committee = committee;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Address [id=" + id + ", pid=" + pid + ", province=" + province + ", city=" + city + ", district="
                + district + ", street=" + street + ", committee=" + committee + "]";
    }

}