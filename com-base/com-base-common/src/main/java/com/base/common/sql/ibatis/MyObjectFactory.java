package com.base.common.sql.ibatis;

import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.log4j.Logger;

public class MyObjectFactory extends DefaultObjectFactory {

    private static final Logger logger = Logger.getLogger(MyObjectFactory.class);

    public Object create(Class type) {
        return super.create(type);
    }

    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

}
