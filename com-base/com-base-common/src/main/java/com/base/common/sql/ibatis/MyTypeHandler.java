package com.base.common.sql.ibatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.log4j.Logger;

public class MyTypeHandler implements TypeHandler<Object> {

    private static final Logger logger = Logger.getLogger(MyTypeHandler.class);

    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, (String) parameter);
    }

    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

    @Override
    public Object getResult(ResultSet rs, int index) throws SQLException {
        return rs.getObject(index);
    }

}
