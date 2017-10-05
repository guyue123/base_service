/**
 * 
 */
package com.base.common.json;

import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json日期类型格式转换
 * 
 * @author Administrator
 *
 */
public class JsonNullProcessor implements JsonValueProcessor {

    public JsonNullProcessor() {
        super();
    }

    @Override
    public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    @Override
    public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    private Object process(Object value) {
        if (value instanceof JSONNull) {
            return "";
        }

        return value == null ? "" : value;
    }

}
