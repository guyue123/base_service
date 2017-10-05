package com.base.common.sql.dao;

/**
 * Dao抽象类 and batches for an SQL Map.
 *
 * @see org.apache.ibatis.session.SqlSessionFactory
 */
public abstract class AbstractDao {

    private DaoTemplate daoTemplate;

    /**
     * @return the daoTemplate
     */
    public DaoTemplate getDaoTemplate() {
        return daoTemplate;
    }

    /**
     * @param daoTemplate
     *            the daoTemplate to set
     */
    public void setDaoTemplate(DaoTemplate daoTemplate) {
        this.daoTemplate = daoTemplate;
    }
}
