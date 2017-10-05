package com.base.common.mybatis.plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.base.common.mybatis.annotation.Column;
import com.base.common.mybatis.annotation.Id;
import com.base.common.mybatis.annotation.Table;
import com.base.common.mybatis.annotation.Transient;
import com.base.common.mybatis.util.MapperResolver;
import com.base.common.mybatis.util.ReflectionUtil;
import com.base.common.mybatis.util.SimpleCache;
import com.base.common.mybatis.util.TableNameParser;

/**
 * mybatis拦截器，用于单表常用的增删改查常用操作处理
 * 
 * @author hhx
 * 
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class Transfer implements Interceptor {

    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(Transfer.class);

    /**
     * 参数键值：对象类
     */
    private static final String OBJECT_CLASS = "class";

    private volatile boolean init = false;

    /**
     * 参数键值：主键
     */
    private final String KEY = "key";

    /**
     * 参数键值：对象
     */
    private final String ENTITY = "entity";

    /**
     * 参数键值：sql
     */
    private final String SQL = "sql";

    /**
     * 参数键值：序列
     */
    private final String SEQUENCE = "sequence";

    /**
     * SQL文集合
     */
    private Set<String> commondSqlIdSet = new TreeSet<String>();

    /**
     * 原始SQL文
     */
    private Map<String, String> rawSqlMap = new HashMap<String, String>();

    /**
     * SQL配置文件路径
     */
    private String mapper;

    /**
     * SQL配置Mapper类
     */
    private String mapperClass;

    /**
     * 默认配置文件类路径
     */
    private static String defaultMapper = "com/base/common/mybatis/dao/mapper/CommonDaoMapper.xml";

    /**
     * 默认配置文件类路径
     */
    private static String defaultMapperClass = "com.base.common.mybatis.dao.mapper.CommonDaoMapper";

    /**
     * 初始化参数文件
     */
    private void init() {
        if (init)
            return;
        try {
            commondSqlIdSet = MapperResolver.getElementsSet("id", mapper);
            init = true;
        } catch (SAXException e) {
            logger.error("解析CommonDaoMapper.xml XML文件错误", e);
        }
    }

    /**
     * 处理动态结果集
     * 
     * @param invocation
     * @param handleKey
     */
    private void processDynamicResultMapIntercept(Invocation invocation, boolean handleKey) {
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[0];
        final Object parameter = queryArgs[1];
        String targetSimpleName = getTableName(parameter, true);
        String tableSimpleName = getTableName(parameter, false);
        reconstructSqlSource(ms, parameter, targetSimpleName, handleKey, APPENDTYPE.NONE);
        Class<?> targetClass = null;
        try {
            targetClass = Class.forName(tableSimpleName);
        } catch (ClassNotFoundException e) {
            logger.error("找不到指定类", e);
        }
        ResultMap.Builder builder = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), targetClass,
                new ArrayList<ResultMapping>());
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(0, builder.build());
        ReflectionUtil.setTarget(ms, resultMaps, "resultMaps");
    }

    private void processIntercept(Invocation invocation, boolean handleKey, APPENDTYPE appendType) {
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[0];
        final Object parameter = queryArgs[1];
        String tableSimpleName = getTableName(parameter, true);
        reconstructSqlSource(ms, parameter, tableSimpleName, handleKey, appendType);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void reconstructSqlSource(MappedStatement ms, Object parameter, String tableSimpleName, boolean handleKey,
            APPENDTYPE appendType) {
        SqlSource sqlSource = ms.getSqlSource();
        if (sqlSource instanceof RawSqlSource) {
            BoundSql boundSql = null;

            String sql = null;
            if (rawSqlMap.containsKey(ms.getId())) {
                sql = rawSqlMap.get(ms.getId());
            } else {
                boundSql = sqlSource.getBoundSql(parameter);
                sql = boundSql.getSql();

                rawSqlMap.put(ms.getId(), sql);
            }

            SimpleCache.MAPTYPE mapperType = MapperResolver.getSqlIdToCacheMap().get(getSqlKey(ms.getId()));
            if (!isNullOrEmpty(tableSimpleName)) {
                sql = getSql(tableSimpleName, sql, mapperType);
            }
            if (appendType == APPENDTYPE.INSERT) {
                sql = appendInsertStatement(sql, ((HashMap) parameter).get(ENTITY));
            } else {
                if (appendType == APPENDTYPE.UPDATE) {
                    sql = appendUpdateStatement(sql, ((HashMap) parameter).get(ENTITY));
                }
                if (handleKey) {
                    if (((HashMap) parameter).containsKey(KEY)) {
                        sql = fixKeyStatement(sql, ((HashMap) parameter).get(KEY), getEntityClass((HashMap) parameter),
                                (HashMap) parameter);
                    } else {
                        sql = fixKeyStatement(sql, ((HashMap) parameter).get(ENTITY));
                    }
                }
            }

            // replace all fields
            sql = replaceSpecialFields(parameter, sql);

            RawSqlSource rawSqlSource = new RawSqlSource(ms.getConfiguration(), sql, null);
            ReflectionUtil.setTarget(ms, rawSqlSource, "sqlSource");
        } else if (sqlSource instanceof DynamicSqlSource) {
            SqlNode sqlNode = null;
            try {
                sqlNode = (SqlNode) ReflectionUtil.getFieldValue(sqlSource, "rootSqlNode");
            } catch (Exception e) {
                logger.error("XML解析错误", e);
            }
            if (sqlNode instanceof MixedSqlNode) {
                List<SqlNode> contents = (List<SqlNode>) ReflectionUtil.getFieldValue(sqlNode, "contents");
                SqlNode node = contents.get(0);
                if (node instanceof StaticTextSqlNode) {
                    String sql = "";
                    if (rawSqlMap.containsKey(ms.getId())) {
                        sql = rawSqlMap.get(ms.getId());
                    } else {
                        sql = (String) ReflectionUtil.getFieldValue(node, "text");

                        rawSqlMap.put(ms.getId(), sql);
                    }

                    SimpleCache.MAPTYPE mapperType = MapperResolver.getSqlIdToCacheMap().get(getSqlKey(ms.getId()));
                    if (!isNullOrEmpty(tableSimpleName)) {
                        sql = getSql(tableSimpleName, sql, mapperType);
                    }
                    if (appendType == APPENDTYPE.INSERT) {
                        sql = appendInsertStatement(sql, ((HashMap) parameter).get(ENTITY));
                    } else if (appendType == APPENDTYPE.UPDATE) {
                        sql = appendUpdateStatement(sql, ((HashMap) parameter).get(ENTITY));
                        if (handleKey) {
                            if (((HashMap) parameter).containsKey(KEY)) {
                                sql = fixKeyStatement(sql, ((HashMap) parameter).get(KEY),
                                        getEntityClass((HashMap) parameter), (HashMap) parameter);
                            } else {
                                sql = fixKeyStatement(sql, ((HashMap) parameter).get(ENTITY));
                            }
                        }
                    }

                    sql = replaceSpecialFields(parameter, sql);

                    StaticTextSqlNode staticTextSqlNode = new StaticTextSqlNode(sql);
                    contents.set(0, staticTextSqlNode);
                    ReflectionUtil.setTarget(sqlNode, contents, "contents");
                    DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
                    ReflectionUtil.setTarget(ms, dynamicSqlSource, "sqlSource");
                } else {
                    throw new RuntimeException(
                            "reconstruct sqlSource exception, sqlNode is not instanceof StaticTextSqlNode!");
                }
            } else {
                throw new RuntimeException("reconstruct sqlSource exception, sqlNode is not instanceof MixedSqlNode!");
            }
        }
    }

    /**
     * 将特定SQL文转换成实际执行的SQL
     * 
     * @param tableSimpleName
     *            表名
     * @param sql
     *            SQL
     * @param mapperType
     *            类型
     * @return SQL
     */
    private String getSql(String tableSimpleName, String sql, SimpleCache.MAPTYPE mapperType) {
        String cachePreSql;
        if (!sql.contains("@")) {
            cachePreSql = SimpleCache.get(tableSimpleName, mapperType);
            sql = (cachePreSql == null ? MapperResolver.getMapTypeToRawSqlMap().get(mapperType) : cachePreSql);

            sql = sql.replace("*", "!FIELDS!");
        }
        if (sql.contains("@")) {
            TableNameParser tableNameParser;
            tableNameParser = new TableNameParser("@", "@", tableSimpleName);
            sql = tableNameParser.parse(sql);
            SimpleCache.put(tableSimpleName, sql, mapperType);
        }
        return sql;
    }

    /**
     * 替换特殊SQL数据
     * 
     * @param parameter
     *            参数对象
     * @param sql
     *            SQL语句
     * @return 处理过的SQL
     */
    @SuppressWarnings("rawtypes")
    public String replaceSpecialFields(Object parameter, String sql) {
        if (sql.contains("!FIELDS!")) {
            sql = replaceSelectStatement(sql, getEntityClass((HashMap) parameter));
        }

        if (sql.contains("!SEQUENCE!")) {
            sql = sql.replace("!SEQUENCE!", (String) ((HashMap) parameter).get(SEQUENCE));
        }

        if (sql.contains("!SQL!")) {
            sql = sql.replace("!SQL!", (String) ((HashMap) parameter).get(SQL));
        }

        return sql;
    }

    /**
     * 获得实体对象的类
     * 
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Class getEntityClass(HashMap params) {
        if (params.containsKey(ENTITY)) {
            return params.get(ENTITY).getClass();
        }

        if (params.containsKey(OBJECT_CLASS)) {
            return (Class) params.get(OBJECT_CLASS);
        }

        return null;
    }

    /**
     * 使用表字段名替换!FIELDS!
     * 
     * @param sql
     *            SQL语句
     * @param clazz
     *            类名
     * @return SQL语句
     */
    @SuppressWarnings("rawtypes")
    private String replaceSelectStatement(String sql, Class clazz) {
        StringBuilder fieldsBuffer = new StringBuilder();

        List<Field> fieldList = ReflectionUtil.getAllFields(clazz);
        if (fieldList != null) {
            boolean firstVal = true;
            for (int i = 0; i < fieldList.size(); i++) {
                Field field = fieldList.get(i);
                String columnName = getColumnName(field);
                if (!isNullOrEmpty(columnName)) {
                    if (!firstVal) {
                        fieldsBuffer.append(",");
                    }

                    fieldsBuffer.append(columnName.toUpperCase());
                    fieldsBuffer.append(" ");
                    fieldsBuffer.append(field.getName().toUpperCase());
                    firstVal = false;
                }
            }
        } else {
            throw new RuntimeException("entity key has no field exception!");
        }

        return sql.replace("!FIELDS!", fieldsBuffer);
    }

    /**
     * 根据字段名设置更新语句
     * 
     * @param sql
     *            sql语句
     * @param obj
     *            对象
     * @return sql语句
     */
    private String appendUpdateStatement(String sql, Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append(sql).append(" ");
        List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
        if (fieldList != null) {
            boolean firstVal = true;
            for (int i = 0; i < fieldList.size(); i++) {
                boolean add = false;
                Field field = fieldList.get(i);
                try {
                    add = field.get(obj) != null;
                } catch (IllegalAccessException e) {
                    logger.error("字段分析错误", e);
                }
                if (add) {
                    String filedName = getColumnName(field);
                    if (!isNullOrEmpty(filedName)) {
                        if (!firstVal) {
                            builder.append(",");
                        } else {
                            builder.append(" set ");
                        }

                        builder.append(filedName).append("=");
                        builder.append("#{").append(ENTITY).append(".").append(field.getName()).append("}");
                        firstVal = false;
                    }
                }
            }
        }
        return builder.toString();
    }

    /**
     * 字符串是否为空
     * 
     * @param val
     *            字符串
     * @return
     */
    private boolean isNullOrEmpty(String val) {
        if (val == null) {
            return true;
        }

        if ("".equals(val.trim())) {
            return true;
        }

        return false;
    }

    /**
     * 构建插入语句
     * 
     * @param sql
     *            sql语句
     * @param obj
     *            类对象
     * @return
     */
    private String appendInsertStatement(String sql, Object obj) {
        StringBuilder builderPre = new StringBuilder();
        StringBuilder builderSuf = new StringBuilder();
        builderPre.append(" (");
        builderSuf.append(" values (");
        List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
        if (fieldList != null) {
            boolean firstVal = true;
            for (int i = 0; i < fieldList.size(); i++) {
                boolean add = false;
                Field field = fieldList.get(i);
                try {
                    add = field.get(obj) != null;
                } catch (IllegalAccessException e) {
                    logger.error("字段访问错误", e);
                }
                if (add) {
                    String filedName = getColumnName(field);
                    if (!isNullOrEmpty(filedName)) {
                        if (!firstVal) {
                            builderPre.append(",");
                            builderSuf.append(",");
                        }

                        builderPre.append(filedName);
                        builderSuf.append("#{").append(ENTITY).append(".").append(field.getName()).append("}");
                        firstVal = false;
                    }
                }
            }
        } else {
            throw new RuntimeException("entity key has no field exception!");
        }
        builderPre.append(")");
        builderSuf.append(")");
        return sql + builderPre + builderSuf;
    }

    /**
     * 获得字段名
     * 
     * @param field
     *            字段对象
     * @return 字段名
     */
    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        String filedName = field.getName();
        if (column != null && !isNullOrEmpty(column.name())) {
            filedName = column.name();
        }

        Transient trans = field.getAnnotation(Transient.class);
        if (trans != null) {
            return null;
        }

        return filedName;
    }

    /**
     * 获得ID字段名
     * 
     * @param field
     * @return
     */
    private String getIdColumnName(Field field) {
        Id id = field.getAnnotation(Id.class);

        if (id == null) {
            return null;
        }

        Column column = field.getAnnotation(Column.class);
        String filedName = field.getName();
        if (column != null && !isNullOrEmpty(column.name())) {
            filedName = column.name();
        }

        return filedName;
    }

    /**
     * 根据对象查询主键查询
     * 
     * @param sql
     *            sql文
     * @param obj
     *            查询对象
     * @return
     */
    private String fixKeyStatement(String sql, Object obj) {
        List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(" where ");

        if (fieldList != null) {
            boolean hasId = false;
            for (Field field : fieldList) {
                String columnName = getIdColumnName(field);
                if (columnName != null) {
                    try {
                        if (field.get(obj) == null) {
                            throw new RuntimeException("entity key has no value exception!");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("field get value error!");
                    }

                    if (hasId) {
                        builder.append(" and ");
                    }
                    builder.append(columnName).append("=#{").append(ENTITY).append(".").append(field.getName())
                            .append("}");

                    hasId = true;
                }
            }

            if (!hasId) {
                throw new RuntimeException("entity ENTITY has no id field exception!");
            }
        } else {
            throw new RuntimeException("entity ENTITY has no field exception!");
        }

        return builder.toString();
    }

    /**
     * 获得查询条件
     * 
     * @param sql
     *            sql文
     * @param obj
     *            参数对象
     * @param clazz
     *            数据对象
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String fixKeyStatement(String sql, Object obj, Class clazz, Map params) {
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(" where ");

        // 基本数据类型
        if (isBaseDataType(obj)) {
            // 读取字段ID标识
            List<Field> fieldList = ReflectionUtil.getAllFields(clazz);
            if (fieldList != null) {
                boolean hasId = false;
                for (Field field : fieldList) {
                    String columnName = getIdColumnName(field);
                    if (columnName != null) {
                        builder.append(columnName).append("=#{").append(KEY).append(".").append(field.getName())
                                .append("}");

                        // 重新将对象设置到参数中
                        try {
                            Object classObj = clazz.newInstance();
                            ReflectionUtil.setTarget(classObj, obj, field.getName());
                            params.put(KEY, classObj);
                        } catch (Exception e) {
                            throw new RuntimeException(clazz.getName() + " can't make instance!");
                        }

                        hasId = true;
                        break;
                    }
                }

                if (!hasId) {
                    throw new RuntimeException(clazz.getName() + " has no id field exception!");
                }
            } else {
                throw new RuntimeException(clazz.getName() + " has no field exception!");
            }
        } else {
            List<Field> fieldList = ReflectionUtil.getAllFields(obj.getClass());
            if (fieldList != null) {
                boolean added = false;
                for (int i = 0; i < fieldList.size(); i++) {
                    boolean add = false;
                    Field field = fieldList.get(i);
                    try {
                        add = field.get(obj) != null;
                    } catch (IllegalAccessException e) {
                        logger.error("字段访问错误", e);
                    }
                    if (add) {
                        String filedName = getColumnName(field);
                        if (!isNullOrEmpty(filedName)) {
                            if (added) {
                                builder.append(" and ");
                            }

                            builder.append(filedName).append("=#{").append(KEY).append(".").append(field.getName())
                                    .append("}");
                            added = true;
                        }
                    }
                }
            } else {
                throw new RuntimeException("entity key has no field exception!");
            }
        }

        return builder.toString();
    }

    /**
     * 是否是基本数据类型
     * 
     * @param obj
     * @return
     */
    private boolean isBaseDataType(Object obj) {
        return obj instanceof String || obj instanceof Integer || obj instanceof Double || obj instanceof Float
                || obj instanceof Short || obj instanceof Long || obj instanceof Boolean || obj instanceof Byte;
    }

    /**
     * 获得数据库表名或者类名
     * 
     * @param obj
     *            对象类
     * @param simple
     *            是否表名
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getTableName(Object obj, boolean simple) {
        String name;
        if (obj instanceof HashMap) {
            Object param1 = ((HashMap) obj).get("param1");

            if (isBaseDataType(param1)) {
                return "";
            }

            Class clazz = null;
            if (param1 instanceof Class) {
                clazz = ((Class) param1);
            } else {
                clazz = param1.getClass();
            }

            name = getTableName(clazz, simple);

            if (!simple) {
                return name;
            }
            int start = name.lastIndexOf(".");
            if (start > 0)
                name = name.substring(start + 1, name.length());
            return name;
        } else if (obj instanceof Class) {
            return getTableName(((Class) obj), simple);
        } else {
            if (obj != null) {
                return getTableName(obj.getClass(), simple);
            }

            return "";
        }
    }

    /**
     * 从类对象获得表名
     * 
     * @param clazz
     *            类
     * @param simple
     *            是否表名
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String getTableName(Class clazz, boolean simple) {
        // 类名
        if (!simple) {
            return clazz.getName();
        }

        Table t = (Table) clazz.getAnnotation(Table.class);
        if (t != null && !isNullOrEmpty(t.name())) {
            return t.name();
        }

        return clazz.getSimpleName();
    }

    /**
     * 开始拦截
     */
    public synchronized Object intercept(Invocation invocation) throws Throwable {
        // 没有设置参数则使用默认参数
        if (this.mapper == null || this.mapperClass == null || "".equals(this.mapper) || "".equals(this.mapperClass)) {
            this.mapper = defaultMapper;
            this.mapperClass = defaultMapperClass;
        }

        if (!init) {
            init();
        }

        boolean intercept = false;
        Object[] obj = invocation.getArgs();
        Object msObj = obj[0];
        if (msObj instanceof MappedStatement) {
            intercept = commondSqlIdSet.contains(((MappedStatement) msObj).getId());
        }

        if (intercept) {// dynamic
            String sqlId = ((MappedStatement) msObj).getId();
            if (equalsSqlId(MapperResolver.SELECT_BY_CRITERIA, sqlId)) {
                processDynamicResultMapIntercept(invocation, false);
            } else if (equalsSqlId(MapperResolver.SELECT_BY_PRIMARYKEY, sqlId)) {
                processDynamicResultMapIntercept(invocation, true);
            } else {// static
                if (equalsSqlId(MapperResolver.DELETE_BY_PRIMARYKEY, sqlId)) {
                    processIntercept(invocation, true, APPENDTYPE.NONE);
                } else if (equalsSqlId(MapperResolver.UPDATE_BY_PRIMARYKEY, sqlId)) {
                    processIntercept(invocation, true, APPENDTYPE.UPDATE);
                } else if (equalsSqlId(MapperResolver.UPDATE_BY_CRITERIA, sqlId)) {
                    processIntercept(invocation, false, APPENDTYPE.UPDATE);
                } else if (equalsSqlId(MapperResolver.INSERT, sqlId)) {
                    processIntercept(invocation, false, APPENDTYPE.INSERT);
                } else {
                    processIntercept(invocation, false, APPENDTYPE.NONE);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 是否和默认Key相同
     * 
     * @param sqlKey
     * @param sqlId
     * @return
     */
    private boolean equalsSqlId(String sqlKey, String sqlId) {
        return sqlId.equals(this.mapperClass + "." + sqlKey);
    }

    /**
     * @param sqlKey
     * @return
     */
    private String getSqlKey(String sqlId) {
        return sqlId.replace(this.mapperClass + ".", "");
    }

    /**
     * 继承父类
     */
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * 继承父类
     */
    public void setProperties(Properties properties) {
        if (properties == null) {
            return;
        }

        this.mapper = properties.getProperty("mapper");
        this.mapperClass = properties.getProperty("mapperClass");
    }

    /**
     * 操作类型
     * 
     * @author Administrator
     * 
     */
    private enum APPENDTYPE {
        UPDATE, INSERT, NONE;
    }
}