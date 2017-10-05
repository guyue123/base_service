package com.base.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数组，列表公共函数处理
 * 
 * @author Administrator
 * @param <T>
 * 
 */
public final class CollectionUtil {

    /**
     * 列表对象是否为空
     * 
     * @param coll
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyOrNull(Collection coll) {
        if (coll == null || coll.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * 字符串数组转换成列表
     * 
     * @param array
     * @return
     */
    public static List<String> convertArray2List(String[] array) {
        if (array == null) {
            return null;
        }

        List<String> list = new ArrayList<String>();
        for (String str : array) {
            list.add(str);
        }

        return list;
    }

    /**
     * 对象数组转换成列表
     * 
     * @param array
     * @return
     */
    public static <T> List<T> convertArray2List(T[] array) {
        if (array == null) {
            return null;
        }

        List<T> list = new ArrayList<T>();
        for (T str : array) {
            list.add(str);
        }

        return list;
    }

    /**
     * 将列表对象转换为数组
     * 
     * @param list
     * @return
     */
    public static String[] convertList2Array(Collection<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        String[] array = new String[list.size()];
        return list.toArray(array);
    }

    /**
     * 将列表对象转换为数组
     * 
     * @param list
     * @return
     */
    public static Object[] convertCollection2Array(Collection<Object> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Object[] array = new Object[list.size()];
        return list.toArray(array);
    }

    /**
     * 将列表分割成多个指定大小的列表
     * 
     * @param <T>
     * 
     * @param list
     *            列表
     * @param groupSize
     *            尺寸
     * 
     * @return
     */
    public static <T> List<List<T>> groupList(List<T> list, int groupSize) {
        if (isEmptyOrNull(list)) {
            return null;
        }

        List<List<T>> gList = new ArrayList<List<T>>();
        if (groupSize < 1) {
            gList.add(list);
        }

        List<T> tmpList = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            if (tmpList.size() >= groupSize) {
                gList.add(tmpList);
                tmpList = new ArrayList<T>();
            }

            tmpList.add(list.get(i));
        }

        if (tmpList.size() > 0) {
            gList.add(tmpList);
        }

        return gList;
    }

    /**
     * 将数组分割成多个指定大小的列表
     * 
     * @param array
     *            列表
     * @param groupSize
     *            尺寸
     * 
     * @return
     */
    public static <T> List<List<T>> groupArray(T[] array, int groupSize) {
        return groupList(convertArray2List(array), groupSize);
    }

    /**
     * 将集合Map对象转换为指定键值对象
     * 
     * @param coll
     *            集合对象
     * @param key
     *            取键值的键
     * @param valKey
     *            取值的键
     * @return Map对象
     */
    @SuppressWarnings("rawtypes")
    public Map<Object, Object> convertCollection2Map(Collection<Map> coll, String key, String valKey) {
        if (isEmptyOrNull(coll)) {
            return null;
        }

        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        for (Map<?, ?> map : coll) {
            resultMap.put(map.get(key), map.get(valKey));
        }

        return resultMap;
    }
}
