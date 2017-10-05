package com.base.common.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.common.dao.BaseDao;
import com.base.common.mybatis.OrCriteria;
import com.base.common.mybatis.dao.mapper.CommonDaoMapper;
import com.base.common.mybatis.page.Page;

/**
 * 数据库Dao基础实现类.
 * 
 * @author hhx
 * @param <T>
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
    /**
     * 数据库访问对象.
     */
    @Autowired
    CommonDaoMapper commonDaoMapper;

    /**
     * @see com.base.platform.admin.dao.BaseDao#get(java.lang.Class,
     *      java.io.Serializable)
     * @param entityClass
     *            实体class
     * @param id
     *            id
     */
    @Override
    public T get(Class<T> entityClass, Serializable id) {
        List<T> list = commonDaoMapper.selectByPrimaryKey(entityClass, id);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#save(java.lang.Object)
     * @param entity
     *            实体
     */
    @Override
    public Serializable save(T entity) {
        return commonDaoMapper.insert(entity);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#update(java.lang.Object)
     * @param entity
     *            实体
     */
    @Override
    public void update(T entity) {
        commonDaoMapper.updateByPrimaryKey(entity);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#delete(java.lang.Object)
     * @param entity
     *            实体
     */
    @Override
    public int delete(T entity) {
        return commonDaoMapper.deleteByPrimaryKey(entity);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#delete(java.lang.Class,
     *      java.io.Serializable)
     * @param entityClass
     *            实体class
     * @param id
     *            id
     */
    @Override
    public int delete(Class<T> entityClass, Serializable id) {
        return commonDaoMapper.deleteByPrimaryKey(entityClass, id);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getSysGuid()
     */
    @Override
    public String getSysGuid() {
        return commonDaoMapper.selectSysGuid();
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getSeqNextVal(java.lang.String)
     */
    @Override
    public String getSeqNextVal(String sequence) {
        return commonDaoMapper.selectSeqNextVal(sequence);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getSeqCurrVal(java.lang.String)
     */
    @Override
    public String getSeqCurrVal(String sequence) {
        return commonDaoMapper.selectSeqCurrVal(sequence);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getResultByPage(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria, int, int)
     * @param clazz
     *            pojo class
     * @param orCriteria
     *            查询条件
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页数据量
     */
    @Override
    public Page<T> getResultByPage(Class<T> clazz, OrCriteria orCriteria, int pageNo, int pageSize) {
        Page<T> page = new Page<T>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        if (orCriteria == null) {
            orCriteria = new OrCriteria();
        }

        List<T> resultList = commonDaoMapper.selectByCriteria(clazz, orCriteria, page);
        page.setResults(resultList);

        return page;

    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#selectBySql(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> selectBySql(String sql) {
        return commonDaoMapper.selectBySql(sql);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getBySql(java.lang.String)
     */
    @Override
    public Map<String, Object> getBySql(String sql) {
        Map<String, Object> obj = null;
        List<Map<String, Object>> lists = commonDaoMapper.selectBySql(sql);
        if (lists.size() > 0) {
            obj = lists.get(0);
        }
        return obj;
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#selectByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public List<T> selectByCriteria(Class<T> clazz, OrCriteria orCriteria) {
        return commonDaoMapper.selectByCriteria(clazz, orCriteria);
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#getByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public T getByCriteria(Class<T> clazz, OrCriteria orCriteria) {
        T obj = null;
        List<T> lists = commonDaoMapper.selectByCriteria(clazz, orCriteria);
        if (lists.size() > 0) {
            obj = lists.get(0);
        }
        return obj;
    }

    /**
     * 
     * @see com.base.platform.admin.dao.BaseDao#countByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public int countByCriteria(Class<T> clazz, OrCriteria orCriteria) {
        return commonDaoMapper.countByCriteria(clazz, orCriteria);
    }
}
