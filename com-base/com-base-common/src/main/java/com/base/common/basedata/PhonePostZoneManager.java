/**
 * 
 */
package com.base.common.basedata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.base.common.basedata.bean.Address;
import com.base.common.basedata.bean.PhonePostZone;
import com.base.common.string.StringUtil;
import com.csvreader.CsvReader;

/**
 * 缓存：手机号段，邮政编码，区号，所在省市对应关系 提供：手机号段查询，区号查询，邮政编码查询所属地
 *
 * @author <a href="mailto:huhuaxiang@base.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年05月08日
 */
public class PhonePostZoneManager {

    /**
     * 手机号段，邮政编码，区号，所在省市对应关系
     */
    private static Map<String, PhonePostZone> phoneMap;

    /**
     * 区号对应的地址
     */
    private static Map<String, Address> zoneMap;

    /**
     * 邮编对应的地址
     */
    private static Map<String, Address> postMap;

    /**
     * 初始缓存
     * 
     * @throws IOException
     */
    public static void cache() throws IOException {
        if (phoneMap != null) {
            return;
        }

        URL url = PhonePostZoneManager.class.getClassLoader().getResource(
                "com/base/common/basedata/file/phone-post-zone.txt");

        BufferedReader in = null;
        phoneMap = new HashMap<String, PhonePostZone>();
        zoneMap = new HashMap<String, Address>();
        postMap = new HashMap<String, Address>();
        try {
            CsvReader reader = new CsvReader(new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")));
            while (reader.readRecord()) {
                String[] values = reader.getValues();
                PhonePostZone ppz = new PhonePostZone();

                Address addr = new Address();
                addr.setProvince(values[2]);
                addr.setCity(values[3]);

                ppz.setPhoneCode(values[1]);
                ppz.setAddress(addr);
                ppz.setZoneCode(values[4]);
                ppz.setPostCode(values[5]);

                phoneMap.put(ppz.getPhoneCode(), ppz);
                zoneMap.put(ppz.getZoneCode(), addr);
                postMap.put(ppz.getPostCode(), addr);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

    }

    /**
     * 根据手机号码获取号码归属地，邮政编码等
     * 
     * @param phone
     * @return
     */
    public static PhonePostZone getPPZByPhone(String phone) {
        if (phoneMap == null || phoneMap.isEmpty() || !StringUtil.isMobileNo(phone)) {
            return null;
        }

        return phoneMap.get(phone.substring(0, 7));
    }

    /**
     * 根据邮编获得地址
     * 
     * @param phone
     * @return
     */
    public static Address getAddressByPost(String post) {
        if (postMap == null || postMap.isEmpty()) {
            return null;
        }

        return postMap.get(post);
    }

    /**
     * 根据区号获得地址
     * 
     * @param phone
     * @return
     */
    public static Address getAddressByZone(String zone) {
        if (zoneMap == null || zoneMap.isEmpty()) {
            return null;
        }

        return zoneMap.get(zone);
    }
}
