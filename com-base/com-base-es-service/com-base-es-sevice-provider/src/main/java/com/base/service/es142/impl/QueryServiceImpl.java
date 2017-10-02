package com.base.service.es142.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.base.service.es.bean.QueryCondition;
import com.base.service.es.bean.SelectResultBean;
import com.base.service.es.bean.StatsResult;
import com.base.service.es.facade.QueryService142;
import com.base.service.es142.util.EsClientUtil;

public class QueryServiceImpl implements QueryService142 {
	private static Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

	@Override
	public List<Map<String, Object>> groupByQuery(QueryCondition cond) throws IOException {

		return null;
	}

	@Override
	public SelectResultBean select(String sql) throws Exception {
		try {
			return EsClientUtil.select(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public long selectCount(String sql) throws Exception {
		try {
			return EsClientUtil.selectCount(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public double selectSum(String sql) throws Exception {
		try {
			return EsClientUtil.selectSum(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public double selectMax(String sql) throws Exception {
		try {
			return EsClientUtil.selectMax(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public double selectMin(String sql) throws Exception {
		try {
			return EsClientUtil.selectMin(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public StatsResult selectStats(String sql) throws Exception {
		try {
			return EsClientUtil.selectStats(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public double selectAvg(String sql) throws Exception {
		try {
			return EsClientUtil.selectAvg(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<Map<String, Object>> selectGroupBy(String sql) throws Exception {
		try {
			return EsClientUtil.selectGroupBy(sql);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public int insert(String indexName, String typeName, List<Map<String, Object>> dataList) throws Exception {
		try {
			return EsClientUtil.bulkAddIndex(indexName, typeName, dataList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
