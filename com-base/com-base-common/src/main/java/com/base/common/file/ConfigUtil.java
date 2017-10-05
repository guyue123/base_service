package com.base.common.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取属性文件
 * 
 * @author: hhx
 * @version: 1.0, 2014-5-12
 * 
 */
public final class ConfigUtil {

    /**
     * 获得搜索配置文件配置
     * 
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Properties getConfigProperties(String subPath, String configFile) throws FileNotFoundException,
            IOException {
        String configPath = getConfigFile(subPath, configFile);

        Properties sysconfig = new Properties();
        FileReader reader = new FileReader(configPath);
        sysconfig.load(reader);
        reader.close();
        return sysconfig;
    }

    /**
     * 读取项目目录下配置路径
     * 
     * @param configFile
     * @return
     */
    public static String getConfigFile(String path, String configFile) {
        return FileUtil.getPath(path, configFile);
    }
}
