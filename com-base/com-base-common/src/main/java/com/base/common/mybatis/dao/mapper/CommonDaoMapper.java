package com.base.common.mybatis.dao.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.common.mybatis.OrCriteria;
import com.base.common.mybatis.page.Page;

/**
 * 公共数据库接口
 * 
 * @author hhx 2014/11/21
 *
 */
public interface CommonDaoMapper {
    /**
     * 翻页查询
     * 
     * @param clazz 类对象
     * @param orCriteria 查询条件
     * @param page 翻页对象
     * @return 查询结果
     */
    <T> List<T> selectByCriteria(@Param("class") Class<T> clazz, @Param("orCriteria") OrCriteria orCriteria,
            @Param("page") Page<T> page);

    /**
     * 查询
     * 
     * @param clazz 类
     * @param orCriteria 查询条件
     * @return 查询结果
     */
    <T> List<T> selectByCriteria(@Param("class") Class<T> clazz, @Param("orCriteria") OrCriteria orCriteria);

    /**
     * 根据主键查询
     * 
     * @param clazz 类
     * @param key 主键
     * @return 查询结果
     */
    <T> List<T> selectByPrimaryKey(@Param("class") Class<T> clazz, @Param("key") Serializable key);

    /**
     * 根据主键查询
     * @param t 类对象
     * @return 查询结果
     */
    <T> List<T> selectByPrimaryKey(@Param("entity") T t);
    
    /**
     * 根据SQL文查询结果
     * @param sql 无参数sql语句
     * @return 查询结果
     */
    List<Map<String, Object>> selectBySql(@Param("sql") String sql);

    /**
     * 统计数量查询
     * 
     * @param clazz 类
     * @param orCriteria 查询条件
     * @return 统计结果
     */
    <T> int countByCriteria(@Param("class") Class<T> clazz, @Param("orCriteria") OrCriteria orCriteria);

    /**
     * 根据主键删除数据
     * @param clazz 类
     * @param key 主键
     * @return 删除数量
     */
    <T> int deleteByPrimaryKey(@Param("class") Class<T> clazz, @Param("key") Serializable key);

    /**
     * 根据主键删除数据
     * @param t 数据对象
     * @return
     */
    <T> int deleteByPrimaryKey(@Param("entity") T t);

    /**
     * 根据条件删除
     * @param clazz 数据类
     * @param orCriteria 删除条件
     * @return 删除数量
     */
    <T> int deleteByCriteria(@Param("class") Class<T> clazz, @Param("orCriteria") OrCriteria orCriteria);

    /**
     * 插入数据对象
     * @param t 数据对象
     * @return 插入数量
     */
    <T> int insert(@Param("entity") T t);

    /**
     * 根据条件更新数据
     * @param t 数据对象
     * @param orCriteria 更新条件
     * @return 更新数量
     */
    <T> int updateByCriteria(@Param("entity") T t, @Param("orCriteria") OrCriteria orCriteria);

    /**
     * 根据主键更新数据
     * @param t 数据对象
     * @param key 主键
     * @return 更新数量
     */
    <T> int updateByPrimaryKey(@Param("entity") T t, @Param("key") Serializable key);

    /**
     * 根据主键更新数据
     * @param t 数据对象
     * @return 更新数量
     */
    <T> int updateByPrimaryKey(@Param("entity") T t);

    /**
     * 获得sys_guid()
     * 
     * @return
     */
    String selectSysGuid();

    /**
     * 获得sequence的当前值
     */
    String selectSeqCurrVal(@Param("sequence") String sequence);

    /**
     * 获得sequence的下一个值
     */
    String selectSeqNextVal(@Param("sequence") String sequence);
}
