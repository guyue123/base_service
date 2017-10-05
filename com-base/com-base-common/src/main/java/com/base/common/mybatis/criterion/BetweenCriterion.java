package com.base.common.mybatis.criterion;

import com.base.common.mybatis.exception.CommonBatisException;

/**
 * Constrains a property to between two values
 */
public class BetweenCriterion implements Criterion {

    private static final long serialVersionUID = 1L;
    private final String propertyName;
    private final Object lo;
    private final Object hi;

    protected BetweenCriterion(String propertyName, Object lo, Object hi) {
        this.propertyName = propertyName;
        this.lo = lo;
        this.hi = hi;
    }

    public String toString() {
        return propertyName + " between " + lo + " and " + hi;
    }

    public String getStatement() throws CommonBatisException {
        return propertyName + " between ";
    }

}