package com.base.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.base.common.mybatis.OrCriteria;
import com.base.common.mybatis.page.Page;

/**
 * CRUD通用接口.
 * 
 * @author lhr @ date 2014-4-21
 * @param <T>
 */
public interface BaseDao<T> {
    /**
     * 查询数据.
     * 
     * @param entityClass
     *            对象类
     * @param id
     *            数据ID
     * @return pojo
     */
    T get(Class<T> entityClass, Serializable id);

    /**
     * 增加数据.
     * 
     * @param entity
     *            数据对象
     * @return Serializable
     */
    Serializable save(T entity);

    /**
     * 更新数据.
     * 
     * @param entity
     *            数据对象
     */
    void update(T entity);

    /**
     * 删除数据.
     * 
     * @param entity
     *            数据对象，必须包含数据ID
     * @return 删除数据的总数
     */
    int delete(T entity);

    /**
     * 删除数据.
     * 
     * @param entityClass
     *            对象类
     * @param id
     *            Serializable
     * @return 删除数据的总数
     */
    int delete(Class<T> entityClass, Serializable id);

    /**
     * 获得系统随机生成的编号.
     * 
     * @return sysguid
     */
    String getSysGuid();

    /**
     * 获得oracle数据库序列下一个值.
     * 
     * @param sequence
     *            序列名
     * @return oracle数据库序列下一个值
     */
    String getSeqNextVal(String sequence);

    /**
     * 获得oracle数据库序列当前值.
     * 
     * @param sequence
     *            序列名
     * @return oracle数据库序列当前值
     */
    String getSeqCurrVal(String sequence);

    /**
     * 翻页查询.
     * 
     * @param clazz
     *            对象类
     * @param orCriteria
     *            查询条件
     * @param pageNo
     *            页码
     * @param pageSize
     *            单页记录数
     * @return 翻页对象
     */
    Page<T> getResultByPage(Class<T> clazz, OrCriteria orCriteria, int pageNo, int pageSize);
    /**
     * 根据SQL文查询结果.
     * @param sql 查询语句
     * @return List
     */
    List<Map<String, Object>>  selectBySql(String sql);
    /**
     * 根据SQL文查询唯一结果.
     * @param sql 查询语句
     * @return List
     */
    Map<String, Object>  getBySql(String sql);
    /**
     * 查询.
     * @param clazz
     * @param orCriteria
     * @return list 结果集
     */
    List<T> selectByCriteria(Class<T> clazz, OrCriteria orCriteria);
    /**
     * 查询唯一结果.
     * @param clazz
     * @param orCriteria
     * @return list 结果集
     */
    T getByCriteria(Class<T> clazz, OrCriteria orCriteria);
    /**
     * 统计数量查询.
     * @param clazz 类
     * @param orCriteria 查询条件
     * @return 统计结果
     */
    int countByCriteria(Class<T> clazz, OrCriteria orCriteria);
}
