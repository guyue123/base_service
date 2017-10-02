package com.base.service.es142.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.nlpcn.es4sql.SearchDao;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;


/**
 * 
 * 初始化ES运行环境
 *
 * @author <a href="mailto:base@base.cn">base</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年8月7日
 */
public class EsEnvFactory {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(EsEnvFactory.class); 
    
    /**
     * 配置字符串
     */
    private String address;

    /**
     * 集群名称
     */
    private String clusterName;

	/**
     * 客户端
     */
    private static TransportClient client;
    private static SearchDao searchDao;

    public void init() throws Exception {
        ImmutableSettings.Builder updateSettings = ImmutableSettings.settingsBuilder();
        updateSettings.put("client.transport.sniff", true); // 使客户端去嗅探整个集群的状态
        updateSettings.put("client.transport.ping_timeout", "20s");// ping
                                                                   // 时间
        updateSettings.put("cluster.name", clusterName); // 集群名称
        // 节点仅仅作为一个客户端而不去保存数据，可以设置node.data设置成false或 node.client设置成true
        updateSettings.put("node.client", "true");
        client = new TransportClient(updateSettings);
        client.addTransportAddress(getTransportAddress());

        // 测试
        ClusterHealthResponse hr = client.admin().cluster().prepareHealth().execute().actionGet();
        if (!hr.isTimedOut()) {
            logger.info("success");
        } else {
            logger.info("fail");
        }

        // 初始化查询对象
        searchDao = new SearchDao(client);
    }

    /**
     * 获得客户端对象
     * 
     * @return
     */
    public static TransportClient getClient() {
        return client;
    }

    /**
     * 初始化服务器地址
     * 
     * @return
     * @throws UnknownHostException 
     * @throws NumberFormatException 
     */
    private TransportAddress getTransportAddress() throws NumberFormatException, UnknownHostException {
        String[] hostPort = getHostPort();
        logger.info(String.format("Connection details: host: %s. port:%s.", hostPort[0],
                hostPort[1]));
        return new InetSocketTransportAddress(InetAddress.getByName(hostPort[0]), Integer.parseInt(hostPort[1]));
    }
    
    /**
     * 分解
     * @return
     */
    private String[] getHostPort() {
        return address.split(",")[0].split(":");
    }

    /**
     * 获得查询对象
     * 
     * @return
     */
    public static SearchDao getSearchDao() {
        return searchDao;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}
