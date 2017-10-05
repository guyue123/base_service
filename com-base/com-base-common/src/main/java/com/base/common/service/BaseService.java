package com.base.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.base.common.mybatis.OrCriteria;
import com.base.common.mybatis.page.Page;

/**
 * 通用Service层接口
 * 
 * @author kin wong
 */
public interface BaseService<M> {
    /************************** 单表增、删、改 **********************************/
    /**
     * 保存一条记录
     * 
     * @param model
     *            对象实例
     * @return 对象实例
     */
    M save(M model);

    /**
     * 更新一条记录
     * 
     * @param model
     *            对象实例
     */
    void update(M model);

    /**
     * 根据主键删除一条记录
     * 
     * @param id
     *            对象主键
     */
    void delete(Class<M> entityClass, Serializable id);

    /**
     * 根据对象删除一条记录
     * 
     * @param model
     *            对象实例
     */
    void deleteObject(M model);

    /************************** 单表简单查（主键记录、全部记录） **********************************/
    /**
     * 根据主键获得一条记录
     * 
     * @param id
     *            主键
     * @return 对象实例
     */
    M get(Class<M> entityClass, Serializable id);

    /**
     * 获得全部记录
     * 
     * @return 记录列表
     */
    List<M> selectAll(Class<M> clazz);

    /**
     * 根据SQL文查询结果.
     * 
     * @param sql
     *            查询语句
     * @return List
     */
    List<Map<String, Object>> selectBySql(String sql);

    /**
     * 查询.
     * 
     * @param clazz
     * @param orCriteria
     * @return list 结果集
     */
    List<M> selectByCriteria(Class<M> clazz, OrCriteria orCriteria);

    /**
     * 统计数量查询.
     * 
     * @param clazz
     *            类
     * @param orCriteria
     *            查询条件
     * @return 统计结果
     */
    int countByCriteria(Class<M> clazz, OrCriteria orCriteria);

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
    Page<M> getResultByPage(Class<M> clazz, OrCriteria orCriteria, int pageNo, int pageSize);

    /**
     * 根据SQL文查询唯一结果.
     * 
     * @param sql
     *            查询语句
     * @return List
     */
    Map<String, Object> getBySql(String sql);

    /**
     * 查询唯一结果.
     * 
     * @param clazz
     * @param orCriteria
     * @return list 结果集
     */
    M getByCriteria(Class<M> clazz, OrCriteria orCriteria);
}
