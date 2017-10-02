/**
 * 
 */
package com.base.service.es.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 对象事件
 * 
 * @author base
 *
 */
public abstract class QueryCondition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ACTION_GET = "GET";
	
	public static final String ACTION_POST = "POST";
	
	public static final String ACTION_PUT = "PUT";
	
	/**
	 * 开始时间
	 * 
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 开始
	 */
	private int from = 0;
	
	/**
	 * 返回数据量
	 */
	private int size = 10;
	
	/**
	 * 
	 * index
	 */
	private String index;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 排序格式：字段名:排序方式
	 */
	private String sort;
	
	/**
	 * 聚合查询：多个字段名用逗号分隔
	 */
	private String groupBy;
	
	/**
	 * 查询数量
	 */
	private int groupBySize = 10000;
	
	/**
	 * 脚本归并
	 */
	private Map<String, String> scriptedMetric; 
	
	
	/**
	 * 请求类型：get/post/put
	 */
	private String actionType = "GET";
	
	/**
	 * 多字段OR查询
	 * 格式：field1=value1,value2;field2=value3,value4
	 */
	private String shouldQuery;
	
	/**
	 * shoud查询最小匹配数
	 */
	private int minShouldMatch = 1;
	
	
	public QueryCondition() {
		
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 将查询条件转化查询json
	 * @return
	 */
	public abstract String toQueryJson(boolean needAggs);
	/**
	 * 翻页数据
	 * @return
	 */
	public String getFromSizeQueryJson() {
		return getFromSizeQueryJson(from, size);
	}
	
	/**
	 * 翻页数据
	 * @return
	 */
	public String getFromSizeQueryJson(int from, int size) {
		return "\"from\": " + from + ",\n" + 
		"\"size\": " + size + "\n";
	}
	
	/**
	 * geo距离查询
	 * @return
	 */
	public String getGeoQueryJson(String fieldName, double lat, double lng, int distance) {
	   if (distance <= 0) {
		   return "";
	   }
	   
       return "\"geo_distance\" : {\n" + 
                "\"distance\" : \"" + distance + "m\",\n" + 
                "\"" + fieldName + "\" : {\n" + 
                    "\"lat\" : " + lat + ",\n" + 
                    "\"lon\" : " + lng + "\n" +
                "}\n" + 
            "}\n";
	}

	
	/**
	 * 精准查询
	 * @param fieldName
	 * @param fieldValue
	 * @param boost
	 * @return
	 */
	public String termQuery(String fieldName, String fieldValue, float boost) {
	   if (fieldValue == null || "".equals(fieldName)) {
		   return null;
	   }
	   
	   String[] vals = fieldValue.split(",");
	   if (vals.length >= 1) {
		   StringBuffer sb = new StringBuffer();
		   sb.append("{\n" + "\"terms\": {\n");
		   sb.append("\"" + fieldName + "\" : [\n");
		   int i = 0;
		   for (String val : vals) {
			   if ("".equals(val.trim())) {
				   continue;
			   }
			   
			   if(i > 0 ){
				   sb.append(",");
			   }
			   
			   sb.append("\"");
			   sb.append(val);
			   sb.append("\"");
			   
			   i++;
		   }
		   
		   if (i == 0) {
			   return null;
		   }
		   
		   sb.append("]");
		   sb.append("}}");
		   
		   return sb.toString();
	   }
	   
	   return null;
	}
	
	/**
	 * should查询
	 * @param fieldName
	 * @param fieldValue
	 * @param boost
	 * @return
	 */
	public String shouldQuery() {
	   if (shouldQuery == null || "".equals(shouldQuery)) {
		   return null;
	   }
	   
	   String[] kv = shouldQuery.split(";");
	   
	   List<String> bfList = new ArrayList<>();
	   for (String map : kv) {
		   String[] fv = map.split("=");
		   
		   if (fv.length != 2) {
			   continue;
		   }
		   
		   String fieldName = fv[0];
		   String fieldValue = fv[1];
		   
		   String[] vals = fieldValue.split(",");
		   
		   if (vals.length >= 1) {
			   for (String val : vals) {
				   if ("".equals(val.trim())) {
					   continue;
				   }
				   StringBuffer sb = new StringBuffer();
				   sb.append("{\n" + "\"term\": {\n");
				   sb.append("\"" + fieldName + "\" : \n");
				   sb.append("\"");
				   sb.append(val.trim());
				   sb.append("\"");

				   sb.append("}}\n");
				   bfList.add(sb.toString());
			   }
		   }
	   }
	   
	   if (bfList.isEmpty()) {
		   return null;
	   }
	   
	   StringBuffer sb = new StringBuffer();
	   sb.append("\"should\" : [\n");
	   for (int i = 0; i < bfList.size();i++) {
		   if (i > 0) {
			   sb.append(",\n");
		   }
		   
		   sb.append(bfList.get(i));
	   }
	   sb.append("],\n");
	   sb.append("\"minimum_should_match\" : " + minShouldMatch + ",\n");
	   sb.append("\"boost\" : 1.0");

	   
	   return sb.toString();
	}
	
	/**
	 * 范围查询
	 * @return
	 */
	public String getRangeQueryJson(String timeFieldName) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		String format2 = "yyyyMMddHHmmss";
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(format2);
		
		String gte = null;
		if(startTime != null && !"".equals(startTime)) {
			try {
				gte = "\"gte\": " + dateFormat2.format(dateFormat.parse(startTime)) + "";
			} catch (ParseException e) {
			}
			
		}
		
		String lte = null;
		if(endTime != null && !"".equals(endTime)) {
			try {
				lte = "\"lte\": " + dateFormat2.format(dateFormat.parse(endTime)) + "";
			} catch (ParseException e) {
			}
		}
		
		if (gte == null && lte == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		sb.append("\"range\" : {\n");
		sb.append("\"" + timeFieldName + "\" : {\n");
		if (gte != null && lte != null) {
			sb.append(gte + ",\n" + lte + "\n");
		} else if (gte != null) {
			sb.append(gte + "\n");
		} else {
			sb.append(lte + "\n");
		}
		
		sb.append("}\n");
		sb.append("}\n");
		sb.append("}\n");
		
		return sb.toString();
	}
	
	/**
	 * 查询位置
	 * @return
	 */
	public String getEndPoint(String actType) {
		StringBuffer sb = new StringBuffer("/");
		if (index != null && !"".equals(index)) {
			sb.append(index);
			sb.append("/");
		}
		
		if (type != null && !"".equals(type)) {
			sb.append(type);
			sb.append("/");
		}
		
		sb.append(actType);
		
		return sb.toString();
	}
	
	/**
	 * 设置排序
	 * @return
	 */
	public String getSortStr() {
		if (sort == null || "".equals(sort)) {
			return "";
		}
		
		String[] sortArr = sort.split(":");
		String fieldName = "start_time";
		String order = "desc";
		if (sortArr.length == 2) {
			fieldName = sortArr[0];
			order = sortArr[1];
		} else if(sortArr.length == 1) {
			fieldName = sortArr[0];
		} else {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("\"sort\" : [\n{\n");
		sb.append("\"" + fieldName + "\" : {\"order\" : \"" + order + "\"}\n");
		sb.append("}]");
		
		return sb.toString();
	}
	
	/**
	 * 聚合条件
	 * @return
	 */
	public String getAggsStr() {
		if (groupBy == null || "".equals(groupBy)) {
			return null;
		}
		
		return getAggsStr(groupByList());
	}


	/**
	 * @return
	 */
	public List<String> groupByList() {
		if (groupBy == null || "".equals(groupBy)) {
			return null;
		}
		
		String[] fields = groupBy.split(",");
		List<String> list = new ArrayList<>();
		for (String f : fields) {
			if ("".equals(f)) {
				continue;
			}
			
			list.add(f.trim());
		}
		return list;
	}
	
	/**
	 * 聚合条件
	 * @return
	 */
	private String getAggsStr(List<String> fieldsList) {
		if (fieldsList == null || fieldsList.isEmpty()) {
			return null;
		}
		
		String field = fieldsList.get(0).trim();
		if (field == null || "".equals(field.trim())) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		fieldsList.remove(0);
		sb.append("\"aggs\" : {\n");
		sb.append("\"" + field + "\" : {\n");
		if (scriptedMetric != null && scriptedMetric.containsKey(field)) {
			sb.append("\"scripted_metric\" : {\n");
			sb.append(scriptMetric(field, scriptedMetric.get(field)));
		} else {
			sb.append("\"terms\" : {\n");
			sb.append("\"field\" : \"");
			sb.append(field);
			sb.append("\"");
			
			sb.append(",\"size\" : ");
			sb.append(groupBySize);
			
		}
		sb.append("}");
		String subStr = getAggsStr(fieldsList);
		if (subStr != null) {
			sb.append(",\n");
			sb.append(subStr);
		}
		sb.append("}}");
		
		return sb.toString();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public String scriptMetric(String field, String scriptMetric) {
        StringBuffer sb = new StringBuffer();
        if (scriptMetric != null && scriptMetric.startsWith("_concat_")) {
        	String[] concat = scriptMetric.split(":");
        	String[] fields = concat[1].split(",");
        	
        	List<String> valList = new ArrayList<>();
        	for (String f : fields) {
        		valList.add("doc['" + f.trim() + "'].value");
        	}
        	
	        sb.append("\"init_script\" : \"params._agg.transactions = []\",\n");
	        sb.append("\"map_script\" : \"String concatStr=" + StringUtils.join(valList, "+ '#' +") + ";if(!params._agg.transactions.contains(concatStr)) params._agg.transactions.add(concatStr)\",\n");
	        sb.append("\"combine_script\" : \"return params._agg.transactions.join(';')\",\n");
	        sb.append("\"reduce_script\" : \"return params._aggs.join(';')\"\n");
        } else {
        	return scriptMetric;
        }
        
        return sb.toString();
	}
	
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getGroupBy() {
		return groupBy;
	}


	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}


	public int getGroupBySize() {
		return groupBySize;
	}


	public void setGroupBySize(int groupBySize) {
		this.groupBySize = groupBySize;
	}

	public Map<String, String> getScriptedMetric() {
		return scriptedMetric;
	}

	public void setScriptedMetric(Map<String, String> scriptedMetric) {
		this.scriptedMetric = scriptedMetric;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getShouldQuery() {
		return shouldQuery;
	}

	public void setShouldQuery(String shouldQuery) {
		this.shouldQuery = shouldQuery;
	}

	public int getMinShouldMatch() {
		return minShouldMatch;
	}

	public void setMinShouldMatch(int minShouldMatch) {
		this.minShouldMatch = minShouldMatch;
	}

	@Override
	public String toString() {
		return "QueryCondition [startTime=" + startTime + ", endTime=" + endTime + ", from=" + from + ", size=" + size
				+ ", index=" + index + ", type=" + type + ", sort=" + sort + ", groupBy=" + groupBy + ", groupBySize="
				+ groupBySize + ", scriptedMetric=" + scriptedMetric + ", actionType=" + actionType + ", shouldQuery="
				+ shouldQuery + ", minShouldMatch=" + minShouldMatch + "]";
	}




}
