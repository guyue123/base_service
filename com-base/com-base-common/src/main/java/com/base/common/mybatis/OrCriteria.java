package com.base.common.mybatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.base.common.mybatis.criterion.Order;

public class OrCriteria {

    private Integer maxResults;
    private Integer firstResult;
    private List<Order> orderList;
    private int orderListSize = 0;
    private List<Criteria> orCriteria;
    private int orCriteriaSize = 0;

    public int getOrderListSize() {
        return orderListSize;
    }

    public void setOrderListSize(int orderListSize) {
        this.orderListSize = orderListSize;
    }

    public int getOrCriteriaSize() {
        return orCriteriaSize;
    }

    public void setOrCriteriaSize(int orCriteriaSize) {
        this.orCriteriaSize = orCriteriaSize;
    }

    private boolean distinct;

    public OrCriteria() {
        orderList = new ArrayList<Order>();
        orCriteria = new ArrayList<Criteria>();
    }

    public OrCriteria addOrder(Order order) {
        if (order == null || order.getPropertyName() == null || "".equals(order.getPropertyName())) {
            throw new IllegalArgumentException("order or propertyName can not be null");
        }
        orderList.add(order);
        orderListSize++;
        return this;
    }

    public Criteria add(Criteria criteria) {
        orCriteria.add(criteria);
        orCriteriaSize++;
        return criteria;
    }

    public void or(Criteria criteria) {
        orCriteria.add(criteria);
        orCriteriaSize++;
    }

    public Criteria or() {
        Criteria criteria = new Criteria();
        orCriteria.add(criteria);
        orCriteriaSize++;
        return criteria;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public List<Order> getOrderList() {
        return Collections.unmodifiableList(orderList);
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public List<Criteria> getOrCriteria() {
        return Collections.unmodifiableList(orCriteria);
    }

    public void setOrCriteria(List<Criteria> orCriteria) {
        this.orCriteria = orCriteria;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}
