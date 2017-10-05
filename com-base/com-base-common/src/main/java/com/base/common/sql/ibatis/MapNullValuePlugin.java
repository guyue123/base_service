package com.base.common.sql.ibatis;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

/**
 * 处理 Map返回类型(resultType)为空值的Plugin， 因为Mybatis返回类型为Map时， 不设置null值的键key，
 * 一些业务的处理必须每个Map的键个数一样 <XMP>
 * MyBatis允许你在某一点拦截已映射语句执行的调用。默认情况下，MyBatis允许使用插件来拦截方法调用： Executor (update,
 * query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters) ResultSetHandler
 * (handleResultSets, handleOutputParameters) StatementHandler (prepare,
 * parameterize, batch, update, query)
 * 这些类中方法的详情可以通过查看每个方法的签名来发现，而且它们的源代码在MyBatis的发行包中有。
 * 你应该理解你覆盖方法的行为，假设你所做的要比监视调用要多。
 * 如果你尝试修改或覆盖一个给定的方法，你可能会打破MyBatis的核心。这是低层次的类和方法，要谨慎使用插件。
 * 
 * 使用插件是它们提供的非常简单的力量。简单实现拦截器接口，要确定你想拦截的指定签名。 </XMP>
 * 
 * @author Administrator、Dr.Deng
 * @version 2010.11.11
 * 
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class MapNullValuePlugin implements Interceptor {

    private static final Logger logger = Logger.getLogger(MapNullValuePlugin.class);

    public Object intercept(Invocation invocation) throws Throwable {
        Object obj = invocation.proceed();

        if (obj != null && obj instanceof List<?> && ((List<?>) obj).size() > 0) {
            Object row = ((List<?>) obj).get(0);

            if (row instanceof Map) {// Map 值处理；
                if (logger.isDebugEnabled()) {
                    logger.debug("处理前的数据:\n" + obj);
                }

                Statement stmt = (Statement) invocation.getArgs()[0];
                if (stmt.getResultSet() == null) {
                    return obj;
                }

                java.sql.ResultSetMetaData rsmd = stmt.getResultSet().getMetaData();

                for (int rowIndex = 0; rowIndex < ((List<?>) obj).size(); rowIndex++) {
                    row = ((List<?>) obj).get(rowIndex);
                    String columnLabel;
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        columnLabel = rsmd.getColumnLabel(i);
                        if (((Map) row).containsKey(columnLabel)) {

                        } else {
                            ((Map) row).put(columnLabel, null);// 设置一个空数据;
                        }
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("处理后的数据:\n" + obj);
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("不是要处理的类型:" + row);
                }
            }
            for (int i = 0; i < ((List<?>) obj).size(); i++) {

            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("不是要处理的类型:" + obj);
            }
        }
        return obj;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

}
