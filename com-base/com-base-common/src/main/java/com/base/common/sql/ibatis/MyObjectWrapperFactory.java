package com.base.common.sql.ibatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.log4j.Logger;

public class MyObjectWrapperFactory extends DefaultObjectWrapperFactory {
	
    private static final Logger logger = Logger.getLogger(MyObjectWrapperFactory.class);

	public boolean hasWrapperFor(Object object) {
		return false;
	}

	public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
	    if (logger.isDebugEnabled()) {
    		logger.debug(object);
    		logger.debug(metaObject.getOriginalObject());
    		logger.debug(metaObject.getObjectWrapper());
	    }
		return metaObject.getObjectWrapper();
	}

}
