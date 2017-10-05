package com.base.common.sql.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.common.string.StringUtil;


public class CommonDao extends AbstractDao {
	/**
	 * 返回一个新的 {@link CommonDao}实例；
	 * @return 一个新的 {@link CommonDao}实例；
	 */
	public static CommonDao newInstance(){
		return new CommonDao();
	}
	
	/**
	 * 默认构造函数；
	 * 使用一个 {@link DaoTemplate}对象构建；
	 */
	public CommonDao() {
		this(new DaoTemplate());
	}
	
	/**
	 * @param daoTemplate {@link DaoTemplate}
	 */
	public CommonDao(DaoTemplate daoTemplate) {
		setDaoTemplate(daoTemplate);
	}

	/**
	 * 测试数据库连接
	 * 
	 * @return
	 */
	public Object testConnection() {
		return getDaoTemplate().selectOne("com.base.common.test");
	}
	
	/**
	 * 执行SQL文
	 * 
	 * @param sql sql文
	 * @return 查询结果
	 */
	public List<Map<String, Object>> query(String sql) {
	    if (StringUtil.isEmptyOrNull(sql)) {
	        return null;
	    }
	    
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("sql", sql);
	    
	    return (List<Map<String, Object>>)getDaoTemplate().select("com.base.common.query", params);
	}
}
