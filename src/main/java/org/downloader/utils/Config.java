package org.downloader.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    public static final String URL = "URL";
    private static final String JSON_CMD = "JSON_CMD";
    private static final String DOWN_CMD = "DOWN_CMD";

    private static final String JSON_FILE="JSON_FILE";
    private static final String SERIAL_FILE="SERIAL_FILE";

    private static final String DOWN_FILE_PATH="DOWN_FILE_PATH";

    private static final String DATA_FORMAT="DATA_FORMAT";

    private static final String POP="POP";

    public static String jsonCmd;
    public static String downCmd;

    public static String jsonFile;
    public static String serialFile;

    public static String downFilePath;

    public static String dataFormat;

    public static String url;

    public static int pop;

    static {
        read();
    }

    public static void read() {
        try {
            String path = System.getProperty("user.dir");
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = null;
            // 使用properties对象加载输入流
            properties.load(Files.newInputStream(Paths.get(path + "/config/config.properties")));
            //获取key对应的value值
            jsonCmd = properties.getProperty(JSON_CMD);
            downCmd = properties.getProperty(DOWN_CMD);
            serialFile = properties.getProperty(SERIAL_FILE);
            jsonFile = properties.getProperty(JSON_FILE);
            downFilePath = properties.getProperty(DOWN_FILE_PATH);
            dataFormat=properties.getProperty(DATA_FORMAT);
            url=properties.getProperty(URL);
            String popStr = properties.getProperty(POP);
            pop = popStr!=null&&!popStr.isEmpty() ?Integer.parseInt(popStr):5;
            if(serialFile.startsWith("."))serialFile=path+serialFile.substring(1);
            if(jsonFile.startsWith("."))jsonFile=path+jsonFile.substring(1);
            if(downFilePath.startsWith("."))downFilePath=path+downFilePath.substring(1);
            System.out.println("serialFile path: "+jsonFile);
            System.out.println("jsonFile path: "+jsonFile);
            System.out.println("download path:" +downFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
