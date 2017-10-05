/**
 * 
 */
package com.base.common.basedata.bean;

/**
 * 手机号段，邮政编码，区号，所在省市对应关系
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年05月08日
 */
public class PhonePostZone {

    /**
     * 手机号前6位
     */
    private String phoneCode;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 电话区号
     */
    private String zoneCode;

    /**
     * 地址对象
     */
    private Address address;

    /**
     * @return the phoneCode
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * @param phoneCode
     *            the phoneCode to set
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode
     *            the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return the zoneCode
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * @param zoneCode
     *            the zoneCode to set
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PhonePostZone [phoneCode=" + phoneCode + ", postCode=" + postCode + ", zoneCode=" + zoneCode
                + ", address=" + address + "]";
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

}
