package com.base.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.dao.BaseDao;
import com.base.common.mybatis.OrCriteria;
import com.base.common.mybatis.page.Page;
import com.base.common.service.BaseService;

/**
 * 通用Service层实现:有了通用代码后CURD就不用再写了
 * 
 * @author kin wong
 */
@Service
public class BaseServiceImpl<M> implements BaseService<M> {

    @Autowired
    protected BaseDao<M> baseDao;

    public void setBaseDao(BaseDao<M> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public M save(M model) {
        baseDao.save(model);
        return model;
    }

    @Override
    public void update(M model) {
        baseDao.update(model);
    }

    @Override
    public void delete(Class<M> entityClass, Serializable id) {
        baseDao.delete(entityClass, id);
    }

    @Override
    public void deleteObject(M model) {
        baseDao.delete(model);
    }

    @Override
    public M get(Class<M> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public List<M> selectAll(Class<M> clazz) {
        return baseDao.selectByCriteria(clazz, new OrCriteria());
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#selectBySql(java.lang.String)
     */
    @Override
    public List<Map<String, Object>> selectBySql(String sql) {
        return baseDao.selectBySql(sql);
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#getBySql(java.lang.String)
     */
    @Override
    public Map<String, Object> getBySql(String sql) {
        Map<String, Object> obj = null;
        List<Map<String, Object>> lists = baseDao.selectBySql(sql);
        if (lists.size() > 0) {
            obj = lists.get(0);
        }
        return obj;
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#selectByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public List<M> selectByCriteria(Class<M> clazz, OrCriteria orCriteria) {
        return baseDao.selectByCriteria(clazz, orCriteria);
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#countByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public int countByCriteria(Class<M> clazz, OrCriteria orCriteria) {
        return baseDao.countByCriteria(clazz, orCriteria);
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#getResultByPage(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria, int, int)
     */
    @Override
    public Page<M> getResultByPage(Class<M> clazz, OrCriteria orCriteria, int pageNo, int pageSize) {
        return baseDao.getResultByPage(clazz, orCriteria, pageNo, pageSize);
    }

    /**
     * 
     * @see com.base.platform.admin.service.BaseService#getByCriteria(java.lang.Class,
     *      com.base.common.mybatis.OrCriteria)
     */
    @Override
    public M getByCriteria(Class<M> clazz, OrCriteria orCriteria) {
        M obj = null;
        List<M> lists = baseDao.selectByCriteria(clazz, orCriteria);
        if (lists.size() > 0) {
            obj = lists.get(0);
        }
        return obj;
    }

}
