package com.base.common.mybatis.criterion;

import java.io.Serializable;

import com.base.common.mybatis.exception.CommonBatisException;

/**
 * a interface should be implemented to represent a constraint in a criteria
 * <p/>
 * *
 */
public interface Criterion extends Serializable {

    /**
     * Render the SQL fragment
     *
     * @throws com.base.common.mybatis.exception.CommonBatisException Problem during rendering.
     */
    public String getStatement() throws CommonBatisException;

}