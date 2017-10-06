/**
 *  Copyright 2014 govnet, Inc.
 *
 */
package com.base.scheduler.orm.dao;

import java.util.List;

import com.base.common.sql.dao.AbstractDao;
import com.base.common.sql.dao.DaoTemplate;
import com.base.scheduler.orm.pojo.SchedulerConfig;

/**
 * 访问表SCHEDULER_CONFIG的DAO
 *
 * @author <a href="mailto:huhuaxiang@govnet.com.cn">hhx</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2014年8月28日
 */
public class SchedulerConfigDao  extends AbstractDao {
	/**
	 * 返回一个新的 {@link SchedulerConfigDao}实例；
	 * @return 一个新的 {@link SchedulerConfigDao}实例；
	 */
	public static SchedulerConfigDao newInstance() {
		return new SchedulerConfigDao();
	}
	
	/**
	 * 默认构造函数；
	 * 使用一个 {@link DaoTemplate}对象构建；
	 */
	public SchedulerConfigDao() {
		this(new DaoTemplate());
	}
	
	/**
	 * @param daoTemplate {@link DaoTemplate}
	 */
	public SchedulerConfigDao(DaoTemplate daoTemplate) {
		setDaoTemplate(daoTemplate);
	}
	
	/**
	 * 查询配置
	 * @param record 查询条件
	 * @return 配置信息
	 */
	public List<SchedulerConfig> selectConfig(Object record) {
		return (List<SchedulerConfig>)getDaoTemplate().select("com.base.scheduler.schedulerConfig.selectConfig", record);
	}
}
