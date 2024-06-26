package org.downloader.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.downloader.Bean.Channel;
import org.downloader.Bean.FileClass;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Config {
    public static List<Channel> channels=new LinkedList<>();
    public static Map<String,FileClass> fileClassMap =new HashMap();
    private final static String parentPath = System.getProperty("user.dir");
    private final static String configPath = parentPath + "/config/config.json";

    public static String tmpPath=parentPath+"/tmpDownloadDir";


//    static {
//        readConfig();
//    }

    public static void writeConfig(){
        ConfigProperties configProperties = new ConfigProperties();
        configProperties.setChannels(channels);
        configProperties.setFileClassMap(fileClassMap);
        FileWriter fileWriter = FileWriter.create(new File(configPath),StandardCharsets.UTF_8);
        String jsonPrettyStr = JSONUtil.toJsonPrettyStr(configProperties);
        fileWriter.write(jsonPrettyStr);
    }

    static {
        readConfig();
    }

    public static void writeConfig(List<Channel> channels,Map<String,FileClass>fileClassMap){
        ConfigProperties configProperties = new ConfigProperties();
        configProperties.setChannels(channels);
        configProperties.setFileClassMap(fileClassMap);
        FileWriter fileWriter = FileWriter.create(new File(configPath),StandardCharsets.UTF_8);
        String jsonPrettyStr = JSONUtil.toJsonPrettyStr(configProperties);
        fileWriter.write(jsonPrettyStr);
    }

    private static void readConfig() {
        if (!FileUtil.exist(configPath)) {
            FileWriter writer = new FileWriter(configPath);
            writer.setCharset(StandardCharsets.UTF_8);
            writer.write(template);
        }
        JSON json = JSONUtil.readJSON(new File(configPath), StandardCharsets.UTF_8);
        ConfigProperties configProperties = JSONUtil.toBean(json, ConfigProperties.class, false);
        channels=configProperties.getChannels();
        fileClassMap=configProperties.getFileClassMap();
//        System.out.println(json.toStringPretty());
    }

    @Data
    private static class ConfigProperties {
        public List<Channel> channels=new LinkedList<>();
        public Map<String,FileClass> fileClassMap =new HashMap<>();
    }

    private final static String template="";
}
